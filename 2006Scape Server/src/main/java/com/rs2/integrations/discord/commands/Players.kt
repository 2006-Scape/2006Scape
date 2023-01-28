package com.rs2.integrations.discord.commands

import com.rs2.game.players.PlayerHandler
import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Players : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals(JavaCord.commandPrefix + " players", ignoreCase = true)) {
            if (PlayerHandler.getPlayerCount() != 1) {
                event.channel.sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online (" + PlayerHandler.getNonPlayerCount() + " staff online).")
            } else {
                event.channel.sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online (" + PlayerHandler.getNonPlayerCount() + " staff online).")
            }
        }
    }
}