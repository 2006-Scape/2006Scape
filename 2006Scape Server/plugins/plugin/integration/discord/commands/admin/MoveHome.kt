package plugin.integration.discord.commands.admin

import com.rs2.Constants
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class MoveHome : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        val prefix = "${DiscordBot.commandPrefix.lowercase()} movehome"
        val lower = content.lowercase()

        if (!lower.startsWith(prefix)) {
            return
        }

        if (!DiscordBot.isAdmin(event.member)) {
            event.channel.sendMessage("You do not have permission to perform this command.").queue()
            return
        }

        val playerToMove = content.substring(prefix.length).trim()
        if (playerToMove.isEmpty()) {
            event.channel.sendMessage("Usage: `${DiscordBot.commandPrefix} movehome <player-name>`").queue()
            return
        }

        val client = DiscordBot.findOnlinePlayer(playerToMove)
        if (client == null) {
            event.channel.sendMessage("Player **$playerToMove** is not online.").queue()
            return
        }

        event.channel.sendMessage("${client.playerName} has been moved to Lumbridge.").queue()
        client.playerAssistant.movePlayer(Constants.RESPAWN_X, Constants.RESPAWN_Y, 0)
    }
}
