package com.rebotted.game.content.skills.crafting;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Client;

public class LeatherMaking extends CraftingData {

	public static void craftLeatherDialogue(final Client c, final int itemUsed,
			final int usedWith) {
		for (final leatherData l : leatherData.values()) {
			final int leather = itemUsed == 1733 ? usedWith : itemUsed;
			if (leather == l.getLeather()) {
				if (l.getLeather() == 1741) {
					c.getPacketSender().showInterface(2311);
					c.leatherType = leather;
					return;
				}
				String[] name = { "Body", "Chaps", "Bandana", "Boots", "Vamb", };
				if (l.getLeather() == 1743) {
					c.getPacketSender().sendChatInterface(4429);
					c.getPacketSender().sendFrame246(1746, 200, 1131);
					c.getPacketSender().sendFrame126(
							ItemAssistant.getItemName(1131), 2799);
					c.leatherType = leather;
				}
				if (l.getLeather() == 6289) {
					c.getPacketSender().sendChatInterface(8938);
					c.getPacketSender().itemOnInterface(8941, 180, 6322);
					c.getPacketSender().itemOnInterface(8942, 180, 6324);
					c.getPacketSender().itemOnInterface(8943, 180, 6326);
					c.getPacketSender().itemOnInterface(8944, 180, 6328);
					c.getPacketSender().itemOnInterface(8945, 180, 6330);
					for (int i = 0; i < name.length; i++) {
						c.getPacketSender().sendFrame126(name[i], 8949 + i * 4);
					}
					c.leatherType = leather;
					return;
				}
			}
		}
		for (final leatherDialogueData d : leatherDialogueData.values()) {
			final int leather = itemUsed == 1733 ? usedWith : itemUsed;
			String[] name = { "Vamb", "Body", "Chaps", };
			if (leather == d.getLeather()) {
				c.getPacketSender().sendChatInterface(8880);
				c.getPacketSender().itemOnInterface(8883, 180, d.getVamb());
				c.getPacketSender()
						.itemOnInterface(8884, 180, d.getChaps());
				c.getPacketSender().itemOnInterface(8885, 180, d.getBody());
				for (int i = 0; i < name.length; i++) {
					c.getPacketSender().sendFrame126(name[i], 8889 + i * 4);
				}
				c.leatherType = leather;
				return;
			}
		}
	}

	private static int amount;

	public static void craftLeather(final Client c, final int buttonId) {
		if (c.isCrafting == true) {
			return;
		}
		for (final leatherData l : leatherData.values()) {
			if (buttonId == l.getButtonId(buttonId)) {
				if (c.leatherType == l.getLeather()) {
					if (c.playerLevel[12] < l.getLevel()) {
						c.getPacketSender().sendMessage(
								"You need a crafting level of " + l.getLevel()
										+ " to make this.");
						c.getPacketSender().closeAllWindows();
						return;
					}
					if (!c.getItemAssistant().playerHasItem(1734)) {
						c.getPacketSender().sendMessage(
								"You need some thread to make this.");
						c.getPacketSender().closeAllWindows();
						return;
					}
					if (!c.getItemAssistant().playerHasItem(c.leatherType,
							l.getHideAmount())) {
						c.getPacketSender().sendMessage(
								"You need "
										+ l.getHideAmount()
										+ " "
										+ ItemAssistant.getItemName(
												c.leatherType).toLowerCase()
										+ " to make "
										+ ItemAssistant.getItemName(
												l.getProduct()).toLowerCase()
										+ ".");
						c.getPacketSender().closeAllWindows();
						return;
					}
					c.startAnimation(1249);
					c.getPacketSender().closeAllWindows();
					c.isCrafting = true;
					amount = l.getAmount(buttonId);
					CycleEventHandler.getSingleton().addEvent(c,
							new CycleEvent() {

								@Override
								public void execute(
										CycleEventContainer container) {
									if (c.isCrafting == true) {
										if (!c.getItemAssistant()
												.playerHasItem(1734)) {
											c.getPacketSender()
													.sendMessage(
															"You have run out of thread.");
											container.stop();
											return;
										}
										if (!c.getItemAssistant()
												.playerHasItem(c.leatherType,
														l.getHideAmount())) {
											c.getPacketSender()
													.sendMessage(
															"You have run out of leather.");
											container.stop();
											return;
										}
										if (amount == 0) {
											container.stop();
											return;
										}
										c.getItemAssistant().deleteItem(
												1734,
												c.getItemAssistant()
														.getItemSlot(1734), 1);
										c.getItemAssistant().deleteItem(
												c.leatherType,
												l.getHideAmount());
										c.getItemAssistant().addItem(
												l.getProduct(), 1);
										c.getPacketSender()
												.sendMessage(
														"You make "
																+ (ItemAssistant
																		.getItemName(
																				l.getProduct())
																		.contains(
																				"body") ? "a"
																		: "some")
																+ " "
																+ ItemAssistant
																		.getItemName(l
																				.getProduct())
																+ ".");
										c.getPlayerAssistant().addSkillXP(
												(int) l.getXP(), 12);
										c.startAnimation(1249);
										amount--;
										if (!c.getItemAssistant()
												.playerHasItem(1734)) {
											c.getPacketSender()
													.sendMessage(
															"You have run out of thread.");
											container.stop();
											return;
										}
										if (!c.getItemAssistant()
												.playerHasItem(c.leatherType,
														l.getHideAmount())) {
											c.getPacketSender()
													.sendMessage(
															"You have run out of leather.");
											container.stop();
											return;
										}
									} else {
										container.stop();
									}
								}

								@Override
								public void stop() {
									c.isCrafting = false;
								}
							}, 5);
				}
			}
		}
	}
}
