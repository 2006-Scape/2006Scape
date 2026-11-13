package com.rs2;

import com.rs2.event.impl.ServerConfigEvent;
import com.rs2.game.players.Player;
import com.rs2.integrations.PlayersOnlineWebsite;
import com.rs2.integrations.RegisteredAccsWebsite;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class ConfigLoader {
    private static String currentConfigFile = null;
    private static JSONObject currentConfigJson = new JSONObject();

    public static JSONObject getCurrentConfig() {
        return currentConfigJson;
    }

    public static String getCurrentConfigFile() {
        return currentConfigFile;
    }

    public static void dispatchConfigEvent() {
        Player.postGlobal(new ServerConfigEvent(currentConfigJson));
    }

    public static void loadSettings(String config) throws IOException {
        currentConfigFile = config;
        String out;
        try (
            BufferedReader br = new BufferedReader(new FileReader(config))) {
            out = br.lines().collect(Collectors.joining("\n"));
        }
        JSONObject obj = new JSONObject(out);
        currentConfigJson = obj;

        JSONObject core = obj.optJSONObject("core");
        if (core == null) {
            System.err.println("Configuration error: Missing 'core' configuration group in " + config);
            return;
        }

        if (core.has("server_name"))
            Constants.SERVER_NAME = core.getString("server_name");
        if (core.has("server_test_version"))
            Constants.TEST_VERSION = core.getDouble("server_test_version");
        if (core.has("gui_enabled"))
            Constants.GUI_ENABLED = core.getBoolean("gui_enabled");
        if (core.has("website_link"))
            Constants.WEBSITE_LINK = core.getString("website_link");
        if (core.has("server_debug"))
            Constants.SERVER_DEBUG = core.getBoolean("server_debug");
        if (core.has("file_server"))
            Constants.FILE_SERVER = core.getBoolean("file_server");
        if (core.has("http_port"))
            Constants.HTTP_PORT = core.getInt("http_port");
        if (core.has("jaggrab_port"))
            Constants.JAGGRAB_PORT = core.getInt("jaggrab_port");
        if (core.has("world_id"))
            Constants.WORLD = core.getInt("world_id");
        if (core.has("members_only"))
            Constants.MEMBERS_ONLY = core.getBoolean("members_only");
        if (core.has("tutorial_island_enabled"))
            Constants.TUTORIAL_ISLAND = core.getBoolean("tutorial_island_enabled");
        if (core.has("party_room_enabled"))
            Constants.PARTY_ROOM_DISABLED = !core.getBoolean("party_room_enabled");
        if (core.has("clues_enabled"))
            Constants.CLUES_ENABLED = core.getBoolean("clues_enabled");
        if (core.has("admin_can_trade"))
            Constants.ADMIN_CAN_TRADE = core.getBoolean("admin_can_trade");
        if (core.has("admin_can_drop_items"))
            Constants.ADMIN_DROP_ITEMS = core.getBoolean("admin_can_drop_items");
        if (core.has("admin_can_sell"))
            Constants.ADMIN_CAN_SELL_ITEMS = core.getBoolean("admin_can_sell");
        if (core.has("respawn_x"))
            Constants.RESPAWN_X = core.getInt("respawn_x");
        if (core.has("respawn_y"))
            Constants.RESPAWN_Y = core.getInt("respawn_y");
        if (core.has("save_timer"))
            Constants.SAVE_TIMER = core.getInt("save_timer");
        if (core.has("timeout"))
            Constants.TIMEOUT = core.getInt("timeout");
        if (core.has("item_requirements"))
            Constants.ITEM_REQUIREMENTS = core.getBoolean("item_requirements");
        if (core.has("variable_xp_rate"))
            Constants.VARIABLE_XP_RATE = core.getBoolean("variable_xp_rate");
        if (core.has("xp_rate"))
            Constants.XP_RATE = core.getDouble("xp_rate");
        if (core.has("max_players"))
            Constants.MAX_PLAYERS = core.getInt("max_players");
        if (core.has("variable_xp_rates")) {
            JSONArray rates = core.optJSONArray("variable_xp_rates");
            if (rates != null) {
                for (int i = 0; i < rates.length(); ++i) {
                    Constants.VARIABLE_XP_RATES[i] = rates.optInt(i);
                }
            }
        }
        if (core.has("website_integration"))
            Constants.WEBSITE_INTEGRATION = core.getBoolean("website_integration");
        
        if (core.has("cycle_logging")) 
            Constants.CYCLE_LOGGING = core.getBoolean("cycle_logging");
        if (core.has("cycle_logging_tick")) 
            Constants.CYCLE_LOGGING_TICK = core.getInt("cycle_logging_tick");
    }

    private static void initialize() {
        JSONObject main = new JSONObject();
        main
                .put("websitepass", "")
                .put("erssecret", "");
        try (BufferedWriter br = new BufferedWriter(new FileWriter("data/secrets.json"))) {
            br.write(main.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSecrets() throws IOException {
        if (!new File("data/secrets.json").exists()) {
            initialize();
            System.out.println("Please open \"data/secrets.json\" file and enter your Website Password there!");

        } else {
            String out;
            try (
                BufferedReader br = new BufferedReader(new FileReader("data/secrets.json"))) {
                out = br.lines().collect(Collectors.joining("\n"));
            }
            JSONObject obj = new JSONObject(out);

            /*
             * Sets External Services Vars
             */
            if (obj.has("websitepass")) {
                String pass = obj.getString("websitepass");
                PlayersOnlineWebsite.password = pass;
                RegisteredAccsWebsite.password = pass;
            }
            if (obj.has("erssecret"))
                GameEngine.ersSecret = obj.getString("erssecret");

        }
    }
}
