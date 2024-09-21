package com.rs2.game.objects;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.guilds.Guilds;
import com.rs2.game.content.minigames.Dueling;
import com.rs2.game.content.minigames.FightPits;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.minigames.castlewars.CastleWarObjects;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.quests.QuestRewards;
import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.content.randomevents.FreakyForester;
import com.rs2.game.content.skills.agility.AgilityShortcut;
import com.rs2.game.content.skills.core.Mining;
import com.rs2.game.content.skills.crafting.Spinning;
import com.rs2.game.content.skills.farming.Farming;
import com.rs2.game.content.skills.prayer.Ectofuntus;
import com.rs2.game.content.skills.runecrafting.AbyssalHandler;
import com.rs2.game.content.skills.runecrafting.RuneCraftingActions;
import com.rs2.game.content.skills.smithing.Smelting;
import com.rs2.game.content.skills.thieving.SearchForTraps;
import com.rs2.game.content.skills.thieving.Stalls;
import com.rs2.game.content.skills.thieving.ThieveOther;
import com.rs2.game.content.skills.woodcutting.Woodcutting;
import com.rs2.game.content.traveling.DesertCactus;
import com.rs2.game.globalworldobjects.ClimbOther;
import com.rs2.game.globalworldobjects.ClimbOther.ClimbData;
import com.rs2.game.globalworldobjects.PassDoor;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.impl.LightSources;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.npcs.impl.MilkCow;
import com.rs2.game.objects.impl.*;
import com.rs2.game.players.Player;
import com.rs2.game.players.Position;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;

public class ObjectsActions {

    private final Player player;

    public ObjectsActions(Player player2) {
        player = player2;
    }

    public void firstClickObject(int objectType, int objectX, int objectY) {
        player.faceUpdate(0);
        player.clickObjectType = 0;
        player.turnPlayerTo(objectX, objectY);
        player.getCompost().handleObjectClick(objectType, objectX, objectY);
        if (Farming.harvest(player, objectX, objectY)) {
            return;
        }
        if (Webs.webs(player, objectType)) {
            Webs.slashWeb(player, objectType, objectX, objectY);
            return;
        }
        if (player.stopPlayerPacket) {
            return;
        }
        if (player.getGnomeStrongHold().gnomeCourse(objectType)) {
            return;
        }
        if (player.getWildernessAgility().wildernessCourse(objectType)) {
            return;
        }
        if (player.getBarbarianAgility().barbarianCourse(objectType)) {
            return;
        }
        if (player.getPyramidAgility().pyramidCourse(objectType)) {
            return;
        }
        if (player.getApeAtollAgility().apeAtollCourse(objectType)) {
            return;
        }
        if (player.getWerewolfAgility().werewolfCourse(objectType)) {
            return;
        }
        if (Boundary.isIn(player, Boundary.PARTY_ROOM) && objectType >= 115 && objectType <= 122) {
            PartyRoom.popBalloon(player, objectX, objectY);
            return;
        }
        if (objectType >= 5103 && objectType <= 5107) {
            BrimhavenVines.handleBrimhavenVines(player, objectType);
            return;
        }
        ClimbOther.handleOpenOther(player, objectType);
        for (ClimbData t : ClimbData.values()) {
            if (objectType == t.getOpen()) {
                ClimbOther.useOther(player, objectType);
            }
        }
        OtherObjects.interactCurtain(player, objectType, objectX, objectY);
        OtherObjects.searchSpecialObject(player, objectType);
        Searching.searchObject(player, objectType);
        Levers.pullLever(player, objectType);
        ThieveOther.lockedDoor(player, objectType);
        player.getSingleGates().useSingleGate(player, objectType);
        player.getDoubleGates().useDoubleGate(player, objectType);
        PassDoor.processDoor(player, objectType);
        AbyssalHandler.handleAbyssalTeleport(player, objectType);
        OpenObject.interactObject(player, objectType);
        if (Stalls.isObject(objectType)) {
            Stalls.attemptStall(player, objectType, objectX, objectY);
            return;
        }
        //Prevent players getting stuck in level 28 wildy
        if (objectType == 1752 && objectX == 3154 && objectY == 3743) {
            player.getPacketSender().sendMessage("You find that ladder leads nowhere...");
            return;
        }
        switch (objectType) {
            case 6969: // Swamp Boaty
                if (player.objectX == 3523 && player.objectY == 3284)
                    player.getPlayerAssistant().movePlayer(3499, 3380, 0);
                break;
            case 6970: // Swamp Boaty
                if (player.objectX == 3498 && player.objectY == 3377)
                    player.getPlayerAssistant().movePlayer(3522, 3284, 0);
                break;
            case 6615:
                if (player.absY == 2809) {
                    player.getPlayerAssistant().movePlayer(player.absX, 2810, 0);
                } else if (player.absY == 2810) {
                    player.getPlayerAssistant().movePlayer(player.absX, 2809, 0);
                }
                break;
            case Ectofuntus.GRINDER:
                Ectofuntus.useBoneGrinder(player);
                break;

            case Ectofuntus.BIN:
                Ectofuntus.emptyBin(player);
                break;
            case 6:
                player.getCannon().clickCannon(objectX, objectY);
                break;
            case 6702:
            case 6703:
            case 6704:
            case 6705:
            case 6706:
            case 6707:
                player.getBarrows().useStairs();
                break;

            case 10284:
                player.getBarrows().useChest();
                break;

            case 6823:
                if (player.barrowsNpcs[0][1] == 0) {
                    NpcHandler.spawnNpc(player, 2030, player.getX(), player.getY() - 1, 3, 0, 120, 25, 200, 200, true, true);
                    player.barrowsNpcs[0][1] = 1;
                } else {
                    player.getPacketSender().sendMessage("You have already searched in this sarcophagus.");
                }
                break;

            case 6772:
                if (player.barrowsNpcs[1][1] == 0) {
                    NpcHandler.spawnNpc(player, 2029, player.getX() + 1, player.getY(), 3, 0, 120, 20, 200, 200, true, true);
                    player.barrowsNpcs[1][1] = 1;
                } else {
                    player.getPacketSender().sendMessage("You have already searched in this sarcophagus.");
                }
                break;

            case 6822:
                if (player.barrowsNpcs[2][1] == 0) {
                    NpcHandler.spawnNpc(player, 2028, player.getX(), player.getY() - 1, 3, 0, 90, 17, 200, 200, true, true);
                    player.barrowsNpcs[2][1] = 1;
                } else {
                    player.getPacketSender().sendMessage("You have already searched in this sarcophagus.");
                }
                break;

            case 6773:
                if (player.barrowsNpcs[3][1] == 0) {
                    NpcHandler.spawnNpc(player, 2027, player.getX(), player.getY() - 1, 3, 0, 120, 23, 200, 200, true, true);
                    player.barrowsNpcs[3][1] = 1;
                } else {
                    player.getPacketSender().sendMessage("You have already searched in this sarcophagus.");
                }
                break;

            case 6771:
                player.getDialogueHandler().sendDialogues(3222, 2026);
                break;

            case 6821:
                if (player.barrowsNpcs[5][1] == 0) {
                    NpcHandler.spawnNpc(player, 2025, player.getX() - 1, player.getY(), 3, 0, 90, 19, 200, 200, true, true);
                    player.barrowsNpcs[5][1] = 1;
                } else {
                    player.getPacketSender().sendMessage("You have already searched in this sarcophagus.");
                }
                break;

            case 2145:
                if (player.objectX == 3249 && player.objectY == 3192) {
                    player.getPacketSender().object(2146, 3249, 3192, 0, 0, 10);
                    Region.addObject(2146, 3249, 3192, 0, 10, 0, false);
                }
                break;

            case 2146:
                if (player.objectX == 3249 && player.objectY == 3192) {
                    player.getPacketSender().object(2145, 3249, 3192, 0, 0, 10);
                }
                break;

            case 399:
                if (player.objectX == 3096 && player.objectY == 3469) {
                    player.getPacketSender().object(398, 3096, 3469, 0, 3, 10);
                    Region.addObject(398, 3096, 3469, 0, 10, 3, false);
                }
                break;

            case 398:
                if (player.objectX == 3096 && player.objectY == 3469) {
                    player.getPacketSender().object(399, 3096, 3469, 0, 3, 10);
                }
                break;

            case 3828:
                if (player.objectX == 3509 && player.objectY == 9497) {
                    player.getPlayerAssistant().movePlayer(3507, 9494, 0);
                } else {
                    player.getPlayerAssistant().movePlayer(3484, 9509, 2);
                }
                break;

            case 2271:
                player.getPacketSender().object(2272, 2984, 3336, 1, 10);
                Region.addObject(2272, 2984, 3336, 2, 10, 1, false);
                player.getPacketSender().sendMessage("You open the cupboard.");
                break;

            case 2272:
                if (player.knightS == 5) {
                    player.getPacketSender().sendMessage("You search the cupboard...");
                    player.getDialogueHandler().sendDialogues(659, -1);
                    player.knightS = 6;
                } else {
                    player.getPacketSender().sendMessage("You search the cupboard...");
                    player.getPacketSender().sendMessage("and don't find anything interesting.");
                }
                break;

            case 9038:
            case 9039:
                if (!player.getItemAssistant().playerHasItem(6306, 100) && player.absX == 2816) {
                    player.getDialogueHandler().sendStatement("You need 100 trading sticks to enter here.");
                    player.nextChat = 0;
                    return;
                }
                if (player.absY == 3082 || player.absY == 3085) {
                    player.getDialogueHandler().sendStatement("You can't enter from here.");
                    player.nextChat = 0;
                    return;
                }
                if (player.absX == 2816 && player.getItemAssistant().playerHasItem(6306, 100)) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                    player.getItemAssistant().deleteItem(6306, 100);
                } else if (player.absX == 2817) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                }
                break;

            case 12047:
            case 12045:
                if (player.absY == 4439 || player.absY == 4436 || player.absX == 2467 || player.absX == 2464) {
                    player.getDialogueHandler().sendStatement("You can't enter the gate from here.");
                    player.nextChat = 0;
                    return;
                }
                if (!player.getItemAssistant().playerHasItem(1601, 1)) {
                    if (player.absX == 2469 || player.absY == 4434) {
                        player.getDialogueHandler().sendStatement("You need 1 cut diamond to enter.");
                        player.nextChat = 0;
                        return;
                    }
                } else if (player.absX == 2470) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1, player.absY, 0);
                    player.getPacketSender().sendMessage("You pass through the gate.");
                } else if (player.absY == 4433) {
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
                    player.getPacketSender().sendMessage("You pass through the gate.");
                } else if (player.absX == 2469) {
                    player.getItemAssistant().deleteItem(1601, 1);
                    player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                    player.getPacketSender().sendMessage("You pass through the gate.");
                } else if (player.absY == 4434) {
                    player.getItemAssistant().deleteItem(1601, 1);
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
                    player.getPacketSender().sendMessage("You pass through the gate.");
                }
                break;

            case 2039:
            case 2041:
                if (player.absY == 3356) {
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
                } else if (player.absY == 3357) {
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
                }
                player.getPacketSender().sendMessage("You pass through the gate.");
                break;

            case 69:
            case 2178:
                //if (c.objectX == 2675 && c.objectY == 3170) {
                //c.getDH().sendDialogues(79, 0);
                //} else {
                if (player.playerLevel[Constants.FISHING] <= 50) {
                    player.getPacketSender().sendMessage("You need a fishing level of 50 or higher to play Fishing Trawler.");
                    return;
                }
                GameEngine.trawler.getWaitingRoom().join(player);
                //}
                break;

            case 2179:
            case 70:
                GameEngine.trawler.getWaitingRoom().leave(player);
                break;
            case 2167:
                GameEngine.trawler.fixHole(player, objectX, objectY);
                break;
            case 2166:
                GameEngine.trawler.showReward(player);
                break;
            case 2159:
            case 2160:
                player.trawlerFade(2676, 3170, 0);
                break;
            case 2175:
                GameEngine.trawler.downLadder(player, objectX, objectY);
                break;
            case 2174:
                GameEngine.trawler.upLadder(player, objectX, objectY);
                break;

            case 2230:
            case 2265:
                player.getPacketSender().sendMessage("You look at hajedys cart.");
                break;

            case 10041:
                player.getPacketSender().sendMessage("You can't chop this tree.");
                break;

            case 12163:
            case 12164:
            case 12165:
            case 12166:
                Woodcutting.handleCanoe(player, player.objectId);
                break;

            case 5947:
                if (player.inWild()) {
                    return;
                }
                if (player.getItemAssistant().playerHasItem(954)
                        && LightSources.playerHasLightSource(player)) {
                    player.getItemAssistant().deleteItem(954, 1);
                    player.getPlayerAssistant().movePlayer(3168, 9572, 0);
                    return;
                } else if (player.getItemAssistant().playerHasItem(954)
                        && !LightSources.playerHasLightSource(player)) {
                    player.getItemAssistant().deleteItem(954, 1);
                    player.getPlayerAssistant().movePlayer(3168, 9572, 0);
                    return;
                } else {
                    player.getPacketSender().sendMessage(
                            "You need a rope to go down there.");
                }
                break;

            case 5946:
                if (player.inWild()) {
                    return;
                }
                LightSources.brightness3(player);
                player.getPlayerAssistant().movePlayer(3168, 3172, 0);
                break;

            case 4490:
            case 4487:// slayer tower doors
                if (player.absY == 3535) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3536) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 5163:
                player.getPacketSender().sendMessage("This chest is empty.");
                break;

            case 1506:
            case 1508:
                if (player.absX == 3291 || player.absX == 3294) {
                    return;
                }
                if (player.absY == 3167) {
                    player.getPlayerAssistant().movePlayer(player.absX, 3166, 0);
                } else if (player.absY == 3166) {
                    player.getPlayerAssistant().movePlayer(player.absX, 3167, 0);
                }
                break;

            case 3832:
                player.getPlayerAssistant().movePlayer(3509, 9496, 2);
                break;

            case 2896:
            case 2897:
                if (player.playerRights < 3) {
                    player.getPacketSender()
                            .sendMessage("You can't open that!");
                    player.getPlayerAssistant().movePlayer(2728, 3349, 0);
                }
                break;

            case 5581: // take axe from log
                AxeInLog.pullAxeFromLog(player, objectX, objectY);
                break;

            case 2112:
                if (player.absY == 9756
                        && player.playerLevel[Constants.MINING] >= 60) {
                    player.getPlayerAssistant().movePlayer(3046, 9757, 0);
                    player.getPacketSender()
                            .sendMessage("You enter the guild.");
                } else if (player.absY == 9757
                        && player.playerLevel[Constants.MINING] >= 60) {
                    player.getPlayerAssistant().movePlayer(3046, 9756, 0);
                    player.getPacketSender()
                            .sendMessage("You enter the guild.");
                } else if (player.playerLevel[Constants.MINING] < 60) {
                    player.getPacketSender().sendMessage(
                            "You need 60 mining to enter this guild");
                }
                break;

            case 5008:
                player.getPlayerAssistant().movePlayer(2838, 10124, 0);
                break;

            case 5998:
                player.getPlayerAssistant().movePlayer(2730, 3713, 0);
                break;

            case 7258:
                player.getPlayerAssistant().movePlayer(2906, 3537, 0);
                break;

            case 10177:
                player.getPlayerAssistant().movePlayer(1798, 4407, 3);
                break;

            case 10193:
                player.getPlayerAssistant().movePlayer(2545, 10143, 0);
                break;

            case 8966:
                player.getPlayerAssistant().movePlayer(2523, 3740, 0);
                break;

            case 10194:
                player.getPlayerAssistant().movePlayer(2542, 3740, 0);
                break;

            case 10195:
                player.getPlayerAssistant().movePlayer(1809, 4405, 2);
                break;

            case 10196:
                player.getPlayerAssistant().movePlayer(1807, 4405, 3);
                break;

            case 10197:
                player.getPlayerAssistant().movePlayer(1823, 4404, 2);
                break;

            case 10198:
                player.getPlayerAssistant().movePlayer(1825, 4404, 3);
                break;

            case 10199:
                player.getPlayerAssistant().movePlayer(1834, 4388, 2);
                break;

            case 10200:
                player.getPlayerAssistant().movePlayer(1834, 4390, 3);
                break;

            case 10201:
                player.getPlayerAssistant().movePlayer(1811, 4394, 1);
                break;

            case 10202:
                player.getPlayerAssistant().movePlayer(1812, 4394, 2);
                break;

            case 10203:
                player.getPlayerAssistant().movePlayer(1799, 4386, 2);
                break;

            case 10204:
                player.getPlayerAssistant().movePlayer(1799, 4388, 1);
                break;

            case 10205:
                player.getPlayerAssistant().movePlayer(1796, 4382, 1);
                break;

            case 10206:
                player.getPlayerAssistant().movePlayer(1796, 4382, 2);
                break;

            case 10207:
                player.getPlayerAssistant().movePlayer(1800, 4369, 2);
                break;

            case 10208:
                player.getPlayerAssistant().movePlayer(1802, 4370, 1);
                break;

            case 10209:
                player.getPlayerAssistant().movePlayer(1827, 4362, 1);
                break;

            case 10210:
                player.getPlayerAssistant().movePlayer(1825, 4362, 2);
                break;

            case 10211:
                player.getPlayerAssistant().movePlayer(1863, 4373, 2);
                break;

            case 10212:
                player.getPlayerAssistant().movePlayer(1863, 4371, 1);
                break;

            case 10213:
                player.getPlayerAssistant().movePlayer(1864, 4389, 1);
                break;

            case 10214:
                player.getPlayerAssistant().movePlayer(1864, 4387, 2);
                break;

            case 10215:
                player.getPlayerAssistant().movePlayer(1890, 4407, 0);
                break;

            case 10216:
                player.getPlayerAssistant().movePlayer(1890, 4406, 1);
                break;

            case 10217:
                player.getPlayerAssistant().movePlayer(1957, 4373, 1);
                break;

            case 10218:
                player.getPlayerAssistant().movePlayer(1957, 4371, 0);
                break;

            case 10219:
                player.getPlayerAssistant().movePlayer(1824, 4379, 3);
                break;

            case 10220:
                player.getPlayerAssistant().movePlayer(1824, 4381, 2);
                break;

            case 10221:
                player.getPlayerAssistant().movePlayer(1838, 4375, 2);
                break;

            case 10222:
                player.getPlayerAssistant().movePlayer(1838, 4377, 3);
                break;

            case 10223:
                player.getPlayerAssistant().movePlayer(1850, 4386, 1);
                break;

            case 10224:
                player.getPlayerAssistant().movePlayer(1850, 4387, 2);
                break;

            case 10225:
                player.getPlayerAssistant().movePlayer(1932, 4378, 1);
                break;

            case 10226:
                player.getPlayerAssistant().movePlayer(1932, 4380, 2);
                break;

            case 10227:
                if (player.objectX == 1961 && player.objectY == 4392)
                    player.getPlayerAssistant().movePlayer(1961, 4392, 2);
                else
                    player.getPlayerAssistant().movePlayer(1932, 4377, 1);
                break;

            case 10228:
                player.getPlayerAssistant().movePlayer(1961, 4393, 3);
                break;

            case 10229:
                player.getPlayerAssistant().movePlayer(1912, 4367, 0);
                break;

            case 10230:
                player.getPlayerAssistant().movePlayer(2899, 4449, 0);
                break;

            case 2620:
                if (player.gertCat == 6) {
                    player.getPacketSender().sendMessage("You have already found fluffs kitten.");
                    return;
                }
                if (player.gertCat == 5) {
                    player.getPacketSender().sendMessage("You search the crate...");
                    if (Misc.random(25) == 1) {
                        player.getItemAssistant().addItem(1554, 1);
                        player.gertCat = 6;
                        player.getPacketSender().sendMessage("You find the kitten you should go back to fluffs.");
                    } else {
                        player.getPacketSender().sendMessage("and find nothing...");
                    }
                    player.getPacketSender().sendMessage("and find nothing...");
                }
                break;

            case 14921:
            case 9390:
            case 2781:
            case 2785:
            case 2966:
            case 3294:
            case 3413:
            case 4304:
            case 4305:
            case 6189:
            case 6190:
            case 11009:
            case 11010:
            case 11666:
            case 12100:
            case 12809:
                Smelting.startSmelting(player, objectType);
                break;

            case 2156:// wizard tower (draynor)
                player.getPlayerAssistant().startTeleport(3109, 3168, 0, "modern");
                break;

            case 2157:// dark wizard tower (falador)
                player.getPlayerAssistant().startTeleport(2906, 3335, 0, "modern");
                break;

            case 2158:// thormac sorcer tower (cammy)
                player.getPlayerAssistant().startTeleport(2702, 3398, 0, "modern");
                break;

            case 10596:
                if (player.playerLevel[Constants.SLAYER] < 72) {
                    player.getPacketSender().sendMessage(
                            "You need 72 slayer to enter.");
                    return;
                } else {
                    player.getPacketSender().sendMessage(
                            "You enter the icy cavern.");
                    player.getPlayerAssistant().movePlayer(3056, 9555, 0);
                }
                break;

            case 10595:
                player.getPacketSender().sendMessage(
                        "You leave the icy cavern.");
                player.getPlayerAssistant().movePlayer(3056, 9562, 0);
                break;
			
            case 5259:
                if (player.absY == 3507)
                {
                    player.getPacketSender().sendMessage(
                            "You pass through the energy barrier.");
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY + 2, 0);
                }
                else if (player.absY == 3509)
                {

                    player.getPacketSender().sendMessage(
                            "You pass through the energy barrier.");
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY - 2, 0);
                }
		        break;
			
            case 5262:
                if (player.heightLevel == 0)
                {
                    player.getPlayerAssistant().movePlayer(3687, 9888, 1);
                }
                else if (player.heightLevel == 1)
                {
                    player.getPlayerAssistant().movePlayer(3671, 9887, 2);
                }
                else
                    {
                        player.getPlayerAssistant().movePlayer(3692, 9887, 3);
                    }

                break;
            case 5263:
                if (player.heightLevel == 3)
                {
                    player.getPlayerAssistant().movePlayer(3688, 9887, 2);
                }
                else if (player.heightLevel == 2)
                    {
                        player.getPlayerAssistant().movePlayer(3675, 9888, 1);
                    }
                else
                    {
                        player.getPlayerAssistant().movePlayer(3683, 9888, 0);
                    }

                break;
            case 5264:
                player.getPlayerAssistant().movePlayer(3654, 3519, 0);
                break;
            case 5267:
                player.getPlayerAssistant().movePlayer(3669, 9888, 3);
                break;
            case 5280:
                player.getPlayerAssistant().movePlayer(3666, 3522, 1);
                break;
            case 5281:
                player.getPlayerAssistant().movePlayer(3666, 3517, 0);
                break;
            case Ectofuntus.ECTOFUNTUS: // Ectofuntus Worship
                Ectofuntus.handleEctofuntus(player);
                break;

            case 12982:
                if (player.absY == 3278) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 3, 0);
                } else if (player.absY == 3275) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 3, 0);
                }
                break;

            case 2266:
                if (player.absY == 2963) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 2964) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 2606:
                if (player.absY == 9599) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 9600) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 2634:
                if (player.absX == 2837
                        && player.playerLevel[Constants.MINING] >= 50) {
                    player.getPlayerAssistant().movePlayer(player.absX + 3,
                            player.absY, 0);
                } else if (player.absX == 2840
                        && player.playerLevel[Constants.MINING] >= 50) {
                    player.getPlayerAssistant().movePlayer(player.absX - 3,
                            player.absY, 0);
                } else if (player.playerLevel[Constants.MINING] < 50) {
                    player.getDialogueHandler().sendStatement("You need 50 mining to pass to this rock slide.");
                    player.nextChat = 0;
                    return;
                }
                break;

            case 1967:
            case 1968:
                if (player.absX == 2464 || player.absX == 2467) {
                    return;
                }
                if (player.absY == 3491) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 2, 0);
                } else if (player.absY == 3493) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 2, 0);
                }
                break;

            case 4577:
                if (player.absY == 3635) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3636) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 4551:
                if (player.objectX == 2522 && player.objectY == 3597) {
                    player.getPlayerAssistant().movePlayer(2514, 3619, 0);
                    player.startAnimation(3067);
                }
                break;

            case 4558:
                if (player.objectX == 2514 && player.objectY == 3617) {
                    player.getPlayerAssistant().movePlayer(2522, 3595, 0);
                    player.startAnimation(3067);
                }
                break;

            case 2216:
                if (player.absX == 2876) {
                    player.getPlayerAssistant().movePlayer(2880, 2952, 0);
                } else if (player.absX == 2880) {
                    player.getPlayerAssistant().movePlayer(2876, 2952, 0);
                }
                break;

            case 1804:
                if (player.absY == 3449
                        && player.getItemAssistant().playerHasItem(983, 1)) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3450
                        && player.getItemAssistant().playerHasItem(983, 1)) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                } else if (!player.getItemAssistant().playerHasItem(983, 1)) {
                    player.getDialogueHandler().sendStatement("You need a brass key to enter here.");
                    player.nextChat = 0;
                }
                break;

            case 135:
                if (player.absY == 3353) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3354) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;


            case 95:
            case 94:
            case 90:
            case 89:
            case 5812:
            case 2341:
                player.getPacketSender().sendMessage(
                        "Will be added later with the quest!");
                break;

            case 8689:
                if (player.milking == false) {
                    MilkCow.milk(player);
                }
                break;

            case 24:
                if (player.absX == 2764) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, 1);
                } else if (player.absX == 2765) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, 1);
                }
                break;

            case 2706:
                if (player.absY == 3321) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3322) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 102:
                if (player.absY == 3480) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absY == 3481) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                }
                break;

            case 9330:
            case 9328:
            case 9293:
            case 11844:
            case 9301:
            case 9302:
            case 2322:
            case 2323:
            case 2296:
            case 5100:
            case 5110:
            case 5111:
            case 14922:
            case 3067:
            case 9309:
            case 9310:
            case 2618:
            case 2332:
            case 5088:
            case 5090:
            case 4615:
            case 4616:
            case 3933:
            case 12127:
            case 9294:
            case 9326:
            case 9321:
            case 993:
            case 9307:
            case 9308:
                AgilityShortcut.processAgilityShortcut(player);
                break;

            case 2612:
                player.getPacketSender().object(2613, 3096, 3269, 1, 0, 10);
                Region.addObject(2613, 3096, 3269, 1, 10, 0, false);
                player.getPacketSender().sendMessage("You open the cupboard.");
                break;

            case 2613:
                player.getItemAssistant().addItem(1550, 1);
                break;


            case 9391:
            case 62:
            case 416:
            case 6545:
            case 3500:
                player.getPacketSender().sendMessage(
                        "This feature is currently disabled.");
                break;

            case 2670:
                DesertCactus.cutCactus(player, DesertCactus.getCacCutter(player), objectType,
                        objectX, objectY);
                break;

            /**
             * tutorial island objects
             */

            case 1519:
            case 1516:
                if (player.tutorialProgress == 28 && player.absX == 3129) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, player.heightLevel);
                } else if (player.tutorialProgress == 28 && player.absX == 3128) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, player.heightLevel);
                }
                break;

            case 3015:
            case 3016:
                if (player.tutorialProgress == 7 || player.diedOnTut) {
                    if (player.diedOnTut) {
                        player.getPlayerAssistant().movePlayer(player.absX - 1,
                                player.absY, 0);
                        player.getPacketSender().createArrow(3078, 3084,
                                player.getH(), 2);
                        player.getDialogueHandler().sendStatement(
                                "You have died and have already beat this step",
                                "you may continue.");
                    } else if (player.diedOnTut == false) {
                        player.getPlayerAssistant().movePlayer(player.absX - 1,
                                player.absY, 0);
                        player.getDialogueHandler().sendDialogues(3020, -1);
                    } else {
                        player.getPacketSender().sendMessage(
                                "You aren't on this part yet.");
                        return;
                    }
                }
                break;

            case 3018: // door again
                if (player.tutorialProgress > 9) {
                    PassDoor.passThroughDoor(player, 3018, 1, 2, 0, -1, 0, 0);
                    if (player.diedOnTut) {
                        player.getDialogueHandler().sendStatement(
                                "You have died and have already beat this step",
                                "you may continue.");
                        player.getPacketSender().createArrow(3086, 3126,
                                player.getH(), 2);
                    } else {
                        player.getDialogueHandler().sendDialogues(3038, -1);
                    }
                }
                break;

            case 3017:// door tutorial island
                if (player.tutorialProgress > 6 || player.diedOnTut) {
                    if (player.diedOnTut && Position.checkPosition(player, 3079, 3084, 0)) {
                        PassDoor.passThroughDoor(player, 3017, 3, 0, 0, -1, 0, 0);
                        player.getDialogueHandler().sendStatement("You have died and have already beat this step", "you may continue.");
                        player.getPacketSender().createArrow(3072, 3090, player.getH(), 2);
                    } else if (player.diedOnTut == false && Position.checkPosition(player, 3079, 3084, 0)) {
                        PassDoor.passThroughDoor(player, 3017, 3, 0, 0, -1, 0, 0);
                        player.getPacketSender().drawHeadicon(1, 3, 0, 0);
                    }
                } else {
                    player.getPacketSender().sendMessage("You aren't on this part yet.");
                    return;
                }
                break;

            case 3025:
                if (player.tutorialProgress >= 28) {
                    if (Position.checkPosition(player, 3129, 3124, 0)) {
                        PassDoor.passThroughDoor(player, 3025, 3, 0, 0, 1, 0, 0);
                    }
                    // client.getPacketDispatcher().tutorialIslandInterface(70,
                    // 15);
                    player.getPacketSender().chatbox(6180);
                    player.getDialogueHandler()
                            .chatboxText(
                                    "Follow the path to the chapel and enter it.",
                                    "Once inside talk to the monk. He'll tell you all about the skill.",
                                    "", "", "Prayer");
                    player.getPacketSender().chatbox(6179);
                    player.getPacketSender().drawHeadicon(1, 8, 0, 0); // sends
                    // to
                    // prayer
                    // dude
                }

                break;
            case 3026:
                if (player.tutorialProgress >= 32) {
                    // client.getPacketDispatcher().tutorialIslandInterface(80,
                    // 17);
                    player.getPacketSender().drawHeadicon(1, 9, 0, 0); // sends
                    // to
                    // prayer
                    // dude
                    if (Position.checkPosition(player, 3122, 3103, 0)) {
                        PassDoor.passThroughDoor(player, 3026, 0, 1, 0, 0, -1, 0);
                    }
                }
                break;

            case 3024:
                if (player.tutorialProgress >= 27) {
                    if (Position.checkPosition(player, 3124, 3124, 0)) {
                        PassDoor.passThroughDoor(player, 3024, 3, 0, 0, 1, 0, 0);
                    }
                    // client.getPacketDispatcher().tutorialIslandInterface(65,
                    // 14);
                    player.getPacketSender().chatbox(6180);
                    player.getDialogueHandler()
                            .chatboxText(
                                    "The guide here will tell you all about making cash. Just click on",
                                    "him to hear what he's got to say.", "", "",
                                    "Financial advice");
                    player.getPacketSender().chatbox(6179);
                    player.getPacketSender().drawHeadicon(1, 7, 0, 0);
                }
                break;

            case 3045:
                if (player.tutorialProgress == 26) {
                    player.getPacketSender().openUpBank();
                    // client.getPacketDispatcher().tutorialIslandInterface(60,
                    // 13);
                    player.getPacketSender().createArrow(3125, 3124,
                            player.getH(), 2);
                    player.getPacketSender().chatbox(6180);
                    player.getDialogueHandler()
                            .chatboxText(
                                    "You can store stuff here for safekeeping. If you die anything",
                                    "in your bank will be saved. To deposit something, rich click it",
                                    "and select 'store'. Once you've had a good look, close the",
                                    "window and move on through the door indicated.",
                                    "This is your bank box");
                    player.getPacketSender().chatbox(6179);
                    player.tutorialProgress = 27;
                    player.getPacketSender().createArrow(1, 7);
                } else if (player.tutorialProgress >= 27) {
                    player.getDialogueHandler().sendDialogues(1013, 494);
                }
                break;

            case 3039:
                if (player.getItemAssistant().playerHasItem(2307) && player.tutorialProgress >= 8) {
                    player.startAnimation(896);
                    player.getPlayerAssistant().requestUpdates();
                    player.getItemAssistant().deleteItem(2307, 1);
                    player.getItemAssistant().addItem(2309, 1);
                    player.getDialogueHandler().sendDialogues(3037, -1);
                }
                break;

            case 3019:
                if (player.tutorialProgress >= 11 || player.diedOnTut) {
                    if (player.diedOnTut && Position.checkPosition(player, 3086, 3126, 0)) {
                        PassDoor.passThroughDoor(player, 3019, 2, 3, 0, 0, -1, 0);
                        player.getDialogueHandler().sendStatement(
                                "You have died and have already beat this step",
                                "you may continue.");
                        player.getPacketSender().createArrow(3088, 3119,
                                player.getH(), 2);
                    } else {
                        if (Position.checkPosition(player, 3086, 3126, 0)) {
                            player.getDialogueHandler().sendDialogues(3042, -1);
                        }
                    }
                }
                break;


            case 3014:
                if (Position.checkPosition(player, 3097, 3107, 0) && player.tutorialProgress >= 2) {
                    PassDoor.passThroughDoor(player, 3014, 1, 0, 0, 1, 0, 0);
                    player.getDialogueHandler().sendDialogues(3011, -1);
                    player.getPacketSender().createArrow(1, 2);
                } else if (Position.checkPosition(player, 3097, 3107, 0) && player.diedOnTut) {
                    player.getPacketSender().createArrow(3089, 3092, player.getH(), 2);
                    player.getDialogueHandler().sendStatement("You have died and have already beat this step", "you may continue.");
                } else if (player.tutorialProgress < 2 && player.diedOnTut != true) {
                    player.getPacketSender().sendMessage("You aren't on this step yet.");
                    return;
                }
                break;

            case 9299: //Lumbridge squeeze-through fence
                if (player.absX == 3240) {
                    if (player.absY == 3191) {
                        player.getPlayerAssistant().walkTo(0, -1);
                        player.getPacketSender().sendFrame36(173, 0);
                        player.playerWalkIndex = 749;
                        player.getPlayerAssistant().requestUpdates();
                        //PlayerAssistant.sendFrame36(c, 173,1);

                    } else {
                        player.getPlayerAssistant().walkTo(0, 1);
                        player.playerWalkIndex = 749;
                        player.getPlayerAssistant().requestUpdates();
                    }
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                            // TODO Auto-generated method stub
                            player.getPlayerAssistant().setAnimationBack();
                            player.stopPlayerPacket = false;
                            container.stop();
                        }

                        @Override
                        public void stop() {
                            // TODO Auto-generated method stub

                        }

                    }, 1);
                } else {
                    player.getPacketSender().sendMessage("You can't do that from here.");
                }
                break;

            case 3020:
            case 3021:
                if (player.diedOnTut && (player.getY() == 9502 || player.getY() == 9503)) {
                    player.getDialogueHandler()
                            .sendStatement(
                                    "You have died so now all you need to do is continue",
                                    "onto the next step.");
                    player.getPacketSender().createArrow(3111, 9518,
                            player.getH(), 2);
                } else if (player.diedOnTut == false && player.tutorialProgress >= 21 && (player.getY() == 9502 || player.getY() == 9503)) {
                    player.getPacketSender().chatbox(6180);
                    player.getDialogueHandler()
                            .chatboxText(
                                    "In this area you will find out about combat with swords and",
                                    "bows. Speak to the guide and he will tell you all about it.",
                                    "", "", "Combat");
                    player.getPacketSender().chatbox(6179);
                    player.getPacketSender().object(-1, 3094, 9502, 0, 0);
                    player.getPacketSender().object(3021, 3095, 9502, 7, 0);

                    player.getPacketSender().object(-1, 3094, 9503, 0, 0);
                    player.getPacketSender().object(3020, 3095, 9503, 1, 0);

                    player.getPlayerAssistant().walkTo(1, 0);
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                            player.getPacketSender().object(3020, 3094, 9503,
                                    2, 0);
                            player.getPacketSender().object(3021, 3094, 9502,
                                    2, 0);
                            // others
                            player.getPacketSender().object(-1, 3095, 9502, 0,
                                    0);
                            player.getPacketSender().object(-1, 3095, 9503, 0,
                                    0);
                            player.getPacketSender().createArrow(1, 6); // draws
                            // headicon
                            // to
                            // combat dude

                            container.stop();
                        }

                        @Override
                        public void stop() {

                        }
                    }, 2);
                }
                break;

            case 3023:
            case 3022:
                if (player.tutorialProgress >= 24
                        && (player.getY() == 9519 || player.getY() == 9518)
                        || player.diedOnTut) {
                    if (player.diedOnTut) {
                        player.getDialogueHandler()
                                .sendStatement("Be more careful this time",
                                        "now continue to kill the rat and talk to the guide.");
                    }
                    player.getPacketSender().chatbox(6180);
                    player.getDialogueHandler()
                            .chatboxText(
                                    "",
                                    "To attack the rat, right click it and select the attack option. you",
                                    "will then walk over to it and start hitting it.",
                                    "", "Attacking");
                    player.getPacketSender().chatbox(6179);
                    player.getPacketSender().object(-1, 3111, 9518, 0, 0);
                    player.getPacketSender().object(3022, 3110, 9518, 7, 0);

                    player.getPacketSender().object(-1, 3111, 9519, 0, 0);
                    player.getPacketSender().object(3023, 3110, 9519, 1, 0);

                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {

                            player.getPacketSender().object(3022, 3111, 9518,
                                    0, 0);
                            player.getPacketSender().object(3023, 3111, 9519,
                                    0, 0);
                            // others
                            player.getPacketSender().object(-1, 3110, 9518, 7,
                                    0);
                            player.getPacketSender().object(-1, 3110, 9519, 1,
                                    0);
                            player.getPacketSender().createArrow(1, 6); // draws
                            // headicon
                            // to combat ude

                            container.stop();
                        }

                        @Override
                        public void stop() {

                        }
                    }, 4);
                } else if (player.tutorialProgress >= 25
                        && (player.getY() == 9519 || player.getY() == 9518)) {
                    player.getPacketSender().object(-1, 3111, 9518, 0, 0);
                    player.getPacketSender().object(3022, 3110, 9518, 7, 0);

                    player.getPacketSender().object(-1, 3111, 9519, 0, 0);
                    player.getPacketSender().object(3023, 3110, 9519, 1, 0);

                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {

                            player.getPacketSender().object(3022, 3111, 9518,
                                    0, 0);
                            player.getPacketSender().object(3023, 3111, 9519,
                                    0, 0);
                            // others
                            player.getPacketSender().object(-1, 3110, 9518, 7,
                                    0);
                            player.getPacketSender().object(-1, 3110, 9519, 1,
                                    0);

                            container.stop();
                        }

                        @Override
                        public void stop() {

                        }
                    }, 4);
                }
                break;

            // tutorial stuff end

            case 12349:
            case 12350:
                if (player.absX == 3213) {
                    if (player.absY == 3221 || player.absY == 3222) {
                        player.getPlayerAssistant().movePlayer(player.absX - 1, player.absY, 0);
                    }
                } else if (player.absX == 3212) {
                    if (player.absY == 3221 || player.absY == 3222) {
                        player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                    }
                }
                break;

            case 11716:
            case 11721:
                if (player.absX == 2964) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, 0);
                } else if (player.absX == 2965) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, 0);
                } else if (player.absY == 3337) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                } else if (player.absY == 3338) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absX == 2981) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, 0);
                } else if (player.absX == 2982) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, 0);
                }
                break;

            case 11717:
            case 11719:
                if (player.absY == 3342) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 2);
                } else if (player.absY == 3343) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 2);
                } else if (player.absY == 3335) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 2);
                } else if (player.absY == 3334) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 2);
                }
                break;

            case 4500:// fremennik dung
                if (player.objectX == 2809 && player.objectY == 10001) {
                    player.getPlayerAssistant().movePlayer(2796, 3615, 0);
                }
                break;

            // case 5008://near rock crabs not sure where it leads to think its
            // the boating place somewhere
            case 4499:
                if (player.objectX == 2797 && player.objectY == 3614) {
                    player.getPlayerAssistant().movePlayer(2808, 10002, 0);
                }
                break;

            case 9295:
                if (player.playerLevel[Constants.AGILITY] < 51) {
                    player.getPacketSender().sendMessage(
                            "You need 51 agility to use this shortcut.");
                    return;
                } else if (player.absX == 3155) {
                    player.turnPlayerTo(player.objectX, player.objectY);
                    player.getPlayerAssistant().movePlayer(3149, 9906, 0);
                    player.startAnimation(844);
                } else if (player.absX == 3149) {
                    player.turnPlayerTo(player.objectX, player.objectY);
                    player.getPlayerAssistant().movePlayer(3155, 9906, 0);
                    player.startAnimation(844);
                }
                break;

            case 8717:
                player.getPacketSender()
                        .sendMessage(
                                "Feature currently disabled will be added in a later release.");
                break;

            case 7057:
                if (player.absY == 3250) {
                    return;
                }
                player.getPlayerAssistant().movePlayer(3093, 3251, 1);
                break;

            case 7056:
                player.getPlayerAssistant().movePlayer(3089, 3251, 0);
                break;

            case 2186:
                if (player.absY == 3161) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                } else if (player.absY == 3160) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                }
                break;

            case 3029:
                if (player.tutorialProgress >= 14 || player.diedOnTut) {
                    if (player.diedOnTut) {
                        player.startAnimation(828);
                        player.getPlayerAssistant().movePlayer(3088, 9520, 0);
                        player.getPacketSender().createArrow(3094, 9503,
                                player.getH(), 2);
                        player.getDialogueHandler().sendStatement(
                                "You have died and have already beat this step",
                                "you may continue.");
                    } else if (player.diedOnTut == false) {
                        player.getDialogueHandler().sendDialogues(3051, -1);
                        player.startAnimation(828);
                        player.getPlayerAssistant().movePlayer(3088, 9520, 0);
                    } else {
                        player.getPacketSender().sendMessage(
                                "You aren't on this part yet.");
                        return;
                    }
                }
                break;

            case 3028:
                if (player.tutorialProgress >= 14) {
                    player.getPacketSender().sendMessage(
                            "You have already completed this step.");
                    return;
                }
                break;

            case 9300:
                if (player.absY == 3335) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                } else if (player.absY == 3334) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                }
                break;

            case 2492:
                if (player.objectX == 2911 && player.objectY == 3614) {
                    return;
                }
                player.getPlayerAssistant().movePlayer(3253, 3399, 0);
                break;

            case 5083:
                if (!player.hasPaidBrim) {
                    player.getDialogueHandler().sendDialogues(1048, 1595);
                } else {
                    player.getPlayerAssistant().movePlayer(2709, 9564, 0);
                    player.hasPaidBrim = false;
                }
                break;

            case 5084:
                player.getPlayerAssistant().movePlayer(2744, 3152, 0);
                break;
            case 5094:
                player.getPlayerAssistant().movePlayer(2643, 9594, 2);
                break;
            case 5098:
                player.getPlayerAssistant().movePlayer(2637, 9517, 0);
                break;
            case 5097:
                player.getPlayerAssistant().movePlayer(2636, 9510, 2);
                break;

            case 2287:
                if (player.playerLevel[Constants.AGILITY] < 35) {
                    player.getPacketSender().sendMessage(
                            "You need 35 agility to enter here!");
                    return;
                }
                if (player.absX == 2552 && player.absY == 3561) {
                    player.getPlayerAssistant().movePlayer(2552, 3558, 0);
                    player.startAnimation(844);
                } else if (player.absX == 2552 && player.absY == 3558) {
                    player.getPlayerAssistant().movePlayer(2552, 3561, 0);
                    player.startAnimation(844);
                }
                break;

            case 2514:
            case 1600:
            case 1601:
            case 2025:
            case 2113:
            case 2647:
            case 2712:
            case 2391:
            case 2392:
            case 2624:
            case 2625:
            case 2641:
            case 1805:
                Guilds.attemptGuild(player, objectType);
                break;

            case 14315:
                if (!PestControl.waitingBoat.containsKey(player) && player.absX == 2657 && player.absY > 2638 && player.absY < 2640) {
                    PestControl.addToWaitRoom(player);
                } else {
                    if (player.absX == 2657 && player.absY > 2638 && player.absY < 2641) {
                        player.getPlayerAssistant().movePlayer(2661, 2639, 0);
                    }
                }
                break;
            case 14314:
                if (Boundary.isIn(player, Boundary.PC_BOAT)) {
                    if (PestControl.waitingBoat.containsKey(player)) {
                        PestControl.leaveWaitingBoat(player);
                    } else {
                        if (player.absX == 2661 && player.absY > 2637 && player.absY < 2641) {
                            player.getPlayerAssistant().movePlayer(2657, 2639, 0);
                        }
                    }
                }
                break;
            case 9369:
                if (player.absX == 2399 && player.absY == 5177) {
                    FightPits.addPlayer(player);
                } else if (player.absX == 2399 && player.absY == 5175) {
                    FightPits.removePlayer(player, false);
                }
                break;

            case 9368:
                if (player.absX > 2397 && player.absX < 2401 && player.absY == 5167) {
                    FightPits.removePlayer(player, false);
                }
                break;

            case 3031:
                if (player.tutorialProgress == 26) {
                    player.getPacketSender().sendMessage(
                            "You have already completed this step.");
                    return;
                } else if (player.tutorialProgress > 35) {
                    UseOther.useDown(player, objectType);
                }
                break;

            case 3030:
                if (player.tutorialProgress == 26) {
                    player.getDialogueHandler().sendDialogues(3078, -1);
                    player.getPlayerAssistant().movePlayer(3111, 3125, 0);
                    player.startAnimation(828);
                } else if (player.tutorialProgress > 35) {
                    UseOther.useUp(player, objectType);
                }
                break;

            case 5493:
                player.getPlayerAssistant().movePlayer(3165, 3251, 0);
                player.getPacketSender()
                        .sendMessage("You climb up the ladder.");
                player.startAnimation(828);
                player.getPacketSender().closeAllWindows();
                break;

            case 4881:
                player.getPlayerAssistant().movePlayer(2806, 2785, 0);
                player.getPacketSender().sendMessage("You climb up the rope.");
                player.startAnimation(828);
                player.getPacketSender().closeAllWindows();
                break;

            case 4411:
            case 4415:
            case 4417:
            case 4418:
            case 4419:
            case 4420:
            case 4469:
            case 4470:
            case 4911:
            case 4912:
            case 4437:
            case 6281:
            case 6280:
            case 4472:
            case 4471:
            case 4406:
            case 4407:
            case 4458:
            case 4902:
            case 4903:
            case 4900:
            case 4901:
            case 4377:
            case 4378:
                if (!CastleWars.isInCw(player)) {
                    player.getPacketSender().sendMessage("You have to be in castle wars to use these objects.");
                    CastleWars.resetPlayer(player);
                    return;
                }
                CastleWarObjects.handleObject(player, objectType, objectX, objectY);
                break;

            case 1757:
                if (CastleWars.isInCw(player)) {
                    CastleWarObjects.handleObject(player, objectType, objectX, objectY);
                } else if (player.objectX == 2892 && player.objectY == 9907) {
                    player.getPlayerAssistant().movePlayer(2893, 3507, 0);
                } else {
                    UseOther.useUp(player, objectType);
                }
                break;

            case 4031:
                OtherObjects.initShantay(player, objectType);
                break;

            case 2883:
            case 2882:
                player.getDialogueHandler().sendDialogues(1018, 925);
                break;

            case 2320:
                long clickTimer = 0;
                if (player.absY <= 9963 && player.playerLevel[Constants.AGILITY] > 14 && System.currentTimeMillis() - clickTimer > 2000) {
                    player.getPlayerAssistant().movePlayer(3120, 9970, 0);
                    player.startAnimation(744);
                    player.turnPlayerTo(player.objectX, player.objectY);
                    player.getPacketSender().sendMessage("You swing on the monkey bars.");
                    player.getPlayerAssistant().addSkillXP(25, Constants.AGILITY);
                    clickTimer = System.currentTimeMillis();
                } else if (player.absY <= 9970 && player.playerLevel[Constants.AGILITY] > 14 && System.currentTimeMillis() - clickTimer > 2000) {
                    player.getPlayerAssistant().movePlayer(3120, 9963, 0);
                    player.startAnimation(744);
                    player.turnPlayerTo(player.objectX, player.objectY);
                    player.getPacketSender().sendMessage("You swing on the monkey bars.");
                    player.getPlayerAssistant().addSkillXP(25, Constants.AGILITY);
                    clickTimer = System.currentTimeMillis();
                } else if (player.playerLevel[Constants.AGILITY] < 15) {
                    player.getPacketSender().sendMessage("You need 15 agility to use these monkey bars.");
                } else {
                    player.getPacketSender().sendMessage("You can't do the monkey bars here.");
                }
                break;

            // PARTY ROOM START
            case 2417: // 26193 if falador
                //player.inPartyRoom = true;
                PartyRoom.open(player);
                break;

            case 2416:
                //player.inPartyRoom = true;
                PartyRoom.dropAll();
                break;

            case 9356:
                player.getPlayerAssistant().enterCaves();
                break;

            case 9357:
                player.getPlayerAssistant().resetTzhaar();
                break;

            case 492:
                player.getPlayerAssistant().movePlayer(2856, 9570, 0);
                break;

            case 1764:
                if (player.objectX == 2856 && player.objectY == 9569) {
                    player.getPlayerAssistant().movePlayer(2858, 3168, 0);
                }
                break;

            case 9358:
                player.getPlayerAssistant().movePlayer(2480, 5175, 0);
                break;

            case 9359:
                player.getPlayerAssistant().movePlayer(2862, 9572, 0);
                break;

            case 2610:
                player.getPlayerAssistant().movePlayer(2833, 3257, 0);
                break;

            case 2609:
                player.getPlayerAssistant().movePlayer(2834, 9657, 0);
                break;

            case 2465:
            case 2466:
            case 2467:
            case 2468:
            case 2469:
            case 2470:
            case 2471:
            case 2472:
            case 2473:
            case 2474:
            case 2475:
            case 2478:
            case 2479:
            case 2480:
            case 2481:
            case 2482:
            case 2483:
            case 2484:
            case 2485:
            case 2486:
            case 2487:
            case 2488:
            case 2452:
            case 2453:
            case 2454:
            case 2455:
            case 2456:
            case 2457:
            case 2458:
            case 2459:
            case 2460:
            case 2461:
            case 2462:
                RuneCraftingActions.handleRuneCrafting(player, objectType);
                break;
            case 3203:
                Dueling.handleForfeit(player);
                break;
            case 6481:
                player.getPlayerAssistant().movePlayer(3233, 9317, 0);
                break;

            /**
             * End
             */

            case 3829:
                player.getPlayerAssistant().movePlayer(3227, 3107, 0);
                break;

            case 4427:
                player.getPlayerAssistant().movePlayer(2373,
                        player.absY == 3120 ? 3119 : 3120, 0);
                break;

            case 4428:
                player.getPlayerAssistant().movePlayer(2372,
                        player.absY == 3120 ? 3119 : 3120, 0);
                break;

            case 4465:
                player.getPlayerAssistant().movePlayer(
                        player.absX == 2414 ? 2415 : 2414, 3073, 0);
                break;

            case 4424:
            case 4423:
                if (!CastleWars.isInCw(player)) {
                    player.getPacketSender().sendMessage("You have to be in castle wars to use these objects.");
                    CastleWars.resetPlayer(player);
                    return;
                }
                player.getPlayerAssistant().movePlayer(2427,
                        player.absY == 3087 ? 3088 : 3087, 0);
                break;


            /**
             * End
             */

            case 2079:
                if (player.getItemAssistant().playerHasItem(432, 1)) {
                    player.getItemAssistant().addItem(433, 1);
                    player.getPacketSender().sendMessage(
                            "All that's in the chest is a message...");
                    player.pirateTreasure = 4;
                } else {
                    player.getPacketSender().sendMessage(
                            "You need a key to open this chest.");
                }
                break;

            case 2071:
                if (player.pirateTreasure == 2) {
                    player.getDialogueHandler().sendStatement("You search the crate...");
                    player.getPacketSender().sendMessage(
                            "You find a bottle of rum and 10 bananas.");
                    player.getItemAssistant().addItem(431, 1);
                    player.getItemAssistant().addItem(1963, 10);
                    player.nextChat = 0;
                } else {
                    player.getPacketSender().sendMessage(
                            "You aren't on this step right now.");
                }
                break;

            case 2593:
                player.getPacketSender().sendMessage(
                        "Disabled for dragon slayer.");
                break;

            case 2024: // WP quest
                if (player.witchspot == 2) {
                    // c.getDH().sendStatement("You drink from the cauldron, it tastes horrible!",
                    // "You feel yourself imbued with power.");
                    player.witchspot = 3;
                    QuestRewards.witchFinish(player);
                } else {
                    player.getPacketSender().sendMessage(
                            "You are not on this part of the quest.");
                }
                break;

            case 2614:
                if (player.vampSlayer == 3 && player.clickedVamp == false) {
                    NpcHandler.spawnNpc(player, 757, player.getX(), player.getY(), 0, 0, 50, 10, 30, 30, true, true);
                    player.getPacketSender().sendMessage("You will need a stake and hammer to attack count draynor.");
                    player.clickedVamp = true;
                } else if (player.vampSlayer == 3 && player.clickedVamp) {
                    player.getPacketSender().sendMessage("You have already spawned the vampyre.");
                    return;
                } else if (player.vampSlayer > 3) {
                    player.getPacketSender().sendMessage("You have already killed the vampire.");
                } else if (player.vampSlayer < 3) {
                    player.getPacketSender().sendMessage("You still need to progress into vampire slayer to fight this monster.");
                }
                break;

            case 2617:
                if (player.absX > 3076 && player.absX < 3079 && player.absY == 9771) {
                    player.getPlayerAssistant().movePlayer(3115, 3356, 0);
                }
                break;

            case 2616:
                if (player.absX > 3114 && player.absX < 3117 && player.absY == 3356) {
                    player.getPlayerAssistant().movePlayer(3077, 9771, 0);
                }
                break;

            case 10093:
            case 10094:
                if (player.getItemAssistant().playerHasItem(1927, 1)) {
                    player.turnPlayerTo(player.objectX, player.objectY);
                    player.startAnimation(883);
                    player.getItemAssistant().addItem(2130, 1);
                    player.getItemAssistant().deleteItem(1927, 1);
                    player.getPlayerAssistant()
                            .addSkillXP(18, Constants.COOKING);
                } else {
                    player.getPacketSender().sendMessage(
                            "You need a bucket of milk to do this.");
                }
                break;

            case 2072: // crate
                if (player.getItemAssistant().playerHasItem(1963, 10)
                        && player.luthas) {
                    player.getItemAssistant().deleteItem(1963, 10);
                    player.getDialogueHandler().sendStatement(
                            "You pack your bananas in the crate...");
                    player.getPacketSender().sendMessage(
                            "Talk to luthas for your reward.");
                    player.bananas = 2;
                } else if (player.getItemAssistant().playerHasItem(431, 1)
                        && player.pirateTreasure == 1) {
                    player.getItemAssistant().deleteItem(431, 1);
                    player.getDialogueHandler().sendStatement(
                            "You stash your rum in the crate");
                    player.pirateTreasure = 2;
                } else if (player.objectX == 2746) {
                    player.getPacketSender().sendMessage("You search the crate...");
                    player.stopPlayerPacket = true;
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                            player.getPacketSender().sendMessage("You find nothing of interest.");
                            container.stop();
                        }

                        @Override
                        public void stop() {
                            player.stopPlayerPacket = false;
                        }
                    }, 2);
                } else {
                    player.getDialogueHandler().sendStatement(
                            "I should talk to luthas and see what to do.");
                    player.getPacketSender().sendMessage(
                            "I think I need to put some bannanas in this crate.");
                }
                break;

            case 2073: // Banana tree
            case 4754:
                if (System.currentTimeMillis() - player.waitTime > 2000) {
                    if (player.luthas) {
                        player.bananas += 1;
                        player.getItemAssistant().addItem(1963, 1);
                        player.waitTime = System.currentTimeMillis();
                    }
                    player.getItemAssistant().addItem(1963, 1);
                    player.waitTime = System.currentTimeMillis();
                } else {
                    player.getPacketSender().sendMessage("You must wait two seconds before grabbing another banana.");
                }
                break;

            case 2406:
                if (player.playerEquipment[player.playerWeapon] != 772) {
                    player.getPacketSender().sendMessage("This door is locked.");
                    return;
                }
                if (player.playerEquipment[player.playerWeapon] == 772) {
                    player.getPlayerAssistant().startTeleport(2452, 4470, 0, "modern");
                    player.getPacketSender().sendMessage("You are suddenly teleported away.");
                    if (player.lostCity == 2 || player.lostCity == 1)
                    {
                        player.getPacketSender().sendMessage("You have found the Lost City of Zanaris!");
                        QuestRewards.lostCityReward(player);
                    }

                }
                break;

            case 3759://entrance
                if (player.absX == 2893 && player.absY == 3671) {
                    player.getPlayerAssistant().movePlayer(2893, 10074, 0);
                }
                break;

            case 3760://exit
                if (player.absX == 2893 && player.absY == 10074) {
                    player.getPlayerAssistant().movePlayer(2893, 3671, 0);
                }
                break;

            case 1568:
                if (player.absX == 3098) {
                    player.getPacketSender().sendMessage("You can't use the trapdoor here.");
                    return;
                }
			/*if (objectX == 3097 && objectY == 3468) {
				player.getPlayerAssistant().movePlayer(3097, 9868, 0);*/
                if (CastleWars.isInCw(player)) {
                    CastleWarObjects.handleObject(player, objectY, objectY, objectY);
                    //} else {
                    //OtherObjects.useDown(c, c.objectId);
                }
                break;

            case 96:
            case 98:
            case 1722:
            case 1723:
            case 1733:
            case 1734:
            case 1736:
            case 1737:
            case 1742:
            case 1744:
            case 1755:
            case 2405:
            case 2711:
            case 3432:
            case 3443:
            case 4383:
            case 4755:
            case 4756:
            case 4879:
            case 5492:
            case 5096:
            case 6278:
            case 11724:
            case 11725:
            case 11727:
            case 11728:
            case 11729:
            case 11731:
            case 11732:
            case 11733:
            case 11734:
            case 11735:
            case 11736:
            case 11737:
            case 12265:
            case 2147:
            case 2148:
            case 2408:
            case 6279:
            case 7257:
            case 6439:
            case 11888:
            case 11889:
            case 11890:
            case 4568:
            case 4569:
            case 4570:
            case 4413:
            case 9582:
            case 9584:
            case 5131:
            case 5130:
            case 1725:
            case 1726:
            case 6434:
            case 6436:
            case 1738:
            case 5167:
            case 12266:
            case 272:
            case 273:
            case 245:
            case 246:
            case 1767:
                Climbing.handleClimbing(player);
                break;

            case 190:
                if (player.absY == 3385) {
                    player.getPlayerAssistant().movePlayer(player.absX, 3382, 0);
                } else if (player.absY == 3382) {
                    player.getPlayerAssistant().movePlayer(player.absX, 3385, 0);
                }
                break;

            case 1754:
                if (player.objectX == 2696 && player.objectY == 3282) {
                    player.startAnimation(827);
                    player.getPacketSender().closeAllWindows();
                    player.getPlayerAssistant().movePlayer(2696, 9683, 0);
                    player.getPacketSender().sendMessage("You climb down.");
                } else {
                    UseOther.useDown(player, player.objectId);
                }
                break;

            case 1759:
            case 9472:
            case 11867:
            case 100:
                UseOther.useDown(player, player.objectId);
                break;

            case 1739:
                Climbing.handleLadder(player);
                player.dialogueAction = 147;
                break;

            case 1748:
                if (player.objectX == 3286 && player.objectY == 3192) {
                    Climbing.climbDown(player);
                } else {
                    Climbing.handleLadder(player);
                    player.dialogueAction = 147;
                }
                break;

            case 12537:
            case 2884:
            case 12965:
            case 14747:
                Climbing.handleLadder(player);
                player.dialogueAction = 147;
                break;

            case 12536:
            case 12964:
            case 1750:
            case 2833:
            case 2796:
            case 4772:
            case 1752:
            case 11739:
            case 14745:
            case 9558:
                Climbing.climbUp(player);
                break;

            case 1747:
                if (player.objectX == 2642 && player.objectY == 3428 && player.absX == 2643 && player.absY == 3429) {
                    return;
                }
                if (player.absX > 3081 && player.absX < 3085 && player.absY == 3514) {
                    return;
                }
                if (player.objectX == 2532 && player.objectY == 3545) {
                    player.getAgility().climbUp(player.getX(), player.getY(), 1);
                } else {
                    Climbing.climbUp(player);
                }
                break;

            case 1740:
            case 12538:
            case 1746:
            case 4778:
            case 12966:
            case 2797:
            case 1749:
            case 11742:
            case 11741:
            case 14746:
            case 9559:
                Climbing.climbDown(player);
                break;

            /**
             * Bank Booths
             */
            case 11338:
            case 2214:
            case 10517:
                // case 3045:
            case 5276:
            case 6084:
            case 11758:
            case 14367:
            case 2213:
                player.getDialogueHandler().sendDialogues(1013, 494);
                break;

            case 9398:// deposit box
                player.getPacketSender().sendString("The Bank of " + Constants.SERVER_NAME + " - Deposit Box", 7421);
                player.getPacketSender().sendFrame248(4465, 197);
                player.getItemAssistant().resetItems(7423);
                break;

            case 3194: // Bank Chest open
            case 4483: // Bank Chest
            case 104: // shantay chest open
                player.getPacketSender().openUpBank();
                break;

            case 2403:// should be 2418 but not working
                if (player.shieldArrav >= 6 && player.getItemAssistant().playerHasItem(759)) {
                    GameEngine.objectHandler.createAnObject(player, 2604, objectX, objectY, player.heightLevel, 0);
                    Region.addObject(2604, objectX, objectY, 0, 0, 0, false);
                } else {
                    player.getPacketSender().sendMessage("It's locked, maybe I can get the key from somewhere.");
                }
                break;

            case 2604:
                if (player.objectX == 3235 && player.objectY == 9761 && player.shieldArrav >= 6) {
                    player.getDialogueHandler().sendDialogues(742, -1);
                } else {
                    player.getPacketSender().openUpBank();
                }
                break;

            case 3193:
                if (player.objectX == 3382 && player.objectY == 3270) {
                    player.getPacketSender().object(3194, 3382, 3270, 0, 1, 10);
                    Region.addObject(3194, 3382, 3270, 0, 10, 1, false);
                } else if (player.objectX == 3381 && player.objectY == 3269) {
                    player.getPacketSender().object(3194, 3381, 3269, 0, 2, 10);
                    Region.addObject(3194, 3381, 3269, 0, 10, 2, false);
                } else {
                    GameEngine.objectHandler.createAnObject(player, 3194, objectX, objectY, player.heightLevel, -1);
                }
                break;

            case 2412:
                if (player.objectX == 3048 && player.objectY == 3233) {
                    player.getPlayerAssistant().movePlayer(3048, 3231, 1);
                    player.getPacketSender().sendMessage("You cross the Gangplank.");
                }
                break;

            case 2413:
                if (player.objectX == 3048 && player.objectY == 3232) {
                    player.getPlayerAssistant().movePlayer(3048, 3234, 0);
                }
                break;

            case 2083:
                if (player.objectX == 3030 && player.objectY == 3217) {
                    player.getPlayerAssistant().movePlayer(3032, 3217, 1);
                    player.getPacketSender().sendMessage("You cross the Gangplank.");
                }
                break;

            case 2084:
                if (player.objectX == 3031 && player.objectY == 3217) {
                    player.getPlayerAssistant().movePlayer(3029, 3217, 0);
                }
                break;

            case 2081:
                if (player.objectX == 2956 && player.objectY == 3145) {
                    player.getPlayerAssistant().movePlayer(2956, 3143, 1);
                }
                break;

            case 2082:
                if (player.objectX == 2956 && player.objectY == 3144) {
                    player.getPlayerAssistant().movePlayer(2956, 3146, 0);
                }
                break;

            case 2415:
                if (player.objectX == 2834 && player.objectY == 3333) {
                    player.getPlayerAssistant().movePlayer(2834, 3335, 0);
                }
                break;

            case 2414:
                if (player.objectX == 2834 && player.objectY == 3334) {
                    player.getPlayerAssistant().movePlayer(2834, 3332, 1);
                }
                break;

            case 14304:
                // Sailing.startTravel(c, 14);
                player.getPlayerAssistant().movePlayer(2659, 2676, 0);
                player.getDialogueHandler().sendStatement("You arrive safely.");
                break;

            case 14306:
                // Sailing.startTravel(c, 15);
                player.getPlayerAssistant().movePlayer(3041, 3202, 0);
                player.getDialogueHandler().sendStatement("You arrive safely.");
                break;

            case 1782:// full flour bin
                FlourMill.emptyFlourBin(player);
                break;

            case 2718: // Hopper
                FlourMill.hopperControl(player);
                break;

            case 8972:
                if (!player.canLeaveArea) {
                    player.getDialogueHandler().sendDialogues(3, 2458);
                } else {
                    FreakyForester.leaveArea(player);
                }
                break;

            case 1765://down
                if (player.inWild() && player.absX > 3015 && player.absX < 3019) {
                    player.getPlayerAssistant().movePlayer(3067, 10256, 0);
                }
                break;

            case 1766://up
                if (player.inWild() && player.absX > 3067 && player.absX < 3070) {
                    player.getPlayerAssistant().movePlayer(3016, 3849, 0);
                }
                break;

            case 6552:
                if (player.playerMagicBook == 0) {
                    player.playerMagicBook = 1;
                    player.getPacketSender().setSidebarInterface(6, 12855);
                    player.getPacketSender().sendMessage("An ancient wisdomin fills your mind.");
                    player.getPlayerAssistant().resetAutocast();
                } else {
                    player.getPacketSender().setSidebarInterface(6, 1151); // modern
                    player.playerMagicBook = 0;
                    player.getPacketSender().sendMessage("You feel a drain on your memory.");
                    player.getPlayerAssistant().resetAutocast();
                }
                break;

            case 8958:
                if (player.getX() == 2490 && (player.getY() == 10162 || player.getY() == 10164)) {
                    new Object(6951, player.objectX, player.objectY, player.heightLevel, 1, 10, 8958, 15);
                }
                break;

            case 8959:
                if (player.getX() == 2490 && (player.getY() == 10146 || player.getY() == 10148)) {
                    new Object(6951, player.objectX, player.objectY, player.heightLevel, 1, 10, 8959, 15);
                }
                break;

            case 8960:
                if (player.getX() == 2490 && (player.getY() == 10132 || player.getY() == 10130)) {
                    new Object(6951, player.objectX, player.objectY, player.heightLevel, 1, 10, 8960, 15);
                }
                break;

            case 14235:
            case 14233:
                if (player.absX == 2670) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, 0);
                } else if (player.absX == 2671) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, 0);
                } else if (player.absY == 2585) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY - 1, 0);
                } else if (player.absY == 2584) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY + 1, 0);
                } else if (player.absX == 2643) {
                    player.getPlayerAssistant().movePlayer(player.absX - 1,
                            player.absY, 0);
                } else if (player.absX == 2642) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1,
                            player.absY, 0);
                }
                break;

            case 14829:
            case 14830:
            case 14827:
            case 14828:
            case 14826:
            case 14831:
                // Server.objectHandler.startObelisk(objectType);
                GameEngine.objectManager.startObelisk(objectType);
                break;

            /*
             * Doors
             */
            case 6749:
                if (objectX == 3562 && objectY == 9678) {
                    player.getPacketSender().object(6749, 3562, 9678, -3, 0);
                    Region.addObject(6749, 3562, 9678, 0, 0, -3, false);
                    player.getPacketSender().object(6730, 3562, 9677, -1, 0);
                    Region.addObject(6730, 3562, 9677, 0, 0, -1, false);
                } else if (objectX == 3558 && objectY == 9677) {
                    player.getPacketSender().object(6749, 3558, 9677, -1, 0);
                    Region.addObject(6749, 3558, 9677, 0, 0, -1, false);
                    player.getPacketSender().object(6730, 3558, 9678, -3, 0);
                    Region.addObject(6730, 3558, 9677, 0, 0, -3, false);
                }
                break;
            case 6730:
                if (objectX == 3558 && objectY == 9677) {
                    player.getPacketSender().object(6749, 3562, 9678, -3, 0);
                    Region.addObject(6749, 3562, 9678, 0, 0, -3, false);
                    player.getPacketSender().object(6730, 3562, 9677, -1, 0);
                    Region.addObject(6730, 3562, 9677, 0, 0, -1, false);
                } else if (objectX == 3558 && objectY == 9678) {
                    player.getPacketSender().object(6749, 3558, 9677, -1, 0);
                    Region.addObject(6749, 3558, 9677, 0, 0, -1, false);
                    player.getPacketSender().object(6730, 3558, 9678, -3, 0);
                    Region.addObject(6730, 3558, 9678, 0, 0, -3, false);
                }
                break;
            case 6727:
                if (objectX == 3551 && objectY == 9684) {
                    player.getPacketSender().sendMessage(
                            "You cant open this door..");
                }
                break;
            case 6746:
                if (objectX == 3552 && objectY == 9684) {
                    player.getPacketSender().sendMessage(
                            "You cant open this door..");
                }
                break;
            case 6748:
                if (objectX == 3545 && objectY == 9678) {
                    player.getPacketSender().object(6748, 3545, 9678, -3, 0);
                    Region.addObject(6748, 3545, 9678, 0, 0, -3, false);
                    player.getPacketSender().object(6729, 3545, 9677, -1, 0);
                    Region.addObject(6729, 3545, 9677, 0, 0, -1, false);
                } else if (objectX == 3541 && objectY == 9677) {
                    player.getPacketSender().object(6748, 3541, 9677, -1, 0);
                    Region.addObject(6748, 3541, 9677, 0, 0, -1, false);
                    player.getPacketSender().object(6729, 3541, 9678, -3, 0);
                    Region.addObject(6729, 3541, 9678, 0, 0, -3, false);
                }
                break;
            case 6729:
                if (objectX == 3545 && objectY == 9677) {
                    player.getPacketSender().object(6748, 3545, 9678, -3, 0);
                    Region.addObject(6748, 3545, 9678, 0, 0, -3, false);
                    player.getPacketSender().object(6729, 3545, 9677, -1, 0);
                    Region.addObject(6729, 3545, 9677, 0, 0, -1, false);
                } else if (objectX == 3541 && objectY == 9678) {
                    player.getPacketSender().object(6748, 3541, 9677, -1, 0);
                    Region.addObject(6748, 3541, 9677, 0, 0, -1, false);
                    player.getPacketSender().object(6729, 3541, 9678, -3, 0);
                    Region.addObject(6729, 3541, 9678, 0, 0, -3, false);
                }
                break;
            case 6726:
                if (objectX == 3534 && objectY == 9684) {
                    player.getPacketSender().object(6726, 3534, 9684, -4, 0);
                    Region.addObject(6726, 3534, 9684, 0, 0, -4, false);
                    player.getPacketSender().object(6745, 3535, 9684, -2, 0);
                    Region.addObject(6745, 3535, 9684, 0, 0, -4, false);
                } else if (objectX == 3535 && objectY == 9688) {
                    player.getPacketSender().object(6726, 3535, 9688, -2, 0);
                    Region.addObject(6726, 3535, 9688, 0, 0, -2, false);
                    player.getPacketSender().object(6745, 3534, 9688, -4, 0);
                    Region.addObject(6745, 3534, 9688, 0, 0, -4, false);
                }
                break;
            case 6745:
                if (objectX == 3535 && objectY == 9684) {
                    player.getPacketSender().object(6726, 3534, 9684, -4, 0);
                    Region.addObject(6726, 3534, 9684, 0, 0, -4, false);
                    player.getPacketSender().object(6745, 3535, 9684, -2, 0);
                    Region.addObject(6745, 3535, 9684, 0, 0, -2, false);
                } else if (objectX == 3534 && objectY == 9688) {
                    player.getPacketSender().object(6726, 3535, 9688, -2, 0);
                    Region.addObject(6726, 3535, 9688, 0, 0, -2, false);
                    player.getPacketSender().object(6745, 3534, 9688, -4, 0);
                    Region.addObject(6745, 3534, 9688, 0, 0, -4, false);
                }
                break;
            case 6743:
                if (objectX == 3545 && objectY == 9695) {
                    player.getPacketSender().object(6724, 3545, 9694, -1, 0);
                    Region.addObject(6724, 3545, 9694, 0, 0, -1, false);
                    player.getPacketSender().object(6743, 3545, 9695, -3, 0);
                    Region.addObject(6743, 3545, 9695, 0, 0, -3, false);
                } else if (objectX == 3541 && objectY == 9694) {
                    player.getPacketSender().object(6724, 3541, 9694, -1, 0);
                    Region.addObject(6724, 3541, 9694, 0, 0, -1, false);
                    player.getPacketSender().object(6743, 3541, 9695, -3, 0);
                    Region.addObject(6743, 3541, 9695, 0, 0, -3, false);
                }
                break;
            case 6724:
                if (objectX == 3545 && objectY == 9694) {
                    player.getPacketSender().object(6724, 3545, 9694, -1, 0);
                    Region.addObject(6724, 3545, 9694, 0, 0, -1, false);
                    player.getPacketSender().object(6743, 3545, 9695, -3, 0);
                    Region.addObject(6743, 3545, 9695, 0, 0, -3, false);
                } else if (objectX == 3541 && objectY == 9695) {
                    player.getPacketSender().object(6724, 3541, 9694, -1, 0);
                    Region.addObject(6724, 3541, 9694, 0, 0, -1, false);
                    player.getPacketSender().object(6743, 3541, 9695, -3, 0);
                    Region.addObject(6743, 3541, 9695, 0, 0, -3, false);
                }
                break;

            case 9319:
                if (player.heightLevel == 0) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY, 1);
                } else if (player.heightLevel == 1) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY, 2);
                }
                break;

            case 9320:
                if (player.heightLevel == 1) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY, 0);
                } else if (player.heightLevel == 2) {
                    player.getPlayerAssistant().movePlayer(player.absX,
                            player.absY, 1);
                }
                break;

            case 4496:
            case 4494:
                if (player.heightLevel == 2) {
                    player.getPlayerAssistant().movePlayer(player.absX - 5,
                            player.absY, 1);
                } else if (player.heightLevel == 1) {
                    player.getPlayerAssistant().movePlayer(player.absX + 5,
                            player.absY, 0);
                }
                break;

            case 4493:
                if (player.heightLevel == 0 && player.absY > 3536 && player.absY < 3539 && player.absX == 3438) {
                    player.getPlayerAssistant().movePlayer(player.absX - 5, player.absY, 1);
                } else if (player.heightLevel == 1 && player.absY > 3536 && player.absY < 3539 && player.absX == 3433) {
                    player.getPlayerAssistant().movePlayer(player.absX + 5, player.absY, 2);
                }
                break;

            case 4495:
                if (player.heightLevel == 1 && player.absX == 3412) {
                    player.getPlayerAssistant().movePlayer(player.absX + 5, player.absY, 2);
                }
                break;

            case 5126:
                if (player.absY == 3554) {
                    player.getPlayerAssistant().walkTo(0, 1);
                } else {
                    player.getPlayerAssistant().walkTo(0, -1);
                }
                break;

            case 409:
            case 4859:
            case 61:
            case 10638:
            case 411:
            case 412:
                if (player.playerLevel[Constants.PRAYER] < player.getPlayerAssistant()
                        .getLevelForXP(player.playerXP[Constants.PRAYER])) {
                    player.startAnimation(645);
                    player.playerLevel[Constants.PRAYER] = player.getPlayerAssistant()
                            .getLevelForXP(player.playerXP[Constants.PRAYER]);
                    player.getPacketSender().sendMessage(
                            "You recharge your prayer points.");
                    player.getPlayerAssistant().refreshSkill(Constants.PRAYER);
                } else {
                    player.getPacketSender().sendMessage(
                            "You already have full prayer points.");
                }
                break;

            case 2640:
                if (player.inWild()) {
                    player.getPacketSender().sendMessage(
                            "You can't use this in the wilderness.");
                    return;
                }
                if (player.playerLevel[Constants.PRAYER] < player.getPlayerAssistant()
                        .getLevelForXP(player.playerXP[Constants.PRAYER])) {
                    player.startAnimation(645);
                    player.playerLevel[Constants.PRAYER] = player.getPlayerAssistant()
                            .getLevelForXP(player.playerXP[Constants.PRAYER]) + 2;
                    player.getPacketSender().sendMessage(
                            "You recharge your prayer points.");
                    player.getPlayerAssistant().refreshSkill(Constants.PRAYER);
                } else {
                    player.getPacketSender().sendMessage(
                            "You already have full prayer points.");
                }
                break;

            case 2407:
                if (player.inWild()) {
                    player.getPacketSender().sendMessage(
                            "You can't use this in the wilderness.");
                    return;
                } else {
                    player.getPacketSender().sendMessage(
                            "You feel the world around you dissolve...");
                    player.getPlayerAssistant().movePlayer(3171,
                            3609 + Misc.random(10), 0);
                }
                break;

            case 2879:
                player.getPlayerAssistant().movePlayer(2538, 4716, 0);
                break;
            case 2878:
                if (player.inWild()) {
                    player.getPacketSender().sendMessage(
                            "You can't use this in the wilderness.");
                    return;
                } else {
                    player.getPlayerAssistant().movePlayer(2509, 4689, 0);
                }
                break;

            case 9706:
                player.getPlayerAssistant().startTeleport2(3105, 3951, 0);
                break;
            case 9707:
                player.getPlayerAssistant().startTeleport2(3105, 3956, 0);
                break;

            case 2558:
                player.getPacketSender().sendMessage("This door is locked.");
                break;

            case 10529:
            case 10527:
                if (player.absY <= player.objectY) {
                    player.getPlayerAssistant().walkTo(0, 1);
                } else {
                    player.getPlayerAssistant().walkTo(0, -1);
                }
                break;
			
            case 10721:
                if (player.absY == 3298)
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY + 2, 0);
                else if (player.absY == 3300)
                    player.getPlayerAssistant().movePlayer(player.absX, player.absY - 2, 0);
                break;
            case 10725: // Bone Pile
            case 10726: // Bone Pile
            case 10727: // Bone Pile
            case 10728: // Bone Pile
                player.getMageTrainingArena().graveyard.searchBonePile(objectType);
                break;
            case 10734: // Coin Collector
                player.getMageTrainingArena().alchemy.collectCoins();
                break;
            case 10735: // Food Chute
                player.getMageTrainingArena().graveyard.depositFood();
                break;
            case 10771:
                player.getPlayerAssistant().movePlayer(3369, 3307, 1);
                break;
            case 10773:
                player.getPlayerAssistant().movePlayer(3366, 3306, 0);
                break;
            case 10775:
                player.getPlayerAssistant().movePlayer(3357, 3307, 1);
                break;
            case 10776:
                player.getPlayerAssistant().movePlayer(3360, 3306, 0);
                break;
            case 10778:
                // TODO: Require Pizazz progress hat equiped
                player.getMageTrainingArena().telekinetic.goToMaze();
                break;
            case 10779:
                // TODO: Require Pizazz progress hat equiped
                player.getPlayerAssistant().startTeleport2(3363, 9639, 0); // Enchantment training
                break;
            case 10780:
                // TODO: Require Pizazz progress hat equiped
                if (player.getItemAssistant().playerHasItem(995)) {
                    player.getDialogueHandler().sendStatement("You cannot bring coins with you.");
                    return;
                }
                player.getPlayerAssistant().startTeleport2(3365, 9624, 2); // Alchemy training
                break;
            case 10781:
                // TODO: Require Pizazz progress hat equiped
                player.getPlayerAssistant().startTeleport2(3364, 9639, 1); // Graveyard training
                break;
            case 10782: // Leave mage training rooms
                player.getPlayerAssistant().startTeleport2(3363, 3318, 0);
                break;
            case 10799: // Mage training arena - Enchantment room objects
                player.getItemAssistant().addItem(6899, 1);
                break;
            case 10800: // Mage training arena - Enchantment room objects
                player.getItemAssistant().addItem(6898, 1);
                break;
            case 10801: // Mage training arena - Enchantment room objects
                player.getItemAssistant().addItem(6900, 1);
                break;
            case 10802: // Mage training arena - Enchantment room objects
                player.getItemAssistant().addItem(6901, 1);
                break;
            case 10803: // Mage training arena - Enchantment room deposit hole
                player.getMageTrainingArena().enchanting.deposit();
                break;
            case 2873:
                if (player.getItemAssistant().hasFreeSlots(1))
                    player.getItemAssistant().addItem(2412, 1);
                break;
            case 2874:
                if (player.getItemAssistant().hasFreeSlots(1))
                    player.getItemAssistant().addItem(2414, 1);
                break;
            case 2875:
                if (player.getItemAssistant().hasFreeSlots(1))
                    player.getItemAssistant().addItem(2413, 1);
                break;
            case 11209:
                player.getPlayerAssistant().movePlayer(3712, 3496, 1);
                break;
            case 11210:
                player.getPlayerAssistant().movePlayer(3709, 3496, 0);
                break;
            case 11211:
                player.getPlayerAssistant().movePlayer(3684, 2950, 1);
                break;
            case 11212:
                player.getPlayerAssistant().movePlayer(3684, 2953, 0);
                break;
            case 11289:
                if (objectX == 3686 && objectY == 2946)
                    player.getPlayerAssistant().movePlayer(3687, 2946, 2);
                if (objectX == 3686 && objectY == 2950)
                    player.getPlayerAssistant().movePlayer(3687, 2950, 2);
                if (objectX == 3712 && objectY == 3494)
                    player.getPlayerAssistant().movePlayer(3712, 3493, 2);
                if (objectX == 3716 && objectY == 3494)
                    player.getPlayerAssistant().movePlayer(3716, 3493, 2);
                break;
            case 11290:
                if (objectX == 3686 && objectY == 2946)
                    player.getPlayerAssistant().movePlayer(3685, 2946, 1);
                if (objectX == 3686 && objectY == 2950)
                    player.getPlayerAssistant().movePlayer(3685, 2950, 1);
                if (objectX == 3712 && objectY == 3494)
                    player.getPlayerAssistant().movePlayer(3712, 3495, 1);
                if (objectX == 3716 && objectY == 3494)
                    player.getPlayerAssistant().movePlayer(3716, 3495, 1);
                break;
            case 11308:
                if (objectX == 3714 && objectY == 3502)
                    player.getPlayerAssistant().movePlayer(3714, 3503, 1);
                if (objectX == 3678 && objectY == 2948)
                    player.getPlayerAssistant().movePlayer(3677, 2948, 1);
                break;
            case 11309:
                if (objectX == 3714 && objectY == 3502)
                    player.getPlayerAssistant().movePlayer(3714, 3503, 0);
                if (objectX == 3678 && objectY == 2948)
                    player.getPlayerAssistant().movePlayer(3677, 2948, 0);
                break;
            case 10783:
            case 10785:
            case 10787:
            case 10789:
            case 10791:
            case 10793:
            case 10795:
            case 10797:
                player.getMageTrainingArena().alchemy.searchCupboard(objectType);
                break;

        }
    }

    public void secondClickObject(int objectType, int obX, int obY) {
        player.faceUpdate(0);
        player.clickObjectType = 0;
        player.turnPlayerTo(obX, obY);
        if (!Region.objectExists(objectType, obX, obY, player.heightLevel)) {
            return;
        }
        if (Farming.inspectObject(player, obX, obY)) {
            return;
        }
        switch (objectType) {
            case 6:
                player.getCannon().loadCannon(obX, obY);
                break;

            case 389:
            case 378:
                Searching.searchObject(player, objectType);
                break;

            case 2145:
                if (player.restGhost == 2 && player.playerEquipment[player.playerAmulet] == 552) {
                    NpcHandler.spawnNpc(player, 457, player.getX(), player.getY() + 2, 0, 0, 0, 0, 0, 0, false, false);
                    player.getPacketSender().sendMessage("You search the coffin.");
                } else if (player.restGhost == 4 && player.getItemAssistant().playerHasItem(553, 1)) {
                    player.getItemAssistant().deleteItem(553, 1);
                    player.getPacketSender().sendMessage("You have freed the ghost!");
                    QuestRewards.restFinish(player);
                    NpcHandler.spawnNpc(player, 457, player.getX(), player.getY() + 2, 0, 0, 0, 0, 0, 0, false, false);
                } else if (player.restGhost == 2 && player.playerEquipment[player.playerAmulet] != 552) {
                    player.getDialogueHandler().sendStatement("You need the ghost speak amulet for this part.");
                    player.nextChat = 0;
                } else if (player.restGhost == 4 && !player.getItemAssistant().playerHasItem(553, 1)) {
                    player.getDialogueHandler().sendStatement("You need the skull for this part.");
                    player.nextChat = 0;
                } else if (player.restGhost == 0) {
                    player.getPacketSender().sendMessage("You have not started this quest yet.");
                } else if (player.restGhost == 5) {
                    player.getPacketSender().sendMessage("You have already finished this quest.");
                }
                break;

            case 2402:
                if (player.shieldArrav == 1)
                    player.getDialogueHandler().sendDialogues(696, -1);
                else
                    player.getPacketSender().sendMessage("The bookcase is empty.");
                break;

            case 2550:
                ThieveOther.pickLock(player, 1, 3.5, 2674, 3306, 1, false);
                break;
            case 2551:
                ThieveOther.pickLock(player, 14, 15, 2674, 3303, 2, false);
                break;
            case 2566:
                SearchForTraps.searchForTraps(player, 2566); // uzdeti 28 thv
                break;
            case 2568:
                SearchForTraps.searchForTraps(player, 2568);
                break;
            case 2567:
                SearchForTraps.searchForTraps(player, 2567);
                break;
            case 2570:
                SearchForTraps.searchForTraps(player, 2570);
                break;
            case 2272:
                player.getPacketSender().object(2271, 2984, 3336, 1, 10);
                player.getPacketSender().sendMessage("You close the cupboard.");
                break;
            case 2613:
                player.getPacketSender().object(2612, 3096, 3269, 0, 10);
                player.getPacketSender().sendMessage("You close the cupboard.");
                break;
            case 9038:
            case 9039:
                if (!player.getItemAssistant().playerHasItem(6306, 100) && player.absX == 2816) {
                    player.getDialogueHandler().sendStatement("You need 100 trading sticks to enter here.");
                    player.nextChat = 0;
                    return;
                }
                if (player.absY == 3082 || player.absY == 3085) {
                    player.getDialogueHandler().sendStatement("You can't enter from here.");
                    player.nextChat = 0;
                    return;
                }
                if (player.absX == 2816 && player.getItemAssistant().playerHasItem(6306, 100)) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                    player.getItemAssistant().deleteItem(6306, 100);
                } else if (player.absX == 2817) {
                    player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
                }
                break;
            case 4569:
                if (player.objectX == 2506 && player.objectY == 3640) {
                    Climbing.climbUp(player);
                }
                break;
            case 2230:
            case 2265:
                if (player.absY > 3209 && player.absY < 3215) {
                    player.getDialogueHandler().sendDialogues(3173, 510);
                } else {
                    player.getDialogueHandler().sendDialogues(3178, 510);
                }
                break;
            case 10041:
                player.getPacketSender().sendMessage("You can't chop this tree.");
                break;
            case 10177:
                player.getPlayerAssistant().movePlayer(2544, 3743, 0);
                break;

            case 11889:
                Climbing.handleClimbing(player);
                break;

            case 2884:
            case 14747:
            case 12537:
                Climbing.climbUp(player);
                break;
            
            case Ectofuntus.GRINDER:
                if (player.ectofuntusBoneCrusherState.equals("Empty")) {
                    player.getPacketSender().sendMessage("You need to load some bones.");
                } else if (player.ectofuntusBoneCrusherState.equals("Loaded")) {
                    player.getPacketSender().sendMessage(DeprecatedItems.getItemName(player.ectofuntusBoneUsed) + " loaded and ready to be grinded.");
                } else if (player.ectofuntusBoneCrusherState.equals("Bin")) {
                    player.getPacketSender().sendMessage("Bonemeal is ready to be collected from the bin.");
                } else {
                    player.getPacketSender().sendMessage("Ghostly forces are playing with the machinery.");
                }
                break;

            case 14921:
            case 9390:
            case 2781:
            case 2785:
            case 2966:
            case 3294:
            case 3413:
            case 4304:
            case 4305:
            case 6189:
            case 6190:
            case 11009:
            case 11010:
            case 11666:
            case 12100:
            case 12809:
                Smelting.startSmelting(player, objectType);
                break;

            case 2644:
                Spinning.showSpinning(player);
                break;

            case 1739:
                Climbing.climbUp(player);
                break;

            case 1748:
            case 12965:
                Climbing.climbUp(player);
                break;

            case 2090:
            case 2091:
            case 3042:
                Mining.prospectRock(player, "copper ore");
                break;
            case 2094:
            case 2095:
            case 3043:
                Mining.prospectRock(player, "tin ore");
                break;
            case 2110:
                Mining.prospectRock(player, "blurite ore");
                break;
            case 2092:
            case 2093:
                Mining.prospectRock(player, "iron ore");
                break;
            case 2100:
            case 2101:
                Mining.prospectRock(player, "silver ore");
                break;
            case 2098:
            case 2099:
                Mining.prospectRock(player, "gold ore");
                break;
            case 2096:
            case 2097:
                Mining.prospectRock(player, "coal");
                break;
            case 2102:
            case 2103:
                Mining.prospectRock(player, "mithril ore");
                break;
            case 2104:
            case 2105:
                Mining.prospectRock(player, "adamantite ore");
                break;
            case 2106:
            case 2107:
                Mining.prospectRock(player, "runite ore");
                break;
            case 10947:
                Mining.prospectRock(player, "granite");
                break;
            case 10946:
                Mining.prospectRock(player, "sandstone");
                break;
            case 2111:
                Mining.prospectRock(player, "gem rocks");
                break;

            case 11338: // Bank Booth
            case 2214: // Bank Booth
            case 3045: // Bank Booth
            case 5276: // Bank Booth
            case 6084: // Bank Booth
            case 11758: // Bank Booth
            case 14367: // Bank Booth
            case 4483: // open bank chest
            case 3194: // open bank chest
            case 10517:
            case 2213:
                player.getPacketSender().openUpBank();
                break;

            case 1161:
            case 2646:
            case 313:
            case 5585:
            case 5584:
            case 312:
            case 3366:
                Pickable.pickObject(player, player.objectId, player.objectX, player.objectY);
                break;

            case 2558:
                if (System.currentTimeMillis() - player.lastLockPick < 3000
                        || player.freezeTimer > 0) {
                    break;
                }
                if (player.getItemAssistant().playerHasItem(1523, 1)) {
                    player.lastLockPick = System.currentTimeMillis();
                    if (Misc.random(10) <= 3) {
                        player.getPacketSender().sendMessage(
                                "You fail to pick the lock.");
                        break;
                    }
                    if (player.objectX == 3044 && player.objectY == 3956) {
                        if (player.absX == 3045) {
                            player.getPlayerAssistant().walkTo2(-1, 0);
                        } else if (player.absX == 3044) {
                            player.getPlayerAssistant().walkTo2(1, 0);
                        }

                    } else if (player.objectX == 3038 && player.objectY == 3956) {
                        if (player.absX == 3037) {
                            player.getPlayerAssistant().walkTo2(1, 0);
                        } else if (player.absX == 3038) {
                            player.getPlayerAssistant().walkTo2(-1, 0);
                        }
                    } else if (player.objectX == 3041 && player.objectY == 3959) {
                        if (player.absY == 3960) {
                            player.getPlayerAssistant().walkTo2(0, -1);
                        } else if (player.absY == 3959) {
                            player.getPlayerAssistant().walkTo2(0, 1);
                        }
                    }
                } else {
                    player.getPacketSender().sendMessage("I need a lockpick to pick this lock.");
                }
                break;

        }
    }

    public void thirdClickObject(int objectType, int obX, int obY) {
        player.clickObjectType = 0;
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("Object type: " + objectType);
        }
        if (!Region.objectExists(objectType, obX, obY, player.heightLevel)) {
            return;
        }
        OpenObject.interactObject(player, objectType);
        switch (objectType) {
            case 6:
                player.getCannon().pickup(obX, obY);
                break;
            case 3194:
                if (player.objectX == 3382 && player.objectY == 3270) {
                    player.getPacketSender().object(3193, 3382, 3270, 0, 1, 10);
                } else if (player.objectX == 3381 && player.objectY == 3269) {
                    player.getPacketSender().object(3193, 3381, 3269, 0, 2, 10);
                }
                break;
            case 4569:
                if (player.objectX == 2506 && player.objectY == 3640) {
                    Climbing.climbDown(player);
                }
                break;
            case 11890:
                Climbing.handleClimbing(player);
                break;
            case 1739:
            case 1748:
            case 12965:
            case 2884:
            case 14747:
            case 12537:
                Climbing.climbDown(player);
                break;
        }
    }

    public void fourthClickObject(int objectType, int obX, int obY) {
        player.clickObjectType = 0;
        if (player.playerRights == 3) {
            player.getPacketSender().sendMessage("Object type: " + objectType);
        }
        if (!Region.objectExists(objectType, obX, obY, player.heightLevel)) {
            return;
        }
    }
}
