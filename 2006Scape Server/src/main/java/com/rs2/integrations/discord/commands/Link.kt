package com.rs2.integrations.discord.commands

import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Link : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        val user = message.author.asUser().get()
        if (message.content.equals("::link", ignoreCase = true)) {
            event.channel.sendMessage(user.mentionTag + ", Please check your DM's to continue.")
            user.sendMessage("Please copy/paste the following in-game to link your Discord account: \n ```::link " + user.idAsString + "```")
        }
    }
}