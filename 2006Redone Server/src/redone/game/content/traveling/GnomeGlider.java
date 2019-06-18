package redone.game.content.traveling;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

public class GnomeGlider {

	private static final int[][] GLIDER_DATA = { { 3058, 2848, 3497, 0, 1 }, // TO
																				// MOUNTAIN
			{ 3057, 2465, 3501, 3, 2 }, // TO GRAND TREE
			{ 3059, 3321, 3427, 0, 3 }, // TO CASTLE
			{ 3060, 3278, 3212, 0, 4 }, // TO DESERT
			{ 3056, 2894, 2730, 0, 8 }, // TO CRASH ISLAND
			{ 48054, 2544, 2970, 0, 10 }, // TO OGRE AREA
	};

	public static void flightButtons(Client player, int button) {
		if (player.gliderOpen == true) {
			for (int i = 0; i < getLength(); i++) {
				if (getButton(i) == button) {
					handleFlight(player, i);
				}
			}
		} else {
			for (int i = 0; i < getLength(); i++) {
				if (player.gliderOpen == false && getButton(i) == button) {
					player.getActionSender().sendMessage("You have improperly opened the glider.");
				}
			}
		}
	}

	public static void handleFlight(final Client player, final int flightId) {
		player.getPlayerAssistant().showInterface(802);
		player.getPlayerAssistant().sendConfig(153, getMove(flightId));
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				player.getPlayerAssistant().movePlayer(getX(flightId), getY(flightId), getH(flightId));
				container.stop();
			}
			@Override
			public void stop() {
				
			}
		}, 3);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				player.getPlayerAssistant().closeAllWindows();
				player.getPlayerAssistant().sendConfig(153, -1);
				player.gliderOpen = false;
				container.stop();
			}
			@Override
			public void stop() {
				
			}
		}, 4);

	}

	public static int getLength() {
		return GLIDER_DATA.length;
	}

	public static int getButton(int i) {
		return GLIDER_DATA[i][0];
	}

	public static int getX(int i) {
		return GLIDER_DATA[i][1];
	}

	public static int getY(int i) {
		return GLIDER_DATA[i][2];
	}

	public static int getH(int i) {
		return GLIDER_DATA[i][3];
	}

	public static int getMove(int i) {
		return GLIDER_DATA[i][4];
	}
}
