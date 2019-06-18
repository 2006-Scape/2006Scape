package redone.game.players;

import java.util.ArrayList;

import redone.Constants;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.npcs.impl.Pets;
import redone.util.ISAACRandomGen;
import redone.util.Misc;
import redone.util.Stream;

public abstract class Player {

	public String currentTime, date, creationAddress = "", slayerMaster;
	
	public boolean lostCannon = false;

	public ArrayList<String> killedPlayers = new ArrayList<String>();
	public ArrayList<Integer> attackedPlayers = new ArrayList<Integer>();
	public ArrayList<String> lastKilledPlayers = new ArrayList<String>();
	
	public int CraftInt, Dcolor, FletchInt,  clanId = -1;
	
	public int[][] barrowCrypt = {
			{4921, 0},
			{2035, 0}
	};

	public long lastCast = 0, homeTele, lastDesert, eventTimer, lastRunRecovery,
			lastButton, lastFire, lastLight, muteTime, waitTime, miscTimer,
			ladderTimer, webSlashDelay, climbDelay, lastReport = 0,
			lastPlayerMove, lastPoison, lastPoisonSip, poisonImmune, lastSpear,
			lastProtItem, dfsDelay, lastVeng, lastYell, teleGrabDelay,
			protMageDelay, protMeleeDelay, protRangeDelay, lastAction,
			lastThieve, lastLockPick, alchDelay, specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay,
			godSpellDelay, singleCombatDelay, singleCombatDelay2, reduceStat,
			restoreStatsDelay, logoutDelay, buryDelay, foodDelay, potDelay,
			doorDelay, doubleDoorDelay, buySlayerTimer, lastIncrease,
			boneDelay, botAttempts, leverDelay = 0, farmTime, searchObjectDelay = 0;
	
	private Npc specialTarget = null;
	public void setSpecialTarget(Npc target) {
			this.specialTarget = target;
		}
		public Npc getSpecialTarget() {
			return specialTarget;
		}

	public boolean initialized = false, musicOn = true, luthas,
			playerIsCooking, disconnected = false, ruleAgreeButton = false,
			RebuildNPCList = false, isActive = false, isKicked = false,
			isSkulled = false, friendUpdate = false, newPlayer = false,
			hasMultiSign = false, saveCharacter = false, mouseButton = false,
			chatEffects = true, acceptAid = false, recievedMask,
			nextDialogue = false, autocasting = false, usedSpecial = false,
			mageFollow = false, dbowSpec = false, craftingLeather = false,
			properLogout = false, secDbow = false, maxNextHit = false,
			ssSpec = false, vengOn = false, addStarter = false,
			accountFlagged = false, inPartyRoom = false, msbSpec = false,
			hasBankPin, enterdBankpin, firstPinEnter, requestPinDelete,
			secondPinEnter, thirdPinEnter, fourthPinEnter, hasBankpin,
			isBanking, isTeleporting = false, desertWarning,
			isPotionMaking = false, isGrinding = false, hasStarter, isSpinning,
			clickedSpinning, hasPaidBrim, isHerblore, herbloreI, secondHerb,
			playerStun, playerFletch, isWoodcutting, playerIsFiremaking,
			hasNpc = false, playerIsFishing = false, isOperate, below459 = true,
			splitChat, strongHold, village, needsNewTask = false,
			canSpeak = true, ignoreFrog, ratdied2 = false,
			fishingWhirlPool, lostDuel, diedOnTut = false, storing = false, rope, rope2,
			canWalkTutorial, closeTutorialInterface, isCrafting, showedUnfire,
			showedFire, isPotCrafting, isFiremaking, playerIsFletching, milking,
			stopPlayerPacket, spiritTree = false, isSmelting,
			hasPaifAnTeleport = false, isSmithing, doingAgility = false,
			hasPaid, canTeleport, magicCharge, isBanned = false, fletchNerfed,
			clickedVamp = false, allowFading, isBotting = false, otherBank = false,
			recievedReward = false, poison, golemSpawned = false, zombieSpawned = false, shadeSpawned = false,
			treeSpiritSpawned = false, chickenSpawned = false, clickedTree = false, filter = true,
			stopPlayer = false, npcCanAttack = true, gliderOpen = false, hasSandwhichLady = false,
			isHarvesting, openDuel = false,  killedJad = false, canHealersRespawn = true, playerIsBusy = false, miningRock;

	public int thankedForDonation, saveDelay, playerKilled, gertCat, restGhost,
			romeojuliet, runeMist, vampSlayer, cookAss, doricQuest,
			dragonSlayerQuestStage, sheepShear, impsC, randomActions, pkPoints,
			totalPlayerDamageDealt, killedBy, lastChatId = 1, privateChat,
			friendSlot = 0, dialogueId, randomCoffin, newLocation, specEffect,
			specBarId, attackLevelReq, defenceLevelReq, strengthLevelReq,
			rangeLevelReq, magicLevelReq, slayerLevelReq, agilityLevelReq,
			followId, skullTimer, votingPoints, nextChat = 0, talkingNpc = -1,
			dialogueAction = 0, autocastId, followDistance, followId2,
			barrageCount = 0, delayedDamage = 0, delayedDamage2 = 0,
			pcPoints = 0, magePoints = 0, desertTreasure = 0,
			lastArrowUsed = -1, autoRet = 0, pcDamage = 0, xInterfaceId = 0,
			xRemoveId = 0, xRemoveSlot = 0, tzhaarToKill = 0, tzhaarKilled = 0,
			waveId, frozenBy = 0, poisonDamage = 0, teleAction = 0,
			bonusAttack = 0, lastNpcAttacked = 0, killCount = 0, witchspot,
			pirateTreasure, ptjob, cwKills, cwDeaths, cwGames, tzKekSpawn = 0,
			playerBankPin, recoveryDelay = 3, attemptsRemaining = 3,
			lastPinSettings = -1, setPinDate = -1, changePinDate = -1,
			deletePinDate = -1, firstPin, secondPin, thirdPin, fourthPin,
			bankPin1, bankPin2, bankPin3, bankPin4, pinDeleteDateRequested,
			rememberNpcIndex, lastLoginDate, selectedSkill, log = -1, newHerb,
			newItem, newXp, doingHerb, herbAmount, treeX, treeY, lastH,
			cookingItem, cookingObject, summonId, npcId2 = 0, leatherType = -1,
			weightCarried, teleotherType, rockX, rockY, itemUsing, tzKekTimer, 
			bananas, flourAmount, grain, questPoints, questStages,
			teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy,
			underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0,
			teleBlockLength, poisonDelay, slayerPoints, blackMarks,
			playerEnergy = 100, SlayerMaster, teleOtherTimer = 0,
			teleOtherSlot = -1, tutorialProgress, Cookstage1 = 1,
			woodcuttingTree, smeltAmount, knightS, otherDirection,
			brightness = 3, recoilHits, droppedItem = -1,
					spawnedHealers, cannonX = 0, cannonY = 0;

	public Pets getSummon() {
		return pets;
	}

	private final Pets pets = new Pets();

	public int removedTasks[] = { -1, -1, -1, -1 };

	public boolean isRunning() {
		return isNewWalkCmdIsRunning() || isRunning2 && isMoving;
	}

	public void faceNpc(int npc) {
		face = npc;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void faceNPC(int index) {
		faceNPC = index;
		faceNPCupdate = true;
		updateRequired = true;
	}

	protected boolean faceNPCupdate = false;
	public int faceNPC = -1;

	public void appendFaceNPCUpdate(Stream str) {
		str.writeWordBigEndian(faceNPC);
	}

	public int getLocalX() {
		return getX() - 8 * getMapRegionX();
	}

	public int getLocalY() {
		return getY() - 8 * getMapRegionY();
	}

	public int getKillCount() {
		return barrowsKillCount;
	}

	/**
	 * @param randomGrave
	 *            the randomGrave to set
	 */
	public void setRandomGrave(int randomGrave) {
		this.randomGrave = randomGrave;
	}

	/**
	 * @return the randomGrave
	 */
	public int getRandomGrave() {
		return randomGrave;
	}

	private int randomGrave;

	public void setBarrowsNpcDead(int index, boolean dead) {
		barrowsNpcDead[index] = dead;
	}

	/**
	 * @return the barrowsNpcDead
	 */
	public boolean[] getBarrowsNpcDead() {
		return barrowsNpcDead;
	}

	/**
	 * @return the barrowsNpcDead
	 */
	public boolean getBarrowsNpcDead(int id) {
		return barrowsNpcDead[id];
	}

	private final boolean barrowsNpcDead[] = new boolean[6];

	public abstract void updateWalkEntities();

	public boolean membership = false, awardedmembership = false;
	public Client teleporter = null;
	public int[] miningSettings = new int[10];
	public int[] fishingProp = new int[13];
	public int[] party = new int[8];
	public int[] partyN = new int[8];
	public int[] cookingProp = new int[7];
	public int[] cookingCoords = new int[2];
	public String lastReported = "";
	public String bankPin = "";
	public int attempts = 3;
	public boolean setPin = false;
	public int[][] playerSkillProp = new int[20][15];
	public boolean[] playerSkilling = new boolean[20];
	public boolean stopPlayerSkill;
	public int doAmount, addAmount;
	public int lastNpcClickIndex;
	public boolean[] killedPheasant = new boolean[5];
	public boolean playerHasRandomEvent;
	public boolean canLeaveArea;
	public int pieSelect = 0, getPheasent, kebabSelect = 0, breadID,
			chocSelect = 0, bagelSelect = 0, triangleSandwich = 0,
			squareSandwich = 0, breadSelect = 0;
	public String clanName, properName;
	public int lastX, lastY;
	public int[] voidStatus = new int[5];
	public int[] itemKeptId = new int[4];
	public int[] pouches = new int[4];
	public final int[] POUCH_SIZE = { 3, 6, 9, 12 };
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
	public long friends[] = new long[200], ignores[] = new long[200];
	public double specAmount = 0;
	public double specAccuracy = 1;
	public double specDamage = 1;
	public boolean isFletching;
	public double weight = 0.0;

	public boolean canChangeAppearance = false;
	public boolean mageAllowed;
	public int poisonMask;

	public Npc getCloseRandomNpc(int distance) {
		ArrayList<Npc> npcs = new ArrayList<Npc>();
		for (Npc npc : NpcHandler.npcs) {
			if (npc != null) {
				Npc n = npc;
				if (distanceToPoint(n.getX(), n.getY()) <= distance) {
					if (!n.underAttack) {
						if (n.heightLevel == heightLevel) {
							npcs.add(n);
						}
					}
				}
			}
		}
		if (npcs.size() > 0) {
			return npcs.get(Misc.random(npcs.size() - 1));
		} else {
			return null; // No near npcs :C
		}
	}

	public boolean isAutoButton(int button) {
		for (int j = 0; j < autocastIds.length; j += 2) {
			if (autocastIds[j] == button) {
				return true;
			}
		}
		return false;
	}

	public int[] autocastIds = { 51133, 32, 51185, 33, 51091, 34, 24018, 35,
			51159, 36, 51211, 37, 51111, 38, 51069, 39, 51146, 40, 51198, 41,
			51102, 42, 51058, 43, 51172, 44, 51224, 45, 51122, 46, 51080, 47,
			7038, 0, 7039, 1, 7040, 2, 7041, 3, 7042, 4, 7043, 5, 7044, 6,
			7045, 7, 7046, 8, 7047, 9, 7048, 10, 7049, 11, 7050, 12, 7051, 13,
			7052, 14, 7053, 15, 47019, 27, 47020, 25, 47021, 12, 47022, 13,
			47023, 14, 47024, 15 };

	// public String spellName = "Select Spell";
	public void assignAutocast(int button) {
		for (int j = 0; j < autocastIds.length; j++) {
			if (autocastIds[j] == button) {
				Client c = (Client) PlayerHandler.players[playerId];
				autocasting = true;
				autocastId = autocastIds[j + 1];
				c.getPlayerAssistant().sendConfig(108, 1);
				c.getActionSender().setSidebarInterface(0, 328);
				// spellName = getSpellName(autocastId);
				// spellName = spellName;
				// c.getPA().sendString(spellName, 354);
				c = null;
				break;
			}
		}
	}

	public boolean inCWsaraBase() {
		if (absX > 2422 && absX < 2432 && absY > 3071 && absY < 3081
				&& heightLevel == 1) {
			return true;
		}
		return false;
	}

	public boolean inCWzammyBase() {
		if (absX > 2367 && absX < 2377 && absY > 3126 && absY < 3136
				&& heightLevel == 1) {
			return true;
		}
		return false;
	}

	public boolean saraTeam() {
		return playerEquipment[Constants.CAPE] == 4041;
	}

	public boolean zammyTeam() {
		return playerEquipment[Constants.CAPE] == 4042;
	}

	public boolean inCwSafe() {
		return (Area(2423, 2431, 3072, 3080) || Area(2368, 2376, 3127, 3135))
				&& heightLevel == 1;
	}

	public boolean inZammyWait() {
		return Area(2409, 2431, 9511, 9535);
	}

	public boolean inSaraWait() {
		return Area(2368, 2392, 9479, 9498);
	}

	public boolean inCwGame() {
		return Area(2368, 2431, 9479, 9535) || Area(2368, 2431, 3072, 3135)
				&& !inSaraWait() && !inZammyWait();
	}

	public boolean inCwUnderground() {
		return Area(2368, 2431, 9479, 9535) && !inSaraWait() && !inZammyWait();
	}

	public boolean inZammyBase() {
		return Area(2368, 2384, 3118, 3135);
	}

	public boolean inSaraBase() {
		return Area(2414, 2431, 3072, 3088);
	}

	public void gameInterface(int id) {
		if (gameInterface != id) {
			gameInterface = id;
		}
	}

	public int gameInterface;
	public int lastGame;


	public int[][] barrowsNpcs = { { 2030, 0 }, // verac
			{ 2029, 0 }, // toarg
			{ 2028, 0 }, // karil
			{ 2027, 0 }, // guthan
			{ 2026, 0 }, // dharok
			{ 2025, 0 } // ahrim
	};

	public int barrowsKillCount;

	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = { 250000, 250000, 250000, 500000,
			500000, 500000 }; // how long does the other player stay immune to
	// the spell
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = { 1153, 1157, 1161, 1542, 1543, 1562 };
	public boolean[] canUseReducingSpell = { true, true, true, true, true, true };

	public int slayerTask, taskAmount;

	public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq,
			duelOption, duelingWith, duelStatus;
	public int headIconPk = -1, headIconHints;
	public boolean duelRequested;
	public boolean[] duelRule = new boolean[22];
	public final int[] DUEL_RULE_ID = { 1, 2, 16, 32, 64, 128, 256, 512, 1024,
			4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152,
			8388608, 16777216, 67108864, 134217728 };

	public boolean doubleHit, usingSpecial, npcDroppingItems, usingRangeWeapon,
			usingBow, usingMagic, castingMagic;
	public int npcIndex, npcClickIndex, npcType, castingSpellId, oldSpellId,
			spellId, hitDelay;
	public int specMaxHitIncrease, freezeDelay, freezeTimer = -6, killerId,
			playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage,
			crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation,
			teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex,
			totalDamageDealt, oldNpcIndex, fightMode, attackTimer;
	public boolean magicFailed, oldMagicFailed;
	public int bowSpecShot, clickNpcType, clickObjectType, objectId, objectX,
			objectY, objectXOffset, objectYOffset, objectDistance;
	public int pItemX, pItemY, pItemId;
	public boolean isMoving, walkingToItem;
	public boolean isShopping, updateShop;
	public int myShopId;
	public int tradeStatus, tradeWith;
	public boolean forcedChatUpdateRequired, inDuel, tradeAccepted, goodTrade,
			inTrade, tradeRequested, tradeResetNeeded, tradeConfirmed,
			tradeConfirmed2, acceptTrade, acceptedTrade;
	public int attackAnim, animationRequest = -1, animationWaitCycles;
	public int[] playerBonus = new int[12];
	public boolean isRunning2 = true;
	public boolean takeAsNote;
	public int combatLevel;
	public boolean saveFile = false;
	public int playerAppearance[] = new int[13];
	public int apset;
	public int actionID;
	public int wearItemTimer, wearId, wearSlot, interfaceId;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	// public int tutorial;
	public boolean usingGlory = false;
	public int[] woodcut = new int[7];
	public int wcTimer = 0;
	public int miningTick = 0;
	public int miningAnimTick = 0;
	public int miningRockId = -1;
	public int miningX = -1;
	public int miningY = -1;
	public boolean fishing = false;
	public int fishTimer = 0;
	public boolean isMining;
	public int smeltType; // 1 = bronze, 2 = iron, 3 = steel, 4 = gold, 5 =
							// mith, 6 = addy, 7 = rune
	public int smeltTimer = 0;
	public boolean smeltInterface;
	public boolean patchCleared;
	public int[] farmData = new int[2];

	public boolean antiFirePot = false;
	
	public boolean underWater = false;
    public boolean prevRunning2;
    public int prevPrevPlayerRunIndex;
    public int prevPlayerStandIndex;
    public int prevplayerWalkIndex;
    public int prevPlayerTurnIndex;
    public int prevPlayerTurn90CWIndex;
    public int prevPlayerTurn90CCWIndex;
    public int prevPlayerTurn180Index;

   public Client asClient() {
       return (Client) this;
   }
   
   private Player player;
   public Player asPlayer() {
       return (Player) player;
   }

   public boolean inTrawlerBoat() {
       if(inArea(2808, 2811,3415,3425)) {
           return true;
       }
       return false;
   }
   
   public boolean inTrawlerGame() {
       if(inArea(2808, 2811,3415,3425)) {
           return true;
       }
       return false;
   }

   public long lastFishingTrawlerInteraction;
   public boolean inFishingTrawlerRewardsInterface;

	/**
	 * Castle Wars
	 */
	public int castleWarsTeam;
	public boolean inCwGame;
	public boolean inCwWait;

	/**
	 * Fight Pits
	 */
	public boolean inPits = false;
	public int pitsStatus = 0;

	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */

	public boolean inCw() {
		Client c = (Client) this;
		if (CastleWars.isInCwWait(c)) {
			return true;
		}
		if (CastleWars.isInCw(c)) {
			return true;
		}
		return false;
	}

	public boolean isInTut() {
		if (absX >= 2625 && absX <= 2687 && absY >= 4670 && absY <= 4735) {
			return true;
		}
		return false;
	}

	public boolean FightPitsArea() {
		return absX >= 2378 && absX <= 2415 && absY >= 5133 && absY <= 5167
				|| absX >= 2394 && absX <= 2404 && absY >= 5169 && absY <= 5174;
	}

	public boolean fightPitsArea() {
		return absX >= 2378 && absX <= 2415 && absY >= 5133 && absY <= 5167;
	}

	public boolean inBarrows() {
		if (absX > 3520 && absX < 3598 && absY > 9653 && absY < 9750) {
			return true;
		}
		return false;
	}

	public boolean inArea(int x, int y, int x1, int y1) {
		if (absX > x && absX < x1 && absY < y && absY > y1) {
			return true;
		}
		return false;
	}

	public boolean inMulti() {
		if (absX >= 3136
				&& absX <= 3327
				&& absY >= 3519
				&& absY <= 3607// duel?
				|| absX >= 2360
				&& absX <= 2445
				&& absY >= 5045
				&& absY <= 5125
				|| absX >= 3190
				&& absX <= 3327
				&& absY >= 3648
				&& absY <= 3839// duel?
				|| absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967
				|| absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967
				|| absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831
				|| absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903
				|| absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711
				|| absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647
				|| absX >= 2624
				&& absX <= 2690
				&& absY >= 2550
				&& absY <= 2619
				|| absX >= 2667
				&& absX <= 2685
				&& absY >= 3712
				&& absY <= 3730 // rock
								// crabs
				|| absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117
				|| absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630
				|| absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464
				|| absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711) {
			return true;
		}
		return false;
	}

	public boolean inWild() {
		if (inCw()) {
			return true;
		}
		if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966
				|| absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
			return true;
		}
		return false;
	}

	public boolean inDesert() {
		return absX >= 3137 && absX <= 3321 && absY >= 2880 && absY <= 3115;
	}

	public boolean duelingArena() {
		if (absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260) {
			return true;
		}
		return false;
	}
	
	
	 public boolean playerIsBusy() {
	        if(isShopping || inTrade || openDuel || isBanking || duelStatus == 1) {
	            return true;
	        }
	        return false;
	    }
	
	public boolean isInBarrows() {		
		if(absX > 3543 && absX < 3584 && absY > 3265 && absY < 3311) {
			return true;
		}
		return false;
	}
	
	public boolean isInBarrows2() {		
		if(absX > 3529 && absX < 3581 && absY > 9673 && absY < 9722) {
			return true;
		}
		return false;
	}

	public boolean inPcBoat() {
		return absX >= 2660 && absX <= 2663 && absY >= 2638 && absY <= 2643;
	}

	public boolean inPcGame() {
		return absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619;
	}

	public boolean inDuelArena() {
		if (absX > 3322 && absX < 3394 && absY > 3195 && absY < 3291 || absX > 3311 && absX < 3323 && absY > 3223 && absY < 3248) {
			return true;
		}
		return false;
	}

	public boolean inBank() { // Area(top left X, bottom right X, bottom right Y, top left Y)
		   return Area(3090, 3099, 3487, 3500) || Area(3089, 3090, 3492, 3498) || Area(3249, 3258, 3413, 3428) || Area(3180, 3191, 3432, 3448) || Area(2945, 2948, 3365, 3374) || 
			   Area(2943, 2948, 3367, 3374) || Area(2945, 2950, 3365, 3370) || Area(3009, 3018, 3352, 3359) || Area(3017, 3022, 3353, 3357);
	}
	
	public boolean inLumbBuilding() {
		return Area(3205, 3216, 3209, 3228) || Area(3229, 3233, 3206, 3208) || Area(3228, 3233, 3201, 3205) || Area(3230, 3237, 3195, 3198) || Area(3238, 3229, 3209, 3211) ||
			   Area(3240, 3247, 3204, 3215) || Area(3247, 3252, 3190, 3195) || Area(3227, 3230, 3212, 3216) || Area(3227, 3230, 3221, 3225) || Area(3229, 3232, 3236, 3241) ||
			   Area(3209, 3213, 3243, 3250) || Area(3222, 3229, 3252, 3257) || Area(3184, 3192, 3270, 3275) || Area(3222, 3224, 3292, 3294) || Area(3225, 3230, 3287, 3228) ||
			   Area(3243, 3248, 3244, 3248) || Area(3202, 3205, 3167, 3170) || Area(3231, 3238, 3151, 3155) || Area(3233, 3234, 3156, 3156) || Area(3163, 3170, 3305, 3308) ||
			   Area(3165, 3168, 3303, 3310);
	}

     	public boolean inDraynorBuilding() {
			return Area(3097, 3102, 3277, 3281) || Area(3088, 3092, 3273, 3276) || Area(3096, 3102, 3266, 3270) || Area(3089, 3095, 3265, 3268) || Area(3083, 3088, 3256, 3261) ||
				   Area(3087, 3094, 3251, 3255) || Area(3121, 3130, 3240, 3246) || Area(3102, 3112, 3162, 3165) || Area(3107, 3111, 3166, 3166) || Area(3103, 3115, 3157, 3161) ||
				   Area(3105, 3114, 3156, 3156) || Area(3105, 3113, 3155, 3155) || Area(3106, 3112, 3154, 3154) || Area(3092, 3097, 3240, 3246);
	}

	public boolean Area(final int x1, final int x2, final int y1, final int y2) {
		return absX >= x1 && absX <= x2 && absY >= y1 && absY <= y2;
	}

	public boolean altars() {
		return safeAreas(3090, 3506, 3097, 3506);
	}

	public boolean safeAreas(int x, int y, int x1, int y1) {
		return absX >= x && absX <= x1 && absY >= y && absY <= y1;
	}

	public boolean inFightCaves() {
		return absX >= 2360 && absX <= 2445 && absY >= 5045 && absY <= 5125;
	}

	public boolean inPirateHouse() {
		return absX >= 3038 && absX <= 3044 && absY >= 3949 && absY <= 3959;
	}

	public String connectedFrom = "";
	public String globalMessage = "";

	public abstract void initialize();

	public abstract void update();

	public int playerId = -1;
	public String playerName = null;
	public String playerName2 = null;
	public String playerPass = null;
	public int playerRights;
	public PlayerHandler handler = null;
	public int playerItems[] = new int[28];
	public int playerItemsN[] = new int[28];
	public int bankItems[] = new int[Constants.BANK_SIZE];
	public int bankItemsN[] = new int[Constants.BANK_SIZE];
	public boolean bankNotes = false;
	public boolean shouldSave = false;

	public int playerStandIndex = 0x328;
	public int playerTurnIndex = 0x337;
	public int playerWalkIndex = 0x333;
	public int playerTurn180Index = 0x334;
	public int playerTurn90CWIndex = 0x335;
	public int playerTurn90CCWIndex = 0x336;
	public int playerRunIndex = 0x338;

	public int playerHat = 0;
	public int playerCape = 1;
	public int playerAmulet = 2;
	public int playerWeapon = 3;
	public int playerChest = 4;
	public int playerShield = 5;
	public int playerLegs = 7;
	public int playerHands = 9;
	public int playerFeet = 10;
	public int playerRing = 12;
	public int playerArrows = 13;

	public int playerAttack = 0;
	public int playerDefence = 1;
	public int playerStrength = 2;
	public int playerHitpoints = 3;
	public int playerRanged = 4;
	public int playerPrayer = 5;
	public int playerMagic = 6;
	public int playerCooking = 7;
	public int playerWoodcutting = 8;
	public int playerFletching = 9;
	public int playerFishing = 10;
	public int playerFiremaking = 11;
	public int playerCrafting = 12;
	public int playerSmithing = 13;
	public int playerMining = 14;
	public int playerHerblore = 15;
	public int playerAgility = 16;
	public int playerThieving = 17;
	public int playerSlayer = 18;
	public int playerFarming = 19;
	public int playerRunecrafting = 20;

	public int[] playerEquipment = new int[14];
	public int[] playerEquipmentN = new int[14];
	public int[] playerLevel = new int[25];
	public int[] playerXP = new int[25];

	public void updateshop(int i) {
		Client p = (Client) PlayerHandler.players[playerId];
		p.getShopAssistant().resetShop(i);
	}

	public void println_debug(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public void println(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public Player(int _playerId) {
		playerId = _playerId;
		playerRights = 0;

		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i = 0; i < playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i = 0; i < playerLevel.length; i++) {
			if (i == 3) {
				playerLevel[i] = 10;
			} else {
				playerLevel[i] = 1;
			}
		}

		for (int i = 0; i < playerXP.length; i++) {
			if (i == 3) {
				playerXP[i] = 1300;
			} else {
				playerXP[i] = 0;
			}
		}
		for (int i = 0; i < Constants.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i = 0; i < Constants.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}

		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 7; // head
		playerAppearance[2] = 25;// Torso
		playerAppearance[3] = 29; // arms
		playerAppearance[4] = 35; // hands
		playerAppearance[5] = 39; // legs
		playerAppearance[6] = 44; // feet
		playerAppearance[7] = 14; // beard
		playerAppearance[8] = 7; // hair colour
		playerAppearance[9] = 8; // torso colour
		playerAppearance[10] = 9; // legs colour
		playerAppearance[11] = 5; // feet colour
		playerAppearance[12] = 0; // skin colour

		apset = 0;
		actionID = 0;

		playerEquipment[playerHat] = -1;
		playerEquipment[playerCape] = -1;
		playerEquipment[playerAmulet] = -1;
		playerEquipment[playerChest] = -1;
		playerEquipment[playerShield] = -1;
		playerEquipment[playerLegs] = -1;
		playerEquipment[playerHands] = -1;
		playerEquipment[playerFeet] = -1;
		playerEquipment[playerRing] = -1;
		playerEquipment[playerArrows] = -1;
		playerEquipment[playerWeapon] = -1;

		heightLevel = 0;

		if (Constants.TUTORIAL_ISLAND) {
			teleportToX = 3094;
			teleportToY = 3107;
		} else {
			teleportToX = 3233;
			teleportToY = 3229;
		}

		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	void destruct() {
		playerListSize = 0;
		for (int i = 0; i < maxPlayerListSize; i++) {
			playerList[i] = null;
		}
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public static final int maxPlayerListSize = Constants.MAX_PLAYERS;
	public Player playerList[] = new Player[maxPlayerListSize];
	public int playerListSize = 0;

	public byte playerInListBitmap[] = new byte[Constants.MAX_PLAYERS + 7 >> 3];

	public static final int maxNPCListSize = NpcHandler.maxNPCs;
	public Npc npcList[] = new Npc[maxNPCListSize];
	public int npcListSize = 0;

	public byte npcInListBitmap[] = new byte[NpcHandler.maxNPCs + 7 >> 3];

	public boolean withinDistance(Player otherPlr) {
		if (heightLevel != otherPlr.heightLevel) {
			return false;
		}
		int deltaX = otherPlr.absX - absX, deltaY = otherPlr.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(Npc npc) {
		if (heightLevel != npc.heightLevel) {
			return false;
		}
		if (npc.needRespawn == true) {
			return false;
		}
		int deltaX = npc.absX - absX, deltaY = npc.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(int absX, int getY, int getHeightLevel) {
		if (heightLevel != getHeightLevel) {
			return false;
		}
		if (objectId == 2242) {
			System.out.println("not within distance");
			return false;
		}
		int deltaX = getX() - absX, deltaY = getY() - getY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public int distanceToPoint(int pointX, int pointY) {
		return (int) Math.sqrt(Math.pow(absX - pointX, 2)
				+ Math.pow(absY - pointY, 2));
	}

	public int mapRegionX, mapRegionY;
	public int absX;

	public int absY;
	public int currentX, currentY;

	public int heightLevel;
	public int playerSE = 0x328;
	public int playerSEW = 0x333;
	public int playerSER = 0x334;

	public boolean updateRequired = true;

	public final int walkingQueueSize = 50;
	public int walkingQueueX[] = new int[walkingQueueSize],
			walkingQueueY[] = new int[walkingQueueSize];
	public int wQueueReadPtr = 0;
	public int wQueueWritePtr = 0;
	public boolean isRunning = true;
	public int teleportToX = -1, teleportToY = -1;

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;
		for (int i = 0; i < walkingQueueSize; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}

	public void addToWalkingQueue(int x, int y) {
		// if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
		int next = (wQueueWritePtr + 1) % walkingQueueSize;
		if (next == wQueueWritePtr) {
			return;
		}
		walkingQueueX[wQueueWritePtr] = x;
		walkingQueueY[wQueueWritePtr] = y;
		wQueueWritePtr = next;
		// }
	}

	public boolean goodDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if (objectId == 2282 || objectId == 10883 || objectId == 2322
						|| objectId == 4493 || objectId == 12164
						|| objectId == 1721 || objectId == 1722
						|| objectId == 4304 && playerX == 2619
						&& playerY == 3667) {
					return true;
				}
				if (objectX + i == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				} else if (objectX - i == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNextWalkingDirection() {
		if (wQueueReadPtr == wQueueWritePtr) {
			return -1;
		}
		int dir;
		do {
			dir = Misc.direction(currentX, currentY,
					walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if (dir == -1) {
				wQueueReadPtr = (wQueueReadPtr + 1) % walkingQueueSize;
			} else if ((dir & 1) != 0) {
				println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while (dir == -1 && wQueueReadPtr != wQueueWritePtr);
		if (dir == -1) {
			return -1;
		}
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		/*if (!Region.canMove(absX, absY, (absX + Misc.directionDeltaX[dir]), (absY + Misc.directionDeltaY[dir]), heightLevel, 1, 1))
			return -1;*/
		absX += Misc.directionDeltaX[dir];
		absY += Misc.directionDeltaY[dir];
		updateWalkEntities();
		return dir;
	}

	public boolean didTeleport = false;
	public boolean mapRegionDidChange = false;
	public int dir1 = -1, dir2 = -1;
	public boolean createItems = false;
	public int poimiX = 0, poimiY = 0;

	public synchronized void getNextPlayerMovement() {
		mapRegionDidChange = false;
		didTeleport = false;
		dir1 = dir2 = -1;

		if (teleportToX != -1 && teleportToY != -1) {
			mapRegionDidChange = true;
			if (mapRegionX != -1 && mapRegionY != -1) {
				int relX = teleportToX - mapRegionX * 8, relY = teleportToY
						- mapRegionY * 8;
				if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8
						&& relY < 11 * 8) {
					mapRegionDidChange = false;
				}
			}
			if (mapRegionDidChange) {
				mapRegionX = (teleportToX >> 3) - 6;
				mapRegionY = (teleportToY >> 3) - 6;
			}
			currentX = teleportToX - 8 * mapRegionX;
			currentY = teleportToY - 8 * mapRegionY;
			absX = teleportToX;
			absY = teleportToY;
			resetWalkingQueue();

			teleportToX = teleportToY = -1;
			didTeleport = true;
			updateWalkEntities();
		} else {
			dir1 = getNextWalkingDirection();
			if (dir1 == -1) {
				return;
			}
			if (isRunning) {
				dir2 = getNextWalkingDirection();
			}
			// c.sendMessage("Cycle Ended");
			int deltaX = 0, deltaY = 0;
			if (currentX < 2 * 8) {
				deltaX = 4 * 8;
				mapRegionX -= 4;
				mapRegionDidChange = true;
			} else if (currentX >= 11 * 8) {
				deltaX = -4 * 8;
				mapRegionX += 4;
				mapRegionDidChange = true;
			}
			if (currentY < 2 * 8) {
				deltaY = 4 * 8;
				mapRegionY -= 4;
				mapRegionDidChange = true;
			} else if (currentY >= 11 * 8) {
				deltaY = -4 * 8;
				mapRegionY += 4;
				mapRegionDidChange = true;
			}

			if (mapRegionDidChange) {
				currentX += deltaX;
				currentY += deltaY;
				for (int i = 0; i < walkingQueueSize; i++) {
					walkingQueueX[i] += deltaX;
					walkingQueueY[i] += deltaY;
				}
			}
		}
	}

	public void updateThisPlayerMovement(Stream str) {
		if (mapRegionDidChange) {
			str.createFrame(73);
			str.writeWordA(mapRegionX + 6);
			str.writeWord(mapRegionY + 6);
		}

		if (didTeleport) {
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);
			str.writeBits(2, 3);
			str.writeBits(2, heightLevel);
			str.writeBits(1, 1);
			str.writeBits(1, updateRequired ? 1 : 0);
			str.writeBits(7, currentY);
			str.writeBits(7, currentX);
			return;
		}

		if (dir1 == -1) {
			// don't have to update the character position, because we're
			// just standing
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			isMoving = false;
			if (updateRequired) {
				// tell client there's an update block appended at the end
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
			if (DirectionCount < 50) {
				DirectionCount++;
			}
		} else {
			DirectionCount = 0;
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);

			if (dir2 == -1) {
				isMoving = true;
				str.writeBits(2, 1);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				if (updateRequired) {
					str.writeBits(1, 1);
				} else {
					str.writeBits(1, 0);
				}
			} else {
				isMoving = true;
				str.writeBits(2, 2);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
				if (updateRequired) {
					str.writeBits(1, 1);
				} else {
					str.writeBits(1, 0);
				}
				if (playerEnergy > 0 && playerRights < 2) {
					if (weight > 0.0) {
						playerEnergy -= 1 + Misc.random(1);
					} else {
						playerEnergy -= 1;
					}
				} else if (playerRights >= 2) {
					playerEnergy = 100;
					isRunning2 = true;
				} else if (playerEnergy < 1) {
					playerEnergy = 0;
					isRunning2 = false;
				}
			}
		}
	}

	public void updatePlayerMovement(Stream str) {
		if (dir1 == -1) {
			if (updateRequired || isChatTextUpdateRequired()) {

				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else if (dir2 == -1) {

			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(1, updateRequired || isChatTextUpdateRequired() ? 1
					: 0);
		} else {

			str.writeBits(1, 1);
			str.writeBits(2, 2);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
			str.writeBits(1, updateRequired || isChatTextUpdateRequired() ? 1
					: 0);
		}
	}

	public byte cachedPropertiesBitmap[] = new byte[Constants.MAX_PLAYERS + 7 >> 3];

	public void addNewNPC(Npc npc, Stream str, Stream updateBlock) {
		int id = npc.npcId;
		npcInListBitmap[id >> 3] |= 1 << (id & 7);
		npcList[npcListSize++] = npc;

		str.writeBits(14, id);

		int z = npc.absY - absY;
		if (z < 0) {
			z += 32;
		}
		str.writeBits(5, z);
		z = npc.absX - absX;
		if (z < 0) {
			z += 32;
		}
		str.writeBits(5, z);

		str.writeBits(1, 0);
		str.writeBits(12, npc.npcType);

		boolean savedUpdateRequired = npc.updateRequired;
		npc.updateRequired = true;
		npc.appendNPCUpdateBlock(updateBlock);
		npc.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
	}

	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		int id = plr.playerId;
		playerInListBitmap[id >> 3] |= 1 << (id & 7);
		playerList[playerListSize++] = plr;
		str.writeBits(11, id);
		str.writeBits(1, 1);
		boolean savedFlag = plr.isAppearanceUpdateRequired();
		boolean savedUpdateRequired = plr.updateRequired;
		plr.setAppearanceUpdateRequired(true);
		plr.updateRequired = true;
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setAppearanceUpdateRequired(savedFlag);
		plr.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
		int z = plr.absY - absY;
		if (z < 0) {
			z += 32;
		}
		str.writeBits(5, z);
		z = plr.absX - absX;
		if (z < 0) {
			z += 32;
		}
		str.writeBits(5, z);
	}

	public int headIcon = -1, bountyIcon = 0;

	public int DirectionCount = 0;
	public boolean appearanceUpdateRequired = true;
	public int hitDiff2;
	public int hitDiff = 0;
	public boolean hitUpdateRequired2;
	public boolean hitUpdateRequired = false;
	public boolean isDead = false;

	protected static Stream playerProps;
	static {
		playerProps = new Stream(new byte[100]);
	}

	protected void appendPlayerAppearance(Stream str) {
		playerProps.currentOffset = 0;

		playerProps.writeByte(playerAppearance[0]);

		playerProps.writeByte(headIcon);
		playerProps.writeByte(headIconPk);
		// playerProps.writeByte(headIconHints);
		// playerProps.writeByte(bountyIcon);

		if (playerEquipment[playerHat] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerHat]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerCape] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerCape]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerAmulet] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerWeapon] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerChest] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerChest]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[2]);
		}

		if (playerEquipment[playerShield] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerShield]);
		} else {
			playerProps.writeByte(0);
		}

		if (!Item.isFullBody(playerEquipment[playerChest])) {
			playerProps.writeWord(0x100 + playerAppearance[3]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerLegs] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[5]);
		}

		if (!Item.isFullHelm(playerEquipment[playerHat])
				&& !Item.isFullMask(playerEquipment[playerHat])) {
			playerProps.writeWord(0x100 + playerAppearance[1]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerHands] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerHands]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[4]);
		}

		if (playerEquipment[playerFeet] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[6]);
		}

		if (playerAppearance[0] != 1
				&& !Item.isFullMask(playerEquipment[playerHat])) {
			playerProps.writeWord(0x100 + playerAppearance[7]);
		} else {
			playerProps.writeByte(0);
		}

		playerProps.writeByte(playerAppearance[8]);
		playerProps.writeByte(playerAppearance[9]);
		playerProps.writeByte(playerAppearance[10]);
		playerProps.writeByte(playerAppearance[11]);
		playerProps.writeByte(playerAppearance[12]);
		playerProps.writeWord(playerStandIndex); // standAnimIndex
		playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
		playerProps.writeWord(playerWalkIndex); // walkAnimIndex
		playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
		playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
		playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
		playerProps.writeWord(playerRunIndex); // runAnimIndex
		playerProps.writeQWord(Misc.playerNameToInt64(playerName));
		combatLevel = calculateCombatLevel();
		playerProps.writeByte(combatLevel); // combat level
		playerProps.writeWord(0);
		str.writeByteC(playerProps.currentOffset);
		str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
	}

	public int calculateCombatLevel() {
		int j = getLevelForXP(playerXP[playerAttack]);
		int k = getLevelForXP(playerXP[playerDefence]);
		int l = getLevelForXP(playerXP[playerStrength]);
		int i1 = getLevelForXP(playerXP[playerHitpoints]);
		int j1 = getLevelForXP(playerXP[playerPrayer]);
		int k1 = getLevelForXP(playerXP[playerRanged]);
		int l1 = getLevelForXP(playerXP[playerMagic]);
		int combatLevel = (int) ((k + i1 + Math.floor(j1 / 2)) * 0.25D) + 1;
		double d = (j + l) * 0.32500000000000001D;
		double d1 = Math.floor(k1 * 1.5D) * 0.32500000000000001D;
		double d2 = Math.floor(l1 * 1.5D) * 0.32500000000000001D;
		if (d >= d1 && d >= d2) {
			combatLevel += d;
		} else if (d1 >= d && d1 >= d2) {
			combatLevel += d1;
		} else if (d2 >= d && d2 >= d1) {
			combatLevel += d2;
		}
		return combatLevel;
	}

	public int getLevelForXP(int exp) {
		if (exp > 13034430) {
			return 99;
		} else {
			int points = 0;
			for (int lvl = 1; lvl <= 99; ++lvl) {
				points = (int) (points + Math.floor(lvl + 300.0D
						* Math.pow(2.0D, lvl / 7.0D)));
				int var5 = (int) Math.floor(points / 4);
				if (var5 >= exp) {
					return lvl;
				}
			}

			return 99;
		}
	}

	private boolean chatTextUpdateRequired = false;
	private byte chatText[] = new byte[4096];
	private byte chatTextSize = 0;
	private int chatTextColor = 0;
	private int chatTextEffects = 0;

	protected void appendPlayerChatText(Stream str) {
		str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8) + (getChatTextEffects() & 0xFF));
		str.writeByte(playerRights);
		str.writeByteC(getChatTextSize());
		str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
	}

	public void forcedChat(String text) {
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}

	public String forcedText = "null";

	public void appendForcedChat(Stream str) {
		str.writeString(forcedText);
	}

	/**
	 * Graphics
	 **/

	public int mask100var1 = 0;
	public int mask100var2 = 0;
	protected boolean mask100update = false;

	public void appendMask100Update(Stream str) {
		str.writeWordBigEndian(mask100var1);
		str.writeDWord(mask100var2);
	}

	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		mask100update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 65536;
		mask100update = true;
		updateRequired = true;
	}

	public boolean wearing2h() {
		Client c = (Client) this;
		String s = ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h")) {
			return true;
		} else if (s.contains("godsword")) {
			return true;
		}
		return false;
	}

	/**
	 * Animations
	 **/
	public void startAnimation(int animId) {
		if (wearing2h() && animId == 829) {
			return;
		}
		if (animId == -1) {
			animId = 65535;
		}
		if (isBotting == true) {
			animId = 65535;
		}
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}

	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void appendAnimationRequest(Stream str) {
		str.writeWordBigEndian(animationRequest == -1 ? 65535
				: animationRequest);
		str.writeByteC(animationWaitCycles);
	}

	/**
	 * Face Update
	 **/

	protected boolean faceUpdateRequired = false;
	public int face = -1;
	public int FocusPointX = -1, FocusPointY = -1;

	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceUpdate(Stream str) {
		str.writeWordBigEndian(face);
	}

	public void turnPlayerTo(int pointX, int pointY) {
		FocusPointX = 2 * pointX + 1;
		FocusPointY = 2 * pointY + 1;
		updateRequired = true;
	}

	private void appendSetFocusDestination(Stream str) {
		str.writeWordBigEndianA(FocusPointX);
		str.writeWordBigEndian(FocusPointY);
	}

	/**
	 * Hit Update
	 **/

	protected void appendHitUpdate(Stream str) {
		str.writeByte(getHitDiff()); // What the perseon got 'hit' for
		if (poisonMask == 1) {
			str.writeByteA(2);
		} else if (getHitDiff() > 0) {
			str.writeByteA(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteA(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[3] <= 0) {
			playerLevel[3] = 0;
			isDead = true;
		}
		str.writeByteC(playerLevel[3]); // Their current hp, for HP bar
		str.writeByte(getLevelForXP(playerXP[3]));
	}

	protected void appendHitUpdate2(Stream str) {
		str.writeByte(hitDiff2); // What the perseon got 'hit' for
		if (poisonMask == 2) {
			str.writeByteS(2);
			poisonMask = -1;
		} else if (hitDiff2 > 0) {
			str.writeByteS(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteS(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[3] <= 0) {
			playerLevel[3] = 0;
			isDead = true;
		}
		str.writeByte(playerLevel[3]); // Their current hp, for HP bar
		str.writeByteC(getLevelForXP(playerXP[3])); // Their max hp, for HP
	}

	public void appendPlayerUpdateBlock(Stream str) {
		if (!updateRequired && !isChatTextUpdateRequired()) {
			return; // nothing required
		}
		int updateMask = 0;
		if (mask100update) {
			updateMask |= 0x100;
		}
		if (animationRequest != -1) {
			updateMask |= 8;
		}
		if (forcedChatUpdateRequired) {
			updateMask |= 4;
		}
		if (isChatTextUpdateRequired()) {
			updateMask |= 0x80;
		}
		if (isAppearanceUpdateRequired()) {
			updateMask |= 0x10;
		}
		if (faceUpdateRequired) {
			updateMask |= 1;
		}
		if (FocusPointX != -1) {
			updateMask |= 2;
		}
		if (isHitUpdateRequired()) {
			updateMask |= 0x20;
		}

		if (hitUpdateRequired2) {
			updateMask |= 0x200;
		}

		if (updateMask >= 0x100) {
			updateMask |= 0x40;
			str.writeByte(updateMask & 0xFF);
			str.writeByte(updateMask >> 8);
		} else {
			str.writeByte(updateMask);
		}

		// now writing the various update blocks itself - note that their
		// order crucial
		if (mask100update) {
			appendMask100Update(str);
		}
		if (animationRequest != -1) {
			appendAnimationRequest(str);
		}
		if (forcedChatUpdateRequired) {
			appendForcedChat(str);
		}
		if (isChatTextUpdateRequired()) {
			appendPlayerChatText(str);
		}
		if (faceUpdateRequired) {
			appendFaceUpdate(str);
		}
		if (isAppearanceUpdateRequired()) {
			appendPlayerAppearance(str);
		}
		if (FocusPointX != -1) {
			appendSetFocusDestination(str);
		}
		if (isHitUpdateRequired()) {
			appendHitUpdate(str);
		}
		if (hitUpdateRequired2) {
			appendHitUpdate2(str);
		}
	}

	public void clearUpdateFlags() {
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setHitUpdateRequired(false);
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		mask100update = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		poisonMask = -1;
		faceUpdateRequired = false;
		face = 65535;
	}

	public void stopMovement() {
		if (teleportToX <= 0 && teleportToY <= 0) {
			teleportToX = absX;
			teleportToY = absY;
		}
		newWalkCmdSteps = 0;
		getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
		getNextPlayerMovement();
	}

	private int newWalkCmdX[] = new int[walkingQueueSize];
	private int newWalkCmdY[] = new int[walkingQueueSize];
	public int newWalkCmdSteps = 0;
	private boolean newWalkCmdIsRunning = false;
	protected int travelBackX[] = new int[walkingQueueSize];
	protected int travelBackY[] = new int[walkingQueueSize];
	protected int numTravelBackSteps = 0;

	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public abstract void process();

	public abstract boolean processQueuedPackets();

	public void postProcessing() {
		if (newWalkCmdSteps > 0) {
			int firstX = getNewWalkCmdX()[0];
			int firstY = getNewWalkCmdY()[0];
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			if (dir != -1 && (dir & 1) != 0) {
				do {
					int var13 = dir;
					--ptr;
					if (ptr < 0) {
						ptr = 49;
					}

					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					dir = Misc.direction(walkingQueueX[ptr],
							walkingQueueY[ptr], firstX, firstY);
					if (var13 != dir) {
						found = true;
						break;
					}
				} while (ptr != wQueueWritePtr);
			} else {
				found = true;
			}

			if (found) {
				wQueueWritePtr = wQueueReadPtr;
				addToWalkingQueue(currentX, currentY);
				int i;
				if (dir != -1 && (dir & 1) != 0) {
					for (i = 0; i < numTravelBackSteps - 1; ++i) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}

					i = travelBackX[numTravelBackSteps - 1];
					int wayPointY2 = travelBackY[numTravelBackSteps - 1];
					int wayPointX1;
					int wayPointY1;
					if (numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					} else {
						wayPointX1 = travelBackX[numTravelBackSteps - 2];
						wayPointY1 = travelBackY[numTravelBackSteps - 2];
					}

					dir = Misc.direction(wayPointX1, wayPointY1, i, wayPointY2);
					if (dir != -1 && (dir & 1) == 0) {
						dir >>= 1;
						found = false;
						int x = wayPointX1;
						int y = wayPointY1;

						while (x != i || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
								found = true;
								break;
							}
						}

						if (!found) {
							println_debug("Fatal: Internal error: unable to determine connection vertex!  wp1=("
									+ wayPointX1
									+ ", "
									+ wayPointY1
									+ "), wp2=("
									+ i
									+ ", "
									+ wayPointY2
									+ "), "
									+ "first=("
									+ firstX
									+ ", "
									+ firstY + ")");
						} else {
							addToWalkingQueue(wayPointX1, wayPointY1);
						}
					} else {
						println_debug("Fatal: The walking queue is corrupt! wp1=("
								+ wayPointX1
								+ ", "
								+ wayPointY1
								+ "), "
								+ "wp2=(" + i + ", " + wayPointY2 + ")");
					}
				} else {
					for (i = 0; i < numTravelBackSteps; ++i) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				for (i = 0; i < newWalkCmdSteps; ++i) {
					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
				}
			}

			isRunning = isNewWalkCmdIsRunning() || isRunning2;
		}
	}

	public int getMapRegionX() {
		return mapRegionX;
	}

	public int getMapRegionY() {
		return mapRegionY;
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}

	public int getH() {
		return heightLevel;
	}

	public int getId() {
		return playerId;
	}

	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}

	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}

	public int getHitDiff() {
		return hitDiff;
	}

	public void setHitUpdateRequired(boolean hitUpdateRequired) {
		this.hitUpdateRequired = hitUpdateRequired;
	}

	public void setHitUpdateRequired2(boolean hitUpdateRequired2) {
		this.hitUpdateRequired2 = hitUpdateRequired2;
	}

	public boolean isHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired2() {
		return hitUpdateRequired2;
	}

	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}

	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}

	public int getChatTextEffects() {
		return chatTextEffects;
	}

	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}

	public byte getChatTextSize() {
		return chatTextSize;
	}

	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}

	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}

	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}

	public byte[] getChatText() {
		return chatText;
	}

	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}

	public int getChatTextColor() {
		return chatTextColor;
	}

	public void setNewWalkCmdX(int newWalkCmdX[]) {
		this.newWalkCmdX = newWalkCmdX;
	}

	public int[] getNewWalkCmdX() {
		return newWalkCmdX;
	}

	public void setNewWalkCmdY(int newWalkCmdY[]) {
		this.newWalkCmdY = newWalkCmdY;
	}

	public int[] getNewWalkCmdY() {
		return newWalkCmdY;
	}

	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
	}

	public boolean isNewWalkCmdIsRunning() {
		return newWalkCmdIsRunning;
	}

	public void setInStreamDecryption(ISAACRandomGen inStreamDecryption) {
	}

	public void setOutStreamDecryption(ISAACRandomGen outStreamDecryption) {
	}

	public boolean samePlayer() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId) {
				continue;
			}
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}
			}
		}
		return false;
	}

	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();
	}

	public void dealDamage(int damage) {
		if (teleTimer <= 0) {
			playerLevel[3] -= damage;
		} else {
			if (hitUpdateRequired) {
				hitUpdateRequired = false;
			}
			if (hitUpdateRequired2) {
				hitUpdateRequired2 = false;
			}
		}

	}

	public int[] damageTaken = new int[PlayerHandler.players.length];

	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;
		}
		updateRequired = true;
	}
}
