import java.math.BigInteger;

/**
 * ClientSettings
 *
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class ClientSettings {
    /**
     * @QoL
     * Require control key to zoom the client
     */
    public static boolean CONTROL_KEY_ZOOMING = false;
    
    /**
     * @QoL
     * Show zoom level messages in chat when changing zoom levels
     */
    public static boolean SHOW_ZOOM_LEVEL_MESSAGES = false;
    /**
     * @QoL
     * Hide roofs
     */
    public static boolean HIDE_ROOFS = false;
    /**
     * @QoL
     * Forces Server-Wide Snow floor
     */
    public static boolean SNOW_FLOOR_FORCE_ENABLED = false;
    /**
     * @QoL
     * Toggles Server-Wide Snow floor (for the designated month)
     */
    public static boolean SNOW_FLOOR_ENABLED = true;
    /**
     * @QoL
     * Forces Server-Wide Snow overlay
     */
    public static boolean SNOW_OVERLAY_FORCE_ENABLED = false;
    /**
     * @QoL
     * Toggles Server-Wide Snow overlay (for the designated month)
     */
    public static boolean SNOW_OVERLAY_ENABLED = false;
    /**
     * @QoL
     * Enables Server-Wide Snow In The Designated Month
     */
    public static String SNOW_MONTH = ".12";
    /**
     * The Servers Name
     */
    public final static String SERVER_NAME = "2006Scape";
    /**
     * The Servers Website
     */
    public final static String SERVER_WEBSITE = "https://2006Scape.org/";
    /**
     * The Servers Ip
     * You don't have to change this, the client will automatically connect to the server
     * on localhost (Assuming you're running Client and LocalGame respectively)
     */
    public static String SERVER_IP = "server.2006scape.org";
    /**
     * The Servers World
     * This Determines The Port The Server Will Connect On
     * World 1 Will Connect On Port 43594
     * World 2 Will Connect On Port 43598
     */
    public static int SERVER_WORLD = 1;

    /**
     * @QoL
     * If false, the client will run the on demand fetcher based on SERVER_WORLD. If true it will
     * only connect to the server on world 1/Port 43594.
     */
    public static boolean SINGLE_ONDEMAND = true;

    public static boolean SHOW_NAVBAR = true;
    public static final String NAV_MAINMENU_LINK = SERVER_WEBSITE;
    public static final String NAV_WORLDMAP_LINK = SERVER_WEBSITE + "img/worldmap.jpg";
    public static final String NAV_MANUAL_LINK = SERVER_WEBSITE;
    public static final String NAV_RULES_LINK = SERVER_WEBSITE + "kbase/rules.php";

    /**
     * @QoL
     * Enables A Custom Tab With Various QoL Changes
     */
    public static boolean CUSTOM_SETTINGS_TAB = false;
    /**
     * @QoL
     * Enables Bilinear Minimap Filtering Which Smooths Out Lines And Sprites On The Minimap
     */
    public static boolean BILINEAR_MINIMAP_FILTERING = false;
    /**
     * @QoL
     * fixes overlapping lines drawn on transparent objects by post-incrementing the offset
     * note: there's 2 other instances that haven't been updated in Texture.java (misnamed) because rarely used like this
     */
    public static boolean FIX_TRANSPARENCY_OVERFLOW = false;
    /**
     * @QoL
     * render the game to 512px instead of 511px (black line on right side)
     */
    public static boolean FULL_512PX_VIEWPORT = false;

    /**
     * @QoL
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
