package redone.game.content.music;

import java.util.HashMap;
import java.util.Map;

import redone.game.players.Client;

public class PlayList {

	private final Client player;

	@SuppressWarnings("unused")
	private final int[] songs = { 2, 3, 12, 14, 9, 157, 76, 57, 419, 15, 18,
			327, 125, 106, 123, 177, 169, 98, 141, 36, 50, 72, 186, 151, 35,
			180, 105, 127, 62, 175, 54, 96, 8, 34, 1, 69, 537, 558, 559, 560,
			93, 143, 337, 64, 85, 65, 158, 190, 325 };

	@SuppressWarnings("unused")
	private final int[] buttons = { 41, 155, 99, 167, 296, 105, 81, 123, 2,
			170, 83, 59, 73, 63, 29, 24, 53, 71, 251, 28, 25, 66, 33, 171, 130,
			40, 187, 113, 112, 135, 183, 86, 248, 168, 84, 61, 435, 436, 437,
			434, 116, 253, 263, 47, 159, 108, 117, 199, 49 };

	@SuppressWarnings("unused")
	private final int[] configs = { 20, 21, 22, 23, 24, 25, 298, 311, 346, 414,
			464, 598, 662, 721, 906, 1009 };

	@SuppressWarnings("unused")
	private final int[] values = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0 };

	public boolean[] unlocked = { false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, true, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false };

	private final String[] names = { "Autumn Voyage", "Unknown Land",
			"Long Way Home", "Witching", "Kingdom", "Medieval", "Harmony",
			"Rune Essence", "Pheasant Peasant", "Workshop", "Horizon", "Dream",
			"Garden", "Expanse", "Arabian 2", "Adventure", "Crystal Sword",
			"Forever", "Barbarianism", "Arabian", "Al Kharid", "Fanfare",
			"Arrival", "Start", "Sea Shanty 2", "Attention", "Tomorrow",
			"Nightfall", "Newbie Melody", "Spirit", "Scape Soft",
			"Inspiration", "Wildwood", "Wonder", "Iban", "Egypt",
			"Dogs of War", "Food for Thought", "Malady", "Dance of Death",
			"Parade", "Down To Earth", "Faithless", "Book Of Spells", "Vision",
			"Miracle Dance", "Quest", "Heart and Mind", "Cave Background",
			"Talking Forest" };

	private boolean loop = false;

	public boolean auto = true;

	public PlayList(Client p) {
		player = p;
	}

	private static enum Songs {
		// action id, songid, ?, song number
		AUTUMN_VOYAGE(16208, 2, 4304, 0), UNKNOWN_LAND(17066, 3, 4418, 1), LONG_WAY_HOME(
				17010, 12, 4362, 2), WITCHING(17078, 14, 4430, 3), KINGDOM(
				32243, 9, 8435, 4), MEDIEVAL(17016, 157, 4368, 5), HARMONY(
				16248, 76, 4344, 6), RUNE_ESSENCE(17034, 57, 4386, 7), PHEASANT_PEASANT(
				55108, 419, 14188, 8), WORKSHOP(17081, 15, 4433, 9), HORIZON(
				16250, 18, 4346, 10), DREAM(35014, 327, 8974, 11), GARDEN(
				16240, 125, 4336, 12), EXPANSE(16230, 106, 4326, 13), ARABIAN_2(
				16196, 123, 4292, 14), ADVENTURE(16191, 177, 4287, 15), CRYSTAL_SWORD(
				16220, 169, 4316, 16), FOREVER(16238, 98, 4334, 17), BARBARIANISM(
				19000, 141, 4864, 18), ARABIAN(16195, 36, 4291, 19), AL_KHARID(
				16192, 50, 4288, 20), FANFARE(16233, 72, 4329, 21), ARRIVAL(
				16200, 186, 4296, 22),
		// START(151, 23, 2097152, 23),
		SEA_SHANTY_2(17041, 35, 4393, 24), ATTENTION(16207, 180, 4303, 25), TOMORROW(
				24153, 105, 6297, 26), NIGHTFALL(17024, 127, 4376, 27), NEWBIE_MELODY(
				17023, 62, 4375, 28), SPIRIT(17046, 175, 4398, 29), SCAPE_SOFT(
				23100, 54, 5988, 30), INSPIRATION(16253, 96, 4349, 31), WILDWOOD(
				27071, 278, 6983, 32), WONDER(17079, 34, 4431, 33), IBAN(16251,
				1, 4347, 34), EGYPT(16228, 69, 4324, 35), PARADE(17027, 93,
				4379, 40), DOWN_TO_EARTH(19015, 143, 4879, 41), FAITHLESS(
				39145, 337, 346, 42), BOOK_OF_SPELLS(16214, 64, 4310, 43), VISION(
				17070, 85, 4422, 44), MIRACLE_DANCE(17019, 65, 4371, 45), QUEST(
				17028, 158, 4380, 46), HEART_AND_MIND(27033, 52, 6945, 47), // was
																			// 190
																			// changed
																			// to
																			// 52
		CAVE_BACKGROUND(35012, 325, 8972, 48), TALKING_FOREST(17083, 140, 4435,
				49);

		private int songId;
		private int buttonId;
		private int childId;
		private int arraySlot;

		public int songId() {
			return songId;
		}

		@SuppressWarnings("unused")
		public int configId() {
			return songId;
		}

		@SuppressWarnings("unused")
		public int configNumber() {
			return songId;
		}

		@SuppressWarnings("unused")
		public int arraySlot() {
			return arraySlot;
		}

		Songs(int buttonId, int songId, int childId, int arraySlot) {
			this.buttonId = buttonId;
			this.songId = songId;
			this.childId = childId;
			this.arraySlot = arraySlot;
		}

		private static Map<Integer, Songs> songs = new HashMap<Integer, Songs>();
		static {
			for (Songs songId : Songs.values()) {
				songs.put(songId.songId(), songId);
			}
		}

		public static Songs get(int id) {
			return songs.get(id);
		}

	}

	public void fixAllColors() {
		for (int i = 0; i < 385; i++) {
			if (Songs.get(i) != null) {
				player.getActionSender().sendColor(Songs.get(i).childId,
						255 << 10 | 0 << 5 | 0);
				if (unlocked[Songs.get(i).arraySlot]) {
					updateList(Songs.get(i).childId);
				}
			}
		}
	}

	public void playSong(int songID) {
		// System.out.println("songId = "+songID);
		try {
			if (Songs.get(songID) == null) {
				// player.sendMessage("This song has not yet been added to the playlist. - Song ID = "+songID);
				if (auto) {
					player.getPlayerAssistant().sendFrame126("Song Unavailable!",
							4439);
					updateList(4439);
				}
				return;
			}
			if (!unlocked[Songs.get(songID).arraySlot]) {
				player.getActionSender().sendMessage(
						"You have unlocked the song "
								+ names[Songs.get(songID).arraySlot] + ".");
				// System.out.println("You have unlocked the song " +
				// names[Songs.get(songID).arraySlot]);
				unlocked[Songs.get(songID).arraySlot] = true;
				updateList(Songs.get(songID).childId);
				player.getPlayerAssistant().sendFrame126(
						names[Songs.get(songID).arraySlot], 4439);
				updateList(4439);
			} else {
				if (auto) {
					player.getPlayerAssistant().sendFrame126(
							names[Songs.get(songID).arraySlot], 4439);
					updateList(4439);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public void updateList(int id) {
		player.getActionSender().sendColor(id, 0 << 10 | 255 << 5 | 0);
	}

	public void handleButton(int button) {
		if (button == 17023) {
			return;
		}
		if (button == 9925) {
			loop = !loop;
		} else if (button == 6269) {
			auto = true;
			player.getPlayerAssistant().sendConfig(18, 1);
		} else if (button == 6270) {
			auto = false;
			player.getPlayerAssistant().sendConfig(18, 0);
		} else {
			// System.out.println("button="+button);
			int songId = -1;
			for (int i = 0; i < 385; i++) {
				if (Songs.get(i) != null) {
					if (Songs.get(i).buttonId == button) {
						songId = Songs.get(i).songId;
					}
				}
			}
			if (songId == -1) {
				// player.getPacketDispatcher().sendMessage("That song is unavailable at this time.");
			} else if (!unlocked[Songs.get(songId).arraySlot]) {
				player.getActionSender().sendMessage(
						"You have to unlock that song first!");
			} else {
				auto = false;
				player.getPlayerAssistant().sendConfig(18, 0);
				playSong(songId);
				// System.out.println("sending music to ActionSender packet - song id = "+songId);
				player.getActionSender().sendSong(songId);
				player.getPlayerAssistant().sendFrame126(
						names[Songs.get(songId).arraySlot], 4439);
				updateList(4439);
			}
		}
	}
}
