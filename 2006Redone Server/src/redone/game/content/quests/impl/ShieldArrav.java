package redone.game.content.quests.impl;

import redone.game.players.Client;

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

	public static void showInformation(Client client) {
		// Clear all lines
		for (int i = 8144; i < 8195; i++) client.getPlayerAssistant().sendFrame126("", i);
		// Set the title
		client.getPlayerAssistant().sendFrame126("Shield of Arrav", 8144);
		// Add content
		if (client.shieldArrav == 0) {
			lines = new String[]{
					"I can start this quest by speaking to @red@Reldo @bla@in @red@Varrock's",
					"@red@Palace Library@bla@, or by speaking to @red@Charlie the Tramp @bla@near",
					"the @red@Blue Moon Inn @bla@in @red@Varrock.",
					"I will need a friend to help me and some combat experience",
					"may be an advantage.",
			};
		} else if (client.shieldArrav == 1) {
			lines =  new String[]{
					"I need to find @red@'The Shield of Arrav' @bla@book",
					"in @red@Varrock's Palace Library",
			};
		} else if (client.shieldArrav == 2) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"I should speak to @red@Reldo",
			};
		} else if (client.shieldArrav == 3) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"Lets go speak with @red@Baraek",
			};
		} else if (client.shieldArrav == 4) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"He told me where I can find the Phoenix Gang hideout,",
					"He said it was located in the South Eastern side of Varrock,",
					"I should check it out.",
			};
		} else if (client.shieldArrav == 5) {
			lines =  new String[]{
					"@str@I found 'The Shield of Arrav' book",
					"@str@I've spoken with Raldo",
					"@str@I've spoken with Baraek",
					"@str@I found the hideout",
					"I need to goto the @red@Blue Moon Inn",
					"and obtain the intelligence report.",
			};
		} else if (client.shieldArrav == 6) {
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
		} else if (client.shieldArrav == 7) {
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
		} else if (client.shieldArrav == 8) {
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
		for (String line : lines) client.getPlayerAssistant().sendFrame126(line, lineNumber++);
		client.getPlayerAssistant().showInterface(8134);
	}
}
