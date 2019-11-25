package com.rebotted.game.bots;

import static com.rebotted.game.players.PlayerSave.loadPlayerInfo;

import java.text.DecimalFormat;
import java.util.*;

import com.rebotted.GameEngine;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Client;
import com.rebotted.util.Misc;

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
        botClient.npcCanAttack = false;
        GameEngine.playerHandler.newPlayerClient(botClient);

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
        if (items.size() <= 0) return;
        int item_id = Misc.randomArrayListItem(items);
        String item_name = ItemAssistant.getItemName(item_id).toLowerCase();
        int value = BotHandler.getItemPrice(botClient.myShopId, item_id);
        if (value <= 0) return;

        String _message = "Selling " + item_name + " " + formatSellPrice(value) + " ea - " + botClient.playerName;

        // Disable the force chat for now, maybe use that instead, maybe not
        //botClient.forcedChat("Selling " + item_name + " " + formatSellPrice(value) + " ea");

        // Normal chat from here down:
        botClient.setChatTextColor(Misc.random(11));
        botClient.setChatTextEffects(Misc.random(5));
        Misc.textPack(botClient.inStream, _message);
        botClient.setChatTextSize((byte) botClient.inStream.currentOffset);
        botClient.setChatText(botClient.inStream.buffer);
        botClient.inStream.currentOffset = 0;
        botClient.setChatTextUpdateRequired(true);
    }

    private String formatSellPrice(int price) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (price >= 1e9) {
            return df.format(Math.floor(price / 1e8) / 10) + "b";
        } else if (price >= 1e6) {
            return df.format(Math.floor(price / 1e5) / 10) + "m";
        } else if (price >= 1e3) {
            return df.format(Math.floor(price / 100) / 10) + "k";
        } else {
            return price + "gp";
        }
    }
}
