package com.rebotted.game.content.skills.smithing;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Player;

public class Smithing {

	private int addItem, xp, removeItem, removeAmount, makeTimes;

	public void readInput(Player player, int levelReq, int type, int amountToMake) {
		if (ItemAssistant.getItemName(type).contains("Bronze")) {
			 removeItem = 2349;
		} else if (ItemAssistant.getItemName(type).contains("Iron")) {
			 removeItem = 2351;
		} else if (ItemAssistant.getItemName(type).contains("Steel")) {
			 removeItem = 2353;
		} else if (ItemAssistant.getItemName(type).contains("Mith")) {
			 removeItem = 2359;
		} else if (ItemAssistant.getItemName(type).contains("Adam") || 	ItemAssistant.getItemName(type).contains("Addy")) {
			 removeItem = 2361;
		} else 	if (ItemAssistant.getItemName(type).contains("Rune") || ItemAssistant.getItemName(type).contains("Runite")) {
			 removeItem = 2363;
		}
		checkBar(player, levelReq, amountToMake, type);
	}
	 
	private void checkBar(Player player, int level, int amountToMake, int type) {
    	SmithingData item = SmithingData.forId(type);
		if (item != null) {
			if (player.playerLevel[player.playerSmithing] >= item.getLvl()) {
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
		String name = ItemAssistant.getItemName(addItem);
		if (player.getItemAssistant().playerHasItem(removeItem, removeItem2)) {
			if (!player.isSmithing) {
				player.isSmithing = true;
				player.startAnimation(898);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.doAmount <= 0 || !player.getItemAssistant().playerHasItem(removeItem, removeItem2) 
								|| !player.isSmithing || player.isWoodcutting 
								|| player.isCrafting || player.isMoving 
								|| player.isMining || player.isBusy 
								|| player.isShopping || player.isFletching 
								|| player.isFiremaking || player.isSpinning 
								|| player.isPotionMaking || player.playerIsFishing 
								|| player.isBanking || player.isSmelting 
								|| player.isTeleporting || player.isHarvesting 
								|| player.playerIsCooking || player.isPotCrafting) {
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
							player.getPlayerAssistant().addSkillXP(XP, player.playerSmithing);
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