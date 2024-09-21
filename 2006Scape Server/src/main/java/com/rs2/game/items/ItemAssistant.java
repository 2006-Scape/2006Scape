package com.rs2.game.items;

import com.rs2.Constants;
import org.apollo.cache.def.ItemDefinition;

import com.rs2.GameEngine;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.skills.runecrafting.Tiaras;
import com.rs2.game.items.impl.Greegree;
import com.rs2.game.items.impl.Greegree.MonkeyData;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.MainFrameIDs;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;

public class ItemAssistant {

	private final Player player;

	public ItemAssistant(Player player2) {
		player = player2;
	}

	private static int COMMON = Misc.random(5), UNCOMMON = Misc.random(25),
			RARE = Misc.random(100);

	private static final int[][] CASKET = { { 995, Misc.random(3000), COMMON },
			{ 1621, 1, UNCOMMON }, { 1619, 1, UNCOMMON }, { 1617, 1, RARE },
			{ 987, 1, RARE }, { 985, 1, RARE }, { 1454, 1, COMMON },
			{ 1452, 1, UNCOMMON }, { 1462, 1, RARE }, { 1623, 1, COMMON } };

	public void addCasketRewards(int itemId) {
		long clickTimer = 0;
		player.getPacketSender().sendMessage("You search the casket...");
		for (int[] element : CASKET) {
			int item = element[0];
			int amount = element[1];
			int chance = element[2];
			if (Misc.random(chance) == 0 && System.currentTimeMillis() - clickTimer > 1800) {
				addItem(item, amount);
				deleteItem(itemId, 1);
				clickTimer = System.currentTimeMillis();
				player.getPacketSender().sendMessage(
						"You find " + amount + " " + DeprecatedItems.getItemName(item) + ".");
			} else {
				if (System.currentTimeMillis() - clickTimer > 1800) {
					addItem(995, 100);
					deleteItem(itemId, 1);
					clickTimer = System.currentTimeMillis();
					player.getPacketSender().sendMessage("You find 100 coins.");
				}
			}
		}
	}

	public void updateInventory() {
		this.resetItems(3214);
	}

	public void destroyInterface(int itemId) {
		itemId = player.droppedItem;
		String itemName = DeprecatedItems.getItemName(player.droppedItem);
		String[][] info = {
				{ "Are you sure you want to destroy this item?", "14174" },
				{ "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" },
				{ "You probably won't be able to", "14182" }, { "get this item back once lost.", "14183" },
				{ itemName, "14184" } };// make some kind of c.getItemInfo
		player.getPacketSender().sendFrame34(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++)
			player.getPacketSender().sendString(info[i][0], Integer.parseInt(info[i][1]));
		player.getPacketSender().sendChatInterface(14170);
	}

	public void destroyItem(int itemId) {
		itemId = player.droppedItem;
		String itemName = DeprecatedItems.getItemName(itemId);
		deleteItem(itemId,getItemSlot(itemId), player.playerItemsN[getItemSlot(itemId)]);
		player.getPacketSender().sendMessage("Your " + itemName + " vanishes as you destroy it.");
		player.getPacketSender().closeAllWindows();
	}

	public void dropItem(int itemId) {
		itemId = player.droppedItem;
		GameEngine.itemHandler.createGroundItem(player, itemId, player.getX(), player.getY(), player.playerItemsN[getItemSlot(itemId)], player.getId());
		deleteItem(itemId,getItemSlot(itemId), player.playerItemsN[getItemSlot(itemId)]);
		player.getPacketSender().closeAllWindows();
	}

	public void addOrDropItem(int item, int amount) {
		if (ItemDefinition.lookup(item).isStackable() && hasFreeSlots(1)) {
			addItem(item, amount);
		} else if (!hasFreeSlots(amount) && !ItemDefinition.lookup(item).isStackable()) {
			GameEngine.itemHandler.createGroundItem(player, item, player.getX(), player.getY(), amount, player.playerId);
			player.getPacketSender().sendMessage("You have no inventory space, so the item(s) appear beneath you.");
		} else if (ItemDefinition.lookup(item).isStackable() && !hasFreeSlots(1) && !playerHasItem(item)) {
			GameEngine.itemHandler.createGroundItem(player, item, player.getX(), player.getY(), amount, player.playerId);
			player.getPacketSender().sendMessage("You have no inventory space, so the item(s) appear beneath you.");
		} else {
			addItem(item, amount);
		}
	}

	public boolean hasFreeSlots(int slots) {
		return freeSlots() >= slots;
	}

	public void replaceItem(int itemToReplace, int replaceWith) {
		replaceItem(itemToReplace, replaceWith, 1);
	}

	public void replaceItem(int itemToReplace, int replaceWith, int amount) {
		if(playerHasItem(itemToReplace, amount)) {
			deleteItem(itemToReplace, amount);
			addItem(replaceWith, amount);
		}
	}

	public static int getTotalAmountEquipment(Client c) {
		int total = 0;
		for (int element : c.playerEquipment) {
			total = (int) Math.floor(c.getShopAssistant().getItemShopValue(
					element));
		}
		return total;
	}

	public static int getTotalAmountItems(Client c) {
		int total = 0;
		for (int playerItem : c.playerItems) {
			total = (int) Math.floor(c.getShopAssistant().getItemShopValue(
					playerItem));
		}
		return total;
	}

	public static int getTotalWealthCarrying(Client c) {
		return getTotalAmountItems(c) + getTotalAmountEquipment(c);
	}

	public static String getTotalAmount(Client c, int j) {
		if (j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if (j >= 10000000 && j < 2147000000) {
			return j / 1000000 + "M";
		} else {
			return "" + j + " gp";
		}
	}

	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 },
			{ 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 },
			{ 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 },
			{ 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 },
			{ 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 },
			{ 4747, 4926 }, { 4749, 4968 }, { 4751, 4794 }, { 4753, 4980 },
			{ 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	public boolean playerHasEquipped(int itemID) {
		itemID++;
		for (int element : player.playerEquipment) {
			if (element == itemID) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasEquipped(int slot, int itemID) {
		return player.playerEquipment[slot] == itemID;
	}

	public void addItemToBank(int itemId, int amount) {
		itemId++;
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.bankItems[i] <= 0 || player.bankItems[i] == itemId && player.bankItemsN[i] + amount < Integer.MAX_VALUE) {
				player.bankItems[i] = itemId;
				player.bankItemsN[i] += amount;
				resetBank();
				return;
			}
		}
	}

	public void removeItemFromBank(int itemId, int amount) {
		itemId++;
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.bankItems[i] == itemId) {
				player.bankItemsN[i] -= amount;
				if (player.bankItemsN[i] <= 0) {
					player.bankItems[i] = 0;
					player.bankItemsN[i] = 0;
				}
				resetBank();
				rearrangeBank();
				return;
			}
		}
	}

	public void resetItems(int WriteFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			player.getOutStream().writeWord(player.playerItems.length);
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(player.playerItemsN[i]);
				} else {
					player.getOutStream().writeByte(player.playerItemsN[i]);
				}
				player.getOutStream().writeWordBigEndianA(player.playerItems[i]);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}
	
	//for inv
	public int getItemAmount(int itemID) {
		int itemCount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] - 1 == itemID) {
				itemCount += player.playerItemsN[i];
			}
		}
		return itemCount;
	}
	
	//for bank
	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] == itemID) {
				tempAmount += player.playerItemsN[i];
			}
		}
		return tempAmount;
	}


	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < player.playerBonus.length; i++) {
			if (player.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + player.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -" + java.lang.Math.abs(player.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			player.getPacketSender().sendString(send, 1675 + i + offset);
		}

	}

	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < player.playerItems.length; j++) {
			if (ItemDefinition.lookup(itemID + 1).isNote()) {
				if (itemID + 2 == player.playerItems[j]) {
					count += player.playerItemsN[j];
				}
			}
			if (!ItemDefinition.lookup(itemID + 1).isNote()) {
				if (itemID + 1 == player.playerItems[j]) {
					count += player.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < player.bankItems.length; j++) {
			if (player.bankItems[j] == itemID + 1) {
				count += player.bankItemsN[j];
			}
		}
		return count;
	}

	public int getBankItemCount() {
		int count = 0;
		for (int j = 0; j < player.bankItems.length; j++) {
			if (player.bankItems[j] > -1) {
				count += player.bankItemsN[j];
			}
		}
		return count;
	}

	/**
	 * Item kept on death
	 **/

	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] - 1 > 0) {
				int inventoryItemValue = player.getShopAssistant().getItemShopValue(
						player.playerItems[i] - 1);
				if (inventoryItemValue > value && !player.invSlot[i]) {
					value = inventoryItemValue;
					item = player.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < player.playerEquipment.length; i1++) {
			if (player.playerEquipment[i1] > 0) {
				int equipmentItemValue = player.getShopAssistant().getItemShopValue(
						player.playerEquipment[i1]);
				if (equipmentItemValue > value && !player.equipSlot[i1]) {
					value = equipmentItemValue;
					item = player.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			player.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(player.playerItems[slotId] - 1,
						getItemSlot(player.playerItems[slotId] - 1), 1);
			}
		} else {
			player.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		player.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death
	 **/

	public void resetKeepItems() {
		for (int i = 0; i < player.itemKeptId.length; i++) {
			player.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < player.invSlot.length; i1++) {
			player.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < player.equipSlot.length; i2++) {
			player.equipSlot[i2] = false;
		}
	}

	/**
	 * delete all items
	 **/

	public void deleteAllItems() {
		for (int i1 = 0; i1 < player.playerEquipment.length; i1++) {
			deleteEquipment(player.playerEquipment[i1], i1);
		}
		for (int i = 0; i < player.playerItems.length; i++) {
			deleteItem(player.playerItems[i] - 1, getItemSlot(player.playerItems[i] - 1), player.playerItemsN[i]);
		}
	}

	/**
	 * Clear Bank
	 */

	public void clearBank() {
		try {
			for (int i = 0; i < player.bankItems[i]; i++) {
				player.bankItems[i] = 0;
				player.bankItemsN[i] = 0;
			}
			resetTempItems();
			resetBank();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drop all items for your killer
	 **/

	public void dropAllItems() {
		Client o = (Client) PlayerHandler.players[player.killerId];

		for (int i = 0; i < player.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(player.playerItems[i] - 1)) {
					GameEngine.itemHandler.createGroundItem(o,
							player.playerItems[i] - 1, player.getX(), player.getY(),
							player.playerItemsN[i], player.killerId);
				} else {
					if (specialCase(player.playerItems[i] - 1)) {
						GameEngine.itemHandler.createGroundItem(o, 995, player.getX(),
								player.getY(),
								getUntradePrice(player.playerItems[i] - 1),
								player.killerId);
					}
					GameEngine.itemHandler.createGroundItem(player,
							player.playerItems[i] - 1, player.getX(), player.getY(),
							player.playerItemsN[i], player.playerId);
				}
			} else {
				GameEngine.itemHandler.createGroundItem(player, player.playerItems[i] - 1,
						player.getX(), player.getY(), player.playerItemsN[i], player.playerId);
			}
		}
		for (int e = 0; e < player.playerEquipment.length; e++) {
			if (o != null) {
				if (tradeable(player.playerEquipment[e])) {
					GameEngine.itemHandler.createGroundItem(o,
							player.playerEquipment[e], player.getX(), player.getY(),
							player.playerEquipmentN[e], player.killerId);
				} else {
					if (specialCase(player.playerEquipment[e])) {
						GameEngine.itemHandler.createGroundItem(o, 995, player.getX(),
								player.getY(),
								getUntradePrice(player.playerEquipment[e]),
								player.killerId);
					}
					GameEngine.itemHandler.createGroundItem(player,
							player.playerEquipment[e], player.getX(), player.getY(),
							player.playerEquipmentN[e], player.playerId);
				}
			} else {
				GameEngine.itemHandler.createGroundItem(player, player.playerEquipment[e],
						player.getX(), player.getY(), player.playerEquipmentN[e], player.playerId);
			}
		}
		if (o != null) {
			GameEngine.itemHandler.createGroundItem(o, 526, player.getX(), player.getY(), 1,
					player.killerId);
		}
	}

	public int getUntradePrice(int item) {
		switch (item) {
			case 2518:
			case 2524:
			case 2526:
				return 100000;
			case 2520:
			case 2522:
				return 150000;
		}
		return 0;
	}

	public boolean specialCase(int itemId) {
		switch (itemId) {
			case 2518:
			case 2520:
			case 2522:
			case 2524:
			case 2526:
				return true;
		}
		return false;
	}

	public boolean tradeable(int itemId) {
		for (int element : ItemConstants.ITEM_TRADEABLE) {
			if (itemId == element) {
				return false;
			}
		}
		return true;
	}

	public boolean addItem(int item, int amount) {
		if (item == CastleWars.SARA_BANNER || item == CastleWars.ZAMMY_BANNER) {
			return false;
		}
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((freeSlots() >= 1 || playerHasItem(item))
				&& ItemDefinition.lookup(item).isStackable() || freeSlots() > 0
				&& !ItemDefinition.lookup(item).isStackable()) {
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] == item + 1 && ItemDefinition.lookup(item).isStackable()
						&& player.playerItems[i] > 0) {
					player.playerItems[i] = item + 1;
					if (player.playerItemsN[i] + amount < Constants.MAXITEM_AMOUNT
							&& player.playerItemsN[i] + amount > -1) {
						player.playerItemsN[i] += amount;
					} else {
						player.playerItemsN[i] = Constants.MAXITEM_AMOUNT;
					}
					if (player.getOutStream() != null && player != null) {
						player.getOutStream().createFrameVarSizeWord(34);
						player.getOutStream().writeWord(3214);
						player.getOutStream().writeByte(i);
						player.getOutStream().writeWord(player.playerItems[i]);
						if (player.playerItemsN[i] > 254) {
							player.getOutStream().writeByte(255);
							player.getOutStream().writeDWord(player.playerItemsN[i]);
						} else {
							player.getOutStream().writeByte(player.playerItemsN[i]);
						}
						player.getOutStream().endFrameVarSizeWord();
						player.flushOutStream();
					}
					i = 30;
					Weight.updateWeight(player);
					return true;
				}
			}
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] <= 0) {
					player.playerItems[i] = item + 1;
					if (amount < Constants.MAXITEM_AMOUNT && amount > -1) {
						player.playerItemsN[i] = 1;
						if (amount > 1) {
							addItem(item, amount - 1);
							return true;
						}
					} else {
						player.playerItemsN[i] = Constants.MAXITEM_AMOUNT;
					}
					resetItems(3214);
					i = 30;
					Weight.updateWeight(player);
					return true;
				}
			}
			return false;
		} else {
			resetItems(3214);
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return false;
		}
	}

	/**
	 * Bonuses
	 **/

	public final String[] BONUS_NAMES = { "Stab", "Slash", "Crush", "Magic",
			"Range", "Stab", "Slash", "Crush", "Magic", "Range", "Strength",
			"Prayer" };

	public void resetBonus() {
		for (int i = 0; i < player.playerBonus.length; i++) {
			player.playerBonus[i] = 0;
		}
	}

	public void getBonus() {
		for (int element : player.playerEquipment) {
			int[] bonuses = ItemDefinitions.getBonus(element);
			for (int k = 0; k < player.playerBonus.length; k++) {
				player.playerBonus[k] += bonuses[k];
			}
		}
	}

	/**
	 * Wear Item
	 **/

	public void sendWeapon(int weapon, String weaponName) {
		String newWeapon = weaponName.replaceAll("Bronze", "");
		newWeapon = newWeapon.replaceAll("Iron", "");
		newWeapon = newWeapon.replaceAll("Steel", "");
		newWeapon = newWeapon.replaceAll("Black", "");
		newWeapon = newWeapon.replaceAll("Mithril", "");
		newWeapon = newWeapon.replaceAll("Adamant", "");
		newWeapon = newWeapon.replaceAll("Rune", "");
		newWeapon = newWeapon.replaceAll("Granite", "");
		newWeapon = newWeapon.replaceAll("Dragon", "");
		newWeapon = newWeapon.replaceAll("Drag", "");
		newWeapon = newWeapon.replaceAll("Crystal", "");
		newWeapon = newWeapon.trim();
		if (weaponName.equals("Unarmed")) {
			player.getPacketSender().setSidebarInterface(0, 5855); // punch,
			// kick,
			// block
			player.getPacketSender().sendString(weaponName, 5857);
		} else if (weaponName.endsWith("whip")) {
			player.getPacketSender().setSidebarInterface(0, 12290); // flick, lash, deflect
			player.getPacketSender().sendFrame246(12291, 200, weapon);
			player.getPacketSender().sendString(weaponName, 12293);
		} else if (weaponName.endsWith("bow") || weaponName.endsWith("10")
				|| weaponName.endsWith("full")
				|| weaponName.startsWith("seercull")) {
			player.getPacketSender().setSidebarInterface(0, 1764); // accurate, rapid, longrange
			player.getPacketSender().sendFrame246(1765, 200, weapon);
			player.getPacketSender().sendString(weaponName, 1767);
		} else if (weaponName.startsWith("Staff")
				|| weaponName.endsWith("staff") || weaponName.endsWith("wand")) {
			player.getPacketSender().setSidebarInterface(0, 328); // spike, impale, smash, block
			player.getPacketSender().sendFrame246(329, 200, weapon);
			player.getPacketSender().sendString(weaponName, 331);
		} else if (newWeapon.startsWith("dart")
				|| newWeapon.startsWith("knife")
				|| newWeapon.startsWith("javelin")
				|| weaponName.equalsIgnoreCase("toktz-xil-ul")) {
			player.getPacketSender().setSidebarInterface(0, 4446); // accurate, rapid, longrange
			player.getPacketSender().sendFrame246(4447, 200, weapon);
			player.getPacketSender().sendString(weaponName, 4449);
		} else if (newWeapon.startsWith("dagger")
				|| newWeapon.startsWith("sword")) {
			player.getPacketSender().setSidebarInterface(0, 2276); // stab, lunge, slash, block
			player.getPacketSender().sendFrame246(2277, 200, weapon);
			player.getPacketSender().sendString(weaponName, 2279);
		} else if (newWeapon.startsWith("longsword")) {
			player.getPacketSender().setSidebarInterface(0, 2423); // chop, slash, lunge, block
			player.getPacketSender().sendFrame246(2424, 200, weapon);
			player.getPacketSender().sendString(weaponName, 2426);
		} else if (newWeapon.startsWith("pickaxe")) {
			player.getPacketSender().setSidebarInterface(0, 5570); // spike, impale, smash, block
			player.getPacketSender().sendFrame246(5571, 200, weapon);
			player.getPacketSender().sendString(weaponName, 5573);
		} else if (newWeapon.startsWith("axe")
				|| newWeapon.startsWith("battleaxe")) {
			player.getPacketSender().setSidebarInterface(0, 1698); // chop, hack, smash, block
			player.getPacketSender().sendFrame246(1699, 200, weapon);
			player.getPacketSender().sendString(weaponName, 1701);
		} else if (newWeapon.startsWith("halberd")) {
			player.getPacketSender().setSidebarInterface(0, 8460); // jab, swipe, fend
			player.getPacketSender().sendFrame246(8461, 200, weapon);
			player.getPacketSender().sendString(weaponName, 8463);
		} else if (newWeapon.startsWith("Scythe")) {
			player.getPacketSender().setSidebarInterface(0, 8460); // jab, swipe, fend
			player.getPacketSender().sendFrame246(8461, 200, weapon);
			player.getPacketSender().sendString(weaponName, 8463);
		} else if (newWeapon.startsWith("spear")) {
			player.getPacketSender().setSidebarInterface(0, 4679); // lunge, swipe, pound, block
			player.getPacketSender().sendFrame246(4680, 200, weapon);
			player.getPacketSender().sendString(weaponName, 4682);
		} else if (newWeapon.toLowerCase().contains("mace")) {
			player.getPacketSender().setSidebarInterface(0, 3796); // pound, pummel, spike, block
			player.getPacketSender().sendFrame246(3797, 200, weapon);
			player.getPacketSender().sendString(weaponName, 3799);
		} else if (newWeapon.toLowerCase().contains("tzhaar") || newWeapon.toLowerCase().contains("warhammer") || player.playerEquipment[player.playerWeapon] == 4153) {
			player.getPacketSender().setSidebarInterface(0, 425); // war hammer equip
			player.getPacketSender().sendFrame246(426, 200, weapon);
			player.getPacketSender().sendString(weaponName, 428);
		} else {
			player.getPacketSender().setSidebarInterface(0, 2423); // chop, slash, lunge, block
			player.getPacketSender().sendFrame246(2424, 200, weapon);
			player.getPacketSender().sendString(weaponName, 2426);
		}

	}

	/**
	 * Weapon Requirements
	 **/

	public void getRequirements(String itemName, int itemId) {
		player.attackLevelReq = player.defenceLevelReq = player.strengthLevelReq = player.rangeLevelReq = player.magicLevelReq = player.agilityLevelReq = player.slayerLevelReq = 0;
		if (itemName.contains("mystic") || itemName.contains("nchanted")) {
			if (itemName.contains("staff")) {
				player.magicLevelReq = 20;
				player.attackLevelReq = 40;
			} else {
				player.magicLevelReq = 20;
				player.defenceLevelReq = 20;
			}
		}
		if (itemName.contains("infinity")) {
			player.magicLevelReq = 50;
			player.defenceLevelReq = 25;
		}
		if (itemName.contains("splitbark")) {
			player.magicLevelReq = 40;
			player.defenceLevelReq = 40;
		}
		if (itemName.contains("green d'hide")) {
			player.rangeLevelReq = 40;
			if (itemName.contains("body")) {
				player.defenceLevelReq = 40;
			}
		}
		if (itemName.contains("blue d'hide")) {
			player.rangeLevelReq = 50;
			if (itemName.contains("body")) {
				player.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("red d'hide")) {
			player.rangeLevelReq = 60;
			if (itemName.contains("body")) {
				player.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("black d'hide")) {
			if (itemName.contains("body")) {
				player.defenceLevelReq = 40;
			}
			player.rangeLevelReq = 70;
		}
		if (itemName.contains("bronze")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("iron")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("steel")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 5;
			}
			return;
		}
		if (itemName.contains("black")) {
			if (!itemName.contains("knife")
					&& !itemName.equalsIgnoreCase("Black skirt")
					&& !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")
					&& !itemName.contains("vamb") && !itemName.contains("chap") && !itemName.equalsIgnoreCase("Black robe")) {
				player.attackLevelReq = player.defenceLevelReq = 10;
			}
			return;
		}
		if (itemName.contains("mithril")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 20;
			}
			return;
		}
		if (itemName.contains("adamant")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				player.attackLevelReq = player.defenceLevelReq = 30;
			}
			return;
		}
		if (itemName.contains("rune")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")
					&& !itemName.contains("'bow")) {
				player.attackLevelReq = player.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("dragon")) {
			if (!itemName.contains("nti-") && !itemName.contains("fire")) {
				player.attackLevelReq = player.defenceLevelReq = 60;
				return;
			}
		}
		if (itemName.contains("crystal")) {
			if (itemName.contains("shield")) {
				player.defenceLevelReq = 70;
			} else {
				player.rangeLevelReq = 70;
			}
			return;
		}
		if (itemName.contains("ahrim")) {
			if (itemName.contains("staff")) {
				player.magicLevelReq = 70;
				player.attackLevelReq = 70;
			} else {
				player.magicLevelReq = 70;
				player.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("karil")) {
			if (itemName.contains("crossbow")) {
				player.rangeLevelReq = 70;
			} else {
				player.rangeLevelReq = 70;
				player.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("godsword")) {
			player.attackLevelReq = 75;
		}
		if (itemName.contains("3rd age") && !itemName.contains("amulet")) {
			player.defenceLevelReq = 60;
		}
		if (itemName.contains("Initiate")) {
			player.defenceLevelReq = 20;
		}
		if (itemName.contains("verac") || itemName.contains("guthan")
				|| itemName.contains("dharok") || itemName.contains("torag")) {

			if (itemName.contains("hammers")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("axe")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("warspear")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else if (itemName.contains("flail")) {
				player.attackLevelReq = 70;
				player.strengthLevelReq = 70;
			} else {
				player.defenceLevelReq = 70;
			}
		}

		switch (itemId) {
			case 8839:
			case 8840:
			case 8842:
			case 11663:
			case 11664:
			case 11665:
				player.attackLevelReq = 42;
				player.rangeLevelReq = 42;
				player.strengthLevelReq = 42;
				player.magicLevelReq = 42;
				player.defenceLevelReq = 42;
				return;
			case 10551:
			case 2503:
			case 2501:
			case 2499:
			case 1135:
				player.defenceLevelReq = 40;
				return;
			case 1133:
				player.defenceLevelReq = 20;
				player.rangeLevelReq = 20;
				return;
			case 11235:
			case 6522:
				player.rangeLevelReq = 60;
				break;
			case 1097:
				player.rangeLevelReq = 20;
				break;
			case 864:
			case 863:
				player.rangeLevelReq = 1;
				break;
			case 865:
				player.rangeLevelReq = 5;
				break;
			case 866:
				player.rangeLevelReq = 20;
				break;
			case 867:
				player.rangeLevelReq = 30;
				break;
			case 868:
				player.rangeLevelReq = 40;
				break;
			case 6524:
				player.defenceLevelReq = 60;
				break;
			case 11284:
				player.defenceLevelReq = 75;
				return;
			case 6889:
			case 6914:
				player.magicLevelReq = 60;
				break;
			case 10828:
				player.defenceLevelReq = 55;
				break;
			case 11724:
			case 11726:
			case 11728:
				player.defenceLevelReq = 65;
				break;
			case 847:
			case 849:
				player.rangeLevelReq = 20;
				break;
			case 843:
			case 845:
				player.rangeLevelReq = 5;
				break;
			case 851:
			case 853:
				player.rangeLevelReq = 30;
				break;
			case 855:
			case 857:
				player.rangeLevelReq = 40;
				break;
			case 859:
			case 861:
				player.rangeLevelReq = 50;
				break;
			case 3749:
			case 3751:
			case 3755:
			case 3753:
				player.defenceLevelReq = 45;
				break;

			case 7462:
			case 7461:
				player.defenceLevelReq = 40;
				break;
			case 8846:
				player.defenceLevelReq = 5;
				break;
			case 8847:
				player.defenceLevelReq = 10;
				break;
			case 8848:
				player.defenceLevelReq = 20;
				break;
			case 8849:
				player.defenceLevelReq = 30;
				break;
			case 8850:
				player.defenceLevelReq = 40;
				break;

			case 7460:
				player.defenceLevelReq = 40;
				break;

			case 837:
				player.rangeLevelReq = 61;
				break;

			case 4151: // if you don't want to use names
				player.attackLevelReq = 70;
				return;

			case 6724: // seercull
				player.rangeLevelReq = 60; // idk if that is correct
				return;
			case 6523:
			case 6525:
			case 6527:
				player.attackLevelReq = 60;
				return;
			case 6526:
				player.attackLevelReq = 60;
				player.magicLevelReq = 60;
				return;
			case 4156:
				player.defenceLevelReq = 20;
				player.slayerLevelReq = 25;
				return;
			case 1391:
			case 1393:
			case 1395:
			case 1397:
			case 1399:
			case 3053:
				player.attackLevelReq = 30;
				player.magicLevelReq = 30;
				return;
			case 4158:
				player.slayerLevelReq = 55;
				player.attackLevelReq = 50;
				return;
			case 4153:
				player.attackLevelReq = 50;
				player.strengthLevelReq = 50;
				return;
			case 6528:
				player.strengthLevelReq = 60;
				return;
			case 4161:
				player.slayerLevelReq = 20;
				return;
			case 4168:
				player.slayerLevelReq = 60;
				return;
			case 6696:
				player.slayerLevelReq = 22;
				return;
			case 8923:
				player.slayerLevelReq = 35;
				return;
			case 7159:
				player.slayerLevelReq = 37;
				return;
			case 6708:
				player.slayerLevelReq = 42;
				return;
			case 4170:
				player.slayerLevelReq = 55;
				return;
			case 4162:
				player.slayerLevelReq = 75;
				return;
			case 7421:
			case 7422:
			case 7423:
			case 7424:
			case 7425:
			case 7426:
			case 7427:
			case 7428:
			case 7429:
			case 7430:
			case 7431:
			case 7432:
				player.slayerLevelReq = 57;
				return;
			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
				player.agilityLevelReq = 50;
				player.rangeLevelReq = 70;
				return;
			case 4150:
			case 4160:
			case 4172:
			case 4174:
				player.slayerLevelReq = 55;
				return;
			case 1015:
				player.defenceLevelReq = 1;
				return;
			case 6664:
				player.slayerLevelReq = 32;
				return;
			case 4551:
				player.defenceLevelReq = 5;
				return;
			case 7051:
				player.slayerLevelReq = 33;
				return;
			case 4166:
				player.slayerLevelReq = 15;
				return;
			case 4164:
				player.slayerLevelReq = 10;
				return;
		}
	}

	/**
	 * two handed weapon check
	 **/
	public boolean is2handed(String itemName, int itemId) {
		if (itemName.contains("ahrim") || itemName.contains("karil") || itemName.contains("verac") || itemName.contains("guthan") || itemName.contains("dharok") || itemName.contains("torag")) {
			return true;
		}
		if (itemName.contains("claws")) {
			return true;
		}
		if (itemName.contains("longbow") || itemName.contains("shortbow") || itemName.contains("ark bow")) {
			return true;
		}
		if (itemName.contains("crystal")) {
			return true;
		}
		if (itemName.contains("2h") || itemName.contains("spear")) {
			return true;
		}
		switch (itemId) {
			case 6724: // seercull
			case 11730:
			case 4153:
			case 6528:
				return true;
		}
		return false;
	}

	/**
	 * Weapons special bar, adds the spec bars to weapons that require them and
	 * removes the spec bars from weapons which don't require them
	 **/

	public void addSpecialBar(int weapon) {
		switch (weapon) {

			case 4151: // whip
				player.getPacketSender().sendHideInterfaceLayer(12323, false);
				specialAmount(weapon, player.specAmount, 12335);
				break;

			case 859: // magic bows
			case 861:
			case 11235:
				player.getPacketSender().sendHideInterfaceLayer(7549, false);
				specialAmount(weapon, player.specAmount, 7561);
				break;

			case 4587: // dscimmy
				player.getPacketSender().sendHideInterfaceLayer(7599, false);
				specialAmount(weapon, player.specAmount, 7611);
				break;

			case 3204: // d hally
				player.getPacketSender().sendHideInterfaceLayer(8493, false);
				specialAmount(weapon, player.specAmount, 8505);
				break;

			case 1377: // d battleaxe
				player.getPacketSender().sendHideInterfaceLayer(7499, false);
				specialAmount(weapon, player.specAmount, 7511);
				break;

			case 4153: // gmaul
				player.getPacketSender().sendHideInterfaceLayer(7474, false);
				specialAmount(weapon, player.specAmount, 7486);
				break;

			case 1249: // dspear
				player.getPacketSender().sendHideInterfaceLayer(7674, false);
				specialAmount(weapon, player.specAmount, 7686);
				break;

			case 1215:// dragon dagger
			case 1231:
			case 5680:
			case 5698:
			case 1305: // dragon long
			case 11694:
			case 11698:
			case 11700:
			case 11730:
			case 11696:
				player.getPacketSender().sendHideInterfaceLayer(7574, false);
				specialAmount(weapon, player.specAmount, 7586);
				break;

			case 1434: // dragon mace
				player.getPacketSender().sendHideInterfaceLayer(7624, false);
				specialAmount(weapon, player.specAmount, 7636);
				break;

			default:
				player.getPacketSender().sendHideInterfaceLayer(7624, true); // mace
				// interface
				player.getPacketSender().sendHideInterfaceLayer(7474, true); // hammer, gmaul
				player.getPacketSender().sendHideInterfaceLayer(7499, true); // axe
				player.getPacketSender().sendHideInterfaceLayer(7549, true); // bow interface
				player.getPacketSender().sendHideInterfaceLayer(7574, true); // sword
				// interface
				player.getPacketSender().sendHideInterfaceLayer(7599, true); // scimmy sword
				// interface,
				// for most
				// swords
				player.getPacketSender().sendHideInterfaceLayer(8493, true);
				player.getPacketSender().sendHideInterfaceLayer(12323, true); // whip
				// interface
				break;
		}
	}

	/**
	 * Specials bar filling amount
	 **/

	public void specialAmount(int weapon, double specAmount, int barId) {
		player.specBarId = barId;
		player.getPacketSender().sendFrame70(specAmount >= 10 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 9 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 8 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 7 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 6 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 5 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 4 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 3 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 2 ? 500 : 0, 0, --barId);
		player.getPacketSender().sendFrame70(specAmount >= 1 ? 500 : 0, 0, --barId);
		updateSpecialBar();
		sendWeapon(weapon, DeprecatedItems.getItemName(weapon));
	}

	/**
	 * Special attack text and what to highlight or blackout
	 **/

	public void updateSpecialBar() {
		if (player.usingSpecial) {
			player.getPacketSender().sendString(
					""
							+ (player.specAmount >= 2 ? "@yel@S P"
							: "@bla@S P")
							+ ""
							+ (player.specAmount >= 3 ? "@yel@ E"
							: "@bla@ E")
							+ ""
							+ (player.specAmount >= 4 ? "@yel@ C I"
							: "@bla@ C I")
							+ ""
							+ (player.specAmount >= 5 ? "@yel@ A L"
							: "@bla@ A L")
							+ ""
							+ (player.specAmount >= 6 ? "@yel@  A"
							: "@bla@  A")
							+ ""
							+ (player.specAmount >= 7 ? "@yel@ T T"
							: "@bla@ T T")
							+ ""
							+ (player.specAmount >= 8 ? "@yel@ A"
							: "@bla@ A")
							+ ""
							+ (player.specAmount >= 9 ? "@yel@ C"
							: "@bla@ C")
							+ ""
							+ (player.specAmount >= 10 ? "@yel@ K"
							: "@bla@ K"), player.specBarId);
		} else {
			player.getPacketSender().sendString(
					"@bla@S P E C I A L  A T T A C K", player.specBarId);
		}
	}

	/**
	 * Wear Item
	 **/

	public boolean wearItem(int wearID, int slot) {
		// Check the player has the item the want to wear
		if (!playerHasItem(wearID, 1, slot)) {
			return false;
		}
		if (slot < 0) {
			return false;
		}
		boolean greegree = Greegree.attemptGreegree(player, wearID);
		if (!greegree) {
			return false;
		}
		if (player.tutorialProgress < 22) {
			player.getPacketSender().sendMessage("You'll be told how to equip items later.");
			return false;
		}

		if (player.tutorialProgress == 22) {
			player.getPacketSender().chatbox(6180);
			player.getDialogueHandler()
					.chatboxText(
							"Clothes, armour, weapons and many other items are equipped",
							"like this. You can unequip items by clicking on the item in the",
							"worn inventory. You can close this window by clicking on the",
							"small x. Speak to the Combat Instructor to continue.",
							"You're now holding your dagger");
			player.getPacketSender().chatbox(6179);
			player.tutorialProgress = 23;
			// c.setSidebarInterface(0, -1);// worn

		} else if (player.tutorialProgress == 23) {
			player.getPacketSender().chatbox(6180);
			player.getDialogueHandler()
					.chatboxText(
							"",
							"Click on the flashing crossed swords icon to see the combat",
							"interface.", "", "Combat interface");
			player.getPacketSender().chatbox(6179);
			player.getPacketSender().flashSideBarIcon(0);
			// c.getPacketDispatcher().tutorialIslandInterface(50, 11);
		}

		int wearAmount = player.playerItemsN[slot];
		if (wearAmount < 1) {
			return false;
		}

		int targetSlot = ItemConstants.HAT;
		boolean canWearItem = true;
		if (player.playerItems[slot] == wearID + 1) {
			getRequirements(DeprecatedItems.getItemName(wearID).toLowerCase(), wearID);
			targetSlot = ItemData.targetSlots[wearID];

			if (player.duelRule[11] && targetSlot == 0) {
				player.getPacketSender().sendMessage("Wearing hats has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[12] && targetSlot == 1) {
				player.getPacketSender().sendMessage("Wearing capes has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[13] && targetSlot == 2) {
				player.getPacketSender().sendMessage("Wearing amulets has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[14] && targetSlot == 3) {
				player.getPacketSender().sendMessage("Wielding weapons has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[15] && targetSlot == 4) {
				player.getPacketSender().sendMessage("Wearing bodies has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[16] && targetSlot == 5 || player.duelRule[16] && is2handed(DeprecatedItems.getItemName(wearID).toLowerCase(), wearID)) {
				player.getPacketSender().sendMessage("Wearing shield has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[17] && targetSlot == 7) {
				player.getPacketSender().sendMessage("Wearing legs has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[18] && targetSlot == 9) {
				player.getPacketSender().sendMessage("Wearing gloves has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[19] && targetSlot == 10) {
				player.getPacketSender().sendMessage("Wearing boots has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[20] && targetSlot == 12) {
				player.getPacketSender().sendMessage("Wearing rings has been disabled in this duel!");
				return false;
			}
			if (player.duelRule[21] && targetSlot == 13) {
				player.getPacketSender().sendMessage("Wearing arrows has been disabled in this duel!");
				return false;
			}

			if (Constants.ITEM_REQUIREMENTS) {
				// Check if slot is armor
				if (targetSlot == ItemConstants.FEET
						|| targetSlot == ItemConstants.LEGS
						|| targetSlot == ItemConstants.SHIELD
						|| targetSlot == ItemConstants.CHEST
						|| targetSlot == ItemConstants.HAT
						|| targetSlot == ItemConstants.HANDS) {
					if (player.defenceLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.DEFENCE]) < player.defenceLevelReq) {
							player.getPacketSender().sendMessage("You need a defence level of " + player.defenceLevelReq + " to wear this item.");
							canWearItem = false;
						}
					}
					if (player.rangeLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.RANGED]) < player.rangeLevelReq) {
							player.getPacketSender().sendMessage("You need a range level of " + player.rangeLevelReq + " to wear this item.");
							canWearItem = false;
						}
					}
					if (player.magicLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.MAGIC]) < player.magicLevelReq) {
							player.getPacketSender().sendMessage("You need a magic level of " + player.magicLevelReq + " to wear this item.");
							canWearItem = false;
						}
					}
				}
				if (player.slayerLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.SLAYER]) < player.slayerLevelReq) {
						player.getPacketSender().sendMessage("You need a slayer level of " + player.slayerLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (player.agilityLevelReq > 0) {
					if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.AGILITY]) < player.agilityLevelReq) {
						player.getPacketSender().sendMessage("You need a agility level of " + player.agilityLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				// Weapon
				if (targetSlot == ItemConstants.WEAPON) {
					if (player.attackLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.ATTACK]) < player.attackLevelReq) {
							player.getPacketSender().sendMessage("You need an attack level of " + player.attackLevelReq + " to wield this weapon.");
							canWearItem = false;
						}
					}
					if (player.rangeLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.RANGED]) < player.rangeLevelReq) {
							player.getPacketSender().sendMessage("You need a range level of " + player.rangeLevelReq + " to wield this weapon.");
							canWearItem = false;
						}
					}
					if (player.magicLevelReq > 0) {
						if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.MAGIC]) < player.magicLevelReq) {
							player.getPacketSender().sendMessage("You need a magic level of " + player.magicLevelReq + " to wield this weapon.");
							canWearItem = false;
						}
					}
				}
			}

			if (wearID == 4079) {
				player.startAnimation(1458);
				return false;
			}
			switch (wearID) {
				// Dragon daggers/sword
				case 1215:
				case 1231:
				case 5680:
				case 5698:
				case 1305:
					if (player.lostCity != 3 && player.playerRights != 3) {
						player.getPacketSender().sendMessage("You must have completed the Lost City quest to equip this weapon.");
						canWearItem = false;
					}
			}

			if (!canWearItem) {
				// return false here so we can send multiple messages of requirements
				return false;
			}

			if (CastleWars.isInCw(player) || CastleWars.isInCwWait(player)) {
				if (targetSlot == ItemConstants.CAPE || targetSlot == ItemConstants.HAT) {
					player.getPacketSender().sendMessage("You can't wear your own capes or hats in a Castle Wars Game!");
					return false;
				}
			}

			if (targetSlot == ItemConstants.WEAPON) {
				player.autocasting = false;
				player.autocastId = 0;
				player.getPacketSender().sendConfig(108, 0);
			}

			if (slot >= 0 && wearID >= 0) {
				int toEquip = player.playerItems[slot];
				int toEquipN = player.playerItemsN[slot];
				int toRemove = player.playerEquipment[targetSlot];
				int toRemoveN = player.playerEquipmentN[targetSlot];
				if (toEquip == toRemove + 1 && ItemDefinition.lookup(toRemove).isStackable()) {
					deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
					player.playerEquipmentN[targetSlot] += toEquipN;
				} else if (targetSlot != ItemConstants.SHIELD && targetSlot != ItemConstants.WEAPON) {
					player.playerItems[slot] = toRemove + 1;
					player.playerItemsN[slot] = toRemoveN;
					player.playerEquipment[targetSlot] = toEquip - 1;
					player.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == ItemConstants.SHIELD) {
					boolean wearing2h = is2handed(DeprecatedItems.getItemName(player.playerEquipment[ItemConstants.WEAPON]).toLowerCase(), player.playerEquipment[ItemConstants.WEAPON]);
					if (wearing2h) {
						// remove the weapon, add to inventory
						toRemove = player.playerEquipment[player.playerWeapon];
						toRemoveN = player.playerEquipmentN[player.playerWeapon];
						player.playerEquipment[player.playerWeapon] = -1;
						player.playerEquipmentN[player.playerWeapon] = 0;
						updateSlot(ItemConstants.WEAPON);
					}
					player.playerItems[slot] = toRemove + 1;
					player.playerItemsN[slot] = toRemoveN;
					player.playerEquipment[targetSlot] = toEquip - 1;
					player.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == ItemConstants.WEAPON) {
					if (CastleWars.SARA_BANNER == toRemove || CastleWars.ZAMMY_BANNER == toRemove) { // alk
						// update
						CastleWars.dropFlag(player, toRemove);
						toRemove = -1;
						toRemoveN = 0;
					}
					boolean is2h = is2handed(DeprecatedItems.getItemName(wearID).toLowerCase(), wearID);
					boolean wearingShield = player.playerEquipment[ItemConstants.SHIELD] > 0;
					boolean wearingWeapon = player.playerEquipment[ItemConstants.WEAPON] > 0;
					if (is2h) {
						if (wearingShield && wearingWeapon) {
							if (freeSlots() > 0) {
								player.playerItems[slot] = toRemove + 1;
								player.playerItemsN[slot] = toRemoveN;
								player.playerEquipment[targetSlot] = toEquip - 1;
								player.playerEquipmentN[targetSlot] = toEquipN;
								removeItem(player.playerEquipment[ItemConstants.SHIELD], ItemConstants.SHIELD);
							} else {
								player.getPacketSender().sendMessage("You do not have enough inventory space to do this.");
								return false;
							}
						} else if (wearingShield && !wearingWeapon) {
							player.playerItems[slot] = player.playerEquipment[ItemConstants.SHIELD] + 1;
							player.playerItemsN[slot] = player.playerEquipmentN[ItemConstants.SHIELD];
							player.playerEquipment[targetSlot] = toEquip - 1;
							player.playerEquipmentN[targetSlot] = toEquipN;
							player.playerEquipment[ItemConstants.SHIELD] = -1;
							player.playerEquipmentN[ItemConstants.SHIELD] = 0;
							updateSlot(ItemConstants.SHIELD);
						} else {
							player.playerItems[slot] = toRemove + 1;
							player.playerItemsN[slot] = toRemoveN;
							player.playerEquipment[targetSlot] = toEquip - 1;
							player.playerEquipmentN[targetSlot] = toEquipN;
						}
					} else {
						player.playerItems[slot] = toRemove + 1;
						player.playerItemsN[slot] = toRemoveN;
						player.playerEquipment[targetSlot] = toEquip - 1;
						player.playerEquipmentN[targetSlot] = toEquipN;
					}
				}
			}
			resetItems(3214);
			if (targetSlot == ItemConstants.WEAPON) {
				player.usingSpecial = false;
				addSpecialBar(wearID);
			}
			if (player.getOutStream() != null && player != null) {
				player.getOutStream().createFrameVarSizeWord(34);
				player.getOutStream().writeWord(1688);
				player.getOutStream().writeByte(targetSlot);
				player.getOutStream().writeWord(wearID + 1);

				if (player.playerEquipmentN[targetSlot] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord(player.playerEquipmentN[targetSlot]);
				} else {
					player.getOutStream().writeByte(player.playerEquipmentN[targetSlot]);
				}

				player.getOutStream().endFrameVarSizeWord();
				player.flushOutStream();
			}
			sendWeapon(player.playerEquipment[player.playerWeapon], DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]));
			resetBonus();
			getBonus();
			writeBonus();
			if (!MonkeyData.isWearingGreegree(player)) {
				player.getCombatAssistant().getPlayerAnimIndex();
			}
			Tiaras.handleTiara(player, wearID);
			player.getPlayerAssistant().requestUpdates();
			return true;
		} else {
			return false;
		}
	}

	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(targetSlot);
			player.getOutStream().writeWord(wearID + 1);

			if (wearAmount > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(wearAmount);
			} else {
				player.getOutStream().writeByte(wearAmount);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			player.playerEquipment[targetSlot] = wearID;
			player.playerEquipmentN[targetSlot] = wearAmount;
			player.getItemAssistant();
			player.getItemAssistant()
					.sendWeapon(
							player.playerEquipment[player.playerWeapon],
							DeprecatedItems
									.getItemName(player.playerEquipment[player.playerWeapon]));
			resetBonus();
			getBonus();
			writeBonus();
			player.getCombatAssistant().getPlayerAnimIndex();
			player.updateRequired = true;
			player.setAppearanceUpdateRequired(true);
		}
	}

	public void updateSlot(int slot) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(slot);
			player.getOutStream().writeWord(player.playerEquipment[slot] + 1);
			if (player.playerEquipmentN[slot] > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(player.playerEquipmentN[slot]);
			} else {
				player.getOutStream().writeByte(player.playerEquipmentN[slot]);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}

	}
	
	public void removeItem(int slot) {
		if (player.getOutStream() != null && player != null) {
			if (player.playerEquipment[slot] > -1) {
				if (addItem(player.playerEquipment[slot], player.playerEquipmentN[slot])) {
					player.playerEquipment[slot] = -1;
					player.playerEquipmentN[slot] = 0;
					sendWeapon(player.playerEquipment[ItemConstants.WEAPON],
							DeprecatedItems.getItemName(player.playerEquipment[ItemConstants.WEAPON]));
					resetBonus();
					getBonus();
					writeBonus();
					player.getCombatAssistant().getPlayerAnimIndex();
					player.getOutStream().createFrame(34);
					player.getOutStream().writeWord(6);
					player.getOutStream().writeWord(1688);
					player.getOutStream().writeByte(slot);
					player.getOutStream().writeWord(0);
					player.getOutStream().writeByte(0);
					player.flushOutStream();
					player.updateRequired = true;
					player.setAppearanceUpdateRequired(true);
				}
			}
		}
	}

	public void removeItem(int wearID, int slot) {
		if (player.getOutStream() != null && player != null) {
			if (player.playerEquipment[slot] > -1) {
				if (player.playerEquipment[slot] == CastleWars.SARA_BANNER|| player.playerEquipment[slot] == CastleWars.ZAMMY_BANNER) {
					CastleWars.dropFlag(player, player.playerEquipment[slot]);
				}
				if ((player.playerEquipment[slot] == CastleWars.SARA_CAPE || player.playerEquipment[slot] == CastleWars.ZAMMY_CAPE) && player.inCw()) {
					player.getPacketSender().sendMessage("You cannot unequip your castle wars cape!");
					return;
				}
				boolean greegree = Greegree.attemptRemove(player, slot);
				if (!greegree) {
					return;
				}
				if (addItem(player.playerEquipment[slot], player.playerEquipmentN[slot])) {
					if (player.playerEquipment[slot] == CastleWars.SARA_BANNER || player.playerEquipment[slot] == CastleWars.ZAMMY_BANNER) {
						CastleWars.dropFlag(player, player.playerEquipment[slot]);
						deleteItem(player.playerEquipment[slot], 1);
					}
					player.playerEquipment[slot] = -1;
					player.playerEquipmentN[slot] = 0;
					sendWeapon(player.playerEquipment[player.playerWeapon],
							DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]));
					resetBonus();
					getBonus();
					writeBonus();
					player.getCombatAssistant().getPlayerAnimIndex();
					player.getOutStream().createFrame(34);
					player.getOutStream().writeWord(6);
					player.getOutStream().writeWord(1688);
					player.getOutStream().writeByte(slot);
					player.getOutStream().writeWord(0);
					player.getOutStream().writeByte(0);
					player.flushOutStream();
					player.updateRequired = true;
					player.setAppearanceUpdateRequired(true);
					//Weight.calcWeight(c, wearID, "deleteitem");
				}
			}
		}
	}

	/**
	 * BANK
	 */

	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (player.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (player.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							player.bankItems[j - spots] = player.bankItems[j];
							player.bankItemsN[j - spots] = player.bankItemsN[j];
							player.bankItemsV[j - spots] = player.bankItemsV[j];
							stop = true;
							player.bankItems[j] = 0;
							player.bankItemsN[j] = 0;
							player.bankItemsV[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter) {
			if (!player.isBot)
				player.disconnected = true;
		}
	}

	public void resetBank() {
		player.getPacketSender().sendString("The Bank of " + Constants.SERVER_NAME, 5383, true);
		if (player.getOutStream() != null) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(5382); // bank
			player.getOutStream().writeWord(ItemConstants.BANK_SIZE);
		}
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.getOutStream() != null) {
				if (player.bankItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(player.bankItemsN[i]);
				} else {
					player.getOutStream().writeByte(player.bankItemsN[i]);
				}
			}
			if (player.bankItemsN[i] < 1) {
				player.bankItems[i] = 0;
			}
			if (player.bankItems[i] > Constants.ITEM_LIMIT || player.bankItems[i] < 0) {
				player.bankItems[i] = Constants.ITEM_LIMIT;
			}
			if (player.getOutStream() != null) {
				player.getOutStream().writeWordBigEndianA(player.bankItems[i]);
			}
		}

		if (player.getOutStream() != null) {
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void resetTempItems() {
		int itemCount = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] > -1) {
				itemCount = i;
			}
		}
		if (player.getOutStream() != null){
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(5064);
			player.getOutStream().writeWord(itemCount + 1);
		}
		for (int i = 0; i < itemCount + 1; i++) {

			if (player.getOutStream() != null) {
				if (player.playerItemsN[i] > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(player.playerItemsN[i]);
				} else {
					player.getOutStream().writeByte(player.playerItemsN[i]);
				}
			}
			if (player.playerItems[i] > Constants.ITEM_LIMIT || player.playerItems[i] < 0) {
				player.playerItems[i] = Constants.ITEM_LIMIT;
			}
			if (player.getOutStream() != null) {
				player.getOutStream().writeWordBigEndianA(player.playerItems[i]);
			}
		}
		if (player.getOutStream() != null) {
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public boolean bankItem(int itemID, int fromSlot, int amount) {
		if (player.inTrade) {
			player.getPacketSender().sendMessage("You can't store items while trading!");
			return false;
		}
		for (int i = 0; i < ItemConstants.ITEM_BANKABLE.length; i++) {
			if (itemID == ItemConstants.ITEM_BANKABLE[i]) {
				player.getPacketSender().sendMessage("You can't bank that item!");
				return false;
			}
		}
		if (!CastleWars.deleteCastleWarsItems(player, itemID)) {
			return false;
		}
		if (player.otherBank == true) {
			player.getPacketSender().closeAllWindows();
			player.getPacketSender().sendMessage("You can't bank while viewing someones bank!");
			player.otherBank = false;
			return false;
		}

		if (!(player.lastMainFrameInterface == MainFrameIDs.DEPOSIT_BOX || player.lastMainFrameInterface == MainFrameIDs.BANK || Boundary.isIn(player, Boundary.BANK_AREA))) { //Packet exploit prevention
			player.getPacketSender().sendMessage("You don't have a bank open! Report this ID to developers: " + player.lastMainFrameInterface);
			return false;
		}

		if (player.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!ItemDefinition.lookup(player.playerItems[fromSlot] - 1).isNote()) {
			if (player.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (ItemDefinition.lookup(player.playerItems[fromSlot] - 1).isStackable() || player.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot]) {
						if (player.playerItemsN[fromSlot] < amount) {
							amount = player.playerItemsN[fromSlot];
						}
						alreadyInBank = true;
						toBankSlot = i;
						i = ItemConstants.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = ItemConstants.BANK_SIZE + 1;
						}
					}
					player.bankItems[toBankSlot] = player.playerItems[fromSlot];
					if (player.playerItemsN[fromSlot] < amount) {
						amount = player.playerItemsN[fromSlot];
					}
					if (player.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT
							&& player.bankItemsN[toBankSlot] + amount > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						player.getPacketSender().sendMessage("Bank full!");
						return false;
					}
					deleteItem(player.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if (player.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT
							&& player.bankItemsN[toBankSlot] + amount > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						player.getPacketSender().sendMessage("Bank full!");
						return false;
					}
					deleteItem(player.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					player.getPacketSender().sendMessage("Your bank is full!");
					return false;
				}
			} else {
				itemID = player.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = ItemConstants.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = ItemConstants.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if (player.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItems[toBankSlot] = player.playerItems[firstPossibleSlot];
							player.bankItemsN[toBankSlot] += 1;
							deleteItem(player.playerItems[firstPossibleSlot] - 1,
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if (player.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItemsN[toBankSlot] += 1;
							deleteItem(player.playerItems[firstPossibleSlot] - 1,
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					player.getPacketSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else if (ItemDefinition.lookup(player.playerItems[fromSlot] - 1).isNote() && !ItemDefinition.lookup(player.playerItems[fromSlot] - 2).isNote()) {
			if (player.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (ItemDefinition.lookup(player.playerItems[fromSlot] - 1).isStackable() || player.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot] - 1) {
						if (player.playerItemsN[fromSlot] < amount) {
							amount = player.playerItemsN[fromSlot];
						}
						alreadyInBank = true;
						toBankSlot = i;
						i = ItemConstants.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = ItemConstants.BANK_SIZE + 1;
						}
					}
					player.bankItems[toBankSlot] = player.playerItems[fromSlot] - 1;
					if (player.playerItemsN[fromSlot] < amount) {
						amount = player.playerItemsN[fromSlot];
					}
					if (player.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT && player.bankItemsN[toBankSlot] + amount > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem(player.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if (player.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT && player.bankItemsN[toBankSlot] + amount > -1) {
						player.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem(player.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					player.getPacketSender().sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = player.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
					if (player.bankItems[i] == player.playerItems[fromSlot] - 1) {
						alreadyInBank = true;
						toBankSlot = i;
						i = ItemConstants.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
						if (player.bankItems[i] <= 0) {
							toBankSlot = i;
							i = ItemConstants.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if (player.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItems[toBankSlot] = player.playerItems[firstPossibleSlot] - 1;
							player.bankItemsN[toBankSlot] += 1;
							deleteItem(player.playerItems[firstPossibleSlot] - 1,
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < player.playerItems.length; i++) {
							if (player.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							player.bankItemsN[toBankSlot] += 1;
							deleteItem(player.playerItems[firstPossibleSlot] - 1,
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					player.getPacketSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			player.getPacketSender().sendMessage("Item not supported " + (player.playerItems[fromSlot] - 1));
			return false;
		}
	}

	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			if (player.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int getBankQuantity(int itemID)
	{
		for (int i = 0; i < player.bankItems.length; i++) {
			if (player.bankItems[i] == itemID)
			{
				return player.bankItemsN[i];
			}
		}
		return 0;
	}

	public void fromBank(int itemID, int fromSlot, int amount) {
		boolean cantWithdrawCuzMaxStack = false;
		if (!(player.lastMainFrameInterface == MainFrameIDs.BANK || Boundary.isIn(player, Boundary.BANK_AREA))) {
			player.getPacketSender().sendMessage("Your bank isn't open!");
			return;
		}
		if (amount > 0) {
			if (player.bankItems[fromSlot] > 0) {
				if (player.getItemAssistant().playerHasItem(itemID)) {
					for (int i = 0; i <= 27; i++) {
						if (itemID == player.playerItems[i] || (itemID == 995 && player.playerItems[i] - 1 == 995)) {
							if ((player.playerItemsN[i] + amount + 1) < -1) {
								cantWithdrawCuzMaxStack = true;
								break;
							}
						}
					}
				}
				if (!cantWithdrawCuzMaxStack) {
					if (!player.takeAsNote) {
						if (ItemDefinition.lookup(player.bankItems[fromSlot] - 1).isStackable()) {
							if (player.bankItemsN[fromSlot] > amount) {
								if (addItem(player.bankItems[fromSlot] - 1, amount)) {
									player.bankItemsN[fromSlot] -= amount;
									resetBank();
									resetItems(5064);
								}
							} else {
								if (addItem(player.bankItems[fromSlot] - 1, player.bankItemsN[fromSlot])) {
									player.bankItems[fromSlot] = 0;
									player.bankItemsN[fromSlot] = 0;
									resetBank();
									resetItems(5064);
								}
							}
						} else {
							while (amount > 0) {
								if (player.bankItemsN[fromSlot] > 0) {
									if (addItem(player.bankItems[fromSlot] - 1, 1)) {
										player.bankItemsN[fromSlot] += -1;
										amount--;
									} else {
										amount = 0;
									}
								} else {
									amount = 0;
								}
							}
							resetBank();
							resetItems(5064);
						}
					} else if (player.takeAsNote && ItemDefinition.lookup(player.bankItems[fromSlot]).isNote()) {
						if (player.bankItemsN[fromSlot] > amount) {
							if (addItem(player.bankItems[fromSlot], amount)) {
								player.bankItemsN[fromSlot] -= amount;
								resetBank();
								resetItems(5064);
							}
						} else {
							if (addItem(player.bankItems[fromSlot], player.bankItemsN[fromSlot])) {
								player.bankItems[fromSlot] = 0;
								player.bankItemsN[fromSlot] = 0;
								resetBank();
								resetItems(5064);
							}
						}
					} else {
						player.getPacketSender().sendMessage("This item can't be withdrawn as a note.");
						if (ItemDefinition.lookup(player.bankItems[fromSlot] - 1).isStackable()) {
							if (player.bankItemsN[fromSlot] > amount) {
								if (addItem(player.bankItems[fromSlot] - 1, amount)) {
									player.bankItemsN[fromSlot] -= amount;
									resetBank();
									resetItems(5064);
								}
							} else {
								if (addItem(player.bankItems[fromSlot] - 1, player.bankItemsN[fromSlot])) {
									player.bankItems[fromSlot] = 0;
									player.bankItemsN[fromSlot] = 0;
									resetBank();
									resetItems(5064);
								}
							}
						} else {
							while (amount > 0) {
								if (player.bankItemsN[fromSlot] > 0) {
									if (addItem(player.bankItems[fromSlot] - 1, 1)) {
										player.bankItemsN[fromSlot] += -1;
										amount--;
									} else {
										amount = 0;
									}
								} else {
									amount = 0;
								}
							}
							resetBank();
							resetItems(5064);
						}
					}
				} else {
					player.getPacketSender().sendMessage("Can't withdraw more of that item!");
				}
			}
		}
	}

	/**
	 * Update Equip tab
	 **/

	public void setEquipment(int wearID, int amount, int targetSlot) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(targetSlot);
			player.getOutStream().writeWord(wearID + 1);
			if (amount > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(amount);
			} else {
				player.getOutStream().writeByte(amount);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
		player.playerEquipment[targetSlot] = wearID;
		player.playerEquipmentN[targetSlot] = amount;
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	/**
	 * Move Items
	 **/

	public void moveItems(int from, int to, int moveWindow, boolean insertMode) {
		if (moveWindow == 3214) {
			int tempI;
			int tempN;
			tempI = player.playerItems[from];
			tempN = player.playerItemsN[from];
			player.playerItems[from] = player.playerItems[to];
			player.playerItemsN[from] = player.playerItemsN[to];
			player.playerItems[to] = tempI;
			player.playerItemsN[to] = tempN;
		}

		if (moveWindow == 5382 && from >= 0 && to >= 0
				&& from < ItemConstants.BANK_SIZE && to < ItemConstants.BANK_SIZE
				&& to < ItemConstants.BANK_SIZE) {
			if (insertMode) {
				int tempFrom = from;
				for (int tempTo = to; tempFrom != tempTo;)
					if (tempFrom > tempTo) {
						swapBankItem(tempFrom, tempFrom - 1);
						tempFrom--;
					} else if (tempFrom < tempTo) {
						swapBankItem(tempFrom, tempFrom + 1);
						tempFrom++;
					}
			} else {
				swapBankItem(from, to);
			}
		}

		if (moveWindow == 5382) {
			resetBank();
		}
		if (moveWindow == 5064) {
			int tempI;
			int tempN;
			tempI = player.playerItems[from];
			tempN = player.playerItemsN[from];

			player.playerItems[from] = player.playerItems[to];
			player.playerItemsN[from] = player.playerItemsN[to];
			player.playerItems[to] = tempI;
			player.playerItemsN[to] = tempN;
			resetItems(3214);
		}
		resetTempItems();
		if (moveWindow == 3214) {
			resetItems(3214);
		}

	}

	public void swapBankItem(int from, int to) {
		int tempI = player.bankItems[from];
		int tempN = player.bankItemsN[from];
		player.bankItems[from] = player.bankItems[to];
		player.bankItemsN[from] = player.bankItemsN[to];
		player.bankItems[to] = tempI;
		player.bankItemsN[to] = tempN;
	}

	/**
	 * delete Item
	 **/

	public void deleteEquipment(int i, int j) {
		if (PlayerHandler.players[player.playerId] == null) {
			return;
		}
		if (i < 0) {
			return;
		}

		player.playerEquipment[j] = -1;
		player.playerEquipmentN[j] = player.playerEquipmentN[j] - 1;

		if (player.getOutStream() != null) {
			player.getOutStream().createFrame(34);
			player.getOutStream().writeWord(6);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(j);
			player.getOutStream().writeWord(0);
			player.getOutStream().writeByte(0);
		}
		getBonus();
		if (j == player.playerWeapon) {
			sendWeapon(-1, "Unarmed");
		}
		resetBonus();
		getBonus();
		writeBonus();
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	public void deleteItem(int id, int amount) {
		if (id <= 0 || amount <= 0) {
			return;
		}
		id++;
		for (int slot = 0; slot < player.playerItems.length; slot++) {
			if (amount <= 0) {
				break;
			}
			if (player.playerItems[slot] == id) {
				if (player.playerItemsN[slot] > amount) {
					player.playerItemsN[slot] -= amount;
					break;
				} else {
					amount -= player.playerItemsN[slot];
					player.playerItems[slot] = 0;
					player.playerItemsN[slot] = 0;
				}
			}
		}
		resetItems(3214);
		Weight.updateWeight(player);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (player.playerItems[slot] == id + 1) {
			if (player.playerItemsN[slot] > amount) {
				player.playerItemsN[slot] -= amount;
			} else {
				player.playerItemsN[slot] = 0;
				player.playerItems[slot] = 0;
			}
			resetItems(3214);
			Weight.updateWeight(player);
		}
	}

	/**
	 * Delete Arrows
	 **/
	public void deleteArrow() {
		if (player.playerEquipment[player.playerCape] == 10499 && Misc.random(5) != 1
				&& player.playerEquipment[player.playerArrows] != 4740) {
			return;
		}
		if (player.playerEquipmentN[player.playerArrows] == 1) {
			deleteEquipment(
					player.playerEquipment[player.playerArrows], player.playerArrows);
		}
		if (player.playerEquipmentN[player.playerArrows] != 0) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(player.playerArrows);
			player.getOutStream().writeWord(player.playerEquipment[player.playerArrows] + 1);
			if (player.playerEquipmentN[player.playerArrows] - 1 > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(
						player.playerEquipmentN[player.playerArrows] - 1);
			} else {
				player.getOutStream().writeByte(
						player.playerEquipmentN[player.playerArrows] - 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			player.playerEquipmentN[player.playerArrows] -= 1;
		}
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	public void deleteEquipment() {
		if (player.playerEquipmentN[player.playerWeapon] == 1) {
			deleteEquipment(
					player.playerEquipment[player.playerWeapon], player.playerWeapon);
		}
		if (player.playerEquipmentN[player.playerWeapon] != 0) {
			player.getOutStream().createFrameVarSizeWord(34);
			player.getOutStream().writeWord(1688);
			player.getOutStream().writeByte(player.playerWeapon);
			player.getOutStream().writeWord(player.playerEquipment[player.playerWeapon] + 1);
			if (player.playerEquipmentN[player.playerWeapon] - 1 > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord(
						player.playerEquipmentN[player.playerWeapon] - 1);
			} else {
				player.getOutStream().writeByte(
						player.playerEquipmentN[player.playerWeapon] - 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			player.playerEquipmentN[player.playerWeapon] -= 1;
		}
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
	}

	/**
	 * Dropping Arrows
	 **/

	public void dropArrowNpc() {
		if (player.playerEquipment[player.playerCape] == 10499) {
			return;
		}
		int enemyX = NpcHandler.npcs[player.oldNpcIndex].getX();
		int enemyY = NpcHandler.npcs[player.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed, enemyX, enemyY) == 0) {
				GameEngine.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, 1, player.getId());
			} else if (GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed,
						enemyX, enemyY);
				GameEngine.itemHandler.removeGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, false);
				GameEngine.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, amount + 1, player.getId());
			}
		}
	}

	public void dropArrowPlayer() {
		int enemyX = PlayerHandler.players[player.oldPlayerIndex].getX();
		int enemyY = PlayerHandler.players[player.oldPlayerIndex].getY();
		if (player.playerEquipment[player.playerCape] == 10499) {
			return;
		}
		if (Misc.random(10) >= 4) {
			if (GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed, enemyX, enemyY) == 0) {
				GameEngine.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, 1, player.getId());
			} else if (GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = GameEngine.itemHandler.itemAmount(player.playerName, player.rangeItemUsed,
						enemyX, enemyY);
				GameEngine.itemHandler.removeGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, false);
				GameEngine.itemHandler.createGroundItem(player, player.rangeItemUsed, enemyX,
						enemyY, amount + 1, player.getId());
			}
		}
	}

	public void removeAllItems() {
		for (int i = 0; i < player.playerItems.length; i++) {
			player.playerItems[i] = 0;
		}
		for (int i = 0; i < player.playerItemsN.length; i++) {
			player.playerItemsN[i] = 0;
		}
		resetItems(3214);
		Weight.updateWeight(player);
	}

	public int freeSlots() {
		int freeS = 0;
		for (int playerItem : player.playerItems) {
			if (playerItem <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int freeSlots(int itemID, int amount) {
		int freeS = 0;
		for (int i = 0; i < player.playerItems.length; i ++) {
			int _id = player.playerItems[i];
			int _amt = player.playerItemsN[i];
			if (_id <= 0 || (_id == itemID && ItemDefinition.lookup(_id).isStackable() && _amt + amount <= Integer.MAX_VALUE)) {
				freeS++;
			}
		}
		return freeS;
	}

	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < player.playerItems.length; i++) {
			if (items[i] - 1 == id && amounts[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	public int getItemSlot(int ItemID) {
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] - 1 == ItemID) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (player.playerItems[slot] == itemID) {
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] == itemID) {
					if (player.playerItemsN[i] >= amt) {
						return true;
					} else {
						found++;
					}
				}
			}
			if (found >= amt) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean playerHasItem(int itemID) {
		itemID++;
		for (int playerItem : player.playerItems) {
			if (playerItem == itemID) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < player.playerItems.length; i++) {
			if (player.playerItems[i] == itemID) {
				if (player.playerItemsN[i] >= amt) {
					return true;
				} else {
					found++;
				}
			}
		}
		if (found >= amt) {
			return true;
		}
		return false;
	}

}
