package com.rs2.integrations.discord.commands.admin

import com.rs2.GameConstants
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class Pin : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.startsWith("::pin")) {
            if (event.messageAuthor.isServerAdmin) {
                if (GameConstants.WORLD == 1) {
                    if (message.referencedMessage.isPresent) {
                        val messageToPin = message.referencedMessage.get()
                        messageToPin.pin()
                    }
                }
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
        if (message.content.startsWith("::unpin")) {
            if (event.messageAuthor.isServerAdmin) {
                if (GameConstants.WORLD == 1) {
                    if (message.referencedMessage.isPresent) {
                        val messageToUnpin = message.referencedMessage.get()
                        messageToUnpin.unpin()
                        event.channel.sendMessage("Un-Pinned Message: " + messageToUnpin.link)
                    }
                }
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
    }
}