package com.rebotted.game.content.combat.magic;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;

public class CastOnOther extends CastRequirements {

	public static boolean castOnOtherSpells(int castingSpellId) {
		int[] spells = { 12435, 12455, 12425, 30298, 30290, 30282, };
		for (int spell : spells) {
			if (castingSpellId == spell) {
				return true;
			}
		}
		return false;
	}

	public static void teleOtherDistance(Player c, int type, int i) {
		Player castOn = (Client) PlayerHandler.players[i];
		int[][] data = { { 74, SOUL, LAW, EARTH, 1, 1, 1 },
				{ 82, SOUL, LAW, WATER, 1, 1, 1 },
				{ 90, SOUL, LAW, -1, 2, 1, -1 }, };
		if (!hasRequiredLevel(c, data[type][0])) {
			c.getPacketSender().sendMessage(
					"You need to have a magic level of " + data[type][0]
							+ " to cast this spell.");
			return;
		}
		if (!hasRunes(c, new int[] { data[type][1], data[type][2],
				data[type][3] }, new int[] { data[type][4], data[type][5],
				data[type][6] })) {
			return;
		}
		deleteRunes(c,
				new int[] { data[type][1], data[type][2], data[type][3] },
				new int[] { data[type][4], data[type][5], data[type][6] });
		String[] location = { "Lumbridge", "Falador", "Camelot", };
		c.faceUpdate(i + 32768);
		c.startAnimation(1818);
		c.gfx0(343);
		if (castOn != null) {
			if (castOn.distanceToPoint(c.absX, c.absY) <= 15) {
				if (c.heightLevel == castOn.heightLevel) {
					castOn.getPacketSender().sendString(location[type], 12560);
					castOn.getPacketSender().sendString(c.playerName, 12558);
					castOn.getPacketSender().showInterface(12468);
					castOn.teleotherType = type;
				}
			}
		}
	}

	public static void teleOtherLocation(final Player player, final int i,
			boolean decline) {
		player.getPacketSender().closeAllWindows();
		final int[][] coords = { { 3222, 3218 }, // LUMBRIDGE
				{ 2967, 3378 }, // FALADOR
				{ 2757, 3477 }, // CAMELOT
		};
		if (!decline) {
			   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					player.startAnimation(715);
					player.teleportToX = coords[player.teleotherType][0];
					player.teleportToY = coords[player.teleotherType][1];
					player.teleotherType = -1;
					container.stop();
				}
				@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
			}, 3);
			player.startAnimation(1816);
			player.gfx100(342);
		}
	}
}
