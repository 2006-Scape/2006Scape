package com.rebotted.world;

/**
 * Representing a specific location.
 * 
 * @author Emiel
 *
 */
public class Coordinate {
	
	private int x, y, h;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		h = 0;
	}

	public Coordinate(int x, int y, int h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getH() {
		return h;
	}
	
	/**
	 * Absolute distance between this Coordiante and another.
	 * @param other The other Coordiante.
	 * @return The distance between the 2 Coordinates.
	 */
	public int getDistance(Coordinate other) {
		return (int) Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(h - other.h, 2));
	}
	
	/**
	 * Absolute distance between 2 Coordiantes.
	 * @param c1 The first Coordiante.
	 * @param c2 The the second Coordiante.
	 * @return The distance between the 2 Coordinates.
	 */
	public static int getDistance(Coordinate c1, Coordinate c2) {
		return (int) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2) + Math.pow(c1.h - c2.h, 2));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + h;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Coordinate) {
			Coordinate other = (Coordinate) obj;
			return x == other.x && y == other.y && h == other.h;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + ", h=" + h + "]";
	}
	
}
