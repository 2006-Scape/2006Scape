package com.rs2.integrations.discord.commands;

import com.rs2.Constants;
import com.rs2.integrations.discord.JavaCord;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Hiscores implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " hiscores") || message.getContent().equalsIgnoreCase(JavaCord.commandPrefix + " highscores")) {
            event.getChannel().sendMessage(Constants.WEBSITE_LINK + "/hiscores.html");
        }
    }
}
