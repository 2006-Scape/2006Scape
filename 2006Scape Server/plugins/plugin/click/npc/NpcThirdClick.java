package plugin.click.npc;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.NpcThirdClickEvent;
import com.rs2.game.players.Player;

@SubscribesTo(NpcThirdClickEvent.class)
public final class NpcThirdClick implements EventSubscriber<NpcThirdClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, NpcThirdClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= npc], [type = third], [id= " + event.getNpc() + "], [Type= " + event.getNpc() + "]");
        }
    }

}
