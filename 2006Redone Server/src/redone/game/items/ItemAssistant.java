package redone.game.items;

import redone.Constants;
import redone.Server;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.items.impl.Weight;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

public class ItemAssistant {

	private final Client c;

	public ItemAssistant(Client client) {
		c = client;
	}

	private static int COMMON = Misc.random(5), UNCOMMON = Misc.random(25),
			RARE = Misc.random(100);

	private static final int[][] CASKET = { { 995, Misc.random(3000), COMMON },
			{ 1621, 1, UNCOMMON }, { 1619, 1, UNCOMMON }, { 1617, 1, RARE },
			{ 987, 1, RARE }, { 985, 1, RARE }, { 1454, 1, COMMON },
			{ 1452, 1, UNCOMMON }, { 1462, 1, RARE }, { 1623, 1, COMMON } };

	public void addCasketRewards(int itemId) {
		long clickTimer = 0;
		c.getActionSender().sendMessage("You search the casket...");
		for (int[] element : CASKET) {
			int item = element[0];
			int amount = element[1];
			int chance = element[2];
			if (Misc.random(chance) == 0 && System.currentTimeMillis() - clickTimer > 1800) {
				addItem(item, amount);
				deleteItem2(itemId, 1);
				clickTimer = System.currentTimeMillis();
				c.getActionSender().sendMessage(
						"You find " + amount + " " + getItemName(item) + ".");
			} else {
				if (System.currentTimeMillis() - clickTimer > 1800) {
					addItem(995, 100);
					deleteItem2(itemId, 1);
					clickTimer = System.currentTimeMillis();
					c.getActionSender().sendMessage("You find 100 coins.");
				}
			}
		}
	}

	private final int[] TREE_SEEDS = { 5291, 5292, 5293, 5294, 5295, 5296, 5297,
			5298, 5299, 5300, 5301, 5302, 5303, 5304, 5315, 5316, 5313, 5314 };

	public void handleTreeSeeds(int itemId) {
		c.getActionSender().sendMessage("You search the nest...");
		final int reward = TREE_SEEDS[Misc.random(TREE_SEEDS.length)];
		addItem(reward, 1 + Misc.random(1));
		deleteItem(itemId, 1);
		addItem(5075, 1);
		c.getActionSender().sendMessage("You find a " + getItemName(reward) + ".");
	}
	
	private final int[] SEEDS = { 5291, 5292, 5293, 5294, 5295, 5296, 5297, 298, 5299, 5300, 5301, 5302, 5303, 5304 };
	
	public void handleNonTreeSeeds(int itemId) {
		c.getActionSender().sendMessage("You search the nest...");
		final int reward = SEEDS[Misc.random(SEEDS.length)];
		addItem(reward, 1 + Misc.random(1));
		deleteItem(itemId, 1);
		addItem(5075, 1);
		c.getActionSender().sendMessage("You find a " + getItemName(reward) + ".");
	}

	public int[] RINGS = { 1635, 1637, 1639, 1641, 1643 };

	public void handleRings(int itemId) {
		c.getActionSender().sendMessage("You search the nest...");
		int reward = RINGS[Misc.random(RINGS.length)];
		addItem(reward, 1);
		deleteItem(itemId, 1);
		addItem(5075, 1);
		c.getActionSender().sendMessage("You find a " + getItemName(reward) + ".");
	}
	
    public void updateInventory() {
        this.resetItems(3214);
    }
    
    public void destroyInterface(int itemId) {
		itemId = c.droppedItem;
		String itemName = getItemName(c.droppedItem);
		String[][] info = {
				{ "Are you sure you want to destroy this item?", "14174" },
				{ "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" },
				{ "You probably won't be able to", "14182" }, { "get this item back once lost.", "14183" },
				{ itemName, "14184" } };// make some kind of c.getItemInfo
		c.getPlayerAssistant().sendFrame34(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++)
		c.getPlayerAssistant().sendFrame126(info[i][0], Integer.parseInt(info[i][1]));
		c.getPlayerAssistant().sendChatInterface(14170);
	}

	public void destroyItem(int itemId) {
		itemId = c.droppedItem;
		String itemName = getItemName(itemId);
		deleteItem(itemId,getItemSlot(itemId), c.playerItemsN[getItemSlot(itemId)]);
		c.getActionSender().sendMessage("Your " + itemName + " vanishes as you destroy it.");
		c.getPlayerAssistant().removeAllWindows();
	}

	public void dropItem(int itemId) {
		itemId = c.droppedItem;
		Server.itemHandler.createGroundItem(c, itemId, c.absX, c.absY, c.playerItemsN[getItemSlot(itemId)], c.getId());
		deleteItem(itemId,getItemSlot(itemId), c.playerItemsN[getItemSlot(itemId)]);
		c.getPlayerAssistant().removeAllWindows();
	}

	public void addOrDropItem(int item, int amount) {
		if (isStackable(item) && hasFreeSlots(1)) {
			addItem(item, amount);
		} else if (!hasFreeSlots(amount) && !isStackable(item)) {
			Server.itemHandler.createGroundItem(c, item, c.absX, c.absY,
					amount, c.playerId);
			c.getActionSender()
					.sendMessage(
							"You have no inventory space, so the item(s) appear beneath you.");
		} else {
			addItem(item, amount);
		}
	}

	public boolean hasFreeSlots(int slots) {
		return freeSlots() >= slots;
	}
	
	   public void replaceItem(int itemToReplace, int replaceWith) {
	         if(playerHasItem(itemToReplace)) {
	             deleteItem(itemToReplace, 1);
	             addItem(replaceWith, 1);
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
		for (int element : c.playerEquipment) {
			if (element == itemID) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasEquipped(int slot, int itemID) {
		return c.playerEquipment[slot] == itemID;
	}

	public void removeItem(int slot) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (c.playerEquipment[slot] > -1) {
				if (addItem(c.playerEquipment[slot], c.playerEquipmentN[slot])) {
					c.playerEquipment[slot] = -1;
					c.playerEquipmentN[slot] = 0;
					sendWeapon(c.playerEquipment[c.playerWeapon],
							getItemName(c.playerEquipment[c.playerWeapon]));
					resetBonus();
					getBonus();
					writeBonus();
					c.getCombatAssistant().getPlayerAnimIndex();
					c.getOutStream().createFrame(34);
					c.getOutStream().writeWord(6);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(slot);
					c.getOutStream().writeWord(0);
					c.getOutStream().writeByte(0);
					c.flushOutStream();
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				}
			}
		}
	}

	public void addItemToBank(int itemId, int amount) {
		for (int i = 0; i < Constants.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0 || c.bankItems[i] == itemId + 1 && c.bankItemsN[i] + amount < Integer.MAX_VALUE) {
				c.bankItems[i] = itemId + 1;
				c.bankItemsN[i] += amount;
				resetBank();
				return;
			}
		}
	}

	public void resetItems(int WriteFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(WriteFrame);
			c.getOutStream().writeWord(c.playerItems.length);
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.playerItemsN[i]);
				}
				c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public int getItemCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (c.playerItems[j] == itemID + 1) {
				count += c.playerItemsN[j];
			}
		}
		return count;
	}

	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < c.playerBonus.length; i++) {
			if (c.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + c.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -" + java.lang.Math.abs(c.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			c.getPlayerAssistant().sendFrame126(send, 1675 + i + offset);
		}

	}

	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (Item.itemIsNote[itemID + 1]) {
				if (itemID + 2 == c.playerItems[j]) {
					count += c.playerItemsN[j];
				}
			}
			if (!Item.itemIsNote[itemID + 1]) {
				if (itemID + 1 == c.playerItems[j]) {
					count += c.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] == itemID + 1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public int getBankItemCount() {
		int count = 0;
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] > -1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public void sendItemsKept() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(6963);
			c.getOutStream().writeWord(c.itemKeptId.length);
			for (int i = 0; i < c.itemKeptId.length; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(1);
				} else {
					c.getOutStream().writeByte(1);
				}
				if (c.itemKeptId[i] > 0) {
					c.getOutStream().writeWordBigEndianA(c.itemKeptId[i] + 1);
				} else {
					c.getOutStream().writeWordBigEndianA(0);
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Item kept on death
	 **/

	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 > 0) {
				int inventoryItemValue = c.getShopAssistant().getItemShopValue(
						c.playerItems[i] - 1);
				if (inventoryItemValue > value && !c.invSlot[i]) {
					value = inventoryItemValue;
					item = c.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			if (c.playerEquipment[i1] > 0) {
				int equipmentItemValue = c.getShopAssistant().getItemShopValue(
						c.playerEquipment[i1]);
				if (equipmentItemValue > value && !c.equipSlot[i1]) {
					value = equipmentItemValue;
					item = c.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			c.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(c.playerItems[slotId] - 1,
						getItemSlot(c.playerItems[slotId] - 1), 1);
			}
		} else {
			c.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		c.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death
	 **/

	public void resetKeepItems() {
		for (int i = 0; i < c.itemKeptId.length; i++) {
			c.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < c.invSlot.length; i1++) {
			c.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < c.equipSlot.length; i2++) {
			c.equipSlot[i2] = false;
		}
	}

	/**
	 * delete all items
	 **/

	public void deleteAllItems() {
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			deleteEquipment(c.playerEquipment[i1], i1);
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			deleteItem(c.playerItems[i] - 1, getItemSlot(c.playerItems[i] - 1),
					c.playerItemsN[i]);
		}
	}
	
	/**
	 * Clear Bank
	 */
	
	public void clearBank() {
	try {
		for (int i = 0; i < c.bankItems[i]; i++) {
            c.bankItems[i] = 0;
            c.bankItemsN[i] = 0;
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
		Client o = (Client) PlayerHandler.players[c.killerId];

		for (int i = 0; i < c.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(c.playerItems[i] - 1)) {
					Server.itemHandler.createGroundItem(o,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.killerId);
				} else {
					if (specialCase(c.playerItems[i] - 1)) {
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(),
								getUntradePrice(c.playerItems[i] - 1),
								c.killerId);
					}
					Server.itemHandler.createGroundItem(c,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerItems[i] - 1,
						c.getX(), c.getY(), c.playerItemsN[i], c.playerId);
			}
		}
		for (int e = 0; e < c.playerEquipment.length; e++) {
			if (o != null) {
				if (tradeable(c.playerEquipment[e])) {
					Server.itemHandler.createGroundItem(o,
							c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.killerId);
				} else {
					if (specialCase(c.playerEquipment[e])) {
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(),
								getUntradePrice(c.playerEquipment[e]),
								c.killerId);
					}
					Server.itemHandler.createGroundItem(c,
							c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerEquipment[e],
						c.getX(), c.getY(), c.playerEquipmentN[e], c.playerId);
			}
		}
		if (o != null) {
			Server.itemHandler.createGroundItem(o, 526, c.getX(), c.getY(), 1,
					c.killerId);
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

	public void addToVoidList(int itemId) {
		switch (itemId) {
		case 2518:
			c.voidStatus[0]++;
			break;
		case 2520:
			c.voidStatus[1]++;
			break;
		case 2522:
			c.voidStatus[2]++;
			break;
		case 2524:
			c.voidStatus[3]++;
			break;
		case 2526:
			c.voidStatus[4]++;
			break;
		}
	}

	public boolean tradeable(int itemId) {
		for (int element : Constants.ITEM_TRADEABLE) {
			if (itemId == element) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add Item
	 **/
	public boolean addItem(int item, int amount) {
		// synchronized(c) {
		if (item == CastleWars.SARA_BANNER || item == CastleWars.ZAMMY_BANNER) {
			return false;
		}
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((freeSlots() >= 1 || playerHasItem(item, 1))
				&& Item.itemStackable[item] || freeSlots() > 0
				&& !Item.itemStackable[item]) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] == item + 1 && Item.itemStackable[item]
						&& c.playerItems[i] > 0) {
					c.playerItems[i] = item + 1;
					if (c.playerItemsN[i] + amount < Constants.MAXITEM_AMOUNT
							&& c.playerItemsN[i] + amount > -1) {
						c.playerItemsN[i] += amount;
					} else {
						c.playerItemsN[i] = Constants.MAXITEM_AMOUNT;
					}
					if (c.getOutStream() != null && c != null) {
						c.getOutStream().createFrameVarSizeWord(34);
						c.getOutStream().writeWord(3214);
						c.getOutStream().writeByte(i);
						c.getOutStream().writeWord(c.playerItems[i]);
						if (c.playerItemsN[i] > 254) {
							c.getOutStream().writeByte(255);
							c.getOutStream().writeDWord(c.playerItemsN[i]);
						} else {
							c.getOutStream().writeByte(c.playerItemsN[i]);
						}
						c.getOutStream().endFrameVarSizeWord();
						c.flushOutStream();
					}
					i = 30;
					Weight.calcWeight(c, item, "additem");
					return true;
				}
			}
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] <= 0) {
					c.playerItems[i] = item + 1;
					if (amount < Constants.MAXITEM_AMOUNT && amount > -1) {
						c.playerItemsN[i] = 1;
						if (amount > 1) {
							addItem(item, amount - 1);
							return true;
						}
					} else {
						c.playerItemsN[i] = Constants.MAXITEM_AMOUNT;
					}
					resetItems(3214);
					i = 30;
					Weight.calcWeight(c, item, "additem");
					return true;
				}
			}
			return false;
		} else {
			resetItems(3214);
			c.getActionSender().sendMessage(
					"Not enough space in your inventory.");
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
		for (int i = 0; i < c.playerBonus.length; i++) {
			c.playerBonus[i] = 0;
		}
	}

	public void getBonus() {
		for (int element : c.playerEquipment) {
			if (element > -1) {
				for (int j = 0; j < Constants.ITEM_LIMIT; j++) {
					if (Server.itemHandler.ItemList[j] != null) {
						if (Server.itemHandler.ItemList[j].itemId == element) {
							for (int k = 0; k < c.playerBonus.length; k++) {
								c.playerBonus[k] += Server.itemHandler.ItemList[j].Bonuses[k];
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Wear Item
	 **/

	public void sendWeapon(int Weapon, String WeaponName) {
		String WeaponName2 = WeaponName.replaceAll("Bronze", "");
		WeaponName2 = WeaponName2.replaceAll("Iron", "");
		WeaponName2 = WeaponName2.replaceAll("Steel", "");
		WeaponName2 = WeaponName2.replaceAll("Black", "");
		WeaponName2 = WeaponName2.replaceAll("Mithril", "");
		WeaponName2 = WeaponName2.replaceAll("Adamant", "");
		WeaponName2 = WeaponName2.replaceAll("Rune", "");
		WeaponName2 = WeaponName2.replaceAll("Granite", "");
		WeaponName2 = WeaponName2.replaceAll("Dragon", "");
		WeaponName2 = WeaponName2.replaceAll("Drag", "");
		WeaponName2 = WeaponName2.replaceAll("Crystal", "");
		WeaponName2 = WeaponName2.trim();
		if (WeaponName.equals("Unarmed")) {
			c.getActionSender().setSidebarInterface(0, 5855); // punch,
																	// kick,
																	// block
			c.getPlayerAssistant().sendFrame126(WeaponName, 5857);
		} else if (WeaponName.endsWith("whip")) {
			c.getActionSender().setSidebarInterface(0, 12290); // flick,
																	// lash,
																	// deflect
			c.getPlayerAssistant().sendFrame246(12291, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 12293);
		} else if (WeaponName.endsWith("bow") || WeaponName.endsWith("10")
				|| WeaponName.endsWith("full")
				|| WeaponName.startsWith("seercull")) {
			c.getActionSender().setSidebarInterface(0, 1764); // accurate,
																	// rapid,
																	// longrange
			c.getPlayerAssistant().sendFrame246(1765, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 1767);
		} else if (WeaponName.startsWith("Staff")
				|| WeaponName.endsWith("staff") || WeaponName.endsWith("wand")) {
			c.getActionSender().setSidebarInterface(0, 328); // spike,
																	// impale,
																	// smash,
																	// block
			c.getPlayerAssistant().sendFrame246(329, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 331);
		} else if (WeaponName2.startsWith("dart")
				|| WeaponName2.startsWith("knife")
				|| WeaponName2.startsWith("javelin")
				|| WeaponName.equalsIgnoreCase("toktz-xil-ul")) {
			c.getActionSender().setSidebarInterface(0, 4446); // accurate,
																	// rapid,
																	// longrange
			c.getPlayerAssistant().sendFrame246(4447, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 4449);
		} else if (WeaponName2.startsWith("dagger")
				|| WeaponName2.contains("sword")) {
			c.getActionSender().setSidebarInterface(0, 2276); // stab,
																	// lunge,
																	// slash,
																	// block
			c.getPlayerAssistant().sendFrame246(2277, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 2279);
		} else if (WeaponName2.startsWith("pickaxe")) {
			c.getActionSender().setSidebarInterface(0, 5570); // spike,
																	// impale,
																	// smash,
																	// block
			c.getPlayerAssistant().sendFrame246(5571, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 5573);
		} else if (WeaponName2.startsWith("axe")
				|| WeaponName2.startsWith("battleaxe")) {
			c.getActionSender().setSidebarInterface(0, 1698); // chop,
																	// hack,
																	// smash,
																	// block
			c.getPlayerAssistant().sendFrame246(1699, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 1701);
		} else if (WeaponName2.startsWith("halberd")) {
			c.getActionSender().setSidebarInterface(0, 8460); // jab,
																	// swipe,
																	// fend
			c.getPlayerAssistant().sendFrame246(8461, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("Scythe")) {
			c.getActionSender().setSidebarInterface(0, 8460); // jab,
																	// swipe,
																	// fend
			c.getPlayerAssistant().sendFrame246(8461, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("spear")) {
			c.getActionSender().setSidebarInterface(0, 4679); // lunge,
																	// swipe,
																	// pound,
																	// block
			c.getPlayerAssistant().sendFrame246(4680, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 4682);
		} else if (WeaponName2.toLowerCase().contains("mace")) {
			c.getActionSender().setSidebarInterface(0, 3796);
			c.getPlayerAssistant().sendFrame246(3797, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 3799);

		} else if (c.playerEquipment[c.playerWeapon] == 4153) {
			c.getActionSender().setSidebarInterface(0, 425); // war hamer
																	// equip.
			c.getPlayerAssistant().sendFrame246(426, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 428);
		} else {
			c.getActionSender().setSidebarInterface(0, 2423); // chop,
																	// slash,
																	// lunge,
																	// block
			c.getPlayerAssistant().sendFrame246(2424, 200, Weapon);
			c.getPlayerAssistant().sendFrame126(WeaponName, 2426);
		}

	}

	/**
	 * Weapon Requirements
	 **/

	public void getRequirements(String itemName, int itemId) {
		c.attackLevelReq = c.defenceLevelReq = c.strengthLevelReq = c.rangeLevelReq = c.magicLevelReq = c.agilityLevelReq = c.slayerLevelReq = 0;
		if (itemName.contains("mystic") || itemName.contains("nchanted")) {
			if (itemName.contains("staff")) {
				c.magicLevelReq = 20;
				c.attackLevelReq = 40;
			} else {
				c.magicLevelReq = 20;
				c.defenceLevelReq = 20;
			}
		}
		if (itemName.contains("infinity")) {
			c.magicLevelReq = 50;
			c.defenceLevelReq = 25;
		}
		if (itemName.contains("splitbark")) {
			c.magicLevelReq = 40;
			c.defenceLevelReq = 40;
		}
		if (itemName.contains("green d'hide")) {
			c.rangeLevelReq = 40;
			if (itemName.contains("body")) {
				c.defenceLevelReq = 40;
			}
		}
		if (itemName.contains("blue d'hide")) {
			c.rangeLevelReq = 50;
			if (itemName.contains("body")) {
				c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("red d'hide")) {
			c.rangeLevelReq = 60;
			if (itemName.contains("body")) {
				c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("black d'hide")) {
			if (itemName.contains("body")) {
				c.defenceLevelReq = 40;
			}
			c.rangeLevelReq = 70;
		}
		if (itemName.contains("bronze")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("iron")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("steel")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 5;
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
				c.attackLevelReq = c.defenceLevelReq = 10;
			}
			return;
		}
		if (itemName.contains("mithril")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 20;
			}
			return;
		}
		if (itemName.contains("adamant")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 30;
			}
			return;
		}
		if (itemName.contains("rune")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")
					&& !itemName.contains("'bow")) {
				c.attackLevelReq = c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("dragon")) {
			if (!itemName.contains("nti-") && !itemName.contains("fire")) {
				c.attackLevelReq = c.defenceLevelReq = 60;
				return;
			}
		}
		if (itemName.contains("crystal")) {
			if (itemName.contains("shield")) {
				c.defenceLevelReq = 70;
			} else {
				c.rangeLevelReq = 70;
			}
			return;
		}
		if (itemName.contains("ahrim")) {
			if (itemName.contains("staff")) {
				c.magicLevelReq = 70;
				c.attackLevelReq = 70;
			} else {
				c.magicLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("karil")) {
			if (itemName.contains("crossbow")) {
				c.rangeLevelReq = 70;
			} else {
				c.rangeLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("godsword")) {
			c.attackLevelReq = 75;
		}
		if (itemName.contains("3rd age") && !itemName.contains("amulet")) {
			c.defenceLevelReq = 60;
		}
		if (itemName.contains("Initiate")) {
			c.defenceLevelReq = 20;
		}
		if (itemName.contains("verac") || itemName.contains("guthan")
				|| itemName.contains("dharok") || itemName.contains("torag")) {

			if (itemName.contains("hammers")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("axe")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("warspear")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("flail")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else {
				c.defenceLevelReq = 70;
			}
		}

		switch (itemId) {
		case 8839:
		case 8840:
		case 8842:
		case 11663:
		case 11664:
		case 11665:
			c.attackLevelReq = 42;
			c.rangeLevelReq = 42;
			c.strengthLevelReq = 42;
			c.magicLevelReq = 42;
			c.defenceLevelReq = 42;
			return;
		case 10551:
		case 2503:
		case 2501:
		case 2499:
		case 1135:
			c.defenceLevelReq = 40;
			return;
		case 1133:
			c.defenceLevelReq = 20;
			c.rangeLevelReq = 20;
			return;
		case 11235:
		case 6522:
			c.rangeLevelReq = 60;
			break;
		case 1097:
			c.rangeLevelReq = 20;
			break;
		case 864:
		case 863:
			c.rangeLevelReq = 1;
			break;
		case 865:
			c.rangeLevelReq = 5;
			break;
		case 866:
			c.rangeLevelReq = 20;
			break;
		case 867:
			c.rangeLevelReq = 30;
			break;
		case 868:
			c.rangeLevelReq = 40;
			break;
		case 6524:
			c.defenceLevelReq = 60;
			break;
		case 11284:
			c.defenceLevelReq = 75;
			return;
		case 6889:
		case 6914:
			c.magicLevelReq = 60;
			break;
		case 10828:
			c.defenceLevelReq = 55;
			break;
		case 11724:
		case 11726:
		case 11728:
			c.defenceLevelReq = 65;
			break;
		case 847:
		case 849:
			c.rangeLevelReq = 20;
			break;
		case 843:
		case 845:
			c.rangeLevelReq = 5;
			break;
		case 851:
		case 853:
			c.rangeLevelReq = 30;
			break;
		case 855:
		case 857:
			c.rangeLevelReq = 40;
			break;
		case 859:
		case 861:
			c.rangeLevelReq = 50;
			break;
		case 3749:
		case 3751:
		case 3755:
		case 3753:
			c.defenceLevelReq = 45;
			break;

		case 7462:
		case 7461:
			c.defenceLevelReq = 40;
			break;
		case 8846:
			c.defenceLevelReq = 5;
			break;
		case 8847:
			c.defenceLevelReq = 10;
			break;
		case 8848:
			c.defenceLevelReq = 20;
			break;
		case 8849:
			c.defenceLevelReq = 30;
			break;
		case 8850:
			c.defenceLevelReq = 40;
			break;

		case 7460:
			c.defenceLevelReq = 40;
			break;

		case 837:
			c.rangeLevelReq = 61;
			break;

		case 4151: // if you don't want to use names
			c.attackLevelReq = 70;
			return;

		case 6724: // seercull
			c.rangeLevelReq = 60; // idk if that is correct
			return;
		case 6523:
		case 6525:
		case 6527:
			c.attackLevelReq = 60;
			return;
		case 6526:
			c.attackLevelReq = 60;
			c.magicLevelReq = 60;
			return;
		case 4156:
			c.defenceLevelReq = 20;
			c.slayerLevelReq = 25;
			return;
		case 1391:
		case 1393:
		case 1395:
		case 1397:
		case 1399:
		case 3053:
			c.attackLevelReq = 30;
			c.magicLevelReq = 30;
			return;
		case 4158:
			c.slayerLevelReq = 55;
			c.attackLevelReq = 50;
			return;
		case 4153:
			c.attackLevelReq = 50;
			c.strengthLevelReq = 50;
			return;
		case 6528:
			c.strengthLevelReq = 60;
			return;
		case 4161:
			c.slayerLevelReq = 20;
			return;
		case 4168:
			c.slayerLevelReq = 60;
			return;
		case 6696:
			c.slayerLevelReq = 22;
			return;
		case 8923:
			c.slayerLevelReq = 35;
			return;
		case 7159:
			c.slayerLevelReq = 37;
			return;
		case 6708:
			c.slayerLevelReq = 42;
			return;
		case 4170:
			c.slayerLevelReq = 55;
			return;
		case 4162:
			c.slayerLevelReq = 75;
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
			c.slayerLevelReq = 57;
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
			c.agilityLevelReq = 50;
			c.rangeLevelReq = 70;
			return;
		case 4150:
		case 4160:
		case 4172:
		case 4174:
			c.slayerLevelReq = 55;
			return;
		case 1015:
			c.defenceLevelReq = 1;
			return;
		case 6664:
			c.slayerLevelReq = 32;
			return;
		case 4551:
			c.defenceLevelReq = 5;
			return;
		case 7051:
			c.slayerLevelReq = 33;
			return;
		case 4166:
			c.slayerLevelReq = 15;
			return;
		case 4164:
			c.slayerLevelReq = 10;
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
		if (itemName.contains("godsword") || itemName.contains("aradomin sword") || itemName.contains("2h") || itemName.contains("spear")) {
			return true;
		}
		switch (itemId) {
		case 6724: // seercull
		case 11730:
		case 4153:
		case 6528:
		case 14484:
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
			c.getPlayerAssistant().sendFrame171(0, 12323);
			specialAmount(weapon, c.specAmount, 12335);
			break;

		case 859: // magic bows
		case 861:
		case 11235:
			c.getPlayerAssistant().sendFrame171(0, 7549);
			specialAmount(weapon, c.specAmount, 7561);
			break;

		case 4587: // dscimmy
			c.getPlayerAssistant().sendFrame171(0, 7599);
			specialAmount(weapon, c.specAmount, 7611);
			break;

		case 3204: // d hally
			c.getPlayerAssistant().sendFrame171(0, 8493);
			specialAmount(weapon, c.specAmount, 8505);
			break;

		case 1377: // d battleaxe
			c.getPlayerAssistant().sendFrame171(0, 7499);
			specialAmount(weapon, c.specAmount, 7511);
			break;

		case 4153: // gmaul
			c.getPlayerAssistant().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;

		case 1249: // dspear
			c.getPlayerAssistant().sendFrame171(0, 7674);
			specialAmount(weapon, c.specAmount, 7686);
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
			c.getPlayerAssistant().sendFrame171(0, 7574);
			specialAmount(weapon, c.specAmount, 7586);
			break;

		case 1434: // dragon mace
			c.getPlayerAssistant().sendFrame171(0, 7624);
			specialAmount(weapon, c.specAmount, 7636);
			break;

		default:
			c.getPlayerAssistant().sendFrame171(1, 7624); // mace
															// interface
			c.getPlayerAssistant().sendFrame171(1, 7474); // hammer, gmaul
			c.getPlayerAssistant().sendFrame171(1, 7499); // axe
			c.getPlayerAssistant().sendFrame171(1, 7549); // bow interface
			c.getPlayerAssistant().sendFrame171(1, 7574); // sword
															// interface
			c.getPlayerAssistant().sendFrame171(1, 7599); // scimmy sword
															// interface,
															// for most
			// swords
			c.getPlayerAssistant().sendFrame171(1, 8493);
			c.getPlayerAssistant().sendFrame171(1, 12323); // whip
															// interface
			break;
		}
	}

	/**
	 * Specials bar filling amount
	 **/

	public void specialAmount(int weapon, double specAmount, int barId) {
		c.specBarId = barId;
		c.getPlayerAssistant().sendFrame70(specAmount >= 10 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 9 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 8 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 7 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 6 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 5 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 4 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 3 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 2 ? 500 : 0, 0,
				--barId);
		c.getPlayerAssistant().sendFrame70(specAmount >= 1 ? 500 : 0, 0,
				--barId);
		updateSpecialBar();
		sendWeapon(weapon, getItemName(weapon));
	}

	/**
	 * Special attack text and what to highlight or blackout
	 **/

	public void updateSpecialBar() {
		if (c.usingSpecial) {
			c.getPlayerAssistant()
					.sendFrame126(
							""
									+ (c.specAmount >= 2 ? "@yel@S P"
											: "@bla@S P")
									+ ""
									+ (c.specAmount >= 3 ? "@yel@ E"
											: "@bla@ E")
									+ ""
									+ (c.specAmount >= 4 ? "@yel@ C I"
											: "@bla@ C I")
									+ ""
									+ (c.specAmount >= 5 ? "@yel@ A L"
											: "@bla@ A L")
									+ ""
									+ (c.specAmount >= 6 ? "@yel@  A"
											: "@bla@  A")
									+ ""
									+ (c.specAmount >= 7 ? "@yel@ T T"
											: "@bla@ T T")
									+ ""
									+ (c.specAmount >= 8 ? "@yel@ A"
											: "@bla@ A")
									+ ""
									+ (c.specAmount >= 9 ? "@yel@ C"
											: "@bla@ C")
									+ ""
									+ (c.specAmount >= 10 ? "@yel@ K"
											: "@bla@ K"), c.specBarId);
		} else {
			c.getPlayerAssistant().sendFrame126(
					"@bla@S P E C I A L  A T T A C K", c.specBarId);
		}
	}

	/**
	 * Wear Item
	 **/

	public boolean wearItem(int wearID, int slot) {
		if (!playerHasItem(wearID, 1, slot)) {
			// add a method here for logging cheaters(If you want)
			return false;
		}
		if (c.tutorialProgress < 22) {
			c.getActionSender().sendMessage(
					"You'll be told how to equip items later.");
			return false;
		}

		if (c.tutorialProgress == 22) {
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler()
					.chatboxText(
							c,
							"Clothes, armour, weapons and many other items are equipped",
							"like this. You can unequip items by clicking on the item in the",
							"worn inventory. You can close this window by clicking on the",
							"small x. Speak to the Combat Instructor to continue.",
							"You're now holding your dagger");
			c.getActionSender().chatbox(6179);
			c.tutorialProgress = 23;
			// c.setSidebarInterface(0, -1);// worn

		} else if (c.tutorialProgress == 23) {
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler()
					.chatboxText(
							c,
							"",
							"Click on the flashing crossed swords icon to see the combat",
							"interface.", "", "Combat interface");
			c.getActionSender().chatbox(6179);
			c.getActionSender().flashSideBarIcon(0);
			// c.getPacketDispatcher().tutorialIslandInterface(50, 11);
		}
		int targetSlot = 0;
		boolean canWearItem = true;
		if (c.playerItems[slot] == wearID + 1) {
			getRequirements(getItemName(wearID).toLowerCase(), wearID);
			targetSlot = Item.targetSlots[wearID];

			if (c.duelRule[11] && targetSlot == 0) {
				c.getActionSender().sendMessage(
						"Wearing hats has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[12] && targetSlot == 1) {
				c.getActionSender().sendMessage(
						"Wearing capes has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[13] && targetSlot == 2) {
				c.getActionSender().sendMessage(
						"Wearing amulets has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[14] && targetSlot == 3) {
				c.getActionSender().sendMessage(
						"Wielding weapons has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[15] && targetSlot == 4) {
				c.getActionSender().sendMessage(
						"Wearing bodies has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[16] && targetSlot == 5 || c.duelRule[16]
					&& is2handed(getItemName(wearID).toLowerCase(), wearID)) {
				c.getActionSender().sendMessage(
						"Wearing shield has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[17] && targetSlot == 7) {
				c.getActionSender().sendMessage(
						"Wearing legs has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[18] && targetSlot == 9) {
				c.getActionSender().sendMessage(
						"Wearing gloves has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[19] && targetSlot == 10) {
				c.getActionSender().sendMessage(
						"Wearing boots has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[20] && targetSlot == 12) {
				c.getActionSender().sendMessage(
						"Wearing rings has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[21] && targetSlot == 13) {
				c.getActionSender().sendMessage(
						"Wearing arrows has been disabled in this duel!");
				return false;
			}

			if (Constants.itemRequirements) {
				if (targetSlot == 10 || targetSlot == 7 || targetSlot == 5
						|| targetSlot == 4 || targetSlot == 0
						|| targetSlot == 9 || targetSlot == 10) {
					if (c.defenceLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[1]) < c.defenceLevelReq) {
							c.getActionSender().sendMessage(
									"You need a defence level of "
											+ c.defenceLevelReq
											+ " to wear this item.");
							canWearItem = false;
						}
					}
					if (c.rangeLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
							c.getActionSender().sendMessage(
									"You need a range level of "
											+ c.rangeLevelReq
											+ " to wear this item.");
							canWearItem = false;
						}
					}
					if (c.magicLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
							c.getActionSender().sendMessage(
									"You need a magic level of "
											+ c.magicLevelReq
											+ " to wear this item.");
							canWearItem = false;
						}
					}
				}
				if (c.slayerLevelReq > 0) {
					if (c.getPlayerAssistant().getLevelForXP(c.playerXP[18]) < c.slayerLevelReq) {
						c.getActionSender().sendMessage(
								"You need a slayer level of "
										+ c.slayerLevelReq
										+ " to wear this item.");
						canWearItem = false;
					}
				}
				if (c.agilityLevelReq > 0) {
					if (c.getPlayerAssistant().getLevelForXP(c.playerXP[16]) < c.agilityLevelReq) {
						c.getActionSender().sendMessage(
								"You need a agility level of "
										+ c.agilityLevelReq
										+ " to wear this item.");
						canWearItem = false;
					}
				}
				if (targetSlot == 3) {
					if (c.attackLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[0]) < c.attackLevelReq) {
							c.getActionSender().sendMessage(
									"You need an attack level of "
											+ c.attackLevelReq
											+ " to wield this weapon.");
							canWearItem = false;
						}
					}
					if (c.rangeLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
							c.getActionSender().sendMessage(
									"You need a range level of "
											+ c.rangeLevelReq
											+ " to wield this weapon.");
							canWearItem = false;
						}
					}
					if (c.magicLevelReq > 0) {
						if (c.getPlayerAssistant().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
							c.getActionSender().sendMessage(
									"You need a magic level of "
											+ c.magicLevelReq
											+ " to wield this weapon.");
							canWearItem = false;
						}
					}
				}
			}

			switch (wearID) {
			case 6181:
			case 428:// legs
			case 538:
			case 6343:
			case 6353:
			case 6363:
			case 6396:
			case 6373:
			case 6404:
			case 5044:
			case 5046:
			case 5050:
			case 5052:
			case 5040:
			case 5038:
			case 6752:
			case 5048:
			case 5036:
			case 5042:
			case 4300:
			case 1835:
			case 7116:
			case 7126:
			case 7132:
			case 7138:
			case 548:
			case 6185:
				targetSlot = 7;
				break;
			case 4166:// hats
			case 1167:
			case 5525:
			case 4168:
			case 1025:
			case 7112:
			case 7124:
			case 7130:
			case 7136:
			case 4611:
			case 5527:
			case 5529:
			case 5531:
			case 5533:
			case 5535:
			case 5537:
			case 5539:
			case 5541:
			case 5543:
			case 5545:
			case 5547:
				targetSlot = 0;
				break;
			case 4304:// cape
			case 3759:
			case 3761:
			case 3763:
			case 3765:
			case 3777:
			case 3779:
			case 3781:
			case 3783:
			case 3785:
			case 3787:
			case 3789:
			case 4514:
			case 4516:
				targetSlot = 1;
				break;
			case 7051:
			case 7053:// shields
				targetSlot = 5;
				break;
			case 577:
			case 426:// bodies
			case 540:
			case 430:
			case 6786:
			case 581:
			case 5024:
			case 5030:
			case 1757:
			case 5034:
			case 5032:
			case 3793:
			case 1005:
			case 546:
			case 6402:
			case 6788:
			case 6184:
			case 6186:
				targetSlot = 4;
				break;
			case 3853:
			case 3855:
			case 3857:
			case 3859:
			case 3861:
			case 3863:
			case 1718:
			case 3865:
			case 4306:
			case 3867:// necklace/amulet
			case 1702:
				targetSlot = 2;
				break;
			case 776:// gloves
				targetSlot = 9;
				break;
			case 1215:
			case 1231:
			case 5680:
			case 5698:
			case 1305:
				if (c.spiritTree == false && c.playerRights != 3) {
					c.getActionSender().sendMessage("You need to beat the tree spirit to wear this.");
					return false;
				}
			}
			if (!canWearItem) {
				return false;
			}

			int wearAmount = c.playerItemsN[slot];
			if (wearAmount < 1) {
				return false;
			}

			if (targetSlot == c.playerWeapon) {
				c.autocasting = false;
				c.autocastId = 0;
				c.getPlayerAssistant().sendConfig(108, 0);
			}

			if (CastleWars.isInCw(c) || CastleWars.isInCwWait(c)) {
				if (targetSlot == 1 || targetSlot == 0) {
					c.getActionSender().sendMessage("You can't wear your own capes or hats in a Castle Wars Game!");
					return false;
				}
			}

			/*
			 * if (slot >= 0 && wearID >= 0) { int toEquip =
			 * c.playerItems[slot]; int toEquipN = c.playerItemsN[slot]; int
			 * toRemove = c.playerEquipment[targetSlot]; int toRemoveN =
			 * c.playerEquipmentN[targetSlot];
			 */

			if (slot >= 0 && wearID >= 0) {
				int toEquip = c.playerItems[slot];
				int toEquipN = c.playerItemsN[slot];
				int toRemove = c.playerEquipment[targetSlot];
				int toRemoveN = c.playerEquipmentN[targetSlot];
				if (toEquip == toRemove + 1 && Item.itemStackable[toRemove]) {
					deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
					c.playerEquipmentN[targetSlot] += toEquipN;
					/*
					 * Castle wars
					 */

					if (CastleWars.SARA_BANNER == toRemove
							|| CastleWars.ZAMMY_BANNER == toRemove) { // alk
																		// update
						CastleWars.dropFlag(c, toRemove);
						toRemove = -1;
						toRemoveN = 0;
					}
				} else if (targetSlot != 5 && targetSlot != 3) {
					// c.playerItems[slot] = 0;
					// c.playerItemsN[slot] = 0;
					// c.playerEquipment[targetSlot] = toEquip - 1;
					// c.playerEquipmentN[targetSlot] = toEquipN;

					c.playerItems[slot] = 0;
					c.playerItemsN[slot] = 0;
					if (toRemove > 0 && toRemoveN > 0) {
						addItem(toRemove, toRemoveN);
					}
					c.playerEquipment[targetSlot] = toEquip - 1;
					c.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 5) {
					boolean wearing2h = is2handed(
							getItemName(c.playerEquipment[c.playerWeapon])
									.toLowerCase(),
							c.playerEquipment[c.playerWeapon]);
					if (wearing2h) {
						toRemove = c.playerEquipment[c.playerWeapon];
						toRemoveN = c.playerEquipmentN[c.playerWeapon];
						c.playerEquipment[c.playerWeapon] = -1;
						c.playerEquipmentN[c.playerWeapon] = 0;
						updateSlot(c.playerWeapon);
					}
					c.playerItems[slot] = toRemove + 1;
					c.playerItemsN[slot] = toRemoveN;
					c.playerEquipment[targetSlot] = toEquip - 1;
					c.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 3) {
					boolean is2h = is2handed(getItemName(wearID).toLowerCase(),
							wearID);
					boolean wearingShield = c.playerEquipment[c.playerShield] > 0;
					boolean wearingWeapon = c.playerEquipment[c.playerWeapon] > 0;
					if (is2h) {
						if (wearingShield && wearingWeapon) {
							if (freeSlots() > 0) {
								c.playerItems[slot] = toRemove + 1;
								c.playerItemsN[slot] = toRemoveN;
								c.playerEquipment[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
								removeItem(c.playerEquipment[c.playerShield],
										c.playerShield);
							} else {
								c.getActionSender()
										.sendMessage(
												"You do not have enough inventory space to do this.");
								return false;
							}
						} else if (wearingShield && !wearingWeapon) {
							c.playerItems[slot] = c.playerEquipment[c.playerShield] + 1;
							c.playerItemsN[slot] = c.playerEquipmentN[c.playerShield];
							c.playerEquipment[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
							c.playerEquipment[c.playerShield] = -1;
							c.playerEquipmentN[c.playerShield] = 0;
							updateSlot(c.playerShield);
						} else {
							c.playerItems[slot] = toRemove + 1;
							c.playerItemsN[slot] = toRemoveN;
							c.playerEquipment[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
						}
					} else {
						// c.playerItems[slot] = toRemove + 1;
						// c.playerItemsN[slot] = toRemoveN;
						// c.playerEquipment[targetSlot] = toEquip - 1;
						// c.playerEquipmentN[targetSlot] = toEquipN;

						c.playerItems[slot] = 0;
						c.playerItemsN[slot] = 0;

						// c.playerItems[slot] = toRemove + 1;
						// c.playerItemsN[slot] = toRemoveN;
						if (toRemove > 0 && toRemoveN > 0) {
							addItem(toRemove, toRemoveN);
						}
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					}
				}
			}
			resetItems(3214);
			if (targetSlot == 3) {
				c.usingSpecial = false;
				addSpecialBar(wearID);
			}
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(targetSlot);
				c.getOutStream().writeWord(wearID + 1);

				if (c.playerEquipmentN[targetSlot] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[targetSlot]);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[targetSlot]);
				}

				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
			sendWeapon(c.playerEquipment[c.playerWeapon],
					getItemName(c.playerEquipment[c.playerWeapon]));
			resetBonus();
			getBonus();
			writeBonus();
			c.getCombatAssistant().getPlayerAnimIndex();
			c.getPlayerAssistant().requestUpdates();
			return true;
		} else {
			return false;
		}
	}

	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(targetSlot);
				c.getOutStream().writeWord(wearID + 1);

				if (wearAmount > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(wearAmount);
				} else {
					c.getOutStream().writeByte(wearAmount);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipment[targetSlot] = wearID;
				c.playerEquipmentN[targetSlot] = wearAmount;
				c.getItemAssistant();
				c.getItemAssistant()
						.sendWeapon(
								c.playerEquipment[c.playerWeapon],
								ItemAssistant
										.getItemName(c.playerEquipment[c.playerWeapon]));
				resetBonus();
				getBonus();
				/*for (int bowId : RangeData.BOWS) {
					if (c.playerEquipment[c.playerWeapon] == bowId) {
						for (int arrowId : RangeData.ARROWS) {
							if (c.playerEquipment[c.playerArrows] == arrowId && c.playerEquipment[c.playerWeapon] == bowId) {
								writeBonus();
							} else if (c.playerEquipment[c.playerWeapon] == bowId && c.playerEquipment[c.playerArrows] != arrowId) {
								writeBonus();
							} else if (c.playerEquipment[c.playerArrows] != arrowId && c.playerEquipment[c.playerWeapon] != bowId) {
								writeBonus();
							}
						}
					}
				}*/
				writeBonus();
				c.getCombatAssistant().getPlayerAnimIndex();
				c.updateRequired = true;
				c.setAppearanceUpdateRequired(true);
			}
		}
	}

	public void updateSlot(int slot) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(slot);
			c.getOutStream().writeWord(c.playerEquipment[slot] + 1);
			if (c.playerEquipmentN[slot] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(c.playerEquipmentN[slot]);
			} else {
				c.getOutStream().writeByte(c.playerEquipmentN[slot]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}

	}

	/**
	 * Remove Item
	 **/
	public void removeItem(int wearID, int slot) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (c.playerEquipment[slot] > -1) {
				if (c.playerEquipment[slot] == CastleWars.SARA_BANNER|| c.playerEquipment[slot] == CastleWars.ZAMMY_BANNER) {
					CastleWars.dropFlag(c, c.playerEquipment[slot]);
				}
				if ((c.playerEquipment[slot] == CastleWars.SARA_CAPE || c.playerEquipment[slot] == CastleWars.ZAMMY_CAPE) && c.inCw()) {
					c.getActionSender().sendMessage("You cannot unequip your castle wars cape!");
					return;
				}
				if (addItem(c.playerEquipment[slot], c.playerEquipmentN[slot])) {
					if (c.playerEquipment[slot] == CastleWars.SARA_BANNER || c.playerEquipment[slot] == CastleWars.ZAMMY_BANNER) {
						CastleWars.dropFlag(c, c.playerEquipment[slot]);
						deleteItem2(c.playerEquipment[slot], 1);
					}
					c.playerEquipment[slot] = -1;
					c.playerEquipmentN[slot] = 0;
					sendWeapon(c.playerEquipment[c.playerWeapon],
							getItemName(c.playerEquipment[c.playerWeapon]));
					resetBonus();
					getBonus();
					writeBonus();
					c.getCombatAssistant().getPlayerAnimIndex();
					c.getOutStream().createFrame(34);
					c.getOutStream().writeWord(6);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(slot);
					c.getOutStream().writeWord(0);
					c.getOutStream().writeByte(0);
					c.flushOutStream();
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					Weight.calcWeight(c, wearID, "deleteitem");
				}
			}
		}
		// }
	}

	/**
	 * BANK
	 */

	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < Constants.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (c.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (c.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							c.bankItems[j - spots] = c.bankItems[j];
							c.bankItemsN[j - spots] = c.bankItemsN[j];
							stop = true;
							c.bankItems[j] = 0;
							c.bankItemsN[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < Constants.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter) {
			c.disconnected = true;
		}
	}

	public void itemOnInterface(int id, int amount) {
		// synchronized(c) {
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(2274);
		c.getOutStream().writeWord(1);
		if (amount > 254) {
			c.getOutStream().writeByte(255);
			c.getOutStream().writeDWord_v2(amount);
		} else {
			c.getOutStream().writeByte(amount);
		}
		c.getOutStream().writeWordBigEndianA(id);
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public void resetBank() {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(5382); // bank
			c.getOutStream().writeWord(Constants.BANK_SIZE);
			for (int i = 0; i < Constants.BANK_SIZE; i++) {
				if (c.bankItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.bankItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.bankItemsN[i]);
				}
				if (c.bankItemsN[i] < 1) {
					c.bankItems[i] = 0;
				}
				if (c.bankItems[i] > Constants.ITEM_LIMIT || c.bankItems[i] < 0) {
					c.bankItems[i] = Constants.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(c.bankItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void resetTempItems() {
		// synchronized(c) {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > -1) {
				itemCount = i;
			}
		}
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(5064);
		c.getOutStream().writeWord(itemCount + 1);
		for (int i = 0; i < itemCount + 1; i++) {
			if (c.playerItemsN[i] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
			} else {
				c.getOutStream().writeByte(c.playerItemsN[i]);
			}
			if (c.playerItems[i] > Constants.ITEM_LIMIT || c.playerItems[i] < 0) {
				c.playerItems[i] = Constants.ITEM_LIMIT;
			}
			c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
		// }
	}

	public boolean bankItem(int itemID, int fromSlot, int amount) {
		if (c.inTrade) {
			c.getActionSender().sendMessage("You can't store items while trading!");
			return false;
		}
		for (int i = 0; i < Constants.ITEM_BANKABLE.length; i++) {
			if (itemID == Constants.ITEM_BANKABLE[i]) {
				c.getActionSender().sendMessage("You can't bank that item!");
				return false;
			}
		}
		if (!CastleWars.deleteCastleWarsItems(c, itemID)) {
			return false;
		}
		if (c.otherBank == true) {
			c.getPlayerAssistant().closeAllWindows();
			c.getActionSender().sendMessage("You can't bank while viewing someones bank!");
			c.otherBank = false;
			return false;
		}
		if (c.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!Item.itemIsNote[c.playerItems[fromSlot] - 1]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1] || c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Constants.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						if (c.playerItemsN[fromSlot] < amount) {
							amount = c.playerItemsN[fromSlot];
						}
						alreadyInBank = true;
						toBankSlot = i;
						i = Constants.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Constants.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Constants.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = c.playerItems[fromSlot];
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if (c.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT
							&& c.bankItemsN[toBankSlot] + amount > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.getActionSender().sendMessage("Bank full!");
						return false;
					}
					deleteItem(c.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if (c.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT
							&& c.bankItemsN[toBankSlot] + amount > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.getActionSender().sendMessage("Bank full!");
						return false;
					}
					deleteItem(c.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.getActionSender().sendMessage("Your bank is full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Constants.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Constants.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Constants.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Constants.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if (c.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = c.playerItems[firstPossibleSlot];
							c.bankItemsN[toBankSlot] += 1;
							deleteItem(c.playerItems[firstPossibleSlot] - 1,
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
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if (c.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem(c.playerItems[firstPossibleSlot] - 1,
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
					c.getActionSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else if (Item.itemIsNote[c.playerItems[fromSlot] - 1] && !Item.itemIsNote[c.playerItems[fromSlot] - 2]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1] || c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Constants.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot] - 1) {
						if (c.playerItemsN[fromSlot] < amount) {
							amount = c.playerItemsN[fromSlot];
						}
						alreadyInBank = true;
						toBankSlot = i;
						i = Constants.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Constants.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Constants.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = c.playerItems[fromSlot] - 1;
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if (c.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT && c.bankItemsN[toBankSlot] + amount > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem(c.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if (c.bankItemsN[toBankSlot] + amount <= Constants.MAXITEM_AMOUNT && c.bankItemsN[toBankSlot] + amount > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem(c.playerItems[fromSlot] - 1, fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.getActionSender().sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Constants.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot] - 1) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Constants.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Constants.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Constants.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if (c.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = c.playerItems[firstPossibleSlot] - 1;
							c.bankItemsN[toBankSlot] += 1;
							deleteItem(c.playerItems[firstPossibleSlot] - 1,
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
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if (c.playerItems[i] == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem(c.playerItems[firstPossibleSlot] - 1,
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
					c.getActionSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			c.getActionSender().sendMessage("Item not supported " + (c.playerItems[fromSlot] - 1));
			return false;
		}
	}

	public int freeBankSlots() {
		int freeS = 0;
			for (int i = 0; i < Constants.BANK_SIZE; i++) {
				if (c.bankItems[i] <= 0) {
					freeS++;
				}
			}
		return freeS;
	}

	public void fromBank(int itemID, int fromSlot, int amount) {
		if (amount > 0) {
			if (c.bankItems[fromSlot] > 0) {
				if (!c.takeAsNote) {
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem(c.bankItems[fromSlot] - 1, amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								resetItems(5064);
							}
						} else {
							if (addItem(c.bankItems[fromSlot] - 1,
									c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem(c.bankItems[fromSlot] - 1, 1)) {
									c.bankItemsN[fromSlot] += -1;
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
				} else if (c.takeAsNote && Item.itemIsNote[c.bankItems[fromSlot]]) {
					if (c.bankItemsN[fromSlot] > amount) {
						if (addItem(c.bankItems[fromSlot], amount)) {
							c.bankItemsN[fromSlot] -= amount;
							resetBank();
							resetItems(5064);
						}
					} else {
						if (addItem(c.bankItems[fromSlot], c.bankItemsN[fromSlot])) {
							c.bankItems[fromSlot] = 0;
							c.bankItemsN[fromSlot] = 0;
							resetBank();
							resetItems(5064);
						}
					}
				} else {
					c.getActionSender().sendMessage("This item can't be withdrawn as a note.");
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem(c.bankItems[fromSlot] - 1, amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								resetItems(5064);
							}
						} else {
							if (addItem(c.bankItems[fromSlot] - 1,
									c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem(c.bankItems[fromSlot] - 1, 1)) {
									c.bankItemsN[fromSlot] += -1;
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
			}
		}
	}

	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				tempAmount += c.playerItemsN[i];
			}
		}
		return tempAmount;
	}

	public boolean isStackable(int itemID) {
		return Item.itemStackable[itemID];
	}

	/**
	 * Update Equip tab
	 **/

	public void setEquipment(int wearID, int amount, int targetSlot) {
		// synchronized(c) {
		c.getOutStream().createFrameVarSizeWord(34);
		c.getOutStream().writeWord(1688);
		c.getOutStream().writeByte(targetSlot);
		c.getOutStream().writeWord(wearID + 1);
		if (amount > 254) {
			c.getOutStream().writeByte(255);
			c.getOutStream().writeDWord(amount);
		} else {
			c.getOutStream().writeByte(amount);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
		c.playerEquipment[targetSlot] = wearID;
		c.playerEquipmentN[targetSlot] = amount;
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	/**
	 * Move Items
	 **/

	public void moveItems(int from, int to, int moveWindow, boolean insertMode) {
		if (moveWindow == 3214) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];
			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
		}

		if (moveWindow == 5382 && from >= 0 && to >= 0
				&& from < Constants.BANK_SIZE && to < Constants.BANK_SIZE
				&& to < Constants.BANK_SIZE) {
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
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
			resetItems(3214);
		}
		resetTempItems();
		if (moveWindow == 3214) {
			resetItems(3214);
		}

	}

	public void swapBankItem(int from, int to) {
		int tempI = c.bankItems[from];
		int tempN = c.bankItemsN[from];
		c.bankItems[from] = c.bankItems[to];
		c.bankItemsN[from] = c.bankItemsN[to];
		c.bankItems[to] = tempI;
		c.bankItemsN[to] = tempN;
	}

	/**
	 * delete Item
	 **/

	public void deleteEquipment(int i, int j) {
		// synchronized(c) {
		if (PlayerHandler.players[c.playerId] == null) {
			return;
		}
		if (i < 0) {
			return;
		}

		c.playerEquipment[j] = -1;
		c.playerEquipmentN[j] = c.playerEquipmentN[j] - 1;
		c.getOutStream().createFrame(34);
		c.getOutStream().writeWord(6);
		c.getOutStream().writeWord(1688);
		c.getOutStream().writeByte(j);
		c.getOutStream().writeWord(0);
		c.getOutStream().writeByte(0);
		getBonus();
		if (j == c.playerWeapon) {
			sendWeapon(-1, "Unarmed");
		}
		resetBonus();
		getBonus();
		writeBonus();
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void deleteItem(int id, int amount) {
		if (id <= 0) {
			return;
		}
		for (int j = 0; j < c.playerItems.length; j++) {
			if (amount <= 0) {
				break;
			}
			if (c.playerItems[j] == id + 1) {
				c.playerItems[j] = 0;
				c.playerItemsN[j] = 0;
				amount--;
			}
		}
		resetItems(3214);
		Weight.calcWeight(c, id, "deleteitem");
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (c.playerItems[slot] == id + 1) {
			if (c.playerItemsN[slot] > amount) {
				c.playerItemsN[slot] -= amount;
			} else {
				c.playerItemsN[slot] = 0;
				c.playerItems[slot] = 0;
			}
			resetItems(3214);
			Weight.calcWeight(c, id, "deleteitem");
		}
	}

	public void deleteItem2(int id, int amount) {
		int am = amount;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (am == 0) {
				break;
			}
			if (c.playerItems[i] == id + 1) {
				if (c.playerItemsN[i] > amount) {
					c.playerItemsN[i] -= amount;
					break;
				} else {
					c.playerItems[i] = 0;
					c.playerItemsN[i] = 0;
					am--;
				}
			}
		}
		resetItems(3214);
		Weight.calcWeight(c, id, "deleteItem");
	}

	/**
	 * Delete Arrows
	 **/
	public void deleteArrow() {
		// synchronized(c) {
		if (c.playerEquipment[c.playerCape] == 10499 && Misc.random(5) != 1
				&& c.playerEquipment[c.playerArrows] != 4740) {
			return;
		}
		if (c.playerEquipmentN[c.playerArrows] == 1) {
			deleteEquipment(
					c.playerEquipment[c.playerArrows], c.playerArrows);
		}
		if (c.playerEquipmentN[c.playerArrows] != 0) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(c.playerArrows);
			c.getOutStream().writeWord(c.playerEquipment[c.playerArrows] + 1);
			if (c.playerEquipmentN[c.playerArrows] - 1 > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(
						c.playerEquipmentN[c.playerArrows] - 1);
			} else {
				c.getOutStream().writeByte(
						c.playerEquipmentN[c.playerArrows] - 1);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipmentN[c.playerArrows] -= 1;
		}
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void deleteEquipment() {
		// synchronized(c) {
		if (c.playerEquipmentN[c.playerWeapon] == 1) {
			deleteEquipment(
					c.playerEquipment[c.playerWeapon], c.playerWeapon);
		}
		if (c.playerEquipmentN[c.playerWeapon] != 0) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(c.playerWeapon);
			c.getOutStream().writeWord(c.playerEquipment[c.playerWeapon] + 1);
			if (c.playerEquipmentN[c.playerWeapon] - 1 > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(
						c.playerEquipmentN[c.playerWeapon] - 1);
			} else {
				c.getOutStream().writeByte(
						c.playerEquipmentN[c.playerWeapon] - 1);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipmentN[c.playerWeapon] -= 1;
		}
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	/**
	 * Dropping Arrows
	 **/

	public void dropArrowNpc() {
		if (c.playerEquipment[c.playerCape] == 10499) {
			return;
		}
		int enemyX = NpcHandler.npcs[c.oldNpcIndex].getX();
		int enemyY = NpcHandler.npcs[c.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed,
						enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	public void dropArrowPlayer() {
		int enemyX = PlayerHandler.players[c.oldPlayerIndex].getX();
		int enemyY = PlayerHandler.players[c.oldPlayerIndex].getY();
		if (c.playerEquipment[c.playerCape] == 10499) {
			return;
		}
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed,
						enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		resetItems(3214);
		/*
		 * c.weight = 0; Weight.updateWeight(c);
		 */
	}

	public int freeSlots() {
		int freeS = 0;
		for (int playerItem : c.playerItems) {
			if (playerItem <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if (items[i] - 1 == id && amounts[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	public static String getItemName(int ItemID) {
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					return Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		return "Unarmed";
	}

	public int getItemId(String itemName) {
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName
						.equalsIgnoreCase(itemName)) {
					return Server.itemHandler.ItemList[i].itemId;
				}
			}
		}
		return -1;
	}

	public int getItemSlot(int ItemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 == ItemID) {
				return i;
			}
		}
		return -1;
	}

	public int getItemAmount(int ItemID) {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 == ItemID) {
				itemCount += c.playerItemsN[i];
			}
		}
		return itemCount;
	}

	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (c.playerItems[slot] == itemID) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] == itemID) {
					if (c.playerItemsN[i] >= amt) {
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
		for (int playerItem : c.playerItems) {
			if (playerItem == itemID) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				if (c.playerItemsN[i] >= amt) {
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

	public int getUnnotedItem(int ItemID) {
		int NewID = ItemID - 1;
		String NotedName = "";
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					NotedName = Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName == NotedName) {
					if (Server.itemHandler.ItemList[i].itemDescription.startsWith("Swap this note at any bank for a") == false) {
						NewID = Server.itemHandler.ItemList[i].itemId;
						break;
					}
				}
			}
		}
		return NewID;
	}
	
}
