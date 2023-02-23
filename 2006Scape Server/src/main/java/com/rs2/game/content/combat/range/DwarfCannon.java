package com.rs2.game.content.combat.range;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.objects.Objects;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;

import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.StaticObjectList;

/**
 * Cannon
 * @author Andrew (Mr Extremez)
 */

public class DwarfCannon {

	public DwarfCannon(Player player2) {
		this.player = player2;
	}

	private Player player;

	public final int[] ITEM_PARTS = {StaticItemList.CANNON_BASE, StaticItemList.CANNON_STAND, StaticItemList.CANNON_BARRELS, StaticItemList.CANNON_FURNACE};
	
	private final int[] OBJECT_PARTS = {StaticObjectList.CANNON_BASE, StaticObjectList.CANNON_STAND, StaticObjectList.CANNON_BASE_9, StaticObjectList.DWARF_MULTICANNON};

	public boolean settingUp = false;
	
	private int setUpStage = 0;
	
	private int maxBalls = 30;
	
	public int myBalls = 0;
	
	private boolean rotating = false;
	
	private int rotation = 0;
	
	private int maxHit = 30;
	
	private final int maxDistance = 20;
	
	private int totalRotations = 0;
	
	private boolean justClicked = false;

	public static void makeBall(Player player)
	{
		//An interface could be added instead of making all.
		if (!player.getItemAssistant().playerHasItem(StaticItemList.STEEL_BAR) || !player.getItemAssistant().playerHasItem(StaticItemList.AMMO_MOULD))
		{
			player.getPacketSender().sendMessage("You need an ammo mould and steel bars to make cannonballs.");
			return;
		}
		if (!player.isSmithing)
		{
			player.isSmithing = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (player.isWoodcutting || player.isCrafting || player.isFletching || player.isMoving || player.isMining || player.isBusy || player.isShopping || player.isFiremaking || player.isSpinning || player.isPotionMaking || player.playerIsFishing || player.isBanking || player.isSmelting || player.isTeleporting || player.isHarvesting || player.playerIsCooking || player.isPotCrafting ||!player.isSmithing || !player.getItemAssistant().playerHasItem(StaticItemList.STEEL_BAR) || !player.getItemAssistant().playerHasItem(StaticItemList.AMMO_MOULD))
					{
						container.stop();
						return;
					}
					else
					{
						player.startAnimation(899);
						player.getItemAssistant().deleteItem(StaticItemList.STEEL_BAR, 1);
						player.getItemAssistant().addItem(2, 4);
						player.getPacketSender().sendMessage("You make some cannonballs.");
						player.getPlayerAssistant().addSkillXP(26, Constants.SMITHING);
						player.getPacketSender().sendSound(352, 100, 0);
					}
				}

				@Override
				public void stop() {
					player.isSmithing = false;
				}
			}, 3);
		}

	}
	public void placeCannon() {
		if (settingUp) {
			return;
		}
		if (noSetUpArea()) {
			player.getPacketSender().sendMessage("You are not allowed to set up a cannon here!");
			return;
		}
		if (hasCannon()) {
			player.getPacketSender().sendMessage("You already have a cannon placed!");
			return;
		}
		if (nearCannon()) {
			player.getPacketSender().sendMessage("You must be farther away from an existing cannon to set a new one up!");
			return;
		}
		if (!canSetUp()) {
			player.getPacketSender().sendMessage("You need all the parts of the cannon to set a cannon up.");
			return;
		}
		for (int i = 0; i < 50; i++) {
			if (GameEngine.cannonsX[i] == 0 && GameEngine.cannonsY[i] == 0) {
				GameEngine.cannonsX[i] = player.absX;
				GameEngine.cannonsY[i] = player.absY;
				break;
			}
		}
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
		@Override
    		public void execute(CycleEventContainer container) {
				if (setUpStage >= 4) {
					container.stop();
					setUpStage = 0;
					player.CannonSetupStage = setUpStage;
					settingUp = false;
					return;
				}
				if (!canSetUp()) {
					player.getPacketSender().sendMessage("You need all the parts of the cannon to set a cannon up.");
					container.stop();
					return;
				}
				settingUp = true;
				player.startAnimation(827);
				player.turnPlayerTo(player.absX, player.absY);
				player.cannonX = player.absX;
				player.cannonY = player.absY;
				placeObject(OBJECT_PARTS[setUpStage], player.absX, player.absY, true);
				player.getItemAssistant().deleteItem(ITEM_PARTS[setUpStage], 1);
				setUpStage ++;
				player.CannonSetupStage = setUpStage;
			}
			@Override
			public void stop() {
				/**
				 * Balls
				 */
				int cballs = getBalls();
				int amount = cballs - myBalls;
				player.getItemAssistant().deleteItem(StaticItemList.CANNONBALL, player.getItemAssistant().getItemSlot(StaticItemList.CANNONBALL), amount);
				myBalls = cballs;
			}
		}, 2);
	}
		
		public void loginCheck() {
			if (player.lostCannon) {
				player.getPacketSender().sendMessage("@red@You can collect your cannon from Nulodion.");
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
			return (player.CannonSetupStage != 0 || (player.cannonX > 0 && player.cannonY > 0));
		}
		
		private boolean myCannon(int x, int y) {
			return (player.cannonX == x) && (player.cannonY == y);
		}
		
		public int getBalls() {
			int cannonBalls = player.getItemAssistant().getItemAmount(StaticItemList.CANNONBALL);
			if (cannonBalls >= maxBalls) {
				return maxBalls;
			}
			return cannonBalls;
		}
		
		public boolean nearCannon() {
			for(int i = 0; i < GameEngine.cannonsX.length; i++) {
				if ((player.absX >= GameEngine.cannonsX[i] - 1) && (player.absX <= GameEngine.cannonsX[i] + 1) && (player.absY >= GameEngine.cannonsY[i] - 2) && (player.absY <= GameEngine.cannonsY[i] + 1)) {
						return true;
					}
				}
			return false;
		}
		
		public void loadCannons() {
			for(int i = 0; i < GameEngine.cannonsX.length; i++) {
				if (GameEngine.cannonsX[i] != 0) {
					player.getPacketSender().checkObjectSpawn(6, GameEngine.cannonsX[i], GameEngine.cannonsY[i], 0, 10);
				}
			}
		}
		
		public void loadCannon(int x, int y) {
			int cballs = getBalls();
			if (!myCannon(x, y)) {
				player.getPacketSender().sendMessage("You can't load somebody else's cannon!");
				return;
			}
			if (myBalls <= 29) {
				int amount = cballs - myBalls;
				player.getItemAssistant().deleteItem(StaticItemList.CANNONBALL, player.getItemAssistant().getItemSlot(StaticItemList.CANNONBALL), amount);
				myBalls = cballs;
				if (player.getItemAssistant().playerHasItem(StaticItemList.CANNONBALL)) {
					player.getPacketSender().sendMessage(amount > 1 ? "You load the cannon with " + amount + " cannonballs." : "You load the cannon with 1 cannonball.");
				} else {
					player.getPacketSender().sendMessage("You have no cannonballs to load into the cannon.");
				}
			}
		}
		
		public void clickCannon(int x, int y) {
			if (!myCannon(x, y)) {
				player.getPacketSender().sendMessage("You can't fire somebody else's cannon!");
				return;
			}
			if (myBalls == 0) {
				player.getPacketSender().sendMessage("Your cannon has run out of cannonballs.");
				return;
			}
			if (myBalls >= 1 && rotating == false) {
				shoot();
			} else if (myBalls >= 1 && rotating) {
				player.getPacketSender().sendMessage("Your cannon is already shooting.");
			}
		}
			
		public void handleDisconnect() {
			removeObject(player.cannonX, player.cannonY);
			for(int i = 0; i < GameEngine.cannonsX.length; i++) {
				if (GameEngine.cannonsX[i] == player.cannonX && GameEngine.cannonsY[i] == player.cannonY) {
					GameEngine.cannonsX[i] = 0;
					GameEngine.cannonsY[i] = 0;
					GameEngine.cannonsO[i] = null;
				}
			}
		}
		
		public void handleDeath() {
			if (hasCannon()) {
				player.lostCannon = true;
				removeObject(player.cannonX, player.cannonY);
				for(int i = 0; i < GameEngine.cannonsX.length; i++) {
					if (GameEngine.cannonsX[i] == player.cannonX && GameEngine.cannonsY[i] == player.cannonY) {
						GameEngine.cannonsX[i] = 0;
						GameEngine.cannonsY[i] = 0;
						GameEngine.cannonsO[i] = null;
					}
				}
				player.cannonX = 0;
				player.cannonY = 0;
			}
		}
		
		public void shoot() {
			if (justClicked) {
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
					if (rotating) {
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
			for (int i = 0; i < GameEngine.cannonsX.length; i++) {
				if (GameEngine.cannonsX[i] == x && GameEngine.cannonsY[i] == y) {
					GameEngine.cannonsX[i] = 0;
					GameEngine.cannonsY[i] = 0;
					break;
				}
			}
		}

		public void pickup(int x, int y) {
			if (!myCannon(x, y)) {
				player.getPacketSender().sendMessage("You can't pick up somebody else's cannon!");
				return;
			}
			if (rotating) {
				rotating = false;
			}
			if (player.getItemAssistant().freeSlots() > 3) {
				player.startAnimation(827);
				player.getPacketSender().sendMessage("You pick up the cannon. It's really heavy.");
				removeCannon(player.cannonX, player.cannonY);
				player.getItemAssistant().addItem(ITEM_PARTS[0], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[1], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[2], 1);
				player.getItemAssistant().addItem(ITEM_PARTS[3], 1);
			} else {
				player.getPacketSender().sendMessage("You don't have enough free inventory slots to do that.");
			}
			if (myBalls > 0) {
				player.getItemAssistant().addItem(StaticItemList.CANNONBALL, myBalls);
				myBalls = 0;
			}
			removeObject(player.cannonX, player.cannonY);
			player.cannonX = 0;
			player.cannonY = 0;
			player.cannonX = 0;
			player.cannonY = 0;
		}

		public void placeObject(int id, int x, int y, boolean add) {
			GameEngine.objectHandler.placeObject(new Objects(id, x, y, 0, 516, 10, 0));
			if (add)
			Region.addObject(id, x, y, 0, 10, 516, true);
		}

		public void removeObject(int x, int y) {
			placeObject(-1, x, y, false);
		}
		
		public boolean noSetUpArea() {
			return Boundary.isIn(player, Boundary.BANK_AREA) || player.inFightCaves();
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
				if (!Boundary.isIn(player, Boundary.MULTI)) {
					if (target.underAttackBy > 0 && target.underAttackBy != player.playerId) {
						return;
					}
					if (player.underAttackBy2 > 0 && player.underAttackBy2 != target.npcId) {
						return;
					}
				}
				cannonProjectile(target);
				target.hitDiff2 = damage;
				target.HP -= damage;
				player.globalDamageDealt += damage;
				target.killerId = player.playerId;
				target.killedBy = player.playerId;
				target.facePlayer(player);
				target.hitUpdateRequired2 = true;
				target.updateRequired = true;
				myBalls -= 1;
				player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE, Constants.RANGED);
			}
		}
		
		
		private Npc targetNpc() {
			for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
				Npc npc = NpcHandler.npcs[i];
				if (npc == null || npc.heightLevel != player.heightLevel || !canAttackSlayer(i)) {
					continue;
				}
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

		public boolean canAttackSlayer(int i){
			return player.playerLevel[Constants.SLAYER] >= player.getSlayer().getRequiredLevel(NpcHandler.npcs[i].npcType);
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