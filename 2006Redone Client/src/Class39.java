// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Class39 {

	private float method541(int i, int j, float f) {
		float f1 = anIntArrayArrayArray667[i][0][j] + f * (anIntArrayArrayArray667[i][1][j] - anIntArrayArrayArray667[i][0][j]);
		f1 *= 0.001525879F;
		return 1.0F - (float) Math.pow(10D, -f1 / 20F);
	}

	private float method542(float f) {
		float f1 = 32.7032F * (float) Math.pow(2D, f);
		return f1 * 3.141593F / 11025F;
	}

	private float method543(float f, int i, int j) {
		float f1 = anIntArrayArrayArray666[j][0][i] + f * (anIntArrayArrayArray666[j][1][i] - anIntArrayArrayArray666[j][0][i]);
		f1 *= 0.0001220703F;
		return method542(f1);
	}

	public int method544(int i, float f) {
		if (i == 0) {
			float f1 = anIntArray668[0] + (anIntArray668[1] - anIntArray668[0]) * f;
			f1 *= 0.003051758F;
			aFloat671 = (float) Math.pow(0.10000000000000001D, f1 / 20F);
			anInt672 = (int) (aFloat671 * 65536F);
		}
		if (anIntArray665[i] == 0) {
			return 0;
		}
		float f2 = method541(i, 0, f);
		aFloatArrayArray669[i][0] = -2F * f2 * (float) Math.cos(method543(f, 0, i));
		aFloatArrayArray669[i][1] = f2 * f2;
		for (int k = 1; k < anIntArray665[i]; k++) {
			float f3 = method541(i, k, f);
			float f4 = -2F * f3 * (float) Math.cos(method543(f, k, i));
			float f5 = f3 * f3;
			aFloatArrayArray669[i][k * 2 + 1] = aFloatArrayArray669[i][k * 2 - 1] * f5;
			aFloatArrayArray669[i][k * 2] = aFloatArrayArray669[i][k * 2 - 1] * f4 + aFloatArrayArray669[i][k * 2 - 2] * f5;
			for (int j1 = k * 2 - 1; j1 >= 2; j1--) {
				aFloatArrayArray669[i][j1] += aFloatArrayArray669[i][j1 - 1] * f4 + aFloatArrayArray669[i][j1 - 2] * f5;
			}

			aFloatArrayArray669[i][1] += aFloatArrayArray669[i][0] * f4 + f5;
			aFloatArrayArray669[i][0] += f4;
		}

		if (i == 0) {
			for (int l = 0; l < anIntArray665[0] * 2; l++) {
				aFloatArrayArray669[0][l] *= aFloat671;
			}

		}
		for (int i1 = 0; i1 < anIntArray665[i] * 2; i1++) {
			anIntArrayArray670[i][i1] = (int) (aFloatArrayArray669[i][i1] * 65536F);
		}

		return anIntArray665[i] * 2;
	}

	public void method545(Stream stream, Class29 class29) {
		int i = stream.readUnsignedByte();
		anIntArray665[0] = i >> 4;
		anIntArray665[1] = i & 0xf;
		if (i != 0) {
			anIntArray668[0] = stream.readUnsignedWord();
			anIntArray668[1] = stream.readUnsignedWord();
			int j = stream.readUnsignedByte();
			for (int k = 0; k < 2; k++) {
				for (int l = 0; l < anIntArray665[k]; l++) {
					anIntArrayArrayArray666[k][0][l] = stream.readUnsignedWord();
					anIntArrayArrayArray667[k][0][l] = stream.readUnsignedWord();
				}

			}

			for (int i1 = 0; i1 < 2; i1++) {
				for (int j1 = 0; j1 < anIntArray665[i1]; j1++) {
					if ((j & 1 << i1 * 4 << j1) != 0) {
						anIntArrayArrayArray666[i1][1][j1] = stream.readUnsignedWord();
						anIntArrayArrayArray667[i1][1][j1] = stream.readUnsignedWord();
					} else {
						anIntArrayArrayArray666[i1][1][j1] = anIntArrayArrayArray666[i1][0][j1];
						anIntArrayArrayArray667[i1][1][j1] = anIntArrayArrayArray667[i1][0][j1];
					}
				}

			}

			if (j != 0 || anIntArray668[1] != anIntArray668[0]) {
				class29.method326(stream);
			}
		} else {
			anIntArray668[0] = anIntArray668[1] = 0;
		}
	}

	public Class39() {
		anIntArray665 = new int[2];
		anIntArrayArrayArray666 = new int[2][2][4];
		anIntArrayArrayArray667 = new int[2][2][4];
		anIntArray668 = new int[2];
	}

	final int[] anIntArray665;
	private final int[][][] anIntArrayArrayArray666;
	private final int[][][] anIntArrayArrayArray667;
	private final int[] anIntArray668;
	private static final float[][] aFloatArrayArray669 = new float[2][8];
	static final int[][] anIntArrayArray670 = new int[2][8];
	private static float aFloat671;
	static int anInt672;

}
