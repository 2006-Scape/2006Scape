package plugin.integration.discord

import com.rs2.Constants
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class WelcomeMessage : ListenerAdapter() {

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        try {
            if (DiscordConfig.welcomeChannelId.isNotEmpty()) {
                val channel = event.guild.getTextChannelById(DiscordConfig.welcomeChannelId)
                channel?.sendMessage("Hello ${event.user.asMention}, and welcome to the ${Constants.SERVER_NAME} Discord server!")?.queue()
            }
        } catch (e: Exception) {
            System.err.println("Failed to send welcome message: ${e.message}")
        }
    }
}
