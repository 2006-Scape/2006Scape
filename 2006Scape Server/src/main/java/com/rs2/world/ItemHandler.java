package com.rs2.world;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.GameConstants;
import com.rs2.game.items.Deprecated;
import com.rs2.game.items.GroundItem;
import com.rs2.game.items.ItemList;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.GameLogger;
import com.rs2.util.ItemData;
import com.rs2.util.Misc;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles ground items
 **/

public class ItemHandler {

    public              List<GroundItem> items      = new ArrayList<GroundItem>();
    public static final int              HIDE_TICKS = 100;

    public ItemHandler() {
        for (int i = 0; i < GameConstants.ITEM_LIMIT; i++) {
            itemList[i] = null;
        }
        //loadItemList("item.cfg");
        loadItemListJson();
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
        for (GroundItem i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                if (i.getItemId() == itemId && i.getItemX() == itemX && i.getItemY() == itemY) {
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
            if (i.getItemId() == itemId && i.getItemX() == itemX && i.getItemY() == itemY) {
                return true;
            }
        }
        if (GlobalDropsHandler.itemExists(itemId, itemX, itemY, true)) {
            return true;
        }
        return false;
    }

    public void moveItem(GroundItem item, int itemX, int itemY) {
        if (items.remove(item)) {
            int oldX = item.itemX;
            int oldY = item.itemY;
            item.itemX = itemX;
            item.itemY = itemY;
            items.add(item);
            for (Player p: PlayerHandler.players) {
                if (p == null) continue;
                p.getPacketSender().removeGroundItem(item.itemId, oldX, oldY, item.itemAmount);
                reloadItems(p);
            }
        }
    }

    /**
     * Reloads any items if you enter a new region
     **/
    public void reloadItems(Player c) {
        for (GroundItem i : items) {
            if (c != null && i != null) {
                if (c.getH() == i.getItemH() && c.distanceToPoint(i.getItemX(), i.getItemY()) <= 120) {
                    c.getPacketSender().removeGroundItem(
                            i.getItemId(), i.getItemX(), i.getItemY(),
                            i.getItemAmount());
                }
            }
        }
        for (GroundItem i : items) {
            if (c != null && i != null) {
                // If it's a players item or tradeable
                if (c.getItemAssistant().tradeable(i.getItemId()) || i.getName().equalsIgnoreCase(c.playerName)) {
                    // Make sure item on the same height and within 60 blocks
                    if (c.getH() == i.getItemH() && c.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
                        if (i.hideTicks > 0  && i.getName().equalsIgnoreCase(c.playerName)) {
                            c.getPacketSender().createGroundItem(
                                    i.getItemId(), i.getItemX(), i.getItemY(),
                                    i.getItemAmount());
                        }
                        if (i.hideTicks == 0) {
                            c.getPacketSender().createGroundItem(
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

    public void createGroundItem(Player c, int itemId, int itemX, int itemY, int itemAmount, int playerId) {
        if (itemId > 0) {
            if (itemId >= 2412 && itemId <= 2414) {
                c.getPacketSender().sendMessage("The cape vanishes as it touches the ground.");
                return;
            }
            if (itemId >= 4708 && itemId <= 4759) {
                for (int[] brokenBarrow : brokenBarrows) {
                    if (brokenBarrow[0] == itemId) {
                        itemId = brokenBarrow[1];
                        break;
                    }
                }
            }
            if (!org.apollo.cache.def.ItemDefinition.lookup(itemId).isStackable() && itemAmount > 0) {
                for (int j = 0; j < itemAmount; j++) {
                    GroundItem item = new GroundItem(itemId, itemX, itemY, c.getH(), 1, c.playerId, HIDE_TICKS, PlayerHandler.players[playerId].playerName);
                    addItem(item);
                    String itemName = Deprecated.getItemName(itemId).toLowerCase();
                    if (c.isDead == false && itemId != 526) {
                        if (c.getPlayerAssistant().isPlayer()) {
                            GameLogger.writeLog(c.playerName, "dropitem", c.playerName + " dropped " + itemAmount + " " + itemName + " absX: " + c.absX + " absY: " + c.absY + "");
                        }
                    }
                }
            } else {
                GroundItem item = new GroundItem(itemId, itemX, itemY, c.getH(), itemAmount, c.playerId, HIDE_TICKS, PlayerHandler.players[playerId].playerName);
                addItem(item);
                String itemName = Deprecated.getItemName(itemId).toLowerCase();
                if (c.isDead == false && itemId != 526) {
                    if (c.getPlayerAssistant().isPlayer()) {
                        GameLogger.writeLog(c.playerName, "dropitem", c.playerName + " dropped " + itemAmount + " " + itemName + " absX: " + c.absX + " absY: " + c.absY + "");
                    }
                }
            }
            reloadItems(c);
        }
    }

    /**
     * Shows items for everyone who is within 60 squares
     **/
    public void createGlobalItem(GroundItem i) {
        if (!itemExists(i.getItemId(), i.getItemX(), i.getItemY())) {
            addItem(i);
        }
        for (Player p : PlayerHandler.players) {
            if (p != null) {
                Client person = (Client) p;
                if (person != null) {
                    if (person.playerId != i.getItemController()) {
                        if (!person.getItemAssistant().tradeable(i.getItemId())
                                && person.playerId != i.getItemController()) {
                            continue;
                        }
                        if (person.getH() == i.getItemH() && person.distanceToPoint(i.getItemX(), i.getItemY()) <= 60) {
                            person.getPacketSender().createGroundItem(
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

    public void removeGroundItem(Player c, int itemId, int itemX, int itemY, boolean add) {
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

    public void removeControllersItem(GroundItem i, Player c, int itemId,
                                      int itemX, int itemY, int itemAmount) {
        c.getPacketSender().removeGroundItem(itemId, itemX, itemY,
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
                        person.getPacketSender().removeGroundItem(itemId, itemX, itemY, itemAmount);
                    }
                }
            }
        }
        removeItem(i);
    }

    /**
     * Item List
     **/

    public ItemList itemList[] = new ItemList[GameConstants.ITEM_LIMIT];

    public void newItemList(int ItemId, String ItemName, String ItemDescription, double ShopValue, double LowAlch, double HighAlch, int Bonuses[]) {
        // first, search for a free slot
        int slot = -1;
        for (int i = 0; i < 11740; i++) {
            if (itemList[i] == null) {
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
        itemList[slot] = newItemList;
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
        for (com.rs2.game.items.ItemList element : itemList) {
            if (element != null) {
                if (element.itemId == i) {
                    return element;
                }
            }
        }
        return null;
    }

    public void loadItemListJson() {
        Gson gson = new Gson();

        try {
            Type collectionType = new TypeToken<ItemData[]>() {
            }.getType();
            ItemData[] data = gson.fromJson(new FileReader("./data/cfg/items.json"), collectionType);

            for (ItemData item : data) {
                newItemList(item.getId(),
                        item.getName(),
                        item.getExamine(),
                        item.getValues().getShopValue(),
                        item.getValues().getLowAlch(),
                        item.getValues().getHighAlch(),
                        item.getBonuses().getBonuses());
            }
        } catch (FileNotFoundException fileex) {
            Misc.println("items.json: file not found.");
        }
    }

    public boolean readCfgWriteJson(String FileName) {
        String         line          = "";
        String         token         = "";
        String         token2        = "";
        String         token2_2      = "";
        String[]       token3        = new String[10];
        boolean        EndOfFile     = false;
        BufferedReader characterfile = null;
        JSONArray      array         = new JSONArray();
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
                token2_2 = token2.replaceAll("\t+", "\t");
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
                    JSONObject object = new JSONObject();

                    object.put("id", token3[0]);
                    object.put("name", token3[1].replaceAll("_", " "));
                    object.put("examine", token3[2].replaceAll("_", " "));

                    JSONArray  array1  = new JSONArray();
                    JSONObject object1 = new JSONObject();
                    object1.put("shopValue", token3[4]);
                    object1.put("lowAlch", token3[5]);
                    object1.put("highAlch", token3[6]);
                    array1.put(0, object1);
                    object.put("values", array1);

                    JSONArray  array2  = new JSONArray();
                    JSONObject object2 = new JSONObject();
                    object2.put("attackStab", Bonuses[0]);
                    object2.put("attackSlash", Bonuses[1]);
                    object2.put("attackCrush", Bonuses[2]);
                    object2.put("attackMagic", Bonuses[3]);
                    object2.put("attackRange", Bonuses[4]);
                    object2.put("defenceStab", Bonuses[5]);
                    object2.put("defenceSlash", Bonuses[6]);
                    object2.put("defenceCrush", Bonuses[7]);
                    object2.put("defenceMagic", Bonuses[8]);
                    object2.put("defenceRange", Bonuses[9]);
                    object2.put("strengthBonus", Bonuses[10]);
                    object2.put("prayerBonus", Bonuses[11]);
                    array2.put(0, object2);
                    object.put("bonuses", array2);

                    array.put(object);
                }
            } else {
                if (line.equals("[ENDOFITEMLIST]")) {
                    try {
                        FileWriter fileWriter = new FileWriter("item-dump.json");
                        fileWriter.write(array.toString());
                        fileWriter.close();
                        characterfile.close();
                    } catch (IOException e) {
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
