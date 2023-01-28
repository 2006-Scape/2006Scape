package org.apollo.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import com.google.common.base.Preconditions;
import org.apollo.util.xml.XmlNode;
import org.apollo.util.xml.XmlParser;

/**
 * Holds various network-related constants such as port numbers.
 *
 * @author Graham
 * @author Major
 */
public final class NetworkConstants {

	/**
	 * The HTTP port.
	 */
	public static final int HTTP_PORT;

	/**
	 * The number of seconds before a connection becomes idle.
	 */
	public static final int IDLE_TIME = 15;

	/**
	 * The JAGGRAB port.
	 */
	public static final int JAGGRAB_PORT;

	/**
	 * The exponent used when decrypting the RSA block.
	 */
	public static final BigInteger RSA_EXPONENT;

	/**
	 * The modulus used when decrypting the RSA block.
	 */
	public static final BigInteger RSA_MODULUS;

	/**
	 * The service port.
	 */
	public static final int SERVICE_PORT;

	static {
		try (InputStream is = new FileInputStream("data/net.xml")) {
			XmlNode net = new XmlParser().parse(is);
			if (!net.getName().equals("net")) {
				throw new IOException("Root node name is not 'net'.");
			}

			XmlNode rsa = net.getChild("rsa");
			Preconditions.checkState(rsa != null, "Root node must have a child named 'rsa'.");

			XmlNode modulus = rsa.getChild("modulus"), exponent = rsa.getChild("private-exponent");
			Preconditions.checkState(modulus != null && exponent != null, "Rsa node must have two children: 'modulus' and 'private-exponent'.");
			
			RSA_MODULUS = new BigInteger(modulus.getValue());
			RSA_EXPONENT = new BigInteger(exponent.getValue());
			
			XmlNode ports = net.getChild("ports");
			Preconditions.checkState(ports != null, "Root node must have a child named 'ports'.");

			XmlNode http = ports.getChild("http"), service = ports.getChild("service"), jaggrab = ports.getChild("jaggrab");
			Preconditions.checkState(http != null && service != null && jaggrab != null, "Ports node must have three children: 'http', 'service', and 'jaggrab'.");

			HTTP_PORT = Integer.parseInt(http.getValue());
			SERVICE_PORT = Integer.parseInt(service.getValue());
			JAGGRAB_PORT = Integer.parseInt(jaggrab.getValue());
		} catch (Exception exception) {
			throw new ExceptionInInitializerError(new IOException("Error parsing net.xml.", exception));
		}
	}

	/**
	 * Sole private constructor to prevent instantiation.
	 */
	private NetworkConstants() {

	}

}