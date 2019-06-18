package redone.game.players.antimacro;

import redone.game.players.Client;

/**
 * AntiSpam
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class AntiSpam {
	
	private final static String[] BLOCKED_WORDS = {
		"(dot)", ".com", "tk", ".org", ".net", ".info", ".cam", ".c0m", ". net", "(,)com",
		".inf0", ".0rg", "(.)", ".biz", ".co.uk", ". com", ". info", ". c0m", ",com",
		". biz", ". tk", ". 0rg", ". cam", ". inf0", "c'om", "c'0m", ". org", "dupe",
		"http", "no-ip", "tradereq", "duelreq", "www", "vww", "c' om", "c' 0m", "w-w-w",
		"w'ww", "w'w'w", "w 'ww", "w' w' w", "ww' w", "wvv", "ww", "wwv", "vvv", "vwv",
		"w)w)w", "?com", "/com", "'com", "(com)", "(w)(w)(w)", "bot", "sythe", "abusewith.us",
		"osrs", "2007rs", "runerebels", "06prime", "2006prime", "arios498", "ariosrsps", "coom"
	};
	
	public static boolean blockedWords(Client player, String word, boolean chat) {
		for (int i = 0; i < BLOCKED_WORDS.length; i++) {
			if (player.getPlayerAssistant().isPlayer()) {
				if (word.contains(BLOCKED_WORDS[i]) || word.equalsIgnoreCase(player.playerPass)) {
					player.getActionSender().sendMessage("You can't say that word!");
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
