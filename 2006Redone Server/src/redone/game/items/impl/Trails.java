package redone.game.items.impl;


public class Trails {

	/*public Client c;

	public Trails(Client client) {
		this.c = client;
	}

	public int clue1[] = { 1077, 1089, 1107, 1125, 1131, 1129, 1133, 1511,
			1168, 1165, 1179, 1195, 1217, 1283, 1297, 1313, 1327, 1341, 1361,
			1367, 1426, 2633, 2635, 2637, 7388, 7386, 7392, 7390, 7396, 7394,
			2631, 7364, 7362, 7368, 7366, 2583, 2585, 2587, 2589, 2591, 2593,
			2595, 2597, 7332, 7338, 7350, 7356, 10404, 10406, 10408, 10410,
			10412, 10414, 10424, 10426, 10428, 10430, 10432, 10434, 10458,
			10460, 10462, 10464, 10466, 10468 };

	public int clue2[] = { 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613,
			7334, 7340, 7346, 7352, 7358, 7319, 7321, 7323, 7325, 7327, 7372,
			7370, 7380, 7378, 2645, 2647, 2649, 2577, 2579, 1073, 1091, 1099,
			1111, 1135, 1124, 1145, 1161, 1169, 1183, 1199, 1211, 1245, 1271,
			1287, 1301, 1317, 1332, 1357, 1371, 1430, 6916, 6918, 6920, 6922,
			6924, 10400, 10402, 10416, 10418, 10420, 10422, 10436, 10438,
			10446, 10448, 10450, 10452, 10454, 10456, 6889 };

	public int clue3[] = { 
			1079, 1093, 1113, 1127, 1147, 1163, 1185, 1201,
			1275, 1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503, 861, 859,
			2581, 2651, 1079, 1093, 1113, 1127, 1147, 1163, 1185, 1201, 1275,
			1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503, 861, 859, 2581,
			2651, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 2639, 2641,
			2643, 2651, 2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669,
			2671, 2673, 2675, 2581, 7342, 7348, 7454, 7460, 7374, 7376, 7382,
			7384, 7398, 7399, 7400, 3481, 3483, 3485, 3486, 3488, 1079, 1093,
			1113, 1127, 1148, 1164, 1185, 1201, 1213, 1247, 1275, 1289, 1303,
			1319, 1333, 1347, 1359, 1374, 1432, 2615, 2617, 2619, 2621, 2623,
			2625, 2627, 2629, 2639, 2641, 2643, 2651, 2653, 2655, 2657, 2659,
			2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675, 2581, 7342, 7348,
			7454, 7460, 7374, 7376, 7382, 7384, 7398, 7399, 7400, 3481, 3483,
			3485, 3486, 3488, 1079, 1093, 1113, 1127, 1148, 1164, 1185, 1201,
			1213, 1247, 1275, 1289, 1303, 1319, 1333, 1347, 1359, 1374, 1432,
			10330, 10338, 10348, 10332, 10340, 10346, 10334, 10342, 10350,
			10336, 10344, 10352, 10368, 10376, 10384, 10370, 10378, 10386,
			10372, 10380, 10388, 10374, 10382, 10390, 10470, 10472, 10474,
			10440, 10442, 10444, 6914 };

	public void clueReward(int i1, int a1, int i2, int a2, int i3, int a3,
			int i4, int a4) {
		c.getPA().showInterface(6960);
		c.getPA().sendFrame34(6963, i1, 0, a1);
		c.getPA().sendFrame34(6963, i2, 1, a2);
		c.getPA().sendFrame34(6963, i3, 2, a3);
		c.getPA().sendFrame34(6963, i4, 3, a4);
		c.getItems().addItem(i1, a1);
		c.getItems().addItem(i2, a2);
		c.getItems().addItem(i3, a3);
		c.getItems().addItem(i4, a4);
	}

	public void Clue(String a, String b, String c1, String d, String e,
			String f, String g, String h) {
		c.getPA().sendFrame126(a, 6968);
		c.getPA().sendFrame126(b, 6969);
		c.getPA().sendFrame126(c1, 6970);
		c.getPA().sendFrame126(d, 6971);
		c.getPA().sendFrame126(e, 6972);
		c.getPA().sendFrame126(f, 6973);
		c.getPA().sendFrame126(g, 6974);
		c.getPA().sendFrame126(h, 6975);
		c.getPA().showInterface(6965);
	}

	public void showitem(int item, String s1) {
		c.getPA().sendFrame246(11860, 150, item);
		c.getPA().sendFrame126(s1, 11861);
		c.getPA().sendFrame164(11859);
	}

	/**
	 * Puzzle Boxes
	 */

	/*public int freeSlot;
	public int[] puzzleSlot = new int[26]; // 24 increasing
	public boolean canMove;
	public boolean doingPuzzle;
	public int fourthSlot;
	public boolean puzzleDone = false;
	public boolean openedAlready = false;
	public int puzzleId;
	public int[] castlePuzzle = { 2749, 2750, 2751, 2752, 2753, 2754, 2755,
			2756, 2757, 2758, 2759, 2760, 2761, 2762, 2763, 2764, 2765, 2766,
			2767, 2768, 2769, 2770, 2771, 2772 };
	public int[] treePuzzle = { 3619, 3620, 3621, 3622, 3623, 3624, 3625, 3626,
			3627, 3628, 3629, 3630, 3631, 3632, 3633, 3634, 3635, 3636, 3637,
			3638, 3639, 3640, 3641, 3642 };
	public int[] trollPuzzle = { 3643, 3644, 3645, 3646, 3647, 3648, 3649,
			3650, 3651, 3652, 3653, 3654, 3655, 3656, 3657, 3658, 3659, 3660,
			3661, 3662, 3663, 3664, 3665, 3666 };

	public void findFreeSlot() {
		for (int i = 0; i < 27; i++) {
			if (puzzleSlot[i] < 5) {
				freeSlot = i;
				// c.getActionSender().sendMessage("FreeSlot " + freeSlot + "");
				break;
			}
		}
	}

	public void Hints(int puzzle) {
		int v = 0;
		switch (puzzle) {
		case 1:
			for (int i = 2749; i < 2773; i++) {
				c.getPA().sendFrame34P2(i, v, 6985, 1);
				v += 1;
			}
			c.getPA().sendFrame34P2(2753, 4, 6985, 1);
			break;
		case 3:
			v = 0;
			for (int i = 3619; i < 3643; i++) {
				c.getPA().sendFrame34P2(i, v, 6985, 1);
				v += 1;
			}
			c.getPA().sendFrame34P2(3623, 4, 6985, 1);
			break;
		case 2:
			v = 0;
			for (int i = 3643; i < 3667; i++) {
				c.getPA().sendFrame34P2(i, v, 6985, 1);
				v += 1;
			}
			c.getPA().sendFrame34P2(3647, 4, 6985, 1);
			break;
		}
	}

	public void openPuzzle(int puzzle) {
		int v = 0;
		int notfilled = Misc.random(22);
		puzzleId = puzzle;
		switch (puzzle) {

		case 1:
			for (int i = 0; i < 24; i++) {
				int d = Misc.random(castlePuzzle.length - 1);
				if (castlePuzzle[d] == 0) {
					i -= 1;
				}
				if (v == notfilled) {
					v = notfilled + 1;
				}
				if (castlePuzzle[d] != 0) {
					c.getPA().sendFrame34P2(castlePuzzle[d], v, 6980, 1); // 11126
					puzzleSlot[v] = castlePuzzle[d];
					castlePuzzle[d] = 0;
					if (v != notfilled) {
						v += 1;
					}
					if (v == notfilled) {
						v = notfilled + 1;
					}
				}
			}
			break;

		case 2:
			for (int i = 0; i < 24; i++) {
				int d = Misc.random(trollPuzzle.length - 1);
				if (trollPuzzle[d] == 0) {
					i -= 1;
				}
				if (v == notfilled) {
					v = notfilled + 1;
				}
				if (trollPuzzle[d] != 0) {
					c.getPA().sendFrame34P2(trollPuzzle[d], v, 6980, 1); // 11126
					puzzleSlot[v] = trollPuzzle[d];
					trollPuzzle[d] = 0;
					if (v != notfilled) {
						v += 1;
					}
					if (v == notfilled) {
						v = notfilled + 1;
					}
				}
			}
			break;

		case 3:
			for (int i = 0; i < 24; i++) {
				int d = Misc.random(treePuzzle.length - 1);
				if (treePuzzle[d] == 0) {
					i -= 1;
				}
				if (v == notfilled) {
					v = notfilled + 1;
				}
				if (treePuzzle[d] != 0) {
					c.getPA().sendFrame34P2(treePuzzle[d], v, 6980, 1); // 11126
					puzzleSlot[v] = treePuzzle[d];
					treePuzzle[d] = 0;
					if (v != notfilled) {
						v += 1;
					}
					if (v == notfilled) {
						v = notfilled + 1;
					}
				}
			}
			break;
		}
		c.getPA().sendFrame34P2(puzzleSlot[4], 4, 6980, 1); // 11126
	}

	public void clickPuzzle(int item) {
		switch (item) {
		case 2795: // castle
			if (openedAlready == false) {
				openPuzzle(1);
				Hints(1);
				openedAlready = true;
			}
			c.getPA().showInterface(6976);
			break;
		case 3576: // troll
			if (openedAlready == false) {
				openPuzzle(2);
				Hints(2);
				openedAlready = true;
			}
			c.getPA().showInterface(6976);
			break;
		case 3565:
			if (openedAlready == false) {
				openPuzzle(3);
				Hints(3);
				openedAlready = true;
			}
			c.getPA().showInterface(6976);
			break;
		}
		doingPuzzle = true;
	}

	public void clearPuzzle() {
		for (int i = 0; i < 25; i++) {
			c.getPA().sendFrame34P2(-1, i, 6980, 0); // 11126
			puzzleSlot[i] = -1;
		}
		int z = 0;
		switch (puzzleId) {
		case 1:
			z = 0;
			for (int i = 2749; i < 2773; i++) {
				castlePuzzle[z] = i;
				z += 1;
			}
			break;
		case 2:
			z = 0;
			for (int i = 3619; i < 3643; i++) {
				treePuzzle[z] = i;
				z += 1;

			}
			break;
		case 3:
			z = 0;
			for (int i = 3643; i < 3667; i++) {
				trollPuzzle[z] = i;
				z += 1;

			}
			break;
		}
		openedAlready = false;
		doingPuzzle = false;
		puzzleDone = false;
		puzzleId = -1;
		c.getPA().removeAllWindows();
	}

	public void hasDonePuzzle() {
		int z = 0;
		switch (puzzleId) {
		case 1:
			z = 0;
			for (int i = 2749; i < 2773; i++) {
				if (puzzleSlot[z] == i) {
					z += 1;
				}
			}
			if (z == 24) {
				puzzleDone = true;
			}
			if (puzzleDone) {
				c.getItems().deleteItem2(2795, 1);
				c.getItems().addItem(lvl3(), 1);
				showitem(lvl3(), "You've find another clue!");
				c.level3 += 1;
				clearPuzzle();
			}
			break;
		case 2:
			z = 0;
			for (int i = 3643; i < 3667; i++) {
				if (puzzleSlot[z] == i) {
					z += 1;
				}
			}
			if (z == 24) {
				puzzleDone = true;
			}
			if (puzzleDone) {
				c.getItems().deleteItem2(3576, 1);
				c.getItems().addItem(lvl3(), 1);
				showitem(lvl3(), "You've find another clue!");
				c.level3 += 1;
				clearPuzzle();
			}
			break;

		case 3:
			z = 0;
			for (int i = 3619; i < 3643; i++) {
				if (puzzleSlot[z] == i) {
					z += 1;
				}
			}
			if (z == 24) {
				puzzleDone = true;
			}
			if (puzzleDone) {
				c.getItems().deleteItem2(3565, 1);
				c.getItems().addItem(lvl3(), 1);
				showitem(lvl3(), "You've find another clue!");
				c.level3 += 1;
				clearPuzzle();
			}
			break;
		}
	}

	public void findMove(int slot) {
		findFreeSlot();
		if (slot == freeSlot - 1) {
			canMove = true;
		}
		if (slot == freeSlot + 1) {
			canMove = true;
		}
		if (slot == freeSlot - 5) {
			canMove = true;
		}
		if (slot - 1 == freeSlot) {
			canMove = true;
		}
		if (slot - 1 == freeSlot) {
			canMove = true;
		}
		if (slot + 1 == freeSlot) {
			canMove = true;
		}
		if (slot - 5 == freeSlot) {
			canMove = true;
		}
		if (slot + 5 == freeSlot) {
			canMove = true;
		}
	}

	/**
	 * Clue scroll data
	 */

	/*public static int trail1[] = { 2677, 2678, 2679, 2680, 2681, 2682, 2683,
			2684 };

	public static int trail2[] = { 2685, 2686, 2687, 2688, 2689, 2690, 2691,
			2692 };

	public static int trail3[] = { 2693, 2694, 2695, 2696, 2697, 2698, 2699,
			2700 };

	public static int puzzles[] = { 3576, 3565 };

	public static int lvl1() {
		return trail1[(int) (Math.random() * trail1.length)];
	}

	public static int lvl2() {
		return trail2[(int) (Math.random() * trail2.length)];
	}

	public static int lvl3() {
		return trail3[(int) (Math.random() * trail3.length)];
	}

	public int c1() {
		return clue1[(int) (Math.random() * clue1.length)];
	}

	public int c2() {
		return clue2[(int) (Math.random() * clue2.length)];
	}

	public int c3() {
		return clue3[(int) (Math.random() * clue3.length)];
	}

	public int puzzle() {
		return puzzles[(int) (Math.random() * puzzles.length)];
	}

	public void searchObject(int x, int y, int ob) {
		switch (ob) {
		case 299:
			if (x == 3249 && y == 3432) {
				if (c.getItems().playerHasItem(2677, 1)) {
					if (c.level1 >= 5 && Misc.random(2) == 0) {
						clueReward(c1(), 1, c1(), 1, c1(), 1, c1(), 1);
						c.sendMessage("Well done, you've completed the Treasure Trail!");
						c.level1 = 0;
					} else {
						c.getItems().addItem(lvl1(), 1);
						showitem(lvl1(), "You've found another clue!");
						c.level1 += 1;
					}
					c.getItems().deleteItem2(2677, 1);
				}
			}
			break;
		case 11745:
			if (x == 2955 && y == 3390) {
				if (c.getItems().playerHasItem(2682, 1)) {
					if (c.level1 >= 5 && Misc.random(2) == 0) {
						clueReward(c1(), 1, c1(), 1, c1(), 1, c1(), 1);
						c.sendMessage("Well done, you've completed the Treasure Trail!");
						c.level1 = 0;
					} else {
						c.getItems().addItem(lvl1(), 1);
						showitem(lvl1(), "You've found another clue!");
						c.level1 += 1;
					}
					c.getItems().deleteItem2(2682, 1);
				}
			}
			break;
		case 356:
			if (x == 2804 && y == 3428) {
				if (c.getItems().playerHasItem(2686, 1)) {
					if (c.level2 >= 8 && Misc.random(2) == 0) {
						clueReward(c2(), 1, c2(), 1, c2(), 1, c2(), 1);
						c.sendMessage("Well done, you've completed the Treasure Trail!");
						c.level2 = 0;
					} else {
						c.getItems().addItem(lvl2(), 1);
						showitem(lvl2(), "You've found another clue!");
						c.level2 += 1;
					}
					c.getItems().deleteItem2(2686, 1);
				}
			}
			break;
		}
	}

	public boolean clueNpc(int npc) {
		if (npc == 541 && c.getItems().playerHasItem(2678, 1)) {
			c.getItems().deleteItem2(2678, 1);
			c.getItems().addItem(lvl1(), 1);
			showitem(lvl1(), "Zeke gives you another clue!");
			c.level1 += 1;
			return true;
		} else if (npc == 0 && c.getItems().playerHasItem(2679, 1)) {
			c.getItems().deleteItem2(2679, 1);
			c.getItems().addItem(lvl1(), 1);
			showitem(lvl1(), "Hans gives you another clue!");
			c.level1 += 1;
			return true;
		} else if (npc == 388 && c.getItems().playerHasItem(2680, 1)) {
			c.getItems().deleteItem2(2680, 1);
			c.getItems().addItem(lvl1(), 1);
			showitem(lvl1(), "The seer gives you another clue!");
			c.level1 += 1;
			return true;
		} else if (npc == 548 && c.getItems().playerHasItem(2681, 1)) {
			c.getItems().deleteItem2(2681, 1);
			c.getItems().addItem(lvl1(), 1);
			showitem(lvl1(), "Thessalia gives you another clue!");
			c.level1 += 1;
			return true;
		} else if (npc == 379 && c.getItems().playerHasItem(2683, 1)) {
			c.getItems().deleteItem2(2683, 1);
			c.getItems().addItem(lvl1(), 1);
			showitem(lvl1(), "Luthas gives you another clue!");
			c.level1 += 1;
			return true;
		} else if (npc == 2290 && c.getItems().playerHasItem(2685, 1)) {
			c.getItems().deleteItem2(2685, 1);
			c.getItems().addItem(lvl2(), 1);
			showitem(lvl2(), "Sir Tiffy gives you another clue!");
			c.level2 += 1;
			return true;
		} else if (npc == 5141 && c.getItems().playerHasItem(2690, 1)) {
			c.getItems().deleteItem2(2690, 1);
			c.getItems().addItem(2777, 1);
			showitem(2777, "Uri gives you a casket.");
			return true;
		} else if (npc == 3217 && c.getItems().playerHasItem(2691, 1)) {
			c.getItems().deleteItem2(2691, 1);
			c.getItems().addItem(lvl2(), 1);
			showitem(lvl2(), "Kaylee gives you another clue!");
			c.level2 += 1;
			return true;
		} else if (npc == 538 && c.getItems().playerHasItem(2692, 1)) {
			c.getItems().deleteItem2(2692, 1);
			c.getItems().addItem(lvl2(), 1);
			showitem(lvl2(), "Peska gives you another clue!");
			c.level2 += 1;
			return true;
		} else if (npc == 5142 && c.getItems().playerHasItem(2694, 1)) {
			c.getItems().deleteItem2(2694, 1);
			c.getItems().addItem(2779, 1);
			showitem(2779, "Uri gives you a casket.");
			return true;
		} else if (npc == 5143 && c.getItems().playerHasItem(2695, 1)) {
			c.getItems().deleteItem2(2695, 1);
			c.getItems().addItem(2779, 1);
			showitem(2779, "Uri gives you a casket.");
			return true;
		} else if (npc == 657 && c.getItems().playerHasItem(2696, 1)) {
			c.getItems().deleteItem2(2696, 1);
			c.getItems().addItem(lvl3(), 1);
			showitem(lvl3(), "The monk gives you another clue!");
			c.level3 += 1;
			return true;
		} else if (npc == 4494 && c.getItems().playerHasItem(2697, 1)) {
			c.getItems().deleteItem2(2697, 1);
			c.getItems().addItem(lvl3(), 1);
			showitem(lvl3(), "General Wartface gives you another clue!");
			c.level3 += 1;
			return true;
		} else if (npc == 1060 && c.getItems().playerHasItem(2700, 1)) {
			c.getItems().deleteItem2(2700, 1);
			c.getItems().addItem(lvl3(), 1);
			showitem(lvl3(), "Denulth gives you another clue!");
			c.level3 += 1;
			return true;
		}
		return false;
	}

	public void getDigLoc(int x, int y) {
		if (c.getItems().playerHasItem(2688, 1)) {
			if (x == 2906 && y == 3294) {
				c.getItems().deleteItem2(2688, 1);
				c.getItems().addItem(2777, 1);
				showitem(2777, "You've found a casket!");
			}
		} else if (c.getItems().playerHasItem(2689, 1)) {
			if (x == 2650 && y == 3229) {
				c.getItems().deleteItem2(2689, 1);
				c.getItems().addItem(2777, 1);
				showitem(2777, "You've found a casket!");
			}
		} else if (c.getItems().playerHasItem(2693, 1)) {
			if (x == 3092 && y == 3226) {
				c.getItems().deleteItem2(2693, 1);
				c.getItems().addItem(2779, 1);
				showitem(2779, "You've found a casket!");
			}
		}

		else if (c.getItems().playerHasItem(2698, 1)) {
			if ((x == 3167 && y == 3360) || (x == 3167 && y == 3361)) {
				c.getItems().deleteItem2(2698, 1);
				c.getItems().addItem(2779, 1);
				showitem(2779, "You've found a casket!");
			}
		}
	}

	public void clues(int clueScroll) {
		switch (clueScroll) {
		case 2677:// lvl 1
			Clue("", "", "", "Search the haybales in Varrock's",
					"training facility.", "", "", "");// obj:299 x:3249 y:3432
			break;
		case 2678:// lvl 1
			Clue("", "", "", "Speak to Zeke for your next clue.", "", "", "",
					"");// npc:541
			break;
		case 2679:// lvl 1
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"SNHA", "", "");// npc:0
			break;
		case 2680:// lvl 1
			Clue("", "", "", "Speak with a Seer for your next clue.", "", "",
					"", "");// npc:388
			break;
		case 2681:// lvl 1
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"ESALITHAS", "", "");
			break;
		case 2682:// lvl 1
			Clue("", "", "", "Search the crates in the Falador",
					"General Store.", "", "", "");
			break;
		case 2683:// lvl 1
			Clue("", "", "", "Speak to Luthas in Edgeville for",
					"your next clue.", "", "", "");
			break;
		case 2684:// lvl 1
			Clue("", "", "", "Kill a goblin for your next clue.", "", "", "",
					"");
			break;
		case 2685:// lvl 2
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"RSI FTIYF NEISACH", "", "");
			break;
		case 2686:// lvl 2
			Clue("", "", "", "Search a crate on Catherby port.", "", "", "", "");
			break;
		case 2687:// lvl 2
			Clue("", "", "The Falador guards, they do not see,",
					"my thievery fingers steal all things,",
					"though I tend to aim at their pocketries.", "", "", "");
			break;
		case 2688:// lvl2
			c.getPA().showInterface(4305);
			break;
		case 2689:// lvl2
			c.getPA().showInterface(9632);
			break;
		case 2690:// lvl 2
			Clue("", "", "Jig in the bank of Canifis.",
					"Equip a bronze platebody, a iron sword",
					"and climbing boots.", "", "", "");
			break;
		case 2691:// lvl 2
			Clue("", "", "", "Speak with Kaylee in Falador.", "", "", "", "");
			break;
		case 2692:// lvl 2
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"SKEPA", "", "");
			break;
		case 2693:// lvl3
			c.getPA().showInterface(7113);
			break;
		case 2694:// lvl 3
			Clue("", "", "Dance in the Falador General store.",
					"Equip a rune scimitar, a mystic hat", "and a blue skirt.",
					"", "", "");
			break;
		case 2695:// lvl 3
			Clue("", "", "Salute in the Banana Plantation.",
					"Beware of double agents!",
					"Equip a Iban's Staff, rune platelegs",
					"and an adamant kiteshield.", "", "");
			break;
		case 2696:// lvl 3
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"NOKM FO NTERNAN", "", "");
			break;
		case 2697:// lvl 3
			Clue("", "", "", "Speak with General Wartface for",
					"your next clue.", "", "", "");
			break;
		case 2698:// lvl3
			c.getPA().showInterface(6994);
			break;
		case 2699:// lvl 3
			Clue("", "", "", "Kill a green dragon for your next clue.", "", "",
					"", "");
			break;
		case 2700:// lvl 3
			Clue("", "", "This anagram reveals", "who to speak to next:", "",
					"HTENDLU", "", "");
			break;
		case 2775:// level1 Chest
			if (c.level1 >= 5 && Misc.random(2) == 0) {
				clueReward(c1(), 1, c1(), 1, c1(), 1, c1(), 1);
				c.sendMessage("Well done, you've completed the Treasure Trail!");
				c.level1 = 0;
			} else {
				c.getItems().addItem(lvl1(), 1);
				showitem(lvl1(), "You've found another clue!");
				c.level1 += 1;
			}
			c.getItems().deleteItem2(2775, 1);
			break;
		case 2777:// level2 Chest
			if (c.level2 >= 8 && Misc.random(2) == 0) {
				clueReward(c2(), 1, c2(), 1, c2(), 1, c2(), 1);
				c.sendMessage("Well done, you've completed the Treasure Trail!");
				c.level2 = 0;
			} else {
				c.getItems().addItem(lvl2(), 1);
				showitem(lvl2(), "You've found another clue!");
				c.level2 += 1;
			}
			c.getItems().deleteItem2(2777, 1);
			break;
		case 2779:// level3 Chest
			if (c.level3 >= 13 && Misc.random(3) == 0) {
				clueReward(c3(), 1, c3(), 1, c3(), 1, c3(), 1);
				c.sendMessage("Well done, you've completed the Treasure Trail!");
				c.level3 = 0;
			} else {
				c.getItems().addItem(lvl3(), 1);
				showitem(lvl3(), "You've found another clue!");
				c.level3 += 1;
			}
			c.getItems().deleteItem2(2779, 1);
			break;
		}
	}*/
}