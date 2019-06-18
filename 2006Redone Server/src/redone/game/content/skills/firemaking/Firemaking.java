package redone.game.content.skills.firemaking;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.content.skills.SkillHandler;
import redone.game.content.skills.cooking.Cooking;
import redone.game.items.ItemAssistant;
import redone.game.objects.Object;
import redone.game.players.Client;
import redone.util.Misc;
import redone.world.clip.Region;

public class Firemaking {

	public static boolean stopFiremaking = false, pickedUpFiremakingLog = false, logLit;
	
	public static void stopFiremaking(Client c) {
		c.startAnimation(65535);
		SkillHandler.lastSkillingAction = System.currentTimeMillis();
		c.isFiremaking = false;
		Cooking.resetCooking(c);
		logLit = false;
	}
	
	public static void attemptFire(final Client c, final int itemUsed, final int usedWith, final int x, final int y, final boolean groundObject) {
		int firemakingItems[] = {590, 7329, 7330, 7331};
		for (int i = 0; i < firemakingItems.length; i++) {
		if (Firemaking.pickedUpFiremakingLog == true) {
			c.getActionSender().sendMessage("You can't do that!");
			Firemaking.pickedUpFiremakingLog = false;
			return;
		}
		if (c.isFiremaking && logLit == false) {
			return;
		}
		if (!SkillHandler.FIREMAKING) {
			c.getActionSender().sendMessage("This skill is currently disabled.");
			return;
		}
		for (final LogData l : LogData.values()) {
			final int logId = usedWith == firemakingItems[i] ? itemUsed : usedWith;
			if (logId == l.getLogId()) {
				if (c.playerLevel[11] < l.getLevel()) {
					c.getActionSender().sendMessage("You need a firemaking level of " + l.getLevel() + " to light " + ItemAssistant.getItemName(logId));
					return;
				}
				if (c.inBank() || c.inLumbBuilding() || c.inDraynorBuilding()) {
					c.getActionSender().sendMessage("You cannot light a fire here.");
					return;
				}
				if (Server.objectManager.objectExists(c.absX, c.absY)) {
					c.getActionSender().sendMessage("You cannot light a fire here.");
					return;
				}
				c.isFiremaking = true;
				logLit = false;
				boolean notInstant = System.currentTimeMillis() - SkillHandler.lastSkillingAction > 2500;
				int cycle = 2;
				if (notInstant) {
					c.getActionSender().sendMessage("You attempt to light a fire.");
					c.getActionSender().sendSound(SoundList.FIRE_LIGHT, 100, 0);
					if (!notInstant) {
						c.getActionSender().sendSound(SoundList.FIRST_ATTEMPT, 100, 0);// testing
					}
					if (c.tutorialProgress == 4) {
						c.getActionSender().chatbox(6180);
						c.getDialogueHandler().chatboxText(c, "", "Your character is now attempting to light the fire.", "This should only take a few seconds.", "", "Please wait");
						c.getActionSender().chatbox(6179);
					}
					if (groundObject == false) {
						c.getItemAssistant().deleteItem(logId, c.getItemAssistant().getItemSlot(logId), 1);
						Server.itemHandler.createGroundItem(c, logId, c.absX, c.absY, 1, c.playerId);
					}
					cycle = 3 + Misc.random(6);
				} else {
					if (groundObject == false) {
						c.getItemAssistant().deleteItem(logId, c.getItemAssistant().getItemSlot(logId), 1);
					}
				}
				final boolean walk;
				if (Region.getClipping(x - 1, y, c.heightLevel, -1, 0)) {
					walk = true;
				} else {
					walk = false;
				}
				c.startAnimation(733);
				c.getPlayerAssistant().walkTo(walk == true ? -1 : 1, 0);
				stopFiremaking = false;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						if (stopFiremaking == true) {
							stopFiremaking = false;
							return;
						}
						if (c.isWoodcutting || c.playerIsFletching || c.isFletching) {
							container.stop();
						}
						if (c.isFiremaking == true) {
							Server.itemHandler.removeGroundItem(c, logId, x, y, false);
							c.getActionSender().sendSound(SoundList.FIRE_SUCCESSFUL, 100, 0);
							if (itemUsed == 7331 || usedWith == 7331)
								new Object(11406, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							else if (itemUsed == 7330 || usedWith == 7330)
								new Object(11405, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							else if (itemUsed == 7329 || usedWith == 7329)
								new Object(11404, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							else
								new Object(2732, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							c.getActionSender().sendMessage("The fire catches and the log beings to burn.");
							c.getPlayerAssistant().addSkillXP((int) l.getXp(), 11);
							if (c.tutorialProgress == 4) {
								c.getDialogueHandler().sendDialogues(3016, 943);
							}
							CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

										@Override
										public void execute(CycleEventContainer container) {
											c.turnPlayerTo(walk == true ? x + 1 : x - 1, y);
											logLit = true;
											stopFiremaking(c);
											container.stop();
										}

										@Override
										public void stop() {
										}
									}, 2);
							container.stop();
						} else {
							return;
						}
					}

					@Override
					public void stop() {
						stopFiremaking(c);
					}
				}, cycle);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.playerIsCooking) {
							Cooking.resetCooking(c);
						}
						Server.objectHandler.createAnObject(c, -1, x, y);
						Server.itemHandler.createGroundItem(c, 592, x, y, 1, c.getId());
						container.stop();
					}
					@Override
					public void stop() {

					}
					}, 60);
				}
			}
		}
	}
}
