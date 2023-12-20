package com.rs2.game.players;

import java.util.*;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.prayer.PrayerDrain;
import com.rs2.game.content.minigames.FightPits;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.skills.SkillData;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.GameItem;
import com.rs2.game.items.ItemConstants;
import com.rs2.game.items.impl.LightSources;
import com.rs2.game.items.impl.Greegree.MonkeyData;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.util.GameLogger;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.TileControl;
import com.rs2.world.clip.PathFinder;
import com.rs2.world.clip.Region;

public class PlayerAssistant {

	private Player player;

	public PlayerAssistant(Player player2) {
		this.player = player2;
	}
	
	public boolean savePlayer() {
		return (player.wildLevel < 20 && player.playerEquipment[ItemConstants.RING] == 2570 && player.playerLevel[Constants.HITPOINTS] > 0 && player.playerLevel[Constants.HITPOINTS] <= getLevelForXP(player.playerXP[Constants.HITPOINTS]) / 10 && player.underAttackBy > 0);
	}
	
	public void handleROL() {
		if (!savePlayer()) {
			return;
		}
		player.getItemAssistant().deleteEquipment(2570, ItemConstants.RING);
		player.getPlayerAssistant().startTeleport(3222, 3218, 0, "modern");
		player.getPacketSender().sendMessage("Your ring of life saves you.");
	}
	
	public void objectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		for (Player p : PlayerHandler.players) {
			if(p != null) {
				Player players = (Player)p;
				if(players.distanceToPoint(X, Y) <= 25) {
					players.getPacketSender().createPlayersObjectAnim(X, Y, animationID, tileObjectType, orientation);	
				}
			}
		}
	}
	
	public void resetAutocast() {
		player.autocastId = 0;
		player.autocasting = false;
		player.getPacketSender().sendConfig(108, 0);
	}
	
	public void setAnimationBack() {
		player.isRunning = true;
		player.getPacketSender().sendFrame36(173,1);
		player.getCombatAssistant().getPlayerAnimIndex();
		requestUpdates();
	}
	
	public boolean isPlayer() {
		return player.playerRights < 2 || player.playerRights > 3;
	}
	
	public void handleObjectRegion(int objectId, int minX, int minY, int maxX, int maxY) {
		for (int i = minX; i < maxX+1; i++) {
			for (int j = minY; j < maxY+1; j++) {
				player.getPacketSender().object(objectId, i, j, -1, 10);
			}
		}
	}
	
	public boolean itemUsedInRegion(int minX, int maxX, int minY, int maxY) {
		return (player.objectX >= minX && player.objectX <= maxX) && (player.objectY >= minY && player.objectY <= maxY);
	}
	
	public void loginScreen() {
		player.getPacketSender().showInterface(15244);
		player.getPacketSender().sendString("Welcome to " + Constants.SERVER_NAME + "             World: " + Constants.WORLD + "\\n", 15257);
		   int currentDay = player.getLastLogin() - player.lastLoginDate;

		if (player.playerLevel[Constants.HERBLORE] < 3) {

			player.playerLevel[Constants.HERBLORE] = 3;
			player.playerXP[Constants.HERBLORE] = 175;
			player.getPlayerAssistant().refreshSkill(Constants.HERBLORE);
		}
	        if (player.lastLoginDate <= 0) {
	            player.getPacketSender().sendString("This is your first time logging in!", 15258);
	        } else if (player.lastLoginDate == 1) {
	           player.getPacketSender().sendString("You last logged in @red@yesterday @bla@ from: @red@" + player.lastConnectedFrom, 15258);
	        } else {
	        	player.getPacketSender().sendString("You last logged in @red@" + (currentDay > 1 ? (currentDay + " @bla@days ago") : ("earlier today")) + " @bla@ from: @red@" + player.lastConnectedFrom, 15258);
	        }
		player.getPacketSender().sendString("" + Constants.SERVER_NAME + " will NEVER email you.\\n We use the forums or we \\nWill contact you through game.", 15260);
		player.getPacketSender().sendString("You have 0 unread messages\\nin your message centre.", 15261);
		player.getPacketSender().sendString("You have @gre@unlimited@yel@ days of member credit.", 15262);
		player.getPacketSender().sendString("CLICK HERE TO PLAY", 15263);
		if (!player.hasBankpin) {
			player.getPacketSender().sendString("You currently have no bank pin set!\\nWe strongly advise you to set\\n one.", 15270);
		} else {
			player.getPacketSender().sendString("\\nYou currently have a bank pin set.\\nYour bank is secure!.", 15270);
		}
		int random = Misc.random(3);
		player.getPacketSender().sendString(welcomeMessages[random][0], 15803);
		player.getPacketSender().sendString(welcomeMessages[random][1], 15804);
	}
	
	private String[][] welcomeMessages = {
			{"Remember to vote daily to help " + Constants.SERVER_NAME + "", "Every vote counts! :)"},
			{"Not a member of our discord community?", "Join our discord at: https://discord.gg/hZ6VfWG"},
			{"Do you have any bugs that you would like to report?", "Report them on our discord or message a staff member. :)"},
			{"Want to help the server grow?", "Remember to vote daily and invite your friends!"}
		};
	
	public void showMap() {
		int position = (player.getX() / 64 - 46) + (player.getY() / 64 - 49) * 6;
		player.getPacketSender().sendConfig(106, position);
		player.getPacketSender().showInterface(5392);
	}

    public ArrayList<GameItem> randomFish(int fish) {
        Random r = new Random();
        ArrayList<GameItem> toReturn = new ArrayList<GameItem>();
        boolean turtles = true;
        boolean mantas = true;
        boolean lobsters = true;
        boolean swordfish = true;
        int turt = 0;
        int manta = 0;
        int lobs = 0;
        int swordFish = 0;
        int junk = 0;
        int done = 0;
        while (done != fish) {
            done++;
            int random = r.nextInt(100);
            if (random >= 85 - GameEngine.trawler.chanceByLevel(player, 381)) {
                if (mantas) {
                    manta++;
                }
            } else if (random >= 70 - GameEngine.trawler.chanceByLevel(player,
                    381)) {
                if (turtles) {
                    turt++;
                }
            } else if (random >= 40) {
                if (swordfish) {
                    swordFish++;
                }
            } else if (random >= 5) {
                if (lobsters) {
                    lobs++;
                }
            } else {
                junk++;
            }
        }
        int xpToAdd = 0;
        if (manta > 0) {
            toReturn.add(new GameItem(389, manta));
            if (player.playerLevel[Constants.FISHING] >= 81) {
                xpToAdd += (manta * 46);
            }
        }
        if (turt > 0) {
            toReturn.add(new GameItem(395, turt));
            if (player.playerLevel[Constants.FISHING] >= 79) {
                xpToAdd += (manta * 38);
            }
        }
        if (lobs > 0) {
            toReturn.add(new GameItem(377, lobs));
            if (player.playerLevel[Constants.FISHING] >= 40) {
                xpToAdd += (manta * 90);
            }
        }
        if (swordFish > 0) {
            toReturn.add(new GameItem(371, swordFish));
            if (player.playerLevel[Constants.FISHING] >= 50) {
                xpToAdd += (manta * 100);
            }
        }
        if (junk > 0)
            toReturn.add(new GameItem(685, junk));
        player.getPlayerAssistant().addSkillXP(xpToAdd, Constants.FISHING);
        return toReturn;
    }
    
    public void removeFishingTrawlerRewardItem(int slot, boolean all) {
        try {
            if (!all) {
                if (player.getItemAssistant().freeSlots() != 0) {
                    if (player.fishingTrawlerReward.get(slot).amount >= 1) {
                        player.getItemAssistant().addItem(
                                player.fishingTrawlerReward.get(slot).id, 1);
                        player.fishingTrawlerReward.get(slot).amount--;
                        if (player.fishingTrawlerReward.get(slot).amount <= 0) {
                            player.fishingTrawlerReward.remove(slot);
                            GameEngine.trawler.showReward(player);
                        } else {
                            GameEngine.trawler.updateRewardSlot(player, slot);
                        }
                    }
                } else {
                    player.getPacketSender().sendMessage("You don't have enough inventory space to withdraw that");
                }
            } else {
                int loop = player.fishingTrawlerReward.get(slot).amount;
                for (int j = 0; j < loop; j++) {
                    if (player.getItemAssistant().freeSlots() == 0) {
                        player.getPacketSender().sendMessage("You don't have enough inventory space to withdraw that");
                        GameEngine.trawler.updateRewardSlot(player, slot);
                        return;
                    }
                    player.getItemAssistant()
                            .addItem(player.fishingTrawlerReward.get(slot).id, 1);
                    player.fishingTrawlerReward.get(slot).amount--;
                    if (player.fishingTrawlerReward.get(slot).amount <= 0) {
                        player.fishingTrawlerReward.remove(slot);
                        GameEngine.trawler.showReward(player);
                        return;
                    }
                }
            }
        } catch (Exception e) {

        }
    }
    
    public void removeAllSidebars() {
    	for (int i = 0; i < 14; i++) {
    		 player.getPacketSender().setSidebarInterface(i, -1);
    	}
    }
    
    public void resetAnimationsToPrevious() {
        player.playerRunIndex = player.prevPrevPlayerRunIndex;
        player.playerStandIndex = player.prevPlayerStandIndex;
        player.playerWalkIndex = player.prevplayerWalkIndex;
        player.playerTurnIndex = player.prevPlayerTurnIndex;
        player.playerTurn90CWIndex = player.prevPlayerTurn90CWIndex;
        player.playerTurn90CCWIndex = player.prevPlayerTurn90CCWIndex;
        player.playerTurn180Index = player.prevPlayerTurn180Index;
        requestUpdates();
    }
	    
	public int backupItems[] = new int[ItemConstants.BANK_SIZE];
	public int backupItemsN[] = new int[ItemConstants.BANK_SIZE];
	public int backupInvItems[] = new int[28];
	public int backupInvItemsN[] = new int[28];

	public void otherInv(Client c, Client o) {
		if (o == c || o == null || c == null)
			return;
		for (int i = 0; i < o.playerItems.length; i++) {
			backupInvItems[i] = c.playerItems[i];
			backupInvItemsN[i] = c.playerItemsN[i];
			c.playerItems[i] = o.playerItems[i];
			c.playerItemsN[i] = o.playerItemsN[i];
		}
		c.getItemAssistant().updateInventory();
		for (int i = 0; i < o.playerItems.length; i++) {
			c.playerItemsN[i] = backupInvItemsN[i];
			c.playerItems[i] = backupInvItems[i];
		}
	}

	public void otherBank(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i];
			backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i];
			c.bankItems[i] = o.bankItems[i];
		}

		player.getPacketSender().openUpBank();

		for (int i = 0; i < o.bankItems.length; i++) {
			c.bankItemsN[i] = backupItemsN[i];
			c.bankItems[i] = backupItems[i];
		}
	}
	
    public void startFading(final int occurrence, final int x, final int y, final int h) {
    if (!player.allowFading)
            return;
    player.allowFading = false;
    player.getPacketSender().showInterface(13583);
    player.getPacketSender().sendMapState(2);
    CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                    movePlayer(x, y, h);
                    resetAnimation();
                    requestUpdates();
                    container.stop();
            }

            @Override
            public void stop() {
                    player.allowFading = true;
                    player.getPacketSender().sendMapState(0);
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                            @Override
                            public void execute(CycleEventContainer container2) {
                                    container2.stop();
                            }

                            @Override
                            public void stop() {
                                    player.getPacketSender().closeAllWindows();
                                    switch (occurrence) {
                                    case 0:
                                            player.getDialogueHandler().sendStatement("You arrive at Port Khazard.");
                                            break;
                                    case 1:
                                            player.getPacketSender().sendMessage("You wash up onto the shore...");
                                            break;
                                    case 2:
                                            player.getPacketSender().sendMessage("You find yourself inside a hidden cavern.");
                                            break;
                                    }
                            }
                    }, 2);
            }
    }, 4);
}

	public void sendSidebars() {
		for (int i = 0; i < Constants.SIDEBARS.length; i++) {
			player.getPacketSender().setSidebarInterface(i, Constants.SIDEBARS[i]);
		}
		player.getPacketSender().setSidebarInterface(6, player.playerMagicBook == 0 ? 1151 : 12855);
	}

	public boolean removeGloves() {
		if (player.getItemAssistant().playerHasItem(776)) {
			player.getItemAssistant().deleteItem(776, 1);
			return true;
		} else if (player.getItemAssistant().playerHasItem(775)) {
			player.getItemAssistant().deleteItem(775, 1);
			return true;
		} else if (player.playerEquipment[player.playerHands] == 775 || player.playerEquipment[player.playerHands] == 776) {
			player.getDialogueHandler().sendStatement("You need to take your gloves off to do this.");
			player.nextChat = 0;
			return false;
		}
		return false;
	}

	public void feature(String feature) {
		player.getDialogueHandler().sendStatement("Sorry, " + feature + " is currently disabled.");
		player.nextChat = 0;
	}

	public static void removeHintIcon(Player c) {
		c.getPacketSender().drawHeadicon(0, 0, 0, 0);
	}

	/**
	 * Hides all Side Bars
	 */

	public void hideAllSideBars() {
		for (int i = 0; i < 14; i++) {
			player.getPacketSender().setSidebarInterface(i, -1);
		}
		player.getPacketSender().setSidebarInterface(10, 2449);
	}

	public void writeEnergy() {
		if (player.playerEnergy >= 100) {
			player.getPacketSender().sendString("100%", 149);
		} else { 
			player.getPacketSender().sendString(player.playerEnergy > 0 && player.playerEnergy < 100 ? (int) Math.ceil(player.playerEnergy) + "%" : "0%", 149);
		}
	}

	public int raiseTimer() {
		// calculations from https://oldschool.runescape.wiki/w/Energy
		double seconds  = 60 / (8 + Math.floor(player.playerLevel[Constants.AGILITY] / 6));
		return (int) Math.floor(seconds * 1000);
	}

	public void handleTiara() {
		int[] tiaras = { 5527, 5529, 5531, 5535, 5537, 5533, 5539, 5543, 5541, 5545, 5547 };
		if (player.wearId >= tiaras[0] && player.wearId <= tiaras[10]) {
			for (int i = 0; i < tiaras.length; i++) {
				if (player.wearId == tiaras[i]) {
					int tempInt = 1;
					int loc = i;
					while (loc > 0) {
						tempInt *= 2;
						loc--;
					}
					player.getPacketSender().setConfig(491, tempInt);
				}
			}
		}
	}
	
	public static boolean pathBlocked(Client attacker, Client victim) {

		double offsetX = Math.abs(attacker.absX - victim.absX);
		double offsetY = Math.abs(attacker.absY - victim.absY);

		int distance = TileControl.calculateDistance(attacker, victim);

		if (distance == 0) {
			return true;
		}

		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];

		int curX = attacker.absX;
		int curY = attacker.absY;
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;

		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while (distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;
					currentTileXCount -= offsetX;
				}
			} else if (curX < victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;
					currentTileYCount -= offsetY;
				}
			} else if (curY < victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = attacker.heightLevel;
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])/* && !Region.blockedShot(path[i][0], path[i][1], path[i][2])*/) {
				return true;
			}
		}
		return false;
	}

	public static boolean pathBlocked(Client attacker, Npc victim) {
		double offsetX = Math.abs(attacker.absX - victim.absX);
		double offsetY = Math.abs(attacker.absY - victim.absY);

		int distance = TileControl.calculateDistance(attacker, victim);

		if (distance == 0) {
			return true;
		}

		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];

		int curX = attacker.absX;
		int curY = attacker.absY;
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;

		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while (distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;
					currentTileXCount -= offsetX;
				}
			} else if (curX < victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;
					currentTileYCount -= offsetY;
				}
			} else if (curY < victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = attacker.heightLevel;
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;
		}
		return false;
	}

	
	public void stepAway() {
		player.faceUpdate(player.followId + 32768);
			if (Region.getClipping(player.getX() - 1, player.getY(), player.heightLevel, -1, 0)) {
				walkTo(-1, 0);
			} else if (Region.getClipping(player.getX() + 1, player.getY(), player.heightLevel, 1, 0)) {
				walkTo(1, 0);
			} else if (Region.getClipping(player.getX(), player.getY() - 1, player.heightLevel, 0, -1)) {
				walkTo(0, -1);
			} else if (Region.getClipping(player.getX(), player.getY() + 1, player.heightLevel, 0, 1)) {
				walkTo(0, 1);
			}
		}

	public void squeezeThroughRailing() {
		player.startAnimation(2240);
		player.turnPlayerTo(player.objectX, player.objectY);
		player.getPacketSender().sendMessage("You squeeze through the loose railing.");
	}

	public void spiritTree() {
		player.getDialogueHandler().sendOption("The Tree Gnome Village", "The Gnome Stronghold", "Varrock");
		player.dialogueAction = 53;
	}

	public void handleCanoe() {
		player.getDialogueHandler().sendOption("Travel the canoe to Barbarian Village.", "Travel the canoe to the Champions Guild.", "Travel the canoe to Lumbridge.", "Travel the canoe to Edgeville.");
		player.dialogueAction = 122;
	}

	public boolean bananasCheck() {
		int reqAmount = 10 - player.getItemAssistant().getItemAmount(1963);
		switch (player.getItemAssistant().getItemAmount(1963)) {
		case 0:
			player.getDialogueHandler().sendPlayerChat("I'll go collect " + reqAmount + " bananas then come back...");
			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			player.getDialogueHandler().sendPlayerChat("I'll go collect " + reqAmount + " more bananas then come back...");
			break;
		}
		return true;
	}

	/**
	 * Teleports
	 */

	public void spellTeleport(int x, int y, int height) {
		startTeleport(x, y, height, player.playerMagicBook == 1 ? "ancient" : "modern");
	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		if (FightPits.getState(player) != null) {
			player.getPacketSender().sendMessage("You can't teleport from a Fight pits Game!");
			return;
		}
		if (player.inWild() && player.wildLevel > Constants.NO_TELEPORT_WILD_LEVEL) {
			player.getPacketSender().sendMessage("You can't teleport above level " + Constants.NO_TELEPORT_WILD_LEVEL + " wilderness.");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getPacketSender().sendMessage("You can't teleport from tutorial island!");
			return;
		}
		int[] cwitems = { 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 1706,
				1708, 1710, 1712, 8007, 8008, 8009, 8010, 8011 };
		for (int cwitem : cwitems) {
			if (player.inCw() || player.inCw() && player.getItemAssistant().playerHasItem(cwitem)) {
				player.getPacketSender().sendMessage("You can't teleport from castle wars.");
				return;
			}
		}
		if (player.inTrade) {
			player.getPacketSender().sendMessage("You can't teleport while in trade.");
			return;
		}
		if (player.duelStatus == 5) {
			player.getPacketSender().sendMessage("You can't teleport while in a duel.");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getPacketSender().sendMessage("This feature is curently disabled.");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getPacketSender().sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (SkillHandler.isSkilling(player)) {
			player.getPacketSender().sendMessage("You can't teleport while skilling!");
			return;
		}
		if (MonkeyData.isWearingGreegree(player)) {
			player.getPacketSender().sendMessage("You can't teleport as a monkey.");
			return;
		}
		if (!player.isDead && player.teleTimer == 0
				&& player.respawnTimer == -6) {
			if (player.playerIndex > 0 || player.npcIndex > 0) {
				player.getCombatAssistant().resetPlayerAttack();
			}
			if (player.clickedTree) {
				player.clickedTree = false;
			}
			player.stopMovement();
			player.getPacketSender().closeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			player.refresh = false;
			if(player.heightLevel != height) {
				player.refresh = true;
			}
			player.teleHeight = height;
			player.isTeleporting = true;
			if (Constants.SOUND) {
				player.getPacketSender().sendSound(SoundList.TELEPORT, 100, 700);
			}
			if (teleportType.equalsIgnoreCase("modern")) {
				player.startAnimation(714, 10);
				player.teleTimer = 10;
				player.teleGfx = 308;
				player.teleEndAnimation = 715;
			}
			if (teleportType.equalsIgnoreCase("spiritTree")) {
				player.startAnimation(4731);
				player.gfx0(1228);
				player.teleTimer = 9;
				player.teleEndAnimation = 715;
			}
			if (teleportType.equalsIgnoreCase("ancient")) {
				player.startAnimation(1979);
				player.teleGfx = 0;
				player.teleTimer = 9;
				player.teleEndAnimation = 0;
				player.gfx0(392);
			}
		}
	}

	public void startTeleport2(int x, int y, int height) {
		int[] cwitems = { 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 1706,
				1708, 1710, 1712, 8007, 8008, 8009, 8010, 8011 };
		for (int cwitem : cwitems) {
			if (player.inCw() || player.inCw() && player.getItemAssistant().playerHasItem(cwitem)) {
				player.getPacketSender().sendMessage("You can't teleport from castle wars!");
				return;
			}
		}
		if (player.inTrade) {
			player.getPacketSender().sendMessage(
					"You can't teleport while in trade!");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getPacketSender().sendMessage(
					"You can't teleport from tutorial island!");
			return;
		}
		if (FightPits.getState(player) != null) {
			player.getPacketSender().sendMessage(
					"You can't teleport from a Fight pits Game!");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getPacketSender().sendMessage(
					"This feature is curently disabled.");
			return;
		}
		if (player.duelStatus == 5) {
			player.getPacketSender().sendMessage(
					"You can't teleport during a duel!");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getPacketSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (MonkeyData.isWearingGreegree(player)) {
			player.getPacketSender().sendMessage("You can't teleport as a monkey.");
			return;
		}
		if (Constants.SOUND) {
			player.getPacketSender().sendSound(SoundList.TELEPORT, 100, 0);
		}
		if (!player.isDead && player.teleTimer == 0) {
			player.stopMovement();
			player.getPacketSender().closeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			if (player.heightLevel != height) {
				player.refresh = true;
			}
			player.teleHeight = height;
			player.startAnimation(714);
			player.teleTimer = 11;
			player.teleGfx = 308;
			player.teleEndAnimation = 715;
			player.isTeleporting = true;
		}
	}

	public void gloryTeleport(int x, int y, int height, String teleportType) {
		int[] cwitems = { 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 1706,
				1708, 1710, 1712, 8007, 8008, 8009, 8010, 8011 };
		for (int cwitem : cwitems) {
			if (player.inCw() || player.duelStatus > 0
					&& player.getItemAssistant().playerHasItem(cwitem)) {
				player.getPacketSender().sendMessage(
						"You can't teleport from Castle Wars!");
				return;
			}
		}
		if (player.inTrade) {
			player.getPacketSender().sendMessage("You can't teleport while in trade!");
			return;
		}
		if (MonkeyData.isWearingGreegree(player)) {
			player.getPacketSender().sendMessage("You can't teleport as a monkey.");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getPacketSender().sendMessage("You can't teleport from tutorial island!");
			return;
		}
		if (FightPits.getState(player) != null) {
			player.getPacketSender().sendMessage("You can't teleport from a Fight pits Game!");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getPacketSender().sendMessage("This feature is curently disabled.");
			return;
		}
		if (player.duelStatus == 5) {
			player.getPacketSender().sendMessage("You can't teleport during a duel!");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getPacketSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (Constants.SOUND) {
			player.getPacketSender().sendSound(SoundList.TELEPORT, 100, 0);
		}
		if (player.inWild() && player.wildLevel > 30) {
			player.getPacketSender().sendMessage("You can't teleport above level 30 wilderness.");
			return;
		}
		if (!player.isDead && player.teleTimer == 0) {
			player.stopMovement();
			player.getPacketSender().closeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			if (player.heightLevel != height) {
				player.refresh = true;
			}
			player.teleHeight = height;
			player.startAnimation(714);
			player.teleTimer = 11;
			player.teleGfx = 308;
			player.teleEndAnimation = 715;
			player.isTeleporting = true;
		}
	}

	public void processTeleport() {
		player.teleportToX = player.teleX;
		player.teleportToY = player.teleY;
		if (player.teleEndAnimation > 0) {
			player.startAnimation(player.teleEndAnimation);
		}
	}

	public void movePlayer(int x, int y, int h) {
		player.refresh = false;
		player.resetWalkingQueue();
		player.teleportToX = x;
		player.teleportToY = y;
		if(player.heightLevel != h) {
			player.refresh = true;
		}
		player.teleHeight = h;
		player.getPlayerAssistant().requestUpdates();
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(player, x, y, true, 1, 1);
	}

	public void handleEmpty() {
		if (player.playerRights != 3) {
			player.getDialogueHandler().sendOption("Yes, empty my inventory please.", "No, don't empty my inventory.");
			player.dialogueAction = 855;
		} else {
			player.getItemAssistant().removeAllItems();
		}
	}

	public void resetTzhaar() {
		if (!player.inFightCaves())
			return;
		if (!player.killedJad) {
			if (player.waveId > 1) {
				player.getItemAssistant().addItem(6529, ((int)((player.waveId*player.waveId)+(((double)player.waveId/2.0)+0.5))));
				player.getDialogueHandler().sendDialogues(104, 2617);
			}
		}
		player.killedJad = false;
		player.canHealersRespawn = true;
		player.waveId = -1;
		player.tzhaarToKill = -1;
		player.tzhaarKilled = -1;
		movePlayer(2438, 5168, 0);
	}

	public void enterCaves() {
		player.getDialogueHandler().sendDialogues(101, 2617);
		player.getPlayerAssistant().movePlayer(2413, 5117, player.playerId * 4);
		player.waveId = 0;
		player.tzhaarToKill = -1;
		player.tzhaarKilled = -1;
		   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (player.disconnected) {
					container.stop();
					return;
				}
				GameEngine.fightCaves.spawnNextWave((Client) PlayerHandler.players[player.playerId]);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 16);
	}

	private static final int[][] STARTER_ITEMS = { { 1351, 1 }, { 590, 1 },
			{ 303, 1 }, { 315, 1 }, { 1925, 1 }, { 1931, 1 }, { 2309, 1 },
			{ 1265, 1 }, { 1205, 1 }, { 1277, 1 }, { 1171, 1 }, { 841, 1 },
			{ 882, 25 }, { 556, 25 }, { 558, 15 }, { 555, 6 }, { 557, 4 },
			{ 559, 2 } };

	public void addStarter() {
		for (int[] element : STARTER_ITEMS) {
			int item = element[0];
			int amount = element[1];
			player.getItemAssistant().addItem(item, amount);
		}
	}

	public void hitPlayers(int x1, int x2, int y1, int y2, int hp) {
		for (Player player : PlayerHandler.players) {
			if (player != null && player.isActive) {
				Client t = (Client) player;
				if (t.absX >= x1 && t.absX <= x2 && t.absY >= y1
						&& t.absY <= y2) {
					int hit = t.playerLevel[Constants.HITPOINTS] / hp;
					t.setHitDiff2(hit);
					t.setHitUpdateRequired2(true);
					t.playerLevel[Constants.HITPOINTS] -= hit;
					t.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
					t.updateRequired = true;
				}
			}
		}
	}
	
	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPacketSender().stillGfx(id, x, y,
									height, time);
						}
					}
				}
			}
		}
	}
	

	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		// synchronized(c) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == player.heightLevel) {
								person.getPacketSender().createProjectile(
										x, y, offX, offY, angle, speed,
										gfxMoving, startHeight, endHeight,
										lockon, time);
							}
						}
					}
				}
			}
		}
	}

	public void createPlayersProjectile2(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPacketSender()
									.createProjectile2(x, y, offX, offY, angle,
											speed, gfxMoving, startHeight,
											endHeight, lockon, time, slope);
						}
					}
				}
			}
		}
	}

	/**
	 * Private Messaging
	 */
	public void logIntoPM() {
		player.getPacketSender().setPrivateMessaging(2);
		for (Player p : PlayerHandler.players) {
			if (p != null && p.isActive) {
				Client o = (Client) p;
				o.getPlayerAssistant().updatePM(player.playerId, Constants.WORLD);
			}
		}
		boolean pmLoaded = false;

		for (long friend : player.friends) {
			if (friend != 0) {
				for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null && !p.isBot && p.isActive
							&& Misc.playerNameToInt64(p.playerName) == friend) {
						Client o = (Client) p;
						if (player.playerRights >= 2
								|| p.privateChat == 0
								|| p.privateChat == 1
								&& o.getPlayerAssistant()
										.isInPM(Misc
												.playerNameToInt64(player.playerName))) {
							player.getPacketSender().loadPM(friend, Constants.WORLD);
							pmLoaded = true;
						}
						break;
					}
				}
				if (!pmLoaded) {
					player.getPacketSender().loadPM(friend, 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < PlayerHandler.players.length; i1++) {
				Player p = PlayerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					o.getPlayerAssistant().updatePM(player.playerId, Constants.WORLD);
				}
			}
		}
	}

	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = PlayerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		long l = Misc.playerNameToInt64(PlayerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (long friend : player.friends) {
				if (friend != 0) {
					if (l == friend) {
						player.getPacketSender().loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (long friend : player.friends) {
				if (friend != 0) {
					if (l == friend) {
						if (o.getPlayerAssistant().isInPM(
								Misc.playerNameToInt64(player.playerName))) {
							player.getPacketSender().loadPM(l, world);
							return;
						} else {
							player.getPacketSender().loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (long friend : player.friends) {
				if (friend != 0) {
					if (l == friend && player.playerRights < 2) {
						player.getPacketSender().loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (long friend : player.friends) {
			if (friend != 0) {
				if (l == friend) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */

	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId,
			int healType) {
		player.attackTimer = player.getCombatAssistant().getAttackDelay();
		if (player.duelRule[5]) {
			player.getPacketSender().sendMessage(
					"Potions has been disabled in this duel!");
			return;
		}
		if (!player.isDead
				&& System.currentTimeMillis() - player.foodDelay > 2000) {
			if (player.getItemAssistant().playerHasItem(itemId, 1, itemSlot)) {
				player.getPacketSender().sendMessage(
						"You drink the "
								+ DeprecatedItems.getItemName(itemId)
										.toLowerCase() + ".");
				player.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				player.startAnimation(0x33D);
				player.getItemAssistant().deleteItem(itemId, itemSlot, 1);
				player.getItemAssistant().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}

	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {
		if (!player.getItemAssistant().playerHasItem(itemId, 1, slot)
				|| itemId == 995) {
			return;
		}
		boolean canAlch = true;
		switch (spellId) {
		case 1162: // low alch
			player.getPacketSender().sendShowTab(6);
			if (player.inTrade) {
				player.getPacketSender().sendMessage("You can't alch while in a trade!");
				return;
			}
			if (System.currentTimeMillis() - player.alchDelay <= 1000) {
				return;
			}
			if (Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA)) {
				player.getMageTrainingArena().alchItem(itemId, spellId);
				return;
			}
			if (!player.getCombatAssistant().checkMagicReqs(49)) {
				return;
			}
			canAlch = true;
			for (int i : ItemConstants.ITEM_UNALCHABLE) {
				if (itemId == i) {
					player.getPacketSender().sendMessage("You can't alch that item!");
					canAlch = false;
					return;
				}
			}
			if (canAlch) {
				int value = (int) Math.floor(player.getShopAssistant().getItemShopValue(itemId) * 0.4);
				String itemName = DeprecatedItems.getItemName(itemId).toLowerCase();
				if (player.getPlayerAssistant().isPlayer()) {
					GameLogger.writeLog(player.playerName, "alchemy", player.playerName + " cast Low Alchemy on " + itemName + " for " + GameLogger.formatCurrency(value) + " coins");
				}
				player.getItemAssistant().deleteItem(itemId, slot, 1);
				//855 - 858
				if (itemId > 854 && itemId < 857) {
					player.getItemAssistant().addItem(995, 512);
				} else if (itemId > 856 && itemId < 859) {
					player.getItemAssistant().addItem(995, 320);
				} else if (itemId > 860 && itemId < 863) {
					player.getItemAssistant().addItem(995, 640);
				} else if (itemId > 858 && itemId < 861) {
					player.getItemAssistant().addItem(995, 1024);
				} else {
					player.getItemAssistant().addItem(995, value);
				}
				player.startAnimation(MagicData.MAGIC_SPELLS[49][2]);
				player.gfx100(MagicData.MAGIC_SPELLS[49][3]);
				player.alchDelay = System.currentTimeMillis();
				player.getPacketSender().sendShowTab(6);
				addSkillXP(31, 6);
				player.getPacketSender().sendSound(
						SoundList.LOW_ALCHEMY, 100, 0);
				RandomEventHandler.addRandom(player);
				refreshSkill(6);
			}
			break;

		case 1155: // Lvl-1 enchant sapphire
		case 1165: // Lvl-2 enchant emerald
		case 1176: // Lvl-3 enchant ruby
		case 1180: // Lvl-4 enchant diamond
		case 1187: // Lvl-5 enchant dragonstone
		case 6003: // Lvl-6 enchant onyx
			player.getPacketSender().sendShowTab(6);
			if (Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA)) {
				player.getMageTrainingArena().enchantItem(itemId, spellId);
			} else {
				player.getEnchanting().enchantItem(itemId, spellId);
			}
			break;

		case 1178: // high alch
			player.getPacketSender().sendShowTab(6);
			if (player.inTrade) {
				player.getPacketSender().sendMessage("You can't alch while in a trade!");
				return;
			}
			if (System.currentTimeMillis() - player.alchDelay <= 1000) {
				return;
			}
			if (Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA)) {
				player.getMageTrainingArena().alchItem(itemId, spellId);
				return;
			}
			if (!player.getCombatAssistant().checkMagicReqs(50)) {
				break;
			}
			canAlch = true;
			for (int i : ItemConstants.ITEM_UNALCHABLE) {
				if (itemId == i) {
					player.getPacketSender().sendMessage("You can't alch that item!");
					canAlch = false;
					return;
				}
			}
			if (canAlch) {
				int value = (int) Math.floor(player.getShopAssistant().getItemShopValue(itemId) * 0.75);
				String itemName = DeprecatedItems.getItemName(itemId).toLowerCase();
				if (player.getPlayerAssistant().isPlayer()) {
					GameLogger.writeLog(player.playerName, "alchemy", player.playerName + " cast High Alchemy on " + itemName + " for" + GameLogger.formatCurrency(value) + " coins");
				}
				player.getItemAssistant().deleteItem(itemId, slot, 1);
				if (itemId > 854 && itemId < 857) {
					player.getItemAssistant().addItem(995, 768);
				} else if (itemId > 856 && itemId < 859) {
					player.getItemAssistant().addItem(995, 480);
				} else if (itemId > 858 && itemId < 861) {
					player.getItemAssistant().addItem(995, 1536);
				} else if (itemId > 860 && itemId < 863) {
					player.getItemAssistant().addItem(995, 960);
				} else {
					player.getItemAssistant().addItem(995, (int) (player.getShopAssistant().getItemShopValue(itemId) * .75));
				}
				player.startAnimation(MagicData.MAGIC_SPELLS[50][2]);
				player.gfx100(MagicData.MAGIC_SPELLS[50][3]);
				player.alchDelay = System.currentTimeMillis();
				player.getPacketSender().sendShowTab(6);
				RandomEventHandler.addRandom(player);
				addSkillXP(65, 6);
				player.getPacketSender().sendSound(
						SoundList.HIGH_ALCHEMY, 100, 0);
				refreshSkill(6);
			}
			break;
		}
	}

	public String optionType = "null";

	public String deathMsgs() {
		int deathMsgs = Misc.random(9);
		switch (deathMsgs) {
		case 0:
			return "With a crushing blow, you defeat " + player.playerName
					+ ".";
		case 1:
			return "It's a humiliating defeat for " + player.playerName + ".";
		case 2:
			return "" + player.playerName
					+ " didn't stand a chance against you.";
		case 3:
			return "You've defeated " + player.playerName + ".";
		case 4:
			return "" + player.playerName
					+ " regrets the day they met you in combat.";
		case 5:
			return "It's all over for " + player.playerName + ".";
		case 6:
			return "" + player.playerName + " falls before your might.";
		case 7:
			return "Can anyone defeat you? Certainly not " + player.playerName
					+ ".";
		case 8:
			return "You were clearly a better fighter than "
					+ player.playerName + ".";
		}
		return "You've defeated " + player.playerName + ".";
	}

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[player.playerId] = 0;
			}
		}
	}

	public void resetTb() {
		player.teleBlockLength = 0;
		player.teleBlockDelay = 0;
	}

	public void resetFollowers() {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				if (player.followId == player.playerId) {
					Client c = (Client) player;
					c.getPlayerAssistant().resetFollow();
				}
			}
		}
	}

	public void applyDead() {
		player.getDueling().stakedItems.clear();
		player.respawnTimer = 15;
		player.isDead = true;
		// client.getPacketDispatcher().sendSound(203, 100, 0);
		int weapon = player.playerEquipment[player.playerWeapon];
		if (player.duelStatus != 6) {
			player.killerId = findKiller();
			Client opponent = (Client) PlayerHandler.players[player.killerId];
			if (opponent != null) {
				if(player.inWild() && player.npcIndex < 1) {
					if (player.killerId != player.playerId) {
						opponent.getPacketSender().sendMessage(deathMsgs());
					}
				}
				if (player.getPlayerAssistant().isPlayer() && player.inWild() && player.npcIndex < 1) {
					GameLogger.writeLog(opponent.playerName, "pkingkiller", opponent.playerName + " killed " + player.playerName + " absX: " + player.absX + " absY: " + player.absY + "");
				}
				if (opponent.getPlayerAssistant().isPlayer() && player.inWild() && player.npcIndex < 1) {
					GameLogger.writeLog(player.playerName, "pkingkilled", player.playerName + " was killed by " + opponent.playerName + " absX: " + opponent.absX + " absY: " + opponent.absY + "");
				}
				if (weapon == CastleWars.SARA_BANNER || weapon == CastleWars.ZAMMY_BANNER) {
					player.getItemAssistant().removeItem(weapon, 3);
					player.getItemAssistant().deleteItem(weapon, 1);
					CastleWars.dropFlag(player, weapon);
				}
				if (opponent.duelStatus == 5) {
					opponent.duelStatus++;
				}
				if (opponent.getCannon().hasCannon()) {
					opponent.getCannon().handleDeath();
				}
			}
		}
		player.faceUpdate(0);
		player.npcIndex = 0;
		player.playerIndex = 0;
		player.stopMovement();
		if (player.inCw()) {
			player.cwDeaths += 1;
			Client o = (Client) PlayerHandler.players[player.killerId];
			o.cwKills += 1;
		} else if (player.duelStatus <= 4) {
			player.getDueling().stakedItems.clear();
		} else if (player.duelStatus != 6) {
			Client duelOpponent = (Client) PlayerHandler.players[player.duelingWith];
			player.getDueling().stakedItems.clear();
			player.getPacketSender().sendMessage("You have lost the duel!");
			if (duelOpponent.getPlayerAssistant().isPlayer()) {
				GameLogger.writeLog(player.playerName, "duelingkilled", player.playerName + " was killed by " + duelOpponent.playerName + " in the duel arena.");
			}
		}
		if (player.vampSlayer == 3 && player.clickedVamp) {
			player.clickedVamp = false;
		} else if (player.isWoodcutting) {
			player.isWoodcutting = false;
		} else if (player.playerSkilling[Constants.FISHING]) {
			player.playerSkilling[Constants.FISHING] = false;
		} else if(player.clickedTree) {
				player.clickedTree = false;
		}
		resetDamageDone();
		player.specAmount = 10;
		player.getItemAssistant().addSpecialBar(player.playerEquipment[player.playerWeapon]);
		resetFollowers();
		player.attackTimer = 10;
		player.getPacketSender().closeAllWindows();
		player.tradeResetNeeded = true;
	}

	public void giveLife() {
		player.isDead = false;
		player.faceUpdate(-1);
		player.freezeTimer = 0;
		player.getPacketSender().closeAllWindows();
		player.tradeResetNeeded = true;
		if (player.duelStatus <= 4 // Player Duelling
				&& !player.inDuelArena() // Dual Arena
				&& !CastleWars.isInCw(player) // Castle Wars
				&& !PestControl.isInGame(player) // Pest Control
				&& !PestControl.isInPcBoat(player) // Pest Control
				&& player.tutorialProgress > 35 // Tutorial Island
				&& FightPits.getState(player) == null // Fight Pits
				&& !player.inFightCaves()) { // Fight Caves
			player.getItemAssistant().resetKeepItems();
			// admin and bots do not lose/drop items
			if (player.playerRights != 3 && !player.isBot) {
				if (!player.isSkulled) { // what items to keep
					player.getItemAssistant().keepItem(0, true);
					player.getItemAssistant().keepItem(1, true);
					player.getItemAssistant().keepItem(2, true);
				}
				if (player.getPrayer().prayerActive[10] && System.currentTimeMillis() - player.lastProtItem > 700) {
					player.getItemAssistant().keepItem(3, true);
				}
				player.getItemAssistant().dropAllItems(); // drop all items
				player.getItemAssistant().deleteAllItems(); // delete all items

				if (!player.isSkulled) { // add the kept items once we finish deleting and dropping them
					for (int i1 = 0; i1 < 3; i1++) {
						if (player.itemKeptId[i1] > 0) {
							player.getItemAssistant().addItem(player.itemKeptId[i1], 1);
						}
					}
				}
				if (player.getPrayer().prayerActive[10]) { // if we have protect items
					if (player.itemKeptId[3] > 0) {
						player.getItemAssistant().addItem(player.itemKeptId[3], 1);
					}
				}
			}
			player.getItemAssistant().resetKeepItems();
		}
		PrayerDrain.resetPrayers(player);
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = getLevelForXP(player.playerXP[i]);
			refreshSkill(i);
		}
		if (FightPits.getState(player) != null) {
			FightPits.handleDeath(player);
		} else if (Boundary.isIn(player, Boundary.FIGHT_PITS)) {
			player.getPlayerAssistant().movePlayer(2399, 5178, 0);
		} else if (player.inCw()) {
			if (CastleWars.getTeamNumber(player) == 1) {
				player.getPlayerAssistant().movePlayer(2426 + Misc.random(3),
						3076 - Misc.random(3), 1);
			} else {
				player.getPlayerAssistant().movePlayer(2373 + Misc.random(3),
						3131 - Misc.random(3), 1);
			}
		} else if (PestControl.isInGame(player) || Boundary.isIn(player, Boundary.PC_GAME)) {
			player.getPlayerAssistant().movePlayer(2658, 2609, 0);
			player.getDialogueHandler().sendDialogues(601, 3790);
		} else if (player.tutorialProgress < 36 || Boundary.isIn(player, Boundary.TUTORIAL)) {
			player.getPlayerAssistant().movePlayer(3094, 3107, 0);
			player.diedOnTut = true;
			player.getDialogueHandler().sendStatement(
					"Oh dear you died! Go back to the step you",
					"were on to continue Tutorial Island.");
			player.getPacketSender().createArrow(3098, 3107, player.getH(),
					2);
		} else if (player.inFightCaves()) {// to fix
			player.getPlayerAssistant().resetTzhaar();
		} else if (player.duelStatus != 5 && !player.lostDuel) { // if we are
																	// not in a
																	// duel
																	// repawn to
																	// wildy
			movePlayer(Constants.RESPAWN_X, Constants.RESPAWN_Y, 0);
			player.isSkulled = false;
			player.skullTimer = 0;
			player.attackedPlayers.clear();
		} else if (player.duelStatus == 5 || player.lostDuel) { // we are in a
																// duel,
																// respawn
																// outside
																// of arena
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o != null) {
				o.getPacketSender().createPlayerHints(10, -1);
				if (o.duelStatus == 6 && player.duelStatus == 5) {
					o.getDueling().duelVictory();
				}
			}
			player.getPacketSender().sendSound(122, 100, 0);
			player.getPlayerAssistant().movePlayer(
					Constants.DUELING_RESPAWN_X + 5,
					Constants.DUELING_RESPAWN_Y + 5, 0);
			assert o != null;
			if (o != null) {
				o.getPacketSender().sendSound(122, 100, 0);
				o.getPlayerAssistant().movePlayer(
						Constants.DUELING_RESPAWN_X + 5,
						Constants.DUELING_RESPAWN_Y + 5, 0);
			}
			if (player.duelStatus != 6) { // if we have won but have died,
											// don't reset the duel status.
				player.getDueling().resetDuel();
			}
			player.lostDuel = false;
		}
		PlayerSave.saveGame(player);
		player.getPacketSender().sendMessage("Oh dear, you are dead!");
		player.getPacketSender().sendQuickSong(75, 1);
		player.getCombatAssistant().resetPlayerAttack();
		resetAnimation();
		player.startAnimation(65535);
		player.getPacketSender().frame1();
		resetTb();
		player.playerEnergy = 100;
		player.getPacketSender().sendString((int) Math.ceil(player.playerEnergy) + "%", 149);
		player.isSkulled = false;
		player.attackedPlayers.clear();
		player.headIconPk = -1;
		player.skullTimer = -1;
		player.damageTaken = new int[PlayerHandler.players.length];
		requestUpdates();
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (player.newLocation) {
		case 1:
			player.getPacketSender().sendMapState(2);
			movePlayer(3578, 9706, 3);
			break;
		case 2:
			player.getPacketSender().sendMapState(2);
			movePlayer(3568, 9683, 3);
			break;
		case 3:
			player.getPacketSender().sendMapState(2);
			movePlayer(3557, 9703, 3);
			break;
		case 4:
			player.getPacketSender().sendMapState(2);
			movePlayer(3556, 9718, 3);
			break;
		case 5:
			player.getPacketSender().sendMapState(2);
			movePlayer(3534, 9704, 3);
			break;
		case 6:
			player.getPacketSender().sendMapState(2);
			movePlayer(3546, 9684, 3);
			break;
		}
		player.newLocation = 0;
	}

	public int[] getFollowLocation(int x, int y) {
		int[] nonDiags = {0, 2, 4, 6};
		int[][] nodes = {
				{ x + Misc.directionDeltaX[nonDiags[0]], y + Misc.directionDeltaY[nonDiags[0]] },
				{ x + Misc.directionDeltaX[nonDiags[1]], y + Misc.directionDeltaY[nonDiags[1]] },
				{ x + Misc.directionDeltaX[nonDiags[2]], y + Misc.directionDeltaY[nonDiags[2]] },
				{ x + Misc.directionDeltaX[nonDiags[3]], y + Misc.directionDeltaY[nonDiags[3]] }
		};

		int bestX = 0;
		int bestY = 0;
		double bestDist = 99999;

		boolean projectile = player.usingMagic || player.usingBow || player.usingRangeWeapon;

		for (int i = 0; i < nodes.length; i++) {
			double dist = Misc.distance(player.absX, player.absY, nodes[i][0], nodes[i][1]);
			if (dist < bestDist) {
				if (PathFinder.getPathFinder().accessible(player.absX, player.absY, player.heightLevel, nodes[i][0], nodes[i][1])) {
					if (!projectile || PathFinder.isProjectilePathClear(nodes[i][0], nodes[i][1], player.heightLevel, x, y)) {
						bestDist = dist;
						bestX = nodes[i][0];
						bestY = nodes[i][1];
					}
				}
			}
		}

		if (bestX == 0 && bestY == 0) {
			bestX = x;
			bestY = y;
		}

		return new int[] {bestX, bestY};
	}

	public void followPlayer() {
		if (PlayerHandler.players[player.followId] == null
				|| PlayerHandler.players[player.followId].isDead) {
			resetFollow();
			return;
		}
		if (player.freezeTimer > 0) {
			return;
		}
		if (player.isDead || player.playerLevel[Constants.HITPOINTS] <= 0) {
			return;
		}

		int otherX = PlayerHandler.players[player.followId].getX();
		int otherY = PlayerHandler.players[player.followId].getY();

		if (!player.goodDistance(otherX, otherY, player.getX(), player.getY(),
				25)) {
			player.followId = 0;
			resetFollow();
			return;
		}

		int[] follow = getFollowLocation(otherX, otherY);
		player.faceUpdate(player.followId + 32768);
		PathFinder.getPathFinder().findRoute(player, follow[0], follow[1], false, 1, 1);
	}


	public void followNpc() {
		Npc npc = NpcHandler.npcs[player.followId2];
		if (npc == null || npc.isDead) {
			return;
		}
		int x = NpcHandler.npcs[player.followId2].getX();
		int y = NpcHandler.npcs[player.followId2].getY();
		if (!player.goodDistance(x, y, player.getX(), player.getY(), 25)) {
			player.followId2 = 0;
			resetFollow();
			return;
		}

		int[] follow = getFollowLocation(x, y);
		player.faceUpdate(player.followId2);
        PathFinder.getPathFinder().findRoute(player, follow[0], follow[1], true, 1, 1);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2) {
			return 2;
		} else if (j - i < -2) {
			return -2;
		} else {
			return j - i;
		}
	}

	public void resetFollow() {
		player.followId = 0;
		player.followId2 = 0;
		player.mageFollow = false;
	}

	public void walkTo(int i, int j) {
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50) {
			player.newWalkCmdSteps = 0;
		}
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (player.freezeDelay > 0) {
			return;
		}
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50) {
			player.newWalkCmdSteps = 0;
		}
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (player.freezeDelay > 0) {
			return;
		}
		player.newWalkCmdSteps = 1;
		int xMove = otherX - player.getX();
		int yMove = 0;
		if (xMove == 0) {
			yMove = otherY - player.getY();
		}

		int k = player.getX() + xMove;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + yMove;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkToCheck(int i, int j) {
		if (player.freezeDelay > 0) {
			return;
		}
		player.newWalkCmdSteps = 0;
		if (++player.newWalkCmdSteps > 50) {
			player.newWalkCmdSteps = 0;
		}
		int k = player.getX() + i;
		k -= player.mapRegionX * 8;
		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
		int l = player.getY() + j;
		l -= player.mapRegionY * 8;

		for (int n = 0; n < player.newWalkCmdSteps; n++) {
			player.getNewWalkCmdX()[n] += k;
			player.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - player.lastSpear < 4000) {
			return 0;
		}
		if (place1 - place2 == 0) {
			return 0;
		} else if (place1 - place2 < 0) {
			return 1;
		} else if (place1 - place2 > 0) {
			return -1;
		}
		return 0;
	}

	public boolean fullVeracs() {
		return player.playerEquipment[player.playerHat] == 4753
				&& player.playerEquipment[player.playerChest] == 4757
				&& player.playerEquipment[player.playerLegs] == 4759
				&& player.playerEquipment[player.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return player.playerEquipment[player.playerHat] == 4724
				&& player.playerEquipment[player.playerChest] == 4728
				&& player.playerEquipment[player.playerLegs] == 4730
				&& player.playerEquipment[player.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		player.getCombatAssistant().getPlayerAnimIndex();
		player.startAnimation(player.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
		player.updateWalkEntities();
	}

	public void sendAutoRetalitate() {
		player.getPacketSender().sendConfig(172, player.autoRet == 1 ? 0 : 1);
	}

	public void firstTimeTutorial() {
		if (Constants.TUTORIAL_ISLAND && player.tutorialProgress == 0) {
			player.getItemAssistant().deleteAllItems();
			player.getPlayerAssistant().hideAllSideBars();
			movePlayer(3094, 3107, 0);
			player.getDialogueHandler().sendDialogues(2995, -1);
			player.tutorialProgress = 0;
			player.isRunning2 = false;
			player.autoRet = 1;
			sendAutoRetalitate();
			LightSources.saveBrightness(player);
		} else if (player.tutorialProgress == 0 && !Constants.TUTORIAL_ISLAND) {
			player.getPlayerAssistant().sendSidebars();
			PlayerAssistant.removeHintIcon(player);
			player.getPacketSender().walkableInterface(-1);
			player.getPacketSender().chatbox(-1);
			player.getItemAssistant().deleteAllItems();
			player.getItemAssistant().clearBank();
			player.getPlayerAssistant().addStarter();
			player.getPlayerAssistant().movePlayer(3233, 3229, 0);
			player.getPacketSender().sendMessage("Welcome to @blu@" + Constants.SERVER_NAME + " World: " + Constants.WORLD + "@bla@ - we are currently in Server Stage v@blu@" + Constants.TEST_VERSION + "@bla@.");
			player.getPacketSender().sendMessage("@red@Did you know?@bla@ We're open source and pull requests are welcome!");
			player.getPacketSender().sendMessage("Source code: github.com/2006-Scape/2006Scape");
			player.getPacketSender().sendMessage("Discord: https://discord.gg/hZ6VfWG");
			player.getDialogueHandler().sendDialogues(3115, 2224);
			player.isRunning2 = false;
			player.autoRet = 1;
			sendAutoRetalitate();
			LightSources.saveBrightness(player);
			if (!player.hasBankpin) {
				player.getPacketSender().sendMessage("You do not, have a bank pin it is highly recommended you set one.");
			}
		}
	}

	public int getTotalLevel() {
		int total = 0;
		for (int i = 0; i <= 20; i++) {
			total += getLevelForXP(player.playerXP[i]);
		}
		return total;
	}

	public void levelUp(int skill) {
		SkillHandler.resetSkills(player);
		player.getPacketSender().sendString("Total Lvl: "+getTotalLevel(), 3984);
		player.getPacketSender().sendString("Combat Lvl: "+player.calculateCombatLevel()+"", 3983);

		Optional<SkillData> data = SkillData.getSkill(skill);
		
		if(!data.isPresent())
			return;
					
		player.getPacketSender().sendMessage("Congratulations, you've advanced a level in "+data.get().toString()+"!");
		player.getPacketSender().sendString("Congratulations, you've advanced a level in "+data.get().toString()+"!", data.get().getFrame2());
		player.getPacketSender().sendString("Your " +data.get().toString()+ " level is now " + getLevelForXP(player.playerXP[skill]) + ".", data.get().getFrame3());
		player.getPacketSender().sendChatInterface(data.get().getFrame1());
		player.gfx0(199);
		player.dialogueAction = 0;
		player.nextChat = 0;
		String levelType= "";
		levelType = data.get().toString();
		switch(levelType)
		{
			case "Cooking":
				if (getLevelForXP(player.playerXP[skill]) % 10 == 0) //Play a milestone cooking sound
				{
					player.getPacketSender().sendQuickSong(89, 1);
				}
				else if(getLevelForXP(player.playerXP[skill]) == 99)
				{
					player.getPacketSender().sendQuickSong(68, 1);
				}
				else {
					player.getPacketSender().sendQuickSong(68, 1);
				}
				break;
			case "Fishing":
				player.getPacketSender().sendQuickSong(89, 1);
				break;
			case "Attack":
				if (getLevelForXP(player.playerXP[skill]) % 10 == 0) //Play a milestone attack sound
				{
					player.getPacketSender().sendQuickSong(84, 1);
				}
				else if(getLevelForXP(player.playerXP[skill]) == 99)
				{
					player.getPacketSender().sendQuickSong(105, 1);
				}
				else {
					player.getPacketSender().sendQuickSong(91, 1);
				}
				break;
			case "Strength":
				player.getPacketSender().sendQuickSong(107, 0);
				break;
			case "Defence":
				player.getPacketSender().sendQuickSong(88, 0);
				break;
			case "Herblore":
				player.getPacketSender().sendQuickSong(90, 1);
				break;
			case "Prayer":
				player.getPacketSender().sendQuickSong(83, 1);
				break;
			case "Mining":
				player.getPacketSender().sendQuickSong(99, 1);
				break;
			case "Magic":
				if (getLevelForXP(player.playerXP[skill]) % 10 == 0) //Play a milestone magic sound
				{
					player.getPacketSender().sendQuickSong(86, 1);
				}
				else if(getLevelForXP(player.playerXP[skill]) == 99)
				{
					player.getPacketSender().sendQuickSong(102, 1);
				}
				else {
					player.getPacketSender().sendQuickSong(87, 1);
					}

				break;
			case "Ranged":
				player.getPacketSender().sendQuickSong(74, 1);
				break;
			case "Woodcutting":
				player.getPacketSender().sendQuickSong(92, 1);
				break;
			case "Fletching":
				player.getPacketSender().sendQuickSong(77, 1);
				break;
			case "Firemaking":
				player.getPacketSender().sendQuickSong(71, 1);
				break;
			case "Smithing":
				player.getPacketSender().sendQuickSong(103, 1);
				break;
			case "Hitpoints":
				player.getPacketSender().sendQuickSong(94, 1);
				break;
			case "Runecrafting":
				player.getPacketSender().sendQuickSong(72, 1);
				break;
			case "Crafting":
				player.getPacketSender().sendQuickSong(100, 1);
				break;
			case "Thieving":
				player.getPacketSender().sendQuickSong(73, 1);
				break;
			case "Agility":
				player.getPacketSender().sendQuickSong(95, 1);
				break;
			case "Farming":
				player.getPacketSender().sendQuickSong(101, 1);
				break;
			case "Slayer":
				player.getPacketSender().sendQuickSong(109, 1);
				break;
		}
	}

	public void refreshSkill(int skill) {
		player.getPacketSender().sendString("Total Lvl: "+getTotalLevel(), 3984);
		player.getPacketSender().sendString("Combat Lvl: "+player.calculateCombatLevel()+"", 3983);
		Optional<SkillData> data = SkillData.getSkill(skill);

		if(!data.isPresent())
			return;

		player.getPacketSender().sendString("" + player.playerLevel[skill] + "", data.get().getFrame4());
		player.getPacketSender().sendString("" + getLevelForXP(player.playerXP[skill]) + "", data.get().getFrame5());
		player.getPacketSender().sendString("" + player.playerXP[skill] + "", data.get().getFrame6());
		player.getPacketSender().sendString("" + getXPForLevel(getLevelForXP(player.playerXP[skill]) + 1) + "", data.get().getFrame7());
		if (skill == 5) {
			player.getPacketSender().sendString("" + player.playerLevel[Constants.PRAYER] + "/" + getLevelForXP(player.playerXP[Constants.PRAYER]) + "", 687);// Prayer
		}
		// Update skill data
		player.getPacketSender().setSkillLevel(skill, player.playerLevel[skill], player.playerXP[skill]);
	}

	/* Updated methods for getXPForLevel/getLevelForXP */
	private static final int[] xp_per_level = new int[100];
	
	static {
		int points = 0, output = 0;
		for (int lvl = 1; lvl <= 99; lvl++) {
			xp_per_level[lvl] = output;
			points += Math.floor(lvl + 300 * Math.pow(2, lvl / 7.0));
			output = (int) Math.floor(points / 4);
		}
	}
	
	public static int getXPForLevel(int level) {
		return level > 99 ? 200_000_000 : xp_per_level[level];
	}

	public static int getLevelForXP(int exp) {
		for (int lvl = 1; lvl <= 98; lvl++) {
			if (exp < xp_per_level[lvl + 1]) {
				return lvl;
			}
		}
		return 99;
	}

	public boolean addSkillXP(double amount, int skill) {
		if (amount + player.playerXP[skill] < 0 || player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		if (player.tutorialProgress < 36 && player.playerLevel[skill] == 3 && Constants.TUTORIAL_ISLAND) {
			return false;
		}
		if (Constants.VARIABLE_XP_RATE){
			amount *= player.getXPRate();
		} else {
			amount *= Constants.XP_RATE;
		}
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < getLevelForXP(player.playerXP[skill]) && skill != 3 && skill != 5) {
				player.playerLevel[skill] = getLevelForXP(player.playerXP[skill]);
			}
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		player.getPacketSender().setSkillLevel(skill, player.playerLevel[skill], player.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public void resetBarrows() {
		player.barrowsNpcs[0][1] = 0;
		player.barrowsNpcs[1][1] = 0;
		player.barrowsNpcs[2][1] = 0;
		player.barrowsNpcs[3][1] = 0;
		player.barrowsNpcs[4][1] = 0;
		player.barrowsNpcs[5][1] = 0;
		player.barrowsKillCount = 0;
		player.randomCoffin = Misc.random(3) + 1;
	}

	public int getNpcId(int id) {
		for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
			if (NpcHandler.npcs[i] != null) {
				if (NpcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		player.getPacketSender().object(-1, x, x, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		player.getPacketSender().object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		removeObject(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
	}

	public boolean inPitsWait() {
		return player.getX() <= 2404 && player.getX() >= 2394 && player.getY() <= 5175 && player.getY() >= 5169;
	}

	public int antiFire() {
		int toReturn = 0;
		if (player.antiFirePot) {
			toReturn++;
		}
		if (player.playerEquipment[player.playerShield] == 1540 || player.playerEquipment[player.playerShield] == 11284 || player.playerEquipment[player.playerShield] == 11283) {
			toReturn++;
		}
		return toReturn;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 },
				{ 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 },
				{ 560, 100000 }, { 555, 300000 } };
		for (int[] element : itemsToCheck) {
			if (element[1] < player.getItemAssistant().getTotalCount(element[0])) {
				return true;
			}
		}
		return false;
	}

	public int getWearingAmount() {
		int count = 0;
		for (int element : player.playerEquipment) {
			if (element > 0) {
				count++;
			}
		}
		return count;
	}

	public void getSpeared(int otherX, int otherY) {
		int x = player.absX - otherX;
		int y = player.absY - otherY;
		if (x > 0) {
			x = 1;
		} else if (x < 0) {
			x = -1;
		}
		if (y > 0) {
			y = 1;
		} else if (y < 0) {
			y = -1;
		}
		moveCheck(x, y);
		player.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(player.absX + xMove, player.absY + yMove, player.heightLevel);
	}

	public int findKiller() {
		int killer = player.playerId;
		int damage = 0;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] == null) {
				continue;
			}
			if (j == player.playerId) {
				continue;
			}
			if (player.goodDistance(player.absX, player.absY,
					PlayerHandler.players[j].absX,
					PlayerHandler.players[j].absY, 40)
					|| player.goodDistance(player.absX, player.absY + 9400,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY, 40)
					|| player.goodDistance(player.absX, player.absY,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY + 9400, 40)) {
				if (player.damageTaken[j] > damage) {
					damage = player.damageTaken[j];
					killer = j;
				}
			}
		}
		return killer;
	}

	public void appendPoison(int damage) {
		if (player.duelStatus == 5 || player.isDead) {
			player.poisonDamage = 0;
			return;
		}
		if (System.currentTimeMillis() - player.lastPoisonSip > player.poisonImmune && player.poison == false) {
			player.getPacketSender().sendMessage("You have been poisoned.");
			player.poisonDamage = damage;
			player.poison = true;
		}
		if (player.poisonDamage == 0 && player.isDead == false) {
			player.getPacketSender().sendMessage("The poison has worn off.");
			player.poison = false;
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkPouch(int i) {
		if (i < 0) {
			return;
		}
		player.getPacketSender().sendMessage("This pouch has " + player.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0) {
			return;
		}
		int toAdd = player.POUCH_SIZE[i] - player.pouches[i];
		if (toAdd > player.getItemAssistant().getItemAmount(1436)) {
			toAdd = player.getItemAssistant().getItemAmount(1436);
		}
		if (toAdd > player.POUCH_SIZE[i] - player.pouches[i]) {
			toAdd = player.POUCH_SIZE[i] - player.pouches[i];
		}
		if (toAdd > 0) {
			player.getItemAssistant().deleteItem(1436, toAdd);
			player.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0) {
			return;
		}
		int toAdd = player.pouches[i];
		if (toAdd > player.getItemAssistant().freeSlots()) {
			toAdd = player.getItemAssistant().freeSlots();
		}
		if (toAdd > 0) {
			player.getItemAssistant().addItem(1436, toAdd);
			player.pouches[i] -= toAdd;
		}
	}

	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = player.getItemAssistant().getItemAmount(995);
		for (int j = 0; j < player.playerItems.length; j++) {
			boolean breakOut = false;
			for (int[] brokenBarrow : player.getItemAssistant().brokenBarrows) {
				if (player.playerItems[j] - 1 == brokenBarrow[1]) {
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						player.getPacketSender().sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					player.playerItems[j] = brokenBarrow[0] + 1;
				}
			}
			if (breakOut) {
				break;
			}
		}
		if (totalCost > 0) {
			player.getItemAssistant().deleteItem(995, player.getItemAssistant().getItemSlot(995), totalCost);
		}
	}

	public void handleWeaponStyle() {
		if (player.fightMode == 0) {
			player.getPacketSender().sendConfig(43, player.fightMode);
		} else if (player.fightMode == 1) {
			player.getPacketSender().sendConfig(43, 3);
		} else if (player.fightMode == 2) {
			player.getPacketSender().sendConfig(43, 1);
		} else if (player.fightMode == 3) {
			player.getPacketSender().sendConfig(43, 2);
		}
	}

	public int totalGold() {
		return player.getItemAssistant().getBankQuantity(996) + player.getItemAssistant().getItemAmount(995);
	}

	public void unMorphPlayer() {
		sendSidebars();
		player.getItemAssistant().sendWeapon(player.playerEquipment[player.playerWeapon], DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]));
		if (player.playerEquipment[player.playerRing] == 6583 || player.playerEquipment[player.playerRing] == 7927) {
			int ring = player.playerEquipment[player.playerRing];
			player.getItemAssistant().deleteEquipment(ring, player.playerRing);
			player.getItemAssistant().addItem(ring, 1);
		}
		player.wearId = 0;
		player.isNpc = false;
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
	}

	/**
	 * anchors the camera to a specific view (for cutscenes)
	 * @param x  The X Coordinate (Within the player's loaded area)
	 * @param y The Y Coordinate (Within the player's loaded area)
	 * @param height The Height of Camera (not relative to the game world height)
	 * @param speed The Camera Speed (Speed at which the camera turns to where it should point?)
	 * @param angle The Camera Angle
	 */
	public void sendCameraCutscene(int x, int y, int height, int speed, int angle) {
		player.getOutStream().createFrame(177);
		player.getOutStream().writeByte(x); // divided by 64 apparently allows real world coords
		player.getOutStream().writeByte(y); // divided by 64 apparently allows real world coords
		player.getOutStream().writeWord(height); //
		player.getOutStream().writeByte(speed); //
		player.getOutStream().writeByte(angle);
	}

	/**
	 * anchors the camera to a specific view (for cutscenes)
	 * @param x  The X Coordinate (Within the player's loaded area)
	 * @param y The Y Coordinate (Within the player's loaded area)
	 * @param height The Height of Camera (not relative to the game world height)
	 * @param speed The Camera Speed (Speed at which the camera turns to where it should point?)
	 * @param angle The Camera Angle
	 */
	public void sendCameraCutscene2(int x, int y, int height, int speed, int angle) {
		player.getOutStream().createFrame(166);
		player.getOutStream().writeByte(x); //
		player.getOutStream().writeByte(y); //
		player.getOutStream().writeWord(height); //
		player.getOutStream().writeByte(speed); //
		player.getOutStream().writeByte(angle);
	}

	/**
	 * Makes the player view shake (Cutscene use and other events like Barrows?)
	 * @param i1
	 * @param i2
	 * @param i3
	 * @param i4
	 */
	public void sendCameraShake(int i1, int i2, int i3, int i4) {
		player.getOutStream().createFrame(35); //Not sure of each specific integer, but some cannot be greater than 5 (maybe less)
		player.getOutStream().writeByte(i1);
		player.getOutStream().writeByte(i2);
		player.getOutStream().writeByte(i3);
		player.getOutStream().writeByte(i4);
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
	}

	/**
	 * Resets the players camera from using sendCameraShake and sendCameraCutscene
	 */
	public void sendCameraReset() {
		synchronized(player) {
			if(player.getOutStream() != null && player != null) {
				player.getOutStream().createFrame(107);
				player.flushOutStream();
			}
		}
	}
	
}
