package redone.game.objects;

import redone.Server;

public class Object {

	public int objectId;
	public int objectX;
	public int objectY;
	public int height;
	public int face, faceOriginal;
	public int type;
	public int newId;
	public int tick;

	public Object(int ID, int X, int Y, int Height, int Face, int Type, int NewId, int Tick) {
		Object p = Server.objectManager.getObject(X, Y, Height);
		if (p != null) {
			if (ID == p.objectId) {
				return;
			}
		}
		objectId = ID;
		objectX = X;
		objectY = Y;
		height = Height;
		face = Face;
		type = Type;
		newId = NewId;
		tick = Tick;
		Server.objectManager.addObject(this);
	}

}
