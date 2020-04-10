package com.rebotted.game.objects;

import com.rebotted.world.clip.ObjectDefinition;

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

	public int[] getObjectSize() {
		ObjectDefinition def = ObjectDefinition.getObjectDef(objectId);
		if (def == null) {
			return new int[] {1, 1};
		}
		if (objectId == 2781) {
			return new int[] {3, 3};
		}
		int xLength;
		int yLength;
		if (objectFace != 1 && objectFace != 3) {
			xLength = def.xLength();
			yLength = def.yLength();
		} else {
			xLength = def.yLength();
			yLength = def.xLength();
		}

		return new int[] {xLength, yLength};
	}

	@Override
	public String toString() {
		return "Objects{" +
				"objectId=" + objectId +
				", objectX=" + objectX +
				", objectY=" + objectY +
				", objectHeight=" + objectHeight +
				'}';
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
