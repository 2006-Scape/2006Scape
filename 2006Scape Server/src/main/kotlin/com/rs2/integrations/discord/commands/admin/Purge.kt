package com.rs2.integrations.discord.commands.admin

import com.rs2.GameConstants
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class Purge : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val message = event.message
        if (message.content.startsWith("::purge")) {
            if (event.messageAuthor.isServerAdmin) {
                if (GameConstants.WORLD == 1) {
                    val messagesToPurge = event.messageContent.replace("::purge ", "").toInt()
                    if (messagesToPurge > 50) {
                        event.channel.sendMessage("Can't purge more than 50 messages at once.")
                        return
                    }
                    try {
                        event.channel.sendMessage("Purging $messagesToPurge Messages.")
                        message.getMessagesBefore(messagesToPurge).get().deleteAll()
                        val Purge = message.getMessagesAfter(1).get().newestMessage.get()
                        Purge.edit("Purged $messagesToPurge Messages.")
                        TimeUnit.SECONDS.sleep(5)
                        message.delete()
                        Purge.delete()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } catch (e: ExecutionException) {
                        e.printStackTrace()
                    }
                }
            } else {
                event.channel.sendMessage("You do not have permission to perform this command")
            }
        }
    }
}