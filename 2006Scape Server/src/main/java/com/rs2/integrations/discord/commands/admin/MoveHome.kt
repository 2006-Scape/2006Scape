package com.rs2.integrations.discord.commands.admin

import com.rs2.GameConstants
import com.rs2.game.players.Client
import com.rs2.game.players.PlayerHandler
import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class MoveHome : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        if (event.messageContent.startsWith(JavaCord.commandPrefix + " movehome")) {
            if (event.messageAuthor.isServerAdmin) {
                val teleToMe = event.messageContent.replace(JavaCord.commandPrefix + " movehome ", "")
                for (i in PlayerHandler.players.indices) {
                    if (PlayerHandler.players[i] != null) {
                        if (PlayerHandler.players[i].playerName.equals(teleToMe, ignoreCase = true)) {
                            val p = PlayerHandler.players[i] as Client
                            event.channel.sendMessage(p.playerName + " has been moved to Lumbridge.")
                            p.playerAssistant.movePlayer(GameConstants.RESPAWN_X, GameConstants.RESPAWN_Y, 0)
                        }
                    }
                }
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
    }
}