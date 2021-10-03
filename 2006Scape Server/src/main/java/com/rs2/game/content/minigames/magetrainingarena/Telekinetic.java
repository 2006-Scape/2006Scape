package com.rs2.game.content.minigames.magetrainingarena;

import com.rs2.GameConstants;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.items.GroundItem;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Telekinetic {

    public static enum Maze {
        MAZE_0(3338, 9705, 3347, 9714, 0,
            3343, 9705,
            3347, 9714),
        MAZE_1(3366, 9711, 3375, 9720, 0,
            3375, 9715,
            3367, 9720),
        MAZE_2(3338, 9675, 3347, 9684, 0,
            3343, 9680,
            3342, 9684),
        MAZE_3(3369, 9673, 3378, 9682, 0,
            3373, 9678,
            3375, 9682);

        public int minX, minY, maxX, maxY, height, startX, startY, endX, endY;
        public Boundary mazeArea;

        private Maze(int minX, int minY, int maxX, int maxY, int height, int startX, int startY, int endX, int endY) {
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
            this.height = height;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.mazeArea = new Boundary(minX, maxX, minY, maxY, height);
        }
    }

	private Player player;

	public Telekinetic(Player c) {
		this.player = c;
	}

	public void moveStatue(int itemX, int itemY) {
        // Play animation, Award XP
        // TODO: take runes
        int offY = (player.getX() - itemX) * -1;
        int offX = (player.getY() - itemY) * -1;
        player.teleGrabX = itemX;
        player.teleGrabY = itemY;
        player.turnPlayerTo(itemX, itemY);
        player.teleGrabDelay = System.currentTimeMillis();
        player.startAnimation(MagicData.MAGIC_SPELLS[51][2]);
        player.gfx100(MagicData.MAGIC_SPELLS[51][3]);
        player.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY, 0, 72);
        player.getPlayerAssistant().createPlayersProjectile(player.getX(), player.getY(), offX, offY, 50, 70, MagicData.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
        player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[51][7], 6);
        player.getPlayerAssistant().refreshSkill(GameConstants.MAGIC);
        player.stopMovement();
	}

	public void deposit() {
	}

    public void clearItems() {
    }

    /* ITEMS */
    // 6888 - Guardian Statue

    /* OBJECTS */

    /* INTERFACES */

    public static int ticks = 0;

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
	}


    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ENCHANTING)) {
            return;
        }
    }
}