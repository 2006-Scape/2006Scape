package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

public class KnightsSword {
	
	public static void showInformation(Player player) {
		for(int i = 8144; i < 8195; i++) {
			player.getPacketSender().sendString("", i);
		}
		player.getPacketSender().sendString("@dre@The Knight's Sword", 8144);
		player.getPacketSender().sendString("", 8145);
		if(player.knightS == 0) {
			player.getPacketSender().sendString("I can start this quest by speaking to the squire", 8147);
			player.getPacketSender().sendString("who is in the courtyard of the White Knight's castle", 8148);
			player.getPacketSender().sendString("", 8149);
			player.getPacketSender().sendString("I will need at least 10 mining to complete this quest", 8150);
		} else if(player.knightS == 1) {
			player.getPacketSender().sendString("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("by speaking to Reldo, the librarian in the Varrock Castle", 8149);
		} else if (player.knightS == 2) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("Red Berry Pie to get him to be willing to talk to me", 8153);
		} else if(player.knightS == 3) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("and find out if he'll make the sword for me.", 8157);
		} else if(player.knightS == 4) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
		} else if(player.knightS == 5) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
			player.getPacketSender().sendString("The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendString("a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendString("get caught.", 8164);
			player.getPacketSender().sendString("", 8165);
			player.getPacketSender().sendString("", 8166);
			player.getPacketSender().sendString("", 8167);
			player.getPacketSender().sendString("", 8168);
			player.getPacketSender().sendString("", 8169); 
		} else if(player.knightS == 6) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
			player.getPacketSender().sendString("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendString("@str@a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendString("@str@get caught.", 8164);
			player.getPacketSender().sendString("I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendString("", 8166);
			player.getPacketSender().sendString("", 8167);
			player.getPacketSender().sendString("", 8168);
			player.getPacketSender().sendString("", 8169); 
		} else if(player.knightS == 7) {
			player.getPacketSender().sendString("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("to find a replacement.@str@ He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry Pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
			player.getPacketSender().sendString("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendString("@str@cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendString("@str@get caught.", 8164);
			player.getPacketSender().sendString("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendString("", 8166);
			player.getPacketSender().sendString("Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendString("ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendString("mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendString("dangerous monsters. So I should be very careful.", 8170);
		} else if(player.knightS == 8) {
			player.getPacketSender().sendString("The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("to find a replacement.@str@ He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
			player.getPacketSender().sendString("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendString("@str@cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendString("@str@get caught.", 8164);
			player.getPacketSender().sendString("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendString("", 8166);
			player.getPacketSender().sendString("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendString("@str@ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendString("@str@mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendString("@str@dangerous monsters. So I should be very careful.", 8170);
			player.getPacketSender().sendString("", 8171);
			player.getPacketSender().sendString("Thurgo made me the sword, I should bring it back to the", 8172);
			player.getPacketSender().sendString("knight to get my reward!", 8173);
			player.getPacketSender().sendString("", 8174);
		} else if(player.knightS == 9) {
			player.getPacketSender().sendString("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			player.getPacketSender().sendString("@str@to find a replacement. He suggested that I start", 8148);
			player.getPacketSender().sendString("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			player.getPacketSender().sendString("", 8150);
			player.getPacketSender().sendString("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			player.getPacketSender().sendString("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			player.getPacketSender().sendString("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			player.getPacketSender().sendString("", 8154);
			player.getPacketSender().sendString("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			player.getPacketSender().sendString("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			player.getPacketSender().sendString("@str@and find out if he'll make the sword for me.", 8157);
			player.getPacketSender().sendString("", 8158);
			player.getPacketSender().sendString("@str@Thurgo says he needs a picture of the sword.", 8159);
			player.getPacketSender().sendString("@str@Maybe the squire will have one?", 8160);
			player.getPacketSender().sendString("", 8161);
			player.getPacketSender().sendString("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			player.getPacketSender().sendString("@str@a cupboard in his room, but I must be very careful not to", 8163);
			player.getPacketSender().sendString("@str@get caught.", 8164);
			player.getPacketSender().sendString("@str@I should bring the picture to Thurgo", 8165);
			player.getPacketSender().sendString("", 8166);
			player.getPacketSender().sendString("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			player.getPacketSender().sendString("@str@ore for him to make the sword with. He says blurite can be", 8168);
			player.getPacketSender().sendString("@str@mined in the cave by his home, but it is guarded by", 8169);
			player.getPacketSender().sendString("@str@dangerous monsters. So I should be very careful.", 8170);
			player.getPacketSender().sendString("@str@Thurgo made me the sword, I should bring it back to the", 8172);
			player.getPacketSender().sendString("@str@knight to get my reward!", 8173);
			player.getPacketSender().sendString("", 8173);
			player.getPacketSender().sendString("@red@Quest Complete!", 8174);
		} 
		player.getPacketSender().showInterface(8134);
	}

}
