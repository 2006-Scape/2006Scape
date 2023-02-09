package com.rs2.integrations.discord.commands;

import com.rs2.Constants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Vote implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::vote")) {
            if (Constants.WORLD == 1) {
                event.getChannel().sendMessage("Visit " + Constants.WEBSITE_LINK + "/vote.html then type \"::claimvote\" in-game to receive your reward!");
            }
        }
    }
}

