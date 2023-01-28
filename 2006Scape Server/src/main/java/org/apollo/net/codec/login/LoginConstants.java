package org.apollo.net.codec.login;

/**
 * Holds login-related constants.
 *
 * @author Graham
 */
public final class LoginConstants {

	/**
	 * Exchange data login status.
	 */
	public static final int STATUS_EXCHANGE_DATA = 0;

	/**
	 * Delay for 2 seconds login status.
	 */
	public static final int STATUS_DELAY = 1;

	/**
	 * OK login status.
	 */
	public static final int STATUS_OK = 2;

	/**
	 * Invalid credentials login status.
	 */
	public static final int STATUS_INVALID_CREDENTIALS = 3;

	/**
	 * Account disabled login status.
	 */
	public static final int STATUS_ACCOUNT_DISABLED = 4;

	/**
	 * Account online login status.
	 */
	public static final int STATUS_ACCOUNT_ONLINE = 5;

	/**
	 * Game updated login status.
	 */
	public static final int STATUS_GAME_UPDATED = 6;

	/**
	 * Server full login status.
	 */
	public static final int STATUS_SERVER_FULL = 7;

	/**
	 * Login server offline login status.
	 */
	public static final int STATUS_LOGIN_SERVER_OFFLINE = 8;

	/**
	 * Too many connections login status.
	 */
	public static final int STATUS_TOO_MANY_CONNECTIONS = 9;

	/**
	 * Bad session id login status.
	 */
	public static final int STATUS_BAD_SESSION_ID = 10;

	/**
	 * Login server rejected session login status.
	 */
	public static final int STATUS_LOGIN_SERVER_REJECTED_SESSION = 11;

	/**
	 * Members account required login status.
	 */
	public static final int STATUS_MEMBERS_ACCOUNT_REQUIRED = 12;

	/**
	 * Could not complete login status.
	 */
	public static final int STATUS_COULD_NOT_COMPLETE = 13;

	/**
	 * Server updating login status.
	 */
	public static final int STATUS_UPDATING = 14;

	/**
	 * Reconnection OK login status.
	 */
	public static final int STATUS_RECONNECTION_OK = 15;

	/**
	 * Too many login attempts login status.
	 */
	public static final int STATUS_TOO_MANY_LOGINS = 16;

	/**
	 * Standing in members area on free world status.
	 */
	public static final int STATUS_IN_MEMBERS_AREA = 17;

	/**
	 * Invalid login server status.
	 */
	public static final int STATUS_INVALID_LOGIN_SERVER = 20;

	/**
	 * Profile transfer login status.
	 */
	public static final int STATUS_PROFILE_TRANSFER = 21;

	/**
	 * Standard login type id.
	 */
	public static final int TYPE_STANDARD = 16;

	/**
	 * Reconnection login type id.
	 */
	public static final int TYPE_RECONNECTION = 18;

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private LoginConstants() {

	}

}