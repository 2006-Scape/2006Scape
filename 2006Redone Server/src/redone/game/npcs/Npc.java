package redone.game.npcs;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;
import redone.util.Stream;

public class Npc {

	public int npcId;
	public int npcType;
	public int absX, absY;
	public int heightLevel;
	public static int lastX, lastY;
	public int makeX, makeY, maxHit, defence, attack, moveX, moveY, direction, walkingType, hitsToHeal;
	public int spawnX, spawnY;
	public int viewX, viewY;
	public boolean summoner;
	public int summonedBy, size;
	public int focusPointX, focusPointY, masterId;
	public boolean turnUpdateRequired;

	/**
	 * attackType: 0 = melee, 1 = range, 2 = mage
	 */
	public int attackType, projectileId, endGfx, spawnedBy, hitDelayTimer, HP,
			MaxHP, hitDiff, animNumber, actionTimer, enemyX, enemyY,
			combatLevel;
	public boolean applyDead, isDead, needRespawn, respawns, aggressive;
	public boolean walkingHome, underAttack;
	public int freezeTimer, attackTimer, killerId, killedBy, oldIndex,
			underAttackBy;
	public long lastDamageTaken;
	public boolean randomWalk;
	public boolean dirUpdateRequired;
	public boolean animUpdateRequired;
	public boolean hitUpdateRequired;
	public boolean updateRequired;
	public boolean forcedChatRequired;
	public boolean faceToUpdateRequired;
	public int firstAttacker;
	public String forcedText;
	public boolean transformUpdateRequired = false, isTransformed = false;
	public int transformId;
	
	public Npc(int _npcId, int _npcType) {
		npcId = _npcId;
		npcType = _npcType;
		direction = -1;
		isDead = false;
		applyDead = false;
		actionTimer = 0;
		randomWalk = true;
	}
	
	public void requestTransform(int id) {
    	transformId = id;
    	transformUpdateRequired = true;
    	updateRequired = true;
	}
	
	public void requestTransformTime(Client player, int itemId, int animation, final int currentId, final int newId, int transformTime) {
		if (!player.getItemAssistant().playerHasItem(itemId)) {
			player.getActionSender().sendMessage("You need " + ItemAssistant.getItemName(itemId).toLowerCase() + " to do that.");
			return;
		}
		if (NpcHandler.npcs[currentId].isTransformed == true) {
			player.getActionSender().sendMessage("This npc is already transformed.");
			return;
		}
		if (animation > 0) {
			player.startAnimation(animation);
		}
		NpcHandler.npcs[currentId].isTransformed = true;
		requestTransform(newId);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				requestTransform(currentId);
				container.stop();
			}

			@Override
			public void stop() {
				NpcHandler.npcs[currentId].isTransformed = false;
			}
		}, transformTime);
	}
	
	public void appendTransformUpdate(Stream str) {
    	str.writeWordBigEndianA(transformId);
	}
	

	public void updateNPCMovement(Stream str) {
		if (direction == -1) {

			if (updateRequired) {

				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else {

			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, Misc.xlateDirectionToClient[direction]);
			if (updateRequired) {
				str.writeBits(1, 1);
			} else {
				str.writeBits(1, 0);
			}
		}
	}

	/**
	 * Text update
	 **/

	public void forceChat(String text) {
		forcedText = text;
		forcedChatRequired = true;
		updateRequired = true;
	}


	/**
	 * Graphics
	 **/

	public int mask80var1 = 0;
	public int mask80var2 = 0;
	protected boolean mask80update = false;

	public void appendMask80Update(Stream str) {
		str.writeWord(mask80var1);
		str.writeDWord(mask80var2);
	}

	public void gfx100(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 6553600;
		mask80update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 65536;
		mask80update = true;
		updateRequired = true;
	}

	public void appendAnimUpdate(Stream str) {
		str.writeWordBigEndian(animNumber);
		str.writeByte(1);
	}
	
	public int startAnimation(int anim, int npcId) {
		return animNumber;
	}

	/**
	 * 
	 Face
	 **/

	public int FocusPointX = -1, FocusPointY = -1;
	public int face = 0;

	private void appendSetFocusDestination(Stream str) {
		str.writeWordBigEndian(FocusPointX);
		str.writeWordBigEndian(FocusPointY);
	}

	public void turnNpc(int i, int j) {
		FocusPointX = 2 * i + 1;
		FocusPointY = 2 * j + 1;
		updateRequired = true;
		turnUpdateRequired = true;
	}

	public int getNextWalkingDirection2() {
		int dir;
		dir = Misc.direction(absX, absY, absX + moveX, absY + moveY);
		dir >>= 1;
		absX += moveX;
		absY += moveY;
		return dir;
	}

	public void getRandomAndHomeNPCWalking(int i) {
		direction = -1;
		if (NpcHandler.npcs[i].freezeTimer == 0) {
			direction = getNextWalkingDirection2();
		}
	}

	public void appendFaceEntity(Stream str) {
		str.writeWord(face);
	}

	public void facePlayer(int player) {
		face = player + 32768;
		dirUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceToUpdate(Stream str) {
		str.writeWordBigEndian(viewX);
		str.writeWordBigEndian(viewY);
	}

	public void appendNPCUpdateBlock(Stream str) {
		if (!updateRequired) {
			return;
		}
		int updateMask = 0;
		if (animUpdateRequired) {
			updateMask |= 0x10;
		}
		if (hitUpdateRequired2) {
			updateMask |= 8;
		}
		if (mask80update) {
			updateMask |= 0x80;
		}
		if (dirUpdateRequired) {
			updateMask |= 0x20;
		}
		if (forcedChatRequired) {
			updateMask |= 1;
		}
		if (hitUpdateRequired) {
			updateMask |= 0x40;
		}
		if (transformUpdateRequired) {
			updateMask |= 2;
		}
		if (turnUpdateRequired) {
			updateMask |= 4;
		}

		str.writeByte(updateMask);

		if (animUpdateRequired) {
			appendAnimUpdate(str);
		}
		if (hitUpdateRequired2) {
			appendHitUpdate2(str);
		}
		if (mask80update) {
			appendMask80Update(str);
		}
		if (dirUpdateRequired) {
			appendFaceEntity(str);
		}
		if (forcedChatRequired) {
			str.writeString(forcedText);
		}
		if (hitUpdateRequired) {
			appendHitUpdate(str);
		}
		if (transformUpdateRequired) {	
			appendTransformUpdate(str);
		}
		if (turnUpdateRequired) {
			appendSetFocusDestination(str);
		}

	}

	public void clearUpdateFlags() {
		updateRequired = false;
		forcedChatRequired = false;
		hitUpdateRequired = false;
		hitUpdateRequired2 = false;
		animUpdateRequired = false;
		dirUpdateRequired = false;
		transformUpdateRequired = false;
		mask80update = false;
		forcedText = null;
		moveX = 0;
		moveY = 0;
		direction = -1;
		focusPointX = -1;
		focusPointY = -1;
		turnUpdateRequired = false;
	}

	public int getNextWalkingDirection() {
		int nextX = absX + moveX;
		int nextY = absY + moveY;
		int dir;
		dir = Misc.direction(absX, absY, absX + moveX, absY + moveY);
		for (Npc npc : NpcHandler.npcs) {
			if (npc == null) {
				continue;
			}
			if (npc.absX == nextX && npc.absY == nextY
					&& npc.heightLevel == heightLevel) {
				return -1;
			}
		}
		for (Player p : PlayerHandler.players) {
			if (p == null) {
				continue;
			}
			if (p.absX == nextX && p.absY == nextY
					&& p.heightLevel == heightLevel) {
				return -1;
			}
		}
		if (dir == -1) {
			return -1;
		}
		dir >>= 1;
		absX += moveX;
		absY += moveY;
		return dir;
	}

	public void getNextNPCMovement(int i) {
		direction = -1;
		if (NpcHandler.npcs[i].freezeTimer == 0) {
			direction = getNextWalkingDirection();
		}
	}

	public void appendHitUpdate(Stream str) {
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteC(hitDiff);
		if (hitDiff > 0) {
			str.writeByteS(1);
		} else {
			str.writeByteS(0);
		}
		str.writeByteS(HP);
		str.writeByteC(MaxHP);
	}

	public int hitDiff2 = 0;
	public boolean hitUpdateRequired2 = false;

	public void appendHitUpdate2(Stream str) {
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteA(hitDiff2);
		if (hitDiff2 > 0) {
			str.writeByteC(1);
		} else {
			str.writeByteC(0);
		}
		str.writeByteA(HP);
		str.writeByte(MaxHP);
	}

	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;
		}
		updateRequired = true;
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public void setAbsX(int absX) {
		Npc.lastX = this.absX;
		this.absX = absX;
	}

	public void setAbsY(int absY) {
		Npc.lastY = this.absY;
		this.absY = absY;
	}

	public void deleteNPC(Npc npc) {
		setAbsX(0);
		setAbsY(0);
		npc = null;
	}
	
	public boolean inLesserNpc() {
		return (absX >= 3108 && absX <= 3112 && absY >= 3156 && absY <= 3158 && heightLevel == 2);
	}

	public boolean inMulti() {
		if (absX >= 3136
				&& absX <= 3327
				&& absY >= 3519
				&& absY <= 3607
				|| absX >= 2625
				&& absX <= 2685
				&& absY >= 2550
				&& absY <= 2620 // Pest
								// Control
				|| absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839
				|| absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967
				|| absX >= 2992
				&& absX <= 3007
				&& absY >= 3912
				&& absY <= 3967
				|| absX >= 2946
				&& absX <= 2959
				&& absY >= 3816
				&& absY <= 3831
				|| absX >= 3008
				&& absX <= 3199
				&& absY >= 3856
				&& absY <= 3903
				|| absX >= 2667
				&& absX <= 2685
				&& absY >= 3712
				&& absY <= 3730 // rock
								// crabs
				|| absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711
				|| absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647
				|| absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619
				|| absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117
				|| absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630
				|| absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464
				|| absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711) {
			return true;
		}
		return false;
	}

	public boolean inWild() {// beg, end, beg, end, beg, end, beg, end
		if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966
				|| absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
			return true;
		}
		return false;
	}
}
