package com.rebotted.game.content.random;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.game.items.Item;
import com.rebotted.game.objects.Objects;
import com.rebotted.game.players.Player;

public class PartyRoom {

	static Random r = new Random();
	static int[] roomItems = new int[50];
	static int[] roomItemsN = new int[50];
	static long lastAnnouncment;
	static int announcmentFrequency = 1; // announcment frequency in mins
	static ArrayList<Point> coords = new ArrayList<Point>();

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
		int trys = 0;
		int amount = getAmount();
		if (amount < 1) {
			return;
		}
		for (int x = 0; x < roomItems.length; x++) {
			if (roomItemsN[x] > 0) {
				Balloons b = null;
				do {
					b = Balloons.getBalloon(roomItems[x], roomItemsN[x]);
					trys++;
				} while (coords.contains(Balloons.getCoords()) && trys < 100);
				GameEngine.objectHandler.addObject(b);
				GameEngine.objectHandler.placeObject(b);
			}
			if (trys > 100) {
				break;
			}
			roomItems[x] = 0;
			roomItemsN[x] = 0;
		}
		trys = 0;
		for (int x = 0; x < amount * 2; x++) {
			Objects o;
			do {
				o = Balloons.getEmpty();
			} while (coords.contains(new Point(o.objectX, o.objectY))
					&& trys < 100);
			if (trys > 100) {
				break;
			}
			GameEngine.objectHandler.addObject(o);
			GameEngine.objectHandler.placeObject(o);
		}
		coords.clear();
	}

	public static int arraySlot(int[] array, int target) {
		int spare = -1;
		for (int x = 0; x < array.length; x++) {
			if (array[x] == target) {
				return x;
			} else if (spare == -1 && array[x] <= 0) {
				spare = x;
			}
		}
		return spare;
	}

	public static void open(Player player) {
		if (!GameConstants.PARTY_ROOM_DISABLED) {
			updateGlobal(player);
			updateDeposit(player);
			player.getItemAssistant().resetItems(5064);
			player.getPacketSender().sendFrame248(2156, 5063);
		} else {
			player.getPacketSender().sendMessage("The partyroom has been disabled.");
		}
	}

	public static void accept(Player c) {
		for (int x = 0; x < c.party.length; x++) {
			if (c.partyN[x] > 0) {
				if (Item.itemStackable[c.party[x]]) {
					int slot = arraySlot(roomItems, c.party[x]);
					if (slot < 0) {
						c.getPacketSender().sendMessage(
								"Theres not enough space left in the chest.");
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
						int slot = arraySlot(roomItems, -2);
						if (slot < 0) {
							c.getPacketSender()
									.sendMessage(
											"Theres not enough space left in the chest.");
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
		updateGlobal(c);
	}

	// public static void updateAll() {
	// for (int x = 0; x < PlayerHandler.getPlayers().length; x++) {
	// updateGlobal((Client) PlayerHandler.getPlayers()[x]);
	// }
	// }

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
		int slot = arraySlot(player.party, id);
		for (int i : GameConstants.ITEM_TRADEABLE) {
			if (i == id) {
				player.getPacketSender().sendMessage("You can't deposit this item.");
				return;
			}
			if (id == 995) {
				player.getPacketSender().sendMessage("You can't deposit coins!");
				return;
			}
		}
		if (player.getItemAssistant().getItemAmount(id) < amount) {
			amount = player.getItemAssistant().getItemAmount(id);
		}
		if (!player.getItemAssistant().playerHasItem(id, amount)) {
			player.getPacketSender().sendMessage(
					"You don't have that many items!");
			return;
		}
		if (slot == -1) {
			player.getPacketSender().sendMessage(
					"You cant deposit more than 8 items at once.");
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
		updateDeposit(player);
	}

	public static void withdrawItem(Player c, int slot) {
		if (c.party[slot] >= 0 && c.getItemAssistant().freeSlots() > 0) {
			c.getItemAssistant().addItem(c.party[slot], c.partyN[slot]);
			c.party[slot] = 0;
			c.partyN[slot] = 0;
		}
		updateDeposit(c);
		updateGlobal(c);
	}

	public static void updateDeposit(Player player) {
		player.getItemAssistant().resetItems(5064);
		for (int x = 0; x < 8; x++) {
			if (player.partyN[x] <= 0) {
				itemOnInterface(player, 2274, x, -1, 0);
			} else {
				itemOnInterface(player, 2274, x, player.party[x], player.partyN[x]);
			}
		}
	}

	public static void updateGlobal(Player player) {
		for (int x = 0; x < roomItems.length; x++) {
			if (roomItemsN[x] <= 0) {
				itemOnInterface(player, 2273, x, -1, 0);
			} else {
				itemOnInterface(player, 2273, x, roomItems[x], roomItemsN[x]);
			}
		}
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
