package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

public class KnightsSword {
	
	public static void showInformation(Player player) {
		for(int i = 8144; i < 8195; i++) {
			player.getPacketSender().sendFrame126("", i);
		}
		player.getPacketSender().sendFrame126("@dre@The Knight's Sword", 8144);
		player.getPacketSender().sendFrame126("", 8145);
		if(player.knightS == 0) {
			player.getPacketSender().sendFrame126("I can start this quest by speaking to the squire", 8147);
			player.getPacketSender().sendFrame126("who is in the courtyard of the White Knight's castle", 8148);
			player.getPacketSender().sendFrame126("", 8149);
			player.getPacketSender().sendFrame126("I will need at least 10 mining to complete this quest", 8150);
		} else if(player.knightS == 1) {
			player.getPacketSender().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("by speaking to Reldo, the librarian in the Varrock Castle", 8149);
		} else if (player.knightS == 2) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("Red Berry Pie to get him to be willing to talk to me", 8153);
		} else if(player.knightS == 3) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("and find out if he'll make the sword for me.", 8157);
		} else if(player.knightS == 4) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
		} else if(player.knightS == 5) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
			player.getPacketSender().sendFrame126("The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendFrame126("a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendFrame126("get caught.", 8164);
			player.getPacketSender().sendFrame126("", 8165);
			player.getPacketSender().sendFrame126("", 8166);
			player.getPacketSender().sendFrame126("", 8167);
			player.getPacketSender().sendFrame126("", 8168);
			player.getPacketSender().sendFrame126("", 8169); 
		} else if(player.knightS == 6) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
			player.getPacketSender().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendFrame126("@str@a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendFrame126("@str@get caught.", 8164);
			player.getPacketSender().sendFrame126("I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendFrame126("", 8166);
			player.getPacketSender().sendFrame126("", 8167);
			player.getPacketSender().sendFrame126("", 8168);
			player.getPacketSender().sendFrame126("", 8169); 
		} else if(player.knightS == 7) {
			player.getPacketSender().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("to find a replacement.@str@ He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry Pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
			player.getPacketSender().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendFrame126("@str@cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendFrame126("@str@get caught.", 8164);
			player.getPacketSender().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendFrame126("", 8166);
			player.getPacketSender().sendFrame126("Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendFrame126("ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendFrame126("mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendFrame126("dangerous monsters. So I should be very careful.", 8170);
		} else if(player.knightS == 8) {
			player.getPacketSender().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("to find a replacement.@str@ He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
			player.getPacketSender().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendFrame126("@str@cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendFrame126("@str@get caught.", 8164);
			player.getPacketSender().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendFrame126("", 8166);
			player.getPacketSender().sendFrame126("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendFrame126("@str@ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendFrame126("@str@mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendFrame126("@str@dangerous monsters. So I should be very careful.", 8170);
			player.getPacketSender().sendFrame126("", 8171);
			player.getPacketSender().sendFrame126("Thurgo made me the sword, I should bring it back to the", 8172);
			player.getPacketSender().sendFrame126("knight to get my reward!", 8173);
			player.getPacketSender().sendFrame126("", 8174);
		} else if(player.knightS == 9) {
			player.getPacketSender().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendFrame126("", 8150);
			player.getPacketSender().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendFrame126("", 8154);
			player.getPacketSender().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendFrame126("", 8158);
			player.getPacketSender().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendFrame126("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendFrame126("", 8161);
			player.getPacketSender().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendFrame126("@str@a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendFrame126("@str@get caught.", 8164);
			player.getPacketSender().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendFrame126("", 8166);
			player.getPacketSender().sendFrame126("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendFrame126("@str@ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendFrame126("@str@mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendFrame126("@str@dangerous monsters. So I should be very careful.", 8170);
			player.getPacketSender().sendFrame126("@str@Thurgo made me the sword, I should bring it back to the", 8172);
			player.getPacketSender().sendFrame126("@str@knight to get my reward!", 8173);
			player.getPacketSender().sendFrame126("", 8173);
			player.getPacketSender().sendFrame126("@red@Quest Complete!", 8174);
		} 
		player.getPacketSender().showInterface(8134);
	}

}
