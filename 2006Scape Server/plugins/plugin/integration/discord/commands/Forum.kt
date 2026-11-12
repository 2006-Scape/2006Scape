package plugin.integration.discord.commands

import com.rs2.Constants
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Forum : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::forum", ignoreCase = true) || content.equals("::forums", ignoreCase = true)) {
            event.channel.sendMessage("${Constants.WEBSITE_LINK}/forums/index.php").queue()
        }
    }
}
