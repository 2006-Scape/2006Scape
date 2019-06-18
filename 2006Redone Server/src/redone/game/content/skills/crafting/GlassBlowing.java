package redone.game.content.skills.crafting;

import redone.Constants;
import redone.event.*;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Arrowz flame
 */
public class GlassBlowing {
    
	private Client c;

	public GlassBlowing(Client c) {
		this.c = c;
	}
        
        public int[][] data = {
            {44210, 229, 33, 35},
            {48108, 567, 46 ,53},
            {48112, 1919, 1, 18},
            {48116, 4527, 4, 19},
            {48120, 4522, 12, 25},
            {24059, 6667, 42, 42},
            {48124, 4542, 49, 55}
        };
        
        private boolean started;
                
        public boolean getStarted() {
            return started;
        }
        
        public void setStarted(boolean started) {
            this.started = started;
        }
        
        public void showInterface() {
            if(!started) {
                c.getPlayerAssistant().showInterface(11462);
            }
        }
        
        
        public void ItemOnItem(int id, int id2) {
            if(id == 1785 && id2 == 1775) {
                showInterface();
            } else if(id2 == 1785 && id == 1775) {
                showInterface();
            }
        }
        
        public void handleActionButtin(int buttonId) {
            for(int i = 0; i < data.length; i++) {
                if(data[i][0] == buttonId) {
                   makeItem(data[i][1], 1, i);
                   c.getPlayerAssistant().closeAllWindows();
                   break;
                }
                               
                if(data[i][0] == buttonId+1) {
                   makeItem(data[i][1], 5, i);
                   c.getPlayerAssistant().closeAllWindows();
                   break;
                }
                                
                if(data[i][0] == buttonId+2) {
                   makeItem(data[i][1], 10, i);
                   c.getPlayerAssistant().closeAllWindows();
                   break;
                }
                                
                if(data[i][0] == buttonId+3) {
                   makeItem(data[i][1], 28, i);
                   c.getPlayerAssistant().closeAllWindows();
                   break;
                }
            }
           
        }
        
        public void makeItem(final int id, final int amount, final int slot) {
            if(c.playerLevel[Constants.CRAFTING] < data[slot][2]) {
				c.getActionSender().sendMessage("You need a crafting level of "+data[slot][2]+" make a "+ItemAssistant.getItemName(id)+"");
                return;
            }
            if(!started) {
                
                started = true;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                int made = 0;
                @Override
                public void execute(CycleEventContainer container) {
                    if(!started) {
                        container.stop();
                    }
                   if(c.getItemAssistant().playerHasItem(1775)) {
                       if(c.getItemAssistant().playerHasItem(1785)) {
                           c.getItemAssistant().deleteItem(1775, 1);
                           c.getItemAssistant().addItem(id, 1);
                           c.getPlayerAssistant().addSkillXP(data[slot][3], Constants.CRAFTING);
                           c.startAnimation(884);
                           made++;
                           if(made >= amount) {
                               container.stop();
                           }
                       } else {
                           container.stop();
                       }
                   } else {
                       container.stop();
                   }
                }

                public void stop() {
                    started = false;
                }

                },2);
            }
        }
}
