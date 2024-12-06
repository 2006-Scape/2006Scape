package plugin.npc.shopkeeper;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.NpcFirstClickEvent;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticNpcList.SHOP_KEEPER_528;

@SubscribesTo(NpcFirstClickEvent.class)
public final class FirstClick implements EventSubscriber<NpcFirstClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, NpcFirstClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= npc], [type = first], [id= " + event.getNpc() + "], [Type= " + event.getNpc() + "]");
        }

        switch (event.getNpc()) {

            case SHOP_KEEPER_528:
                player.getDialogueFactory().sendDialogue(new GeneralStoreDialogue());
                break;
        }
    }

}
