package com.rs2.integrations.discord.commands;

import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Commands implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " commands")) {
            event.getChannel().sendMessage("```fix"
                    + System.lineSeparator() +
                    "::forum/::forums"
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
                    + "```");
        }
    }
}
