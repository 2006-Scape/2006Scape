package com.rs2.integrations.discord.commands;

import com.rs2.Constants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Website implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::website") || message.getContent().equalsIgnoreCase("::site")) {
            if (Constants.WORLD == 1) {
                event.getChannel().sendMessage(Constants.WEBSITE_LINK);
            }
        }
    }
}
