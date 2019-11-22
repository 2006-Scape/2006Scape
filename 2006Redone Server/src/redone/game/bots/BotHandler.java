package redone.game.bots;

import redone.Constants;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

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
        }

        if (playerShop == null) return;

        playerShop.getBotClient().getPlayerAssistant().movePlayer(player.getX(), player.getY(), player.getH());
        playerShop.getBotClient().getItemAssistant().removeAllItems();
        int i = 0;
        for (int level : player.playerLevel) {
            playerShop.getBotClient().playerXP[i] = player.getPlayerAssistant().getXPForLevel(level) + 5;
            playerShop.getBotClient().playerLevel[i] = level;
            playerShop.getBotClient().getPlayerAssistant().refreshSkill(i);
            playerShop.getBotClient().getPlayerAssistant().levelUp(i);
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

}
