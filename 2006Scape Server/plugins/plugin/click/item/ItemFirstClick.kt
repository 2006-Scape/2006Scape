package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemFirstClickEvent
import com.rs2.game.players.Player


@SubscribesTo(ItemFirstClickEvent::class)
class ItemFirstClick : EventSubscriber<ItemFirstClickEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemFirstClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[ItemClick#1] - Item: ${event.item}")
        }
		
		when(event.item) {

			4079 -> player.startAnimation(1457) // yo-yo

		}
		
		
	}
	
}