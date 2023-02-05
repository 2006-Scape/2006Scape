package plugin.click.item;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ItemOnObjectEvent;
import com.rs2.game.items.impl.Fillables;
import com.rs2.game.players.Player;

@SubscribesTo(ItemOnObjectEvent.class)
public final class ItemOnObject implements EventSubscriber<ItemOnObjectEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ItemOnObjectEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[ItemOnObject] - itemId: " + event.getItem() + " objectId: " + event.getGameObject() + " Location: x: " + player.objectX + "y: " + player.objectY);
        }

        if (Fillables.canFill(event.getItem(), event.getGameObject()) && player.getItemAssistant().playerHasItem(event.getItem())) {
            //val amount = player.itemAssistant.getItemAmount(event.item)
            player.getItemAssistant().deleteItem(event.getItem(), 1);
            player.getItemAssistant().addItem(Fillables.counterpart(event.getItem()), 1);
            player.getPacketSender().sendMessage(Fillables.fillMessage(event.getItem(), event.getGameObject()));
            player.startAnimation(832);
            return;
        }
    }

}