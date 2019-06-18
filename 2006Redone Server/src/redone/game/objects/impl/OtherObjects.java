package redone.game.objects.impl;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.objects.ObjectDefaults;
import redone.game.players.Client;
import redone.util.Misc;
import redone.world.clip.ObjectDef;

public class OtherObjects {
	
	private final static int[] SPECIAL_OBJECTS = {160, 155, 156, 298, 299, 300, 304, 1181, 5253, 5254, 5255, 5256, 5257, 5258};
	
	public static void searchSpecialObject(Client player, int objectType) {
		for (int i = 0; i < SPECIAL_OBJECTS.length; i++) {
			if (objectType == SPECIAL_OBJECTS[i]) {
				if (System.currentTimeMillis() - player.searchObjectDelay < 1200 || objectType == 160 && player.absX != 3096 || objectType > 154 && objectType < 157 && player.absX != 3098 || player.absY == 3301) {
					return;
				}
				player.stopPlayerPacket = true;
				player.searchObjectDelay = System.currentTimeMillis();
				handleSpecialObject(player, objectType);
			}
		}
	}
	
	private static void object(Client player, int id, int x, int y) {
		player.getActionSender().object(id, x, y, 0, ObjectDefaults.getObjectFace(player, id), 10);
	}
	
	private static void handleSpecialObject(final Client player, final int objectType) {	
		String objectName = ObjectDef.getObjectDef(objectType).name;
			if (objectType == 160 && player.absX == 3096) {
					player.getPlayerAssistant().walkTo(0, 1);
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {	
						@Override
						public void execute(CycleEventContainer container) {
							player.getPlayerAssistant().movePlayer(3098, player.absY, 0);
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
									player.stopPlayerPacket = false;
								}	
							}, 2);
						}
						@Override
						public void stop() {
							object(player, 155, 3097, 3357);
							object(player, 156, 3097, 3360);
						}
					}, 1);
					} else if (objectType > 154 && objectType < 157 && player.absX == 3098) {
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
										player.stopPlayerPacket = false;
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
						player.stopPlayerPacket = false;
					} else {
						player.setHitUpdateRequired2(true);
						player.setHitDiff2(nettlesDamage);
						player.updateRequired = true;
						player.poisonMask = 2;
						player.dealDamage(nettlesDamage);
						player.getPlayerAssistant().refreshSkill(3);
						player.getActionSender().sendMessage("You have been stung by the nettles.");
						player.stopPlayerPacket = false;
					}
				} else if (objectName.startsWith("Hay") || objectName.startsWith("hay")) {
					final int damage = 1, random = Misc.random(15);
					player.startAnimation(832);
					player.getActionSender().sendMessage("You search the " + objectName.toLowerCase() + "...");
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (random == 1) {
								player.getDialogueHandler().sendStatement2("Wow! A needle!", "Now what are the chances of finding that?");
								player.nextChat = 0;
								player.getItemAssistant().addItem(1733, 1);
								container.stop();
							} else if (random == 9) {
								player.handleHitMask(damage);
								player.dealDamage(damage);
								player.getPlayerAssistant().refreshSkill(3);
								container.stop();
							} else {
								player.getActionSender().sendMessage("You find nothing of interest.");
								container.stop();
							}
						}

						@Override
						public void stop() {
							player.stopPlayerPacket = false;
						}
					}, 2);
				}
			}

}
