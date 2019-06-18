package redone.game.players;
 
public class Location {
 
        private int x1, x2, y1, y2;
       
        public Location(int x1, int x2, int y1, int y2) {
                this.x1 = x1;
                this.x2 = x2;
                this.y1 = y1;
                this.y2 = y2;
        }
       
        public boolean playerInArea(Player p) {
                if(p.inArea(x1, y1, x2, y2)) {
                        return true;
                }
                return false;
        }
       
}