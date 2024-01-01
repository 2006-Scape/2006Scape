package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

public class BlackKnightsFortress {
    
    public static void showInformation(Player client) {
        for (int i = 8144; i < 8196; i++) {
            client.getPacketSender().sendString("", i);
        }
        for (int i = 12174; i < (12174 + 50); i++) {
			client.getPacketSender().sendString( "", i);
		}
		for (int i = 14945; i < (14945 + 100); i++) {
			client.getPacketSender().sendString("", i);
		}
        client.getPacketSender().sendString("Black Knights' Fortress", 8144);
        if (client.blackKnight == 0) {
            client.getPacketSender().sendString("I can start this quest by speaking to Sir Amik Varze in", 8147);
            client.getPacketSender().sendString("Falador Castle.", 8148);
            client.getPacketSender().sendString("", 8149);
            client.getPacketSender().sendString("Quest Requirements:", 8150);
            client.getPacketSender().sendString("12 Quest Points", 8151);
        } else if (client.blackKnight == 1) {
            client.getPacketSender().sendString("@str@I've talked with Sir Amik Varze", 8147);
            client.getPacketSender().sendString("He wants me to kill 30 Black Knights and", 8148);
            client.getPacketSender().sendString("collect their notes.", 8149);
            client.getPacketSender().sendString("", 8150);
            client.getPacketSender().sendString("@red@30 Black Knight notes", 8151);

        } else if (client.blackKnight == 2) {
            client.getPacketSender().sendString("@str@I talked to Sir Amik Varze.", 8147);
            client.getPacketSender().sendString("@str@I've killed 30 Black Knights", 8148);
            client.getPacketSender().sendString("@str@and given Sir Amik Varze his items.", 8149);
            client.getPacketSender().sendString("I should go speak to Sir Amik Varze.", 8150);
        } else if (client.blackKnight == 3) {
            client.getPacketSender().sendString("@str@I talked to Sir Amik Varze.", 8147);
            client.getPacketSender().sendString("@str@I've killed 30 Black Knights", 8148);
            client.getPacketSender().sendString("@str@and given Sir Amik Varze his items.", 8149);
            client.getPacketSender().sendString("", 8150);
            client.getPacketSender().sendString("@red@QUEST COMPLETE", 8151);
            client.getPacketSender().sendString("", 8152);
            client.getPacketSender().sendString("REWARDS:", 8153);
            client.getPacketSender().sendString("2,500 coins", 8154);
            client.getPacketSender().sendString("3 Quest Points", 8155);
        }
        client.getPacketSender().showInterface(8134);
    }

}
