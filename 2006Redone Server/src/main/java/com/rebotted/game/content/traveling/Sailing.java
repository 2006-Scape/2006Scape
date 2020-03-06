package com.rebotted.game.content.traveling;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.items.ItemConstants;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Player;

/**
 * @author Andrew (Mr Extremez)
 */

public class Sailing {

	private static final int[][] TRAVEL_DATA = { {}, // 0 - Null
			{ 2834, 3335, 15 }, // 1 - From Port Sarim to Entrana
			{ 3048, 3234, 15 }, // 2 - From Entrana to Port Sarim
			{ 2853, 3237, 12 }, // 3 - From Port Sarim to Crandor
			{ 2834, 3335, 13 }, // 4 - From Crandor to Port Sarim
			{ 2956, 3146, 9 }, // 5 - From Port Sarim to Karajama
			{ 3029, 3217, 8 }, // 6 - From Karajama to Port Sarim
			{ 2772, 3234, 3 }, // 7 - From Ardougne to Brimhaven
			{ 2683, 3271, 3 }, // 8 - From Brimhaven to Ardougne
			{ 2998, 3043, 23 }, // 11 - From Port Khazard to Ship Yard
			{ 2676, 3170, 23 }, // 12 - From Ship Yard to Port Khazard
			{ 2998, 3043, 17 }, // 13 - From Cairn Island to Ship Yard
			{ 2659, 2676, 12 }, // 14 - From Port Sarim to Pest Control
			{ 3041, 3202, 12 }, // 15 - From Pest Control to Port Sarim
			{ 2763, 2956, 10 }, // 16 - To Cairn Isle from Feldip Hills
			{ 2551, 3759, 20}, // 17 - To Waterbirth from Relleka
			{ 2620, 3686, 20}, //18 - To Relleka from Waterbirth
	};
	
	//2620, 3686 - relleka
	//2551, 3759 - waterbirth
	
	public static boolean checkForCash(Player c) {
		if (!c.getItemAssistant().playerHasItem(995, 1000)) {
			c.getDialogueHandler().sendNpcChat1("You need 1000 coins to to travel on this ship!", 2437, NpcHandler.getNpcListName(c.npcType));
			c.nextChat = 0;
			return false;
		}
		c.getItemAssistant().deleteItem(995, 1000);
		c.getPacketSender().sendMessage("Your free to go and pay the 1000 coins.");
		return true;
	}

	public static boolean checkForCoins(Player c) {
		if (!c.getItemAssistant().playerHasItem(995, 30)) {
			c.getDialogueHandler().sendNpcChat1("You need 30 coins to to travel on this ship!", 381, NpcHandler.getNpcListName(c.npcType));
			c.nextChat = 0;
			return false;
		}
		c.getItemAssistant().deleteItem(995, 30);
		c.getPacketSender().sendMessage("Your free to go and pay the 30 coins.");
		return true;
	}

	public static boolean searchForAlcohol(Player c) {
		for (int element : ItemConstants.ALCOHOL_RELATED_ITEMS) {
			if (c.getItemAssistant().playerHasItem(element, 1)) {
				c.getDialogueHandler().sendNpcChat1("You can't bring intoxicating items to Asgarnia!", c.npcType, NpcHandler.getNpcListName(c.npcType));
				c.nextChat = 0;
				return false;
			}
		}
		c.getPacketSender().sendMessage(
				"Your clean of any possible alchohol.");
		return true;
	}

	public static boolean quickSearch(Player c) {
		for (int element : ItemConstants.COMBAT_RELATED_ITEMS) {
			if (c.getItemAssistant().playerHasItem(element, 1) || c.getItemAssistant().playerHasEquipped(element)) {
				c.getDialogueHandler().sendNpcChat2("Grr! I see you brought some illegal items! Get", "out of my sight immediately!", 657, NpcHandler.getNpcListName(c.npcType));
				c.nextChat = 0;
				return false;
			}
		}
		c.getPacketSender().sendMessage("Your clean of any possible weapons.");
		return true;
	}

	public static void startTravel(final Player c, final int i) {
		if (i == 1) {// entrana check
			if (!quickSearch(c)) {
				return;
			}
		}
		if (i == 2) {// entrana check
			if (!searchForAlcohol(c)) {
				return;
			}
		}
		if (i == 7 || i == 8) {
			if (!checkForCoins(c)) {
				return;
			}
		}
		if (i == 17 || i == 18) {
			if (!checkForCash(c)) {
				return;
			}
		}
		c.stopPlayerPacket = true;
		c.getPacketSender().showInterface(3281);
		c.getPacketSender().sendConfig(75, i);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.getPlayerAssistant().movePlayer(getX(i), getY(i), 0);
				container.stop();
			}
			@Override
			public void stop() {
				
			}
		}, getTime(i) - 1);

		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.stopPlayerPacket = false;
				c.getPacketSender().sendConfig(75, -1);
				c.getPacketSender().closeAllWindows();
				c.getDialogueHandler().sendStatement("You arrive safely.");
				c.nextChat = 0;
				container.stop();
			}
			@Override
			public void stop() {
				
			}
		}, getTime(i));
	
	}

	public static int getX(int i) {
		return TRAVEL_DATA[i][0];
	}

	public static int getY(int i) {
		return TRAVEL_DATA[i][1];
	}

	public static int getTime(int i) {
		return TRAVEL_DATA[i][2];
	}

}
