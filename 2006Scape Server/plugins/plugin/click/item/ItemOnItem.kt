package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemOnItemEvent
import com.rs2.game.content.StaticItemList.*
import com.rs2.game.players.Player

@SubscribesTo(ItemOnItemEvent::class)
class ItemOnItem : EventSubscriber<ItemOnItemEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemOnItemEvent) {

        if (player.playerRights >= 3) {
            player.packetSender.sendMessage("[ItemOnItem] - used: ${event.used} with: ${event.usedWith}")
        }

        if (event.used == BLACK_CANDLE && event.usedWith == TINDERBOX) {
            player.itemAssistant.addItem(LIT_BLACK_CANDLE, 1)
            player.itemAssistant.deleteItem(BLACK_CANDLE, 1)
        }
		
	}
	
}