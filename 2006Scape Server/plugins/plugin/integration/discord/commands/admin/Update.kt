package plugin.integration.discord.commands.admin

import com.rs2.game.players.PlayerHandler
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot

class Update : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        val prefix = "${DiscordBot.commandPrefix.lowercase()} update"
        val lower = content.lowercase()

        if (!lower.startsWith(prefix)) {
            return
        }

        if (!DiscordBot.isAdmin(event.member)) {
            event.channel.sendMessage("You do not have permission to perform this command.").queue()
            return
        }

        val secondsStr = content.substring(prefix.length).trim()
        if (secondsStr.isEmpty()) {
            event.channel.sendMessage("Usage: `${DiscordBot.commandPrefix} update <seconds>`").queue()
            return
        }

        try {
            val seconds = secondsStr.toInt()
            PlayerHandler.updateSeconds = seconds
            PlayerHandler.updateAnnounced = false
            PlayerHandler.updateRunning = true
            PlayerHandler.updateStartTime = System.currentTimeMillis()
            event.channel.sendMessage("Server update will begin in $seconds seconds.").queue()
        } catch (e: NumberFormatException) {
            event.channel.sendMessage("Please specify a valid number of seconds: `${DiscordBot.commandPrefix} update <seconds>`").queue()
        }
    }
}
