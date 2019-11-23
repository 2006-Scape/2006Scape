package redone.game.bots;

import io.netty.util.Timeout;
import redone.Constants;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.game.shops.ShopHandler;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotHandler
{
    static final List<Bot> botList = new ArrayList<>(BotConstants.MAX_BOTS);
    static final Random random = new SecureRandom();
    static final int currency = 995;

    public static Bot connectBot(String username, int x, int y, int z) {
        Bot bot;
        if (PlayerHandler.playerCount >= Constants.MAX_PLAYERS) {
            System.out.println("Bot could not be connected, server is full.");
            return null;
        }

        bot = new Bot(username, x, y, z);
        botList.add(bot);
        return bot;
    }

    public static void playerShop(Client player){
        Client playerShop = getPlayerShop(player);

        if (playerShop == null) {
            String shopName = getShopName(player);
            Bot bot = connectBot(shopName, player.getX(), player.getY(), player.getH());
            playerShop = bot.getBotClient();
            ShopHandler.createPlayerShop(playerShop);
        }

        Client client = playerShop;
        client.getPlayerAssistant().movePlayer(player.getX(), player.getY(), player.getH());


        int i = 0;
        // Remove all items except money
        for (int item_id : client.playerItems) {
            if (item_id > 0 && item_id != currency + 1){
                client.playerItems[i] = 0;
                client.playerItemsN[i] = 0;
            }
            i++;
        }
        // Set bot to same level as player
        i = 0;
        for (int level : player.playerLevel) {
            client.playerLevel[i] = level;
            client.getPlayerAssistant().refreshSkill(i);
            i++;
        }
        // Take the appearance of the player
        i = 0;
        for (int id : player.playerAppearance) {
            client.playerAppearance[i] = id;
            i++;
        }
        // Dress the bot the same as the player
        i = 0;
        for (int item_id : player.playerEquipment) {
            client.playerEquipment[i] = item_id;
            client.playerEquipmentN[i] = 1;
            i++;
        }
    }

    private static String getShopName(Client player){
        return "♥" + player.playerName;
    }

    private static Client getPlayerShop(Client player){
        String shopName = getShopName(player);
        for(Bot bot : botList) {
            if(bot != null && bot.getBotClient() != null) {
                Client botClient = bot.getBotClient();
                if(botClient.playerName.equalsIgnoreCase(shopName)) {
                    return botClient;
                }
            }
        }
        return null;
    }

    private static Client getPlayerShop(int shop_id){
        for(Bot bot : botList) {
            if(bot != null && bot.getBotClient() != null) {
                Client botClient = bot.getBotClient();
                if(botClient.myShopId == shop_id) {
                    return botClient;
                }
            }
        }
        return null;
    }

    public static void closeShop(Client player) {
        Client shop = getPlayerShop(player);
        if (shop == null) return;
        shop.getPlayerAssistant().movePlayer(0,0,0);
        new Thread(() -> {
            try {
                Thread.sleep(500);
                shop.disconnected = true;
                shop.logout(true);
                for (int index = 0; index < botList.size(); index++){
                    if (botList.get(index).getBotClient().properName.equalsIgnoreCase(player.properName)) {
                        botList.remove(index);
                        return;
                    }
                    index++;
                }
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public static void addCoins(int shop_id, int amount){
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().addItem(currency, amount);
    }

    public static void takeCoins(Client player){
        if (!player.getItemAssistant().playerHasItem(currency) || player.getItemAssistant().freeSlots() <= 0) {
            player.getActionSender().sendMessage("You don't have enough space in your inventory.");
            return;
        }
        Client shop = getPlayerShop(player);
        if (shop == null) return;
        if (!shop.getItemAssistant().playerHasItem(currency)) {
            player.getActionSender().sendMessage("There are no coins to collect.");
            return;
        }
        int totalCoins = shop.getItemAssistant().getItemAmount(currency);
        player.getItemAssistant().addItem(currency, totalCoins);
        shop.getItemAssistant().deleteItem(currency, totalCoins);
    }

    public static void addTobank(int shop_id, int item_id, int amount){
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().addItemToBank(item_id, amount);
    }

    public static void removeFrombank(int shop_id, int item_id, int amount){
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        shop.getItemAssistant().removeitemFromBank(item_id, amount);
    }

    public static int getItemPrice(int shop_id, int item_id){
        item_id++;
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return 0;
        for (int slot = 0; slot < ShopHandler.MaxShopItems; slot++) {
            if (shop.bankItems[slot] == item_id) {
                return Math.max(1, shop.bankItemsV[slot]);
            }
        }
        return 0;
    }

    public static void setPrice(int shop_id, int item_id, int amount){
        item_id++;
        Client shop = getPlayerShop(shop_id);
        if (shop == null) return;
        for (int slot = 0; slot < ShopHandler.MaxShopItems; slot++) {
            if (shop.bankItems[slot] == item_id) {
                shop.bankItemsV[slot] = amount;
            }
        }
    }

}
