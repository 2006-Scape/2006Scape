import java.math.BigInteger;

/**
 * ClientSettings
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class ClientSettings {

    public static String SNOW_MONTH = ".12";
	/**
	 * The Servers Name
	 */
	public final static String SERVER_NAME = "2006Scape";
	/**
	 * The Servers Website
	 */
	public final static String SERVER_WEBSITE = "www.2006Scape.org";
	/**
	 * The Servers Ip
	 * You don't have to change this, the client will automatically connect to the server
	 * on localhost (Assuming you're running Client and LocalGame respectively)
	 */
	public static String SERVER_IP = "localhost";
	/**
	 * The Servers World
	 * This Determines The Port The Server Will Connect On
	 * World 1 Will Connect On Port 43594
	 * World 2 Will Connect On Port 43598
	 */
	public static int SERVER_WORLD = 1;

	/**
	 * If false, the server will run the on demand server on every world. If true it will
	 * only connect to the server on world 1.
	 */
	public static boolean SINGLE_ONDEMAND  = true;

	
	/**
	 * Enables/Disables FileServer CRC Checking For Cache Updates
	 * FileServer Must Be Running Before Starting The Client If This Is True
	 */
	public static boolean CHECK_CRC = true;

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
