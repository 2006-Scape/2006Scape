package com.rebotted.game.content.skills.slayer;

import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Player;

/**
 * Slayer Item Requirements
 * @author Andrew (Mr Extremez)
 */

public class SlayerRequirements {

	private static final int NOSE_PEG = 4168, MIRROR_SHIELD = 4156,
			EAR_MUFFS = 4166, ROCK_HAMMER = 4162, FACEMASK = 4164,
			LEAF_BLADED_SPEAR = 4158, BROAD_ARROWS = 4172, BAG_OF_SALT = 4161;

	public static boolean itemNeededSlayer(Player c, int i) {
		int npcType = NpcHandler.npcs[i].npcType;
		switch (NpcHandler.npcs[i].npcType) {
		case 1622:
		case 1623: //rock slug
		if (!c.getItemAssistant().playerHasItem(BAG_OF_SALT, 1)) {
			c.getPacketSender().sendMessage("You need a Bag of Salt to attack Rock Slugs.");
			c.getCombatAssistant().resetPlayerAttack();
			return false;	
		}
		break;
		case 1632: // turoth
			if (c.playerEquipment[c.playerWeapon] != LEAF_BLADED_SPEAR && c.playerEquipment[c.playerArrows] != BROAD_ARROWS) {
				c.getPacketSender().sendMessage("You need a Leaf Bladed Spear or Broad Arrows to attack Turoths.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			} else if (c.playerEquipment[c.playerArrows] != BROAD_ARROWS && c.playerEquipment[c.playerWeapon] != LEAF_BLADED_SPEAR) {
				c.getPacketSender().sendMessage("You need a Leaf Bladed Spear or Broad Arrows to attack Turoths.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		case 1612:// banshee
			if (c.playerEquipment[c.playerHat] != EAR_MUFFS) {
				c.getPacketSender().sendMessage("You need some Ear Muffs to attack Banshees.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		case 1620: //basilisk
		case 1616:// cockatrice
			if (c.playerEquipment[c.playerShield] != MIRROR_SHIELD) {
				c.getPacketSender().sendMessage("You need a Mirror Shield to attack a " + NpcHandler.getNpcListName(npcType).toLowerCase() + ".");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		case 1624:// dust devil
			if (c.playerEquipment[c.playerHat] != FACEMASK) {
				c.getPacketSender().sendMessage("You need a Face Mask to attack Dust devils.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		case 1604:
		case 1605:
		case 1606:
		case 1607:// spectre
			if (c.playerEquipment[c.playerHat] != NOSE_PEG) {
				c.getPacketSender().sendMessage("You need a Nose Peg to attack Aberrant specter.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		case 1611:// garg
			if (!c.getItemAssistant().playerHasItem(ROCK_HAMMER)) {
				c.getPacketSender().sendMessage("You need a Rock Hammer to attack gargoyles.");
				c.getCombatAssistant().resetPlayerAttack();
				return false;
			}
			break;
		}
		return true;
	}

}
