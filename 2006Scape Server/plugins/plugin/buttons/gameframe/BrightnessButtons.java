package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.items.impl.LightSources;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class BrightnessButtons extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {
            case 3138:
                LightSources.brightness1(player);
                break;
            case 3140:
                LightSources.brightness2(player);
                break;
            case 3142:
                LightSources.brightness3(player);
                break;
            case 3144:
                LightSources.brightness4(player);
                break;

        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 3138 || event.getButton() == 3140 || event.getButton() == 3142 || event.getButton() == 3144;
    }
}
