package com.rs2.game.objects.impl;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.players.Player;
import com.rs2.world.Boundary;

/**
 * Pickables
 * @author Andrew (Mr Extremez)
 */

public class Pickable {

	private final static int[][] PICKABLE_ITEMS = { 
			{ 1161, 1965 }, // Cabbage
			{ 2646, 1779 }, // Flax
			{ 313, 1947 }, // Wheat
			{ 5585, 1947 }, // Wheat
			{ 5584, 1947 }, // Wheat
			{ 5585, 1947 }, // Wheat
			{ 312, 1942 }, // Potato
			{ 3366, 1957 }, // Onion
	};

	public static void pickObject(final Player player, final int objectType, final int objectX, final int objectY) {
		if (player.miscTimer + 1800 > System.currentTimeMillis()) {
			return;
		}
		for (int[] data : PICKABLE_ITEMS) {
			final int objectId = data[0];
			int itemId = data[1];
			if (objectType == objectId) {
				if (objectType == 1161 && Boundary.isIn(player, Boundary.FALADOR)) {//Cabbage falador
					player.getItemAssistant().addItem(1967, 1);
				} else {
					player.getItemAssistant().addItem(itemId, 1);
				}
				break;
			}
		}
		if (player.getItemAssistant().freeSlots() > 0) {
			player.turnPlayerTo(objectX, objectY);
			player.startAnimation(827);
			if (objectType == 2646 && random(3) == 0 || objectType != 2646) {
				if (player.outStream != null) {
					GameEngine.objectHandler.createAnObject(-1, objectX, objectY);
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							container.stop();
						}
						@Override
						public void stop() {
							if (player.outStream != null) {
								GameEngine.objectHandler.createAnObject(objectType, objectX, objectY);
							}
						}
					}, 5);
				}
			}
			player.getPacketSender().sendSound(SoundList.PICKABLE, 100, 1);
			player.miscTimer = System.currentTimeMillis();
		} else {
			player.getPacketSender().sendMessage("Your inventory is too full to hold any more items!");
		}
	}

	private static int random(int range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}
}
