package com.rs2.game.content.minigames.trawler;
 
import com.rs2.game.players.Player;
import com.rs2.world.Boundary;
 
public class TrawlerWaitingRoom extends WaitingRoom {
 
        private Trawler trawler;
        private Boundary boat = new Boundary(2668,2674,3165,3185);
        //private Location boat = new Location(2808, 2811,3415,3425);
       
       
        public TrawlerWaitingRoom(Trawler trawler) {
                super(2, 1);
                this.trawler = trawler;
        }
 
        @Override
        public boolean startGame() {
                if(trawler.inProgress()) {
                        return false;
                }
                if(waiting.size() < minimumPlayers) {
                        return false;
                }
                return true;
        }
 
        @Override
        public void onStart() {
                trawler.players.clear();
       
                for(Player p : waiting) {
                        trawler.players.add(p);
                }
               
                trawler.start();
                waiting.clear();
        }
 
        @Override
        public void onLeave(Player p) {
                p.asClient().getPlayerAssistant().movePlayer(2676, 3170, 0);
        }
 
        @Override
        public void onJoin(Player p) {
                p.getPlayerAssistant().movePlayer(2672, 3170, 1);
                if(!isActive()) {
                        p.getPacketSender().sendMessage(trawler.getGameTime() == 0 ? "The trawler will be returning in less than a minute!" : "The trawler will return in "+trawler.getGameTime() + (trawler.getGameTime() == 1 ? " minute" : " minutes")+"!");
                } else {
                        p.getPacketSender().sendMessage(getTimeRemaining() == 0 ? "The trawler will be leaving in less than a minute!" : "The trawler will leave in "+ getTimeRemaining() + (getTimeRemaining() == 1 ? " minute" : " minutes")+"!");
                }
        }
 
        public Boundary getLocation() {
                return boat;
        }
 
        @Override
        public void onTimeChange() {
                for(Player p : waiting) {
                        if(!isActive()) {
                                p.getPacketSender().sendMessage(trawler.getGameTime() == 0 ? "The trawler will be returning in less than a minute!" : "The trawler will return in "+trawler.getGameTime() + (trawler.getGameTime() == 1 ? " minute" : " minutes")+"!");
                        } else {
                                p.getPacketSender().sendMessage(getTimeRemaining() == 0 ? "The trawler will be leaving in less than a minute!" : "The trawler will leave in "+ getTimeRemaining() + (getTimeRemaining() == 1 ? " minute" : " minutes")+"!");
                        }
                }
        }
 
        @Override
        public boolean canStart() {
                if(trawler.inProgress()) {
                        return false;
                }
                if(waiting.size() < minimumPlayers) {
                        return false;
                }
                return true;
        }
 
}