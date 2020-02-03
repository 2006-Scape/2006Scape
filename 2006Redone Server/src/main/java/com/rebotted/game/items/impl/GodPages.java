package com.rebotted.game.items.impl;

import com.rebotted.game.players.Player;

public class GodPages {

	public static void fillBook(Player player, int oldBook, int newBook, int page1, int page2, int page3) {
		if (player.getItemAssistant().playerHasItem(oldBook, 1) && player.getItemAssistant().playerHasItem(page1, 1) && player.getItemAssistant().playerHasItem(page2, 1) && player.getItemAssistant().playerHasItem(page3, 1)) {
			player.getItemAssistant().deleteItem(oldBook, player.getItemAssistant().getItemSlot(oldBook), 1);
			player.getItemAssistant().deleteItem(page1, player.getItemAssistant().getItemSlot(page1), 1);
			player.getItemAssistant().deleteItem(page2, player.getItemAssistant().getItemSlot(page2), 1);
			player.getItemAssistant().deleteItem(page3, player.getItemAssistant().getItemSlot(page3), 1);
			player.getItemAssistant().addItem(newBook, 1);
		} else {
			player.getPacketSender().sendMessage("You need all 3 pages to fill the book!");
		}
	}
	
	public static void itemOnItemHandle(Player player, int useWith, int itemUsed) {		
		if ((useWith == 3827) || (useWith == 3827) || (useWith == 3827) && (itemUsed == 3839)) { // sara
			fillBook(player, 3839, 3840, 3827, 3828, 3829);
		}		
		if ((itemUsed == 3827) || (itemUsed == 3828) || (itemUsed == 3829) || (useWith == 3839)) {// sara
			fillBook(player, 3839, 3840, 3827, 3828, 3829);
		}		
		if ((useWith == 3831) || (useWith == 3832) || (useWith == 3833) && (itemUsed == 3841)) { // zam
			fillBook(player, 3841, 3842, 3831, 3832, 3833);
		}		
		if ((itemUsed == 3831) || (itemUsed == 3832) || (itemUsed == 3833) || (useWith == 3841)) { // zam
			fillBook(player, 3841, 3842, 3831, 3832, 3833);
		}		
		if ((useWith == 3835) || (useWith == 3836) || (useWith == 3837) && (itemUsed == 3843)) { // guth
			fillBook(player, 3843, 3844, 3835, 3836, 3837);
		}		
		if ((itemUsed == 3835) || (itemUsed == 3836) || (itemUsed == 3837) || (useWith == 3843)) { // guth
			fillBook(player, 3843, 3844, 3835, 3836, 3837);
		}
	}

}