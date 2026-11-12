package plugin.integration.discord

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerShutdownEvent
import com.rs2.game.players.Player

@SubscribesTo(ServerShutdownEvent::class)
class DiscordShutdownSubscriber : EventSubscriber<ServerShutdownEvent> {

    override fun subscribe(context: EventContext, player: Player?, event: ServerShutdownEvent) {
        try {
            DiscordBot.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
