package com.rebotted.integrations.discord;

import com.rebotted.game.players.PlayerHandler;

public class DiscordActivity {

    private static int count = 50;

    public static void updateActivity() {
        try {
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

        } catch (Exception e) {
            System.out.println("Could not set Discord activity: " + e);
            System.out.println("Null checks");
            try {
                System.out.println("JavaCord.api: " + JavaCord.api);
                System.out.println("PlayerHandler.getPlayerCount: " + PlayerHandler.getPlayerCount());
            } catch (Exception e2) {
                System.out.println(e2);
            }
        }
    }
}
