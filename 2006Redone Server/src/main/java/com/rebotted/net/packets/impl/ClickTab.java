package com.rebotted.net.packets.impl;

import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

public class ClickTab implements PacketType {

	@Override
	public void processPacket(final Player player, int packetType, int packetSize) {
		switch (packetSize) {
		case 1:// first part.
			if (player.tutorialProgress == 0) { // wrench
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"On the side panel you can now see a variety of options from",
								"changing your graphic settings and audio and music volume",
								"to selecting whether your player should accept help from",
								"other players. Don't worry about these too much for now.",
								"@blu@Player controls");
				player.getPacketSender().chatbox(6179);
				player.tutorialProgress = 1;
			} else if (player.tutorialProgress == 3) { // backpack
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"You can click on the backpack icon at any time to view the",
								"items that you currently have in your inventory. You will see",
								"that you now have an axe in your inventory. Use this to get",
								"some logs by clicking on one of the trees in the area.",
								"Cut down a tree");
				player.getPacketSender().createArrow(3099, 3095, player.getH(), 2);
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 4) { // Skills tab
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"Here you will see how good your skills are. As you move your",
								"mouse over any of the icons in this panel, the small yellow",
								"popup box will show you the exact amount of experience you",
								"have and how much is needed to get to the next level.",
								"Your skill stats");
				player.tutorialProgress = 5;
				player.getPacketSender().chatbox(6179);
				player.getPacketSender().createArrow(1, 2);
			} else if (player.tutorialProgress == 9) { // Music tab
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"From this interface you can control the music that is played.",
								"As you explore the world, more of the tunes will become",
								"unlocked. Once you've examined this menu use the next door",
								"to continue. If you need a recap, talk to the Master Chef",
								"The music player");
				player.getPacketSender().createArrow(3073, 3090, player.getH(), 2);
				player.tutorialProgress = 10;
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 10) { // Emotes aNd running
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"For those situations where words don't quite describe how you",
								"feel, try an emote. Go ahead, try one out! You might notice",
								"that some of the emotes are grey and cannot be used now.",
								"As you progress further into the game you'll gain more.",
								"Emotes");
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 12) { // Quest Tab
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"",
								"This is your Quest Journal, a list of all the quests in the game.",
								"Talk to the Quest Guide again for an explaination.",
								"", "Your Quest Journal");
				player.tutorialProgress = 13;
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 21) { // Worn inventory
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"You can see what items you are wearing in the worn inventory",
								"to the left of the screen with their combined statistics on the",
								"right. Let's add something. Left click your dagger to 'wield' it.",
								"", "Worn interface");
				player.getPacketSender().chatbox(6179);
				player.tutorialProgress = 22;
			} else if (player.tutorialProgress == 23) { // Attack syle tabs
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"From this interface you can select the type of attack your",
								"character will use. Different monsters have different",
								"weaknesses. If you hover your mouse over the buttons, you",
								"will see the type of XP you will receive when using each attack.",
								"This is your combat interface");
				player.tutorialProgress = 24;
				player.getPacketSender().chatbox(6179);
				player.getItemAssistant()
						.sendWeapon(
								player.playerEquipment[player.playerWeapon],
								ItemAssistant
										.getItemName(player.playerEquipment[player.playerWeapon]));
				player.getPacketSender().createArrow(3111, 9518, player.getH(), 2);
			} else if (player.tutorialProgress == 29) { // Prayer
				player.getDialogueHandler().sendDialogues(3092, 222);
			} else if (player.tutorialProgress == 30) { // friends tab
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"This will be explaing by Brother Brace shortly, but first click",
								"on the other flashing face to the right of your screen.",
								"", "", "This is your friends list");
				player.getPacketSender().setSidebarInterface(9, 5715);
				player.getPacketSender().flashSideBarIcon(-9);
				player.tutorialProgress = 31;
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 31) { // ignores tab
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"The two lists - friends and ignore - can be very helpful for",
								"keeping track of when your friends are online or for blocking",
								"messages from people you simply don't like. Speak with",
								"Brother Brace and he will tell you more.",
								"This is your ignore list");
				player.getPacketSender().chatbox(6179);
			} else if (player.tutorialProgress == 32) { // Final magic tab
				player.tutorialProgress = 33;
				player.getDialogueHandler().sendDialogues(3108, 946);
			}
			break;

		}

	}

}
