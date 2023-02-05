package com.rs2.integrations.discord.commands;

import com.rs2.game.players.PlayerHandler;
import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Players implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " players")) {
            if (PlayerHandler.getPlayerCount() != 1) {
                event.getChannel().sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online (" + PlayerHandler.getNonPlayerCount() + " staff online).");
            } else {
                event.getChannel().sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online (" + PlayerHandler.getNonPlayerCount() + " staff online).");
            }
        }
    }
}
