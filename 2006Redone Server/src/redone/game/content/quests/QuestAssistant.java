package redone.game.content.quests;

import redone.game.content.quests.impl.KnightsSword;
import redone.game.content.quests.impl.ShieldArrav;
import redone.game.players.Client;

/**
 * Quest Assistant
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class QuestAssistant {

	public static final int MAXIMUM_QUESTPOINTS = 23;

	public static void sendStages(Client client) {
		client.getPlayerAssistant().sendFrame126("QP: " + client.questPoints + " ", 3985);
		for (Quests quests : Quests.values()) {
			if (!quests.questStatus() && quests.getStringId() > 0) {
				client.getPlayerAssistant().sendFrame126("", quests.getStringId());
			}
		}
		if (client.pirateTreasure == 0) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 7341);
		} else if (client.pirateTreasure == 6) {
			client.getPlayerAssistant().sendFrame126("@gre@Pirate's Treasure", 7341);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Pirate's Treasure", 7341);
		}
		if (client.witchspot == 0) {
			client.getPlayerAssistant().sendFrame126("Witch's Potion", 7348);
		} else if (client.witchspot == 3) {
			client.getPlayerAssistant().sendFrame126("@gre@Witch's Potion", 7348);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Witch's Potion", 7348);
		}
		if (client.romeojuliet == 0) {
			client.getPlayerAssistant().sendFrame126("Romeo and Juliet", 7343);
		} else if (client.romeojuliet < 9) {
			client.getPlayerAssistant().sendFrame126("@yel@Romeo and Juliet",
					7343);
		} else if (client.romeojuliet >= 9) {
			client.getPlayerAssistant().sendFrame126("@gre@Romeo and Juliet",
					7343);
		}
		if (client.vampSlayer == 0) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 7347);
		} else if (client.vampSlayer == 5) {
			client.getPlayerAssistant().sendFrame126("@gre@Vampyre Slayer", 7347);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Vampyre Slayer", 7347);
		}
		if (client.doricQuest == 0) {
			client.getPlayerAssistant().sendFrame126("Doric's Quest", 7336);
		} else if (client.doricQuest == 3) {
			client.getPlayerAssistant().sendFrame126("@gre@Doric's Quest", 7336);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Doric's Quest", 7336);
		}
		if (client.restGhost == 0) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 7337);
		} else if (client.restGhost == 5) {
			client.getPlayerAssistant().sendFrame126("@gre@Restless Ghost", 7337);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Restless Ghost", 7337);
		}
		if (client.impsC == 0) {
			client.getPlayerAssistant().sendFrame126("Imp Catcher", 7340);
		} else if (client.impsC == 1) {
			client.getPlayerAssistant().sendFrame126("@yel@Imp Catcher", 7340);
		} else if (client.impsC == 2) {
			client.getPlayerAssistant().sendFrame126("@gre@Imp Catcher", 7340);
		}
		if (client.gertCat == 0) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 7360);
		} else if (client.gertCat == 7) {
			client.getPlayerAssistant().sendFrame126("@gre@Gertrudes Cat", 7360);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Gertrudes Cat", 7360);
		}
		if (client.sheepShear == 0) {
			client.getPlayerAssistant().sendFrame126("Sheep Shearer", 7344);
		} else if (client.sheepShear == 2) {
			client.getPlayerAssistant().sendFrame126("@gre@Sheep Shearer", 7344);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Sheep Shearer", 7344);
		}
		if (client.runeMist == 0) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 7335);
		} else if (client.runeMist == 4) {
			client.getPlayerAssistant().sendFrame126("@gre@Rune Mysteries", 7335);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Rune Mysteries", 7335);
		}
		if (client.knightS == 0) {
			client.getPlayerAssistant().sendFrame126("The Knight's Sword", 7346);
		} else if (client.knightS < 9) {
			client.getPlayerAssistant().sendFrame126("@yel@The Knight's Sword", 7346);
		} else if (client.knightS == 9) {
			client.getPlayerAssistant().sendFrame126("@gre@The Knight's Sword", 7346);
		}
		if (client.cookAss == 0) {
			client.getPlayerAssistant().sendFrame126("Cook's Assistant", 7333);
		} else if (client.cookAss == 3) {
			client.getPlayerAssistant().sendFrame126("@gre@Cook's Assistant", 7333);
		} else if (client.cookAss > 0 && client.cookAss < 3) {
			client.getPlayerAssistant().sendFrame126("@yel@Cook's Assistant", 7333);
		}
		if (client.blackKnight == 0) {
			client.getPlayerAssistant().sendFrame126("Black Knights' Fortress", 7332);
		} else if (client.blackKnight == 3) {
			client.getPlayerAssistant().sendFrame126("@gre@Black Knights' Fortress", 7332);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Black Knights' Fortress", 7332);
		}
		if (client.shieldArrav == 0) {
			client.getPlayerAssistant().sendFrame126("Shield of Arrav", 7345);
		} else if (client.shieldArrav == 8) {
			client.getPlayerAssistant().sendFrame126("@gre@Shield of Arrav", 7345);
		} else {
			client.getPlayerAssistant().sendFrame126("@yel@Shield of Arrav", 7345);
		}
	}

	public enum Quests {
		BLACK_KNIGHT(28164, 7332, "Black Knights' Fortress", true), 
		COOKS_ASSISTANT(28165, 7333, "Cook's Assistant", true), 
		DEMON_SLAYER(28166, 7334, "Demon Slayer", false), 
		DORICS_QUEST(28168, 7336, "Doric's Quest", true), 
		DRAGON_SLAYER(28215, 7383, "Dragon Slayer", false),
		ERNEST(28171, 7339, "Ernest the Chicken", false), 
		GOBLIN(28170, 7338, "Goblin Diplomacy", false), 
		IMP_CATCHER(28172, 7340, "Imp Catcher", true), 
		KNIGHTS_SWORD(28178, 7346, "The Knight's Sword", true), 
		PIRATES_TREASURE(28173, 7341, "Pirates Treasure", true), 
		PRINCE_RESCUE(28174, 7342, "Prince Ali Rescue", false), 
		RESTLESS_GHOST(28169, 7337, "Restless Ghost", true), 
		ROMEO_JULIET(28175, 7343, "Romeo Juliet", false), 
		RUNE_MYSTERIES(28167, 7335, "Rune Mysteries", true), 
		SHEEP_SHEARER(28176, 7344, "Sheep Shearer", true), 
		SHIELD_OF_ARRAV(28177, 7345, "Shield of Arrav", true),
		VAMPYRE_SLAYER(28179, 7347, "Vampyre Slayer", true), 
		WITCHS_POTION(28180, 7348, "Witchs Potion", true), 
		BETWEEN_A_ROCK(49228, 12772, "Between A Rock", false), 
		CHOMPY(2161, 673, "Big Chompy Bird Hunting", false), 
		BIOHAZARD(28124, 7352, "Biohazard", false), 
		CABIN(68102, 17510, "Cabin Fever", false), 
		CLOCK(28185, 7353, "Clock Tower", false), 
		DEATH(32246, 8438, "Death Plateau", false), 
		CREATURE(47097, 12129, "Creature of Fenkenstrain", false), 
		DESERT_TREASURE(50052, 12852, "Desert Treasure", false), 
		DRUDIC_RITUAL(28187, 7355, "Drudic Ritual", false), 
		DWARF_CANNON(28188, 7356, "Dwarf Cannon", false), 
		EADGARS_RUSE(33231, 8679, "Eadgars Ruse", false), 
		DEVIOUS(61225, 15841, "Devious Minds", false), 
		DIGSITE(28186, 7354, "Digsite Quest", false), 
		ELEMENTAL(29035, 7459, "Elemental Workshop", false), 
		ENAKHRA(63021, 16149, "Enakhra's Lamet", false), 
		FAIRY1(27075, 6987, "A Fairy Tale Pt. 1", false), 
		FAMILYCREST(28189, 7357, "Family Crest", false), 
		FEUD(50036, 12836, "The Feud", false), 
		FIGHT_ARENA(28190, 7358, "Fight Arena", false), 
		FISHING_CONTEST(28191, 7359, "Fishing Contest", false),
		FORGETTABLE_TABLE(50089, 14169, "Forgettable Tale...", false), 
		FREMMY_TRIALS(39131, 10115, "The Fremennik Trials", false), 
		GARDEN(57012, 14604, "Garden of Tranquillity", false), 
		GERTRUDES_CAT(28192, 7360, "Gertrude's Cat", true),
		GHOSTS(47250, 12282, "Ghosts Ahoy", false), 
		GIANT_DWARF(53009, 13577, "The Giant Dwarf", false), 
		GOLEM(50039, 12839, "The Golem", false), 
		GRAND_TREE(28193, 7361, "The Grand Tree", false), 
		HAND_IN_THE_SAND(63000, 16128, "The Hand in the Sand", false), 
		HAUNTED_MINE(46081, 11857, "Haunted Mine", false), 
		HAZEEL(28194, 7362, "Hazeel Cult", false), 
		HEROES(28195, 7363, "Heroes Quest", false), 
		HOLY(28196, 7364, "Holy Grail", false), 
		HORROR(39151, 10135, "Horror from the Deep", false), 
		ITCHLARIN(17156, 4508, "Itchlarin's Little Helper", false), 
		AID_OF_MYREQUE(72085, 18517, "In Aid of the Myreque", false), 
		SEARCH_OF_MYREQUE(46131, 11907, "In Search of the Myreque", false), 
		JUNGLE_POTION(28197, 7365, "Jungle Potion", false), 
		LEGENDS_QUEST(28198, 7366, "Legends Quest", false), 
		LOST_CITY(28199, 7367, "Lost City", false), 
		LOST_TRIBE(52077, 13389, "The Lost Tribe", false), 
		MAKING_HISTORY(60127, 15487, "Making History", false), 
		MONKEY_MADNESS(43124, 11132, "Monkey Madness", false), 
		MERLINS_CRYSTAL(28200, 7368, "Merlins Crystal", false), 
		MONKS_FRIEND(28201, 7369, "Monks Friend", false), 
		MOUNTAIN_DAUGHTER(48101, 12389, "Mountain Daughter", false), 
		MOURNINGS_END_1(54150, 13974, "Mourning's Ends Part 1", false), 
		MOURNINGS_END_2(23139, 6027, "Mourning's Ends Part 2", false), 
		MURDER_MYSTERY(28202, 7370, "Murder Mystery", false), 
		NATURE_SPIRIT(31201, 8137, "Nature Spirit", false), 
		OBSERVATORY(28203, 7371, "Observatory Quest", false), 
		ONE_SMALL_FAVOUR(48057, 12345, "One Small Favour", false), 
		PLAGUE_CITY(28204, 7372, "Plague City", false), 
		PRIEST_IN_PERIL(31179, 8115, "Priest in Peril", false), 
		RAG_AND_BONE_MAN(72252, 18684, "Rag and Bone Man", false), 
		RAT_CATCHERS(60139, 15499, "Rat Catchers", false), 
		RECIPE(71130, 18306, "Recipe for Disaster", false), 
		RECRUITMENT_DRIVE(2156, 668, "Recruitment Drive", false), 
		REGICIDE(33128, 8576, "Regicide", false),
		ROVING_ELVES(47017, 12139, "Roving Elves", false), 
		RUM_DEAL(58064, 14912, "Rum Deal", false), 
		SCORPION_CATCHER(28205, 7373, "Scorpion Catcher", false), 
		SEA_SLUG(28206, 7374, "Sea Slug Quest", false), 
		SHADES_OF_MORTON(35009, 8969, "Shades of Mort'ton", false), 
		SHADOW_OF_THE_STORM(59248, 15352, "Shadow of the Storm", false), 
		SHEEP_HERDER(28207, 7375, "Sheep Herder", false), 
		SHILO_VILLAGE(28208, 7376, "Shilo Village", false), 
		SOULS_BANE(28250, 15098, "A Soul's Bane", false),
		SPIRITS_OF_THE_ELID(60232, 15592, "Spirits of The Elid", false), 
		SWAN_SONG(249, 249, "Swan Song", false), 
		TAI_BWO(6204, 1740, "Tai Bwo Wannai Trio", false), 
		TWO_CATS(59131, 15235, "A Tail of Two Cats", false), 
		TEARS_OF_GUTHIX(12206, 3278, "Tears of Guthix", false), 
		TEMPLE_OF_IKOV(28210, 7378, "Temple of Ikov", false), 
		THRONE_OF_MISCELLANIA(25118, 6518, "Throne of Miscellania", false), 
		TOURIST_TRAP(28211, 7379, "The Tourist Trap", false), 
		TREE_GNOME_VILLAGE(28212, 7380, "Tree Gnome Village", false), 
		TRIBAL_TOTEM(28213, 7381, "Tribal Totem", false), 
		TROLL_ROMANCE(46082, 11858, "Troll Romance", false), 
		TROLL_STRONGHOLD(191, 191, "Troll Stronghold", false), 
		UNDERGROUND_PASS(38199, 9927, "Underground Pass", false), 
		WANTED(23136, 6024, "Wanted", false), 
		WATCHTOWER(28181, 7349, "Watch Tower", false), 
		WATERFALL(28182, 7350, "Waterfall Quest", false),
		WITCH(28183, 7351, "Witch's House", false), 
		ZOGRE(52044, 13356, "Zogre Flesh Eaters", false);

		private final int button, string;
		private final String name;
		private final boolean questStatus;

		private Quests(final int button, final int string, final String name, final boolean questStatus) {
			this.button = button;
			this.name = name;
			this.string = string;
			this.questStatus = questStatus;
		}
		
		public int getStringId() {
			return string;
		}
		
		public boolean questStatus() {
			return questStatus;
		}

		public int getButton() {
			return button;
		}

		public String getName() {
			return name;
		}

		public static Quests forButton(int button) {
			for (Quests q : Quests.values()) {
				if (q.getButton() == button) {
					return q;
				}
			}
			return null;
		}
	}

	public static void questButtons(Client client, int buttonId) {
		switch (buttonId) {

		case 28165:
			client.getCooksAssistant().showInformation();
			break;
		case 28167:
			client.getRuneMysteries().showInformation();
			break;
		case 28168:
			client.getDoricsQuest().showInformation();
			break;
		case 28169:
			client.getRestlessGhost().showInformation();
			break;
		case 28172:
			client.getImpCatcher().showInformation();
			break;
		case 28173:
			client.getPiratesTreasure().showInformation();
			break;
		case 28175:
			client.getRomeoJuliet().showInformation();
			break;
		case 28176:
			client.getSheepShearer().showInformation();
			break;
		case 28177:
			ShieldArrav.showInformation(client);
			break;
		case 28178:
			KnightsSword.showInformation(client);
		break;
		case 28179:
			client.getVampyreSlayer().showInformation();
			break;
		case 28180:
			client.getWitchesPotion().showInformation();
			break;
		case 28192:
			client.getGertrudesCat().showInformation();
			break;
		case 28164:
			client.getBlackKnightsFortress().showInformation();
			break;

		default:
			if (Quests.forButton(buttonId) != null) {
				client.getActionSender().sendMessage("The quest " + Quests.forButton(buttonId).getName() + " is currently disabled.");
			}
			break;
		}
	}
}
