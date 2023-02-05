package plugin.click.obj;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ObjectThirdClickEvent;
import com.rs2.game.content.skills.thieving.Stalls;
import com.rs2.game.players.Player;
import com.rs2.world.clip.Region;

import static com.rs2.game.content.StaticObjectList.IRON_LADDER_10177;

@SubscribesTo(ObjectThirdClickEvent.class)
public final class ObjectThirdClick implements EventSubscriber<ObjectThirdClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ObjectThirdClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= object], [type= third], [id= " + player.objectId + "], [location= x:" + player.objectX + " y:" + player.objectY + "]");
        }

        if (!Region.objectExists(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
            return;
        }

        if (Stalls.isObject(event.getGameObject())) {
            Stalls.attemptStall(player, event.getGameObject(), player.objectX, player.objectY);
            return;
        }

        switch (event.getGameObject()) {
            case IRON_LADDER_10177:
                player.getPlayerAssistant().movePlayer(1798, 4407, 3);
                break;
        }
    }

}
