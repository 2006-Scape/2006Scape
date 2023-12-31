package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.content.music.Music;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class SoundVolumeButtons extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            case 3173:
                if (player.soundOn) {
                    player.soundOn = false;
                } else {
                    player.getPacketSender().sendMessage("Your sound is already turned off.");
                }
                break;
            case 3174:
            case 3175:
            case 3176:
            case 3177:
                player.soundOn = true;
                break;
        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 3173 || event.getButton() == 3174 || event.getButton() == 3175 || event.getButton() == 3176 || event.getButton() == 3177;
    }

}
