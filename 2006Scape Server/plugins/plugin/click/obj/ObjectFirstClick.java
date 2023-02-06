package plugin.click.obj;

import com.rs2.event.EventContext;
import com.rs2.event.EventSubscriber;
import com.rs2.event.SubscribesTo;
import com.rs2.event.impl.ObjectFirstClickEvent;
import com.rs2.game.content.StaticObjectList;
import com.rs2.game.content.skills.core.Mining;
import com.rs2.game.players.Player;
import com.rs2.world.clip.Region;

@SubscribesTo(ObjectFirstClickEvent.class)
public final class ObjectFirstClick implements EventSubscriber<ObjectFirstClickEvent> {

    @Override
    public void subscribe(EventContext context, Player player, ObjectFirstClickEvent event) {
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("[click= object], [type= first], [id= " + player.objectId + "], [location= x:" + player.objectX + " y:" + player.objectY + "]");
        }

        if (!Region.objectExists(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
            return;
        }

        // if its a rock we can mine, mine it
        if (Mining.rockExists(event.getGameObject())) {
            player.getMining().startMining(player, event.getGameObject(), player.objectX, player.objectY, player.clickObjectType);
            return;
        }
        
        //TODO when plugins occur, move the handling of this to the specific plugin for those map areas.
        if(event.getGameObject() == StaticObjectList.CRATE_6839) {
        	player.getShopAssistant().openShop(146);
        }

    }

}
