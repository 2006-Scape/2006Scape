package  com.rs2.game.content.randomevents;

import com.rs2.Constants;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * Swarm Event
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Swarm {

	private static int checkStats(Player c, boolean bot) {
		if (bot) {
			return c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) * 3;
		} else {
			return c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) * 2;
		}
	}

	/**
	 * Spawns the swarm.
	 * 
	 * @param c
	 */
	public static void spawnSwarm(Player c) {
		NpcHandler.spawnNpc(c, // param
				411, // npctype
				c.absX + Misc.random(1), // posX
				c.absY + Misc.random(1), // posY
				c.heightLevel, // height
				0, // walkingtype
				1, // HP
				2, // maxhit
				checkStats(c, false),
				3 * c.combatLevel, // defence
				true, // attackplayer
				false); // headicon
		c.autoRet = 0;
	}

	/**
	 * Spawns the swarm. If suspect a botter ... this one is stronger, botter
	 * has to react faster to survive.
	 * 
	 * @param c
	 */
	public static void checkBot(Client client) {
		NpcHandler.spawnNpc(client, // param
				411, // npctype
				client.absX + Misc.random(1), // posX
				client.absY + Misc.random(1), // posY
				client.heightLevel, // height
				0, // walkingtype
				1, // HP
				6, // maxhit
				checkStats(client, true), // attack
				4 * client.combatLevel, // defence
				true, // attackplayer
				false); // headicon
		client.autoRet = 0;
	}
}
