package com.rebotted.game.content.traveling;

import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

public class Desert {

	/**
	 * The animation played when cutting the cactus.(SLASH)
	 */
	private static final int CUTTING_ANIMATION = 451;

	/**
	 * The dry cactus object id.
	 */
	private static final int DRY_CACTUS = 2671;

	/**
	 * The cactus re-spawning delay.
	 */
	private static final int CACTUS_DELAY = 20 + Misc.random(5);

	/**
	 * The cactus cutting items.
	 */
	public static int[] CACTUS_CUTTER = { 946 };

	/**
	 * Various filling vessels. (The water-skins)
	 */
	public static int[][] FILLS = { { 1825, 1823 }, { 1827, 1825 },
			{ 1829, 1827 }, { 1831, 1829 } };

	/**
	 * Gets the cutters.
	 * 
	 * @param player
	 *            Player c.
	 * @return Returns default value.
	 */
	public static int getCacCutter(Player player) {
		int cut = 0;
		for (int element : CACTUS_CUTTER) {
			if (player.getItemAssistant().playerHasItem(element)) {
				cut = element;
			}
		}
		return cut;
	}

	public static void showWarning(Player c) {
		for (int i = 8144; i < 8195; i++) {
			c.getPacketSender().sendFrame126("", i);
		}
		c.getPacketSender().sendFrame126("@dre@DESERT WARNING", 8144);
		c.getPacketSender().sendFrame126("", 8145);
		c.getPacketSender().sendFrame126("The intense heat of the desert reduces your health.", 8147);
		c.getPacketSender().sendFrame126("Bring 2-5 waterskins to avoid receiving any damage.", 8148);
		c.getPacketSender().sendFrame126("", 8149);
		c.getPacketSender().sendFrame126("Wearing desert robes will not prevent the damage, but", 8150);
		c.getPacketSender().sendFrame126("will reduce it significantly.", 8151);
		c.getPacketSender().sendFrame126("", 8152);
		c.getPacketSender().sendFrame126("The waterskins however need to be re-filled. Bring a", 8153);
		c.getPacketSender().sendFrame126("knife and cut healthy cacti to re-fill the waterskins.", 8154);
		c.getPacketSender().sendFrame126("@red@Any water vessels will evaporate, such as jug of water.", 8155);
		c.getPacketSender().showInterface(8134);
	}

	/**
	 * Handles fails and success attempts.
	 * 
	 * @param c
	 * @param objectId
	 * @param obX
	 * @param obY
	 */
	public static void checkCactus(Player c, int objectId, int obX, int obY) {
		int fail = Misc.random(2);
		if (fail == 1) {
			c.getPacketSender().sendMessage("You failed to cut the cactus.");
			c.getPlayerAssistant().addSkillXP(1, c.playerWoodcutting);
			return;
		}
		c.startAnimation(CUTTING_ANIMATION);
		c.getPacketSender().sendMessage("You slash away the cactus.");
		GameEngine.objectHandler.createAnObject(c, DRY_CACTUS, obX, obY, -1);
		for (int element[] : FILLS) {
			if (c.getItemAssistant().playerHasItem(element[0])) {
				c.getItemAssistant().deleteItem(element[0], c.getItemAssistant().getItemSlot(element[0]), 1);
				c.getItemAssistant().addItem(element[1], 1);
				c.getPlayerAssistant().addSkillXP(10, c.playerWoodcutting);
			}
		}
	}

	/**
	 * Cuts the cactus. Uses schedule task management.
	 * 
	 * @param c
	 *            The Player client.
	 * @param itemId
	 *            The cactus cutter item id.
	 * @param objectId
	 *            The cactus(healthy/dry) object id.
	 * @param obX
	 *            Gets the object coordinate x.
	 * @param obY
	 *            Gets the object coordinate y.
	 */
	public static void cutCactus(final Player c, int itemId, final int objectId, final int obX, final int obY) {
		for (int element : CACTUS_CUTTER) {
			if (itemId == element) {
				checkCactus(c, objectId, obX, obY);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
								GameEngine.objectHandler.createAnObject(c, objectId, obX, obY, -1);
								container.stop();
							}
							@Override
							public void stop() {
								
							}
						}, CACTUS_DELAY);
			} else {
				c.getPacketSender().sendMessage("You need a knife or a sharp weapon to cut this.");
			}
		}
	}

}
