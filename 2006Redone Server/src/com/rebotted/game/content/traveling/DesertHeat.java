package com.rebotted.game.content.traveling;

import com.rebotted.GameConstants;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

/**
 * Aug 12, 2017 : 1:51:21 AM
 * DesertHeat.java
 * @author Andrew (Mr Extremez)
 */
public class DesertHeat {

	/**
	 * Damage dealt to player
	 */
	private static int DAMAGE = 1+Misc.random(8);
	/**
	 * Waterskin animation
	 */
	private static final int ANIMATION = 829;
	/**
	 * Time player has if they don't have protection
	 */
	private static int REGULAR_TIMER = 30000;
	/**
	 * Integer to check if player has waterskins
	 */
	private static int waterskin = -1;
	/**
	 * Waterskins before and after
	 */
	private static int[][] WATERSKINS = {
			{1825, 1823},//waterskin 3
			{1827, 1825},//waterskin 2
			{1829, 1827},//waterskin 1
			{1831, 1829}//waterskin 0
	};
	/**
	 * Desert clothes
	 */
	private static final int[][] CLOTHES = {
			{1833, GameConstants.CHEST}, {1835, GameConstants.LEGS}, {1837, GameConstants.FEET}
		};
	
	private static void doDamage(Player player) {
		player.getPacketSender().sendMessage("You should get a waterskin for any traveling in the desert.");
		player.handleHitMask(DAMAGE);
		player.dealDamage(DAMAGE);
		player.getPlayerAssistant().refreshSkill(player.playerHitpoints);
	}
	
	private static int getClothes(Player player) {
		int temp = 0;
	    for (int element[] : CLOTHES) {
			if (player.playerEquipment[element[1]] == element[0]) {
				temp += 1;
			}
		}
		return temp;
	}
	
	private static int getTimer(Player player) {
		/**
		 * 10 secs extra for each desert clothing item
		 */
		int heat = 10000 * getClothes(player);
		return REGULAR_TIMER + heat;
	}

	public static void callHeat(final Player player) {
		if (!player.inDesert() || player.playerLevel[player.playerHitpoints] < 0) {
			return;
		}
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.inDesert() || player.playerLevel[player.playerHitpoints] < 0 || player.disconnected) {
					container.stop();
					return;
				}
				if (System.currentTimeMillis() - player.lastDesert > getTimer(player)) {
					player.lastDesert = System.currentTimeMillis();
					if (!checkWaterskin(player)) {
						doDamage(player);
					}
					container.stop();
				} else if (player.playerLevel[player.playerHitpoints] < 0) {
					player.isDead = true;
					container.stop();
				}
			}
			@Override
			public void stop() {
				
			}
		}, 1);
	}
	
	public static boolean checkWaterskin(final Player player) {
	  for (int i = 0; i < WATERSKINS.length; i++) {
	    	if (player.getItemAssistant().playerHasItem(WATERSKINS[i][1])) {
	    		waterskin = i;
           }
        }
		if (waterskin == -1) {//empty waterskin
			return false;
		}
		if (waterskin > 0) {
		    player.getItemAssistant().deleteItem(WATERSKINS[waterskin][1], 1);
		    player.getItemAssistant().addItem(WATERSKINS[waterskin][0], 1);
			player.startAnimation(ANIMATION);
			return true;
		}
		return false;
	}
	
	public static void showWarning(Player player) {
		for (int i = 8144; i < 8195; i++) {
			player.getPacketSender().sendFrame126("", i);
		}
		player.getPacketSender().sendFrame126("@dre@DESERT WARNING", 8144);
		player.getPacketSender().sendFrame126("", 8145);
		player.getPacketSender().sendFrame126("The intense heat of the desert reduces your health.", 8147);
		player.getPacketSender().sendFrame126("Bring 2-5 waterskins to avoid receiving any damage.", 8148);
		player.getPacketSender().sendFrame126("", 8149);
		player.getPacketSender().sendFrame126("Wearing desert robes will not prevent the damage, but", 8150);
		player.getPacketSender().sendFrame126("will reduce it significantly.", 8151);
		player.getPacketSender().sendFrame126("", 8152);
		player.getPacketSender().sendFrame126("The waterskins however need to be re-filled. Bring a", 8153);
		player.getPacketSender().sendFrame126("knife and cut healthy cacti to re-fill the waterskins.", 8154);
		player.getPacketSender().sendFrame126("@red@Any water vessels will evaporate, such as jug of water.", 8155);
		player.getPacketSender().showInterface(8134);
	}
}
