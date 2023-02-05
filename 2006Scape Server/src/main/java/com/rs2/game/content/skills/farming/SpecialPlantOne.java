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
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;
/**
 * Created by IntelliJ IDEA. User: vayken Date: 24/02/12 Time: 20:34 To change
 * this template use File | Settings | File Templates.
 */
public class SpecialPlantOne {

	private Player player;

	// set of global constants for Farming

	private static final double COMPOST_CHANCE = 0.9;
	private static final double SUPERCOMPOST_CHANCE = 0.7;
	private static final double CLEARING_EXPERIENCE = 4;

	public SpecialPlantOne(Player player) {
		this.player = player;
	}

	// Farming data
	public int[] specialPlantStages = new int[4];
	public int[] specialPlantSaplings = new int[4];
	public int[] specialPlantState = new int[4];
	public long[] specialPlantTimer = new long[4];
	public double[] diseaseChance = { 1, 1, 1, 1 };
	public boolean[] hasFullyGrown = { false, false, false, false };

	public static final int MAIN_SPECIAL_PLANT_CONFIG = 507;

	/* This is the enum holding the saplings info */

	public enum SpecialPlantData {
		SPIRIT_TREE(5375, -1, 1, 83, 3680, 0.15, 199.5, 0, 0x09, 0x14, 0x2c,
				19301.8, 12, 23), CALQUAT_TREE(5503, 5980, 1, 72, 1200, 0.15,
				129.5, 48.5, 0x04, 0x12, 0x22, 12096, 14, 20);

		private int saplingId;
		private int harvestId;
		private int saplingAmount;
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

		private static Map<Integer, SpecialPlantData> saplings = new HashMap<Integer, SpecialPlantData>();

		static {
			for (SpecialPlantData data : SpecialPlantData.values()) {
				saplings.put(data.saplingId, data);
			}
		}

		SpecialPlantData(int saplingId, int harvestId, int saplingAmount,
				int levelRequired, int growthTime, double diseaseChance,
				double plantingXp, double harvestXp, int startingState,
				int endingState, int checkHealthState,
				double checkHealthExperience, int diseaseDiffValue,
				int deathDiffValue) {
			this.saplingId = saplingId;
			this.harvestId = harvestId;
			this.saplingAmount = saplingAmount;
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

		public static SpecialPlantData forId(int saplingId) {
			return saplings.get(saplingId);
		}

		public int getSapplingId() {
			return saplingId;
		}

		public int getHarvestId() {
			return harvestId;
		}

		public int getSeedAmount() {
			return saplingAmount;
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
		BRIMHAVEN(0,
				new Point[] { new Point(2801, 3202), new Point(2803, 3204) },
				5375), KARAMJA(1, new Point[] { new Point(2795, 3100),
				new Point(2797, 3102) }, 5503), DRAYNOR(2, new Point[] {
				new Point(3059, 3257), new Point(3061, 3259) }, 5375), ETCETERIA(
				3,
				new Point[] { new Point(2612, 3857), new Point(2614, 3859) },
				5375);

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

		public int getSaplingd() {
			return seedId;
		}
	}

	/* This is the enum that hold the different data for inspecting the plant */

	public enum InspectData {

		SPIRIT_TREE(
				5375,
				new String[][] {
						{ "The spirit tree sapling has only just been planted. It has not grown yet." },
						{ "The spirit tree has grown slightly, and sprouted a few more leaves." },
						{ "Some dark spots have appeared on the trees trunk, and the leaves have grown longer." },
						{ "The tree has grown larger in all respects, and has grown more leaves." },
						{
								"The spirit tree base has widened showing some roots, and the leaves have morphed into a small canopy.",
								"Two small branches have appeared on either side of the trunk." },
						{
								"The spirit tree has grown wider in girth, but is still the same height as before.",
								"The base is larger as well." },
						{ "The spirit tree has grown larger in all respects.",
								"The trunk is more warped towards the west, and the roots are more visible" },
						{ "The spirit tree is larger in all respects.",
								"The trunk has grown in a 'S' shape." },
						{
								"The spirit tree has grown another knob on the trunk which will eventually become its nose.",
								"The tree is larger in all respects, and its branches are growing out to the sides more." },
						{
								"The spirit tree canopy shifts the angle its inclining towards, and its branches are almost parallel to the ground.",
								"he nose is more defined, and the tree is slightly larger." },
						{ "The spirit tree branches are slightly angling towards the ground, and it is slightly larger than before." },
						{
								"The spirit tree canopy is smaller, the face is fully defined and the texture of the tree has changed dramatically",
								"The Spirit tree is ready to be checked." } }), CALQUAT_TREE(
				5503,
				new String[][] {
						{ "The calquat sapling has only just been planted." },
						{ "The calquat tree grows another segment taller." },
						{ "The calquat tree grows another segment taller." },
						{ "The calquat tree grows another segment longer and starts a branch midway up its trunk." },
						{ "The calquat tree grows some leaves." },
						{ "The calquat tree grows another segment upside down and grows leaves on its mid-branch." },
						{ "The calquat tree grows towards the ground." },
						{ "The calquat tree is ready to be harvested." }, });
		private int saplingId;
		private String[][] messages;

		private static Map<Integer, InspectData> saplings = new HashMap<Integer, InspectData>();

		static {
			for (InspectData data : InspectData.values()) {
				saplings.put(data.saplingId, data);
			}
		}

		InspectData(int saplingId, String[][] messages) {
			this.saplingId = saplingId;
			this.messages = messages;
		}

		public static InspectData forId(int saplingId) {
			return saplings.get(saplingId);
		}

		public int getSeedId() {
			return saplingId;
		}

		public String[][] getMessages() {
			return messages;
		}
	}

	/* update all the patch states */

	public void updateSpecialPlants() {
		// brimhaven - karamja - draynor - Etceteria
		int[] configValues = new int[specialPlantStages.length];

		int configValue;
		for (int i = 0; i < specialPlantStages.length; i++) {
			configValues[i] = getConfigValue(specialPlantStages[i],
					specialPlantSaplings[i], specialPlantState[i], i);
		}

		configValue = (configValues[0] << 16) + (configValues[1] << 8 << 16)
				+ configValues[2] + (configValues[3] << 8);
		player.getPacketSender().sendConfig(MAIN_SPECIAL_PLANT_CONFIG, configValue);
	}

	/* getting the different config values */

	public int getConfigValue(int specialStage, int saplingId, int plantState,
			int index) {
		SpecialPlantData specialPlantData = SpecialPlantData.forId(saplingId);
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
		for (int i = 0; i < specialPlantSaplings.length; i++) {
			if (specialPlantStages[i] > 0
					&& specialPlantStages[i] <= 3
					&& GameEngine.getMinutesCounter() - specialPlantTimer[i] >= 5) {
				specialPlantStages[i]--;
				specialPlantTimer[i] = GameEngine.getMinutesCounter();
				updateSpecialPlants();
				continue;
			}
			SpecialPlantData specialPlantData = SpecialPlantData
					.forId(specialPlantSaplings[i]);
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
							+ (specialPlantData == SpecialPlantData.SPIRIT_TREE ? 3
									: -2)) {
				if (specialPlantStages[i] == specialPlantData.getEndingState()
						- specialPlantData.getStartingState()
						+ (specialPlantData == SpecialPlantData.SPIRIT_TREE ? 3
								: -2)) {
					specialPlantStages[i] = specialPlantData.getEndingState()
							- specialPlantData.getStartingState() + 7;
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
				.forId(specialPlantSaplings[i]);
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
					.forId(specialPlantSaplings[index]);
			if (specialPlantData == null) {
				return;
			}

			double chance = diseaseChance[index]
					* specialPlantData.getDiseaseChance();
			int maxChance = (int) chance * 100;
			if (Misc.random(100) < maxChance) {
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

	/* planting the saplings */

	public boolean plantSapling(int objectX, int objectY, final int saplingId) {
		final SpecialPlantFieldsData specialPlantFieldData = SpecialPlantFieldsData
				.forIdPosition(objectX, objectY);
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(saplingId);
		if (specialPlantFieldData == null || specialPlantData == null
				|| specialPlantFieldData.getSaplingd() != saplingId) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if ((specialPlantStages[0] > 3 || specialPlantStages[2] > 3 || specialPlantStages[3] > 3)
				&& (specialPlantFieldData.getSpecialPlantsIndex() != 1)) {
			player.getPacketSender().sendMessage(
					"You already have a spirit tree planted somewhere else.");
			return true;

		}
		if (specialPlantStages[specialPlantFieldData.getSpecialPlantsIndex()] != 3) {
			player.getPacketSender().sendMessage("You can't plant a sapling here.");
			return true;
		}
		if (specialPlantData.getLevelRequired() > player.playerLevel[SkillConstants.FARMING.ordinal()]) {
			player.getDialogueHandler().sendStatement("You need a farming level of "
							+ specialPlantData.getLevelRequired()
							+ " to plant this sapling.");
			return true;
		}

		if (!player.getItemAssistant().playerHasItem(FarmingConstants.TROWEL)) {
			player.getDialogueHandler().sendStatement(
					"You need a trowel to plant the sapling here.");
			return true;
		}
		player.startAnimation(
				FarmingConstants.PLANTING_POT_ANIM);
		specialPlantStages[specialPlantFieldData.getSpecialPlantsIndex()] = 4;
		player.getItemAssistant().deleteItem(saplingId, 1);

		player.stopPlayer(true);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				specialPlantState[specialPlantFieldData.getSpecialPlantsIndex()] = 0;
				specialPlantSaplings[specialPlantFieldData
						.getSpecialPlantsIndex()] = saplingId;
				specialPlantTimer[specialPlantFieldData.getSpecialPlantsIndex()] = GameEngine
						.getMinutesCounter();
				player.getPlayerAssistant().addSkillXP(specialPlantData.getPlantingXp(), SkillConstants.FARMING.ordinal());
				container.stop();
			}

			@Override
			public void stop() {
				updateSpecialPlants();
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
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
				.forId(specialPlantSaplings[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		if (specialPlantData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (specialPlantData == SpecialPlantData.SPIRIT_TREE
				&& specialPlantState[specialPlantFieldsData
						.getSpecialPlantsIndex()] != 3) {
			handleSpiritTree();
			return true;
		}
		if (player.getItemAssistant().freeSlots()  <= 0) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return true;
		}
		player.startAnimation(832);
		player.stopPlayer(true);
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
								+ DeprecatedItems.getItemName(specialPlantData.getHarvestId()).toLowerCase() + ".");
				player.getItemAssistant().addItem(
						specialPlantData.getHarvestId(), 1);
				player.getPlayerAssistant().addSkillXP(specialPlantData.getHarvestXp(), SkillConstants.FARMING.ordinal());
				specialPlantStages[specialPlantFieldsData
						.getSpecialPlantsIndex()]--;
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

	private void handleSpiritTree() {
		// SpiritTree.sendDialogue(player, 3636);
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
				.forId(specialPlantSaplings[specialPlantFieldsData
						.getSpecialPlantsIndex()]);
		final SpecialPlantData specialPlantData = SpecialPlantData
				.forId(specialPlantSaplings[specialPlantFieldsData
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
			player.getDialogueHandler().sendStatement("This is one of the special patches. The soil has not been treated.",
							"The patch needs weeding.");
		} else if (specialPlantStages[specialPlantFieldsData
				.getSpecialPlantsIndex()] == 3) {
			player.getDialogueHandler().sendStatement("This is one of the special patches. The soil has not been treated.",
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
						player.getDialogueHandler().sendStatement(inspectData.getMessages()[specialPlantStages[specialPlantFieldsData
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
				.forId(specialPlantSaplings[specialPlantFieldsData
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
		specialPlantSaplings[index] = 0;
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
