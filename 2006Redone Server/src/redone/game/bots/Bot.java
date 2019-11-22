package redone.game.bots;


import redone.Server;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;

import static redone.game.players.PlayerSave.loadPlayerInfo;

public class Bot {

    private Client botClient;

    public Bot(String username) {
        botClient = new Client(null, -1);
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
    }

    public Client getBotClient() {
        return botClient;
    }
}
