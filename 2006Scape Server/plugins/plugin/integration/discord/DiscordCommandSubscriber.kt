package plugin.integration.discord

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.CommandEvent
import com.rs2.game.players.Player

@SubscribesTo(CommandEvent::class)
class DiscordCommandSubscriber : EventSubscriber<CommandEvent> {

    override fun subscribe(context: EventContext, player: Player?, event: CommandEvent) {
        if (player == null) {
            return
        }

        when (event.name.lowercase()) {
            "link" -> {
                val args = event.arguments
                if (args.isNotEmpty()) {
                    player.discordCode = args[0]
                    player.packetSender.sendMessage("Your Account has now been linked with Discord User ID:")
                    player.packetSender.sendMessage(player.discordCode)
                } else {
                    player.packetSender.sendMessage("Usage: ::link <discord-user-id>")
                }
                context.breakSubscriberChain()
            }
            "stuck" -> {
                DiscordBot.sendLog("${player.playerName} used ::stuck at X/Y: ${player.absX}/${player.absY}")
            }
        }
    }
}
