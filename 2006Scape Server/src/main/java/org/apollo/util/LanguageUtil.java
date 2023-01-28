package org.apollo.util;

/**
 * Contains language-related utility methods.
 *
 * @author Graham
 * @author Major
 */
public final class LanguageUtil {

	/**
	 * Gets the indefinite article of the specified String.
	 *
	 * @param string The String.
	 * @return The indefinite article.
	 */
	public static String getIndefiniteArticle(String string) {
		char first = Character.toLowerCase(string.charAt(0));
		if (allUpperCase(string) && (first == 'f' || first == 'l' || first == 'm' || first == 'n' || first == 's')) {
				return "an";
		}

		boolean vowel = first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u';
		return vowel ? "an" : "a";
	}

	/**
	 * Returns whether or not the each letter in the specified String is upper case (i.e. digits etc are ignored).
	 *
	 * @param string The string.
	 * @return {@code true} if no letters in the specified String are lower case, otherwise {@code false}.
	 */
	private static boolean allUpperCase(String string) {
		for (char character : string.toCharArray()) {
			if (Character.isLowerCase(character)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Sole private constructor to prevent instantiation.
	 */
	private LanguageUtil() {

	}

}