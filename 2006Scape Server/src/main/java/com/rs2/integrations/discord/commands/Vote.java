package com.rs2.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rs2.GameConstants;

public class Vote implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::vote")) {
            if (GameConstants.WORLD == 1) {
                event.getChannel().sendMessage("Visit " + GameConstants.WEBSITE_LINK + "/vote.html then type \"::claimvote\" in-game to receive your reward!");
            }
        }
    }
}

