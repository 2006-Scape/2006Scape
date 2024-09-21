package com.rs2.game.npcs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.AttackType;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.npcs.NpcAggressive;
import com.rs2.game.content.combat.npcs.NpcCombat;
import com.rs2.game.content.combat.npcs.NpcEmotes;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.music.sound.CombatSounds;
import com.rs2.game.content.randomevents.FreakyForester;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.randomevents.RiverTroll;
import com.rs2.game.npcs.drops.ItemDrop;
import com.rs2.game.npcs.drops.NPCDropsHandler;
import com.rs2.game.npcs.impl.Pets;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.util.NpcSpawn;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// Facetypes: 1-Walk, 2-North, 3-South, 4-East, 5-West

public class NpcHandler {

    public static int     MAX_NPCS      = 4000;
    public static int     maxListedNPCs = 4000;
    public static Npc     npcs[]        = new Npc[MAX_NPCS];
    public static NpcList NpcList[]     = new NpcList[maxListedNPCs];

    public void spawnSecondForm(Player c, final int i) {
        //	npcs[i].gfx0(1055);
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                spawnNpc2(1160, npcs[i].absX, npcs[i].absY, 0, 1, 230, 45, 500, 300, true);
                container.stop();
            }

            @Override
            public void stop() {

            }
        }, 15);
    }

    /**
     * kq respawn first form
     */
    public void spawnFirstForm(Player c, final int i) {
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                spawnNpc2(1158, npcs[i].absX, npcs[i].absY, 0, 1, 230, 45, 500, 300, true);
                container.stop();
            }

            @Override
            public void stop() {

            }
        }, 15);
    }

    public void catchRat(final int npcIndex) {
        int foundRat = -1;
        for (int i = 0; i < MAX_NPCS; i++) {
            if (npcs[i] == null || foundRat != -1) {
                continue;
            }
            if (npcs[i].npcType == 47 && !npcs[i].isDead) {
                if (goodDistance(npcs[npcIndex].absX, npcs[npcIndex].absY, npcs[i].absX, npcs[i].absY, 5)) {
                    foundRat = i;
                    continue;
                }
            }
        }
        final Client slaveOwner = (PlayerHandler.players[npcs[npcIndex].summonedBy] != null ? (Client) PlayerHandler.players[npcs[npcIndex].summonedBy] : null);
        if (foundRat == -1) {
            if (slaveOwner != null) {
                slaveOwner.getPacketSender().sendMessage("The " + getNpcListName(NpcHandler.npcs[npcIndex].npcType) + " can't seem to find any rats nearby.");
            }
        } else {
            npcs[npcIndex].chasingRat = foundRat;
            boolean beatChance = (Misc.random(2) == 1 ? true : false);
            CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                @Override
                public void execute(CycleEventContainer container) {
                    if (npcs[npcIndex].absX == npcs[npcs[npcIndex].chasingRat].absX && npcs[npcIndex].absY == npcs[npcs[npcIndex].chasingRat].absY && (beatChance || npcs[npcIndex].npcType >= 768 && npcs[npcIndex].npcType <= 773)) {
                        npcs[npcs[npcIndex].chasingRat].isDead = true;
                        npcs[npcs[npcIndex].chasingRat].forceChat("Eek!");
                        npcs[npcIndex].forceChat("Meow!");
                        //startAnimation(9163, npcIndex);
                        slaveOwner.getPacketSender().sendMessage("The " + getNpcListName(NpcHandler.npcs[npcIndex].npcType) + " caught a rat!");
						if (npcs[npcIndex].npcType >= 761 && npcs[npcIndex].npcType <= 766) {
							slaveOwner.ratsCaught++;
						}
                        if (slaveOwner.ratsCaught == Pets.RATS_NEEDED_TO_GROW) {
                            slaveOwner.getPacketSender().sendMessage("Your kitten has grown into a cat!");
                            int   newNpcId = npcs[npcIndex].npcType + 7;
                            int[] coords   = { npcs[npcIndex].absX, npcs[npcIndex].absY, npcs[npcIndex].heightLevel };
                            spawnNpc3(slaveOwner, newNpcId, coords[0], coords[1], coords[2], 0, 120, 25, 200, 200, true, false, true);
                            npcs[npcIndex].absX = 0;
                            npcs[npcIndex].absY = 0;
                            npcs[npcIndex] = null;
                            //Spawns grown cat in spot of kitten.
                            slaveOwner.summonId = Pets.summonItemId(newNpcId);
                            slaveOwner.ratsCaught = 0;
                        }
                        npcs[npcIndex].chasingRat = -1;
                    } else {
						if (slaveOwner != null) {
							slaveOwner.getPacketSender().sendMessage("The " + getNpcListName(NpcHandler.npcs[npcIndex].npcType) + " failed to catch the rat.");
						}
                        npcs[npcIndex].chasingRat = -1;
                    }
                    container.stop();
                }

                @Override
                public void stop() {
                    // TODO Auto-generated method stub

                }
            }, 4);
        }
    }

    public NpcHandler() {
        for (int i = 0; i < MAX_NPCS; i++) {
            npcs[i] = null;
        }
        for (int i = 0; i < maxListedNPCs; i++) {
            NpcList[i] = null;
        }
        loadNPCList();
        loadSpawnList();
        NPCDropsHandler.loadItemDropData();
        try {
            NPCDefinition.init();
        } catch (Exception e) {
            System.out.println("npc def error: ");
            e.printStackTrace();
        }
    }

    public static boolean isUndead(int index) {
        String name = getNpcListName(npcs[index].npcType);
		for (String s : Constants.UNDEAD) {
			if (s.equalsIgnoreCase(name)) {
				return true;
			}
		}
        return false;
    }

    public void spawnNpc3(Player c, int npcType, int x, int y, int heightLevel,
                          int WalkingType, int HP, int maxHit, int attack, int defence,
                          boolean attackPlayer, boolean headIcon, boolean summonFollow) {
        int slot = -1;
        for (int i = 1; i < MAX_NPCS; i++) {
            if (npcs[i] == null) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            return;
        }
        Npc newNPC = new Npc(slot, npcType);
        newNPC.absX = x;
        newNPC.absY = y;
        newNPC.makeX = x;
        newNPC.makeY = y;
        newNPC.heightLevel = heightLevel;
        newNPC.walkingType = WalkingType;
        newNPC.HP = HP;
        newNPC.MaxHP = HP;
        newNPC.maxHit = maxHit;
        newNPC.attack = attack;
        newNPC.defence = defence;
        newNPC.spawnedBy = c.getId();
        newNPC.facePlayer(c);
        if (headIcon) {
            c.getPacketSender().drawHeadicon(1, slot, 0, 0);
        }
        if (summonFollow) {
            newNPC.summoner = true;
            newNPC.summonedBy = c.playerId;
            c.summonId = npcType;
            c.hasNpc = true;
        }
        if (attackPlayer) {
            newNPC.underAttack = true;
            if (c != null) {
                newNPC.killerId = c.playerId;
            }
        }
        npcs[slot] = newNPC;
    }

    public boolean switchesAttackers(int i) {
        switch (npcs[i].npcType) {
            case 2551:
            case 2552:
            case 2553:
            case 2559:
            case 2560:
            case 2561:
            case 2563:
            case 2564:
            case 2565:
            case 2892:
            case 2894:
                return true;
        }
        return false;
    }

    public int getClosePlayer(Player c, int i) {
        for (int j = 0; j < PlayerHandler.players.length; j++) {
            if (PlayerHandler.players[j] != null) {
                if (j == npcs[i].spawnedBy) {
                    return j;
                }
                if (goodDistance(PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || FightCaves.isFightCaveNpc(i)) {
                    if (PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0 || Boundary.isIn(PlayerHandler.players[j], Boundary.MULTI)) {
                        if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel) {
                            return j;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Summon npc, barrows, etc
     **/
    public static void spawnNpc(Player client, int npcType, int x, int y,
                                int heightLevel, int WalkingType, int HP, int maxHit, int attack,
                                int defence, boolean attackPlayer, boolean headIcon) {
        // first, search for a free slot
        int slot = -1;
        for (int i = 1; i < MAX_NPCS; i++) {
            if (npcs[i] == null) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            // System.out.println("No Free Slot");
            return; // no free slot found
        }
        Npc newNPC = new Npc(slot, npcType);
        newNPC.absX = x;
        newNPC.absY = y;
        newNPC.makeX = x;
        newNPC.makeY = y;
        newNPC.heightLevel = heightLevel;
        newNPC.walkingType = WalkingType;
        newNPC.HP = HP;
        newNPC.MaxHP = HP;
        newNPC.maxHit = maxHit;
        newNPC.attack = attack;
        newNPC.defence = defence;
        newNPC.spawnedBy = client.getId();
        if (newNPC.npcType == FightCaves.TZTOK_JAD) {
            client.setSpecialTarget(newNPC);
        }
        if (headIcon) {
            client.getPacketSender().drawHeadicon(1, slot, 0, 0);
        }
        if (attackPlayer) {
            newNPC.underAttack = true;
            if (client != null) {
                for (int anim = 4277; anim < 4285; anim++) {
                    if (npcType == anim) {
                        newNPC.forceChat("I'M ALIVE!");
                    }
                }

                newNPC.killerId = client.playerId;
            }
        }
        npcs[slot] = newNPC;
    }

    public void spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer) {
        // first, search for a free slot
        int slot = -1;
        for (int i = 1; i < MAX_NPCS; i++) {
            if (npcs[i] == null) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            // System.out.println("No Free Slot");
            return; // no free slot found
        }
        Npc newNPC = new Npc(slot, npcType);
        newNPC.absX = x;
        newNPC.absY = y;
        newNPC.makeX = x;
        newNPC.makeY = y;
        newNPC.heightLevel = heightLevel;
        newNPC.walkingType = WalkingType;
        newNPC.HP = HP;
        newNPC.MaxHP = HP;
        newNPC.maxHit = maxHit;
        newNPC.attack = attack;
        if (attackPlayer) {
            newNPC.underAttack = true;
        }
        newNPC.defence = defence;
        npcs[slot] = newNPC;
    }

    private boolean killedBarrow(int i) {
        boolean barrows = false;
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            for (int o = 0; o < c.barrowsNpcs.length; o++) {
                if (npcs[i].npcType == c.barrowsNpcs[o][0]) {
                    c.barrowsNpcs[o][1] = 2; // 2 for dead
                    c.barrowsKillCount++;
                    barrows = true;
                }
            }
        }
        return barrows;
    }

    private boolean killedCrypt(int i) {
        boolean crypt = false;
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            for (int o = 0; o < c.barrowCrypt.length; o++) {
                if (npcs[i].npcType == c.barrowCrypt[o][0]) {
                    c.barrowsKillCount++;
                    c.getPacketSender().sendString("" + c.barrowsKillCount, 4536);
                    crypt = true;
                }
            }
        }
        return crypt;
    }

    public void newNPC(int npcType, int x, int y, int heightLevel,
                       int WalkingType, int HP, int maxHit, int attack, int defence) {
        // first, search for a free slot
        int slot = -1;
        for (int i = 1; i < MAX_NPCS; i++) {
            if (npcs[i] == null) {
                slot = i;
                break;
            }
        }

        if (slot == -1) {
            return; // no free slot found
        }

        Npc newNPC = new Npc(slot, npcType);
        newNPC.absX = x;
        newNPC.absY = y;
        newNPC.makeX = x;
        newNPC.makeY = y;
        newNPC.heightLevel = heightLevel;
        newNPC.walkingType = WalkingType;
        newNPC.HP = HP;
        newNPC.MaxHP = HP;
        newNPC.maxHit = maxHit;
        newNPC.attack = attack;
        newNPC.defence = defence;
        npcs[slot] = newNPC;
    }

    public void newNPCList(int npcType, String npcName, int combat, int HP) {
        // first, search for a free slot
        int slot = -1;
        for (int i = 0; i < maxListedNPCs; i++) {
            if (NpcList[i] == null) {
                slot = i;
                break;
            }
        }

        if (slot == -1) {
            return; // no free slot found
        }

        NpcList newNPCList = new NpcList(npcType);
        newNPCList.npcName = npcName;
        newNPCList.npcCombat = combat;
        newNPCList.npcHealth = HP;
        NpcList[slot] = newNPCList;
    }

    public String[] voidKnightTalk = { "We must not fail!",
            "Take down the portals", "The Void Knights will not fall!",
            "Hail the Void Knights!", "We are beating these scum!",
            "Don't let these creatures leech my health!!",
            "Do not let me die!!!", "Please....help me!",
            "For the knights we shall prevail!" };

    public int getKillerId(int playerId) {
        int oldDamage = 0;
        int killerId  = 0;
        for (int i = 1; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] != null) {
                if (PlayerHandler.players[i].killedBy == playerId) {
                    if (PlayerHandler.players[i]
                            .withinDistance(PlayerHandler.players[playerId])) {
                        if (PlayerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
                            oldDamage = PlayerHandler.players[i].totalPlayerDamageDealt;
                            killerId = i;
                        }
                    }
                    PlayerHandler.players[i].totalPlayerDamageDealt = 0;
                    PlayerHandler.players[i].killedBy = 0;
                }
            }
        }
        return killerId;
    }

    public void process() {
        for (Npc i : NpcHandler.npcs) {
            if (i == null) {
                continue;
            }
            i.clearUpdateFlags();
        }

        for (int i = 0; i < MAX_NPCS; i++) {
            if (npcs[i] != null) {

                Client slaveOwner = (Client) PlayerHandler.players[npcs[i].summonedBy];
                if (slaveOwner == null && npcs[i].summoner) {
                    npcs[i].absX = 0;
                    npcs[i].absY = 0;
                }
                if (slaveOwner != null
                        && slaveOwner.hasNpc
                        && !slaveOwner.goodDistance(npcs[i].getX(),
                        npcs[i].getY(), slaveOwner.absX,
                        slaveOwner.absY, 15) && npcs[i].summoner) {
                    npcs[i].absX = slaveOwner.absX;
                    npcs[i].absY = slaveOwner.absY - 1;
                }

                if (slaveOwner != null && slaveOwner.hasNpc && npcs[i].summoner) {
                    if (slaveOwner.goodDistance(npcs[i].absX, npcs[i].absY, slaveOwner.absX, slaveOwner.absY, 15)) {
                        NpcHandler.followPlayer(i, slaveOwner);
                    }
                }

                if (npcs[i].actionTimer > 0) {
                    npcs[i].actionTimer--;
                }

                if (npcs[i].freezeTimer > 0) {
                    npcs[i].freezeTimer--;
                }

                if (npcs[i].hitDelayTimer > 0) {
                    npcs[i].hitDelayTimer--;
                }

                if (npcs[i].hitDelayTimer == 1) {
                    npcs[i].hitDelayTimer = 0;
                    NpcCombat.registerNpcHit(i);
                }

                if (npcs[i].attackTimer > 0) {
                    npcs[i].attackTimer--;
                }

                if (npcs[i].npcType == 3782 && PestControl.gameStarted) {
                    if (Misc.random(10) == 4) {
                        npcs[i].forceChat(voidKnightTalk[Misc.random3(voidKnightTalk.length)]);
                    }
                }

                if (npcs[i].npcType == 162) {
                    if (npcs[i].getX() == 2475 && npcs[i].getY() == 3438) {
                        npcs[i].forceChat("Okay get over that log, quick quick!");
                    } else if (npcs[i].getX() == 2471 && npcs[i].getY() == 3427) {
                        npcs[i].forceChat("Move it, move it, move it!");
                    } else if (npcs[i].getX() == 2476 && npcs[i].getY() == 3423) {
                        npcs[i].forceChat("That's it - straight up");
                    } else if (npcs[i].getX() == 2475 && npcs[i].getY() == 3421) {
                        if (Misc.random(1) == 0) {
                            npcs[i].forceChat("Terrorbirds could climb faster than that!");
                        } else {
                            npcs[i].forceChat("Come on scaredy cat, get across that rope!");
                        }
                    } else if (npcs[i].getX() == 2481 && npcs[i].getY() == 3424) {
                        npcs[i].forceChat("My Granny can move faster than you.");
                    }
                }

                if (npcs[i].npcType == 43) {
                    if (Misc.random(20) == 4) {
                        npcs[i].forceChat("Baa!");
                    }
                }
                if (npcs[i].npcType == 81 || npcs[i].npcType == 397 || npcs[i].npcType == 1766 || npcs[i].npcType == 1767 || npcs[i].npcType == 1768) {
                    if (Misc.random(30) == 4) {
                        npcs[i].forceChat("Moo");
                    }
                }
                if (npcs[i].npcType == 45) {
                    if (Misc.random(30) == 6) {
                        npcs[i].forceChat("Quack!");
                    }
                }
		    if (npcs[i].npcType == 1685)
		    {
			    if (Misc.random(25) == 7) {
				    int rand = Misc.random(25);
				    if (rand <= 5)
					    npcs[i].forceChat("Down with Necrovarus!!");
				    else if (rand <= 10)
					    npcs[i].forceChat("United we conquer - divided we fall!!");
				    else if (rand <= 15)
					    npcs[i].forceChat("We shall overcome!!");
				    else if (rand <= 20)
					    npcs[i].forceChat("Let Necrovarus know we want out!!");
				    else
					    npcs[i].forceChat("Don't stay silent - victory in numbers!!");
			    }
		    }    
                if (npcs[i].spawnedBy > 0) {
                    if (PlayerHandler.players[npcs[i].spawnedBy] == null
                            || PlayerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel
                            || PlayerHandler.players[npcs[i].spawnedBy].respawnTimer > 0
                            || !PlayerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), PlayerHandler.players[npcs[i].spawnedBy].getX(), PlayerHandler.players[npcs[i].spawnedBy].getY(), ((FightCaves.isFightCaveNpc(i)) ? 60 : 20))) {

                        if (npcs[i].npcType == FightCaves.YT_HURKOT) {
                            Player c       = ((Client) PlayerHandler.players[npcs[i].spawnedBy]);
                            int    ranHeal = Misc.random(10);
                            if (ranHeal == 10) {
                                FightCaves.healJad(c, i);
                            }
                        }
                        if (PlayerHandler.players[npcs[i].spawnedBy] != null) {
                            for (int o = 0; o < PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++) {
                                if (npcs[i].npcType == PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
                                    if (PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1) {
                                        PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
                                    }
                                }
                            }
                        }
                        npcs[i] = null;
                    }
                }
                if (npcs[i] == null) {
                    continue;
                }

                Player client = (Client) PlayerHandler.players[NpcData.getCloseRandomPlayer(i)];
                if (client != null) {
                    boolean aggressive = (NpcAggressive.isAggressive(i) || getNpcListCombat(npcs[i].npcType) * 2 > client.combatLevel && getNpcListAggressive(npcs[i].npcType));
                    if (aggressive && !npcs[i].underAttack && !npcs[i].isDead && npcs[i].MaxHP > 0) {
                        npcs[i].killerId = NpcData.getCloseRandomPlayer(i);
                    }
                }

                if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000) {
                    npcs[i].underAttackBy = 0;
                }

                if ((npcs[i].killerId > 0 || npcs[i].underAttack) && !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
                    if (!npcs[i].isDead) {
                        int p = npcs[i].killerId;
                        if (PlayerHandler.players[p] != null) {
                            Player c = (Client) PlayerHandler.players[p];
                            followPlayer(i, c);
                            if (npcs[i] == null) {
                                continue;
                            }
                            stepAway(c, i);
                            if (npcs[i].attackTimer == 0) {
                                NpcCombat.attackPlayer(c, i);
                            }
                        } else {
                            npcs[i].killerId = 0;
                            npcs[i].underAttack = false;
                            npcs[i].facePlayer(null);
                        }
                    }
                }

                /**
                 * Random walking and walking home
                 **/
                if (npcs[i] == null) {
                    continue;
                }
                if ((!npcs[i].underAttack || npcs[i].walkingHome)
                        && npcs[i].randomWalk && !npcs[i].isDead) {
                    npcs[i].facePlayer(null);
                    npcs[i].killerId = 0;
                    if (npcs[i].spawnedBy == 0) {
                        if (npcs[i].absX > npcs[i].makeX
                                + Constants.NPC_RANDOM_WALK_DISTANCE
                                || npcs[i].absX < npcs[i].makeX
                                - Constants.NPC_RANDOM_WALK_DISTANCE
                                || npcs[i].absY > npcs[i].makeY
                                + Constants.NPC_RANDOM_WALK_DISTANCE
                                || npcs[i].absY < npcs[i].makeY
                                - Constants.NPC_RANDOM_WALK_DISTANCE) {
                            npcs[i].walkingHome = true;
                        }
                    }

                    if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX
                            && npcs[i].absY == npcs[i].makeY) {
                        npcs[i].walkingHome = false;
                    } else if (npcs[i].walkingHome) {
                        npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
                        npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
                        handleClipping(i);
                        npcs[i].getNextNPCMovement(i);
                        npcs[i].updateRequired = true;
                    }
                    if (npcs[i].walkingType >= 0) {
                        switch (npcs[i].walkingType) {

                            case 5:
                                npcs[i].turnNpc(npcs[i].absX - 1, npcs[i].absY);
                                break;

                            case 4:
                                npcs[i].turnNpc(npcs[i].absX + 1, npcs[i].absY);
                                break;

                            case 3:
                                npcs[i].turnNpc(npcs[i].absX, npcs[i].absY - 1);
                                break;
                            case 2:
                                npcs[i].turnNpc(npcs[i].absX, npcs[i].absY + 1);
                                break;

                            default:
                                if (npcs[i].walkingType >= 0) {
                                    npcs[i].turnNpc(npcs[i].absX, npcs[i].absY);
                                }
                                break;
                        }
                    }
                    if (npcs[i].walkingType == 1) {
                        if (Misc.random(3) == 1 && !npcs[i].walkingHome) {
                            int MoveX = 0;
                            int MoveY = 0;
                            int Rnd   = Misc.random(9);
                            if (Rnd == 1) {
                                MoveX = 1;
                                MoveY = 1;
                            } else if (Rnd == 2) {
                                MoveX = -1;
                            } else if (Rnd == 3) {
                                MoveY = -1;
                            } else if (Rnd == 4) {
                                MoveX = 1;
                            } else if (Rnd == 5) {
                                MoveY = 1;
                            } else if (Rnd == 6) {
                                MoveX = -1;
                                MoveY = -1;
                            } else if (Rnd == 7) {
                                MoveX = -1;
                                MoveY = 1;
                            } else if (Rnd == 8) {
                                MoveX = 1;
                                MoveY = -1;
                            }

                            if (MoveX == 1) {
                                if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
                                    npcs[i].moveX = MoveX;
                                } else {
                                    npcs[i].moveX = 0;
                                }
                            }

                            if (MoveX == -1) {
                                if (npcs[i].absX - MoveX > npcs[i].makeX - 1) {
                                    npcs[i].moveX = MoveX;
                                } else {
                                    npcs[i].moveX = 0;
                                }
                            }

                            if (MoveY == 1) {
                                if (npcs[i].absY + MoveY < npcs[i].makeY + 1) {
                                    npcs[i].moveY = MoveY;
                                } else {
                                    npcs[i].moveY = 0;
                                }
                            }

                            if (MoveY == -1) {
                                if (npcs[i].absY - MoveY > npcs[i].makeY - 1) {
                                    npcs[i].moveY = MoveY;
                                } else {
                                    npcs[i].moveY = 0;
                                }
                            }
                            handleClipping(i);
                            // NpcData.handleClipping(i);
                            npcs[i].getNextNPCMovement(i);
                            npcs[i].updateRequired = true;
                        }
                    }
                }

                if (npcs[i].isDead) {
                    if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false && npcs[i].needRespawn == false) {
                        npcs[i].updateRequired = true;
                        npcs[i].facePlayer(null);
                        npcs[i].killedBy = NpcData.getNpcKillerId(i);
                        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
                        if (c != null) {
                            npcs[i].animNumber = NpcEmotes.getDeadEmote(c, i); // dead emote
                            if (CombatConstants.COMBAT_SOUNDS) {
                                if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
                                    return;
                                }
                                c.getPacketSender().sendSound(CombatSounds.getNpcDeathSounds(npcs[i].npcType), 100, 0);
                            }
                        }
                        npcs[i].animUpdateRequired = true;
                        npcs[i].freezeTimer = 0;
                        npcs[i].applyDead = true;
                        boolean barrows = killedBarrow(i);
                        boolean crypt = killedCrypt(i);
                        npcs[i].actionTimer = 4; // delete time
                        resetPlayersInCombat(i);
                        if (!crypt && !barrows && c != null) {
                            c.incrementNpcKillCount(npcs[i].npcType, 1);
                            if (c.displayRegularKcMessages || (c.displayBossKcMessages && Constants.BOSS_NPC_IDS.contains(npcs[i].npcType)) || (c.displaySlayerKcMessages && Constants.SLAYER_NPC_IDS.contains(npcs[i].npcType))) {
                                c.getPacketSender().sendMessage("Your " + npcs[i].name() + " kill count is now: " + c.getNpcKillCount(npcs[i].npcType));
                            }
                        }
                    } else if (npcs[i].actionTimer == 0
                            && npcs[i].applyDead
                            && npcs[i].needRespawn == false) {
                        npcs[i].needRespawn = true;
                        npcs[i].actionTimer = NpcData.getRespawnTime(i); // respawn
                        // time
                        dropItems(i); // npc drops items!
                        FightCaves.tzhaarDeathHandler(i);
                        if (npcs[i].npcType == 2745) {
                            FightCaves.handleJadDeath(i);
                            if (PlayerHandler.players[npcs[i].killerId] != null) {
                                PlayerHandler.players[npcs[i].killerId].spawnedHealers = 0;
                                PlayerHandler.players[npcs[i].killerId].canHealersRespawn = true;
                            }
                        }
                        if (npcs[i].npcType == FightCaves.YT_HURKOT) {
                            if (PlayerHandler.players[npcs[i].killerId] != null) {
                                if (PlayerHandler.players[npcs[i].killerId].spawnedHealers != 0) {
                                    PlayerHandler.players[npcs[i].killerId].spawnedHealers -= 1;
                                    if (PlayerHandler.players[npcs[i].killerId].spawnedHealers <= 0) {
                                        PlayerHandler.players[npcs[i].killerId].spawnedHealers = 0;
                                    }
                                }
                            }
                        }
                        appendSlayerExperience(i);
                        resetEvent(i);
                        Client player = (Client) PlayerHandler.players[npcs[i].killedBy];
                        if (player != null) {
                            if (player.tutorialProgress == 24) {
                                handleratdeath(i);
                            } else if (player.tutorialProgress == 25
                                    && player.ratdied2) {
                                handleratdeath2(i);
                            }
                        }
                        if (npcs[i].npcType > 3726 && npcs[i].npcType < 3732) {
                            int damage = 10 + Misc.random(10);
                            player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]) - damage;
                            player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
                            player.handleHitMask(damage);
                        }
                        if (npcs[i].npcType == 655) {
                            player.spiritTree = true;
                            player.getPacketSender().sendMessage(
                                    "You have defeated the tree spirit.");
                        }
                        if (npcs[i].npcType > 412 && npcs[i].npcType < 419) {
                            player.golemSpawned = false;
                        }
                        if (npcs[i].npcType == 757 && player.vampSlayer == 3) {
                            player.vampSlayer = 4;
                        }
                        if (npcs[i].npcType > 390 && npcs[i].npcType < 397) {
                            RiverTroll.hasRiverTroll = false;
                        }
                        if (npcs[i].npcType > 418 && npcs[i].npcType < 425) {
                            player.zombieSpawned = false;
                        }
                        if (npcs[i].npcType > 424 && npcs[i].npcType < 431) {
                            player.shadeSpawned = false;
                        }
                        if (npcs[i].npcType > 437 && npcs[i].npcType < 444) {
                            player.treeSpiritSpawned = false;
                        }
                        if (npcs[i].npcType > 2462 && npcs[i].npcType < 2469) {
                            player.chickenSpawned = false;
                        }
                        npcs[i].absX = npcs[i].makeX;
                        npcs[i].absY = npcs[i].makeY;
                        npcs[i].HP = npcs[i].MaxHP;
                        npcs[i].animNumber = 0x328;
                        npcs[i].updateRequired = true;
                        npcs[i].animUpdateRequired = true;
                        if (npcs[i].npcType >= 2440 && npcs[i].npcType <= 2446) {
                            GameEngine.objectManager.removeObject(npcs[i].absX,
                                    npcs[i].absY);
                        }
                    } else if (npcs[i].actionTimer == 0 && npcs[i].needRespawn && npcs[i].npcType != 1158) {
                        if (npcs[i].spawnedBy > 0) {
                            npcs[i] = null;
                        } else {
                            int old1 = npcs[i].npcType;
                            int old2 = npcs[i].makeX;
                            int old3 = npcs[i].makeY;
                            int old4 = npcs[i].heightLevel;
                            int old5 = npcs[i].walkingType;
                            int old6 = npcs[i].MaxHP;
                            int old7 = npcs[i].maxHit;
                            int old8 = npcs[i].attack;
                            int old9 = npcs[i].defence;

                            npcs[i] = null;
                            newNPC(old1, old2, old3, old4, old5, old6, old7,
                                    old8, old9);
                        }
                    }
                }
            }
        }
    }

    private void handleratdeath(int i) {
        final Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            c.getPacketSender().chatbox(6180);
            c.getDialogueHandler()
                    .chatboxText(
                            "",
                            "Pass through the gate and talk to the Combat Instructor, he",
                            "will give you your next task.", "",
                            "Well done, you've made your first kill!");
            c.getPacketSender().chatbox(6179);
            c.getPacketSender().drawHeadicon(1, 6, 0, 0); // draws
            // headicon to
            // combat ude
            c.tutorialProgress = 25;
        }
    }

    private void handleratdeath2(int i) {
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            c.getPacketSender().chatbox(6180);
            c.getDialogueHandler()
                    .chatboxText(
                            "You have completed the tasks here. To move on, click on the",
                            "ladder shown. If you need to go over any of what you learnt",
                            "here, just talk to the Combat Instructor and he'll tell you what",
                            "he can.", "Moving on");
            c.getPacketSender().chatbox(6179);
            c.tutorialProgress = 26;
            c.getPacketSender().createArrow(3111, 9525, c.getH(), 2); // send
            // hint
            // to
            // furnace
        }
    }

    public boolean getsPulled(Player c, int i) {
        switch (npcs[i].npcType) {
            case 2550:
                if (npcs[i].firstAttacker > 0) {
                    return false;
                }
                break;
            case 87:
                if (Boundary.isIn(c, Boundary.TUTORIAL) || c.tutorialProgress < 36) {
                    return false;
                }
                break;
        }
        return true;
    }

    public static boolean multiAttacks(int i) {
        switch (npcs[i].npcType) {
            case 1158: //kq
				if (npcs[i].attackType == AttackType.MAGIC.getValue()) {
					return true;
				}
            case 1160: //kq
				if (npcs[i].attackType == AttackType.RANGE.getValue()) {
					return true;
				}
            case 2558:
                return true;
            case 2562:
                if (npcs[i].attackType == AttackType.MAGIC.getValue()) {
                    return true;
                }
            case 2550:
                if (npcs[i].attackType == AttackType.RANGE.getValue()) {
                    return true;
                }
            default:
                return false;
        }

    }

    private void stepAway(Player c, int i) {
        int otherX = NpcHandler.npcs[i].getX();
        int otherY = NpcHandler.npcs[i].getY();
        if (otherX == c.getX() && otherY == c.getY()) {
            if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
                npcs[i].moveX = -1;
            } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
                npcs[i].moveX = 1;
            } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
                npcs[i].moveY = -1;
            } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
                npcs[i].moveY = 1;
            }
            npcs[i].getNextNPCMovement(i);
            npcs[i].updateRequired = true;
        }
    }

    public static void handleClipping(int i) {
        Npc npc = npcs[i];
        if (npc.moveX == 1 && npc.moveY == 1) {
            if ((Region
                    .getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0) {
                npc.moveX = 0;
                npc.moveY = 0;
                if ((Region
                        .getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0) {
                    npc.moveY = 1;
                } else {
                    npc.moveX = 1;
                }
            }
        } else if (npc.moveX == -1 && npc.moveY == -1) {
            if ((Region
                    .getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0) {
                npc.moveX = 0;
                npc.moveY = 0;
                if ((Region
                        .getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0) {
                    npc.moveY = -1;
                } else {
                    npc.moveX = -1;
                }
            }
        } else if (npc.moveX == 1 && npc.moveY == -1) {
            if ((Region
                    .getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0) {
                npc.moveX = 0;
                npc.moveY = 0;
                if ((Region
                        .getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0) {
                    npc.moveY = -1;
                } else {
                    npc.moveX = 1;
                }
            }
        } else if (npc.moveX == -1 && npc.moveY == 1) {
            if ((Region
                    .getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0) {
                npc.moveX = 0;
                npc.moveY = 0;
                if ((Region
                        .getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0) {
                    npc.moveY = 1;
                } else {
                    npc.moveX = -1;
                }
            }
        } // Checking Diagonal movement.

        if (npc.moveY == -1) {
            if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0) {
                npc.moveY = 0;
            }
        } else if (npc.moveY == 1) {
            if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0) {
                npc.moveY = 0;
            }
        } // Checking Y movement.
        if (npc.moveX == 1) {
            if ((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0) {
                npc.moveX = 0;
            }
        } else if (npc.moveX == -1) {
            if ((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0) {
                npc.moveX = 0;
            }
        } // Checking X movement.
    }

    /**
     * Dropping Items!
     **/

    // [npc][0] = item dropped
    // [npc][1] = amount
    // [npc][2] = rarity
    // [j][2] = rarity
    // [j][1] = amount
    // [j][0] = drop
    public void dropItems(int i) {
        // TODO: add ring of wealth
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            // These npcs shouldn't have drops
            if (npcs[i].npcType == 2627 // Tz-Kih
                    || npcs[i].npcType == 2630 // Tz-Kek
                    || npcs[i].npcType == 2631 // Tok-Xil
                    || npcs[i].npcType == 2638 // Neite
                    || npcs[i].npcType == 2641 // Dragonkin
                    || npcs[i].npcType == 2643 // R4ng3rNo0b889
                    || npcs[i].npcType == 2645 // Love Cats
                    || npcs[i].npcType == 1532 // Barricade
                    || npcs[i].npcType == 153 // Butterfly
                    || PestControl.npcIsPCMonster(npcs[i].npcType)
                    || FightCaves.isFightCaveNpc(i)) {
                // These npcs shouldn't have drops
                return;
            }
            boolean isDropped = false;
            for (ItemDrop possible_drop : NPCDropsHandler.getNpcDrops(getNpcListName(npcs[i].npcType).toLowerCase().replace(" ", "_"), npcs[i].npcType)) {
                if (Misc.random(possible_drop.getChance()) == 0 && !isDropped) {
                    int amt = possible_drop.getAmount();
                    GameEngine.itemHandler.createGroundItem(c, possible_drop.getItemID(), npcs[i].absX, npcs[i].absY, amt, c.playerId);
                    //Making sure items that always drop don't cost us our drop table roll
                    if (possible_drop.getChance() > 1)
                        isDropped = true;
                }
            }
            switch (npcs[i].npcType) {
                case 2459:
                    FreakyForester.killedPheasant(c, 0);
                    GameEngine.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    break;
                case 2460:
                    FreakyForester.killedPheasant(c, 1);
                    GameEngine.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    break;
                case 2461:
                    FreakyForester.killedPheasant(c, 2);
                    GameEngine.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    break;
                case 2462:
                    FreakyForester.killedPheasant(c, 3);
                    GameEngine.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    break;
                case 92:
                    if (c.restGhost == 3) {
                        GameEngine.itemHandler.createGroundItem(c, 553, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                        c.restGhost = 4;
                    }
                    break;
                case 47:
                    if (c.witchspot == 1 || c.romeojuliet > 0 && c.romeojuliet < 9) {
                        GameEngine.itemHandler.createGroundItem(c, 300, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    }
                    break;
                case 645:
                    if (c.shieldArrav == 5) {
                        GameEngine.itemHandler.createGroundItem(c, 761, npcs[i].absX, npcs[i].absY, 1, c.playerId);
                    }
                    break;
            }
            int level = getNpcListCombat(npcs[i].npcType);
            // higher level monsters have a better drop rate (max of 1/128)
            int chance = Math.max(128, 512 - (level * 2));
            if (Misc.random(1, chance) == 1) {
                int scroll = -1;
				if (level <= 1) // none
				{
					scroll = -1;
				} else if (level <= 24) // easy
				{
					scroll = 2677;
				} else if (level <= 40) // easy → medium
				{
					scroll = 2677 + Misc.random(0, 1);
				} else if (level <= 80) // medium
				{
					scroll = 2678;
				} else if (level <= 150) // medium → hard
				{
					scroll = 2678 + Misc.random(0, 1);
				} else if (level > 150)// hard
				{
					scroll = 2679;
				}
				if (scroll >= 0 && Constants.CLUES_ENABLED) {
					GameEngine.itemHandler.createGroundItem(c, scroll, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}
            }
        }
    }

    /**
     * Slayer Experience
     **/
    public void appendSlayerExperience(int i) {
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            // if (c.getSlayer().isSlayerTask(i)) {
            if (c.slayerTask == npcs[i].npcType) {
                c.taskAmount--;
                c.getPlayerAssistant().addSkillXP(c.getSlayer().getTaskExp(c.slayerTask), 18);
                if (c.taskAmount <= 0) {
                    int points = c.getSlayer().getDifficulty(c.slayerTask) * 4;
                    c.slayerTask = -1;
                    c.slayerPoints += points;
                    c.getPacketSender().sendMessage("You completed your slayer task. You obtain " + points + " slayer points.");
                    c.getPacketSender().sendMessage("Please talk to your slayer master for a new task.");
                }
            }
        }
    }

    // }

    public void resetEvent(int i) {
        Player c = (Client) PlayerHandler.players[npcs[i].killedBy];
        if (c != null) {
            RandomEventHandler.addRandom(c);
        }
    }

    /**
     * Resets players in combat
     */

    public void resetPlayersInCombat(int i) {
        for (int j = 0; j < PlayerHandler.players.length; j++) {
            if (PlayerHandler.players[j] != null) {
                if (PlayerHandler.players[j].underAttackBy2 == i) {
                    PlayerHandler.players[j].underAttackBy2 = 0;
                }
            }
        }
    }

    /**
     * Npc Follow Player
     **/

    public static int GetMove(int Place1, int Place2) {
        if (Place1 - Place2 == 0) {
            return 0;
        } else if (Place1 - Place2 < 0) {
            return 1;
        } else if (Place1 - Place2 > 0) {
            return -1;
        }
        return 0;
    }

    public static boolean followPlayer(int i) {
        if (NpcHandler.npcs[i].inLesserNpc()) {
            return false;
        }
        switch (npcs[i].npcType) {
            case 1456:
            case 2892:
            case 2894:
            case 1532:
            case 1534:
                return false;
        }
        return true;
    }

    public static void followPlayer(int i, Player player) {
        if (player == null) {
            return;
        }
        if (player.npcCanAttack == false) {
            return;
        }
        if (player.respawnTimer > 0) {
            npcs[i].facePlayer(null);
            npcs[i].randomWalk = true;
            npcs[i].underAttack = false;
            return;
        }

        if (npcs[i].npcType == 1532 || npcs[i].npcType == 1534) {
            return;
        }

        if (!followPlayer(i) && npcs[i].npcType != 1532 && npcs[i].npcType != 1534) {
            npcs[i].facePlayer(player);
            return;
        }

        int playerX = player.absX;
        int playerY = player.absY;
        npcs[i].randomWalk = false;
        int dir = Misc.direction(npcs[i].getX(), npcs[i].getY(), playerX, playerY);
        Set<Integer> nearbyDirections = new HashSet<>(Arrays.asList(6, 8, 9, 10, 12, 13));
        boolean nearBy = nearbyDirections.contains(dir);
        if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY, nearBy && NPCDefinition.forId(npcs[i].npcType).getSize() > 1 ? distanceRequired(i) - 1 : distanceRequired(i))) {
            return;
        }

        Npc    npc    = npcs[i];
        int    x      = npc.absX;
        int    y      = npc.absY;
        if (npcs[i].spawnedBy > 0
                || x < npc.makeX + Constants.NPC_FOLLOW_DISTANCE
                && x > npc.makeX - Constants.NPC_FOLLOW_DISTANCE
                && y < npc.makeY + Constants.NPC_FOLLOW_DISTANCE
                && y > npc.makeY - Constants.NPC_FOLLOW_DISTANCE) {
            if (npc.heightLevel == player.heightLevel) {
                if (player != null && npc != null) {
                    if (playerX > x && playerY < y) {
                        npc.moveX = GetMove(x, playerX);//Diagonal bottom right
                    } else if (playerX < x && playerY < y) {
                        npc.moveY = GetMove(y, playerY); //Diagonal bottom left
                    } else if (playerX < x && playerY > y) {
                        npc.moveX = GetMove(x, playerX);// Diagonal top left
                    } else if (playerX > x && playerY > y) {
                        npc.moveY = GetMove(y, playerY);// Diagonal top right
                    } else if (playerY < y) {
                        npc.moveX = GetMove(x, playerX); //Move South to player
                        npc.moveY = GetMove(y, playerY);
                    } else if (playerY > y) {
                        npc.moveX = GetMove(x, playerX); //Move North to player
                        npc.moveY = GetMove(y, playerY);
                    } else if (playerX < x) {
                        npc.moveX = GetMove(x, playerX); //Move West to player
                        npc.moveY = GetMove(y, playerY);
                    } else if (playerX > x) {
                        npc.moveX = GetMove(x, playerX); //Move East to player
                        npc.moveY = GetMove(y, playerY);
                    }
                    npc.facePlayer(player);
                    handleClipping(i);
                    npc.getRandomAndHomeNPCWalking(i);
                    npc.updateRequired = true;
                }
            }
        } else {
            npc.facePlayer(null);
            npc.randomWalk = true;
            npc.underAttack = false;
        }
    }

    /**
     * Distanced required to attack
     * If NPCs are maging in melee distance check that the NPC ID is actually in here.
     * It's also worth checking  {@link NpcData#distanceRequired}
     **/
    public static int distanceRequired(int i) {
        //if (npcs[i].npcType == 81)
        //    System.out.println("NpcHandler distanceRequired npc size " + NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize());
        switch (npcs[i].npcType) {
            case 2025:
            case 2028:
                return 6;
            case 50:
            case 2562:
                return 2;
            case 172: // dark wizards
            case 174:
            case 2881:// dag kings
            case 2882:
            case 3200:// chaos ele
            case 2743:
            case 2631:
            case 2745:
                return 8;
            case 2883:// rex
                return 1;
            case 2552:
            case 2553:
            case 2556:
            case 2557:
            case 2558:
            case 2559:
            case 2560:
            case 2564:
            case 2565:
                return 9;
            // things around dags
            case 2892:
            case 2894:
                return 10;
            default:
                return NPCDefinition.forId(npcs[i].npcType).getSize();
        }
    }

    public static int followDistance(int i) {
        
        switch (npcs[i].npcType) {
            case 2550:
            case 2551:
            case 2562:
            case 2563:
                return 8;
            case 2883:
                return 4;
            case 2881:
            case 2882:
                return 1;
        }
        return 0;
    }

    public static int getProjectileSpeed(int i) {
        switch (npcs[i].npcType) {
            case 2881:
            case 2882:
            case 3200:
                return 85;

            case 2745:
                return 130;

            case 50:
                return 90;

            case 2025:
                return 85;

            case 2028:
                return 80;

            default:
                return 85;
        }
    }

    public static int offset(int i) {
        switch (npcs[i].npcType) {
            case 50:
            case 110:
            case 941:
            case 1590:
            case 1591:
            case 1592:
            case 2642:
            case 55:
            case 54:
            case 53:
                return 2;
            case 2881:
            case 2882:
                return 1;
            case 2745:
            case 2743:
                return 1;
        }
        return 0;
    }

    public boolean specialCase(Player c, int i) { // responsible for npcs that
        // much
        if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8)
                && !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
                c.getY(), distanceRequired(i))) {
            return true;
        }
        return false;
    }

    public boolean retaliates(int npcType) {
        return npcType < 3777 || npcType > 3780
                && !(npcType >= 2440 && npcType <= 2446);
    }

    public static void handleSpecialEffects(Player c, int i, int damage) {
        if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894 || npcs[i].npcType == 1158
                || npcs[i].npcType == 1160) {
            if (damage > 0) {
                if (c != null) {
                    if (c.playerLevel[Constants.PRAYER] > 0) {
                        c.playerLevel[Constants.PRAYER]--;
                        c.getPlayerAssistant().refreshSkill(Constants.PRAYER);
                        c.getPlayerAssistant().appendPoison(12);
                    }
                }
            }
        }
    }

    public static boolean goodDistance(int objectX, int objectY, int playerX,
                                       int playerY, int distance) {
        return objectX - playerX <= distance && objectX - playerX >= -distance
                && objectY - playerY <= distance
                && objectY - playerY >= -distance
                && !((objectX - playerX == distance && objectY - playerY == -distance) //Detect diagonal positioning
                || (objectX - playerX == -distance && objectY - playerY == -distance)
                || (objectX - playerX == -distance && objectY - playerY == distance)
                || (objectX - playerX == distance && objectY - playerY == distance))
                ;
    }

    public static int getMaxHit(int i) {
        switch (npcs[i].npcType) {
            case 1158:
                return 30;
            case 2558:
                if (npcs[i].attackType == AttackType.MAGIC.getValue()) {
                    return 28;
                } else {
                    return 68;
                }
            case 1265:
            case 1267:
                return 2;
            case 2562:
                return 31;
            case 2550:
                return 36;
        }
        return 1;
    }

    public static int getNpcListCombat(int npcId) {
        for (int i = 0; i < maxListedNPCs; i++) {
            if (NpcList[i] != null) {
                if (NpcList[i].npcId == npcId) {
                    return NpcList[i].npcCombat;
                }
            }
        }
        return 0;
    }

    public void loadSpawnList() {
        Gson gson = new Gson();

        try {
            Type collectionType = new TypeToken<NpcSpawn[]>() {
            }.getType();
            NpcSpawn[] data = gson.fromJson(new FileReader("./data/cfg/spawns.json"), collectionType);

            for (NpcSpawn spawn : data) {
                newNPC(spawn.getId(),
                        spawn.getX(),
                        spawn.getY(),
                        spawn.getHeight(),
                        spawn.isWalk(),
                        getNpcListHP(spawn.getId()),
                        spawn.getMaxHit(),
                        spawn.getAttack(),
                        spawn.getStrength());
            }
        } catch (FileNotFoundException fileex) {
            System.out.println("spawns.json: file not found.");
        }
    }

    public boolean writeAutoSpawn(String FileName) {
        String   line      = "";
        String   token     = "";
        String   token2    = "";
        String   token2_2  = "";
        String[] token3    = new String[10];
        boolean  EndOfFile = false;
        // int ReadMode = 0;
        BufferedReader characterfile = null;
        JSONArray      array         = new JSONArray();
        try {
            characterfile = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException fileex) {
            System.out.println(FileName + ": file not found.");
            return false;
        }
        try {
            line = characterfile.readLine();
        } catch (IOException ioexception) {
            System.out.println(FileName + ": error loading file.");
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
                token2_2 = token2.replaceAll("\t\t", "\t");
                token2_2 = token2_2.replaceAll("\t\t", "\t");
                token2_2 = token2_2.replaceAll("\t\t", "\t");
                token2_2 = token2_2.replaceAll("\t\t", "\t");
                token2_2 = token2_2.replaceAll("\t\t", "\t");
                token3 = token2_2.split("\t");
                if (token.equals("spawn")) {
                    JSONObject object = new JSONObject();

                    object.put("id", Integer.parseInt(token3[0]));
                    object.put("x", Integer.parseInt(token3[1]));
                    object.put("y", Integer.parseInt(token3[2]));
                    object.put("height", Integer.parseInt(token3[3]));
                    object.put("walk", Integer.parseInt(token3[4]));
                    object.put("maxHit", Integer.parseInt(token3[5]));
                    object.put("attack", Integer.parseInt(token3[6]));
                    object.put("strength", Integer.parseInt(token3[7]));

                    array.put(object);

                }
            } else {
                if (line.equals("[ENDOFSPAWNLIST]")) {
                    try {
                        characterfile.close();
                    } catch (IOException ioexception) {
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

            FileWriter fileWriter = new FileWriter("spawns-dump.json");
            fileWriter.write(array.toString());
        } catch (IOException ioexception) {
        }
        return false;
    }

    public static int getNpcListHP(int npcId) {
        for (int i = 0; i < maxListedNPCs; i++) {
            if (NpcList[i] != null) {
                if (NpcList[i].npcId == npcId) {
                    return NpcList[i].npcHealth;
                }
            }
        }
        return 0;
    }

    public static String getNpcListName(int npcId) {
        for (int i = 0; i < maxListedNPCs; i++) {
            if (NpcList[i] != null) {
                if (NpcList[i].npcId == npcId) {
                    return NpcList[i].npcName.replace("_", " ");
                }
            }
        }
        return "nothing";
    }

    public void loadNPCList() {
        Gson gson = new Gson();

        try {
            Type collectionType = new TypeToken<com.rs2.util.NpcData[]>() {
            }.getType();
            com.rs2.util.NpcData[] data = gson.fromJson(new FileReader("./data/cfg/npc.json"), collectionType);

            for (com.rs2.util.NpcData npc : data) {
                newNPCList(npc.getId(), npc.getName(), npc.getCombat(), npc.getHitpoints());
            }
        } catch (FileNotFoundException fileex) {
            System.out.println("npc.json: file not found.");
        }
    }

    public boolean writeNpcListJson(String FileName) {
        String         line          = "";
        String         token         = "";
        String         token2        = "";
        String         token2_2      = "";
        String[]       token3        = new String[10];
        boolean        EndOfFile     = false;
        BufferedReader characterfile = null;
        JSONArray      array         = new JSONArray();
        try {
            characterfile = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException fileex) {
            System.out.println(FileName + ": file not found.");
            return false;
        }
        try {
            line = characterfile.readLine();
            // characterfile.close();
        } catch (IOException ioexception) {
            System.out.println(FileName + ": error loading file.");
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
                if (token.equals("npc")) {
                    JSONObject object = new JSONObject();

                    object.put("id", Integer.parseInt(token3[0]));
                    object.put("name", token3[1].replace("_", " "));
                    object.put("combat", Integer.parseInt(token3[2]));
                    object.put("hitpoints", Integer.parseInt(token3[3]));

					array.put(object);
                }
            } else {
                if (line.equals("[ENDOFNPCLIST]")) {
                    try {
						FileWriter fileWriter = new FileWriter("npc-dump.json");
						fileWriter.write(array.toString());
                        characterfile.close();
                    } catch (IOException ioexception) {
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

    public static boolean checkSpawn(Client player, int i) {
        return npcs[i] != null && npcs[i].spawnedBy != -1 && npcs[i].npcType == i;
    }

    public boolean getNpcListAggressive(int npcId) {
        return NPCDefinition.forId(npcId).isAggressive();
    }

    public int getNpcSize(int npcId) {
        return NPCDefinition.forId(npcId).getSize();
    }

}
