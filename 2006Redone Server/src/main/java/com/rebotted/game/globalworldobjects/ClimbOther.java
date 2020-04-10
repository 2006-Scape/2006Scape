package com.rebotted.game.globalworldobjects;

import com.rebotted.game.objects.Object;
import com.rebotted.game.objects.ObjectDefaults;
import com.rebotted.game.players.Player;
import com.rebotted.world.clip.ObjectDefinition;
import com.rebotted.world.clip.Region;

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

	public static void handleOpenOther(Player player, int objectType) {
		for (ClimbData t: ClimbData.values()) {
			if (objectType == t.getClosed()) {
				new Object(t.getOpen(), player.objectX, player.objectY, player.heightLevel, ObjectDefaults.getObjectFace(player, t.getClosed()), 10, t.getClosed(), 100);
				Region.addObject(t.getOpen(), player.objectX, player.objectY, player.heightLevel, 10,  ObjectDefaults.getObjectFace(player, t.getClosed()), false);
			}
		}
	}
	
	public static void useOther(Player player, int objectType) {
		final String objectName = ObjectDefinition.getObjectDef(objectType).name;
		if (System.currentTimeMillis() - player.climbDelay < 1800) {
			return;
		}
		player.stopMovement();
		player.startAnimation(827);
		player.getPacketSender().closeAllWindows();
		player.teleportToX = player.absX;
		player.teleportToY = player.absY + 6400;
		player.getPacketSender().sendMessage("You climb down the " + objectName.toLowerCase() + ".");
		player.climbDelay = System.currentTimeMillis();
	}

}
