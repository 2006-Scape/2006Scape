package com.rs2.game.bots;

import static com.rs2.game.players.PlayerSave.loadPlayerInfo;

import java.text.DecimalFormat;
import java.util.*;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Client;
import com.rs2.util.Misc;
import com.rs2.util.Stream;

public class Bot {

    private Client botClient;
    static Timer timer = new Timer();
    private Stream inStream;
    
    public Bot(String username, Integer x, Integer y, Integer z) {
        botClient = new Client(null);
        inStream = new Stream(new byte[Constants.BUFFER_SIZE]);
        inStream.currentOffset = 0;
        botClient.playerName = username;
        botClient.playerName2 = botClient.playerName;
        botClient.properName = Character.toUpperCase(username.charAt(1)) + username.substring(2);
        // TODO: randomize the bot passwords
        botClient.playerPass = generatePassword(10);


        botClient.saveFile = true;
        botClient.saveCharacter = true;
        botClient.isActive = true;
        botClient.disconnected = false;
        botClient.npcCanAttack = false;
        botClient.canWalkTutorial = true;
        botClient.tutorialProgress = 36;
        botClient.privateChat = 2;
        GameEngine.playerHandler.newPlayerClient(botClient);

        // password doesn't matter as it's not doing a real login
        loadPlayerInfo(botClient, username, botClient.playerPass, false);

        if (x != null) {
            botClient.getPlayerAssistant().movePlayer(x, y, z);
        }
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
        String item_name = DeprecatedItems.getItemName(item_id).toLowerCase();
        int value = BotHandler.getItemPrice(botClient.shopId, item_id);
        if (value <= 0) return;

        String _message = "Selling " + item_name + " " + formatSellPrice(value) + " ea - " + botClient.playerName;

        // Disable the force chat for now, maybe use that instead, maybe not
        //botClient.forcedChat("Selling " + item_name + " " + formatSellPrice(value) + " ea");

        // Normal chat from here down:
        botClient.setChatTextColor(Misc.random(11));
        botClient.setChatTextEffects(Misc.random(5));
        Misc.textPack(inStream, _message);
        botClient.setChatTextSize((byte) inStream.currentOffset);
        botClient.setChatText(inStream.buffer);
        inStream.currentOffset = 0;
        botClient.setChatTextUpdateRequired(true);
    }

    public static String formatSellPrice(int price) {
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

    public String generatePassword(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
