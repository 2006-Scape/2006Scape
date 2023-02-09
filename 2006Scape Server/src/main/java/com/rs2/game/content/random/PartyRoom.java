package com.rs2.game.content.random;

import java.awt.Point;
import java.util.Random;

import com.rs2.Constants;
import org.apollo.cache.def.ItemDefinition;

import com.rs2.GameEngine;
import com.rs2.game.items.ItemConstants;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class PartyRoom {
	static int mainInterfaceID = 2156;
	static Random r = new Random();
	static int[] roomItems = new int[216];
	static int[] roomItemsN = new int[216];
	static Balloons[][] balloons = new Balloons[15][14];
	static Point corner = new Point(2730, 3462);
	static long lastAnnouncment;
	static int announcmentFrequency = 1; // announcment frequency in mins

	public static int getAmount() {
		int amount = 0;
		for (int roomItem : roomItems) {
			if (roomItem > 0) {
				amount++;
			}
		}
		return amount;
	}

	public static void startTimer(Player c) {
		if (System.currentTimeMillis() - lastAnnouncment > 1000 * 60 * announcmentFrequency) {
			dropAll();
			lastAnnouncment = System.currentTimeMillis();
		}
	}

	public static void dropAll() {
		int maxTry = 150;
		int trys = 0;
		int amount = getAmount();
		if (amount < 1) {
			return;
		}
		for (int i = 0; i < roomItems.length; i++) {
			int split = r.nextInt(4);
			while (roomItemsN[i] > 0) {
				int x = r.nextInt(balloons.length);
				int y = r.nextInt(balloons[0].length);
				int amt = split > 0 ? Math.max(1, (int) (Math.random() * roomItemsN[i])) : roomItemsN[i];
				// If already balloons there, or on the table, retry
				while ((balloons[x][y] != null || Boundary.isIn(corner.x + x, corner.y + y, Boundary.PARTY_ROOM_TABLE)) && trys < maxTry) {
					x = r.nextInt(balloons.length);
					y = r.nextInt(balloons[0].length);
					trys++;
				}

				if (trys >= maxTry) {
					break;
				}

				balloons[x][y] = Balloons.getBalloon(corner.x + x, corner.y + y, roomItems[i], amt);
				GameEngine.objectHandler.addObject(balloons[x][y]);
				GameEngine.objectHandler.placeObject(balloons[x][y]);

				roomItemsN[i] -= amt;
				if (roomItemsN[i] <= 0) {
					roomItems[i] = 0;
				}
				split--;
			}
		}
		trys = 0;
		for (int i = 0; i < amount * 2; i++) {
			int x = r.nextInt(balloons.length);
			int y = r.nextInt(balloons[0].length);

			// If already balloons there, or on the table, retry
			while ((balloons[x][y] != null || Boundary.isIn(corner.x + x, corner.y + y, Boundary.PARTY_ROOM_TABLE)) && trys < maxTry) {
				x = r.nextInt(balloons.length);
				y = r.nextInt(balloons[0].length);
				trys++;
			}

			if (trys >= maxTry) {
				break;
			}

			balloons[x][y] = Balloons.getEmpty(corner.x + x, corner.y + y);
			GameEngine.objectHandler.addObject(balloons[x][y]);
			GameEngine.objectHandler.placeObject(balloons[x][y]);
		}
	}

	public static void popBalloon(Player player, int x, int y) {
		x = x - corner.x;
		y = y - corner.y;
		Balloons balloon = balloons[x][y];
		if (balloon != null) {
			balloon.popBalloon(player);
		}
		balloons[x][y] = null;
	}

	public static int arraySlot(int[] array, int itemID) {
		int spare = -1;
		for (int x = 0; x < array.length; x++) {
			if (array[x] == itemID && ItemDefinition.lookup(itemID).isStackable()) {
				return x;
			} else if (spare == -1 && array[x] <= 0) {
				spare = x;
			}
		}
		return spare;
	}

	public static void open(Player player) {
		if (!Constants.PARTY_ROOM_DISABLED) {
			updateGlobal(player);
			updateDeposit(player);
			player.getItemAssistant().resetItems(5064); // Player inventory
			player.getPacketSender().sendFrame248(mainInterfaceID, 5063); // Party Drop Chest interface, Deposit interface
		} else {
			player.getPacketSender().sendMessage("The partyroom is currently disabled.");
		}
	}

	public static void accept(Player c) {
		for (int x = 0; x < c.party.length; x++) {
			if (c.partyN[x] > 0) {
				if (ItemDefinition.lookup(c.party[x]).isStackable()) {
					int slot = arraySlot(roomItems, c.party[x]);
					if (slot < 0) {
						c.getPacketSender().sendMessage("Theres not enough space left in the chest.");
						break;
					}
					if (roomItems[slot] != c.party[x]) {
						roomItems[slot] = c.party[x];
						roomItemsN[slot] = c.partyN[x];
					} else {
						roomItemsN[slot] += c.partyN[x];
					}
					c.party[x] = -1;
					c.partyN[x] = 0;
				} else {
					int left = c.partyN[x];
					for (int y = 0; y < left; y++) {
						int slot = arraySlot(roomItems, c.party[x]);
						if (slot < 0) {
							c.getPacketSender().sendMessage("Theres not enough space left in the chest.");
							break;
						}
						roomItems[slot] = c.party[x];
						roomItemsN[slot] = 1;
						c.partyN[x]--;
					}
					if (c.partyN[x] <= 0) {
						c.party[x] = -1;
					}
				}
			}
		}
		updateDeposit(c);
		updateAll();
	}

	public static void updateAll() {
		for (Player p : PlayerHandler.players) {
			if (p == null) {
				continue;
			}
			if (p.lastMainFrameInterface == mainInterfaceID) {
				updateGlobal(p);
			}
		}
	}

	public static void fix(Player c) {
		for (int x = 0; x < 8; x++) {
			if (c.party[x] < 0) {
				c.partyN[x] = 0;
			} else if (c.partyN[x] <= 0) {
				c.party[x] = 0;
			}
		}
	}

	public static void depositItem(Player player, int id, int amount) {
		for (int i : ItemConstants.ITEM_TRADEABLE) {
			if (i == id) {
				player.getPacketSender().sendMessage("You can't deposit this item.");
				return;
			}
			if (id == 995) {
				player.getPacketSender().sendMessage("You can't deposit coins!");
				return;
			}
		}

		amount = Math.min(player.getItemAssistant().getItemAmount(id), amount);

		if (!player.getItemAssistant().playerHasItem(id, amount)) {
			player.getPacketSender().sendMessage("You don't have that many items!");
			return;
		}

		if (ItemDefinition.lookup(id).isStackable()) {
			int slot = arraySlot(player.party, id);
			if (slot == -1) {
				player.getPacketSender().sendMessage("You can only deposit up to 8 items at once.");
				return;
			}

			player.getItemAssistant().deleteItem(id, amount);
			if (player.party[slot] != id) {
				player.party[slot] = id;
				player.partyN[slot] = amount;
			} else {
				player.party[slot] = id;
				player.partyN[slot] += amount;
			}
		} else {
			for (int i = 0; i < amount; i ++) {
				int slot = arraySlot(player.party, id);
				if (slot == -1) {
					player.getPacketSender().sendMessage("You can only deposit up to 8 items at once.");
					updateDeposit(player);
					return;
				}
	
				player.getItemAssistant().deleteItem(id, 1);
				player.party[slot] = id;
				player.partyN[slot] = 1;
			}
		}
		updateDeposit(player);
	}

	public static void withdrawItem(Player c, int slot, int amount) {
		int itemID = c.party[slot];
		amount = Math.min(c.partyN[slot], amount);
		if (c.party[slot] >= 0 && c.getItemAssistant().freeSlots(itemID, amount) > 0) {
			c.getItemAssistant().addItem(c.party[slot], amount);
			c.partyN[slot] -= amount;
			if (c.partyN[slot] <= 0) {
				c.party[slot] = 0;
			}
		}
		updateDeposit(c);
	}

	public static void updateDeposit(Player player) {
		player.getItemAssistant().resetItems(5064);
		player.getPacketSender().sendUpdateItems(2274, player.party, player.partyN);
	}

	public static void updateGlobal(Player player) {
		player.getPacketSender().sendUpdateItems(2273, roomItems, roomItemsN);
	}

	public static void itemOnInterface(Player player, int frame, int slot, int id, int amount) {
		player.outStream.createFrameVarSizeWord(34);
		player.outStream.writeWord(frame);
		player.outStream.writeByte(slot);
		player.outStream.writeWord(id + 1);
		player.outStream.writeByte(255);
		player.outStream.writeDWord(amount);
		player.outStream.endFrameVarSizeWord();
	}
}
