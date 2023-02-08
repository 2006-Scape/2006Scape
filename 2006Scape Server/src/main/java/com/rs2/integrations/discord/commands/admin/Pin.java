package com.rs2.integrations.discord.commands.admin;

import com.rs2.Constants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Pin implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().startsWith("::pin")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                if(Constants.WORLD == 1) {
                    if (message.getReferencedMessage().isPresent()) {
                        Message messageToPin = message.getReferencedMessage().get();
                        messageToPin.pin();
                    }
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
        if (message.getContent().startsWith("::unpin")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                if(Constants.WORLD == 1) {
                    if (message.getReferencedMessage().isPresent()) {
                        Message messageToUnpin = message.getReferencedMessage().get();
                        messageToUnpin.unpin();
                        event.getChannel().sendMessage("Un-Pinned Message: " + messageToUnpin.getLink());

                    }
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}
