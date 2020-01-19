package com.rebotted.game.items;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

public class ItemDefinition {

	public static ItemDefinition getDefs()[] {
        return definitions;
    }

	public ItemDefinition() {
		name = null;
		itemDescription = null;
		shopValue = 0;
		lowAlch = 0;
		highAlch = 0;
		isStackable = false;
		isNoteable = false;
		weight = 0;
		bonuses = null;
		stand = -1;
		walk = -1;
		run = -1;
		turn90left = -1;
		turn90right = -1;
		turn180 = -1;
		attack = -1;
		block = -1;
		id = -1;
	}

	public ItemDefinition(String iname, String idescription, int ishopvalue, int ilowalch, int ihighalch, boolean iisstackable, boolean iisnoteable,
						  int iweight, double[] ibonuses, int istand, int iwalk, int irun, int iturn90left, int iturn90right, int iturn180,
						  int iattack, int iblock) {
		this.name = iname;
		this.itemDescription = idescription;
		this.shopValue = ishopvalue;
		this.lowAlch = ilowalch;
		this.highAlch = ihighalch;
		this.isStackable = iisstackable;
		this.isNoteable = iisnoteable;
		this.weight = iweight;
		this.bonuses = ibonuses;
		this.stand = istand;
		this.walk = iwalk;
		this.run = irun;
		this.turn90left = iturn90left;
		this.turn90right = iturn90right;
		this.turn180 = iturn180;
		this.attack = iattack;
		this.block = iblock;
	}

    private static int id;

    public static int getId() {
        return id;
    }


	/**
	 * Reads the definitions from the file.
	 */
	public static void read() {
		try {
			DataInputStream in = new DataInputStream(new FileInputStream("./data/data/itemdef.gsu"));
			total = in.readShort();
			System.out.println(total);
			if(definitions == null)
				definitions = new ItemDefinition[total];
			for(int j = 0; j < total; j++) {
				if(definitions[j] == null) {
					definitions[j] = new ItemDefinition();
				}
				definitions[j].getValues(in);
				System.out.println(definitions[j]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the stream values.
	 * @param in
	 */
	private void getValues(DataInputStream in) {
		try {
			do {
				int opcode = in.readByte();
				if(opcode == 0)
					return;
				if(opcode == 1) {
					name = in.readUTF();
				} else if(opcode == 2) {
					itemDescription = in.readUTF();
				} else if(opcode == 3) {
					shopValue = in.readInt();
				} else if(opcode == 4) {
					lowAlch = in.readInt();
				} else if(opcode == 5) {
					highAlch = in.readInt();
				} else if(opcode == 6) {
					isStackable = true;
				} else if(opcode == 7) {
					isNoteable = true;
				} else if(opcode == 8) {
					weight = in.readDouble();
				} else if(opcode == 9) {
					int length = in.readShort();
					bonuses = new double[length];
					for (int index = 0; index < length; index++) {
						bonuses[index] = in.readDouble();
					}
				} else if(opcode == 10) {
					stand = in.readShort();
				} else if(opcode == 11) {
					walk = in.readShort();
				} else if(opcode == 12) {
					run = in.readShort();
				} else if(opcode == 13) {
					turn90left = in.readShort();
				} else if(opcode == 14) {
					turn90right = in.readShort();
				} else if(opcode == 15) {
					turn180 = in.readShort();
				} else if(opcode == 16) {
					attack = in.readShort();
				} else if(opcode == 17) {
					block = in.readShort();
				} else {
					System.out.println("Unrecognized opcode: " + opcode);
				}
			} while(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The item definition cache.
	 */
	public static ItemDefinition definitions[];

	/**
	 * The total items read from the definitions.
	 */
	public static int total;

	/**
	 * Returns the total item number.
	 */
	public static int getTotal() {
		return total;
	}

	/**
	 * The item name.
	 */
	public String name;

	/**
	 * Returns the name of the item.
	 * @return
	 */
	public static String getName(int id) {
		return definitions[id].name;
	}

	/**
	 * The item description.
	 */
	public String itemDescription;

	/**
	 * Returns the description of an item.
	 */
	public static String getDescription(int id) {
		return definitions[id].itemDescription;
	}

	/**
	 * The item price.
	 */
	public int shopValue;

	/**
	 * Returns the price of an item.
	 */
	public static int getPrice(int id) {
		return definitions[id].shopValue;
	}

	/**
	 * The low alch value.
	 */
	public int lowAlch;

	/**
	 * Returns the low alch value of an item.
	 */
	public static int getLow(int id) {
		return definitions[id].lowAlch;
	}

	/**
	 * The high alch value.
	 */
	public int highAlch;

	/**
	 * Returns the high alch value of an item.
	 */
	public static int getHigh(int id) {
		return definitions[id].highAlch;
	}

	/**
	 * Can the item be stacked?
	 */
	public boolean isStackable;

	/**
	 * Returns whether or not the item can be stacked.
	 */
	public static boolean canStack(int id) {
		return definitions[id].isStackable;
	}

	/**
	 * Can the item be noted?
	 */
	public boolean isNoteable;

	/**
	 * Returns whether or not the item can be noted.
	 */
	public static boolean canNote(int id) {
		return definitions[id].isNoteable;
	}

	/**
	 * The weight of an item.
	 */
	public double weight;

	/**
	 * Returns the weight of an item.
	 */
	public static double getWeight(int id) {
		if (id >= 0 && id < definitions.length)
			return definitions[id].weight;

		System.out.println("WARNING: id " + id + " doesn't have a definition! 2.147kg is used as weight.");
		return 2.147;
	}

	/**
	 * The bonuses of an item.
	 */
	public double[] bonuses;

	/**
	 * Returns the array of bonuses for an item.
	 */
	public double[] getBonuses(int id) {
		return definitions[id].bonuses;
	}

	/**
	 * The stand animation of an item.
	 */
	public int stand;

	/**
	 * Returns the stand animation of an item.
	 */
	public static int getStand(int id) {
		return definitions[id].stand;
	}

	/**
	 * The walk animation of an item.
	 */
	public int walk;

	/**
	 * Returns the walk animation of an item.
	 */
	public static int getWalk(int id) {
		return definitions[id].walk;
	}

	/**
	 * The run animation of an item.
	 */
	public int run;

	/**
	 * Returns the run animation of an item.
	 */
	public static int getRun(int id) {
		return definitions[id].run;
	}

	/**
	 * The 90-degree left turn animation of an item.
	 */
	public int turn90left;

	/**
	 * Returns the 90-degree left turn animation of an item.
	 */
	public static int get90left(int id) {
		return definitions[id].turn90left;
	}

	/**
	 * The 90-degree right turn animation of an item.
	 */
	public int turn90right;

	/**
	 * Returns the 90-degree right turn animation of an item.
	 */
	public static int get90right(int id) {
		return definitions[id].turn90right;
	}

	/**
	 * The 180-degree turn animation of an item.
	 */
	public int turn180;

	/**
	 * Returns the 180-degree turn animation of an item.
	 */
	public static int get180(int id) {
		return definitions[id].turn180;
	}

	/**
	 * The attack animation of an item.
	 */
	public int attack;

	/**
	 * Returns the attack animation of an item
	 */
	public static int getAttack(int id) {
		return definitions[id].attack;
	}

	/**
	 * The block animation of an item.
	 */
	public int block;

	/**
	 * Returns the block animation of an item
	 */
	public static int getBlock(int id) {
		return definitions[id].block;
	}

	public static ItemDefinition fromString(String idString) {
		JSONObject jobj = new JSONObject(idString);

		Object[] bonusObjArr = jobj.getJSONArray("bonuses").toList().toArray();
		double[] pbonuses = ArrayUtils.toPrimitive(Arrays.copyOf(bonusObjArr, bonusObjArr.length, Double[].class));

		return new ItemDefinition(jobj.getString("name"),
				jobj.getString("itemDescription"),
				jobj.getInt("shopValue"),
				jobj.getInt("lowAlch"),
				jobj.getInt("highAlch"),
				jobj.getBoolean("isStackable"),
				jobj.getBoolean("isNoteable"),
				jobj.getInt("weight"),
                pbonuses,
				jobj.getInt("stand"),
				jobj.getInt("walk"),
				jobj.getInt("run"),
				jobj.getInt("turn90left"),
				jobj.getInt("turn90right"),
				jobj.getInt("turn180"),
				jobj.getInt("attack"),
				jobj.getInt("block"));
	}

	private String nuke(String str){
		String stripped = str.replaceAll("<.*?>", "");
		stripped = stripped.replaceAll("\"", "\\\\\"");
		return stripped;
	}

	@Override
	public String toString() {
		return "{" +
				"name:\"" + nuke(name) + '\"' +
				", itemDescription:\"" + nuke(itemDescription) + '\"' +
				", shopValue:" + shopValue +
				", lowAlch:" + lowAlch +
				", highAlch:" + highAlch +
				", isStackable:" + isStackable +
				", isNoteable:" + isNoteable +
				", weight:" + weight +
				", bonuses:" + Arrays.toString(bonuses) +
				", stand:" + stand +
				", walk:" + walk +
				", run:" + run +
				", turn90left:" + turn90left +
				", turn90right:" + turn90right +
				", turn180:" + turn180 +
				", attack:" + attack +
				", block:" + block +
				'}';
	}
}