package plugin.integration.discord

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerConfigEvent
import com.rs2.game.players.Player

/**
 * Dedicated subscriber that registers and loads all Discord configuration keys
 * from the active server configuration file via [ServerConfigEvent].
 */
@SubscribesTo(ServerConfigEvent::class)
class DiscordConfig : EventSubscriber<ServerConfigEvent> {

    companion object {
        var botToken: String = ""
        var welcomeChannelId: String = ""
        var logChannelId: String = ""
        var displayPlayerCount: Boolean = false
    }

    override fun subscribe(context: EventContext, player: Player?, event: ServerConfigEvent) {
        event.group("discord") { g ->
            g.registerString("token", botToken, "discord_bot_token") { botToken = it }
            g.registerString("welcome_channel_id", welcomeChannelId, "discord_bot_welcome_channel_id") { welcomeChannelId = it }
            g.registerString("log_channel_id", logChannelId, "discord_bot_log_channel_id") { logChannelId = it }
            g.registerBoolean("player_count", displayPlayerCount, "discord_bot_player_count") { displayPlayerCount = it }
        }
    }
}
