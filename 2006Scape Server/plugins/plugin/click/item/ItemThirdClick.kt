package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemThirdClickEvent
import com.rs2.game.players.Player

@SubscribesTo(ItemThirdClickEvent::class)
class ItemThirdClick : EventSubscriber<ItemThirdClickEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemThirdClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[ItemClick#3] - ItemId: ${event.id}")
        }
		
		when(event.id) {

			4079 -> player.startAnimation(1460) //yo-yo


		}
		
		
	}
	
}