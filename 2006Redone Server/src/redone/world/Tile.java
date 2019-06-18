package redone.world;

public class Tile {

	private int[] pointer = new int[3];

	public Tile(int x, int y, int z) {
		this.pointer[0] = x;
		this.pointer[1] = y;
		this.pointer[2] = z;
	}

	public Tile(int x, int y) {
		this.pointer[0] = x;
		this.pointer[1] = y;
	}

	public int[] getTile() {
		return pointer;
	}

	public int getTileX() {
		return pointer[0];
	}

	public int getTileY() {
		return pointer[1];
	}

	public int getTileHeight() {
		return pointer[2];
	}

}