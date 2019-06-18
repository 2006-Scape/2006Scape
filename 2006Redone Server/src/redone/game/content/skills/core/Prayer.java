package redone.game.content.skills.core;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.content.randomevents.Shade;
import redone.game.content.randomevents.Zombie;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * Class Prayer Handles Prayer
 * @author 2012 23:56 29/12/2010
 */

public class Prayer {

	private static int[][] data = { { 526, 5 }, // NPC BONES
			{ 528, 5 }, // BURNT BONES
			{ 530, 5 }, // BAT BONES
			{ 2859, 5 }, // WOLF BONES
			{ 3179, 5 }, // MONKEY BONES
			{ 3180, 5 }, // MONKEY BONES
			{ 3181, 5 }, // MONKEY BONES
			{ 3182, 5 }, // MONKEY BONES
			{ 3183, 5 }, // MONKEY BONES
			{ 3185, 5 }, // MONKEY BONES
			{ 3186, 5 }, // MONKEY BONES
			{ 3187, 5 }, // MONKEY BONES
			{ 532, 15 }, // BIG BONES
			{ 534, 30 }, // BABY DRAGON BONES
			{ 536, 72 }, // DRAGON BONES
			{ 2530, 5 }, // PLAYER BONES
			{ 3123, 25 }, // SHAIKAHAN BONES
			{ 3125, 23 }, // JOGRE BONES
			{ 3127, 25 }, // BURNT JOGRE BONES
			{ 4812, 82 }, // ZOGRE BONES
			{ 4830, 84 }, // FAYGR BONES
			{ 4832, 96 }, // RAURG BONES
			{ 4834, 140 }, // OURG BONES
			{ 6729, 125 }, // DAGANNOTH BONES
			{ 6812, 50 }, // WYVERN BONES
	};

	public static boolean playerBones(Client c, int item) {
		for (int[] element : data) {
			if (item == element[0]) {
				return true;
			}
		}
		return false;
	}

	private static void handleBones(final Client c, int i, int slot) {
		if (Misc.random(300) == 4 && c.shadeSpawned == false) {
			Zombie.spawnZombie(c);
		} else if (Misc.random(300) == 2 && c.zombieSpawned == false) {
			Shade.spawnShade(c);
		}
		for (final int[] element : data) {
			if (i == element[0]) {
				if (!SkillHandler.PRAYER) {
					c.getActionSender().sendMessage("This skill is currently disabled.");
				}
				if (System.currentTimeMillis() - c.buryDelay > 800) {
					c.getItemAssistant().deleteItem(element[0], slot, 1);
					c.getPlayerAssistant().addSkillXP(element[1], 5);
					c.buryDelay = System.currentTimeMillis();
					c.startAnimation(827);
					c.getActionSender().sendSound(SoundList.BONE_BURY, 100, 0);
					c.getActionSender().sendMessage("You dig a hole in the ground...");
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							c.getActionSender().sendMessage("You bury the " + ItemAssistant.getItemName(element[0]).toLowerCase() + ".");
							container.stop();
						}
						@Override
						public void stop() {
							
						}
					}, 1);
				}
			}
		}
	}

	public static void buryBones(Client c, int i, int slot) {
		handleBones(c, i, slot);
	}
}
