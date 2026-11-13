package plugin.integration.website

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerStartupEvent
import com.rs2.game.players.Player

@SubscribesTo(ServerStartupEvent::class)
class WebsiteStartupSubscriber : EventSubscriber<ServerStartupEvent> {

    override fun subscribe(context: EventContext, player: Player?, event: ServerStartupEvent) {
        PlayersOnlineWebsite.start()
        RegisteredAccsWebsite.start()
    }
}
