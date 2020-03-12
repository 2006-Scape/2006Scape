package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Hiscores implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::hiscores") || message.getContent().equalsIgnoreCase("::highscores")) {
            event.getChannel().sendMessage("https://rsrebotted.com/hiscores.html");
        }
    }
}