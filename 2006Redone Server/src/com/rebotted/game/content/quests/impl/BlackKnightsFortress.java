package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Client;

/**
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 * Cooks Assistant
 */

public class BlackKnightsFortress {

    Client client;

    public BlackKnightsFortress(Client client) {
        this.client = client;
    }

    private static final int NOTES = 291;

    public void showInformation() {
        for (int i = 8144; i < 8195; i++) {
            client.getPlayerAssistant().sendFrame126("", i);
        }
        client.getPlayerAssistant().sendFrame126("Black Knights' Fortress", 8144);
        if (client.blackKnight == 0) {
            client.getPlayerAssistant().sendFrame126("I can start this quest by speaking to Sir Amik Varze in", 8147);
            client.getPlayerAssistant().sendFrame126("Falador Castle.", 8148);
            client.getPlayerAssistant().sendFrame126("", 8149);
            client.getPlayerAssistant().sendFrame126("QUEST REQUIREMENTS:", 8150);
            client.getPlayerAssistant().sendFrame126("12 Quest Points", 8151);
        } else if (client.blackKnight == 1) {
            client.getPlayerAssistant().sendFrame126("@str@I've Talked with Sir Amik Varze", 8147);
            client.getPlayerAssistant().sendFrame126("He wants me to kill 30 Black Knights and", 8148);
            client.getPlayerAssistant().sendFrame126("collect their notes.", 8149);
            client.getPlayerAssistant().sendFrame126("", 8150);
            client.getPlayerAssistant().sendFrame126("@red@30 Black Knight notes", 8151);

        } else if (client.blackKnight == 2) {
            client.getPlayerAssistant().sendFrame126("@str@I talked to Sir Amik Varze.", 8147);
            client.getPlayerAssistant().sendFrame126("@str@I've killed 30 Black Knights", 8148);
            client.getPlayerAssistant().sendFrame126("@str@and given Sir Amik Varze his items.", 8149);
            client.getPlayerAssistant().sendFrame126("I should go speak to Sir Amik Varze.", 8150);
        } else if (client.blackKnight == 3) {
            client.getPlayerAssistant().sendFrame126("@str@I talked to Sir Amik Varze.", 8147);
            client.getPlayerAssistant().sendFrame126("@str@I've killed 30 Black Knights", 8148);
            client.getPlayerAssistant().sendFrame126("@str@and given Sir Amik Varze his items.", 8149);
            client.getPlayerAssistant().sendFrame126("", 8150);
            client.getPlayerAssistant().sendFrame126("@red@QUEST COMPLETE", 8151);
            client.getPlayerAssistant().sendFrame126("", 8152);
            client.getPlayerAssistant().sendFrame126("REWARDS:", 8153);
            client.getPlayerAssistant().sendFrame126("2,500 coins", 8154);
            client.getPlayerAssistant().sendFrame126("3 Quest Points", 8155);
        }
        client.getPlayerAssistant().showInterface(8134);
    }

}
