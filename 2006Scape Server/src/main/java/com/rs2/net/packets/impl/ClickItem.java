package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.impl.ItemFirstClickEvent;
import com.rs2.game.content.consumables.Beverages;
import com.rs2.game.content.consumables.Kebabs;
import com.rs2.game.content.consumables.Beverages.beverageData;
import com.rs2.game.content.consumables.Food;
import com.rs2.game.content.minigames.TreasureTrails;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.skills.herblore.Herblore;
import com.rs2.game.content.skills.prayer.Prayer;
import com.rs2.game.content.skills.woodcutting.BirdNest;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.impl.ExperienceLamp;
import com.rs2.game.items.impl.Flowers;
import com.rs2.game.items.impl.GodBooks;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.util.GameLogger;
import com.rs2.util.Misc;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		player.endCurrentTask();
		packet.readSignedWordBigEndianA();
		int itemSlot = packet.readUnsignedWordA();
		int itemId = packet.readUnsignedWordBigEndian();
		if (itemId != player.playerItems[itemSlot] - 1) {
			return;
		}
		if(!player.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		player.post(new ItemFirstClickEvent(itemId));
		GodBooks.sendPreachOptions(player, itemId);
		if (itemId == 6) {
			player.getCannon().placeCannon();
		}
		String itemName = DeprecatedItems.getItemName(itemId).toLowerCase();
		if (player.getPlayerAssistant().isPlayer()) {
			GameLogger.writeLog(player.playerName, "clickitem", player.playerName + " clicked item " + itemName + "");
		}
		if (!CastleWars.deleteCastleWarsItems(player, itemId)) {
			return;
		}
		if(CastleWars.isInCw(player) && itemId == 4053) {
			player.getItemAssistant().deleteItem(4053, player.getItemAssistant().getItemSlot(4053), 1);
			//npc id, x, y, height, walk, hp, maxhit, att, def
			GameEngine.npcHandler.spawnNpc2(1532, player.absX, player.absY, player.heightLevel, 0, 200, 0, 0, 100, false);
			player.getPacketSender().sendMessage("You setup a barricade.");
		} else if (!CastleWars.isInCw(player) && itemId == 4053) {
			player.getPacketSender().sendMessage("You need to be in castlewars to drop a barricade.");
			player.getItemAssistant().deleteItem(itemId, player.getItemAssistant().getItemAmount(itemId));
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509) {
				pouch = 0;
			}
			if (a == 5510) {
				pouch = 1;
			}
			if (a == 5512) {
				pouch = 2;
			}
			if (a == 5514) {
				pouch = 3;
			}
			player.getPlayerAssistant().fillPouch(pouch);
			return;
		}
		switch (itemId) {
		case 4251:
			player.getPlayerAssistant().movePlayer(3661, 3526, 0);
			player.getItemAssistant().replaceItem(4251, 4252);
			break;
		case 4079:
			player.startAnimation(1457);
			break;
		case 407:
			int rand = Misc.random(1, 5);
			if (rand <= 2) {
				player.getItemAssistant().replaceItem(407, 409);
			} else if (rand <= 4) {
				player.getItemAssistant().replaceItem(407, 411);
			} else {
				player.getItemAssistant().replaceItem(407, 413);
			}
		break;
		
		case 2329:
			player.getItemAssistant().deleteItem(2329, 1);
			player.getItemAssistant().addItem(2313, 1);
		break;
		
		case 550:
			player.getPlayerAssistant().showMap();
		break;
		
		  case 583:
	            GameEngine.trawler.bail(player);
	            break;
	        case 585:
	            GameEngine.trawler.emptyBucket(player);
	            break;

		case 33:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(36, 1);
			break;

		case 32:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(38, 1);
			break;

		case 594:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(596, 1);
			break;

		case 4550:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(4548, 1);
			break;

		case 4537:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(4539, 1);
			break;

		case 1971:
			Kebabs.eat(player, itemSlot);
			break;

		case 2528:
		case 4447:
			ExperienceLamp.rubLamp(player, itemId);
			break;
			
		case 2677:
			if (Constants.CLUES_ENABLED) {
				player.getItemAssistant().deleteItem(itemId, 1);
				TreasureTrails.addClueReward(player, 0);
			}
			break;

		case 2678:
			if (Constants.CLUES_ENABLED) {
				player.getItemAssistant().deleteItem(itemId, 1);
				TreasureTrails.addClueReward(player, 1);
			}
			break;

		case 2679:
			if (Constants.CLUES_ENABLED) {
				player.getItemAssistant().deleteItem(itemId, 1);
				TreasureTrails.addClueReward(player, 2);
			}
			break;

		case 299:
			new Flowers(player);
			player.dialogueAction = 21;
			break;

		case 4155:// enchanted gem
			player.getDialogueHandler().sendOption("How many kills do I have left?",
					"Who are you?", "Where are you located?",
					"How many slayer points do I have?");
			player.dialogueAction = 145;
			break;

		case 3691:
			player.getItemAssistant().deleteItem(3691, 1);
			player.getItemAssistant().addItem(3690, 1);
			player.getPlayerAssistant().startTeleport(2661, 3310, 0, "modern");
			break;

		// case 2528:
		// ExperienceLamp.showInterface(c);
		// break;

		case 5070:
			if (player.getItemAssistant().freeSlots() >= 2) {
				player.getPacketSender().sendMessage("You search the nest.");
				player.getItemAssistant().addItem(5076, 1);
				player.getItemAssistant().deleteItem(itemId, 1);
				player.getItemAssistant().addItem(5075, 1);
			} else {
				player.getPacketSender().sendMessage("You do not have enough inventory space to do that.");
			}
			break;

		case 5071:
		if (player.getItemAssistant().freeSlots() >= 2) {
			player.getPacketSender().sendMessage("You search the nest.");
			player.getItemAssistant().addItem(5078, 1);
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(5075, 1);
		} else {
			player.getPacketSender().sendMessage("You do not have enough inventory space to do that.");
		}
		break;

		case 5072:
			if (player.getItemAssistant().freeSlots() >= 2) {
				player.getPacketSender().sendMessage("You search the nest.");
				player.getItemAssistant().addItem(5077, 1);
				player.getItemAssistant().deleteItem(itemId, 1);
				player.getItemAssistant().addItem(5075, 1);
			} else {
				player.getPacketSender().sendMessage("You do not have enough inventory space to do that.");
			}
		break;
		
		case 5073:
			BirdNest.handleBirdNest(player, itemId, 0);
			break;

		case 5074:
			BirdNest.handleBirdNest(player, itemId, 1);
			break;
			
		case 7413:
			BirdNest.handleBirdNest(player, itemId, 2);
			break;

		case 2297:
			player.getItemAssistant().addItem(2299, 1);
			player.getItemAssistant().deleteItem(2297, 1);
			break;

		case 2299:
			player.getItemAssistant().deleteItem(2299, 1);
			break;

		case 2301:
			player.getItemAssistant().addItem(2303, 1);
			player.getItemAssistant().deleteItem(2301, 1);
			break;

		case 2303:
			player.getItemAssistant().deleteItem(2303, 1);
			break;

		case 2520:
			player.forcedChat("Come on Dobbin, we can win the race!");
			player.startAnimation(918);
			break;
		case 2522:
			player.forcedChat("Come on Dobbin, we can win the race!");
			player.startAnimation(919);
			break;
		case 2524:
			player.forcedChat("Hi-ho Silver, and away!");
			player.startAnimation(920);
			break;
		case 2526:
			player.forcedChat("Neahhhyyy! Giddy-up horsey!");
			player.startAnimation(921);
			break;

		case 405:
			player.getItemAssistant().addCasketRewards(itemId);
			break;

		case 433:
			player.getDialogueHandler().sendStatement("Visit the city of the white knights.");
			player.nextChat = 0;
			player.isBanking = false;
			break;

		case 2714:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(995, 450);
			player.getItemAssistant().addItem(1639, 1);
			player.getItemAssistant().addItem(1635, 1);
			player.pirateTreasure = 6;
			break;
		}
		
		for (final beverageData b : beverageData.values()) {
			if (itemId == b.getBev()) {
				Beverages.drinkBeverage(player, itemId, itemSlot);
			}
		}

		if (Food.isFood(itemId)) {
			Food.eat(player, itemId, itemSlot);
		}
		if (Prayer.playerBones(player, itemId)) {
			Prayer.buryBones(player, itemId, itemSlot);
		}
		if (player.getPotions().isPotion(itemId)) {
			player.getPotions().handlePotion(itemId, itemSlot);
		}
		if (Herblore.isHerb(itemId)) {
			Herblore.handleHerbCleaning(player, itemId, itemSlot);
			return;
		}
		if (itemId == 952) {
			player.getBarrows().spadeDigging();
			return;
		}
	}

}
