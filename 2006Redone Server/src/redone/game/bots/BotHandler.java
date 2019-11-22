package redone.game.bots;

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
        Bot playerShop = getPlayerShop(player);

        if (playerShop == null) {
            String shopName = getShopName(player);
            playerShop = connectBot(shopName, player.getX(), player.getY(), player.getH());
            ShopHandler.createPlayerShop(playerShop.getBotClient());
        }

        Client client = playerShop.getBotClient();
        client.getPlayerAssistant().movePlayer(player.getX(), player.getY(), player.getH());
        client.getItemAssistant().removeAllItems();
        // Set bot to same level as player
        int i = 0;
        for (int level : player.playerLevel) {
            client.playerXP[i] = player.getPlayerAssistant().getXPForLevel(level) + 5;
            client.playerLevel[i] = level;
            client.getPlayerAssistant().refreshSkill(i);
            client.getPlayerAssistant().levelUp(i);
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

    private static Bot getPlayerShop(Client player){
        String shopName = getShopName(player);
        for(Bot bot : botList) {
            if(bot != null && bot.getBotClient() != null) {
                Client botClient = bot.getBotClient();
                if(botClient.playerName.equalsIgnoreCase(shopName)) {
                    return bot;
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
        if (shop == null) return 1;
        for (int slot = 0; slot < ShopHandler.MaxShopItems; slot++) {
            if (shop.bankItems[slot] == item_id) {
                return Math.max(1, shop.bankItemsV[slot]);
            }
        }
        return 1;
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
