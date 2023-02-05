package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemOnNpcEvent;
import com.rs2.game.players.Player;

@SubscribesTo(ItemOnNpcEvent.class)
public final class ItemOnNpc implements EventSubscriber<ItemOnNpcEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemOnNpcEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemOnNpc] - itemId: " + event.getItem() + " npcId: " + event.getNpc());
        }
    }

}
