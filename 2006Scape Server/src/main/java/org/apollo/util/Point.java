package org.apollo.util;

/**
 * Represents a point on a 2-dimensional Cartesian plane.
 *
 * @author Major
 */
public final class Point {

	/**
	 * The x coordinate.
	 */
	private final int x;

	/**
	 * The y coordinate.
	 */
	private final int y;

	/**
	 * Creates a new point with the specified coordinates.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate of this point.
	 *
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of this point.
	 *
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}

}