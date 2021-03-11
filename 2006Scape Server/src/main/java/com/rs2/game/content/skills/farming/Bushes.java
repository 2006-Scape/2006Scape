/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs2.game.content.skills.farming;

/**
 *
 * @author ArrowzFtw
 */

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.skills.SkillConstants;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * Created by IntelliJ IDEA. User: vayken Date: 24/02/12 Time: 20:34 To change
 * this template use File | Settings | File Templates.
 */
public class Bushes {

	private Player player;

	// set of global constants for Farming

	private static final double COMPOST_CHANCE = 0.9;
	private static final double SUPERCOMPOST_CHANCE = 0.7;
	private static final double CLEARING_EXPERIENCE = 4;

	public Bushes(Player player) {
		this.player = player;
	}

	// Farming data
	public int[] bushesStages = new int[4];
	public int[] bushesSeeds = new int[4];
	public int[] bushesState = new int[4];
	public long[] bushesTimer = new long[4];
	public double[] diseaseChance = { 1, 1, 1, 1 };
	public boolean[] hasFullyGrown = { false, false, false, false };
	public boolean[] bushesWatched = { false, false, false, false };

	/* set of the constants for the patch */

	// states - 2 bits plant - 6 bits
	public static final int GROWING = 0x00;
	public static final int DISEASED = 0x01;
	public static final int DEAD = 0x02;
	public static final int CHECK = 0x03;

	public static final int MAIN_BUSHES_CONFIG = 509;

	/* This is the enum holding the seeds info */

	public enum BushesData {
		REDBERRY(5101, 1951, 1, 10, new int[] { 5478, 4 }, 100, 0.20, 11.5,
				4.5, 0x05, 0x0e, 0x09, 0x3a, 64), CADAVABERRY(5102, 753, 1, 22,
				new int[] { 5968, 3 }, 140, 0.20, 18, 7, 0x0f, 0x19, 0x14,
				0x3b, 102.5), DWELLBERRY(5103, 2126, 1, 36,
				new int[] { 5406, 3 }, 140, 0.20, 31.5, 12, 0x1a, 0x25, 0x20,
				0x3c, 177.5), JANGERBERRY(5104, 247, 1, 48,
				new int[] { 5982, 6 }, 160, 0.20, 50.5, 19, 0x26, 0x32, 0x2d,
				0x3d, 284.5), WHITEBERRY(5105, 239, 1, 59,
				new int[] { 6004, 8 }, 160, 0.20, 78, 29, 0x33, 0x3f, 0x3a,
				0x3e, 437.5), POISONIVYBERRY(5106, 6018, 1, 70, null, 160,
				0.20, 120, 45, 0xc5, 0xd1, 0xcc, 0x3f, 674);

		private int seedId;
		private int harvestId;
		private int seedAmount;
		private int levelRequired;
		private int[] paymentToWatch;
		private int growthTime;
		private double diseaseChance;
		private double plantingXp;
		private double harvestXp;
		private int startingState;
		private int endingState;
		private int limitState;
		private int checkHealthState;
		private double checkHealthExperience;

		private static Map<Integer, BushesData> seeds = new HashMap<Integer, BushesData>();

		static {
			for (BushesData data : BushesData.values()) {
				seeds.put(data.seedId, data);
			}
		}

		BushesData(int seedId, int harvestId, int seedAmount,
				int levelRequired, int[] paymentToWatch, int growthTime,
				double diseaseChance, double plantingXp, double harvestXp,
				int startingState, int endingState, int limitState,
				int checkHealthState, double checkHealthExperience) {
			this.seedId = seedId;
			this.harvestId = harvestId;
			this.seedAmount = seedAmount;
			this.levelRequired = levelRequired;
			this.paymentToWatch = paymentToWatch;
			this.growthTime = growthTime;
			this.diseaseChance = diseaseChance;
			this.plantingXp = plantingXp;
			this.harvestXp = harvestXp;
			this.startingState = startingState;
			this.endingState = endingState;
			this.limitState = limitState;
			this.checkHealthState = checkHealthState;
			this.checkHealthExperience = checkHealthExperience;
		}

		public static BushesData forId(int seedId) {
			return seeds.get(seedId);
		}

		public int getSeedId() {
			return seedId;
		}

		public int getHarvestId() {
			return harvestId;
		}

		public int getSeedAmount() {
			return seedAmount;
		}

		public int getLevelRequired() {
			return levelRequired;
		}

		public int[] getPaymentToWatch() {
			return paymentToWatch;
		}

		public int getGrowthTime() {
			return growthTime;
		}

		public double getDiseaseChance() {
			return diseaseChance;
		}

		public double getPlantingXp() {
			return plantingXp;
		}

		public double getHarvestXp() {
			return harvestXp;
		}

		public int getStartingState() {
			return startingState;
		}

		public int getEndingState() {
			return endingState;
		}

		public int getLimitState() {
			return limitState;
		}

		public int getCheckHealthState() {
			return checkHealthState;
		}

		public double getCheckHealthXp() {
			return checkHealthExperience;
		}
	}

	/* This is the enum data about the different patches */

	public enum BushesFieldsData {
		ETCETERIA(0,
				new Point[] { new Point(2591, 3863), new Point(2592, 3864) },
				2337), SOUTH_ARDOUGNE(1, new Point[] { new Point(2617, 3225),
				new Point(2618, 3226) }, 2338), CHAMPION_GUILD(2, new Point[] {
				new Point(3181, 3357), new Point(3182, 3358) }, 2335), RIMMINGTON(
				3,
				new Point[] { new Point(2940, 3221), new Point(2941, 3222) },
				2336);

		private int bushesIndex;
		private Point[] bushesPosition;
		private int npcId;

		private static Map<Integer, BushesFieldsData> npcsProtecting = new HashMap<Integer, BushesFieldsData>();

		static {
			for (BushesFieldsData data : BushesFieldsData.values()) {
				npcsProtecting.put(data.npcId, data);

			}
		}

		public static BushesFieldsData forId(int npcId) {
			return npcsProtecting.get(npcId);
		}

		BushesFieldsData(int bushesIndex, Point[] bushesPosition, int npcId) {
			this.bushesIndex = bushesIndex;
			this.bushesPosition = bushesPosition;
			this.npcId = npcId;
		}

		public static BushesFieldsData forIdPosition(int x, int y) {
			for (BushesFieldsData bushesFieldsData : BushesFieldsData.values()) {
				if (FarmingConstants.inRangeArea(
						bushesFieldsData.getBushesPosition()[0],
						bushesFieldsData.getBushesPosition()[1], x, y)) {
					return bushesFieldsData;
				}
			}
			return null;
		}

		public int getBushesIndex() {
			return bushesIndex;
		}

		public Point[] getBushesPosition() {
			return bushesPosition;
		}

		public int getNpcId() {
			return npcId;
		}
	}

	/* This is the enum that hold the different data for inspecting the plant */

	public enum InspectData {

		REDBERRY(5101, new String[][] {
				{ "The Redberry seeds have only just been planted." },
				{ "The Redberry bush grows larger." },
				{ "The Redberry bush grows larger." },
				{ "The Redberry bush grows small, unripe,", "green berries." },
				{ "The berries grow larger, and pink." },
				{ "The Redberry bush is ready to harvest.",
						"The berries on the bush are red." }, }), CADAVABERRY(
				5102,
				new String[][] {
						{ "The Cadavaberry seeds have only just been planted." },
						{ "The Cadavaberry bush grows larger." },
						{ "The Cadavaberry bush grows larger." },
						{ "The Cadavaberry bush grows larger." },
						{ "The Cadavaberry bush grows small, unripe,",
								"green berries." },
						{ "The berries grow larger, and pink." },
						{ "The Cadavaberry bush is ready to harvest.",
								"The berries on the bush are purple." } }), DWELLBERRY(
				5103, new String[][] {
						{ "The Dwellbery seeds have only just been planted." },
						{ "The Dwellbery bush grows larger." },
						{ "The Dwellbery bush grows larger." },
						{ "The Dwellbery bush grows larger." },
						{ "The Dwellbery bush grows larger." },
						{ "The Dwellbery bush grows small, unripe,",
								"green berries." },
						{ "The berries grow larger, and light blue." },
						{ "The Dwellbery bush is ready to harvest.",
								"The berries on the bush are blue." }, }), JANGERBERRY(
				5104,
				new String[][] {
						{ "The Jangerberry seeds have only just been planted." },
						{ "The Jangerberry bush grows larger." },
						{ "The Jangerberry bush grows larger." },
						{ "The Jangerberry bush grows larger." },
						{ "The Jangerberry bush grows larger." },
						{ "The Jangerberry bush grows small, unripe,",
								"green berries." },
						{ "The berries grow larger." },
						{ "The berries grow larger, and light green." },
						{ "The Jangerberry bush is ready to harvest.",
								"The berries on the bush are green." } }), WHITEBERRY(
				5105,
				new String[][] {
						{ "The Whiteberry seeds have only just been planted." },
						{ "The Whiteberry bush grows larger." },
						{ "The Whiteberry bush grows larger." },
						{ "The Whiteberry bush grows larger." },
						{ "The Whiteberry bush grows larger." },
						{ "The Whiteberry bush grows larger." },
						{ "The Whiteberry bush grows small, unripe,",
								"green berries." },
						{ "The berries grow larger." },
						{ "The Whiteberry bush is ready to harvest.",
								"The berries on the bush are white." }, }), POISONIVYBERRY(
				5106,
				new String[][] {
						{ "The Poison ivy seeds have only just been planted." },
						{ "The Poison ivy bush grows larger." },
						{ "The Poison ivy bush grows larger." },
						{ "The Poison ivy bush grows larger." },
						{ "The Poison ivy bush grows larger." },
						{ "The Poison ivy bush grows small, unripe,",
								"green berries." },
						{ "The berries grow larger." },
						{ "The berries grow larger, and light green." },
						{ "The Poison ivy bush is ready to harvest.",
								"The berries on the bush are pale yellow." } });
		private int seedId;
		private String[][] messages;

		private static Map<Integer, InspectData> seeds = new HashMap<Integer, InspectData>();

		static {
			for (InspectData data : InspectData.values()) {
				seeds.put(data.seedId, data);
			}
		}

		InspectData(int seedId, String[][] messages) {
			this.seedId = seedId;
			this.messages = messages;
		}

		public static InspectData forId(int seedId) {
			return seeds.get(seedId);
		}

		public int getSeedId() {
			return seedId;
		}

		public String[][] getMessages() {
			return messages;
		}
	}

	/* update all the patch states */

	public void updateBushesStates() {
		// etceteria - south ardougne - champion guild - rimmington
		int[] configValues = new int[bushesStages.length];

		int configValue;
		for (int i = 0; i < bushesStages.length; i++) {
			configValues[i] = getConfigValue(bushesStages[i], bushesSeeds[i],
					bushesState[i], i);
		}

		configValue = (configValues[0] << 16) + (configValues[1] << 8 << 16)
				+ configValues[2] + (configValues[3] << 8);
		player.getPacketSender().sendConfig(MAIN_BUSHES_CONFIG, configValue);
	}

	/* getting the different config values */

	public int getConfigValue(int bushesStage, int seedId, int plantState,
			int index) {
		BushesData bushesData = BushesData.forId(seedId);
		switch (bushesStage) {
		case 0:// weed
			return (GROWING << 6) + 0x00;
		case 1:// weed cleared
			return (GROWING << 6) + 0x01;
		case 2:
			return (GROWING << 6) + 0x02;
		case 3:
			return (GROWING << 6) + 0x03;
		}
		if (bushesData == null) {
			return -1;
		}
		if (bushesStage > bushesData.getEndingState()
				- bushesData.getStartingState() - 1) {
			hasFullyGrown[index] = true;
		}
		if (getPlantState(plantState) == 3)
			return (getPlantState(plantState) << 6)
					+ bushesData.getCheckHealthState();
		if (seedId == 5106) {
			if (getPlantState(plantState) == 1) {
				return bushesData.getStartingState() + bushesStage - 4 + 12;
			} else if (getPlantState(plantState) == 2) {
				return bushesData.getStartingState() + bushesStage - 4 + 20;
			}
		}
		return (getPlantState(plantState) << 6) + bushesData.getStartingState()
				+ bushesStage - 4 + (getPlantState(plantState) == 2 ? -1 : 0);
	}

	/* getting the plant states */

	public int getPlantState(int plantState) {
		switch (plantState) {
		case 0:
			return GROWING;
		case 1:
			return DISEASED;
		case 2:
			return DEAD;
		case 3:
			return CHECK;
		}
		return -1;
	}

	/* calculating the disease chance and making the plant grow */

	public void doCalculations() {
		for (int i = 0; i < bushesSeeds.length; i++) {
			if (bushesStages[i] > 0 && bushesStages[i] <= 3
					&& GameEngine.getMinutesCounter() - bushesTimer[i] >= 5) {
				bushesStages[i]--;
				bushesTimer[i] = GameEngine.getMinutesCounter();
				updateBushesStates();
				continue;
			}
			BushesData bushesData = BushesData.forId(bushesSeeds[i]);
			if (bushesData == null) {
				continue;
			}

			long difference = GameEngine.getMinutesCounter() - bushesTimer[i];
			long growth = bushesData.getGrowthTime();
			int nbStates = bushesData.getEndingState()
					- bushesData.getStartingState();
			int state = (int) (difference * nbStates / growth);
			if (bushesTimer[i] == 0 || bushesState[i] == 2
					|| bushesState[i] == 3 || (state > nbStates)) {
				continue;
			}
			if (4 + state != bushesStages[i]) {
				if (bushesStages[i] == bushesData.getEndingState()
						- bushesData.getStartingState() - 1) {
					bushesStages[i] = bushesData.getEndingState()
							- bushesData.getStartingState() + 4;
					bushesState[i] = 3;
					updateBushesStates();
					return;
				}
				bushesStages[i] = 4 + state;
				if (bushesStages[i] <= 4 + state)
					for (int j = bushesStages[i]; j <= 4 + state; j++)
						doStateCalculation(i);
				updateBushesStates();
			}
		}
	}

	public void modifyStage(int i) {
		BushesData bushesData = BushesData.forId(bushesSeeds[i]);
		if (bushesData == null)
			return;
		long difference = GameEngine.getMinutesCounter() - bushesTimer[i];
		long growth = bushesData.getGrowthTime();
		int nbStates = bushesData.getEndingState()
				- bushesData.getStartingState();
		int state = (int) (difference * nbStates / growth);
		bushesStages[i] = 4 + state;
		updateBushesStates();

	}

	/* calculations about the diseasing chance */

	public void doStateCalculation(int index) {
		if (bushesState[index] == 2) {
			return;
		}
		// if the patch is diseased, it dies, if its watched by a farmer, it
		// goes back to normal
		if (bushesState[index] == 1) {
			if (bushesWatched[index]) {
				bushesState[index] = 0;
				BushesData bushesData = BushesData.forId(bushesSeeds[index]);
				if (bushesData == null)
					return;
				System.out.println(bushesSeeds[index]);
				int difference = bushesData.getEndingState()
						- bushesData.getStartingState();
				int growth = bushesData.getGrowthTime();
				bushesTimer[index] += (growth / difference);
				modifyStage(index);
			} else {
				bushesState[index] = 2;
			}
		}

		if (bushesState[index] == 5 && bushesStages[index] != 2) {
			bushesState[index] = 0;
		}

		if (bushesState[index] == 0 && bushesStages[index] >= 5
				&& !hasFullyGrown[index]) {
			BushesData bushesData = BushesData.forId(bushesSeeds[index]);
			if (bushesData == null) {
				return;
			}

			double chance = diseaseChance[index]
					* bushesData.getDiseaseChance();
			int maxChance = (int) chance * 100;
			if (Misc.random(100) <= maxChance) {
				bushesState[index] = 1;
			}
		}
	}

	/* clearing the patch with a rake of a spade */

	public boolean clearPatch(int objectX, int objectY, int itemId) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		int finalAnimation;
		int finalDelay;
		if (bushesFieldsData == null
				|| (itemId != FarmingConstants.RAKE && itemId != FarmingConstants.SPADE)) {
			return false;
		}
		if (bushesStages[bushesFieldsData.getBushesIndex()] == 3) {
			return true;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (bushesStages[bushesFieldsData.getBushesIndex()] <= 3) {
			if (!player.getItemAssistant().playerHasItem(FarmingConstants.RAKE)) {
				player.getDialogueHandler().sendStatement(
						"You need a rake to clear this path.");
				return true;
			} else {
				finalAnimation = FarmingConstants.RAKING_ANIM;
				finalDelay = 5;
			}
		} else {
			if (!player.getItemAssistant().playerHasItem(FarmingConstants.SPADE)) {
				player.getDialogueHandler().sendStatement("You need a spade to clear this path.");
				return true;
			} else {
				finalAnimation = FarmingConstants.SPADE_ANIM;
				finalDelay = 3;
			}
		}
		final int animation = finalAnimation;
		player.stopPlayer(true);
		player.startAnimation(animation);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				player.startAnimation(animation);
				if (bushesStages[bushesFieldsData.getBushesIndex()] <= 2) {
					bushesStages[bushesFieldsData.getBushesIndex()]++;
					player.getItemAssistant().addItem(6055, 1);
				} else {
					bushesStages[bushesFieldsData.getBushesIndex()] = 3;
					container.stop();
				}
				player.getPlayerAssistant().addSkillXP(CLEARING_EXPERIENCE, SkillConstants.FARMING.ordinal());
				bushesTimer[bushesFieldsData.getBushesIndex()] = GameEngine
						.getMinutesCounter();
				updateBushesStates();
				if (bushesStages[bushesFieldsData.getBushesIndex()] == 3) {
					container.stop();
					return;
				}
			}

			@Override
			public void stop() {
				resetBushes(bushesFieldsData.getBushesIndex());
				player.getPacketSender().sendMessage("You clear the patch.");
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, finalDelay);
		return true;

	}

	/* planting the seeds */

	public boolean plantSeed(int objectX, int objectY, final int seedId) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		final BushesData bushesData = BushesData.forId(seedId);
		if (bushesFieldsData == null || bushesData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (bushesStages[bushesFieldsData.getBushesIndex()] != 3) {
			player.getPacketSender().sendMessage("You can't plant a seed here.");
			return false;
		}
		if (bushesData.getLevelRequired() > player.playerLevel[SkillConstants.FARMING.ordinal()]) {
			player.getDialogueHandler().sendStatement("You need a farming level of "
							+ bushesData.getLevelRequired()
							+ " to plant this seed.");
			return true;
		}
		if (!player.getItemAssistant().playerHasItem(FarmingConstants.SEED_DIBBER)) {
			player.getDialogueHandler().sendStatement(
					"You need a seed dibber to plant seed here.");
			return true;
		}
		if (player.getItemAssistant().getItemAmount(bushesData.getSeedId()) < bushesData
				.getSeedAmount()) {
			player.getDialogueHandler().sendStatement("You need atleast "
					+ bushesData.getSeedAmount() + " seeds to plant here.");
			return true;
		}
		player.startAnimation(FarmingConstants.SEED_DIBBING);
		bushesStages[bushesFieldsData.getBushesIndex()] = 4;
		player.getItemAssistant().deleteItem(seedId, bushesData.getSeedAmount());

		player.stopPlayer(true);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				bushesState[bushesFieldsData.getBushesIndex()] = 0;
				bushesSeeds[bushesFieldsData.getBushesIndex()] = seedId;
				bushesTimer[bushesFieldsData.getBushesIndex()] = GameEngine
						.getMinutesCounter();
				
				player.getPlayerAssistant().addSkillXP(bushesData.getPlantingXp(), SkillConstants.FARMING.ordinal());
				container.stop();
			}

			@Override
			public void stop() {
				updateBushesStates();
				player.stopPlayer(false);
			}
		}, 3);
		return true;
	}

	@SuppressWarnings("unused")
	private void displayAll() {
		for (int i = 0; i < bushesStages.length; i++) {
			System.out.println("index : " + i);
			System.out.println("state : " + bushesState[i]);
			System.out.println("seeds : " + bushesSeeds[i]);
			System.out.println("level : " + bushesStages[i]);
			System.out.println("timer : " + bushesTimer[i]);
			System.out.println("disease chance : " + diseaseChance[i]);
			System.out
					.println("-----------------------------------------------------------------");
		}
	}

	/* harvesting the plant resulted */

	public boolean harvestOrCheckHealth(int objectX, int objectY) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldsData == null) {
			return false;
		}
		final BushesData bushesData = BushesData
				.forId(bushesSeeds[bushesFieldsData.getBushesIndex()]);
		if (bushesData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (player.getItemAssistant().freeSlots()  <= 0) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return true;
		}
		player.startAnimation(832);
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (player.getItemAssistant().freeSlots()  <= 0) {
					container.stop();
					return;
				}

				if (bushesState[bushesFieldsData.getBushesIndex()] == 3) {
					player.getPacketSender()
							.sendMessage(
									"You examine the bush for signs of disease and find that it's in perfect health.");
					player.getPlayerAssistant().addSkillXP(bushesData.getCheckHealthXp(), SkillConstants.FARMING.ordinal());
					bushesState[bushesFieldsData.getBushesIndex()] = 0;
					hasFullyGrown[bushesFieldsData.getBushesIndex()] = false;
					bushesTimer[bushesFieldsData.getBushesIndex()] = GameEngine
							.getMinutesCounter() - bushesData.getGrowthTime();
					// bushesStages[bushesFieldsData.getBushesIndex()] -= 2;
					modifyStage(bushesFieldsData.getBushesIndex());
					container.stop();
					return;
				}
				player.getPacketSender().sendMessage(
						"You harvest the crop, and pick some berries.");
				player.getItemAssistant().addItem(bushesData.getHarvestId(), 1);
				player.getPlayerAssistant().addSkillXP(bushesData.getHarvestXp(), SkillConstants.FARMING.ordinal());
				bushesTimer[bushesFieldsData.getBushesIndex()] = GameEngine
						.getMinutesCounter();
				int difference = bushesData.getEndingState()
						- bushesData.getStartingState();
				int growth = bushesData.getGrowthTime();
				lowerStage(
						bushesFieldsData.getBushesIndex(),
						growth
								- (growth / difference)
								* (difference + 5 - bushesStages[bushesFieldsData
										.getBushesIndex()]));
				modifyStage(bushesFieldsData.getBushesIndex());
				container.stop();
			}

			@Override
			public void stop() {
				player.getPlayerAssistant().resetAnimation();
			}
		}, 2);
		return true;
	}

	/* lowering the stage */

	public void lowerStage(int index, int timer) {
		hasFullyGrown[index] = false;
		bushesTimer[index] -= timer;
	}

	/* putting compost onto the plant */

	public boolean putCompost(int objectX, int objectY, final int itemId) {
		if (itemId != 6032 && itemId != 6034) {
			return false;
		}
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldsData == null) {
			return false;
		}

		if (bushesStages[bushesFieldsData.getBushesIndex()] != 3
				|| bushesState[bushesFieldsData.getBushesIndex()] == 5) {
			player.getPacketSender().sendMessage("This patch doesn't need compost.");
			return true;
		}
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(1925, 1);

		player.getPacketSender().sendMessage(
				"You pour some " + (itemId == 6034 ? "super" : "")
						+ "compost on the patch.");
		player.startAnimation(FarmingConstants.PUTTING_COMPOST);
		player.getPlayerAssistant().addSkillXP(itemId == 6034 ? Compost.SUPER_COMPOST_EXP_USE
						: Compost.COMPOST_EXP_USE, SkillConstants.FARMING.ordinal());

		player.stopPlayer(true);
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				diseaseChance[bushesFieldsData.getBushesIndex()] *= itemId == 6032 ? COMPOST_CHANCE
						: SUPERCOMPOST_CHANCE;
				bushesState[bushesFieldsData.getBushesIndex()] = 5;
				container.stop();
			}

			@Override
			public void stop() {
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 7);
		return true;
	}

	/* inspecting a plant */

	public boolean inspect(int objectX, int objectY) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		final InspectData inspectData = InspectData
				.forId(bushesSeeds[bushesFieldsData.getBushesIndex()]);
		final BushesData bushesData = BushesData
				.forId(bushesSeeds[bushesFieldsData.getBushesIndex()]);
		if (bushesState[bushesFieldsData.getBushesIndex()] == 1) {
			player.getDialogueHandler().sendStatement("This plant is diseased. Use a plant cure on it to cure it, ",
							"or clear the patch with a spade.");
			return true;
		} else if (bushesState[bushesFieldsData.getBushesIndex()] == 2) {
			player.getDialogueHandler().sendStatement("This plant is dead. You did not cure it while it was diseased.",
							"Clear the patch with a spade.");
			return true;
		} else if (bushesState[bushesFieldsData.getBushesIndex()] == 3) {
			player.getDialogueHandler().sendStatement(
					"This plant has fully grown. You can check it's health",
					"to gain some farming experiences.");
			return true;
		}
		if (bushesStages[bushesFieldsData.getBushesIndex()] == 0) {
			player.getDialogueHandler().sendStatement(
					"This is a bush patch. The soil has not been treated.",
					"The patch needs weeding.");
		} else if (bushesStages[bushesFieldsData.getBushesIndex()] == 3) {
			player.getDialogueHandler().sendStatement(
					"This is a bush patch. The soil has not been treated.",
					"The patch is empty and weeded.");
		} else if (inspectData != null && bushesData != null) {
			player.getPacketSender().sendMessage(
					"You bend down and start to inspect the patch...");

			player.startAnimation(1331);
			player.stopPlayer(true);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
					if (bushesStages[bushesFieldsData.getBushesIndex()] - 4 < inspectData
							.getMessages().length - 2) {
						player.getDialogueHandler().sendStatement( inspectData
								.getMessages()[bushesStages[bushesFieldsData
								.getBushesIndex()] - 4]);
					} else if (bushesStages[bushesFieldsData.getBushesIndex()] < bushesData
							.getEndingState()
							- bushesData.getStartingState()
							+ 2) {
						player.getDialogueHandler().sendStatement(
								inspectData.getMessages()[inspectData
										.getMessages().length - 2]);
					} else {
						player.getDialogueHandler().sendStatement(
								inspectData.getMessages()[inspectData
										.getMessages().length - 1]);
					}
					container.stop();
				}

				@Override
				public void stop() {
					player.startAnimation(1332);
					player.stopPlayer(false);
					// player.reset();
				}
			}, 5);
		}
		return true;
	}

	/* opening the corresponding guide about the patch */

	public boolean guide(int objectX, int objectY) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		player.getSkillInterfaces().farmingComplex(5);
		player.getSkillInterfaces().selected = 20;
		return true;
	}

	/* Curing the plant */

	public boolean curePlant(int objectX, int objectY, int itemId) {
		final BushesFieldsData bushesFieldsData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldsData == null || itemId != 6036) {
			return false;
		}
		final BushesData bushesData = BushesData
				.forId(bushesSeeds[bushesFieldsData.getBushesIndex()]);
		if (bushesData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (bushesState[bushesFieldsData.getBushesIndex()] != 1) {
			player.getPacketSender().sendMessage("This plant doesn't need to be cured.");
			return true;
		}
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(229, 1);
		player.startAnimation(FarmingConstants.CURING_ANIM);
		player.stopPlayer(true);
		bushesState[bushesFieldsData.getBushesIndex()] = 0;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				player.getPacketSender().sendMessage(
						"You cure the plant with a plant cure.");
				container.stop();
			}

			@Override
			public void stop() {
				updateBushesStates();
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 7);

		return true;

	}

	private void resetBushes(int index) {
		bushesSeeds[index] = 0;
		bushesState[index] = 0;
		diseaseChance[index] = 1;
		hasFullyGrown[index] = false;
		bushesWatched[index] = false;
	}

	/* checking if the patch is raked */

	public boolean checkIfRaked(int objectX, int objectY) {
		final BushesFieldsData bushesFieldData = BushesFieldsData
				.forIdPosition(objectX, objectY);
		if (bushesFieldData == null)
			return false;
		if (bushesStages[bushesFieldData.getBushesIndex()] == 3)
			return true;
		return false;
	}

}
