package plugin.integration.website

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ServerConfigEvent
import com.rs2.game.players.Player

@SubscribesTo(ServerConfigEvent::class)
class WebsiteConfig : EventSubscriber<ServerConfigEvent> {

    companion object {
        var password: String = ""
        var playersOnline: Boolean = false
        var registeredAccounts: Boolean = false
    }

    override fun subscribe(context: EventContext, player: Player?, event: ServerConfigEvent) {
        val legacyMaster = event.getBoolean("website_integration", false)
        event.group("website") { g ->
            g.registerString("password", password, "websitepass") { password = it }
            g.registerBoolean("players_online", legacyMaster, "website_players_online") { playersOnline = it }
            g.registerBoolean("registered_accounts", legacyMaster, "website_registered_accounts") { registeredAccounts = it }
        }
    }
}
