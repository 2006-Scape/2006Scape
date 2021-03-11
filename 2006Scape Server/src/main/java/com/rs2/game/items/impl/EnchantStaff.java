package com.rs2.game.items.impl;

import java.util.HashMap;

import com.rs2.game.items.Item;
import com.rs2.game.players.Player;

public class EnchantStaff {

	public static boolean staffButtons(Player player, int button) {
		final staffData staff = staffData.forId(button);
		if (staff != null) {
			if(!player.getInventory().playerHasItem(staff.getBattlestaff())){
				player.getPacketSender().sendMessage("You need a battlestaff to do this!");
				return true;
			}
			if(!player.getItemAssistant().playerHasItem(995, 40000)) {
				player.getPacketSender().sendMessage("You don't have enough coins with you!");
				return true;
			}
			player.getInventory().removeItem(new Item(staff.getBattlestaff(), 1));
			player.getInventory().removeItem(new Item(995, 40000));
			player.getInventory().addItem(new Item(staff.getMysticstaff(), 1));
			player.getPacketSender().sendMessage("Thormac enchants your staff into a mystic staff.");
			player.getPacketSender().closeAllWindows();
			return true;
		}
		return false;
	}
	
	public static enum staffData {// button, battlestaff, mystic staff
		AIR(1734, 1397, 1405), WATER(1735, 1395, 1403), EARTH(1736, 1399, 1407), FIRE(1737, 1393, 1401), LAVA(1738, 3053, 3054), MUD(15348, 6562, 6563);

		private int button;
		private int battlestaff;
		private int mysticstaff;

		public static HashMap<Integer, staffData> craftingStaff = new HashMap<Integer, staffData>();

		public static staffData forId(int id) {
			return craftingStaff.get(id);
		}

		static {
			for (staffData c : staffData.values()) {
				craftingStaff.put(c.getButton(), c);
			}
		}

		private staffData(int button, int battlestaff, int mysticstaff) {
			this.button = button;
			this.battlestaff = battlestaff;
			this.mysticstaff = mysticstaff;
		}

		public int getButton() {
			return button;
		}

		public int getBattlestaff() {
			return battlestaff;
		}

		public int getMysticstaff() {
			return mysticstaff;
		}
	}
	
}
