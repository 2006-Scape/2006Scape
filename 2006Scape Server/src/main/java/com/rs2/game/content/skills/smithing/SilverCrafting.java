package com.rs2.game.content.skills.smithing;

import java.util.HashMap;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

public class SilverCrafting {

	public static final int SILVER_BAR = 2355;
	private static final int SILVER_ANIMATION = 899;

	public static enum SilverCraft {
		UNBLESSED(34205, 2355, 1716, 1, 16, 50), UNBLESSED5(34204, 2355, 1716,
				5, 16, 50), UNBLESSED10(34203, 2355, 1716, 10, 16, 50), UNBLESSEDX(
				34202, 2355, 1716, 0, 16, 50), UNHOLY(34209, 2355, 1724, 1, 17,
				50), UNHOLY5(34208, 2355, 1724, 5, 17, 50), UNHOLY10(34207,
				2355, 1724, 10, 17, 50), UNHOLYX(34206, 2355, 1724, 0, 17, 50), SICKLE(
				34213, 2355, 2961, 1, 18, 50), SICKLE5(34212, 2355, 2961, 5,
				18, 50), SICKLE10(34211, 2355, 2961, 10, 18, 50), SICKLEX(
				34210, 2355, 2961, 0, 18, 50), TIARA(34217, 2355, 5525, 1, 23,
				52.5), TIARA5(34216, 2355, 5525, 5, 23, 52.5), TIARA10(34215,
				2355, 5525, 10, 23, 52.5), TIARAX(34214, 2355, 5525, 0, 23,
				52.5);

		private final int buttonId;
		private final int used;
		private final int result;
		private final int amount;
		private final int level;
		private final double experience;

		public static HashMap<Integer, SilverCraft> silverItems = new HashMap<Integer, SilverCraft>();

		public static SilverCraft forId(int id) {
			return silverItems.get(id);
		}

		static {
			for (SilverCraft data : SilverCraft.values()) {
				silverItems.put(data.buttonId, data);
			}
		}

		private SilverCraft(int buttonId, int used, int result, int amount,
				int level, double experience) {
			this.buttonId = buttonId;
			this.used = used;
			this.result = result;
			this.amount = amount;
			this.level = level;
			this.experience = experience;
		}

		public int getButtonId() {
			return buttonId;
		}

		public int getUsed() {
			return used;
		}

		public int getResult() {
			return result;
		}

		public int getAmount() {
			return amount;
		}

		public int getLevel() {
			return level;
		}

		public double getExperience() {
			return experience;
		}

	}

	public static void makeSilver(final Player player, int buttonId,
			final int amount) {
		final SilverCraft silverCraft = SilverCraft.forId(buttonId);
		if (silverCraft == null || silverCraft.getAmount() == 0 && amount == 0) {
			return;
		}
		if (silverCraft.getUsed() == SILVER_BAR && player.isCrafting) {
			if (!SkillHandler.CRAFTING) {
				player.getPacketSender().sendMessage("This skill is currently disabled.");
				return;
			}
			if (!player.getItemAssistant().playerHasItem(SILVER_BAR)) {
				player.getDialogueHandler().sendStatement("You need a silver bar to do this.");
				return;
			}
			if (player.playerLevel[Constants.CRAFTING] < silverCraft.getLevel()) {
				player.getDialogueHandler().sendStatement("You need a crafting level of " + silverCraft.getLevel() + " to make this.");
				return;
			}
			player.startAnimation(SILVER_ANIMATION);
			player.isCrafting = true;
			player.getPacketSender().closeAllWindows();

			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				int amnt = silverCraft.getAmount() != 0 ? silverCraft
						.getAmount() : amount;

				@Override
				public void execute(CycleEventContainer container) {
					if (amnt == 0
							|| !player.getItemAssistant().playerHasItem(
									SILVER_BAR) || player.isCrafting == false) {
						container.stop();
						return;
					}
					container.setTick(3);
					player.startAnimation(SILVER_ANIMATION);
					player.getPacketSender().sendMessage(
							"You make the silver bar into "
									+ DeprecatedItems
											.getItemName(
													silverCraft.getResult())
											.toLowerCase().toLowerCase() + ".");
					player.getItemAssistant().deleteItem(SILVER_BAR, 1);
					player.getItemAssistant().addItem(silverCraft.getResult(),
							1);
					player.getPlayerAssistant().addSkillXP(
							silverCraft.getExperience(), Constants.CRAFTING);
					amnt--;

				}

				@Override
				public void stop() {
					player.startAnimation(65535);
					player.isCrafting = false;
				}
			}, 3);
		}
	}
}
