package plugin.npc.fadli

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcSecondClickEvent
import com.rs2.game.players.Player
import com.rs2.game.content.StaticNpcList.FADLI

@SubscribesTo(NpcSecondClickEvent::class)
class SecondClick : EventSubscriber<NpcSecondClickEvent> {

    override fun subscribe(context: EventContext, player: Player, event: NpcSecondClickEvent) {
        if (event.npc == FADLI) {
            player.packetSender.openUpBank()
        }
    }
}