package org.apollo.archive;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

/**
 * Represents an archive.
 *
 * @author Graham
 */
public final class Archive {

	/**
	 * Decodes the archive in the specified buffer.
	 *
	 * @param buffer The buffer.
	 * @return The archive.
	 * @throws IOException If there is an error decompressing the archive.
	 */
	public static Archive decode(ByteBuffer buffer) throws IOException {
		int extractedSize = Archive.readUnsignedMedium(buffer);
		int size = Archive.readUnsignedMedium(buffer);
		boolean extracted = false;

		if (size != extractedSize) {
			byte[] compressed = new byte[size];
			byte[] decompressed = new byte[extractedSize];
			buffer.get(compressed);
			Archive.debzip2(compressed, decompressed);
			buffer = ByteBuffer.wrap(decompressed);
			extracted = true;
		}

		int entryCount = buffer.getShort() & 0xFFFF;
		int[] identifiers = new int[entryCount];
		int[] extractedSizes = new int[entryCount];
		int[] sizes = new int[entryCount];

		for (int i = 0; i < entryCount; i++) {
			identifiers[i] = buffer.getInt();
			extractedSizes[i] = Archive.readUnsignedMedium(buffer);
			sizes[i] = Archive.readUnsignedMedium(buffer);
		}

		ArchiveEntry[] entries = new ArchiveEntry[entryCount];
		for (int entry = 0; entry < entryCount; entry++) {
			ByteBuffer entryBuffer;
			if (!extracted) {
				byte[] compressed = new byte[sizes[entry]];
				byte[] decompressed = new byte[extractedSizes[entry]];
				buffer.get(compressed);
				Archive.debzip2(compressed, decompressed);
				entryBuffer = ByteBuffer.wrap(decompressed);
			} else {
				byte[] buf = new byte[extractedSizes[entry]];
				buffer.get(buf);
				entryBuffer = ByteBuffer.wrap(buf);
			}
			entries[entry] = new ArchiveEntry(identifiers[entry], entryBuffer);
		}
		return new Archive(entries);
	}

	/**
	 * The entries in this archive.
	 */
	private final ArchiveEntry[] entries;

	/**
	 * Creates a new archive.
	 *
	 * @param entries The entries in this archive.
	 */
	public Archive(ArchiveEntry[] entries) {
		this.entries = entries;
	}

	/**
	 * Gets an {@link ArchiveEntry} by its name.
	 *
	 * @param name The name.
	 * @return The entry.
	 * @throws FileNotFoundException If the entry could not be found.
	 */
	public ArchiveEntry getEntry(String name) throws FileNotFoundException {
		int hash = hash(name);

		for (ArchiveEntry entry : entries) {
			if (entry.getIdentifier() == hash) {
				return entry;
			}
		}
		throw new FileNotFoundException("Could not find entry: " + name + ".");
	}

	/**
	 * Hashes the specified string into an integer used to identify an {@link ArchiveEntry}.
	 *
	 * @param name The name of the entry.
	 * @return The hash.
	 */
	public static int hash(String name) {
		return name.toUpperCase().chars().reduce(0, (hash, character) -> hash * 61 + character - 32);
	}
	
	/**
	 * Debzip2s the compressed array and places the result into the decompressed array.
	 *
	 * @param compressed The compressed array, <strong>without</strong> the header.
	 * @param decompressed The decompressed array.
	 * @throws IOException If there is an error decompressing the array.
	 */
	private static void debzip2(byte[] compressed, byte[] decompressed) throws IOException {
		byte[] newCompressed = new byte[compressed.length + 4];
		newCompressed[0] = 'B';
		newCompressed[1] = 'Z';
		newCompressed[2] = 'h';
		newCompressed[3] = '1';
		System.arraycopy(compressed, 0, newCompressed, 4, compressed.length);
	
		try (DataInputStream is = new DataInputStream(new BZip2CompressorInputStream(new ByteArrayInputStream(newCompressed)))) {
			is.readFully(decompressed);
		}
	}

	/**
	 * Reads a 24-bit medium integer from the specified {@link ByteBuffer}s current position and increases the buffers
	 * position by 3.
	 *
	 * @param buffer The {@link ByteBuffer} to read from.
	 * @return The read 24-bit medium integer.
	 */
	private static int readUnsignedMedium(ByteBuffer buffer) {
		return (buffer.getShort() & 0xFFFF) << 8 | buffer.get() & 0xFF;
	}

}