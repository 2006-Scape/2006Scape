package redone.game.shops;

import redone.Constants;
import redone.game.bots.Bot;
import redone.game.bots.BotHandler;
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
			player.TotalShopItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {	//adds items in store when items are sold until max value.
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					player.TotalShopItems++;
				}
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(3900);
			player.getOutStream().writeWord(player.TotalShopItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems[player.myShopId].length; i++)
			{
				if (ShopHandler.ShopItems[ShopID][i] > 0
						|| i <= ShopHandler.ShopItemsStandard[ShopID])
				{
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						player.getOutStream().writeByte(255);
						player.getOutStream().writeDWord_v2(ShopHandler.ShopItemsN[ShopID][i]);
					}
					else
						{
						player.getOutStream().writeByte(ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Constants.ITEM_LIMIT
							|| ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Constants.ITEM_LIMIT;
					}
					player.getOutStream().writeWordBigEndianA(
							ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > player.TotalShopItems) {
					break;
				}
			}
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public int getItemShopValue(int ItemID, int Type, boolean isSelling) {
		double ShopValue = 1;
		double TotPrice = 0;
		double sellingRatio = isSelling ? 0.85 : 1;
		if (ItemDefinitions.getDef()[ItemID] != null) {
			ShopValue = ItemDefinitions.getDef()[ItemID].highAlch / 3.0 * 5.0 * sellingRatio;
		}

		TotPrice = ShopValue;

		// General store pays less for items
		if (isSelling && ShopHandler.ShopBModifier[player.myShopId] == 1) {
			TotPrice *= 0.90;
		}
		// Minimum value of 1
		return (int) Math.max(1, Math.floor(TotPrice));
	}

	public int getItemShopValue(int itemId) {
		return getItemShopValue(itemId, 0, false);
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
		switch (ShopHandler.ShopSModifier[player.myShopId]) {
			// Only buys what is in stock
			case 2:
				for (int j = 0; j <= ShopHandler.ShopItemsStandard[player.myShopId]; j++) {
					if (removeId == (ShopHandler.ShopItems[player.myShopId][j] - 1)) {
						IsIn = true;
						break;
					}
				}
				break;
			// General store
			case 1:
				IsIn = true;
				break;
			// Player owned store
			case 0:
				IsIn = ShopHandler.ShopName[player.myShopId].equalsIgnoreCase(player.properName + "'s Store");
				break;
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
			if (ShopHandler.ShopName[player.myShopId].equalsIgnoreCase(player.properName + "'s Store")) {
				player.getActionSender().sendMessage(ItemAssistant.getItemName(removeId) + ": set your sell price.");
			} else if (player.myShopId != RANGE_SHOP && player.myShopId != PEST_SHOP && player.myShopId != CASTLE_SHOP && player.myShopId != 138 && player.myShopId != 58 && player.myShopId != 139) {
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
		}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		for (int i : Constants.ITEM_SELLABLE) {
			if (i == itemID) {
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
		// We can only store 40 items per shop
		if (player.TotalShopItems >= 40) {
			player.getActionSender().sendMessage("This shop is out of space!");
			return false;
		}
		// Check we have the item in our inventory
		int inventoryAmount = player.getItemAssistant().getItemAmount(itemID);
		if (amount > 0 && inventoryAmount > 0) {
			boolean canSellToStore = false;
			// Type of store
			switch (ShopHandler.ShopSModifier[player.myShopId]) {
				// Only buys what they sell
				case 2:
					for (int j = 0; j <= ShopHandler.ShopItemsStandard[player.myShopId]; j++) {
						if (itemID == (ShopHandler.ShopItems[player.myShopId][j] - 1)) {
							canSellToStore = true;
							break;
						}
					}
					break;
				// General store - buys anything
				case 1:
					canSellToStore = true;
					break;
				// Player owned store - only "buys" from the player whos store it is
				case 0:
					canSellToStore = ShopHandler.ShopName[player.myShopId].equalsIgnoreCase(player.properName + "'s Store");
					break;
			}
			if (canSellToStore == false) {
				player.getItemAssistant();
				player.getActionSender().sendMessage("You can't sell " + ItemAssistant.getItemName(itemID).toLowerCase() + " to this store.");
				return false;
			}
			if (amount > inventoryAmount) {
				amount = inventoryAmount;
			}
			String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
			int value = 1;
			int currency = 995;
			if (player.myShopId == 138 || player.myShopId == 58 || player.myShopId == 139) {
				value = (int) Math.floor(getTokkulValue(itemID) * .85);
				currency = 6529;
			} else {
				value = (int) Math.floor(getItemShopValue(itemID, amount, true));
				currency = 995;
			}

			boolean isStackable = ItemDefinitions.getDef()[itemID].isStackable;

			if (!player.getItemAssistant().playerHasItem(currency) && isStackable && amount < inventoryAmount && player.getItemAssistant().freeSlots() <= 0) {
				player.getActionSender().sendMessage("You don't have enough space in your inventory.");
			}

			player.getItemAssistant().deleteItem(itemID, amount);
			String ItemNameUnNotedItem = ItemAssistant.getItemName(itemID - 1).toLowerCase();
			if (itemName.contains(ItemNameUnNotedItem)) {
				itemID = itemID - 1; //Replace the noted item by it's un-noted version.
			}

			if (ShopHandler.ShopName[player.myShopId].equalsIgnoreCase(player.properName + "'s Store")) {
				// Add items to players store
				player.getActionSender().sendMessage("You sent " + amount + " " + itemName + " to your store.");
				BotHandler.addTobank(player.myShopId, itemID, amount);
			} else {
				// Add currency to players inventory
				int totalValue = value * amount;
				player.getItemAssistant().addItem(currency, totalValue);
				player.getActionSender().sendMessage("You sold " + amount + " " + itemName + " for " + totalValue + " " + ItemAssistant.getItemName(itemID).toLowerCase() + ".");
			}

			// Add item to the shop
			addShopItem(itemID, amount);
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
		for (int i = 0; i < ShopHandler.ShopItems[player.myShopId].length; i++) {
			if (ShopHandler.ShopItems[player.myShopId][i] - 1 == itemID) {
				ShopHandler.ShopItemsN[player.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems[player.myShopId].length; i++) {
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
		int shopID = player.myShopId;
		boolean isStackable = ItemDefinitions.getDef()[itemID].isStackable;
		int freeSlots = player.getItemAssistant().freeSlots();
		int storeQty = ShopHandler.getStock(shopID, itemID);
		System.out.println("Item " + itemID + " stock = " + storeQty);
		if (amount > 0) {
			if (storeQty <= 0) {
				// none in stock, or not sold here
				player.getActionSender().sendMessage("You can't buy that right now!");
				return false;
			}
			if (amount > storeQty) {
				// buy all that the store has
				amount = storeQty;
			}
			if (freeSlots <= 0){
				if (!isStackable || isStackable && !player.getItemAssistant().playerHasItem(itemID)) {
					player.getActionSender().sendMessage("You don't have enough space in your inventory.");
					return false;
				}
			}
			if (!isStackable && amount > freeSlots) {
				// player will fill their inventory
				amount = freeSlots;
			}
			if(!player.isShopping) {
		        return false;
			}
			for (int i = 0; i < FISHING_ITEMS.length; i++) {
				if (player.myShopId == 32 && itemID == FISHING_ITEMS[i]) {
					player.getActionSender().sendMessage("You can't buy that item from this store!");
					return false;
				}		
			}
			int value = 0;	// Item Value
			int currency = 995; // currency this shop uses
			if (player.myShopId == 138 || player.myShopId == 58 || player.myShopId == 139) {
				value = getTokkulValue(itemID);
				currency = 6529; // Tokkul
			} else if (player.myShopId == RANGE_SHOP) {
				value = getRGItemValue(itemID);
				currency = 1464; // Archery tickets
			} else if (player.myShopId == PEST_SHOP) {
				value = getPestItemValue(itemID);
				currency = 995; // gp
			} else if (player.myShopId == CASTLE_SHOP) {
				value = getCastleItemValue(itemID);
				currency = 4067; // castle wars tickets
			} else {
				value = getItemShopValue(itemID, 0, false);
				currency = 995; //gp
			}
			int currencySlot = player.getItemAssistant().getItemSlot(currency);

			// player has none of the required currency
			if (currencySlot == -1) {
				player.getActionSender().sendMessage("You don't have enough " + ItemAssistant.getItemName(currency).toLowerCase() + " to buy that.");
				return false;
			}

			// amount of currency the player has
			int currencyAmount = player.playerItemsN[currencySlot];

			int totalValue = value * amount;
			if (currencyAmount < totalValue) {
				amount = (int) Math.floor(player.playerItemsN[currencySlot] / amount);
				// buy as many as we can afford
				totalValue = value * amount;
				if (currencyAmount < totalValue) {
					player.getActionSender().sendMessage("You don't have enough " + ItemAssistant.getItemName(currency).toLowerCase() + " to buy that.");
					return false;
				}
			}
			player.getItemAssistant().deleteItem2(currency, totalValue);
			player.getItemAssistant().addItem(itemID, amount);
			ShopHandler.buyItem(shopID, itemID, amount);
			if (ShopHandler.ShopBModifier[shopID] == 0){
				BotHandler.removeFrombank(shopID, itemID, amount);
			}
			player.getActionSender().sendMessage("You bought " + amount + " " + ItemAssistant.getItemName(itemID).toLowerCase() + " for " + totalValue + " " + ItemAssistant.getItemName(currency).toLowerCase() + "." );

			String itemName = ItemAssistant.getItemName(itemID).toLowerCase();
			if (player.getPlayerAssistant().isPlayer()) {
				GameLogger.writeLog(player.playerName, "shopbuying", player.playerName + " bought " + amount + " " + ItemAssistant.getItemName(itemID).toLowerCase() + " for " + totalValue + " " + ItemAssistant.getItemName(currency).toLowerCase() + " from store " + shopID + ".");
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.myShopId);
			updatePlayerShop();
			return true; //return TRUE / FALSE Update = shop&Inventory / Doesnt Update
		}
		return false;
	}
}
