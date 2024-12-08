package com.rs2.world;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.GlobalDropData;
import com.rs2.util.Misc;
import com.rs2.util.ShopData;

import org.apollo.cache.def.ItemDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles global drops which respawn after set amount of time when taken
 *
 * @author Stuart <RogueX>
 */
public class GlobalDropsHandler {

    /**
     * time in seconds it takes for the item to respawn
     */
    private static final int TIME_TO_RESPAWN = 20;

    /**
     * holds all the objects
     */
    private static final List<GlobalDrop> globalDrops = new ArrayList<>();

    private static final Set<GlobalDrop> spawnedDrops = new HashSet<>();


    /**
     * loads the items
     */
    public static void initialize() {
        Gson           gson = new Gson();
        try {
            Type collectionType = new TypeToken<GlobalDropData[]>() {
            }.getType();
            GlobalDropData[] globalDropData = gson.fromJson(new FileReader("./data/cfg/globaldrops.json"), collectionType);

            for (GlobalDropData data : globalDropData) {
				if (data.getHeight() > 0) {
					globalDrops.add(new GlobalDrop(data.getId(), data.getAmount(), data.getItemX(), data.getItemY(), data.getHeight()));
				} else {
					globalDrops.add(new GlobalDrop(data.getId(), data.getAmount(), data.getItemX(), data.getItemY()));
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded " + globalDrops.size() + " global drops.");

        for (Player player : PlayerHandler.players) {
            Client player2 = (Client) player;
            if (player2 != null) {
                CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                    @Override
                    public void execute(CycleEventContainer container) {
                        for (GlobalDrop drop : globalDrops) {
                            if (drop.isTaken() && drop.isSpawned()) {
                                if (System.currentTimeMillis() - drop.getTakenAt() >= TIME_TO_RESPAWN * 1000) {
                                    drop.setTaken(false);
                                    if (player2.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
                                        player2.getPacketSender().createGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount(), drop.getHeight());
                                        spawnedDrops.add(drop);
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    public void stop() {

                    }
                }, 1);
            }
        }
    }

    public static void writeGlobalDropsDump() {
        String         Data;
        BufferedReader Checker;
        JSONArray      array = new JSONArray();
        try {
            Checker = new BufferedReader(new FileReader("./data/cfg/globaldrops.txt"));
            while ((Data = Checker.readLine()) != null) {
                if (Data.startsWith("#")) {
                    continue;
                }
                String[] args = Data.split(":");

                JSONObject object = new JSONObject();

                object.put("id", Integer.parseInt(args[0]));
                object.put("amount", args[1].replace("_", " "));
                object.put("itemX", Integer.parseInt(args[2]));
                object.put("itemY", Integer.parseInt(args[3]));

                if (args.length == 5) {
                    object.put("height", Integer.parseInt(args[4]));
                }

                array.put(object);
            }
            Checker.close();

            FileWriter fileWriter = new FileWriter("globaldrops-dump.json");
            fileWriter.write(array.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * See if a drop exists at the given place
     *
     * @param a item id
     * @param b x cord
     * @param c y cord
     *
     * @return return the statement
     */
    private static GlobalDrop itemExists(int a, int b, int c) {
        for (GlobalDrop drop : globalDrops) {
            if (drop.getId() == a && drop.getX() == b && drop.getY() == c) {
                return drop;
            }
        }
        return null;
    }

    public static boolean itemExists(int itemID, int itemX, int itemY, boolean yes) {
        for (GlobalDrop drop : spawnedDrops) {
            if (drop.getId() == itemID && drop.getX() == itemX && drop.getY() == itemY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Pick up an item at the given location
     *
     * @param player the Player
     * @param itemID       item id
     * @param itemX       cord x
     * @param itemY       cord y
     */
    public static void pickup(Player player, int itemID, int itemX, int itemY) {
        GlobalDrop drop = itemExists(itemID, itemX, itemY);
        if (drop == null) {
            return;
        }
        if (drop.isTaken()) {
            return;
        }
        if (player.getItemAssistant().freeSlots() < 1) {
            if (!(player.getItemAssistant().playerHasItem(player.pItemId) && ItemDefinition.lookup(player.pItemId).isStackable())) {
                player.getPacketSender().sendMessage("Not enough space in your inventory.");
                return;
            }
        }
        player.getItemAssistant().addItem(drop.getId(), drop.getAmount());
        drop.setTakenAt(System.currentTimeMillis());
        drop.setTaken(true);
        for (Player playerLoop : PlayerHandler.players) {
            Client cl = (Client) playerLoop;
            if (cl != null) {
                cl.getPacketSender().removeGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount());
                spawnedDrops.remove(drop);
            }
        }
    }

    /**
     * Loads all the items when a player changes region
     *
     * @param player the Player
     */
    public static void load(Client player) {
        for (GlobalDrop drop : globalDrops) {
            if (!drop.isTaken() && !drop.isSpawned() && !itemExists(drop.getId(), drop.getX(), drop.getY(), true) && player.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
                player.getPacketSender().createGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount(), drop.getHeight());
                spawnedDrops.add(drop);
                drop.setSpawned(true);
            }
        }
    }

    public static void reset(Player c) {
        for (GlobalDrop drop : globalDrops) {
            if (c.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
                c.getPacketSender().removeGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount());
            }
        }
        spawnedDrops.clear();
        globalDrops.clear();
        initialize();
        for (GlobalDrop drop : globalDrops) {
            if (!drop.isTaken() && !drop.isSpawned() && !itemExists(drop.getId(), drop.getX(), drop.getY(), true) && c.distanceToPoint(drop.getX(), drop.getY()) <= 60) {
                c.getPacketSender().createGroundItem(drop.getId(), drop.getX(), drop.getY(), drop.getAmount(), drop.getHeight());
                spawnedDrops.add(drop);
                drop.setSpawned(true);
            }
        }
    }

    /**
     * Holds each drops data
     *
     * @author Stuart
     */
    static class GlobalDrop {

        /**
         * cord x
         */
        int itemX;
        /**
         * cord y
         */
        int itemY;

        private int height;
        /**
         * item id
         */
        int     id;
        /**
         * item amount
         */
        int     amount;
        /**
         * has the item been taken
         */
        boolean taken = false;

        private boolean spawned = false;

        /**
         * Time it was taken at
         */
        long takenAt;

        /**
         * Sets the drop arguments
         *
         * @param id item id
         * @param amount       item amount
         * @param itemX  cord x
         * @param itemY       cord y
         */

        public GlobalDrop(int id, int amount, int itemX, int itemY) {
            this.id = id;
            this.amount = amount;
            this.itemX = itemX;
            this.itemY = itemY;
        }

        public GlobalDrop(int id, int amount, int itemX, int itemY, int height) {
            this.id = id;
            this.amount = amount;
            this.itemX = itemX;
            this.itemY = itemY;
            this.height = height;
        }

        /**
         * get cord x
         *
         * @return return the statement
         */
        public int getX() {
            return itemX;
        }

        /**
         * get cord x
         *
         * @return return the statement
         */
        public int getY() {
            return itemY;
        }

        /**
         * get the item id
         *
         * @return return the statement
         */
        public int getId() {
            return id;
        }

        /**
         * get the item amount
         *
         * @return return the statement
         */
        public int getAmount() {
            return amount;
        }

        /**
         * has the drop already been taken?
         *
         * @return return the statement
         */
        public boolean isTaken() {
            return taken;
        }

        /**
         * set if or not the drop has been taken
         *
         * @param a true yes false no
         */
        public void setTaken(boolean a) {
            taken = a;
        }

        /**
         * set the time it was picked up
         *
         * @param a the a
         */
        public void setTakenAt(long a) {
            takenAt = a;
        }

        /**
         * get the time it was taken at
         *
         * @return return the statement
         */
        public long getTakenAt() {
            return takenAt;
        }

        public boolean isSpawned() {
            return spawned;
        }

        public void setSpawned(boolean spawned) {
            this.spawned = spawned;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }

}
