package com.rebotted.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rebotted.game.players.PlayerHandler;

public class Players implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::players")) {
            if (PlayerHandler.getPlayerCount() != 1) {
                event.getChannel().sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online (" + PlayerHandler.getNonPlayerCount() + " staff online).");
            } else {
                event.getChannel().sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online.");
            }
        }
    }
}
