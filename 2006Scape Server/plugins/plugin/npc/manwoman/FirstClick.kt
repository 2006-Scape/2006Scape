package plugin.npc.manwoman

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
			// Man or Woman
			1,2,3,4,5,6 -> player.dialogueFactory.sendDialogue(ManWomanDialogue(Misc.random(22)))
		}
	}
}