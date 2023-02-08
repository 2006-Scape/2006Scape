package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.event.impl.ButtonActionEvent;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.Specials;
import com.rs2.game.content.combat.magic.*;
import com.rs2.game.content.combat.prayer.ActivatePrayers;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.quests.QuestAssistant;
import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.content.randomevents.SandwhichLady;
import com.rs2.game.content.skills.cooking.Cooking;
import com.rs2.game.content.skills.cooking.CookingTutorialIsland;
import com.rs2.game.content.skills.cooking.DairyChurn;
import com.rs2.game.content.skills.crafting.LeatherMaking;
import com.rs2.game.content.skills.crafting.Pottery;
import com.rs2.game.content.skills.crafting.Spinning;
import com.rs2.game.content.skills.crafting.Tanning;
import com.rs2.game.content.skills.crafting.CraftingData.tanningData;
import com.rs2.game.content.skills.fletching.LogCutting;
import com.rs2.game.content.skills.herblore.Herblore;
import com.rs2.game.content.skills.smithing.SilverCrafting;
import com.rs2.game.content.skills.smithing.Smelting;
import com.rs2.game.content.traveling.GnomeGlider;
import com.rs2.game.dialogues.DialogueOptions;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.GameItem;
import com.rs2.game.items.impl.EnchantStaff;
import com.rs2.game.items.impl.ExperienceLamp;
import com.rs2.game.objects.impl.Climbing;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.Boundary;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Player player, Packet packet) {
		int actionButtonId = packet.readHex();
		player.getGlassBlowing().handleActionButtin(actionButtonId);
		GnomeGlider.flightButtons(player, actionButtonId);
		player.getEmoteHandler().startEmote(actionButtonId);
		QuestAssistant.questButtons(player, actionButtonId);
		LogCutting.handleClick(player, actionButtonId);
		Smelting.getBar(player, actionButtonId);
		ExperienceLamp.buttons(player, actionButtonId);
		Herblore.handleHerbloreButtons(player, actionButtonId);
		LeatherMaking.craftLeather(player, actionButtonId);
		SandwhichLady.handleOptions(player, actionButtonId);
		SilverCrafting.makeSilver(player, actionButtonId, 0);
		Climbing.handleLadderButtons(player, actionButtonId);
		Specials.specialClicking(player, actionButtonId);
		DialogueOptions.handleDialogueOptions(player, actionButtonId);
		DairyChurn.churnItem(player, actionButtonId);
		if (EnchantStaff.staffButtons(player, actionButtonId)) {
			return;
		}
		if (player.musicOn) {
			player.getPlayList().handleButton(actionButtonId);
		}

		for (tanningData t : tanningData.values()) {
			if (actionButtonId == t.getButtonId(actionButtonId)) {
				Tanning.tanHide(player, actionButtonId);
			}
		}
		if (player.isDead) {
			return;
		}
		if (player.playerRights == 3) {
			player.getPacketSender().sendMessage(player.playerName + " - actionbutton: " + actionButtonId);
		}
		if (player.isAutoButton(actionButtonId)) {
			player.assignAutocast(actionButtonId);
		}
		player.post(new ButtonActionEvent(actionButtonId));

		switch (actionButtonId) {
			case 23132:
				player.getPlayerAssistant().unMorphPlayer();
			break;

		case 55096:
			player.getPacketSender().closeAllWindows();
            player.droppedItem = -1;
		break;
		
		case 55095:
			player.getItemAssistant().destroyItem(player.droppedItem);
            player.droppedItem = -1;
			break;


			/**
			 * Spellbook Teleports
			 */

			case 50235:
			case 50245:
			case 50253:
			case 51005:
			case 51013:
			case 51023:
			case 51031:
			case 51039:
			case 4140:
			case 4143:
			case 4146:
			case 4150:
			case 6004:
			case 6005:
			case 29031:
			case 72038:
				MagicTeleports.handleSpellTeleport(player, SpellTeleport.forButtonId(actionButtonId));
				break;


		case 4135:
			if (System.currentTimeMillis() - player.boneDelay < 2000) {
				return;
			}
			if (player.inTrade) {
				player.getPacketSender().sendMessage(
						"You can't do this in trade!");
				return;
			}
			if (player.playerLevel[Constants.MAGIC] < 15) {
				player.getPacketSender().sendString(
								"You need a magic level of @blu@15 @bla@to cast bones to bananas",
								357);
				player.getPacketSender().sendChatInterface(356);
				return;
			}
			if (Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
				player.getMageTrainingArena().graveyard.bonesToFood(52);
				return;
			}
			if (!player.getItemAssistant().playerHasItem(526, 1)) {
				player.getPacketSender().sendMessage("You don't have any bones!");
				return;
			}
			if (!player.getItemAssistant().playerHasItem(561, 1)
					|| !player.getItemAssistant().playerHasItem(555, 2)
					|| !player.getItemAssistant().playerHasItem(557, 2)) {
				player.getPacketSender().sendString("You do not have the correct runes to cast this spell.", 357);
				player.getPacketSender().sendChatInterface(356);
				return;
			}
			if (System.currentTimeMillis() - player.boneDelay > 2000) {
				player.getItemAssistant().deleteItem(561, 1);
				player.getItemAssistant().deleteItem(557, 2);
				player.getItemAssistant().deleteItem(555, 2);
				player.getPlayerAssistant().addSkillXP(25, 6);
				player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
				player.startAnimation(722);
				player.gfx100(141);
				player.getPacketSender().sendShowTab(6);
				player.getPacketSender().sendSound(SoundList.BONES_TO_BANNAS, 100, 0);
				player.boneDelay = System.currentTimeMillis();
				do {
					player.getItemAssistant().replaceItem(526, 1963);
				} while (player.getItemAssistant().playerHasItem(526, 1));
			}
			break;

		case 62005:
			if (System.currentTimeMillis() - player.boneDelay < 2000) {
				return;
			}
			if (player.inTrade) {
				player.getPacketSender().sendMessage(
						"You can't do this in trade!");
				return;
			}
			if (player.playerLevel[Constants.MAGIC] < 60) {
				player.getPacketSender().sendString("You need a magic level of @blu@60  @blu@ to cast bones to peaches.", 357);
				player.getPacketSender().sendChatInterface(356);
				return;
			}
			if (!player.unlockedBonesToPeaches) {
				player.getPacketSender().sendString("You haven't unlocked this spell yet.", 357);
				player.getPacketSender().sendChatInterface(356);
				return;
			}
			if (Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
				player.getMageTrainingArena().graveyard.bonesToFood(53);
				return;
			}
			if (!player.getItemAssistant().playerHasItem(526, 1)) {
				player.getPacketSender().sendMessage("You don't have any bones!");
				return;
			}
			if (!player.getItemAssistant().playerHasItem(561, 2)
					|| !player.getItemAssistant().playerHasItem(555, 4)
					|| !player.getItemAssistant().playerHasItem(557, 4)) {
				player.getPacketSender().sendString(
								"You do not have the correct runes to cast this spell.",
								357);
				player.getPacketSender().sendChatInterface(356);
				return;
			}
			if (System.currentTimeMillis() - player.boneDelay > 2000) {
				player.getItemAssistant().deleteItem(561, 2);
				player.getItemAssistant().deleteItem(557, 4);
				player.getItemAssistant().deleteItem(555, 4);
				player.getPlayerAssistant().addSkillXP(35.5, 6);
				player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
				player.startAnimation(722);
				player.gfx100(311);
				player.getPacketSender().sendShowTab(6);
				player.boneDelay = System.currentTimeMillis();
				do {
					player.getItemAssistant().replaceItem(526, 6883);
				} while (player.getItemAssistant().playerHasItem(526, 1));
			}
			break;

		case 14067:
			player.canChangeAppearance = false;
			break;

		case 34185:
		case 34193:
		case 34189:
			if (player.clickedSpinning) {
				Spinning.getAmount(player, 1);
			}
			break;

		case 34184:
		case 34188:
		case 34192:
			if (player.clickedSpinning) {
				Spinning.getAmount(player, 5);
			}
			break;

		case 34183:
		case 34187:
		case 34191:
			if (player.clickedSpinning) {
				Spinning.getAmount(player, 10);
			}
			break;

		case 34182:
		case 34186:
		case 34190:
			if (player.clickedSpinning) {
				Spinning.getAmount(player, 28);
			}
			break;

		/*
		 * Item on interface 5
		 */
		// item 1
		case 34245:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 1);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 1);
			}
			break;
		case 34244:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 5);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 5);
			}
			break;
		case 34243:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 10);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 10);
			}
			break;
		case 34242:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 28);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 28);
			}
			break;
		// item 2
		case 34249:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1789, 15, 7, 1);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 1);
			}
			break;
		case 34248:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1789, 15, 7, 5);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 5);
			}
			break;
		case 34247:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1789, 15, 7, 10);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 10);
			}
			break;
		case 34246:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1789, 15, 7, 28);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 28);
			}
			break;
		// item 3
		case 34253:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1791, 18, 8, 1);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 1);
			}
			break;
		case 34252:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1791, 18, 8, 5);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 5);
			}
			break;
		case 34251:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1791, 18, 8, 10);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 10);
			}
			break;
		case 34250:
			if (player.showedUnfire) {
				Pottery.makeUnfire(player, 1791, 18, 8, 28);
			}
			if (player.showedFire) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 28);
			}
			break;

		case 9118:
			player.getPacketSender().closeAllWindows();
			break;

		case 49022:
			CastOnOther.teleOtherLocation(player, player.teleotherType, false);
			break;

		case 49024:
			CastOnOther.teleOtherLocation(player, player.teleotherType, true);
			break;

		case 8100:
			player.playerAppearance[7] = 11; // beard 11: long
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8101:
			player.playerAppearance[7] = 10; // beard 10: goatee
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8102:
			player.playerAppearance[7] = 13; // beard 13: mustache
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8103:
			player.playerAppearance[7] = 15; // beard 15: Chin strap
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8104:
			player.playerAppearance[7] = 17; // beard 17: Barbarian beard?
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8105:
			player.playerAppearance[7] = 12; // beard 12: Egyptian beard?
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8106:
			player.playerAppearance[7] = 14; // beard 14: Clean shaven
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8107:
			player.playerAppearance[7] = 16; // beard 16: Goatee + Chin
												// strap
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8088:
			player.playerAppearance[8] = 0; // hair/beard color: Dark-brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8089:
			player.playerAppearance[8] = 1; // hair/beard color: White
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8090:
			player.playerAppearance[8] = 2; // hair/beard color: Gray
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8091:
			player.playerAppearance[8] = 3; // hair/beard color: Black
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8092:
			player.playerAppearance[8] = 4; // hair/beard color: Orange
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8093:
			player.playerAppearance[8] = 5; // hair/beard color: Blonde
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8094:
			player.playerAppearance[8] = 6; // hair/beard color: Light-brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8095:
			player.playerAppearance[8] = 7; // hair/beard color: Brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8096:
			player.playerAppearance[8] = 8; // hair/beard color: Cyan
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8097:
			player.playerAppearance[8] = 9; // hair/beard color: Green
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8098:
			player.playerAppearance[8] = 10; // hair/beard color: Red
			player.getPlayerAssistant().requestUpdates();
			break;

		case 8099:
			player.playerAppearance[8] = 11; // hair/beard color: Pink
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10229:
			player.playerAppearance[1] = 0; // 0: Bald
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10230:
			player.playerAppearance[1] = 1; // 1: Dreadlocks
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10231:
			player.playerAppearance[1] = 2; // 2: Long hair
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10232:
			player.playerAppearance[1] = 3; // 3: Medium hair
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10233:
			player.playerAppearance[1] = 4; // 4: Monk
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10234:
			player.playerAppearance[1] = 5; // 5: Comb-over
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10235:
			player.playerAppearance[1] = 6; // 6: Close-cropped
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10236:
			player.playerAppearance[1] = 7; // Wild spikes
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10237:
			player.playerAppearance[1] = 8; // Spikes
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10217:
			player.playerAppearance[8] = 0; // hair/beard color: Dark-brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10218:
			player.playerAppearance[8] = 1; // hair/beard color: White
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10219:
			player.playerAppearance[8] = 2; // hair/beard color: Gray
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10220:
			player.playerAppearance[8] = 3; // hair/beard color: Black
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10221:
			player.playerAppearance[8] = 4; // hair/beard color: Orange
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10222:
			player.playerAppearance[8] = 5; // hair/beard color: Blonde
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10223:
			player.playerAppearance[8] = 6; // hair/beard color: Light-brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10224:
			player.playerAppearance[8] = 7; // hair/beard color: Brown
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10225:
			player.playerAppearance[8] = 8; // hair/beard color: Cyan
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10226:
			player.playerAppearance[8] = 9; // hair/beard color: Green
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10227:
			player.playerAppearance[8] = 10; // hair/beard color: Red
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10228:
			player.playerAppearance[8] = 11; // hair/beard color: Pink
			player.getPlayerAssistant().requestUpdates();
			break;

		case 10193:
			player.getItemAssistant().deleteItem(995, 2000);
			player.getPacketSender().closeAllWindows();
			break;

		case 8065:
			player.getItemAssistant().deleteItem(995, 2000);
			player.getPacketSender().closeAllWindows();
			break;
		/** End of Hairdresser buttons */

		case 8198:
			PartyRoom.accept(player);
			break;

		case 53152:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 1);
			} else {
				Cooking.cookItem(player, player.cookingItem, 1, player.cookingObject);
			}
			break;

		case 53151:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 5);
			} else {
				Cooking.cookItem(player, player.cookingItem, 5, player.cookingObject);
			}
			break;

		case 53150:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 10);
			} else {
				player.playerIsCooking = true;
				player.getOutStream().createFrame(27);
			}
			break;

		case 53149:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 28);
			} else {
				Cooking.cookItem(player, player.cookingItem, 28, player.cookingObject);
			}
			break;

		case 58074:
			player.getBankPin().closeBankPin();
			break;

		case 58073:
			if (player.hasBankpin && !player.requestPinDelete) {
				player.requestPinDelete = true;
				player.getBankPin().dateRequested();
				player.getBankPin().dateExpired();
				player.getDialogueHandler().sendDialogues(1017, 1);
				player.getPacketSender()
						.sendMessage(
								"[Notice] A PIN delete has been requested. Your PIN will be deleted in "
										+ player.getBankPin().recovery_Delay
										+ " days.");
				player.getPacketSender().sendMessage(
						"To cancel this change just type in the correct PIN.");
			} else {
				player.getPacketSender()
						.sendMessage(
								"[Notice] Your PIN is already pending deletion. Please wait the entire 2 days.");
				player.getPacketSender().closeAllWindows();
			}
			break;

		case 58025:
		case 58026:
		case 58027:
		case 58028:
		case 58029:
		case 58030:
		case 58031:
		case 58032:
		case 58033:
		case 58034:
			player.getBankPin().bankPinEnter(actionButtonId);
			break;

		case 58230:
			if (!player.hasBankpin) {
				player.getBankPin().openPin();
			} else if (player.hasBankpin && player.enterdBankpin) {
				player.getBankPin().resetBankPin();
				player.getPacketSender().sendMessage(
						"Your PIN has been deleted as requested.");
			} else {
				player.getPacketSender()
						.sendMessage(
								"Please enter your Bank Pin before requesting a delete.");
				player.getPacketSender()
						.sendMessage(
								"You can do this by simply opening your bank. This is to verify it's really you.");
				player.getPacketSender().closeAllWindows();
			}
			break;

		case 34142: // tab 1
			player.getSkillInterfaces().menuCompilation(1);
			break;

		case 34119: // tab 2
			player.getSkillInterfaces().menuCompilation(2);
			break;

		case 34120: // tab 3
			player.getSkillInterfaces().menuCompilation(3);
			break;

		case 34123: // tab 4
			player.getSkillInterfaces().menuCompilation(4);
			break;

		case 34133: // tab 5
			player.getSkillInterfaces().menuCompilation(5);
			break;

		case 34136: // tab 6
			player.getSkillInterfaces().menuCompilation(6);
			break;

		case 34139: // tab 7
			player.getSkillInterfaces().menuCompilation(7);
			break;

		case 34155: // tab 8
			player.getSkillInterfaces().menuCompilation(8);
			break;

		case 34158: // tab 9
			player.getSkillInterfaces().menuCompilation(9);
			break;

		case 34161: // tab 10
			player.getSkillInterfaces().menuCompilation(10);
			break;

		case 59199: // tab 11
			player.getSkillInterfaces().menuCompilation(11);
			break;

		case 59202: // tab 12
			player.getSkillInterfaces().menuCompilation(12);
			break;
		case 59203: // tab 13
			player.getSkillInterfaces().menuCompilation(13);
			break;

		case 33206: // attack
			player.getSkillInterfaces().attackComplex(1);
			player.getSkillInterfaces().selected = 0;
			break;
		case 33209: // strength
			player.getSkillInterfaces().strengthComplex(1);
			player.getSkillInterfaces().selected = 1;
			break;
		case 33212: // Defence
			player.getSkillInterfaces().defenceComplex(1);
			player.getSkillInterfaces().selected = 2;
			break;
		case 33215: // range
			player.getSkillInterfaces().rangedComplex(1);
			player.getSkillInterfaces().selected = 3;
			break;
		case 33218: // prayer
			player.getSkillInterfaces().prayerComplex(1);
			player.getSkillInterfaces().selected = 4;
			break;
		case 33221: // mage
			player.getSkillInterfaces().magicComplex(1);
			player.getSkillInterfaces().selected = 5;
			break;
		case 33224: // runecrafting
			player.getSkillInterfaces().runecraftingComplex(1);
			player.getSkillInterfaces().selected = 6;
			break;
		case 33207: // hp
			player.getSkillInterfaces().hitpointsComplex(1);
			player.getSkillInterfaces().selected = 7;
			break;
		case 33210: // agility
			player.getSkillInterfaces().agilityComplex(1);
			player.getSkillInterfaces().selected = 8;
			break;
		case 33213: // herblore
			player.getSkillInterfaces().herbloreComplex(1);
			player.getSkillInterfaces().selected = 9;
			break;
		case 33216: // theiving
			player.getSkillInterfaces().thievingComplex(1);
			player.getSkillInterfaces().selected = 10;
			break;
		case 33219: // crafting
			player.getSkillInterfaces().craftingComplex(1);
			player.getSkillInterfaces().selected = 11;
			break;
		case 33222: // fletching
			player.getSkillInterfaces().fletchingComplex(1);
			player.getSkillInterfaces().selected = 12;
			break;
		case 47130:// slayer
			player.getSkillInterfaces().slayerComplex(1);
			player.getSkillInterfaces().selected = 13;
			break;
		case 33208: // mining
			player.getSkillInterfaces().miningComplex(1);
			player.getSkillInterfaces().selected = 14;
			break;
		case 33211: // smithing
			player.getSkillInterfaces().smithingComplex(1);
			player.getSkillInterfaces().selected = 15;
			break;
		case 33214: // fishing
			player.getSkillInterfaces().fishingComplex(1);
			player.getSkillInterfaces().selected = 16;
			break;
		case 33217: // cooking
			player.getSkillInterfaces().cookingComplex(1);
			player.getSkillInterfaces().selected = 17;
			break;
		case 33220: // firemaking
			player.getSkillInterfaces().firemakingComplex(1);
			player.getSkillInterfaces().selected = 18;
			break;
		case 33223: // woodcut
			player.getSkillInterfaces().woodcuttingComplex(1);
			player.getSkillInterfaces().selected = 19;
			break;
		case 54104: // farming
			player.getSkillInterfaces().farmingComplex(1);
			player.getSkillInterfaces().selected = 20;
			break;
			
		case 151:
		if (player.autoRet == 1) {
			player.autoRet = 0;
			player.getPacketSender().sendConfig(172, 1);
		} else {
			player.getPacketSender().sendMessage("Your auto retaliate is already turned off.");
		}
		break;
		
		case 150:
		if (player.autoRet == 0) {
			player.autoRet = 1;
			player.getPacketSender().sendConfig(172, 0);
		} else {
			player.getPacketSender().sendMessage("Your auto retaliate is already turned on.");
		}
		break;

		// 1st tele option
		case 9190:
			if (player.dialogueAction == 10) {
				player.getPlayerAssistant().spellTeleport(2845, 4832, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 11) {
				player.getPlayerAssistant().spellTeleport(2786, 4839, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 12) {
				player.getPlayerAssistant().spellTeleport(2398, 4841, 0);
				player.dialogueAction = -1;
			}
			break;

		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (player.dialogueAction == 10) {
				player.getPlayerAssistant().spellTeleport(2796, 4818, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 11) {
				player.getPlayerAssistant().spellTeleport(2527, 4833, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 12) {
				player.getPlayerAssistant().spellTeleport(2464, 4834, 0);
				player.dialogueAction = -1;
			}
			break;
		// 3rd tele option

		case 9192:
			if (player.dialogueAction == 10) {
				player.getPlayerAssistant().spellTeleport(2713, 4836, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 11) {
				player.getPlayerAssistant().spellTeleport(2162, 4833, 0);
				player.dialogueAction = -1;
			} else if (player.dialogueAction == 12) {
				player.getPlayerAssistant().spellTeleport(2207, 4836, 0);
				player.dialogueAction = -1;
			}
			break;
		// 4th tele option
		case 9193:
			if (player.dialogueAction == 10) {
				player.getPlayerAssistant().spellTeleport(2660, 4839, 0);
				player.dialogueAction = -1;
			}
			break;
		// 5th tele option
		case 9194:
			if (player.dialogueAction == 10 || player.dialogueAction == 11) {
				player.dialogueId++;
				player.getDialogueHandler().sendDialogues(player.dialogueId, 0);
			} else if (player.dialogueAction == 12) {
				player.dialogueId = 17;
				player.getDialogueHandler().sendDialogues(player.dialogueId, 0);
			}
			break;

		case 58253:
			// c.getPA().showInterface(15106);
			player.getItemAssistant().writeBonus();
			break;

		case 59004:
			player.getPacketSender().closeAllWindows();
			break;

		case 1093:
		case 1094:
		case 1097:
			if (player.autocastId > 0) {
				player.getPlayerAssistant().resetAutocast();
			} else {
				if (player.playerMagicBook == 1) {
					if (player.playerEquipment[player.playerWeapon] == 4675) {
						player.getPacketSender().setSidebarInterface(0,
								1689);
					} else {
						player.getPacketSender()
								.sendMessage(
										"You can't autocast ancients without an ancient staff.");
					}
				} else if (player.playerMagicBook == 0) {
					if (player.playerEquipment[player.playerWeapon] == 4170) {
						player.getPacketSender().setSidebarInterface(0,
								12050);
					} else {
						player.getPacketSender().setSidebarInterface(0,
								1829);
					}
				}

			}
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			player.duelSlot = -1;
			player.getDueling().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			player.duelSlot = -1;
			player.getDueling().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			player.duelSlot = -1;
			player.getDueling().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			player.duelSlot = -1;
			player.getDueling().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			player.duelSlot = -1;
			player.getDueling().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			player.duelSlot = -1;
			player.getDueling().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			player.duelSlot = -1;
			player.getDueling().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			player.duelSlot = -1;
			player.getDueling().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			player.duelSlot = -1;
			player.getDueling().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			player.duelSlot = -1;
			player.getDueling().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			player.duelSlot = -1;
			player.getDueling().selectRule(10);
			break;

		case 53245: // no helm
			player.duelSlot = 0;
			player.getDueling().selectRule(11);
			break;

		case 53246: // no cape
			player.duelSlot = 1;
			player.getDueling().selectRule(12);
			break;

		case 53247: // no ammy
			player.duelSlot = 2;
			player.getDueling().selectRule(13);
			break;

		case 53249: // no weapon.
			player.duelSlot = 3;
			player.getDueling().selectRule(14);
			break;

		case 53250: // no body
			player.duelSlot = 4;
			player.getDueling().selectRule(15);
			break;

		case 53251: // no shield
			player.duelSlot = 5;
			player.getDueling().selectRule(16);
			break;

		case 53252: // no legs
			player.duelSlot = 7;
			player.getDueling().selectRule(17);
			break;

		case 53255: // no gloves
			player.duelSlot = 9;
			player.getDueling().selectRule(18);
			break;

		case 53254: // no boots
			player.duelSlot = 10;
			player.getDueling().selectRule(19);
			break;

		case 53253: // no rings
			player.duelSlot = 12;
			player.getDueling().selectRule(20);
			break;

		case 53248: // no arrows
			player.duelSlot = 13;
			player.getDueling().selectRule(21);
			break;

		case 26018:
			if (player.inDuelArena()) {
				Client opponent = (Client) PlayerHandler.players[player.duelingWith];
				if (opponent == null) {
					player.getDueling().declineDuel();
					return;
				}

				if (player.duelRule[2] && player.duelRule[3]
						&& player.duelRule[4]) {
					player.getPacketSender()
							.sendMessage(
									"You won't be able to attack the player with the rules you have set.");
					break;
				}
				if (player.duelRule[9]) {
					player.getPacketSender()
							.sendMessage(
									"@red@You won't be able to attack if you don't have a fun weapon.");
				}
				player.duelStatus = 2;
				if (player.duelStatus == 2) {
					player.getPacketSender().sendString(
							"Waiting for other player...", 6684);
					opponent.getPacketSender().sendString(
							"Other player has accepted.", 6684);
				}
				if (opponent.duelStatus == 2) {
					opponent.getPacketSender().sendString(
							"Waiting for other player...", 6684);
					player.getPacketSender().sendString(
							"Other player has accepted.", 6684);
				}

				if (player.duelStatus == 2 && opponent.duelStatus == 2) {
					player.duelStatus = 3;
					opponent.duelStatus = 3;
					player.getDueling().confirmDuel();
					opponent.getDueling().confirmDuel();
				}
			} else {
				Client o = (Client) PlayerHandler.players[player.duelingWith];
				player.getDueling().declineDuel();
				o.getDueling().declineDuel();
				player.getPacketSender().sendMessage(
						"You can't stake out of the Duel Arena.");
			}
			break;

		case 25120:
			if (player.inDuelArena()) {
				if (player.duelStatus == 5) {
					break;
				}
				Client o1 = (Client) PlayerHandler.players[player.duelingWith];
				if (o1 == null) {
					player.getDueling().declineDuel();
					return;
				}

				player.duelStatus = 4;
				if (o1.duelStatus == 4 && player.duelStatus == 4) {
					player.getDueling().startDuel();
					o1.getDueling().startDuel();
					o1.duelCount = 4;
					player.duelCount = 4;
					player.duelDelay = System.currentTimeMillis();
					o1.duelDelay = System.currentTimeMillis();
				} else {
					player.getPacketSender().sendString(
							"Waiting for other player...", 6571);
					o1.getPacketSender().sendString(
							"Other player has accepted.", 6571);
				}
			} else {
				Client o = (Client) PlayerHandler.players[player.duelingWith];
				player.getDueling().declineDuel();
				o.getDueling().declineDuel();
				player.getPacketSender().sendMessage(
						"You can't stake out of the Duel Arena.");
			}
			break;

		/*
		 * if (System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
		 * if (duelCount != 1) { forcedChat("" + (--duelCount)); duelDelay =
		 * System.currentTimeMillis(); } else { damageTaken = new
		 * int[Config.MAX_PLAYERS]; forcedChat("FIGHT!"); duelCount = 0; } }
		 */

		case 4169: // god spell charge
			player.usingMagic = true;
			if (!player.getCombatAssistant().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - player.godSpellDelay < CombatConstants.GOD_SPELL_CHARGE) {
				player.getPacketSender().sendMessage("You still feel the charge in your body!");
				break;
			}
			player.godSpellDelay = System.currentTimeMillis();
			player.getPacketSender().sendMessage(	"You feel charged with a magical power!");
			player.gfx100(MagicData.MAGIC_SPELLS[48][3]);
			player.startAnimation(MagicData.MAGIC_SPELLS[48][2]);
			player.usingMagic = false;
			break;

		case 21010:
			if (player.isBanking) {	
				player.takeAsNote = true;
			} else {
				player.getPacketSender().sendMessage("You must be banking to do this!");
			}
			break;

		case 21011:
		if (player.isBanking) {
			player.takeAsNote = false;
		} else {
			player.getPacketSender().sendMessage("You must be banking to do this!");
		}
		break;

		case 9125: // Accurate
		case 6221: // range accurate
		case 22228: // punch (unarmed)
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
		case 14218: // mace
		case 14221: // mace
			player.fightMode = 0;// attack
			if (player.autocasting) {
				player.getPlayerAssistant().resetAutocast();
			}
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 22229: // block (unarmed)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
		case 14219: // mace
			player.fightMode = 1;// def
			if (player.autocasting) {
				player.getPlayerAssistant().resetAutocast();
			}
			break;
			
		case 9128: // Aggressive
		case 6220: // range rapid
		case 22230: // kick (unarmed)
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
		case 14220: // mace
			player.fightMode = 2;// shared
			if (player.autocasting) {
				player.getPlayerAssistant().resetAutocast();
			}
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			player.fightMode = 3;// block
			if (player.autocasting) {
				player.getPlayerAssistant().resetAutocast();
			}
			break;


		/** Prayers **/
		case 21233: // thick skin
			ActivatePrayers.activatePrayer(player, 0);
			break;
		case 21234: // burst of str
			ActivatePrayers.activatePrayer(player, 1);
			break;
		case 21235: // charity of thought
			ActivatePrayers.activatePrayer(player, 2);
			break;
		case 70080: // range
			ActivatePrayers.activatePrayer(player, 3);
			break;
		case 70082: // mage
			ActivatePrayers.activatePrayer(player, 4);
			break;
		case 21236: // rockskin
			ActivatePrayers.activatePrayer(player, 5);
			break;
		case 21237: // super human
			ActivatePrayers.activatePrayer(player, 6);
			break;
		case 21238: // improved reflexes
			ActivatePrayers.activatePrayer(player, 7);
			break;
		case 21239: // hawk eye
			ActivatePrayers.activatePrayer(player, 8);
			break;
		case 21240:
			ActivatePrayers.activatePrayer(player, 9);
			break;
		case 21241: // protect Item
			ActivatePrayers.activatePrayer(player, 10);
			break;
		case 70084: // 26 range
			ActivatePrayers.activatePrayer(player, 11);
			break;
		case 70086: // 27 mage
			ActivatePrayers.activatePrayer(player, 12);
			break;
		case 21242: // steel skin
			ActivatePrayers.activatePrayer(player, 13);
			break;
		case 21243: // ultimate str
			ActivatePrayers.activatePrayer(player, 14);
			break;
		case 21244: // incredible reflex
			ActivatePrayers.activatePrayer(player, 15);
			break;
		case 21245: // protect from magic
			ActivatePrayers.activatePrayer(player, 16);
			break;
		case 21246: // protect from range
			ActivatePrayers.activatePrayer(player, 17);
			break;
		case 21247: // protect from melee
			ActivatePrayers.activatePrayer(player, 18);
			break;
		case 70088: // 44 range
			ActivatePrayers.activatePrayer(player, 19);
			break;
		case 70090: // 45 mystic
			ActivatePrayers.activatePrayer(player, 20);
			break;
		case 2171: // retrui
			ActivatePrayers.activatePrayer(player, 21);
			break;
		case 2172: // redem
			ActivatePrayers.activatePrayer(player, 22);
			break;
		case 2173: // smite
			ActivatePrayers.activatePrayer(player, 23);
			break;
		case 70092: // chiv
			ActivatePrayers.activatePrayer(player, 24);
			break;
		case 70094: // piety
			ActivatePrayers.activatePrayer(player, 25);
			break;

		case 13092:
			if (System.currentTimeMillis() - player.lastButton < 400) {

				player.lastButton = System.currentTimeMillis();

				break;

			} else {

				player.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) PlayerHandler.players[player.tradeWith];
			if (ot == null || !ot.inTrade) {
				player.getTrading().declineTrade();
				player.getPacketSender().sendMessage(
						"Trade declined as the other player has disconnected.");
				ot.getTrading().declineTrade();
				ot.getPacketSender().sendMessage(
						"Trade declined as you disconnected.");
				break;
			}
			player.getPacketSender().sendString(
					"Waiting for other player...", 3431);
			ot.getPacketSender().sendString("Other player has accepted.",
					3431);
			player.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : player.getTrading().offeredItems) {
				if (item.id > 0) {
					if (ot.getItemAssistant().freeSlots() < player.getTrading().offeredItems
							.size()) {
						player.getPacketSender().sendMessage(
								ot.playerName
										+ " only has "
										+ ot.getItemAssistant().freeSlots()
										+ " free slots, please remove "
										+ (player.getTrading().offeredItems
												.size() - ot.getItemAssistant()
												.freeSlots()) + " items.");
						ot.getPacketSender().sendMessage(
								player.playerName
										+ " has to remove "
										+ (player.getTrading().offeredItems
												.size() - ot.getItemAssistant()
												.freeSlots())
										+ " items or you could offer them "
										+ (player.getTrading().offeredItems
												.size() - ot.getItemAssistant()
												.freeSlots()) + " items.");
						player.goodTrade = false;
						ot.goodTrade = false;
						player.getPacketSender().sendString(
								"Not enough inventory spaces.", 3431);
						ot.getPacketSender().sendString(
								"Not enough inventory spaces.", 3431);
						break;
					} else {
						player.getPacketSender().sendString(
								"Waiting for other player...", 3431);
						ot.getPacketSender().sendString(
								"Other player has accepted.", 3431);
						player.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (player.inTrade && !player.tradeConfirmed && ot.goodTrade
					&& player.goodTrade) {
				player.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					player.getTrading().confirmScreen();
					ot.getTrading().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - player.lastButton < 400) {

				player.lastButton = System.currentTimeMillis();

				break;

			} else {

				player.lastButton = System.currentTimeMillis();

			}
			player.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[player.tradeWith];
			if (ot1 == null) {
				player.getTrading().declineTrade();
				player.getPacketSender().sendMessage(
						"Trade declined as the other player has disconnected.");
				break;
			}

			if (player.inTrade && player.tradeConfirmed && ot1.tradeConfirmed
					&& !player.tradeConfirmed2) {
				player.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					player.acceptedTrade = true;
					ot1.acceptedTrade = true;
					player.getTrading().giveItems();
					ot1.getTrading().giveItems();
					//here
					player.getPacketSender().sendMessage(
							"@red@Trade completed.");
					ot1.getPacketSender().sendMessage(
							"@red@Trade completed.");
					player.tradeStatus = 0;
					ot1.tradeStatus = 0;
					break;
				}
				ot1.getPacketSender().sendString(
						"Other player has accepted.", 3535);
				player.getPacketSender().sendString(
						"Waiting for other player...", 3535);
			}

			break;
		/* Player Options */
		case 74176:
			if (!player.mouseButton) {
				player.mouseButton = true;
				player.getPacketSender().sendConfig(500, 1);
				player.getPacketSender().sendConfig(170, 1);
			} else if (player.mouseButton) {
				player.mouseButton = false;
				player.getPacketSender().sendConfig(500, 0);
				player.getPacketSender().sendConfig(170, 0);
			}
			break;
		case 74180:
			if (!player.chatEffects) {
				player.chatEffects = true;
				player.getPacketSender().sendConfig(501, 1);
				player.getPacketSender().sendConfig(171, 0);
			} else {
				player.chatEffects = false;
				player.getPacketSender().sendConfig(501, 0);
				player.getPacketSender().sendConfig(171, 1);
			}
			break;
		case 74188:
			if (!player.acceptAid) {
				player.acceptAid = true;
				player.getPacketSender().sendConfig(503, 1);
				player.getPacketSender().sendConfig(427, 1);
			} else {
				player.acceptAid = false;
				player.getPacketSender().sendConfig(503, 0);
				player.getPacketSender().sendConfig(427, 0);
			}
			break;
		case 74192:
			if (!player.isRunning2) {
				player.isRunning2 = true;
				player.getPacketSender().sendConfig(504, 1);
				player.getPacketSender().sendConfig(173, 1);
			} else {
				player.isRunning2 = false;
				player.getPacketSender().sendConfig(504, 0);
				player.getPacketSender().sendConfig(173, 0);
			}
			break;

		case 74206:// area1
			player.getPacketSender().sendConfig(509, 1);
			player.getPacketSender().sendConfig(510, 0);
			player.getPacketSender().sendConfig(511, 0);
			player.getPacketSender().sendConfig(512, 0);
			break;
		case 74207:// area2
			player.getPacketSender().sendConfig(509, 0);
			player.getPacketSender().sendConfig(510, 1);
			player.getPacketSender().sendConfig(511, 0);
			player.getPacketSender().sendConfig(512, 0);
			break;
		case 74208:// area3
			player.getPacketSender().sendConfig(509, 0);
			player.getPacketSender().sendConfig(510, 0);
			player.getPacketSender().sendConfig(511, 1);
			player.getPacketSender().sendConfig(512, 0);
			break;
		case 74209:// area4
			player.getPacketSender().sendConfig(509, 0);
			player.getPacketSender().sendConfig(510, 0);
			player.getPacketSender().sendConfig(511, 0);
			player.getPacketSender().sendConfig(512, 1);
			break;

		case 24017:
			player.getPlayerAssistant().resetAutocast();
			player.getItemAssistant().sendWeapon(player.playerEquipment[player.playerWeapon], DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]));
			break;

		}
	}

}
