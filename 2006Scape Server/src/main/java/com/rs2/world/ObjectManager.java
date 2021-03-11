package com.rs2.world;

import java.util.ArrayList;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.quests.QuestAssistant;
import com.rs2.game.objects.Object;
import com.rs2.game.objects.Objects;
import com.rs2.game.objects.impl.FlourMill;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.clip.Region;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private final ArrayList<Object> toRemove = new ArrayList<Object>();
	
	public static void objectTicks(final Player player, final int objectId, final int objectX, final int objectY, final int objectH, final int face, final int objectType, int ticks) {
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				player.getPacketSender().object(objectId, objectX, objectY, objectH, face, objectType);
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, ticks);
	}
	
	public static void singleGateTicks(final Player player, final int objectId, final int newObjectX, final int newObjectY, final int oldObjectX, final int oldObjectY, final int objectH, final int face, int ticks) {
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (player.getGateHandler().gateStatus == player.getGateHandler().CLOSED || player.disconnected) {
					container.stop();
					return;
				}
				GameEngine.objectHandler.placeObject(new Objects(-1, oldObjectX, oldObjectY, objectH, face, 0, 0));
				GameEngine.objectHandler.placeObject(new Objects(objectId, newObjectX, newObjectY, objectH, face, 0, 0));
				container.stop();
			}

			@Override
			public void stop() {
				if (player.getGateHandler().gateStatus == player.getGateHandler().PARTIAL_OPEN) {
					player.getGateHandler().gateStatus = player.getGateHandler().CLOSED;
				}
			}
		}, ticks);
	}
	
	public static void doubleGateTicks(final Player player, final int objectId, final int newObjectX, final int newObjectY, final int oldObjectX, final int oldObjectY, final int oldObjectX2, final int oldObjectY2, final int objectH, final int face, int ticks) {
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (player.getGateHandler().gateStatus == player.getGateHandler().CLOSED || player.disconnected) {
					container.stop();
					return;
				}
				GameEngine.objectHandler.placeObject(new Objects(-1, oldObjectX, oldObjectY, objectH, face, 0, 0));
				GameEngine.objectHandler.placeObject(new Objects(-1, oldObjectX2, oldObjectY2, objectH, face, 0, 0));
				GameEngine.objectHandler.placeObject(new Objects(objectId, newObjectX, newObjectY, objectH, face, 0, 0));
				container.stop();
			}

			@Override
			public void stop() {
				if (player.getGateHandler().gateStatus == player.getGateHandler().OPEN) {
					player.getGateHandler().gateStatus = player.getGateHandler().PARTIAL_OPEN;
				} else if (player.getGateHandler().gateStatus == player.getGateHandler().PARTIAL_OPEN) {
					player.getGateHandler().gateStatus = player.getGateHandler().CLOSED;
				}
			}
		}, ticks);
	}
 
	public boolean objectExists(final int x, final int y) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y) {
				return true;
			}
		}
		return false;
	}

	public void process() {
		for (final Object o : objects) {
			if (o.tick > 0) {
				o.tick--;
			} else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (final Object o : toRemove) {
			/*if (o.objectId == 2732) {
				for (final Player player : PlayerHandler.players) {
					if (player != null) {
						final Client c = (Client) player;
						Server.itemHandler.createGroundItem(c, 592, o.objectX, o.objectY, 1, c.playerId);
						if (c.playerIsCooking) {
							Cooking.resetCooking(c);
						}
					}
				}
			}*/
			if (isObelisk(o.newId)) {
				final int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

	public void removeObject(int x, int y) {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				c.getPacketSender().object(-1, x, y, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				if (loadForPlayer(o, c)) {
					c.getPacketSender().object(o.newId, o.objectX, o.objectY, o.face, o.type);
				}
			}
		}
	}

	public void placeObject(Object o) {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60) {
					c.getPacketSender().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
				}
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height) {
				return o;
			}
		}
		return null;
	}

	public void loadObjects(Player c) {
		if (c == null) {
			return;
		}
		for (Object o : objects) {
			if (loadForPlayer(o, c)) {
				c.getPacketSender().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}
		}
		loadCustomSpawns(c);
	}

	public void loadCustomSpawns(Player c) {
		c.getPacketSender().checkObjectSpawn(2474, 3233, 9312, 0, 10);
		if (c.rope) {
			c.getPacketSender().object(3828, 3227, 3108, 0, 0, 10);
			Region.addObject(3828, 3227, 3108, 0, 10, 0, false);
		} 
		if (c.rope2) {
			c.getPacketSender().object(3828, 3509, 9497, 2, 0, 10);
			Region.addObject(3828, 3509, 9497, 2, 10, 0, false);
		}
		if (c.questPoints >= QuestAssistant.MAXIMUM_QUESTPOINTS) {
			c.getPacketSender().checkObjectSpawn(2403, 3219, 9623, 3, 10);// RFD
		} else {
			c.getPacketSender().checkObjectSpawn(-1, 3219, 9623, 3, 10);// RFD
		}
		// CHEST
		if (c.flourAmount > 0 && c.heightLevel == 0) {
			c.getPacketSender().checkObjectSpawn(FlourMill.FULL_FLOUR_BIN, 3166, 3306, 0, 10);
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

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 },
			{ 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
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
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(),
						obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2,
						1)) {
					c.getPlayerAssistant().startTeleport2(
							obeliskCoords[random][0] + xOffset,
							obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Player c) {
		if (o == null || c == null) {
			return false;
		}

		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}
