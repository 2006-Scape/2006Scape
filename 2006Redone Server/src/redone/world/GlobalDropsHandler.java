package redone.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

/**
 * Handles global drops which respawn after set amount of time when taken
 * 
 * @author Stuart <RogueX>
 */
public class GlobalDropsHandler {

	/**
	 * time in seconds it takes for the item to respawn
	 */
	private static final int TIME_TO_RESPAWN = 30;

	/**
	 * holds all the objects
	 */
	private static List<GlobalDrop> globalDrops = new ArrayList<GlobalDrop>();

	/**
	 * loads the items
	 */
	public static void initialize() {
		String Data;
		BufferedReader Checker;
		try {
			Checker = new BufferedReader(new FileReader(
					"./Data/cfg/globaldrops.txt"));
			while ((Data = Checker.readLine()) != null) {
				if (Data.startsWith("#")) {
					continue;
				}
				String[] args = Data.split(":");
				globalDrops.add(new GlobalDrop(Integer.parseInt(args[0]),
						Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3])));
			}
			Checker.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Misc.println("Loaded " + globalDrops.size() + " global drops.");

	for (Player player : PlayerHandler.players) {
		final Client client = (Client) player;
		if (client != null) {
		   CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				for (GlobalDrop drop : globalDrops) {
					if (drop.isTaken()) {
						if (System.currentTimeMillis() - drop.getTakenAt() >= TIME_TO_RESPAWN * 1000) {
							drop.setTaken(false);
								if (client.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
									client.getActionSender().createGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount(), 0);
								}
							}
						}
					}
				}
	            @Override
				public void stop() {
					// TODO Auto-generated method stub
				}
				}, 1);
			}
		}
	}

	/**
	 * See if a drop exists at the given place
	 * 
	 * @param a
	 *            item id
	 * @param b
	 *            x cord
	 * @param c
	 *            y cord
	 * @return return the statement
	 */
	private static GlobalDrop itemExists(int a, int b, int c) {
		for (GlobalDrop drop : globalDrops) {
			if (drop.getId() == a && drop.getX() == b && drop.getY() == c) {
				return drop;
			}
		}
		return null;
	}

	/**
	 * Pick up an item at the given location
	 * 
	 * @param client
	 *            the client
	 * @param a
	 *            item id
	 * @param b
	 *            cord x
	 * @param c
	 *            cord y
	 */
	public static void pickup(Client client, int a, int b, int c) {
		GlobalDrop drop = itemExists(a, b, c);
		if (drop == null) {
			return;
		}
		if (drop.isTaken()) {
			return;
		}
		drop.setTakenAt(System.currentTimeMillis());
		drop.setTaken(true);
		if (client.getItemAssistant().freeSlots() > 0) {
			client.getItemAssistant().addItem(drop.getId(), drop.getAmount());
		}
		// TODO use the region manager for this...
		for (Player player : PlayerHandler.players) {
			Client cl = (Client) player;
			if (cl != null) {
				if (cl.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
					// cl.getItems().removeGroundItem(drop.getId(), drop.getX(),
					// drop.getY(), drop.getAmount());
					cl.getActionSender().removeGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount());
				}
			}
		}
	}

	/**
	 * Loads all the items when a player changes region
	 * 
	 * @param client
	 *            the client
	 */
	public static void load(Client client) {
		for (GlobalDrop drop : globalDrops) {
			if (!drop.isTaken()) {
				if (client.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
					client.getActionSender().createGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount(), 0);
				}
			}
		}
	}

	/**
	 * Holds each drops data
	 * 
	 * @author Stuart
	 */
	static class GlobalDrop {

		/**
		 * cord x
		 */
		int x;
		/**
		 * cord y
		 */
		int y;
		/**
		 * item id
		 */
		int id;
		/**
		 * item amount
		 */
		int amount;
		/**
		 * has the item been taken
		 */
		boolean taken = false;
		/**
		 * Time it was taken at
		 */
		long takenAt;

		/**
		 * Sets the drop arguments
		 * 
		 * @param a
		 *            item id
		 * @param b
		 *            item amount
		 * @param c
		 *            cord x
		 * @param d
		 *            cord y
		 */
		public GlobalDrop(int a, int b, int c, int d) {
			id = a;
			amount = b;
			x = c;
			y = d;
		}

		/**
		 * get cord x
		 * 
		 * @return return the statement
		 */
		public int getX() {
			return x;
		}

		/**
		 * get cord x
		 * 
		 * @return return the statement
		 */
		public int getY() {
			return y;
		}

		/**
		 * get the item id
		 * 
		 * @return return the statement
		 */
		public int getId() {
			return id;
		}

		/**
		 * get the item amount
		 * 
		 * @return return the statement
		 */
		public int getAmount() {
			return amount;
		}

		/**
		 * has the drop already been taken?
		 * 
		 * @return return the statement
		 */
		public boolean isTaken() {
			return taken;
		}

		/**
		 * set if or not the drop has been taken
		 * 
		 * @param a
		 *            true yes false no
		 */
		public void setTaken(boolean a) {
			taken = a;
		}

		/**
		 * set the time it was picked up
		 * 
		 * @param a
		 *            the a
		 */
		public void setTakenAt(long a) {
			takenAt = a;
		}

		/**
		 * get the time it was taken at
		 * 
		 * @return return the statement
		 */
		public long getTakenAt() {
			return takenAt;
		}

	}

}
