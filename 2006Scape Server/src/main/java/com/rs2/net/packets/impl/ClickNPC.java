package com.rs2.net.packets.impl;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.event.impl.NpcFirstClickEvent;
import com.rs2.event.impl.NpcSecondClickEvent;
import com.rs2.event.impl.NpcThirdClickEvent;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.range.RangeData;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {

	public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155,
			SECOND_CLICK = 17, THIRD_CLICK = 21;

	@Override
	public void processPacket(final Player player, Packet packet) {
		player.npcIndex = 0;
		player.npcClickIndex = 0;
		player.playerIndex = 0;
		player.clickNpcType = 0;
		player.getPlayerAssistant().resetFollow();
		player.getCombatAssistant().resetPlayerAttack();
		player.getPlayerAssistant().requestUpdates();
		player.endCurrentTask();
		switch (packet.getOpcode()) {

		/**
		 * Attack npc melee or range
		 **/
		case ATTACK_NPC:
			if (player.tutorialProgress == 24) {
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"While you are fighting you will see a bar over your head. The",
								"bar shows how much health you have left. Your opponent will",
								"have one too. You will continue to attack the rat until it's dead",
								"or you do something else.",
								"Sit back and watch");
				player.getPacketSender().chatbox(6179);

			}
			if (player.tutorialProgress == 33) {
				player.getPacketSender()
						.sendMessage(
								"You can't range these chickens you have to mage them!");
				return;
			}
			if (!player.mageAllowed) {
				player.mageAllowed = true;
				player.getPacketSender().sendMessage("I can't reach that.");
				break;
			}
			player.npcIndex = packet.readUnsignedWordA();
			if (NpcHandler.npcs[player.npcIndex] == null) {
				player.npcIndex = 0;
				break;
			}
			if (NpcHandler.npcs[player.npcIndex].MaxHP == 0) {
				player.npcIndex = 0;
				break;
			}
			if (NpcHandler.npcs[player.npcIndex] == null) {
				break;
			}
			if (player.autocastId > 0) {
				player.autocasting = true;
			}
			if (!player.autocasting && player.spellId > 0) {
				player.spellId = 0;
			}
			player.faceUpdate(player.npcIndex);
			player.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
			if (player.playerEquipment[player.playerWeapon] >= 4214
					&& player.playerEquipment[player.playerWeapon] <= 4223) {
				usingBow = true;
			}
			for (int bowId : RangeData.BOWS) {
				if (player.playerEquipment[player.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : RangeData.ARROWS) {
						if (player.playerEquipment[player.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
				if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			if ((usingBow || player.autocasting)
					&& player.goodDistance(player.getX(), player.getY(),
							NpcHandler.npcs[player.npcIndex].getX(),
							NpcHandler.npcs[player.npcIndex].getY(), 7)) {
				player.stopMovement();
			}

			if (usingOtherRangeWeapons
					&& player.goodDistance(player.getX(), player.getY(),
							NpcHandler.npcs[player.npcIndex].getX(),
							NpcHandler.npcs[player.npcIndex].getY(), 4)) {
				player.stopMovement();
			}
			if (!usingCross && !usingArrows && usingBow
					&& player.playerEquipment[player.playerWeapon] < 4212
					&& player.playerEquipment[player.playerWeapon] > 4223 && !usingCross) {
				player.getPacketSender().sendMessage(
						"You have run out of arrows!");
				break;
			}
			if (RangeData.correctBowAndArrows(player) < player.playerEquipment[player.playerArrows]
					&& CombatConstants.CORRECT_ARROWS
					&& usingBow
					&& !RangeData.usingCrystalBow(player)
					&& player.playerEquipment[player.playerWeapon] != 9185) {
				player.getPacketSender().sendMessage(
						"You can't use "
								+ DeprecatedItems.getItemName(
										player.playerEquipment[player.playerArrows])
										.toLowerCase()
								+ "s with a "
								+ DeprecatedItems.getItemName(
										player.playerEquipment[player.playerWeapon])
										.toLowerCase() + ".");
				player.stopMovement();
				player.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (player.playerEquipment[player.playerWeapon] == 9185
					&& !player.getCombatAssistant().properBolts()) {
				player.getPacketSender().sendMessage(
						"You must use bolts with a crossbow.");
				player.stopMovement();
				player.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (!player.usingRangeWeapon && !RangeData.usingHally(player) && !player.usingBow && !player.usingMagic && player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[player.npcIndex].getX(), NpcHandler.npcs[player.npcIndex].getY(), NPCDefinition.forId(NpcHandler.npcs[player.npcIndex].npcType).getSize())) {
				System.out.println("distance good! stop movement 1");
				player.stopMovement();
			}
			if (player.followId > 0) {
				player.getPlayerAssistant().resetFollow();
			}
			if (player.attackTimer <= 0) {
				player.getCombatAssistant().attackNpc(player.npcIndex);
				player.attackTimer++;
			}

			break;

		/**
		 * Attack npc with magic
		 **/
		case MAGE_NPC:
			if (player.tutorialProgress == 33) {
				player.getPacketSender().chatbox(6180);
				player.getDialogueHandler()
						.chatboxText(
								"",
								"All you need to do is move on to the mainland. Just speak",
								"with Terrova and he'll teleport you to Lumbridge Castle.",
								"", "You have almost completed the tutorial!");
				player.getPacketSender().chatbox(6179);
				// c.getDialogues().sendStatement4("You have almost completed the tutorial!",
				// "All you need to do is move on to the mainland. Just speak",
				// "with Terrova and he'll teleport you to Lumbridge.", "");
				player.tutorialProgress = 34;
				player.getPacketSender().createArrow(1, 9);
			}
			if (!player.mageAllowed) {
				player.mageAllowed = true;
				player.getPacketSender().sendMessage("I can't reach that.");
				break;
			}
			// c.usingSpecial = false;
			// c.getItems().updateSpecialBar();

			player.npcIndex = packet.readSignedWordBigEndianA();
			int castingSpellId = packet.readSignedWordA();
			player.usingMagic = false;

			if (NpcHandler.npcs[player.npcIndex] == null) {
				break;
			}

			if (NpcHandler.npcs[player.npcIndex].MaxHP == 0
					|| NpcHandler.npcs[player.npcIndex].npcType == 944) {
				player.getPacketSender().sendMessage(
						"You can't attack this npc.");
				break;
			}

			for (int i = 0; i < MagicData.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == MagicData.MAGIC_SPELLS[i][0]) {
					player.spellId = i;
					player.usingMagic = true;
					break;
				}
			}

			if (player.autocasting) {
				player.autocasting = false;
			}

			if (player.usingMagic) {
				if (player.goodDistance(player.getX(), player.getY(),
						NpcHandler.npcs[player.npcIndex].getX(),
						NpcHandler.npcs[player.npcIndex].getY(), 6)) {
					player.stopMovement();
				}
				if (player.attackTimer <= 0) {
					player.getCombatAssistant().attackNpc(player.npcIndex);
					player.attackTimer++;
				}
			}

			break;

		case FIRST_CLICK:
			player.npcClickIndex = packet.readSignedWordBigEndian();
			player.npcType = NpcHandler.npcs[player.npcClickIndex].npcType;

			if (player.goodDistance(NpcHandler.npcs[player.npcClickIndex].getX(),
					NpcHandler.npcs[player.npcClickIndex].getY(), player.getX(),
					player.getY(), 2)) {
				player.turnPlayerTo(NpcHandler.npcs[player.npcClickIndex].getX(),
						NpcHandler.npcs[player.npcClickIndex].getY());
				NpcHandler.npcs[player.npcClickIndex].facePlayer(player);
				player.getNpcs().firstClickNpc(player.npcType);
				player.post(new NpcFirstClickEvent(player.npcType));
			} else {
				player.clickNpcType = 1;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickNpcType == 1
								&& NpcHandler.npcs[player.npcClickIndex] != null) {
							if (player.goodDistance(player.getX(), player.getY(),
									NpcHandler.npcs[player.npcClickIndex].getX(),
									NpcHandler.npcs[player.npcClickIndex].getY(), 1)) {
								player.turnPlayerTo(
										NpcHandler.npcs[player.npcClickIndex].getX(),
										NpcHandler.npcs[player.npcClickIndex].getY());
								NpcHandler.npcs[player.npcClickIndex]
										.facePlayer(player);
								player.getNpcs().firstClickNpc(player.npcType);
								player.post(new NpcFirstClickEvent(player.npcType));
								container.stop();
							}
						}
						if (player.clickNpcType == 0 || player.clickNpcType > 1) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickNpcType = 0;
					}
				}, 1);
			}
			break;
		case SECOND_CLICK:
			player.npcClickIndex = packet.readUnsignedWordBigEndianA();
			player.npcType = NpcHandler.npcs[player.npcClickIndex].npcType;
			if (player.goodDistance(NpcHandler.npcs[player.npcClickIndex].getX(),
					NpcHandler.npcs[player.npcClickIndex].getY(), player.getX(),
					player.getY(), 2)) {
				player.turnPlayerTo(NpcHandler.npcs[player.npcClickIndex].getX(),
						NpcHandler.npcs[player.npcClickIndex].getY());
				NpcHandler.npcs[player.npcClickIndex].facePlayer(player);
				player.getNpcs().secondClickNpc(player.npcType);
				player.post(new NpcSecondClickEvent(player.npcType));
			} else {
				player.clickNpcType = 2;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickNpcType == 2
								&& NpcHandler.npcs[player.npcClickIndex] != null) {
							if (player.goodDistance(player.getX(), player.getY(),
									NpcHandler.npcs[player.npcClickIndex].getX(),
									NpcHandler.npcs[player.npcClickIndex].getY(), 1)) {
								player.turnPlayerTo(
										NpcHandler.npcs[player.npcClickIndex].getX(),
										NpcHandler.npcs[player.npcClickIndex].getY());
								NpcHandler.npcs[player.npcClickIndex]
										.facePlayer(player);
								player.getNpcs().secondClickNpc(player.npcType);
								player.post(new NpcSecondClickEvent(player.npcType));
								container.stop();
							}
						}
						if (player.clickNpcType < 2 || player.clickNpcType > 2) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickNpcType = 0;
					}
				}, 1);
			}
			break;

		case THIRD_CLICK:
			player.npcClickIndex = packet.readSignedWord();
			player.npcType = NpcHandler.npcs[player.npcClickIndex].npcType;
			if (player.goodDistance(NpcHandler.npcs[player.npcClickIndex].getX(),
					NpcHandler.npcs[player.npcClickIndex].getY(), player.getX(),
					player.getY(), 2)) {
				player.turnPlayerTo(NpcHandler.npcs[player.npcClickIndex].getX(),
						NpcHandler.npcs[player.npcClickIndex].getY());
				NpcHandler.npcs[player.npcClickIndex].facePlayer(player);
				player.getNpcs().thirdClickNpc(player.npcType);
				player.post(new NpcThirdClickEvent(player.npcType));
			} else {
				player.clickNpcType = 3;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickNpcType == 3
								&& NpcHandler.npcs[player.npcClickIndex] != null) {
							if (player.goodDistance(player.getX(), player.getY(),
									NpcHandler.npcs[player.npcClickIndex].getX(),
									NpcHandler.npcs[player.npcClickIndex].getY(), 1)) {
								player.turnPlayerTo(
										NpcHandler.npcs[player.npcClickIndex].getX(),
										NpcHandler.npcs[player.npcClickIndex].getY());
								NpcHandler.npcs[player.npcClickIndex]
										.facePlayer(player);
								player.getNpcs().thirdClickNpc(player.npcType);
								player.post(new NpcThirdClickEvent(player.npcType));
								container.stop();
							}
						}
						if (player.clickNpcType < 3) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickNpcType = 0;
					}
				}, 1);
			}
			break;
		}

	}
}
