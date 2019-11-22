package redone.game.shops;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import redone.game.bots.Bot;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

/**
 * Shops
 **/

public class ShopHandler {

	public static int MaxShops = 400;
	public static int MaxShopItems = 40;
	public static int MaxShowDelay = 2;
	public static int MaxSpecShowDelay = 60;
	public static int TotalShops = 0;
	public static int[][] ShopItems = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsN = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsDelay = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsSN = new int[MaxShops][MaxShopItems];
	public static int[] ShopItemsStandard = new int[MaxShops];
	public static String[] ShopName = new String[MaxShops];
	public static int[] ShopSModifier = new int[MaxShops];
	public static int[] ShopBModifier = new int[MaxShops];
	public static long[][] ShopItemsRestock = new long[MaxShops][MaxShopItems];

	public ShopHandler() {
		for (int i = 0; i < MaxShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				ResetItem(i, j);
				ShopItemsSN[i][j] = 0;
			}
			ShopItemsStandard[i] = 0;
			ShopSModifier[i] = 0;
			ShopBModifier[i] = 0;
			ShopName[i] = "";
		}
		TotalShops = 0;
		loadShops("shops.cfg");
	}
	
	public static int restockTimeItem(int itemId) {
		switch(itemId) {
			default:
			return 1000;
		}

	}

	public void process() {
		boolean DidUpdate = false;
		for (int i = 1; i <= TotalShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				if (ShopItems[i][j] > 0) {
					if (ShopItemsDelay[i][j] >= MaxShowDelay) {
						if (j <= ShopItemsStandard[i] && ShopItemsN[i][j] <= ShopItemsSN[i][j]) {
							if (ShopItemsN[i][j] < ShopItemsSN[i][j] && System.currentTimeMillis() - ShopItemsRestock[i][j] > restockTimeItem(ShopItems[i][j])) {
								ShopItemsN[i][j] += 1;
								ShopItemsDelay[i][j] = 1;
								ShopItemsDelay[i][j] = 0;
								DidUpdate = true;
								ShopItemsRestock[i][j] = System.currentTimeMillis();
							}
						} else if (ShopItemsDelay[i][j] >= MaxSpecShowDelay) {
							DiscountItem(i, j);
							ShopItemsDelay[i][j] = 0;
							DidUpdate = true;
						}
					}
					ShopItemsDelay[i][j]++;
				}
			}
			if (DidUpdate) {
				for (int k = 1; k < PlayerHandler.players.length; k++) {
					if (PlayerHandler.players[k] != null) {
						if (PlayerHandler.players[k].isShopping && PlayerHandler.players[k].myShopId == i) {
							PlayerHandler.players[k].updateShop = true;
							PlayerHandler.players[k].updateshop(i);
						}
					}
				}
				DidUpdate = false;
			}
		}
	}

	private void DiscountItem(int ShopID, int ArrayID) {
		ShopItemsN[ShopID][ArrayID] -= 1;
		if (ShopItemsN[ShopID][ArrayID] <= 0) {
			ShopItemsN[ShopID][ArrayID] = 0;
			ResetItem(ShopID, ArrayID);
		}
	}

	private static void ResetItem(int ShopID, int ArrayID) {
		ShopItems[ShopID][ArrayID] = 0;
		ShopItemsN[ShopID][ArrayID] = 0;
		ShopItemsDelay[ShopID][ArrayID] = 0;
	}


	public boolean loadShops(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[(MaxShopItems * 2)];
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
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t+", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("shop")) {
					int ShopID = Integer.parseInt(token3[0]);
					ShopName[ShopID] = token3[1].replaceAll("_", " ");
					ShopSModifier[ShopID] = Integer.parseInt(token3[2]);
					ShopBModifier[ShopID] = Integer.parseInt(token3[3]);
					for (int i = 0; i < ((token3.length - 4) / 2); i++) {
						if (token3[(4 + (i * 2))] != null) {
							ShopItems[ShopID][i] = (Integer.parseInt(token3[(4 + (i * 2))]) + 1);
							ShopItemsN[ShopID][i] = Integer.parseInt(token3[(5 + (i * 2))]);
							ShopItemsSN[ShopID][i] = Integer.parseInt(token3[(5 + (i * 2))]);
							ShopItemsStandard[ShopID]++;
						} else {
							break;
						}
					}
					TotalShops++;
				}
			} else {
				if (line.equals("[ENDOFSHOPLIST]")) {
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
		int id = getEmptyShop();
		player.myShopId = id;
		ShopSModifier[id] = 0;
		ShopName[id] = player.properName + "'s Store";
		for (int i = 0; i < MaxShopItems; i++){
			ShopItems[id][i] = player.bankItems[i];
			ShopItemsN[id][i] = player.bankItemsN[i];
			ShopItemsSN[id][i] = 0;
			ShopItemsDelay[id][i] = 0;
		}
		TotalShops++;
	}

	private static int getEmptyShop(){
		for (int i = 0; i < MaxShops; i++) {
			if (ShopName[i] == "") return i;
		}
		return -1;
	}

	public static void refreshShop(int shop_id){
		// We don't want to remove items that should be kept in stock
		for (int j = ShopItemsStandard[shop_id]; j < MaxShopItems; j++) {
			if (ShopItems[shop_id][j] > 0 && ShopItemsN[shop_id][j] <= 0) {
				ResetItem(shop_id, j);
				if (ShopItems[shop_id][j + 1] > 0) {
					ShopItems[shop_id][j] = ShopItems[shop_id][j + 1];
					ShopItemsN[shop_id][j] = ShopItemsN[shop_id][j + 1];
					ShopItemsSN[shop_id][j] = ShopItemsSN[shop_id][j + 1];
					ShopItemsDelay[shop_id][j] = ShopItemsDelay[shop_id][j + 1];
					ResetItem(shop_id, j + 1);
				}
			}
		}
	}

	public static int getStock(int shop_id, int item_id){
		item_id++;
		for (int j = 0; j < MaxShopItems; j++) {
			if (ShopItems[shop_id][j] > 0)
				System.out.println("item " + item_id + " = " + ShopItemsN[shop_id][j]);
			if (ShopItems[shop_id][j] == item_id) {
				return ShopItemsN[shop_id][j];
			}
		}
		return -1;
	}

	public static void buyItem(int shop_id, int item_id, int amount){
		item_id++;
		for (int j = 0; j < MaxShopItems; j++) {
			if (ShopItems[shop_id][j] == item_id) {
				ShopItemsN[shop_id][j] -= amount;
			}
		}
		refreshShop(shop_id);
	}
}
