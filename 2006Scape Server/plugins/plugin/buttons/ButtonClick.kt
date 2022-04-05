package plugin.buttons

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.players.Player


abstract class ButtonClick : EventSubscriber<ButtonActionEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ButtonActionEvent) {
		execute(player, event)
	}
	
	abstract fun execute(player : Player, event : ButtonActionEvent);

}