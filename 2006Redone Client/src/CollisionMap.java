// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class CollisionMap {

	public CollisionMap() {
		anInt290 = 0;
		anInt291 = 0;
		anInt292 = 104;
		anInt293 = 104;
		anIntArrayArray294 = new int[anInt292][anInt293];
		method210();
	}

	public void method210() {
		for (int i = 0; i < anInt292; i++) {
			for (int j = 0; j < anInt293; j++) {
				if (i == 0 || j == 0 || i == anInt292 - 1 || j == anInt293 - 1) {
					anIntArrayArray294[i][j] = 0xffffff;
				} else {
					anIntArrayArray294[i][j] = 0x1000000;
				}
			}

		}

	}

	public void method211(int i, int j, int k, int l, boolean flag) {
		k -= anInt290;
		i -= anInt291;
		if (l == 0) {
			if (j == 0) {
				method214(k, i, 128);
				method214(k - 1, i, 8);
			}
			if (j == 1) {
				method214(k, i, 2);
				method214(k, i + 1, 32);
			}
			if (j == 2) {
				method214(k, i, 8);
				method214(k + 1, i, 128);
			}
			if (j == 3) {
				method214(k, i, 32);
				method214(k, i - 1, 2);
			}
		}
		if (l == 1 || l == 3) {
			if (j == 0) {
				method214(k, i, 1);
				method214(k - 1, i + 1, 16);
			}
			if (j == 1) {
				method214(k, i, 4);
				method214(k + 1, i + 1, 64);
			}
			if (j == 2) {
				method214(k, i, 16);
				method214(k + 1, i - 1, 1);
			}
			if (j == 3) {
				method214(k, i, 64);
				method214(k - 1, i - 1, 4);
			}
		}
		if (l == 2) {
			if (j == 0) {
				method214(k, i, 130);
				method214(k - 1, i, 8);
				method214(k, i + 1, 32);
			}
			if (j == 1) {
				method214(k, i, 10);
				method214(k, i + 1, 32);
				method214(k + 1, i, 128);
			}
			if (j == 2) {
				method214(k, i, 40);
				method214(k + 1, i, 128);
				method214(k, i - 1, 2);
			}
			if (j == 3) {
				method214(k, i, 160);
				method214(k, i - 1, 2);
				method214(k - 1, i, 8);
			}
		}
		if (flag) {
			if (l == 0) {
				if (j == 0) {
					method214(k, i, 0x10000);
					method214(k - 1, i, 4096);
				}
				if (j == 1) {
					method214(k, i, 1024);
					method214(k, i + 1, 16384);
				}
				if (j == 2) {
					method214(k, i, 4096);
					method214(k + 1, i, 0x10000);
				}
				if (j == 3) {
					method214(k, i, 16384);
					method214(k, i - 1, 1024);
				}
			}
			if (l == 1 || l == 3) {
				if (j == 0) {
					method214(k, i, 512);
					method214(k - 1, i + 1, 8192);
				}
				if (j == 1) {
					method214(k, i, 2048);
					method214(k + 1, i + 1, 32768);
				}
				if (j == 2) {
					method214(k, i, 8192);
					method214(k + 1, i - 1, 512);
				}
				if (j == 3) {
					method214(k, i, 32768);
					method214(k - 1, i - 1, 2048);
				}
			}
			if (l == 2) {
				if (j == 0) {
					method214(k, i, 0x10400);
					method214(k - 1, i, 4096);
					method214(k, i + 1, 16384);
				}
				if (j == 1) {
					method214(k, i, 5120);
					method214(k, i + 1, 16384);
					method214(k + 1, i, 0x10000);
				}
				if (j == 2) {
					method214(k, i, 20480);
					method214(k + 1, i, 0x10000);
					method214(k, i - 1, 1024);
				}
				if (j == 3) {
					method214(k, i, 0x14000);
					method214(k, i - 1, 1024);
					method214(k - 1, i, 4096);
				}
			}
		}
	}

	public void method212(boolean flag, int j, int k, int l, int i1, int j1) {
		int k1 = 256;
		if (flag) {
			k1 += 0x20000;
		}
		l -= anInt290;
		i1 -= anInt291;
		if (j1 == 1 || j1 == 3) {
			int l1 = j;
			j = k;
			k = l1;
		}
		for (int i2 = l; i2 < l + j; i2++) {
			if (i2 >= 0 && i2 < anInt292) {
				for (int j2 = i1; j2 < i1 + k; j2++) {
					if (j2 >= 0 && j2 < anInt293) {
						method214(i2, j2, k1);
					}
				}

			}
		}

	}

	public void method213(int i, int k) {
		k -= anInt290;
		i -= anInt291;
		anIntArrayArray294[k][i] |= 0x200000;
	}

	private void method214(int i, int j, int k) {
		anIntArrayArray294[i][j] |= k;
	}

	public void method215(int i, int j, boolean flag, int k, int l) {
		k -= anInt290;
		l -= anInt291;
		if (j == 0) {
			if (i == 0) {
				method217(128, k, l);
				method217(8, k - 1, l);
			}
			if (i == 1) {
				method217(2, k, l);
				method217(32, k, l + 1);
			}
			if (i == 2) {
				method217(8, k, l);
				method217(128, k + 1, l);
			}
			if (i == 3) {
				method217(32, k, l);
				method217(2, k, l - 1);
			}
		}
		if (j == 1 || j == 3) {
			if (i == 0) {
				method217(1, k, l);
				method217(16, k - 1, l + 1);
			}
			if (i == 1) {
				method217(4, k, l);
				method217(64, k + 1, l + 1);
			}
			if (i == 2) {
				method217(16, k, l);
				method217(1, k + 1, l - 1);
			}
			if (i == 3) {
				method217(64, k, l);
				method217(4, k - 1, l - 1);
			}
		}
		if (j == 2) {
			if (i == 0) {
				method217(130, k, l);
				method217(8, k - 1, l);
				method217(32, k, l + 1);
			}
			if (i == 1) {
				method217(10, k, l);
				method217(32, k, l + 1);
				method217(128, k + 1, l);
			}
			if (i == 2) {
				method217(40, k, l);
				method217(128, k + 1, l);
				method217(2, k, l - 1);
			}
			if (i == 3) {
				method217(160, k, l);
				method217(2, k, l - 1);
				method217(8, k - 1, l);
			}
		}
		if (flag) {
			if (j == 0) {
				if (i == 0) {
					method217(0x10000, k, l);
					method217(4096, k - 1, l);
				}
				if (i == 1) {
					method217(1024, k, l);
					method217(16384, k, l + 1);
				}
				if (i == 2) {
					method217(4096, k, l);
					method217(0x10000, k + 1, l);
				}
				if (i == 3) {
					method217(16384, k, l);
					method217(1024, k, l - 1);
				}
			}
			if (j == 1 || j == 3) {
				if (i == 0) {
					method217(512, k, l);
					method217(8192, k - 1, l + 1);
				}
				if (i == 1) {
					method217(2048, k, l);
					method217(32768, k + 1, l + 1);
				}
				if (i == 2) {
					method217(8192, k, l);
					method217(512, k + 1, l - 1);
				}
				if (i == 3) {
					method217(32768, k, l);
					method217(2048, k - 1, l - 1);
				}
			}
			if (j == 2) {
				if (i == 0) {
					method217(0x10400, k, l);
					method217(4096, k - 1, l);
					method217(16384, k, l + 1);
				}
				if (i == 1) {
					method217(5120, k, l);
					method217(16384, k, l + 1);
					method217(0x10000, k + 1, l);
				}
				if (i == 2) {
					method217(20480, k, l);
					method217(0x10000, k + 1, l);
					method217(1024, k, l - 1);
				}
				if (i == 3) {
					method217(0x14000, k, l);
					method217(1024, k, l - 1);
					method217(4096, k - 1, l);
				}
			}
		}
	}

	public void method216(int i, int j, int k, int l, int i1, boolean flag) {
		int j1 = 256;
		if (flag) {
			j1 += 0x20000;
		}
		k -= anInt290;
		l -= anInt291;
		if (i == 1 || i == 3) {
			int k1 = j;
			j = i1;
			i1 = k1;
		}
		for (int l1 = k; l1 < k + j; l1++) {
			if (l1 >= 0 && l1 < anInt292) {
				for (int i2 = l; i2 < l + i1; i2++) {
					if (i2 >= 0 && i2 < anInt293) {
						method217(j1, l1, i2);
					}
				}

			}
		}

	}

	private void method217(int i, int j, int k) {
		anIntArrayArray294[j][k] &= 0xffffff - i;
	}

	public void method218(int j, int k) {
		k -= anInt290;
		j -= anInt291;
		anIntArrayArray294[k][j] &= 0xdfffff;
	}

	public boolean method219(int i, int j, int k, int i1, int j1, int k1) {
		if (j == i && k == k1) {
			return true;
		}
		j -= anInt290;
		k -= anInt291;
		i -= anInt290;
		k1 -= anInt291;
		if (j1 == 0) {
			if (i1 == 0) {
				if (j == i - 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 + 1 && (anIntArrayArray294[j][k] & 0x1280120) == 0) {
					return true;
				}
				if (j == i && k == k1 - 1 && (anIntArrayArray294[j][k] & 0x1280102) == 0) {
					return true;
				}
			} else if (i1 == 1) {
				if (j == i && k == k1 + 1) {
					return true;
				}
				if (j == i - 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280108) == 0) {
					return true;
				}
				if (j == i + 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280180) == 0) {
					return true;
				}
			} else if (i1 == 2) {
				if (j == i + 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 + 1 && (anIntArrayArray294[j][k] & 0x1280120) == 0) {
					return true;
				}
				if (j == i && k == k1 - 1 && (anIntArrayArray294[j][k] & 0x1280102) == 0) {
					return true;
				}
			} else if (i1 == 3) {
				if (j == i && k == k1 - 1) {
					return true;
				}
				if (j == i - 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280108) == 0) {
					return true;
				}
				if (j == i + 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280180) == 0) {
					return true;
				}
			}
		}
		if (j1 == 2) {
			if (i1 == 0) {
				if (j == i - 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 + 1) {
					return true;
				}
				if (j == i + 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280180) == 0) {
					return true;
				}
				if (j == i && k == k1 - 1 && (anIntArrayArray294[j][k] & 0x1280102) == 0) {
					return true;
				}
			} else if (i1 == 1) {
				if (j == i - 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280108) == 0) {
					return true;
				}
				if (j == i && k == k1 + 1) {
					return true;
				}
				if (j == i + 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 - 1 && (anIntArrayArray294[j][k] & 0x1280102) == 0) {
					return true;
				}
			} else if (i1 == 2) {
				if (j == i - 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280108) == 0) {
					return true;
				}
				if (j == i && k == k1 + 1 && (anIntArrayArray294[j][k] & 0x1280120) == 0) {
					return true;
				}
				if (j == i + 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 - 1) {
					return true;
				}
			} else if (i1 == 3) {
				if (j == i - 1 && k == k1) {
					return true;
				}
				if (j == i && k == k1 + 1 && (anIntArrayArray294[j][k] & 0x1280120) == 0) {
					return true;
				}
				if (j == i + 1 && k == k1 && (anIntArrayArray294[j][k] & 0x1280180) == 0) {
					return true;
				}
				if (j == i && k == k1 - 1) {
					return true;
				}
			}
		}
		if (j1 == 9) {
			if (j == i && k == k1 + 1 && (anIntArrayArray294[j][k] & 0x20) == 0) {
				return true;
			}
			if (j == i && k == k1 - 1 && (anIntArrayArray294[j][k] & 2) == 0) {
				return true;
			}
			if (j == i - 1 && k == k1 && (anIntArrayArray294[j][k] & 8) == 0) {
				return true;
			}
			if (j == i + 1 && k == k1 && (anIntArrayArray294[j][k] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean method220(int i, int j, int k, int l, int i1, int j1) {
		if (j1 == i && k == j) {
			return true;
		}
		j1 -= anInt290;
		k -= anInt291;
		i -= anInt290;
		j -= anInt291;
		if (l == 6 || l == 7) {
			if (l == 7) {
				i1 = i1 + 2 & 3;
			}
			if (i1 == 0) {
				if (j1 == i + 1 && k == j && (anIntArrayArray294[j1][k] & 0x80) == 0) {
					return true;
				}
				if (j1 == i && k == j - 1 && (anIntArrayArray294[j1][k] & 2) == 0) {
					return true;
				}
			} else if (i1 == 1) {
				if (j1 == i - 1 && k == j && (anIntArrayArray294[j1][k] & 8) == 0) {
					return true;
				}
				if (j1 == i && k == j - 1 && (anIntArrayArray294[j1][k] & 2) == 0) {
					return true;
				}
			} else if (i1 == 2) {
				if (j1 == i - 1 && k == j && (anIntArrayArray294[j1][k] & 8) == 0) {
					return true;
				}
				if (j1 == i && k == j + 1 && (anIntArrayArray294[j1][k] & 0x20) == 0) {
					return true;
				}
			} else if (i1 == 3) {
				if (j1 == i + 1 && k == j && (anIntArrayArray294[j1][k] & 0x80) == 0) {
					return true;
				}
				if (j1 == i && k == j + 1 && (anIntArrayArray294[j1][k] & 0x20) == 0) {
					return true;
				}
			}
		}
		if (l == 8) {
			if (j1 == i && k == j + 1 && (anIntArrayArray294[j1][k] & 0x20) == 0) {
				return true;
			}
			if (j1 == i && k == j - 1 && (anIntArrayArray294[j1][k] & 2) == 0) {
				return true;
			}
			if (j1 == i - 1 && k == j && (anIntArrayArray294[j1][k] & 8) == 0) {
				return true;
			}
			if (j1 == i + 1 && k == j && (anIntArrayArray294[j1][k] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean method221(int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = j + j1 - 1;
		int i2 = i + l - 1;
		if (k >= j && k <= l1 && k1 >= i && k1 <= i2) {
			return true;
		}
		if (k == j - 1 && k1 >= i && k1 <= i2 && (anIntArrayArray294[k - anInt290][k1 - anInt291] & 8) == 0 && (i1 & 8) == 0) {
			return true;
		}
		if (k == l1 + 1 && k1 >= i && k1 <= i2 && (anIntArrayArray294[k - anInt290][k1 - anInt291] & 0x80) == 0 && (i1 & 2) == 0) {
			return true;
		}
		return k1 == i - 1 && k >= j && k <= l1 && (anIntArrayArray294[k - anInt290][k1 - anInt291] & 2) == 0 && (i1 & 4) == 0 || k1 == i2 + 1 && k >= j && k <= l1 && (anIntArrayArray294[k - anInt290][k1 - anInt291] & 0x20) == 0 && (i1 & 1) == 0;
	}

	private final int anInt290;
	private final int anInt291;
	private final int anInt292;
	private final int anInt293;
	public final int[][] anIntArrayArray294;
}
