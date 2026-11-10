package plugin.area.alkharid

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcFirstClickEvent
import com.rs2.game.npcs.NpcHandler
import com.rs2.game.players.Player
import plugin.area.alkharid.AlKharidGateUtil.BORDER_GUARD_1
import plugin.area.alkharid.AlKharidGateUtil.BORDER_GUARD_2
import plugin.area.alkharid.AlKharidGateUtil.hasCompletedPrinceAli

@SubscribesTo(NpcFirstClickEvent::class)
class BorderGuardFirstClick : EventSubscriber<NpcFirstClickEvent> {

    override fun subscribe(context: EventContext, player: Player, event: NpcFirstClickEvent) {
        val npcId = event.npc
        if (npcId != BORDER_GUARD_1 && npcId != BORDER_GUARD_2) {
            return
        }

        val guardIndex = if (event.npcIndex > 0) event.npcIndex else player.npcClickIndex

        val guard = if (guardIndex in 1 until NpcHandler.npcs.size) NpcHandler.npcs[guardIndex] else null
        if (guard != null) {
            val playerOnLumbridgeSide = player.absX <= 3267
            val guardOnLumbridgeSide = guard.absX <= 3267
            if (playerOnLumbridgeSide != guardOnLumbridgeSide) {
                player.packetSender.sendMessage("I can't reach that.")
                player.stopMovement()
                guard.facePlayer(null)
                guard.dirUpdateRequired = false
                guard.updateRequired = false
                return
            }
            guard.facePlayer(player)
        }

        if (player.hasCompletedPrinceAli()) {
            player.talkingNpc = npcId
            player.dialogueHandler.sendPlayerChat("Can I come through this gate?")
            player.nextChat = 1030 // "You may pass, friend of Al-Kharid."
        } else {
            player.dialogueHandler.sendDialogues(1018, npcId)
        }
    }
}
