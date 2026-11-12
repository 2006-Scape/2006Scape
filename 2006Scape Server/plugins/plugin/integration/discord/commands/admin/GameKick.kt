package plugin.integration.discord.commands.admin

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class GameKick : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        val prefix = "${DiscordBot.commandPrefix.lowercase()} gamekick"
        val lower = content.lowercase()

        if (!lower.startsWith(prefix)) {
            return
        }

        val playerToKick = content.substring(prefix.length).trim()
        if (playerToKick.isEmpty()) {
            event.channel.sendMessage("Usage: `${DiscordBot.commandPrefix} gamekick <player-name>`").queue()
            return
        }

        val client = DiscordBot.findOnlinePlayer(playerToKick)
        if (client == null) {
            event.channel.sendMessage("Player **$playerToKick** is not online.").queue()
            return
        }

        if (DiscordBot.isAdmin(event.member) || event.author.id == client.discordCode) {
            event.channel.sendMessage("${client.playerName} was kicked by ${event.author.effectiveName}.").queue()
            client.disconnected = true
            client.logout(true)
        } else {
            event.channel.sendMessage("You do not have permission to kick this player.").queue()
        }
    }
}
