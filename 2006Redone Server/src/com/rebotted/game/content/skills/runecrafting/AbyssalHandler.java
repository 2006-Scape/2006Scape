package com.rebotted.game.content.skills.runecrafting;

import com.rebotted.game.players.Player;

public class AbyssalHandler {

	public static void handleAbyssalTeleport(Player player, int objectId) {
		switch (objectId) {
		case 7147: // squeeze through gap
			player.getPlayerAssistant().movePlayer(3030, 4842, 0);
			break;
		case 7133: // nature
			player.getPlayerAssistant().startTeleport(2395, 4841, 0, "modern");
			break;
		case 7132: // cosmic
			player.getPlayerAssistant().startTeleport(2144, 4831, 0, "modern");
			break;
		case 7129: // fire
			player.getPlayerAssistant().startTeleport(2585, 4836, 0, "modern");
			break;
		case 7130: // earth
			player.getPlayerAssistant().startTeleport(2658, 4839, 0, "modern");
			break;
		case 7131: // body
			player.getPlayerAssistant().startTeleport(2525, 4830, 0, "modern");
			break;
		case 7140: // mind
			player.getPlayerAssistant().startTeleport(2786, 4834, 0, "modern");
			break;
		case 7139: // air
			player.getPlayerAssistant().startTeleport(2844, 4837, 0, "modern");
			break;
		case 7137: // water
			player.getPlayerAssistant().startTeleport(2722, 4833, 0, "modern");
			break;
		case 7136: // death
			player.getPlayerAssistant().startTeleport(2205, 4834, 0, "modern");
			break;
		case 7135: // law
			player.getPlayerAssistant().startTeleport(2464, 4830, 0, "modern");
			break;
		case 7134: // chaos
			player.getPlayerAssistant().startTeleport(2274, 4842, 0, "modern");
			break;
		case 7138: // soul
		case 7141: // blood
			player.getPacketSender().sendMessage("This altar is currently disabled.");
			break;
		}
	}

}
