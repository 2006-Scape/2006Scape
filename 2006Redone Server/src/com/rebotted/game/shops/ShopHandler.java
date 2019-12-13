package com.rebotted.game.shops;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.util.Misc;


public class ShopHandler {

	public static int MAX_SHOPS = 800;
	
	public static int MAX_SHOP_ITEMS = 40;
	
	public static int SHOW_DELAY = 2;
	
	public static int SPECIAL_DELAY = 60;
	
	public static int totalshops = 0;
	
	public static int[][] shopItems = new int[MAX_SHOPS][MAX_SHOP_ITEMS];
	
	public static int[][] shopItemsN = new int[MAX_SHOPS][MAX_SHOP_ITEMS];
	
	public static int[][] shopItemsDelay = new int[MAX_SHOPS][MAX_SHOP_ITEMS];
	
	public static int[][] shopItemsSN = new int[MAX_SHOPS][MAX_SHOP_ITEMS];
	
	public static int[] shopItemsStandard = new int[MAX_SHOPS];
	
	public static String[] shopName = new String[MAX_SHOPS];
	
	public static int[] shopSModifier = new int[MAX_SHOPS];
	
	public static int[] shopBModifier = new int[MAX_SHOPS];
	
	public static long[][] shopItemsRestock = new long[MAX_SHOPS][MAX_SHOP_ITEMS];

	public ShopHandler() {
		for (int i = 0; i < MAX_SHOPS; i++) {
			for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
				ResetItem(i, j);
				shopItemsSN[i][j] = 0;
			}
			shopItemsStandard[i] = 0;
			shopSModifier[i] = 0;
			shopBModifier[i] = 0;
			shopName[i] = "";
		}
		totalshops = 0;
		loadshops("shops.cfg");
	}
	
	public static int restockTimeItem(int itemId) {
		switch(itemId) {
			default:
			return 1000;
		}

	}

	public void process() {
		boolean DidUpdate = false;
		for (int i = 1; i <= totalshops; i++) {
			if (shopBModifier[i] == 0 || shopSModifier[i] == 0) continue;
			for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
				if (shopItems[i][j] > 0) {
					if (shopItemsDelay[i][j] >= SHOW_DELAY) {
						if (j <= shopItemsStandard[i] && shopItemsN[i][j] <= shopItemsSN[i][j]) {
							if (shopItemsN[i][j] < shopItemsSN[i][j] && System.currentTimeMillis() - shopItemsRestock[i][j] > restockTimeItem(shopItems[i][j])) {
								shopItemsN[i][j] += 1;
								shopItemsDelay[i][j] = 1;
								shopItemsDelay[i][j] = 0;
								DidUpdate = true;
								shopItemsRestock[i][j] = System.currentTimeMillis();
							}
						} else if (shopItemsDelay[i][j] >= SPECIAL_DELAY) {
							DiscountItem(i, j);
							shopItemsDelay[i][j] = 0;
							DidUpdate = true;
						}
						refreshshop(i);
					}
					shopItemsDelay[i][j]++;
				}
			}
			if (DidUpdate) {
				for (int k = 1; k < PlayerHandler.players.length; k++) {
					if (PlayerHandler.players[k] != null) {
						if (PlayerHandler.players[k].isShopping && PlayerHandler.players[k].shopId == i) {
							PlayerHandler.players[k].updateShop = true;
							PlayerHandler.players[k].updateShop(i);
						}
					}
				}
				DidUpdate = false;
			}
		}
	}

	private void DiscountItem(int shopID, int ArrayID) {
		shopItemsN[shopID][ArrayID] -= 1;
		if (shopItemsN[shopID][ArrayID] <= 0) {
			shopItemsN[shopID][ArrayID] = 0;
			if (shopItemsStandard[shopID] >= ArrayID)
				ResetItem(shopID, ArrayID);
		}
	}

	private static void ResetItem(int shopID, int ArrayID) {
		if (shopItemsStandard[shopID] < ArrayID) return;
		shopItems[shopID][ArrayID] = 0;
		shopItemsN[shopID][ArrayID] = 0;
		shopItemsDelay[shopID][ArrayID] = 0;
	}


	public boolean loadshops(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[(MAX_SHOP_ITEMS * 2)];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./data/cfg/" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token3 = token2.trim().split("\t+");
				if (token.equals("shop")) {
					int shopID = Integer.parseInt(token3[0]);
					shopName[shopID] = token3[1].replaceAll("_", " ");
					shopSModifier[shopID] = Integer.parseInt(token3[2]);
					shopBModifier[shopID] = Integer.parseInt(token3[3]);
					for (int i = 0; i < ((token3.length - 4) / 2); i++) {
						int itemID = Integer.parseInt(token3[(4 + (i * 2))]);
						int itemAmount = Integer.parseInt(token3[(5 + (i * 2))]);
						if (itemID > 0) {
							shopItems[shopID][i] = itemID + 1;
							shopItemsN[shopID][i] = itemAmount;
							shopItemsSN[shopID][i] = itemAmount;
							shopItemsStandard[shopID]++;
						} else {
							break;
						}
					}
					totalshops++;
				}
			} else {
				if (line.equalsIgnoreCase("[ENDOFSHOPLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public static void createPlayerShop(Client player){
		int id = getEmptyshop();
		player.shopId = id;
		shopSModifier[id] = 0;
		shopBModifier[id] = 0;
		shopName[id] = player.properName + "'s Store";
		for (int i = 0; i < MAX_SHOP_ITEMS; i++){
			shopItems[id][i] = player.bankItems[i];
			shopItemsN[id][i] = player.bankItemsN[i];
			shopItemsSN[id][i] = 0;
			shopItemsDelay[id][i] = 0;
		}
		totalshops++;
	}

	private static int getEmptyshop(){
		for (int i = 0; i < MAX_SHOPS; i++) {
			if (shopName[i] == "") return i;
		}
		return -1;
	}

	public static void refreshshop(int shop_id){
		// We don't want to remove items that should be kept in stock
		for (int j = shopItemsStandard[shop_id]; j < MAX_SHOP_ITEMS; j++) {
			if (shopItemsN[shop_id][j] <= 0) {
				ResetItem(shop_id, j);
				int next = j + 1;
				if (next < MAX_SHOP_ITEMS && shopItemsN[shop_id][next] > 0) {
					shopItems[shop_id][j] = shopItems[shop_id][next];
					shopItemsN[shop_id][j] = shopItemsN[shop_id][next];
					shopItemsDelay[shop_id][j] = shopItemsDelay[shop_id][next];
					ResetItem(shop_id, next);
				}
			}
		}
	}

	public static int getStock(int shop_id, int item_id){
		item_id++;
		for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
			if (shopItems[shop_id][j] == item_id) {
				return shopItemsN[shop_id][j];
			}
		}
		return -1;
	}

	public static void buyItem(int shop_id, int item_id, int amount){
		item_id++;
		for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
			if (shopItems[shop_id][j] == item_id) {
				shopItemsN[shop_id][j] -= amount;
			}
		}
		refreshshop(shop_id);
	}

	public static boolean playerOwnsStore(int shop_id, Player player){
		return shopSModifier[shop_id] == 0 && shopBModifier[shop_id] == 0 && shopName[shop_id].equalsIgnoreCase(player.properName + "'s Store");
	}
}
