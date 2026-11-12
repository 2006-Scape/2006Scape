package plugin.integration.discord.commands.admin

import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class Purge : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        val lower = content.lowercase()
        if (lower == "::purge" || lower.startsWith("::purge ")) {
            if (DiscordBot.isAdmin(event.member)) {
                val messagesStr = content.substring(7).trim()
                val messagesToPurge = messagesStr.toIntOrNull()
                if (messagesToPurge == null || messagesToPurge <= 0) {
                    event.channel.sendMessage("Usage: `::purge <1-50>`").queue()
                    return
                }
                if (messagesToPurge > 50) {
                    event.channel.sendMessage("Can't purge more than 50 messages at once.").queue()
                    return
                }
                val channel = event.channel
                if (channel is GuildMessageChannel) {
                    channel.getHistoryBefore(event.messageId, messagesToPurge).queue({ history ->
                        channel.purgeMessages(history.retrievedHistory)
                        channel.sendMessage("Purging $messagesToPurge Messages.").queue()
                    }, { e ->
                        System.err.println("Failed to retrieve history for purge: ${e.message}")
                    })
                }
            } else {
                event.channel.sendMessage("You do not have permission to perform this command.").queue()
            }
        }
    }
}
