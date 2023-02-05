package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class ToggleRunButtons extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            case 152:
                player.getPacketSender().sendConfig(173, 0);
                player.isRunning = false;
                player.isRunning2 = false;
                break;
            case 153:
                if (player.tutorialProgress == 11) {
                    player.getDialogueHandler().sendDialogues(3041, 0);
                }
                player.getPacketSender().sendConfig(173, 1);
                player.isRunning = true;
                player.isRunning2 = true;
                break;
        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 152 || event.getButton() == 153;
    }

}