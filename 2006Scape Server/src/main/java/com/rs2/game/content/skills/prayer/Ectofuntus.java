package com.rs2.game.content.skills.prayer;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

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
		BONES(526, 4255, 18),
		BURNT_BONES(528, 4258, 18),
		WOLF_BONES(2859, 4262, 18),
		BAT_BONES(530, 4256, 21.2),
		BIG_BONES(532, 4257, 60),
		JOGRE_BONES(3125, 4271, 60),
		BURNT_JOGRE_BONES(3127, 4259, 60),
		BABY_DRAGON_BONES(534, 4260, 120),
		DRAGON_BONES(536, 4261, 288),
		SMALL_NINJA_MONKEY_BONES(3179, 4263, 20),
		MEDIUM_NINJA_MONKEY_BONES(3180, 4264, 20),
		SMALL_ZOMBIE_MONKEY_BONES(3183, 4268, 20),
		LARGE_ZOMBIE_MONKEY_BONES(3181, 4269, 20),
		GORILLA_MONKEY_BONES(3182, 4265, 20),
		BEARDED_GORILLA_MONKEY_BONES(3182, 4266, 20),
		MONKEY_BONES(3185, 4267, 20),
		SKELETON_BONES(2530, 4270, 20),
		ZOGRE_BONES(4812, 4852, 90),
		FAYRG_BONES(4830, 4853, 336),
		RAURG_BONES(4832, 4854, 384),
		OURG_BONES(4834, 4855, 560),
		SHAIKAHAN_BONES(3123, 5615, 100),
		DAGANNOTH_BONES(6729, 6728, 500),
		WYVERN_BONES(6812, 6810, 200);

		int boneId, bonemealId;
		double worshipExperience;

		EctofuntusData(int boneId, int bonemealId, double worshipExperience) {
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

		public double getWorshipExperience() {
			return worshipExperience;
		}
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
						player.getPacketSender().sendMessage("You load the " + DeprecatedItems.getItemName(ectofuntus.getBoneId()) + " into the loader.");
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
	public static void handleEctofuntus(Player player) {
		player.turnPlayerTo(3660, 3520);
		boolean worshipped = false;
		for (final EctofuntusData ectofuntus : EctofuntusData.values()) {
			if (!worshipped && player.getItemAssistant().playerHasItem(ectofuntus.getBonemealId()) && player.getItemAssistant().playerHasItem(BUCKET_OF_SLIME)) {
				worshipped = true;
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.isMoving || !player.getItemAssistant().playerHasItem(ectofuntus.getBonemealId()) || !player.getItemAssistant().playerHasItem(BUCKET_OF_SLIME)) {
							container.stop();
							return;
						}
						player.getItemAssistant().replaceItem(ectofuntus.getBonemealId(), 1931);
						player.getItemAssistant().replaceItem(BUCKET_OF_SLIME, 1925);// Bucket
						player.startAnimation(WORSHIP);
						player.getPlayerAssistant().addSkillXP(ectofuntus.getWorshipExperience(), Constants.PRAYER);
						player.getPacketSender().sendMessage("You pray to the ectofuntus.");
						player.ectofuntusWorshipped++;
						if (player.isMoving || !player.getItemAssistant().playerHasItem(ectofuntus.getBonemealId()) || !player.getItemAssistant().playerHasItem(BUCKET_OF_SLIME)) {
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

		if (!worshipped) {
			player.getPacketSender().sendMessage("You'll need ectoplasm and bonemeal to worship the Ectofuntus.");
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
	public final static int POT = 1931;
	public final static int BUCKET = 1925;
	public final static int BUCKET_OF_SLIME = 4286;

	/**
	 * object constants
	 */
	public final static int ECTOFUNTUS = 5282;
	public final static int LOADER = 11162;
	public final static int GRINDER = 11163;
	public final static int BIN = 11164;
	public final static int[] SLIME = { 5461, 5462 };
	
	/**
	 * animation constants
	 */
	public final static int WORSHIP = 1651;
	public final static int WHEEL = 1648;
	public final static int BONES = 1649;
	public final static int BUCKET_FILL = 895;

}