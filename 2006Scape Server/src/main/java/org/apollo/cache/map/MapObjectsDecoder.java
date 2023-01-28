package org.apollo.cache.map;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.map.MapIndex;
import org.apollo.cache.map.MapConstants;
import org.apollo.cache.map.MapObject;
import org.apollo.util.BufferUtil;
import org.apollo.util.CompressionUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * A decoder for reading the map objects for a given map.
 *
 * @author Major
 */
public final class MapObjectsDecoder {
	/**
	 * Creates a MapObjectsDecoder for the specified map file.
	 *
	 * @param fs The {@link IndexedFileSystem} to get the file from.
	 * @param index The map index to decode objects for.
	 * @return The MapObjectsDecoder.
	 * @throws IOException If there is an error reading or decompressing the file.
	 */
	public static MapObjectsDecoder create(IndexedFileSystem fs, MapIndex index) throws IOException {
		ByteBuffer compressed = fs.getFile(MapConstants.MAP_INDEX, index.getObjectFile());
		ByteBuffer decompressed = ByteBuffer.wrap(CompressionUtil.degzip(compressed));

		return new MapObjectsDecoder(decompressed);
	}

	/**
	 * The buffer to decode {@link MapObject}s from.
	 */
	private final ByteBuffer buffer;

	/**
	 * Create a new {@link MapObjectsDecoder} from the given buffer and map coordinates.
	 *
	 * @param buffer The decompressed object file buffer.
	 */
	public MapObjectsDecoder(ByteBuffer buffer) {
		this.buffer = buffer.asReadOnlyBuffer();
	}

	/**
	 * Decodes the data in the {@code buffer} to a list of {@link MapObject}s.
	 *
	 * @return A list of decoded {@link MapObject}s.
	 */
	public List<MapObject> decode() {
		List<MapObject> objects = new ArrayList<>();

		int id = -1;
		int idOffset = BufferUtil.readSmart(buffer);

		while (idOffset != 0) {
			id += idOffset;

			int packed = 0;
			int positionOffset = BufferUtil.readSmart(buffer);

			while (positionOffset != 0) {
				packed += positionOffset - 1;

				int attributes = buffer.get() & 0xFF;
				int type = attributes >> 2;
				int orientation = attributes & 0x3;
				objects.add(new MapObject(id, packed, type, orientation));

				positionOffset = BufferUtil.readSmart(buffer);
			}

			idOffset = BufferUtil.readSmart(buffer);
		}

		return objects;
	}
}
