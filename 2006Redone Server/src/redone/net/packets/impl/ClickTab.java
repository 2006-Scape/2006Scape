package redone.net.packets.impl;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.net.packets.PacketType;

public class ClickTab implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		switch (packetSize) {
		case 1:// first part.
			if (c.tutorialProgress == 0) { // wrench
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"On the side panel you can now see a variety of options from",
								"changing your graphic settings and audio and music volume",
								"to selecting whether your player should accept help from",
								"other players. Don't worry about these too much for now.",
								"@blu@Player controls");
				c.getActionSender().chatbox(6179);
				c.tutorialProgress = 1;
			} else if (c.tutorialProgress == 3) { // backpack
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"You can click on the backpack icon at any time to view the",
								"items that you currently have in your inventory. You will see",
								"that you now have an axe in your inventory. Use this to get",
								"some logs by clicking on one of the trees in the area.",
								"Cut down a tree");
				c.getActionSender().createArrow(3099, 3095, c.getH(), 2);
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 4) { // Skills tab
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"Here you will see how good your skills are. As you move your",
								"mouse over any of the icons in this panel, the small yellow",
								"popup box will show you the exact amount of experience you",
								"have and how much is needed to get to the next level.",
								"Your skill stats");
				c.tutorialProgress = 5;
				c.getActionSender().chatbox(6179);
				c.getActionSender().createArrow(1, 2);
			} else if (c.tutorialProgress == 9) { // Music tab
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"From this interface you can control the music that is played.",
								"As you explore the world, more of the tunes will become",
								"unlocked. Once you've examined this menu use the next door",
								"to continue. If you need a recap, talk to the Master Chef",
								"The music player");
				c.getActionSender().createArrow(3073, 3090, c.getH(), 2);
				c.tutorialProgress = 10;
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 10) { // Emotes aNd running
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"For those situations where words don't quite describe how you",
								"feel, try an emote. Go ahead, try one out! You might notice",
								"that some of the emotes are grey and cannot be used now.",
								"As you progress further into the game you'll gain more.",
								"Emotes");
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 12) { // Quest Tab
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"",
								"This is your Quest Journal, a list of all the quests in the game.",
								"Talk to the Quest Guide again for an explaination.",
								"", "Your Quest Journal");
				c.tutorialProgress = 13;
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 21) { // Worn inventory
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"You can see what items you are wearing in the worn inventory",
								"to the left of the screen with their combined statistics on the",
								"right. Let's add something. Left click your dagger to 'wield' it.",
								"", "Worn interface");
				c.getActionSender().chatbox(6179);
				c.tutorialProgress = 22;
			} else if (c.tutorialProgress == 23) { // Attack syle tabs
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"From this interface you can select the type of attack your",
								"character will use. Different monsters have different",
								"weaknesses. If you hover your mouse over the buttons, you",
								"will see the type of XP you will receive when using each attack.",
								"This is your combat interface");
				c.tutorialProgress = 24;
				c.getActionSender().chatbox(6179);
				c.getItemAssistant()
						.sendWeapon(
								c.playerEquipment[c.playerWeapon],
								ItemAssistant
										.getItemName(c.playerEquipment[c.playerWeapon]));
				c.getActionSender().createArrow(3111, 9518, c.getH(), 2);
			} else if (c.tutorialProgress == 29) { // Prayer
				c.getDialogueHandler().sendDialogues(3092, 222);
			} else if (c.tutorialProgress == 30) { // friends tab
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"This will be explaing by Brother Brace shortly, but first click",
								"on the other flashing face to the right of your screen.",
								"", "", "This is your friends list");
				c.getActionSender().setSidebarInterface(9, 5715);
				c.getActionSender().flashSideBarIcon(-9);
				c.tutorialProgress = 31;
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 31) { // ignores tab
				c.getActionSender().chatbox(6180);
				c.getDialogueHandler()
						.chatboxText(
								c,
								"The two lists - friends and ignore - can be very helpful for",
								"keeping track of when your friends are online or for blocking",
								"messages from people you simply don't like. Speak with",
								"Brother Brace and he will tell you more.",
								"This is your ignore list");
				c.getActionSender().chatbox(6179);
			} else if (c.tutorialProgress == 32) { // Final magic tab
				c.tutorialProgress = 33;
				c.getDialogueHandler().sendDialogues(3108, 946);
			}
			break;

		}

	}

}
