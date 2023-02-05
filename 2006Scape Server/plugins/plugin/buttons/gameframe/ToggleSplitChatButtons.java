package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class ToggleSplitChatButtons extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            case 3189:
                player.getPacketSender().sendConfig(502, 1);
                player.getPacketSender().sendConfig(287, 1);
                player.splitChat = true;
                break;
            case 3190:
                player.getPacketSender().sendConfig(502, 0);
                player.getPacketSender().sendConfig(287, 0);
                player.splitChat = false;
                break;

        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 3189 || event.getButton() == 3190;
    }

}