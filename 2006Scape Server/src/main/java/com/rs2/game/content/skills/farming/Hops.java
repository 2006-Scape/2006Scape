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
public class Hops {

	private Player player;

	// set of global constants for Farming

	private static final int START_HARVEST_AMOUNT = 3;
	private static final int END_HARVEST_AMOUNT = 41;

	private static final double WATERING_CHANCE = 0.5;
	private static final double COMPOST_CHANCE = 0.9;
	private static final double SUPERCOMPOST_CHANCE = 0.7;
	private static final double CLEARING_EXPERIENCE = 4;

	public Hops(Player player) {
		this.player = player;
	}

	// Farming data
	public int[] hopsStages = new int[4];
	public int[] hopsSeeds = new int[4];
	public int[] hopsHarvest = new int[4];
	public int[] hopsState = new int[4];
	public long[] hopsTimer = new long[4];
	public double[] diseaseChance = { 1, 1, 1, 1 };
	public boolean[] hasFullyGrown = { false, false, false, false };
	public boolean[] hopsWatched = { false, false, false, false };

	/* set of the constants for the patch */

	// states - 2 bits plant - 6 bits
	public static final int GROWING = 0x00;
	public static final int WATERED = 0x01;
	public static final int DISEASED = 0x02;
	public static final int DEAD = 0x03;

	public static final int MAIN_HOPS_CONFIG = 506;

	/* This is the enum holding the seeds info */

	public enum HopsData {
		BARLEY(5305, 6006, 4, 3, new int[] { 6032, 3 }, 40, 0.35, 8.5, 9.5,
				0x31, 0x35), HAMMERSTONE(5307, 5994, 4, 4,
				new int[] { 6010, 1 }, 40, 0.35, 9, 10, 0x04, 0x08), ASGARNIAN(
				5308, 5996, 4, 8, new int[] { 5458, 1 }, 40, 0.30, 10.5, 12,
				0x0b, 0x10), JUTE(5306, 5931, 3, 13, new int[] { 6008, 6 }, 40,
				0.30, 13, 14.5, 0x38, 0x3d), YANILLIAN(5309, 5998, 4, 16,
				new int[] { 5968, 1 }, 40, 0.25, 14.5, 16, 0x13, 0x19), KRANDORIAN(
				5310, 6000, 4, 21, new int[] { 5478 }, 40, 0.25, 17.5, 19.5,
				0x1c, 0x23), WILDBLOOD(5311, 6002, 4, 28,
				new int[] { 6012, 1 }, 40, 0.20, 23, 26, 0x26, 0x2e), ;

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

		private static Map<Integer, HopsData> seeds = new HashMap<Integer, HopsData>();

		static {
			for (HopsData data : HopsData.values()) {
				seeds.put(data.seedId, data);
			}
		}

		HopsData(int seedId, int harvestId, int seedAmount, int levelRequired,
				int[] paymentToWatch, int growthTime, double diseaseChance,
				double plantingXp, double harvestXp, int startingState,
				int endingState) {
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
		}

		public static HopsData forId(int seedId) {
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
	}

	/* This is the enum data about the different patches */

	public enum HopsFieldsData {

		LUMBRIDGE(0,
				new Point[] { new Point(3227, 3313), new Point(3231, 3317) },
				2333), MCGRUBOR(1, new Point[] { new Point(2664, 3523),
				new Point(2669, 3528) }, 2334), YANILLE(2, new Point[] {
				new Point(2574, 3103), new Point(2577, 3106) }, 2332), ENTRANA(
				3,
				new Point[] { new Point(2809, 3335), new Point(2812, 3338) },
				2327);

		private int hopsIndex;
		private Point[] hopsPosition;
		private int npcId;

		private static Map<Integer, HopsFieldsData> npcsProtecting = new HashMap<Integer, HopsFieldsData>();

		static {
			for (HopsFieldsData data : HopsFieldsData.values()) {
				npcsProtecting.put(data.npcId, data);

			}
		}

		public static HopsFieldsData forId(int npcId) {
			return npcsProtecting.get(npcId);
		}

		HopsFieldsData(int hopsIndex, Point[] hopsPosition, int npcId) {
			this.hopsIndex = hopsIndex;
			this.hopsPosition = hopsPosition;
			this.npcId = npcId;
		}

		public static HopsFieldsData forIdPosition(int x, int y) {
			for (HopsFieldsData hopsFieldsData : HopsFieldsData.values()) {
				if (FarmingConstants.inRangeArea(
						hopsFieldsData.getHopsPosition()[0],
						hopsFieldsData.getHopsPosition()[1], x, y)) {
					return hopsFieldsData;
				}
			}
			return null;
		}

		public int getHopsIndex() {
			return hopsIndex;
		}

		public Point[] getHopsPosition() {
			return hopsPosition;
		}

		public int getNpcId() {
			return npcId;
		}
	}

	/* This is the enum that hold the different data for inspecting the plant */

	public enum InspectData {

		BARLEY(5305, new String[][] {
				{ "The barley seeds have only just been planted." },
				{ "Grain heads develop at the upper part of the stalks,",
						"as the barley grows taller." },
				{ "The barley grows taller, the heads weighing",
						"slightly on the stalks." },
				{ "The barley grows taller." },
				{ "The barley is ready to harvest. The heads of grain",
						"are weighing down heavily on the stalks!" } }), HAMMERSTONE(
				5307,
				new String[][] {
						{ "The Hammerstone seeds have only just been planted." },
						{ "The Hammerstone hops plant grows a little bit taller." },
						{ "The Hammerstone hops plant grows a bit taller." },
						{ "The Hammerstone hops plant grows a bit taller." },
						{ "The Hammerstone hops plant is ready to harvest." } }), ASGARNIAN(
				5308, new String[][] {
						{ "The Asgarnian seeds have only just been planted." },
						{ "The Asgarnian hops plant grows a bit taller." },
						{ "The Asgarnian hops plant grows a bit taller." },
						{ "The Asgarnian hops plant grows a bit taller." },
						{ "The upper new leaves appear dark green to the",
								"rest of the plant." },
						{ "The Asgarnian hops plant is ready to harvest." } }), JUTE(
				5306, new String[][] {
						{ "The Jute seeds have only just been planted." },
						{ "The jute plants grow taller." },
						{ "The jute plants grow taller." },
						{ "The jute plants grow taller." },
						{ "The jute plant grows taller. They are as high",
								"as the player." },
						{ "The jute plants are ready to harvest." } }), YANILLIAN(
				5309, new String[][] {
						{ "The Yanillian seeds have only just been planted." },
						{ "The Yanillian hops plant grows a bit taller." },
						{ "The Yanillian hops plant grows a bit taller." },
						{ "The Yanillian hops plant grows a bit taller." },
						{ "The new leaves on the top of the Yanillian hops",
								"plant are dark green." },
						{ "The new leaves on the top of the Yanillian hops",
								"plant are dark green." },
						{ "The Yanillian hops plant is ready to harvest." } }), KRANDORIAN(
				5310,
				new String[][] {
						{ "The Krandorian seeds have only just been planted." },
						{ "The Krandorian plant grows a bit taller." },
						{ "The Krandorian plant grows a bit taller." },
						{ "The Krandorian plant grows a bit taller." },
						{ "The new leaves on top of the Krandorian plant are",
								"dark green." },
						{ "The Krandorian plant grows a bit taller." },
						{ "The new leaves on top of the Krandorian plant",
								"are dark green." },
						{ "The Krandorian plant is ready for harvesting." } }), WILDBLOOD(
				5311,
				new String[][] {
						{ "The wildblood seeds have only just been planted." },
						{ "The wildblood hops plant grows a bit taller." },
						{ "The wildblood hops plant grows a bit taller." },
						{ "The wildblood hops plant grows a bit taller." },
						{ "The wildblood hops plant grows a bit taller." },
						{ "The wildblood hops plant grows a bit taller." },
						{ "The wildblood hops plant grows a bit taller." },
						{
								"The new leaves at the top of the wildblood hops plant",
								"are dark green." },
						{ "The wildblood hops plant is ready to harvest." } });
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

	public void updateHopsStates() {
		// lumbridge - mc grubor - yanille - entrana
		int[] configValues = new int[hopsStages.length];

		int configValue;
		for (int i = 0; i < hopsStages.length; i++) {
			configValues[i] = getConfigValue(hopsStages[i], hopsSeeds[i],
					hopsState[i], i);
		}

		configValue = (configValues[0] << 16) + (configValues[1] << 8 << 16)
				+ configValues[2] + (configValues[3] << 8);
		player.getPacketSender().sendConfig(MAIN_HOPS_CONFIG, configValue);

	}

	/* getting the different config values */

	public int getConfigValue(int hopsStage, int seedId, int plantState,
			int index) {
		HopsData hopsData = HopsData.forId(seedId);
		switch (hopsStage) {
		case 0:// weed
			return (GROWING << 6) + 0x00;
		case 1:// weed cleared
			return (GROWING << 6) + 0x01;
		case 2:
			return (GROWING << 6) + 0x02;
		case 3:
			return (GROWING << 6) + 0x03;
		}
		if (hopsData == null) {
			return -1;
		}
		if (hopsData.getEndingState() == hopsData.getStartingState()
				+ hopsStage - 1) {
			hasFullyGrown[index] = true;
		}

		return (getPlantState(plantState) << 6) + hopsData.getStartingState()
				+ hopsStage - 4;
	}

	/* getting the plant states */

	public int getPlantState(int plantState) {
		switch (plantState) {
		case 0:
			return GROWING;
		case 1:
			return WATERED;
		case 2:
			return DISEASED;
		case 3:
			return DEAD;
		}
		return -1;
	}

	/* calculating the disease chance and making the plant grow */

	public void doCalculations() {
		for (int i = 0; i < hopsSeeds.length; i++) {
			if (hopsStages[i] > 0 && hopsStages[i] <= 3
					&& GameEngine.getMinutesCounter() - hopsTimer[i] >= 5) {
				hopsStages[i]--;
				hopsTimer[i] = GameEngine.getMinutesCounter();
				updateHopsStates();
				continue;
			}
			HopsData hopsData = HopsData.forId(hopsSeeds[i]);
			if (hopsData == null) {
				continue;
			}

			long difference = GameEngine.getMinutesCounter() - hopsTimer[i];
			long growth = hopsData.getGrowthTime();
			int nbStates = hopsData.getEndingState()
					- hopsData.getStartingState();
			int state = (int) (difference * nbStates / growth);
			if (hopsTimer[i] == 0 || hopsState[i] == 3 || state > nbStates) {
				continue;
			}
			if (4 + state != hopsStages[i]) {
				hopsStages[i] = 4 + state;
				if (hopsStages[i] <= 4 + state)
					for (int j = hopsStages[i]; j <= 4 + state; j++)
						doStateCalculation(i);
				updateHopsStates();
			}
		}
	}

	public void modifyStage(int i) {
		HopsData hopsData = HopsData.forId(hopsSeeds[i]);
		if (hopsData == null)
			return;
		long difference = GameEngine.getMinutesCounter() - hopsTimer[i];
		long growth = hopsData.getGrowthTime();
		int nbStates = hopsData.getEndingState() - hopsData.getStartingState();
		int state = (int) (difference * nbStates / growth);
		hopsStages[i] = 4 + state;
		updateHopsStates();

	}

	/* calculations about the diseasing chance */

	public void doStateCalculation(int index) {
		if (hopsState[index] == 3) {
			return;
		}
		// if the patch is diseased, it dies, if its watched by a farmer, it
		// goes back to normal
		if (hopsState[index] == 2) {
			if (hopsWatched[index]) {
				hopsState[index] = 0;
				HopsData hopsData = HopsData.forId(hopsSeeds[index]);
				if (hopsData == null)
					return;
				int difference = hopsData.getEndingState()
						- hopsData.getStartingState();
				int growth = hopsData.getGrowthTime();
				hopsTimer[index] += (growth / difference);
				modifyStage(index);
			} else {
				hopsState[index] = 3;
			}
		}

		if (hopsState[index] == 1) {
			diseaseChance[index] *= 2;
			hopsState[index] = 0;
		}

		if (hopsState[index] == 5 && hopsStages[index] != 3) {
			hopsState[index] = 0;
		}

		if (hopsState[index] == 0 && hopsStages[index] >= 5
				&& !hasFullyGrown[index]) {
			HopsData hopsData = HopsData.forId(hopsSeeds[index]);
			if (hopsData == null) {
				return;
			}

			double chance = diseaseChance[index] * hopsData.getDiseaseChance();
			int maxChance = (int) chance * 100;
			if (Misc.random(100) <= maxChance) {
				hopsState[index] = 2;
			}
		}
	}

	/* watering the patch */

	public boolean waterPatch(int objectX, int objectY, int itemId) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null) {
			return false;
		}
		HopsData hopsData = HopsData.forId(hopsSeeds[hopsFieldsData
				.getHopsIndex()]);
		if (hopsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (hopsState[hopsFieldsData.getHopsIndex()] == 1
				|| hopsStages[hopsFieldsData.getHopsIndex()] <= 1
				|| hopsStages[hopsFieldsData.getHopsIndex()] == hopsData
						.getEndingState() - hopsData.getStartingState() + 4) {
			player.getPacketSender().sendMessage("This patch doesn't need watering.");
			return true;
		}
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(itemId == 5333 ? itemId - 2 : itemId - 1, 1);

		if (!player.getItemAssistant().playerHasItem(FarmingConstants.RAKE)) {
			player.getDialogueHandler().sendStatement(
					"You need a seed dibber to plant seed here.");
			return true;
		}
		player.getPacketSender().sendMessage("You water the patch.");
		player.startAnimation(
				FarmingConstants.WATERING_CAN_ANIM);

		player.stopPlayer(true);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				diseaseChance[hopsFieldsData.getHopsIndex()] *= WATERING_CHANCE;
				hopsState[hopsFieldsData.getHopsIndex()] = 1;
				container.stop();
			}

			@Override
			public void stop() {
				updateHopsStates();
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 5);
		return true;
	}

	/* clearing the patch with a rake of a spade */

	public boolean clearPatch(int objectX, int objectY, int itemId) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
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
		if (hopsStages[hopsFieldsData.getHopsIndex()] == 3) {
			return true;
		}
		if (hopsStages[hopsFieldsData.getHopsIndex()] <= 3) {
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
				if (hopsStages[hopsFieldsData.getHopsIndex()] <= 2) {
					hopsStages[hopsFieldsData.getHopsIndex()]++;
					player.getItemAssistant().addItem(6055, 1);
				} else {
					hopsStages[hopsFieldsData.getHopsIndex()] = 3;
					container.stop();
				}
				player.getPlayerAssistant().addSkillXP(CLEARING_EXPERIENCE, SkillConstants.FARMING.ordinal());
				hopsTimer[hopsFieldsData.getHopsIndex()] = GameEngine
						.getMinutesCounter();
				updateHopsStates();
				if (hopsStages[hopsFieldsData.getHopsIndex()] == 3) {
					container.stop();
					return;
				}
			}

			@Override
			public void stop() {
				resetHops(hopsFieldsData.getHopsIndex());
				player.getPacketSender().sendMessage("You clear the patch.");
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, finalDelay);
		return true;

	}

	/* planting the seeds */

	public boolean plantSeed(int objectX, int objectY, final int seedId) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		final HopsData hopsData = HopsData.forId(seedId);
		if (hopsFieldsData == null || hopsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (hopsStages[hopsFieldsData.getHopsIndex()] != 3) {
			player.getPacketSender().sendMessage("You can't plant a seed here.");
			return false;
		}
		if (hopsData.getLevelRequired() > player.playerLevel[SkillConstants.FARMING.ordinal()]) {
			player.getDialogueHandler().sendStatement("You need a farming level of "
							+ hopsData.getLevelRequired()
							+ " to plant this seed.");
			return true;
		}
		if (!player.getItemAssistant().playerHasItem(FarmingConstants.SEED_DIBBER)) {
			player.getDialogueHandler().sendStatement(
					"You need a seed dibber to plant seed here.");
			return true;
		}
		if (player.getItemAssistant().getItemAmount(hopsData.getSeedId()) < hopsData
				.getSeedAmount()) {
			player.getDialogueHandler().sendStatement( "You need atleast "
					+ hopsData.getSeedAmount() + " seeds to plant here.");
			return true;
		}
		player.startAnimation(FarmingConstants.SEED_DIBBING);
		hopsStages[hopsFieldsData.getHopsIndex()] = 4;
		player.getItemAssistant().deleteItem(seedId, hopsData.getSeedAmount());

		player.stopPlayer(true);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				hopsState[hopsFieldsData.getHopsIndex()] = 0;
				hopsSeeds[hopsFieldsData.getHopsIndex()] = seedId;
				hopsTimer[hopsFieldsData.getHopsIndex()] = GameEngine
						.getMinutesCounter();
				player.getPlayerAssistant().addSkillXP(hopsData.getPlantingXp(), SkillConstants.FARMING.ordinal());
				container.stop();
			}

			@Override
			public void stop() {
				updateHopsStates();
				player.stopPlayer(false);
			}
		}, 3);
		return true;
	}

	/* harvesting the plant resulted */

	public boolean harvest(int objectX, int objectY) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null) {
			return false;
		}
		final HopsData hopsData = HopsData.forId(hopsSeeds[hopsFieldsData
				.getHopsIndex()]);
		if (hopsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (!player.getItemAssistant().playerHasItem(FarmingConstants.SPADE)) {
			player.getDialogueHandler().sendStatement(
					"You need a spade to harvest here.");
			return true;
		}
		player.startAnimation(FarmingConstants.SPADE_ANIM);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				if (hopsHarvest[hopsFieldsData.getHopsIndex()] == 0) {
					hopsHarvest[hopsFieldsData.getHopsIndex()] = 1 + (START_HARVEST_AMOUNT + Misc
							.random(END_HARVEST_AMOUNT - START_HARVEST_AMOUNT)) * (1);
				}
				if (hopsHarvest[hopsFieldsData.getHopsIndex()] == 1) {
					resetHops(hopsFieldsData.getHopsIndex());
					hopsStages[hopsFieldsData.getHopsIndex()] = 3;
					hopsTimer[hopsFieldsData.getHopsIndex()] = GameEngine
							.getMinutesCounter();
					container.stop();
					return;
				}
				if (player.getItemAssistant().freeSlots()  <= 0) {
					container.stop();
					return;
				}
				hopsHarvest[hopsFieldsData.getHopsIndex()]--;
				player.startAnimation(
						FarmingConstants.SPADE_ANIM);
				player.getPacketSender().sendMessage(
						"You harvest the crop, and get some vegetables.");
				player.getItemAssistant()
						.addItem(hopsData.getHarvestId(), 1);
				player.getPlayerAssistant().addSkillXP(hopsData.getHarvestXp(), SkillConstants.FARMING.ordinal());
			}

			@Override
			public void stop() {
				updateHopsStates();
				player.getPlayerAssistant().resetAnimation();
			}
		}, 2);
		return true;
	}

	/* putting compost onto the plant */

	public boolean putCompost(int objectX, int objectY, final int itemId) {
		if (itemId != 6032 && itemId != 6034) {
			return false;
		}
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (hopsStages[hopsFieldsData.getHopsIndex()] != 3
				|| hopsState[hopsFieldsData.getHopsIndex()] == 5) {
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
				diseaseChance[hopsFieldsData.getHopsIndex()] *= itemId == 6032 ? COMPOST_CHANCE
						: SUPERCOMPOST_CHANCE;
				hopsState[hopsFieldsData.getHopsIndex()] = 5;
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
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		final InspectData inspectData = InspectData
				.forId(hopsSeeds[hopsFieldsData.getHopsIndex()]);
		final HopsData hopsData = HopsData.forId(hopsSeeds[hopsFieldsData
				.getHopsIndex()]);
		if (hopsState[hopsFieldsData.getHopsIndex()] == 2) {
			player.getDialogueHandler().sendStatement("This plant is diseased. Use a plant cure on it to cure it, ",
							"or clear the patch with a spade.");
			return true;
		} else if (hopsState[hopsFieldsData.getHopsIndex()] == 3) {
			player.getDialogueHandler().sendStatement("This plant is dead. You did not cure it while it was diseased.",
							"Clear the patch with a spade.");
			return true;
		}
		if (hopsStages[hopsFieldsData.getHopsIndex()] == 0) {
			player.getDialogueHandler().sendStatement(
					"This is a hops patch. The soil has not been treated.",
					"The patch needs weeding.");
		} else if (hopsStages[hopsFieldsData.getHopsIndex()] == 3) {
			player.getDialogueHandler().sendStatement(
					"This is a hops patch. The soil has not been treated.",
					"The patch is empty and weeded.");
		} else if (inspectData != null && hopsData != null) {
			player.getPacketSender().sendMessage(
					"You bend down and start to inspect the patch...");

			player.startAnimation(1331);
			player.stopPlayer(true);
			 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (hopsStages[hopsFieldsData.getHopsIndex()] - 4 < inspectData
							.getMessages().length - 2) {
						player.getDialogueHandler().sendStatement( inspectData
								.getMessages()[hopsStages[hopsFieldsData
								.getHopsIndex()] - 4]);
					} else if (hopsStages[hopsFieldsData.getHopsIndex()] < hopsData
							.getEndingState() - hopsData.getStartingState() + 2) {
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
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		player.getSkillInterfaces().farmingComplex(2);
		player.getSkillInterfaces().selected = 20;
		return true;
	}

	/* Curing the plant */

	public boolean curePlant(int objectX, int objectY, int itemId) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null || itemId != 6036) {
			return false;
		}
		final HopsData hopsData = HopsData.forId(hopsSeeds[hopsFieldsData
				.getHopsIndex()]);
		if (hopsData == null) {
			return false;
		}
		if (!SkillConstants.getEnabled(SkillConstants.FARMING.ordinal())) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return true;
		}
		if (hopsState[hopsFieldsData.getHopsIndex()] != 2) {
			player.getPacketSender().sendMessage("This plant doesn't need to be cured.");
			return true;
		}
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(229, 1);
		player.startAnimation(FarmingConstants.CURING_ANIM);
		player.stopPlayer(true);
		hopsState[hopsFieldsData.getHopsIndex()] = 0;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
				player.getPacketSender().sendMessage(
						"You cure the plant with a plant cure.");
				container.stop();
			}

			@Override
			public void stop() {
				updateHopsStates();
				player.stopPlayer(false);
				player.getPlayerAssistant().resetAnimation();
			}
		}, 7);

		return true;

	}

	private void resetHops(int index) {
		hopsSeeds[index] = 0;
		hopsState[index] = 0;
		diseaseChance[index] = 1;
		hopsHarvest[index] = 0;
		hasFullyGrown[index] = false;
		hopsWatched[index] = false;
	}

	/* checking if the patch is raked */

	public boolean checkIfRaked(int objectX, int objectY) {
		final HopsFieldsData hopsFieldsData = HopsFieldsData.forIdPosition(
				objectX, objectY);
		if (hopsFieldsData == null)
			return false;
		if (hopsStages[hopsFieldsData.getHopsIndex()] == 3)
			return true;
		return false;
	}

}
