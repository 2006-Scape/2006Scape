package plugin.integration.discord.commands.admin

import com.rs2.Constants
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot
import java.awt.Color

class AdminCommands : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::admincommands", ignoreCase = true) || content.equals("::admincommand", ignoreCase = true)) {
            if (DiscordBot.isAdmin(event.member)) {
                val embed = EmbedBuilder()
                    .setTitle("🛡️ ${Constants.SERVER_NAME} Administrator Commands")
                    .setColor(Color(0xE7, 0x4C, 0x3C))
                    .setDescription("Staff commands for server administration:")
                    .addField(
                        "Commands",
                        """
                        `::purge <1-50>` — Purges specified amount of messages from Discord channel
                        `::w<id> gamekick <player>` — Disconnects a player from specified world
                        `::w<id> movehome <player>` — Teleports player to Lumbridge
                        `::w<id> update <seconds>` — Triggers server update countdown on specified world
                        """.trimIndent(),
                        false
                    )
                    .setFooter("World-specific commands require the world number prefix (e.g. ::w1 or ::w2)")
                    .build()

                event.channel.sendMessageEmbeds(embed).queue()
            } else {
                event.channel.sendMessage("You do not have permission to perform this command.").queue()
            }
        }
    }
}
