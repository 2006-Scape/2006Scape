package com.rs2.integrations.discord.commands.admin

import com.rs2.game.players.PlayerHandler
import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Update : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val seconds = event.messageContent.replace(JavaCord.commandPrefix + " update ", "")
        if (event.messageContent.startsWith(JavaCord.commandPrefix + " update")) {
            if (event.messageAuthor.isServerAdmin) {
                PlayerHandler.updateSeconds = seconds.toInt()
                PlayerHandler.updateAnnounced = false
                PlayerHandler.updateRunning = true
                PlayerHandler.updateStartTime = System.currentTimeMillis()
                event.channel.sendMessage("Server update will begin in $seconds seconds.")
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
    }
}