package com.rs2.game.items.impl;

import java.util.HashMap;
import java.util.Map;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Player;

/**
 * Handles the preaching of god books
 * @author Final Project
 *
 */
public enum GodBooks {

	HOLY_BOOK(3840, new String[][] {
		{"9178", "In the name of Saradomin,", "Protector of us all,", "I now join you in the eyes of Saradomin.", null},
		{"9179", "Thy cause was false, thy skills did lack.", "See you in lumbridge when you get back", null},
		{"9180", "Go in peace in the name of Saradomin,", "May his glory shine upon you like the sun.", null},
		{"9181", "Walk proud, and show mercy,", "For you carry my name in your heart,", "This is Saradomin's wisdom.", null}
	}),
	BOOK_OF_BALANCE(3844, new String[][] {
		{"9178", "Light and dark, day and night,", "Balance arises from contrast.", "I unify thee in the name of Guthix.", null},
		{"9179", "Thy death was not in vain,", "For it brought some balance to the world.", "May Guthix bring you rest.", null},
		{"9180", "May you walk the path and never fall,", "For guthix walks beside thee on thy journey.", "May Guthix bring you peace.", null},
		{"9181", "A Journey of a single step,", "May take thee over a thousand miles.", "May Guthix bring you balance.", null}
	}),
	UNHOLY_BOOK(3842, new String[][] {
		{"9178", "Two great warriors, joined by hand,", "to spread destruction across the land.", "In Zamorak's name, now two are one.", null},
		{"9179", "The weak deserve to die,", "So that the strong may flourish.", "This is the creed of Zamorak.", null},
		{"9180", "May your bloodthirst be never sated,", "and may all your battles be glorious.", "Zamorak bring you strength", null},
		{"9181", "There is no opinion that cannot be proven true,", "by crushing those who choose to disagree with it.", "Zamorak give me strength!", null}
	});

	private int itemId;

	private String[][] preachData;

	private GodBooks(int itemId, String[][] preachData) {
		this.itemId = itemId;
		this.preachData = preachData;
	}

	private static Map<Integer, GodBooks> godBooks = new HashMap<Integer, GodBooks>();

	static {
		for (final GodBooks type : values()) {
			godBooks.put(type.itemId, type);
		}
	}

	/**
	 * Sends the options dialogue with preach options
	 * @param player
	 * 			the player to send the options to
	 * @param itemId
	 * 			the item the player's interacting with
	 */
	public static void sendPreachOptions(Player player, int itemId) {
		player.preaching = true;
		switch (itemId) {
		case 3840:
			player.getDialogueHandler().sendOption("Partnership", "Blessing","Last Rights", "Preach");
			player.dialogueAction = 3840;
			break;
		case 3842:
		case 3844:
			player.getDialogueHandler().sendOption("Wedding Ceremony", "Blessing", "Last Rights", "Preach");
			player.dialogueAction = 3842;
			break;
		}
	}

	/**
	 * Handles the preaching action
	 * @param player
	 * 			the player preaching
	 * @param itemId
	 * 			the item the player's interacting with
	 * @param actionButtonId
	 * 			the button id the player's interacting with
	 */
	public static void handlePreach(Player player, int itemId, int actionButtonId) {
		player.getPacketSender().closeAllWindows();
		GodBooks books = godBooks.get(itemId);
		if (player.specAmount < 2.5) {
			player.getPacketSender().sendMessage("You need at least 25% of special attack to preach!");
			return;
		}
		if (itemId == books.itemId) {
			player.specAmount -= 2.5;
			player.getItemAssistant().updateSpecialBar();
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				private int currentQuote = 1;

				@Override
				public void execute(CycleEventContainer container) {
					for (int preaches = 0; preaches < books.preachData.length; preaches++) {
						if (actionButtonId == Integer.parseInt(books.preachData[preaches][0])) {
							if (books.preachData[preaches][currentQuote] == null) {
								container.stop();
								return;
							}
							if (books.preachData[preaches][currentQuote] != null) {
								player.startAnimation(1670);
								player.forcedChat(books.preachData[preaches][currentQuote]);
								currentQuote++;
							}
							break;
						}
					}
				}

				@Override
				public void stop() {
					currentQuote = 1;
					player.preaching = false;
				}

			}, 1);
		}
	}

}