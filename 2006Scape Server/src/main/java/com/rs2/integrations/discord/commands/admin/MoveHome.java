package com.rs2.integrations.discord.commands.admin;

import com.rs2.Constants;
import com.rs2.game.players.Client;
import com.rs2.game.players.PlayerHandler;
import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MoveHome implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(JavaCord.commandPrefix + " movehome")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                String teleToMe = event.getMessageContent().replace( JavaCord.commandPrefix + " movehome ", "");
                for (int i = 0; i < PlayerHandler.players.length; i++) {
                    if (PlayerHandler.players[i] != null) {
                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(teleToMe)) {
                            Client p = (Client) PlayerHandler.players[i];
                            event.getChannel().sendMessage(p.playerName + " has been moved to Lumbridge.");
                            p.getPlayerAssistant().movePlayer(Constants.RESPAWN_X, Constants.RESPAWN_Y, 0);
                        }
                    }
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}