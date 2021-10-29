package plugins.plugin.buttons

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import plugin.buttons.ButtonClick
import com.rs2.game.dialogues.AstraeusDialogue
import com.rs2.game.players.Player

@SubscribesTo(ButtonActionEvent::class)
class DialogueOptionButtons : ButtonClick() {

    override fun execute(player: Player, event: ButtonActionEvent) {
        when (event.button) {

            // First Option (Option Interfaces 2, 3, 4, and 5)
            9157, 9167, 9178, 9190 ->
                player.dialogueFactory.executeOption(0, player.optionDialogue)

            // Second Option (Option Interfaces 2, 3, 4, and 5)
            9158, 9168, 9179, 9191 ->
                player.dialogueFactory.executeOption(1, player.optionDialogue)

            // Third Option (Option Interfaces 3, 4, and 5)
            9169, 9180, 9192 ->
                player.dialogueFactory.executeOption(2, player.optionDialogue)

            // Fourth Option (Option Interfaces 4 and 5)
            9181, 9193 ->
                player.dialogueFactory.executeOption(3, player.optionDialogue)

            // Fifth Option (Option Interface 5)
            9194 ->
                player.dialogueFactory.executeOption(4, player.optionDialogue)
        }
    }

    override fun test(event: ButtonActionEvent): Boolean {
        return AstraeusDialogue.isDialogueButton(event.button)
    }
}