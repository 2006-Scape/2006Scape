package org.apollo.cache.map;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.archive.Archive;
import org.apollo.cache.archive.ArchiveEntry;
import org.apollo.cache.map.MapIndex;

/**
 * Decodes {@link MapIndex}s from the {@link IndexedFileSystem}.
 *
 * @author Ryley
 * @author Major
 */
public final class MapIndexDecoder implements Runnable {

	/**
	 * The file id of the versions archive.
	 */
	private static final int VERSIONS_ARCHIVE_FILE_ID = 5;

	/**
	 * The IndexedFileSystem.
	 */
	private final IndexedFileSystem fs;

	public MapIndexDecoder(IndexedFileSystem fs) {
		this.fs = fs;
	}

	/**
	 * Decodes {@link MapIndex}s from the specified {@link IndexedFileSystem}.
	 *
	 * @return A {@link Map} of packed coordinates to their MapDefinitions.
	 * @throws IOException If there is an error reading or decoding the Archive.
	 */
	public Map<Integer, MapIndex> decode() throws IOException {
		Archive archive = fs.getArchive(0, VERSIONS_ARCHIVE_FILE_ID);
		ArchiveEntry entry = archive.getEntry("map_index");
		Map<Integer, MapIndex> definitions = new HashMap<>();

		ByteBuffer buffer = entry.getBuffer();
		int count = buffer.capacity() / (3 * Short.BYTES + Byte.BYTES);

		for (int times = 0; times < count; times++) {
			int id = buffer.getShort() & 0xFFFF;
			int terrain = buffer.getShort() & 0xFFFF;
			int objects = buffer.getShort() & 0xFFFF;
			boolean members = buffer.get() == 1;

			definitions.put(id, new MapIndex(id, terrain, objects, members));
		}

		return definitions;
	}

	@Override
	public void run() {
		try {
			MapIndex.init(decode());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}