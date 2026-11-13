package plugin.integration.website

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerShutdownEvent
import com.rs2.game.players.Player

@SubscribesTo(ServerShutdownEvent::class)
class WebsiteShutdownSubscriber : EventSubscriber<ServerShutdownEvent> {

    override fun subscribe(context: EventContext, player: Player?, event: ServerShutdownEvent) {
        PlayersOnlineWebsite.stop()
        RegisteredAccsWebsite.stop()
        WebsiteService.stop()
    }
}
