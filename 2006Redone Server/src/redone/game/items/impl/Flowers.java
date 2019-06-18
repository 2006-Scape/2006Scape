package redone.game.items.impl;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.util.Misc;
import redone.world.clip.Region;

/**
 * @author Faris
 */
public class Flowers {

	/**
	 * Constants & boolean checker
	 */
	private final int FLOWER_IDS[] = { 2980, 2981, 2982, 2983, 2984, 2985,
			2986, 2987, 2988 };

	/**
	 * Checks weather user is currently interacting with flower
	 */
	public boolean clientFlowering = false;

	/**
	 * Stores temporary variables for each new instance of flower planted
	 */
	public static int lastObject;

	/**
	 * algorithm decides which flower to give.
	 * 
	 * @param flower
	 * @return
	 */
	private static int flowerDecoder(final int flower) {
		int modifier = flower - 2980;
		return modifier + 2460;
	}

	/**
	 * Constructor creates the place flower event
	 * 
	 * @param c
	 */
	public Flowers(final Client c) {
		if (c.checkBusy()) {
			return;
		}
		c.setBusy(true);
		executeAction(c);
		   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				c.setBusy(false);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 1);
	}

	/**
	 * Main block, Spawns the object & starts animation. Moves player to the
	 * side Clipped
	 * 
	 * @param c
	 */
	private void executeAction(final Client c) {
		final int newFlower = getRandom();
		final int[] coords = new int[2];
		coords[0] = c.absX;
		coords[1] = c.absY;
		updateConstants(newFlower, c);
		Server.objectHandler.createAnObject(c, newFlower, coords[0], coords[1],
				1);
		deleteSeeds(c);
		sendOptions(c);
		clientFlowering = true;
		moveOneStep(c);
		c.turnPlayerTo(coords[0], coords[1]);
		   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				Server.objectHandler.createAnObject(c, -1, coords[0], coords[1], 1);
				c.getActionSender().sendMessage(	"Your flower is no longer flourishing.");
				container.stop();
			}

				@Override
				public void stop() {
					
				}

		}, 10);
	}

	/**
	 * Handles movement direction.
	 * 
	 * @param c
	 */
	private static void moveOneStep(Client c) {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPlayerAssistant().walkTo(-1, 0);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1,
				0)) {
			c.getPlayerAssistant().walkTo(1, 0);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0,
				-1)) {
			c.getPlayerAssistant().walkTo(0, -1);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0,
				1)) {
			c.getPlayerAssistant().walkTo(0, 1);
		}
	}

	/**
	 * Selects a random flower ID
	 * 
	 * @return
	 */
	private int getRandom() {
		return FLOWER_IDS[Misc.random(FLOWER_IDS.length - 1)];
	}

	/**
	 * Sends the client an option to handle flowers
	 * 
	 * @param c
	 */
	private void sendOptions(Client c) {
		c.getDialogueHandler().sendOption2("Leave Flowers", "Harvest Flowers");
	}

	/**
	 * Removes the seeds from invent
	 * 
	 * @param c
	 */
	private static void deleteSeeds(Client c) {
		c.getItemAssistant().deleteItem2(299, 1);
	}

	private void updateConstants(int objectType, Client c) {
		lastObject = objectType;
	}

	/**
	 * Method harvests flower from ground
	 * 
	 * @param c
	 * @param object
	 * @param oX
	 * @param oY
	 */
	public static void harvestFlower(Client c, int object) {
		c.getItemAssistant().addItem(flowerDecoder(object), 1);
		c.getActionSender().sendMessage("You receive a random flower.");
		c.startAnimation(827);
		// c.getPA().checkObjectSpawn(c,-1, c.getX()+1, c.getY(), 1, 10);
		// c.getPA().object(c,-1, c.getX()+1, c.getY(), 1, 10);
		Server.objectHandler.createAnObject(c, -1, c.getX() + 1, c.getY(), 1);
		c.turnPlayerTo(c.getX() + 1, c.getY());
	}

	/**
	 * Sends the option action
	 * 
	 * @param option
	 */
	public void handleOptions(int option, Client c) {
		if (option == 0) {
			return;
		} else {
			harvestFlower(c, lastObject);
		}
		clientFlowering = false;
	}
}
