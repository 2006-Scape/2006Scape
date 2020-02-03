package com.rebotted.game.content.skills.prayer;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;

/**
 * Aug 31, 2017 : 5:29:02 PM
 * Ectofuntus.java
 * @author Andrew (Mr Extremez)
 * @author Haley n (most of this ectofuntus base is hers)
 */
public class Ectofuntus {

	/**
	 * Data storage for ectofuntus
	 */
	public enum EctofuntusData {
		BONES(526, 4255, 18), BIG_BONES(532, 4257, 60), BABYDRAGON_BONES(534,
				4260, 120), DRAGON_BONES(536, 4261, 288), DAGANNOTH_BONES(6729,
				6728, 500);

		int boneId, bonemealId, worshipExperience;

		EctofuntusData(int boneId, int bonemealId, int worshipExperience) {
			this.boneId = boneId;
			this.bonemealId = bonemealId;
			this.worshipExperience = worshipExperience;
		}

		public int getBoneId() {
			return boneId;
		}

		public int getBonemealId() {
			return bonemealId;
		}

		public int getWorshipExperience() {
			return worshipExperience;
		}
	}

	/**
	 * Get the name of the bone used
	 * 
	 * @param boneId
	 * @return
	 */
	public static String getBoneName(int boneId) {
		for (EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (ectofuntus.boneId == boneId) {
				return ectofuntus.name().replaceAll("_", " ").toLowerCase();
			}
		}
		return "";
	}

	/**
	 * Put bones in the loader
	 * 
	 * @param objectId
	 * @param boneId
	 */
	public static void boneOnLoader(Player player, int objectId, int boneId) {
		for (final EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (player.ectofuntusBoneCrusherState.equals("Loaded")) {
				player.getPacketSender().sendMessage("There are already bone ready to be crushed.");
				return;
			} else if (player.ectofuntusBoneCrusherState.equals("Bin")) {
				player.getPacketSender().sendMessage("The bin is full please empty it.");
				return;
			} else if (objectId == LOADER && ectofuntus.boneId == boneId && player.ectofuntusBoneCrusherState.equals("Empty")) {
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.startAnimation(BONES);
						player.getItemAssistant().deleteItem(ectofuntus.getBoneId(), 1);
						player.ectofuntusBoneCrusherState = "Loaded";
						player.ectofuntusBoneUsed = ectofuntus.getBoneId();
						player.getPacketSender().sendMessage("You load the " + getBoneName(ectofuntus.getBoneId()) + " into the loader.");
						container.stop();
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
				}, 3);
			}
		}
	}

	/**
	 * Grinds the bones
	 */
	public static void useBoneGrinder(Player player) {
		if (player.ectofuntusBoneCrusherState.equals("Loaded")) {
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					player.startAnimation(WHEEL);
					player.ectofuntusBoneCrusherState = "Bin";
					player.getPacketSender().sendMessage("You crush the bones.");
					container.stop();
				}

				@Override
				public void stop() {
					// TODO Auto-generated method stub
					
				}
			}, 3);
		} else if (player.ectofuntusBoneCrusherState.equals("Empty") || player.ectofuntusBoneCrusherState.equals("Bin")) {
			player.getPacketSender().sendMessage("There is nothing to be crushed.");
		}
	}

	/**
	 * Emptys the bin into pots
	 */
	public static void emptyBin(Player player) {
		for (final EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (player.ectofuntusBoneCrusherState.equals("Loaded") || player.ectofuntusBoneCrusherState.equals("Empty")) {
				player.getPacketSender().sendMessage("There is nothing to be crushed.");
				return;
			} else if (!player.getItemAssistant().playerHasItem(POT)) {
				player.getPacketSender().sendMessage("You need a pot to collect the bonemeal.");
				return;
			} else if (player.getItemAssistant().playerHasItem(POT) && ectofuntus.boneId == player.ectofuntusBoneUsed && player.ectofuntusBoneCrusherState.equals("Bin")) {
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.getItemAssistant().addItem(ectofuntus.getBonemealId(), 1);
						player.getItemAssistant().deleteItem(POT, 1);
						player.ectofuntusBoneCrusherState = "Empty";
						player.ectofuntusBoneUsed = -1;
						player.getPacketSender().sendMessage("You collect the bonemeal.");
						container.stop();
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
				}, 3);
			}
		}
	}

	/**
	 * Handles ectofuntus
	 * 
	 * @param objectId
	 * @param itemId
	 */
	public static void handleEctofuntus(Player player, int objectId) {
		player.turnPlayerTo(3660, 3520);
		for (final EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (objectId == ECTOFUNTUS && player.getItemAssistant().playerHasItem(ectofuntus.getBonemealId()) && player.getItemAssistant().playerHasItem(BUCKET_OF_SLIME)) {
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.getItemAssistant().deleteItem(ectofuntus.getBonemealId(), 1);
						player.startAnimation(WORSHIP);
						player.getPlayerAssistant().addSkillXP(ectofuntus.getWorshipExperience(), player.playerPrayer);
						player.getPacketSender().sendMessage("You pray to the ectofuntus.");
						container.stop();
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
				}, 3);
			}
		}
	}

	/**
	 * Fills all buckets in your inventory individualy with slime
	 * 
	 * @param objectId
	 * @param itemId
	 */
	public static void fillBucketWithSlime(Player player, final int objectId) {
		for (int i = 0; i < SLIME.length; i++) {
			if (objectId == SLIME[i]) {
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.getItemAssistant().playerHasItem(BUCKET)) {
							player.getItemAssistant().deleteItem(BUCKET, 1);
							player.getItemAssistant().addItem(BUCKET_OF_SLIME, 1);
							player.startAnimation(BUCKET_FILL);
						} else {
							player.getPacketSender().sendMessage("You have ran out of buckets.");
							container.stop();
						}
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
				}, 3);
			}
		}
	}

	/**
	 * item constants
	 */
	private final static int POT = 1931;
	public final static int BUCKET = 1925;
	private final static int BUCKET_OF_SLIME = 4286;

	/**
	 * object constants
	 */
	private final static int ECTOFUNTUS = 5282;
	private final static int LOADER = 11162;
	private final static int[] SLIME = { 5461, 5462 };
	
	/**
	 * animation constants
	 */
	private final static int WORSHIP = 1651;
	private final static int WHEEL = 1648;
	private final static int BONES = 1649;
	private final static int BUCKET_FILL = 895;

}