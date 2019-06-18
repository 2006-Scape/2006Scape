package redone.game.content.skills.herblore;

import redone.game.content.music.sound.SoundList;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

public class GrindingAction {

	public static final int PESTLE_AND_MORTAR = 233;

	public enum Data {
		UNICORN(237, 235), CHOCO(1973, 1975), NEST(5075, 6693), SCALE(243, 241), SHARDS(
				6466, 6467), WEED1(401, 6683), WEED2(403, 6683), BAT(530, 2391), CHARCOAL(
				973, 704), COD(341, 7528), KELP(7516, 7517), CRABMEAT(7518,
				7527), THISLE(3263, 3264), MUSHROOM(4620, 4622);

		public int id;
		public int end;

		Data(int id, int end) {
			this.id = id;
			this.end = end;
		}

		public int getId() {
			return id;
		}

		public int getEnd() {
			return end;
		}
	}

	public static void init(Client c, int itemUsed, int useWith) {
		for (Data d : Data.values()) {
			if (itemUsed == PESTLE_AND_MORTAR && useWith == d.getId()
					|| itemUsed == d.getId() && useWith == PESTLE_AND_MORTAR) {
				c.startAnimation(364);
				c.getActionSender().sendSound(SoundList.PESTLE_MOTAR, 100,
						0);
				c.getItemAssistant().deleteItem(d.getId(), 1);
				c.getItemAssistant().addItem(d.getEnd(), 1);
				c.getActionSender().sendMessage(
						"You carefully grind the "
								+ ItemAssistant.getItemName(d.getId()) + ".");
				c.getPlayerAssistant().addSkillXP(1, c.playerHerblore);
			}
		}
	}
}
