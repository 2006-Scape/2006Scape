package com.rs2.integrations.discord.commands

import com.rs2.GameConstants
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Issues : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals("::issues", ignoreCase = true) || message.content.equals(
                "::bugs",
                ignoreCase = true
            )
        ) {
            if (GameConstants.WORLD == 1) {
                event.channel.sendMessage("https://github.com/2006-Scape/2006Scape/issues")
            }
        }
    }
}