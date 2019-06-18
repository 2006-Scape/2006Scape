package redone.game.players;

import java.util.concurrent.CopyOnWriteArrayList;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.items.GameItem;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.items.impl.RareProtection;
import redone.util.GameLogger;
import redone.util.Misc;

public class Trading {

	private final Client player;

	public Trading(Client Client) {
		player = Client;
	}

	/**
	 * Trading
	 **/

	public CopyOnWriteArrayList<GameItem> offeredItems = new CopyOnWriteArrayList<GameItem>();

	public void requestTrade(int id) {
		try {
			Client o = (Client) PlayerHandler.players[id];
			if (id == player.playerId) {
				return;
			}
			player.tradeWith = id;
			if (player.isBotting) {
				player.getActionSender().sendMessage("You can't trade items, until you confirm you aren't botting.");
				player.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
				return;
			}
			/*if (c.connectedFrom.equals(o.connectedFrom)) {
				c.getActionSender().sendMessage("You cannot trade your own IP.");
				return;
			}*/
			if (!CastleWars.deleteCastleWarsItems(player, id)) {
				return;
			}
			if (!player.inTrade && o.tradeRequested && o.tradeWith == player.playerId && player.playerIsBusy() == false && o.playerIsBusy() == false) {
				player.getTrading().openTrade();
				o.getTrading().openTrade();
			} else if (!player.inTrade && player.playerIsBusy() == false && o.playerIsBusy() == false) {

				player.tradeRequested = true;
				player.getActionSender().sendMessage("Sending trade request...");
				o.getActionSender()
						.sendMessage(player.playerName + ":tradereq:");
			} else if (player.playerIsBusy() == false && o.playerIsBusy() == true) {
				player.getActionSender().sendMessage("Other player is busy at the moment.");
			}
		} catch (Exception e) {
			Misc.println("Error requesting trade.");
		}
	}

	public void openTrade() {
		Client o = (Client) PlayerHandler.players[player.tradeWith];

		if (o == null) {
			return;
		}
		player.inTrade = true;
		player.tradeStatus = 1;
		player.tradeRequested = false;
		player.getItemAssistant().resetItems(3322);
		resetTItems(3415);
		resetOTItems(3416);
		String out = o.playerName;

		if (o.playerRights == 1) {
			out = "@cr1@" + out;
		} else if (o.playerRights == 2) {
			out = "@cr2@" + out;
		}
		player.getPlayerAssistant().sendFrame126(
				"Trading with: " + o.playerName + " who has @gre@"
						+ o.getItemAssistant().freeSlots() + " free slots",
				3417);
		player.getPlayerAssistant().sendFrame126("", 3431);
		player.getPlayerAssistant().sendFrame126(
				"Are you sure you want to make this trade?", 3535);
		player.getPlayerAssistant().sendFrame248(3323, 3321);
	}

	public void resetTItems(int WriteFrame) {
		synchronized (player) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			int len = offeredItems.toArray().length;
			int current = 0;
			player.getOutStream().writeWord(len);
			for (GameItem item : offeredItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255);
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
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

	public boolean fromTrade(int itemID, int fromSlot, int amount) {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return false;
		}
		try {
			if (!player.inTrade) {
				declineTrade();
				return false;
			}
			player.tradeConfirmed = false;
			o.tradeConfirmed = false;
			if (!Item.itemStackable[itemID]) {
				for (int a = 0; a < amount; a++) {
					for (GameItem item : offeredItems) {
						if (item.id == itemID) {
							if (!item.stackable) {
								offeredItems.remove(item);
								player.getItemAssistant().addItem(itemID, 1);
								o.getPlayerAssistant().sendFrame126(
										"Trading with: "
												+ player.playerName
												+ " who has @gre@"
												+ player.getItemAssistant()
														.freeSlots()
												+ " free slots", 3417);
							} else {
								if (item.amount > amount) {
									item.amount -= amount;
									player.getItemAssistant()
											.addItem(itemID, amount);
									o.getPlayerAssistant().sendFrame126(
											"Trading with: "
													+ player.playerName
													+ " who has @gre@"
													+ player.getItemAssistant()
															.freeSlots()
													+ " free slots", 3417);
								} else {
									amount = item.amount;
									offeredItems.remove(item);
									player.getItemAssistant()
											.addItem(itemID, amount);
									o.getPlayerAssistant().sendFrame126(
											"Trading with: "
													+ player.playerName
													+ " who has @gre@"
													+ player.getItemAssistant()
															.freeSlots()
													+ " free slots", 3417);
								}
							}
							break;
						}
						o.getPlayerAssistant().sendFrame126(
								"Trading with: " + player.playerName
										+ " who has @gre@"
										+ player.getItemAssistant().freeSlots()
										+ " free slots", 3417);
						player.tradeConfirmed = false;
						o.tradeConfirmed = false;
						player.getItemAssistant().resetItems(3322);
						resetTItems(3415);
						o.getTrading().resetOTItems(3416);
						player.getPlayerAssistant().sendFrame126("", 3431);
						o.getPlayerAssistant().sendFrame126("", 3431);
					}
				}
			}
			for (GameItem item : offeredItems) {
				if (item.id == itemID) {
					if (!item.stackable) {
					} else {
						if (item.amount > amount) {
							item.amount -= amount;
							player.getItemAssistant().addItem(itemID, amount);
							o.getPlayerAssistant().sendFrame126(
									"Trading with: " + player.playerName
											+ " who has @gre@"
											+ player.getItemAssistant().freeSlots()
											+ " free slots", 3417);
						} else {
							amount = item.amount;
							offeredItems.remove(item);
							player.getItemAssistant().addItem(itemID, amount);
							o.getPlayerAssistant().sendFrame126(
									"Trading with: " + player.playerName
											+ " who has @gre@"
											+ player.getItemAssistant().freeSlots()
											+ " free slots", 3417);
						}
					}
					break;
				}
			}

			o.getPlayerAssistant().sendFrame126(
					"Trading with: " + player.playerName + " who has @gre@"
							+ player.getItemAssistant().freeSlots() + " free slots",
					3417);
			player.tradeConfirmed = false;
			o.tradeConfirmed = false;
			player.getItemAssistant().resetItems(3322);
			resetTItems(3415);
			o.getTrading().resetOTItems(3416);
			player.getPlayerAssistant().sendFrame126("", 3431);
			o.getPlayerAssistant().sendFrame126("", 3431);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean tradeItem(int itemID, int fromSlot, int amount) {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return false;
		}
		if (!(player.playerItems[fromSlot] == itemID + 1 && player.playerItemsN[fromSlot] >= amount)) {
			player.getActionSender().sendMessage("You don't have that amount!");
			return false;
		}
		
		if (amount >= 1000000000) {
			player.getActionSender().sendMessage("You can't possibly have that much of that item!");
			player.getItemAssistant().deleteItem(itemID, fromSlot, amount);
			return false;
		}

		for (int i : Constants.ITEM_TRADEABLE) {
			if (i == itemID && player.playerRights < 3) {
				player.getActionSender().sendMessage(
						"You can't trade this item.");
				return false;
			}
		}
		if (!RareProtection.doOtherDupe(player, itemID)) {
			return false;
		}
		// /if (!((c.playerItems[fromSlot] == itemID + 1) &&
		// (c.playerItemsN[fromSlot] >= amount))) {
		// c.getPacketDispatcher().sendMessage("You don't have that amount!");
		// return false;
		// }
		player.tradeConfirmed = false;
		o.tradeConfirmed = false;
		if (!Item.itemStackable[itemID] && !Item.itemIsNote[itemID]) {
			for (int a = 0; a < amount && a < 28; a++) {
				if (player.getItemAssistant().playerHasItem(itemID, 1)) {
					offeredItems.add(new GameItem(itemID, 1));
					player.getItemAssistant().deleteItem(itemID,
							player.getItemAssistant().getItemSlot(itemID), 1);
					o.getPlayerAssistant().sendFrame126(
							"Trading with: " + player.playerName + " who has @gre@"
									+ player.getItemAssistant().freeSlots()
									+ " free slots", 3417);
				}
			}
			o.getPlayerAssistant().sendFrame126(
					"Trading with: " + player.playerName + " who has @gre@"
							+ player.getItemAssistant().freeSlots() + " free slots",
					3417);
			player.getItemAssistant().resetItems(3322);
			resetTItems(3415);
			o.getTrading().resetOTItems(3416);
			player.getPlayerAssistant().sendFrame126("", 3431);
			o.getPlayerAssistant().sendFrame126("", 3431);
		}
		if (player.getItemAssistant().getItemCount(itemID) < amount) {
			amount = player.getItemAssistant().getItemCount(itemID);
			if (amount == 0) {
				return false;
			}
		}
		if (!player.inTrade) {
			declineTrade();
			return false;
		}

		if (Item.itemStackable[itemID] || Item.itemIsNote[itemID]) {
			boolean inTrade = false;
			for (GameItem item : offeredItems) {
				if (item.id == itemID) {
					inTrade = true;
					item.amount += amount;
					player.getItemAssistant().deleteItem2(itemID, amount);
					o.getPlayerAssistant().sendFrame126(
							"Trading with: " + player.playerName + " who has @gre@"
									+ player.getItemAssistant().freeSlots()
									+ " free slots", 3417);
					break;
				}
			}

			if (!inTrade) {
				offeredItems.add(new GameItem(itemID, amount));
				player.getItemAssistant().deleteItem2(itemID, amount);
				o.getPlayerAssistant().sendFrame126(
						"Trading with: " + player.playerName + " who has @gre@"
								+ player.getItemAssistant().freeSlots()
								+ " free slots", 3417);
			}
		}
		o.getPlayerAssistant().sendFrame126(
				"Trading with: " + player.playerName + " who has @gre@"
						+ player.getItemAssistant().freeSlots() + " free slots",
				3417);
		player.getItemAssistant().resetItems(3322);
		resetTItems(3415);
		o.getTrading().resetOTItems(3416);
		player.getPlayerAssistant().sendFrame126("", 3431);
		o.getPlayerAssistant().sendFrame126("", 3431);
		return true;
	}

	public void resetTrade() {
		offeredItems.clear();
		player.inTrade = false;
		player.tradeWith = 0;
		player.tradeConfirmed = false;
		player.tradeConfirmed2 = false;
		player.acceptedTrade = false;
		player.getPlayerAssistant().removeAllWindows();
		player.tradeResetNeeded = false;
		player.getPlayerAssistant().sendFrame126(
				"Are you sure you want to make this trade?", 3535);
	}

	public void declineTrade() {
		player.tradeStatus = 0;
		declineTrade(true);
	}

	public void declineTrade(boolean tellOther) {
		player.getPlayerAssistant().removeAllWindows();
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return;
		}

		if (tellOther) {
			o.getTrading().declineTrade(false);
			o.getTrading().player.getPlayerAssistant().removeAllWindows();
		}

		for (GameItem item : offeredItems) {
			if (item.amount < 1) {
				continue;
			}
			if (item.stackable) {
				player.getItemAssistant().addItem(item.id, item.amount);
			} else {
				for (int i = 0; i < item.amount; i++) {
					player.getItemAssistant().addItem(item.id, 1);
				}
			}
		}
		player.tradeConfirmed = false;
		player.tradeConfirmed2 = false;
		offeredItems.clear();
		player.inTrade = false;
		player.tradeWith = 0;
	}

	public void resetOTItems(int WriteFrame) {
		synchronized (player) {
			Client o = (Client) PlayerHandler.players[player.tradeWith];
			if (o == null) {
				return;
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(WriteFrame);
			int len = o.getTrading().offeredItems.toArray().length;
			int current = 0;
			player.getOutStream().writeWord(len);
			for (GameItem item : o.getTrading().offeredItems) {
				if (item.amount > 254) {
					player.getOutStream().writeByte(255); // item's stack count. if
														// over 254, write byte
														// 255
					player.getOutStream().writeDWord_v2(item.amount);
				} else {
					player.getOutStream().writeByte(item.amount);
				}
				player.getOutStream().writeWordBigEndianA(item.id + 1); // item id
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

	public void confirmScreen() {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return;
		}
		if(!player.inTrade) {
            declineTrade();
            return;
        }
		player.getItemAssistant().resetItems(3214);
		String SendTrade = "Absolutely nothing!";
		String SendAmount = "";
		int Count = 0;
		for (GameItem item : offeredItems) {
			if (item.id > 0) {
				if (item.amount >= 1000 && item.amount < 1000000) {
					SendAmount = "@cya@" + item.amount / 1000 + "K @whi@("
							+ Misc.format(item.amount) + ")";
				} else if (item.amount >= 1000000) {
					SendAmount = "@gre@" + item.amount / 1000000
							+ " million @whi@(" + Misc.format(item.amount)
							+ ")";
				} else {
					SendAmount = "" + Misc.format(item.amount);
				}

				if (Count == 0) {
					SendTrade = ItemAssistant.getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n"
							+ ItemAssistant.getItemName(item.id);
				}

				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}

		player.getPlayerAssistant().sendFrame126(SendTrade, 3557);
		SendTrade = "Absolutely nothing!";
		SendAmount = "";
		Count = 0;

		for (GameItem item : o.getTrading().offeredItems) {
			if (item.id > 0) {
				if (item.amount >= 1000 && item.amount < 1000000) {
					SendAmount = "@cya@" + item.amount / 1000 + "K @whi@("
							+ Misc.format(item.amount) + ")";
				} else if (item.amount >= 1000000) {
					SendAmount = "@gre@" + item.amount / 1000000
							+ " million @whi@(" + Misc.format(item.amount)
							+ ")";
				} else {
					SendAmount = "" + Misc.format(item.amount);
				}

				if (Count == 0) {
					SendTrade = ItemAssistant.getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n"
							+ ItemAssistant.getItemName(item.id);
				}
				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}
		player.getPlayerAssistant().sendFrame126(SendTrade, 3558);
		// TODO: find out what 197 does eee 3213
		player.getPlayerAssistant().sendFrame248(3443, 197);
	}

	public void giveItems() {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return;
		}
		try {
			for (GameItem item : o.getTrading().offeredItems) {
				String itemName = ItemAssistant.getItemName(item.id);
				if (item.id > 0) {
					player.getItemAssistant().addItem(item.id, item.amount);
					if (player.getPlayerAssistant().isPlayer()) {
						GameLogger.writeLog(o.playerName, "tradesgave", o.playerName + " traded " + player.playerName + " and gave " + item.amount + " " + itemName + "");
					}
				}
				if (player.getPlayerAssistant().isPlayer()) {
					GameLogger.writeLog(player.playerName, "tradesrecieved", player.playerName + " was traded by " + o.playerName + " and recieved " + item.amount + " " + itemName + "");
				}
			}
			player.getPlayerAssistant().removeAllWindows();
			player.tradeResetNeeded = true;
			   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (player.inTrade && player.tradeResetNeeded) {
						Client o = (Client) PlayerHandler.players[player.tradeWith];
						if (o != null) {
							if (o.tradeResetNeeded) {
								player.getTrading().resetTrade();
								o.getTrading().resetTrade();
								container.stop();
							} else {
								container.stop();
							}
						} else {
							container.stop();
						}
					} else {
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.tradeResetNeeded = false;
				}
			}, 1);
		} catch (Exception e) {
		}
	}

}
