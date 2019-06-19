package redone.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import redone.Constants;
import redone.game.items.GroundItem;
import redone.game.items.ItemAssistant;
import redone.game.items.ItemList;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.GameLogger;
import redone.util.Misc;

/**
 * Handles ground items
 **/

public class ItemHandler {

	public List<GroundItem> items = new ArrayList<GroundItem>();
	public static final int HIDE_TICKS = 100;

	public ItemHandler() {
		for (int i = 0; i < Constants.ITEM_LIMIT; i++) {
			ItemList[i] = null;
		}
		loadItemList("item.cfg");
		loadItemPrices("prices.txt");
	}

	/**
	 * Adds item to list
	 **/
	public void addItem(GroundItem item) {
		items.add(item);
	}

	/**
	 * Removes item from list
	 **/
	public void removeItem(GroundItem item) {
		items.remove(item);
	}

	/**
	 * Item amount
	 **/
	
public int itemAmount(String name, int itemId, int itemX, int itemY) {
		for(GroundItem i : items) {
			if (i.hideTicks >= 1 && i.getName().equalsIgnoreCase(name)) {
				if(i.getItemId() == itemId && i.getItemX() == itemX && i.getItemY() == itemY) {
					return i.getItemAmount();
				}
			} else if (i.hideTicks < 1) {
				if(i.getItemId() == itemId && i.getItemX() == itemX && i.getItemY() == itemY) {
					return i.getItemAmount();
				}
			}
		}
		return 0;
	}
	/**
	 * Item exists
	 **/
	public boolean itemExists(int itemId, int itemX, int itemY) {
		for (GroundItem i : items) {
			if (i.getItemId() == itemId && i.getItemX() == itemX
					&& i.getItemY() == itemY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reloads any items if you enter a new region
	 **/
	public void reloadItems(Client c) {
		for (GroundItem i : items) {
			if (c != null) {
				if (c.getItemAssistant().tradeable(i.getItemId())
						|| i.getName().equalsIgnoreCase(c.playerName)) {
					if (c.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
						if (i.hideTicks > 0
								&& i.getName().equalsIgnoreCase(c.playerName)) {
							c.getActionSender().removeGroundItem(
									i.getItemId(), i.getItemX(), i.getItemY(),
									i.getItemAmount());
							c.getActionSender().createGroundItem(
									i.getItemId(), i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
						if (i.hideTicks == 0) {
							c.getActionSender().removeGroundItem(
									i.getItemId(), i.getItemX(), i.getItemY(),
									i.getItemAmount());
							c.getActionSender().createGroundItem(
									i.getItemId(), i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
					}
				}
			}
		}
	}

	public void process() {
		ArrayList<GroundItem> toRemove = new ArrayList<GroundItem>();
		for (int j = 0; j < items.size(); j++) {
			if (items.get(j) != null) {
				GroundItem i = items.get(j);
				if (i.hideTicks > 0) {
					i.hideTicks--;
				}
				if (i.hideTicks == 1) { // item can now be seen by others
					i.hideTicks = 0;
					createGlobalItem(i);
					i.removeTicks = HIDE_TICKS;
				}
				if (i.removeTicks > 0) {
					i.removeTicks--;
				}
				if (i.removeTicks == 1) {
					i.removeTicks = 0;
					toRemove.add(i);
				}

			}

		}

		for (int j = 0; j < toRemove.size(); j++) {
			GroundItem i = toRemove.get(j);
			removeGlobalItem(i, i.getItemId(), i.getItemX(), i.getItemY(),
					i.getItemAmount());
		}
	}

	/**
	 * Creates the ground item
	 **/
	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 },
			{ 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 },
			{ 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 },
			{ 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 },
			{ 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 },
			{ 4747, 4926 }, { 4749, 4968 }, { 4751, 4994 }, { 4753, 4980 },
			{ 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	public void createGroundItem(Client player, int itemId, int itemX, int itemY, int itemAmount, int playerId) {
		if (itemId > 0) {
			if (itemId >= 2412 && itemId <= 2414) {
				player.getActionSender().sendMessage("The cape vanishes as it touches the ground.");
				return;
			}
			if (itemId > 4705 && itemId < 4760) {
				for (int[] brokenBarrow : brokenBarrows) {
					if (brokenBarrow[0] == itemId) {
						itemId = brokenBarrow[1];
						break;
					}
				}
			}
			if (player.isDead == false) {
				return;
			}
			if (!redone.game.items.Item.itemStackable[itemId] && itemAmount > 0) {
				for (int j = 0; j < itemAmount; j++) {
					player.getActionSender().createGroundItem(itemId, itemX, itemY, 1);
					GroundItem item = new GroundItem(itemId, itemX, itemY, player.getH(), 1, player.playerId, HIDE_TICKS, PlayerHandler.players[playerId].playerName);
					addItem(item);
					String itemName = ItemAssistant.getItemName(itemId).toLowerCase();
					if (player.isDead == false && itemId != 526) {
						if (player.getPlayerAssistant().isPlayer()) {
							GameLogger.writeLog(player.playerName, "dropitem", player.playerName + " dropped " + itemAmount + " " + itemName + " absX: " + player.absX + " absY: " + player.absY + "");
						}
					}
				}
			} else {
				player.getActionSender().createGroundItem(itemId, itemX, itemY, itemAmount);
				GroundItem item = new GroundItem(itemId, itemX, itemY, player.getH(), itemAmount, player.playerId, HIDE_TICKS, PlayerHandler.players[playerId].playerName);
				addItem(item);
				String itemName = ItemAssistant.getItemName(itemId).toLowerCase();
				if (player.isDead == false && itemId != 526) {
					if (player.getPlayerAssistant().isPlayer()) {
						GameLogger.writeLog(player.playerName, "dropitem", player.playerName + " dropped " + itemAmount + " " + itemName + " absX: " + player.absX + " absY: " + player.absY + "");
					}
				}
			}
		}
	}

	/**
	 * Shows items for everyone who is within 60 squares
	 **/
	public void createGlobalItem(GroundItem i) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.playerId != i.getItemController()) {
						if (!person.getItemAssistant().tradeable(i.getItemId())
								&& person.playerId != i.getItemController()) {
							continue;
						}
						if (person.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
							person.getActionSender().createGroundItem(
									i.getItemId(), i.getItemX(), i.getItemY(),
									i.getItemAmount());
						}
					}
				}
			}
		}
	}

	/**
	 * Removing the ground item
	 **/

	public void removeGroundItem(Client c, int itemId, int itemX, int itemY, boolean add) {
		for (GroundItem i : items) {
			if (i.getItemId() == itemId && i.getItemX() == itemX
					&& i.getItemY() == itemY) {
				if (i.hideTicks > 0
						&& i.getName().equalsIgnoreCase(c.playerName)) {
					if (add) {
						if (!c.getItemAssistant().specialCase(itemId)) {
							if (c.getItemAssistant().addItem(i.getItemId(),
									i.getItemAmount())) {
								removeControllersItem(i, c, i.getItemId(),
										i.getItemX(), i.getItemY(),
										i.getItemAmount());
								break;
							}
						} else {
							removeControllersItem(i, c, i.getItemId(),
									i.getItemX(), i.getItemY(),
									i.getItemAmount());
							break;
						}
					} else {
						removeControllersItem(i, c, i.getItemId(),
								i.getItemX(), i.getItemY(), i.getItemAmount());
						break;
					}
				} else if (i.hideTicks <= 0) {
					if (add) {
						if (c.getItemAssistant().addItem(i.getItemId(),
								i.getItemAmount())) {
							removeGlobalItem(i, i.getItemId(), i.getItemX(),
									i.getItemY(), i.getItemAmount());
							break;
						}
					} else {
						removeGlobalItem(i, i.getItemId(), i.getItemX(),
								i.getItemY(), i.getItemAmount());
						break;
					}
				}
			}
		}
	}

	/**
	 * Remove item for just the item controller (item not global yet)
	 **/

	public void removeControllersItem(GroundItem i, Client c, int itemId,
			int itemX, int itemY, int itemAmount) {
		c.getActionSender().removeGroundItem(itemId, itemX, itemY,
				itemAmount);
		removeItem(i);
	}

	/**
	 * Remove item for everyone within 60 squares
	 **/

	public void removeGlobalItem(GroundItem i, int itemId, int itemX,
			int itemY, int itemAmount) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.distanceToPoint(itemX, itemY) <= 60) {
						person.getActionSender().removeGroundItem(itemId,
								itemX, itemY, itemAmount);
					}
				}
			}
		}
		removeItem(i);
	}

	/**
	 * Item List
	 **/

	public ItemList ItemList[] = new ItemList[Constants.ITEM_LIMIT];

	public void newItemList(int ItemId, String ItemName,
			String ItemDescription, double ShopValue, double LowAlch,
			double HighAlch, int Bonuses[]) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 0; i < 11740; i++) {
			if (ItemList[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1) {
			return; // no free slot found
		}
		ItemList newItemList = new ItemList(ItemId);
		newItemList.itemName = ItemName;
		newItemList.itemDescription = ItemDescription;
		newItemList.ShopValue = ShopValue;
		newItemList.LowAlch = LowAlch;
		newItemList.HighAlch = HighAlch;
		newItemList.Bonuses = Bonuses;
		ItemList[slot] = newItemList;
	}

	public void loadItemPrices(String filename) {
		try {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(new File("./data/cfg/" + filename));
			while (s.hasNextLine()) {
				String[] line = s.nextLine().split(" ");
				ItemList temp = getItemList(Integer.parseInt(line[0]));
				if (temp != null) {
					temp.ShopValue = Integer.parseInt(line[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ItemList getItemList(int i) {
		for (redone.game.items.ItemList element : ItemList) {
			if (element != null) {
				if (element.itemId == i) {
					return element;
				}
			}
		}
		return null;
	}

	public boolean loadItemList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./data/cfg/"
					+ FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			// return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("item")) {
					int[] Bonuses = new int[12];
					for (int i = 0; i < 12; i++) {
						if (token3[6 + i] != null) {
							Bonuses[i] = Integer.parseInt(token3[6 + i]);
						} else {
							break;
						}
					}
					newItemList(Integer.parseInt(token3[0]),
							token3[1].replaceAll("_", " "),
							token3[2].replaceAll("_", " "),
							Double.parseDouble(token3[4]),
							Double.parseDouble(token3[4]),
							Double.parseDouble(token3[6]), Bonuses);
				}
			} else {
				if (line.equals("[ENDOFITEMLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					//return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}
}
