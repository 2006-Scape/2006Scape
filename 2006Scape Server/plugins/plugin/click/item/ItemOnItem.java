package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemOnItemEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.*;

@SubscribesTo(ItemOnItemEvent.class)
public final class ItemOnItem implements EventSubscriber<ItemOnItemEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemOnItemEvent event) {

        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemOnItem] - used: " + event.getUsed() + " with: " + event.getUsedWith());
        }

        if (event.getUsed() == BLACK_CANDLE && event.getUsedWith() == TINDERBOX) {
            player.getItemAssistant().addItem(LIT_BLACK_CANDLE, 1);
            player.getItemAssistant().deleteItem(BLACK_CANDLE, 1);
        }
    }

}
