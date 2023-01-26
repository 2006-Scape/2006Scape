package com.rs2.integrations.discord.commands

import com.rs2.GameConstants
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Vote : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals("::vote", ignoreCase = true)) {
            if (GameConstants.WORLD == 1) {
                event.channel.sendMessage("Visit " + GameConstants.WEBSITE_LINK + "/vote.html then type \"::claimvote\" in-game to receive your reward!")
            }
        }
    }
}