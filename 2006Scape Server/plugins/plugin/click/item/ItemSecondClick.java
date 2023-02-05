package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemSecondClickEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.YOYO;

@SubscribesTo(ItemSecondClickEvent.class)
public final class ItemSecondClick implements EventSubscriber<ItemSecondClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemSecondClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemClick#2] - ItemId: " + event.getId());
        }

        switch (event.getId()) {

            case YOYO:
                player.startAnimation(1459);
                break;

        }
    }

}
