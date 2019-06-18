package redone.net.packets.impl;

import redone.Constants;
import redone.Server;
import redone.game.content.combat.Specials;
import redone.game.content.combat.magic.CastOnOther;
import redone.game.content.combat.magic.MagicData;
import redone.game.content.combat.magic.MagicTeleports;
import redone.game.content.combat.prayer.ActivatePrayers;
import redone.game.content.music.Music;
import redone.game.content.music.sound.SoundList;
import redone.game.content.quests.QuestAssistant;
import redone.game.content.random.PartyRoom;
import redone.game.content.randomevents.SandwhichLady;
import redone.game.content.skills.cooking.Cooking;
import redone.game.content.skills.cooking.CookingTutorialIsland;
import redone.game.content.skills.crafting.JewelryMaking;
import redone.game.content.skills.crafting.LeatherMaking;
import redone.game.content.skills.crafting.Pottery;
import redone.game.content.skills.crafting.Spinning;
import redone.game.content.skills.crafting.Tanning;
import redone.game.content.skills.crafting.CraftingData.tanningData;
import redone.game.content.skills.fletching.LogCutting;
import redone.game.content.skills.herblore.Herblore;
import redone.game.content.skills.smithing.SilverCrafting;
import redone.game.content.skills.smithing.Smelting;
import redone.game.content.traveling.GnomeGlider;
import redone.game.items.GameItem;
import redone.game.items.ItemAssistant;
import redone.game.items.impl.ExperienceLamp;
import redone.game.items.impl.Flowers;
import redone.game.items.impl.LightSources;
import redone.game.items.impl.Teles;
import redone.game.objects.impl.Climbing;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client player, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(player.getInStream().buffer, 0, packetSize);
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
		if (player.musicOn == true) {
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
			player.getActionSender().sendMessage(
					player.playerName + " - actionbutton: " + actionButtonId);
		}
		
		if (player.isAutoButton(actionButtonId)) {
			player.assignAutocast(actionButtonId);
		}

		switch (actionButtonId) {
		

		case 71074:
			if (player.clanId >= 0) {
				if (Server.clanChat.clans[player.clanId].owner
						.equalsIgnoreCase(player.playerName)) {
					Server.clanChat
							.sendLootShareMessage(
									player.clanId,
									"Lootshare has been toggled to "
											+ (!Server.clanChat.clans[player.clanId].lootshare ? "on"
													: "off")
											+ " by the clan leader.");
					Server.clanChat.clans[player.clanId].lootshare = !Server.clanChat.clans[player.clanId].lootshare;
				} else
					player.getActionSender().sendMessage("Only the owner of the clan has the power to do that.");
			}
			break;
		case 70212:
			if (player.clanId > -1)
				Server.clanChat.leaveClan(player.playerId, player.clanId);
			else
				player.getActionSender().sendMessage("You are not in a clan.");
			break;
		case 62137:
			if (player.clanId >= 0) {
				player.getActionSender().sendMessage("You are already in a clan.");
				break;
			}
			if (player.getOutStream() != null) {
				player.getOutStream().createFrame(187);
				player.flushOutStream();
			}
			break;
		
		case 55096:
			player.getPlayerAssistant().removeAllWindows();
            player.droppedItem = -1;
		break;
		
		case 55095:
			player.getItemAssistant().destroyItem(player.droppedItem);
            player.droppedItem = -1;
			break;

		case 50235:
			MagicTeleports.paddewwaTeleport(player);
			break;

		case 50245:
			MagicTeleports.senntisenTeleport(player);
			break;

		case 50253:
			MagicTeleports.kharyllTeleport(player);
			break;

		case 51005:
			MagicTeleports.lassarTeleport(player);
			break;

		case 51013:
			MagicTeleports.dareeyakTeleport(player);
			break;

		case 51023:
			MagicTeleports.carrallangarTeleport(player);
			break;

		case 51031:
			MagicTeleports.annakarlTeleport(player);
			break;

		case 51039:
			MagicTeleports.ghorrockTeleport(player);
			break;

		case 4140:
			MagicTeleports.varrockTeleport(player);
			break;

		case 4143:
			MagicTeleports.lumbridgeTeleport(player);
			break;

		case 4146:
			MagicTeleports.faladorTeleport(player);
			break;

		case 4150:
			MagicTeleports.camelotTeleport(player);
			break;

		case 6004:
			MagicTeleports.ardougneTeleport(player);
			break;

		case 6005:
			MagicTeleports.watchTowerTeleport(player);
			break;

		case 29031:
			MagicTeleports.trollhiemTeleport(player);
			break;

		case 72038:
			MagicTeleports.apeAtollTeleport(player);
			break;

		/**
		 * End of Modern Teleports
		 */

		case 4135:
			if (player.inTrade) {
				player.getActionSender().sendMessage(
						"You can't do this in trade!");
				return;
			}
			if (player.playerLevel[6] < 15) {
				player.getPlayerAssistant()
						.sendFrame126(
								"You need a magic level of @blu@15 @bla@to cast bones to bananas",
								357);
				player.getPlayerAssistant().sendChatInterface(356);
				return;
			}
			if (!player.getItemAssistant().playerHasItem(526, 1)) {
				player.getActionSender().sendMessage(
						"You don't have any bones!");
				return;
			}
			if (!player.getItemAssistant().playerHasItem(561, 1)
					|| !player.getItemAssistant().playerHasItem(555, 2)
					|| !player.getItemAssistant().playerHasItem(557, 2)) {
				player.getPlayerAssistant()
						.sendFrame126(
								"You do not have the correct runes to cast this spell.",
								357);
				player.getPlayerAssistant().sendChatInterface(356);
				return;
			}
			if (System.currentTimeMillis() - player.boneDelay > 2000) {
				player.getItemAssistant().deleteItem2(561, 1);
				player.getItemAssistant().deleteItem2(557, 2);
				player.getItemAssistant().deleteItem2(555, 2);
				player.getPlayerAssistant().addSkillXP(40, 6);
				player.getPlayerAssistant().refreshSkill(6);
				player.startAnimation(722);
				player.gfx100(141);
				player.getPlayerAssistant().sendFrame106(6);
				player.getActionSender().sendSound(
						SoundList.BONES_TO_BANNAS, 100, 0);
				player.boneDelay = System.currentTimeMillis();
				do {
					player.getItemAssistant().deleteItem2(526, 1);
					player.getItemAssistant().addItem(1963, 1);
				} while (player.getItemAssistant().playerHasItem(526, 1));
			}
			break;

		case 62005:
			if (player.inTrade) {
				player.getActionSender().sendMessage(
						"You can't do this in trade!");
				return;
			}
			if (player.playerLevel[6] < 60) {
				player.getPlayerAssistant()
						.sendFrame126(
								"You need a magic level of @blu@60  @blu@ to cast bones to peaches.",
								357);
				player.getPlayerAssistant().sendChatInterface(356);
				return;
			}
			if (!player.getItemAssistant().playerHasItem(526, 1)) {
				player.getActionSender().sendMessage(
						"You don't have any bones!");
				return;
			}
			if (!player.getItemAssistant().playerHasItem(561, 2)
					|| !player.getItemAssistant().playerHasItem(555, 4)
					|| !player.getItemAssistant().playerHasItem(557, 4)) {
				player.getPlayerAssistant()
						.sendFrame126(
								"You do not have the correct runes to cast this spell.",
								357);
				player.getPlayerAssistant().sendChatInterface(356);
				return;
			}
			if (System.currentTimeMillis() - player.boneDelay > 2000) {
				player.getItemAssistant().deleteItem2(561, 2);
				player.getItemAssistant().deleteItem2(557, 4);
				player.getItemAssistant().deleteItem2(555, 4);
				player.getPlayerAssistant().addSkillXP(40, 6);
				player.getPlayerAssistant().refreshSkill(6);
				player.startAnimation(722);
				player.gfx100(311);
				player.getPlayerAssistant().sendFrame106(6);
				player.boneDelay = System.currentTimeMillis();
				do {
					player.getItemAssistant().deleteItem2(526, 1);
					player.getItemAssistant().addItem(6883, 1);
				} while (player.getItemAssistant().playerHasItem(526, 1));
			}
			break;

		case 14067:
			player.canChangeAppearance = false;
			break;

		case 34185:
		case 34193:
		case 34189:
			if (player.clickedSpinning == true) {
				Spinning.getAmount(player, 1);
			}
			break;

		case 34184:
		case 34188:
		case 34192:
			if (player.clickedSpinning == true) {
				Spinning.getAmount(player, 5);
			}
			break;

		case 34183:
		case 34187:
		case 34191:
			if (player.clickedSpinning == true) {
				Spinning.getAmount(player, 10);
			}
			break;

		case 34182:
		case 34186:
		case 34190:
			if (player.clickedSpinning == true) {
				Spinning.getAmount(player, 28);
			}
			break;

		/*
		 * Item on interface 5
		 */
		// item 1
		case 34245:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 1);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 1);
			}
			break;
		case 34244:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 5);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 5);
			}
			break;
		case 34243:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 10);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 10);
			}
			break;
		case 34242:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1787, 6.3, 1, 28);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1787, 1931, 1, 6.3, 28);
			}
			break;
		// item 2
		case 34249:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1789, 15, 7, 1);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 1);
			}
			break;
		case 34248:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1789, 15, 7, 5);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 5);
			}
			break;
		case 34247:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1789, 15, 7, 10);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 10);
			}
			break;
		case 34246:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1789, 15, 7, 28);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1789, 2313, 7, 10, 28);
			}
			break;
		// item 3
		case 34253:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1791, 18, 8, 1);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 1);
			}
			break;
		case 34252:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1791, 18, 8, 5);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 5);
			}
			break;
		case 34251:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1791, 18, 8, 10);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 10);
			}
			break;
		case 34250:
			if (player.showedUnfire == true) {
				Pottery.makeUnfire(player, 1791, 18, 8, 28);
			}
			if (player.showedFire == true) {
				Pottery.makeFire(player, 1791, 1923, 8, 15, 28);
			}
			break;
			
		case 23132:
			player.getPlayerAssistant().closeAllWindows();
			player.isBotting = false;
			player.getActionSender().sendMessage("You are not botting.");
		break;

		case 9118:
			player.getPlayerAssistant().closeAllWindows();
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
			player.getItemAssistant().deleteItem2(995, 2000);
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 8065:
			player.getItemAssistant().deleteItem2(995, 2000);
			player.getPlayerAssistant().removeAllWindows();
			break;
		/** End of Hairdresser buttons */

		case 3166:
		case 3165:
		case 3164:
		case 3163:
				Music.playMusic(player);
				player.musicOn = true;
			break;

		case 3162:
			if (player.musicOn == true) {
				player.musicOn = false;
			} else {
				player.getActionSender().sendMessage("Your music is already turned off.");
			}
			break;

		case 8198:
			PartyRoom.accept(player);
			break;

		case 53152:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 1);
			} else {
				Cooking.cookItem(player, player.cookingItem, 1,
						player.cookingObject);
			}
			break;

		case 53151:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 5);
			} else {
				Cooking.cookItem(player, player.cookingItem, 5,
						player.cookingObject);
			}
			break;

		case 53150:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 10);
			} else {
				Cooking.cookItem(player, player.cookingItem, 10, player.cookingObject);
			}
			break;

		case 53149:
			if (player.tutorialProgress < 36) {
				CookingTutorialIsland.getAmount(player, 28);
			} else {
				Cooking.cookItem(player, player.cookingItem, 28,
						player.cookingObject);
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
				player.getActionSender()
						.sendMessage(
								"[Notice] A PIN delete has been requested. Your PIN will be deleted in "
										+ player.getBankPin().recovery_Delay
										+ " days.");
				player.getActionSender().sendMessage(
						"To cancel this change just type in the correct PIN.");
			} else {
				player.getActionSender()
						.sendMessage(
								"[Notice] Your PIN is already pending deletion. Please wait the entire 2 days.");
				player.getPlayerAssistant().closeAllWindows();
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
				player.getActionSender().sendMessage(
						"Your PIN has been deleted as requested.");
			} else {
				player.getActionSender()
						.sendMessage(
								"Please enter your Bank Pin before requesting a delete.");
				player.getActionSender()
						.sendMessage(
								"You can do this by simply opening your bank. This is to verify it's really you.");
				player.getPlayerAssistant().closeAllWindows();
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
			player.getPlayerAssistant().sendConfig(172, 1);
		} else {
			player.getActionSender().sendMessage("Your auto retaliate is already turned off.");
		}
		break;
		
		case 150:
		if (player.autoRet == 0) {
			player.autoRet = 1;
			player.getPlayerAssistant().sendConfig(172, 0);
		} else {
			player.getActionSender().sendMessage("Your auto retaliate is already turned on.");
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
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 1093:
		case 1094:
		case 1097:
			if (player.autocastId > 0) {
				player.getPlayerAssistant().resetAutocast();
			} else {
				if (player.playerMagicBook == 1) {
					if (player.playerEquipment[player.playerWeapon] == 4675) {
						player.getActionSender().setSidebarInterface(0,
								1689);
					} else {
						player.getActionSender()
								.sendMessage(
										"You can't autocast ancients without an ancient staff.");
					}
				} else if (player.playerMagicBook == 0) {
					if (player.playerEquipment[player.playerWeapon] == 4170) {
						player.getActionSender().setSidebarInterface(0,
								12050);
					} else {
						player.getActionSender().setSidebarInterface(0,
								1829);
					}
				}

			}
			break;

		case 9167:
			switch (player.dialogueAction) {
			case 63:
				player.getDialogueHandler().sendDialogues(166, player.npcType);
				return;
			case 64:
				player.getDialogueHandler().sendDialogues(173, player.npcType);
				return;
			case 60:
				player.getDialogueHandler().sendDialogues(277, player.npcType);
				return;
			case 61:
				player.getDialogueHandler().sendDialogues(295, player.npcType);
				return;
			case 129:
				player.getDialogueHandler().sendDialogues(231, player.npcType);
				return;
			case 58:
				player.getDialogueHandler().sendDialogues(540, player.npcType);
				return;
			case 68:
				player.getDialogueHandler().sendDialogues(39, player.npcType);
				return;
			case 124:
				player.getDialogueHandler().sendDialogues(194, player.npcType);
				return;
			case 230:
				player.getDialogueHandler().sendDialogues(1053, player.npcType);
				return;
			case 251:
				player.getPlayerAssistant().openUpBank();
				player.nextChat = 0;
				return;
			case 144:
				player.getDialogueHandler().sendDialogues(1314, player.npcType);
				return;
			case 502:
				player.getDialogueHandler().sendDialogues(1026, player.npcType);
				return;
			case 1301: // first option haircut.
				player.getDialogueHandler().sendDialogues(1302, 598);
				return;
			case 53:
				if (player.objectId == 1293 || player.objectId == 1317) {
					player.getPlayerAssistant().startTeleport(2542, 3169, 0, "modern");
				} else {
					player.getActionSender().sendMessage("You can't teleport there, because you are already there!");
					player.getPlayerAssistant().closeAllWindows();
				}
				return;
			case 159:
				player.getDialogueHandler().sendDialogues(3161, player.npcType);
				return;
			case 167:
				player.getDialogueHandler().sendDialogues(1343, player.npcType);
				return;
			case 222:
				player.getDialogueHandler().sendDialogues(911, player.npcType);
				player.dialogueAction = -1;
				return;
			case 182:
				player.getDialogueHandler().sendNpcChat1("No, I was hoping someone could help me find it though.", player.talkingNpc, "Squire");
				player.nextChat = 0;
				return;
			case 188:
				player.getDialogueHandler().sendDialogues(3129, 945);
				return;
			case 185:
				player.getDialogueHandler().sendDialogues(629, player.npcType);
				return;
			}
			player.dialogueAction = 0;
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 9168:
			switch (player.dialogueAction) {
			case 63:
				player.getDialogueHandler().sendDialogues(167, player.npcType);
				return;
			case 64:
				player.getDialogueHandler().sendDialogues(174, player.npcType);
				return;
			case 60:
				player.getDialogueHandler().sendDialogues(279, player.npcType);
				return;
			case 61:
				player.getDialogueHandler().sendDialogues(297, player.npcType);
				return;
			case 124:
				player.getDialogueHandler().sendDialogues(192, player.npcType);
				return;
			case 126:
				player.getDialogueHandler().sendDialogues(203, player.npcType);
				return;
			case 58:
				player.getDialogueHandler().sendDialogues(538, player.npcType);
				return;
			case 68:
				player.getDialogueHandler().sendDialogues(40, player.npcType);
				return;
			case 230:
				player.getDialogueHandler().sendDialogues(1049, player.npcType);
				break;
			case 251:
				player.getBankPin().bankPinSettings();
				player.nextChat = 0;
				return;
			case 502:
				player.getDialogueHandler().sendDialogues(1022, player.npcType);
				return;
			case 1301:
				player.getDialogueHandler().sendDialogues(1308, 598);
				return;
			case 144:
				player.getDialogueHandler().sendDialogues(1315, player.npcType);
				return;
			case 53:
				if (player.objectId == 1294 || player.objectId == 1317) {
					player.getPlayerAssistant().startTeleport(2461, 3444, 0,
							"modern");
				} else {
					player.getActionSender().sendMessage("You can't teleport there, because you are already there!");
					player.getPlayerAssistant().closeAllWindows();
				}
				return;
			case 159:
				player.getDialogueHandler().sendDialogues(3195, player.npcType);
				return;
			case 167:
				player.getDialogueHandler().sendDialogues(1344, player.npcType);
				return;
			case 222:
				player.getDialogueHandler().sendDialogues(912, player.npcType);
				player.dialogueAction = -1;
				return;
			case 182:
				player.getDialogueHandler().sendDialogues(615, player.npcType);
				return;
			case 188:
				player.getDialogueHandler().sendDialogues(3130, 945);
				return;
			case 185:
				player.getDialogueHandler().sendDialogues(628, player.npcType);
				return;
			}
			player.dialogueAction = 0;
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 9169:
			switch (player.dialogueAction) {
			case 63:
				player.getDialogueHandler().sendDialogues(168, player.npcType);
				return;
			case 64:
				player.getDialogueHandler().sendDialogues(175, player.npcType);
				return;
			case 60:
				player.getDialogueHandler().sendDialogues(278, player.npcType);
				return;
			case 61:
				player.getDialogueHandler().sendDialogues(296, player.npcType);
				return;
			case 53:
				if (player.objectId == 1294 || player.objectId == 1293) {
					player.getPlayerAssistant().startTeleport(3179, 3507, 0,
							"modern");
				} else {
					player.getActionSender().sendMessage("You can't teleport there, because you are already there!");
					player.getPlayerAssistant().closeAllWindows();
				}
				return;
			case 129:
				player.getDialogueHandler().sendDialogues(232, player.npcType);
				return;
			case 126:
				player.getDialogueHandler().sendDialogues(204, player.npcType);
				return;
			case 144:
				player.getDialogueHandler().sendDialogues(1316, player.npcType);
				return;
			case 124:
				player.getDialogueHandler().sendDialogues(3193, 741);
				return;
			case 58:
				player.getDialogueHandler().sendDialogues(539, player.npcType);
				return;
			case 68:
				player.getDialogueHandler().sendDialogues(41, player.npcType);
				return;
			case 230:
				player.getDialogueHandler().sendDialogues(1050, player.npcType);
				break;
			case 251:
				player.getDialogueHandler().sendDialogues(1015, 494);
				return;
			case 502:
				player.getDialogueHandler().sendDialogues(1025, player.npcType);
				return;
			case 1301:
				player.getDialogueHandler().sendDialogues(1306, 598);
				return;
			case 222:
				player.getDialogueHandler().sendDialogues(913, player.npcType);
				player.dialogueAction = -1;
				return;
			case 167:
				player.getDialogueHandler().sendDialogues(1342, player.npcType);
				return;
			case 159:
				player.getDialogueHandler().sendDialogues(3160, player.npcType);
				return;
			case 182:
				player.getDialogueHandler().sendNpcChat1("Of course he is angry...", player.talkingNpc, "Squire");
				player.nextChat = 0;
				return;
			case 188:
				player.getDialogueHandler().sendDialogues(3131, 945);
				return;
			case 185:
				player.getDialogueHandler().sendDialogues(630, player.npcType);
				return;
			}
			player.dialogueAction = 0;
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 9157:// barrows tele to tunnels
			if (player.dialogueAction == 1) {
				int r = 4;
				// int r = Misc.random(3);

				switch (r) {
				case 0:
					player.getPlayerAssistant().movePlayer(3534, 9677, 0);
					break;

				case 1:
					player.getPlayerAssistant().movePlayer(3534, 9712, 0);
					break;

				case 2:
					player.getPlayerAssistant().movePlayer(3568, 9712, 0);
					break;

				case 3:
					player.getPlayerAssistant().movePlayer(3568, 9677, 0);
					break;
				case 4:
					player.getPlayerAssistant().movePlayer(3551, 9694, 0);
					break;
				}
			} else if (player.dialogueAction == 2) {
				player.getPlayerAssistant().movePlayer(2507, 4717, 0);
			} else if (player.dialogueAction == 7) {
				player.getPlayerAssistant().startTeleport(3088, 3933, 0, "modern");
				player.getActionSender().sendMessage(
						"NOTE: You are now in the wilderness...");
			} else if (player.dialogueAction == 8) {
				player.getPlayerAssistant().resetBarrows();
				player.getActionSender().sendMessage(
						"Your barrows have been reset.");
			} else if (player.dialogueAction == 29) {
				player.getDialogueHandler().sendDialogues(480, player.npcType);
				return;
			} else if (player.dialogueAction == 30) {
				player.getDialogueHandler().sendDialogues(488, player.npcType);
				return;
			} else if (player.dialogueAction == 34) {
				player.getDialogueHandler().sendDialogues(361, player.npcType);
				return;
			} else if (player.dialogueAction == 50) {
				player.getPlayerAssistant().startTeleport(2898, 3562, 0,
						"modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 55) {
				player.getDialogueHandler().sendDialogues(91, player.npcType);
				return;
			} else if (player.dialogueAction == 56) {
				player.getDialogueHandler().sendDialogues(96, player.npcType);
				return;
			} else if (player.dialogueAction == 57) {
				player.getDialogueHandler().sendDialogues(57, player.npcType);
				return;
			} else if (player.dialogueAction == 3222) {
				player.getBarrows().checkCoffins();
				player.getPlayerAssistant().removeAllWindows();
				return;
			} else if (player.dialogueAction == 3218) {
				player.getDialogueHandler().sendDialogues(3219, 0);
				return;
			} else if (player.dialogueAction == 65) {
				player.getDialogueHandler().sendDialogues(179, player.npcType);
				return;
			} else if (player.dialogueAction == 66) {
				player.getDialogueHandler().sendDialogues(182, player.npcType);
				return;
			} else if (player.dialogueAction == 67) {
				player.getDialogueHandler().sendDialogues(36, player.npcType);
				return;
			} else if (player.dialogueAction == 68) {
				player.getDialogueHandler().sendDialogues(587, player.npcType);
				return;
			} else if (player.dialogueAction == 70) {
				player.getDialogueHandler().sendDialogues(1009, player.npcType);
				return;
			} else if (player.dialogueAction == 71) {
				player.getDialogueHandler().sendDialogues(556, player.npcType);
				return;
			} else if (player.dialogueAction == 72) {
				player.getDialogueHandler().sendDialogues(563, player.npcType);
				return;
			} else if (player.dialogueAction == 73) {
				player.getDialogueHandler().sendDialogues(579, player.npcType);
				return;
			} else if (player.dialogueAction == 74) {
				player.getDialogueHandler().sendDialogues(534, player.npcType);
				return;
			} else if (player.dialogueAction == 90) {
				player.getDialogueHandler().sendDialogues(12, player.npcType);
				return;
			} else if (player.dialogueAction == 91) {
				player.getDialogueHandler().sendDialogues(16, player.npcType);
				return;
			} else if (player.dialogueAction == 92) {
				player.getDialogueHandler().sendDialogues(9, player.npcType);
				return;
			} else if (player.dialogueAction == 93) {
				player.getDialogueHandler().sendDialogues(23, player.npcType);
				return;
			} else if (player.dialogueAction == 118) {
				player.getDialogueHandler().sendDialogues(394, player.npcType);
				return;
			} else if (player.dialogueAction == 119) {
				player.getDialogueHandler().sendDialogues(399, player.npcType);
				return;
			} else if (player.dialogueAction == 120) {
				player.getDialogueHandler().sendDialogues(406, player.npcType);
				return;
			} else if (player.dialogueAction == 121) {
				player.getDialogueHandler().sendDialogues(438, player.npcType);
				return;
			} else if (player.dialogueAction == 125) {
				player.getDialogueHandler().sendDialogues(154, player.npcType);
				return;
			} else if (player.dialogueAction == 127) {
				player.getDialogueHandler().sendDialogues(210, player.npcType);
				return;
			} else if (player.dialogueAction == 128) {
				player.getDialogueHandler().sendDialogues(223, player.npcType);
				return;
			} else if (player.dialogueAction == 130) {
				player.getDialogueHandler().sendDialogues(594, player.npcType);
				return;
			} else if (player.dialogueAction == 132) {
				player.getDialogueHandler().sendDialogues(1013, player.npcType);
			} else if (player.dialogueAction == 133) {
				player.getDialogueHandler().sendDialogues(1016, player.npcType);
			} else if (player.dialogueAction == 140) {
				player.getDialogueHandler().sendDialogues(198, player.npcType);
				return;
			} else if (player.dialogueAction == 141) {
				player.getDialogueHandler().sendDialogues(1020, player.npcType);
				return;
			} else if (player.dialogueAction == 143) {
				player.getDialogueHandler().sendDialogues(1232, player.npcType);
				return;
			} else if (player.dialogueAction == 168) {
				player.getDialogueHandler().sendDialogues(476, player.npcType);
				return;
			} else if (player.dialogueAction == 508) {
				player.getDialogueHandler().sendDialogues(1026, player.npcType);
				return;
			} else if (player.dialogueAction == 855) {
				player.getItemAssistant().removeAllItems();
			} else if (player.dialogueAction == 146) {
				player.getDialogueHandler().sendDialogues(1325, player.npcType);
				return;
			} else if (player.dialogueAction == 177) {
				player.getDialogueHandler().sendDialogues(1376, player.npcType);
				return;
			} else if (player.dialogueAction == 151) {
				player.getDialogueHandler().sendDialogues(2998, player.npcType);
				return;
			} else if (player.dialogueAction == 152) {
				player.getDialogueHandler().sendDialogues(3121, player.npcType);
				return;
			} else if (player.dialogueAction == 154) {
				player.getDialogueHandler().sendDialogues(3137, player.npcType);
				return;
			} else if (player.dialogueAction == 155) {
				player.getDialogueHandler().sendDialogues(3142, player.npcType);
				return;
			} else if (player.dialogueAction == 156) {
				player.getDialogueHandler().sendDialogues(3147, player.npcType);
				return;
			} else if (player.dialogueAction == 157) {
				player.getDialogueHandler().sendDialogues(3153, player.npcType);
				return;
			} else if (player.dialogueAction == 158) {
				player.getDialogueHandler().sendDialogues(3156, player.npcType);
				return;
			} else if (player.dialogueAction == 3111) {
				player.getDialogueHandler().sendDialogues(3112, 946);
				return;
			} else if (player.dialogueAction == 162) {
				player.getDialogueHandler().sendDialogues(3170, player.npcType);
				return;
			} else if (player.dialogueAction == 163) {
				player.getDialogueHandler().sendDialogues(3129, player.npcType);
				return;
			} else if (player.dialogueAction == 164) {
				player.getDialogueHandler().sendDialogues(3177, 510);
				return;
			} else if (player.dialogueAction == 165) {
				player.getDialogueHandler().sendDialogues(3182, 510);
				return;
			} else if (player.dialogueAction == 166) {
				player.getDialogueHandler().sendDialogues(1340, player.npcType);
				return;
			} else if (player.dialogueAction == 170) {
				player.getDialogueHandler().sendDialogues(1348, player.npcType);
				return;
			} else if (player.dialogueAction == 171) {
				player.getDialogueHandler().sendDialogues(1352, player.npcType);
				return;
			} else if (player.dialogueAction == 172) {
				player.getDialogueHandler().sendDialogues(1355, player.npcType);
				return;
			} else if (player.dialogueAction == 173) {
				player.getDialogueHandler().sendDialogues(1360, player.npcType);
				return;
			} else if (player.dialogueAction == 175) {
				player.getDialogueHandler().sendDialogues(3192, player.npcType);
				return;
			} else if (player.dialogueAction == 176) {
				player.getDialogueHandler().sendDialogues(1372, player.npcType);
				return;
			} else if (player.dialogueAction == 178) {
				player.getDialogueHandler().sendDialogues(3186, player.npcType);
				return;
			} else if (player.dialogueAction == 179) {
				player.getDialogueHandler().sendDialogues(1380, player.npcType);
				return;
			} else if (player.dialogueAction == 180) {
				player.getDialogueHandler().sendDialogues(3197, player.npcType);
				return;
			} else if (player.dialogueAction == 181) {
				player.getDialogueHandler().sendDialogues(612, player.npcType);
				return;
			} else if (player.dialogueAction == 183) {
				player.getDialogueHandler().sendDialogues(620, player.npcType);
				return;
			} else if (player.dialogueAction == 184) {
				player.getDialogueHandler().sendDialogues(624, player.npcType);
				return;
			} else if (player.dialogueAction == 186) {
				player.getItemAssistant().deleteItem2(1929, 1);
				player.getItemAssistant().deleteItem2(1933, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1931, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 187) {
				player.getItemAssistant().deleteItem2(1933, 1);
				player.getItemAssistant().deleteItem2(1937, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1935, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 189) {
				player.getDialogueHandler().sendDialogues(3210, player.npcType);
				return;
			} else if (player.dialogueAction == 161) {// rod
				player.getPlayerAssistant().startTeleport(3313, 3234, 0, "modern");
				Teles.necklaces(player);
				return;
			}
			player.dialogueAction = 0;
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 9158:
			if (player.dialogueAction == 8) {
				player.getPlayerAssistant().fixAllBarrows();
			} else if (player.dialogueAction == 29) {
				player.getDialogueHandler().sendDialogues(481, player.npcType);
				return;
			} else if (player.dialogueAction == 34) {
				player.getDialogueHandler().sendDialogues(359, player.npcType);
				return;
			} else if (player.dialogueAction == 50) {
				player.getPlayerAssistant().startTeleport(2545, 3569, 0, "modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 55) {
				player.getDialogueHandler().sendDialogues(92, player.npcType);
				return;
			} else if (player.dialogueAction == 56) {
				player.getDialogueHandler().sendDialogues(95, player.npcType);
				return;
			} else if (player.dialogueAction == 74) {
				player.getDialogueHandler().sendDialogues(535, player.npcType);
				return;
			} else if (player.dialogueAction == 57) {
				player.getDialogueHandler().sendDialogues(58, player.npcType);
				return;
			} else if (player.dialogueAction == 62) {
				player.getDialogueHandler().sendDialogues(309, player.npcType);
				return;
			} else if (player.dialogueAction == 67) {
				player.getDialogueHandler().sendDialogues(35, player.npcType);
				return;
			} else if (player.dialogueAction == 68) {
				player.getDialogueHandler().sendDialogues(586, player.npcType);
				return;
			} else if (player.dialogueAction == 71) {
				player.getDialogueHandler().sendDialogues(582, player.npcType);
				return;
			} else if (player.dialogueAction == 72) {
				player.getDialogueHandler().sendDialogues(562, player.npcType);
				return;
			} else if (player.dialogueAction == 73) {
				player.getDialogueHandler().sendDialogues(580, player.npcType);
				return;
			} else if (player.dialogueAction == 90) {
				player.getDialogueHandler().sendDialogues(13, player.npcType);
				return;
			} else if (player.dialogueAction == 91) {
				player.getDialogueHandler().sendDialogues(17, player.npcType);
				return;
			} else if (player.dialogueAction == 118) {
				player.getDialogueHandler().sendDialogues(392, player.npcType);
				return;
			} else if (player.dialogueAction == 119) {
				player.getDialogueHandler().sendDialogues(404, player.npcType);
				return;
			} else if (player.dialogueAction == 120) {
				player.getDialogueHandler().sendDialogues(404, player.npcType);
				return;
			} else if (player.dialogueAction == 121) {
				player.getDialogueHandler().sendDialogues(437, player.npcType);
				return;
			} else if (player.dialogueAction == 125) {
				player.getDialogueHandler().sendDialogues(163, player.npcType);
				return;
			} else if (player.dialogueAction == 130) {
				player.getDialogueHandler().sendDialogues(593, player.npcType);
				return;
			} else if (player.dialogueAction == 131) {
				JewelryMaking.mouldInterface(player);
				return;
			} else if (player.dialogueAction == 141) {
				player.getDialogueHandler().sendDialogues(1021, player.npcType);
				return;
			} else if (player.dialogueAction == 143) {
				player.getDialogueHandler().sendDialogues(1233, player.npcType);
				return;
			} else if (player.dialogueAction == 161) {// rod
				player.getPlayerAssistant().startTeleport(2441, 3090, 0, "modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 508) {
				player.getDialogueHandler().sendDialogues(1025, player.npcType);
				return;
			} else if (player.dialogueAction == 146) {
				player.getDialogueHandler().sendDialogues(1324, player.npcType);
				return;
			} else if (player.dialogueAction == 177) {
				player.getDialogueHandler().sendDialogues(1375, player.npcType);
				return;
			} else if (player.dialogueAction == 21) {
				Flowers.harvestFlower(player, Flowers.lastObject);
				player.getPlayerAssistant().removeAllWindows();
			} else if (player.dialogueAction == 3111) {
				player.getDialogueHandler().sendDialogues(3117, 946);
				return;
			} else if (player.dialogueAction == 152) {
				player.getDialogueHandler().sendDialogues(3120, player.npcType);
				return;
			} else if (player.dialogueAction == 151) {
				player.getDialogueHandler().sendDialogues(3000, player.npcType);
				player.getPlayerAssistant().removeAllWindows();
				return;
			} else if (player.dialogueAction == 154) {
				player.getDialogueHandler().sendDialogues(3135, player.npcType);
				return;
			} else if (player.dialogueAction == 155) {
				player.getDialogueHandler().sendDialogues(3141, player.npcType);
				return;
			} else if (player.dialogueAction == 156) {
				player.getDialogueHandler().sendDialogues(3146, player.npcType);
				return;
			} else if (player.dialogueAction == 157) {
				player.getDialogueHandler().sendDialogues(3152, player.npcType);
				return;
			} else if (player.dialogueAction == 158) {
				player.getDialogueHandler().sendDialogues(3157, player.npcType);
				return;
			} else if (player.dialogueAction == 162) {
				player.getDialogueHandler().sendDialogues(3169, player.npcType);
				return;
			} else if (player.dialogueAction == 163) {
				player.getDialogueHandler().sendDialogues(3131, player.npcType);
				return;
			} else if (player.dialogueAction == 164) {
				player.getDialogueHandler().sendDialogues(3175, player.npcType);
				return;
			} else if (player.dialogueAction == 165) {
				player.getDialogueHandler().sendDialogues(3180, player.npcType);
				return;
			} else if (player.dialogueAction == 166) {
				player.getDialogueHandler().sendDialogues(1339, player.npcType);
				return;
			} else if (player.dialogueAction == 168) {
				player.getDialogueHandler().sendDialogues(1337, player.npcType);
				return;
			} else if (player.dialogueAction == 170) {
				player.getDialogueHandler().sendDialogues(1347, player.npcType);
				return;
			} else if (player.dialogueAction == 171) {
				player.getDialogueHandler().sendDialogues(1351, player.npcType);
				return;
			} else if (player.dialogueAction == 172) {
				player.getDialogueHandler().sendDialogues(1356, player.npcType);
				return;
			} else if (player.dialogueAction == 173) {
				player.getDialogueHandler().sendDialogues(1361, player.npcType);
				return;
			} else if (player.dialogueAction == 175) {
				player.getDialogueHandler().sendDialogues(3191, player.npcType);
				return;
			} else if (player.dialogueAction == 176) {
				player.getDialogueHandler().sendDialogues(1371, player.npcType);
				return;
			} else if (player.dialogueAction == 178) {
				player.getDialogueHandler().sendDialogues(3185, player.npcType);
				return;
			} else if (player.dialogueAction == 179) {
				player.getDialogueHandler().sendDialogues(1381, player.npcType);
				return;
			} else if (player.dialogueAction == 180) {
				player.getDialogueHandler().sendDialogues(3199, player.npcType);
				return;
			} else if (player.dialogueAction == 181) {
				player.getDialogueHandler().sendNpcChat1("No I like my job as Squire, I just need some help.", player.talkingNpc, "Squire");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 183) {
				player.getDialogueHandler().sendPlayerChat1("Well I hope you find it soon.");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 184) {
				player.getDialogueHandler().sendPlayerChat1("No, I've got lots of mining work to do.");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 186) {
				player.getItemAssistant().deleteItem2(1929, 1);
				player.getItemAssistant().deleteItem2(1933, 1);
				player.getItemAssistant().addItem(2307, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1931, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 187) {
				player.getItemAssistant().deleteItem2(1933, 1);
				player.getItemAssistant().deleteItem2(1937, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1935, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 189) {
				player.getDialogueHandler().sendDialogues(3212, player.npcType);
				return;
			}
			player.dialogueAction = 0;
			player.getPlayerAssistant().removeAllWindows();
			break;

		case 9178:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(3428, 3538, 0, "modern");
			}
			if (player.dialogueAction == 122 && player.objectId == 12164 || player.objectId == 12163 || player.objectId == 12166) {//barb
				player.getPlayerAssistant().startTeleport(3112, 3410, 0, "modern");
			} else if (player.objectId == 12165) {
				if (player.dialogueAction == 122) {
				player.getActionSender().sendMessage("You can't take the canoe to barbarian village because you're already there!");
				player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(3565, 3314, 0,
						"modern");
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3088, 3500, 0,
						"modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(500, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(340, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(354, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(378, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3088, 3500, 0,
						"modern");
			}
			Teles.necklaces(player);
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(52, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1005, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1045, player.npcType);
			}
			/*
			 * if (client.dialogueAction == 142)
			 * client.getDialogues().handleDialogues(1231, client.npcType);
			 */
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1318, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3123, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3164, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1234, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().buyArrows();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(28, player.npcType);
			}
			break;

		case 9179:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2884, 3395, 0,
						"modern");
			}
			if (player.dialogueAction == 122 && player.objectId == 12163 || player.objectId == 12165 || player.objectId == 12166) {//champ
				player.getPlayerAssistant().startTeleport(3203, 3343, 0, "modern");
			} else if (player.objectId == 12164) {
				if (player.dialogueAction == 122) {
				player.getActionSender().sendMessage("You can't take the canoe to the Champion Guild because you're already there!");
				player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(2444, 5170, 0,
						"modern");
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3243, 3513, 0,
						"modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(502, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(341, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(356, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(376, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3293, 3174, 0,
						"modern");
			}
			Teles.necklaces(player);
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(64, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500002, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1042, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1319, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3124, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3164, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1235, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().exchangePoints();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(29, player.npcType);
			}
			break;

		case 9180:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2471, 10137, 0,
						"modern");
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500003, player.npcType);
			}
			if (player.dialogueAction == 122 && player.objectId == 12164 || player.objectId == 12165 || player.objectId == 12166) {//lumby
				player.getPlayerAssistant().startTeleport(3243, 3237, 0, "modern");
			} else if (player.objectId == 12163) {
				if (player.dialogueAction == 122) {
					player.getActionSender().sendMessage("You can't take the canoe to Lumbridge because you're already there!");
					player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3363, 3676, 0,
						"modern");
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(2659, 2676, 0,
						"modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(501, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(342, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(355, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(377, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(2911, 3152, 0,
						"modern");
			}
			Teles.necklaces(player);
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(65, player.npcType);
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(30, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1041, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1320, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3125, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3165, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1236, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().howAmIDoing();
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1003, player.npcType);
			}
			break;

		case 9181:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2669, 3714, 0, "modern");
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500004, player.npcType);
			}
			if (player.dialogueAction == 122 && player.objectId == 12163 || player.objectId == 12164 || player.objectId == 12165) {//edge
				player.getPlayerAssistant().startTeleport(3132, 3509, 0, "modern");
			} else if (player.objectId == 12166) {
				if (player.dialogueAction == 122) {
					player.getActionSender().sendMessage("You can't take the canoe to Edgeville because you're already there!");
					player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(2540, 4716, 0,
						"modern");
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3103, 3249, 0,
						"modern");
			}
			Teles.necklaces(player);
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(63, player.npcType);
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(31, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1004, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1038, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1321, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3126, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3166, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1231, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getPlayerAssistant().closeAllWindows();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(28, player.npcType);
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
					player.getActionSender()
							.sendMessage(
									"You won't be able to attack the player with the rules you have set.");
					break;
				}
				player.duelStatus = 2;
				if (player.duelStatus == 2) {
					player.getPlayerAssistant().sendFrame126(
							"Waiting for other player...", 6684);
					opponent.getPlayerAssistant().sendFrame126(
							"Other player has accepted.", 6684);
				}
				if (opponent.duelStatus == 2) {
					opponent.getPlayerAssistant().sendFrame126(
							"Waiting for other player...", 6684);
					player.getPlayerAssistant().sendFrame126(
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
				player.getActionSender().sendMessage(
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
					player.getPlayerAssistant().sendFrame126(
							"Waiting for other player...", 6571);
					o1.getPlayerAssistant().sendFrame126(
							"Other player has accepted", 6571);
				}
			} else {
				Client o = (Client) PlayerHandler.players[player.duelingWith];
				player.getDueling().declineDuel();
				o.getDueling().declineDuel();
				player.getActionSender().sendMessage(
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

			if (System.currentTimeMillis() - player.godSpellDelay < Constants.GOD_SPELL_CHARGE) {
				player.getActionSender().sendMessage(
						"You still feel the charge in your body!");
				break;
			}
			player.godSpellDelay = System.currentTimeMillis();
			player.getActionSender().sendMessage(
					"You feel charged with a magical power!");
			player.gfx100(MagicData.MAGIC_SPELLS[48][3]);
			player.startAnimation(MagicData.MAGIC_SPELLS[48][2]);
			player.usingMagic = false;
			break;

		case 153:
			if (player.tutorialProgress == 11) {
				player.getDialogueHandler().sendDialogues(3041, 0);
			}
			player.getPlayerAssistant().sendConfig(173, 1);
			player.isRunning2 = true;
			break;

		case 152:
			/*if (client.performingAction) {
				return;
			}*/
			player.isRunning2 = false;
			player.getPlayerAssistant().sendConfig(173, 0);
			break;

		case 9154:
			player.logout();
			break;

		case 21010:
			if (player.isBanking) {	
				player.takeAsNote = true;
			} else {
				player.getActionSender().sendMessage("You must be banking to do this!");
			}
			break;

		case 21011:
		if (player.isBanking) {
			player.takeAsNote = false;
		} else {
			player.getActionSender().sendMessage("You must be banking to do this!");
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
			if (player.playerName.equalsIgnoreCase("andrew")) {
				player.getActionSender().sendMessage("Fight mode = 0.");
			}
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
			if (player.playerName.equalsIgnoreCase("andrew")) {
				player.getActionSender().sendMessage("Fight mode = 1.");
			}
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
			if (player.playerName.equalsIgnoreCase("andrew")) {
				player.getActionSender().sendMessage("Fight mode = 2.");
			}
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
			if (player.playerName.equalsIgnoreCase("andrew")) {
				player.getActionSender().sendMessage("Fight mode = 3.");
			}
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
			if (ot == null) {
				player.getTrading().declineTrade();
				player.getActionSender().sendMessage(
						"Trade declined as the other player has disconnected.");
				break;
			}
			player.getPlayerAssistant().sendFrame126(
					"Waiting for other player...", 3431);
			ot.getPlayerAssistant().sendFrame126("Other player has accepted",
					3431);
			player.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : player.getTrading().offeredItems) {
				if (item.id > 0) {
					if (ot.getItemAssistant().freeSlots() < player.getTrading().offeredItems
							.size()) {
						player.getActionSender().sendMessage(
								ot.playerName
										+ " only has "
										+ ot.getItemAssistant().freeSlots()
										+ " free slots, please remove "
										+ (player.getTrading().offeredItems
												.size() - ot.getItemAssistant()
												.freeSlots()) + " items.");
						ot.getActionSender().sendMessage(
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
						player.getPlayerAssistant().sendFrame126(
								"Not enough inventory space...", 3431);
						ot.getPlayerAssistant().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						player.getPlayerAssistant().sendFrame126(
								"Waiting for other player...", 3431);
						ot.getPlayerAssistant().sendFrame126(
								"Other player has accepted", 3431);
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
				player.getActionSender().sendMessage(
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
					break;
				}
				ot1.getPlayerAssistant().sendFrame126(
						"Other player has accepted.", 3535);
				player.getPlayerAssistant().sendFrame126(
						"Waiting for other player...", 3535);
			}

			break;
		/* Player Options */
		case 74176:
			if (!player.mouseButton) {
				player.mouseButton = true;
				player.getPlayerAssistant().sendConfig(500, 1);
				player.getPlayerAssistant().sendConfig(170, 1);
			} else if (player.mouseButton) {
				player.mouseButton = false;
				player.getPlayerAssistant().sendConfig(500, 0);
				player.getPlayerAssistant().sendConfig(170, 0);
			}
			break;
		case 3189:
			if (player.splitChat == false) {
				player.getPlayerAssistant().sendConfig(502, 1);
				player.getPlayerAssistant().sendConfig(287, 1);
				player.splitChat = true;
			} else if (player.splitChat == true) {
				player.getPlayerAssistant().sendConfig(502, 0);
				player.getPlayerAssistant().sendConfig(287, 0);
				player.splitChat = false;
			}
			break;
		case 74180:
			if (!player.chatEffects) {
				player.chatEffects = true;
				player.getPlayerAssistant().sendConfig(501, 1);
				player.getPlayerAssistant().sendConfig(171, 0);
			} else {
				player.chatEffects = false;
				player.getPlayerAssistant().sendConfig(501, 0);
				player.getPlayerAssistant().sendConfig(171, 1);
			}
			break;
		case 74188:
			if (!player.acceptAid) {
				player.acceptAid = true;
				player.getPlayerAssistant().sendConfig(503, 1);
				player.getPlayerAssistant().sendConfig(427, 1);
			} else {
				player.acceptAid = false;
				player.getPlayerAssistant().sendConfig(503, 0);
				player.getPlayerAssistant().sendConfig(427, 0);
			}
			break;
		case 74192:
			if (!player.isRunning2) {
				player.isRunning2 = true;
				player.getPlayerAssistant().sendConfig(504, 1);
				player.getPlayerAssistant().sendConfig(173, 1);
			} else {
				player.isRunning2 = false;
				player.getPlayerAssistant().sendConfig(504, 0);
				player.getPlayerAssistant().sendConfig(173, 0);
			}
			break;
			
		//case 74201:// brightness1
		case 3138:
			LightSources.brightness1(player);
			break;

		//case 74203:// brightness2
		case 3140:
			LightSources.brightness2(player);
			break;

		//case 74204:// brightness3
		case 3142:
			LightSources.brightness3(player);
			break;

		//case 74205:// brightness4
		case 3144:
			LightSources.brightness4(player);
			break;

		case 74206:// area1
			player.getPlayerAssistant().sendConfig(509, 1);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 0);
			break;
		case 74207:// area2
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 1);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 0);
			break;
		case 74208:// area3
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 1);
			player.getPlayerAssistant().sendConfig(512, 0);
			break;
		case 74209:// area4
			player.getPlayerAssistant().sendConfig(509, 0);
			player.getPlayerAssistant().sendConfig(510, 0);
			player.getPlayerAssistant().sendConfig(511, 0);
			player.getPlayerAssistant().sendConfig(512, 1);
			break;

		case 24017:
			player.getPlayerAssistant().resetAutocast();
			player.getItemAssistant()
					.sendWeapon(
							player.playerEquipment[player.playerWeapon],
							ItemAssistant
									.getItemName(player.playerEquipment[player.playerWeapon]));
			break;

		}
	}

}
