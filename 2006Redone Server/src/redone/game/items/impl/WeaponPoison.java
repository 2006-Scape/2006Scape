package redone.game.items.impl;

import java.util.HashMap;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * Manages weapon poisoning.
 * @author Andew added every single weapon to the enum
 * @author Hybris writing the system
 */
public class WeaponPoison {

	/**
	 * Represents a weapon that can be poisoned. Stores the initial weapon item
	 * id, the type of poison used on the weapon and the new poisoned weapon
	 * that will be obtained.
	 */
	private enum Weapon {
		BRONZE_DAGGER(1205, new int[][] {{ 5940, 5688 }, { 5937, 5670 }, { 187, 1221 }}),
		IRON_DAGGER(1203, new int[][] {{ 5940, 5686 }, { 5937, 5668 }, { 187, 1219 }}),
		STEEL_DAGGER(1207, new int[][] {{ 5940, 5690 }, { 5937, 5672 }, { 187, 1223 }}),
		BLACK_DAGGER(1217, new int[][] {{ 5940, 5700 }, { 5937, 5682 }, { 187, 1233 }}),
		MITHRIL_DAGGER(1209, new int[][] {{ 5940, 5692 }, { 5937, 5674 }, { 187, 1225 }}),
		ADAMANT_DAGGER(1211, new int[][] {{ 5940, 5694 }, { 5937, 5676 }, { 187, 1227 }}),
		RUNE_DAGGER(1213, new int[][] {{ 5940, 5696 }, { 5937, 5678 }, { 187, 1229 }}),
		DRAGON_DAGGER(1215, new int[][] {{ 5940, 5698 }, { 5937, 5680 }, { 187, 1231 }}),
		BRONZE_DART(806, new int[][] {{ 5940, 5635 }, { 5937, 5628 }, { 187, 812 }}),
		IRON_DART(807, new int[][] {{ 5940, 5636 }, { 5937, 5629 }, { 187, 813 }}),
		STEEL_DART(808, new int[][] {{ 5940, 5637 }, { 5937, 5630 }, { 187, 814 }}),
		BLACK_DART(3093, new int[][] {{ 5940, 5638 }, { 5937, 5631 }, { 187, 815 }}),
		MITHRIL_DART(809, new int[][] {{ 5940, 5639 }, { 5937, 5632 }, { 187, 816 }}),
		ADAMANT_DART(810, new int[][] {{ 5940, 5640 }, { 5937, 5633 }, { 187, 817 }}),
		RUNE_DART(811, new int[][] {{ 5940, 5641 }, { 5937, 5634 }, { 187, 818 }}),
		BRONZE_SPEAR(1237, new int[][] {{ 5940, 5718 }, { 5937, 5704 }, { 187, 1251 }}),
		IRON_SPEAR(1239, new int[][] {{ 5940, 5720 }, { 5937, 5706 }, { 187, 1253 }}),
		STEEL_SPEAR(1241, new int[][] {{ 5940, 5722 }, { 5937, 5708 }, { 187, 1255 }}),
		MITHRIL_SPEAR(1243, new int[][] {{ 5940, 5724 }, { 5937, 5710 }, { 187, 1257 }}),
		ADAMANT_SPEAR(1245, new int[][] {{ 5940, 5726 }, { 5937, 5712 }, { 187, 1259 }}),
		RUNE_SPEAR(1247, new int[][] {{ 5940, 5728 }, { 5937, 5714 }, { 187, 1261 }}),
		DRAGON_SPEAR(1249, new int[][] {{ 5940, 5730 }, { 5937, 5716 }, { 187, 1263 }}),
		BRONZE_JAVELIN(825, new int[][] { { 5940, 5648 }, { 5937, 5642 }, { 187, 831 }}),
		IRON_JAVELIN(826, new int[][] { { 5940, 5648 }, { 5937, 5643 }, { 187, 832 }}),
		STEEL_JAVELIN(827, new int[][] {{ 5940, 5648 }, { 5937, 5644 }, { 187, 833 }}),
		MITHRIL_JAVELIN(828, new int[][] {{ 5940, 5648 }, { 5937, 5645 }, { 187, 834 }}),
		ADAMANT_JAVELIN(829, new int[][] {{ 5940, 5648 }, { 5937, 5646 }, { 187, 835 }}),
		RUNE_JAVELIN(830, new int[][] {{ 5940, 5648 }, { 5937, 5647 }, { 187, 836 }}),
		BRONZE_ARROW(882, new int[][] {{ 5940, 5622 }, { 5937, 5616 }, { 187, 883 }}),
		IRON_ARROW(884, new int[][] {{ 5940, 5623 }, { 5937, 5617 }, { 187, 885 }}),
		STEEL_ARROW(886, new int[][] {{ 5940, 5624 }, { 5937, 5618 }, { 187, 887 }}),
		MITHRIL_ARROW(888, new int[][] {{ 5940, 5625 }, { 5937, 5619 }, { 187, 889 }}),
		ADAMANT_ARROW(890, new int[][] {{ 5940, 5626 }, { 5937, 5620 }, { 187, 891 }}),
		RUNE_ARROW(892, new int[][] {{ 5940, 5627 }, { 5937, 5621 }, { 187, 893 }}),
		BRONZE_KNIFE(864, new int[][] {{ 5940, 5661 }, { 5937, 5654 }, { 187, 870 }}),
		IRON_KNIFE(863, new int[][] {{ 5940, 5662 }, { 5937, 5655 }, { 187, 871}}),
		STEEL_KNIFE(865, new int[][] {{ 5940, 5663 }, { 5937, 5656 }, { 187, 872 }}),
		BLACK_KNIFE(869, new int[][] {{ 5940, 5665 }, { 5937, 5658 }, { 187, 873 }}),
		MITHRIL_KNIFE(866, new int[][] {{ 5940, 5664 }, { 5937, 5657 }, { 187, 874 }}),
		ADAMANT_KNIFE(867, new int[][] {{ 5940, 5666 }, { 5937, 5659 }, { 187, 875 }}),
		RUNE_KNIFE(868, new int[][] {{ 5940, 5667 }, { 5937, 5660 }, { 187, 876 }});
		
		/**
		 * Creates the weapon.
		 * 
		 * @param itemId
		 *            The weapon item id.
		 * @param newItemId
		 *            The poisoned weapon item id.
		 */
		private Weapon(int itemId, int[][] newItemId) {
			this.itemId = itemId;
			this.newItemId = newItemId;
		}

		/**
		 * Gets the item id.
		 * 
		 * @return the itemId
		 */
		public int getItemId() {
			return itemId;
		}

		/**
		 * The weapon item id.
		 */
		private final int itemId;

		/**
		 * The poisoned weapon item id.
		 */
		private final int[][] newItemId;

		/**
		 * Represents a map for the weapon item ids.
		 */
		public static HashMap<Integer, Weapon> weapon = new HashMap<Integer, Weapon>();

		/**
		 * @return the newItemId
		 */
		public int[][] getNewItemId() {
			return newItemId;
		}

		/**
		 * Populates a map for the weapons.
		 */
		static {
			for (Weapon w : Weapon.values()) {
				weapon.put(w.getItemId(), w);
			}

		}
	}

	/**
	 * The item id for Vial.
	 */
	private final static int VIAL = 229;

	/**
	 * Starts the weapon poison event for each individual weapon item from the
	 * enumeration <code>Weapon</code>.
	 * 
	 * @param player
	 *            The Player player.
	 * @param itemUse
	 *            The first item use.
	 * @param useWith
	 *            The second item use.
	 */
	public static void execute(final Client player, int itemUse, int useWith) {
		final Weapon weapon = Weapon.weapon.get(useWith);
		if (weapon != null) {
			for (int element[] : weapon.getNewItemId()) {
				if (itemUse == element[0]) {
					player.getActionSender().sendMessage("You make a " + ItemAssistant.getItemName(element[1]) + ".");
					player.getItemAssistant().deleteItem(element[0], player.getItemAssistant().getItemSlot(element[0]), 1);
					player.getItemAssistant().deleteItem(weapon.getItemId(), player.getItemAssistant().getItemSlot(weapon.getItemId()), 1);
					player.getItemAssistant().addItem(VIAL, 1);
					player.getItemAssistant().addItem(element[1], 1);
				}
			}
		}

	}
}
