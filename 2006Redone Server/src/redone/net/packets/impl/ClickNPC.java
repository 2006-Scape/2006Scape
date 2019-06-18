package redone.net.packets.impl;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.combat.magic.MagicData;
import redone.game.content.combat.range.RangeData;
import redone.game.content.skills.thieving.Pickpocket;
import redone.game.items.ItemAssistant;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {

	public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155,
			SECOND_CLICK = 17, THIRD_CLICK = 21;

	@Override
	public void processPacket(final Client client, int packetType, int packetSize) {
		client.npcIndex = 0;
		client.npcClickIndex = 0;
		client.playerIndex = 0;
		client.clickNpcType = 0;
		client.getPlayerAssistant().resetFollow();
		client.getCombatAssistant().resetPlayerAttack();
		client.getPlayerAssistant().requestUpdates();
		switch (packetType) {

		/**
		 * Attack npc melee or range
		 **/
		case ATTACK_NPC:
			if (client.tutorialProgress == 24) {
				client.getActionSender().chatbox(6180);
				client.getDialogueHandler()
						.chatboxText(
								client,
								"While you are fighting you will see a bar over your head. The",
								"bar shows how much health you have left. Your opponent will",
								"have one too. You will continue to attack the rat until it's dead",
								"or you do something else.",
								"Sit back and watch");
				client.getActionSender().chatbox(6179);

			}
			if (client.tutorialProgress == 33) {
				client.getActionSender()
						.sendMessage(
								"You can't range these chickens you have to mage them!");
				return;
			}
			if (!client.mageAllowed) {
				client.mageAllowed = true;
				client.getActionSender().sendMessage("I can't reach that.");
				break;
			}
			client.npcIndex = client.getInStream().readUnsignedWordA();
			if (NpcHandler.npcs[client.npcIndex] == null) {
				client.npcIndex = 0;
				break;
			}
			if (NpcHandler.npcs[client.npcIndex].MaxHP == 0) {
				client.npcIndex = 0;
				break;
			}
			if (NpcHandler.npcs[client.npcIndex] == null) {
				break;
			}
			if (client.autocastId > 0) {
				client.autocasting = true;
			}
			if (!client.autocasting && client.spellId > 0) {
				client.spellId = 0;
			}
			client.faceUpdate(client.npcIndex);
			client.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = client.playerEquipment[client.playerWeapon] == 9185;
			if (client.playerEquipment[client.playerWeapon] >= 4214
					&& client.playerEquipment[client.playerWeapon] <= 4223) {
				usingBow = true;
			}
			for (int bowId : RangeData.BOWS) {
				if (client.playerEquipment[client.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : RangeData.ARROWS) {
						if (client.playerEquipment[client.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
				if (client.playerEquipment[client.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			if ((usingBow || client.autocasting)
					&& client.goodDistance(client.getX(), client.getY(),
							NpcHandler.npcs[client.npcIndex].getX(),
							NpcHandler.npcs[client.npcIndex].getY(), 7)) {
				client.stopMovement();
			}

			if (usingOtherRangeWeapons
					&& client.goodDistance(client.getX(), client.getY(),
							NpcHandler.npcs[client.npcIndex].getX(),
							NpcHandler.npcs[client.npcIndex].getY(), 4)) {
				client.stopMovement();
			}
			if (!usingCross && !usingArrows && usingBow
					&& client.playerEquipment[client.playerWeapon] < 4212
					&& client.playerEquipment[client.playerWeapon] > 4223 && !usingCross) {
				client.getActionSender().sendMessage(
						"You have run out of arrows!");
				break;
			}
			if (RangeData.correctBowAndArrows(client) < client.playerEquipment[client.playerArrows]
					&& Constants.CORRECT_ARROWS
					&& usingBow
					&& !RangeData.usingCrystalBow(client)
					&& client.playerEquipment[client.playerWeapon] != 9185) {
				client.getActionSender().sendMessage(
						"You can't use "
								+ ItemAssistant.getItemName(
										client.playerEquipment[client.playerArrows])
										.toLowerCase()
								+ "s with a "
								+ ItemAssistant.getItemName(
										client.playerEquipment[client.playerWeapon])
										.toLowerCase() + ".");
				client.stopMovement();
				client.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (client.playerEquipment[client.playerWeapon] == 9185
					&& !client.getCombatAssistant().properBolts()) {
				client.getActionSender().sendMessage(
						"You must use bolts with a crossbow.");
				client.stopMovement();
				client.getCombatAssistant().resetPlayerAttack();
				return;
			}

			if (client.followId > 0) {
				client.getPlayerAssistant().resetFollow();
			}
			if (client.attackTimer <= 0) {
				client.getCombatAssistant().attackNpc(client.npcIndex);
				client.attackTimer++;
			}

			break;

		/**
		 * Attack npc with magic
		 **/
		case MAGE_NPC:
			if (client.tutorialProgress == 33) {
				client.getActionSender().chatbox(6180);
				client.getDialogueHandler()
						.chatboxText(
								client,
								"",
								"All you need to do is move on to the mainland. Just speak",
								"with Terrova and he'll teleport you to Lumbridge Castle.",
								"", "You have almost completed the tutorial!");
				client.getActionSender().chatbox(6179);
				// c.getDialogues().sendStatement4("You have almost completed the tutorial!",
				// "All you need to do is move on to the mainland. Just speak",
				// "with Terrova and he'll teleport you to Lumbridge.", "");
				client.tutorialProgress = 34;
				client.getActionSender().createArrow(1, 9);
			}
			if (!client.mageAllowed) {
				client.mageAllowed = true;
				client.getActionSender().sendMessage("I can't reach that.");
				break;
			}
			// c.usingSpecial = false;
			// c.getItems().updateSpecialBar();

			client.npcIndex = client.getInStream().readSignedWordBigEndianA();
			int castingSpellId = client.getInStream().readSignedWordA();
			client.usingMagic = false;

			if (NpcHandler.npcs[client.npcIndex] == null) {
				break;
			}

			if (NpcHandler.npcs[client.npcIndex].MaxHP == 0
					|| NpcHandler.npcs[client.npcIndex].npcType == 944) {
				client.getActionSender().sendMessage(
						"You can't attack this npc.");
				break;
			}

			for (int i = 0; i < MagicData.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == MagicData.MAGIC_SPELLS[i][0]) {
					client.spellId = i;
					client.usingMagic = true;
					break;
				}
			}

			if (client.autocasting) {
				client.autocasting = false;
			}

			if (client.usingMagic) {
				if (client.goodDistance(client.getX(), client.getY(),
						NpcHandler.npcs[client.npcIndex].getX(),
						NpcHandler.npcs[client.npcIndex].getY(), 6)) {
					client.stopMovement();
				}
				if (client.attackTimer <= 0) {
					client.getCombatAssistant().attackNpc(client.npcIndex);
					client.attackTimer++;
				}
			}

			break;

		case FIRST_CLICK:
			client.npcClickIndex = client.inStream.readSignedWordBigEndian();
			client.npcType = NpcHandler.npcs[client.npcClickIndex].npcType;
			if (client.goodDistance(NpcHandler.npcs[client.npcClickIndex].getX(),
					NpcHandler.npcs[client.npcClickIndex].getY(), client.getX(),
					client.getY(), 2)) {
				client.turnPlayerTo(NpcHandler.npcs[client.npcClickIndex].getX(),
						NpcHandler.npcs[client.npcClickIndex].getY());
				NpcHandler.npcs[client.npcClickIndex].facePlayer(client.playerId);
				client.getNpcs().firstClickNpc(client.npcType);
			} else {
				client.clickNpcType = 1;
				   CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (client.clickNpcType == 1
								&& NpcHandler.npcs[client.npcClickIndex] != null) {
							if (client.goodDistance(client.getX(), client.getY(),
									NpcHandler.npcs[client.npcClickIndex].getX(),
									NpcHandler.npcs[client.npcClickIndex].getY(), 1)) {
								client.turnPlayerTo(
										NpcHandler.npcs[client.npcClickIndex].getX(),
										NpcHandler.npcs[client.npcClickIndex].getY());
								NpcHandler.npcs[client.npcClickIndex]
										.facePlayer(client.playerId);
								client.getNpcs().firstClickNpc(client.npcType);
								container.stop();
							}
						}
						if (client.clickNpcType == 0 || client.clickNpcType > 1) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						client.clickNpcType = 0;
					}
				}, 1);
			}
			break;

		case SECOND_CLICK:
			client.npcClickIndex = client.inStream.readUnsignedWordBigEndianA();
			client.npcType = NpcHandler.npcs[client.npcClickIndex].npcType;
			if (client.goodDistance(NpcHandler.npcs[client.npcClickIndex].getX(),
					NpcHandler.npcs[client.npcClickIndex].getY(), client.getX(),
					client.getY(), 2)) {
				client.turnPlayerTo(NpcHandler.npcs[client.npcClickIndex].getX(),
						NpcHandler.npcs[client.npcClickIndex].getY());
				NpcHandler.npcs[client.npcClickIndex].facePlayer(client.playerId);
				client.getNpcs().secondClickNpc(client.npcType);
				if (Pickpocket.isNPC(client, client.npcType)) {
					Pickpocket.attemptPickpocket(client, client.npcType);
					return;
				}
			} else {
				client.clickNpcType = 2;
				   CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (client.clickNpcType == 2
								&& NpcHandler.npcs[client.npcClickIndex] != null) {
							if (client.goodDistance(client.getX(), client.getY(),
									NpcHandler.npcs[client.npcClickIndex].getX(),
									NpcHandler.npcs[client.npcClickIndex].getY(), 1)) {
								client.turnPlayerTo(
										NpcHandler.npcs[client.npcClickIndex].getX(),
										NpcHandler.npcs[client.npcClickIndex].getY());
								NpcHandler.npcs[client.npcClickIndex]
										.facePlayer(client.playerId);
								client.getNpcs().secondClickNpc(client.npcType);
								container.stop();
							}
						}
						if (client.clickNpcType < 2 || client.clickNpcType > 2) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						client.clickNpcType = 0;
					}
				}, 1);
			}
			break;

		case THIRD_CLICK:
			client.npcClickIndex = client.inStream.readSignedWord();
			client.npcType = NpcHandler.npcs[client.npcClickIndex].npcType;
			if (client.goodDistance(NpcHandler.npcs[client.npcClickIndex].getX(),
					NpcHandler.npcs[client.npcClickIndex].getY(), client.getX(),
					client.getY(), 2)) {
				client.turnPlayerTo(NpcHandler.npcs[client.npcClickIndex].getX(),
						NpcHandler.npcs[client.npcClickIndex].getY());
				NpcHandler.npcs[client.npcClickIndex].facePlayer(client.playerId);
				client.getNpcs().thirdClickNpc(client.npcType);
			} else {
				client.clickNpcType = 3;
				   CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (client.clickNpcType == 3
								&& NpcHandler.npcs[client.npcClickIndex] != null) {
							if (client.goodDistance(client.getX(), client.getY(),
									NpcHandler.npcs[client.npcClickIndex].getX(),
									NpcHandler.npcs[client.npcClickIndex].getY(), 1)) {
								client.turnPlayerTo(
										NpcHandler.npcs[client.npcClickIndex].getX(),
										NpcHandler.npcs[client.npcClickIndex].getY());
								NpcHandler.npcs[client.npcClickIndex]
										.facePlayer(client.playerId);
								client.getNpcs().thirdClickNpc(client.npcType);
								container.stop();
							}
						}
						if (client.clickNpcType < 3) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						client.clickNpcType = 0;
					}
				}, 1);
			}
			break;
		}

	}
}
