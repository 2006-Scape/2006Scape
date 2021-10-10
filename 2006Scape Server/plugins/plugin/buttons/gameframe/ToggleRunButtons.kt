package plugin.buttons.gameframe

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.players.Player
import plugin.buttons.ButtonClick


@SubscribesTo(ButtonActionEvent::class)
class ToggleRunButtons : ButtonClick() {

    override fun execute(player: Player, event: ButtonActionEvent) {
        when (event.button) {
            152 -> {
                player.packetSender.sendConfig(173, 0)
                player.isRunning = false
                player.isRunning2 = false

            }

            153 -> {
                if (player.tutorialProgress == 11) {
                    player.dialogueHandler.sendDialogues(3041, 0)
                }
                player.packetSender.sendConfig(173, 1)
                player.isRunning = true
                player.isRunning2 = true
            }
        }
    }
    override fun test(event: ButtonActionEvent): Boolean {
            return event.button == 152 || event.button == 153
    }
}