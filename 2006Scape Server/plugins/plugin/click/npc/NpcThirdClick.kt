package plugin.click.npc

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcThirdClickEvent
import com.rs2.game.players.Player

@SubscribesTo(NpcThirdClickEvent::class)
class NpcThirdClick : EventSubscriber<NpcThirdClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: NpcThirdClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[click= npc], [type = third], [id= ${event.npc}], [Type= ${event.npc}]");
		}
		
		when(event.npc) {

		}
		
	}
	
}