package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Romeo and Juliet
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class RomeoJuliet {

	Player client;

	public RomeoJuliet(Player player) {
		this.client = player;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPacketSender().sendFrame126("", i);
		}
		client.getPacketSender().sendFrame126("@dre@Romeo & Juliet", 8144);
		client.getPacketSender().sendFrame126("", 8145);
		if (client.romeojuliet == 0) {
			client.getPacketSender().sendFrame126(
					"To start the quest, you should talk with Romeo", 8147);
			client.getPacketSender().sendFrame126("found in Varrock Square.",
					8148);
		} else if (client.romeojuliet == 1) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender().sendFrame126(
					"Romeo has asked you to speak to Juliet for him", 8150);
			client.getPacketSender().sendFrame126(
					"and return to him, as she hasn't been responding to any",
					8151);
			client.getPacketSender().sendFrame126("his letters lately.", 8152);
		} else if (client.romeojuliet == 2) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender()
					.sendFrame126(
							"You have spoken to Juliet who's been acting strange",
							8154);
			client.getPacketSender().sendFrame126(
					"she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126("Could this be for Romeo?",
					8156);
		} else if (client.romeojuliet == 4) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"You have spoken to Romeo and given him the message", 8158);
			client.getPacketSender().sendFrame126(
					"You should try to talk to him again", 8159);
		} else if (client.romeojuliet == 5) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendFrame126(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendFrame126(
					"Romeo says you should see a witch called Winelda", 8160);
		} else if (client.romeojuliet == 6) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendFrame126(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendFrame126(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendFrame126("", 8161);
			client.getPacketSender().sendFrame126(
					"Winelda needs me to bring her 1 rats tail, 1 bone, and 1",
					8162);
			client.getPacketSender().sendFrame126("vial of water", 8163);
		} else if (client.romeojuliet == 7) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendFrame126(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendFrame126(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendFrame126("", 8161);
			client.getPacketSender().sendFrame126(
					"@str@Winelda needs me to bring her 1 rats tail, 1 bone",
					8162);
			client.getPacketSender().sendFrame126("@str@and 1 vial of water",
					8163);
			client.getPacketSender().sendFrame126("", 8164);
			client.getPacketSender()
					.sendFrame126(
							"I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",
							8162);
			client.getPacketSender().sendFrame126(
					"I should go speak to Juliet", 8163);
		} else if (client.romeojuliet == 8) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendFrame126(
					"@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendFrame126(
					"@str@Romeo says you should see a witch called Winelda",
					8160);
			client.getPacketSender().sendFrame126("", 8161);
			client.getPacketSender().sendFrame126(
					"@str@Winelda needs me to bring her 1 rats tail, 1 bone",
					8162);
			client.getPacketSender().sendFrame126("@str@and 1 vial of water",
					8163);
			client.getPacketSender().sendFrame126("", 8164);
			client.getPacketSender()
					.sendFrame126(
							"@str@I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",
							8165);
			client.getPacketSender().sendFrame126(
					"@str@I should go speak to Juliet and give her the potion",
					8166);
			client.getPacketSender().sendFrame126("", 8167);
			client.getPacketSender().sendFrame126(
					"I should speak to juliet and give her the potion", 8168);
			client.getPacketSender().sendFrame126(
					"I should go speak to Romeo", 8169);
		} else if (client.romeojuliet == 9) {
			client.getPacketSender()
					.sendFrame126(
							"@str@To start the quest, you should talk with Romeo",
							8147);
			client.getPacketSender().sendFrame126(
					"@str@found in Varrock Square.", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender()
					.sendFrame126(
							"@str@Romeo has asked you to speak to Juliet for him",
							8150);
			client.getPacketSender()
					.sendFrame126(
							"@str@and return to him, as she hasn't been responding to any",
							8151);
			client.getPacketSender().sendFrame126("@str@his letters lately.",
					8152);
			client.getPacketSender().sendFrame126("", 8153);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Juliet who's been acting strange",
					8154);
			client.getPacketSender().sendFrame126(
					"@str@she gave you a message and asked you to leave", 8155);
			client.getPacketSender().sendFrame126(
					"@str@could this be for Romeo?", 8156);
			client.getPacketSender().sendFrame126("", 8157);
			client.getPacketSender().sendFrame126(
					"@str@You have spoken to Romeo and given him the message",
					8158);
			client.getPacketSender().sendFrame126("@str@You should try to talk to him again", 8159);
			client.getPacketSender().sendFrame126("@str@Romeo says you should see a witch called Winelda", 8160);
			client.getPacketSender().sendFrame126("", 8161);
			client.getPacketSender().sendFrame126("@str@Winelda needs me to bring her 1 rats tail, 1 bone", 8162);
			client.getPacketSender().sendFrame126("@str@and 1 vial of water",8163);
			client.getPacketSender().sendFrame126("", 8164);
			client.getPacketSender().sendFrame126("@str@I brought Winelda 1 rats tail, 1 bone, and 1 vail of water",8165);
			client.getPacketSender().sendFrame126("@str@I should go speak to Juliet and give her the potion", 8166);
			client.getPacketSender().sendFrame126("", 8167);
			client.getPacketSender().sendFrame126("@str@I have spoken to Juliet, she drank the potion", 8168);
			client.getPacketSender().sendFrame126("@str@I should go speak to Romeo", 8169);
			client.getPacketSender().sendFrame126("", 8168);
			client.getPacketSender().sendFrame126("I have spoken to Romeo, he's thankful for all of our help", 8169);
			client.getPacketSender().sendFrame126("@red@     QUEST COMPLETE", 8170);
			client.getPacketSender().sendFrame126("As a reward, I gained 5 quest points.", 8171);
		}
		client.getPacketSender().showInterface(8134);
	}

}
