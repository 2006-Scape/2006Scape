package org.apollo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Contains utility methods for {@link InputStream}s and {@link OutputStream}s.
 *
 * @author Graham
 */
public final class StreamUtil {

	/**
	 * Reads a String from the specified {@link InputStream}.
	 *
	 * @param input The {@link InputStream}. Must not be {@code null}.
	 * @return The String. Will never be {@code null}. May be empty.
	 * @throws IOException If an error occurs when reading from the {@link InputStream}.
	 */
	public static String readString(InputStream input) throws IOException {
		StringBuilder builder = new StringBuilder();
		int character;

		while ((character = input.read()) != -1 && character != '\0') {
			builder.append((char) character);
		}

		return builder.toString();
	}

	/**
	 * Writes an ASCII String to the specified {@link OutputStream}.
	 *
	 * @param output The {@link OutputStream}. Must not be {@code null}.
	 * @param string The String. Must be ASCII. Must not be {@code null}.
	 * @throws IOException If an error occurs when writing to the {@link OutputStream}.
	 */
	public static void writeString(OutputStream output, String string) throws IOException {
		for (char character : string.toCharArray()) {
			output.write(character);
		}

		output.write('\0');
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private StreamUtil() {

	}

}