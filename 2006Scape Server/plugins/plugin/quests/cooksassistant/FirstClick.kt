package plugin.quests.cooksassistant

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.NpcFirstClickEvent
import com.rs2.game.players.Player
import plugin.quests.cooksassistant.dialogue.LumbridgeCookDialogue


@SubscribesTo(NpcFirstClickEvent::class)
class FirstClick : EventSubscriber<NpcFirstClickEvent> {

	override fun subscribe(context: EventContext, player: Player, event: NpcFirstClickEvent) {

		when(event.npc) {
			//Lumbridge Cook (278)
			278 -> {
				if (player.playerRights >= 3) {
					player.packetSender.sendMessage("[click= npc], [type = first/quest], [id= ${event.npc}], [Type= ${event.npc}]")
				}
				player.dialogueFactory.sendDialogue(LumbridgeCookDialogue())
			}
		}
	}
}