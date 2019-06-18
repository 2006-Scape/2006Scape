package redone.game.players;

import java.util.ArrayList;
import java.util.Random;

import redone.Constants;
import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.combat.magic.MagicData;
import redone.game.content.combat.prayer.PrayerDrain;
import redone.game.content.combat.range.RangeData;
import redone.game.content.minigames.FightPits;
import redone.game.content.minigames.PestControl;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.content.music.sound.SoundList;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.content.skills.smithing.Superheat;
import redone.game.items.GameItem;
import redone.game.items.ItemAssistant;
import redone.game.items.impl.LightSources;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.players.antimacro.AntiBotting;
import redone.util.GameLogger;
import redone.util.Misc;
import redone.world.TileControl;
import redone.world.clip.PathFinder;
import redone.world.clip.Region;

public class PlayerAssistant {

	private Client player;

	public PlayerAssistant(Client Client) {
		this.player = Client;
	}
	
	public void objectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		for (Player p : PlayerHandler.players) {
			if(p != null) {
				Client players = (Client)p;
				if(players.distanceToPoint(X, Y) <= 25) {
					player.getActionSender().createPlayersObjectAnim(X, Y, animationID, tileObjectType, orientation);	
				}
			}
		}
	}
	
	public void resetAutocast() {
		player.autocastId = 0;
		player.autocasting = false;
		player.getPlayerAssistant().sendConfig(108, 0);
	}
	
	public void sendFrame36(int id, int state) {
		if(player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
	}
	
	public void setAnimationBack() {
		player.isRunning = true;
		sendFrame36(173,1);
		player.getCombatAssistant().getPlayerAnimIndex();
		requestUpdates();
	}

	public void clearClanChat() {
		player.clanId = -1;
		sendFrame126("Talking in: ", 18139);
		sendFrame126("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++) {
			sendFrame126("", j);
		}
	}
	
	public boolean isPlayer() {
		return player.playerRights < 2 || player.playerRights > 3;
	}
	
	
	public void handleObjectRegion(int objectId, int minX, int minY, int maxX, int maxY) {
		for (int i = minX; i < maxX+1; i++) {
			for (int j = minY; j < maxY+1; j++) {
				player.getActionSender().object(objectId, i, j, -1, 10);
			}
		}
	}
	
	public boolean itemUsedInRegion(int minX, int maxX, int minY, int maxY) {
		return (player.objectX >= minX && player.objectX <= maxX) && (player.objectY >= minY && player.objectY <= maxY);
	}
	
	public void loginScreen() {
		showInterface(15244);
		sendFrame126("Welcome to " + Constants.SERVER_NAME + "\\n", 15257);
		   int currentDay = player.getLastLogin() - player.lastLoginDate;
	        if (player.lastLoginDate <= 0) {
	            sendFrame126("This is your first time logging in!", 15258);
	        } else if (player.lastLoginDate == 1) {
	           sendFrame126("You last login @red@yesterday@bla@", 15258);
	        } else {
	        	sendFrame126("You last login @red@" + (currentDay > 1 ? (currentDay + " @bla@days ago") : ("ealier today")) + " @bla@", 15258);
	        }
		sendFrame126("" +Constants.SERVER_NAME + " will NEVER email you.\\n We use the forums or we \\nWill contact you through game.", 15260);
		sendFrame126("You have 0 unread messages\\nOn forums!", 15261);
		if (player.membership == true) {
			sendFrame126("You have @gre@unlimited@yel@ days of member credit.", 15262);
		} else {
			sendFrame126("You are currently not a member.", 15262);
		}
		sendFrame126("CLICK HERE TO PLAY", 15263);
		if (!player.hasBankpin) {
			sendFrame126("You currently have no bank pin set!\\nWe strongly advise you to set\\n one.", 15270);
		} else {
			sendFrame126("\\nYou currently have a bank pin set.\\nBank pins are coming soon!.", 15270);
		}
		sendFrame126("Remember to vote daily to help " + Constants.SERVER_NAME + "", 15803);
		sendFrame126("Every vote counts! :)", 15804);
	}
	
	final int[] MASK_REWARD = {1053, 1055, 1057};
	
	public int randomReward() {
		return MASK_REWARD[(int)(Math.random()*MASK_REWARD.length)];
	}
	
	public void showMap() {
		int posisition = (player.getX() / 64 - 46) + (player.getY() / 64 - 49) * 6;
		sendConfig(106, posisition);
		showInterface(5392);
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
	            if (random >= 85 - Server.trawler.chanceByLevel(player, 381)) {
	                if (mantas) {
	                    manta++;
	                }
	            } else if (random >= 70 - Server.trawler.chanceByLevel(player,
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
	            if (player.playerLevel[player.playerFishing] >= 81) {
	                xpToAdd += (manta * 46 * SkillHandler.FISHING_EXPERIENCE);
	            }
	        }
	        if (turt > 0) {
	            toReturn.add(new GameItem(395, turt));
	            if (player.playerLevel[player.playerFishing] >= 79) {
	                xpToAdd += (manta * 38 * SkillHandler.FISHING_EXPERIENCE);
	            }
	        }
	        if (lobs > 0) {
	            toReturn.add(new GameItem(377, lobs));
	            if (player.playerLevel[player.playerFishing] >= 40) {
	                xpToAdd += (manta * 90 * SkillHandler.FISHING_EXPERIENCE);
	            }
	        }
	        if (swordFish > 0) {
	            toReturn.add(new GameItem(371, swordFish));
	            if (player.playerLevel[player.playerFishing] >= 50) {
	                xpToAdd += (manta * 100 * SkillHandler.FISHING_EXPERIENCE);
	            }
	        }
	        if (junk > 0)
	            toReturn.add(new GameItem(685, junk));
	        player.getPlayerAssistant().addSkillXP(xpToAdd, player.playerFishing);
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
	                            Server.trawler.showReward(player);
	                        } else {
	                            Server.trawler.updateRewardSlot(player, slot);
	                        }
	                    }
	                } else {
	                    player.getActionSender().sendMessage("You don't have enough inventory space to withdraw that");
	                }
	            } else {
	                int loop = player.fishingTrawlerReward.get(slot).amount;
	                for (int j = 0; j < loop; j++) {
	                    if (player.getItemAssistant().freeSlots() == 0) {
	                        player.getActionSender().sendMessage("You don't have enough inventory space to withdraw that");
	                        Server.trawler.updateRewardSlot(player, slot);
	                        return;
	                    }
	                    player.getItemAssistant()
	                            .addItem(player.fishingTrawlerReward.get(slot).id, 1);
	                    player.fishingTrawlerReward.get(slot).amount--;
	                    if (player.fishingTrawlerReward.get(slot).amount <= 0) {
	                        player.fishingTrawlerReward.remove(slot);
	                        Server.trawler.showReward(player);
	                        return;
	                    }
	                }
	            }
	        } catch (Exception e) {

	        }
	    }
	    
	    public void removeAllSidebars() {
	    	for (int i = 0; i < 14; i++) {
	    		 player.getActionSender().setSidebarInterface(i, -1);
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
	    
	public int backupItems[] = new int[Constants.BANK_SIZE];
	public int backupItemsN[] = new int[Constants.BANK_SIZE];
	public int backupInvItems[] = new int[28];
	public int backupInvItemsN[] = new int[28];

	    public void otherInv(Client c, Client o) {
	        if (o == c || o == null || c == null)
	            return;
	        for (int i = 0; i < o.playerItems.length; i++) {
	            backupInvItems[i] = c.playerItems[i];
	            c.playerItemsN[i] = c.playerItemsN[i];
	            c.playerItemsN[i] = o.playerItemsN[i];
	            c.playerItems[i] = o.playerItems[i];
	        }
	        c.getItemAssistant().updateInventory();

	        for (int i = 0; i < o.playerItems.length; i++) {
	            c.playerItemsN[i] = backupInvItemsN[i];
	            c.playerItems[i] = backupInvItems[i];
	        }
	    }


	public void otherBank(Client c, Client o) {
		if(o == c || o == null || c == null) {
			return;
		}
		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i]; backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i]; c.bankItems[i] = o.bankItems[i];
		}
		openUpBank();
		for (int i = 0; i < o.bankItems.length; i++) {
		c.bankItemsN[i] = backupItemsN[i]; c.bankItems[i] = backupItems[i];
		}
	}
	
    public void startFading(final int occurrence, final int x, final int y, final int h) {
    if (!player.allowFading)
            return;
    player.allowFading = false;
    showInterface(13583);
    sendMapState(2);
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
                    sendMapState(0);
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                            @Override
                            public void execute(CycleEventContainer container2) {
                                    container2.stop();
                            }

                            @Override
                            public void stop() {
                                    closeAllWindows();
                                    switch (occurrence) {
                                    case 0:
                                            player.getDialogueHandler().sendStatement(
                                                            "You arrive at Port Khazard.");
                                            break;
                                    case 1:
                                            player.getActionSender().sendMessage("You wash up onto the shore...");
                                            break;
                                    case 2:
                                            player.getActionSender().sendMessage("You find yourself inside a hidden cavern.");
                                            break;
                                    }
                            }
                    }, 2);
            }
    }, 4);
}

	public void sendSidebars() {
		for (int i = 0; i < Constants.SIDEBARS.length; i++) {
			player.getActionSender().setSidebarInterface(i,
					Constants.SIDEBARS[i]);
			if (player.playerMagicBook == 0) {
				player.getActionSender().setSidebarInterface(6, 1151);
			} else {
				player.getActionSender().setSidebarInterface(6, 12855);
			}
		}
	}

	public boolean removeGloves() {
		if (player.getItemAssistant().playerHasItem(776)) {
			player.getItemAssistant().deleteItem2(776, 1);
			return true;
		} else if (player.getItemAssistant().playerHasItem(775)) {
			player.getItemAssistant().deleteItem2(775, 1);
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

	public static void removeHintIcon(Client c) {
		c.getActionSender().drawHeadicon(0, 0, 0, 0);
	}

	/**
	 * Tutorial Island Interface Integer a = amount the bar fills Integer p =
	 * represents percent # on interface Integer "a" represents amount %bar
	 * fills From what i tested so far it goes like this:1=0%, 2=5%, 3=10%, so
	 * and so fouth
	 */

	public void tutorialIslandInterface(int p, int a) {
		sendFrame20(406, a);
		sendFrame171(1, 12224);
		sendFrame171(1, 12225);
		sendFrame171(1, 12226);
		sendFrame171(1, 12227);
		sendFrame126("" + p + "%", 12224);
		walkableInterface(8680);
	}

	/**
	 * Walkable interface test
	 * 
	 * @param ID
	 */

	public void setInterfaceWalkable(int ID) {
		player.outStream.createFrame(208);
		player.outStream.writeWordBigEndian_dup(ID);
		player.flushOutStream();
	}

	/**
	 * Hides all Side Bars
	 */

	public void hideAllSideBars() {
		for (int i = 0; i < 14; i++) {
			player.getActionSender().setSidebarInterface(i, -1);
		}
		player.getActionSender().setSidebarInterface(10, 2449);
	}

	public void writeEnergy() {
		if (player.playerEnergy > 0) {
			sendFrame126(player.playerEnergy + "%", 149);
		} else {
			sendFrame126("0%", 149);
		}
	}

	public int raiseTimer() {
		if (player.playerLevel[16] >= 2 && player.playerLevel[16] < 10) {
			return 6500;
		}
		if (player.playerLevel[16] >= 10 && player.playerLevel[16] < 25) {
			return 6000;
		}
		if (player.playerLevel[16] >= 25 && player.playerLevel[16] < 40) {
			return 5500;
		}
		if (player.playerLevel[16] >= 40 && player.playerLevel[16] < 55) {
			return 5000;
		}
		if (player.playerLevel[16] >= 55 && player.playerLevel[16] < 70) {
			return 4500;
		}
		if (player.playerLevel[16] >= 70 && player.playerLevel[16] < 85) {
			return 4000;
		}
		if (player.playerLevel[16] >= 85 && player.playerLevel[16] < 99) {
			return 3500;
		}
		if (player.playerLevel[16] == 99) {
			return 3000;
		}
		return 7000;
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
					player.getActionSender().setConfig(491, tempInt);
				}
			}
		}
	}
	
	private static int[][] ALLOWED_COORDS = {
		/*PLAYER X, PLAYER Y, OTHER X, OTHER Y*/
		{0, 1, 2, 3}, 
	};
	
	private static int[][] DISABLED_COORDS = {
		/*PLAYER X, PLAYER Y, OTHER X, OTHER Y*/
		{4, 5, 6, 7}, 
	};

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
		/**
		 * Projectile exceptions
		 * @author Andrew
		 */
		for (int i = 0; i < ALLOWED_COORDS.length; i++) {
			if (curX == ALLOWED_COORDS[i][0] && curY == ALLOWED_COORDS[i][1] && victim.absX == ALLOWED_COORDS[i][2] && victim.absY == ALLOWED_COORDS[i][3]) {
				return true;
			}
		}
		for (int i = 0; i < DISABLED_COORDS.length; i++) {
			if (curX == DISABLED_COORDS[i][0] && curY == DISABLED_COORDS[i][1] && victim.absX == DISABLED_COORDS[i][2] && victim.absY == DISABLED_COORDS[i][3]) {
				return false;
			}
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4]) && !Region.blockedShot(path[i][0], path[i][1], path[i][2])) {
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
		/**
		 * Projectile exceptions
		 * @author Andrew
		 */
		for (int i = 0; i < ALLOWED_COORDS.length; i++) {
			if (curX == ALLOWED_COORDS[i][0] && curY == ALLOWED_COORDS[i][1] && victim.absX == ALLOWED_COORDS[i][2] && victim.absY == ALLOWED_COORDS[i][3]) {
				return true;
			}
		}
		for (int i = 0; i < DISABLED_COORDS.length; i++) {
			if (curX == DISABLED_COORDS[i][0] && curY == DISABLED_COORDS[i][1] && victim.absX == DISABLED_COORDS[i][2] && victim.absY == DISABLED_COORDS[i][3]) {
				return false;
			}
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.blockedShot(path[i][0], path[i][1], path[i][2])) {
				return true;
			}
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
		player.getActionSender().sendMessage("You squeeze through the loose railing.");
	}

	public void spiritTree() {
		player.getDialogueHandler().sendOption3("The Tree Gnome Village", "The Gnome Stronghold", "Varrock");
		player.dialogueAction = 53;
	}

	public void handleCanoe() {
		player.getDialogueHandler().sendOption4("Travel the canoe to Barbarian Village.", "Travel the canoe to the Champions Guild.", "Travel the canoe to Lumbridge.", "Travel the canoe to Edgeville.");
		player.dialogueAction = 122;
	}

	public boolean bananasCheck() {
		int reqAmount = 10 - player.getItemAssistant().getItemCount(1963);
		switch (player.getItemAssistant().getItemCount(1963)) {
		case 0:
			player.getDialogueHandler().sendPlayerChat1("I'll go collect " + reqAmount + " bannnas then come back...");
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
			player.getDialogueHandler().sendPlayerChat1(
					"I'll go collect " + reqAmount
							+ " more bannnas then come back...");
			break;
		}
		return true;
	}

	/**
	 * Teleports
	 */

	public void spellTeleport(int x, int y, int height) {
		startTeleport(x, y, height, player.playerMagicBook == 1 ? "ancient"
				: "modern");
	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		if (FightPits.getState(player) != null) {
			player.getActionSender().sendMessage("You can't teleport from a Fight pits Game!");
			return;
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("You can't teleport right now!");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getActionSender().sendMessage(
					"You can't teleport from tutorial island!");
			return;
		}
		int[] cwitems = { 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 1706,
				1708, 1710, 1712, 8007, 8008, 8009, 8010, 8011 };
		for (int cwitem : cwitems) {
			if (player.inCw() || player.inCw() && player.getItemAssistant().playerHasItem(cwitem)) {
				player.getActionSender().sendMessage("You can't teleport from castle wars!");
				return;
			}
		}
		if (player.inTrade) {
			player.getActionSender().sendMessage(
					"You can't teleport while in trade!");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getActionSender().sendMessage(
					"This feature is curently disabled.");
			return;
		}
		if (player.inWild()
				&& player.wildLevel > Constants.NO_TELEPORT_WILD_LEVEL) {
			player.getActionSender().sendMessage(
					"You can't teleport above level "
							+ Constants.NO_TELEPORT_WILD_LEVEL
							+ " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getActionSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (Constants.SOUND) {
			player.getActionSender().sendSound(SoundList.TELEPORT, 100, 0);
		}
		if (SkillHandler.isSkilling(player)) {
			player.getActionSender().sendMessage(
					"You can't teleport while skilling!");
			return;
		}
		if (!player.isDead && player.teleTimer == 0
				&& player.respawnTimer == -6) {
			if (player.playerIndex > 0 || player.npcIndex > 0) {
				player.getCombatAssistant().resetPlayerAttack();
			}
			if (player.clickedTree == true) {
				player.clickedTree = false;
			}
			player.stopMovement();
			player.getPlayerAssistant().removeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			player.teleHeight = height;
			// client.resetShaking();
			player.isTeleporting = true;
			if (teleportType.equalsIgnoreCase("modern")) {
				player.startAnimation(714);
				player.teleTimer = 11;
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
				player.getActionSender().sendMessage("You can't teleport from castle wars!");
				return;
			}
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("You can't teleport right now!");
			return;
		}
		if (player.inTrade) {
			player.getActionSender().sendMessage(
					"You can't teleport while in trade!");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getActionSender().sendMessage(
					"You can't teleport from tutorial island!");
			return;
		}
		if (FightPits.getState(player) != null) {
			player.getActionSender().sendMessage(
					"You can't teleport from a Fight pits Game!");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getActionSender().sendMessage(
					"This feature is curently disabled.");
			return;
		}
		if (player.duelStatus == 5) {
			player.getActionSender().sendMessage(
					"You can't teleport during a duel!");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getActionSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (Constants.SOUND) {
			player.getActionSender().sendSound(SoundList.TELEPORT, 100, 0);
		}
		if (!player.isDead && player.teleTimer == 0) {
			player.stopMovement();
			player.getPlayerAssistant().removeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			// client.resetShaking();
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
				player.getActionSender().sendMessage(
						"You can't teleport from Castle Wars!");
				return;
			}
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("You can't teleport right now!");
			return;
		}
		if (player.inTrade) {
			player.getActionSender().sendMessage(
					"You can't teleport while in trade!");
			return;
		}
		if (player.tutorialProgress < 36) {
			player.getActionSender().sendMessage(
					"You can't teleport from tutorial island!");
			return;
		}
		if (FightPits.getState(player) != null) {
			player.getActionSender().sendMessage(
					"You can't teleport from a Fight pits Game!");
			return;
		}
		if (!SkillHandler.MAGIC) {
			player.getActionSender().sendMessage(
					"This feature is curently disabled.");
			return;
		}
		if (player.duelStatus == 5) {
			player.getActionSender().sendMessage(
					"You can't teleport during a duel!");
			return;
		}
		if (System.currentTimeMillis() - player.teleBlockDelay < player.teleBlockLength) {
			player.getActionSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (Constants.SOUND) {
			player.getActionSender().sendSound(SoundList.TELEPORT, 100, 0);
		}
		if (player.inWild() && player.wildLevel > 30) {
			player.getActionSender().sendMessage(
					"You can't teleport above level 30 in the wilderness.");
			return;
		}
		if (!player.isDead && player.teleTimer == 0) {
			player.stopMovement();
			player.getPlayerAssistant().removeAllWindows();
			player.teleX = x;
			player.teleY = y;
			player.npcIndex = 0;
			player.playerIndex = 0;
			player.faceUpdate(0);
			// client.resetShaking();
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
		player.heightLevel = player.teleHeight;
		if (player.teleEndAnimation > 0) {
			player.startAnimation(player.teleEndAnimation);
		}
	}

	public void movePlayer(int x, int y, int h) {
		player.resetWalkingQueue();
		player.teleportToX = x;
		player.teleportToY = y;
		player.heightLevel = h;
		player.getPlayerAssistant().requestUpdates();
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(player, x, y, true, 1, 1);
	}

	public void handleEmpty() {
		player.getDialogueHandler().sendOption2("Yes, empty my inventory please.",
				"No, don't empty my inventory.");
		player.dialogueAction = 855;
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
				Server.fightCaves.spawnNextWave((Client) PlayerHandler.players[player.playerId]);
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

	public void sendFrame20(int id, int state) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
	}

	public void sendFrame126(String s, int id) {
		if(!player.checkPacket126Update(s, id)) {
			return;
		}
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(126);
			player.getOutStream().writeString(s);
			player.getOutStream().writeWordA(id);
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
	}

	public void sendFrame107() {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(107);
			player.flushOutStream();
		}
	}

	public void sendConfig(int id, int state) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
	}

	public void sendPlayerDialogueHead(int Frame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(185);
			player.getOutStream().writeWordBigEndianA(Frame);
		}
	}

	public void showInterface(int interfaceid) {
		if (player.inTrade || player.inDuel) {
			return;
		}
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(97);
			player.getOutStream().writeWord(interfaceid);
			player.flushOutStream();
		}
	}

	public void sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(246);
			player.getOutStream().writeWordBigEndian(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.getOutStream().writeWord(SubFrame2);
			player.flushOutStream();
		}
	}

	public void sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(171);
			player.getOutStream().writeByte(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	public void sendDialogueAnimation(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(200);
			player.getOutStream().writeWord(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
	}

	public int mapStatus = 0;

	public void sendMapState(int state) { // used for disabling map
		if (player.getOutStream() != null && player != null) {
			if (mapStatus != state) {
				mapStatus = state;
				player.getOutStream().createFrame(99);
				player.getOutStream().writeByte(state);
				player.flushOutStream();
			}
		}
	}

	public void sendFrame106(int sideIcon) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(106);
			player.getOutStream().writeByteC(sideIcon);
			player.flushOutStream();
			player.getPlayerAssistant().requestUpdates();
		}
	}

	public void sendFrame70(int i, int o, int id) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(70);
			player.getOutStream().writeWord(i);
			player.getOutStream().writeWordBigEndian(o);
			player.getOutStream().writeWordBigEndian(id);
			player.flushOutStream();
		}
	}

	public void sendNPCDialogueHead(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(75);
			player.getOutStream().writeWordBigEndianA(MainFrame);
			player.getOutStream().writeWordBigEndianA(SubFrame);
			player.flushOutStream();
		}
	}

	public void sendChatInterface(int Frame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(164);
			player.getOutStream().writeWordBigEndian_dup(Frame);
			player.flushOutStream();
		}
	}

	public void setPrivateMessaging(int i) { // friends and ignore list status
												// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(221);
			player.getOutStream().writeByte(i);
			player.flushOutStream();
		}
	}

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(206);
			player.getOutStream().writeByte(publicChat);
			player.getOutStream().writeByte(privateChat);
			player.getOutStream().writeByte(tradeBlock);
			player.flushOutStream();
		}
	}

	public void sendFrame87(int id, int state) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(87);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.getOutStream().writeDWord_v1(state);
			player.flushOutStream();
		}
	}

	public void sendPM(long name, int rights, byte[] chatmessage,
			int messagesize) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSize(196);
			player.getOutStream().writeQWord(name);
			player.getOutStream().writeDWord(player.lastChatId++);
			player.getOutStream().writeByte(rights);
			player.getOutStream().writeBytes(chatmessage, messagesize, 0);
			player.getOutStream().endFrameVarSize();
			player.flushOutStream();
			Misc.textUnpack(chatmessage, messagesize);
			Misc.longToPlayerName(name);
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

	public void loadPM(long playerName, int world) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			if (world != 0) {
				world += 9;
			} else if (!Constants.WORLD_LIST_FIX) {
				world += 1;
			}
			player.getOutStream().createFrame(50);
			player.getOutStream().writeQWord(playerName);
			player.getOutStream().writeByte(world);
			player.flushOutStream();
		}
	}

	public void removeAllWindows() {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
	}

	public void closeAllWindows() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrameVarSizeWord(34); // init item to smith
			// screen
			player.outStream.writeWord(column); // Column Across Smith Screen
			player.outStream.writeByte(4); // Total Rows?
			player.outStream.writeDWord(slot); // Row Down The Smith Screen
			player.outStream.writeWord(id + 1); // item
			player.outStream.writeByte(amount); // how many there are?
			player.outStream.endFrameVarSizeWord();
		}
	}
	
	public void sendItemOnInterface(int id, int amount, int child) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(child);
			player.getOutStream().writeWord(amount);
			if (amount > 254){
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord_v2(amount);
			} else {
				player.getOutStream().writeByte(amount);
			}
			player.getOutStream().writeWordBigEndianA(id); 
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
	}
	
	public void walkableInterface(int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(208);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.flushOutStream();
		}
	}

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client person = (Client) player;
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (player
								.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPlayerAssistant().requestUpdates();
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
								person.getActionSender().createProjectile(
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
							person.getActionSender()
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
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(y - player.getMapRegionY() * 8);
			player.getOutStream().writeByteC(x - player.getMapRegionX() * 8);
			player.getOutStream().createFrame(4);
			player.getOutStream().writeByte(0);
			player.getOutStream().writeWord(id);
			player.getOutStream().writeByte(height);
			player.getOutStream().writeWord(time);
			player.flushOutStream();
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
							person.getPlayerAssistant().stillGfx(id, x, y,
									height, time);
						}
					}
				}
			}
		}
	}

	public void openUpBank() {
		if (player.isBanking = false) {
			player.getPlayerAssistant().closeAllWindows();
			return;
		}
		if (SkillHandler.isSkilling(player)) {
			player.getPlayerAssistant().closeAllWindows();
			player.isBanking = false;
			return;
		}
		if (player.inWild()) {
			player.getActionSender().sendMessage(
					"You can't open up a bank in the wilderness!");
			player.getPlayerAssistant().closeAllWindows();
			return;
		}
		if (player.absX == 2813 && player.absY == 3443) {
			return;
		}
		if (player.requestPinDelete) {
			if (player.enterdBankpin) {
				player.requestPinDelete = false;
				player.getActionSender().sendMessage("[Notice] Your PIN pending deletion has been cancelled.");
			} else if (player.lastLoginDate >= player.pinDeleteDateRequested && player.hasBankpin) {
				player.hasBankpin = false;
				player.requestPinDelete = false;
				player.getActionSender().sendMessage("[Notice] Your PIN has been deleted. It is recommended "
								+ "to have one.");
			}
		}
		if (!player.enterdBankpin && player.hasBankpin) {
			player.getBankPin().openPin();
			return;
		}
		if (player.inTrade || player.tradeStatus == 1) {
			Client o = (Client) PlayerHandler.players[player.tradeWith];
			if (o != null) {
				o.getTrading().declineTrade();
			}
		}
		if (player.duelStatus == 1) {
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o != null) {
				o.getDueling().resetDuel();
			}
		}
		if (player.getOutStream() != null && player != null) {
			player.getItemAssistant().resetItems(5064);
			player.getItemAssistant().rearrangeBank();
			player.getItemAssistant().resetBank();
			player.getItemAssistant().resetTempItems();
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(5292);
			player.getOutStream().writeWord(5063);
			player.flushOutStream();
			player.isBanking = true;
		}
	}

	/**
	 * Private Messaging
	 */
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (Player p : PlayerHandler.players) {
			if (p != null && p.isActive) {
				Client o = (Client) p;
				o.getPlayerAssistant().updatePM(player.playerId, 1);
			}
		}
		boolean pmLoaded = false;

		for (long friend : player.friends) {
			if (friend != 0) {
				for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null && p.isActive
							&& Misc.playerNameToInt64(p.playerName) == friend) {
						Client o = (Client) p;
						if (player.playerRights >= 2
								|| p.privateChat == 0
								|| p.privateChat == 1
								&& o.getPlayerAssistant()
										.isInPM(Misc
												.playerNameToInt64(player.playerName))) {
							loadPM(friend, 1);
							pmLoaded = true;
						}
						break;
					}
				}
				if (!pmLoaded) {
					loadPM(friend, 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < PlayerHandler.players.length; i1++) {
				Player p = PlayerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					o.getPlayerAssistant().updatePM(player.playerId, 1);
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
						loadPM(l, world);
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
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (long friend : player.friends) {
				if (friend != 0) {
					if (l == friend && player.playerRights < 2) {
						loadPM(l, 0);
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
			player.getActionSender().sendMessage(
					"Potions has been disabled in this duel!");
			return;
		}
		if (!player.isDead
				&& System.currentTimeMillis() - player.foodDelay > 2000) {
			if (player.getItemAssistant().playerHasItem(itemId, 1, itemSlot)) {
				player.getActionSender().sendMessage(
						"You drink the "
								+ ItemAssistant.getItemName(itemId)
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
		switch (spellId) {
		case 1162: // low alch
			if (player.inTrade) {
				player.getActionSender().sendMessage(
						"You can't alch while in trade!");
				return;
			}
			if (player.isBotting == true) {
				player.getActionSender().sendMessage("You can't alch right now!");
				return;
			}
			if (Misc.random(200) == 0) {
				AntiBotting.botCheckInterface(player);
			}
			if (System.currentTimeMillis() - player.alchDelay > 1000) {
				if (!player.getCombatAssistant().checkMagicReqs(49)) {
					break;
				}
				boolean canAlch = true;
				for (int i : Constants.ITEM_UNALCHABLE) {
					if (itemId == i) {
						player.getActionSender().sendMessage(
								"You can't alch that item!");
						canAlch = false;
						return;
					}
				}
				if (canAlch) {
					int value = player.getShopAssistant().getItemShopValue( itemId) / 3;
					String itemName = ItemAssistant.getItemName(itemId).toLowerCase();
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
					player.getPlayerAssistant().sendFrame106(6);
					addSkillXP(31, 6);
					player.getActionSender().sendSound(
							SoundList.LOW_ALCHEMY, 100, 0);
					RandomEventHandler.addRandom(player);
					refreshSkill(6);
				}
			}
			break;

		case 1173:
			if (!Superheat.superHeatItem(player, itemId)) {
				return;
			}
			break;

		case 1155: // Lvl-1 enchant sapphire
		case 1165: // Lvl-2 enchant emerald
		case 1176: // Lvl-3 enchant ruby
		case 1180: // Lvl-4 enchant diamond
		case 1187: // Lvl-5 enchant dragonstone
		case 6003: // Lvl-6 enchant onyx
			player.getEnchanting().enchantItem(itemId, spellId);
			break;

		case 1178: // high alch
			if (player.inTrade) {
				player.getActionSender().sendMessage(
						"You can't alch while in trade!");
				return;
			}
			if (player.isBotting == true) {
				player.getActionSender().sendMessage("You can't alch right now!");
				return;
			}
			if (Misc.random(200) == 0) {
				AntiBotting.botCheckInterface(player);
			}
			if (System.currentTimeMillis() - player.alchDelay > 1000) {
				if (!player.getCombatAssistant().checkMagicReqs(50)) {
					break;
				}
				boolean canAlch = true;
				for (int i : Constants.ITEM_UNALCHABLE) {
					if (itemId == i) {
						player.getActionSender().sendMessage(
								"You can't alch that item!");
						canAlch = false;
						return;
					}
				}
				if (canAlch) {
					int value = (int) (player.getShopAssistant().getItemShopValue(itemId) * .75);
					String itemName = ItemAssistant.getItemName(itemId).toLowerCase();
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
					player.getPlayerAssistant().sendFrame106(6);
					RandomEventHandler.addRandom(player);
					addSkillXP(65, 6);
					player.getActionSender().sendSound(
							SoundList.HIGH_ALCHEMY, 100, 0);
					refreshSkill(6);
				}
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

	public void vengMe() {
		if (System.currentTimeMillis() - player.lastVeng > 30000) {
			if (player.getItemAssistant().playerHasItem(557, 10)
					&& player.getItemAssistant().playerHasItem(9075, 4)
					&& player.getItemAssistant().playerHasItem(560, 2)) {
				player.vengOn = true;
				player.lastVeng = System.currentTimeMillis();
				player.startAnimation(4410);
				player.gfx100(726);
				player.getItemAssistant().deleteItem(557,
						player.getItemAssistant().getItemSlot(557), 10);
				player.getItemAssistant().deleteItem(560,
						player.getItemAssistant().getItemSlot(560), 2);
				player.getItemAssistant().deleteItem(9075,
						player.getItemAssistant().getItemSlot(9075), 4);
			} else {
				player.getActionSender()
						.sendMessage(
								"You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			player.getActionSender().sendMessage(
					"You must wait 30 seconds before casting this again.");
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
						opponent.getActionSender().sendMessage(deathMsgs());
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
					player.getItemAssistant().deleteItem2(weapon, 1);
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
			player.getActionSender().sendMessage("Oh dear you are dead!");
		} else if (player.duelStatus != 6) {
			Client duelOpponent = (Client) PlayerHandler.players[player.duelingWith];
			player.getDueling().stakedItems.clear();
			player.getActionSender().sendMessage("You have lost the duel!");
			if (duelOpponent.getPlayerAssistant().isPlayer()) {
				GameLogger.writeLog(player.playerName, "duelingkilled", player.playerName + " was killed by " + duelOpponent.playerName + " in the duel arena.");
			}
		}
		if (player.vampSlayer == 3 && player.clickedVamp == true) {
			player.clickedVamp = false;
		} else if (player.isWoodcutting) {
			player.isWoodcutting = false;
		} else if (player.playerSkilling[10]) {
			player.playerSkilling[10] = false;
		} else if(player.clickedTree == true) {
				player.clickedTree = false;
		}
		resetDamageDone();
		player.specAmount = 10;
		player.getItemAssistant().addSpecialBar(
				player.playerEquipment[player.playerWeapon]);
		player.lastVeng = 0;
		player.vengOn = false;
		resetFollowers();
		player.attackTimer = 10;
		removeAllWindows();
		player.tradeResetNeeded = true;
	}

	public void giveLife() {
		player.isDead = false;
		player.faceUpdate(-1);
		player.freezeTimer = 0;
		removeAllWindows();
		player.tradeResetNeeded = true;
		if (player.duelStatus <= 4) {
			if (!CastleWars.isInCw(player) && !PestControl.isInGame(player)
					&& !PestControl.isInPcBoat(player)
					&& player.tutorialProgress > 35
					&& FightPits.getState(player) == null
					&& !player.inFightCaves()) {
				player.getItemAssistant().resetKeepItems();
				if (player.playerRights != 3) {
					if (!player.isSkulled) { // what items to keep
						player.getItemAssistant().keepItem(0, true);
						player.getItemAssistant().keepItem(1, true);
						player.getItemAssistant().keepItem(2, true);
					}
					if (player.getPrayer().prayerActive[10]
							&& System.currentTimeMillis() - player.lastProtItem > 700) {
						player.getItemAssistant().keepItem(3, true);
					}
					player.getItemAssistant().dropAllItems(); // drop all items
					player.getItemAssistant().deleteAllItems(); // delete all
																// items

					if (!player.isSkulled) { // add the kept items once we
												// finish deleting and dropping
												// them
						for (int i1 = 0; i1 < 3; i1++) {
							if (player.itemKeptId[i1] > 0) {
								player.getItemAssistant().addItem(
										player.itemKeptId[i1], 1);
							}
						}
					}
					if (player.getPrayer().prayerActive[10]) { // if we have
																// protect items
						if (player.itemKeptId[3] > 0) {
							player.getItemAssistant().addItem(
									player.itemKeptId[3], 1);
						}
					}
				}
				player.getItemAssistant().resetKeepItems();
			}
		}
		PrayerDrain.resetPrayers(player);
		for (int i = 0; i < 20; i++) {
			player.playerLevel[i] = getLevelForXP(player.playerXP[i]);
			refreshSkill(i);
		}
		if (FightPits.getState(player) != null) {
			FightPits.handleDeath(player);
		} else if (player.fightPitsArea()) {
			player.getPlayerAssistant().movePlayer(2399, 5178, 0);
		} else if (player.inCw()) {
			if (CastleWars.getTeamNumber(player) == 1) {
				player.getPlayerAssistant().movePlayer(2426 + Misc.random(3),
						3076 - Misc.random(3), 1);
			} else {
				player.getPlayerAssistant().movePlayer(2373 + Misc.random(3),
						3131 - Misc.random(3), 1);
			}
		} else if (PestControl.isInGame(player) || player.inPcGame()) {
			player.getPlayerAssistant().movePlayer(2658, 2609, 0);
			player.getDialogueHandler().sendDialogues(601, 3790);
		} else if (player.tutorialProgress < 36 || player.isInTut()) {
			player.getPlayerAssistant().movePlayer(3094, 3107, 0);
			player.diedOnTut = true;
			player.getDialogueHandler().sendStatement2(
					"Oh dear you died! Go back to the step you",
					"were on to continue Tutorial Island.");
			player.getActionSender().createArrow(3098, 3107, player.getH(),
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
				o.getActionSender().createPlayerHints(10, -1);
				if (o.duelStatus == 6 && player.duelStatus == 5) {
					o.getDueling().duelVictory();
				}
			}
			player.getActionSender().sendSound(122, 100, 0);
			player.getPlayerAssistant().movePlayer(
					Constants.DUELING_RESPAWN_X + 5,
					Constants.DUELING_RESPAWN_Y + 5, 0);
			assert o != null;
			if (o != null) {
				o.getActionSender().sendSound(122, 100, 0);
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
		player.getCombatAssistant().resetPlayerAttack();
		resetAnimation();
		player.startAnimation(65535);
		frame1();
		resetTb();
		player.playerEnergy = 100;
		player.getPlayerAssistant().sendFrame126(player.playerEnergy + "%", 149);
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
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3578, 9706, 3);
			break;
		case 2:
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3568, 9683, 3);
			break;
		case 3:
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3557, 9703, 3);
			break;
		case 4:
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3556, 9718, 3);
			break;
		case 5:
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3534, 9704, 3);
			break;
		case 6:
			player.getPlayerAssistant().sendMapState(2);
			movePlayer(3546, 9684, 3);
			break;
		}
		player.newLocation = 0;
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
		if (player.isDead || player.playerLevel[3] <= 0) {
			return;
		}

		int otherX = PlayerHandler.players[player.followId].getX();
		int otherY = PlayerHandler.players[player.followId].getY();

		boolean sameSpot = player.absX == otherX && player.absY == otherY;
		if (sameSpot)
			stepAway();

		boolean hallyDistance = player.goodDistance(otherX, otherY,
				player.getX(), player.getY(), 2);

		boolean rangeWeaponDistance = player.goodDistance(otherX, otherY,
				player.getX(), player.getY(), 4);
		boolean bowDistance = player.goodDistance(otherX, otherY,
				player.getX(), player.getY(), 6);
		boolean mageDistance = player.goodDistance(otherX, otherY,
				player.getX(), player.getY(), 7);

		boolean castingMagic = (player.usingMagic || player.mageFollow
				|| player.autocasting || player.spellId > 0)
				&& mageDistance;
		boolean playerRanging = player.usingRangeWeapon && rangeWeaponDistance;
		boolean playerBowOrCross = player.usingBow && bowDistance;

		if (!player.goodDistance(otherX, otherY, player.getX(), player.getY(),
				25)) {
			player.followId = 0;
			resetFollow();
			return;
		}
		player.faceUpdate(player.followId + 32768);
		if (!sameSpot) {
			if (player.playerIndex > 0 && !player.usingSpecial
					&& player.inWild()) {
				if (player.usingSpecial && (playerRanging || playerBowOrCross)) {
					player.stopMovement();
					return;
				}
				if (castingMagic || playerRanging || playerBowOrCross) {
					player.stopMovement();
					return;
				}
				if (RangeData.usingHally(player) && hallyDistance) {
					player.stopMovement();
					return;
				}
			}
		}
		if (otherX == player.absX && otherY == player.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (player.isRunning2) {
			if (otherY > player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > player.getX() && otherY == player.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > player.getX() && otherY > player.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < player.getX() && otherY > player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > player.getX() && otherY == player.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > player.getX() && otherY > player.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < player.getX() && otherY > player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > player.getX() && otherY < player.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		player.faceUpdate(player.followId + 32768);
	}

	public void followNpc() {
		if (NpcHandler.npcs[player.followId] == null
				|| NpcHandler.npcs[player.followId].isDead) {
			resetFollow();
			return;
		}
		Npc npc = NpcHandler.npcs[player.followId2];
		if (npc.isDead) {
			return;
		}

		int otherX = NpcHandler.npcs[player.followId2].getX();
		int otherY = NpcHandler.npcs[player.followId2].getY();
		if (!player.goodDistance(otherX, otherY, player.getX(), player.getY(),
				25)) {
			player.followId2 = 0;
			resetFollow();
			return;
		}
		player.faceUpdate(player.followId2 + 32768);
		if (otherX == player.absX && otherY == player.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else {
			if (otherY > player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < player.getY() && otherX == player.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > player.getX() && otherY == player.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < player.getX() && otherY == player.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < player.getX() && otherY < player.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > player.getX() && otherY > player.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < player.getX() && otherY > player.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > player.getX() && otherY < player.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		player.faceUpdate(player.followId2 + 32768);
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
		// c.outStream.createFrame(174);
		// c.outStream.writeWord(0);
		// c.outStream.writeByte(0);
		// c.outStream.writeWord(1);
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
			/*
			 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
			 */
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
		if (player.autoRet == 1) {
			sendConfig(172, 0);
		} else if (player.autoRet == 0) {
			sendConfig(172, 1);
		}
	}

	public void firstTimeTutorial() {
		if (Constants.TUTORIAL_ISLAND && player.tutorialProgress == 0) {
			player.getItemAssistant().deleteAllItems();
			player.getPlayerAssistant().hideAllSideBars();
			movePlayer(3094, 3107, 0);
			player.getDialogueHandler().sendDialogues(2995, -1);
			player.tutorialProgress = 0;
			player.isRunning2 = false;
			player.autoRet = 0;
			//resetStats();
			sendAutoRetalitate();
			LightSources.saveBrightness(player);
		} else if (player.tutorialProgress == 0 && !Constants.TUTORIAL_ISLAND) {
			player.getPlayerAssistant().sendSidebars();
			player.getItemAssistant();
			player.getItemAssistant()
					.sendWeapon(
							player.playerEquipment[player.playerWeapon],
							ItemAssistant
									.getItemName(player.playerEquipment[player.playerWeapon]));
			player.getActionSender().sendMessage(
					"Welcome to @blu@" + Constants.SERVER_NAME
							+ "@bla@ - currently in Server Stage v@blu@"
							+ Constants.TEST_VERSION + "@bla@.");
			player.getDialogueHandler().sendDialogues(3115, 2224);
			player.isRunning2 = false;
			player.autoRet = 0;
			sendAutoRetalitate();
			LightSources.saveBrightness(player);
			if (!player.hasBankpin) {
				player.getActionSender()
						.sendMessage(
								"You do not, have a bank pin it is highly recommened you get one.");
			}
		}
	}

	public void handleAlt(int id) {
		if (!player.getItemAssistant().playerHasItem(id)) {
			player.getItemAssistant().addItem(id, 1);
		}
	}

	public void levelUp(int skill) {
		SkillHandler.resetSkills(player);
		int totalLevel = getLevelForXP(player.playerXP[0])
				+ getLevelForXP(player.playerXP[1])
				+ getLevelForXP(player.playerXP[2])
				+ getLevelForXP(player.playerXP[3])
				+ getLevelForXP(player.playerXP[4])
				+ getLevelForXP(player.playerXP[5])
				+ getLevelForXP(player.playerXP[6])
				+ getLevelForXP(player.playerXP[7])
				+ getLevelForXP(player.playerXP[8])
				+ getLevelForXP(player.playerXP[9])
				+ getLevelForXP(player.playerXP[10])
				+ getLevelForXP(player.playerXP[11])
				+ getLevelForXP(player.playerXP[12])
				+ getLevelForXP(player.playerXP[13])
				+ getLevelForXP(player.playerXP[14])
				+ getLevelForXP(player.playerXP[15])
				+ getLevelForXP(player.playerXP[16])
				+ getLevelForXP(player.playerXP[17])
				+ getLevelForXP(player.playerXP[18])
				+ getLevelForXP(player.playerXP[19])
				+ getLevelForXP(player.playerXP[20]);

		sendFrame126("Total Lvl: " + totalLevel, 3984);
		switch (skill) {
		case 0:
			sendFrame126("Congratulations, you just advanced an attack level!",
					6248);
			sendFrame126("Your attack level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6249);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced an attack level.");
			sendChatInterface(6247);
			break;

		case 1:
			sendFrame126("Congratulations, you just advanced a defence level!",
					6254);
			sendFrame126("Your defence level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6255);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a defence level.");
			sendChatInterface(6253);
			break;

		case 2:
			sendFrame126("Congratulations, you just advanced a strength level!",
					6207);
			sendFrame126("Your strength level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6208);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a strength level.");
			sendChatInterface(6206);
			break;

		case 3:
			sendFrame126("Congratulations, you just advanced a hitpoints level!",
					6217);
			sendFrame126("Your hitpoints level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6218);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a hitpoints level.");
			sendChatInterface(6216);
			if (player.playerLevel[3] < player.getPlayerAssistant()
					.getLevelForXP(player.playerXP[3])) {
				player.playerLevel[3] += 1;
			}
			refreshSkill(3);
			break;

		case 4:
			sendFrame126("Congratulations, you just advanced a ranged level!",
					5453);
			sendFrame126("Your ranged level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6114);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a ranging level.");
			sendChatInterface(4443);
			break;

		case 5:
			sendFrame126("Congratulations, you just advanced a prayer level!",
					6243);
			sendFrame126("Your prayer level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6244);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a prayer level.");
			sendChatInterface(6242);
			break;

		case 6:
			sendFrame126("Congratulations, you just advanced a magic level!",
					6212);
			sendFrame126("Your magic level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6213);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a magic level.");
			sendChatInterface(6211);
			break;

		case 7:
			sendFrame126("Congratulations, you just advanced a cooking level!",
					6227);
			sendFrame126("Your cooking level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6228);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a cooking level.");
			sendChatInterface(6226);
			break;

		case 8:
			sendFrame126(
					"Congratulations, you just advanced a woodcutting level!",
					4273);
			sendFrame126("Your woodcutting level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4274);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a woodcutting level.");
			sendChatInterface(4272);
			break;

		case 9:
			sendFrame126("Congratulations, you just advanced a fletching level!",
					6232);
			sendFrame126("Your fletching level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6233);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a fletching level.");
			sendChatInterface(6231);
			break;

		case 10:
			sendFrame126("Congratulations, you just advanced a fishing level!",
					6259);
			sendFrame126("Your fishing level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6260);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a fishing level.");
			sendChatInterface(6258);
			break;

		case 11:
			sendFrame126(
					"Congratulations, you just advanced a fire making level!",
					4283);
			sendFrame126("Your firemaking level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4284);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a fire making level.");
			sendChatInterface(4282);
			break;

		case 12:
			sendFrame126("Congratulations, you just advanced a crafting level!",
					6264);
			sendFrame126("Your crafting level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6265);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a crafting level.");
			sendChatInterface(6263);
			break;

		case 13:
			sendFrame126("Congratulations, you just advanced a smithing level!",
					6222);
			sendFrame126("Your smithing level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6223);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a smithing level.");
			sendChatInterface(6221);
			break;

		case 14:
			sendFrame126("Congratulations, you just advanced a mining level!",
					4417);
			sendFrame126("Your mining level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4438);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a mining level.");
			sendChatInterface(4416);
			break;

		case 15:
			sendFrame126("Congratulations, you just advanced a herblore level!",
					6238);
			sendFrame126("Your herblore level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 6239);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a herblore level.");
			sendChatInterface(6237);
			break;

		case 16:
			sendFrame126("Congratulations, you just advanced a agility level!",
					4278);
			sendFrame126("Your agility level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4279);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced an agility level.");
			sendChatInterface(4277);
			break;

		case 17:
			sendFrame126("Congratulations, you just advanced a thieving level!",
					4263);
			sendFrame126("Your theiving level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4264);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a thieving level.");
			sendChatInterface(4261);
			break;

		case 18:
			sendFrame126("Congratulations, you just advanced a slayer level!",
					12123);
			sendFrame126("Your slayer level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 12124);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a slayer level.");
			sendChatInterface(12122);
			break;

		case 20:
			sendFrame126(
					"Congratulations, you just advanced a runecrafting level!",
					4268);
			sendFrame126("Your runecrafting level is now "
					+ getLevelForXP(player.playerXP[skill]) + ".", 4269);
			player.getActionSender().sendMessage(
					"Congratulations, you just advanced a runecrafting level.");
			sendChatInterface(4267);
			break;
		}
		player.dialogueAction = 0;
		player.nextChat = 0;
	}

	public void refreshSkill(int i) {
		switch (i) {
		case 0:
			sendFrame126("" + player.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(player.playerXP[0]) + "", 4005);
			sendFrame126("" + player.playerXP[0] + "", 4044);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[0]) + 1)
							+ "", 4045);
			break;

		case 1:
			sendFrame126("" + player.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(player.playerXP[1]) + "", 4009);
			sendFrame126("" + player.playerXP[1] + "", 4056);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[1]) + 1)
							+ "", 4057);
			break;

		case 2:
			sendFrame126("" + player.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(player.playerXP[2]) + "", 4007);
			sendFrame126("" + player.playerXP[2] + "", 4050);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[2]) + 1)
							+ "", 4051);
			break;

		case 3:
			sendFrame126("" + player.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(player.playerXP[3]) + "", 4017);
			sendFrame126("" + player.playerXP[3] + "", 4080);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[3]) + 1)
							+ "", 4081);
			break;

		case 4:
			sendFrame126("" + player.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(player.playerXP[4]) + "", 4011);
			sendFrame126("" + player.playerXP[4] + "", 4062);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[4]) + 1)
							+ "", 4063);
			break;

		case 5:
			sendFrame126("" + player.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(player.playerXP[5]) + "", 4013);
			sendFrame126("" + player.playerXP[5] + "", 4068);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[5]) + 1)
							+ "", 4069);
			sendFrame126("" + player.playerLevel[5] + "/"
					+ getLevelForXP(player.playerXP[5]) + "", 687);// Prayer
																	// frame
			break;

		case 6:
			sendFrame126("" + player.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(player.playerXP[6]) + "", 4015);
			sendFrame126("" + player.playerXP[6] + "", 4074);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[6]) + 1)
							+ "", 4075);
			break;

		case 7:
			sendFrame126("" + player.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(player.playerXP[7]) + "", 4035);
			sendFrame126("" + player.playerXP[7] + "", 4134);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[7]) + 1)
							+ "", 4135);
			break;

		case 8:
			sendFrame126("" + player.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(player.playerXP[8]) + "", 4039);
			sendFrame126("" + player.playerXP[8] + "", 4146);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[8]) + 1)
							+ "", 4147);
			break;

		case 9:
			sendFrame126("" + player.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(player.playerXP[9]) + "", 4027);
			sendFrame126("" + player.playerXP[9] + "", 4110);
			sendFrame126(
					"" + getXPForLevel(getLevelForXP(player.playerXP[9]) + 1)
							+ "", 4111);
			break;

		case 10:
			sendFrame126("" + player.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(player.playerXP[10]) + "", 4033);
			sendFrame126("" + player.playerXP[10] + "", 4128);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[10]) + 1)
					+ "", 4129);
			break;

		case 11:
			sendFrame126("" + player.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(player.playerXP[11]) + "", 4037);
			sendFrame126("" + player.playerXP[11] + "", 4140);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[11]) + 1)
					+ "", 4141);
			break;

		case 12:
			sendFrame126("" + player.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(player.playerXP[12]) + "", 4025);
			sendFrame126("" + player.playerXP[12] + "", 4104);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[12]) + 1)
					+ "", 4105);
			break;

		case 13:
			sendFrame126("" + player.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(player.playerXP[13]) + "", 4031);
			sendFrame126("" + player.playerXP[13] + "", 4122);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[13]) + 1)
					+ "", 4123);
			break;

		case 14:
			sendFrame126("" + player.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(player.playerXP[14]) + "", 4029);
			sendFrame126("" + player.playerXP[14] + "", 4116);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[14]) + 1)
					+ "", 4117);
			break;

		case 15:
			sendFrame126("" + player.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(player.playerXP[15]) + "", 4021);
			sendFrame126("" + player.playerXP[15] + "", 4092);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[15]) + 1)
					+ "", 4093);
			break;

		case 16:
			sendFrame126("" + player.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(player.playerXP[16]) + "", 4019);
			sendFrame126("" + player.playerXP[16] + "", 4086);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[16]) + 1)
					+ "", 4087);
			break;

		case 17:
			sendFrame126("" + player.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(player.playerXP[17]) + "", 4023);
			sendFrame126("" + player.playerXP[17] + "", 4098);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[17]) + 1)
					+ "", 4099);
			break;

		case 18:
			sendFrame126("" + player.playerLevel[18] + "", 12166);
			sendFrame126("" + getLevelForXP(player.playerXP[18]) + "", 12167);
			sendFrame126("" + player.playerXP[18] + "", 12171);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[18]) + 1)
					+ "", 12172);
			break;

		case 19:
			sendFrame126("" + player.playerLevel[19] + "", 13926);
			sendFrame126("" + getLevelForXP(player.playerXP[19]) + "", 13927);
			sendFrame126("" + player.playerXP[19] + "", 13921);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[19]) + 1)
					+ "", 13922);
			break;

		case 20:
			sendFrame126("" + player.playerLevel[20] + "", 4152);
			sendFrame126("" + getLevelForXP(player.playerXP[20]) + "", 4153);
			sendFrame126("" + player.playerXP[20] + "", 4157);
			sendFrame126(""
					+ getXPForLevel(getLevelForXP(player.playerXP[20]) + 1)
					+ "", 4158);
			break;
		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430) {
			return 99;
		}
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean addSkillXP(int amount, int skill) {
		if (amount + player.playerXP[skill] < 0
				|| player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("You can't gain exp, until you confirm you are not a bot.");
			player.getActionSender().sendMessage("If you need to relog you can do so.");
			return false;
		}
		if (player.tutorialProgress < 36 && player.playerLevel[skill] == 3 && Constants.TUTORIAL_ISLAND == true) {
			return false;
		}
		amount *= Constants.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < player
					.getLevelForXP(player.playerXP[skill])
					&& skill != 3
					&& skill != 5) {
				player.playerLevel[skill] = player
						.getLevelForXP(player.playerXP[skill]);
			}
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		player.getActionSender().setSkillLevel(skill,
				player.playerLevel[skill], player.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}
	
	public boolean addNormalXpRate(int amount, int skill) {
		if (amount + player.playerXP[skill] < 0 || player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= 1;
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < player.getLevelForXP(player.playerXP[skill]) && skill != 3 && skill != 5) {
				player.playerLevel[skill] = player.getLevelForXP(player.playerXP[skill]);
			}
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		player.getActionSender().setSkillLevel(skill,
				player.playerLevel[skill], player.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public boolean addSkillXP(double amount, int skill) {// normal experience
															// rate method
		if (amount + player.playerXP[skill] < 0
				|| player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("You can't gain exp, until you confirm you are not a bot.");
			player.getActionSender().sendMessage("If you need to relog you can do so.");
			return false;
		}
		if (player.tutorialProgress < 36 && player.playerLevel[skill] == 3
				&& Constants.TUTORIAL_ISLAND == true) {
			return false;
		}
		amount *= Constants.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < player
					.getLevelForXP(player.playerXP[skill])
					&& skill != 3
					&& skill != 5) {
				player.playerLevel[skill] = player
						.getLevelForXP(player.playerXP[skill]);
			}
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		player.getActionSender().setSkillLevel(skill,
				player.playerLevel[skill], player.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public boolean addNormalExperienceRate(int amount, int skill) {// used for
																	// 1x
																	// experience
																	// rates
		if (amount + player.playerXP[skill] < 0
				|| player.playerXP[skill] > 200000000) {
			if (player.playerXP[skill] > 200000000) {
				player.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= 1;
		int oldLevel = getLevelForXP(player.playerXP[skill]);
		player.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(player.playerXP[skill])) {
			if (player.playerLevel[skill] < player
					.getLevelForXP(player.playerXP[skill])
					&& skill != 3
					&& skill != 5) {
				player.playerLevel[skill] = player
						.getLevelForXP(player.playerXP[skill]);
			}
			levelUp(skill);
			player.gfx100(199);
			requestUpdates();
		}
		player.getActionSender().setSkillLevel(skill,
				player.playerLevel[skill], player.playerXP[skill]);
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

	public static int Barrows[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720,
			4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747,
			4749, 4751, 4753, 4755, 4757, 4759 };

	public static final int[][] RUNES = { { 560, 1, 1 }, { 565, 1, 2 },
			{ 562, 1, 3 }, { 558, 1, 4 } };

	public static final int[] POTS = { 165, 147, 159 };

	public int randomBarrows() {
		return Barrows[(int) (Math.random() * Barrows.length)];
	}

	public void randomRunes() {
		for (int[] element : RUNES) {
			int item = element[0];
			int amount = element[1];
			int chance = element[2];
			if (Misc.random(5) == chance) {
				player.getItemAssistant().addItem(item, amount);
			}
		}
	}

	public int randomPots() {
		return POTS[(int) (Math.random() * POTS.length)];
	}

	public int getNpcId(int id) {
		for (int i = 0; i < NpcHandler.maxNPCs; i++) {
			if (NpcHandler.npcs[i] != null) {
				if (NpcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		player.getActionSender().object(-1, x, x, 10, 10);
	}

	public void objectToRemove(int X, int Y) {
		player.getActionSender().object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		player.getActionSender().object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
	}

	public boolean inPitsWait() {
		return player.getX() <= 2404 && player.getX() >= 2394
				&& player.getY() <= 5175 && player.getY() >= 5169;
	}

	public int antiFire() {
		int toReturn = 0;
		if (player.antiFirePot) {
			toReturn++;
		}
		if (player.playerEquipment[player.playerShield] == 1540
				|| player.playerEquipment[player.playerShield] == 11284
				|| player.playerEquipment[player.playerShield] == 11283) {
			toReturn++;
		}
		return toReturn;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 },
				{ 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 },
				{ 560, 100000 }, { 555, 300000 } };
		for (int[] element : itemsToCheck) {
			if (element[1] < player.getItemAssistant()
					.getTotalCount(element[0])) {
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

	public void useOperate(int itemId) {
		switch (itemId) {
		case 11283:
		case 11284:
			if (player.playerIndex > 0) {
				player.getCombatAssistant().handleDfs();
			} else if (player.npcIndex > 0) {
				player.getCombatAssistant().handleDfsNPC();
			}
			break;
		}
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
			player.getActionSender().sendMessage("You have been poisoned.");
			player.poisonDamage = damage;
			player.poison = true;
		}
		if (player.poisonDamage == 0 && player.isDead == false) {
			player.getActionSender().sendMessage("The poison has worn off.");
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
		player.getActionSender().sendMessage(
				"This pouch has " + player.pouches[i] + " rune ess in it.");
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
						player.getActionSender().sendMessage(
								"You have run out of money.");
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
			player.getItemAssistant().deleteItem(995,
					player.getItemAssistant().getItemSlot(995), totalCost);
		}
	}

	public void handleWeaponStyle() {
		if (player.fightMode == 0) {
			player.getPlayerAssistant().sendConfig(43, player.fightMode);
		} else if (player.fightMode == 1) {
			player.getPlayerAssistant().sendConfig(43, 3);
		} else if (player.fightMode == 2) {
			player.getPlayerAssistant().sendConfig(43, 1);
		} else if (player.fightMode == 3) {
			player.getPlayerAssistant().sendConfig(43, 2);
		}
	}

}
