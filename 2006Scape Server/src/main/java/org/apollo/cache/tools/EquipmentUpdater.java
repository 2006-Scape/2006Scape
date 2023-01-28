package org.apollo.cache.tools;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.cache.def.ItemDefinition;
import org.apollo.util.tools.EquipmentConstants;

import com.google.common.base.Preconditions;

/**
 * A tool for updating the equipment data.
 *
 * @author Graham
 * @author Palidino76
 */
public final class EquipmentUpdater {

	/**
	 * The entry point of the application.
	 *
	 * @param args The command line arguments.
	 * @throws Exception If an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		Preconditions.checkArgument(args.length == 1, "Usage:\njava -cp ... org.apollo.tools.EquipmentUpdater [release].");
		String release = args[0];

		try (DataOutputStream os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data/equipment-" + release + ".dat")));
			IndexedFileSystem fs = new IndexedFileSystem(Paths.get("data/fs/", release), true)) {
			ItemDefinitionDecoder decoder = new ItemDefinitionDecoder(fs);
			decoder.run();

			int count = ItemDefinition.count();
			os.writeShort(count);

			for (int id = 0; id < count; id++) {
				ItemDefinition definition = ItemDefinition.lookup(id);
				int type = getWeaponType(definition);
				os.writeByte(type);

				if (type != -1) {
					os.writeBoolean(isTwoHanded(definition));
					os.writeBoolean(isFullBody(definition));
					os.writeBoolean(isFullHat(definition));
					os.writeBoolean(isFullMask(definition));
					os.writeByte(getAttackRequirement(definition));
					os.writeByte(getStrengthRequirement(definition));
					os.writeByte(getDefenceRequirement(definition));
					os.writeByte(getRangedRequirement(definition));
					os.writeByte(getPrayerRequirement(definition));
					os.writeByte(getMagicRequirement(definition));
				}
			}
		}
	}

	/**
	 * Gets the attack requirement.
	 *
	 * @param definition The item definition.
	 * @return The required level.
	 */
	private static int getAttackRequirement(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		if (name.equals("Black sword")) {
			return 10;
		} else if (name.equals("Black dagger")) {
			return 10;
		} else if (name.equals("Black spear")) {
			return 10;
		} else if (name.equals("Black longsword")) {
			return 10;
		} else if (name.equals("Black scimitar")) {
			return 10;
		} else if (name.equals("Black axe")) {
			return 10;
		} else if (name.equals("Black battleaxe")) {
			return 10;
		} else if (name.equals("Black mace")) {
			return 10;
		} else if (name.equals("Black halberd")) {
			return 10;
		} else if (name.equals("Mithril sword")) {
			return 20;
		} else if (name.equals("Mithril dagger")) {
			return 20;
		} else if (name.equals("Mithril spear")) {
			return 20;
		} else if (name.equals("Mihril longsword")) {
			return 20;
		} else if (name.equals("Mithril scimitar")) {
			return 20;
		} else if (name.equals("Mithril axe")) {
			return 20;
		} else if (name.equals("Mithril battleaxe")) {
			return 20;
		} else if (name.equals("Mithril mace")) {
			return 20;
		} else if (name.equals("Mithril halberd")) {
			return 20;
		} else if (name.equals("Adamant sword")) {
			return 30;
		} else if (name.equals("Adamant dagger")) {
			return 30;
		} else if (name.equals("Adamant spear")) {
			return 30;
		} else if (name.equals("Adamant longsword")) {
			return 30;
		} else if (name.equals("Adamant scimitar")) {
			return 30;
		} else if (name.equals("Adamant axe")) {
			return 30;
		} else if (name.equals("Adamant battleaxe")) {
			return 30;
		} else if (name.equals("Adamant mace")) {
			return 30;
		} else if (name.equals("Adamant halberd")) {
			return 30;
		} else if (name.equals("Rune sword")) {
			return 40;
		} else if (name.equals("Rune dagger")) {
			return 40;
		} else if (name.equals("Rune spear")) {
			return 40;
		} else if (name.equals("Rune longsword")) {
			return 40;
		} else if (name.equals("Rune scimitar")) {
			return 40;
		} else if (name.equals("Rune axe")) {
			return 40;
		} else if (name.equals("Rune battleaxe")) {
			return 40;
		} else if (name.equals("Rune mace")) {
			return 40;
		} else if (name.equals("Rune halberd")) {
			return 40;
		} else if (name.equals("Dragon sword")) {
			return 60;
		} else if (name.equals("Dragon dagger(s)")) {
			return 60;
		} else if (name.equals("Dragon dagger")) {
			return 60;
		} else if (name.startsWith("Dragon spear")) {
			return 60;
		} else if (name.equals("Dragon longsword")) {
			return 60;
		} else if (name.equals("Dragon scimitar")) {
			return 60;
		} else if (name.equals("Dragon axe")) {
			return 60;
		} else if (name.equals("Dragon battleaxe")) {
			return 60;
		} else if (name.equals("Dragon mace")) {
			return 60;
		} else if (name.equals("Dragon halberd")) {
			return 60;
		} else if (name.equals("Abyssal whip")) {
			return 70;
		} else if (name.equals("Veracs flail")) {
			return 70;
		} else if (name.equals("Torags hammers")) {
			return 70;
		} else if (name.equals("Dharoks greataxe")) {
			return 70;
		} else if (name.equals("Guthans warspear")) {
			return 70;
		} else if (name.equals("Ahrims staff")) {
			return 70;
		} else if (name.equals("Granite maul")) {
			return 50;
		} else if (name.equals("Toktz-xil-ak")) {
			return 60;
		} else if (name.equals("Tzhaar-ket-em")) {
			return 60;
		} else if (name.equals("Toktz-xil-ek")) {
			return 60;
		} else if (name.equals("Granite legs")) {
			return 99;
		} else if (name.equals("Mud staff")) {
			return 30;
		} else if (name.equals("Armadyl godsword")) {
			return 75;
		} else if (name.equals("Bandos godsword")) {
			return 75;
		} else if (name.equals("Saradomin godsword")) {
			return 75;
		} else if (name.equals("Zamorak godsword")) {
			return 75;
		} else if (name.equals("Lava battlestaff")) {
			return 30;
		} else if (name.equals("Toktz-mej-tal")) {
			return 60;
		} else if (name.equals("Ancient staff")) {
			return 50;
		}

		return 1;
	}

	/**
	 * Gets the defence requirement.
	 *
	 * @param definition The item definition.
	 * @return The required level.
	 */
	private static int getDefenceRequirement(ItemDefinition definition) {
		int id = definition.getId();
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		if (name.equals("Rune boots")) {
			return 40;
		} else if (id == 2499) {
			return 40;
		} else if (id == 4123) {
			return 5;
		} else if (id == 4125) {
			return 10;
		} else if (id == 4127) {
			return 20;
		} else if (id == 4129) {
			return 30;
		} else if (id == 7990) {
			return 60;
		} else if (id == 2501) {
			return 40;
		} else if (id == 1131) {
			return 10;
		} else if (id == 2503) {
			return 40;
		} else if (id == 1135) {
			return 40;
		} else if (id == 7462) {
			return 42;
		} else if (id == 7461) {
			return 42;
		} else if (id == 7460) {
			return 42;
		} else if (id == 7459) {
			return 20;
		} else if (id == 7458) {
			return 1;
		} else if (id == 7457) {
			return 1;
		} else if (id == 7456) {
			return 1;
		} else if (id == 2661) {
			return 40;
		} else if (id == 2667) {
			return 40;
		} else if (id == 3479) {
			return 40;
		} else if (name.equals("White med helm")) {
			return 10;
		} else if (name.equals("White chainbody")) {
			return 10;
		} else if (name.startsWith("White full helm")) {
			return 10;
		} else if (name.startsWith("White platebody")) {
			return 10;
		} else if (name.startsWith("White plateskirt")) {
			return 10;
		} else if (name.startsWith("White platelegs")) {
			return 10;
		} else if (name.startsWith("White kiteshield")) {
			return 10;
		} else if (name.startsWith("White sq shield")) {
			return 10;
		} else if (name.startsWith("Studded chaps")) {
			return 1;
		} else if (name.startsWith("Studded")) {
			return 20;
		} else if (name.startsWith("Black kiteshield(h)")) {
			return 10;
		} else if (name.startsWith("Rune kiteshield(h)")) {
			return 40;
		} else if (name.equals("Black med helm")) {
			return 10;
		} else if (name.equals("Black chainbody")) {
			return 10;
		} else if (name.startsWith("Black full helm")) {
			return 10;
		} else if (name.startsWith("Black platebody")) {
			return 10;
		} else if (name.startsWith("Black plateskirt")) {
			return 10;
		} else if (name.startsWith("Black platelegs")) {
			return 10;
		} else if (name.startsWith("Black kiteshield")) {
			return 10;
		} else if (name.startsWith("Black sq shield")) {
			return 10;
		} else if (name.equals("Mithril med helm")) {
			return 20;
		} else if (name.equals("Mithril chainbody")) {
			return 20;
		} else if (name.startsWith("Mithril full helm")) {
			return 20;
		} else if (name.startsWith("Mithril platebody")) {
			return 20;
		} else if (name.startsWith("Mithril plateskirt")) {
			return 20;
		} else if (name.startsWith("Mithril platelegs")) {
			return 20;
		} else if (name.startsWith("Mithril kiteshield")) {
			return 20;
		} else if (name.startsWith("Mithril sq shield")) {
			return 20;
		} else if (name.equals("Adamant med helm")) {
			return 30;
		} else if (name.equals("Adamant chainbody")) {
			return 30;
		} else if (name.startsWith("Adamant full helm")) {
			return 30;
		} else if (name.startsWith("Adamant platebody")) {
			return 30;
		} else if (name.startsWith("Adamant plateskirt")) {
			return 30;
		} else if (name.startsWith("Adamant platelegs")) {
			return 30;
		} else if (name.startsWith("Adamant kiteshield")) {
			return 30;
		} else if (name.startsWith("Adamant sq shield")) {
			return 30;
		} else if (name.startsWith("Adam full helm")) {
			return 30;
		} else if (name.startsWith("Adam platebody")) {
			return 30;
		} else if (name.startsWith("Adam plateskirt")) {
			return 30;
		} else if (name.startsWith("Adam platelegs")) {
			return 30;
		} else if (name.startsWith("Adam kiteshield")) {
			return 30;
		} else if (name.startsWith("Adam kiteshield(h)")) {
			return 30;
		} else if (name.startsWith("D-hide body(g)")) {
			return 40;
		} else if (name.startsWith("D-hide body(t)")) {
			return 40;
		} else if (name.equals("Dragon sq shield")) {
			return 60;
		} else if (name.equals("Dragon med helm")) {
			return 60;
		} else if (name.equals("Dragon chainbody")) {
			return 60;
		} else if (name.equals("Dragon plateskirt")) {
			return 60;
		} else if (name.equals("Dragon platelegs")) {
			return 60;
		} else if (name.equals("Dragon sq shield")) {
			return 60;
		} else if (name.equals("Rune med helm")) {
			return 40;
		} else if (name.equals("Rune chainbody")) {
			return 40;
		} else if (name.startsWith("Rune full helm")) {
			return 40;
		} else if (name.startsWith("Rune platebody")) {
			return 40;
		} else if (name.startsWith("Rune plateskirt")) {
			return 40;
		} else if (name.startsWith("Rune platelegs")) {
			return 40;
		} else if (name.startsWith("Rune kiteshield")) {
			return 40;
		} else if (name.startsWith("Zamorak full helm")) {
			return 40;
		} else if (name.startsWith("Zamorak platebody")) {
			return 40;
		} else if (name.startsWith("Zamorak plateskirt")) {
			return 40;
		} else if (name.startsWith("Zamorak platelegs")) {
			return 40;
		} else if (name.startsWith("Zamorak kiteshield")) {
			return 40;
		} else if (name.startsWith("Guthix full helm")) {
			return 40;
		} else if (name.startsWith("Guthix platebody")) {
			return 40;
		} else if (name.startsWith("Guthix plateskirt")) {
			return 40;
		} else if (name.startsWith("Guthix platelegs")) {
			return 40;
		} else if (name.startsWith("Guthix kiteshield")) {
			return 40;
		} else if (name.startsWith("Saradomin full")) {
			return 40;
		} else if (name.startsWith("Saradomrangedin plate")) {
			return 40;
		} else if (name.startsWith("Saradomin plateskirt")) {
			return 40;
		} else if (name.startsWith("Saradomin legs")) {
			return 40;
		} else if (name.startsWith("Zamorak kiteshield")) {
			return 40;
		} else if (name.startsWith("Rune sq shield")) {
			return 40;
		} else if (name.equals("Gilded full helm")) {
			return 40;
		} else if (name.equals("Gilded platebody")) {
			return 40;
		} else if (name.equals("Gilded plateskirt")) {
			return 40;
		} else if (name.equals("Gilded platelegs")) {
			return 40;
		} else if (name.equals("Gilded kiteshield")) {
			return 40;
		} else if (name.equals("Fighter torso")) {
			return 40;
		} else if (name.equals("Granite legs")) {
			return 99;
		} else if (name.equals("Toktz-ket-xil")) {
			return 60;
		} else if (name.equals("Dharoks helm")) {
			return 70;
		} else if (name.equals("Dharoks platebody")) {
			return 70;
		} else if (name.equals("Dharoks platelegs")) {
			return 70;
		} else if (name.equals("Guthans helm")) {
			return 70;
		} else if (name.equals("Guthans platebody")) {
			return 70;
		} else if (name.equals("Guthans chainskirt")) {
			return 70;
		} else if (name.equals("Torags helm")) {
			return 70;
		} else if (name.equals("Torags platebody")) {
			return 70;
		} else if (name.equals("Torags platelegs")) {
			return 70;
		} else if (name.equals("Veracs helm")) {
			return 70;
		} else if (name.equals("Veracs brassard")) {
			return 70;
		} else if (name.equals("Veracs plateskirt")) {
			return 70;
		} else if (name.equals("Ahrims hood")) {
			return 70;
		} else if (name.equals("Ahrims robetop")) {
			return 70;
		} else if (name.equals("Ahrims robeskirt")) {
			return 70;
		} else if (name.equals("Karils coif")) {
			return 70;
		} else if (name.equals("Karils leathertop")) {
			return 70;
		} else if (name.equals("Karils leatherskirt")) {
			return 70;
		} else if (name.equals("Granite shield")) {
			return 50;
		} else if (name.equals("New crystal shield")) {
			return 70;
		} else if (name.equals("Archer helm")) {
			return 45;
		} else if (name.equals("Berserker helm")) {
			return 45;
		} else if (name.equals("Warrior helm")) {
			return 45;
		} else if (name.equals("Farseer helm")) {
			return 45;
		} else if (name.equals("Initiate helm")) {
			return 20;
		} else if (name.equals("Initiate platemail")) {
			return 20;
		} else if (name.equals("Initiate platelegs")) {
			return 20;
		} else if (name.equals("Dragonhide body")) {
			return 40;
		} else if (name.equals("Mystic hat")) {
			return 20;
		} else if (name.equals("Mystic robe top")) {
			return 20;
		} else if (name.equals("Mystic robe bottom")) {
			return 20;
		} else if (name.equals("Mystic gloves")) {
			return 20;
		} else if (name.equals("Mystic boots")) {
			return 20;
		} else if (name.equals("Enchanted hat")) {
			return 20;
		} else if (name.equals("Enchanted top")) {
			return 20;
		} else if (name.equals("Enchanted robe")) {
			return 20;
		} else if (name.equals("Splitbark helm")) {
			return 40;
		} else if (name.equals("Splitbark body")) {
			return 40;
		} else if (name.equals("Splitbark gauntlets")) {
			return 40;
		} else if (name.equals("Splitbark legs")) {
			return 40;
		} else if (name.equals("Splitbark greaves")) {
			return 40;
		} else if (name.equals("Infinity gloves")) {
			return 25;
		} else if (name.equals("Infinity hat")) {
			return 25;
		} else if (name.equals("Infinity top")) {
			return 25;
		} else if (name.equals("Infinity bottoms")) {
			return 25;
		} else if (name.equals("Infinity boots")) {
			return 25;
		}

		return 1;
	}

	/**
	 * Gets the magic requirement.
	 *
	 * @param definition The item definition.
	 * @return The required level.
	 */
	private static int getMagicRequirement(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		if (name.equals("Mystic hat")) {
			return 40;
		} else if (name.equals("Mystic robe top")) {
			return 40;
		} else if (name.equals("Mystic robe bottom")) {
			return 40;
		} else if (name.equals("Mystic gloves")) {
			return 40;
		} else if (name.equals("Mystic boots")) {
			return 40;
		} else if (name.equals("Slayer's staff")) {
			return 50;
		} else if (name.equals("Enchanted hat")) {
			return 40;
		} else if (name.equals("Enchanted top")) {
			return 40;
		} else if (name.equals("Enchanted robe")) {
			return 40;
		} else if (name.equals("Splitbark helm")) {
			return 40;
		} else if (name.equals("Splitbark body")) {
			return 40;
		} else if (name.equals("Splitbark gauntlets")) {
			return 40;
		} else if (name.equals("Splitbark legs")) {
			return 40;
		} else if (name.equals("Splitbark greaves")) {
			return 40;
		} else if (name.equals("Infinity gloves")) {
			return 50;
		} else if (name.equals("Infinity hat")) {
			return 50;
		} else if (name.equals("Infinity top")) {
			return 50;
		} else if (name.equals("Infinity bottoms")) {
			return 50;
		} else if (name.equals("Infinity boots")) {
			return 50;
		} else if (name.equals("Ahrims hood")) {
			return 70;
		} else if (name.equals("Ahrims robetop")) {
			return 70;
		} else if (name.equals("Ahrims robeskirt")) {
			return 70;
		} else if (name.equals("Ahrims staff")) {
			return 70;
		} else if (name.equals("Saradomin cape")) {
			return 60;
		} else if (name.equals("Saradomin staff")) {
			return 60;
		} else if (name.equals("Zamorak cape")) {
			return 60;
		} else if (name.equals("Zamorak staff")) {
			return 60;
		} else if (name.equals("Guthix cape")) {
			return 60;
		} else if (name.equals("Guthix staff")) {
			return 60;
		} else if (name.equals("mud staff")) {
			return 30;
		} else if (name.equals("Fire battlestaff")) {
			return 30;
		} else if (name.equals("Toktz-mej-tal")) {
			return 60;
		}
		return 1;
	}

	/**
	 * Gets the prayer requirement.
	 *
	 * @param definition the item.
	 * @return The required level.
	 */
	private static int getPrayerRequirement(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}
		if (name.contains("Initiate")) {
			return 10;
		}
		if (name.contains("Proselyte")) {
			return 20;
		}
		return 1;
	}

	/**
	 * Gets the ranged requirement.
	 *
	 * @param definition The item.
	 * @return The required level.
	 */
	private static int getRangedRequirement(ItemDefinition definition) {
		int id = definition.getId();
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		if (id == 2499) {
			return 50;
		} else if (id == 1135) {
			return 40;
		} else if (id == 1099) {
			return 40;
		} else if (id == 1065) {
			return 40;
		} else if (id == 2501) {
			return 60;
		} else if (id == 2503) {
			return 70;
		} else if (id == 2487) {
			return 50;
		} else if (id == 2489) {
			return 60;
		} else if (id == 2495) {
			return 60;
		} else if (id == 2491) {
			return 70;
		} else if (id == 2493) {
			return 50;
		} else if (id == 2505) {
			return 60;
		} else if (id == 2507) {
			return 70;
		} else if (id == 859) {
			return 40;
		} else if (id == 861) {
			return 40;
		} else if (id == 7370) {
			return 40;
		} else if (id == 7372) {
			return 40;
		} else if (id == 7378) {
			return 40;
		} else if (id == 7380) {
			return 40;
		} else if (id == 7374) {
			return 50;
		} else if (id == 7376) {
			return 50;
		} else if (id == 7382) {
			return 50;
		} else if (id == 7384) {
			return 50;
		} else if (name.equals("Coif")) {
			return 20;
		} else if (name.startsWith("Studded chaps")) {
			return 20;
		} else if (name.startsWith("Studded")) {
			return 20;
		} else if (name.equals("Karils coif")) {
			return 70;
		} else if (name.equals("Karils leathertop")) {
			return 70;
		} else if (name.equals("Karils leatherskirt")) {
			return 70;
		} else if (name.equals("Robin hood hat")) {
			return 40;
		} else if (name.equals("Ranger boots")) {
			return 40;
		} else if (name.equals("Crystal bow full")) {
			return 70;
		} else if (name.equals("New crystal bow")) {
			return 70;
		} else if (name.equals("Karils crossbow")) {
			return 70;
		} else if (id == 2497) {
			return 70;
		} else if (name.equals("Rune thrownaxe")) {
			return 40;
		} else if (name.equals("Rune dart")) {
			return 40;
		} else if (name.equals("Rune javelin")) {
			return 40;
		} else if (name.equals("Rune knife")) {
			return 40;
		} else if (name.equals("Adamant thrownaxe")) {
			return 30;
		} else if (name.equals("Adamant dart")) {
			return 30;
		} else if (name.equals("Adamant javelin")) {
			return 30;
		} else if (name.equals("Adamant knife")) {
			return 30;
		} else if (name.equals("Toktz-xil-ul")) {
			return 60;
		} else if (name.equals("Seercull")) {
			return 50;
		} else if (name.equals("Bolt rack")) {
			return 70;
		} else if (name.equals("Rune arrow")) {
			return 40;
		} else if (name.equals("Adamant arrow")) {
			return 30;
		} else if (name.equals("Mithril arrow")) {
			return 1;
		}

		return 1;
	}

	/**
	 * Gets the strength requirement.
	 *
	 * @param def The item.
	 * @return The required level.
	 */
	private static int getStrengthRequirement(ItemDefinition def) {
		String name = def.getName();
		if (name == null) {
			name = "null";
		}

		if (name.equals("Torags hammers")) {
			return 70;
		} else if (name.equals("Dharoks greataxe")) {
			return 70;
		} else if (name.equals("Granite maul")) {
			return 50;
		} else if (name.equals("Granite legs")) {
			return 99;
		} else if (name.equals("Tzhaar-ket-om")) {
			return 60;
		} else if (name.equals("Granite shield")) {
			return 50;
		}

		return 1;
	}

	/**
	 * Gets the weapon type.
	 *
	 * @param definition The item.
	 * @return The weapon type, or {@code -1} if it is not a weapon.
	 */
	private static int getWeaponType(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}
		for (String element : EquipmentConstants.CAPES) {
			if (name.contains(element)) {
				return 1;
			}
		}
		for (String element : EquipmentConstants.HATS) {
			if (name.contains(element)) {
				return 0;
			}
		}
		for (String element : EquipmentConstants.BOOTS) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 10;
			}
		}
		for (String element : EquipmentConstants.GLOVES) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 9;
			}
		}
		for (String element : EquipmentConstants.SHIELDS) {
			if (name.contains(element)) {
				return 5;
			}
		}
		for (String element : EquipmentConstants.AMULETS) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 2;
			}
		}
		for (String element : EquipmentConstants.ARROWS) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 13;
			}
		}
		for (String element : EquipmentConstants.RINGS) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 12;
			}
		}
		for (String element : EquipmentConstants.BODY) {
			if (name.contains(element)) {
				return 4;
			}
		}
		for (String element : EquipmentConstants.LEGS) {
			if (name.contains(element)) {
				return 7;
			}
		}
		for (String element : EquipmentConstants.WEAPONS) {
			if (name.endsWith(element) || name.startsWith(element)) {
				return 3;
			}
		}
		return -1;
	}

	/**
	 * Checks if the item is a full body item.
	 *
	 * @param definition The item.
	 * @return {@code true} if so, {@code false} otherwise.
	 */
	private static boolean isFullBody(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		for (String element : EquipmentConstants.FULL_BODIES) {
			if (name.contains(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the item is a full hat item.
	 *
	 * @param definition The item.
	 * @return {@code true} if so, {@code false} otherwise.
	 */
	private static boolean isFullHat(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		for (String element : EquipmentConstants.FULL_HATS) {
			if (name.endsWith(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the item is a full mask item.
	 *
	 * @param definition The item.
	 * @return {@code true} if so, {@code false} otherwise.
	 */
	private static boolean isFullMask(ItemDefinition definition) {
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		for (String element : EquipmentConstants.FULL_MASKS) {
			if (name.endsWith(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the item is two handed.
	 *
	 * @param definition The item.
	 * @return {@code true} if so, {@code false} otherwise.
	 */
	private static boolean isTwoHanded(ItemDefinition definition) {
		int id = definition.getId();
		String name = definition.getName();
		if (name == null) {
			name = "null";
		}

		if (id == 4212) {
			return true;
		} else if (id == 4214) {
			return true;
		} else if (id == 6526) {
			return true;
		} else if (name.endsWith("2h sword")) {
			return true;
		} else if (name.endsWith("longbow")) {
			return true;
		} else if (name.equals("Seercull")) {
			return true;
		} else if (name.endsWith("shortbow")) {
			return true;
		} else if (name.endsWith("Longbow")) {
			return true;
		} else if (name.endsWith("Shortbow")) {
			return true;
		} else if (name.endsWith("bow full")) {
			return true;
		} else if (name.endsWith("halberd")) {
			return true;
		} else if (name.equals("Granite maul")) {
			return true;
		} else if (name.equals("Karils crossbow")) {
			return true;
		} else if (name.equals("Torags hammers")) {
			return true;
		} else if (name.equals("Veracs flail")) {
			return true;
		} else if (name.equals("Dharoks greataxe")) {
			return true;
		} else if (name.equals("Guthans warspear")) {
			return true;
		} else if (name.equals("Tzhaar-ket-om")) {
			return true;
		} else if (name.endsWith("godsword")) {
			return true;
		} else if (name.equals("Saradomin sword")) {
			return true;
		}

		return false;
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private EquipmentUpdater() {
	}

}