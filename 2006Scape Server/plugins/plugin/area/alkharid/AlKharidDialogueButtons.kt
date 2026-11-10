package plugin.area.alkharid

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.players.Player
import plugin.area.alkharid.AlKharidGateUtil.DIALOGUE_ACTION_SUB_TOLL
import plugin.area.alkharid.AlKharidGateUtil.DIALOGUE_ACTION_TOLL
import plugin.area.alkharid.AlKharidGateUtil.isNearGate
import plugin.area.alkharid.AlKharidGateUtil.passThroughGate
import plugin.area.alkharid.AlKharidGateUtil.payToll

@SubscribesTo(ButtonActionEvent::class)
class AlKharidDialogueButtons : EventSubscriber<ButtonActionEvent> {

    override fun subscribe(context: EventContext, player: Player, event: ButtonActionEvent) {
        val action = player.dialogueAction
        if (action != DIALOGUE_ACTION_TOLL && action != DIALOGUE_ACTION_SUB_TOLL) {
            return
        }

        val button = event.button

        if (action == DIALOGUE_ACTION_TOLL) {
            when (button) {
                // Option 1: "Okay, I'll pay."
                9167 -> {
                    if (!isNearGate(player)) {
                        player.packetSender.sendMessage("You need to be closer to the gate to use it.")
                        player.packetSender.closeAllWindows()
                        player.dialogueAction = 0
                        return
                    }
                    if (player.payToll()) {
                        player.packetSender.sendMessage("You pay the border guard 10 coins.")
                        passThroughGate(player)
                        player.nextChat = 0
                    } else {
                        player.dialogueHandler.sendPlayerChat("I haven't got that much.")
                        player.nextChat = 1028 // Guard: "Well, you can't come through here then."
                    }
                    player.dialogueAction = 0
                }
                // Option 2: "Who does my money go to?"
                9168 -> {
                    player.dialogueHandler.sendPlayerChat("Who does my money go to?")
                    player.nextChat = 1023
                    player.dialogueAction = 0
                }
                // Option 3: "No thanks, I'll walk around."
                9169 -> {
                    player.dialogueHandler.sendPlayerChat("No thanks, I'll walk around.")
                    player.nextChat = 1029 // Guard: "Ok suit yourself."
                    player.dialogueAction = 0
                }
            }
        } else if (action == DIALOGUE_ACTION_SUB_TOLL) {
            when (button) {
                // Option 1: "Okay, I'll pay."
                9157 -> {
                    if (!isNearGate(player)) {
                        player.packetSender.sendMessage("You need to be closer to the gate to use it.")
                        player.packetSender.closeAllWindows()
                        player.dialogueAction = 0
                        return
                    }
                    if (player.payToll()) {
                        player.packetSender.sendMessage("You pay the border guard 10 coins.")
                        passThroughGate(player)
                        player.nextChat = 0
                    } else {
                        player.dialogueHandler.sendPlayerChat("I haven't got that much.")
                        player.nextChat = 1028 // Guard: "Well, you can't come through here then."
                    }
                    player.dialogueAction = 0
                }
                // Option 2: "No thanks, I'll walk around."
                9158 -> {
                    player.dialogueHandler.sendPlayerChat("No thanks, I'll walk around.")
                    player.nextChat = 1029 // Guard: "Ok suit yourself."
                    player.dialogueAction = 0
                }
            }
        }
    }
}
