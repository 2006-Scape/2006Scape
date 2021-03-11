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
public class SpecialPlantTwo {

	private Player player;

	// set of global constants for Farming

	private static final double COMPOST_CHANCE = 0.9;
	private static final double SUPERCOMPOST_CHANCE = 0.7;
	private static final double CLEARING_EXPERIENCE = 4;

	public SpecialPlantTwo(Player player) {
		this.player = player;
	}

	// Farming data
	public int[] specialPlantStages = new int[4];
	public int[] specialPlantSeeds = new int[4];
	public int[] specialPlantState = new int[4];
	public long[] specialPlantTimer = new long[4];
	public double[] diseaseChance = { 1, 1, 1, 1 };
	public boolean[] hasFullyGrown = { false, false, false, false };

	public static final int MAIN_SPECIAL_PLANT_CONFIG = 512;

	/* This is the enum holding the seeds info */

	public enum SpecialPlantData {
		BELLADONNA(5281, 2398, 1, 63, 280, 0.15, 91, 512, 0x04, 0x08, -1, 0, 5,
				8), CACTUS(5280, 6016, 1, 55, 550, 0.15, 66.5, 25, 0x08, 0x12,
				0x1f, 374, 11, 17), BITTERCAP(5282, 6004, 1, 53, 220, 0.15,
				61.5, 57.7, 0x04, 0x0f, -1, 0, 12, 17);

		private int seedId;
		private int harvestId;
		private int seedAmount;
		private int levelRequired;
		private int growthTime;
		private double diseaseChance;
		private double plantingXp;
		private double harvestXp;
		private int startingState;
		private int endingState;
		private int checkHealthState;
		private double checkHealthExperience;
		private int diseaseDiffValue;
		private int deathDiffValue;

		private static Map<Integer, SpecialPlantData> seeds = new HashMap<Integer, SpecialPlantData>();

		static {
			for (SpecialPlantData data : SpecialPlantData.values()) {
				seeds.put(data.seedId, data);
			}
		}

		SpecialPlantData(int seedId, int harvestId, int seedAmount,
				int levelRequired, int growthTime, double diseaseChance,
				double plantingXp, double harvestXp, int startingState,
				int endingState, int checkHealthState,
				double checkHealthExperience, int diseaseDiffValue,
				int deathDiffValue) {
			this.seedId = seedId;
			this.harvestId = harvestId;
			this.seedAmount = seedAmount;
			this.levelRequired = levelRequired;
			this.growthTime = growthTime;
			this.diseaseChance = diseaseChance;
			this.plantingXp = plantingXp;
			this.harvestXp = harvestXp;
			this.startingState = startingState;
			this.endingState = endingState;
			this.checkHealthState = checkHealthState;
			this.checkHealthExperience = checkHealthExperience;
			this.diseaseDiffValue = diseaseDiffValue;
			this.deathDiffValue = deathDiffValue;
		}

		public static SpecialPlantData forId(int seedId) {
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

		public int getCheckHealthState() {
			return checkHealthState;
		}

		public double getCheckHealthXp() {
			return checkHealthExperience;
		}

		public int getDiseaseDiffValue() {
			return diseaseDiffValue;
		}

		public int getDeathDiffValue() {
			return deathDiffValue;
		}
	}

	/* This is the enum data about the different patches */

	public enum SpecialPlantFieldsData {
		DRAYNOR_MANOR(0, new Point[] { new Point(3086, 3354),
				new Point(3087, 3355) }, 5281), AL_KARID(2, new Point[] {
				new Point(3315, 3202), new Point(3316, 3203) }, 5280), CANIFIS(
				3,
				new Point[] { new Point(3451, 3472), new Point(3452, 3473) },
				5282);

		private int specialPlantsIndex;
		private Point[] specialPlantPosition;
		private int seedId;

		SpecialPlantFieldsData(int specialPlantsIndex,
				Point[] specialPlantPosition, int seedId) {
			this.specialPlantsIndex = specialPlantsIndex;
			this.specialPlantPosition = specialPlantPosition;
			this.seedId = seedId;
		}

		public static SpecialPlantFieldsData forIdPosition(int x, int y) {
			for (SpecialPlantFieldsData specialPlantFieldsData : SpecialPlantFieldsData
					.values()) {
				if (FarmingConstants.inRangeArea(
						specialPlantFieldsData.getSpecialPlantPosition()[0],
						specialPlantFieldsData.getSpecialPlantPosition()[1], x,
						y)) {
					return specialPlantFieldsData;
				}
			}
			return null;
		}

		public int getSpecialPlantsIndex() {
			return specialPlantsIndex;
		}

		public Point[] getSpecialPlantPosition() {
			return specialPlantPosition;
		}

		public int getSeedId() {
			return seedId;
		}
	}

	/* This is the enum that hold the different data for inspecting the plant */

	public enum InspectData {
		BELLADONNA(5281, new String[][] {
				{ "The belladonna seed has only just been planted." },
				{ "The belladonna plant grows a little taller." },
				{ "The belladonna plant grows taller and leafier." },
				{ "The belladonna plant grows some flower buds." },
				{ "The belladonna plant is ready to harvest." } }), CACTUS(
				5280,
				new String[][] {
						{ "The cactus seed has only just been planted." },
						{ "The cactus grows taller." },
						{ "The cactus grows two small stumps." },
						{ "The cactus grows its stumps longer." },
						{ "The cactus grows larger." },
						{ "The cactus curves its arms upwards and grows another stump." },
						{ "The cactus grows all three of its arms upwards." },
						{ "The cactus is ready to be harvested." } }), BITTERCAP(
				5282, new String[][] {
						{ "The mushroom spore has only just been planted." },
						{ "The mushrooms grow a little taller." },
						{ "The mushrooms grow a little taller." },
						{ "The mushrooms grow a little larger." },
						{ "The mushrooms grow a little larger." },
						{ "The mushrooms tops grow a little wider." },
						{ "The mushrooms are ready to harvest." } });

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

	public void updateSpecialPlants() {
		// draynor manor - none - al karid - canifis
		int[] configValues = new int[specialPlantStages.length];

		int configValue;
		for (int i = 0; i < specialPlantStages.length; i++) {
			configValues[i] = getConfigValue(specialPlantStages[i],
					specialPlantSeeds[i], specialPlantState[i], i);
		}

		configValue = (configValues[0] << 16) + (configValues[1] << 8 << 16)
				+ configValues[2] + (configValues[3] << 8);
		player.getPacketSender().sendConfig(MAIN_SPECIAL_PLANT_CONFIG, configValue);
	}

	/* getting the different config values */

	public int getConfigValue(int specialStage, int seedId, int plantState,
			int index) {
		SpecialPlantData specialPlantData = SpecialPlantData.forId(seedId);
		switch (specialStage) {
		case 0:// weed
			return 0x00;
		case 1:// weed cleared
			return 0x01;
		case 2:
			return 0x02;
		case 3:
			return 0x03;
		}
		if (specialPlantData == null) {
			return -1;
		}
		if (specialStage > specialPlantData.getEndingState()
				- specialPlantData.getStartingState() - 1) {
			hasFullyGrown[index] = true;
		}
		if (getPlantState(plantState, specialPlantData, specialStage) == 3)
			return specialPlantData.getCheckHealthState();

		return getPlantState(plantState, specialPlantData, specialStage);
	}

	/* getting the plant states */

	public int getPlantState(int plantState, SpecialPlantData specialPlantData,
			int specialStage) {
		int value = specialPlantData.getStartingState() + specialStage - 4;
		switch (plantState) {
		case 0:
			return value;
		case 1:
			return value + specialPlantData.getDiseaseDiffValue();
		case 2:
			return value + specialPlantData.getDeathDiffValue();
		case 3:
			return specialPlantData.getCheckHealthState();
		}
		return -1;
	}

	/* calculating the disease chance and making the plant grow */

	public void doCalculations() {
		for (int i = 0; i < specialPlantSeeds.length; i++) {
			if (specialPlantStages[i] > 0
					&& specialPlantStages[i] <= 3
					&& GameEngine.getMinutesCounter() - specialPlantTimer[i] >= 5) {
				specialPlantStages[i]--;
				specialPlantTimer[i] = GameEngine.getMinutesCounter();
				updateSpecialPlants();
				continue;
			}
			SpecialPlantData specialPlantData = SpecialPlantData
					.forId(specialPlantSeeds[i]);
			if (specialPlantData == null) {
				continue;
			}

			long difference = GameEngine.getMinutesCounter()
					- specialPlantTimer[i];
			long growth = specialPlantData.getGrowthTime();
			int nbStates = specialPlantData.getEndingState()
					- specialPlantData.getStartingState();
			int state = (int) (difference * nbStates / growth);
			if (specialPlantTimer[i] == 0 || specialPlantState[i] == 2
					|| specialPlantState[i] == 3 || (state > nbStates)) {
				continue;
			}
			if (4 + state != specialPlantStages[i]
					&& specialPlantStages[i] <= specialPlantData
							.getEndingState()
							- specialPlantData.getStartingState()
							+ (specialPlantData == SpecialPlantData.BELLADONNA ? 5
									: -2)) {
				if (specialPlantStages[i] == specialPlantData.getEndingState()
						- specialPlantData.getStartingState() - 2
						&& specialPlantData.getCheckHealthState() != -1) {
					specialPlantStages[i] = specialPlantData.getEndingState()
							- specialPlantData.getStartingState() + 4;
					specialPlantState[i] = 3;
					updateSpecialPlants();
					return;
				}
				specialPlantStages[i] = 4 + state;
				doStateCalculation(i);
				updateSpecialPlants();
			}
		}
	}

	public void modifyStage(int i) {
		SpecialPlantData specialPlantData = SpecialPlantData
				.forId(specialPlantSeeds[i]);
		if (specialPlantData == null)
			return;
		long difference = GameEngine.getMinutesCounter() - specialPlantTimer[i];
		long growth = specialPlantData.getGrowthTime();
		int nbStates = specialPlantData.getEndingState()
				- specialPlantData.getStartingState();
		int state = (int) (difference * nbStates / growth);
		specialPlantStages[i] = 4 + state;
		updateSpecialPlants();

	}

	/* calculations about the diseasing chance */

	public void doStateCalculation(int index) {
		if (specialPlantState[index] == 2) {
			return;
		}
		// if the patch is diseased, it dies, if its watched by a farmer, it
		// goes back to normal
		if (specialPlantState[index] == 1) {
			specialPlantState[index] = 2;
		}

		if (specialPlantState[index] == 5 && specialPlantStages[index] != 2) {
			specialPlantState[index] = 0;
		}

		if (specialPlantState[index] == 0 && specialPlantStages[index] >= 5
				&& !hasFullyGrown[index]) {
			SpecialPlantData specialPlantData = SpecialPlantData
					.forId(specialPlantSeeds[index]);
			if (specialPlantData == null) {
				return;
			}

			double chance = diseaseChance[index]
					* specialPlantData.getDiseaseChance();
			int maxChance = (int) chance * 100;
			if (Misc.random(100) <= maxChance) {
				specialPlantState[index] = 1;
			}
		}
	}

	/* clearing the patch with a rake of a spade */

	public boolean clearPatch(int objectX, int objectY, int itemId) {
		final SpecialPlantFieldsData hopsFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		int finalAnimation;
		int finalDelay;
		if (hopsFieldsData == null
				|| (itemId != FarmingConstants.RAKE && itemId != FarmingConstants.SPADE)) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()] == 3) {
			return true;
		}
		if (specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()] <= 3) {
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
				player.getDialogueHandler().sendStatement(
						"You need a spade to clear this path.");
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
				if (specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()] <= 2) {
					specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()]++;
					player.getItemAssistant().addItem(6055, 1);
				} else {
					specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()] = 3;
					container.stop();
				}
				player.getPlayerAssistant().addSkillXP(CLEARING_EXPERIENCE, SkillConstants.FARMING.ordinal());
				specialPlantTimer[hopsFieldsData.getSpecialPlantsIndex()] = GameEngine
						.getMinutesCounter();
				updateSpecialPlants();
				if (specialPlantStages[hopsFieldsData.getSpecialPlantsIndex()] == 3) {
					container.stop();
					return;
				}
			}

			@Override
			public void stop() {
				resetSpecialPlants(hopsFieldsData.getSpecialPlantsIndex());
				player.getPacketSender().sendMessage("You clear the patch.");
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, finalDelay);
		return true;

	}

	/* planting the seeds */

	public boolean plantSeeds(int objectX, int objectY, final int seedId) {
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(seedId);
		if (specialPlantFieldsData == null || specialPlantData == null
				|| specialPlantFieldsData.getSeedId() != seedId) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (specialPlantStages[specialPlantFieldsData.getSpecialPlantsIndex()] != 3) {
			player.getPacketSender().sendMessage("You can't plant a seed here.");
			return false;
		}
		if (specialPlantData.getLevelRequired() > player.playerLevel[SkillConstants.FARMING.ordinal()]) {
			player.getDialogueHandler().sendStatement("You need a farming level of "
							+ specialPlantData.getLevelRequired()
							+ " to plant this seed.");
			return true;
		}
		if (!player.getItemAssistant().playerHasItem(FarmingConstants.SEED_DIBBER)) {
			player.getDialogueHandler().sendStatement(
					"You need a seed dibber to plant seed here.");
			return true;
		}
		if (player.getItemAssistant().getItemAmount(specialPlantData.getSeedId()) < specialPlantData
				.getSeedAmount()) {
			player.getDialogueHandler().sendStatement( "You need atleast "
					+ specialPlantData.getSeedAmount()
					+ " seeds to plant here.");
			return true;
		}
		player.startAnimation(FarmingConstants.SEED_DIBBING);
		specialPlantStages[specialPlantFieldsData.getSpecialPlantsIndex()] = 4;
		player.getItemAssistant().deleteItem(seedId, specialPlantData.getSeedAmount());

		player.stopPlayer(true);
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				specialPlantState[specialPlantFieldsData
						.getSpecialPlantsIndex()] = 0;
				specialPlantSeeds[specialPlantFieldsData
						.getSpecialPlantsIndex()] = seedId;
				specialPlantTimer[specialPlantFieldsData
						.getSpecialPlantsIndex()] = GameEngine
						.getMinutesCounter();
				player.getPlayerAssistant().addSkillXP(specialPlantData.getPlantingXp(), SkillConstants.FARMING.ordinal());
				container.stop();
			}

			@Override
			public void stop() {
				updateSpecialPlants();
				player.stopPlayer(false);
			}
		}, 3);
		return true;
	}
	
	/* harvesting the plant resulted */

	public boolean harvestOrCheckHealth(int objectX, int objectY) {
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldsData == null) {
			return false;
		}
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(specialPlantSeeds[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		if (specialPlantData == null) {
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
		player.stopPlayer(true);
		player.startAnimation(832);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				if (player.getItemAssistant().freeSlots()  <= 0) {
					container.stop();
					return;
				}

				if (specialPlantState[specialPlantFieldsData
						.getSpecialPlantsIndex()] == 3) {
					player.getPacketSender()
							.sendMessage(
									"You examine the plant for signs of disease and find that it's in perfect health.");
					player.getPlayerAssistant().addSkillXP(specialPlantData.getCheckHealthXp(), SkillConstants.FARMING.ordinal());
					specialPlantState[specialPlantFieldsData
							.getSpecialPlantsIndex()] = 0;
					hasFullyGrown[specialPlantFieldsData
							.getSpecialPlantsIndex()] = false;
					specialPlantTimer[specialPlantFieldsData
							.getSpecialPlantsIndex()] = GameEngine
							.getMinutesCounter()
							- specialPlantData.getGrowthTime();
					modifyStage(specialPlantFieldsData.getSpecialPlantsIndex());
					container.stop();
					return;
				}
				player.getPacketSender().sendMessage(
						"You harvest the crop, and pick some "
								+ specialPlantData.getHarvestId() + ".");
				player.getItemAssistant().addItem(specialPlantData.getHarvestId(), 1);
				player.getPlayerAssistant().addSkillXP(specialPlantData.getHarvestXp(), SkillConstants.FARMING.ordinal());

				switch (specialPlantData) {
				case BELLADONNA:
					resetSpecialPlants(specialPlantFieldsData
							.getSpecialPlantsIndex());
					specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()] = 3;
					specialPlantTimer[specialPlantFieldsData
							.getSpecialPlantsIndex()] = GameEngine
							.getMinutesCounter();
					break;
				case CACTUS:
					specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()]--;
					break;
				case BITTERCAP:
					specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()]++;
					if (specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()] == 16) {
						resetSpecialPlants(specialPlantFieldsData
								.getSpecialPlantsIndex());
						specialPlantStages[specialPlantFieldsData
								.getSpecialPlantsIndex()] = 3;
						specialPlantTimer[specialPlantFieldsData
								.getSpecialPlantsIndex()] = GameEngine
								.getMinutesCounter();
					}
					break;
				}
				updateSpecialPlants();
				container.stop();
			}

			@Override
			public void stop() {
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 2);
		return true;
	}

	/* lowering the stage */

	public void lowerStage(int index, int timer) {
		hasFullyGrown[index] = false;
		specialPlantTimer[index] -= timer;
	}

	/* putting compost onto the plant */

	public boolean putCompost(int objectX, int objectY, final int itemId) {
		if (itemId != 6032 && itemId != 6034) {
			return false;
		}
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (specialPlantStages[specialPlantFieldsData.getSpecialPlantsIndex()] != 3
				|| specialPlantState[specialPlantFieldsData
						.getSpecialPlantsIndex()] == 5) {
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
				diseaseChance[specialPlantFieldsData.getSpecialPlantsIndex()] *= itemId == 6032 ? COMPOST_CHANCE
						: SUPERCOMPOST_CHANCE;
				specialPlantState[specialPlantFieldsData
						.getSpecialPlantsIndex()] = 5;
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
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		final InspectData inspectData = InspectData
				.forId(specialPlantSeeds[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(specialPlantSeeds[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		if (specialPlantState[specialPlantFieldsData.getSpecialPlantsIndex()] == 1) {
			player.getDialogueHandler().sendStatement("This plant is diseased. Use a plant cure on it to cure it, ",
							"or clear the patch with a spade.");
			return true;
		} else if (specialPlantState[specialPlantFieldsData
				.getSpecialPlantsIndex()] == 2) {
			player.getDialogueHandler().sendStatement("This plant is dead. You did not cure it while it was diseased.",
							"Clear the patch with a spade.");
			return true;
		} else if (specialPlantState[specialPlantFieldsData
				.getSpecialPlantsIndex()] == 3) {
			player.getDialogueHandler().sendStatement(
					"This plant has fully grown. You can check it's health",
					"to gain some farming experiences.");
			return true;
		}
		if (specialPlantStages[specialPlantFieldsData.getSpecialPlantsIndex()] == 0) {
			player.getDialogueHandler().sendStatement(
							"This is one of the special patches. The soil has not been treated.",
							"The patch needs weeding.");
		} else if (specialPlantStages[specialPlantFieldsData
				.getSpecialPlantsIndex()] == 3) {
			player.getDialogueHandler().sendStatement(
					"This is one of the special patches. The soil has not been treated.",
							"The patch is empty and weeded.");
		} else if (inspectData != null && specialPlantData != null) {
			player.getPacketSender().sendMessage(
					"You bend down and start to inspect the patch...");

			player.startAnimation(1331);
			player.stopPlayer(true);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
					if (specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()] - 4 < inspectData
							.getMessages().length - 2) {
						player.getDialogueHandler().sendStatement(
								inspectData.getMessages()[specialPlantStages[specialPlantFieldsData
										.getSpecialPlantsIndex()] - 4]);
					} else if (specialPlantStages[specialPlantFieldsData
							.getSpecialPlantsIndex()] < specialPlantData
							.getEndingState()
							- specialPlantData.getStartingState() + 2) {
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
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		player.getSkillInterfaces().farmingComplex(8);
		player.getSkillInterfaces().selected = 20;
		return true;
	}

	/* Curing the plant */

	public boolean curePlant(int objectX, int objectY, int itemId) {
		final SpecialPlantFieldsData specialPlantFieldsData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldsData == null || itemId != 6036) {
			return false;
		}
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(specialPlantSeeds[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		if (specialPlantData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (specialPlantState[specialPlantFieldsData.getSpecialPlantsIndex()] != 1) {
			player.getPacketSender().sendMessage("This plant doesn't need to be cured.");
			return true;
		}
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(229, 1);
		player.startAnimation(FarmingConstants.CURING_ANIM);
		player.stopPlayer(true);
		specialPlantState[specialPlantFieldsData.getSpecialPlantsIndex()] = 0;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				player.getPacketSender().sendMessage(
						"You cure the plant with a plant cure.");
				container.stop();
			}

			@Override
			public void stop() {
				updateSpecialPlants();
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 7);

		return true;

	}

	private void resetSpecialPlants(int index) {
		specialPlantSeeds[index] = 0;
		specialPlantState[index] = 0;
		diseaseChance[index] = 1;
		hasFullyGrown[index] = false;
	}

	/* checking if the patch is raked */

	public boolean checkIfRaked(int objectX, int objectY) {
		final SpecialPlantFieldsData specialPlantFieldData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		if (specialPlantFieldData == null)
			return false;
		if (specialPlantStages[specialPlantFieldData.getSpecialPlantsIndex()] == 3)
			return true;
		return false;
	}

}
