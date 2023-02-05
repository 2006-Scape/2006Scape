package plugin.click.npc;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.NpcSecondClickEvent;
import com.rs2.game.content.skills.thieving.Pickpocket;
import com.rs2.game.players.Player;

@SubscribesTo(NpcSecondClickEvent.class)
public final class NpcSecondClick implements EventSubscriber<NpcSecondClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, NpcSecondClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= npc], [type = second], [id= " + event.getNpc() + "], [Type= " + event.getNpc() + "]");
        }

        if (Pickpocket.isNPC(player, player.npcType)) {
            Pickpocket.attemptPickpocket(player, player.npcType);
            return;
        }
    }

}
