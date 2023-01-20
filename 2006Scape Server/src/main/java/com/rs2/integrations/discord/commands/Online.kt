package com.rs2.integrations.discord.commands

import com.rs2.GameConstants
import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Online : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals(JavaCord.commandPrefix + " online", ignoreCase = true)) {
            event.channel.sendMessage(":tada: " + JavaCord.serverName + " World:" + GameConstants.WORLD + " is Online! :tada:")
        }
    }
}