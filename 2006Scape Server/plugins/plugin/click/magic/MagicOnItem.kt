package plugin.click.magic

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.MagicOnItemEvent
import com.rs2.game.content.skills.smithing.Superheat
import com.rs2.game.players.Player

@SubscribesTo(MagicOnItemEvent::class)
class MagicOnItem : EventSubscriber<MagicOnItemEvent> {

	override fun subscribe(context: EventContext, player: Player, event: MagicOnItemEvent) {
		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[MagicOnItem] - ItemId: ${event.itemId} Slot: ${event.slot} SpellId: ${event.spellId}");
        }
		
		when(event.spellId) {
			1173 -> if (!Superheat.superHeatItem(player, event.itemId)) {
						return;
					}
		}
		
	}
	
}