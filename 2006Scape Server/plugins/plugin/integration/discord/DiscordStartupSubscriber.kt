package plugin.integration.discord

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerStartupEvent
import com.rs2.game.players.Player

@SubscribesTo(ServerStartupEvent::class)
class DiscordStartupSubscriber : EventSubscriber<ServerStartupEvent> {

    override fun subscribe(context: EventContext, player: Player?, event: ServerStartupEvent) {
        try {
            DiscordBot.init()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
