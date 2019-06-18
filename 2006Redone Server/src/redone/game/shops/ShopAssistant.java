package redone.game.shops;

import redone.Constants;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.items.ItemDefinitions;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.GameLogger;


/**
 * Many Fixes/Things Added
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class ShopAssistant {

	private final Client player;

	public ShopAssistant(Client client) {
		player = client;
	}
	
	public static final int RANGE_SHOP = 111, PEST_SHOP = 175, CASTLE_SHOP = 112;

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if (itemID == ShopHandler.ShopItems[player.myShopId][i] - 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Shops
	 **/

	public void openShop(int ShopID) {
		player.getActionSender().sendSound(1465, 100, 0);
		player.getItemAssistant().resetItems(3823);
		resetShop(ShopID);
		player.isShopping = true;
		player.myShopId = ShopID;
		player.getPlayerAssistant().sendFrame248(3824, 3822);
		player.getPlayerAssistant().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}

	public void updatePlayerShop() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true
						&& PlayerHandler.players[i].myShopId == player.myShopId
						&& i != player.playerId) {
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		synchronized (player) {
			int TotalItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(3900);
			player.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0
						|| i <= ShopHandler.ShopItemsStandard[ShopID]) {
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						player.getOutStream().writeByte(255);
						player.getOutStream().writeDWord_v2(
								ShopHandler.ShopItemsN[ShopID][i]);
					} else {
						player.getOutStream().writeByte(
								ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Constants.ITEM_LIMIT
							|| ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Constants.ITEM_LIMIT;
					}
					player.getOutStream().writeWordBigEndianA(
							ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (ItemDefinitions.getDef()[i] != null) {
				ShopValue = (int) ItemDefinitions.getDef()[ItemID].highAlch/3 *5;
				//ShopValue = (int) ItemDefinitions.getDef()[ItemID].shopValue;
			}
		}

		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[player.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public int getItemShopValue(int itemId) {
		return (int) ItemDefinitions.getDef()[itemId].highAlch/3 *5;
	}


	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0, removeSlot));
		int SpecialValue = getTokkulValue(removeId);
		String ShopAdd = "";
		if (player.myShopId == 138 || player.myShopId == 139 || player.myShopId == 58) {
			player.getActionSender().sendMessage(
					ItemAssistant.getItemName(removeId) + ": currently costs " + SpecialValue + " tokkul.");
			return;
		}
		if (player.myShopId == PEST_SHOP) {
			player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId)+": currently costs " + getPestItemValue(removeId) + " pest control points.");
			return;
		}
		if (player.myShopId == CASTLE_SHOP) {
			player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId)+": currently costs " + getCastleItemValue(removeId) + " castle wars tickets.");
			return;
		}
		if (player.myShopId == RANGE_SHOP) {
			player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId)+": currently costs " + getRGItemValue(removeId) + " archery tickets.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + ShopValue / 1000 + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + ShopValue / 1000000 + " million)";
		}
		player.getActionSender().sendMessage(
				ItemAssistant.getItemName(removeId) + ": currently costs "
						+ ShopValue + " coins" + ShopAdd);
	}
	
	public int getCastleItemValue(int id) {
		switch (id) {
		/*Red Items*/
		case 4071:
			return 4;
		case 4069:
			return 8;
		case 4070:
		case 4072:
			return 6;
		case 4068:
			return 5;
		/*Silver/Blue Items*/
		case 4506:
			return 40;
		case 4504:
			return 80;
		case 4505:
		case 4507:
			return 60;
		case 4503:
			return 50;
		/*Gold/Blue Items*/
		case 4511:
			return 400;
		case 4509:
			return 800;
		case 4510:
		case 4512:
			return 600;
		case 4508:
			return 500;
		/*Capes & Hoods*/
		case 4513:
		case 4514:
		case 4515:
		case 4516:
			return 10;
		}
		return 0;
	}
	
	
	public int getPestItemValue(int id) {
		switch (id) {
		}
		return 0;
	}
	
	public int getRGItemValue(int id) {
		switch (id) {
		case 47:
			return 4;
		case 1133:
			return 51;
		case 1135:
			return 2400;
		case 829:
			return 15;
		case 1169:
			return 100;
		case 892:
			return 40;
		}
		return 0;
	}
	

	public int getTokkulValue(int id) {
		switch (id) {
		case 438:
		case 436:
			return 4;
		case 453:
			return 25;
		case 1623:
			return 37;
		case 1621:
			return 75;
		case 6571:
			return 300000;
		case 554:
		case 555:
		case 556:
		case 557:
			return 6;
		case 558:
		case 559:
			return 4;
		case 562:
			return 135;
		case 560:
			return 270;
		case 6522:
			return 375;
		case 6523:
			return 60000;
		case 6524:
			return 67500;
		case 6525:
			return 37500;
		case 6526:
			return 52500;
		case 6527:
			return 45000;
		case 6528:
			return 75000;
		case 6568:
			return 90000;
		}
		return 0;
	}
	
	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Constants.ITEM_SELLABLE) {
			if (i == removeId) {
				player.getActionSender().sendMessage("You can't sell " + ItemAssistant.getItemName(removeId).toLowerCase() + ".");
				return;
			}
		}
		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[player.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[player.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[player.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			player.getActionSender().sendMessage("You can't sell " + ItemAssistant.getItemName(removeId).toLowerCase() + " to this store.");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1, removeSlot) *.85);
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			if (player.myShopId != RANGE_SHOP && player.myShopId != PEST_SHOP && player.myShopId != CASTLE_SHOP && player.myShopId != 138 && player.myShopId != 58 && player.myShopId != 139) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + ShopValue + " coins." + ShopAdd);
			} else if (player.myShopId == RANGE_SHOP) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + getRGItemValue(removeId) + " archery tickets." + ShopAdd);
			} else if (player.myShopId == PEST_SHOP) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + getPestItemValue(removeId) + " pest control points." + ShopAdd);
			} else if (player.myShopId == CASTLE_SHOP) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + getCastleItemValue(removeId) + " castle war tickets." + ShopAdd);
			}
			player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + ShopValue + " coins." + ShopAdd);
		}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		for (int i : Constants.ITEM_SELLABLE) {
			if (i == itemID) {
				player.getItemAssistant();
				player.getActionSender().sendMessage("You can't sell " + ItemAssistant.getItemName(itemID).toLowerCase() + ".");
				return false;
			}
		}
		if (player.playerRights == 2 && !Constants.ADMIN_CAN_SELL_ITEMS) {
			player.getActionSender().sendMessage("Selling items as an admin has been disabled.");
			return false;
		}
		if(!player.isShopping) {
	        return false;
		}

		if (amount > 0 && itemID == (player.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[player.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[player.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[player.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					player.getItemAssistant();
					player.getActionSender().sendMessage("You can't sell " + ItemAssistant.getItemName(itemID).toLowerCase() + " to this store.");
					return false;
				}
			}

			if (amount > player.playerItemsN[fromSlot] && (ItemDefinitions.getDef()[player.playerItems[fromSlot] - 1].isNoteable == true || ItemDefinitions.getDef()[player.playerItems[fromSlot] - 1].isStackable == true)) {
				amount = player.playerItemsN[fromSlot];
			} else if (amount > player.getItemAssistant().getItemAmount(itemID) && ItemDefinitions.getDef()[player.playerItems[fromSlot] - 1].isNoteable == false && ItemDefinitions.getDef()[player.playerItems[fromSlot] - 1].isStackable == false) {
				amount = player.getItemAssistant().getItemAmount(itemID);
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1, fromSlot) *.85);
				if (player.getItemAssistant().freeSlots() > 0 || player.getItemAssistant().playerHasItem(995)) {
					if (ItemDefinitions.getDef()[itemID].isNoteable == false) {
						player.getItemAssistant().deleteItem(itemID, player.getItemAssistant().getItemSlot(itemID), 1);
					} else {
						player.getItemAssistant().deleteItem(itemID, fromSlot, 1);
					}
					player.getItemAssistant().addItem(995, TotPrice2);
					addShopItem(itemID, 1);
					if (player.getPlayerAssistant().isPlayer()) {
						GameLogger.writeLog(player.playerName, "shopselling", player.playerName + " sold " + itemName + " to store id: " + player.myShopId + " for" + GameLogger.formatCurrency(TotPrice2) + " coins");
					}
				} else {
					player.getActionSender().sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID]) {
			itemID = player.getItemAssistant().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if (ShopHandler.ShopItems[player.myShopId][i] - 1 == itemID) {
				ShopHandler.ShopItemsN[player.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[player.myShopId][i] == 0) {
					ShopHandler.ShopItems[player.myShopId][i] = itemID + 1;
					ShopHandler.ShopItemsN[player.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[player.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}
	
	private static final int FISHING_ITEMS[] = {383, 371, 377, 359, 321, 341, 353, 345, 327, 317};

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if (amount > 0) {
			if (amount > ShopHandler.ShopItemsN[player.myShopId][fromSlot]) {
				amount = ShopHandler.ShopItemsN[player.myShopId][fromSlot];
			}
			if(!player.isShopping) {
		        return false;
			}
			if (ShopHandler.ShopItems[player.myShopId][fromSlot] - 1 != itemID || ShopHandler.ShopItems[player.myShopId][fromSlot] < 0) {
				return false;
			}
			for (int i = 0; i < FISHING_ITEMS.length; i++) {
				if (player.myShopId == 32 && itemID == FISHING_ITEMS[i]) {
					player.getActionSender().sendMessage("You can't buy that item from this store!");
					return false;
				}		
			}
			if (!shopSellsItem(itemID)) {
				return false;
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			int Slot = 0;
			int tokkulSlot = 0;// Tokkul
			int rangeSlot = 0;
			int castleSlot = 0;
			for (int i = amount; i > 0; i--) {
				if (player.myShopId != 138 && player.myShopId != 58 && player.myShopId != 139 && player.myShopId != RANGE_SHOP && player.myShopId != PEST_SHOP && player.myShopId != CASTLE_SHOP) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0, fromSlot));
				} else if (player.myShopId == 138 || player.myShopId == 58 || player.myShopId == 139) {
					TotPrice2 = getTokkulValue(itemID);	
				} else if (player.myShopId == RANGE_SHOP) {
					TotPrice2 = getRGItemValue(itemID);
				} else if (player.myShopId == PEST_SHOP) {
					TotPrice2 = getPestItemValue(itemID);
				} else if (player.myShopId == CASTLE_SHOP) {
					TotPrice2 = getCastleItemValue(itemID);
				}
				Slot = player.getItemAssistant().getItemSlot(995);
				tokkulSlot = player.getItemAssistant().getItemSlot(6529);
				rangeSlot = player.getItemAssistant().getItemSlot(1464);
				castleSlot = player.getItemAssistant().getItemSlot(4067);
				if (Slot == -1) {
					if (player.myShopId != 138 && player.myShopId != 139 && player.myShopId != 58 && player.myShopId != RANGE_SHOP && player.myShopId != CASTLE_SHOP && player.myShopId != PEST_SHOP) {
						player.getActionSender().sendMessage("You don't have enough coins.");
						break;
					}
				}
				if (rangeSlot == -1) {
					if (player.myShopId == RANGE_SHOP) {
						player.getActionSender().sendMessage("You don't have enough archery tickets to buy that.");
						break;
					}
				}
				if (castleSlot == -1) {
					if (player.myShopId == CASTLE_SHOP) {
						player.getActionSender().sendMessage("You don't have enough castle wars tickets to buy that.");
						break;
					}
				}
				if (tokkulSlot == -1) {
					if (player.myShopId == 138 || player.myShopId == 58 || player.myShopId == 139) {
						player.getActionSender().sendMessage("You don't have enough tokkul to buy that.");
						break;
					}
				}

				if (TotPrice2 <= 1) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0, fromSlot));
					TotPrice2 *= 1.66;
				}
				
				String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
				if (player.getPlayerAssistant().isPlayer()) {
					GameLogger.writeLog(player.playerName, "shopbuying", player.playerName + " bought " + itemName + " from store id: " + player.myShopId + " for" + GameLogger.formatCurrency(TotPrice2) + " coins");
				}

				// TzHaar Shops
				if (player.myShopId == 138 || player.myShopId == 139 || player.myShopId == 58) {
					if (player.playerItemsN[tokkulSlot] >= TotPrice2) {
						if (player.getItemAssistant().freeSlots() > 0) {
							player.getItemAssistant().deleteItem(6529, tokkulSlot, TotPrice2);
							player.getItemAssistant().addItem(itemID, 1);
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
							ShopHandler.ShopItemsRestock[player.myShopId][fromSlot] = System.currentTimeMillis();
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = 0;
							}
						} else {
							player.getActionSender()
									.sendMessage(
											"You don't have enough space in your inventory.");
							break;
						}
					} else {
						player.getActionSender().sendMessage(
								"You don't have enough tokkul.");
						break;
					}
				} else if (player.myShopId == RANGE_SHOP) {
					if (player.playerItemsN[rangeSlot] >= TotPrice2) {
						if (player.getItemAssistant().freeSlots() > 0) {
							player.getItemAssistant().deleteItem(1464, rangeSlot, TotPrice2);
							player.getItemAssistant().addItem(itemID, 1);
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = 0;
							}
						} else {
							player.getActionSender()
									.sendMessage(
											"You don't have enough space in your inventory.");
							break;
						}
					} else {
						player.getActionSender().sendMessage(
								"You don't have enough archery tickets.");
						break;
					}
				} else if (player.myShopId == CASTLE_SHOP) {
					if (player.playerItemsN[castleSlot] >= TotPrice2) {
						if (player.getItemAssistant().freeSlots() > 0) {
							player.getItemAssistant().deleteItem(4067, castleSlot, TotPrice2);
							player.getItemAssistant().addItem(itemID, 1);
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = 0;
							}
						} else {
							player.getActionSender()
									.sendMessage(
											"You don't have enough space in your inventory.");
							break;
						}
					}
				} else {
					if (player.playerItemsN[Slot] >= TotPrice2) {
						if (player.getItemAssistant().freeSlots() > 0) {
							player.getItemAssistant().deleteItem(995,
									player.getItemAssistant().getItemSlot(995),
									TotPrice2);
							player.getItemAssistant().addItem(itemID, 1);
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = 0;
							}
						} else {
							player.getActionSender()
									.sendMessage(
											"You don't have enough space in your inventory.");
							break;
						}
					} else {
						player.getActionSender().sendMessage(
								"You don't have enough coins.");
						break;
					}
				}
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}
}
