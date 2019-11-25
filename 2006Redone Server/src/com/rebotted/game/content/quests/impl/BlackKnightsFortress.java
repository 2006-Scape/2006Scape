package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

public class BlackKnightsFortress {
    
    public static void showInformation(Player client) {
        for (int i = 8144; i < 8195; i++) {
            client.getPacketSender().sendFrame126("", i);
        }
        client.getPacketSender().sendFrame126("Black Knights' Fortress", 8144);
        if (client.blackKnight == 0) {
            client.getPacketSender().sendFrame126("I can start this quest by speaking to Sir Amik Varze in", 8147);
            client.getPacketSender().sendFrame126("Falador Castle.", 8148);
            client.getPacketSender().sendFrame126("", 8149);
            client.getPacketSender().sendFrame126("QUEST REQUIREMENTS:", 8150);
            client.getPacketSender().sendFrame126("12 Quest Points", 8151);
        } else if (client.blackKnight == 1) {
            client.getPacketSender().sendFrame126("@str@I've Talked with Sir Amik Varze", 8147);
            client.getPacketSender().sendFrame126("He wants me to kill 30 Black Knights and", 8148);
            client.getPacketSender().sendFrame126("collect their notes.", 8149);
            client.getPacketSender().sendFrame126("", 8150);
            client.getPacketSender().sendFrame126("@red@30 Black Knight notes", 8151);

        } else if (client.blackKnight == 2) {
            client.getPacketSender().sendFrame126("@str@I talked to Sir Amik Varze.", 8147);
            client.getPacketSender().sendFrame126("@str@I've killed 30 Black Knights", 8148);
            client.getPacketSender().sendFrame126("@str@and given Sir Amik Varze his items.", 8149);
            client.getPacketSender().sendFrame126("I should go speak to Sir Amik Varze.", 8150);
        } else if (client.blackKnight == 3) {
            client.getPacketSender().sendFrame126("@str@I talked to Sir Amik Varze.", 8147);
            client.getPacketSender().sendFrame126("@str@I've killed 30 Black Knights", 8148);
            client.getPacketSender().sendFrame126("@str@and given Sir Amik Varze his items.", 8149);
            client.getPacketSender().sendFrame126("", 8150);
            client.getPacketSender().sendFrame126("@red@QUEST COMPLETE", 8151);
            client.getPacketSender().sendFrame126("", 8152);
            client.getPacketSender().sendFrame126("REWARDS:", 8153);
            client.getPacketSender().sendFrame126("2,500 coins", 8154);
            client.getPacketSender().sendFrame126("3 Quest Points", 8155);
        }
        client.getPacketSender().showInterface(8134);
    }

}
