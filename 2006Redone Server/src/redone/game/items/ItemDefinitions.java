package redone.game.items;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ItemDefinitions {


    public static ItemDefinitions getDef()[] {
        return definitions;
    }

	public ItemDefinitions() {
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
    
    private static int id;

    public static int getId() {
        return id;
    }


	/**
	 * Reads the definitions from the file.
	 */
	public static void read() {
		try {
			DataInputStream in = new DataInputStream(new FileInputStream("./Data/data/itemdef.gsu"));
			total = in.readShort();
			if(definitions == null)
				definitions = new ItemDefinitions[total];
			for(int j = 0; j < total; j++) {
				if(definitions[j] == null) {
					definitions[j] = new ItemDefinitions();
				}
				definitions[j].getValues(in);
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
	public static ItemDefinitions definitions[];

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
		return definitions[id].weight;
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
}