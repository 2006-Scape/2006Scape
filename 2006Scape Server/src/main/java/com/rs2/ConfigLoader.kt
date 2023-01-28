package com.rs2

import com.rs2.integrations.PlayersOnlineWebsite
import com.rs2.integrations.RegisteredAccsWebsite
import com.rs2.integrations.discord.JavaCord
import org.json.JSONObject
import java.io.*
import java.util.stream.Collectors

object ConfigLoader {
    @JvmStatic
    @Throws(IOException::class)
    fun loadSettings(config: String?) {
        val br = config?.let { FileReader(it) }?.let { BufferedReader(it) }
        val out = br?.lines()?.collect(Collectors.joining("\n"))
        val obj = JSONObject(out)
        if (obj.has("server_name")) GameConstants.SERVER_NAME = obj.getString("server_name")
        if (obj.has("server_test_version")) GameConstants.TEST_VERSION = obj.getDouble("server_test_version")
        if (obj.has("website_link")) GameConstants.WEBSITE_LINK = obj.getString("website_link")
        if (obj.has("server_debug")) GameConstants.SERVER_DEBUG = obj.getBoolean("server_debug")
        if (obj.has("file_server")) GameConstants.FILE_SERVER = obj.getBoolean("file_server")
        if (obj.has("world_id")) GameConstants.WORLD = obj.getInt("world_id")
        if (obj.has("members_only")) GameConstants.MEMBERS_ONLY = obj.getBoolean("members_only")
        if (obj.has("tutorial_island_enabled")) GameConstants.TUTORIAL_ISLAND = obj.getBoolean("tutorial_island_enabled")
        if (obj.has("party_room_enabled")) GameConstants.PARTY_ROOM_DISABLED = !obj.getBoolean("party_room_enabled")
        if (obj.has("clues_enabled")) GameConstants.CLUES_ENABLED = obj.getBoolean("clues_enabled")
        if (obj.has("admin_can_trade")) GameConstants.ADMIN_CAN_TRADE = obj.getBoolean("admin_can_trade")
        if (obj.has("admin_can_drop_items")) GameConstants.ADMIN_DROP_ITEMS = obj.getBoolean("admin_can_drop_items")
        if (obj.has("admin_can_sell")) GameConstants.ADMIN_CAN_SELL_ITEMS = obj.getBoolean("admin_can_sell")
        if (obj.has("respawn_x")) GameConstants.RESPAWN_X = obj.getInt("respawn_x")
        if (obj.has("respawn_y")) GameConstants.RESPAWN_Y = obj.getInt("respawn_y")
        if (obj.has("save_timer")) GameConstants.SAVE_TIMER = obj.getInt("save_timer")
        if (obj.has("timeout")) GameConstants.TIMEOUT = obj.getInt("timeout")
        if (obj.has("item_requirements")) GameConstants.ITEM_REQUIREMENTS = obj.getBoolean("item_requirements")
        if (obj.has("variable_xp_rate")) GameConstants.VARIABLE_XP_RATE = obj.getBoolean("variable_xp_rate")
        if (obj.has("xp_rate")) GameConstants.XP_RATE = obj.getDouble("xp_rate")
        if (obj.has("max_players")) GameConstants.MAX_PLAYERS = obj.getInt("max_players")
        if (obj.has("variable_xp_rates")) {
            val rates = obj.optJSONArray("variable_xp_rates")
            for (i in 0 until rates.length()) {
                GameConstants.VARIABLE_XP_RATES[i] = rates.optInt(i)
            }
        }
        if (obj.has("website_integration")) GameConstants.WEBSITE_INTEGRATION = obj.getBoolean("website_integration")
    }

    private fun initialize() {
        val main = JSONObject()
        main
            .put("bot-token", "")
            .put("websitepass", "")
            .put("erssecret", "")
        try {
            val br = BufferedWriter(FileWriter("data/secrets.json"))
            br.write(main.toString())
            br.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun loadSecrets() {
        if (!File("data/secrets.json").exists()) {
            initialize()
            println("Please open \"data/secrets.json\" file and enter your discord token bot there!")
            println("Please open \"data/secrets.json\" file and enter your Website Password there!")
        } else {
            val br = BufferedReader(FileReader("data/secrets.json"))
            val out = br.lines().collect(Collectors.joining("\n"))
            val obj = JSONObject(out)

            /*
             * Sets External Services Vars
             */if (obj.has("bot-token")) JavaCord.token = obj.getString("bot-token")
            if (obj.has("websitepass")) PlayersOnlineWebsite.password = obj.getString("websitepass")
            RegisteredAccsWebsite.password = obj.getString("websitepass")
            if (obj.has("erssecret")) GameEngine.ersSecret = obj.getString("erssecret")
        }
    }
}