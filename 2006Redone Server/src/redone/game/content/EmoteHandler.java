package redone.game.content;

import java.util.HashMap;

import redone.game.players.Client;

public class EmoteHandler {

	private final Client player;

	public EmoteHandler(Client c) {
		this.player = c;
	}

	public Emotes EMOTES = null;

	public enum Emotes {
		Yes(168, 855, -1), No(169, 856, -1), Bow(164, 858, -1), Angry(167, 864,
				-1), Think(162, 857, -1), Wave(163, 863, -1), Shrug(52058,
				2113, -1), Cheer(171, 862, -1), Beckon(165, 859, -1), Laugh(
				170, 861, -1), Jump_For_Joy(52054, 2109, -1), Yawn(52056, 2111,
				-1), Dance(166, 866, -1), Jig(52051, 2106, -1), Twirl(52052,
				2107, -1), Headbang(52053, 2108, -1), Cry(161, 860, -1), Blow_Kiss(
				43092, 0x558, 574), Panic(52050, 2105, -1), Rasberry(52055,
				2110, -1), Clap(172, 865, -1), Salute(52057, 2112, -1), Goblin_Bow(
				52071, 0x84F, -1), Goblin_Salute(52072, 0x850, -1), Glass_Box(
				2155, 0x46B, -1), Climb_Rope(25103, 0x46A, -1), Lean(25106,
				0x469, -1), Glass_Wall(2154, 0x468, -1), Idea(88060, 4276, 712), Stomp(
				88061, 4278, -1), Flap(88062, 4280, -1), Slap_Head(88063, 4275,
				-1), Zombie_Walk(72032, 3544, -1), Zombie_Dance(72033, 3543, -1), Zombie_Hand(
				88065, 7272, 1244), Scared(59062, 2836, -1), Bunny_Hop(72254,
				3866, -1);

		private Emotes(int buttonId, int animId, int gfxId) {
			buttonID = buttonId;
			animID = animId;
			gfxID = gfxId;
		}

		public static HashMap<Integer, Emotes> emotes = new HashMap<Integer, Emotes>();

		public static Emotes loadEmote(int buttonId) {
			return emotes.get(buttonId);
		}

		static {
			for (Emotes e : Emotes.values()) {
				emotes.put(e.buttonID, e);
			}
		}

		public int gfxID;
		public int animID;
		public int buttonID;
	}

	public void startEmote(int buttonId) {
		Emotes EMOTES = Emotes.loadEmote(buttonId);
		if (EMOTES != null && this.EMOTES == null) {
			this.EMOTES = EMOTES;
			if (EMOTES.animID != 1) {
				if (player.tutorialProgress == 10) {
					player.getDialogueHandler().sendDialogues(3039, 0);
				}
				player.startAnimation(EMOTES.animID);
				EMOTES = null;
				this.EMOTES = null;
			}
		}
	}
}
