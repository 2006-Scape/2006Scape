package plugin.buttons.gameframe;

import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.content.music.Music;
import com.rs2.game.players.Player;
import plugin.buttons.ButtonClick;

@SubscribesTo(ButtonActionEvent.class)
public final class MusicVolumeButtons extends ButtonClick {

    @Override
    protected void execute(Player player, ButtonActionEvent event) {
        switch (event.getButton()) {

            case 3162:
                if (player.musicOn) {
                    player.musicOn = false;
                } else {
                    player.getPacketSender().sendMessage("Your music is already turned off.");
                }
                break;
            case 3163:
            case 3164:
            case 3165:
            case 3166:
                Music.playMusic(player);
                player.musicOn = true;
                break;
        }
    }

    @Override
    public boolean test(ButtonActionEvent event) {
        return event.getButton() == 3162 || event.getButton() == 3163 || event.getButton() == 3164 || event.getButton() == 3165 || event.getButton() == 3166;
    }

}
