package com.rs2;

import com.rs2.game.content.StaticNpcList;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Constants {

    /**
     * The Variables Below Can Be Also Changed On Server Startup By Using The ConfigLoader
     *
     * SERVER_NAME Sets The Name The Server Will Use
     * WEBSITE_LINK Defines The Server Website Links
     * WORLD Sets The Servers World ID
     * The HTTP Server port.
     * The JAGGRAB Server port.
     * GUI_ENABLED Enables/Disables The Server Control Panel
     * MAX_PLAYERS Sets The Maximum Amount Of Players Allow To Be Logged In At Once
     * SAVE_TIMER Sets In Seconds How Often The Server Shouls Auto-Save All Characters
     * RESPAWN_X Sets The X Coordinate That You Will Respawn At After Death
     * RESPAWN_Y Sets The Y Coordinate That You Will Respawn At After Death
     * FILE_SERVER Sets Whether The FileServer Should Run With The Server
     * SERVER_DEBUG Sets Whether The Server Should Start In Debug Mode
     * The Amount Of Time Before A Player Timeouts From A Bad Connection
     * MEMBERS_ONLY Sets Whether The World Is Members Only
     * TUTORIAL_ISLAND Enables/Disables Tutorial Island For Players On First Login
     * PARTY_ROOM_DISABLED Enables/Disables The Party Room Should Be Disabled
     * CLUES_ENABLED Enables/Disables Clue Scrolls
     * ITEM_REQUIREMENTS Enables/Disables Item Requirements for All Players
     * ADMIN_CAN_TRADE Defines Whether Admins Can Trade
     * ADMIN_DROP_ITEMS Defines Whether Admins Can Drop Items
     * ADMIN_CAN_SELL_ITEMS Defines Whether Admins Can Sell Items
     * VARIABLE_XP_RATE Allows Players To Choose An XP Rate Set In VARIABLE_XP_RATES
     * VARIABLE_XP_RATES Defines The XP Rates That Should Be Available To Players When VARIABLE_XP_RATES is true(Array Must Contain Four Entries)
     * XP_RATE Sets The XP Rate Multiplier For All Players/Skills If VARIABLE_XP_RATES is false
     * WEBSITE_INTEGRATION Enables/Disables Website Features(Total Accounts Registered & Players Online)
     */
    public static String SERVER_NAME = "2006Scape", WEBSITE_LINK = "https://2006Scape.org";
    public static int WORLD = 1, HTTP_PORT = 8080, JAGGRAB_PORT = 43595, MAX_PLAYERS = 200, SAVE_TIMER = 120, TIMEOUT = 60, RESPAWN_X = 3222, RESPAWN_Y = 3218,  CYCLE_LOGGING_TICK = 10;
    public static boolean GUI_ENABLED = false, FILE_SERVER = true, SERVER_DEBUG = false, MEMBERS_ONLY = false, TUTORIAL_ISLAND = false,
            PARTY_ROOM_DISABLED = false, CLUES_ENABLED = true, ITEM_REQUIREMENTS = true,
            ADMIN_CAN_TRADE = false, ADMIN_DROP_ITEMS = false, ADMIN_CAN_SELL_ITEMS = false, VARIABLE_XP_RATE = false,
            WEBSITE_INTEGRATION = false, CYCLE_LOGGING = true;
    public static int[] VARIABLE_XP_RATES = new int[] {1, 2, 5, 10};
    public static double TEST_VERSION = 2.3, XP_RATE = 1.0;

    /**
     * The Variables Below Should Only Be Changed If You Understand What You Are Doing
     */
    public final static int ITEM_LIMIT = 15000, MAXITEM_AMOUNT = Integer.MAX_VALUE,
            IPS_ALLOWED = 250, CONNECTION_DELAY = 100;

    public final static boolean sendServerPackets = false, SOUND = true, GUILDS = true;

    public static int[] SIDEBARS = { 2423, 3917, 638, 3213, 1644, 5608, 1151,
            18128, 5065, 5715, 2449, 904, 147, 962 };

    public final static int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464,
            2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
            2476, 2477 }; // fun weapons for dueling


    public final static int DUELING_RESPAWN_X = 3362;

    public final static int DUELING_RESPAWN_Y = 3263;

    public final static int NO_TELEPORT_WILD_LEVEL = 20;

    public final static int NPC_RANDOM_WALK_DISTANCE = 5;

    public final static int NPC_FOLLOW_DISTANCE = 10;

    public final static String[] UNDEAD = {
            "armoured zombie", "ankous", "banshee", "crawling hand", "dried zombie", "ghost", "ghostly warrior", "ghast",
            "mummy", "mighty banshee", "reventant imp", "reventant goblin",  "reventant icefiend",  "reventant pyrefiend",
            "reventant hobgoblin",  "reventant vampyre",  "reventant werewolf", "reventant cyclops", "reventant darkbeast",
            "reventant demon", "reventant ork",  "reventant hellhound", "reventant knight", "reventant dragon",
            "shade", "skeleton", "skeleton brute", "skeleton thug", "skeleton warload", "summoned zombie",
            "skorge", "tortured soul", "undead chicken", "undead cow", "undead one", "undead troll", "zombie", "zombie rat", "zogre"
    };

    public final static int CYCLE_TIME = 600;

    public final static int BUFFER_SIZE = 10000;

    /**
     * The directory of the file system.
     */
    public static final String FILE_SYSTEM_DIR = "./data/cache/";

    public final static String SERVER_LOG_DIR = "./data/logs/";

    /**
     * The exponent used when decrypting the RSA block.
     */
    public static final BigInteger RSA_EXPONENT = new BigInteger("33280025241734061313051117678670856264399753710527826596057587687835856000539511539311834363046145710983857746766009612538140077973762171163294453513440619295457626227183742315140865830778841533445402605660729039310637444146319289077374748018792349647460850308384280105990607337322160553135806205784213241305");;

    /**
     * The modulus used when decrypting the RSA block.
     */
    public static final BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");;


    public final static int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
            HITPOINTS = 3, RANGED = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
            WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
            CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
            AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
            RUNECRAFTING = 20;
    
    public static final LinkedHashSet<Integer> BOSS_NPC_IDS = new LinkedHashSet<>(Arrays.asList(
        StaticNpcList.CHAOS_ELEMENTAL,
        StaticNpcList.DAGANNOTH_REX,
        StaticNpcList.DAGANNOTH_PRIME,
        StaticNpcList.DAGANNOTH_SUPREME,
        StaticNpcList.GIANT_MOLE,
        StaticNpcList.KING_BLACK_DRAGON,
        StaticNpcList.KALPHITE_QUEEN,
        StaticNpcList.TZTOKJAD    
    ));

    public static final LinkedHashSet<Integer> SLAYER_NPC_IDS = new LinkedHashSet<>(Arrays.asList(
        StaticNpcList.CRAWLING_HAND,    
        StaticNpcList.CRAWLING_HAND_1649,    
        StaticNpcList.CRAWLING_HAND_1650,    
        StaticNpcList.CRAWLING_HAND_1651,    
        StaticNpcList.CRAWLING_HAND_1652,    
        StaticNpcList.CRAWLING_HAND_1653,    
        StaticNpcList.CRAWLING_HAND_1654,    
        StaticNpcList.CRAWLING_HAND_1655,    
        StaticNpcList.CRAWLING_HAND_1656,    
        StaticNpcList.CRAWLING_HAND_1657,
        StaticNpcList.CAVE_BUG,
        StaticNpcList.CAVE_CRAWLER,
        StaticNpcList.CAVE_CRAWLER_1601,
        StaticNpcList.CAVE_CRAWLER_1602,
        StaticNpcList.CAVE_CRAWLER_1603,
        StaticNpcList.BANSHEE,
        StaticNpcList.CAVE_SLIME,
        StaticNpcList.ROCKSLUG,
        StaticNpcList.ROCKSLUG_1623,
        StaticNpcList.DESERT_LIZARD,
        StaticNpcList.DESERT_LIZARD_2805,
        StaticNpcList.DESERT_LIZARD_2806,
        StaticNpcList.COCKATRICE,
        StaticNpcList.COCKATRICE_1621,
        StaticNpcList.PYREFIEND,
        StaticNpcList.PYREFIEND_1634,
        StaticNpcList.PYREFIEND_1635,
        StaticNpcList.PYREFIEND_1636,
        StaticNpcList.MOGRE,
        StaticNpcList.HARPIE_BUG_SWARM,
        StaticNpcList.WALL_BEAST,
        StaticNpcList.KILLERWATT,
        StaticNpcList.KILLERWATT_3202,
        StaticNpcList.BASILISK,
        StaticNpcList.BASILISK_1617,
        StaticNpcList.FEVER_SPIDER,
        StaticNpcList.INFERNAL_MAGE,
        StaticNpcList.INFERNAL_MAGE_1644,
        StaticNpcList.INFERNAL_MAGE_1645,
        StaticNpcList.INFERNAL_MAGE_1646,
        StaticNpcList.INFERNAL_MAGE_1647,
        StaticNpcList.JELLY,
        StaticNpcList.JELLY_1638,
        StaticNpcList.JELLY_1639,
        StaticNpcList.JELLY_1640,
        StaticNpcList.JELLY_1641,
        StaticNpcList.JELLY_1642,
        StaticNpcList.TUROTH,
        StaticNpcList.TUROTH_1627,
        StaticNpcList.TUROTH_1628,
        StaticNpcList.TUROTH_1629,
        StaticNpcList.TUROTH_1630,
        StaticNpcList.TUROTH_1631,
        StaticNpcList.TUROTH_1632,
        StaticNpcList.ABERRANT_SPECTER,
        StaticNpcList.ABERRANT_SPECTER_1605,
        StaticNpcList.ABERRANT_SPECTER_1606,
        StaticNpcList.ABERRANT_SPECTER_1607,
        StaticNpcList.DUST_DEVIL,
        StaticNpcList.KURASK,
        StaticNpcList.KURASK_1609,
        StaticNpcList.SKELETAL_WYVERN,
        StaticNpcList.SKELETAL_WYVERN_3069,
        StaticNpcList.SKELETAL_WYVERN_3070,
        StaticNpcList.SKELETAL_WYVERN_3071,
        StaticNpcList.GARGOYLE,
        StaticNpcList.GARGOYLE_1611,
        StaticNpcList.NECHRYAEL,
        StaticNpcList.ABYSSAL_DEMON,
        StaticNpcList.DARK_BEAST,
        StaticNpcList.SMOKEDEVIL
    ));
}