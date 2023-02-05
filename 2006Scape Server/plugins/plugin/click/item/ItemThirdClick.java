package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemThirdClickEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.YOYO;

@SubscribesTo(ItemThirdClickEvent.class)
public final class ItemThirdClick implements EventSubscriber<ItemThirdClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemThirdClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemClick#3] - ItemId: " + event.getId());
        }

        switch (event.getId()) {

            case YOYO:
                player.startAnimation(1460);
                break;


        }
    }

}
