package com.rs2.integrations.discord.commands;

import com.rs2.GameConstants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Commands implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::w" + GameConstants.WORLD + " commands")) {
            event.getChannel().sendMessage("```fix"
                    + System.lineSeparator() +
                    "::w(World ID) forum/::(World ID) forums"
                    + System.lineSeparator() +
                    "::(World ID) hiscores/::(World ID) highscores"
                    + System.lineSeparator() +
                    "::(World ID) issues/::(World ID) bugs"
                    + System.lineSeparator() +
                    "::(World ID) online"
                    + System.lineSeparator() +
                    "::(World ID) players"
                    + System.lineSeparator() +
                    "::(World ID) vote"
                    + System.lineSeparator() +
                    "::(World ID) website/::(World ID) site"
                    + "```");
        }
    }
}
