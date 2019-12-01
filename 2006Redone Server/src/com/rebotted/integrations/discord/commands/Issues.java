package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Issues implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::issues") || message.getContent().equalsIgnoreCase("::bugs")) {
            event.getChannel().sendMessage("https://github.com/dginovker/2006rebotted/issues");
        }
    }
}