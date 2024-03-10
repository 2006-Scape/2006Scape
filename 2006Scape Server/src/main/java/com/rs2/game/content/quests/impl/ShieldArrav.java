package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Client;
import com.rs2.game.players.Player;

/**
 * Shield of Arrav
 * @author RedSparr0w
 */

public class ShieldArrav {

	Client client;

	private static String[] lines = new String[]{};

	public ShieldArrav(Client client) {
		this.client = client;
	}

	public static void showInformation(Player player) {
		// Clear all lines
		for (int i = 8144; i < 8196; i++) player.getPacketSender().sendString("", i);
		for (int i = 12174; i < (12174 + 50); i++) {
			player.getPacketSender().sendString( "", i);
		}
		for (int i = 14945; i < (14945 + 100); i++) {
			player.getPacketSender().sendString("", i);
		}
		// Set the title
		player.getPacketSender().sendString("Shield of Arrav", 8144);
		// Add content
		if (player.shieldArrav == 0) {
			lines = new String[]{
					"I can start this quest by speaking to @red@Reldo @bla@in @red@Varrock's",
					"@red@Palace Library@bla@, or by speaking to @red@Charlie the Tramp @bla@near",
					"the @red@Blue Moon Inn @bla@in @red@Varrock.",
					"I will need a friend to help me and some combat experience",
					"may be an advantage.",
			};
		} else if (player.shieldArrav == 1) {
			lines =  new String[]{
					"I need to find @red@'The Shield of Arrav' @bla@book",
					"in @red@Varrock's Palace Library",
			};
		} else if (player.shieldArrav == 2) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"I should speak to @red@Reldo",
			};
		} else if (player.shieldArrav == 3) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"Lets go speak with @red@Baraek",
			};
		} else if (player.shieldArrav == 4) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"He told me where I can find the Phoenix Gang hideout,",
					"He said it was located in the South Eastern side of Varrock,",
					"I should check it out.",
			};
		} else if (player.shieldArrav == 5) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"@str@I found the hideout",
					"I need to goto the @red@Blue Moon Inn",
					"and obtain the intelligence report.",
			};
		} else if (player.shieldArrav == 6) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"@str@I found the hideout",
					"@str@I obtained the intelligence report",
					"I need to find the Shield",
					"and take it to @red@Curator Haig Halen",
					"he should be at @red@Varrock Museum"
			};
		} else if (player.shieldArrav == 7) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"@str@I found the hideout",
					"@str@I obtained the intelligence report",
					"@str@I turned in the shield",
					"I need to take this certificate",
					"to @red@King Roald @bla@for my reward",
			};
		} else if (player.shieldArrav == 8) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"@str@I found the hideout",
					"@str@I obtained the intelligence report",
					"@str@I turned in the shield",
					"@str@I got a certificate and",
					"@str@turned it in for my reward",
					"",
					"@red@QUEST COMPLETE",
					"",
					"REWARDS:",
					"1,200 coins",
					"1 Quest Point",
			};
		}
		// Send the lines to the client
		int lineNumber = 8147;
		for (String line : lines) player.getPacketSender().sendString(line, lineNumber++);
		player.getPacketSender().showInterface(8134);
	}
}
