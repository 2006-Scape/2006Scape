package org.apollo.util.security;

/**
 * <p>
 * An implementation of the <a href="http://www.burtleburtle.net/bob/rand/isaacafa.html">ISAAC</a> psuedorandom number
 * generator.
 * </p>
 *
 * <pre>
 * ------------------------------------------------------------------------------
 * Rand.java: By Bob Jenkins.  My random number generator, ISAAC.
 *   rand.init() -- initialize
 *   rand.val()  -- get a random value
 * MODIFIED:
 *   960327: Creation (addition of randinit, really)
 *   970719: use context, not global variables, for internal state
 *   980224: Translate to Java
 * ------------------------------------------------------------------------------
 * </pre>
 * <p>
 * This class has been changed to be more conformant to Java and javadoc conventions.
 * </p>
 *
 * @author Bob Jenkins
 */
public final class IsaacRandom {

	/**
	 * The golden ratio.
	 */
	private static final int GOLDEN_RATIO = 0x9e3779b9;

	/**
	 * The log of the size of the result and state arrays.
	 */
	private static final int LOG_SIZE = Long.BYTES;

	/**
	 * The size of the result and states arrays.
	 */
	private static final int SIZE = 1 << LOG_SIZE;

	/**
	 * A mask for pseudo-random lookup.
	 */
	private static int MASK = SIZE - 1 << 2;

	/**
	 * The results given to the user.
	 */
	private final int[] results = new int[SIZE];

	/**
	 * The internal state.
	 */
	private final int[] state = new int[SIZE];

	/**
	 * The count through the results in the results array.
	 */
	private int count = SIZE;

	/**
	 * The accumulator.
	 */
	private int accumulator;

	/**
	 * The last result.
	 */
	private int last;

	/**
	 * The counter.
	 */
	private int counter;

	/**
	 * Creates the random number generator with the specified seed.
	 *
	 * @param seed The seed.
	 */
	public IsaacRandom(int[] seed) {
		int length = Math.min(seed.length, results.length);
		System.arraycopy(seed, 0, results, 0, length);
		init();
	}

	/**
	 * Generates 256 results.
	 */
	private void isaac() {
		int i, j, x, y;

		last += ++counter;
		for (i = 0, j = SIZE / 2; i < SIZE / 2;) {
			x = state[i];
			accumulator ^= accumulator << 13;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator >>> 6;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator << 2;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator >>> 16;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;
		}

		for (j = 0; j < SIZE / 2;) {
			x = state[i];
			accumulator ^= accumulator << 13;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator >>> 6;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator << 2;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;

			x = state[i];
			accumulator ^= accumulator >>> 16;
			accumulator += state[j++];
			state[i] = y = state[(x & MASK) >> 2] + accumulator + last;
			results[i++] = last = state[(y >> LOG_SIZE & MASK) >> 2] + x;
		}
	}

	/**
	 * Initializes this random number generator.
	 */
	private void init() {
		int i;
		int a, b, c, d, e, f, g, h;
		a = b = c = d = e = f = g = h = GOLDEN_RATIO;

		for (i = 0; i < 4; ++i) {
			a ^= b << 11;
			d += a;
			b += c;
			b ^= c >>> 2;
			e += b;
			c += d;
			c ^= d << 8;
			f += c;
			d += e;
			d ^= e >>> 16;
			g += d;
			e += f;
			e ^= f << 10;
			h += e;
			f += g;
			f ^= g >>> 4;
			a += f;
			g += h;
			g ^= h << 8;
			b += g;
			h += a;
			h ^= a >>> 9;
			c += h;
			a += b;
		}

		for (i = 0; i < SIZE; i += 8) { /* fill in mem[] with messy stuff */
			a += results[i];
			b += results[i + 1];
			c += results[i + 2];
			d += results[i + 3];
			e += results[i + 4];
			f += results[i + 5];
			g += results[i + 6];
			h += results[i + 7];

			a ^= b << 11;
			d += a;
			b += c;
			b ^= c >>> 2;
			e += b;
			c += d;
			c ^= d << 8;
			f += c;
			d += e;
			d ^= e >>> 16;
			g += d;
			e += f;
			e ^= f << 10;
			h += e;
			f += g;
			f ^= g >>> 4;
			a += f;
			g += h;
			g ^= h << 8;
			b += g;
			h += a;
			h ^= a >>> 9;
			c += h;
			a += b;
			state[i] = a;
			state[i + 1] = b;
			state[i + 2] = c;
			state[i + 3] = d;
			state[i + 4] = e;
			state[i + 5] = f;
			state[i + 6] = g;
			state[i + 7] = h;
		}

		for (i = 0; i < SIZE; i += 8) {
			a += state[i];
			b += state[i + 1];
			c += state[i + 2];
			d += state[i + 3];
			e += state[i + 4];
			f += state[i + 5];
			g += state[i + 6];
			h += state[i + 7];
			a ^= b << 11;
			d += a;
			b += c;
			b ^= c >>> 2;
			e += b;
			c += d;
			c ^= d << 8;
			f += c;
			d += e;
			d ^= e >>> 16;
			g += d;
			e += f;
			e ^= f << 10;
			h += e;
			f += g;
			f ^= g >>> 4;
			a += f;
			g += h;
			g ^= h << 8;
			b += g;
			h += a;
			h ^= a >>> 9;
			c += h;
			a += b;
			state[i] = a;
			state[i + 1] = b;
			state[i + 2] = c;
			state[i + 3] = d;
			state[i + 4] = e;
			state[i + 5] = f;
			state[i + 6] = g;
			state[i + 7] = h;
		}

		isaac();
	}

	/**
	 * Gets the next random value.
	 *
	 * @return The next random value.
	 */
	public int nextInt() {
		if (0 == count--) {
			isaac();
			count = SIZE - 1;
		}
		return results[count];
	}

}