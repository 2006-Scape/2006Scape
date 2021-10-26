package plugin.buttons.gameframe

import com.rs2.event.SubscribesTo
import com.rs2.event.impl.ButtonActionEvent
import com.rs2.game.content.music.Music
import com.rs2.game.players.Player
import plugin.buttons.ButtonClick

@SubscribesTo(ButtonActionEvent::class)
class MusicVolumeButtons : ButtonClick() {

    override fun execute(player: Player, event: ButtonActionEvent) {
        when (event.button) {
            3162 -> {
                if (player.musicOn) {
                    player.musicOn = false
                } else {
                    player.packetSender.sendMessage("Your music is already turned off.")
                }
            }
            3163,3164,3165,3166 -> {
                Music.playMusic(player)
                player.musicOn = true
            }
        }
    }

    override fun test(event: ButtonActionEvent): Boolean {
        return event.button == 3162 || event.button == 3163 || event.button == 3164 || event.button == 3165 || event.button == 3166
    }

}