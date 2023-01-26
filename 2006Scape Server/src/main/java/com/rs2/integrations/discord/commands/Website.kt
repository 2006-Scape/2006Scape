package com.rs2.integrations.discord.commands

import com.rs2.GameConstants
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Website : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals("::website", ignoreCase = true) || message.content.equals(
                "::site",
                ignoreCase = true
            )
        ) {
            if (GameConstants.WORLD == 1) {
                event.channel.sendMessage(GameConstants.WEBSITE_LINK)
            }
        }
    }
}