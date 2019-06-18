package redone.game.globalworldobjects;

import redone.game.objects.Object;
import redone.game.objects.ObjectDefaults;
import redone.game.players.Client;
import redone.world.clip.ObjectDef;
import redone.world.clip.Region;

public class ClimbOther {

	public enum ClimbData {

		VARROCK_MANHOLE(881, 882),
		LUMBRIDGE_TRAPDOOR(14879, 10698),
		VARROCK_TRAPDOOR(1568, 10698);

		private int closedId, openId;

		private ClimbData(int closedId, int openId) {
			this.closedId = closedId;
			this.openId = openId;
		}

		public int getClosed() {
			return closedId;
		}

		public int getOpen() {
			return openId;
		}
	}

	public static void handleOpenOther(Client player, int objectType) {
		for (ClimbData t: ClimbData.values()) {
			if (objectType == t.getClosed()) {
				new Object(t.getOpen(), player.objectX, player.objectY, player.heightLevel, ObjectDefaults.getObjectFace(player, t.getClosed()), 10, t.getClosed(), 100);
				Region.addObject(t.getOpen(), player.objectX, player.objectY, player.heightLevel, 10,  ObjectDefaults.getObjectFace(player, t.getClosed()), false);
			}
		}
	}
	
	public static void useOther(Client player, int objectType) {
		final String objectName = ObjectDef.getObjectDef(objectType).name;
		if (System.currentTimeMillis() - player.climbDelay < 1800) {
			return;
		}
		player.stopMovement();
		player.startAnimation(827);
		player.getPlayerAssistant().removeAllWindows();
		player.teleportToX = player.absX;
		player.teleportToY = player.absY + 6400;
		player.getActionSender().sendMessage("You climb down the " + objectName.toLowerCase() + ".");
		player.climbDelay = System.currentTimeMillis();
	}

}
