package com.rs2.integrations.discord.commands.admin;

import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class AdminCommands implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " admincommands")) {
            if (event.getMessageAuthor().isServerAdmin()) {
            event.getChannel().sendMessage("```fix"
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
                    + "```");
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}
