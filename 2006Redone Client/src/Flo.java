// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Flo {

	public static void unpackConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("flo.dat"));
		int cacheSize = stream.readUnsignedWord();
		if (cache == null) {
			cache = new Flo[cacheSize];
		}
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null) {
				cache[j] = new Flo();
			}
			cache[j].readValues(stream);
		}
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			} else if (i == 1) {
				anInt390 = stream.read3Bytes();
				method262(anInt390);
			} else if (i == 2) {
				anInt391 = stream.readUnsignedByte();
			} else if (i == 3) {
			} else if (i == 5) {
				aBoolean393 = false;
			} else if (i == 6) {
				stream.readString();
			} else if (i == 7) {
				int j = anInt394;
				int k = anInt395;
				int l = anInt396;
				int i1 = anInt397;
				int j1 = stream.read3Bytes();
				method262(j1);
				anInt394 = j;
				anInt395 = k;
				anInt396 = l;
				anInt397 = i1;
				anInt398 = i1;
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	private void method262(int i) {
		double d = (i >> 16 & 0xff) / 256D;
		double d1 = (i >> 8 & 0xff) / 256D;
		double d2 = (i & 0xff) / 256D;
		double d3 = d;
		if (d1 < d3) {
			d3 = d1;
		}
		if (d2 < d3) {
			d3 = d2;
		}
		double d4 = d;
		if (d1 > d4) {
			d4 = d1;
		}
		if (d2 > d4) {
			d4 = d2;
		}
		double d5 = 0.0D;
		double d6 = 0.0D;
		double d7 = (d3 + d4) / 2D;
		if (d3 != d4) {
			if (d7 < 0.5D) {
				d6 = (d4 - d3) / (d4 + d3);
			}
			if (d7 >= 0.5D) {
				d6 = (d4 - d3) / (2D - d4 - d3);
			}
			if (d == d4) {
				d5 = (d1 - d2) / (d4 - d3);
			} else if (d1 == d4) {
				d5 = 2D + (d2 - d) / (d4 - d3);
			} else if (d2 == d4) {
				d5 = 4D + (d - d1) / (d4 - d3);
			}
		}
		d5 /= 6D;
		anInt394 = (int) (d5 * 256D);
		anInt395 = (int) (d6 * 256D);
		anInt396 = (int) (d7 * 256D);
		if (anInt395 < 0) {
			anInt395 = 0;
		} else if (anInt395 > 255) {
			anInt395 = 255;
		}
		if (anInt396 < 0) {
			anInt396 = 0;
		} else if (anInt396 > 255) {
			anInt396 = 255;
		}
		if (d7 > 0.5D) {
			anInt398 = (int) ((1.0D - d7) * d6 * 512D);
		} else {
			anInt398 = (int) (d7 * d6 * 512D);
		}
		if (anInt398 < 1) {
			anInt398 = 1;
		}
		anInt397 = (int) (d5 * anInt398);
		int k = anInt394 + (int) (Math.random() * 16D) - 8;
		if (k < 0) {
			k = 0;
		} else if (k > 255) {
			k = 255;
		}
		int l = anInt395 + (int) (Math.random() * 48D) - 24;
		if (l < 0) {
			l = 0;
		} else if (l > 255) {
			l = 255;
		}
		int i1 = anInt396 + (int) (Math.random() * 48D) - 24;
		if (i1 < 0) {
			i1 = 0;
		} else if (i1 > 255) {
			i1 = 255;
		}
		anInt399 = method263(k, l, i1);
	}

	private int method263(int i, int j, int k) {
		if (k > 179) {
			j /= 2;
		}
		if (k > 192) {
			j /= 2;
		}
		if (k > 217) {
			j /= 2;
		}
		if (k > 243) {
			j /= 2;
		}
		return (i / 4 << 10) + (j / 32 << 7) + k / 2;
	}

	private Flo() {
		anInt391 = -1;
		aBoolean393 = true;
	}

	public static Flo cache[];
	public int anInt390;
	public int anInt391;
	public boolean aBoolean393;
	public int anInt394;
	public int anInt395;
	public int anInt396;
	public int anInt397;
	public int anInt398;
	public int anInt399;
	
}
