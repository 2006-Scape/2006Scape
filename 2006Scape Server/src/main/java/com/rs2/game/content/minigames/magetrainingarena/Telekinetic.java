package com.rs2.game.content.minigames.magetrainingarena;

import java.awt.Point;
import java.util.Random;

import com.rs2.Constants;
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
        public GroundItem statue;
        public boolean initialized = false;

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
            this.statue = new GroundItem(6888, startX, startY, height, 1, -1, 0, "");
        }

        public static Maze getMaze(int x, int y, int h) {
            for (Maze maze : values()){
                if (Boundary.isIn(x, y, h, maze.mazeArea)) {
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
    private Random random = new Random();
    private boolean observingStatue = false;

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
        player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[51][7], Constants.MAGIC);
        player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
        player.stopMovement();

        Maze maze = Maze.getMaze(itemX, itemY, player.heightLevel);
        if (maze == null) {
            return;
        }

        Point direction = maze.calcDirection(player);
        Point newPosition = maze.getNewPos(itemX, itemY, direction.x, direction.y);
        
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.walkingToItem) {
					container.stop();
				} else if (System.currentTimeMillis() - player.teleGrabDelay > 1550) {
					if (GameEngine.itemHandler.itemExists(6888, itemX, itemY)) {
                        GameEngine.itemHandler.moveItem(maze.statue, newPosition.x, newPosition.y);

                        if (newPosition.x == maze.endX && newPosition.y == maze.endY) {
                            player.telekineticPoints += 2;
                            player.telekineticMazesSolved++;

                            // Every 5 solves, give the player 8 extra points, 10 law runes, 1000 magic experience
                            if (player.telekineticMazesSolved % 5 == 0) {
                                player.telekineticPoints += 8;
                                player.getItemAssistant().addOrDropItem(563, 10);
                                player.getPlayerAssistant().addSkillXP(1000, Constants.MAGIC);
                                player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
                            }

                            resetStatue(newPosition.x, newPosition.y);
                            
                            if (observingStatue) {
                                observingStatue = false;
                                player.getPlayerAssistant().sendCameraReset();
                            }
                            goToMaze();
                        }
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

    public void goToMaze() {
        int r = random.nextInt(Maze.values().length);
        Maze maze = Maze.values()[r];
        player.getPlayerAssistant().startTeleport2(maze.minX - 1, maze.minY - 1, maze.height);
        
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
                GameEngine.itemHandler.reloadItems(player);
                container.stop();
			}

			@Override
			public void stop() {}
		}, 8);
    }

    public void observeStatue(int itemX, int itemY) {
        Maze maze = Maze.getMaze(itemX, itemY, player.heightLevel);
        if (maze == null) {
            return;
        }
        if (!observingStatue) {
            observingStatue = true;
            player.getPlayerAssistant().sendCameraCutscene((maze.minX + 5) - 8 * player.getMapRegionX(), (maze.minY - 1) - 8 * player.getMapRegionY(), 10, 25, 0);
            player.getPlayerAssistant().sendCameraCutscene2((maze.minX + 5) - 8 * player.getMapRegionX(), (maze.minY + 5) - 8 * player.getMapRegionY() - 8, 2400, 25, 0);
            player.getPacketSender().sendMessage("You overlook the maze..");
            player.getPacketSender().sendMessage("Click the statue again to leave this view.");
        } else {
            observingStatue = false;
            player.getPlayerAssistant().sendCameraReset();
        }
    }

    public void resetStatue(int itemX, int itemY) {
        Maze maze = Maze.getMaze(itemX, itemY, player.heightLevel);
        if (maze == null) {
            return;
        }
        
        // reset statue to start position
        GameEngine.itemHandler.moveItem(maze.statue, maze.startX, maze.startY);
    }

    /* ITEMS */
    // 6888 - Guardian Statue

    /* OBJECTS */

    /* INTERFACES */
    // 15962 - Main interface
    // 15966 - Pizazz points
    // 15968 - Mazes solved

    public static int ticks = 0;

	public static void process() {
        for (Maze maze: Maze.values()) {
            if (!maze.initialized) {
                maze.initialized = true;
                GameEngine.itemHandler.createGlobalItem(maze.statue);
            }
        }
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
	}


    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_TELEKINETIC)) {
            return;
        }
        player.getPacketSender().sendString("" + player.telekineticPoints, 15966);
        player.getPacketSender().sendString("" + player.telekineticMazesSolved, 15968);
    }
}
