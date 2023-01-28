package org.apollo.cache.map;

/*
 * Copyright (c) 2012-2013 Jonathan Edgecombe <jonathanedgecombe@gmail.com>
 * Copyright (c) 2015 Major
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

/**
 * Contains tile-related utility methods.
 *
 * @author Johnny
 * @author Major
 */
public final class TileUtils {

	/**
	 * The x coordinate offset, used for computing the Tile height.
	 */
	static final int TILE_HEIGHT_X_OFFSET = 0xe3b7b;

	/**
	 * The z coordinate offset, used for computing the Tile height.
	 */
	static final int TILE_HEIGHT_Z_OFFSET = 0x87cce;

	/**
	 * The cosine table used for interpolation.
	 */
	private static final int[] COSINE = new int[2048];

	static {
		for (int index = 0; index < COSINE.length; index++) {
			COSINE[index] = (int) (65536 * Math.cos(2 * Math.PI * index / COSINE.length));
		}
	}

	/**
	 * Calculates the height offset for the specified coordinate pair.
	 *
	 * @param x The x coordinate of the Tile.
	 * @param z The z coordinate of the Tile.
	 * @return The height offset.
	 */
	public static int calculateHeight(int x, int z) {
		int regionSize = 8;
		int regionOffset = 6;
		int offset = regionOffset * regionSize;

		int baseX = x - offset;
		int baseZ = z - offset;

		return computeHeight(x + TILE_HEIGHT_X_OFFSET - baseX, z + TILE_HEIGHT_Z_OFFSET - baseZ)
			* MapConstants.HEIGHT_MULTIPLICAND;
	}

	/**
	 * Gets the height offset for the specified coordinate pair.
	 *
	 * @param x The offset-x coordinate of the tile.
	 * @param z The offset-z coordinate of the tile.
	 * @return The tile height offset.
	 */
	private static int computeHeight(int x, int z) {
		int total = interpolatedNoise(x + 45365, z + 91923, 4) - 128;

		total += (interpolatedNoise(x + 10294, z + 37821, 2) - 128) / 2;
		total += (interpolatedNoise(x, z, 1) - 128) / 4;

		total = (int) Math.max(total * 0.3 + 35, 10);
		return Math.min(total, 60);
	}

	/**
	 * Interpolates two smooth noise values.
	 *
	 * @param a The first smooth noise value.
	 * @param b The second smooth noise value.
	 * @param theta The angle.
	 * @param reciprocal The frequency reciprocal.
	 * @return The interpolated value.
	 */
	private static int interpolate(int a, int b, int theta, int reciprocal) {
		int cosine = 65536 - COSINE[theta * COSINE.length / (2 * reciprocal)] / 2;
		return (a * (65536 - cosine)) / 65536 + (b * cosine) / 65536;
	}

	/**
	 * Gets interpolated noise for the specified coordinate pair, using the specified frequency reciprocal.
	 *
	 * @param x The x coordinate.
	 * @param z The z coordinate.
	 * @param reciprocal The frequency reciprocal.
	 * @return The interpolated noise.
	 */
	private static int interpolatedNoise(int x, int z, int reciprocal) {
		int xt = x % reciprocal;
		int zt = z % reciprocal;

		x /= reciprocal;
		z /= reciprocal;

		int c = smoothNoise(x, z);
		int e = smoothNoise(x + 1, z);
		int ce = interpolate(c, e, xt, reciprocal);

		int n = smoothNoise(x, z + 1);
		int ne = smoothNoise(x + 1, z + 1);
		int u = interpolate(n, ne, xt, reciprocal);

		return interpolate(ce, u, zt, reciprocal);
	}

	/**
	 * Computes noise for the specified coordinate pair.
	 *
	 * @param x The x coordinate.
	 * @param z The z coordinate.
	 * @return The noise.
	 */
	private static int noise(int x, int z) {
		int n = x + z * 57;
		n = (n << 13) ^ n;
		n = (n * (n * n * 15731 + 789221) + 1376312589) & Integer.MAX_VALUE;
		return (n >> 19) & 0xff;
	}

	/**
	 * Computes smooth noise for the specified coordinate pair.
	 *
	 * @param x The x coordinate.
	 * @param z The z coordinate.
	 * @return The smooth noise.
	 */
	private static int smoothNoise(int x, int z) {
		int corners = noise(x - 1, z - 1) + noise(x + 1, z - 1) + noise(x - 1, z + 1) + noise(x + 1, z + 1);
		int sides = noise(x - 1, z) + noise(x + 1, z) + noise(x, z - 1) + noise(x, z + 1);
		int center = noise(x, z);

		return corners / 16 + sides / 8 + center / 4;
	}

	/**
	 * Sole private constructor to prevent instantiation.
	 */
	private TileUtils() {

	}

}