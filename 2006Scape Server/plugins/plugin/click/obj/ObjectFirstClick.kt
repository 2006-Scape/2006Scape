package plugin.click.obj

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ObjectFirstClickEvent
import com.rs2.game.content.skills.core.Mining
import com.rs2.game.players.Player

@SubscribesTo(ObjectFirstClickEvent::class)
class ObjectFirstClick : EventSubscriber<ObjectFirstClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: ObjectFirstClickEvent) {
		
		if (player.playerRights >= 3) {
            player.packetSender.sendMessage("[click= object], [type= first], [id= ${player.objectId}], [location= x:${player.objectX} y:${player.objectY} ]")
        }

		// if its a rock we can mine, mine it
		if (Mining.rockExists(event.gameObject)) {
			player.mining.startMining(player, event.gameObject, player.objectX, player.objectY, player.clickObjectType)
			return
		}
		
		when (event.gameObject) {
			
		}
		
	}
	
}