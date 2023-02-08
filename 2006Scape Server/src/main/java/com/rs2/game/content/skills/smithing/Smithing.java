package com.rs2.game.content.skills.smithing;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

public class Smithing {

	private int addItem, xp, removeItem, removeAmount, makeTimes;
	
	private boolean hasItem(Player player, int type, String string) {
		return (DeprecatedItems.getItemName(type).contains(string));
	}

	public void readInput(Player player, int levelReq, int type, int amountToMake) {
		if (hasItem(player, type, "Bronze")) {
			 removeItem = 2349;
		} else if (hasItem(player, type, "Iron")) {
			 removeItem = 2351;
		} else if (hasItem(player, type, "Steel")) {
			 removeItem = 2353;
		} else if (hasItem(player, type, "Mith")) {
			 removeItem = 2359;
		} else if (hasItem(player, type, "Adam") || hasItem(player, type, "Addy")) {
			 removeItem = 2361;
		} else if (hasItem(player, type, "Rune") || hasItem(player, type, "Runite")) {
			 removeItem = 2363;
		}
		checkBar(player, levelReq, amountToMake, type);
	}
	 
	private void checkBar(Player player, int level, int amountToMake, int type) {
    	SmithingData item = SmithingData.forId(type);
		if (item != null) {
			if (player.playerLevel[Constants.SMITHING] >= item.getLvl()) {
				if (type == item.getId()) {
					addItem = item.getId();
					removeAmount = item.getAmount();
					makeTimes = amountToMake;
					xp = item.getXp();
			    	smithItem(player, addItem, removeItem, removeAmount, makeTimes, xp);
				}
			} else {
				 player.getPacketSender().sendMessage("You don't have a high enough level to make this item.");
			}
		}
    }

    public static void smithItem(Player player, int addItem, int removeItem, int removeItem2, int timesToMake, int XP) {
		player.doAmount = timesToMake;
		player.getPacketSender().closeAllWindows();
		String name = DeprecatedItems.getItemName(addItem);
		if (player.getItemAssistant().playerHasItem(removeItem, removeItem2)) {
			if (!player.isSmithing) {
				player.isSmithing = true;
				player.startAnimation(898);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.doAmount <= 0 || !player.getItemAssistant().playerHasItem(removeItem, removeItem2) 
								|| !player.isSmithing || player.isBanking 
								|| player.isSmelting) {
							container.stop();
						} else {
							player.startAnimation(898);
							player.getPacketSender().sendSound(468, 100, 0);
							if (name.contains("ball")) {
								player.getPacketSender().sendMessage("You make some " + name.toLowerCase() + "s.");
							} else if (name.charAt(name.length() -1) == 's') {
								player.getPacketSender().sendMessage("You make some " + name.toLowerCase() + ".");
							} else {
								if (name.charAt(1) == 'a' || name.charAt(1) == 'e' || name.toLowerCase().charAt(1) == 'i' || name.charAt(1) == 'o' || name.charAt(1) == 'u') {
									player.getPacketSender().sendMessage("You make an " + name.toLowerCase() + ".");
								} else {
									player.getPacketSender().sendMessage("You make a " + name.toLowerCase() + ".");
								}
							}
							player.getItemAssistant().deleteItem(removeItem, removeItem2);
							if (name.contains("bolt")) {
								player.getItemAssistant().addItem(addItem, 10);
							} else if (name.contains("dart tip")) {
								player.getItemAssistant().addItem(addItem, 10);
							} else if (name.contains("arrow") || name.contains("nail") || (name.contains("tip") && !name.contains("dart tip"))) {
								player.getItemAssistant().addItem(addItem, 15);
							} else if (name.contains("knife")) {
								player.getItemAssistant().addItem(addItem, 5);
							} else if (name.contains("ball")) {
								player.getItemAssistant().addItem(addItem, 4);
							} else {
								player.getItemAssistant().addItem(addItem, 1);
							}
							player.getPlayerAssistant().addSkillXP(XP, Constants.SMITHING);
							player.doAmount--;
						}
					}

					@Override
					public void stop() {
						player.isSmithing = false;
					}
				}, addItem == 2 ? 10 : 3);
			}
		} else {
			player.getPacketSender().sendMessage("You don't have enough bars to make this item!");
			player.isSmithing = false;
		}
	}
    
}