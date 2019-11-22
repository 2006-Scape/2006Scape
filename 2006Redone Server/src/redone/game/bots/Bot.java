package redone.game.bots;

import redone.Server;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;

import java.nio.charset.StandardCharsets;
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
        botClient.forcedChat("Selling Rune Platebody 210k ea");
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
}
