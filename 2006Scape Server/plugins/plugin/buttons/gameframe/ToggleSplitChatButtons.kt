package plugin.buttons.gameframe

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.players.Player
import plugin.buttons.ButtonClick


@SubscribesTo(ButtonActionEvent::class)
class ToggleSplitChatButtons : ButtonClick() {

    override fun execute(player: Player, event: ButtonActionEvent) {
        when (event.button) {
            3189 -> {
                player.packetSender.sendConfig(502, 1)
                player.packetSender.sendConfig(287, 1)
                player.splitChat = true
            }

            3190 -> {
                player.packetSender.sendConfig(502, 0)
                player.packetSender.sendConfig(287, 0)
                player.splitChat = false
            }
        }
    }

    override fun test(event: ButtonActionEvent): Boolean {
        return event.button == 3189 || event.button == 3190
    }

}