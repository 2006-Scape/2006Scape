package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Forum implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::forum") || message.getContent().equalsIgnoreCase("::forums")) {
            event.getChannel().sendMessage("https://rsrebotted.com/forums/index.php");
        }
    }
}