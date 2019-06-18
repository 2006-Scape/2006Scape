package redone.game.content.minigames;

import java.util.concurrent.CopyOnWriteArrayList;

import redone.Constants;
import redone.Server;
import redone.game.content.combat.prayer.PrayerDrain;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.items.GameItem;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.items.impl.RareProtection;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.game.players.PlayerSave;
import redone.util.GameLogger;
import redone.util.Misc;

public class Dueling {

	private final Client player;

	public Dueling(Client Client) {
		player = Client;
	}

	public CopyOnWriteArrayList<GameItem> otherStakedItems = new CopyOnWriteArrayList<GameItem>();
	public CopyOnWriteArrayList<GameItem> stakedItems = new CopyOnWriteArrayList<GameItem>();

	public void requestDuel(int id) {
		try {
			if (id == player.playerId) {
				return;
			}
			if (!CastleWars.deleteCastleWarsItems(player, id)) {
				return;
			}
			if (player.inTrade || player.isShopping) {
				player.getActionSender().sendMessage("You can not stake currently.");
				return;
			}
			if (!player.inDuelArena()) {
				player.getActionSender().sendMessage("You must be in the duel arena to do that.");
				return;
			}
			resetDuel();
			resetDuelItems();
			player.duelingWith = id;
			Client o = (Client) PlayerHandler.players[id];
			if (o == null) {
				return;
			}
			/*if (player.connectedFrom.equals(o.connectedFrom)) {
				player.getActionSender().sendMessage("You can't duel your own IP.");
				return;
			}*/
			player.duelRequested = true;
			if (player.duelStatus == 0 && o.duelStatus == 0 && player.duelRequested
					&& o.duelRequested && player.duelingWith == o.getId()
					&& o.duelingWith == player.getId()) {
				if (player.goodDistance(player.getX(), player.getY(), o.getX(), o.getY(), 2)) {
					player.getDueling().openDuel();
					o.getDueling().openDuel();
				} else {
					player.getActionSender()
							.sendMessage(
									"You need to get closer to your opponent to start the duel.");
				}

			} else {
				player.getActionSender().sendMessage("Sending duel request...");
				o.getActionSender().sendMessage(player.playerName + ":duelreq:");
			}
		} catch (Exception e) {
			Misc.println("Error requesting duel.");
		}
	}

	public void openDuel() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (o == null) {
			return;
		}
		if (player.inTrade || player.isShopping) {
			player.getActionSender().sendMessage("You can not stake currently.");
			return;
		}
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to do that.");
			return;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return;
		}
		player.duelStatus = 1;
		refreshduelRules();
		refreshDuelScreen();
		player.openDuel = true;
	    o.openDuel = true;
		for (int i = 0; i < player.playerEquipment.length; i++) {
			sendDuelEquipment(player.playerEquipment[i], player.playerEquipmentN[i], i);
		}
		player.getPlayerAssistant().sendFrame126("Dueling with: " + o.playerName + " (level-" + o.combatLevel + ")", 6671);
		player.getPlayerAssistant().sendFrame126("", 6684);
		player.getPlayerAssistant().sendFrame248(6575, 3321);
		player.getItemAssistant().resetItems(3322);
	}

	public void sendDuelEquipment(int itemId, int amount, int slot) {
		synchronized (player) {
			if (itemId != 0) {
				player.getOutStream().createFrameVarSizeWord(34);
				player.getOutStream().writeWord(13824);
				player.getOutStream().writeByte(slot);
				player.getOutStream().writeWord(itemId + 1);

				if (amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord(amount);
				} else {
					player.getOutStream().writeByte(amount);
				}
				player.getOutStream().endFrameVarSizeWord();
				player.flushOutStream();
			}
		}
	}

	public void refreshduelRules() {
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
		player.getPlayerAssistant().sendFrame87(286, 0);
		player.duelOption = 0;
	}

	public void refreshDuelScreen() {
		synchronized (player) {
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o == null) {
				return;
			}
		    if(!player.openDuel && !o.openDuel) {
                declineDuel();
                return;
            }
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6669);
			player.getOutStream().writeWord(stakedItems.toArray().length);
			int current = 0;
			for (GameItem item : stakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > Constants.ITEM_LIMIT || item.id < 0) {
					item.id = Constants.ITEM_LIMIT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);

				current++;
			}

			if (current < 27) {
				for (int i = current; i < 28; i++) {
					player.getOutStream().writeByte(1);
					player.getOutStream().writeWordBigEndianA(-1);
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();

			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6670);
			player.getOutStream().writeWord(
					o.getDueling().stakedItems.toArray().length);
			current = 0;
			for (GameItem item : o.getDueling().stakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > Constants.ITEM_LIMIT || item.id < 0) {
					item.id = Constants.ITEM_LIMIT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);
				current++;
			}

			if (current < 27) {
				for (int i = current; i < 28; i++) {
					player.getOutStream().writeByte(1);
					player.getOutStream().writeWordBigEndianA(-1);
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public boolean stakeItem(int itemID, int fromSlot, int amount) {

		for (int i : Constants.ITEM_TRADEABLE) {
			if (i == itemID || itemID >= 6864 && itemID <= 6882) {
				player.getActionSender().sendMessage(
						"You can't stake this item.");
				return false;
			}
		}
		if (player.inTrade || player.isShopping) {
			player.getActionSender().sendMessage("You can not stake currently.");
			return false;
		}
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to do that.");
			return false;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return false;
		}
		if (!RareProtection.doOtherDupe(player, itemID)) {
			return false;
		}
		if (amount <= 0) {
			return false;
		}
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if(!player.openDuel && !o.openDuel) {
            declineDuel();
            return false;
        }
        if(!player.getItemAssistant().playerHasItem(itemID, amount))
            return false;
        if (amount <= 0)
            return false;
		if (o == null) {
			declineDuel();
			return false;
		}
		if (o.duelStatus <= 0 || player.duelStatus <= 0) {
			declineDuel();
			o.getDueling().declineDuel();
			return false;
		}
		changeDuelStuff();
		if (!Item.itemStackable[itemID]) {
			for (int a = 0; a < amount; a++) {
				if (player.getItemAssistant().playerHasItem(itemID, 1)) {
					stakedItems.add(new GameItem(itemID, 1));
					player.getItemAssistant().deleteItem(itemID,
							player.getItemAssistant().getItemSlot(itemID), 1);
				}
			}
			player.getItemAssistant().resetItems(3214);
			player.getItemAssistant().resetItems(3322);
			o.getItemAssistant().resetItems(3214);
			o.getItemAssistant().resetItems(3322);
			refreshDuelScreen();
			o.getDueling().refreshDuelScreen();
			player.getPlayerAssistant().sendFrame126("", 6684);
			o.getPlayerAssistant().sendFrame126("", 6684);
		}
		if (Item.itemStackable[itemID] || Item.itemIsNote[itemID]) {
			boolean found = false;
			for (GameItem item : stakedItems) {
				if (item.id == itemID) {
					found = true;
					item.amount += amount;
					player.getItemAssistant().deleteItem(itemID, fromSlot, amount);
					break;
				}
			}
			if (!found) {
				player.getItemAssistant().deleteItem(itemID, fromSlot, amount);
				stakedItems.add(new GameItem(itemID, amount));
			}
		}

		player.getItemAssistant().resetItems(3214);
		player.getItemAssistant().resetItems(3322);
		o.getItemAssistant().resetItems(3214);
		o.getItemAssistant().resetItems(3322);
		refreshDuelScreen();
		o.getDueling().refreshDuelScreen();
		player.getPlayerAssistant().sendFrame126("", 6684);
		o.getPlayerAssistant().sendFrame126("", 6684);
		return true;
	}

	public boolean fromDuel(int itemID, int fromSlot, int amount) {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (o == null) {
			declineDuel();
			return false;
		}
		if(!player.openDuel && !o.openDuel) {
            declineDuel();
            return false;
	    }
		if (player.inTrade || player.isShopping) {
			player.getActionSender().sendMessage("You can not stake currently.");
			return false;
		}
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to do that.");
			return false;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return false;
		}
		if (o.duelStatus <= 0 || player.duelStatus <= 0) {
			declineDuel();
			o.getDueling().declineDuel();
			return false;
		}
		if (Item.itemStackable[itemID]) {
			if (player.getItemAssistant().freeSlots() - 1 < player.duelSpaceReq) {
				player.getActionSender().sendMessage(
						"You have too many rules set to remove that item.");
				return false;
			}
		}

		changeDuelStuff();
		boolean goodSpace = true;
		if (!Item.itemStackable[itemID]) {
			for (int a = 0; a < amount; a++) {
				for (GameItem item : stakedItems) {
					if (item.id == itemID) {
						if (!item.stackable) {
							if (player.getItemAssistant().freeSlots() - 1 < player.duelSpaceReq) {
								goodSpace = false;
								break;
							}
							stakedItems.remove(item);
							player.getItemAssistant().addItem(itemID, 1);
						} else {
							if (player.getItemAssistant().freeSlots() - 1 < player.duelSpaceReq) {
								goodSpace = false;
								break;
							}
							if (item.amount > amount) {
								item.amount -= amount;
								player.getItemAssistant().addItem(itemID, amount);
							} else {
								if (player.getItemAssistant().freeSlots() - 1 < player.duelSpaceReq) {
									goodSpace = false;
									break;
								}
								amount = item.amount;
								stakedItems.remove(item);
								player.getItemAssistant().addItem(itemID, amount);
							}
						}
						break;
					}
					o.duelStatus = 1;
					player.duelStatus = 1;
					player.getItemAssistant().resetItems(3214);
					player.getItemAssistant().resetItems(3322);
					o.getItemAssistant().resetItems(3214);
					o.getItemAssistant().resetItems(3322);
					player.getDueling().refreshDuelScreen();
					o.getDueling().refreshDuelScreen();
					o.getPlayerAssistant().sendFrame126("", 6684);
				}
			}
		}

		for (GameItem item : stakedItems) {
			if (item.id == itemID) {
				if (!item.stackable) {
				} else {
					if (item.amount > amount) {
						item.amount -= amount;
						player.getItemAssistant().addItem(itemID, amount);
					} else {
						amount = item.amount;
						stakedItems.remove(item);
						player.getItemAssistant().addItem(itemID, amount);
					}
				}
				break;
			}
		}
		o.duelStatus = 1;
		player.duelStatus = 1;
		player.getItemAssistant().resetItems(3214);
		player.getItemAssistant().resetItems(3322);
		o.getItemAssistant().resetItems(3214);
		o.getItemAssistant().resetItems(3322);
		player.getDueling().refreshDuelScreen();
		o.getDueling().refreshDuelScreen();
		o.getPlayerAssistant().sendFrame126("", 6684);
		if (!goodSpace) {
			player.getActionSender().sendMessage(
					"You have too many rules set to remove that item.");
			return true;
		}
		return true;
	}

	public void confirmDuel() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (o == null) {
			declineDuel();
			return;
		}
		if (player.inTrade || player.isShopping) {
			player.getActionSender().sendMessage("You can not stake currently.");
			return;
		}
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to confirm a duel.");
			return;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return;
		}
		String itemId = "";
		for (GameItem item : stakedItems) {
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				itemId += ItemAssistant.getItemName(item.id) + " x "
						+ Misc.format(item.amount) + "\\n";
			} else {
				itemId += ItemAssistant.getItemName(item.id) + "\\n";
			}
		}
		player.getPlayerAssistant().sendFrame126(itemId, 6516);
		itemId = "";
		for (GameItem item : o.getDueling().stakedItems) {
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				itemId += ItemAssistant.getItemName(item.id) + " x "
						+ Misc.format(item.amount) + "\\n";
			} else {
				itemId += ItemAssistant.getItemName(item.id) + "\\n";
			}
		}
		player.getPlayerAssistant().sendFrame126(itemId, 6517);
		player.getPlayerAssistant().sendFrame126("", 8242);
		for (int i = 8238; i <= 8253; i++) {
			player.getPlayerAssistant().sendFrame126("", i);
		}
		player.getPlayerAssistant().sendFrame126("Hitpoints will be restored.", 8250);
		player.getPlayerAssistant().sendFrame126("Boosted stats will be restored.",
				8238);
		if (player.duelRule[8]) {
			player.getPlayerAssistant().sendFrame126(
					"There will be obstacles in the arena.", 8239);
		}
		player.getPlayerAssistant().sendFrame126("", 8240);
		player.getPlayerAssistant().sendFrame126("", 8241);

		String[] rulesOption = { "Players cannot forfeit!",
				"Players cannot move.", "Players cannot use range.",
				"Players cannot use melee.", "Players cannot use magic.",
				"Players cannot drink pots.", "Players cannot eat food.",
				"Players cannot use prayer." };

		int lineNumber = 8242;
		for (int i = 0; i < 8; i++) {
			if (player.duelRule[i]) {
				player.getPlayerAssistant().sendFrame126("" + rulesOption[i],
						lineNumber);
				lineNumber++;
			}
		}
		player.getPlayerAssistant().sendFrame126("", 6571);
		player.getPlayerAssistant().sendFrame248(6412, 197);
		// c.getPA().showInterface(6412);
	}

	public void startDuel() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to start a duel.");
			return;
		}
		if (player.inTrade || player.isShopping) {
			player.getActionSender().sendMessage("You can not stake currently.");
			return;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return;
		}
		if (o == null || o.disconnected) {
			duelVictory();
		}
		player.headIconHints = 2;

		if (player.duelRule[7]) {
			for (int p = 0; p < player.getPrayer().PRAYER.length; p++) { // reset
																	// prayer
																	// glows
				player.getPrayer().prayerActive[p] = false;
				player.getPlayerAssistant().sendConfig(player.getPrayer().PRAYER_GLOW[p],
						0);
			}
			player.headIcon = -1;
			player.getPlayerAssistant().requestUpdates();
		}
		if (player.duelRule[11]) {
			player.getItemAssistant().removeItem(player.playerEquipment[0], 0);
		}
		if (player.duelRule[12]) {
			player.getItemAssistant().removeItem(player.playerEquipment[1], 1);
		}
		if (player.duelRule[13]) {
			player.getItemAssistant().removeItem(player.playerEquipment[2], 2);
		}
		if (player.duelRule[14]) {
			player.getItemAssistant().removeItem(player.playerEquipment[3], 3);
		}
		if (player.duelRule[15]) {
			player.getItemAssistant().removeItem(player.playerEquipment[4], 4);
		}
		if (player.duelRule[16]) {
			player.getItemAssistant().removeItem(player.playerEquipment[5], 5);
		}
		if (player.duelRule[17]) {
			player.getItemAssistant().removeItem(player.playerEquipment[7], 7);
		}
		if (player.duelRule[18]) {
			player.getItemAssistant().removeItem(player.playerEquipment[9], 9);
		}
		if (player.duelRule[19]) {
			player.getItemAssistant().removeItem(player.playerEquipment[10], 10);
		}
		if (player.duelRule[20]) {
			player.getItemAssistant().removeItem(player.playerEquipment[12], 12);
		}
		if (player.duelRule[21]) {
			player.getItemAssistant().removeItem(player.playerEquipment[13], 13);
		}
		player.duelStatus = 5;
		player.getPlayerAssistant().removeAllWindows();
		player.specAmount = 10;
		player.getItemAssistant().addSpecialBar(player.playerEquipment[player.playerWeapon]);

		if (player.duelRule[8]) {
			if (player.duelRule[1]) {
				player.getPlayerAssistant().movePlayer(player.duelTeleX, player.duelTeleY, 0);
			} else {
				player.getPlayerAssistant().movePlayer(3366 + Misc.random(12),
						3246 + Misc.random(6), 0);
			}
		} else {
			if (player.duelRule[1]) {
				player.getPlayerAssistant().movePlayer(player.duelTeleX, player.duelTeleY, 0);
			} else {
				player.getPlayerAssistant().movePlayer(3335 + Misc.random(12),
						3246 + Misc.random(6), 0);
			}
		}

		player.getActionSender().createPlayerHints(10, o.playerId);
		player.getActionSender().showOption(3, 0, "Attack", 1);
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = player.getPlayerAssistant().getLevelForXP(
					player.playerXP[i]);
			player.getPlayerAssistant().refreshSkill(i);
		}
		for (GameItem item : o.getDueling().stakedItems) {
			otherStakedItems.add(new GameItem(item.id, item.amount));
		}
		PlayerSave.saveGame(player);
		player.getPlayerAssistant().requestUpdates();
	}

	public void duelVictory() {
		Client opponent = (Client) PlayerHandler.players[player.duelingWith];
		if (opponent != null) {
			player.getPlayerAssistant().sendFrame126("" + opponent.combatLevel, 6839);
			player.getPlayerAssistant().sendFrame126(opponent.playerName, 6840);
			opponent.duelStatus = 0;
		} else {
			player.getPlayerAssistant().sendFrame126("", 6839);
			player.getPlayerAssistant().sendFrame126("", 6840);
		}
		PrayerDrain.resetPrayers(player);
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = player.getPlayerAssistant().getLevelForXP(
					player.playerXP[i]);
			player.getPlayerAssistant().refreshSkill(i);
		}
		// c.getPacketDispatcher().sendSound(Sound.DUEL_WON, 100, 0);
		player.duelStatus = 6;
		  if (player.isSkulled) {
	            player.isSkulled = false;
	            player.skullTimer = 0;
	            player.headIconPk = -1;
	            player.getPlayerAssistant().requestUpdates();
	        }
		player.getPlayerAssistant().refreshSkill(3);
		duelRewardInterface();
		player.getPlayerAssistant().showInterface(6733);
		player.getActionSender().sendMessage("You have won the duel!");
		if (player.getPlayerAssistant().isPlayer()) {
			GameLogger.writeLog(player.playerName, "duelingkiller", player.playerName + " killed " + opponent.playerName + " in the duel arena.");
		}
		player.getPlayerAssistant().movePlayer(Constants.DUELING_RESPAWN_X + Misc.random(5), Constants.DUELING_RESPAWN_Y + Misc.random(5), 0);
		player.getPlayerAssistant().requestUpdates();
		player.getActionSender().showOption(3, 0, "Challenge", 3);
		player.getActionSender().createPlayerHints(10, -1);
		player.duelSpaceReq = 0;
		player.openDuel = false;
		opponent.openDuel = false;
		player.duelingWith = 0;
		player.getCombatAssistant().resetPlayerAttack();
		player.duelRequested = false;
		PlayerSave.saveGame(player);
	    if (opponent != null) {
	    	PlayerSave.saveGame(opponent);
	    }
	}

	public void duelRewardInterface() {
		synchronized (player) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(6822);
			player.getOutStream().writeWord(otherStakedItems.toArray().length);
			for (GameItem item : otherStakedItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				if (item.id > Constants.ITEM_LIMIT || item.id < 0) {
					item.id = Constants.ITEM_LIMIT;
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1);
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void claimStakedItems() {
		for (GameItem item : otherStakedItems) {
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!player.getItemAssistant().addItem(item.id, item.amount)) {
						Server.itemHandler.createGroundItem(player, item.id,
								player.getX(), player.getY(), item.amount, player.getId());
					}
				} else {
					int amount = item.amount;
					for (int a = 1; a <= amount; a++) {
						if (!player.getItemAssistant().addItem(item.id, 1)) {
							Server.itemHandler.createGroundItem(player, item.id,
									player.getX(), player.getY(), 1, player.getId());
						}
					}
				}
			}
		}
		for (GameItem item : stakedItems) {
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!player.getItemAssistant().addItem(item.id, item.amount)) {
						Server.itemHandler.createGroundItem(player, item.id,
								player.getX(), player.getY(), item.amount, player.getId());
					}
				} else {
					int amount = item.amount;
					for (int a = 1; a <= amount; a++) {
						if (!player.getItemAssistant().addItem(item.id, 1)) {
							Server.itemHandler.createGroundItem(player, item.id,
									player.getX(), player.getY(), 1, player.getId());
						}
					}
				}
			}
		}
		resetDuel();
		resetDuelItems();
		PlayerSave.saveGame(player);
		player.duelStatus = 0;
	}

	public void declineDuel() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to do that.");
			return;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return;
		}
		player.getPlayerAssistant().removeAllWindows();
		player.duelStatus = 0;
		player.openDuel = false;
		o.openDuel = false;
		player.duelingWith = 0;
		player.duelSpaceReq = 0;
		player.duelRequested = false;
		for (GameItem item : stakedItems) {
			if (item.amount < 1) {
				continue;
			}
			if (Item.itemStackable[item.id] || Item.itemIsNote[item.id]) {
				player.getItemAssistant().addItem(item.id, item.amount);
			} else {
				player.getItemAssistant().addItem(item.id, 1);
			}
		}
		stakedItems.clear();
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
	}
	
	public void checkDuelWalk() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (player.duelStatus == 5 && o.duelStatus == 5 && o.duelingArena() && !player.duelingArena()) {
			o.getDueling().duelVictory();
			player.getDueling().resetDuel();
			return;
		}
	}

	public void resetDuel() {
		//Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (player.isDead) {
			player.lostDuel = true;
		}
		player.getActionSender().showOption(3, 0, "Challenge", 3);
		player.headIconHints = 0;
		for (int i = 0; i < player.duelRule.length; i++) {
			player.duelRule[i] = false;
		}
		player.getActionSender().createPlayerHints(10, -1);
		player.duelStatus = 0;
		player.duelSpaceReq = 0;
		//player.openDuel = false;
		//o.openDuel = false;
		player.duelingWith = 0;
		player.getPlayerAssistant().requestUpdates();
		player.getCombatAssistant().resetPlayerAttack();
		player.duelRequested = false;
	}

	public void resetDuelItems() {
		stakedItems.clear();
		otherStakedItems.clear();
	}

	public void changeDuelStuff() {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (o == null) {
			return;
		}
		o.duelStatus = 1;
		player.duelStatus = 1;
		o.getPlayerAssistant().sendFrame126("", 6684);
		player.getPlayerAssistant().sendFrame126("", 6684);
	}

	public void selectRule(int i) { // rules
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (!player.inDuelArena()) {
			player.getActionSender().sendMessage("You must be in the duel arena to change your rules.");
			return;
		}
		if (player.duelingArena()) {
			player.getActionSender().sendMessage("You can't do that in a duel!");
			return;
		}
		if (o == null) {
			return;
		}
		changeDuelStuff();
		o.duelSlot = player.duelSlot;
		if (i >= 11 && player.duelSlot > -1) {
			if (player.playerEquipment[player.duelSlot] > 0) {
				if (!player.duelRule[i]) {
					player.duelSpaceReq++;
				} else {
					player.duelSpaceReq--;
				}
			}
			if (o.playerEquipment[o.duelSlot] > 0) {
				if (!o.duelRule[i]) {
					o.duelSpaceReq++;
				} else {
					o.duelSpaceReq--;
				}
			}
		}
		if (i == 16 && (player.getItemAssistant().is2handed(ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase(), player.playerEquipment[player.playerWeapon])
	                && player.getItemAssistant().freeSlots() == 0) || (o.getItemAssistant().is2handed(ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase(), player.playerEquipment[player.playerWeapon])
	                && o.getItemAssistant().freeSlots() == 0)) {
	            player.getActionSender().sendMessage("You or your opponent don't have the required space to set this rule.");
	            return;
	        }

		if (i >= 11) {
			if (player.getItemAssistant().freeSlots() < player.duelSpaceReq
					|| o.getItemAssistant().freeSlots() < o.duelSpaceReq) {
				player.getActionSender()
						.sendMessage(
								"You or your opponent don't have the required space to set this rule.");
				if (player.playerEquipment[player.duelSlot] > 0) {
					player.duelSpaceReq--;
				}
				if (o.playerEquipment[o.duelSlot] > 0) {
					o.duelSpaceReq--;
				}
				return;
			}
		}

		if (!player.duelRule[i]) {
			player.duelRule[i] = true;
			player.duelOption += player.DUEL_RULE_ID[i];
		} else {
			player.duelRule[i] = false;
			player.duelOption -= player.DUEL_RULE_ID[i];
		}

		player.getPlayerAssistant().sendFrame87(286, player.duelOption);
		o.duelOption = player.duelOption;
		o.duelRule[i] = player.duelRule[i];
		o.getPlayerAssistant().sendFrame87(286, o.duelOption);

		if (player.duelRule[8]) {
			if (player.duelRule[1]) {
				player.duelTeleX = 3366 + Misc.random(12);
				o.duelTeleX = player.duelTeleX - 1;
				player.duelTeleY = 3246 + Misc.random(6);
				o.duelTeleY = player.duelTeleY;
			}
		} else {
			if (player.duelRule[1]) {
				player.duelTeleX = 3335 + Misc.random(12);
				o.duelTeleX = player.duelTeleX - 1;
				player.duelTeleY = 3246 + Misc.random(6);
				o.duelTeleY = player.duelTeleY;
			}
		}
	}
}
