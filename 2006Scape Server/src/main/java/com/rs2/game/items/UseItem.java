package com.rs2.game.items;

import com.rs2.Constants;
import com.rs2.event.impl.ItemOnItemEvent;
import com.rs2.game.content.skills.cooking.Cooking;
import com.rs2.game.content.skills.crafting.BattleStaffs;
import com.rs2.game.content.skills.crafting.GemCutting;
import com.rs2.game.content.skills.crafting.JewelryMaking;
import com.rs2.game.content.skills.crafting.LeatherMaking;
import com.rs2.game.content.skills.crafting.SoftClay;
import com.rs2.game.content.skills.farming.Farming;
import com.rs2.game.content.skills.firemaking.Firemaking;
import com.rs2.game.content.skills.fletching.ArrowMaking;
import com.rs2.game.content.skills.fletching.LogCutting;
import com.rs2.game.content.skills.fletching.LogCuttingInterface;
import com.rs2.game.content.skills.fletching.Stringing;
import com.rs2.game.content.skills.fletching.TipMaking;
import com.rs2.game.content.skills.herblore.GrindingAction;
import com.rs2.game.content.skills.herblore.Herblore;
import com.rs2.game.content.skills.prayer.Ectofuntus;
import com.rs2.game.content.skills.prayer.Ectofuntus.EctofuntusData;
import com.rs2.game.content.skills.runecrafting.Tiaras;
import com.rs2.game.items.impl.Dye;
import com.rs2.game.items.impl.GodPages;
import com.rs2.game.items.impl.WeaponPoison;
import com.rs2.game.npcs.impl.MilkCow;
import com.rs2.game.objects.impl.CrystalChest;
import com.rs2.game.objects.impl.FlourMill;
import com.rs2.game.objects.impl.Webs;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * @author Ryan / Lmctruck30
 */

public class UseItem {

	public static void itemOnObject(Player c, int objectID, int objectX, int objectY, int itemId) {
		final int minX = objectX - 5;
		final int maxX = objectX + 5;
		final int minY = objectY - 5;
		final int maxY = objectY + 5;
		if (c.absX >= minX && c.absX <= maxX && c.absY >= minY && c.absY <= maxY) {
			c.turnPlayerTo(objectX, objectY);
		} else {
			c.getPlayerAssistant().playerWalk(objectX, objectY);
		}
		if (!c.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		if (itemId == Ectofuntus.BUCKET) {
			Ectofuntus.fillBucketWithSlime(c, objectID);
		}
		if (Farming.prepareCrop(c, itemId, objectID, objectX, objectY)) {
			return;
		}
		for (final EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (itemId == ectofuntus.getBoneId()) {
				Ectofuntus.boneOnLoader(c, objectID, itemId);
			}
		}
		if (Tiaras.bindTiara(c, itemId, objectID)) {
			return;
		}
		switch (objectID) {
		case 733:
		if (itemId == 946) {
			Webs.slashWeb(c, objectID, objectX, objectY);
		}
		break;
		case 8689:
			if (c.milking == false) {
				MilkCow.milk(c);
			}
			break;
		case 2783:
			c.getSmithingInt().showSmithInterface(itemId);
			break;
		case 2782:
			if (c.doricQuest >= 3) {
				c.getSmithingInt().showSmithInterface(itemId);
			} else {
				c.getPacketSender().sendMessage(
						"You need to beat dorics quest to use his anvils");
			}
			break;
		case 879:
		case 12279:
		case 14868:
			if (itemId == SoftClay.CLAY) {
				SoftClay.makeClay(c);
			}
			break;
		case 2714:
			FlourMill.grainOnHopper(c, objectID, itemId);
			break;
		case 172:
			if (itemId == CrystalChest.KEY) {
				CrystalChest.searchChest(c, objectID, objectX, objectY);
			}
			break;

		case 364:
			if (itemId == 1919) {
				c.getItemAssistant().deleteItem(1919, 1);
				c.getItemAssistant().addItem(1917, 1);
				c.getPacketSender().sendMessage(
						"You refill your beer glass.");
			}
			break;

		default:
			if (c.playerRights == 3) {
				System.out.println("Player At Object id: " + objectID + " objectX: "
						+ objectX + " objectY: " + objectY + " with Item id: "
						+ itemId);
			}
			break;
		}

	}

	public static void itemOnItem(Player player, int itemUsed, int useWith) {
		player.post(new ItemOnItemEvent(itemUsed, useWith));
		player.post(new ItemOnItemEvent(useWith, itemUsed));
		LogCuttingInterface.handleItemOnItem(player, itemUsed, useWith);
		ArrowMaking.makeArrow(player, itemUsed, useWith);
		Stringing.StringBow(player, itemUsed, useWith);
		TipMaking.makeTips(player, itemUsed, useWith);
		WeaponPoison.execute(player, itemUsed, useWith);
		player.getGlassBlowing().ItemOnItem(itemUsed, useWith);
		//CapeDye.execute(c, itemUsed, useWith);
		if (DeprecatedItems.getItemName(itemUsed).contains("(")
				&& DeprecatedItems.getItemName(useWith).contains("(")) {
			player.getPotMixing().mixPotion2(itemUsed, useWith);
		}
		BattleStaffs.makeBattleStaff(player, itemUsed, useWith);
		GrindingAction.init(player, itemUsed, useWith);
		Dye.dyeItem(player, itemUsed, useWith);
		for (final int[] element : Dye.MAIL_DATA) {
			if (itemUsed == element[0] && useWith == element[1] || itemUsed == element[1] && useWith == element[0]) {
				player.getItemAssistant().deleteItem(element[0], 1);
				player.getItemAssistant().deleteItem(element[1], 1);
				player.getItemAssistant().addItem(element[2], 1);
			}
		}
		GodPages.itemOnItemHandle(player, useWith, itemUsed);
		if (Herblore.isIngredient(itemUsed) || Herblore.isIngredient(useWith)) {
			Herblore.setupPotion(player, itemUsed, useWith);
		}
		if (itemUsed == 6703 || useWith == 6703) {
			player.getPTS().handlePotato(itemUsed, useWith);
		}
		if (itemUsed == 1755 || useWith == 1755) {
			GemCutting.cutGem(player, itemUsed, useWith);
		}
		if (itemUsed == 1759 || useWith == 1759) {
			JewelryMaking.stringAmulet(player, itemUsed, useWith);
		}
		if (itemUsed == 946 || useWith == 2862) {
			LogCutting.makeShafts(player);
		}
		if (itemUsed == 2862 || useWith == 946) {
			LogCutting.makeShafts(player);
		}
		if (itemUsed == 314 || useWith == 2864) {
			LogCutting.flightedArrow(player);
		}
		if (itemUsed == 2864 || useWith == 314) {
			LogCutting.flightedArrow(player);
		}
		if (itemUsed == 2861 || useWith == 2865) {
			LogCutting.ogreArrow(player);
		}
		if (itemUsed == 2865 || useWith == 2861) {
			LogCutting.ogreArrow(player);
		}
		if (itemUsed == 2859 || useWith == 1755) {
			LogCutting.wolfBoneArrow(player);
		}
		if (itemUsed == 1755 || useWith == 2859) {
			LogCutting.wolfBoneArrow(player);
		}
		if (itemUsed == 771 && useWith == 946
				&& player.playerLevel[Constants.CRAFTING] > 30) {
			player.getItemAssistant().deleteItem(771, 1);
			player.getItemAssistant().addItem(772, 1);
		} else if (player.playerLevel[Constants.CRAFTING] < 31 && itemUsed == 771
				&& useWith == 946) {
			player.getPacketSender().sendMessage(
					"You need 31 crafting to make this.");
		}
		if (itemUsed == 946 && useWith == 771
				&& player.playerLevel[Constants.CRAFTING] > 30) {
			player.getItemAssistant().deleteItem(771, 1);
			player.getItemAssistant().addItem(772, 1);
		} else if (player.playerLevel[Constants.CRAFTING] < 31 && itemUsed == 946
				&& useWith == 771) {
			player.getPacketSender().sendMessage(
					"You need 31 crafting to make this.");
		}
		if (useWith == 7051 && itemUsed == 590 || itemUsed == 590
				&& useWith == 7051) {
			player.getItemAssistant().deleteItem(7051, 1);
			player.getItemAssistant().addItem(7053, 1);
		}
		int firemakingItems[] = {590, 7329, 7330, 7331};
		for (int i = 0; i < firemakingItems.length; i++) {
			if (itemUsed == firemakingItems[i] || useWith == firemakingItems[i] && player.isFiremaking == false) {
				Firemaking.attemptFire(player, itemUsed, useWith, player.absX, player.absY, false);
			} else if (itemUsed == firemakingItems[i] || useWith == firemakingItems[i] && player.isFiremaking) {
				player.getPacketSender().sendMessage("You can't do that, you are already firemaking.");
			}
		}
		if (itemUsed == 1733 || useWith == 1733) {
			LeatherMaking.craftLeatherDialogue(player, itemUsed, useWith);
		}
		if (itemUsed == 1573 && useWith == 327 || itemUsed == 327 && useWith == 1573) {
			player.getItemAssistant().deleteItem(1573, 1);
			player.getItemAssistant().deleteItem(327, 1);
			player.getItemAssistant().addItem(1552, 1);
		}
		/*f (itemUsed == 38 && useWith == 590 || useWith == 38
				&& itemUsed == 590) {//
			player.getItemAssistant().addItem(32, 1);
			player.getItemAssistant().deleteItem(38, 1);
		}*/
		if (itemUsed == 36 && useWith == 590 || useWith == 36
				&& itemUsed == 590) {
			player.getItemAssistant().addItem(33, 1);
			player.getItemAssistant().deleteItem(36, 1);
		}
		if (itemUsed == 596 && useWith == 590 || useWith == 596
				&& itemUsed == 590) {
			player.getItemAssistant().addItem(594, 1);
			player.getItemAssistant().deleteItem(596, 1);
		}
		if (itemUsed == 4537 && useWith == 590 || useWith == 4537
				&& itemUsed == 590) {
			player.getItemAssistant().addItem(4539, 1);
			player.getItemAssistant().deleteItem(4537, 1);
		}
		if (itemUsed == 4548 && useWith == 590 || useWith == 4548
				&& itemUsed == 590) {
			player.getItemAssistant().addItem(4550, 1);
			player.getItemAssistant().deleteItem(4548, 1);
		}
		if (itemUsed == 1095 && useWith == 2370 || itemUsed == 2370
				&& useWith == 1095 && player.playerLevel[Constants.CRAFTING] > 43) {// chaps
			player.getItemAssistant().deleteItem(2370, 1);
			player.getItemAssistant().deleteItem(1095, 1);
			player.getItemAssistant().addItem(1097, 1);
			player.getPlayerAssistant().addSkillXP(42, Constants.CRAFTING);
		} else if (itemUsed == 1095 && useWith == 2370 || itemUsed == 2370 && useWith == 1095 && player.playerLevel[Constants.CRAFTING] < 44) {
			player.getPacketSender().sendMessage("You need 44 crafting to make this.");
		}
		if (itemUsed == 946 && useWith == 1963 || itemUsed == 1963  && useWith == 946) {
			player.getItemAssistant().deleteItem(1963, 1);
			player.getItemAssistant().addItem(3162, 1);
			player.getPacketSender().sendMessage("You slice your banana.");
		}
		if (itemUsed == 946 && useWith == 1973 || itemUsed == 1973 && useWith == 946) {
			player.getItemAssistant().deleteItem(1973, 1);
			player.getItemAssistant().addItem(1975, 1);
			player.getPacketSender().sendMessage("You slice your chocolate bar.");
		}
		if (itemUsed == 1129 && useWith == 2370 || itemUsed == 2370 && useWith == 1129 && player.playerLevel[Constants.CRAFTING] > 40) {// body
			player.getItemAssistant().deleteItem(2370, 1);
			player.getItemAssistant().deleteItem(1129, 1);
			player.getItemAssistant().addItem(1133, 1);
			player.getPlayerAssistant().addSkillXP(40, Constants.CRAFTING);
		} else if (itemUsed == 1129 && useWith == 2370 || itemUsed == 2370 && useWith == 1129 && player.playerLevel[Constants.CRAFTING] < 41) {
			player.getPacketSender().sendMessage("You need 41 crafting to make this.");
		}
		if (itemUsed == 4593 && useWith == 4591 || useWith == 4591 && itemUsed == 4593) {
			player.getItemAssistant().deleteItem(4591, 1);
			player.getItemAssistant().deleteItem(4593, 1);
			player.getItemAssistant().addItem(4611, 1);
		}
		if (itemUsed == 985 && useWith == 987 || itemUsed == 987 && useWith == 985) {
			player.getItemAssistant().deleteItem(985, 1);
			player.getItemAssistant().deleteItem(987, 1);
			player.getItemAssistant().addItem(989, 1);
		}
		if (itemUsed == 2313 && useWith == 1953 || itemUsed == 1953 && useWith == 2313) {
			player.getItemAssistant().deleteItem(2313, 1);
			player.getItemAssistant().deleteItem(1953, 1);
			player.getItemAssistant().addItem(2315, 1);
		}

		/**
		 * Pizza Creation
		 */
		if (itemUsed == 1982 && useWith == 2283 || itemUsed == 2283
				&& useWith == 1982) {
			Cooking.pastryCreation(player, 1982, 2283, 2285, "");
		}
		if (itemUsed == 2285 && useWith == 1985 || itemUsed == 1985
				&& useWith == 2285) {
			Cooking.pastryCreation(player, 2285, 1985, 2287, "");
		}
		if (itemUsed == 2140 && useWith == 2289 || itemUsed == 2289
				&& useWith == 2140) {
			Cooking.cookingAddon(player, 2140, 2289, 2293, 45, 26);
		}
		if (itemUsed == 319 && useWith == 2289 || itemUsed == 2289
				&& useWith == 319) {
			Cooking.cookingAddon(player, 319, 2289, 2297, 55, 39);
		}
		if (itemUsed == 2116 && useWith == 2289 || itemUsed == 2289
				&& useWith == 2116) {
			Cooking.cookingAddon(player, 2116, 2289, 2301, 65, 45);
		}
		/**
		 * Pie Making
		 */
		if (itemUsed == 2313 && useWith == 1953 || itemUsed == 1953
				&& useWith == 2313) {
			Cooking.pastryCreation(player, 2313, 1953, 2315,
					"You put the pastry dough into the pie dish to make a pie shell.");
		}
		if (itemUsed == 2315 && useWith == 1955 || itemUsed == 1955
				&& useWith == 2315) {
			Cooking.pastryCreation(player, 2315, 1955, 2317,
					"You fill the pie with cooking apple.");
		}
		if (itemUsed == 2315 && useWith == 5504 || itemUsed == 5504
				&& useWith == 2315) {
			Cooking.pastryCreation(player, 2315, 5504, 7212, "");
		}
		if (itemUsed == 7212 && useWith == 5982 || itemUsed == 5982
				&& useWith == 7212) {
			Cooking.pastryCreation(player, 7212, 5982, 7214, "");
		}
		if (itemUsed == 1955 && useWith == 7214 || itemUsed == 7214
				&& useWith == 1955) {
			Cooking.pastryCreation(player, 1955, 7214, 7216, "");
		}
		if (itemUsed == 2315 && useWith == 1951 || itemUsed == 1951 && useWith == 2315) {
			Cooking.pastryCreation(player, 1951, 2315, 2321, "");
		}
		/**
		 * Pitta/ Ugthanki Kebab
		 */
		if (itemUsed == 1865 && useWith == 1881 || itemUsed == 1881
				&& useWith == 1865) {
			Cooking.cookingAddon(player, 1865, 1881, 1883, 0, 40);
		}
		
		if (player.tutorialProgress < 36) {
			if (itemUsed == 1929 && useWith == 1933 || itemUsed == 1933 && useWith == 1929) {
				player.getItemAssistant().deleteItem(1929, 1);
				player.getItemAssistant().deleteItem(1933, 1);
				player.getItemAssistant().addItem(2307, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1931, 1);
				if (player.tutorialProgress == 8) {
					player.getDialogueHandler().sendDialogues(3026, -1);
				}
			}
		}
		
		if (player.tutorialProgress > 35) {
			if (itemUsed == 1929 && useWith == 1933 || itemUsed == 1933 && useWith == 1929) {
				player.getDialogueHandler().sendDialogues(3204, -1);
			}
		}
		
		if (player.tutorialProgress > 35) {
			if (itemUsed == 1933 && useWith == 1937 || itemUsed == 1937 && useWith == 1933) {
				player.getDialogueHandler().sendDialogues(3205, -1);
			}
		}

		if (itemUsed == 1987 && useWith == 1937 || itemUsed == 1937
				&& useWith == 1987) {
			if (player.playerLevel[Constants.COOKING] >= 35) {
				player.getItemAssistant().addItem(1993, 1);
				player.getItemAssistant().deleteItem(1937, 1);
				player.getItemAssistant().deleteItem(1987, 1);
				player.getPlayerAssistant().addSkillXP(200, Constants.COOKING);
			} else {
				player.getPacketSender().sendMessage(
						"You need grapes and a jug of water to make wine.");
			}
		}

		if (player.playerRights == 3) {
			System.out.println("Player used Item id: " + itemUsed + " with Item id: " + useWith);
		}
	}

	public static void itemOnNpc(final Player c, final int itemId, final int npcId, final int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3) {
				System.out.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			}
			break;
		}

	}
}
