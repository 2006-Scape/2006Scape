// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Class4 {

	public static int method155(int i, int j, int k) {
		i &= 3;
		if (i == 0) {
			return k;
		}
		if (i == 1) {
			return j;
		}
		if (i == 2) {
			return 7 - k;
		} else {
			return 7 - j;
		}
	}

	public static int method156(int i, int j, int l) {
		j &= 3;
		if (j == 0) {
			return i;
		}
		if (j == 1) {
			return 7 - l;
		}
		if (j == 2) {
			return 7 - i;
		} else {
			return l;
		}
	}

	public static int method157(int i, int j, int k, int l, int i1) {
		i &= 3;
		if (i == 0) {
			return k;
		}
		if (i == 1) {
			return l;
		}
		if (i == 2) {
			return 7 - k - (i1 - 1);
		} else {
			return 7 - l - (j - 1);
		}
	}

	public static int method158(int j, int k, int l, int i1, int j1) {
		l &= 3;
		if (l == 0) {
			return j;
		}
		if (l == 1) {
			return 7 - j1 - (i1 - 1);
		}
		if (l == 2) {
			return 7 - j - (k - 1);
		} else {
			return j1;
		}
	}

}
