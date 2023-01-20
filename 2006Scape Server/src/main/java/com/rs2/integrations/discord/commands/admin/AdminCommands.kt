package com.rs2.integrations.discord.commands.admin

import com.rs2.integrations.discord.JavaCord
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class AdminCommands : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.equals(JavaCord.commandPrefix + " admincommands", ignoreCase = true)) {
            if (event.messageAuthor.isServerAdmin) {
                event.channel.sendMessage(
                    "```fix"
                            + System.lineSeparator() +
                            "::pin/::unpin(Pins/Un-Pins The Replied Mesage)"
                            + System.lineSeparator() +
                            "::purge(Purges The Specified Amount Of Messages From Discord Channel)"
                            + System.lineSeparator() +
                            JavaCord.commandPrefix + " gamekick(Kicks The Specified Player From The GameServer)"
                            + System.lineSeparator() +
                            JavaCord.commandPrefix + " movehome(Moves The Specified Player To Lumbridge)"
                            + System.lineSeparator() +
                            JavaCord.commandPrefix + " update(Triggers A GameServer Update In The Specified Amount Of Seconds)"
                            + "```"
                )
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
    }
}