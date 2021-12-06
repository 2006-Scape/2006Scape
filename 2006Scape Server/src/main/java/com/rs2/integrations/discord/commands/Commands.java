package com.rs2.integrations.discord.commands;

import com.rs2.GameConstants;
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
                    JavaCord.commandPrefix + " forum/" + JavaCord.commandPrefix + " forums"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " hiscores/" + JavaCord.commandPrefix + " highscores"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " issues/" + JavaCord.commandPrefix + " bugs"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " online"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " players"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " vote"
                    + System.lineSeparator() +
                    JavaCord.commandPrefix + " website/" + JavaCord.commandPrefix + " site"
                    + "```");
        }
    }
}
