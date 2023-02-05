package plugin.click.obj;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ObjectSecondClickEvent;
import com.rs2.game.content.skills.thieving.Stalls;
import com.rs2.game.players.Player;
import com.rs2.world.clip.Region;

@SubscribesTo(ObjectSecondClickEvent.class)
public final class ObjectSecondClick implements EventSubscriber<ObjectSecondClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ObjectSecondClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= object], [type= second], [id= " + player.objectId + "], [location= x:" + player.objectX + " y:" + player.objectY + "]");
        }

        if (!Region.objectExists(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
            return;
        }

        if (Stalls.isObject(event.getGameObject())) {
            Stalls.attemptStall(player, event.getGameObject(), player.objectX, player.objectY);
            return;
        }
    }

}
