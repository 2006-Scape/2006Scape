package com.rs2

object GameConstants {
    /**
     * The Variables Below Can Be Also Changed On Server Startup By Using The ConfigLoader
     *
     * SERVER_NAME Sets The Name The Server Will Use
     * WEBSITE_LINK Defines The Server Website Links
     * WORLD Sets The Servers World ID
     * MAX_PLAYERS Sets The Maximum Amount Of Players Allow To Be Logged In At Once
     * TIMEOUT Sets The Amount Of Time Before A Player Timeouts From A Bad Connection
     * SAVE_TIMER Sets In Seconds How Often The Server Shouls Auto-Save All Characters
     * RESPAWN_X Sets The X Coordinate That You Will Respawn At After Death
     * RESPAWN_Y Sets The Y Coordinate That You Will Respawn At After Death
     * FILE_SERVER Sets Whether The FileServer Should Run With The Server
     * SERVER_DEBUG Sets Whether The Server Should Start In Debug Mode
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
	@JvmField
	var SERVER_NAME = "2006Scape"
    @JvmField
	var WEBSITE_LINK = "https://2006Scape.org"
    @JvmField
	var WORLD = 1
    @JvmField
	var MAX_PLAYERS = 200
    @JvmField
	var TIMEOUT = 60
    @JvmField
	var SAVE_TIMER = 120
    @JvmField
	var RESPAWN_X = 3222
    @JvmField
	var RESPAWN_Y = 3218
    @JvmField
	var FILE_SERVER = true
    @JvmField
	var SERVER_DEBUG = false
    @JvmField
	var MEMBERS_ONLY = false
    @JvmField
	var TUTORIAL_ISLAND = false
    @JvmField
	var PARTY_ROOM_DISABLED = false
    @JvmField
	var CLUES_ENABLED = true
    @JvmField
	var ITEM_REQUIREMENTS = true
    @JvmField
	var ADMIN_CAN_TRADE = false
    @JvmField
	var ADMIN_DROP_ITEMS = false
    @JvmField
	var ADMIN_CAN_SELL_ITEMS = false
    @JvmField
	var VARIABLE_XP_RATE = false
    @JvmField
	var WEBSITE_INTEGRATION = false
    @JvmField
	var VARIABLE_XP_RATES = intArrayOf(1, 2, 5, 10)
    @JvmField
	var TEST_VERSION = 2.3
    @JvmField
	var XP_RATE = 1.0

    /**
     * The Variables Below Should Only Be Changed If You Understand What You Are Doing
     */
    const val ITEM_LIMIT = 15000
    const val MAXITEM_AMOUNT = Int.MAX_VALUE
    const val IPS_ALLOWED = 250
    const val CONNECTION_DELAY = 100
    const val sendServerPackets = false
    const val SOUND = true
    const val GUILDS = true
    @JvmField
	var SIDEBARS = intArrayOf(
        2423, 3917, 638, 3213, 1644, 5608, 1151,
        18128, 5065, 5715, 2449, 904, 147, 962
    )
    @JvmField
	val FUN_WEAPONS = intArrayOf(
        2460, 2461, 2462, 2463, 2464,
        2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
        2476, 2477
    ) // fun weapons for dueling
    const val DUELING_RESPAWN_X = 3362
    const val DUELING_RESPAWN_Y = 3263
    const val NO_TELEPORT_WILD_LEVEL = 20
    const val NPC_RANDOM_WALK_DISTANCE = 5
    const val NPC_FOLLOW_DISTANCE = 10
    @JvmField
	val UNDEAD = arrayOf(
        "armoured zombie",
        "ankous",
        "banshee",
        "crawling hand",
        "dried zombie",
        "ghost",
        "ghostly warrior",
        "ghast",
        "mummy",
        "mighty banshee",
        "reventant imp",
        "reventant goblin",
        "reventant icefiend",
        "reventant pyrefiend",
        "reventant hobgoblin",
        "reventant vampyre",
        "reventant werewolf",
        "reventant cyclops",
        "reventant darkbeast",
        "reventant demon",
        "reventant ork",
        "reventant hellhound",
        "reventant knight",
        "reventant dragon",
        "shade",
        "skeleton",
        "skeleton brute",
        "skeleton thug",
        "skeleton warload",
        "summoned zombie",
        "skorge",
        "tortured soul",
        "undead chicken",
        "undead cow",
        "undead one",
        "undead troll",
        "zombie",
        "zombie rat",
        "zogre"
    )
    const val CYCLE_TIME = 600
    const val BUFFER_SIZE = 10000
    const val ATTACK = 0
    const val DEFENCE = 1
    const val STRENGTH = 2
    const val HITPOINTS = 3
    const val RANGED = 4
    const val PRAYER = 5
    const val MAGIC = 6
    const val COOKING = 7
    const val WOODCUTTING = 8
    const val FLETCHING = 9
    const val FISHING = 10
    const val FIREMAKING = 11
    const val CRAFTING = 12
    const val SMITHING = 13
    const val MINING = 14
    const val HERBLORE = 15
    const val AGILITY = 16
    const val THIEVING = 17
    const val SLAYER = 18
    const val FARMING = 19
    const val RUNECRAFTING = 20
}