package com.rebotted.game.content.minigames.trawler;
 
import java.util.ArrayList;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Location;
import com.rebotted.game.players.Player;
 
public abstract class WaitingRoom {
       
        public int minimumPlayers;
        public ArrayList<Player> waiting = new ArrayList<Player>();
        private int wait_time;
        private int minutes_remaining;
        private boolean active;
       
        public abstract Location getLocation();
        public abstract boolean startGame();
        public abstract void onStart();
        public abstract void onLeave(Player player);
        public abstract void onJoin(Player p);
        public abstract void onTimeChange();
        public abstract boolean canStart();
       
        public WaitingRoom(int minutes, int minimum) {
                this.wait_time = minutes;
                this.minimumPlayers = minimum;
                this.minutes_remaining = wait_time;
                if(!firstStarted){
                        startWaiting();
                } else {
                        return;
                }
        }
       
        public void join(Player player) {
                if(!waiting.contains(player)) {
                        onJoin(player);
                        waiting.add(player);
                }
        }
       
        public void leave(Player player) {
                if(waiting.contains(player)) {
                        onLeave(player);
                        waiting.remove(player);
                }
        }
       
        public void reset() {
                minutes_remaining = wait_time;
                //startWaiting();
        }
        public static boolean firstStarted = false;
       
        public void startWaiting() {
                if(isActive()) {
                        return;
                }
                setActive(true);
                CycleEventHandler.getSingleton().addEvent(100, this, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                                firstStarted = true;
                                if(minutes_remaining != 0) {
                                        minutes_remaining--;
                                        onTimeChange();
                                } else {
                                        if(startGame()) {
                                                onStart();
                                                reset();
                                                setActive(false);
                                                container.stop();
                                        } else {
                                                messageWaiting("A minimum of "+minimumPlayers+" players are needed to start this minigame!");
                                                reset();
                                                startWaiting();
                                        }
                                }
                        }
 
                        @Override
                        public void stop() {
 
                        }
                }, 80);
        }
       
       
        public void messageWaiting(String message) {
                for(Player p : waiting) {
                        if(p != null) {
                                p.getPacketSender().sendMessage(message);
                        }
                }
        }
       
        public boolean isActive() {
                return active;
        }
       
        public void setActive(boolean active) {
                this.active = active;
        }
       
        public int getTimeRemaining() {
                return minutes_remaining;
        }
       
       
}