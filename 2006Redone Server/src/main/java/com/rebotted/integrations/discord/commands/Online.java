package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rebotted.integrations.discord.JavaCord;

public class Online implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::online")) {
            event.getChannel().sendMessage(":tada: " + JavaCord.serverName + " is Online! :tada:");
        }
    }
}