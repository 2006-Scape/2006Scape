package redone.game.content.skills.slayer;

import java.util.ArrayList;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class Slayer {

	public static final int
			VERY_EASY_TASK = 0,
			EASY_TASK = 1,
			MEDIUM_TASK = 2,
			HARD_TASK = 3,
			VERY_HARD_TASK = 4,
			VERY_EASY_AMOUNT = 15 + r(25),
			EASY_AMOUNT = 25 + r(25),
			MEDIUM_AMOUNT = 50 + r(25),
			HARD_AMOUNT = 100 + r(50),
			VERY_HARD_AMOUNT = 130 + r(70),
			DRAGON_AMOUNT = 10 + r(40);

	public static ArrayList<Integer> veryEasyTask = new ArrayList<Integer>();
	public static ArrayList<Integer> easyTask = new ArrayList<Integer>();
	public static ArrayList<Integer> mediumTask = new ArrayList<Integer>();
	public static ArrayList<Integer> hardTask = new ArrayList<Integer>();
	public static ArrayList<Integer> veryHardTask = new ArrayList<Integer>();

	private final Client c;

	public Slayer(Client c) {
		this.c = c;
	}

	public enum SlayerMasters {
		TURAEL(70, 1, VERY_EASY_TASK,"Taverly", "Turael"),
		MAZCHNA(1596, 20, EASY_TASK, "Canifis", "Mazchna"),
		VANNAKA(1597, 40, MEDIUM_TASK, "Edgeville", "Vannaka"),
		CHAELDAR(1598, 70, HARD_TASK, "Zanaris", "Chaeldar"),
		DURADEL(1599, 100, VERY_HARD_TASK, "Shilo Village", "Duradel");

		private int masterId, combatReq, diffuculty;
		private String masterLocation, masterName;

		private SlayerMasters(int masterId, int combatReq, int diffuculty, String masterLocation, String masterName) {
			this.masterId = masterId;
			this.combatReq = combatReq;
			this.diffuculty = diffuculty;
			this.masterLocation = masterLocation;
			this.masterName = masterName;
		}

		public int getId() {
			return masterId;
		}

		public int getCombatRequirement() {
			return combatReq;
		}

		public String getLocation() {
			return masterLocation;
		}

		public String getMaster() {
			return masterName;
		}

		public int getDifficulty() {
			return diffuculty;
		}

	}

	//dark beast, red dragon, skeleton
	public enum Task {
		ABERRANT_SPECTRE(1604, 60, 90, HARD_TASK, "Slayer Tower"),
		ABYSSAL_DEMON(1615, 85, 150, VERY_HARD_TASK, "Slayer Tower"),
		BANSHEE(1612, 15, 22, VERY_EASY_TASK + r(1), "Slayer Tower"),
		BASILISK(1616, 40, 75, MEDIUM_TASK, "Fremennik Slayer Dungeon"),
		BAT(412, 1, 8, EASY_TASK, "Road to Paterdomus"),
		BLACK_DEMON(84, 1, 157, HARD_TASK, "Taverly Dungeon"),
		BLACK_DRAGON(54, 1, 258, VERY_HARD_TASK, "Taverly Dungeon"),
		BLOODVELD(1618, 50, 120, HARD_TASK + r(1), "Slayer Tower"),
		BLUE_DRAGON(55, 1, 107, HARD_TASK, "Taverly Dungeon"),
		BRONZE_DRAGON(1590, 1, 125, HARD_TASK, "Brimhaven Dungeon"),
		CAVE_CRAWLER(1600, 10, 22, EASY_TASK, "Fremennik Slayer Dungeon"),
		COCKATRICE(1620, 25, 37, MEDIUM_TASK, "Fremennik Slayer Dungeon"),
		CRAWLING_HAND(1648, 5, 16 + r(3), EASY_TASK + r(1), "Slayer Tower"),
		DAGANNOTH_74(1338, 1, 70 + r(50), HARD_TASK, "Lighthouse Basement"),
		DAGANNOTH_92(1342, 1, 80 + r(50), HARD_TASK, "Lighthouse Basement"),
		DARK_BEAST(2783, 90, 180, VERY_HARD_TASK, "Slayer Tower"),
		DUST_DEVIL(1624, 65, 105, MEDIUM_TASK, "Slayer Tower"),
		EARTH_WARRIOR(124, 1, 54, MEDIUM_TASK, "Edgeville Dungeon"),
		FIRE_GIANT(110, 1, 111, HARD_TASK, "Brimhaven Dungeon"),
		GARGOYLE(1611, 75, 105, HARD_TASK, "Slayer Tower"),
		GHOST(103, 1, 25, EASY_TASK + r(1), "Taverly Dungeon"),
		GREATER_DEMON(83, 1, 87, HARD_TASK, "Brimhaven Dungeon"),
		GREEN_DRAGON(941, 1, 75, MEDIUM_TASK, "The Wilderness"),
		HELLHOUND(49, 1, 116, HARD_TASK + r(1),"Taverly Dungeon"),
		HILL_GIANT(117, 1, 35, MEDIUM_TASK, "Edgeville Dungeon"),
		ICE_GIANT(111, 1, 70, MEDIUM_TASK, "Asgarnian Ice Caves or White Wolf Mountain"),
		ICE_WARRIOR(125, 1, 59, MEDIUM_TASK, "Asgarnian Ice Caves or the Wilderness"),
		SKELETAL_WYVERN(3068, 72, 210, VERY_HARD_TASK, "Asgarnian Ice Caves"),
		INFERNAL_MAGE(1643, 45, 60, MEDIUM_TASK, "Slayer Tower"),
		IRON_DRAGON(1591, 1, 174, VERY_HARD_TASK, "Brimhaven Dungeon"),
		JELLY(1637, 52, 75, MEDIUM_TASK, "Fremennik Slayer Dungeon"),
		KALPHITE_WORKER(1156, 1, 40, EASY_TASK + r(1), "Kalphite Lair"),
		KALPHITE_SOLDIER(1154, 1, 90, HARD_TASK, "Kalphite Lair"),
		KALPHITE_GUARDIAN(1157, 1, 170, VERY_HARD_TASK, "Kalphite Lair"),
		KURASK(1608, 70, 97, HARD_TASK, "Fremennik Slayer Dungeon"),
		LESSER_DEMON(82, 1, 79, HARD_TASK, "Karamja Dungeon"),
		MOSS_GIANT(112, 1, 60, MEDIUM_TASK, "Brimhaven Dungeon"),
		NECHRYAELS(1613, 80, 1, HARD_TASK, "Slayer Tower"),
		PYREFIEND(1633, 30, 1, EASY_TASK, "Fremennik Slayer Dungeon"),
		RED_DRAGON(53, 1, 120, HARD_TASK, "Brimhaven Dungeon"),
		ROCKSLUG(1622, 20, 27, EASY_TASK, "Fremennik Slayer Dungeon"),
		SKELETON(90, 1, 30, VERY_EASY_TASK + r(2), "Edgeville Dungeon or Taverly Dungeon"),
		KARAMAJA_SKELETON(91, 1, 30, VERY_EASY_TASK + r(2), "Karamaja"),
		WILDERNESS_SKELETON(92, 1, 30, VERY_EASY_TASK + r(2), "Wilderness"),
		STEEL_DRAGON(1592, 1, 221, VERY_HARD_TASK, "Brimhaven Dungeon"),
		BEAR(105, 1, 27, VERY_EASY_TASK, "Goblin Village"),
		GREEN_GOBLIN(298, 1, 6, VERY_EASY_TASK, "Goblin Village"),
		RED_GOBLIN(299, 1, 6, VERY_EASY_TASK, "Goblin Village"),
		SCORPION(107, 1, 17, VERY_EASY_TASK, "Goblin Village"),
		TUROTH(1632, 55, 77, HARD_TASK, "Fremennik Slayer Dungeon");

		private int npcId, levelReq, diff, exp;
		private String location;

		Task(int npcId, int levelReq, int exp, int difficulty, String location) {
			this.npcId = npcId;
			this.exp = exp;
			this.levelReq = levelReq;
			this.location = location;
			this.diff = difficulty;
		}

		public int getNpcId() {
			return npcId;
		}
		
		public int getExp() {
			return exp;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getDifficulty() {
			return diff;
		}

		public String getLocation() {
			return location;
		}
	}
	
	public static int r(int random) {
		return Misc.random(random);
	}

	public boolean canAttackNpc(int i) {
		if (c.playerLevel[c.playerSlayer] < getRequiredLevel(NpcHandler.npcs[i].npcType)) {
			c.getActionSender().sendMessage("You need a slayer level of " + getRequiredLevel(NpcHandler.npcs[i].npcType) + " to attack this npc.");
			c.getCombatAssistant().resetPlayerAttack();
			return false;
		}
		return true;
	}

	public void resizeTable(int difficulty) {
		if (easyTask.size() > 0 || hardTask.size() > 0 || mediumTask.size() > 0
				|| veryHardTask.size() > 0 || veryEasyTask.size() > 0) {
			easyTask.clear();
			mediumTask.clear();
			hardTask.clear();
			veryHardTask.clear();
			veryEasyTask.clear();
		}
		for (Task slayerTask : Task.values()) {
			if (slayerTask.getDifficulty() == EASY_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					easyTask.add(slayerTask.getNpcId());
				}
				continue;
			} else if (slayerTask.getDifficulty() == VERY_EASY_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					veryEasyTask.add(slayerTask.getNpcId());
				}
				continue;
			} else if (slayerTask.getDifficulty() == MEDIUM_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					mediumTask.add(slayerTask.getNpcId());
				}
				continue;
			} else if (slayerTask.getDifficulty() == HARD_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					hardTask.add(slayerTask.getNpcId());
				}
				continue;
			} else if (slayerTask.getDifficulty() == VERY_HARD_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					veryHardTask.add(slayerTask.getNpcId());
				}
				continue;
			}
		}
	}
	
	public static boolean getMasterRequirment(Client c, int id) {
		for (SlayerMasters slayermasters : SlayerMasters.values()) {
			if (c.combatLevel < slayermasters.getCombatRequirement()
					&& slayermasters.getId() == id) {
				c.getActionSender().sendMessage("You need " + slayermasters.getCombatRequirement() + " combat to use this slayer master.");
				return false;
			}
		}
		return true;
	}
	
	public int getTaskExp(int npcId) {
		for (Task task: Task.values()) {
			if (task.npcId == npcId) {
				return task.exp;
			}
		}
		return -1;
	}

	public int getRequiredLevel(int npcId) {
		for (Task task : Task.values()) {
			if (task.npcId == npcId) {
				return task.levelReq;
			}
		}
		return -1;
	}

	public String getLocation(int npcId) {
		for (Task task : Task.values()) {
			if (task.npcId == npcId) {
				return task.location;
			}
		}
		return "";
	}

	public String getMasterLocation(int npcId) {
		for (SlayerMasters slayermasters : SlayerMasters.values()) {
			if (slayermasters.masterId == npcId) {
				return slayermasters.masterLocation;
			}
		}
		return "";
	}

	public boolean isSlayerNpc(int npcId) {
		for (Task task : Task.values()) {
			if (task.getNpcId() == npcId) {
				return true;
			}
		}
		return false;
	}

	public boolean isSlayerTask(int npcId) {
		if (isSlayerNpc(npcId)) {
			if (c.slayerTask == npcId) {
				return true;
			}
		}
		return false;
	}

	public int getDifficulty(int npcId) {
		for (Task task : Task.values()) {
			if (task.npcId == npcId) {
				return task.getDifficulty();
			}
		}
		return 1;
	}

	public String getSlayerMaster(int npcId) {
		for (SlayerMasters slayermasters : SlayerMasters.values()) {
			if (slayermasters.masterId == npcId) {
				return slayermasters.name().replaceAll("_", " ")
						.replaceAll("2", "").toLowerCase();
			}
		}
		return "";
	}

	public String getTaskName(int npcId) {
		for (Task task : Task.values()) {
			if (task.npcId == npcId) {
				return task.name().replaceAll("_", " ").replaceAll("2", "")
						.toLowerCase();
			}
		}
		return "";
	}

	public int getTaskId(String name) {
		for (Task task : Task.values()) {
			if (task.name() == name) {
				return task.npcId;
			}
		}
		return -1;
	}

	public boolean hasTask() {
		return c.slayerTask > 0 || c.taskAmount > 0;
	}

	public void generateTask() {
		if (hasTask() && !c.needsNewTask) {
			c.getDialogueHandler().sendDialogues(1226, c.npcType);// already have task
			return;
		}
		if (hasTask() && c.needsNewTask) {// assigning new task
			int difficulty = getDifficulty(c.slayerTask);
			if (difficulty == VERY_EASY_TASK) {
				c.getDialogueHandler().sendDialogues(1227, c.npcType);
				c.needsNewTask = false;
				return;
				/*
				 * } else if (difficulty != VERY_EASY_TASK && c.needsNewTask &&
				 * hasTask() && c.wantsEasyTask == true) { int taskLevel =
				 * VERY_EASY_TASK; int task = getRandomTask(taskLevel);
				 * c.slayerTask = task; c.taskAmount = getTaskAmount(taskLevel);
				 * c.getDialogues().handleDialogues(1368, c.npcType);
				 * c.getPacketDispatcher().sendMessage("You have been assigned "
				 * + c.taskAmount + " " + getTaskName(c.slayerTask) +
				 * ", good luck " + c.playerName + "."); c.needsNewTask = false;
				 */
			}
		}
		int taskLevel = getSlayerDifficulty(c);
		// System.out.println("EASY :" + easyTask + "\nMEDIUM: " + mediumTask+
		// "\nHARD: " + hardTask + "");
		for (Task slayerTask : Task.values()) {
			if (slayerTask.getDifficulty() == taskLevel) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					resizeTable(taskLevel);
					if (!c.needsNewTask) {
						int task = getRandomTask(taskLevel);
						for (int removedTask : c.removedTasks) {
							if (task == removedTask) {
								c.getActionSender().sendMessage("Unavailable task: " + task);
								generateTask();
								return;
							}
						}
						c.slayerTask = task;
						c.taskAmount = getTaskAmount(task);
					} else {
						int task = getRandomTask(getDifficulty(taskLevel - 1));
						for (int removedTask : c.removedTasks) {
							if (task == removedTask) {
								c.getActionSender().sendMessage("Unavailable task: " + task);
								generateTask();
								return;
							}
						}
						c.slayerTask = task;
						c.taskAmount = getTaskAmount(task);
						c.needsNewTask = false;
					}
					c.getDialogueHandler().sendDialogues(1237, c.npcType);// assign task
					c.getActionSender().sendMessage("You have been assigned " + c.taskAmount + " " + getTaskName(c.slayerTask) + ", good luck " + c.playerName + ".");
					return;
				}
			}
		}
	}

	public int getTaskAmount(int task_id) {
		Task task = null;
		for (Task _task : Task.values())
			if (_task.getNpcId() == task_id)
				task = _task;

		if (task == null) return VERY_EASY_AMOUNT;
		if (task.name().toLowerCase().contains("dragon"))
			return DRAGON_AMOUNT;
		switch (task.getDifficulty()) {
		case 0:
			return VERY_EASY_AMOUNT;
		case 1:
			return EASY_AMOUNT;
		case 2:
			return MEDIUM_AMOUNT;
		case 3:
			return HARD_AMOUNT;
		case 4:
			return VERY_HARD_AMOUNT;
		}
		return EASY_AMOUNT;
	}

	public int getSlayerDifficulty(Client c) {
		for(SlayerMasters master : SlayerMasters.values()){
			if (master.getId() == c.SlayerMaster)
				return master.getDifficulty();
		}
		return EASY_TASK;
	}

	public int getRandomTask(int diff) {
		if (diff == VERY_EASY_TASK) {
			return veryEasyTask.get(r(veryEasyTask.size() - 1));
		} else if (diff == EASY_TASK) {
			return easyTask.get(r(easyTask.size() - 1));
		} else if (diff == MEDIUM_TASK) {
			return mediumTask.get(r(mediumTask.size() - 1));
		} else if (diff == HARD_TASK) {
			return hardTask.get(r(hardTask.size() - 1));
		} else if (diff == VERY_HARD_TASK) {
			return veryHardTask.get(r(veryHardTask.size() - 1));
		}
		return easyTask.get(r(easyTask.size() - 1));
	}

	public void handleInterface(String shop) {
		if (shop.equalsIgnoreCase("buy")) {
			c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 41011);
			c.getPlayerAssistant().showInterface(41000);
		} else if (shop.equalsIgnoreCase("learn")) {
			c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 41511);
			c.getPlayerAssistant().showInterface(41500);
		} else if (shop.equalsIgnoreCase("assignment")) {
			c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 42011);
			updateCurrentlyRemoved();
			c.getPlayerAssistant().showInterface(42000);
		}
	}

	public void cancelTask() {
		if (!hasTask()) {
			c.getActionSender().sendMessage("You must have a task to cancel first.");
			return;
		}
		if (c.slayerPoints < 30) {
			c.getActionSender().sendMessage("This requires atleast 30 slayer points, which you don't have.");
			c.getDialogueHandler().sendNpcChat1("This requires atleast 30 slayer points, which you don't have.",
					c.npcType,
					NpcHandler.getNpcListName(c.talkingNpc));
			c.nextChat = 0;
			return;
		}
		c.getActionSender().sendMessage("You have cancelled your current task of " + c.taskAmount + " " + getTaskName(c.slayerTask) + ".");
		c.slayerTask = -1;
		c.taskAmount = 0;
		c.slayerPoints -= 30;
	}

	public void removeTask() {
		int counter = 0;
		if (!hasTask()) {
			c.getActionSender().sendMessage("You must have a task to remove first.");
			return;
		}
		if (c.slayerPoints < 100) {
			c.getActionSender().sendMessage("This requires atleast 100 slayer points, which you don't have.");
			c.getDialogueHandler().sendNpcChat1("This requires atleast 100 slayer points, which you don't have.",
					c.npcType,
					NpcHandler.getNpcListName(c.talkingNpc));
			c.nextChat = 0;
			return;
		}
		for (int i = 0; i < c.removedTasks.length; i++) {
			if (c.removedTasks[i] != -1) {
				counter++;
			}
			if (counter == 4) {
				c.getActionSender().sendMessage("You don't have any open slots left to remove tasks.");
				return;
			}
			if (c.removedTasks[i] == -1) {
				c.removedTasks[i] = c.slayerTask;
				c.slayerPoints -= 100;
				c.slayerTask = -1;
				c.taskAmount = 0;
				c.getActionSender().sendMessage("Your current slayer task has been removed, you can't obtain this task again.");
				updateCurrentlyRemoved();
				return;
			}
		}
	}

	public void updatePoints() {
		c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 41011);
		c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 41511);
		c.getPlayerAssistant().sendFrame126("Slayer Points: " + c.slayerPoints, 42011);
	}

	public void updateCurrentlyRemoved() {
		int line[] = { 42014, 42015, 42016, 42017 };
		for (int i = 0; i < c.removedTasks.length; i++) {
			if (c.removedTasks[i] != -1) {
				c.getPlayerAssistant().sendFrame126(
						getTaskName(c.removedTasks[i]), line[i]);
			} else {
				c.getPlayerAssistant().sendFrame126("", line[i]);
			}
		}
	}

	public void buySlayerExperience() {
		if (System.currentTimeMillis() - c.buySlayerTimer < 500) {
			return;
		}
		if (c.slayerPoints < 50) {
			c.getActionSender().sendMessage( "You need at least 50 slayer points to gain 32,500 Experience.");
			return;
		}
		c.buySlayerTimer = System.currentTimeMillis();
		c.slayerPoints -= 50;
		c.getPlayerAssistant().addSkillXP(32500, 18);
		c.getActionSender().sendMessage("You spend 50 slayer points and gain 32,500 experience in slayer.");
		updatePoints();
	}

	public void buySlayerDart() {
		if (System.currentTimeMillis() - c.buySlayerTimer < 500) {
			return;
		}
		if (c.slayerPoints < 35) {
			c.getActionSender().sendMessage("You need at least 35 slayer points to buy Slayer darts.");
			return;
		}
		if (c.getItemAssistant().freeSlots() < 2
				&& !c.getItemAssistant().playerHasItem(560)
				&& !c.getItemAssistant().playerHasItem(558)) {
			c.getActionSender().sendMessage("You need at least 2 free lots to purchase this.");
			return;
		}

		c.buySlayerTimer = System.currentTimeMillis();
		c.slayerPoints -= 35;
		c.getActionSender().sendMessage("You spend 35 slayer points and aquire 250 casts of Slayer darts.");
		c.getItemAssistant().addItem(558, 1000);
		c.getItemAssistant().addItem(560, 250);
		updatePoints();
	}

	public void buyBroadArrows() {
		if (System.currentTimeMillis() - c.buySlayerTimer < 500) {
			return;
		}
		if (c.slayerPoints < 25) {
			c.getActionSender().sendMessage("You need at least 25 slayer points to buy Broad arrows.");
			return;
		}
		if (c.getItemAssistant().freeSlots() < 1
				&& !c.getItemAssistant().playerHasItem(4160)) {
			c.getActionSender().sendMessage("You need at least 1 free lot to purchase this.");
			return;
		}
		c.buySlayerTimer = System.currentTimeMillis();
		c.slayerPoints -= 25;
		c.getActionSender().sendMessage("You spend 35 slayer points and aquire 250 Broad arrows.");
		c.getItemAssistant().addItem(4160, 250);
		updatePoints();
	}

	public void buyRespite() {
		if (System.currentTimeMillis() - c.buySlayerTimer < 1000) {
			return;
		}
		if (c.slayerPoints < 25) {
			c.getActionSender().sendMessage("You need at least 25 slayer points to buy Slayer's respite.");
			return;
		}
		c.buySlayerTimer = System.currentTimeMillis();
		c.slayerPoints -= 25;
		c.getActionSender().sendMessage("You spend 25 slayer points and aquire a useful Slayer's respite.");
		c.getItemAssistant().addItem(5841, 1);
		updatePoints();
	}

}
