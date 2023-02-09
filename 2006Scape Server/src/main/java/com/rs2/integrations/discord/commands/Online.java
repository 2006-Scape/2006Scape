package com.rs2.integrations.discord.commands;

import com.rs2.Constants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rs2.integrations.discord.JavaCord;

public class Online implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " online")) {
            event.getChannel().sendMessage(":tada: " + JavaCord.serverName + " World:" + Constants.WORLD + " is Online! :tada:");
        }
    }
}