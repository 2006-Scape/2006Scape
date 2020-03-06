package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Imp Catcher
 * @author Andrew (Mr Extremez)
 */

public class ImpCatcher {


	public static void showInformation(Player client) {
		for (int i = 8144; i < 8295; i++) {
			client.getPacketSender().sendString("", i);
		}
		client.getPacketSender().sendString("@dre@Imp Catcher", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.impsC == 0) {
			client.getPacketSender().sendString( "I can start this quest by speaking to Wizard Mizgog who is", 8147);
			client.getPacketSender().sendString("in the Wizard's Tower.", 8148);
		} else if (client.impsC == 1) {
			client.getPacketSender().sendString("@str@I can start this quest by speaking to Wizard Mizgog who is", 8147);
			client.getPacketSender().sendString("@str@in the Wizard's Tower.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString("Wizard Mizgog have asked you to get the following items:", 8150);
			client.getPacketSender().sendString("Red bead", 8151);
			client.getPacketSender().sendString("Yellow bead", 8152);
			client.getPacketSender().sendString("Black bead", 8153);
			client.getPacketSender().sendString("White bead", 8154);
		} else if (client.impsC == 2) {
			client.getPacketSender().sendString("@str@I can start this quest by speaking to Wizard Mizgog who is", 8147);
			client.getPacketSender().sendString("@str@in the Wizard's Tower.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString("@str@Wizard Mizgog have asked you to get the following items:", 8150);
			client.getPacketSender().sendString("@str@Red bead", 8151);
			client.getPacketSender().sendString("@str@Yellow bead", 8152);
			client.getPacketSender().sendString("@str@Black bead", 8153);
			client.getPacketSender().sendString("@str@White bead", 8154);
			client.getPacketSender().sendString("", 8155);
			client.getPacketSender().sendString("You have completed this quest!", 8156);
		}
		client.getPacketSender().showInterface(8134);
	}

}
