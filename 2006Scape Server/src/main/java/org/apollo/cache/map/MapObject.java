package org.apollo.cache.map;

/**
 * Represents a static world object in a map file.
 */
public final class MapObject {

	/**
	 * The object definition id of this {@code MapObject}.
	 */
	private final int id;

	/**
	 * The packed coordinates (local XY and height) for this object.
	 */
	private int packedCoordinates;

	/**
	 * The type of this object.
	 */
	private final int type;

	/**
	 * The orientation of this object.
	 */
	private final int orientation;

	/**
	 * Creates a new {@code MapObject}.
	 *
	 * @param id The object ID of this map object.
	 * @param packedCoordinates A packed integer containing the coordinates of this map object.
	 * @param type The type of object.
	 * @param orientation The object facing direction.
	 */
	public MapObject(int id, int packedCoordinates, int type, int orientation) {
		this.id = id;
		this.packedCoordinates = packedCoordinates;
		this.type = type;
		this.orientation = orientation;
	}

	/**
	 * Create a new {@code MapObject}.
	 *
	 * @param id The object ID of this map object.
	 * @param x The local X coordinate of this object.
	 * @param y The local Y coordinate of this object.
	 * @param height The height level of this object.
	 * @param type The type of this object.
	 * @param orientation The orientation of this object.
	 */
	public MapObject(int id, int x, int y, int height, int type, int orientation) {
		this(id, (height & 0x3f) << 12 | (x & 0x3f) << 6 | (y & 0x3f), type, orientation);
	}

	/**
	 * Get the object ID of this map object.
	 *
	 * @return The object ID for {@link org.apollo.cache.def.ObjectDefinition} lookups.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the plane this map object exists on.
	 *
	 * @return The plane this map object is on.
	 */
	public int getHeight() {
		return packedCoordinates >> 12 & 0x3;
	}

	/**
	 * Get the X coordinate of this object relative to the map position.
	 *
	 * @return The local X coordinate.
	 */
	public int getLocalX() {
		return packedCoordinates >> 6 & 0x3F;
	}

	/**
	 * Get the Y coordinate of this object relative to the map position.
	 *
	 * @return The local Y coordinate.
	 */
	public int getLocalY() {
		return packedCoordinates & 0x3F;
	}

	/**
	 * Get the integer representation of this objects orientation (0 indexed, starting West-North-East-South).
	 *
	 * @return The orientation of this object.
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Get a packed integer containing the x/y coordinates and height for this object.
	 *
	 * @return The packed coordinates.
	 */
	public int getPackedCoordinates() {
		return packedCoordinates;
	}

	/**
	 * Get the type of this object.
	 *
	 * @return The type of this object.
	 */
	public int getType() {
		return type;
	}
}
