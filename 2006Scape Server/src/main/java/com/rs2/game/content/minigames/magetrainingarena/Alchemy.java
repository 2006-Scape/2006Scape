package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.Constants;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.magic.MagicRequirements;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Alchemy {

    private Player player;
    private boolean warned = false;

	public Alchemy(Player c) {
		this.player = c;
	}

    public void searchCupboard(int objectID) {
        int index = (objectID - firstCupboard) / 2;
        int item = Alchemy.items[(index + offset) % Alchemy.items.length];
        if (item < 0) {
			player.getPacketSender().sendMessage("The cupboard is empty.");
        } else {
            player.getItemAssistant().addItem(item, 1);
        }
    }

	public void alchItem(int itemID, int spellID) {
        if (System.currentTimeMillis() - player.alchDelay <= 1000) {
            return;
        }
        int index = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == itemID) {
                index = i;
            }
        }
        // Item not found, player trying to alch a different item
        if (index < 0) {
            player.getPacketSender().sendMessage("@red@You cannot alch that item while here.");
            return;
        }
        // Check player has requirements for this spell
        if (!MagicRequirements.checkMagicReqs(player, spellID == 1162 ? 49 : 50, index != freeAlch)) {
            return;
        }
        int value = values[(index + valueOffset) % values.length];
        player.getItemAssistant().deleteItem(itemID, 1);
        player.getItemAssistant().addItem(995, value);
        player.alchDelay = System.currentTimeMillis();
        if (spellID == 1162) {
            player.startAnimation(MagicData.MAGIC_SPELLS[49][2]);
            player.gfx100(MagicData.MAGIC_SPELLS[49][3]);
            player.getPlayerAssistant().addSkillXP(31, 6);
            player.getPacketSender().sendSound(SoundList.LOW_ALCHEMY, 100, 0);
        } else if (spellID == 1178) {
            player.startAnimation(MagicData.MAGIC_SPELLS[50][2]);
            player.gfx100(MagicData.MAGIC_SPELLS[50][3]);
            player.getPlayerAssistant().addSkillXP(65, 6);
            player.getPacketSender().sendSound(SoundList.HIGH_ALCHEMY, 100, 0);
        }
        player.getPacketSender().sendShowTab(6);
        player.getPlayerAssistant().refreshSkill(6);

        int coins = player.getItemAssistant().getItemAmount(995);
        System.out.println(warned + ": " + coins);

        if (!warned && coins >= 10000) {
            player.getPacketSender().sendMessage("@red@You can only deposit up to 12,000 coins at a time.");
            player.getPacketSender().sendMessage("@red@If you try to deposit more than 12,000 coins you won't recieve any rewards.");
            warned = true;
        }
    }

    public void collectCoins() {
        int coins = player.getItemAssistant().getItemAmount(995);
        if (coins < 100) {
			player.getPacketSender().sendMessage("@red@You need to deposit at least 100 coins.");
            return;
        }
        if (coins > 12000) {
			player.getPacketSender().sendMessage("@red@We cannot allow you to deposit that many coins.");
            return;
        }
        int points = (int) Math.floor(coins / 100);
        int bonusExp = coins * 2;
        int toBank = points * 10;
        player.getItemAssistant().deleteItem(995, coins);
        player.alchemyPoints += points;
        player.getItemAssistant().addItemToBank(995, toBank);
        player.getPlayerAssistant().addSkillXP(bonusExp, Constants.MAGIC);
        warned = false;
    }

    public void clearItems() {
        for (int item: items) {
            player.getItemAssistant().deleteItem(item, Integer.MAX_VALUE);
        }
        player.getItemAssistant().deleteItem(995, Integer.MAX_VALUE);
    }

    /* ITEMS */
    // 6893 - Leather boots
    // 6894 - Adamant Kiteshield
    // 6895 - Adamant Med Helm
    // 6896 - Emerald
    // 6897 - Rune Sword
    public static int[] items = {6893, 6894, 6895, 6896, 6897, -1, -1, -1};
    public static int[] values = {30, 15, 8, 5, 1};

    public static int ticks = 0;
    public static int offset = 0;
    public static int valueOffset = 0;
    public static int freeAlch = 0;
    public static int firstCupboard = 10783;
    private static Random random = new Random();

    /* OBJECTS */
    // 10734 - Coin Collector
    // 10783 - 1st Cupboard (+2 for the next 7 cupboards)

    /* INTERFACES */
    // 15892 - Alchemy training arena interface

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
        // Every 71 ticks, randomize the order and such
        if (++ticks % 71 == 0) {
            offset = random.nextInt(items.length);
            valueOffset = random.nextInt(values.length);
            // 1 in 4 chance of an item being free to alch
            freeAlch = random.nextInt(values.length * 4);
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
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ALCHEMY)) {
            return;
        }
        int startInterface = 15902;
        for (int i = 0; i < values.length; i++) {
            player.getPacketSender().sendString("" + values[(i + valueOffset) % values.length], startInterface + i);
            // Hide the arrow if that item isn't free to alch
            player.getPacketSender().sendHideInterfaceLayer(15907 + i, freeAlch != i);
        }
        player.getPacketSender().sendString("" + player.alchemyPoints, 15896);
        player.getPacketSender().walkableInterface(15892);
    }
}
