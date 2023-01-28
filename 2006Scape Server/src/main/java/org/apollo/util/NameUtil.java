package org.apollo.util;

/**
 * Contains name-related utility methods.
 *
 * @author Graham
 */
public final class NameUtil {

	/**
	 * The name with the minimum possible value.
	 */
	private static final long FIRST_VALID_NAME = encodeBase37("");

	/**
	 * The name with the maximum possible value.
	 */
	private static final long LAST_VALID_NAME = encodeBase37("999999999999");

	/**
	 * Decodes a base-37 encoded String, from a {@code long}.
	 *
	 * @param value The encoded value.
	 * @return The decoded String. Will never be {@code null}.
	 * @throws IllegalArgumentException If the {@code value} does not represent a valid name.
	 */
	public static String decodeBase37(long value) {
		if (value < FIRST_VALID_NAME || value > LAST_VALID_NAME) {
			throw new IllegalArgumentException("Invalid name.");
		}

		char[] chars = new char[12];
		int length = 0;

		while (value != 0) {
			int remainder = (int) (value % 37);
			value /= 37;
			char character;

			if (remainder >= 1 && remainder <= 26) {
				character = (char) ('a' + remainder - 1);
			} else if (remainder >= 27 && remainder <= 36) {
				character = (char) ('0' + remainder - 27);
			} else {
				character = '_';
			}

			chars[chars.length - length++ - 1] = character;
		}

		return new String(chars, chars.length - length, length);
	}

	/**
	 * Encodes a String into a {@code long}.
	 *
	 * @param string The String to encode. Must not be {@code null}. Must be less than 12 characters in length.
	 * @return The encoded value, as a {@code long}.
	 * @throws IllegalArgumentException If the String is too long, or contains illegal characters ([^a-zA-Z0-9 _]).
	 */
	public static long encodeBase37(String string) {
		int length = string.length();
		if (length > 12) {
			throw new IllegalArgumentException("String too long.");
		}

		long value = 0;
		for (char character : string.toCharArray()) {
			value *= 37;

			if (character >= 'A' && character <= 'Z') {
				value += character - 'A' + 1;
			} else if (character >= 'a' && character <= 'z') {
				value += character - 'a' + 1;
			} else if (character >= '0' && character <= '9') {
				value += character - '0' + 27;
			} else if (character != ' ' && character != '_') {
				throw new IllegalArgumentException("Illegal character: " + character + ".");
			}
		}

		while (value != 0 && (value % 37) == 0) {
			value /= 37;
		}

		return value;
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private NameUtil() {

	}

}