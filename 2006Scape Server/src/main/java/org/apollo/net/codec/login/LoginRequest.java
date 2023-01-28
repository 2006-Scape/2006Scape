package org.apollo.net.codec.login;

import org.apollo.util.security.IsaacRandomPair;
import org.apollo.util.security.PlayerCredentials;

/**
 * Represents a login request.
 *
 * @author Graham
 */
public final class LoginRequest {

	/**
	 * The archive CRCs.
	 */
	private final int[] archiveCrcs;

	/**
	 * The version denoting whether the client has been modified or not.
	 */
	private final int clientVersion;

	/**
	 * The player's credentials.
	 */
	private final PlayerCredentials credentials;

	/**
	 * The low memory flag.
	 */
	private final boolean lowMemory;

	/**
	 * The pair of random number generators.
	 */
	private final IsaacRandomPair randomPair;

	/**
	 * The reconnecting flag.
	 */
	private final boolean reconnecting;

	/**
	 * The release number.
	 */
	private final int releaseNumber;

	/**
	 * Creates a login request.
	 *
	 * @param credentials The player credentials.
	 * @param randomPair The pair of random number generators.
	 * @param lowMemory The low memory flag.
	 * @param reconnecting The reconnecting flag.
	 * @param releaseNumber The release number.
	 * @param archiveCrcs The archive CRCs.
	 * @param clientVersion The client version.
	 */
	public LoginRequest(PlayerCredentials credentials, IsaacRandomPair randomPair, boolean lowMemory, boolean reconnecting, int releaseNumber, int[] archiveCrcs, int clientVersion) {
		this.credentials = credentials;
		this.randomPair = randomPair;
		this.lowMemory = lowMemory;
		this.reconnecting = reconnecting;
		this.releaseNumber = releaseNumber;
		this.archiveCrcs = archiveCrcs;
		this.clientVersion = clientVersion;
	}

	/**
	 * Gets the archive CRCs.
	 *
	 * @return The array of archive CRCs.
	 */
	public int[] getArchiveCrcs() {
		return archiveCrcs;
	}

	/**
	 * Gets the value denoting the client's (modified) version.
	 *
	 * @return The client version.
	 */
	public int getClientVersion() {
		return clientVersion;
	}

	/**
	 * Gets the player's credentials.
	 *
	 * @return The player's credentials.
	 */
	public PlayerCredentials getCredentials() {
		return credentials;
	}

	/**
	 * Gets the pair of random number generators.
	 *
	 * @return The pair of random number generators.
	 */
	public IsaacRandomPair getRandomPair() {
		return randomPair;
	}

	/**
	 * Gets the release number.
	 *
	 * @return The release number.
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Checks if this client is in low memory mode.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isLowMemory() {
		return lowMemory;
	}

	/**
	 * Checks if this client is reconnecting.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isReconnecting() {
		return reconnecting;
	}

}