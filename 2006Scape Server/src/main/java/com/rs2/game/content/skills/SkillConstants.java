package com.rs2.game.content.skills;
/**
 * SkillConstants.java
 * @author Andrew (Mr Extremez)
 */

public enum SkillConstants {
	
	ATTACK(true), DEFENCE(true), STRENGTH(true), HITPOINTS(true), RANGE(true), 
	PRAYER(true), MAGIC(true), COOKING(true), WOODCUTTING(true), FLETCHING(true), 
	FISHING(true), FIREMAKING(true), CRAFTING(true), SMITHING(true), MINING(true), 
	HERBLORE(true), AGILITY(true), THIEVING(true), SLAYER(true), FARMING(true), RUNECRAFTING(true);
	
	private SkillConstants(boolean skillEnabled) {
		this.skillEnabled = skillEnabled;
	}
	
	private boolean skillEnabled;
	
	public static boolean getEnabled(int id) {
		for (final SkillConstants skillConstants : SkillConstants.values()) {
			if (skillConstants.ordinal() == id && skillConstants.skillEnabled == true) {
				return true;
			}
		}
		return false;
	}
	
	public static String getName(SkillConstants skillConstants) {
		return "The " + skillConstants.name().toLowerCase() + " skill is currently disabled";
	}
	
}
