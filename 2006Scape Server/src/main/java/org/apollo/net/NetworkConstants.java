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
	 * The number of seconds before a connection becomes idle.
	 */
	public static final int IDLE_TIME = 15;

	/**
	 * The exponent used when decrypting the RSA block.
	 */
	public static BigInteger RSA_EXPONENT = new BigInteger("33280025241734061313051117678670856264399753710527826596057587687835856000539511539311834363046145710983857746766009612538140077973762171163294453513440619295457626227183742315140865830778841533445402605660729039310637444146319289077374748018792349647460850308384280105990607337322160553135806205784213241305");;

	/**
	 * The modulus used when decrypting the RSA block.
	 */
	public static BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");;

}