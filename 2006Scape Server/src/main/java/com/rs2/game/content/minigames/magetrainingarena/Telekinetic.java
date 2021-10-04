package com.rs2.game.content.minigames.magetrainingarena;

import java.awt.Point;
import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.items.GroundItem;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;

public class Telekinetic {

    public static enum Maze {
        MAZE_0(3338, 9705, 0,
            3343, 9705,
            3347, 9714),
        MAZE_1(3366, 9711, 0,
            3375, 9715,
            3367, 9720),
        MAZE_2(3338, 9675, 0,
            3343, 9680,
            3342, 9684),
        MAZE_3(3369, 9673, 0,
            3373, 9678,
            3375, 9682),
        MAZE_4(3374, 9713, 1,
            3374, 9713,
            3383, 9713),
        MAZE_5(3341, 9708, 1,
            3350, 9717,
            3341, 9708),
        MAZE_6(3346, 9680, 1,
            3351, 9684,
            3353, 9680),
        MAZE_7(3376, 9677, 1,
            3376, 9686,
            3385, 9686),
        MAZE_8(3339, 9674, 2,
            3348, 9674,
            3339, 9683),
        MAZE_9(3343, 9709, 2,
            3346, 9718,
            3345, 9718);

        public int minX, maxX, minY, maxY, height, startX, startY, endX, endY;
        public Boundary mazeArea, mazeUp, mazeRight, mazeDown, mazeLeft;

        private Maze(int minX, int minY, int height, int startX, int startY, int endX, int endY) {
            this.minX = minX;
            this.maxX = minX + 9;
            this.minY = minY;
            this.maxY = minY + 9;
            this.height = height;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.mazeArea = new Boundary(minX, maxX, minY, maxY, height);
            this.mazeUp = new Boundary(minX, maxX, maxY + 1, maxY + 2, height);
            this.mazeRight = new Boundary(maxX + 1, maxX + 2, minY, maxY, height);
            this.mazeDown = new Boundary(minX, maxX, minY - 2, minY - 1, height);
            this.mazeLeft = new Boundary(minX - 2, minX - 1, minY, maxY, height);
        }

        public static Maze getMaze(int x, int y) {
            for (Maze maze : values()){
                if (Boundary.isIn(x, y, maze.mazeArea)) {
                    return maze;
                }
            }
            return null;
        }

        public Point calcDirection(Player player) {
            if (Boundary.isIn(player, mazeUp)) {
                return new Point(0, 1);
            }
            if (Boundary.isIn(player, mazeRight)) {
                return new Point(1, 0);
            }
            if (Boundary.isIn(player, mazeDown)) {
                return new Point(0, -1);
            }
            if (Boundary.isIn(player, mazeLeft)) {
                return new Point(-1, 0);
            }
            return new Point(0, 0);
        }

        public Point getNewPos(int curX, int curY, int dirX, int dirY) {
            if (dirX != 0) {
                while(curX >= minX && curX <= maxX && Region.getClipping(curX + dirX, curY, this.height, dirX, dirY)) {
                    curX += dirX;
                }
            }
            if (dirY != 0) {
                while(curY >= minY && curY <= maxY && Region.getClipping(curX, curY + dirY, this.height, dirX, dirY)) {
                    curY += dirY;
                }
            }
            return new Point(curX, curY);
        }
    }

	private Player player;

	public Telekinetic(Player c) {
		this.player = c;
	}

	public void moveStatue(int itemX, int itemY) {
        // Play animation, Award XP
        player.walkingToItem = true;
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

        Maze maze = Maze.getMaze(itemX, itemY);
        if (maze == null) {
            return;
        }

        Point direction = maze.calcDirection(player);
        Point newPosition = maze.getNewPos(itemX, itemY, direction.x, direction.y);
        GroundItem item = new GroundItem(6888, newPosition.x, newPosition.y, maze.height, 1, -1, 0, "Global");

        System.out.println("test");
        
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.walkingToItem) {
					container.stop();
				} else if (System.currentTimeMillis() - player.teleGrabDelay > 1550) {
					if (GameEngine.itemHandler.itemExists(6888, itemX, itemY)) {
                        GameEngine.itemHandler.removeGroundItem(player, 6888, itemX, itemY, false);
                        GameEngine.itemHandler.createGlobalItem(item);
                        // TODO: Figure out if on end tile, reset statue, award points, move player to a different maze
					}
					stop();
				}
			}

			@Override
			public void stop() {
                player.usingMagic = false;
				player.walkingToItem = false;
			}
		}, 1);
	}

    public void observeStatue(int itemX, int itemY) {
        Maze maze = Maze.getMaze(itemX, itemY);
        if (maze == null) {
            return;
        }
    }

    public void resetStatue(int itemX, int itemY) {
        Maze maze = Maze.getMaze(itemX, itemY);
        if (maze == null) {
            return;
        }
    }

    /* ITEMS */
    // 6888 - Guardian Statue

    /* OBJECTS */

    /* INTERFACES */
    // 15962 - Main interface

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
