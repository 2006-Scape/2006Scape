package com.rs2.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rs2.GameConstants;

public class Forum implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::forum") || message.getContent().equalsIgnoreCase("::forums")) {
            if (GameConstants.WORLD == 1) {
                event.getChannel().sendMessage(GameConstants.WEBSITE_LINK + "/forums/index.php");
            }
        }
    }
}
