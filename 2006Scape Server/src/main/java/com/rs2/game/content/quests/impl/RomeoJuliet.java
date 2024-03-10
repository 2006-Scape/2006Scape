package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Romeo and Juliet
 * @author Andrew (Mr Extremez)
 */

public class RomeoJuliet {

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
		client.getPacketSender().sendString("@dre@Romeo & Juliet", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.romeojuliet == 0) {
			client.getPacketSender().sendString(
					"To start the quest, you should talk with Romeo", 8147);
			client.getPacketSender().sendString("found in Varrock Square.",
					8148);
		} else if (client.romeojuliet == 1) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString(
					"Romeo has asked you to speak to Juliet for him", 8150);
			client.getPacketSender().sendString(
					"and return to him, as she hasn't been responding to any",
					8151);
			client.getPacketSender().sendString("his letters lately.", 8152);
		} else if (client.romeojuliet == 2) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender()
					.sendString(
							"You have spoken to Juliet who's been acting strange",
							8154);
			client.getPacketSender().sendString(
					"she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString("Could this be for Romeo?",
					8156);
		} else if (client.romeojuliet == 4) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"You have spoken to Romeo and given him the message", 8158);
			client.getPacketSender().sendString(
					"You should try to talk to him again", 8159);
		} else if (client.romeojuliet == 5) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendString(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendString(
					"Romeo says you should see a witch called Winelda", 8160);
		} else if (client.romeojuliet == 6) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendString(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendString(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendString("", 8161);
			client.getPacketSender().sendString(
					"Winelda needs me to bring her 1 rats tail, 1 bone, and 1",
					8162);
			client.getPacketSender().sendString("vial of water", 8163);
		} else if (client.romeojuliet == 7) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendString(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendString(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendString("", 8161);
			client.getPacketSender().sendString(
					"@str@Winelda needs me to bring her 1 rats tail, 1 bone",
					8162);
			client.getPacketSender().sendString("@str@and 1 vial of water",
					8163);
			client.getPacketSender().sendString("", 8164);
			client.getPacketSender()
					.sendString(
							"I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",
							8162);
			client.getPacketSender().sendString(
					"I should go speak to Juliet", 8163);
		} else if (client.romeojuliet == 8) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendString(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendString(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendString("", 8161);
			client.getPacketSender().sendString(
					"@str@Winelda needs me to bring her 1 rats tail, 1 bone",
					8162);
			client.getPacketSender().sendString("@str@and 1 vial of water",
					8163);
			client.getPacketSender().sendString("", 8164);
			client.getPacketSender()
					.sendString(
							"@str@I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",
							8165);
			client.getPacketSender().sendString(
					"@str@I should go speak to Juliet and give her the potion",
					8166);
			client.getPacketSender().sendString("", 8167);
			client.getPacketSender().sendString(
					"I should speak to juliet and give her the potion", 8168);
			client.getPacketSender().sendString(
					"I should go speak to Romeo", 8169);
		} else if (client.romeojuliet == 9) {
			client.getPacketSender()
					.sendString(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendString(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender()
					.sendString(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendString(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendString("@str@his letters lately.",
					8152);
			client.getPacketSender().sendString("", 8153);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendString(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendString(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendString("", 8157);
			client.getPacketSender().sendString(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendString("@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendString("@str@Romeo says you should see a witch called Winelda", 8160);
			client.getPacketSender().sendString("", 8161);
			client.getPacketSender().sendString("@str@Winelda needs me to bring her 1 rats tail, 1 bone", 8162);
			client.getPacketSender().sendString("@str@and 1 vial of water",8163);
			client.getPacketSender().sendString("", 8164);
			client.getPacketSender().sendString("@str@I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",8165);
			client.getPacketSender().sendString("@str@I should go speak to Juliet and give her the potion", 8166);
			client.getPacketSender().sendString("", 8167);
			client.getPacketSender().sendString("@str@I have spoken to Juliet, she drank the potion", 8168);
			client.getPacketSender().sendString("@str@I should go speak to Romeo", 8169);
			client.getPacketSender().sendString("", 8168);
			client.getPacketSender().sendString("I have spoken to Romeo, he's thankful for all of our help", 8169);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE", 8170);
			client.getPacketSender().sendString("As a reward, I gained 5 quest points.", 8171);
		}
		client.getPacketSender().showInterface(8134);
	}

}
