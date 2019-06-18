package redone.game.items.impl;

import redone.Server;
import redone.game.items.ItemDefinitions;
import redone.game.items.ItemList;
import redone.game.players.Client;

/**
 * @author somedude, credits to Galkon for item weights
 */
public class Weight extends ItemDefinitions {

	/**
	 * Calculates the weight when doing actions
	 * 
	 * @param c
	 * @param item
	 * @param action
	 *            - deleteitem, additem, equip, unequip.
	 */
	public static void calcWeight(Client c, int item, String action) {
		if (action.equalsIgnoreCase("deleteitem")) {
			if (getWeight(item) > 99.20) {
				c.weight -= getWeight(item) / 100;
				if (c.weight < 0) {
					c.weight = 0.0;
				}
				c.getActionSender().writeWeight((int) c.weight);
				return;
			}
			c.weight -= getWeight(item) / 10;
			if (c.weight < 0) {
				c.weight = 0.0;
			}
			c.getActionSender().writeWeight((int) c.weight);
		} else if (action.equalsIgnoreCase("additem")) {
			if (getWeight(item) > 99.20) {
				c.weight += getWeight(item) / 100;
				c.getActionSender().writeWeight((int) c.weight);
				return;
			}
			c.weight += getWeight(item) / 10;
			c.getActionSender().writeWeight((int) c.weight);
		}
	}

	/**
	 * Updates the weight for inventory and equipment.
	 * 
	 * @param c
	 */
	public static void updateWeight(Client c) {
		if (c != null) {
			c.getActionSender().writeWeight((int) c.weight);
			for (int playerItem : c.playerItems) {
				if (playerItem > -1) {// inventory
					for (ItemList i1 : Server.itemHandler.ItemList) {
						if (i1 != null) {
							if (i1.itemId == playerItem) {
								calcWeight(c, playerItem, "addItem");
							}
						}
					}
				}
			} // equipment
			for (int element : c.playerEquipment) {
				if (element > -1) {// equipment
					for (ItemList i1 : Server.itemHandler.ItemList) {
						if (i1 != null) {
							if (i1.itemId == element) {
								calcWeight(c, element, "addItem");
							}
						}
					}
				}
			}
		}
		c.getActionSender().writeWeight((int) c.weight);
	}
}
