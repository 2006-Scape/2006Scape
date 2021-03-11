package com.rs2.game.content.skills;

import com.rs2.game.players.Client;

/**
 * Created by IntelliJ IDEA. User: vayken Date: 21/12/11 Time: 13:55 To change
 * this template use File | Settings | File Templates.
 */
public class Menus {

	public static void sendSkillMenu(Client player, String type) {
		if (type == "silverCrafting") {
			display4Item(player, 1716, 1724, 2961, 5525, "Unblessed symbol", "Unholy symbol", "Silver sickle", "Tiara");
		}
		player.setStatedInterface(type);
	}
	
	public static void display4Item(Client player, int i1, int i2, int i3, int i4, String s1, String s2, String s3, String s4) {
		player.getPacketSender().sendItemOnInterface(8902, 250, i1);
		player.getPacketSender().sendItemOnInterface(8903, 150, i2);
		player.getPacketSender().sendItemOnInterface(8904, 200, i3);
		player.getPacketSender().sendItemOnInterface(8905, 250, i4);
		player.getPacketSender().sendString(s1, 8909);
		player.getPacketSender().sendString(s2, 8913);
		player.getPacketSender().sendString(s3, 8917);
		player.getPacketSender().sendString(s4, 8921);
		player.getPacketSender().sendChatInterface(8899);
	}

	public static String determineAorAn(String nextWord) {
		String[] c = {"a", "e", "i", "o", "u", "y"};
		for (String firstLetter : c) {
			if (nextWord.toLowerCase().startsWith(firstLetter)) {
				return "an";
			}
		}
		return "a";
	}
}
