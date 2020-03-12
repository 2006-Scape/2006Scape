package com.rebotted.game.content.traveling;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;

/**
 * Aug 31, 2017 : 6:54:30 AM
 * CarpetTravel.java
 * @author Andrew (Mr Extremez)
 */
public class CarpetTravel {
	
	public static void carpetTravel(final Player player, final int x, final int y) {
		if (!player.getItemAssistant().playerHasItem(995, 200)) {
			player.getDialogueHandler().itemMessage("You need 200 gold to ride my rug.", 995, 200);
			player.getDialogueHandler().endDialogue();
			return;
		}
		player.getPacketSender().sendMessage("You travel into the desert on your magic carpet.");
		player.getItemAssistant().deleteItem(995, 200);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				// TODO Auto-generated method stub
				player.startAnimation(2262);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						// TODO Auto-generated method stub
						player.getPlayerAssistant().movePlayer(x, y, 0);
						//PlayerAssistant.playerWalk(c,x,y);
						player.getPacketSender().closeAllWindows();
						player.getPacketSender().sendMessage("You arrive at your destination.");
						container.stop();
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}

				}, 1);
				container.stop();
			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub
				
			}

		}, 2);
		player.getPacketSender().closeAllWindows();
	}

}
