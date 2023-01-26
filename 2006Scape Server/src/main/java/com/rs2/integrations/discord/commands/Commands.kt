package com.rs2.integrations.discord.commands

import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Commands : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals(JavaCord.commandPrefix + " commands", ignoreCase = true)) {
            event.channel.sendMessage(
                "```fix"
                        + System.lineSeparator() +
                        "::link"
                        + System.lineSeparator() +
                        "::forum/::forums"
                        + System.lineSeparator() +
                        JavaCord.commandPrefix + " gamekick(if account id linked)"
                        + System.lineSeparator() +
                        JavaCord.commandPrefix + " hiscores/" + JavaCord.commandPrefix + " highscores"
                        + System.lineSeparator() +
                        "::issues/::bugs"
                        + System.lineSeparator() +
                        JavaCord.commandPrefix + " online"
                        + System.lineSeparator() +
                        "::vote"
                        + System.lineSeparator() +
                        "::website/::site"
                        + "```"
            )
        }
    }
}