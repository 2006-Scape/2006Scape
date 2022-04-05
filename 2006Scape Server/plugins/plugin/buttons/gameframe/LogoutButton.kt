package plugin.buttons.gameframe

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent

import com.rs2.game.players.Player
import plugin.buttons.ButtonClick

@SubscribesTo(ButtonActionEvent::class)
class LogoutButton : ButtonClick() {

	override fun execute(player: Player, event: ButtonActionEvent) {
		player.logout()
	}

	override fun test(event: ButtonActionEvent): Boolean {
		return event.button == 9154
	}

}