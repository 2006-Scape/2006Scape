package redone.game.items.impl;

public class Fillables {

	public static boolean canFill(int id, int oid) {
		return counterpart(id) != -1 && !getObjectName(oid).equals("Error");
	}

	public static String fillMessage(int id, int oid) {
		return "You fill the " + getItemName(id) + " from the "
				+ getObjectName(oid) + ".";
	}

	public static int counterpart(int id) {
		switch (id) {
		case 1925: // bucket
			return 1929;
		case 1935: // jug
			return 1937;
		case 229: // vial
			return 227;
		case 1923: // bowl
			return 1921;
		case 1980: // cup
			return 4458;
		case 5331: // watering can
		case 5333:
		case 5334:
		case 5335:
		case 5336:
		case 5337:
		case 5338:
		case 5339:
			return 5340;
		case 1831: // waterskin
		case 1825:
		case 1827:
		case 1829:
			return 1823;
		case 6667:
			return 6668;
		}
		return -1;
	}

	public static String getItemName(int id) {
		switch (id) {
		case 1925:
			return "bucket";
		case 1935:
			return "jug";
		case 229:
			return "vial";
		case 1923:
			return "bowl";
		case 1980:
			return "cup";
		case 5331: // watering can
		case 5333:
		case 5334:
		case 5335:
		case 5336:
		case 5337:
		case 5338:
		case 5339:
			return "watering can";
		case 1831:
		case 1825:
		case 1827:
		case 1829:
			return "waterskin";
		case 6667:
			return "fishbowl";
		}
		return "There was a problem with your current action, please report this to Mod Andrew.";
	}

	public static String getObjectName(int id) {
		switch (id) {
		case 873:
		case 874:
		case 4063:
		case 6151:
		case 14917:
			return "sink";
		case 14918:
			return "washbin";
		case 884:
		case 878:
		case 3359:
		case 3485:
		case 4004:
		case 4005:
		case 5086:
		case 6097:
			return "well";
		case 2654:
			return "Sinclair Family fountain";
		case 12809:
			return "Fairy fountain";
		case 11661:
		case 3460:
		case 6827:
			return "waterpump";
		case 879:
		case 11759:
		case 153:
		case 880:
		case 6232:
		case 2864:
			return "fountain";
		}
		return "Error";
	}
}
