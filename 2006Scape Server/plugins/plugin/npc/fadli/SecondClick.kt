package plugin.npc.fadli

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcSecondClickEvent
import com.rs2.game.players.Player

@SubscribesTo(NpcSecondClickEvent::class)
class SecondClick : EventSubscriber<NpcSecondClickEvent> {

    override fun subscribe(context: EventContext, player: Player, event: NpcSecondClickEvent) {
        if (event.npc == 958) {
            player.packetSender.openUpBank()
        }
    }
}