package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemSecondClickEvent
import com.rs2.game.players.Player

@SubscribesTo(ItemSecondClickEvent::class)
class ItemSecondClick : EventSubscriber<ItemSecondClickEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemSecondClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[ItemClick#2] - ItemId: ${event.id}")
        }
		
		when(event.id) {

			4079 -> player.startAnimation(1459) //yo-yo

		}
		
		
	}
	
}