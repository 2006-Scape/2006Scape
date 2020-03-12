package com.rebotted.integrations;

import java.io.IOException;
import java.net.URL;

import com.rebotted.game.players.PlayerHandler;

public class PlayersOnlineWebsite {

    static String password;
    private static boolean hasntwared = true;

    private static void setWebsitePlayersOnline(int amount) throws IOException {
        URL url;
        url = new URL("https://rsrebotted.com/playersonline.php?pass=" + password + "&amount=" + amount);
        url.openStream().close();
    }

    private static int count = 50;
    public static void addUpdatePlayersOnlineTask() {
        if (password != null && !password.equals("")) {
            if (count == 0) {
                try {
                    setWebsitePlayersOnline(PlayerHandler.getPlayerCount());
                    count = 50;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                count--;
            }
        } else if (hasntwared) {
            hasntwared = false;
            System.out.println("No Website Password Set So Website Integration Tasks Stopped");
        }
    }
}
