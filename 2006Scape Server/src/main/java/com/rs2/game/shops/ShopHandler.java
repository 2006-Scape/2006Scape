package com.rs2.game.shops;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.util.ShopData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;

public class ShopHandler {

    public static int MAX_SHOPS = 800;

    public static int MAX_SHOP_ITEMS = 40;

    public static int SHOW_DELAY = 1; // Restock 1 item every tick

    public static int SPECIAL_DELAY = 60; // Remove overstocked items after 60 ticks

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
        //writeShops("shops.cfg");
        loadShops();
    }

    public static int restockTimeItem(int itemId) {
        switch (itemId) {
            default:
                return 1000;
        }

    }

    public void process() {
        boolean DidUpdate = false;
        for (int i = 1; i <= totalshops; i++) {
			if (shopBModifier[i] == 0 || shopSModifier[i] == 0) {
				continue;
			}
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
        }
    }

    private void DiscountItem(int shopID, int ArrayID) {
        shopItemsN[shopID][ArrayID] -= 1;
        if (shopItemsN[shopID][ArrayID] <= 0) {
            shopItemsN[shopID][ArrayID] = 0;
			if (shopItemsStandard[shopID] <= ArrayID) {
				ResetItem(shopID, ArrayID);
			}
        }
    }

    private static void ResetItem(int shopID, int ArrayID) {
		if (shopItemsStandard[shopID] > ArrayID) {
			return;
		}
        shopItems[shopID][ArrayID] = 0;
        shopItemsN[shopID][ArrayID] = 0;
        shopItemsDelay[shopID][ArrayID] = 0;
    }

    public void loadShops() {
        Gson gson = new Gson();

        try {
            Type collectionType = new TypeToken<ShopData[]>() {
            }.getType();
            ShopData[] data = gson.fromJson(new FileReader("./data/cfg/shops.json"), collectionType);

            for (ShopData shop : data) {
                int shopID = shop.getId();
                shopName[shopID] = shop.getName();
                shopSModifier[shopID] = shop.getSellModifier();
                shopBModifier[shopID] = shop.getBuyModifier();
                for (int i = 0; i < shop.getItems().length; i++) {
                    if (shop.getItems()[i].getItemId() > 0) {
                        shopItems[shopID][i] = shop.getItems()[i].getItemId() + 1;
                        shopItemsN[shopID][i] = shop.getItems()[i].getItemAmount();
                        shopItemsSN[shopID][i] = shop.getItems()[i].getItemAmount();
                        shopItemsStandard[shopID]++;
                    } else {
                        break;
                    }
                }
                totalshops++;
            }
        } catch (FileNotFoundException fileex) {
            System.out.println("shops.json: file not found.");
        }
    }

    public boolean writeShops(String FileName) {
        String         line          = "";
        String         token         = "";
        String         token2        = "";
        String[]       token3        = new String[(MAX_SHOP_ITEMS * 2)];
        boolean        EndOfFile     = false;
        BufferedReader characterfile = null;
        JSONArray      array         = new JSONArray();
        try {
            characterfile = new BufferedReader(new FileReader("./data/cfg/" + FileName));
        } catch (FileNotFoundException fileex) {
            System.out.println(FileName + ": file not found.");
            return false;
        }
        try {
            line = characterfile.readLine();
        } catch (IOException ioexception) {
            System.out.println(FileName + ": error loading file.");
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
                    JSONObject object = new JSONObject();

                    object.put("id", Integer.parseInt(token3[0]));
                    object.put("name", token3[1].replace("_", " "));
                    object.put("sellModifier", Integer.parseInt(token3[2]));
                    object.put("buyModifier", Integer.parseInt(token3[3]));

                    JSONArray array1 = new JSONArray();
                    for (int i = 0; i < ((token3.length - 4) / 2); i++) {
                        JSONObject object1 = new JSONObject();
                        object1.put("itemId", Integer.parseInt(token3[(4 + (i * 2))]));
                        object1.put("itemAmount", Integer.parseInt(token3[(5 + (i * 2))]));
                        array1.put(array1.length(), object1);
                    }
                    object.put("items", array1);

                    array.put(object);
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

            FileWriter fileWriter = new FileWriter("shops-dump.json");
            fileWriter.write(array.toString());
        } catch (IOException ioexception) {
        }

        return false;
    }

    public static void createPlayerShop(Client player) {
        int id = getEmptyshop();
        player.shopId = id;
        shopSModifier[id] = 0;
        shopBModifier[id] = 0;
        shopName[id] = player.properName + "'s Store";
        for (int i = 0; i < MAX_SHOP_ITEMS; i++) {
            shopItems[id][i] = player.bankItems[i];
            shopItemsN[id][i] = player.bankItemsN[i];
            shopItemsSN[id][i] = 0;
            shopItemsDelay[id][i] = 0;
        }
        totalshops++;
    }

    public static void closePlayerShop(Client player) {
        for (int id = getEmptyshop(); id >= 0; id--) {
			if (shopName[id].equals(player.properName + "'s Store")) {
                for (int i = 0; i < MAX_SHOP_ITEMS; i++) {
                    shopItems[id][i] = 0;
                    shopItemsN[id][i] = 0;
                    shopItemsSN[id][i] = 0;
                    shopItemsDelay[id][i] = 0;
                }
                refreshshop(id);
			}
        }
    }

    private static int getEmptyshop() {
        for (int i = 0; i < MAX_SHOPS; i++) {
			if (shopName[i].equals("")) {
				return i;
			}
        }
        return -1;
    }

    public static void refreshshop(int shop_id) {
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

        for (int k = 1; k < PlayerHandler.players.length; k++) {
            if (PlayerHandler.players[k] != null) {
                if (PlayerHandler.players[k].isShopping && PlayerHandler.players[k].shopId == shop_id) {
                    PlayerHandler.players[k].updateShop = true;
                    PlayerHandler.players[k].updateShop(shop_id);
                }
            }
        }
    }

    public static int getStock(int shop_id, int item_id) {
        item_id++;
        for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
            if (shopItems[shop_id][j] == item_id) {
                return shopItemsN[shop_id][j];
            }
        }
        return -1;
    }

    public static void buyItem(int shop_id, int item_id, int amount) {
        item_id++;
        for (int j = 0; j < MAX_SHOP_ITEMS; j++) {
            if (shopItems[shop_id][j] == item_id) {
                shopItemsN[shop_id][j] -= amount;
            }
        }
        refreshshop(shop_id);
    }

    public static boolean playerOwnsStore(int shop_id, Player player) {
        return shopSModifier[shop_id] == 0 && shopBModifier[shop_id] == 0 && shopName[shop_id].equalsIgnoreCase(player.properName + "'s Store");
    }
}
