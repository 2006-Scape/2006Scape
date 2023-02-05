package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class LogoutButton extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            case 9154:
                player.logout();
                break;

        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 9154;
    }

}