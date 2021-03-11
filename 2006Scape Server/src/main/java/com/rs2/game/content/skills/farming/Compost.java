/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs2.game.content.skills.farming;

/**
 *
 * @author ArrowzFtw
 */

import java.util.HashMap;
import java.util.Map;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.skills.SkillConstants;
import com.rs2.game.players.Player;

/**
 * Created by IntelliJ IDEA. User: vayken Date: 22/02/12 Time: 15:43 To change
 * this template use File | Settings | File Templates.
 */
public class Compost {

	private Player player;

	public Compost(Player player) {
		this.player = player;
	}

	public int[] compostBins = new int[4];
	public long[] compostBinsTimer = new long[4];
	public int[] organicItemAdded = new int[4];
	public int tempCompostState;

	/* setting up the experiences constants */

	public static final double COMPOST_EXP_RETRIEVE = 4.5;
	public static final double SUPER_COMPOST_EXP_RETRIEVE = 8.5;
	public static final double COMPOST_EXP_USE = 18;
	public static final double SUPER_COMPOST_EXP_USE = 26;

	public static final double ROTTEN_TOMATOES_EXP_RETRIEVE = 8.5;

	/* these are the constants related to compost making */

	public static final int COMPOST = 6032;

	public static final int SUPER_COMPOST = 6034;

	public static final int ROTTE_TOMATO = 2518;

	public static final int TOMATO = 1982;

	public static final int FIRST_TYPE_COMPOST_BIN = 7808;

	public static final int SECOND_TYPE_COMPOST_BIN = 7818;

	public static final int[] COMPOST_ORGANIC = { 6055, 1942, 1957, 1965, 5986,
			5504, 5982, 249, 251, 253, 255, 257, 2998, 259, 261, 263, 3000,
			265, 2481, 267, 269, 1951, 753, 2126, 247, 239, 6018 };

	public static final int[] SUPER_COMPOST_ORGANIC = { 2114, 5978, 5980, 5982,
			6004, 247, 6469 };

	/* this is the enum that stores the different locations of the compost bins */

	public enum CompostBinLocations {
		NORTH_ARDOUGNE(0, FIRST_TYPE_COMPOST_BIN, 3, 2661, 3375), PHASMATYS(1,
				SECOND_TYPE_COMPOST_BIN, 1, 3610, 3522), FALADOR(2,
				FIRST_TYPE_COMPOST_BIN, 4, 3056, 3312), CATHERBY(3,
				FIRST_TYPE_COMPOST_BIN, 3, 2804, 3464);

		private int compostIndex;
		private int binObjectId;
		private int objectFace;
		private int x, y, z;
		private static Map<Integer, CompostBinLocations> bins = new HashMap<Integer, CompostBinLocations>();

		static {
			for (CompostBinLocations data : CompostBinLocations.values()) {
				bins.put(data.compostIndex, data);
			}
		}

		CompostBinLocations(int compostIndex, int binObjectId, int objectFace,
				int x, int y) {
			this.compostIndex = compostIndex;
			this.binObjectId = binObjectId;
			this.objectFace = objectFace;
			this.x = x;
			this.y = y;
		}

		public static CompostBinLocations forId(int index) {
			return bins.get(index);
		}

		public static CompostBinLocations forPosition(int x, int y) {
			for (CompostBinLocations compostBinLocations : CompostBinLocations
					.values()) {
				if (compostBinLocations.x == x && compostBinLocations.y == y) {
					return compostBinLocations;
				}
			}
			return null;
		}

		public int getCompostIndex() {
			return compostIndex;
		}

		public int getBinObjectId() {
			return binObjectId;
		}

		public int getObjectFace() {
			return objectFace;
		}
	}

	/* this is the enum that stores the different compost bins stages */

	public enum CompostBinStages {
		FIRST_TYPE(7808, 7813, 7809, 7810, 7811, 7812, 7814, 7815, 7816, 7817,
				7828, 7829, 7830, 7831), SECOND_TYPE(7818, 7823, 7819, 7820,
				7821, 7822, 7824, 7825, 7826, 7827, 7832, 7833, 7834, 7835);
		private int binEmpty;
		private int closedBin;
		private int binWithCompostable;
		private int binFullOfCompostable;
		private int binWithSuperCompostable;
		private int binFullOFSuperCompostable;
		private int binWithCompost;
		private int binFullOfCompost;
		private int binWithSuperCompost;
		private int binFullOfSuperCompost;
		private int binWithTomatoes;
		private int binFullOfTomatoes;
		private int binWithRottenTomatoes;
		private int binFullOfRottenTomatoes;

		private static Map<Integer, CompostBinStages> bins = new HashMap<Integer, CompostBinStages>();

		static {
			for (CompostBinStages data : CompostBinStages.values()) {
				bins.put(data.binEmpty, data);
			}
		}

		CompostBinStages(int binEmpty, int closedBin, int binWithCompostable,
				int binFullOfCompostable, int binWithSuperCompostable,
				int binFullOFSuperCompostable, int binWithCompost,
				int binFullOfCompost, int binWithSuperCompost,
				int binFullOfSuperCompost, int binWithTomatoes,
				int binFullOfTomatoes, int binWithRottenTomatoes,
				int binFullOfRottenTomatoes) {
			this.binEmpty = binEmpty;
			this.closedBin = closedBin;
			this.binWithCompostable = binWithCompostable;
			this.binFullOfCompostable = binFullOfCompostable;
			this.binWithSuperCompostable = binWithSuperCompostable;
			this.binFullOFSuperCompostable = binFullOFSuperCompostable;
			this.binWithCompost = binWithCompost;
			this.binFullOfCompost = binFullOfCompost;
			this.binWithSuperCompost = binWithSuperCompost;
			this.binFullOfSuperCompost = binFullOfSuperCompost;
			this.binWithTomatoes = binWithTomatoes;
			this.binFullOfTomatoes = binFullOfTomatoes;
			this.binWithRottenTomatoes = binWithRottenTomatoes;
			this.binFullOfRottenTomatoes = binFullOfRottenTomatoes;
		}

		public static CompostBinStages forId(int binId) {
			return bins.get(binId);
		}

		public int getBinEmpty() {
			return binEmpty;
		}

		public int getClosedBin() {
			return closedBin;
		}

		public int getBinWithCompostable() {
			return binWithCompostable;
		}

		public int getBinFullOfCompostable() {
			return binFullOfCompostable;
		}

		public int getBinWithSuperCompostable() {
			return binWithSuperCompostable;
		}

		public int getBinFullOFSuperCompostable() {
			return binFullOFSuperCompostable;
		}

		public int getBinWithCompost() {
			return binWithCompost;
		}

		public int getBinFullOfCompost() {
			return binFullOfCompost;
		}

		public int getBinWithSuperCompost() {
			return binWithSuperCompost;
		}

		public int getBinFullOfSuperCompost() {
			return binFullOfSuperCompost;
		}

		public int getBinWithTomatoes() {
			return binWithTomatoes;
		}

		public int getBinFullOfTomatoes() {
			return binFullOfTomatoes;
		}

		public int getBinWithRottenTomatoes() {
			return binWithRottenTomatoes;
		}

		public int getBinFullOfRottenTomatoes() {
			return binFullOfRottenTomatoes;
		}
	}

	/* handle compost bin updating */
	private void updateCompostBin(int index) {
		CompostBinStages compostBinStages = CompostBinStages
				.forId(CompostBinLocations.forId(index).getBinObjectId());

		if (compostBinStages == null) {
			return;
		}
		int x = CompostBinLocations.forId(index).x;
		int y = CompostBinLocations.forId(index).y;
		int z = CompostBinLocations.forId(index).z;
		int finalObject;

		// handling the different ways to fill a compost bin
		if (compostBins[index] > 0) {
			if (compostBins[index] % 17 == 0) {
				finalObject = compostBinStages.getBinWithSuperCompostable();
			} else if (compostBins[index] % 77 == 0) {
				finalObject = compostBinStages.getBinWithTomatoes();
			} else {
				finalObject = compostBinStages.getBinWithCompostable();
			}
		} else {
			finalObject = compostBinStages.getBinEmpty();
		}

		// handling the different ways to complete a compost bin
		if (compostBins[index] == 255) {
			finalObject = compostBinStages.getBinFullOFSuperCompostable();
			tempCompostState = 2;
		} else if (compostBins[index] == 1155) {
			finalObject = compostBinStages.getBinFullOfTomatoes();
			tempCompostState = 3;
		} else if (organicItemAdded[index] == 15) {
			finalObject = compostBinStages.getBinFullOfCompostable();
			tempCompostState = 1;
		}
		// handling the closed state of the compost bin
		switch (compostBins[index]) {

		case 100:
		case 200:
		case 300:
			finalObject = compostBinStages.getClosedBin();
			break;

		// handling the rotted state of the compost in the bin
		case 150:
			finalObject = compostBinStages.getBinFullOfCompost();
			break;
		case 250:
			finalObject = compostBinStages.getBinFullOfSuperCompost();
			break;
		case 350:
			finalObject = compostBinStages.getBinFullOfRottenTomatoes();
			break;

		}

		// handle the compost bin state when the player retrieve the compost
		if (compostBins[index] == 150 && organicItemAdded[index] < 15) {
			finalObject = compostBinStages.getBinWithCompost();
		} else if (compostBins[index] == 250 && organicItemAdded[index] < 15) {
			finalObject = compostBinStages.getBinWithSuperCompost();
		}
		if (compostBins[index] == 350 && organicItemAdded[index] < 15) {
			finalObject = compostBinStages.getBinWithRottenTomatoes();
		}

		player.getPacketSender().object(finalObject, x, y, z,
				CompostBinLocations.forId(index).getObjectFace(), 10);
	}

	/* handle what happens when the player close the compost bin */
	public void closeCompostBin(final int index) {
		compostBins[index] = tempCompostState * 100;
		compostBinsTimer[index] = GameEngine.getMinutesCounter();

		player.startAnimation(835, 0);
		player.stopPlayer(true);
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				player.getPacketSender()
						.sendMessage(
								"You close the compost bin, and its content start to rot.");
				updateCompostBin(index);
				container.stop();
			}

			@Override
			public void stop() {
				player.stopPlayer(false);
			}
		}, 2);
	}

	/* handle what happens when the player opens the compost bin */
	public void openCompostBin(final int index) {
		// check if the time elapsed is enough to rot the compost
		int timerRequired;
		timerRequired = compostBins[index] == 200 ? 90 : 45;
		if (GameEngine.getMinutesCounter() - compostBinsTimer[index] >= timerRequired) {
			compostBins[index] += 50;
			player.startAnimation(834, 0);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
					updateCompostBin(index);
					container.stop();
				}

				@Override
				public void stop() {
					player.stopPlayer(false);
				}
			}, 2);
		} else {
			player.getPacketSender()
					.sendMessage(
							"The compost bin is still rotting. I should wait until it is complete.");
		}
	}

	/* handle compost bin filling */
	@SuppressWarnings("unused")
	public void fillCompostBin(int x, int y, final int organicItemUsed) {
		final CompostBinLocations compostBinLocations = CompostBinLocations
				.forPosition(x, y);
		final int index = compostBinLocations.getCompostIndex();
		if (compostBinLocations == null) {
			return;
		}

		int incrementFactor = 0;
		// setting up the different increments.
		for (int normalCompost : COMPOST_ORGANIC) {
			if (organicItemUsed == normalCompost) {
				incrementFactor = 2;
			}
		}

		for (int superCompost : SUPER_COMPOST_ORGANIC) {
			if (organicItemUsed == superCompost) {
				incrementFactor = 17;
			}
		}

		if (organicItemUsed == TOMATO) {
			if (compostBins[index] % 77 == 0) {
				incrementFactor = 77;
			} else {
				incrementFactor = 2;
			}
		}

		// checking if the item used was an organic item.
		if (incrementFactor == 0) {
			player.getPacketSender()
					.sendMessage(
							"You need to put organic items into the compost bin in order to make compost.");
			return;
		}
		final int factor = incrementFactor;
		// launching the main event for filling the compost bin.
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (!player.getItemAssistant().playerHasItem(organicItemUsed)
						|| organicItemAdded[index] == 15) {
					container.stop();
					return;
				}
				organicItemAdded[index]++;
				player.startAnimation(832, 0);
				player.getItemAssistant().deleteItem(organicItemUsed, 1);
				compostBins[index] += factor;
				updateCompostBin(index);

			}

			@Override
			public void stop() {
				player.getPlayerAssistant().resetAnimation();
			}
		}, 2);
	}

	// handle what happens when the player retrieve the compost
	public void retrieveCompost(final int index) {

		final int finalItem = compostBins[index] == 150 ? COMPOST : compostBins[index] == 250 ? SUPER_COMPOST : ROTTE_TOMATO;

		player.startAnimation(832, 0);
		 CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (!player.getItemAssistant().playerHasItem(1925)
						&& compostBins[index] != 350
						|| organicItemAdded[index] == 0) {
					container.stop();
					return;
				}
				player.getPlayerAssistant().addSkillXP(finalItem == COMPOST ? COMPOST_EXP_RETRIEVE
										: finalItem == SUPER_COMPOST ? SUPER_COMPOST_EXP_RETRIEVE
												: ROTTEN_TOMATOES_EXP_RETRIEVE, SkillConstants.FARMING.ordinal());
				if (compostBins[index] != 350) {
					player.getItemAssistant().deleteItem(1925, 1);
				}
				player.getItemAssistant().addItem(finalItem, 1);
				player.startAnimation(832, 0);
				organicItemAdded[index]--;
				if (organicItemAdded[index] == 0) {
					resetVariables(index);
				}
				updateCompostBin(index);
			}

			@Override
			public void stop() {
				player.getPlayerAssistant().resetAnimation();
			}
		}, 2);
	}

	/* handling the item on object method */

	public boolean handleItemOnObject(int itemUsed, int objectId, int objectX,
			int objectY) {
		switch (objectId) {
		case 7814:
		case 7815:
		case 7816:
		case 7817:
		case 7824:
		case 7825:
		case 7826:
		case 7827:
			if (itemUsed == 1925) {
				retrieveCompost(CompostBinLocations.forPosition(objectX,
						objectY).getCompostIndex());
			} else {
				player.getPacketSender().sendMessage(
						"You might need some buckets to gather the compost.");
			}
			return true;

		case 7839:
		case 7838:
		case 7837:
		case 7836:
		case 7808:
		case 7809:
		case 7811:
		case 7819:
		case 7821:
		case 7828:
		case 7832:
			fillCompostBin(objectX, objectY, itemUsed);
			return true;

		}
		return false;
	}

	/* handling the object click method */

	public boolean handleObjectClick(int objectId, int objectX, int objectY) {

		switch (objectId) {

		case 7810:
		case 7812:
		case 7820:
		case 7822:
		case 7829:
		case 7833:
			closeCompostBin(CompostBinLocations.forPosition(objectX, objectY)
					.getCompostIndex());
			return true;

		case 7813:
		case 7823:
			openCompostBin(CompostBinLocations.forPosition(objectX, objectY)
					.getCompostIndex());
			return true;

		case 7830:
		case 7831:
		case 7834:
		case 7835:
			retrieveCompost(CompostBinLocations.forPosition(objectX, objectY)
					.getCompostIndex());
			return true;

		}
		return false;
	}

	/* reseting the compost variables */

	public void resetVariables(int index) {
		compostBins[index] = 0;
		compostBinsTimer[index] = 0;
		organicItemAdded[index] = 0;
	}

}
