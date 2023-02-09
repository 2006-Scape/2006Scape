package com.rs2.game.shops;

import com.rs2.Constants;
import org.apollo.cache.def.ItemDefinition;

import com.rs2.game.bots.BotHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.ItemConstants;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.GameLogger;

/**
 * Many Fixes/Things Added
 * @author Andrew (Mr Extremez)
 */

public class ShopAssistant {

	private final Player player;

	public ShopAssistant(Player player2) {
		player = player2;
	}

	public static final int RANGE_SHOP = 111, PEST_SHOP = 175, CASTLE_SHOP = 112;

	/**
	 * Shops
	 **/

	public void openShop(int ShopID) {
		player.getPacketSender().sendSound(1465, 100, 0);
		player.getItemAssistant().resetItems(3823);
		resetShop(ShopID);
		player.isShopping = true;
		player.shopId = ShopID;
		player.getPacketSender().sendFrame248(3824, 3822);
		player.getPacketSender().sendString(ShopHandler.shopName[ShopID], 3901);
	}

	public void updatePlayerShop() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping
						&& PlayerHandler.players[i].shopId == player.shopId
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
			player.totalShopItems = 0;
			for (int i = 0; i < ShopHandler.MAX_SHOP_ITEMS; i++) {	//adds items in store when items are sold until max value.
				if (ShopHandler.shopItems[ShopID][i] > 0) {
					player.totalShopItems++;
				}
			}
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(3900);
			player.getOutStream().writeWord(player.totalShopItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.shopItems[player.shopId].length; i++)
			{
				if (ShopHandler.shopItems[ShopID][i] > 0
						|| i <= ShopHandler.shopItemsStandard[ShopID])
				{
					if (ShopHandler.shopItemsN[ShopID][i] > 254) {
						player.getOutStream().writeByte(255);
						player.getOutStream().writeDWord_v2(ShopHandler.shopItemsN[ShopID][i]);
					}
					else
						{
						player.getOutStream().writeByte(ShopHandler.shopItemsN[ShopID][i]);
					}
					if (ShopHandler.shopItems[ShopID][i] > Constants.ITEM_LIMIT
							|| ShopHandler.shopItems[ShopID][i] < 0) {
						ShopHandler.shopItems[ShopID][i] = Constants.ITEM_LIMIT;
					}
					player.getOutStream().writeWordBigEndianA(
							ShopHandler.shopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > player.totalShopItems) {
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
		if (ItemDefinition.lookup(ItemID) != null) {
			ShopValue = ItemDefinition.lookup(ItemID).getValue() * sellingRatio;
		}

		TotPrice = ShopValue;

		// General store pays less for items
		if (isSelling && ShopHandler.shopBModifier[player.shopId] == 1) {
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

	public void buyFromShopPrice(int itemID) {
		int ShopValue = (int) Math.floor(getItemShopValue(itemID, 0, false));
		int SpecialValue = getTokkulValue(itemID);
		String ShopAdd = "";
		// player owned shop
		if (ShopHandler.shopBModifier[player.shopId] == 0) {
			ShopValue = BotHandler.getItemPrice(player.shopId, itemID);
		}
		if (player.shopId == 138 || player.shopId == 139 || player.shopId == 58) {
			player.getPacketSender().sendMessage(DeprecatedItems.getItemName(itemID) + ": currently costs " + SpecialValue + " tokkul.");
			return;
		}
		if (player.shopId == PEST_SHOP) {
			player.getPacketSender().sendMessage(DeprecatedItems.getItemName(itemID)+": currently costs " + getPestItemValue(itemID) + " pest control points.");
			return;
		}
		if (player.shopId == CASTLE_SHOP) {
			player.getPacketSender().sendMessage(DeprecatedItems.getItemName(itemID)+": currently costs " + getCastleItemValue(itemID) + " castle wars tickets.");
			return;
		}
		if (player.shopId == RANGE_SHOP) {
			player.getPacketSender().sendMessage(DeprecatedItems.getItemName(itemID)+": currently costs " + getRGItemValue(itemID) + " archery tickets.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + ShopValue / 1000 + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + ShopValue / 1000000 + " million)";
		}
		player.getPacketSender().sendMessage(DeprecatedItems.getItemName(itemID) + ": currently costs " + ShopValue + " coins" + ShopAdd);
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
		case 440: // Iron ore
			return 25;
		case 442: // Silver ore
			return 112;
		case 444: // Gold ore
			return 225;
		case 447: // Mithril ore
			return 243;
		case 449: // Adamantite ore
			return 600;
		case 451: // Runite ore
			return 4800;
		case 1619: // Uncut ruby
			return 150;
		case 1617: // Uncut diamond
			return 300;
		case 1631: // Uncut dragonstone
			return 1500;
		}		
		return 0;
	}

	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		int unNotedItemID = getUnNoted(removeId);
		String itemName = DeprecatedItems.getItemName(unNotedItemID);
		for (int i : ItemConstants.ITEM_SELLABLE) {
			if (unNotedItemID == i) {
				player.getPacketSender().sendMessage("You can't sell " + itemName + ".");
				return;
			}
		}
		boolean canSellItem = false;
		switch (ShopHandler.shopSModifier[player.shopId]) {
			// Only buys what is in stock
			case 2:
				for (int j = 0; j <= ShopHandler.shopItemsStandard[player.shopId]; j++) {
					if (unNotedItemID == (ShopHandler.shopItems[player.shopId][j] - 1)) {
						canSellItem = true;
						break;
					}
				}
				break;
			// General store
			case 1:
				canSellItem = true;
				break;
			// Player owns this store
			case 0:
				canSellItem = ShopHandler.playerOwnsStore(player.shopId, player);
				break;
		}

		if (canSellItem == false) {
			player.getPacketSender().sendMessage("You can't sell " + DeprecatedItems.getItemName(removeId).toLowerCase() + " to this store.");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(unNotedItemID, 1, true));
			int tokkulValue = (int) Math.floor(getTokkulValue(unNotedItemID) *.85);
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			if (ShopHandler.playerOwnsStore(player.shopId, player)) {
				if (ShopHandler.getStock(player.shopId, unNotedItemID) > 0)
					player.getPacketSender().sendMessage(itemName + ": you are selling this item for " + BotHandler.getItemPrice(player.shopId, unNotedItemID) + " coins.");
				else
					player.getPacketSender().sendMessage(itemName + ": you haven't set your sell price.");
			} else if (player.shopId != RANGE_SHOP && player.shopId != PEST_SHOP && player.shopId != CASTLE_SHOP && player.shopId != 138 && player.shopId != 58 && player.shopId != 139) {
				player.getPacketSender().sendMessage(itemName + ": shop will buy for " + ShopValue + " coins." + ShopAdd);
			} else if (player.shopId == 138 || player.shopId == 139 || player.shopId == 58) {
				player.getPacketSender().sendMessage(itemName + ": shop will buy for " + tokkulValue + " tokkul.");
			} else if (player.shopId == RANGE_SHOP) {
				player.getPacketSender().sendMessage(itemName + ": shop will buy for " + getRGItemValue(unNotedItemID) + " archery tickets." + ShopAdd);
			} else if (player.shopId == PEST_SHOP) {
				player.getPacketSender().sendMessage(itemName + ": shop will buy for " + getPestItemValue(unNotedItemID) + " pest control points." + ShopAdd);
			} else if (player.shopId == CASTLE_SHOP) {
				player.getPacketSender().sendMessage(itemName + ": shop will buy for " + getCastleItemValue(unNotedItemID) + " castle war tickets." + ShopAdd);
			}
		}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		int unNotedItemID = getUnNoted(itemID);
		String itemName = DeprecatedItems.getItemName(itemID).toLowerCase();
		for (int i : ItemConstants.ITEM_SELLABLE) {
			if (i == unNotedItemID) {
				player.getPacketSender().sendMessage("You can't sell " + itemName + ".");
				return false;
			}
		}
		if (player.playerRights == 2 && !Constants.ADMIN_CAN_SELL_ITEMS) {
			player.getPacketSender().sendMessage("Selling items as an admin has been disabled.");
			return false;
		}
		if(!player.isShopping) {
	        return false;
		}
		// We can only store 40 items per shop
		if (player.totalShopItems >= 40) {
			player.getPacketSender().sendMessage("This shop is out of space!");
			return false;
		}
		// Check we have the item in our inventory
		int inventoryAmount = player.getItemAssistant().getItemAmount(itemID);
		if (amount > 0 && inventoryAmount > 0) {
			boolean canSellToStore = false;
			// Type of store
			switch (ShopHandler.shopSModifier[player.shopId]) {
				// Only buys what they sell
				case 2:
					for (int j = 0; j <= ShopHandler.shopItemsStandard[player.shopId]; j++) {
						if (unNotedItemID == (ShopHandler.shopItems[player.shopId][j] - 1)) {
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
					canSellToStore = ShopHandler.playerOwnsStore(player.shopId, player);
					break;
			}
			if (canSellToStore == false) {
				player.getItemAssistant();
				player.getPacketSender().sendMessage("You can't sell " + itemName + " to this store.");
				return false;
			}
			// player owned store, setting item price
			if (ShopHandler.playerOwnsStore(player.shopId, player)) {
				// No items in stock, we are adding 1 and setting the price
				if (ShopHandler.getStock(player.shopId, unNotedItemID) <= 0){
					player.getItemAssistant().deleteItem(itemID, 1);
					BotHandler.addTobank(player.shopId, unNotedItemID, 1);
					BotHandler.setPrice(player.shopId, unNotedItemID, amount);
					addShopItem(unNotedItemID, 1);
					player.getItemAssistant().resetItems(3823);
					resetShop(player.shopId);
					updatePlayerShop();
					return true;
				}
			}
			if (amount > inventoryAmount) {
				amount = inventoryAmount;
			}

			int value = 1;
			int currency = 995;
			if (player.shopId == 138 || player.shopId == 58 || player.shopId == 139) {
				value = (int) Math.floor(getTokkulValue(unNotedItemID) * .85);
				currency = 6529;
			} else {
				value = (int) Math.floor(getItemShopValue(unNotedItemID, amount, true));
				currency = 995;
			}

			boolean isStackable = ItemDefinition.lookup(itemID).isStackable();

			if (!player.getItemAssistant().playerHasItem(currency) && isStackable && amount < inventoryAmount && player.getItemAssistant().freeSlots() <= 0) {
				player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
			}

			player.getItemAssistant().deleteItem(itemID, amount);

			if (ShopHandler.playerOwnsStore(player.shopId, player)) {
				// Add items to players store
				player.getPacketSender().sendMessage("You sent " + amount + " " + itemName + " to your store.");
				BotHandler.addTobank(player.shopId, unNotedItemID, amount);
			} else {
				// Add currency to players inventory
				int totalValue = value * amount;
				player.getItemAssistant().addItem(currency, totalValue);
				player.getPacketSender().sendMessage("You sold " + amount + " " + itemName + " for " + totalValue + " " + DeprecatedItems.getItemName(currency).toLowerCase() + ".");
			}

			// Add item to the shop
			addShopItem(unNotedItemID, amount);
			player.getItemAssistant().resetItems(3823);
			resetShop(player.shopId);
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
		if (ItemDefinition.lookup(itemID).isNote()) {
			itemID = ItemDefinition.noteToItem(itemID);
		}
		for (int i = 0; i < ShopHandler.shopItems[player.shopId].length; i++) {
			if (ShopHandler.shopItems[player.shopId][i] - 1 == itemID) {
				ShopHandler.shopItemsN[player.shopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.shopItems[player.shopId].length; i++) {
				if (ShopHandler.shopItems[player.shopId][i] == 0) {
					ShopHandler.shopItems[player.shopId][i] = itemID + 1;
					ShopHandler.shopItemsN[player.shopId][i] = amount;
					ShopHandler.shopItemsDelay[player.shopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	private static int getUnNoted(int itemID){
		String itemName = DeprecatedItems.getItemName(itemID).toLowerCase();
		String ItemNameUnNotedItem = DeprecatedItems.getItemName(itemID - 1).toLowerCase();
		if (itemName.equalsIgnoreCase(ItemNameUnNotedItem)) {
			itemID--; //Replace the noted item by it's un-noted version.
		}
		return itemID;
	}

	private static int getNoted(int itemID){
		String itemName = DeprecatedItems.getItemName(itemID).toLowerCase();
		String ItemNameUnNotedItem = DeprecatedItems.getItemName(itemID + 1).toLowerCase();
		if (itemName.equalsIgnoreCase(ItemNameUnNotedItem)) {
			itemID++; //Replace the item by it's noted version.
		}
		return itemID;
	}

	private static final int FISHING_ITEMS[] = {383, 371, 377, 359, 321, 341, 353, 345, 327, 317};

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		int shopID = player.shopId;
		int notedItemID = getNoted(itemID);
		boolean isPlayerShop = ShopHandler.shopBModifier[player.shopId] == 0;
		// Items are stackable if from a player owned shop and notable
		boolean isStackable = ItemDefinition.lookup(itemID).isStackable() || (isPlayerShop && getNoted(itemID) != itemID);
		int freeSlots = player.getItemAssistant().freeSlots();
		int storeQty = ShopHandler.getStock(shopID, itemID);
		if (amount > 0) {
			if (storeQty <= 0) {
				// none in stock, or not sold here
				player.getPacketSender().sendMessage("You can't buy that right now!");
				return false;
			}
			if (amount > storeQty) {
				// buy all that the store has
				amount = storeQty;
			}
			if (freeSlots <= 0){
				if (!isStackable || isStackable && !player.getItemAssistant().playerHasItem(isPlayerShop ? notedItemID : itemID)) {
					player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
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
				if (player.shopId == 32 && itemID == FISHING_ITEMS[i]) {
					player.getPacketSender().sendMessage("You can't buy that item from this store!");
					return false;
				}		
			}
			int value = 0;
			int currency = 995;
			// player owned shop
			boolean shopIsOwnedByThisPlayer = ShopHandler.playerOwnsStore(player.shopId, player);
			if (shopIsOwnedByThisPlayer) { // PLayers own shop, no cost
				value = 0;
				currency = -1;
			} else if (isPlayerShop) { // Shop owned by another player
				value = BotHandler.getItemPrice(player.shopId, itemID);
				currency = 995; // gp
			} else if (player.shopId == 138 || player.shopId == 58 || player.shopId == 139) {
				value = getTokkulValue(itemID);
				currency = 6529; // Tokkul
			} else if (player.shopId == RANGE_SHOP) {
				value = getRGItemValue(itemID);
				currency = 1464; // Archery tickets
			} else if (player.shopId == PEST_SHOP) {
				value = getPestItemValue(itemID);
				currency = 995; // gp
			} else if (player.shopId == CASTLE_SHOP) {
				value = getCastleItemValue(itemID);
				currency = 4067; // castle wars tickets
			} else {
				value = getItemShopValue(itemID, 0, false);
				currency = 995; //gp
			}
			int currencySlot = player.getItemAssistant().getItemSlot(currency);
			String currencyName = DeprecatedItems.getItemName(currency).toLowerCase();

			// player has none of the required currency
			if (currencySlot == -1) {
				player.getPacketSender().sendMessage("You don't have enough " + currencyName + " to buy that.");
				return false;
			}

			// amount of currency the player has
			int currencyAmount = player.playerItemsN[currencySlot];

			int totalValue = value * amount;
			if (currencyAmount < totalValue) {
				amount = (int) Math.floor(player.playerItemsN[currencySlot] / value);
				// buy as many as we can afford
				totalValue = value * amount;
				if (currencyAmount < totalValue || amount <= 0) {
					player.getPacketSender().sendMessage("You don't have enough " + currencyName + " to buy that.");
					return false;
				}
			}

			String itemName = DeprecatedItems.getItemName(itemID).toLowerCase();
			if (!shopIsOwnedByThisPlayer) {
				player.getItemAssistant().deleteItem(currency, totalValue);
				player.getPacketSender().sendMessage("You bought " + amount + " " + itemName + " for " + totalValue + " " + currencyName + "." );
				// If it is a player owned shop, we need to give them the coins
				if (ShopHandler.shopSModifier[player.shopId] == 0)
					BotHandler.addCoins(shopID, totalValue);
			} else {
				player.getPacketSender().sendMessage("You withdrew " + amount + " " + itemName + " from your store." );
			}
			// If it is a player owned store, give the player the noted item
			player.getItemAssistant().addItem(isPlayerShop ? notedItemID : itemID, amount);
			ShopHandler.buyItem(shopID, itemID, amount);
			if (ShopHandler.shopBModifier[shopID] == 0){
				BotHandler.removeFrombank(shopID, itemID, amount);
			}

			if (player.getPlayerAssistant().isPlayer()) {
				GameLogger.writeLog(player.playerName, "shopbuying", player.playerName + " bought " + amount + " " + itemName + " for " + totalValue + " " + currencyName + " from store " + shopID + ".");
			}
			player.getItemAssistant().resetItems(3823);
			resetShop(player.shopId);
			updatePlayerShop();
			return true; //return TRUE / FALSE Update = shop&Inventory / Doesnt Update
		}
		return false;
	}
}
