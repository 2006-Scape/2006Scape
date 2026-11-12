package plugin.integration.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Issues : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::issues", ignoreCase = true) || content.equals("::bugs", ignoreCase = true)) {
            event.channel.sendMessage("https://github.com/2006-Scape/2006Scape/issues").queue()
        }
    }
}
