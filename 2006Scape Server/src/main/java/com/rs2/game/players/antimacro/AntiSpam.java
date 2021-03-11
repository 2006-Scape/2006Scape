package com.rs2.game.players.antimacro;

import com.rs2.game.players.Player;

/**
 * AntiSpam
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class AntiSpam {
	
	private final static String[] BLOCKED_WORDS = {
		"(dot)", ".com", "tk", ".org", ".net", ".info", ".cam", ".c0m", ". net", "(,)com", ".inf0", ".0rg", "(.)",
		".biz", ".co.uk", ". com", ". info", ". c0m", ",com", ". biz", ". tk", ". 0rg", ". cam", ". inf0", "c'om",
		"c'0m", ". org", "http", "no-ip", "tradereq", "duelreq", "www", "vww", "c' om", "c' 0m", "w-w-w", "w'ww",
		"w'w'w", "w 'ww", "w' w' w", "ww' w", "wvv", "wwv", "vvv", "vwv", "w)w)w", "?com", "/com", "'com",  "(com)",
		"(w)(w)(w)", "sythe", "abusewith.us", "runerebels", "06prime", "2006prime", "arios498", "ariosrsps", "coom"
	};
	
	public static boolean blockedWords(Player player, String word, boolean chat) {
		for (int i = 0; i < BLOCKED_WORDS.length; i++) {
			if (player.getPlayerAssistant().isPlayer()) {
				if (word.contains(BLOCKED_WORDS[i]) || word.equalsIgnoreCase(player.playerPass)) {
					player.getPacketSender().sendMessage("You can't say that word!");
					if (chat) {
						player.setChatTextUpdateRequired(false);
					}
					return false;
				}	
			}
		}
		return true;
	}

}
