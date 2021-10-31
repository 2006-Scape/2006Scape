package plugin.npc.banker

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcFirstClickEvent
import com.rs2.game.content.skills.SkillHandler
import com.rs2.game.players.Player
import com.rs2.util.Misc
import plugin.npc.banker.BankerDialogue
import plugin.npc.manwoman.ManWomanDialogue


@SubscribesTo(NpcFirstClickEvent::class)
class FirstClick : EventSubscriber<NpcFirstClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: NpcFirstClickEvent) {

		if (player.playerRights >= 3) {
            player.packetSender.sendMessage("[click= npc], [type = first], [id= ${event.npc}], [Type= ${event.npc}]")
        }

		when(event.npc) {
			// Banker (NOT INCLUDING GHOST BANKER IN PORT PHASMATYS)
			166, 494, 495, 496, 497, 498, 499, 953, 1036, 1360, 2163, 2164, 2354, 2355, 2568, 2569, 2570 -> {
				if (!SkillHandler.isSkilling(player)) {
					player.dialogueFactory.sendDialogue(BankerDialogue())
				}
			}
		}
	}
}