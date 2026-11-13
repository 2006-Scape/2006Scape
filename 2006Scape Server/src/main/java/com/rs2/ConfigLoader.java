package com.rs2;

import com.rs2.event.impl.ServerConfigEvent;
import com.rs2.game.players.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

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

    private static void bindString(JSONObject json, String key, Consumer<String> consumer) {
        if (json.has(key)) consumer.accept(json.getString(key));
    }

    private static void bindInt(JSONObject json, String key, Consumer<Integer> consumer) {
        if (json.has(key)) consumer.accept(json.getInt(key));
    }

    private static void bindBoolean(JSONObject json, String key, Consumer<Boolean> consumer) {
        if (json.has(key)) consumer.accept(json.getBoolean(key));
    }

    private static void bindDouble(JSONObject json, String key, Consumer<Double> consumer) {
        if (json.has(key)) consumer.accept(json.getDouble(key));
    }

    public static void loadSettings(String config) throws IOException {
        currentConfigFile = config;
        String out = new String(Files.readAllBytes(Paths.get(config)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(out);
        currentConfigJson = obj;

        JSONObject core = obj.optJSONObject("core");
        if (core == null) {
            System.err.println("Configuration error: Missing 'core' configuration group in " + config);
            return;
        }

        bindString(core, "server_name", val -> Constants.SERVER_NAME = val);
        bindDouble(core, "server_test_version", val -> Constants.TEST_VERSION = val);
        bindBoolean(core, "gui_enabled", val -> Constants.GUI_ENABLED = val);
        bindString(core, "website_link", val -> Constants.WEBSITE_LINK = val);
        bindBoolean(core, "server_debug", val -> Constants.SERVER_DEBUG = val);
        bindBoolean(core, "file_server", val -> Constants.FILE_SERVER = val);
        bindInt(core, "http_port", val -> Constants.HTTP_PORT = val);
        bindInt(core, "jaggrab_port", val -> Constants.JAGGRAB_PORT = val);
        bindInt(core, "world_id", val -> Constants.WORLD = val);
        bindBoolean(core, "members_only", val -> Constants.MEMBERS_ONLY = val);
        bindBoolean(core, "tutorial_island_enabled", val -> Constants.TUTORIAL_ISLAND = val);
        bindBoolean(core, "party_room_enabled", val -> Constants.PARTY_ROOM_DISABLED = !val);
        bindBoolean(core, "clues_enabled", val -> Constants.CLUES_ENABLED = val);
        bindBoolean(core, "admin_can_trade", val -> Constants.ADMIN_CAN_TRADE = val);
        bindBoolean(core, "admin_can_drop_items", val -> Constants.ADMIN_DROP_ITEMS = val);
        bindBoolean(core, "admin_can_sell", val -> Constants.ADMIN_CAN_SELL_ITEMS = val);
        bindInt(core, "respawn_x", val -> Constants.RESPAWN_X = val);
        bindInt(core, "respawn_y", val -> Constants.RESPAWN_Y = val);
        bindInt(core, "save_timer", val -> Constants.SAVE_TIMER = val);
        bindInt(core, "timeout", val -> Constants.TIMEOUT = val);
        bindBoolean(core, "item_requirements", val -> Constants.ITEM_REQUIREMENTS = val);
        bindBoolean(core, "variable_xp_rate", val -> Constants.VARIABLE_XP_RATE = val);
        bindDouble(core, "xp_rate", val -> Constants.XP_RATE = val);
        bindInt(core, "max_players", val -> Constants.MAX_PLAYERS = val);
        if (core.has("variable_xp_rates")) {
            JSONArray rates = core.optJSONArray("variable_xp_rates");
            if (rates != null) {
                for (int i = 0; i < rates.length(); ++i) {
                    Constants.VARIABLE_XP_RATES[i] = rates.optInt(i);
                }
            }
        }
        bindBoolean(core, "cycle_logging", val -> Constants.CYCLE_LOGGING = val);
        bindInt(core, "cycle_logging_tick", val -> Constants.CYCLE_LOGGING_TICK = val);
    }

    private static void initialize() {
        JSONObject main = new JSONObject();
        main.put("erssecret", "");
        try (BufferedWriter br = new BufferedWriter(new FileWriter("data/secrets.json"))) {
            br.write(main.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSecrets() throws IOException {
        if (!new File("data/secrets.json").exists()) {
            initialize();
        } else {
            String out = new String(Files.readAllBytes(Paths.get("data/secrets.json")), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(out);

            /*
             * Sets External Services Vars
             */
            if (obj.has("erssecret"))
                GameEngine.ersSecret = obj.getString("erssecret");

        }
    }
}
