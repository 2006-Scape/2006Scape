package redone.game.players.antimacro;

import redone.game.players.Client;
import redone.util.Misc;

/**
 * Anti bot
 * @author Andrew
 */

public class AntiBotting {
	
	public static void botCheckInterface(Client client) {
		int x = Misc.random(190);
		int y = Misc.random(190);

		client.getActionSender().setInterfaceOffset(x, y, 6015);
		client.getActionSender().setInterfaceOffset(x, y, 6016);
		client.getActionSender().setInterfaceOffset(x, y, 6017);
		client.getActionSender().setInterfaceOffset(x, y, 6018);
		client.getActionSender().setInterfaceOffset(x, y, 6019);
		client.getActionSender().setInterfaceOffset(x-18, y, 6020);
		client.getPlayerAssistant().sendFrame126("I'm not a bot!", 6020);
		client.getPlayerAssistant().showInterface(6014);
		client.isBotting = true;
		client.getActionSender().sendMessage("@red@Are you a bot?@bla@");
	}

}
