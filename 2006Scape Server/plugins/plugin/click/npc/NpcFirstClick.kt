package plugin.click.npc

import com.rs2.GameConstants
import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcFirstClickEvent
import com.rs2.game.npcs.Npc
import com.rs2.game.players.Player
import com.rs2.util.Misc


@SubscribesTo(NpcFirstClickEvent::class)
class NpcFirstClick : EventSubscriber<NpcFirstClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: NpcFirstClickEvent) {

		if (player.playerRights >= 3) {
            player.packetSender.sendMessage("[click= npc], [type = first], [id= ${event.npc}], [Type= ${event.npc}]");
        }
		
		when(event.npc) {

			1,2,3,4,5,6 -> if (Misc.random(10) <= 5) {
				player.dialogueHandler.sendDialogues(3869, player.npcType)
			} else {
				player.dialogueHandler.sendDialogues(3872, player.npcType)
			}

			//else ->
		}
		
	}
	
}