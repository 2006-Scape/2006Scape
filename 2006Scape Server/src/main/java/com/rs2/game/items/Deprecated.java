package com.rs2.game.items;

import com.rs2.GameEngine;

/**
 * Methods which are set to be phased out but exist to maintain compatibility with the existing source code.
 * 
 * @author Advocatus
 *
 */
public class Deprecated {
				
	/**
	 * Gets the item id for a specific name. This is inefficient and is used for potion mixing. That code needs to be reworked.
	 * Tagging as {@link java.lang.Deprecated} until the rest of the code can be untangled. It is worth noting that it will only return
	 * the first matching item id which can cause unintended behavior and should be avoided.
	 * 
	 * @param name The item name
	 * @return The item id or -1.
	 */
	@java.lang.Deprecated
	public static int getItemId(String name) {
		for (int i = 0; i < org.apollo.cache.def.ItemDefinition.count(); i++) {
			org.apollo.cache.def.ItemDefinition def = org.apollo.cache.def.ItemDefinition.lookup(i);
			if (def != null && def.getName() != null) {
				if (GameEngine.itemHandler.itemList[i].itemName.equalsIgnoreCase(name)) {
					return GameEngine.itemHandler.itemList[i].itemId;
				}
			}
		}
		return -1;
	}


	/**
	 * Temporary method which wraps around the ItemDefinition.lookup(id).getName(); This is utilized due to source specific behaviors.
	 * This should be phased out with discretion.
	 * 
	 * @param id The id.
	 * @return The items name or "Unarmed"
	 */
	@java.lang.Deprecated
	public static String getItemName(int id) {
		if(org.apollo.cache.def.ItemDefinition.lookup(id) == null || org.apollo.cache.def.ItemDefinition.lookup(id).getName() == null)
			return "Unarmed";
		return org.apollo.cache.def.ItemDefinition.lookup(id).getName();
	}
}
