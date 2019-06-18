package redone.game.objects;

public class Objects {

	public long delay, oDelay;
	public int xp, item, owner, target, times;
	public boolean bait;
	public String belongsTo;
	public int objectId;
	public int objectX;
	public int objectY;
	public int objectHeight;
	public int objectFace;
	public int objectType;
	public int objectTicks;

	public int getObjectId() {
		return objectId;
	}

	public int getObjectX() {
		return objectX;
	}

	public int getObjectY() {
		return objectY;
	}

	public Objects(int id, int x, int y, int height, int face, int type, int ticks) {
		objectId = id;
		objectX = x;
		objectY = y;
		objectHeight = height;
		objectFace = face;
		objectType = type;
		objectTicks = ticks;
	}

	public int getObjectHeight() {
		return objectHeight;
	}

	public int getObjectFace() {
		return objectFace;
	}

	public int getObjectType() {
		return objectType;
	}

}
