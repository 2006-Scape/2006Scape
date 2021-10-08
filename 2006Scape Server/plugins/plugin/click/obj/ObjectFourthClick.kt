package plugin.click.obj

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ObjectFourthClickEvent
import com.rs2.game.content.skills.farming.Farming
import com.rs2.game.players.Player
import com.rs2.world.clip.Region

@SubscribesTo(ObjectFourthClickEvent::class)
class ObjectFourthClick : EventSubscriber<ObjectFourthClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: ObjectFourthClickEvent) {

		if (player.playerRights >= 3) {
			player.packetSender.sendMessage("[click= object], [type= fourth], [id= ${player.objectId}], [location= x:${player.objectX} y:${player.objectY} ], [PLUGIN]");
		}

		if (!Region.objectExists(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
			return
		}

		Farming.guide(player, player.objectX, player.objectY)

		when (event.gameObject) {
		}
		
	}
	
}