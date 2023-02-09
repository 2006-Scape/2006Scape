package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.Constants;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Graveyard {

	private Player player;
    private int bonesCollected = 0;
    private int fruitDeposited = 0;

	public Graveyard(Player c) {
		this.player = c;
	}

    public void searchBonePile(int objectID) {
        int index = (int) Math.floor(bonesCollected / 4);
        if (player.getItemAssistant().freeSlots(items[index], 1) <= 0) {
            player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
            return;
        }
        player.getItemAssistant().addItem(items[index], 1);
        bonesCollected = (bonesCollected + 1) % (items.length * 4);
    }

	public void bonesToFood(int spellID) {
		if (!player.getCombatAssistant().checkMagicReqs(spellID)) {
			return;
		}
        player.boneDelay = System.currentTimeMillis();
        player.startAnimation(MagicData.MAGIC_SPELLS[spellID][2]);
        player.gfx100(MagicData.MAGIC_SPELLS[spellID][3]);
        player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[spellID][7], Constants.MAGIC);
        player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
        player.getPacketSender().sendShowTab(6);
        player.getPacketSender().sendSound(SoundList.BONES_TO_BANNAS, 100, 0);
        int amount = 0;
        for (int i = 0; i < items.length; i++) {
            amount = player.getItemAssistant().getItemAmount(items[i]);
            if (amount > 0) {
                player.getItemAssistant().deleteItem(items[i], amount);
                player.getItemAssistant().addItem(spellID == 52 ? 1963 : 6883, amount * values[i]);
            }
        }
    }

    public void depositFood() {
        int amount = player.getItemAssistant().getItemAmount(1963);
        amount += player.getItemAssistant().getItemAmount(6883);
        // remove all peaches and bananas
        player.getItemAssistant().deleteItem(1963, Integer.MAX_VALUE);
        player.getItemAssistant().deleteItem(6883, Integer.MAX_VALUE);
        fruitDeposited += amount;
        while (fruitDeposited >= 16) {
            int reward = random.nextInt(rewards.length);
            player.getItemAssistant().addOrDropItem(rewards[reward], 1);
            player.graveyardPoints++;
            fruitDeposited -= 16;
        }
    }

    public void clearItems() {
        for (int item: items) {
            player.getItemAssistant().deleteItem(item, Integer.MAX_VALUE);
        }
        player.getItemAssistant().deleteItem(1963, Integer.MAX_VALUE);
        player.getItemAssistant().deleteItem(6883, Integer.MAX_VALUE);
    }

    /* ITEMS */
    // 6904 - Animal Bones
    // 6905 - Animal Bones
    // 6906 - Animal Bones
    // 6907 - Animal Bones
    public static int[] items = {6904, 6905, 6906, 6907};
    public static int[] values = {1, 2, 3, 4};
    public static int[] rewards = {555, 557, 561, 560, 565};

    public static int ticks = 0;
    private static Random random = new Random();

    /* OBJECTS */
    // 10735 - Food Chute

    /* INTERFACES */
    // 15931 - Main interface

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
        // Every 12 ticks deal 2 damage to player (50% chance)
        if (++ticks % 12 == 0) {
            for (Player p : PlayerHandler.players) {
                if (p == null) {
                    continue;
                }
                if (!Boundary.isIn(p, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
                    return;
                }
                applyDamage(p);
                updateInterface(p);
            }
        }
	}

    public static void applyDamage(Player player) {
        if (random.nextInt(1) == 0) {
            player.gfx0(520);
            player.dealDamage(2);
            player.handleHitMask(2);
        }
    }

    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
            return;
        }
        player.getPacketSender().sendString("" + player.graveyardPoints, 15935);
        player.getPacketSender().walkableInterface(15931);
    }
}
