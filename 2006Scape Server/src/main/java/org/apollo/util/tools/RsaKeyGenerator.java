package org.apollo.util.tools;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * An RSA key generator.
 *
 * @author Graham
 * @author Major
 * @author Cube
 * @author Advocatus
 */
public final class RsaKeyGenerator {

	/**
	 * The bit count.
	 * <strong>Note:</strong> 2048 bits and above are not compatible with the client without modifications
	 */
	private static final int BIT_COUNT = 1024;

	/**
	 * The entry point of the RsaKeyGenerator.
	 *
	 * @param args The application arguments.
	 */
	public static void main(String[] args) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(BIT_COUNT);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		System.out.println("Place these keys in the client:");
		System.out.println("--------------------");
		System.out.println("public key: " + publicKey.getPublicExponent());
		System.out.println("modulus: " + publicKey.getModulus());

		System.out.println("Place these keys in the server:");
		System.out.println("--------------------");
		System.out.println("private key: " + privateKey.getPrivateExponent());
		System.out.println("modulus: " + privateKey.getModulus());
	}

}