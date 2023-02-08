package com.rs2.game.objects.impl;

import com.rs2.Constants;
import org.apollo.cache.def.ObjectDefinition;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.traveling.DesertHeat;
import com.rs2.game.objects.ObjectDefaults;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;
import com.rs2.world.clip.Region;

public class OtherObjects {

	public static boolean openKharid(Player player, int objectId) {
		return (objectId == 2882 || objectId == 2883);
	}

	public static boolean openShantay(Player player, int objectId) {
		return (objectId == 4033 || objectId == 4031);
	}

	public static void movePlayer(Player player) {
		if (Region.getClipping(player.getX() - 1, player.getY(), player.getH(), -1, 0)) {
			player.getPlayerAssistant().movePlayer(player.getX() + 1, player.getY(), 0);
		} else if (Region.getClipping(player.getX() + 1, player.getY(), player.getH(), 1, 0)) {
			player.getPlayerAssistant().movePlayer(player.getX() - 1, player.getY(), 0);
		} else if (Region.getClipping(player.getX(), player.getY() - 1, player.getH(), 0, -1)) {
			player.getPlayerAssistant().movePlayer(player.getX(), player.getY() + 1, 0);
		} else if (Region.getClipping(player.getX(), player.getY() + 1, player.getH(), 0, 1)) {
			player.getPlayerAssistant().movePlayer(player.getX(), player.getY() - 1, 0);
		}
	}
	
	public static void interactCurtain(Player player, int objectType, int x, int y) {
		if (objectType == 1529) {
			GameEngine.objectHandler.createAnObject(1528, x, y, player.getH(), ObjectDefaults.getObjectFace(player, objectType), ObjectDefaults.getObjectType(player, objectType));
		} else if (objectType == 1528) {
			GameEngine.objectHandler.createAnObject(1529, x, y, player.getH(), ObjectDefaults.getObjectFace(player, objectType), ObjectDefaults.getObjectType(player, objectType));
		}
	}

	public static void initKharid(Player player, int objectId) {
		if (!player.getItemAssistant().playerHasItem(995, 10)) {
			player.getDialogueHandler().itemMessage("You need 10 coins to pass through this gate.", 995, 200);
			player.getDialogueHandler().endDialogue();
			return;
		}
		if (openKharid(player, objectId)) {
			//GameEngine.objectHandler.createAnObject(player, -1, player.objectX, player.objectY, -1);
			final int[] coords = new int[2];
			if (player.getX() == 3267) {
				player.getPlayerAssistant().movePlayer(player.getX() + 1, player.getY(), 0);
			} else if (player.getX() == 3268) {
				player.getPlayerAssistant().movePlayer(player.getX() - 1, player.getY(), 0);
			}
			player.turnPlayerTo(player.objectX, player.objectY);
			coords[0] = player.objectX;
			coords[1] = player.objectY;
			player.getItemAssistant().deleteItem(995, player.getItemAssistant().getItemSlot(995), 10);
		}
	}

	private static boolean movePlayer2(Player player) {
		if (player.getY() == 3117) {
			player.getPlayerAssistant().movePlayer(player.getX(), player.getY() - 2, 0);
			return true;
		} else if (player.getY() == 3115) {
			player.getPlayerAssistant().movePlayer(player.getX(), player.getY() + 2, 0);
			return true;
		}
		player.getPacketSender().sendMessage("Move closer so you can use the gate.");
		return false;
	}

	public static void initShantay(Player player, int objectId) {
		if (!player.getItemAssistant().playerHasItem(1854, 1) && player.getY() == 3117) {
			player.getDialogueHandler().sendStatement("You need a Shantay pass to go through.");
			return;
		}
		final int[] coords = new int[2];
		if (openShantay(player, objectId)) {
			player.getPacketSender().sendMessage("You pass through the gate.");
			movePlayer2(player);
			player.turnPlayerTo(player.objectX, player.objectY);
			coords[0] = player.objectX;
			coords[1] = player.objectY;
			if (player.desertWarning == false && player.getY() == 3117) {
				DesertHeat.showWarning(player);
				player.desertWarning = true;
			}
		}
		if (player.getY() == 3117) {
			player.getItemAssistant().deleteItem(1854, player.getItemAssistant().getItemSlot(1854), 1);
		}
	}

	
	private final static int[] SPECIAL_OBJECTS = {160, 155, 156, 298, 299, 300, 304, 1181, 5253, 5254, 5255, 5256, 5257, 5258};
	
	public static void searchSpecialObject(Player player, int objectType) {
		for (int i = 0; i < SPECIAL_OBJECTS.length; i++) {
			if (objectType == SPECIAL_OBJECTS[i]) {
				if (System.currentTimeMillis() - player.getObjectDelay() < 1200 || objectType == 160 && player.getX() != 3096 || objectType > 154 && objectType < 157 && player.getX() != 3098 || player.getY() == 3301) {
					return;
				}
				player.stopPlayer(true);
				player.setObjectDelay(System.currentTimeMillis());
				handleSpecialObject(player, objectType);
			}
		}
	}
	
	private static void object(Player player, int id, int x, int y) {
		player.getPacketSender().object(id, x, y, 0, ObjectDefaults.getObjectFace(player, id), 10);
	}
	
	private static void handleSpecialObject(final Player player, final int objectType) {	
		String objectName = ObjectDefinition.lookup(objectType).getName();
		if (objectType == 160 && player.getX() == 3096) {
			player.getPlayerAssistant().walkTo(0, 1);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {	
				@Override
				public void execute(CycleEventContainer container) {
					player.getPlayerAssistant().movePlayer(3098, player.getY(), 0);
					container.stop();
				}
				@Override
				public void stop() {
					
				}
			}, 2);
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {	
				@Override
				public void execute(CycleEventContainer container) {
					object(player, -1, 3097, 3358);
					object(player, -1, 3097, 3359);
					container.stop();
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							object(player, -1, 3097, 3357);
							object(player, -1, 3097, 3360);
							container.stop();
						}
						@Override
						public void stop() {
							object(player, 155, 3097, 3358);
							object(player, 156, 3097, 3359);
							player.stopPlayer(false);
						}	
					}, 2);
				}
				@Override
				public void stop() {
					object(player, 155, 3097, 3357);
					object(player, 156, 3097, 3360);
				}
			}, 1);
			} else if (objectType > 154 && objectType < 157 && player.getX() == 3098) {
				player.getPlayerAssistant().walkTo(-2, 0);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {	
					@Override
					public void execute(CycleEventContainer container) {
						object(player, -1, 3097, 3358);
						object(player, -1, 3097, 3359);
						container.stop();
						CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
							@Override
							public void execute(CycleEventContainer container) {
								object(player, -1, 3097, 3357);
								object(player, -1, 3097, 3360);
								container.stop();
							}
							@Override
							public void stop() {
								object(player, 155, 3097, 3358);
								object(player, 156, 3097, 3359);
								player.stopPlayer(false);
							}	
						}, 2);
					}
					@Override
					public void stop() {
						object(player, 155, 3097, 3357);
						object(player, 156, 3097, 3360);
					}
				}, 1);
		} else if (objectName.contains("Nettles")) {
			int nettlesDamage = 1 + Misc.random(1);
			if (player.playerEquipment[player.playerHands] > 0) {
				player.startAnimation(827);
				player.getItemAssistant().addItem(4241, 1);
				player.stopPlayer(false);
			} else {
				player.setHitUpdateRequired2(true);
				player.setHitDiff2(nettlesDamage);
				player.updateRequired = true;
				player.poisonMask = 2;
				player.dealDamage(nettlesDamage);
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				player.getPacketSender().sendMessage("You have been stung by the nettles.");
				player.stopPlayer(false);
			}
		} else if (objectName.startsWith("Hay") || objectName.startsWith("hay")) {
			final int damage = 1, random = Misc.random(15);
			player.startAnimation(832);
			player.getPacketSender().sendMessage("You search the " + objectName.toLowerCase() + "...");
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (random == 1) {
						player.getDialogueHandler().sendStatement("Wow! A needle!", "Now what are the chances of finding that?");
						player.getDialogueHandler().endDialogue();
						player.getItemAssistant().addItem(1733, 1);
						container.stop();
					} else if (random == 9) {
						player.handleHitMask(damage);
						player.dealDamage(damage);
						player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
						container.stop();
					} else {
						player.getPacketSender().sendMessage("You find nothing of interest.");
						container.stop();
					}
				}

				@Override
				public void stop() {
					player.stopPlayer(false);
				}
			}, 2);
		}
	}

}