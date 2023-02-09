package com.rs2.game.content.skills.crafting;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

public class LeatherMaking extends CraftingData {

	public static void craftLeatherDialogue(final Player c, final int itemUsed,
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
					c.getPacketSender().sendString(
							DeprecatedItems.getItemName(1131), 2799);
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
						c.getPacketSender().sendString(name[i], 8949 + i * 4);
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
					c.getPacketSender().sendString(name[i], 8889 + i * 4);
				}
				c.leatherType = leather;
				return;
			}
		}
	}

	private static int amount;

	public static void craftLeather(final Player player, final int buttonId) {
		if (player.isCrafting) {
			return;
		}
		for (final leatherData l : leatherData.values()) {
			if (buttonId == l.getButtonId(buttonId)) {
				if (player.leatherType == l.getLeather()) {
					if (player.playerLevel[Constants.CRAFTING] < l.getLevel()) {
						player.getPacketSender().sendMessage(
								"You need a crafting level of " + l.getLevel()
										+ " to make this.");
						player.getPacketSender().closeAllWindows();
						return;
					}
					if (!player.getItemAssistant().playerHasItem(1734)) {
						player.getPacketSender().sendMessage(
								"You need some thread to make this.");
						player.getPacketSender().closeAllWindows();
						return;
					}
					if (!player.getItemAssistant().playerHasItem(player.leatherType,
							l.getHideAmount())) {
						player.getPacketSender().sendMessage(
								"You need "
										+ l.getHideAmount()
										+ " "
										+ DeprecatedItems.getItemName(
												player.leatherType).toLowerCase()
										+ " to make "
										+ DeprecatedItems.getItemName(
												l.getProduct()).toLowerCase()
										+ ".");
						player.getPacketSender().closeAllWindows();
						return;
					}
					player.startAnimation(1249);
					player.getPacketSender().closeAllWindows();
					player.isCrafting = true;
					amount = l.getAmount(buttonId);
					CycleEventHandler.getSingleton().addEvent(player,
							new CycleEvent() {

								@Override
								public void execute(
										CycleEventContainer container) {
									if (player.isCrafting) {
										if (!player.getItemAssistant()
												.playerHasItem(1734)) {
											player.getPacketSender()
													.sendMessage(
															"You have run out of thread.");
											container.stop();
											return;
										}
										if (!player.getItemAssistant()
												.playerHasItem(player.leatherType,
														l.getHideAmount())) {
											player.getPacketSender()
													.sendMessage(
															"You have run out of leather.");
											container.stop();
											return;
										}
										if (amount == 0) {
											container.stop();
											return;
										}
										player.getItemAssistant().deleteItem(
												1734,
												player.getItemAssistant()
														.getItemSlot(1734), 1);
										player.getItemAssistant().deleteItem(
												player.leatherType,
												l.getHideAmount());
										player.getItemAssistant().addItem(
												l.getProduct(), 1);
										player.getPacketSender()
												.sendMessage(
														"You make "
																+ (DeprecatedItems
																		.getItemName(
																				l.getProduct())
																		.contains(
																				"body") ? "a"
																		: "some")
																+ " "
																+ DeprecatedItems
																		.getItemName(l
																				.getProduct())
																+ ".");
										player.getPlayerAssistant().addSkillXP(
												(int) l.getXP(), 12);
										player.startAnimation(1249);
										amount--;
										if (!player.getItemAssistant()
												.playerHasItem(1734)) {
											player.getPacketSender()
													.sendMessage(
															"You have run out of thread.");
											container.stop();
											return;
										}
										if (!player.getItemAssistant()
												.playerHasItem(player.leatherType,
														l.getHideAmount())) {
											player.getPacketSender()
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
									player.isCrafting = false;
								}
							}, 5);
				}
			}
		}
	}
}
