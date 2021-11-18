package com.rs2;

import com.rs2.integrations.PlayersOnlineWebsite;
import com.rs2.integrations.RegisteredAccsWebsite;
import com.rs2.integrations.discord.JavaCord;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class ConfigLoader {

    public static void loadSettings(String config) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(config));
            String out = br.lines().collect(Collectors.joining("\n"));
            JSONObject obj = new JSONObject(out);

            if(obj.has("server_name"))
            GameConstants.SERVER_NAME = obj.getString("server_name");
            if(obj.has("debug"))
            GameConstants.SERVER_DEBUG = obj.getBoolean("debug");
            if(obj.has("file_server"))
            GameConstants.FILE_SERVER = obj.getBoolean("file_server");
            if(obj.has("world_id"))
            GameConstants.WORLD = obj.getInt("world_id");
            if(obj.has("tutorial_island"))
            GameConstants.TUTORIAL_ISLAND = obj.getBoolean("tutorial_island");
            if(obj.has("admin_can_trade"))
            GameConstants.ADMIN_CAN_TRADE = obj.getBoolean("admin_can_trade");
            if(obj.has("admin_can_sell"))
            GameConstants.ADMIN_CAN_SELL_ITEMS = obj.getBoolean("admin_can_sell");
            if(obj.has("respawn_x"))
            GameConstants.RESPAWN_X = obj.getInt("respawn_x");
            if(obj.has("respawn_y"))
            GameConstants.RESPAWN_Y = obj.getInt("respawn_y");
            if(obj.has("item_requirements"))
            GameConstants.ITEM_REQUIREMENTS = obj.getBoolean("item_requirements");
            if(obj.has("xp_rate"))
            GameConstants.XP_RATE = obj.getDouble("xp_rate");
    }

    private static void initialize() {
        JSONObject main = new JSONObject();
        main
                .put("bot-token", "")
                .put("websitepass", "")
                .put("erssecret", "");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("data/secrets.json"));
            br.write(main.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSecrets() throws IOException {
        if (!new File("data/Secrets.json").exists()) {
            initialize();
            System.out.println("Please open \"data/secrets.json\" file and enter your discord token bot there!");
            System.out.println("Please open \"data/secrets.json\" file and enter your Website Password there!");

        } else {
            BufferedReader br = new BufferedReader(new FileReader("data/secrets.json"));
            String out = br.lines().collect(Collectors.joining("\n"));
            JSONObject obj = new JSONObject(out);

            /*
             * Sets External Services Vars
             */
            if(obj.has("bot-token"))
                JavaCord.token = obj.getString("bot-token");
            if(obj.has("websitepass"))
                PlayersOnlineWebsite.password = obj.getString("websitepass");
            RegisteredAccsWebsite.password = obj.getString("websitepass");
            if(obj.has("erssecret"))
                GameEngine.ersSecret = obj.getString("erssecret");

        }
    }
}
