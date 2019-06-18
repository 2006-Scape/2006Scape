package redone.game.content.skills.runecrafting;

import redone.game.players.Client;

public class AbyssalHandler {

	public static void handleAbyssalTeleport(Client c, int objectId) {
		switch (objectId) {
		case 7147:// added
			c.getPlayerAssistant().movePlayer(3030, 4842, 0);
			break;
		case 7133:// added nature
			c.getPlayerAssistant().startTeleport(2395, 4841, 0, "modern");
			break;
		case 7132: // cosmic
			c.getPlayerAssistant().startTeleport(2144, 4831, 0, "modern");
			break;
		case 7129: // fire
			c.getPlayerAssistant().startTeleport(2585, 4836, 0, "modern");
			break;
		case 7130: // earth
			c.getPlayerAssistant().startTeleport(2658, 4839, 0, "modern");
			break;
		case 7131: // body
			c.getPlayerAssistant().startTeleport(2525, 4830, 0, "modern");
			break;
		case 7140: // mind
			c.getPlayerAssistant().startTeleport(2786, 4834, 0, "modern");
			break;
		case 7139: // air
			c.getPlayerAssistant().startTeleport(2844, 4837, 0, "modern");
			break;
		case 7138: // soul
			c.getActionSender().sendMessage("This altar is disabled atm.");
			break;
		case 7141: // blood
			c.getActionSender().sendMessage("This altar is disabled atm.");
			break;
		case 7137: // water
			c.getPlayerAssistant().startTeleport(2722, 4833, 0, "modern");
			break;
		case 7136: // death
			c.getPlayerAssistant().startTeleport(2205, 4834, 0, "modern");
			break;
		case 7135:
			c.getPlayerAssistant().startTeleport(2464, 4830, 0, "modern");
			break;
		case 7134: // chaos
			c.getPlayerAssistant().startTeleport(2274, 4842, 0, "modern");
			break;
		default:
			c.getActionSender().sendMessage(
					"If you see this message, please PM an Administrator.");
			break;
		}
		c.getActionSender().sendMessage(
				"As you click the object, you teleport to a mystical place...");
	}

}
