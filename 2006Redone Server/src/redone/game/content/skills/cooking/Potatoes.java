package redone.game.content.skills.cooking;

import java.util.HashMap;
import java.util.Map;

import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.players.Client;

public class Potatoes extends SkillHandler {

	Client c;

	public Potatoes(Client c) {
		this.c = c;
	}

	public enum PotatoMaking {
		// first item, new item, xp, level required
		// BUTTERED(6701, 6703, 6697, 95, 39),
		CHILLI(7062, 7054, 165, 41), CHEESE(1985, 6705, 199, 47), EGG(7064,
				7056, 195, 51), MUSHROOM(7066, 7058, 27, 64), TUNA(7068, 7060,
				309, 68);

		/**
		 * Seperate integers for the id's.
		 */
		private int newPotatoID, ingredient, XP, levelReq;

		/**
		 * @param ingredient
		 * @param newPotatoID
		 * @param XP
		 * @param levelReq
		 */
		private PotatoMaking(int ingredient, int newPotatoID, int XP,
				int levelReq) {
			// this.potatoID = potatoID;
			this.ingredient = ingredient;
			this.newPotatoID = newPotatoID;
			this.levelReq = levelReq;
			this.XP = XP;
		}

		public int getNewPotatoID() {
			return newPotatoID;
		}

		public int getIngredient() {
			return ingredient;
		}

		public int getReq() {
			return levelReq;
		}

		public int getXP() {
			return XP;
		}

		private static final Map<Integer, PotatoMaking> potato = new HashMap<Integer, PotatoMaking>();

		public static PotatoMaking forId(int id) {
			return potato.get(id);
		}

		static {
			for (PotatoMaking p : PotatoMaking.values()) {
				potato.put(p.getIngredient(), p);
			}
		}
	}

	/**
	 * Id used with one or the other
	 * 
	 * @param id1
	 * @param id2
	 */
	public void handlePotato(int id1, int id2) {
		makePotato(id1 == 6703 ? id2 : id1);
	}

	/**
	 * Creating the actual item and replacing the id's
	 * 
	 * @param id
	 * @return
	 */
	public boolean makePotato(int id) {
		PotatoMaking potato = PotatoMaking.forId(id);
		if (potato == null) {
			return false;
		}
		if (!COOKING) {
			c.getActionSender().sendMessage(
					"This skill is currently disabled.");
			return false;
		}
		if (c.getItemAssistant().playerHasItem(potato.getIngredient(), 1)) {
			if (c.playerLevel[c.playerCooking] >= potato.getReq()) {
				c.getItemAssistant().deleteItem(potato.getIngredient(), 1);
				c.getItemAssistant().deleteItem(6703, 1);
				c.getActionSender().sendMessage("You put the topping on.");
				c.getItemAssistant().addItem(potato.getNewPotatoID(), 1);
				c.getPlayerAssistant().addSkillXP(
						potato.getXP() * COOKING_EXPERIENCE, c.playerCooking);
				RandomEventHandler.addRandom(c);
			} else {
				c.getActionSender().sendMessage(
						"You need a cooking level of " + potato.getReq()
								+ " to make this potato.");
			}
		}
		return false;
	}

}
