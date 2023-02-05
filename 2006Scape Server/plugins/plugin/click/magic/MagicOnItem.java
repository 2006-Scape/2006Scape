package plugin.click.magic;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.MagicOnItemEvent;
import com.rs2.game.content.skills.smithing.Superheat;
import com.rs2.game.players.Player;

@SubscribesTo(MagicOnItemEvent.class)
public final class MagicOnItem implements EventSubscriber<MagicOnItemEvent> {

    @Override
    public void subscribe(EventContext context, Player player, MagicOnItemEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[MagicOnItem] - ItemId: " + event.getItemId() + " Slot: " + event.getSlot() + " SpellId: " + event.getSpellId());
        }

        switch (event.getSpellId()) {
            case 1173:
                if (!Superheat.superHeatItem(player, event.getItemId())) {
                    return;
                }
                break;
        }
    }

}