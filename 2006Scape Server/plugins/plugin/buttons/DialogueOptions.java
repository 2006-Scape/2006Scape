package plugin.buttons;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.dialog.Dialogue;
import com.rs2.game.players.Player;

@SubscribesTo(ButtonActionEvent.class)
public final class DialogueOptions extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            // First Option (Option Interfaces 2, 3, 4, and 5)
            case 9157:
            case 9167:
            case 9178:
            case 9190:
                player.getDialogueFactory().executeOption(0, player.getOptionDialogue());
                break;
            // Second Option (Option Interfaces 2, 3, 4, and 5)
            case 9158:
            case 9168:
            case 9179:
            case 9191:
                player.getDialogueFactory().executeOption(1, player.getOptionDialogue());
                break;
            // Third Option (Option Interfaces 3, 4, and 5)
            case 9169:
            case 9180:
            case 9192:
                player.getDialogueFactory().executeOption(2, player.getOptionDialogue());
                break;
            // Fourth Option (Option Interfaces 4 and 5)
            case 9181:
            case 9193:
                player.getDialogueFactory().executeOption(3, player.getOptionDialogue());
                break;
            // Fifth Option (Option Interface 5)
            case 9194:
                player.getDialogueFactory().executeOption(4, player.getOptionDialogue());
                break;

        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return Dialogue.isDialogueButton(event.getButton());
    }

}
