package redone.game.content.quests;

import redone.game.content.quests.impl.KnightsSword;
import redone.game.players.Client;

/**
 * Quest Assistant
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class QuestAssistant {

	public static final int MAXIMUM_QUESTPOINTS = 19;

	public static void sendStages(Client client) {
		client.getPlayerAssistant().sendFrame126("QP: " + client.questPoints + " ", 3985);
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
	}

	public enum Quests {
		BLACK_KNIGHT(28164, "Black Knights' Fortress"), 
		COOKS_ASSISTANT(28165, "Cook's Assistant"), 
		DEMON_SLAYER(28166, "Demon Slayer"), 
		DORICS_QUEST(28168, "Doric's Quest"), 
		DRAGON_SLAYER(28215, "Dragon Slayer"),
		ERNEST(28171, "Ernest the Chicken"), 
		GOBLIN(28170, "Goblin Diplomacy"), 
		IMP_CATCHER(28172, "Imp Catcher"), 
		KNIGHTS_SWORD(28178, "The Knight's Sword"), 
		PIRATES_TREASURE(28173, "Pirates Treasure"), 
		PRINCE_RESCUE(28174, "Prince Ali Rescue"), 
		RESTLESS_GHOST(28169, "Restless Ghost"), 
		ROMEO_JULIET(28175, "Romeo Juliet"), 
		RUNE_MYSTERIES(28167, "Rune Mysteries"), 
		SHEEP_SHEARER(28176, "Sheep Shearer"), 
		SHIELD_OF_ARRAV(28177, "Shield of Arrav"), 
		VAMPYRE_SLAYER(28179, "Vampyre Slayer"), 
		WITCHS_POTION(28180, "Witchs Potion"),
		BETWEEN_A_ROCK(49228, "Between A Rock"), 
		CHOMPY(2161, "Big Chompy Bird Hunting"), 
		BIOHAZARD(28124, "Biohazard"), 
		CABIN(68102, "Cabin Fever"), 
		CLOCK(28185, "Clock Tower"), 
		DEATH(32246, "Death Plateau"), 
		CREATURE(47097, "Creature of Fenkenstrain"), 
		DESERT_TREASURE(50052, "Desert Treasure"), 
		DRUDIC_RITUAL(28187, "Drudic Ritual"), 
		DWARF_CANNON(28188, "Dwarf Cannon"), 
		EADGARS_RUSE(33231, "Eadgars Ruse"), 
		DEVIOUS(61225, "Devious Minds"), 
		DIGSITE(28186, "Digsite Quest"), 
		ELEMENTAL(29035, "Elemental Workshop"), 
		ENAKHRA(63021, "Enakhra's Lamet"), 
		FAIRY1(27075, "A Fairy Tale Pt. 1"), 
		FAMILYCREST(28189, "Family Crest"), 
		FEUD(50036, "The Feud"), 
		FIGHT_ARENA(28190, "Fight Arena"), 
		FISHING_CONTEST(28191, "Fishing Contest"), 
		FORGETTABLE_TABLE(50089, "Forgettable Tale..."), 
		FREMMY_TRIALS(39131, "The Fremennik Trials"), 
		GARDEN(57012, "Garden of Tranquillity"), 
		GHOSTS(47250, "Ghosts Ahoy"), 
		GIANT_DWARF(53009, "The Giant Dwarf"), 
		GOLEM(50039, "The Golem"), 
		GRAND_TREE(28193, "The Grand Tree"), 
		HAND_IN_THE_SAND(63000, "The Hand in the Sand"), 
		HAUNTED_MINE(46081, "Haunted Mine"), 
		HAZEEL(28194, "Hazeel Cult"), 
		HEROES(28195, "Heroes Quest"), 
		HOLY(28196, "Holy Grail"), 
		HORROR(39151, "Horror from the Deep"), 
		ITCHLARIN(17156, "Itchlarin's Little Helper"), 
		AID_OF_MYREQUE(72085, "In Aid of the Myreque"), 
		SEARCH_OF_MYREQUE(46131, "In Search of the Myreque"), 
		JUNGLE_POTION(28197, "Jungle Potion"), 
		LEGENDS_QUEST(28198, "Legends Quest"), 
		LOST_CITY(28199, "Lost City"), 
		LOST_TRIBE(52077, "The Lost Tribe"), 
		MAKING_HISTORY(60127, "Making History"), 
		MONKEY_MADNESS(43124, "Monkey Madness"), 
		MERLINS_CRYSTAL(28200, "Merlins Crystal"), 
		MONKS_FRIEND(28201, "Monks Friend"), 
		MOUNTAIN_DAUGHTER(48101, "Mountain Daughter"), 
		MOURNINGS_END_1(54150, "Mourning's Ends Part 1"), 
		MOURNINGS_END_2(23139, "Mourning's Ends Part 2"), 
		MURDER_MYSTERY(28202, "Murder Mystery"), 
		NATURE_SPIRIT(31201, "Nature Spirit"), 
		OBSERVATORY(28203, "Observatory Quest"), 
		ONE_SMALL_FAVOUR(48057, "One Small Favour"), 
		PLAGUE_CITY(28204, "Plague City"), 
		PRIEST_IN_PERIL(31179, "Priest in Peril"), 
		RAG_AND_BONE_MAN(72252, "Rag and Bone Man"), 
		RAT_CATCHERS(60139, "Rat Catchers"), 
		RECIPE(71130, "Recipe for Disaster"), 
		RECRUITMENT_DRIVE(2156, "Recruitment Drive"), 
		REGICIDE(33128, "Regicide"),
		ROVING_ELVES(47017, "Roving Elves"), 
		RUM_DEAL(58064, "Rum Deal"), 
		SCORPION_CATCHER(28205, "Scorpion Catcher"), 
		SEA_SLUG(28206, "Sea Slug Quest"), 
		SHADES_OF_MORTON(35009, "Shades of Mort'ton"), 
		SHADOW_OF_THE_STORM(59248, "Shadow of the Storm"), 
		SHEEP_HERDER(28207, "Sheep Herder"), 
		SHILO_VILLAGE(28208, "Shilo Village"), 
		SOULS_BANE(28250, "A Soul's Bane"),
		SPIRITS_OF_THE_ELID(60232, "Spirits of The Elid"), 
		SWAN_SONG(249, "Swan Song"), 
		TAI_BWO(6204, "Tai Bwo Wannai Trio"), 
		TWO_CATS(59131, "A Tail of Two Cats"), 
		TEARS_OF_GUTHIX(12206, "Tears of Guthix"), 
		TEMPLE_OF_IKOV(28210, "Temple of Ikov"), 
		THRONE_OF_MISCELLANIA(25118, "Throne of Miscellania"), 
		TOURIST_TRAP(28211, "The Tourist Trap"), 
		TREE_GNOME_VILLAGE(28212, "Tree Gnome Village"), 
		TRIBAL_TOTEM(28213, "Tribal Totem"), 
		TROLL_ROMANCE(46082, "Troll Romance"), 
		TROLL_STRONGHOLD(191, "Troll Stronghold"), 
		UNDERGROUND_PASS(38199, "Underground Pass"), 
		WANTED(23136, "Wanted"), 
		WATCHTOWER(28181, "Watch Tower"), 
		WATERFALL(28182, "Waterfall Quest"),
		WITCH(28183, "Witch's House"), 
		ZOGRE(52044, "Zogre Flesh Eaters");

		private final int button;
		private final String name;

		private Quests(final int button, final String name) {
			this.button = button;
			this.name = name;
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
		default:
			if (Quests.forButton(buttonId) != null) {
				client.getActionSender().sendMessage("The quest " + Quests.forButton(buttonId).getName() + " is currently disabled.");
			}
			break;
		}
	}
}
