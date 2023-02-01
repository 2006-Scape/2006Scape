package com.rs2.integrations.discord.commands

import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Link : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals("::link", ignoreCase = true)) {
            event.channel.sendMessage(message.author.asUser().get().mentionTag + ", Please check your DM's to continue.")
            message.author.asUser().get().sendMessage("Please copy/paste the following in-game to link your Discord account: \n ```::link " + message.author.asUser().get().idAsString + "```")
        }
    }
}