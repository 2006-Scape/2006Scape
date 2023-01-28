package plugin.click.obj

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ObjectThirdClickEvent
import com.rs2.game.content.StaticObjectList.IRON_LADDER_10177
import com.rs2.game.content.skills.thieving.Stalls
import com.rs2.game.players.Player
import com.rs2.world.clip.Region

@SubscribesTo(ObjectThirdClickEvent::class)
class ObjectThirdClick : EventSubscriber<ObjectThirdClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: ObjectThirdClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[click= object], [type= third], [id= ${player.objectId}], [location= x:${player.objectX} y:${player.objectY} ], [PLUGIN]");
		}

		if (!Region.objectExists(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
			return
		}

		if (Stalls.isObject(event.gameObject)) {
			Stalls.attemptStall(player, event.gameObject, player.objectX, player.objectY)
			return
		}

		when (event.gameObject) {
			IRON_LADDER_10177 -> player.playerAssistant.movePlayer(1798, 4407, 3);
		}
		
	}
	
}