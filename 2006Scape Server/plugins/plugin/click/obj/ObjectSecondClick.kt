package plugin.click.obj

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ObjectSecondClickEvent
import com.rs2.game.content.skills.thieving.Stalls
import com.rs2.game.players.Player

@SubscribesTo(ObjectSecondClickEvent::class)
class ObjectSecondClick : EventSubscriber<ObjectSecondClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: ObjectSecondClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[click= object], [type= second], [id= ${player.objectId}], [location= x:${player.objectX} y:${player.objectY} ]");
		}

		if (Stalls.isObject(event.gameObject)) {
			Stalls.attemptStall(player, event.gameObject, player.objectX, player.objectY)
			return
		}

		when (event.gameObject) {

		}
		
	}
	
}