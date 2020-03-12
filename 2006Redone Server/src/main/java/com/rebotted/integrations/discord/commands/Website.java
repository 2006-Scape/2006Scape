package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Website implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::website") || message.getContent().equalsIgnoreCase("::site")) {
            event.getChannel().sendMessage("www.rsrebotted.com");
        }
    }
}