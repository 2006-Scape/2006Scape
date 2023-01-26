package com.rs2.integrations.discord.commands.admin

import com.rs2.game.players.Client
import com.rs2.game.players.PlayerHandler
import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class GameKick : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        if (event.messageContent.startsWith(JavaCord.commandPrefix + " gamekick")) {
            val playerToKick = event.messageContent.replace(JavaCord.commandPrefix + " gamekick ", "")
            for (player2 in PlayerHandler.players) {
                if (player2 != null) {
                    if (player2.playerName.equals(playerToKick, ignoreCase = true)) {
                        val c2 = player2 as Client
                        if (event.messageAuthor.isServerAdmin || event.messageAuthor.idAsString == c2.discordCode) {
                            event.channel.sendMessage(playerToKick + " was kicked by " + event.messageAuthor.displayName + ".")
                            c2.disconnected = true
                            c2.logout(true)
                        } else {
                            event.channel.sendMessage("You do not have permission to perform this command")
                        }
                        break
                    }
                }
            }
        }
    }
}