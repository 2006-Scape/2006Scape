package plugin.buttons;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.players.Player;

public abstract class ButtonClick implements EventSubscriber<ButtonActionEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ButtonActionEvent event) {
        execute(player, event);
    }

    protected abstract void execute(Player player, ButtonActionEvent event);

    public abstract boolean test(ButtonActionEvent event);
}
