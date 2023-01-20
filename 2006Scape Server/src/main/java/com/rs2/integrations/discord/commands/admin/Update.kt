package com.rs2.integrations.discord.commands.admin;

import com.rs2.game.players.PlayerHandler;
import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Update implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String seconds = event.getMessageContent().replace(JavaCord.commandPrefix + " update ", "");
        if (event.getMessageContent().startsWith(JavaCord.commandPrefix + " update")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                PlayerHandler.updateSeconds = Integer.parseInt(seconds);
                PlayerHandler.updateAnnounced = false;
                PlayerHandler.updateRunning = true;
                PlayerHandler.updateStartTime = System.currentTimeMillis();
                event.getChannel().sendMessage("Server update will begin in " + seconds + " seconds.");
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}