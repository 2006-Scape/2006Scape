package redone.world;

public final class GameObject {

	private final int id;
	private final int type;
	private final int x;
	private final int y;
	private final int face;

	public GameObject(int id, int type, int x, int y, int face) {
		this.id = id;
		this.type = type;
		this.x = x;
		this.y = y;
		this.face = face;
	}

	public int id() {
		return id;
	}

	public int type() {
		return type;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public int getFace() {
		return face;
	}
}
