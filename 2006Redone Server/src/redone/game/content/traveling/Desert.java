package redone.game.content.traveling;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.util.Misc;

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
	 * @param c
	 *            Player c.
	 * @return Returns default value.
	 */
	public static int getCacCutter(Client c) {
		int cut = 0;
		for (int element : CACTUS_CUTTER) {
			if (c.getItemAssistant().playerHasItem(element)) {
				cut = element;
			}
		}
		return cut;
	}

	public static void showWarning(Client c) {
		for (int i = 8144; i < 8195; i++) {
			c.getPlayerAssistant().sendFrame126("", i);
		}
		c.getPlayerAssistant().sendFrame126("@dre@DESERT WARNING", 8144);
		c.getPlayerAssistant().sendFrame126("", 8145);
		c.getPlayerAssistant().sendFrame126("The intense heat of the desert reduces your health.", 8147);
		c.getPlayerAssistant().sendFrame126("Bring 2-5 waterskins to avoid receiving any damage.", 8148);
		c.getPlayerAssistant().sendFrame126("", 8149);
		c.getPlayerAssistant().sendFrame126("Wearing desert robes will not prevent the damage, but", 8150);
		c.getPlayerAssistant().sendFrame126("will reduce it significantly.", 8151);
		c.getPlayerAssistant().sendFrame126("", 8152);
		c.getPlayerAssistant().sendFrame126("The waterskins however need to be re-filled. Bring a", 8153);
		c.getPlayerAssistant().sendFrame126("knife and cut healthy cacti to re-fill the waterskins.", 8154);
		c.getPlayerAssistant().sendFrame126("@red@Any water vessels will evaporate, such as jug of water.", 8155);
		c.getPlayerAssistant().showInterface(8134);
	}

	/**
	 * Handles fails and success attempts.
	 * 
	 * @param c
	 * @param objectId
	 * @param obX
	 * @param obY
	 */
	public static void checkCactus(Client c, int objectId, int obX, int obY) {
		int fail = Misc.random(2);
		if (fail == 1) {
			c.getActionSender().sendMessage("You failed to cut the cactus.");
			c.getPlayerAssistant().addSkillXP(1, c.playerWoodcutting);
			return;
		}
		c.startAnimation(CUTTING_ANIMATION);
		c.getActionSender().sendMessage("You slash away the cactus.");
		Server.objectHandler.createAnObject(c, DRY_CACTUS, obX, obY, -1);
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
	public static void cutCactus(final Client c, int itemId, final int objectId, final int obX, final int obY) {
		for (int element : CACTUS_CUTTER) {
			if (itemId == element) {
				checkCactus(c, objectId, obX, obY);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
								Server.objectHandler.createAnObject(c, objectId, obX, obY, -1);
								container.stop();
							}
							@Override
							public void stop() {
								
							}
						}, CACTUS_DELAY);
			} else {
				c.getActionSender().sendMessage("You need a knife or a sharp weapon to cut this.");
			}
		}
	}

}
