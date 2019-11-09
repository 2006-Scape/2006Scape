package redone.game.shops;

import redone.Constants;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.items.ItemDefinitions;
import redone.game.players.Client;
import redone.game.players.Player;
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
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {	//adds items in store when items are sold until max value.
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;  //sets the stack of item sold to max value if the resulting amount is higher than max value.
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

	public double getItemShopValue(int ItemID, int Type, boolean isSelling) {
		double ShopValue = 1;
		double TotPrice = 0;
		double sellingRatio = isSelling ? 0.85 : 1;
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			if (ItemDefinitions.getDef()[i] != null) {
				ShopValue = ItemDefinitions.getDef()[ItemID].highAlch/3.0 *5.0 * sellingRatio;
				ShopValue = ShopValue <= 0 ? 1 : ShopValue; //Don't let the value be 0
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
		return (int) Math.round(TotPrice);
	}

	public int getItemShopValue(int itemId) {
		return (int) ItemDefinitions.getDef()[itemId].highAlch/3 *5;
	}


	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0, false));
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
			int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1, true));
			int tokkulValue = (int) Math.floor(getTokkulValue(removeId) *.85);
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			if (player.myShopId != RANGE_SHOP && player.myShopId != PEST_SHOP && player.myShopId != CASTLE_SHOP && player.myShopId != 138 && player.myShopId != 58 && player.myShopId != 139) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + ShopValue + " coins." + ShopAdd);
			} else if (player.myShopId == 138 || player.myShopId == 139 || player.myShopId == 58) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": shop will buy for " + tokkulValue + " tokkul.");
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
		player.getItemAssistant();
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
			String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
			int TotPrice2 = 0;
			if (player.myShopId == 138 || player.myShopId == 58 || player.myShopId == 139) {
				TotPrice2 = (int) (getTokkulValue(itemID) * .85);
			} else {
				 TotPrice2 = (int) Math.floor(getItemShopValue(itemID, amount, true) * amount); //Something about total price of item?
			}
			if (player.getItemAssistant().freeSlots() > 0 || player.getItemAssistant().playerHasItem(995) || player.getItemAssistant().playerHasItem(6529)) { //Checks to see if player has room for coins.
				if (!ItemDefinitions.getDef()[itemID].isNoteable) { //Check to see if its notable.
					player.getItemAssistant().deleteItem2(itemID, amount);
				} else {
					player.getItemAssistant().deleteItem2(itemID, amount);
					String ItemNameUnNotedItem = ItemAssistant.getItemName(itemID - 1).toLowerCase();
					if (itemName.contains(ItemNameUnNotedItem)) {
						itemID = itemID - 1; //Replace the noted item by it's un-noted version.
					}
				}
				if (player.myShopId == 138 || player.myShopId == 139 || player.myShopId == 58) {
					player.getItemAssistant().addItem(6529, TotPrice2); //Add the tokkul to your inventory.
				} else {	
					player.getItemAssistant().addItem(995, TotPrice2); //Add the coins to your inventory.
				}
				addShopItem(itemID, amount); //Add item to the shop.
				if (player.getPlayerAssistant().isPlayer()) { //Logger
					GameLogger.writeLog(player.playerName, "shopselling", player.playerName + " sold " + itemName + " to store id: " + player.myShopId + " for" + GameLogger.formatCurrency(TotPrice2) + " coins");
				}
			} else {
				player.getActionSender().sendMessage("You don't have enough space in your inventory.");
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.myShopId);
			updatePlayerShop();
			player.getActionSender().sendMessage("You sold " + amount + " " +itemName + " for " + TotPrice2 + " coins." );
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
		int iValue = 0;
		int boughtQty = 0;
		boolean boughtItem = false;
		if (amount > 0) {
			//S4
			if (ShopHandler.ShopItemsN[player.myShopId][fromSlot] == 0) {
				player.getActionSender().sendMessage("You can't buy that right now!");
				return false;
			}
			if (amount > ShopHandler.ShopItemsN[player.myShopId][fromSlot] && ShopHandler.ShopItemsN[player.myShopId][fromSlot] > 0) {
				amount = ShopHandler.ShopItemsN[player.myShopId][fromSlot];
			}

			if (amount % 23 == 0) {
				amount = amount / 23;
				iValue = 23; }
			else if (amount % 19 == 0) {
				amount = amount / 19;
				iValue = 19;
			} else if (amount % 17 == 0) {
				amount = amount / 17;
				iValue = 17;
			} else if (amount % 13 == 0) {
				amount = amount / 13;
				iValue = 13;
			} else if (amount % 11 == 0) {
				amount = amount / 11;
				iValue = 11;
			} else if (amount % 7 == 0) {
				amount = amount / 7;
				iValue = 7;
			} else if (amount % 5 == 0) {
				amount = amount / 5;
				iValue = 5;
			}
			else if (amount % 3 == 0) {
				amount = amount / 3;
				iValue = 3;
			} else if (amount % 2 == 0) {
				amount = amount / 2;
				iValue = 2;
			} else{
				iValue = 1;
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
			int TotPrice2 = 0;	//ShopPrice
			int RemainingToBuy; //Remaining of item to buy to fill the order. It's the remaining that can't fit in the loop. It has to be processed by itself after the loop.
			int Slot = 0; //gp (995)
			int tokkulSlot = 0;
			int rangeSlot = 0;
			int castleSlot = 0;
			for (int i = amount; iValue > 0; iValue--) {
				if (player.myShopId != 138 && player.myShopId != 58 && player.myShopId != 139 && player.myShopId != RANGE_SHOP && player.myShopId != PEST_SHOP && player.myShopId != CASTLE_SHOP) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0, false));
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
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0, false));
					TotPrice2 *= 1.66;
				}
				
				String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
				if (player.getPlayerAssistant().isPlayer()) {
					GameLogger.writeLog(player.playerName, "shopbuying", player.playerName + " bought " + itemName + " from store id: " + player.myShopId + " for" + GameLogger.formatCurrency(TotPrice2) + " coins");
				}

				// TzHaar Shops
				if (player.myShopId == 138 || player.myShopId == 139 || player.myShopId == 58) {
					if (player.playerItemsN[tokkulSlot] >= TotPrice2) {
						if (player.getItemAssistant().freeSlots() > 0 || (player.getItemAssistant().playerHasItem(itemID) && ItemDefinitions.getDef()[itemID].isStackable)) {
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
						if (player.getItemAssistant().freeSlots() > 0 || (player.getItemAssistant().playerHasItem(itemID) && ItemDefinitions.getDef()[itemID].isStackable)) {
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
						if (player.getItemAssistant().freeSlots() > 0 || (player.getItemAssistant().playerHasItem(itemID) && ItemDefinitions.getDef()[itemID].isStackable)) {
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
					if (player.playerItemsN[Slot] >= TotPrice2 * amount) {
						if (player.getItemAssistant().freeSlots() >= amount || (player.getItemAssistant().playerHasItem(itemID) && ItemDefinitions.getDef()[itemID].isStackable) || player.getItemAssistant().freeSlots() >= 1 && ItemDefinitions.getDef()[itemID].isStackable) {
							player.getItemAssistant().deleteItem(995,
									player.getItemAssistant().getItemSlot(995),
									TotPrice2 * amount);
							player.getItemAssistant().addItem(itemID, amount);			//All of these actions are performed in a loop. We are in the loop right now.
							boughtQty+=amount;
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= amount; //Delete X item from shop at the slot the item is.
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0; //Shit ass delay
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = itemID + 1;
							}
						} else {
							if (player.getItemAssistant().freeSlots() == 0) {
								player.getActionSender().sendMessage(
												"You don't have enough space in your inventory.");
							} else {
								//Buys the remaining item to fill the inventory slots.
								RemainingToBuy = player.getItemAssistant().freeSlots();
								amount = RemainingToBuy;
								player.getItemAssistant().deleteItem(995,
										player.getItemAssistant().getItemSlot(995),
										TotPrice2 * amount);
								player.getItemAssistant().addItem(itemID, amount);
								boughtQty+=amount;
								ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= amount;
								ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
								if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
									ShopHandler.ShopItems[player.myShopId][fromSlot] = itemID + 1;
								}
							}
							break;
						}
						boughtItem = true;
					} else {
						if (player.playerItemsN[Slot] / TotPrice2 > 0) {
							amount = (int)Math.floor(player.playerItemsN[Slot] / TotPrice2);
						} else {
							player.getActionSender().sendMessage("You don't have enough coins.");
							player.getItemAssistant().resetItems(3823);
							resetShop(player.myShopId);
							updatePlayerShop();
							return false;
						}
						if (player.getItemAssistant().freeSlots() >= amount || (player.getItemAssistant().playerHasItem(itemID) && ItemDefinitions.getDef()[itemID].isStackable) || player.getItemAssistant().freeSlots() >= 1 && ItemDefinitions.getDef()[itemID].isStackable) {
							player.getItemAssistant().deleteItem(995,
									player.getItemAssistant().getItemSlot(995),
									TotPrice2 * amount);
							player.getItemAssistant().addItem(itemID, amount);			//All of these actions are performed in a loop. We are in the loop right now.
							boughtQty+=amount;
							ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= amount; //Delete X item from shop at the slot the item is.
							ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0; //Shit ass delay
							if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
								ShopHandler.ShopItems[player.myShopId][fromSlot] = itemID + 1;
							}
						} else {
							if (player.getItemAssistant().freeSlots() == 0) {
								player.getActionSender().sendMessage(
										"You don't have enough space in your inventory.");
							} else {
								//Buys the remaining item to fill the inventory slots.
								RemainingToBuy = player.getItemAssistant().freeSlots();
								amount = RemainingToBuy;
								player.getItemAssistant().deleteItem(995,
										player.getItemAssistant().getItemSlot(995),
										TotPrice2 * amount);
								player.getItemAssistant().addItem(itemID, amount);
								boughtQty+=amount;
								ShopHandler.ShopItemsN[player.myShopId][fromSlot] -= amount;
								ShopHandler.ShopItemsDelay[player.myShopId][fromSlot] = 0;
								if (fromSlot + 1 > ShopHandler.ShopItemsStandard[player.myShopId]) {
									ShopHandler.ShopItems[player.myShopId][fromSlot] = itemID + 1;
								}
							}
							break;
						}
						boughtItem = true;
					}
				}
			}
			if (boughtItem) {
				player.getActionSender().sendMessage("You bought " + boughtQty + " " + ItemAssistant.getItemName(itemID).toLowerCase() + " for " + TotPrice2 * boughtQty + " coins." );
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.myShopId);
			updatePlayerShop();
			return true; //return TRUE / FALSE Update = shop&Inventory / Doesnt Update
		}
		return false;
	}
}
