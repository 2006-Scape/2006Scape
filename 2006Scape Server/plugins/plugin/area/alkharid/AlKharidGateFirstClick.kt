package plugin.area.alkharid

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ObjectFirstClickEvent
import com.rs2.game.players.Player
import plugin.area.alkharid.AlKharidGateUtil.GATE_NORTH
import plugin.area.alkharid.AlKharidGateUtil.GATE_SOUTH
import plugin.area.alkharid.AlKharidGateUtil.hasCompletedPrinceAli
import plugin.area.alkharid.AlKharidGateUtil.isNearGate
import plugin.area.alkharid.AlKharidGateUtil.passThroughGate

@SubscribesTo(ObjectFirstClickEvent::class)
class AlKharidGateFirstClick : EventSubscriber<ObjectFirstClickEvent> {

    override fun subscribe(context: EventContext, player: Player, event: ObjectFirstClickEvent) {
        val objectId = event.gameObject
        if (objectId != GATE_NORTH && objectId != GATE_SOUTH) {
            return
        }

        if (!isNearGate(player)) {
            player.packetSender.sendMessage("You need to be closer to the gate to use it.")
            return
        }

        if (player.hasCompletedPrinceAli()) {
            player.packetSender.sendMessage("The guards recognize you as a friend of Al-Kharid and let you pass.")
            passThroughGate(player)
            return
        }

        player.packetSender.sendMessage("You need to talk to the border guard.")
    }
}
