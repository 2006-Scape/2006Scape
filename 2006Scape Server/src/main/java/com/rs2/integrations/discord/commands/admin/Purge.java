package com.rs2.integrations.discord.commands.admin;

import com.rs2.Constants;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Purge implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().startsWith("::purge")) {
            if (event.getMessageAuthor().isServerAdmin()) {
                if(Constants.WORLD == 1) {
                    int messagesToPurge = Integer.parseInt(event.getMessageContent().replace("::purge ", ""));
                    if (messagesToPurge > 50) {
                        event.getChannel().sendMessage("Can't purge more than 50 messages at once.");
                        return;
                    }
                    try {
                        event.getChannel().sendMessage("Purging " + messagesToPurge + " Messages.");
                        message.getMessagesBefore(messagesToPurge).get().deleteAll();
                        Message Purge = message.getMessagesAfter(1).get().getNewestMessage().get();
                        Purge.edit("Purged " + messagesToPurge + " Messages.");
                        TimeUnit.SECONDS.sleep(5);
                        message.delete();
                        Purge.delete();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to perform this command");
            }
        }
    }
}
