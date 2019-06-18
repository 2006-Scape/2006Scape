package redone.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import redone.Server;
import redone.game.content.skills.core.Mining;
import redone.game.content.skills.core.Woodcutting;
import redone.game.objects.Objects;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

/**
 * @author Sanity
 */

public class ObjectHandler {

	public List<Objects> globalObjects = new ArrayList<Objects>();

	public static List<Objects> mapObjects = new ArrayList<Objects>();
	public static List<Objects> removedObjects = new ArrayList<Objects>();

	public ObjectHandler() {
		loadGlobalObjects("./Data/cfg/global-objects.cfg");
		// Ladders.loadGlobalLadders("./Data/Ladders/AdvancedLadders.cfg");
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
	        Server.objectHandler.placeObject(OBJECT);
	    }
		

	public void createAnObject(Client c, int id, int x, int y) {
		Objects OBJECT = new Objects(id, x, y, c.heightLevel, 0, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		Server.objectHandler.placeObject(OBJECT);
	}

	public void createAnObject(Client c, int id, int x, int y, int face) {
		Objects OBJECT = new Objects(id, x, y, 0, face, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		Server.objectHandler.placeObject(OBJECT);
	}

	public void createAnObject(int id, int x, int y) {
		Objects OBJECT = new Objects(id, x, y, 0, 0, 10, 0);
		if (id == -1) {
			removeObject(OBJECT);
		} else {
			addObject(OBJECT);
		}
		Server.objectHandler.placeObject(OBJECT);
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
	public void updateObjects(Client c) {
		for (Objects o : globalObjects) {
			if (c != null) {
				if (c.heightLevel == 0 && o.objectTicks == 0 && c.distanceToPoint(o.getObjectX(), o.getObjectY()) <= 60) {
					if (Woodcutting.playerTrees(c, o.getObjectId()) || Mining.rockExists(c, o.getObjectId())) {
						c.getActionSender().object(o.getObjectId(), o.getObjectX(), o.getObjectY(), 0, o.getObjectFace(), o.getObjectType());
					}
				}
				if (c.heightLevel == o.getObjectHeight() && !Woodcutting.playerTrees(c, o.getObjectId()) && !Mining.rockExists(c, o.getObjectId()) && o.objectTicks == 0 && c.distanceToPoint(o.getObjectX(), o.getObjectY()) <= 60) {
					c.getActionSender().object(o.getObjectId(), o.getObjectX(), o.getObjectY(), c.heightLevel, o.getObjectFace(), o.getObjectType());
				}
			}
		}
	}

	/**
	 * Creates the object for anyone who is within 60 squares of the object
	 **/
	public void placeObject(Objects o) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.heightLevel == o.getObjectHeight()
							&& o.objectTicks == 0) {
						if (person.distanceToPoint(o.getObjectX(),
								o.getObjectY()) <= 60) {
							removeAllObjects(o);
							globalObjects.add(o);
							person.getActionSender().object(
									o.getObjectId(), o.getObjectX(),
									o.getObjectY(), o.getObjectFace(),
									o.getObjectType());
							//Region.addObject(o.getObjectId(), o.getObjectX(), o.getObjectY(), o.getObjectHeight(), o.getObjectType(), o.getObjectFace(), true);
						}
					}
				}
			}
		}
	}

	public void removeAllObjects(Objects o) {
		for (Objects s : globalObjects) {
			if (o.getObjectX() == o.objectX && o.getObjectY() == o.objectY
					&& s.getObjectHeight() == o.getObjectHeight()) {
				globalObjects.remove(s);
				break;
			}
		}
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

	public boolean loadGlobalObjects(String fileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		BufferedReader objectFile = null;
		try {
			objectFile = new BufferedReader(new FileReader("./" + fileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(fileName + ": file not found.");
			return false;
		}
		try {
			line = objectFile.readLine();
		} catch (IOException ioexception) {
			Misc.println(fileName + ": error loading file.");
			try {
				objectFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("object")) {
					Objects object = new Objects(Integer.parseInt(token3[0]),
							Integer.parseInt(token3[1]),
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]),
							Integer.parseInt(token3[4]),
							Integer.parseInt(token3[5]), 0);
					addObject(object);
				}
			} else {
				if (line.equals("[ENDOFOBJECTLIST]")) {
					try {
						objectFile.close();
					} catch (IOException ioexception) {
					}
					//return true;
				}
			}
			try {
				line = objectFile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			objectFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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
				Server.objectHandler.placeObject(obby1);
				Server.objectHandler.placeObject(obby2);
				Server.objectHandler.placeObject(obby3);
				Server.objectHandler.placeObject(obby4);
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
