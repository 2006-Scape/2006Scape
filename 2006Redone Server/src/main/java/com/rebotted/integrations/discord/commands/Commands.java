package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Commands implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::commands")) {
            event.getChannel().sendMessage("```fix"
                    + System.lineSeparator() +
                    "::forum/::forums"
                    + System.lineSeparator() +
                    "::hiscores/::highscores"
                    + System.lineSeparator() +
                    "::issues/::bugs"
                    + System.lineSeparator() +
                    "::online"
                    + System.lineSeparator() +
                    "::players"
                    + System.lineSeparator() +
                    "::vote"
                    + System.lineSeparator() +
                    "::website/::site"
                    + "```");
        }
    }
}
