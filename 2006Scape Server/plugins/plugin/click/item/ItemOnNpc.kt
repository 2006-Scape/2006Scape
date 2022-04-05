package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemOnNpcEvent
import com.rs2.game.npcs.NpcHandler
import com.rs2.game.players.Player

@SubscribesTo(ItemOnNpcEvent::class)
class ItemOnNpc : EventSubscriber<ItemOnNpcEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemOnNpcEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[ItemOnNpc] - itemId: ${event.item} npcId: ${event.npc}")
        }

	}
	
}