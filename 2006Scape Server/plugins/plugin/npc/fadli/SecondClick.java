package plugin.npc.fadli;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.NpcSecondClickEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticNpcList.FADLI;

@SubscribesTo(NpcSecondClickEvent.class)
public final class SecondClick implements EventSubscriber<NpcSecondClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, NpcSecondClickEvent event) {
        if (event.getNpc() == FADLI) {
            player.getPacketSender().openUpBank();
        }
    }

}
