// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Entity extends Animable {

	public final void setPos(int i, int j, boolean flag) {
		if (anim != -1 && Animation.anims[anim].anInt364 == 1) {
			anim = -1;
		}
		if (!flag) {
			int k = i - smallX[0];
			int l = j - smallY[0];
			if (k >= -8 && k <= 8 && l >= -8 && l <= 8) {
				if (smallXYIndex < 9) {
					smallXYIndex++;
				}
				for (int i1 = smallXYIndex; i1 > 0; i1--) {
					smallX[i1] = smallX[i1 - 1];
					smallY[i1] = smallY[i1 - 1];
					aBooleanArray1553[i1] = aBooleanArray1553[i1 - 1];
				}

				smallX[0] = i;
				smallY[0] = j;
				aBooleanArray1553[0] = false;
				return;
			}
		}
		smallXYIndex = 0;
		anInt1542 = 0;
		anInt1503 = 0;
		smallX[0] = i;
		smallY[0] = j;
		x = smallX[0] * 128 + anInt1540 * 64;
		y = smallY[0] * 128 + anInt1540 * 64;
	}

	public final void method446() {
		smallXYIndex = 0;
		anInt1542 = 0;
	}

	public final void updateHitData(int j, int k, int l) {
		for (int i1 = 0; i1 < 4; i1++) {
			if (hitsLoopCycle[i1] <= l) {
				hitArray[i1] = k;
				hitMarkTypes[i1] = j;
				hitsLoopCycle[i1] = l + 70;
				return;
			}
		}
	}

	public final void moveInDir(boolean flag, int i) {
		int j = smallX[0];
		int k = smallY[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1) {
			k++;
		}
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3) {
			j--;
		}
		if (i == 4) {
			j++;
		}
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6) {
			k--;
		}
		if (i == 7) {
			j++;
			k--;
		}
		if (anim != -1 && Animation.anims[anim].anInt364 == 1) {
			anim = -1;
		}
		if (smallXYIndex < 9) {
			smallXYIndex++;
		}
		for (int l = smallXYIndex; l > 0; l--) {
			smallX[l] = smallX[l - 1];
			smallY[l] = smallY[l - 1];
			aBooleanArray1553[l] = aBooleanArray1553[l - 1];
		}
		smallX[0] = j;
		smallY[0] = k;
		aBooleanArray1553[0] = flag;
	}

	public int entScreenX;
	public int entScreenY;
	public final int index = -1;
	public boolean isVisible() {
		return false;
	}

	Entity() {
		smallX = new int[10];
		smallY = new int[10];
		interactingEntity = -1;
		anInt1504 = 32;
		anInt1505 = -1;
		height = 200;
		anInt1511 = -1;
		anInt1512 = -1;
		hitArray = new int[4];
		hitMarkTypes = new int[4];
		hitsLoopCycle = new int[4];
		anInt1517 = -1;
		anInt1520 = -1;
		anim = -1;
		loopCycleStatus = -1000;
		textCycle = 100;
		anInt1540 = 1;
		aBoolean1541 = false;
		aBooleanArray1553 = new boolean[10];
		anInt1554 = -1;
		anInt1555 = -1;
		anInt1556 = -1;
		anInt1557 = -1;
	}

	public final int[] smallX;
	public final int[] smallY;
	public int interactingEntity;
	int anInt1503;
	int anInt1504;
	int anInt1505;
	public String textSpoken;
	public int height;
	public int turnDirection;
	int anInt1511;
	int anInt1512;
	int anInt1513;
	final int[] hitArray;
	final int[] hitMarkTypes;
	final int[] hitsLoopCycle;
	int anInt1517;
	int anInt1518;
	int anInt1519;
	int anInt1520;
	int anInt1521;
	int anInt1522;
	int anInt1523;
	int anInt1524;
	int smallXYIndex;
	public int anim;
	int anInt1527;
	int anInt1528;
	int anInt1529;
	int anInt1530;
	int anInt1531;
	public int loopCycleStatus;
	public int currentHealth;
	public int maxHealth;
	int textCycle;
	int anInt1537;
	int anInt1538;
	int anInt1539;
	int anInt1540;
	boolean aBoolean1541;
	int anInt1542;
	int anInt1543;
	int anInt1544;
	int anInt1545;
	int anInt1546;
	int anInt1547;
	int anInt1548;
	int anInt1549;
	public int x;
	public int y;
	int anInt1552;
	final boolean[] aBooleanArray1553;
	int anInt1554;
	int anInt1555;
	int anInt1556;
	int anInt1557;
}
