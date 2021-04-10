package plugin.click.npc

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcSecondClickEvent
import com.rs2.game.content.skills.thieving.Pickpocket
import com.rs2.game.players.Player

@SubscribesTo(NpcSecondClickEvent::class)
class NpcSecondClick : EventSubscriber<NpcSecondClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: NpcSecondClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[click= npc], [type = second], [id= ${event.npc}], [Type= ${event.npc}]");
		}

		if (Pickpocket.isNPC(player, player.npcType)) {
			Pickpocket.attemptPickpocket(player, player.npcType)
			return
		}

		when(event.npc) {

		}
		
	}
	
}