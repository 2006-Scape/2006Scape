package com.rs2.integrations.discord.commands.admin;

import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class GameKick implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(JavaCord.commandPrefix + " gamekick")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                String playerToKick = event.getMessageContent().replace( JavaCord.commandPrefix + " gamekick ", "");
                for (Player player2 : PlayerHandler.players) {
                    if (player2 != null) {
                        if (player2.playerName.equalsIgnoreCase(playerToKick)) {
                            Client c2 = (Client) player2;
                            event.getChannel().sendMessage( playerToKick+ " was kicked by " + event.getMessageAuthor().getDisplayName() + ".");
                            c2.disconnected = true;
                            c2.logout(true);
                            break;
                        }
                    }
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}