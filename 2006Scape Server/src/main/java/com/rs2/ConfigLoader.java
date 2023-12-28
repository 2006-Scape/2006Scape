package com.rs2;

import com.rs2.integrations.PlayersOnlineWebsite;
import com.rs2.integrations.RegisteredAccsWebsite;
import com.rs2.integrations.discord.JavaCord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class ConfigLoader {

    public static void loadSettings(String config) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(config));
        String out = br.lines().collect(Collectors.joining("\n"));
        JSONObject obj = new JSONObject(out);

        if (obj.has("server_name"))
            Constants.SERVER_NAME = obj.getString("server_name");
        if (obj.has("server_test_version"))
            Constants.TEST_VERSION = obj.getDouble("server_test_version");
        if (obj.has("gui_enabled"))
            Constants.GUI_ENABLED = obj.getBoolean("gui_enabled");
        if (obj.has("website_link"))
            Constants.WEBSITE_LINK = obj.getString("website_link");
        if (obj.has("server_debug"))
            Constants.SERVER_DEBUG = obj.getBoolean("server_debug");
        if (obj.has("file_server"))
            Constants.FILE_SERVER = obj.getBoolean("file_server");
        if (obj.has("http_port"))
            Constants.HTTP_PORT = obj.getInt("http_port");
        if (obj.has("jaggrab_port"))
            Constants.JAGGRAB_PORT = obj.getInt("jaggrab_port");
        if (obj.has("world_id"))
            Constants.WORLD = obj.getInt("world_id");
        if (obj.has("members_only"))
            Constants.MEMBERS_ONLY = obj.getBoolean("members_only");
        if (obj.has("tutorial_island_enabled"))
            Constants.TUTORIAL_ISLAND = obj.getBoolean("tutorial_island_enabled");
        if (obj.has("party_room_enabled"))
            Constants.PARTY_ROOM_DISABLED = !obj.getBoolean("party_room_enabled");
        if (obj.has("clues_enabled"))
            Constants.CLUES_ENABLED = obj.getBoolean("clues_enabled");
        if (obj.has("admin_can_trade"))
            Constants.ADMIN_CAN_TRADE = obj.getBoolean("admin_can_trade");
        if (obj.has("admin_can_drop_items"))
            Constants.ADMIN_DROP_ITEMS = obj.getBoolean("admin_can_drop_items");
        if (obj.has("admin_can_sell"))
            Constants.ADMIN_CAN_SELL_ITEMS = obj.getBoolean("admin_can_sell");
        if (obj.has("respawn_x"))
            Constants.RESPAWN_X = obj.getInt("respawn_x");
        if (obj.has("respawn_y"))
            Constants.RESPAWN_Y = obj.getInt("respawn_y");
        if (obj.has("save_timer"))
            Constants.SAVE_TIMER = obj.getInt("save_timer");
        if (obj.has("timeout"))
            Constants.TIMEOUT = obj.getInt("timeout");
        if (obj.has("item_requirements"))
            Constants.ITEM_REQUIREMENTS = obj.getBoolean("item_requirements");
        if (obj.has("variable_xp_rate"))
            Constants.VARIABLE_XP_RATE = obj.getBoolean("variable_xp_rate");
        if (obj.has("xp_rate"))
            Constants.XP_RATE = obj.getDouble("xp_rate");
        if (obj.has("max_players"))
            Constants.MAX_PLAYERS = obj.getInt("max_players");
        if (obj.has("variable_xp_rates")) {
            JSONArray rates = obj.optJSONArray("variable_xp_rates");
            for (int i = 0; i < rates.length(); ++i) {
                Constants.VARIABLE_XP_RATES[i] = rates.optInt(i);
            }
        }
        if (obj.has("website_integration"))
            Constants.WEBSITE_INTEGRATION = obj.getBoolean("website_integration");
        
        if (obj.has("cycle_logging")) 
            Constants.CYCLE_LOGGING = obj.getBoolean("cycle_logging");
        if (obj.has("cycle_logging_tick")) 
            Constants.CYCLE_LOGGING_TICK = obj.getInt("cycle_logging_tick");
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
        if (!new File("data/secrets.json").exists()) {
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
            if (obj.has("bot-token"))
                JavaCord.token = obj.getString("bot-token");
            if (obj.has("websitepass"))
                PlayersOnlineWebsite.password = obj.getString("websitepass");
            RegisteredAccsWebsite.password = obj.getString("websitepass");
            if (obj.has("erssecret"))
                GameEngine.ersSecret = obj.getString("erssecret");

        }
    }
}
