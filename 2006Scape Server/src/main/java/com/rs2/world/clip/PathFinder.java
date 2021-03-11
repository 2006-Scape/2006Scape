package com.rs2.world.clip;

import java.util.LinkedList;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class PathFinder {

	private static final PathFinder pathFinder = new PathFinder();

	public static PathFinder getPathFinder() {
		return pathFinder;
	}

	public void findRoute(Player player, int destX, int destY, boolean moveNear,
			int xLength, int yLength) {
		if (destX == player.getLocalX() && destY == player.getLocalY() && !moveNear) {
			player.getPacketSender().sendMessage("ERROR!");
			return;
		}
		destX = destX - 8 * player.getMapRegionX();
		destY = destY - 8 * player.getMapRegionY();
		int[][] via = new int[104][104];
		int[][] cost = new int[104][104];
		LinkedList<Integer> tileQueueX = new LinkedList<Integer>();
		LinkedList<Integer> tileQueueY = new LinkedList<Integer>();
		for (int xx = 0; xx < 104; xx++) {
			for (int yy = 0; yy < 104; yy++) {
				cost[xx][yy] = 99999999;
			}
		}
		int curX = player.getLocalX();
		int curY = player.getLocalY();
		via[curX][curY] = 99;
		cost[curX][curY] = 0;
		int tail = 0;
		tileQueueX.add(curX);
		tileQueueY.add(curY);
		boolean foundPath = false;
		int pathLength = 4000;
		while (tail != tileQueueX.size() && tileQueueX.size() < pathLength) {
			curX = tileQueueX.get(tail);
			curY = tileQueueY.get(tail);
			int curAbsX = player.getMapRegionX() * 8 + curX;
			int curAbsY = player.getMapRegionY() * 8 + curY;
			if (curX == destX && curY == destY) {
				foundPath = true;
				break;
			}
			tail = (tail + 1) % pathLength;
			int thisCost = cost[curX][curY] + 1;
			if (curY > 0
					&& via[curX][curY - 1] == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, player.heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY - 1);
				via[curX][curY - 1] = 1;
				cost[curX][curY - 1] = thisCost;
			}
			if (curX > 0
					&& via[curX - 1][curY] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, player.heightLevel) & 0x1280108) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY);
				via[curX - 1][curY] = 2;
				cost[curX - 1][curY] = thisCost;
			}
			if (curY < 104 - 1
					&& via[curX][curY + 1] == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, player.heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY + 1);
				via[curX][curY + 1] = 4;
				cost[curX][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& via[curX + 1][curY] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, player.heightLevel) & 0x1280180) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY);
				via[curX + 1][curY] = 8;
				cost[curX + 1][curY] = thisCost;
			}
			if (curX > 0
					&& curY > 0
					&& via[curX - 1][curY - 1] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY - 1,
							player.heightLevel) & 0x128010e) == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, player.heightLevel) & 0x1280108) == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, player.heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY - 1);
				via[curX - 1][curY - 1] = 3;
				cost[curX - 1][curY - 1] = thisCost;
			}
			if (curX > 0
					&& curY < 104 - 1
					&& via[curX - 1][curY + 1] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY + 1,
							player.heightLevel) & 0x1280138) == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, player.heightLevel) & 0x1280108) == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, player.heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY + 1);
				via[curX - 1][curY + 1] = 6;
				cost[curX - 1][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY > 0
					&& via[curX + 1][curY - 1] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY - 1,
							player.heightLevel) & 0x1280183) == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, player.heightLevel) & 0x1280180) == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, player.heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY - 1);
				via[curX + 1][curY - 1] = 9;
				cost[curX + 1][curY - 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY < 104 - 1
					&& via[curX + 1][curY + 1] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY + 1,
							player.heightLevel) & 0x12801e0) == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, player.heightLevel) & 0x1280180) == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, player.heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY + 1);
				via[curX + 1][curY + 1] = 12;
				cost[curX + 1][curY + 1] = thisCost;
			}
		}
		if (!foundPath) {
			if (moveNear) {
				int i_223_ = 1000;
				int thisCost = 100;
				int i_225_ = 10;
				for (int x = destX - i_225_; x <= destX + i_225_; x++) {
					for (int y = destY - i_225_; y <= destY + i_225_; y++) {
						if (x >= 0 && y >= 0 && x < 104 && y < 104
								&& cost[x][y] < 100) {
							int i_228_ = 0;
							if (x < destX) {
								i_228_ = destX - x;
							} else if (x > destX + xLength - 1) {
								i_228_ = x - (destX + xLength - 1);
							}
							int i_229_ = 0;
							if (y < destY) {
								i_229_ = destY - y;
							} else if (y > destY + yLength - 1) {
								i_229_ = y - (destY + yLength - 1);
							}
							int i_230_ = i_228_ * i_228_ + i_229_ * i_229_;
							if (i_230_ < i_223_ || i_230_ == i_223_
									&& cost[x][y] < thisCost) {
								i_223_ = i_230_;
								thisCost = cost[x][y];
								curX = x;
								curY = y;
							}
						}
					}
				}
				if (i_223_ == 1000) {
					return;
				}
			} else {
				return;
			}
		}
		tail = 0;
		tileQueueX.set(tail, curX);
		tileQueueY.set(tail++, curY);
		int l5;
		for (int j5 = l5 = via[curX][curY]; curX != player.getLocalX()
				|| curY != player.getLocalY(); j5 = via[curX][curY]) {
			if (j5 != l5) {
				l5 = j5;
				tileQueueX.set(tail, curX);
				tileQueueY.set(tail++, curY);
			}
			if ((j5 & 2) != 0) {
				curX++;
			} else if ((j5 & 8) != 0) {
				curX--;
			}
			if ((j5 & 1) != 0) {
				curY++;
			} else if ((j5 & 4) != 0) {
				curY--;
			}
		}
		player.resetWalkingQueue();
		int size = tail--;
		int pathX = player.getMapRegionX() * 8 + tileQueueX.get(tail);
		int pathY = player.getMapRegionY() * 8 + tileQueueY.get(tail);
		player.addToWalkingQueue(localize(pathX, player.getMapRegionX()),
				localize(pathY, player.getMapRegionY()));
		for (int i = 1; i < size; i++) {
			tail--;
			pathX = player.getMapRegionX() * 8 + tileQueueX.get(tail);
			pathY = player.getMapRegionY() * 8 + tileQueueY.get(tail);
			player.addToWalkingQueue(localize(pathX, player.getMapRegionX()),
					localize(pathY, player.getMapRegionY()));
		}
	}

	public int getRegionCoordinate(int x) {
		return (x >> 3) - 6;
	}

	public int getLocalCoordinate(int x) {
		return x - 8 * getRegionCoordinate(x);
	}

	public boolean accessible(int x, int y, int heightLevel, int destX, int destY) {
		destX = destX - 8 * getRegionCoordinate(x);
		destY = destY - 8 * getRegionCoordinate(y);
		int[][] via = new int[104][104];
		int[][] cost = new int[104][104];
		LinkedList<Integer> tileQueueX = new LinkedList<Integer>();
		LinkedList<Integer> tileQueueY = new LinkedList<Integer>();
		for (int xx = 0; xx < 104; xx++) {
			for (int yy = 0; yy < 104; yy++) {
				cost[xx][yy] = 99999999;
			}
		}
		int curX = getLocalCoordinate(x);
		int curY = getLocalCoordinate(y);
		via[curX][curY] = 99;
		cost[curX][curY] = 0;
		int tail = 0;
		tileQueueX.add(curX);
		tileQueueY.add(curY);
		boolean foundPath = false;
		int pathLength = 4000;
		while (tail != tileQueueX.size() && tileQueueX.size() < pathLength) {
			curX = tileQueueX.get(tail);
			curY = tileQueueY.get(tail);
			int curAbsX = getRegionCoordinate(x) * 8 + curX;
			int curAbsY = getRegionCoordinate(y) * 8 + curY;
			if (curX == destX && curY == destY) {
				foundPath = true;
				break;
			}
			tail = (tail + 1) % pathLength;
			int thisCost = cost[curX][curY] + 1;
			if (curY > 0
					&& via[curX][curY - 1] == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY - 1);
				via[curX][curY - 1] = 1;
				cost[curX][curY - 1] = thisCost;
			}
			if (curX > 0
					&& via[curX - 1][curY] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, heightLevel) & 0x1280108) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY);
				via[curX - 1][curY] = 2;
				cost[curX - 1][curY] = thisCost;
			}
			if (curY < 104 - 1
					&& via[curX][curY + 1] == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY + 1);
				via[curX][curY + 1] = 4;
				cost[curX][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& via[curX + 1][curY] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, heightLevel) & 0x1280180) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY);
				via[curX + 1][curY] = 8;
				cost[curX + 1][curY] = thisCost;
			}
			if (curX > 0
					&& curY > 0
					&& via[curX - 1][curY - 1] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY - 1,
					heightLevel) & 0x128010e) == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, heightLevel) & 0x1280108) == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY - 1);
				via[curX - 1][curY - 1] = 3;
				cost[curX - 1][curY - 1] = thisCost;
			}
			if (curX > 0
					&& curY < 104 - 1
					&& via[curX - 1][curY + 1] == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY + 1,
					heightLevel) & 0x1280138) == 0
					&& (Region.getClipping(curAbsX - 1, curAbsY, heightLevel) & 0x1280108) == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY + 1);
				via[curX - 1][curY + 1] = 6;
				cost[curX - 1][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY > 0
					&& via[curX + 1][curY - 1] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY - 1,
					heightLevel) & 0x1280183) == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, heightLevel) & 0x1280180) == 0
					&& (Region.getClipping(curAbsX, curAbsY - 1, heightLevel) & 0x1280102) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY - 1);
				via[curX + 1][curY - 1] = 9;
				cost[curX + 1][curY - 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY < 104 - 1
					&& via[curX + 1][curY + 1] == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY + 1,
					heightLevel) & 0x12801e0) == 0
					&& (Region.getClipping(curAbsX + 1, curAbsY, heightLevel) & 0x1280180) == 0
					&& (Region.getClipping(curAbsX, curAbsY + 1, heightLevel) & 0x1280120) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY + 1);
				via[curX + 1][curY + 1] = 12;
				cost[curX + 1][curY + 1] = thisCost;
			}
		}
		return foundPath;
	}

	public static boolean isProjectilePathClear(int x0, int y0, int z, int x1, int y1) {
		int deltaX = x1 - x0;
		int deltaY = y1 - y0;

		double error = 0;
		final double deltaError = Math.abs(
				(deltaY) / (deltaX == 0
						? ((double) deltaY)
						: ((double) deltaX)));

		int x = x0;
		int y = y0;

		int pX = x;
		int pY = y;

		boolean incrX = x0 < x1;
		boolean incrY = y0 < y1;

		while (true) {
			if (x != x1) {
				x += (incrX ? 1 : -1);
			}

			if (y != y1) {
				error += deltaError;

				if (error >= 0.5) {
					y += (incrY ? 1 : -1);
					error -= 1;
				}
			}

			if (!shootable(x, y, z, pX, pY)) {
				return false;
			}

			if (incrX && incrY
					&& x >= x1 && y >= y1) {
				break;
			} else if (!incrX && !incrY
					&& x <= x1 && y <= y1) {
				break;
			} else if (!incrX && incrY
					&& x <= x1 && y >= y1) {
				break;
			} else if (incrX && !incrY
					&& x >= x1 && y <= y1) {
				break;
			}

			pX = x;
			pY = y;
		}

		return true;
	}

	private static boolean shootable(int x, int y, int z, int px, int py) {
		if (x == px && y == py) {
			return true;
		}

		int[] delta1 = Misc.delta(x, y, px, py);
		int[] delta2 = Misc.delta(px, py, x, y);

		int dir = Misc.directionFromDelta(delta1[0], delta1[1]);
		int dir2 = Misc.directionFromDelta(delta2[0], delta2[1]);

		if (dir == -1 || dir2 == -1) {
			return false;
		}

		return Region.canMove(x, y, z, dir) && Region.canMove(px, py, z, dir2)
				|| Region.canShoot(x, y, z, dir) && Region.canShoot(px, py, z, dir2);
	}

	public int localize(int x, int mapRegion) {
		return x - 8 * mapRegion;
	}

}
