package redone.integrations.discord;

import redone.game.players.PlayerHandler;

public class DiscordActivity {

    private static int count = 50;

    public static void updateActivity() {
        if (JavaCord.token != null && !JavaCord.token.equals("")) {
            if (count == 0) {
                if (PlayerHandler.getPlayerCount() != 1) {
                    JavaCord.api.updateActivity(PlayerHandler.getPlayerCount() + " Players Online");
                    System.out.println("Discord Activity Updated");
                    count = 100;
                } else {
                    JavaCord.api.updateActivity(PlayerHandler.getPlayerCount() + " Player Online");
                    System.out.println("Discord Activity Updated");
                    count = 100;
                }
            } else {
                count--;
            }
        }
    }
}
