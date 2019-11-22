package redone.game.bots;


import redone.Server;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static redone.game.players.PlayerSave.loadPlayerInfo;

public class Bot {

    private Client botClient;
    static Timer timer = new Timer();

    public Bot(String username) {
        botClient = new Client(null);
        botClient.playerName = username;

        botClient.playerName = username;
        botClient.playerName2 = botClient.playerName;
        botClient.playerPass = "bot_password";

        botClient.saveCharacter = true;
        char first = username.charAt(0);
        botClient.properName = Character.toUpperCase(first) + username.substring(1, username.length());

        botClient.saveFile = true;
        botClient.saveCharacter = true;
        botClient.isActive = true;
        botClient.disconnected = false;
        System.out.println(botClient.getPlayerAssistant().getTotalLevel());
        Server.playerHandler.newPlayerClient(botClient);
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
            int delay = (15 + new Random().nextInt(25)) * 1000;
            timer.schedule(new TradeChat(), delay);
        }

    }

    public void sendTradeChat() {
        botClient.setChatTextColor(9);
        botClient.setChatTextEffects(2);
        botClient.forcedChat("<col=#FF0033>Selling Rune Platebody 210k ea - " + botClient.playerName + "</col>");
    }
}
