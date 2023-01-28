package org.apollo.cache.map;

import org.apollo.cache.def.ItemDefinition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A definition for a map.
 */
public final class MapIndex {

	/**
	 * Indicates whether or not this map is members-only.
	 */
	private final boolean members;

	/**
	 * The object file id.
	 */
	private final int objects;

	/**
	 * The packed coordinates.
	 */
	private final int packedCoordinates;

	/**
	 * The terrain file id.
	 */
	private final int terrain;

	/**
	 * A mapping of region ids to {@link MapIndex}es.
	 */
	private static Map<Integer, MapIndex> indices;

	/**
	 * Initialises the class with the specified set of indices.
	 */
	public static void init(Map<Integer, MapIndex> indices) {
		MapIndex.indices = Collections.unmodifiableMap(indices);
	}

	/**
	 * Gets the {@code Map} of {@link MapIndex} instances.
	 *
	 * @return The map of {@link MapIndex} instances.
	 */
	public static Map<Integer, MapIndex> getIndices() {
		return indices;
	}

	/**
	 * Creates the {@link MapIndex}.
	 *
	 * @param packedCoordinates The packed coordinates.
	 * @param terrain The terrain file id.
	 * @param objects The object file id.
	 * @param members Indicates whether or not this map is members-only.
	 */
	public MapIndex(int packedCoordinates, int terrain, int objects, boolean members) {
		this.packedCoordinates = packedCoordinates;
		this.terrain = terrain;
		this.objects = objects;
		this.members = members;
	}

	/**
	 * Gets the id of the file containing the object data.
	 *
	 * @return The file id.
	 */
	public int getObjectFile() {
		return objects;
	}

	/**
	 * Gets the packed coordinates.
	 *
	 * @return The packed coordinates.
	 */
	public int getPackedCoordinates() {
		return packedCoordinates;
	}

	/**
	 * Gets the id of the file containing the terrain data.
	 *
	 * @return The file id.
	 */
	public int getMapFile() {
		return terrain;
	}

	/**
	 * Gets the X coordinate of this map.
	 *
	 * @return The X coordinate of this map.
	 */
	public int getX() {
		return (packedCoordinates >> 8 & 0xFF) * MapConstants.MAP_WIDTH;
	}

	/**
	 * Gets the Y coordinate of this map.
	 *
	 * @return The y coordinate of this map.
	 */
	public int getY() {
		return (packedCoordinates & 0xFF) * MapConstants.MAP_WIDTH;

	}

	/**
	 * Returns whether or not this MapIndex is for a members-only area of the world.
	 *
	 * @return {@code true} if this MapIndex is for a members-only area, {@code false} if not.
	 */
	public boolean isMembersOnly() {
		return members;
	}

}
