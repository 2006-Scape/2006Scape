package org.apollo.cache.map;

import com.google.common.base.Preconditions;

/**
 * A 3-dimensional 64x64 area of the map.
 *
 * @author Major
 */
public final class MapFile {

	/**
	 * The array of MapPlanes.
	 */
	private final MapPlane[] planes;

	/**
	 * Creates the MapFile.
	 *
	 * @param planes The {@link MapPlane}s.
	 */
	public MapFile(MapPlane[] planes) {
		this.planes = planes.clone();
	}

	/**
	 * Gets the {@link MapPlane} with the specified level.
	 *
	 * @param plane The plane.
	 * @return The MapPlane.
	 * @throws ArrayIndexOutOfBoundsException If {@code plane} is out of bounds.
	 */
	public MapPlane getPlane(int plane) {
		int length = planes.length;
		Preconditions.checkElementIndex(plane, length, "Plane index out of bounds, must be [0, " + length + ").");
		return planes[plane];
	}

	/**
	 * Gets all of the {@link MapPlane}s in this MapFile.
	 *
	 * @return The MapPlanes.
	 */
	public MapPlane[] getPlanes() {
		return planes.clone();
	}

}