package com.rs2.game.players;

import java.time.temporal.ValueRange;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apollo.cache.def.ItemDefinition;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.GameItem;
import com.rs2.game.items.ItemData;
import com.rs2.game.items.ItemConstants;
import com.rs2.util.GameLogger;
import com.rs2.util.Misc;

public class Trading {

	private final Player player;

	public Trading(Player player2) {
		player = player2;
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

			// player owned shop
			if (o.isBot && o.shopId >= 0){
				if (isCloseTo(o)) {
					player.getShopAssistant().openShop(o.shopId);
				} else {
					player.getPacketSender().sendMessage("Player is not close enough. Retry when you are closer...");
				}
				return;
			}

			player.tradeWith = id;

			if (!CastleWars.deleteCastleWarsItems(player, id)) {
				return;
			}

			if (!player.inTrade && o.tradeRequested && o.tradeWith == player.playerId && !player.playerIsBusy() && !o.playerIsBusy() ) { //start trading process
				if (!isCloseTo(o)) {
					player.getPacketSender().sendMessage("Player is not close enough. Retry when you are closer...");
				} else {
					player.getTrading().openTrade();
					o.getTrading().openTrade();
				}
			} else if (!player.inTrade && !player.playerIsBusy() && !o.playerIsBusy()) { //send trade request
				//Problem = sends the request then walk to player. Solution= change processing order. Fix= Send message when trying to open the trade interface if the other player isn't closer than 3 tiles.
				player.tradeRequested = true;
				player.getPacketSender().sendMessage("Sending trade request...");
				o.getPacketSender()
						.sendMessage(player.playerName + ":tradereq:");
			} else if (player.playerIsBusy()|| o.playerIsBusy()) {
				player.getPacketSender().sendMessage("Other player is busy at the moment.");
			}
		} catch (Exception e) {
			System.out.println("Error requesting trade.");
		}
	}
	public boolean isCloseTo(Client tradedPlayer) {
		ValueRange PlayerCoordRangeX = ValueRange.of(tradedPlayer.absX - 3, tradedPlayer.absX + 3);
		ValueRange PlayerCoordRangeY = ValueRange.of(tradedPlayer.absY - 3, tradedPlayer.absY + 3);
		if (PlayerCoordRangeX.isValidIntValue(player.absX) && PlayerCoordRangeY.isValidIntValue(player.absY)) {
			return true;
		} else {
			return false;
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
		player.getPacketSender().sendString(
				"Trading with: " + o.playerName + " who has @gre@"
						+ o.getItemAssistant().freeSlots() + " free slots",
				3417);
		player.getPacketSender().sendString("", 3431);
		player.getPacketSender().sendString(
				"Are you sure you want to make this trade?", 3535);
		player.getPacketSender().sendFrame248(3323, 3321);
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
			if (!ItemDefinition.lookup(itemID).isStackable()) {
				for (int a = 0; a < amount; a++) {
					for (GameItem item : offeredItems) {
						if (item.id == itemID) {
							if (!item.stackable) {
								offeredItems.remove(item);
								player.getItemAssistant().addItem(itemID, 1);
								o.getPacketSender().sendString(
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
									o.getPacketSender().sendString(
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
									o.getPacketSender().sendString(
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
						o.getPacketSender().sendString(
								"Trading with: " + player.playerName
										+ " who has @gre@"
										+ player.getItemAssistant().freeSlots()
										+ " free slots", 3417);
						player.tradeConfirmed = false;
						o.tradeConfirmed = false;
						player.getItemAssistant().resetItems(3322);
						resetTItems(3415);
						o.getTrading().resetOTItems(3416);
						player.getPacketSender().sendString("", 3431);
						o.getPacketSender().sendString("", 3431);
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
							o.getPacketSender().sendString(
									"Trading with: " + player.playerName
											+ " who has @gre@"
											+ player.getItemAssistant().freeSlots()
											+ " free slots", 3417);
						} else {
							amount = item.amount;
							offeredItems.remove(item);
							player.getItemAssistant().addItem(itemID, amount);
							o.getPacketSender().sendString(
									"Trading with: " + player.playerName
											+ " who has @gre@"
											+ player.getItemAssistant().freeSlots()
											+ " free slots", 3417);
						}
					}
					break;
				}
			}

			o.getPacketSender().sendString(
					"Trading with: " + player.playerName + " who has @gre@"
							+ player.getItemAssistant().freeSlots() + " free slots",
					3417);
			player.tradeConfirmed = false;
			o.tradeConfirmed = false;
			player.getItemAssistant().resetItems(3322);
			resetTItems(3415);
			o.getTrading().resetOTItems(3416);
			player.getPacketSender().sendString("", 3431);
			o.getPacketSender().sendString("", 3431);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean tradeItem(int itemID, int fromSlot, int amount) {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return false;
		}
		if (!(player.playerItems[fromSlot] == itemID + 1 )){//&& player.playerItemsN[fromSlot] >= amount)) { I removed this check to permit trading max amount of item in inventory even when amount is higher than quantity in inventory.
			player.getPacketSender().sendMessage("You don't have that amount!");
			return false;
		}
		
		if (amount >= Integer.MAX_VALUE) {
			player.getPacketSender().sendMessage("You can't possibly have that much of that item!");
			player.getItemAssistant().deleteItem(itemID, fromSlot, amount);
			return false;
		}

		for (int i : ItemConstants.ITEM_TRADEABLE) {
			if (i == itemID && player.playerRights < 3) {
				player.getPacketSender().sendMessage("You can't trade this item.");
				return false;
			}
		}
		player.tradeConfirmed = false;
		o.tradeConfirmed = false;
		if (!ItemDefinition.lookup(itemID).isStackable() && !ItemDefinition.lookup(itemID).isNote()) {
			for (int a = 0; a < amount && a < 28; a++) {
				if (player.getItemAssistant().playerHasItem(itemID, 1)) {
					offeredItems.add(new GameItem(itemID, 1));
					player.getItemAssistant().deleteItem(itemID,
							player.getItemAssistant().getItemSlot(itemID), 1);
					o.getPacketSender().sendString(
							"Trading with: " + player.playerName + " who has @gre@"
									+ player.getItemAssistant().freeSlots()
									+ " free slots", 3417);
				}
			}
			o.getPacketSender().sendString(
					"Trading with: " + player.playerName + " who has @gre@"
							+ player.getItemAssistant().freeSlots() + " free slots",
					3417);
			player.getItemAssistant().resetItems(3322);
			resetTItems(3415);
			o.getTrading().resetOTItems(3416);
			player.getPacketSender().sendString("", 3431);
			o.getPacketSender().sendString("", 3431);
		}
		if (player.getItemAssistant().getItemAmount(itemID) < amount) {
			amount = player.getItemAssistant().getItemAmount(itemID);
			if (amount == 0) {
				return false;
			}
		}
		if (!player.inTrade || !o.inTrade) {
			declineTrade();
			return false;
		}
		if (ItemDefinition.lookup(itemID).isStackable() || ItemDefinition.lookup(itemID).isNote()) {
			boolean inTrade = false;
			for (GameItem item : offeredItems) {
				if (item.id == itemID) {
					inTrade = true;
					item.amount += amount;
					player.getItemAssistant().deleteItem(itemID, amount);
					o.getPacketSender().sendString(
							"Trading with: " + player.playerName + " who has @gre@"
									+ player.getItemAssistant().freeSlots()
									+ " free slots", 3417);
					break;
				}
			}

			if (!inTrade) {
				offeredItems.add(new GameItem(itemID, amount));
				player.getItemAssistant().deleteItem(itemID, amount);
				o.getPacketSender().sendString(
						"Trading with: " + player.playerName + " who has @gre@"
								+ player.getItemAssistant().freeSlots()
								+ " free slots", 3417);
			}
		}
		o.getPacketSender().sendString(
				"Trading with: " + player.playerName + " who has @gre@"
						+ player.getItemAssistant().freeSlots() + " free slots",
				3417);
		player.getItemAssistant().resetItems(3322);
		resetTItems(3415);
		o.getTrading().resetOTItems(3416);
		player.getPacketSender().sendString("", 3431);
		o.getPacketSender().sendString("", 3431);
		return true;
	}

	public void resetTrade() {
		offeredItems.clear();
		player.inTrade = false;
		player.tradeWith = 0;
		player.tradeConfirmed = false;
		player.tradeConfirmed2 = false;
		player.acceptedTrade = false;
		player.getPacketSender().closeAllWindows();
		player.tradeResetNeeded = false;
		player.getPacketSender().sendString("Are you sure you want to make this trade?", 3535);
	}

	public void declineTrade() {
		player.tradeStatus = 0;
		declineTrade(true);
	}

	public void declineTrade(boolean tellOther) {
		player.getPacketSender().closeAllWindows();
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return;
		}

		if (tellOther) {
			o.getTrading().declineTrade(false);
			o.getTrading().player.getPacketSender().closeAllWindows();
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
					player.getOutStream().writeByte(255); // item's stack count. if over 254, write byte 255
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
					SendTrade = DeprecatedItems.getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n"
							+ DeprecatedItems.getItemName(item.id);
				}

				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}

		player.getPacketSender().sendString(SendTrade, 3557);
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
					SendTrade = DeprecatedItems.getItemName(item.id);
				} else {
					SendTrade = SendTrade + "\\n"
							+ DeprecatedItems.getItemName(item.id);
				}
				if (item.stackable) {
					SendTrade = SendTrade + " x " + SendAmount;
				}
				Count++;
			}
		}
		player.getPacketSender().sendString(SendTrade, 3558);
		// TODO: find out what 197 does eee 3213
		player.getPacketSender().sendFrame248(3443, 197);
	}

	public void giveItems() {
		Client o = (Client) PlayerHandler.players[player.tradeWith];
		if (o == null) {
			return;
		}
		try {
			for (GameItem item : o.getTrading().offeredItems) {
				String itemName = DeprecatedItems.getItemName(item.id);
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
			player.getPacketSender().closeAllWindows();
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
