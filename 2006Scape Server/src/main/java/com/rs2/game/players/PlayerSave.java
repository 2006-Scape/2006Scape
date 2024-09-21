package com.rs2.game.players;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import com.rs2.util.Misc;

public class PlayerSave {

	/**
	 * Loading
	 **/
	public static int loadGame(Client player, String playerName, String playerPass) {
		return loadPlayerInfo(player, playerName, playerPass, true);
	}

	public static int loadPlayerInfo(Client player, String playerName, String playerPass, boolean doRealLogin)	{
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;

		if (player.playerName == null) {
			System.out.println("WARNING: called loadPlayerInfo with a Client who does not have a .playerName");
		}
		try {
			characterfile = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/data/characters/" + player.playerName + ".txt"));
			File1 = true;
		} catch (FileNotFoundException fileex1) {
		}

		if (File1) {
			// new File ("./characters/"+playerName+".txt");
		} else {
			if (playerName.equals(""))
			{
				//it's the .gitignore :P
				return 0;
			}
			System.out.println(playerName + ": character file not found.");
			player.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			System.out.println(playerName + ": error loading file.");
			return 3;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t+");
				switch (ReadMode) {
					case 1:
					    if (!doRealLogin)
							break;
						if (token.equals("character-password")) {
							if (playerPass.equalsIgnoreCase(token2) || passwordHash(playerPass).equalsIgnoreCase(token2)) {
								player.playerPass = token2; //Valid password
							} else {
							    System.out.println("hash doesn't match: " + passwordHash(playerPass).toLowerCase());
							    System.out.println("currently is: " + passwordHash(token2).toLowerCase());
	 							return 3; //Invalid password
							}
						}
						break;
					case 2:
						switch (token) {
							case "character-height":
								player.heightLevel = Integer.parseInt(token2);
								player.teleHeight = Integer.parseInt(token2);
								break;
							case "character-posx":
								player.teleportToX = Integer.parseInt(token2) <= 0 ? player.lastX : Integer.parseInt(token2);
								break;
							case "character-posy":
								player.teleportToY = Integer.parseInt(token2) <= 0 ? player.lastY : Integer.parseInt(token2);
								break;
							case "character-rights":
								player.playerRights = Integer.parseInt(token2);
								break;
							case "xp-rate":
								player.xpRate = Integer.parseInt(token2);
							case "last-ip":
								player.lastConnectedFrom = token2;
								break;
							case "isBot":
								player.isBot = Boolean.parseBoolean(token2);
								break;
							case "hideYell":
								player.hideYell = Boolean.parseBoolean(token2);
								break;
							case "blackMarks":
								player.blackMarks = Integer.parseInt(token2);
								break;
							case "lostCannon":
								player.lostCannon = Boolean.parseBoolean(token2);
								break;
							case "myBalls":
								player.getCannon().myBalls = Integer.parseInt(token2);
								break;
							case "ratsCaught":
								player.ratsCaught = Integer.parseInt(token2);
								break;
							case "cannonX":
								player.cannonX = Integer.parseInt(token2);
								break;
							case "cannonY":
								player.cannonY = Integer.parseInt(token2);
								break;
							case "removedTask0":
								player.removedTasks[0] = Integer.parseInt(token2);
								break;
							case "removedTask1":
								player.removedTasks[1] = Integer.parseInt(token2);
								break;
							case "removedTask2":
								player.removedTasks[2] = Integer.parseInt(token2);
								break;
							case "removedTask3":
								player.removedTasks[3] = Integer.parseInt(token2);
								break;
							case "SlayerMaster":
								player.SlayerMaster = Integer.parseInt(token2);
								break;
							case "slayerTask":
								player.slayerTask = Integer.parseInt(token2);
								break;
							case "slayerPoints":
								player.slayerPoints = Integer.parseInt(token2);
								break;
							case "taskAmount":
								player.taskAmount = Integer.parseInt(token2);
								break;
							case "cw-games":
								player.cwGames = Integer.parseInt(token2);
								break;
							case "crystal-bow-shots":
								player.crystalBowArrowCount = Integer.parseInt(token2);
								break;
							case "randomActions":
								player.randomActions = Integer.parseInt(token2);
								break;
							case "debugMode":
								player.debugMode = Boolean.parseBoolean(token2);
								break;
							case "global-damage":
								player.globalDamageDealt = Integer.parseInt(token2);
								break;
							case "skull-timer":
								player.skullTimer = Integer.parseInt(token2);
								break;
							case "recoilHits":
								player.recoilHits = Integer.parseInt(token2);
								break;
							case "brightness":
								player.brightness = Integer.parseInt(token2);
								break;
							case "spiritTree":
								player.spiritTree = Boolean.parseBoolean(token2);
								break;
							case "npcCanAttack":
								player.npcCanAttack = Boolean.parseBoolean(token2);
								break;
							case "rope":
								player.rope = Boolean.parseBoolean(token2);
								break;
							case "rope2":
								player.rope2 = Boolean.parseBoolean(token2);
								break;
							case "recievedMask":
								player.recievedMask = Boolean.parseBoolean(token2);
								break;
							case "recievedReward":
								player.recievedReward = Boolean.parseBoolean(token2);
								break;
							case "splitChat":
								player.splitChat = Boolean.parseBoolean(token2);
								break;
							case "hasPaid":
								player.hasPaid = Boolean.parseBoolean(token2);
								break;
							case "poison":
								player.poison = Boolean.parseBoolean(token2);
								break;
							case "closeTutorialInterface":
								player.closeTutorialInterface = Boolean
										.parseBoolean(token2);
								break;
							case "canWalkTutorial":
								player.canWalkTutorial = Boolean.parseBoolean(token2);
								break;
							case "needsNewTask":
								player.needsNewTask = Boolean.parseBoolean(token2);
								break;
							case "musicOn":
								player.musicOn = Boolean.parseBoolean(token2);
								break;
							case "soundOn":
								player.soundOn = Boolean.parseBoolean(token2);
								break;
							case "barrowsNpcs":
								player.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
								break;
							case "summonId":
								player.summonId = Integer.parseInt(token2);
								break;
							case "has-npc":
								player.hasNpc = Boolean.parseBoolean(token2);
								break;
							case "barrowsKillCount":
								player.barrowsKillCount = Integer.parseInt(token2);
								break;
							case "luthas":
								player.luthas = Boolean.parseBoolean(token2);
								break;
							case "village":
								player.village = Boolean.parseBoolean(token2);
								break;
							case "lastThieve":
								player.lastThieve = Long.parseLong(token2);
								break;
							case "homeTele":
								player.homeTele = Long.parseLong(token2);
								break;
							case "tutorial-progress":
								player.tutorialProgress = Integer.parseInt(token2);
								break;
							case "strongHold":
								player.strongHold = Boolean.parseBoolean(token2);
								break;
							case "filter":
								player.filter = Boolean.parseBoolean(token2);
								break;
							case "ratdied2":
								player.ratdied2 = Boolean.parseBoolean(token2);
								break;
							case "randomToggle":
								player.randomEventsEnabled = Boolean.parseBoolean(token2);
								break;
							case "questStages":
								player.questStages = Integer.parseInt(token2);
								break;
							case "cookAss":
								player.cookAss = Integer.parseInt(token2);
								break;
							case "bananas":
								player.bananas = Integer.parseInt(token2);
								break;
							case "sheepShear":
								player.sheepShear = Integer.parseInt(token2);
								break;
							case "runeMist":
								player.runeMist = Integer.parseInt(token2);
								break;
							case "doricQuest":
								player.doricQuest = Integer.parseInt(token2);
								break;
							case "blackKnight":
								player.blackKnight = Integer.parseInt(token2);
								break;
							case "shieldArrav":
								player.shieldArrav = Integer.parseInt(token2);
								break;
							case "pirateTreasure":
								player.pirateTreasure = Integer.parseInt(token2);
								break;
							case "romeo-juliet":
								player.romeojuliet = Integer.parseInt(token2);
								break;
							case "vampSlayer":
								player.vampSlayer = Integer.parseInt(token2);
								break;
							case "gertCat":
								player.gertCat = Integer.parseInt(token2);
								break;
							case "witchspot":
								player.witchspot = Integer.parseInt(token2);
								break;
							case "lostCity":
								player.lostCity = Integer.parseInt(token2);
								break;
							case "ectoTokens":
								player.ectoTokens = Integer.parseInt(token2);
								break;
							case "easterEvent":
								player.easterEvent = Integer.parseInt(token2);
								break;
							case "restGhost":
								player.restGhost = Integer.parseInt(token2);
								break;
							case "impsC":
								player.impsC = Integer.parseInt(token2);
								break;
							case "knightS":
								player.knightS = Integer.parseInt(token2);
								break;
							case "lastX":
								player.lastX = Integer.parseInt(token2);
								break;
							case "lastY":
								player.lastY = Integer.parseInt(token2);
								break;
							case "lastH":
								player.lastH = Integer.parseInt(token2);
								break;
							case "hasStarter":
								player.hasStarter = Boolean.parseBoolean(token2);
								break;
							case "canSpeak":
								player.canSpeak = Boolean.parseBoolean(token2);
								break;
							case "questPoints":
								player.questPoints = Integer.parseInt(token2);
								break;
							case "votePoints":
								player.votePoints = Integer.parseInt(token2);
								break;
							case "magic-book":
								player.playerMagicBook = Integer.parseInt(token2);
								break;
							case "special-amount":
								player.specAmount = Double.parseDouble(token2);
								break;
							case "selected-coffin":
								player.randomCoffin = Integer.parseInt(token2);
								break;
							case "isRunning":
								player.isRunning2 = Boolean.parseBoolean(token2);
								break;
							case "character-energy":
								player.playerEnergy = Integer.parseInt(token2);
								break;
							case "teleblock-length":
								player.teleBlockDelay = System.currentTimeMillis();
								player.teleBlockLength = Integer.parseInt(token2);
								break;
							case "lastYell":
								player.lastYell = Long.parseLong(token2);
								break;
							case "pc-points":
								player.pcPoints = Integer.parseInt(token2);
								break;
							case "magePoints":
								player.magePoints = Integer.parseInt(token2);
								break;
							case "autoRet":
								player.autoRet = Integer.parseInt(token2);
								break;
							case "flagged":
								player.accountFlagged = Boolean.parseBoolean(token2);
								break;
							case "lastLoginDate":
								player.lastLoginDate = Integer.parseInt(token2);
								break;
							case "hasBankpin":
								player.hasBankpin = Boolean.parseBoolean(token2);
								break;
							case "setPin":
								player.setPin = Boolean.parseBoolean(token2);
								break;
							case "pinRegisteredDeleteDay":
								player.pinDeleteDateRequested = Integer.parseInt(token2);
								break;
							case "requestPinDelete":
								player.requestPinDelete = Boolean.parseBoolean(token2);
								break;
							case "bankPin1":
								player.bankPin1 = Integer.parseInt(token2);
								break;
							case "bankPin2":
								player.bankPin2 = Integer.parseInt(token2);
								break;
							case "bankPin3":
								player.bankPin3 = Integer.parseInt(token2);
								break;
							case "bankPin4":
								player.bankPin4 = Integer.parseInt(token2);
								break;
							case "wave":
								player.waveId = Integer.parseInt(token2);
								break;
							case "ptjob":
								player.ptjob = Integer.parseInt(token2);
								break;
							case "creationAddress":
								player.creationAddress = token2;
								break;
							case "music":
								for (int j = 0; j < token3.length; j++) {
									player.getPlayList().unlocked[j] = Boolean.parseBoolean(token3[j]);
								}
								break;
							case "void":
								for (int j = 0; j < token3.length; j++) {
									player.voidStatus[j] = Integer.parseInt(token3[j]);
								}
								break;
							case "gwkc":
								player.killCount = Integer.parseInt(token2);
								break;
							case "fightMode":
								player.fightMode = Integer.parseInt(token2);
								break;
							case "ectofuntusWorshipped":
								player.ectofuntusWorshipped = Integer.parseInt(token2);
								break;
							case "graveyard-points":
								player.graveyardPoints = Integer.parseInt(token2);
								break;
							case "alchemy-points":
								player.alchemyPoints = Integer.parseInt(token2);
								break;
							case "enchantment-points":
								player.enchantmentPoints = Integer.parseInt(token2);
								break;
							case "telekinetic-points":
								player.telekineticPoints = Integer.parseInt(token2);
								break;
							case "telekinetic-mazes-solved":
								player.telekineticMazesSolved = Integer.parseInt(token2);
								break;
							case "unlocked-bones-to-peaches":
								player.unlockedBonesToPeaches = Boolean.parseBoolean(token2);
								break;
							case "discord-user-id":
								player.discordCode = token2;
								break;
							case "display-boss-kc-messages":
								player.displayBossKcMessages = Boolean.parseBoolean(token2);	
								break;
							case "display-slayer-kc-messages":
								player.displaySlayerKcMessages = Boolean.parseBoolean(token2);	
								break;
							case "display-regular-kc-messages":
								player.displayRegularKcMessages = Boolean.parseBoolean(token2);	
								break;
						}
						break;
					case 3:
						if (token.equals("character-equip")) {
							player.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 4:
						if (token.equals("character-look")) {
							player.playerAppearance[Integer.parseInt(token3[0])] = Integer	.parseInt(token3[1]);
						}
						break;
					case 5:
						if (token.equals("character-skill")) {
							player.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 6:
						if (token.equals("character-item")) {
							player.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 7:
						if (token.equals("character-bank")) {
							player.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
							player.bankItemsV[Integer.parseInt(token3[0])] = token3.length > 3 ? Integer.parseInt(token3[3]) : 1;
						}
						break;
					case 8:
						if (token.equals("character-friend")) {
							player.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
						}
						break;
					case 9:
						if (token.equals("character-ignore")) {
							player.ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
						}
					case 10:
						 if (token.startsWith("npcid-")) {
							try {
								int npcId = Integer.parseInt(token.substring(6));
								int killCount = Integer.parseInt(token2);
								player.incrementNpcKillCount(npcId, killCount);
							} catch (NumberFormatException e) {
								System.out.println("Error parsing NPC kill count for " + token);
							}
						}
						break;
				}
			} else {
				switch (line) {
					case "[ACCOUNT]":
						ReadMode = 1;
						break;
					case "[CHARACTER]":
						ReadMode = 2;
						break;
					case "[EQUIPMENT]":
						ReadMode = 3;
						break;
					case "[LOOK]":
						ReadMode = 4;
						break;
					case "[SKILLS]":
						ReadMode = 5;
						break;
					case "[ITEMS]":
						ReadMode = 6;
						break;
					case "[BANK]":
						ReadMode = 7;
						break;
					case "[FRIENDS]":
						ReadMode = 8;
						break;
					case "[IGNORES]":
						ReadMode = 9;
						break;
					case "[NPC-KILLS]":
						ReadMode = 10;
						break;
					case "[EOF]":
						try {
							characterfile.close();
						} catch (IOException ignored) {
						}
						return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ignored) {
		}
		if (doRealLogin)
			return 13;
		else
			return 14;
	}

	public static String passwordHash(String token2) {
	    String hashed = "HAS HAS FAILED!";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(token2.getBytes(StandardCharsets.UTF_8));

			hashed = Base64.getEncoder().encodeToString(hash);

			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(hashed.getBytes(StandardCharsets.UTF_8));

			hashed = Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashed;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Player player) {
		if (!player.saveFile || player.newPlayer || !player.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (player.playerName == null
				|| PlayerHandler.players[player.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		player.playerName = player.playerName2;
		int tbTime = (int) (player.teleBlockDelay - System.currentTimeMillis() + player.teleBlockLength);
		if (tbTime > 300000 || tbTime < 0) {
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			String filePath = System.getProperty("user.dir") + "/data/characters/" + player.playerName + ".txt";
			new File(filePath).getParentFile().mkdir();
			characterfile = new BufferedWriter(new FileWriter(filePath));

			/* ACCOUNT */
			characterfile.write("[ACCOUNT]");
			characterfile.newLine();
			characterfile.write("character-username = " + player.playerName);
			characterfile.newLine();
			if (player.playerPass.length() < 40) {
				player.playerPass = passwordHash(player.playerPass);
			}
			characterfile.write("character-password = " + player.playerPass);
			characterfile.newLine();
			characterfile.newLine();

			/* CHARACTER */
			characterfile.write("[CHARACTER]");
			characterfile.newLine();
			characterfile.write("character-height = " + player.heightLevel);
			characterfile.newLine();
			characterfile.write("character-posx = " + player.absX);
			characterfile.newLine();
			characterfile.write("character-posy = " + player.absY);
			characterfile.newLine();
			characterfile.write("character-rights = " + player.playerRights);
			characterfile.newLine();
			characterfile.write("xp-rate = " + player.xpRate);
			characterfile.newLine();
			characterfile.write("last-ip = " + player.connectedFrom);
			characterfile.newLine();
			characterfile.write("isBot = " + player.isBot);
			characterfile.newLine();
			characterfile.write("hideYell = " + player.hideYell);
			characterfile.newLine();
			characterfile.write("hasStarter = " + player.hasStarter);
			characterfile.newLine();
			characterfile.write("bankPin1 = " + player.bankPin1);
			characterfile.newLine();
			characterfile.write("bankPin2 = " + player.bankPin2);
			characterfile.newLine();
			characterfile.write("bankPin3 = " + player.bankPin3);
			characterfile.newLine();
			characterfile.write("bankPin4 = " + player.bankPin4);
			characterfile.newLine();
			characterfile.write("hasBankpin = " + player.hasBankpin);
			characterfile.newLine();
			characterfile.write("pinRegisteredDeleteDay = " + player.pinDeleteDateRequested);
			characterfile.newLine();
			characterfile.write("requestPinDelete = " + player.requestPinDelete);
			characterfile.newLine();
			characterfile.write("lastLoginDate = " + player.lastLoginDate);
			characterfile.newLine();
			characterfile.write("setPin = " + player.setPin);
			characterfile.newLine();
			characterfile.write("hasPaid = " + player.hasPaid);
			characterfile.newLine();
			characterfile.write("lostCannon = " + player.lostCannon);
			characterfile.newLine();
			characterfile.write("ratsCaught = " + player.ratsCaught);
			characterfile.newLine();
			characterfile.write("cannonX = " + player.cannonX);
			characterfile.newLine();
			characterfile.write("cannonY = " + player.cannonY);
			characterfile.newLine();
			characterfile.write("myBalls = " + player.getCannon().myBalls);
			characterfile.newLine();
			characterfile.write("poison = " + player.poison);
			characterfile.newLine();
			characterfile.write("spiritTree = " + player.spiritTree);
			characterfile.newLine();
			characterfile.write("npcCanAttack = " + player.npcCanAttack);
			characterfile.newLine();
			characterfile.write("rope = " + player.rope);
			characterfile.newLine();
			characterfile.write("rope2 = " + player.rope2);
			characterfile.newLine();
			characterfile.write("recievedMask = " + player.recievedMask);
			characterfile.newLine();
			characterfile.write("recievedReward = " + player.recievedReward);
			characterfile.newLine();
			characterfile.write("global-damage = " + player.globalDamageDealt);
			characterfile.newLine();
			characterfile.write("brightness = " + player.brightness);
			characterfile.newLine();
			characterfile.write("closeTutorialInterface = " + player.closeTutorialInterface);
			characterfile.newLine();
			characterfile.write("canWalkTutorial = " + player.canWalkTutorial);
			characterfile.newLine();
			characterfile.write("village = " + player.village);
			characterfile.newLine();
			characterfile.write("lastThieve = " + player.lastThieve);
			characterfile.newLine();
			characterfile.write("homeTele = " + player.homeTele);
			characterfile.newLine();
			characterfile.write("strongHold = " + player.strongHold);
			characterfile.newLine();
			characterfile.write("character-energy = " + (int) Math.ceil(player.playerEnergy));
			characterfile.newLine();
			characterfile.write("crystal-bow-shots = " + player.crystalBowArrowCount);
			characterfile.newLine();
			characterfile.write("splitChat = " + player.splitChat);
			characterfile.newLine();
			characterfile.write("canSpeak = " + player.canSpeak);
			characterfile.newLine();
			for (int b = 0; b < player.barrowsNpcs.length; b++) {
				characterfile.write("barrowsNpcs = " + b + "\t" + Math.max(0, player.barrowsNpcs[b][1]));
				characterfile.newLine();
			}
			characterfile.write("questStages = " + player.questStages);
			characterfile.newLine();
			characterfile.write("SlayerMaster = " + player.SlayerMaster);
			characterfile.newLine();
			String music = "";
			for (boolean element : player.getPlayList().unlocked) {
				music += element + "\t";
			}
			characterfile.write("music = " + music.trim());
			characterfile.newLine();
			characterfile.write("randomActions = " + player.randomActions);
			characterfile.newLine();
			characterfile.write("blackMarks = " + player.blackMarks);
			characterfile.newLine();
			characterfile.write("tutorial-progress = " + player.tutorialProgress);
			characterfile.newLine();
			characterfile.write("skull-timer = " + player.skullTimer);
			characterfile.newLine();
			characterfile.write("recoilHits = " + player.recoilHits);
			characterfile.newLine();
			characterfile.write("lastX = " + player.lastX);
			characterfile.newLine();
			characterfile.write("lastY = " + player.lastY);
			characterfile.newLine();
			characterfile.write("lastH = " + player.lastH);
			characterfile.newLine();
			for (int i = 0; i < player.removedTasks.length; i++) {
				characterfile.write("removedTask" + i + " = " + player.removedTasks[i]);
				characterfile.newLine();
			}
			characterfile.write("creationAddress = " + player.creationAddress);
			characterfile.newLine();
			characterfile.write("has-npc = " + player.hasNpc);
			characterfile.newLine();
			characterfile.write("summonId = " + player.summonId);
			characterfile.newLine();
			characterfile.write("questPoints = " + player.questPoints);
			characterfile.newLine();
			characterfile.write("votePoints = " + player.votePoints);
			characterfile.newLine();
			characterfile.write("bananas = " + player.bananas);
			characterfile.newLine();
			characterfile.write("magic-book = " + player.playerMagicBook);
			characterfile.newLine();
			characterfile.write("special-amount = " + player.specAmount);
			characterfile.newLine();
			characterfile.write("musicOn = " + player.musicOn);
			characterfile.newLine();
			characterfile.write("soundOn = " + player.soundOn);
			characterfile.newLine();
			characterfile.write("needsNewTask = " + player.needsNewTask);
			characterfile.newLine();
			characterfile.write("luthas = " + player.luthas);
			characterfile.newLine();
			characterfile.write("selected-coffin = " + player.randomCoffin);
			characterfile.newLine();
			characterfile.write("runeMist = " + player.runeMist);
			characterfile.newLine();
			characterfile.write("blackKnight = " + player.blackKnight);
			characterfile.newLine();
			characterfile.write("shieldArrav = " + player.shieldArrav);
			characterfile.newLine();
			characterfile.write("cookAss = " + player.cookAss);
			characterfile.newLine();
			characterfile.write("pirateTreasure = " + player.pirateTreasure);
			characterfile.newLine();
			characterfile.write("ptjob = " + player.ptjob);
			characterfile.newLine();
			characterfile.write("doricQuest = " + player.doricQuest);
			characterfile.newLine();
			characterfile.write("impsC = " + player.impsC);
			characterfile.newLine();
			characterfile.write("knightS = " + player.knightS);
			characterfile.newLine();
			characterfile.write("sheepShear = " + player.sheepShear);
			characterfile.newLine();
			characterfile.write("romeo-juliet = " + player.romeojuliet);
			characterfile.newLine();
			characterfile.write("gertCat = " + player.gertCat);
			characterfile.newLine();
			characterfile.write("lostCity = " + player.lostCity);
			characterfile.newLine();
			characterfile.write("ectoTokens = " + player.ectoTokens);
			characterfile.newLine();
			characterfile.write("easterEvent = " + player.easterEvent);
			characterfile.newLine();
			characterfile.write("cw-games = " + player.cwGames);
			characterfile.newLine();
			characterfile.write("witchspot = " + player.witchspot);
			characterfile.newLine();
			characterfile.write("restGhost = " + player.restGhost);
			characterfile.newLine();
			characterfile.write("vampSlayer = " + player.vampSlayer);
			characterfile.newLine();
			characterfile.write("RatDied2 = " + player.ratdied2);
			characterfile.newLine();
			characterfile.write("debugMode = " + player.debugMode);
			characterfile.newLine();
			characterfile.write("randomToggle = " + player.randomEventsEnabled);
			characterfile.newLine();
			characterfile.write("teleblock-length = " + tbTime);
			characterfile.newLine();
			characterfile.write("pc-points = " + player.pcPoints);
			characterfile.newLine();
			characterfile.write("lastYell = " + player.lastYell);
			characterfile.newLine();
			characterfile.write("slayerTask = " + player.slayerTask);
			characterfile.newLine();
			characterfile.write("taskAmount = " + player.taskAmount);
			characterfile.newLine();
			characterfile.write("magePoints = " + player.magePoints);
			characterfile.newLine();
			characterfile.write("autoRet = " + player.autoRet);
			characterfile.newLine();
			characterfile.write("barrowsKillCount = " + player.barrowsKillCount);
			characterfile.newLine();
			characterfile.write("slayerPoints = " + player.slayerPoints);
			characterfile.newLine();
			characterfile.write("flagged = " + player.accountFlagged);
			characterfile.newLine();
			characterfile.write("wave = " + player.waveId);
			characterfile.newLine();
			characterfile.write("gwkc = " + player.killCount);
			characterfile.newLine();
			characterfile.write("isRunning = " + player.isRunning2);
			characterfile.newLine();
			characterfile.write("fightMode = " + player.fightMode);
			characterfile.newLine();
			characterfile.write("ectofuntusWorshipped = " + player.ectofuntusWorshipped);
			characterfile.newLine();
			characterfile.write("graveyard-points = " + player.graveyardPoints);
			characterfile.newLine();
			characterfile.write("alchemy-points = " + player.alchemyPoints);
			characterfile.newLine();
			characterfile.write("enchantment-points = " + player.enchantmentPoints);
			characterfile.newLine();
			characterfile.write("telekinetic-points = " + player.telekineticPoints);
			characterfile.newLine();
			characterfile.write("telekinetic-mazes-solved = " + player.telekineticMazesSolved);
			characterfile.newLine();
			characterfile.write("unlocked-bones-to-peaches = " + player.unlockedBonesToPeaches);
			characterfile.newLine();
			String voidStatus = "";
			for (int voidS : player.voidStatus){
				voidStatus += voidS + "\t";
			}
			characterfile.write("void = " + voidStatus.trim());
			characterfile.newLine();
			characterfile.write("discord-user-id = " + player.discordCode);
			characterfile.newLine();
			characterfile.write("display-boss-kc-messages = " + player.displayBossKcMessages);
			characterfile.newLine();
			characterfile.write("display-slayer-kc-messages = " + player.displaySlayerKcMessages);
			characterfile.newLine();
			characterfile.write("display-regular-kc-messages = " + player.displayRegularKcMessages);
			characterfile.newLine();
			characterfile.newLine();

			/* EQUIPMENT */
			characterfile.write("[EQUIPMENT]");
			characterfile.newLine();
			for (int i = 0; i < player.playerEquipment.length; i++) {
				characterfile.write("character-equip = " + i + "\t" + player.playerEquipment[i] + "\t" + player.playerEquipmentN[i]);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* LOOK */
			characterfile.write("[LOOK]");
			characterfile.newLine();
			for (int i = 0; i < player.playerAppearance.length; i++) {
				characterfile.write("character-look = " + i + "\t" + player.playerAppearance[i]);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* SKILLS */
			characterfile.write("[SKILLS]");
			characterfile.newLine();
			for (int i = 0; i < player.playerLevel.length; i++) {
				characterfile.write("character-skill = " + i + "\t" + player.playerLevel[i] + "\t" + player.playerXP[i]);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* ITEMS */
			characterfile.write("[ITEMS]");
			characterfile.newLine();
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] > 0) {
					characterfile.write("character-item = " + i + "\t" + player.playerItems[i] + "\t" + player.playerItemsN[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* BANK */
			characterfile.write("[BANK]");
			characterfile.newLine();
			for (int i = 0; i < player.bankItems.length; i++) {
				if (player.bankItems[i] > 0) {
					characterfile.write("character-bank = " + i + "\t" + player.bankItems[i] + "\t" + player.bankItemsN[i] + (player.isBot ? "\t" + player.bankItemsV[i] : ""));
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* FRIENDS */
			characterfile.write("[FRIENDS]");
			characterfile.newLine();
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] > 0) {
					characterfile.write("character-friend = " + i + "\t" + player.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			characterfile.write("[IGNORES]");
			characterfile.newLine();
			for (int i = 0; i < player.ignores.length; i++) {
				if (player.ignores[i] > 0) {
					characterfile.write("character-ignore = " + i + "\t" + player.ignores[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
			characterfile.write("[NPC-KILLS]");
			characterfile.newLine();
			for (Map.Entry<Integer, Integer> entry : player.getNpcKillCounts().entrySet()) {
				characterfile.write("npcid-" + entry.getKey() + " = " + entry.getValue());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/* EOF */
			characterfile.write("[EOF]");
			characterfile.newLine();
			characterfile.close();
		} catch (IOException ioexception) {
			System.out.println(player.playerName + ": error writing file.");
			return false;
		}
		return true;
	}

}
