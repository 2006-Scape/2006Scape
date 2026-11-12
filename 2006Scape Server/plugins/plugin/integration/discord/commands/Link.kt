package plugin.integration.discord.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Link : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::link", ignoreCase = true)) {
            event.channel.sendMessage("${event.author.asMention}, Please check your DM's to continue.").queue()
            event.author.openPrivateChannel().queue { privateChannel ->
                privateChannel.sendMessage("Please copy/paste the following in-game to link your Discord account: \n ```::link ${event.author.id}```").queue()
            }
        }
    }
}
