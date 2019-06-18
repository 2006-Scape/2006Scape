package redone.game.content.minigames.trawler;
 
import java.util.ArrayList;
import java.util.Random;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.items.GameItem;
import redone.game.players.Client;
import redone.game.players.Player;
 
public class Trawler extends GroupMinigame {
       
        /*
         *
         */
       
        /*
         * The waiting room for the minigame
         */
       
        public WaitingRoom waiting_room = new TrawlerWaitingRoom(this);
       
        /*
         * The arraylist containing all of the players
         */
 
        public ArrayList<Client> players = new ArrayList<Client>();
 
        /*
         * The arraylist containing all of the players that need to removed Created
         * so that an instance wouldn't have to be created every time the updating
         * is called simply just call the clear method
         */
 
        public ArrayList<Client> players_to_remove = new ArrayList<Client>();
 
        /*
         * A unique id, which the cycle event handler may use to stop the trawlers
         * event
         */
 
        public static final int CYCLE_ID = 222;
 
        /*
         * Random object
         */
 
        private final Random random_gen = new Random();
 
        /*
         * All of the animations needed for the minigame
         */
 
        final int climb_up = 828;
        final int climb_down = 827;
        final int swimming_walk = 772;
        final int swimming_stand = 773;
        final int net_interaction = 832;
        final int SWIM_MOVEMENT_ANIMATION = 772;
        final int SWIM_STILL_ANIMATION = 773;
 
        /*
         * All of the items needed for the minigame
         */
 
        final int bailing_bucket_full = 585;
        final int bailing_bucket_empty = 583;
        final int rope = 954;
        final int swamp_paste = 1941;
 
        /*
         * All of the objects needed for the minigame
         */
 
        final int perfect_wall = 2177;
        final int patched_wall = 2168;
        final int leaking_wall = 2167;
 
        /*
         * Variables that will be changing as the minigame progresses
         */
 
        private int water_level = 0;
        private boolean net_ripped = false;
        public int fish_caught = 0;
        private boolean[] wall_status = new boolean[16];
        private boolean isSunk = false;
        private int game_time = 10;
        private boolean started = false;
        boolean in_progress = false;
 
        /*
         * Contains the coordinates for each wall, since all of the objects of the
         * same id's and the methods needed to get them
         */
 
        public enum Wall {
 
        North_One_Normal(0, 1885, 4826), North_Two_Normal(1, 1886, 4826), North_Three_Normal(
                        2, 1887, 4826), North_Four_Normal(3, 1888, 4826), North_Five_Normal(
                        4, 1889, 4826), North_Six_Normal(5, 1890, 4826), North_Seven_Normal(
                        6, 1891, 4826), North_Eight_Normal(7, 1892, 4826), South_One_Normal(
                        8, 1885, 4823), South_Two_Normal(9, 1886, 4823), South_Three_Normal(
                        10, 1887, 4823), South_Four_Normal(11, 1888, 4823), South_Five_Normal(
                        12, 1889, 4823), South_Six_Normal(13, 1890, 4823), South_Seven_Normal(
                        14, 1891, 4823), South_Eight_Normal(15, 1892, 4823), North_One_Sinking(
                        0, 2013, 4826), North_Two_Sinking(1, 2014, 4826), North_Three_Sinking(
                        2, 2015, 4826), North_Four_Sinking(3, 2016, 4826), North_Five_Sinking(
                        4, 2017, 4826), North_Six_Sinking(5, 2018, 4826), North_Seven_Sinking(
                        6, 2019, 4826), North_Eight_Sinking(7, 2020, 4826), South_One_Sinking(
                        8, 2013, 4823), South_Two_Sinking(9, 2014, 4823), South_Three_Sinking(
                        10, 2015, 4823), South_Four_Sinking(11, 2016, 4823), South_Five_Sinking(
                        12, 2017, 4823), South_Six_Sinking(13, 2018, 4823), South_Seven_Sinking(
                        14, 2019, 4823), South_Eight_Sinking(15, 2020, 4823);
 
        int index, x, y;
 
        Wall(int index, int x, int y) {
                this.index = index;
                this.y = y;
                this.x = x;
        }
 
        public static int getIndex(int x, int y) {
                for (Wall s : Wall.values()) {
                        if (s != null) {
                                if (s.x == x && s.y == y) {
                                        return s.index;
                                }
                        }
                }
                return -1;
        }
 
        public static Wall getWallByIndex(int index, boolean sinking) {
                for (Wall w : Wall.values()) {
                        if (w.index == index) {
                                if (sinking && w.x < 2000) {
                                        continue;
                                } else {
                                        return w;
                                }
                        }
                }
                return null;
        }
 
        }
 
        /*
         * Gets the amount of walls that are not broken
         */
 
        public int getAvaliableWallSize() {
                int toReturn = 0;
                for (int j = 0; j < wall_status.length; j++) {
                        if (wall_status[j] == false) {
                                toReturn++;
                        }
                }
                return toReturn;
        }
 
        /*
         * Generates the indexes of the walls that are not broken
         */
 
        public int[] getAvaliableWalls() {
                int[] toReturn = new int[getAvaliableWallSize()];
                int index = 0;
                for (int j = 0; j < wall_status.length; j++) {
                        if (wall_status[j] == false) {
                                toReturn[index] = j;
                                index++;
                        }
                }
                return toReturn;
        }
 
        /*
         * Sets a random wall as broken & updates it
         */
 
        public void breakRandomWall() {
                try {
                        final int[] walls = getAvaliableWalls();
                        int random = walls[random_gen.nextInt(walls.length)];
                        wall_status[random] = true;
                        updateWall(random);
                } catch (Exception e) {// Exception should never occur
 
                }
        }
 
        /*
         * Removes every wall and resets them back to default
         */
       
        public void resetWalls() {
                for(Wall w : Wall.values()) {
                        if(w != null) {
                                Server.objectHandler.removeObject(Server.objectHandler.getObjectByPosition(w.x, w.y));
                                Server.objectHandler.createAnObject(perfect_wall, w.x,w.y, w.y == 4826 ? 1 : 3);
                        }
                }
        }
       
        /*
         * Updates a wall based on index
         */
 
        public void updateWall(int index) {
                Wall w = Wall.getWallByIndex(index, isSunk);
                if (w == null) {
                        System.out.println("null");
                        return;
                }
                Server.objectHandler.removeObject(Server.objectHandler.getObjectByPosition(w.x, w.y));
                Server.objectHandler.removeObject(Server.objectHandler.getObjectByPosition(w.x + (isSunk ? -128 : 128), w.y));
                if (wall_status[index] == true) {
                        Server.objectHandler.createAnObject(leaking_wall, w.x, w.y,
                                        w.y == 4826 ? 1 : 3);
                        if (isSunk)
                                Server.objectHandler.createAnObject(leaking_wall, w.x - 128,
                                                w.y, w.y == 4826 ? 1 : 3);
                        else
                                Server.objectHandler.createAnObject(leaking_wall, w.x + 128,
                                                w.y, w.y == 4826 ? 1 : 3);
                } else {
                        Server.objectHandler.createAnObject(patched_wall, w.x, w.y,
                                        w.y == 4826 ? 1 : 3);
                        if (isSunk)
                                Server.objectHandler.createAnObject(patched_wall, w.x - 128,
                                                w.y, w.y == 4826 ? 1 : 3);
                        else
                                Server.objectHandler.createAnObject(patched_wall, w.x + 128,
                                                w.y, w.y == 4826 ? 1 : 3);
                }
        }
 
        /*
         * Updates every play in the game's interface, if the player is null they
         * are removed from the game
         */
 
        public void playerUpdates() {
                for (Client p : players) {
                        if (p != null) {
                                p.asClient()
                                                .getPlayerAssistant()
                                                .sendFrame126(net_ripped ? "@red@Ripped" : "@gre@Okay",
                                                                11935);
                                p.getPlayerAssistant().sendFrame126("" + fish_caught, 11937);
                                p.getPlayerAssistant().sendFrame126(game_time + " mins", 11938);
                                p.getPlayerAssistant().sendFrame20(391, water_level);
                        } else {
                                players_to_remove.add(p);
                        }
                }
                if (players_to_remove.size() > 0) {
                        for (Player p : players_to_remove) {
                                players.remove(p);
                        }
                        players_to_remove.clear();
                }
        }
 
        /*
         * Does everything needed when the game starts
         */
 
        public void onStart() {
                resetWalls();
                waiting_room.setActive(false);
                water_level = 0;
                fish_caught = 0;
                net_ripped = false;
                isSunk = false;
                started = false;
                game_time = 10;
                in_progress = true;
                for (int j = 0; j < wall_status.length; j++) {
                        wall_status[j] = false;
                }
                playerUpdates();
                for (Client p : players) {
                        if (p != null) {
                                p.getPlayerAssistant().removeAllSidebars();
                                p.getPlayerAssistant().sendMapState(2);
                                p.getPlayerAssistant().showInterface(3281);
                                p.getPlayerAssistant().sendFrame20(75, 11);
                                p.getPlayerAssistant().movePlayer(1885, 4825, 1);
                                p.getPlayerAssistant().sendFrame126("", 11936);
                        }
                }
                CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                                container.stop();
                        }
 
                        @Override
                        public void stop() {
                                for (Client p : players) {
                                        if (p != null) {
                                        	 	p.getPlayerAssistant().sendSidebars();
                                                p.getPlayerAssistant().showInterface(5596);
                                                p.getPlayerAssistant().sendMapState(0);
                                        }
                                }
                                started = true;
                                startGameTimer();
                        }
                }, 25);
        }
 
        /*
         * Does everything needed when the game ends
         */
 
        public void onEndLose() {
                for (Client p : players) {
                        if (p != null) {
                                p.getPlayerAssistant().movePlayer(1885, 4825, 1);
                        }
                }
        }
 
        /*
         * Essentially starts the game
         */
 
        public void start() {
 
                CycleEventHandler.getSingleton().stopEvents(CYCLE_ID);// Stops any other
                                                                                                                                // events using
                                                                                                                                // the trawler's
                                                                                                                                // id
                onStart();
                CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                                if (started) {
                                        tick();
                                        if (end() > 0) {
                                                if (end() == 1) {
                                                        setSwimmingAnimations();// Loss
                                                        movePlayersLoss();
                                                        players.clear();
                                                        container.stop();
                                                } else if (end() == 2) {
                                                        for (Client p : players) {
                                                                if (p != null) {
                                                                        p.fishingTrawlerReward = playerReward(p);
                                                                }
                                                        }
                                                        movePlayerWin(players);
                                                        container.stop();
                                                }
                                                container.stop();
                                        }
                                }
                        }
 
                        @Override
                        public void stop() {
                                waiting_room.reset();
                                waiting_room.setActive(true);
                                in_progress = false;
                                game_time = 0;
                                players.clear();
                                //System.out.println("should start again now");
                                waiting_room.startWaiting();
                        }
                }, 10);
        }
 
        /*
         * Everything that should be done on one tick of the minigame
         */
 
        public void tick() {
                int random;
                random = random_gen.nextInt(2) + 1;
                for (int j = 0; j < random; j++) {
                        breakRandomWall();
                }
                ripNet();
                increaseWaterLevel();
                switchBoats();
                if (!net_ripped)
                        increaseFish();
                playerUpdates();
        }
 
        /*
         * Randomly breaks the net
         */
 
        public void ripNet() {
                if (!net_ripped) {
                        if (random_gen.nextInt(10) > 7) {
                                net_ripped = true;
                        }
                }
        }
 
        /*
         * Adds water depending on the amount of leaks in the ship
         */
 
        public void increaseWaterLevel() {
                int leaks = 16 - getAvaliableWalls().length;
                water_level += (leaks / 2) + random_gen.nextInt(leaks * random_gen.nextInt(2) + 1);
        }
 
        /*
         * Fixes the holes in the ship
         */
 
        public void fixHole(Client p, int x, int y) {
                if (doAction(p)) {
                        if (p.getItemAssistant().playerHasItem(swamp_paste)) {
                                int index = Wall.getIndex(x, y);
                                if (index >= 0) {
                                        p.getItemAssistant().deleteItem2(swamp_paste, 1);
                                        p.startAnimation(832);
                                        wall_status[index] = false;
                                        updateWall(index);
                                        p.turnPlayerTo(x, y + (y == 4826 ? 1 : -1));
                                }
                        } else {
                                p.getActionSender().sendMessage("You don't have any swamp paste.");
                        }
                }
        }
 
        /*
         * Moves the player to the top of the boat
         */
 
        public void upLadder(Client p, int obX, int obY) {
                if (doAction(p)) {
                        if (!isSunk) {
                                p.startAnimation(climb_up);
                                p.getPlayerAssistant()
                                                .movePlayer(obX == 1884 ? 1885 : 1892, obY, 1);
                        } else {
                                p.startAnimation(climb_up);
                                p.getPlayerAssistant().movePlayer(obX == 2021 ? 2020 : 2013, obY, 1);
                        }
                }
        }
 
        /*
         * Moves the player to the top of the boat
         */
 
        public void downLadder(Client p, int obX, int obY) {
                if (doAction(p)) {
                        if (!isSunk) {
                                p.startAnimation(climb_down);
                                p.getPlayerAssistant()
                                                .movePlayer(obX == 1884 ? 1885 : 1892, obY, 0);
                        } else {
                                p.startAnimation(climb_down);
                                p.getPlayerAssistant().movePlayer(obX == 2021 ? 2020 : 2013, obY, 0);
                        }
                }
        }
 
        /*
         * Fixes the net on the ship
         */
 
        public void fixNet(Client p) {
                if (doAction(p)) {
                        if (!net_ripped) {
                                p.getActionSender().sendMessage("The net is not ripped.");
                                return;
                        }
                        if (!p.getItemAssistant().playerHasItem(rope)) {
                                p.getActionSender().sendMessage(
                                                "You need a rope before attempting to fix the net!");
                                return;
                        }
                        p.startAnimation(net_interaction);
                        if (skillCheck(p.playerLevel[p.playerCrafting], 1, 0)) {
                                p.getItemAssistant().deleteItem(rope, 1);
                                net_ripped = false;
                                playerUpdates();
                                p.getActionSender().sendMessage("You successfully fix the net!");
                        } else {
                                p.getItemAssistant().deleteItem(rope, 1);
                                p.getActionSender().sendMessage("You failed to repair the net!");
                        }
                }
        }
 
        /*
         * Returns true if the player can complete the interaction
         */
 
        public boolean doAction(Player p) {
                if (!players.contains(p)) {
                        return false;
                }
                if (System.currentTimeMillis() - p.lastFishingTrawlerInteraction >= 1600) {
                        p.lastFishingTrawlerInteraction = System.currentTimeMillis();
                        return true;
                } else {
                        return false;
                }
        }
 
        /*
         * Bails water out of the boat
         */
 
        public void bail(Client p) {
                if (doAction(p)) {
                        if (p.getItemAssistant().playerHasItem(bailing_bucket_empty)) {
                                p.startAnimation(827);
                                p.getItemAssistant()
                                                .replaceItem(bailing_bucket_empty, bailing_bucket_full);
                                water_level -= random_gen.nextInt(3) + 1;
                        }
                }
        }
 
        /*
         * Bails water out of the boat
         */
 
        public void emptyBucket(Client p) {
                if (doAction(p)) {
                        if (p.getItemAssistant().playerHasItem(bailing_bucket_full)) {
                                p.startAnimation(832);
                                p.getItemAssistant()
                                                .replaceItem(bailing_bucket_full, bailing_bucket_empty);
                        }
                }
        }
 
        /*
         * Adds a random amount of fish to the total fish reaward, based off of the
         * amount of players in the game
         */
 
        public void increaseFish() {
                fish_caught += random_gen.nextInt(players.size() + 2);
        }
 
        /*
         * If it returns true, the minigame will end
         */
 
        public int end() {
                if (players.size() == 0) {
                        return 1;
                }
                if (water_level >= 100) {
                        return 1;
                }
                if (game_time == 0) {
                        return 2;
                }
                return 0;
        }
 
        /*
         * Sets the swimming animations
         */
 
        public void setSwimmingAnimations() {
                for(Client p : players) {
                        if (p != null) {
                                p.prevplayerWalkIndex = p.playerWalkIndex;
                                p.prevPlayerStandIndex = p.playerStandIndex;
                                p.prevPrevPlayerRunIndex = p.playerRunIndex;
                                p.prevPlayerTurnIndex = p.playerTurnIndex;
                                p.prevPlayerTurn180Index = p.playerTurn180Index;
                                p.prevPlayerTurn90CCWIndex = p.playerTurn90CCWIndex;
                                p.prevPlayerTurn90CWIndex = p.playerTurn90CWIndex;
                                p.prevRunning2 = p.isRunning2;
                                p.isRunning2 = false;
                                p.playerRunIndex = SWIM_MOVEMENT_ANIMATION;
                                p.playerStandIndex = SWIM_STILL_ANIMATION;
                                p.playerWalkIndex = SWIM_MOVEMENT_ANIMATION;
                                p.playerTurnIndex = SWIM_STILL_ANIMATION;
                                p.playerTurn90CWIndex = SWIM_STILL_ANIMATION;
                                p.playerTurn90CCWIndex = SWIM_STILL_ANIMATION;
                                p.playerTurn180Index = SWIM_STILL_ANIMATION;
                                p.getPlayerAssistant().requestUpdates();
                                p.getPlayerAssistant().closeAllWindows();
                        }
                }
        }
 
        /*
         * Switches boats based on the water level
         */
 
        public void switchBoats() {
                if (water_level > 25 && !isSunk) {
                        isSunk = true;
                        for (int j = 0; j < players.size(); j++) {
                                if (players.get(j) != null) {
                                        players.get(j).stopMovement();
                                        players.get(j).getPlayerAssistant()
                                                       .movePlayer(players.get(j).absX + 128,
                                                                        players.get(j).absY,
                                                                        players.get(j).heightLevel);
                                }
                        }
                }
                if (water_level <= 25 && isSunk) {
                        isSunk = false;
                        for (int j = 0; j < players.size(); j++) {
                                if (players.get(j) != null) {
                                        players.get(j).stopMovement();
                                        players.get(j).getPlayerAssistant().
                                                        movePlayer(players.get(j).absX - 128,
                                                                        players.get(j).absY,
                                                                        players.get(j).heightLevel);
                                }
                        }
                }
        }
 
        /*
         * Starts the timer for the game
         */
 
        public void startGameTimer() {
                CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                                if (game_time == 0) {
                                        container.stop();
                                } else {
                                        game_time--;
                                        waiting_room.messageWaiting("The trawler will return in "+game_time+ (game_time == 1 ? " minute" : " minutes")+"!");
                                }
                        }
 
                        @Override
                        public void stop() {
                                waiting_room.reset();
                                waiting_room.setActive(true);
                                in_progress = false;
                                players.clear();
                                waiting_room.startWaiting();
                        }
                }, 80);
        }
 
        /*
         * Slightly increases chance of higher level fish with levels
         */
 
        public int chanceByLevel(Player p, int fish) {
                switch (fish) {
                case 381:
                        if (p.playerLevel[p.playerFishing] >= 81
                                        && p.playerLevel[p.playerFishing] < 90) {
                                return 5;
                        } else if (p.playerLevel[p.playerFishing] >= 90
                                        && p.playerLevel[p.playerFishing] < 99) {
                                return 9;
                        } else if (p.playerLevel[p.playerFishing] == 99) {
                                return 13;
                        }
                        return 0;
                case 395:
                        if (p.playerLevel[p.playerFishing] >= 79
                                        && p.playerLevel[p.playerFishing] < 85) {
                                return 8;
                        } else if (p.playerLevel[p.playerFishing] >= 85
                                        && p.playerLevel[p.playerFishing] < 95) {
                                return 13;
                        } else if (p.playerLevel[p.playerFishing] >= 95) {
                                return 17;
                        }
                        return 0;
                }
                return 0;
        }
 
        /*
         * Loss teleporting
         */
        public void movePlayersLoss() {
                for (Client p : players) {
                        if (p != null) {
                                p.getPlayerAssistant().movePlayer(1952, 4826, 0);
                        }
                }
                in_progress = false;
                game_time = 0;
                players.clear();
                waiting_room.startWaiting();
        }
 
        /*
         * Win teleporting
         */
 
        public void movePlayerWin(final ArrayList<Client> pl) {
                for (Client p : pl) {
                        if (p != null) {
                                p.getPlayerAssistant().removeAllSidebars();
                                p.getPlayerAssistant().sendMapState(2);
                                p.getPlayerAssistant().showInterface(3281);
                                p.getPlayerAssistant().sendFrame20(75, 12);
                                p.getPlayerAssistant().movePlayer(2666, 3161, 0);
                                //p.getPlayerAssistant().movePlayer(2804, 3421, 0);
                        }
                }
                CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                                container.stop();
                        }
 
                        @Override
                        public void stop() {
                                for (Client p : pl) {
                                        if (p != null) {
                                                p.getPlayerAssistant().sendSidebars();
                                                p.getPlayerAssistant().sendMapState(0);
                                                p.getPlayerAssistant().closeAllWindows();
                                        }
                                }
                                in_progress = false;
                                game_time = 0;
                                players.clear();
                                waiting_room.startWaiting();
                        }
                }, 25);
        }
 
        /*
         * Adds the rewards to each player
         */
 
        public ArrayList<GameItem> playerReward(Client p) {
                ArrayList<GameItem> toReturn = new ArrayList<GameItem>();
                boolean turtles = true;
                boolean mantas = true;
                boolean lobsters = true;
                boolean swordfish = true;
                int turt = 0;
                int manta = 0;
                int lobs = 0;
                int swordFish = 0;
                int junk = 0;
                int done = 0;
                while (done != fish_caught) {
                        done++;
                        int random = random_gen.nextInt(100);
                        if (random >= 85 - chanceByLevel(p, 381)) {
                                if (mantas) {
                                        manta++;
                                }
                        } else if (random >= 70 - chanceByLevel(p, 381)) {
                                if (turtles) {
                                        turt++;
                                }
                        } else if (random >= 40) {
                                if (swordfish) {
                                        swordFish++;
                                }
                        } else if (random >= 5) {
                                if (lobsters) {
                                        lobs++;
                                }
                        } else {
                                junk++;
                        }
                }
                int xpToAdd = 0;
                if (manta > 0) {
                        toReturn.add(new GameItem(389, manta));
                        if (p.playerLevel[p.playerFishing] >= 81) {
                                xpToAdd += (manta * 46 * SkillHandler.FISHING_EXPERIENCE);
                        }
                }
                if (turt > 0) {
                        toReturn.add(new GameItem(395, turt));
                        if (p.playerLevel[p.playerFishing] >= 79) {
                                xpToAdd += (manta * 38 * SkillHandler.FISHING_EXPERIENCE);
                        }
                }
                if (lobs > 0) {
                        toReturn.add(new GameItem(377, lobs));
                        if (p.playerLevel[p.playerFishing] >= 40) {
                                xpToAdd += (manta * 90 * SkillHandler.FISHING_EXPERIENCE);
                        }
                }
                if (swordFish > 0) {
                        toReturn.add(new GameItem(371, swordFish));
                        if (p.playerLevel[p.playerFishing] >= 50) {
                                xpToAdd += (manta * 100 * SkillHandler.FISHING_EXPERIENCE);
                        }
                }
                if (junk > 0)
                        toReturn.add(new GameItem(685, junk));
                p.getPlayerAssistant().addSkillXP(xpToAdd, p.playerFishing);
                return toReturn;
        }
 
        /*
         * Randomly returns true, players craft level increases chance of returning
         * true
         */
 
        public boolean skillCheck(int level, int levelRequired, int itemBonus) {
                double chance = 0.0;
                double baseChance = Math.pow(10d - levelRequired / 10d, 2d) / 2d;
                chance = baseChance + ((level - levelRequired) / 2d)
                                + (itemBonus / 10d);
                return chance >= (new Random().nextDouble() * 100.0);
        }
 
        @Override
        public WaitingRoom getWaitingRoom() {
                return waiting_room;
        }
 
        @Override
        public String getWaitingRoomMessage() {
                return null;
        }
       
        public boolean inProgress() {
                return in_progress;
        }
       
        public int getGameTime() {
                return game_time;
        }
       
        public void resetRewardsInterface(Client c) {
                for(int j = 0; j < 45; j++) {
                        c.getPlayerAssistant().sendFrame34(-1, j, 4640, -1);
                }
        }
        public void showReward(Client c) {
                resetRewardsInterface(c);
                c.inFishingTrawlerRewardsInterface = true;
                c.getPlayerAssistant().showInterface(4564);
                for(int j = 0; j < c.fishingTrawlerReward.size(); j++) {
                        c.getPlayerAssistant().sendFrame34(c.fishingTrawlerReward.get(j).id, j, 4640, c.fishingTrawlerReward.get(j).amount);
                }
        }
       
        public void updateRewardSlot(Client c, int slot) {
                c.inFishingTrawlerRewardsInterface = true;
                c.getPlayerAssistant().sendFrame34(c.fishingTrawlerReward.get(slot).id, slot, 4640, c.fishingTrawlerReward.get(slot).amount);
                if(slot != 4 && c.fishingTrawlerReward.size() == 5) {
                        c.getPlayerAssistant().sendFrame34(c.fishingTrawlerReward.get(4).id, 4, 4640, c.fishingTrawlerReward.get(4).amount);
                }
        }
       
        public int getRewardSlot(int j) {
                if(j < 4) {
                        return j;
                } else {
                        return j+1;
                }
        }
       
 
}