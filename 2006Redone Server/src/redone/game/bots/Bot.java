package redone.game.bots;

import redone.Server;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.GameLogger;
import redone.util.Misc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static redone.game.players.PlayerSave.loadPlayerInfo;

public class Bot {

    private Client botClient;
    static Timer timer = new Timer();

    public Bot(String username, int x, int y, int z) {
        botClient = new Client(null);
        botClient.playerName = username;

        botClient.playerName = username;
        botClient.playerName2 = botClient.playerName;
        // TODO: randomize the bot passwords
        botClient.playerPass = "bot_password";

        botClient.properName = Character.toUpperCase(username.charAt(1)) + username.substring(2);

        botClient.saveFile = true;
        botClient.saveCharacter = true;
        botClient.isActive = true;
        botClient.disconnected = false;
        Server.playerHandler.newPlayerClient(botClient);

        botClient.getPlayerAssistant().movePlayer(x, y, z);

        loadPlayerInfo(botClient, username, "bot_password", false);
        new TradeChat().run();
    }

    public Client getBotClient() {
        return botClient;
    }

    class TradeChat extends TimerTask {
        @Override
        public void run() {
            sendTradeChat();
            int delay = (5 + new Random().nextInt(15)) * 1000;
            timer.schedule(new TradeChat(), delay);
        }

    }

    public void sendTradeChat() {
        ArrayList<Integer> items = new ArrayList<Integer>();
        for (int slot = 0; slot < 40; slot++){
            if(botClient.bankItems[slot] > 0)
                items.add(botClient.bankItems[slot] - 1);
        }
        int item_id = Misc.randomArrayListItem(items);
        String item_name = ItemAssistant.getItemName(item_id);
        int value = Math.max(1, botClient.getShopAssistant().getItemShopValue(item_id, 0, false));
        botClient.forcedChat("Selling " + item_name + " " + formatSellPrice(value) + " ea");
        /*
        Real chat - Disabled for now, can't get it to function correctly

        botClient.setChatTextColor(9);
        botClient.setChatTextEffects(2);
        String message = "Selling Rune Platebody 210k ea - " + botClient.playerName;
        botClient.setChatTextSize((byte) 29);
        botClient.setChatText(message.getBytes(StandardCharsets.UTF_8));
        botClient.inStream.readBytes_reverseA(botClient.getChatText(), botClient.getChatTextSize(), 0);
        botClient.setChatTextUpdateRequired(true);
        */
    }

    private String formatSellPrice(int price) {
        if (price > 1e9) {
            return (Math.floor(price / 1e8) / 10) + "B";
        } else if (price > 1e6) {
            return (Math.floor(price / 1e8) / 10) + "M";
        } else if (price > 1e3) {
            return (Math.floor(price / 100) / 10) + "K";
        } else {
            return "" + price;
        }
    }
}
