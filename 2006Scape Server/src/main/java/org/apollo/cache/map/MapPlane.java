package org.apollo.cache.map;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A plane of a map, which is a distinct height level.
 *
 * @author Major
 */
public final class MapPlane {

	/**
	 * Returns a shallow copy of the specified 2-dimensional array.
	 *
	 * @param array The array to copy. Must not be {@code null}.
	 * @return The copy.
	 */
	private static <T> T[][] clone(T[][] array) {
		T[][] copy = array.clone();
		for (int index = 0; index < copy.length; index++) {
			copy[index] = array[index].clone();
		}

		return copy;
	}

	/**
	 * The level of this MapPlane.
	 */
	private final int level;

	/**
	 * The 2-dimensional array of Tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Creates the MapPlane.
	 *
	 * @param level The level of the MapPlane.
	 * @param tiles The 2D array of {@link Tile}s. Must not be {@code null}. Must be square.
	 */
	public MapPlane(int level, Tile[][] tiles) {
		this.level = level;
		this.tiles = clone(tiles);
	}

	/**
	 * Gets the level of this MapPlane.
	 *
	 * @return The level.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Gets the amount of tiles in this MapPlane.
	 *
	 * @return The amount of tiles.
	 */
	public int getSize() {
		return tiles.length * tiles[0].length;
	}

	/**
	 * Gets the {@link Tile} at the specified (x, z) coordinate.
	 *
	 * @param x The x coordinate.
	 * @param z The z coordinate.
	 * @return The Tile.
	 */
	public Tile getTile(int x, int z) {
		return tiles[x][z];
	}

	/**
	 * Gets the {@link Tile}s in this MapPlane.
	 * <p>
	 * This method returns the Tiles according on a column-based ordering: for a 2x2 tile set, the order will be
	 * {@code (0, 0), (0, 1), (1, 0), (1, 1)}.
	 *
	 * @return The Tiles.
	 */
	public Stream<Tile> getTiles() {
		return Arrays.stream(tiles).flatMap(Arrays::stream);
	}

}