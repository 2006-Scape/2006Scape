package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.Constants;
import com.rs2.game.content.combat.magic.CastRequirements;
import com.rs2.game.content.combat.magic.Enchanting.EnchantSpell;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Enchanting {
    // TODO: Add dragonstone drops occasionally, double points when enchanted

	private Player player;
    private int itemsEnchanted = 0;
    private int orbsDeposited = 0;

	public Enchanting(Player c) {
		this.player = c;
	}

	public void enchantItem(int itemID, int spellID) {
		EnchantSpell spell = EnchantSpell.forId(spellID);
		if (player.playerLevel[Constants.MAGIC] < spell.getLevelReq()) {
			player.getPacketSender().sendMessage("You need a magic level of at least " + spell.getLevelReq() + " to cast this spell.");
			return;
		}
		if(!CastRequirements.hasRunes(player, player.getEnchanting().getRequiredRunes(spell))){
			player.getPacketSender().sendMessage("You do not have enough runes to cast this spell.");
			return;
		}
        int index = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == itemID) {
                index = i;
            }
        }
        // Item not found, player trying to enchant a different item
        if (index < 0) {
			player.getPacketSender().sendMessage("You cannot enchant that item while here.");
            return;
        }

        int points = spell.getELevel();
        // If it's the 10th item enchanted, double the points
        if (++itemsEnchanted % 10 == 0) {
            points *= 2;
        }
        // If it's the bonus item, award 2 extra points
        points += index == bonus ? 2 : 0;

        player.enchantmentPoints += points;
		player.getItemAssistant().replaceItem(itemID, 6902);
		player.getPlayerAssistant().addSkillXP(spell.getXp() * 0.75, Constants.MAGIC);
		CastRequirements.deleteRunes(player, player.getEnchanting().getRequiredRunes(spell));
		player.startAnimation(spell.getAnim());
		player.gfx100(spell.getGFX());
	}

	public void deposit() {
        int orbs = player.getItemAssistant().getItemAmount(6902);
        player.getItemAssistant().deleteItem(6902, orbs);
        orbsDeposited += orbs;
        // reward the player with runes for every 20 orbs deposited
        while (orbsDeposited >= 20) {
            orbsDeposited -= 20;
            int reward = random.nextInt(rewards.length);
            player.getItemAssistant().addOrDropItem(rewards[reward], 3);
            player.getPacketSender().sendMessage("You are rewarded with 3 " + DeprecatedItems.getItemName(rewards[reward]) + "s.");
        }
        player.getPacketSender().sendMessage((20 - orbsDeposited) + " more Orbs until your next reward.");
	}

    public void clearItems() {
        for (int item: items) {
            player.getItemAssistant().deleteItem(item, Integer.MAX_VALUE);
        }
        player.getItemAssistant().deleteItem(6902, Integer.MAX_VALUE);
        player.getItemAssistant().deleteItem(6903, Integer.MAX_VALUE);
    }

    /* ITEMS */
    // 6898 - Green Cylinder
    // 6899 - Yellow Cube
    // 6900 - Blue Icosahedron
    // 6901 - Red Pentamid
    // 6902 - Orb
    // 6903 - Dragonstone

    /* OBJECTS */
    // 10799 - Yellow Cube Pile
    // 10800 - Green Cylinder Pile
    // 10801 - Blue Icosahedron Pile
    // 10802 - Red Pentamid Pile
    // 10803 - Deposit Hole

    /* INTERFACES */
    // 15917 - Enchantment training arena interface
    // 15921 - How many enchantment points they have
    // 15922 -> 15925 - Bonus object frame (not sure how to get it to show)
    // 15926 -> 15929 - Bonus object (not sure how to get it to show)

    public static int ticks = 0;
    public static int[] items = {6899, 6898, 6901, 6900};
    public static int bonus = 0;
    public static int[] rewards = {560, 564, 565};
    private static Random random = new Random();

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
        // Every 71 ticks, randomize the order and such
        if (++ticks % 71 == 0) {
            bonus = random.nextInt(items.length);
            for (int i = 0; i < NpcHandler.MAX_NPCS; i ++) {
                if (NpcHandler.npcs[i] != null && NpcHandler.npcs[i].npcType == 3099) {
                    NpcHandler.npcs[i].forceChat("Items are changing!");
                }
            }
            for (Player p : PlayerHandler.players) {
                if (p == null) {
                    continue;
                }
                updateInterface(p);
            }
        }
	}

    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ENCHANTING)) {
            return;
        }
        int startInterface = 15922;
        for (int i = 0; i < items.length; i++) {
            // Show the bonus item
            player.getPacketSender().sendHideInterfaceLayer(startInterface + i, bonus != i);
        }
        player.getPacketSender().sendString("" + player.enchantmentPoints, 15921);
        player.getPacketSender().walkableInterface(15917);
    }
}
