package plugin.integration.discord.commands

import com.rs2.game.players.PlayerHandler
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class Players : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("${DiscordBot.commandPrefix} players", ignoreCase = true)) {
            val count = PlayerHandler.getPlayerCount()
            val staff = PlayerHandler.getNonPlayerCount()
            if (count != 1) {
                event.channel.sendMessage("There are currently $count players online ($staff staff online).").queue()
            } else {
                event.channel.sendMessage("There is currently $count player online ($staff staff online).").queue()
            }
        }
    }
}
