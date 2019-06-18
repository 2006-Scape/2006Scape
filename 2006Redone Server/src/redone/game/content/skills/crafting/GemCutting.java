package redone.game.content.skills.crafting;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.util.Misc;

public class GemCutting extends CraftingData {

	public static boolean cutGem(final Client c, final int itemUsed,
			final int usedWith) {
		/*
		 * if (c.isCrafting == true) { return false; }
		 */
		final int itemId = itemUsed == 1755 ? usedWith : itemUsed;
		for (final cutGemData g : cutGemData.values()) {
			if (itemId == g.getUncut()) {
				if (c.playerLevel[12] < g.getLevel()) {
					c.getActionSender().sendMessage(
							"You need a crafting level of " + g.getLevel()
									+ " to cut this gem.");
					return false;
				}
				if (!c.getItemAssistant().playerHasItem(itemId)) {
					return false;
				}
				if (!CRAFTING) {
					c.getActionSender().sendMessage(
							"This skill is currently disabled.");
					return false;
				}
				c.isCrafting = true;
				c.startAnimation(g.getAnimation());
				   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (Misc.random(2) == 0 && itemUsed > 1624 && itemUsed < 1630 || usedWith > 1624 && usedWith < 1630 && Misc.random(2) == 0) {
							c.getActionSender().sendMessage("You fail to cut the gem.");
							c.getItemAssistant().addItem(1633, 1);
							c.getItemAssistant().deleteItem2(itemId, 1);
							c.getPlayerAssistant().addSkillXP(1, 12);
						}
						if (c.isCrafting == true) {
							if (c.getItemAssistant().playerHasItem(itemId)) {
								c.getItemAssistant().deleteItem(itemId, 1);
								c.getItemAssistant().addItem(g.getCut(), 1);
								c.getPlayerAssistant().addSkillXP((int) g.getXP(), 12);
								c.getItemAssistant();
								c.getActionSender().sendMessage(
										"You cut the "
												+ ItemAssistant.getItemName(
														itemId).toLowerCase()
												+ ".");
								c.startAnimation(g.getAnimation());
								c.getActionSender().sendSound(
										SoundList.CUT_GEM, 100, 0);
							} else {
								container.stop();
							}
						} else {
							container.stop();
						}
					}
						@Override
						public void stop() {
							
						}
				}, 4);
			}
		}
		return false;
	}
}
