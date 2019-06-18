package redone.game.content.skills;

import redone.game.players.Client;

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
		player.getPlayerAssistant().sendItemOnInterface(8902, 250, i1);
		player.getPlayerAssistant().sendItemOnInterface(8903, 150, i2);
		player.getPlayerAssistant().sendItemOnInterface(8904, 200, i3);
		player.getPlayerAssistant().sendItemOnInterface(8905, 250, i4);
		player.getPlayerAssistant().sendFrame126(s1, 8909);
		player.getPlayerAssistant().sendFrame126(s2, 8913);
		player.getPlayerAssistant().sendFrame126(s3, 8917);
		player.getPlayerAssistant().sendFrame126(s4, 8921);
		player.getPlayerAssistant().sendChatInterface(8899);
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
