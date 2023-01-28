package org.apollo.cache.archive;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.util.BufferUtil;
import org.apollo.util.CompressionUtil;

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
		int extractedSize = BufferUtil.readUnsignedMedium(buffer);
		int size = BufferUtil.readUnsignedMedium(buffer);
		boolean extracted = false;

		if (size != extractedSize) {
			byte[] compressed = new byte[size];
			byte[] decompressed = new byte[extractedSize];
			buffer.get(compressed);
			CompressionUtil.debzip2(compressed, decompressed);
			buffer = ByteBuffer.wrap(decompressed);
			extracted = true;
		}

		int entryCount = buffer.getShort() & 0xFFFF;
		int[] identifiers = new int[entryCount];
		int[] extractedSizes = new int[entryCount];
		int[] sizes = new int[entryCount];

		for (int i = 0; i < entryCount; i++) {
			identifiers[i] = buffer.getInt();
			extractedSizes[i] = BufferUtil.readUnsignedMedium(buffer);
			sizes[i] = BufferUtil.readUnsignedMedium(buffer);
		}

		ArchiveEntry[] entries = new ArchiveEntry[entryCount];
		for (int entry = 0; entry < entryCount; entry++) {
			ByteBuffer entryBuffer;
			if (!extracted) {
				byte[] compressed = new byte[sizes[entry]];
				byte[] decompressed = new byte[extractedSizes[entry]];
				buffer.get(compressed);
				CompressionUtil.debzip2(compressed, decompressed);
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

}