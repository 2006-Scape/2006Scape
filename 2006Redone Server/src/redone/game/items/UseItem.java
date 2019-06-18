package redone.game.items;

import redone.game.content.skills.cooking.Cooking;
import redone.game.content.skills.crafting.GemCutting;
import redone.game.content.skills.crafting.JewelryMaking;
import redone.game.content.skills.crafting.LeatherMaking;
import redone.game.content.skills.crafting.SoftClay;
import redone.game.content.skills.firemaking.Firemaking;
import redone.game.content.skills.fletching.ArrowMaking;
import redone.game.content.skills.fletching.LogCutting;
import redone.game.content.skills.fletching.LogCuttingInterface;
import redone.game.content.skills.fletching.Stringing;
import redone.game.content.skills.herblore.GrindingAction;
import redone.game.content.skills.herblore.Herblore;
import redone.game.items.impl.CapeDye;
import redone.game.items.impl.WeaponPoison;
import redone.game.npcs.impl.MilkCow;
import redone.game.objects.impl.CrystalChest;
import redone.game.objects.impl.FlourMill;
import redone.game.objects.impl.Webs;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author Ryan / Lmctruck30
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX,
			int objectY, int itemId) {
		final int goodPosXType1 = objectX - 5;
		final int goodPosXType2 = objectX + 5;
		final int goodPosYType1 = objectY - 5;
		final int goodPosYType2 = objectY + 5;
		if (c.absX >= goodPosXType1 && c.absX <= goodPosXType2 && c.absY >= goodPosYType1 && c.absY <= goodPosYType2) {
			c.turnPlayerTo(objectX, objectY);
		} else {
			c.getPlayerAssistant().playerWalk(objectX, objectY);
		}
		if (!c.getItemAssistant().playerHasItem(itemId, 1)) {
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
				c.getActionSender().sendMessage(
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
				c.getItemAssistant().deleteItem2(1919, 1);
				c.getItemAssistant().addItem(1917, 1);
				c.getActionSender().sendMessage(
						"You refill your beer glass.");
			}
			break;

		default:
			if (c.playerRights == 3) {
				Misc.println("Player At Object id: " + objectID + " objectX: "
						+ objectX + " objectY: " + objectY + " with Item id: "
						+ itemId);
			}
			break;
		}

	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		LogCuttingInterface.handleItemOnItem(c, itemUsed, useWith);
		ArrowMaking.makeArrow(c, itemUsed, useWith);
		Stringing.StringBow(c, itemUsed, useWith);
		WeaponPoison.execute(c, itemUsed, useWith);
		c.getGlassBlowing().ItemOnItem(itemUsed, useWith);
		//CapeDye.execute(c, itemUsed, useWith);
		if (ItemAssistant.getItemName(itemUsed).contains("(")
				&& ItemAssistant.getItemName(useWith).contains("(")) {
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		}
		GrindingAction.init(c, itemUsed, useWith);
		CapeDye.capeOnDye(c, itemUsed, useWith);
		if (Herblore.isIngredient(itemUsed) || Herblore.isIngredient(useWith)) {
			Herblore.setupPotion(c, itemUsed, useWith);
		}
		if (itemUsed == 6703 || useWith == 6703) {
			c.getPTS().handlePotato(itemUsed, useWith);
		}
		if (itemUsed == 1755 || useWith == 1755) {
			GemCutting.cutGem(c, itemUsed, useWith);
		}
		if (itemUsed == 1759 || useWith == 1759) {
			JewelryMaking.stringAmulet(c, itemUsed, useWith);
		}
		if (itemUsed == 946 || useWith == 2862) {
			LogCutting.makeShafts(c);
		}
		if (itemUsed == 2862 || useWith == 946) {
			LogCutting.makeShafts(c);
		}
		if (itemUsed == 314 || useWith == 2864) {
			LogCutting.flightedArrow(c);
		}
		if (itemUsed == 2864 || useWith == 314) {
			LogCutting.flightedArrow(c);
		}
		if (itemUsed == 2861 || useWith == 2865) {
			LogCutting.ogreArrow(c);
		}
		if (itemUsed == 2865 || useWith == 2861) {
			LogCutting.ogreArrow(c);
		}
		if (itemUsed == 2859 || useWith == 1755) {
			LogCutting.wolfBoneArrow(c);
		}
		if (itemUsed == 1755 || useWith == 2859) {
			LogCutting.wolfBoneArrow(c);
		}
		if (itemUsed == 771 && useWith == 946
				&& c.playerLevel[c.playerCrafting] > 30) {
			c.getItemAssistant().deleteItem(771, 1);
			c.getItemAssistant().addItem(772, 1);
		} else if (c.playerLevel[c.playerCrafting] < 31 && itemUsed == 771
				&& useWith == 946) {
			c.getActionSender().sendMessage(
					"You need 31 crafting to make this.");
		}
		if (itemUsed == 946 && useWith == 771
				&& c.playerLevel[c.playerCrafting] > 30) {
			c.getItemAssistant().deleteItem(771, 1);
			c.getItemAssistant().addItem(772, 1);
		} else if (c.playerLevel[c.playerCrafting] < 31 && itemUsed == 946
				&& useWith == 771) {
			c.getActionSender().sendMessage(
					"You need 31 crafting to make this.");
		}
		if (useWith == 7051 && itemUsed == 590 || itemUsed == 590
				&& useWith == 7051) {
			c.getItemAssistant().deleteItem2(7051, 1);
			c.getItemAssistant().addItem(7053, 1);
		}
		int firemakingItems[] = {590, 7329, 7330, 7331};
		for (int i = 0; i < firemakingItems.length; i++) {
			if (itemUsed == firemakingItems[i] || useWith == firemakingItems[i] && c.isFiremaking == false) {
				Firemaking.attemptFire(c, itemUsed, useWith, c.absX, c.absY, false);
			} else if (itemUsed == firemakingItems[i] || useWith == firemakingItems[i] && c.isFiremaking == true) {
				c.getActionSender().sendMessage("You can't do that, you are already firemaking.");
			}
		}
		if (itemUsed == 1733 || useWith == 1733) {
			LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
		}
		if (itemUsed == 1573 && useWith == 327 || itemUsed == 327 && useWith == 1573) {
			c.getItemAssistant().deleteItem2(1573, 1);
			c.getItemAssistant().deleteItem2(327, 1);
			c.getItemAssistant().addItem(1552, 1);
		}
		if (itemUsed == 38 && useWith == 590 || useWith == 38
				&& itemUsed == 590) {//
			c.getItemAssistant().addItem(32, 1);
			c.getItemAssistant().deleteItem2(38, 1);
		}
		if (itemUsed == 36 && useWith == 590 || useWith == 36
				&& itemUsed == 590) {
			c.getItemAssistant().addItem(33, 1);
			c.getItemAssistant().deleteItem2(36, 1);
		}
		if (itemUsed == 596 && useWith == 590 || useWith == 596
				&& itemUsed == 590) {
			c.getItemAssistant().addItem(594, 1);
			c.getItemAssistant().deleteItem2(596, 1);
		}
		if (itemUsed == 4537 && useWith == 590 || useWith == 4537
				&& itemUsed == 590) {
			c.getItemAssistant().addItem(4539, 1);
			c.getItemAssistant().deleteItem2(4537, 1);
		}
		if (itemUsed == 4548 && useWith == 590 || useWith == 4548
				&& itemUsed == 590) {
			c.getItemAssistant().addItem(4550, 1);
			c.getItemAssistant().deleteItem2(4548, 1);
		}
		if (itemUsed == 1095 && useWith == 2370 || itemUsed == 2370
				&& useWith == 1095 && c.playerLevel[c.playerCrafting] > 43) {// chaps
			c.getItemAssistant().deleteItem2(2370, 1);
			c.getItemAssistant().deleteItem2(1095, 1);
			c.getItemAssistant().addItem(1097, 1);
			c.getPlayerAssistant().addSkillXP(42, c.playerCrafting);
		} else if (itemUsed == 1095 && useWith == 2370 || itemUsed == 2370 && useWith == 1095 && c.playerLevel[c.playerCrafting] < 44) {
			c.getActionSender().sendMessage("You need 44 crafting to make this.");
		}
		if (itemUsed == 946 && useWith == 1963 || itemUsed == 1963  && useWith == 946) {
			c.getItemAssistant().deleteItem2(1963, 1);
			c.getItemAssistant().addItem(3162, 1);
			c.getActionSender().sendMessage("You slice your banana.");
		}
		if (itemUsed == 946 && useWith == 1973 || itemUsed == 1973 && useWith == 946) {
			c.getItemAssistant().deleteItem2(1973, 1);
			c.getItemAssistant().addItem(1975, 1);
			c.getActionSender().sendMessage("You slice your chocolate bar.");
		}
		if (itemUsed == 1129 && useWith == 2370 || itemUsed == 2370 && useWith == 1129 && c.playerLevel[c.playerCrafting] > 40) {// body
			c.getItemAssistant().deleteItem2(2370, 1);
			c.getItemAssistant().deleteItem2(1129, 1);
			c.getItemAssistant().addItem(1133, 1);
			c.getPlayerAssistant().addSkillXP(40, c.playerCrafting);
		} else if (itemUsed == 1129 && useWith == 2370 || itemUsed == 2370 && useWith == 1129 && c.playerLevel[c.playerCrafting] < 41) {
			c.getActionSender().sendMessage("You need 41 crafting to make this.");
		}
		if (itemUsed == 4593 && useWith == 4591 || useWith == 4591 && itemUsed == 4593) {
			c.getItemAssistant().deleteItem2(4591, 1);
			c.getItemAssistant().deleteItem2(4593, 1);
			c.getItemAssistant().addItem(4611, 1);
		}
		if (itemUsed == 985 && useWith == 987 || itemUsed == 987 && useWith == 985) {
			c.getItemAssistant().deleteItem2(985, 1);
			c.getItemAssistant().deleteItem2(987, 1);
			c.getItemAssistant().addItem(989, 1);
		}
		if (itemUsed == 2313 && useWith == 1953 || itemUsed == 1953 && useWith == 2313) {
			c.getItemAssistant().deleteItem2(2313, 1);
			c.getItemAssistant().deleteItem2(1953, 1);
			c.getItemAssistant().addItem(2315, 1);
		}

		/**
		 * Pizza Creation
		 */
		if (itemUsed == 1982 && useWith == 2283 || itemUsed == 2283
				&& useWith == 1982) {
			Cooking.pastryCreation(c, 1982, 2283, 2285, "");
		}
		if (itemUsed == 2285 && useWith == 1985 || itemUsed == 1985
				&& useWith == 2285) {
			Cooking.pastryCreation(c, 2285, 1985, 2287, "");
		}
		if (itemUsed == 2140 && useWith == 2289 || itemUsed == 2289
				&& useWith == 2140) {
			Cooking.cookingAddon(c, 2140, 2289, 2293, 45, 26);
		}
		if (itemUsed == 319 && useWith == 2289 || itemUsed == 2289
				&& useWith == 319) {
			Cooking.cookingAddon(c, 319, 2289, 2297, 55, 39);
		}
		if (itemUsed == 2116 && useWith == 2289 || itemUsed == 2289
				&& useWith == 2116) {
			Cooking.cookingAddon(c, 2116, 2289, 2301, 65, 45);
		}
		/**
		 * Pie Making
		 */
		if (itemUsed == 2313 && useWith == 1953 || itemUsed == 1953
				&& useWith == 2313) {
			Cooking.pastryCreation(c, 2313, 1953, 2315,
					"You put the pastry dough into the pie dish to make a pie shell.");
		}
		if (itemUsed == 2315 && useWith == 1955 || itemUsed == 1955
				&& useWith == 2315) {
			Cooking.pastryCreation(c, 2315, 1955, 2317,
					"You fill the pie with cooking apple.");
		}
		if (itemUsed == 2315 && useWith == 5504 || itemUsed == 5504
				&& useWith == 2315) {
			Cooking.pastryCreation(c, 2315, 5504, 7212, "");
		}
		if (itemUsed == 7212 && useWith == 5982 || itemUsed == 5982
				&& useWith == 7212) {
			Cooking.pastryCreation(c, 7212, 5982, 7214, "");
		}
		if (itemUsed == 1955 && useWith == 7214 || itemUsed == 7214
				&& useWith == 1955) {
			Cooking.pastryCreation(c, 1955, 7214, 7216, "");
		}
		if (itemUsed == 2315 && useWith == 1951 || itemUsed == 1951 && useWith == 2315) {
			Cooking.pastryCreation(c, 1951, 2315, 2321, "");
		}
		/**
		 * Pitta/ Ugthanki Kebab
		 */
		if (itemUsed == 1865 && useWith == 1881 || itemUsed == 1881
				&& useWith == 1865) {
			Cooking.cookingAddon(c, 1865, 1881, 1883, 0, 40);
		}
		
		if (c.tutorialProgress < 36) {
			if (itemUsed == 1929 && useWith == 1933 || itemUsed == 1933 && useWith == 1929) {
				c.getItemAssistant().deleteItem(1929, 1);
				c.getItemAssistant().deleteItem(1933, 1);
				c.getItemAssistant().addItem(2307, 1);
				c.getItemAssistant().addItem(1925, 1);
				c.getItemAssistant().addItem(1931, 1);
				if (c.tutorialProgress == 8) {
					c.getDialogueHandler().sendDialogues(3026, -1);
				}
			}
		}
		
		if (c.tutorialProgress > 35) {
			if (itemUsed == 1929 && useWith == 1933 || itemUsed == 1933 && useWith == 1929) {
				c.getDialogueHandler().sendDialogues(3204, -1);
			}
		}
		
		if (c.tutorialProgress > 35) {
			if (itemUsed == 1933 && useWith == 1937 || itemUsed == 1937 && useWith == 1933) {
				c.getDialogueHandler().sendDialogues(3205, -1);
			}
		}

		if (itemUsed == 1987 && useWith == 1937 || itemUsed == 1937
				&& useWith == 1987) {
			if (c.playerLevel[c.playerCooking] >= 35) {
				c.getItemAssistant().addItem(1993, 1);
				c.getItemAssistant().deleteItem(1937, 1);
				c.getItemAssistant().deleteItem2(1987, 1);
				c.getPlayerAssistant().addSkillXP(200, c.playerCooking);
			} else {
				c.getActionSender().sendMessage(
						"You need grapes and a jug of water to make wine.");
			}
		}

		switch (itemUsed) {

		default:
			if (c.playerRights == 3) {
				Misc.println("Player used Item id: " + itemUsed
						+ " with Item id: " + useWith);
			}
			break;
		}
	}

	public static void ItemonNpc(final Client c, final int itemId,
			final int npcId, final int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3) {
				Misc.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			}
			break;
		}

	}
}
