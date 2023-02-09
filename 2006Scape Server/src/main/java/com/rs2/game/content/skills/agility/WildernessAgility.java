package com.rs2.game.content.skills.agility;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Player;

public class WildernessAgility {

	private final Player c;

	public WildernessAgility(Player player) {
		this.c = player;
	}

	public static final int WILDERNESS_PIPE_OBJECT = 2288,
			WILDERNESS_SWING_ROPE_OBJECT = 2283,
			WILDERNESS_STEPPING_STONE_OBJECT = 2311,
			WILDERNESS_LOG_BALANCE_OBJECT = 2297,
			WILDERNESS_ROCKS_OBJECT = 2328;

	public boolean wildernessCourse(final int objectId) {
		switch (objectId) {
			case WILDERNESS_PIPE_OBJECT: // pipe
				if (c.getAgility().checkLevel(objectId)) {
					return false;
				}
				if ((c.absX >= 3003 && c.absX <= 3006) && (c.absY >= 3945 && c.absY <= 3954)) {
					return false;
				}

				if (c.getAgility().hotSpot(3004, 3937)) {
					c.getAgility().walk(0, 13,
							c.getAgility().getAnimation(objectId), -1);
				} else if ((c.absX >= 3003 && c.absX <= 3005) && (c.absY > 3937 && c.absY < 3950)) {
					c.getPlayerAssistant().movePlayer(3004, 3950, 0);
				}
				c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
				return true;
			case WILDERNESS_SWING_ROPE_OBJECT:
				if (c.getAgility().checkLevel(objectId)) {
					return false;
				}
				if (c.getAgility().hotSpot(3005, 3953) || ((c.absX >= 3004 && c.absX <= 3006) && (c.absY > 3950 && c.absY < 3953))) {
					c.getAgility().walk(0, 1,
							c.getAgility().getAnimation(objectId), -1);
					if (c.getAgility().agilityProgress[0]) {
						c.getAgility().agilityProgress[1] = true;
					}
					c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (c.disconnected) {
								container.stop();
								return;
							}
							c.getPlayerAssistant().movePlayer(3005, 3958, 0);
							container.stop();
						}
						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				}
				return true;
			case WILDERNESS_STEPPING_STONE_OBJECT:
				if (c.getAgility().checkLevel(objectId)) {
					return false;
				}
				if (c.getAgility().hotSpot(3002, 3960)) {
					c.getAgility().walk(-6, 0,
							c.getAgility().getAnimation(objectId), -1);
				} else if (c.absX > 2996 && c.absX < 3002 && c.absY == 3960) {
					c.getPlayerAssistant().movePlayer(2996, 3960, 0);
				}
				c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
				c.getAgility().steppingStone = 6;
				c.getAgility().steppingStoneTimer = 2;
				c.getAgility().steppingStone--;
				if (c.getAgility().agilityProgress[2]) {
					c.getAgility().agilityProgress[3] = true;
				}
				return true;

			case WILDERNESS_LOG_BALANCE_OBJECT:
				if (c.getAgility().checkLevel(objectId)) {
					return false;
				}
				if (c.getAgility().hotSpot(3002, 3945) || ((c.absX >= 3001 && c.absX <= 3002) && (c.absY >= 3944 && c.absY <= 3946))) {
					if (c.absY != 3945)
					{
						c.getPlayerAssistant().movePlayer(2994, ((3945 - c.absY) + c.absY), 0);
					}
					else
					{
						c.getAgility().walk(-8, 0,
								c.getAgility().getAnimation(objectId), -1);
					}

					if (c.getAgility().agilityProgress[3]) {
						c.getAgility().agilityProgress[5] = true;
					}
					c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
				} else if (c.absX > 2994 && c.absX < 3002 && c.absY == 3945) {
					c.getPlayerAssistant().movePlayer(2994, 3945, 0);
				}
				return true;

			case WILDERNESS_ROCKS_OBJECT:
				if (c.getAgility().checkLevel(objectId)) {
					return false;
				}
				c.getAgility().walk(0, -4, c.getAgility().getAnimation(objectId), -1);
				if (c.getAgility().agilityProgress[5]) {
					c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
					c.getAgility().lapBonus = 2400 / 30;
					c.getAgility().lapFinished();
				} else {
					c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), Constants.AGILITY);
				}
				return true;
		}
		return false;
	}

}
