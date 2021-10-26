package plugin.buttons.gameframe

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.items.impl.LightSources
import com.rs2.game.players.Player
import plugin.buttons.ButtonClick

@SubscribesTo(ButtonActionEvent::class)
class BrightnessButtons : ButtonClick() {

	override fun execute(player: Player, event: ButtonActionEvent) {
		when (event.button) {
		3138 -> LightSources.brightness1(player)
		3140 -> LightSources.brightness2(player)
		3142 -> LightSources.brightness3(player)
		3144 -> LightSources.brightness4(player)
		}
	}

	override fun test(event: ButtonActionEvent): Boolean {
		return event.button == 3138 || event.button == 3140 || event.button == 3142 || event.button == 912
	}

}