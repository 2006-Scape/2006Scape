package com.rs2.integrations.discord.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rs2.GameConstants;

public class Hiscores implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("::w" + GameConstants.WORLD + " hiscores") || message.getContent().equalsIgnoreCase("::w" + GameConstants.WORLD + " highscores")) {
            event.getChannel().sendMessage(GameConstants.WEBSITE_LINK + "/hiscores.html");
        }
    }
}
