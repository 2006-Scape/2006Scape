package com.rs2.integrations.discord

import com.rs2.game.players.PlayerHandler

object DiscordActivity {
    @JvmField
    var playerCount = false
    private var count = 50
    @JvmStatic
    fun updateActivity() {
        try {
            if (JavaCord.token != null && JavaCord.token != "") {
                if (count == 0) {
                    if (PlayerHandler.getPlayerCount() != 1) {
                        JavaCord.api!!.updateActivity(PlayerHandler.getPlayerCount().toString() + " Players Online")
                        println("Discord Activity Updated")
                        count = 100
                    } else {
                        JavaCord.api!!.updateActivity(PlayerHandler.getPlayerCount().toString() + " Player Online")
                        println("Discord Activity Updated")
                        count = 100
                    }
                } else {
                    count--
                }
            }
        } catch (e: Exception) {
            println("Could not set Discord activity: $e")
            println("Null checks")
            try {
                println("JavaCord.api: " + JavaCord.api)
                println("PlayerHandler.getPlayerCount: " + PlayerHandler.getPlayerCount())
            } catch (e2: Exception) {
                println(e2)
            }
        }
    }
}