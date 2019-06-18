package redone.game.content.combat.range;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

/**
 * Cannon
 * @author Andrew
 */

public class DwarfCannon {

	public DwarfCannon(Client player) {
		this.player = player;
	}

	private Client player;

	public final int[] ITEM_PARTS = {6, 8, 10, 12};
	
	private final int[] OBJECT_PARTS = {7, 8, 9, 6};
	
	private final int ballsID = 2;

	public boolean settingUp = false;
	
	private int setUpStage = 0;
	
	private int maxBalls = 30;
	
	public int myBalls = 0;
	
	private boolean rotating = false;
	
	private int rotation = 0;
	
	private int maxHit = 30;
	
	private final int maxDistance = 20;
	
	private final int expRate = 50;
	
	private int totalRotations = 0;
	
	private boolean justClicked = false;

	public void placeCannon() {
		if (settingUp == true) {
			return;
		}
		if (noSetUpArea()) {
			player.getActionSender().sendMessage("You are not allowed to set up a cannon here!");
			return;
		}
		if (hasCannon()) {
			player.getActionSender().sendMessage("You already have a cannon placed!");
			return;
		}
		if (nearCannon()) {
			player.getActionSender().sendMessage("You must be farther away from an existing cannon to set a new one up!");
			return;
		}
		if (!canSetUp()) {
			player.getActionSender().sendMessage("You need all the parts of the cannon to set a cannon up.");
			return;
		}
		for (int i = 0; i < 50; i++) {
			if (Server.cannonsX[i] == 0 && Server.cannonsY[i] == 0) {
				Server.cannonsX[i] = player.absX;
				Server.cannonsY[i] = player.absY;
				break;
			}
		}
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
		@Override
    		public void execute(CycleEventContainer container) {
				if (setUpStage >= 4) {
					container.stop();
					setUpStage = 0;
					settingUp = false;
					return;
				}
				if (!canSetUp()) {
					player.getActionSender().sendMessage("You need all the parts of the cannon to set a cannon up.");
					container.stop();
					return;
				}
				settingUp = true;
				player.startAnimation(827);
				player.turnPlayerTo(player.absX, player.absY);
				player.cannonX = player.absX;
				player.cannonY = player.absY;
				placeObject(OBJECT_PARTS[setUpStage], player.absX, player.absY);
				player.getItemAssistant().deleteItem(ITEM_PARTS[setUpStage], 1);
				setUpStage ++;
			}
			@Override
			public void stop() {
				/**
				 * Balls
				 */
				int cballs = getBalls();
				int amount = cballs - myBalls;
				player.getItemAssistant().deleteItem(ballsID, player.getItemAssistant().getItemSlot(ballsID), amount);
				myBalls = cballs;
			}
		}, 2);
	}
	
		public boolean needsCannon() {
			return (player.lostCannon == true);
		}
		
		public void loginCheck() {
			if (needsCannon()) {
				player.getActionSender().sendMessage("@red@You can collect your cannon at home from Nulodion.");
			}
		}
		
		private boolean canSetUp() {
			if (setUpStage == 0) {
				if (player.getItemAssistant().playerHasItem(ITEM_PARTS[0])  && player.getItemAssistant().playerHasItem(ITEM_PARTS[1]) && player.getItemAssistant().playerHasItem(ITEM_PARTS[2]) && player.getItemAssistant().playerHasItem(ITEM_PARTS[3])) {
					return true;
				}
			} else if (setUpStage == 1) {
				if (player.getItemAssistant().playerHasItem(ITEM_PARTS[1]) && player.getItemAssistant().playerHasItem(ITEM_PARTS[2]) && player.getItemAssistant().playerHasItem(ITEM_PARTS[3])) {
					return true;
				}
			} else if (setUpStage == 2) {
				if (player.getItemAssistant().playerHasItem(ITEM_PARTS[2]) && player.getItemAssistant().playerHasItem(ITEM_PARTS[3])) {
					return true;
				}
			} else if (setUpStage == 3) {
				if (player.getItemAssistant().playerHasItem(ITEM_PARTS[3])) {
					return true;
				}
			}
			return false;
		}
		
		public boolean hasCannon() {
			return (player.cannonX > 0) && (player.cannonY > 0) || (player.cannonX > 0 && player.cannonY > 0);
		}
		
		private boolean myCannon(int x, int y) {
			return (player.cannonX == x) && (player.cannonY == y);
		}
		
		public int getBalls() {
			int cannonBalls = player.getItemAssistant().getItemAmount(ballsID);
			if (cannonBalls >= maxBalls) {
				return maxBalls;
			}
			return cannonBalls;
		}
		
		public boolean nearCannon() {
			for(int i = 0; i < Server.cannonsX.length; i++) {
				if ((player.absX >= Server.cannonsX[i] - 1) && (player.absX <= Server.cannonsX[i] + 1) && (player.absY >= Server.cannonsY[i] - 2) && (player.absY <= Server.cannonsY[i] + 1)) {
						return true;
					}
				}
			return false;
		}
		
		public void loadCannons() {
			for(int i = 0; i < Server.cannonsX.length; i++) {
				if (Server.cannonsX[i] != 0) {
					player.getActionSender().checkObjectSpawn(6, Server.cannonsX[i], Server.cannonsY[i], 0, 10);
				}
			}
		}
		
		public void loadCannon(int x, int y) {
			int cballs = getBalls();
			if (!myCannon(x, y)) {
				player.getActionSender().sendMessage("You can't load somebody else's cannon!");
				return;
			}
			if (myBalls <= 29) {
				int amount = cballs - myBalls;
				player.getItemAssistant().deleteItem(ballsID, player.getItemAssistant().getItemSlot(ballsID), amount);
				myBalls = cballs;
				if (player.getItemAssistant().playerHasItem(ballsID)) {
					player.getActionSender().sendMessage(amount > 1 ? "You load the cannon with " + amount + " cannonballs." : "You load the cannon with 1 cannonball.");
				} else {
					player.getActionSender().sendMessage("You have no cannonballs to load into the cannon.");
				}
			}
		}
		
		public void clickCannon(int x, int y) {
			if (!myCannon(x, y)) {
				player.getActionSender().sendMessage("You can't fire somebody else's cannon!");
				return;
			}
			if (myBalls == 0) {
				player.getActionSender().sendMessage("Your cannon has run out of cannonballs.");
				return;
			}
			if (myBalls >= 1 && rotating == false) {
				shoot();
			} else if (myBalls >= 1 && rotating == true) {
				player.getActionSender().sendMessage("Your cannon is already shooting.");
			}
		}
			
		public void handleDisconnect() {
			removeObject(player.cannonX, player.cannonY);
			for(int i = 0; i < Server.cannonsX.length; i++) {
				if (Server.cannonsX[i] == player.cannonX && Server.cannonsY[i] == player.cannonY) {
					Server.cannonsX[i] = 0;
					Server.cannonsY[i] = 0;
					Server.cannonsO[i] = null;
				}
			}
		}
		
		public void handleDeath() {
			if (hasCannon()) {
				player.lostCannon = true;
				removeObject(player.cannonX, player.cannonY);
				for(int i = 0; i < Server.cannonsX.length; i++) {
					if (Server.cannonsX[i] == player.cannonX && Server.cannonsY[i] == player.cannonY) {
						Server.cannonsX[i] = 0;
						Server.cannonsY[i] = 0;
						Server.cannonsO[i] = null;
					}
				}
				player.cannonX = 0;
				player.cannonY = 0;
			}
		}
		
		public void shoot() {
			if (justClicked == true) {
				return;
			}
			rotating = true;
			justClicked = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer e) {
					totalRotations += 1;
					if (totalRotations >= 2) {
						justClicked = false;
					}
					if(rotating == false) {
						e.stop();
					}
					if (myBalls < 1) {
						rotating = false;
						e.stop();
					}
					if (rotating == true) {
						rotation += 1;
						rotateCannon();
						shootNpcs();
					}
				}
				@Override
				public void stop() {
					
				}
			}, 2);
		}
		
		private void rotateCannon() {
		switch (rotation) {
			case 1: //north
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 516, 10, -1);
			break;
			case 2: //north-east
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 517, 10, -1);
			break;
			case 3: //east
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 518, 10, -1);
			break;
			case 4: //south-east
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 519, 10, -1);
			break;
			case 5: //south
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 520, 10, -1);
			break;
			case 6: //south-west
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 521, 10, -1);
			break;
			case 7: //west
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 514, 10, -1);
			break;
			case 8: //north-west
				player.getPlayerAssistant().objectAnim(player.cannonX, player.cannonY, 515, 10, -1);
				rotation = 0;
			break;
			}
		}
		
		public void removeCannon(int x, int y) {
			for (int i = 0; i < Server.cannonsX.length; i++) {
				if (Server.cannonsX[i] == x && Server.cannonsY[i] == y) {
					Server.cannonsX[i] = 0;
					Server.cannonsY[i] = 0;
					break;
				}
			}
		}
		
		public void pickup(int x, int y) {
			if (!myCannon(x, y)) {
				player.getActionSender().sendMessage("You can't pick up somebody else's cannon!");
				return;
			}
			if (rotating == true) {
				rotating = false;
			}
			if (player.getItemAssistant().freeSlots() > 3) {
				player.startAnimation(827);
				player.getActionSender().sendMessage("You pick up the cannon. It's really heavy.");
				removeCannon(player.cannonX, player.cannonY);
				player.getItemAssistant().addItem(ITEM_PARTS[0], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[1], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[2], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[3], 1);
			} else {
				player.getActionSender().sendMessage("You don't have enough free inventory slots to do that.");
			}
			if (myBalls > 0) {
				player.getItemAssistant().addItem(ballsID, myBalls);
				myBalls = 0;
			}
			removeObject(player.cannonX, player.cannonY);
			player.cannonX = 0;
			player.cannonY = 0;
			player.cannonX = 0;
			player.cannonY = 0;
		}
		
		public void placeObject(int id, int x, int y) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client a = (Client)PlayerHandler.players[j];
					 a.getActionSender().object(id, x, y, 516, 10);
				}
			}
		}
		
		public void removeObject(int x, int y) {
			placeObject(-1, x, y);
		}
		
		public boolean noSetUpArea() {
			return (player.absX >= 2024 && player.absX <= 2047 && player.absY >= 2047 && player.absY <= 4542) || player.inBank() || player.inFightCaves() || (player.absX >= 3161 && player.absX <= 3168 && player.absY >= 3486 && player.absY <= 3493);
		}
		
		private int getHit() {
			int hits = Misc.random(2);
			switch (hits) {
			case 0:
				return Misc.random(maxHit);
			case 1:
				return 15+Misc.random(maxHit-15);
			case 2:
				return 10+Misc.random(maxHit-10);
			}
			return 0;
		}
		
		public void shootNpcs() {
			int damage = getHit();
			Npc target = targetNpc();
			if (target != null) {
				if (damage > target.HP) {
					damage = target.HP;
				}
				if (player.inMulti() && target.inMulti()) {
					cannonProjectile(target);
					target.hitDiff2 = damage;
					target.HP -= damage;
					target.killerId = player.playerId;
					target.facePlayer(player.playerId);
					target.hitUpdateRequired2 = true;
					target.updateRequired = true;
					myBalls -= 1;
					player.getPlayerAssistant().addSkillXP(damage * expRate, player.playerRanged);
				}
				if (!player.inMulti()) {
					if (target.underAttackBy > 0 && target.underAttackBy != player.playerId) {
						return;
					}
					if (player.underAttackBy2 > 0 && player.underAttackBy2 != target.npcId) {
						return;
					}
					cannonProjectile(target);
					target.hitDiff2 = damage;
					target.HP -= damage;
					target.killerId = player.playerId;
					target.facePlayer(player.playerId);
					target.hitUpdateRequired2 = true;
					target.updateRequired = true;
					myBalls -= 1;
					player.getPlayerAssistant().addSkillXP(damage * expRate, player.playerRanged);
				}
			}
		}
		
		
		private Npc targetNpc() {
			for (int i = 0; i < NpcHandler.maxNPCs; i++) {
            if (NpcHandler.npcs[i] == null) {
                continue;
            }
            Npc npc = NpcHandler.npcs[i];
            int myX = player.cannonX;
            int myY = player.cannonY;
            int theirX = npc.absX;
            int theirY = npc.absY;
            if (!npc.isDead && !npc.isDead && npc.HP != 0 && npc.npcType != 1266 && npc.npcType != 1268 && inDistance(theirX, theirY)) {
                switch (rotation) {
                    case 1:
                        if (theirY > myY && theirX >= myX - 1 && theirX <= myX + 1) {
                            return npc;
                        }
                        break;
                    case 2:
                        if (theirX >= myX + 1 && theirY >= myY + 1) {
                            return npc;
                        }
                        break;
                    case 3:
                        if (theirX > myX && theirY >= myY - 1 && theirY <= myY + 1) {
                            return npc;
                        }
                        break;
                    case 4:
                        if (theirY <= myY - 1 && theirX >= myX + 1) {
                            return npc;
                        }
                        break;
                    case 5:
                        if (theirY < myY && theirX >= myX - 1 && theirX <= myX + 1) {
                            return npc;
                        }
                        break;
                    case 6:
                        if (theirX <= myX - 1 && theirY <= myY - 1) {
                            return npc;
                        }
                        break;
                    case 7:
                        if (theirX < myX && theirY >= myY - 1 && theirY <= myY + 1) {
                            return npc;
                        }
                        break;
                    case 8:
                        if (theirX <= myX - 1 && theirY >= myY + 1) {
                            return npc;
                        }
                        break;
                	}
            	}
			}
			return null;
		}
		
		public boolean inDistance(int npcX, int npcY) {
			return (npcX >= player.cannonX - maxDistance && npcX <= player.cannonX + maxDistance && npcY >= player.cannonY - maxDistance && npcY <= player.cannonY + maxDistance);
		}
		
		private void cannonProjectile(Npc n) {
			int oX = player.cannonX+getShootXPos();
			int oY = player.cannonY+getShootYPos();
			int offX = ((oX - n.absX) * -1);
			int offY = ((oY - n.absY) * -1);
        	player.getPlayerAssistant().createPlayersProjectile(oX, oY, offY, offX, 50, 60, 53, 20, 20, -player.oldNpcIndex + 1, 30);
		}
	
		public int getShootXPos() {
			switch(rotation) {
				case 1:
					return 1;
				case 2:
					return 2;
				case 3:
					return 2;
				case 4:
					return 2;
				case 5:
					return 1;
				case 6:
					return 0;
				case 7:
					return 0;
				case 8:
					return 0;
			}
			return 0;
		}
		
		public int getShootYPos() {
			switch(rotation) {
				case 1:
					return 2;
				case 2:
					return 2;
				case 3:
					return 1;
				case 4:
					return 0;
				case 5:
					return 0;
				case 6:
					return 0;
				case 7:
					return 1;
				case 8:
					return 2;
			}
			return 0;
		}
}