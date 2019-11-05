import java.math.BigInteger;

/**
 * ClientSettings
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class ClientSettings {
	
	/**
	 * The Servers Name
	 */
	public final static String SERVER_NAME = "2006Redone";
	/**
	 * The Servers Website
	 */
	public final static String SERVER_WEBSITE = "www.2006redone.org";
	/**
	 * The Servers Ip
	 *
	 * You dont have to change this, the client will automatically connect to the server
	 * on localhost
	 *
	 */
	//public static String SERVER_IP = "35.226.247.68"; //NOW SET IN Main,java
	public static String SERVER_IP = "127.0.0.1";
	/**
	 * The Npc Bits for the Server
	 */
	public final static int NPC_BITS = 12;
	/**
	 * The Servers Uid
	 */
	public final static int UID = 314268572;
	
	public static final BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");
	public static final BigInteger RSA_EXPONENT = new BigInteger("65537");

	
}
