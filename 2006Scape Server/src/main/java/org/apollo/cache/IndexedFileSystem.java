package org.apollo.cache;

import com.google.common.base.Preconditions;
import org.apollo.cache.archive.Archive;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * A file system based on top of the operating system's file system. It consists of a data file and index files. Index
 * files point to blocks in the data file, which contains the actual data.
 *
 * @author Graham
 */
public final class IndexedFileSystem implements Closeable {

	/**
	 * The Map that caches already-decoded Archives.
	 */
	private final Map<FileDescriptor, Archive> cache = new HashMap<>(FileSystemConstants.ARCHIVE_COUNT);

	/**
	 * The index files.
	 */
	private final RandomAccessFile[] indices = new RandomAccessFile[256];

	/**
	 * Read only flag.
	 */
	private final boolean readOnly;

	/**
	 * The cached CRC table.
	 */
	private ByteBuffer crcTable;

	/**
	 * The {@link #crcTable} represented as an {@code int} array.
	 */
	private int[] crcs;

	/**
	 * The data file.
	 */
	private RandomAccessFile data;

	/**
	 * Creates the file system with the specified base directory.
	 *
	 * @param base The base directory.
	 * @param readOnly Indicates whether the file system will be read only or not.
	 * @throws FileNotFoundException If the data files could not be found.
	 */
	public IndexedFileSystem(Path base, boolean readOnly) throws FileNotFoundException {
		this.readOnly = readOnly;
		detectLayout(base);
	}

	@Override
	public void close() throws IOException {
		if (data != null) {
			synchronized (data) {
				data.close();
			}
		}

		for (RandomAccessFile index : indices) {
			if (index != null) {
				synchronized (index) {
					index.close();
				}
			}
		}
	}

	/**
	 * Gets the {@link Archive} pointed to by the specified {@link FileDescriptor}.
	 *
	 * @param type The file type.
	 * @param file The file id.
	 * @return The Archive.
	 * @throws IOException If there is an error decoding the Archive.
	 */
	public Archive getArchive(int type, int file) throws IOException {
		FileDescriptor descriptor = new FileDescriptor(type, file);
		Archive cached = cache.get(descriptor);

		if (cached == null) {
			cached = Archive.decode(getFile(descriptor));

			synchronized (this) {
				cache.put(descriptor, cached);
			}
		}

		return cached;
	}

	/**
	 * Gets the CRC table.
	 *
	 * @return The CRC table.
	 * @throws IOException If there is an error accessing files to create the table.
	 * @throws IllegalStateException If this file system is not read-only.
	 */
	public ByteBuffer getCrcTable() throws IOException {
		if (readOnly) {
			synchronized (this) {
				if (crcTable != null) {
					return crcTable.duplicate();
				}
			}

			int archives = getFileCount(0);
			int hash = 1234;
			int[] crcs = new int[archives];

			CRC32 crc32 = new CRC32();
			for (int i = 1; i < crcs.length; i++) {
				crc32.reset();

				ByteBuffer buffer = getFile(0, i);
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes, 0, bytes.length);
				crc32.update(bytes, 0, bytes.length);

				crcs[i] = (int) crc32.getValue();
			}

			ByteBuffer buffer = ByteBuffer.allocate((crcs.length + 1) * Integer.BYTES);
			for (int crc : crcs) {
				hash = (hash << 1) + crc;
				buffer.putInt(crc);
			}

			buffer.putInt(hash);
			buffer.flip();

			synchronized (this) {
				crcTable = buffer.asReadOnlyBuffer();
				return crcTable.duplicate();
			}
		}

		throw new IllegalStateException("Cannot get CRC table from a writable file system.");
	}

	/**
	 * Gets the CRC table as an {@code int} array.
	 *
	 * @return The CRC table as an {@code int} array.
	 * @throws IOException If there is an error accessing files to create the table.
	 */
	public int[] getCrcs() throws IOException {
		if (crcs == null) {
			ByteBuffer buffer = getCrcTable();
			crcs = new int[(buffer.remaining() / Integer.BYTES) - 1];
			Arrays.setAll(crcs, crc -> buffer.getInt());
		}
		return crcs;
	}

	/**
	 * Gets a file.
	 *
	 * @param descriptor The {@link FileDescriptor} pointing to the file.
	 * @return A {@link ByteBuffer} containing the contents of the file.
	 * @throws IOException If there is an error decoding the file.
	 */
	public ByteBuffer getFile(FileDescriptor descriptor) throws IOException {
		Index index = getIndex(descriptor);
		ByteBuffer buffer = ByteBuffer.allocate(index.getSize());

		long position = index.getBlock() * FileSystemConstants.BLOCK_SIZE;
		int read = 0;
		int size = index.getSize();
		int blocks = size / FileSystemConstants.CHUNK_SIZE;
		if (size % FileSystemConstants.CHUNK_SIZE != 0) {
			blocks++;
		}

		for (int i = 0; i < blocks; i++) {
			byte[] header = new byte[FileSystemConstants.HEADER_SIZE];
			synchronized (data) {
				data.seek(position);
				data.readFully(header);
			}

			position += FileSystemConstants.HEADER_SIZE;

			int nextFile = (header[0] & 0xFF) << 8 | header[1] & 0xFF;
			int curChunk = (header[2] & 0xFF) << 8 | header[3] & 0xFF;
			int nextBlock = (header[4] & 0xFF) << 16 | (header[5] & 0xFF) << 8 | header[6] & 0xFF;
			int nextType = header[7] & 0xFF;

			Preconditions.checkArgument(i == curChunk, "Chunk id mismatch.");

			int chunkSize = size - read;
			if (chunkSize > FileSystemConstants.CHUNK_SIZE) {
				chunkSize = FileSystemConstants.CHUNK_SIZE;
			}

			byte[] chunk = new byte[chunkSize];
			synchronized (data) {
				data.seek(position);
				data.readFully(chunk);
			}
			buffer.put(chunk);

			read += chunkSize;
			position = (long) nextBlock * (long) FileSystemConstants.BLOCK_SIZE;

			// if we still have more data to read, check the validity of the header
			if (size > read) {
				if (nextType != descriptor.getType() + 1) {
					throw new IOException("File type mismatch.");
				}

				if (nextFile != descriptor.getFile()) {
					throw new IOException("File id mismatch.");
				}
			}
		}

		buffer.flip();
		return buffer;
	}

	/**
	 * Gets a file.
	 *
	 * @param type The file type.
	 * @param file The file id.
	 * @return A {@link ByteBuffer} which contains the contents of the file.
	 * @throws IOException If an I/O error occurs.
	 */
	public ByteBuffer getFile(int type, int file) throws IOException {
		return getFile(new FileDescriptor(type, file));
	}

	/**
	 * Checks if this {@link IndexedFileSystem} is read only.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Automatically detect the layout of the specified directory.
	 *
	 * @param base The base directory.
	 * @throws FileNotFoundException If the data files could not be found.
	 */
	private void detectLayout(Path base) throws FileNotFoundException {
		int indexCount = 0;
		for (int index = 0; index < indices.length; index++) {
			Path idx = base.resolve("main_file_cache.idx" + index);
			if (Files.exists(idx) && !Files.isDirectory(idx)) {
				indexCount++;
				indices[index] = new RandomAccessFile(idx.toFile(), readOnly ? "r" : "rw");
			}
		}
		if (indexCount <= 0) {
			throw new FileNotFoundException("No index file(s) present in " + base + ".");
		}

		Path resources = base.resolve("main_file_cache.dat");
		if (Files.exists(resources) && !Files.isDirectory(resources)) {
			data = new RandomAccessFile(resources.toFile(), readOnly ? "r" : "rw");
		} else {
			throw new FileNotFoundException("No data file present.");
		}
	}

	/**
	 * Gets the number of files with the specified type.
	 *
	 * @param type The type.
	 * @return The number of files.
	 * @throws IOException If there is an error getting the length of the specified index file.
	 * @throws IndexOutOfBoundsException If {@code type} is less than 0, or greater than or equal to the amount of
	 * indices.
	 */
	private int getFileCount(int type) throws IOException {
		Preconditions.checkElementIndex(type, indices.length, "File type out of bounds.");

		RandomAccessFile indexFile = indices[type];
		synchronized (indexFile) {
			return (int) (indexFile.length() / FileSystemConstants.INDEX_SIZE);
		}
	}

	/**
	 * Gets the index of a file.
	 *
	 * @param descriptor The {@link FileDescriptor} which points to the file.
	 * @return The {@link Index}.
	 * @throws IOException If there is an error reading from the index file.
	 * @throws IndexOutOfBoundsException If the descriptor type is less than 0, or greater than or equal to the amount
	 * of indices.
	 */
	private Index getIndex(FileDescriptor descriptor) throws IOException {
		int index = descriptor.getType();
		Preconditions.checkElementIndex(index, indices.length, "File descriptor type out of bounds.");

		byte[] buffer = new byte[FileSystemConstants.INDEX_SIZE];
		RandomAccessFile indexFile = indices[index];
		synchronized (indexFile) {
			long position = descriptor.getFile() * FileSystemConstants.INDEX_SIZE;
			if (position >= 0 && indexFile.length() >= position + FileSystemConstants.INDEX_SIZE) {
				indexFile.seek(position);
				indexFile.readFully(buffer);
			} else {
				throw new FileNotFoundException("Could not find find index.");
			}
		}

		return Index.decode(buffer);
	}

}