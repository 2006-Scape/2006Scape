package redone.game.content.combat.magic;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;

public class CastOnOther extends CastRequirements {

	public static boolean castOnOtherSpells(Client c) {
		int[] spells = { 12435, 12455, 12425, 30298, 30290, 30282, };
		for (int spell : spells) {
			if (c.castingSpellId == spell) {
				return true;
			}
		}
		return false;
	}

	public static void teleOtherDistance(Client c, int type, int i) {
		Client castOn = (Client) PlayerHandler.players[i];
		int[][] data = { { 74, SOUL, LAW, EARTH, 1, 1, 1 },
				{ 82, SOUL, LAW, WATER, 1, 1, 1 },
				{ 90, SOUL, LAW, -1, 2, 1, -1 }, };
		if (!hasRequiredLevel(c, data[type][0])) {
			c.getActionSender().sendMessage(
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
					c.getPlayerAssistant().sendFrame126(location[type], 12560);
					c.getPlayerAssistant().sendFrame126(c.playerName, 12558);
					c.getPlayerAssistant().showInterface(12468);
					castOn.teleotherType = type;
				}
			}
		}
	}

	public static void teleOtherLocation(final Client c, final int i,
			boolean decline) {
		c.getPlayerAssistant().removeAllWindows();
		final int[][] coords = { { 3222, 3218 }, // LUMBRIDGE
				{ 2964, 3378 }, // FALADOR
				{ 2757, 3477 }, // CAMELOT
		};
		if (!decline) {
			   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					c.startAnimation(715);
					c.teleportToX = coords[c.teleotherType][0];
					c.teleportToY = coords[c.teleotherType][1];
					c.teleotherType = -1;
					container.stop();
				}
				@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
			}, 3);
			c.startAnimation(1816);
			c.gfx100(342);
		}
	}
}
