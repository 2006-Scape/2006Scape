package plugin.integration.discord.commands

import com.rs2.Constants
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Website : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::website", ignoreCase = true) || content.equals("::site", ignoreCase = true)) {
            event.channel.sendMessage(Constants.WEBSITE_LINK).queue()
        }
    }
}
