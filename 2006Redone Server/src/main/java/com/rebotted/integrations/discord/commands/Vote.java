package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
public class Vote implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::vote")) {
                event.getChannel().sendMessage("Visit https://rsrebotted.com/vote.html then type \"::claimvote\" in-game to receive your reward!");
        }
    }
}

