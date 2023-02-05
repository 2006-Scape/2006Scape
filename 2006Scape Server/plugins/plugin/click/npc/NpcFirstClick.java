package plugin.click.npc;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.NpcFirstClickEvent;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import static com.rs2.game.content.StaticNpcList.*;

@SubscribesTo(NpcFirstClickEvent.class)
public final class NpcFirstClick implements EventSubscriber<NpcFirstClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, NpcFirstClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= npc], [type = first], [id= " + event.getNpc() + "], [Type= " + event.getNpc() + "]");
        }

        switch (event.getNpc()) {

            case MAN:
            case MAN_2:
            case MAN_3:
            case WOMAN:
            case WOMAN_5:
            case WOMAN_6:
                if (Misc.random(10) <= 5) {
                    player.getDialogueHandler().sendDialogues(3869, player.npcType);
                } else {
                    player.getDialogueHandler().sendDialogues(3872, player.npcType);
                }
                break;
        }
    }

}
