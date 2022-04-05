package plugin.click.item

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ItemOnObjectEvent
import com.rs2.game.items.impl.Fillables
import com.rs2.game.players.Player


@SubscribesTo(ItemOnObjectEvent::class)
class ItemOnObject : EventSubscriber<ItemOnObjectEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ItemOnObjectEvent) {
        if (player.playerRights >= 3) {
            player.packetSender.sendMessage("[ItemOnObject] - itemId:  ${event.item} objectId: ${event.gameObject} Location: x: ${player.objectX}, x: ${player.objectY}")
        }

        if (Fillables.canFill(event.item, event.gameObject) && player.itemAssistant.playerHasItem(event.item)) {
            //val amount = player.itemAssistant.getItemAmount(event.item)
            player.itemAssistant.deleteItem(event.item, 1)
            player.itemAssistant.addItem(Fillables.counterpart(event.item), 1)
            player.packetSender.sendMessage(Fillables.fillMessage(event.item, event.gameObject))
            player.startAnimation(832)
            return
        }

	}
	
}