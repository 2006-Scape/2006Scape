package redone.game.content.quests.impl;

import redone.game.players.Client;

public class KnightsSword {
	
	public static void showInformation(Client c) {
		for(int i = 8144; i < 8195; i++) {
			c.getPlayerAssistant().sendFrame126("", i);
		}
		c.getPlayerAssistant().sendFrame126("@dre@The Knight's Sword", 8144);
		c.getPlayerAssistant().sendFrame126("", 8145);
		if(c.knightS == 0) {
			c.getPlayerAssistant().sendFrame126("I can start this quest by speaking to the squire", 8147);
			c.getPlayerAssistant().sendFrame126("who is in the courtyard of the White Knight's castle", 8148);
			c.getPlayerAssistant().sendFrame126("", 8149);
			c.getPlayerAssistant().sendFrame126("I will need at least 10 mining to complete this quest", 8150);
		} else if(c.knightS == 1) {
			c.getPlayerAssistant().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("by speaking to Reldo, the librarian in the Varrock Castle", 8149);
		} else if (c.knightS == 2) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("Red Berry Pie to get him to be willing to talk to me", 8153);
		} else if(c.knightS == 3) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("and find out if he'll make the sword for me.", 8157);
		} else if(c.knightS == 4) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
		} else if(c.knightS == 5) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("@str@Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
			c.getPlayerAssistant().sendFrame126("The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			c.getPlayerAssistant().sendFrame126("a cupboard in his room, but I must be very careful not to", 8163);
			c.getPlayerAssistant().sendFrame126("get caught.", 8164);
			c.getPlayerAssistant().sendFrame126("", 8165);
			c.getPlayerAssistant().sendFrame126("", 8166);
			c.getPlayerAssistant().sendFrame126("", 8167);
			c.getPlayerAssistant().sendFrame126("", 8168);
			c.getPlayerAssistant().sendFrame126("", 8169); 
		} else if(c.knightS == 6) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("@str@Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
			c.getPlayerAssistant().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			c.getPlayerAssistant().sendFrame126("@str@a cupboard in his room, but I must be very careful not to", 8163);
			c.getPlayerAssistant().sendFrame126("@str@get caught.", 8164);
			c.getPlayerAssistant().sendFrame126("I should bring the picture to Thurgo", 8165);
			c.getPlayerAssistant().sendFrame126("", 8166);
			c.getPlayerAssistant().sendFrame126("", 8167);
			c.getPlayerAssistant().sendFrame126("", 8168);
			c.getPlayerAssistant().sendFrame126("", 8169); 
		} else if(c.knightS == 7) {
			c.getPlayerAssistant().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("to find a replacement.@str@ He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry Pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("@str@Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
			c.getPlayerAssistant().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			c.getPlayerAssistant().sendFrame126("@str@cupboard in his room, but I must be very careful not to", 8163);
			c.getPlayerAssistant().sendFrame126("@str@get caught.", 8164);
			c.getPlayerAssistant().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			c.getPlayerAssistant().sendFrame126("", 8166);
			c.getPlayerAssistant().sendFrame126("Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			c.getPlayerAssistant().sendFrame126("ore for him to make the sword with. He says blurite can be", 8168);
			c.getPlayerAssistant().sendFrame126("mined in the cave by his home, but it is guarded by", 8169);
			c.getPlayerAssistant().sendFrame126("dangerous monsters. So I should be very careful.", 8170);
		} else if(c.knightS == 8) {
			c.getPlayerAssistant().sendFrame126("The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("to find a replacement.@str@ He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("@str@Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
			c.getPlayerAssistant().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			c.getPlayerAssistant().sendFrame126("@str@cupboard in his room, but I must be very careful not to", 8163);
			c.getPlayerAssistant().sendFrame126("@str@get caught.", 8164);
			c.getPlayerAssistant().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			c.getPlayerAssistant().sendFrame126("", 8166);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			c.getPlayerAssistant().sendFrame126("@str@ore for him to make the sword with. He says blurite can be", 8168);
			c.getPlayerAssistant().sendFrame126("@str@mined in the cave by his home, but it is guarded by", 8169);
			c.getPlayerAssistant().sendFrame126("@str@dangerous monsters. So I should be very careful.", 8170);
			c.getPlayerAssistant().sendFrame126("", 8171);
			c.getPlayerAssistant().sendFrame126("Thurgo made me the sword, I should bring it back to the", 8172);
			c.getPlayerAssistant().sendFrame126("knight to get my reward!", 8173);
			c.getPlayerAssistant().sendFrame126("", 8174);
		} else if(c.knightS == 9) {
			c.getPlayerAssistant().sendFrame126("@str@The squire has lost Sir Vyvin's sword and asked me", 8147);
			c.getPlayerAssistant().sendFrame126("@str@to find a replacement. He suggested that I start", 8148);
			c.getPlayerAssistant().sendFrame126("@str@by speaking to Reldo, the librarian in the Varrock Castle", 8149);
			c.getPlayerAssistant().sendFrame126("", 8150);
			c.getPlayerAssistant().sendFrame126("@str@Reldo told me there may be an Imcando dwarf living near the", 8151);
			c.getPlayerAssistant().sendFrame126("@str@Asgarnian peninsula. He said I should bring him some", 8152);
			c.getPlayerAssistant().sendFrame126("@str@Red Berry Pie to get him to be willing to talk to me.", 8153);
			c.getPlayerAssistant().sendFrame126("", 8154);
			c.getPlayerAssistant().sendFrame126("@str@I found the Imcando dwarf named Thurgo and gave him some", 8155);
			c.getPlayerAssistant().sendFrame126("@str@Red berry pie. Now that he likes me I should talk to him", 8156);
			c.getPlayerAssistant().sendFrame126("@str@and find out if he'll make the sword for me.", 8157);
			c.getPlayerAssistant().sendFrame126("", 8158);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo says he needs a picture of the sword.", 8159);
			c.getPlayerAssistant().sendFrame126("@str@Maybe the squire will have one?", 8160);
			c.getPlayerAssistant().sendFrame126("", 8161);
			c.getPlayerAssistant().sendFrame126("@str@The squire thinks Sir Vyvin keeps a picture of the sword in", 8162);
			c.getPlayerAssistant().sendFrame126("@str@a cupboard in his room, but I must be very careful not to", 8163);
			c.getPlayerAssistant().sendFrame126("@str@get caught.", 8164);
			c.getPlayerAssistant().sendFrame126("@str@I should bring the picture to Thurgo", 8165);
			c.getPlayerAssistant().sendFrame126("", 8166);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo has asked me to bring him 2 iron bars and 1 blurite", 8167);
			c.getPlayerAssistant().sendFrame126("@str@ore for him to make the sword with. He says blurite can be", 8168);
			c.getPlayerAssistant().sendFrame126("@str@mined in the cave by his home, but it is guarded by", 8169);
			c.getPlayerAssistant().sendFrame126("@str@dangerous monsters. So I should be very careful.", 8170);
			c.getPlayerAssistant().sendFrame126("@str@Thurgo made me the sword, I should bring it back to the", 8172);
			c.getPlayerAssistant().sendFrame126("@str@knight to get my reward!", 8173);
			c.getPlayerAssistant().sendFrame126("", 8173);
			c.getPlayerAssistant().sendFrame126("@red@Quest Complete!", 8174);
		} 
		c.getPlayerAssistant().showInterface(8134);
	}

}
