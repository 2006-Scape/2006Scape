package com.rs2.world;


import java.util.ArrayList;
import java.util.List;
import com.rs2.GameEngine;
import com.rs2.game.content.skills.core.Mining;
import com.rs2.game.content.skills.woodcutting.Woodcutting;
import com.rs2.game.objects.Objects;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.clip.Region;

/**
 * @author Sanity
 */

public class ObjectHandler {

	public List<Objects> globalObjects = new ArrayList<Objects>();

	public static List<Objects> mapObjects = new ArrayList<Objects>();
	public static List<Objects> removedObjects = new ArrayList<Objects>();

	public ObjectHandler() {
		
	}
	
	 public Objects getObjectByPosition(int x, int y) {
			for (Objects o : globalObjects) {
			    for(int j = 0; j < globalObjects.size(); j++) {
	            globalObjects.get(j);
	            globalObjects.get(j);
	            if(o.objectX == x && o.objectY == y) {
	                return globalObjects.get(j);
	            }	
			}
		}
	    return null;
	 }

	    public void createAnObject(int id, int x, int y, int face) {
	        Objects OBJECT = new Objects(id, x, y, 0, face, 10, 0);
	        if (id == -1) {
	            removeObject(OBJECT);
	        } else {
	            addObject(OBJECT);
	        }
	        //Server.canLoadObjects = true;
	        GameEngine.objectHandler.placeObject(OBJECT);
	    }
		

	public void createAnObject(Player c, int id, int x, int y) {
		Objects OBJECT = new Objects(id, x, y, c.heightLevel, 0, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		GameEngine.objectHandler.placeObject(OBJECT);
	}

	public void createAnObject(Player player, int id, int x, int y, int h, int face) {
		Objects OBJECT = new Objects(id, x, y, h, face, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		GameEngine.objectHandler.placeObject(OBJECT);
	}
	
	public void createAnObject(Player player, int id, int x, int y, int h) {
		Objects OBJECT = new Objects(id, x, y, h, 0, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		GameEngine.objectHandler.placeObject(OBJECT);
	}

	
	public void createAnObject(int id, int x, int y, int h, int face, int type) {
		Objects OBJECT = new Objects(id, x, y, h, face, type, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		//Server.canLoadObjects = true;
		GameEngine.objectHandler.placeObject(OBJECT);
	}

	public void createAnObject(int id, int x, int y) {
		Objects OBJECT = new Objects(id, x, y, 0, 0, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		GameEngine.objectHandler.placeObject(OBJECT);
	}

	/**
	 * Adds object to list
	 **/
	public void addObject(Objects object) {
		globalObjects.add(object);
	}

	/**
	 * Removes object from list
	 **/
	public void removeObject(Objects object) {
		globalObjects.remove(object);
	}

	/**
	 * Does object exist
	 **/
	public Objects objectExists(int objectX, int objectY, int objectHeight) {
		for (Objects o : globalObjects) {
			if (o.getObjectX() == objectX && o.getObjectY() == objectY
					&& o.getObjectHeight() == objectHeight) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Update objects when entering a new region or logging in
	 **/
	public void updateObjects(Player c) {
		for (Objects o : globalObjects) {
			if (c != null) {
				if (c.heightLevel == 0 && o.objectTicks == 0 && c.distanceToPoint(o.getObjectX(), o.getObjectY()) <= 60) {
					if (Woodcutting.playerTrees(c, o.getObjectId()) || Mining.rockExists(o.getObjectId())) {
						c.getPacketSender().object(o.getObjectId(), o.getObjectX(), o.getObjectY(), 0, o.getObjectFace(), o.getObjectType());
					}
				}
				if (c.heightLevel == o.getObjectHeight() && !Woodcutting.playerTrees(c, o.getObjectId()) && !Mining.rockExists(o.getObjectId()) && o.objectTicks == 0 && c.distanceToPoint(o.getObjectX(), o.getObjectY()) <= 60) {
					c.getPacketSender().object(o.getObjectId(), o.getObjectX(), o.getObjectY(), c.heightLevel, o.getObjectFace(), o.getObjectType());
				}
			}
		}
	}

	/**
	 * Creates the object for anyone who is within 60 squares of the object
	 **/
	public void placeObject(Objects o) {
		Region.addClipping(o.getObjectX(), o.getObjectY(), o.getObjectHeight(), 0);
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person.heightLevel == o.getObjectHeight()
						&& o.objectTicks == 0) {
					if (person.distanceToPoint(o.getObjectX(),
							o.getObjectY()) <= 60) {
						removeAllObjects(o);
						globalObjects.add(o);
						person.getPacketSender().object(
								o.getObjectId(), o.getObjectX(),
								o.getObjectY(), o.getObjectFace(),
								o.getObjectType());
						//Region.addObject(o.getObjectId(), o.getObjectX(), o.getObjectY(), o.getObjectHeight(), o.getObjectType(), o.getObjectFace(), true);
					}
				}
			}
		}
	}

	public void removeAllObjects(Objects o) {
		//Using Iterator for concurrency
		globalObjects.removeIf(s -> s.getObjectX() == o.getObjectX() &&
				s.getObjectY() == o.getObjectY() &&
				s.getObjectHeight() == o.getObjectHeight());
	}

	public void process() {
		for (int j = 0; j < globalObjects.size(); j++) {
			if (globalObjects.get(j) != null) {
				Objects o = globalObjects.get(j);
				if (o.objectTicks > 0) {
					o.objectTicks--;
				}
				if (o.objectTicks == 1) {
					Objects deleteObject = objectExists(o.getObjectX(),
							o.getObjectY(), o.getObjectHeight());
					removeObject(deleteObject);
					o.objectTicks = 0;
					placeObject(o);
					removeObject(o);
					if (isObelisk(o.objectId)) {
						int index = getObeliskIndex(o.objectId);
						if (activated[index]) {
							activated[index] = false;
							teleportObelisk(index);
						}
					}
				}
			}

		}
	}

	public final int IN_USE_ID = 14825;

	public boolean isObelisk(int id) {
		for (int obeliskId : obeliskIds) {
			if (obeliskId == id) {
				return true;
			}
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 111235, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 },
			{ 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				Objects obby1 = new Objects(14825, obeliskCoords[index][0],
						obeliskCoords[index][1], 0, -1, 10, 0);
				Objects obby2 = new Objects(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1], 0, -1, 10, 0);
				Objects obby3 = new Objects(14825, obeliskCoords[index][0],
						obeliskCoords[index][1] + 4, 0, -1, 10, 0);
				Objects obby4 = new Objects(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, 0);
				addObject(obby1);
				addObject(obby2);
				addObject(obby3);
				addObject(obby4);
				GameEngine.objectHandler.placeObject(obby1);
				GameEngine.objectHandler.placeObject(obby2);
				GameEngine.objectHandler.placeObject(obby3);
				GameEngine.objectHandler.placeObject(obby4);
				Objects obby5 = new Objects(obeliskIds[index],
						obeliskCoords[index][0], obeliskCoords[index][1], 0,
						-1, 10, 10);
				Objects obby6 = new Objects(obeliskIds[index],
						obeliskCoords[index][0] + 4, obeliskCoords[index][1],
						0, -1, 10, 10);
				Objects obby7 = new Objects(obeliskIds[index],
						obeliskCoords[index][0], obeliskCoords[index][1] + 4,
						0, -1, 10, 10);
				Objects obby8 = new Objects(obeliskIds[index],
						obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, 10);
				addObject(obby5);
				addObject(obby6);
				addObject(obby7);
				addObject(obby8);
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id) {
				return j;
			}
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				if (Misc.goodDistance(c.getX(), c.getY(),
						obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2,
						1)) {
					c.getPlayerAssistant().startTeleport(
							obeliskCoords[random][0] + 2,
							obeliskCoords[random][1] + 2, 0, "null");
				}
			}
		}
	}
}
