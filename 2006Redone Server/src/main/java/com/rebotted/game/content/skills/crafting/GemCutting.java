package com.rebotted.game.content.skills.crafting;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

public class GemCutting extends CraftingData {

	public static boolean cutGem(final Player player, final int itemUsed,
			final int usedWith) {
		/*
		 * if (c.isCrafting) { return false; }
		 */
		final int itemId = itemUsed == 1755 ? usedWith : itemUsed;
		for (final cutGemData g : cutGemData.values()) {
			if (itemId == g.getUncut()) {
				if (player.playerLevel[12] < g.getLevel()) {
					player.getPacketSender().sendMessage(
							"You need a crafting level of " + g.getLevel()
									+ " to cut this gem.");
					return false;
				}
				if (!player.getItemAssistant().playerHasItem(itemId)) {
					return false;
				}
				if (!CRAFTING) {
					player.getPacketSender().sendMessage(
							"This skill is currently disabled.");
					return false;
				}
				player.isCrafting = true;
				player.startAnimation(g.getAnimation());
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (Misc.random(2) == 0 && itemUsed > 1624 && itemUsed < 1630 || usedWith > 1624 && usedWith < 1630 && Misc.random(2) == 0) {
							player.getPacketSender().sendMessage("You fail to cut the gem.");
							player.getItemAssistant().addItem(1633, 1);
							player.getItemAssistant().deleteItem(itemId, 1);
							player.getPlayerAssistant().addSkillXP(1, 12);
						}
						if (player.isCrafting) {
							if (player.getItemAssistant().playerHasItem(itemId)) {
								player.getItemAssistant().deleteItem(itemId, 1);
								player.getItemAssistant().addItem(g.getCut(), 1);
								player.getPlayerAssistant().addSkillXP((int) g.getXP(), 12);
								player.getItemAssistant();
								player.getPacketSender().sendMessage(
										"You cut the "
												+ ItemAssistant.getItemName(
														itemId).toLowerCase()
												+ ".");
								player.startAnimation(g.getAnimation());
								player.getPacketSender().sendSound(
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
