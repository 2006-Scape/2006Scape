package com.rs2.game.bots;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.rs2.Constants;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.shops.ShopHandler;
import com.rs2.util.Misc;

import static com.rs2.game.players.PlayerSave.loadPlayerInfo;

public class BotHandler {

    static final List<Bot> botList = new ArrayList<>(BotConstants.MAX_BOTS);
    static final Random random = new SecureRandom();
    static final int currency = 995;

    public static Bot connectBot(String username, Integer x, Integer y, Integer z) {
        Bot bot;
        if (PlayerHandler.playerCount >= Constants.MAX_PLAYERS) {
            System.out.println("Bot could not be connected, server is full.");
            return null;
        }

        bot = new Bot(username, x, y, z);
        botList.add(bot);
        return bot;
    }

    public static void loadPlayerShops() {
        File dir = new File(System.getProperty("user.dir") + "/data/characters/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().startsWith("♥")) {
                    String playerName = child.getName().split("♥")[1];
                    playerName = playerName.substring(0, playerName.length() - 4);

                    Client determineIfLoadBasedOnShop = new Client(null);
                    determineIfLoadBasedOnShop.playerName = getShopName(playerName);
                    Client determineIfLoadBasedOnLevel = new Client(null); //supports legacy bots who's levels didn't update, rip
                    determineIfLoadBasedOnLevel.playerName = playerName;

                    loadPlayerInfo(determineIfLoadBasedOnShop, getShopName(playerName), "bot_password", false);
                    loadPlayerInfo(determineIfLoadBasedOnLevel, playerName, "bot_password", false);
                    if (determineIfLoadBasedOnLevel.getPlayerAssistant().getTotalLevel() < 50 || determineIfLoadBasedOnShop.bankItemsN[0] == 0) {
                        continue;
                    }

                    String shopName = getShopName(playerName);
                    Bot bot = connectBot(shopName, null, null, null);
                    Client playerShop = bot.getBotClient();
                    ShopHandler.createPlayerShop(playerShop);
                }
            }
        }
    }

    public static void playerShop(Player player) {
        if (!player.inPlayerShopArea()) {
            player.getPacketSender().sendMessage("You need to be in a bank zone or trade area for this.");
            return;
        }

        player.getPacketSender().sendMessage("Shop commands- ::shop, ::withdrawshop, ::closeshop");
        player.getPacketSender().sendMessage("Your shop will permanently stay if you're total level > 50!");

        Client playerShop = getPlayerShop(player.playerName);

        if (playerShop == null) {
            String shopName = getShopName(player.playerName);
            Bot bot = connectBot(shopName, player.getX(), player.getY(), player.getH());
            playerShop = bot.getBotClient();
            ShopHandler.createPlayerShop(playerShop);
        }

        playerShop.getPlayerAssistant().movePlayer(player.getX(), player.getY(), player.getH());


        playerShop.faceUpdate(player.face);
        playerShop.turnPlayerTo(player.getX() + Misc.random(-1, 1), player.getY() + Misc.random(-1, 1));
        int i = 0;
        // Remove all inventory items except money
        for (int item_id : playerShop.playerItems) {
            if (item_id > 0 && item_id != currency + 1) {
                playerShop.playerItems[i] = 0;
                playerShop.playerItemsN[i] = 0;
            }
            i++;
        }
        // Set bot to same level as player
        playerShop.combatLevel = player.combatLevel;
        i = 0;
        for (int level : player.playerLevel) {
            playerShop.playerLevel[i] = level;
            playerShop.playerXP[i] = playerShop.getPlayerAssistant().getXPForLevel(level);
            playerShop.getPlayerAssistant().refreshSkill(i);
            i++;
        }
        // Take the appearance of the player
        i = 0;
        for (int id : player.playerAppearance) {
            playerShop.playerAppearance[i] = id;
            i++;
        }
        // Dress the bot the same as the player
        i = 0;
        for (int item_id : player.playerEquipment) {
            playerShop.playerEquipment[i] = item_id;
            playerShop.playerEquipmentN[i] = 1;
            i++;
        }
    }

    private static String getShopName(String playerName) {
        return "♥" + playerName;
    }

    private static Client getPlayerShop(String playerName) {
        String shopName = getShopName(playerName);
        for (Bot bot : botList) {
            if (bot != null && bot.getBotClient() != null) {
                Client botClient = bot.getBotClient();
                if (botClient.playerName.equalsIgnoreCase(shopName)) {
                    return botClient;
                }
            }
        }
        return null;
    }

    private static Client getPlayerShop(int shop_id) {
        for (Bot bot : botList) {
            if (bot != null && bot.getBotClient() != null) {
                Client botClient = bot.getBotClient();
                if (botClient.shopId == shop_id) {
                    return botClient;
                }
            }
        }
        return null;
    }

    public static void closeShop(Player player) {
        Client shop = getPlayerShop(player.playerName);
        if (shop == null) return;
        ShopHandler.closePlayerShop(shop);
        shop.getPlayerAssistant().movePlayer(0, 0, 0);
        new Thread(() -> {
            try {
                Thread.sleep(500);
                shop.disconnected = true;
                shop.logout(true);
                for (int index = 0; index < botList.size(); index++) {
                    if (botList.get(index).getBotClient().properName.equalsIgnoreCase(player.properName)) {
                        botList.remove(index);
                        return;
                    }
                    index++;
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public static void addCoins(int shop_id, int amount) {
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().addItem(currency, amount);
    }

    public static int checkCoins(Player player) {
        Client shop = getPlayerShop(player.playerName);
        if (shop == null) return 0;
        if (!shop.getItemAssistant().playerHasItem(currency)) {
            return 0;
        }
        return shop.getItemAssistant().getItemAmount(currency);
    }

    public static void takeCoins(Player player) {
        if (!player.getItemAssistant().playerHasItem(currency) && player.getItemAssistant().freeSlots() <= 0) {
            player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
            return;
        }
        Client shop = getPlayerShop(player.playerName);
        if (shop == null) return;
        if (!shop.getItemAssistant().playerHasItem(currency)) {
            player.getPacketSender().sendMessage("There are no coins to collect.");
            return;
        }
        int totalCoins = shop.getItemAssistant().getItemAmount(currency);
        player.getItemAssistant().addItem(currency, totalCoins);
        shop.getItemAssistant().deleteItem(currency, totalCoins);
        player.getPacketSender().sendMessage("You collected " + totalCoins + " coins.");
    }

    public static void addTobank(int shop_id, int item_id, int amount) {
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().addItemToBank(item_id, amount);
    }

    public static void removeFrombank(int shop_id, int item_id, int amount) {
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().removeItemFromBank(item_id, amount);
    }

    public static int getItemPrice(int shop_id, int item_id) {
        item_id++;
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return 0;
        for (int slot = 0; slot < ShopHandler.MAX_SHOP_ITEMS; slot++) {
            if (shop.bankItems[slot] == item_id) {
                return Math.max(1, shop.bankItemsV[slot]);
            }
        }
        return 0;
    }

    public static void setPrice(int shop_id, int item_id, int amount) {
        item_id++;
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        for (int slot = 0; slot < ShopHandler.MAX_SHOP_ITEMS; slot++) {
            if (shop.bankItems[slot] == item_id) {
                shop.bankItemsV[slot] = amount;
            }
        }
    }
}
