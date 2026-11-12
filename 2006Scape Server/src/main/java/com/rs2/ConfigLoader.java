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

    private static boolean has(JSONObject core, JSONObject root, String key) {
        return (core != null && core.has(key)) || root.has(key);
    }

    private static String getString(JSONObject core, JSONObject root, String key, String defaultValue) {
        if (core != null && core.has(key)) return core.getString(key);
        if (root.has(key)) return root.getString(key);
        return defaultValue;
    }

    private static int getInt(JSONObject core, JSONObject root, String key, int defaultValue) {
        if (core != null && core.has(key)) return core.getInt(key);
        if (root.has(key)) return root.getInt(key);
        return defaultValue;
    }

    private static boolean getBoolean(JSONObject core, JSONObject root, String key, boolean defaultValue) {
        if (core != null && core.has(key)) return core.getBoolean(key);
        if (root.has(key)) return root.getBoolean(key);
        return defaultValue;
    }

    private static double getDouble(JSONObject core, JSONObject root, String key, double defaultValue) {
        if (core != null && core.has(key)) return core.getDouble(key);
        if (root.has(key)) return root.getDouble(key);
        return defaultValue;
    }

    private static JSONArray getJSONArray(JSONObject core, JSONObject root, String key) {
        if (core != null && core.has(key)) return core.optJSONArray(key);
        return root.optJSONArray(key);
    }

    public static void loadSettings(String config) throws IOException {
        currentConfigFile = config;
        BufferedReader br = new BufferedReader(new FileReader(config));
        String out = br.lines().collect(Collectors.joining("\n"));
        JSONObject obj = new JSONObject(out);
        currentConfigJson = obj;

        JSONObject core = obj.optJSONObject("core");

        if (has(core, obj, "server_name"))
            Constants.SERVER_NAME = getString(core, obj, "server_name", Constants.SERVER_NAME);
        if (has(core, obj, "server_test_version"))
            Constants.TEST_VERSION = getDouble(core, obj, "server_test_version", Constants.TEST_VERSION);
        if (has(core, obj, "gui_enabled"))
            Constants.GUI_ENABLED = getBoolean(core, obj, "gui_enabled", Constants.GUI_ENABLED);
        if (has(core, obj, "website_link"))
            Constants.WEBSITE_LINK = getString(core, obj, "website_link", Constants.WEBSITE_LINK);
        if (has(core, obj, "server_debug"))
            Constants.SERVER_DEBUG = getBoolean(core, obj, "server_debug", Constants.SERVER_DEBUG);
        if (has(core, obj, "file_server"))
            Constants.FILE_SERVER = getBoolean(core, obj, "file_server", Constants.FILE_SERVER);
        if (has(core, obj, "http_port"))
            Constants.HTTP_PORT = getInt(core, obj, "http_port", Constants.HTTP_PORT);
        if (has(core, obj, "jaggrab_port"))
            Constants.JAGGRAB_PORT = getInt(core, obj, "jaggrab_port", Constants.JAGGRAB_PORT);
        if (has(core, obj, "world_id"))
            Constants.WORLD = getInt(core, obj, "world_id", Constants.WORLD);
        if (has(core, obj, "members_only"))
            Constants.MEMBERS_ONLY = getBoolean(core, obj, "members_only", Constants.MEMBERS_ONLY);
        if (has(core, obj, "tutorial_island_enabled"))
            Constants.TUTORIAL_ISLAND = getBoolean(core, obj, "tutorial_island_enabled", Constants.TUTORIAL_ISLAND);
        if (has(core, obj, "party_room_enabled"))
            Constants.PARTY_ROOM_DISABLED = !getBoolean(core, obj, "party_room_enabled", !Constants.PARTY_ROOM_DISABLED);
        if (has(core, obj, "clues_enabled"))
            Constants.CLUES_ENABLED = getBoolean(core, obj, "clues_enabled", Constants.CLUES_ENABLED);
        if (has(core, obj, "admin_can_trade"))
            Constants.ADMIN_CAN_TRADE = getBoolean(core, obj, "admin_can_trade", Constants.ADMIN_CAN_TRADE);
        if (has(core, obj, "admin_can_drop_items"))
            Constants.ADMIN_DROP_ITEMS = getBoolean(core, obj, "admin_can_drop_items", Constants.ADMIN_DROP_ITEMS);
        if (has(core, obj, "admin_can_sell"))
            Constants.ADMIN_CAN_SELL_ITEMS = getBoolean(core, obj, "admin_can_sell", Constants.ADMIN_CAN_SELL_ITEMS);
        if (has(core, obj, "respawn_x"))
            Constants.RESPAWN_X = getInt(core, obj, "respawn_x", Constants.RESPAWN_X);
        if (has(core, obj, "respawn_y"))
            Constants.RESPAWN_Y = getInt(core, obj, "respawn_y", Constants.RESPAWN_Y);
        if (has(core, obj, "save_timer"))
            Constants.SAVE_TIMER = getInt(core, obj, "save_timer", Constants.SAVE_TIMER);
        if (has(core, obj, "timeout"))
            Constants.TIMEOUT = getInt(core, obj, "timeout", Constants.TIMEOUT);
        if (has(core, obj, "item_requirements"))
            Constants.ITEM_REQUIREMENTS = getBoolean(core, obj, "item_requirements", Constants.ITEM_REQUIREMENTS);
        if (has(core, obj, "variable_xp_rate"))
            Constants.VARIABLE_XP_RATE = getBoolean(core, obj, "variable_xp_rate", Constants.VARIABLE_XP_RATE);
        if (has(core, obj, "xp_rate"))
            Constants.XP_RATE = getDouble(core, obj, "xp_rate", Constants.XP_RATE);
        if (has(core, obj, "max_players"))
            Constants.MAX_PLAYERS = getInt(core, obj, "max_players", Constants.MAX_PLAYERS);
        if (has(core, obj, "variable_xp_rates")) {
            JSONArray rates = getJSONArray(core, obj, "variable_xp_rates");
            if (rates != null) {
                for (int i = 0; i < rates.length(); ++i) {
                    Constants.VARIABLE_XP_RATES[i] = rates.optInt(i);
                }
            }
        }
        if (has(core, obj, "website_integration"))
            Constants.WEBSITE_INTEGRATION = getBoolean(core, obj, "website_integration", Constants.WEBSITE_INTEGRATION);
        
        if (has(core, obj, "cycle_logging")) 
            Constants.CYCLE_LOGGING = getBoolean(core, obj, "cycle_logging", Constants.CYCLE_LOGGING);
        if (has(core, obj, "cycle_logging_tick")) 
            Constants.CYCLE_LOGGING_TICK = getInt(core, obj, "cycle_logging_tick", Constants.CYCLE_LOGGING_TICK);
    }

    private static void initialize() {
        JSONObject main = new JSONObject();
        main
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
            System.out.println("Please open \"data/secrets.json\" file and enter your Website Password there!");

        } else {
            BufferedReader br = new BufferedReader(new FileReader("data/secrets.json"));
            String out = br.lines().collect(Collectors.joining("\n"));
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
