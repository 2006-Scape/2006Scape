package plugin.integration.discord.commands

import com.rs2.Constants
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class Online : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("${DiscordBot.commandPrefix} online", ignoreCase = true)) {
            event.channel.sendMessage(":tada: ${Constants.SERVER_NAME} World:${Constants.WORLD} is Online! :tada:").queue()
        }
    }
}
