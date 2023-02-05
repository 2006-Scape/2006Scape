package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemFirstClickEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.YOYO;

@SubscribesTo(ItemFirstClickEvent.class)
public final class ItemFirstClick implements EventSubscriber<ItemFirstClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemFirstClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemClick#1] - Item: " + event.getItem());
        }

        switch (event.getItem()) {

            case YOYO:
                player.startAnimation(1457);
                break;

        }
    }

}
