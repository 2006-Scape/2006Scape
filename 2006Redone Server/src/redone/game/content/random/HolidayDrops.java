package redone.game.content.random;

import redone.Constants;
import redone.game.players.PlayerHandler;

/**
 * Holiday Drops
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public enum HolidayDrops {
	EASTER(1961, "Easter", false), HALLOWEEN(1053, "Halloween", true), CHRISTMAS(962, "Christmas", false);

	private final int item;// holiday item
	private final String name;// holiday name
	private final boolean whichHoliday;// if true, this holiday will be dropped
	public final int DROP_DISTANCE = 40;// distance of drops
	public int count = 0;// count starts at 0 and ends at 400 can be changed
	public final int drops = 7;// this is just for random number of the drops
	public static int DROP_AMOUNT = PlayerHandler.playerCount * 5;// players
	// online *
	// 5 = drop
	// amount
	public static int dropAmount() {
		int amount = DROP_AMOUNT;
		if (Constants.SERVER_DEBUG)  {
			return amount * 60;
		}
		return amount;
	}

	private HolidayDrops(int item, String name, boolean whichHoliday) {
		this.item = item;
		this.name = name;
		this.whichHoliday = whichHoliday;
	}

	public int getItem() {
		return item;
	}

	public String getName() {
		return name;
	}

	public boolean getHoliday() {
		return whichHoliday;
	}
	
	/*private static int addItem() {
		int random = 2;
		if (Misc.random(random) == 0) {
			return 1053;
		} else if (Misc.random(random) == 1) {
			return 1055;
		} else if (Misc.random(random) == 2) {
			return 1057;
		}
		return -1;
	}*/

	public final int[][] COORDS = { { 3214, 3424 }, // Varrock
			{ 3222, 3218 }, // Lumbridge
			{ 2964, 3378 }, // Falador
			{ 3082, 3419 }, // Barb
			{ 3082, 3249 }, // Draynor
			{ 3293, 3180 }, // Al Kharid
			{ 3034, 3246 }, // Rimmington
	};
}
