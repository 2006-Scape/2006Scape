package plugin.integration.discord.commands

import com.rs2.Constants
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import plugin.integration.discord.DiscordBot
import java.awt.Color

class Commands : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val content = event.message.contentRaw
        if (content.equals("::commands", ignoreCase = true) || content.equals("::command", ignoreCase = true)) {
            val embed = EmbedBuilder()
                .setTitle("⚔️ ${Constants.SERVER_NAME} Discord Commands")
                .setColor(Color(0x4A, 0x90, 0xE2))
                .setDescription("Available commands for the ${Constants.SERVER_NAME} Discord bot:")
                .addField(
                    "🌐 Global Commands",
                    """
                    `::commands` — Displays this list of available commands
                    `::link` — Instructions to link your Discord account in-game
                    `::forum` / `::forums` — Link to the official community forums
                    `::website` / `::site` — Link to the official 2006Scape website
                    `::issues` / `::bugs` — Link to report bugs and issues on GitHub
                    `::admincommands` — View administrator commands (Staff only)
                    """.trimIndent(),
                    false
                )
                .addField(
                    "🌍 World Commands (Prefix: `::w<world_id>`)",
                    """
                    *Target a specific world using `::w1`, `::w2`, etc.:*
                    `::w<id> online` — Check if that world is online (e.g. `::w1 online`)
                    `::w<id> players` — View player and staff counts (e.g. `::w1 players`)
                    `::w<id> gamekick <player>` — Kick your character (requires linked account)
                    """.trimIndent(),
                    false
                )
                .setFooter("World commands require the world number prefix (e.g. ::w1 for World 1, ::w2 for World 2)")
                .build()

            event.channel.sendMessageEmbeds(embed).queue()
        }
    }
}
