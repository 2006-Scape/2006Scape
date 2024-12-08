package com.rs2.net.packets.impl;

import static com.rs2.util.GameLogger.writeLog;

import java.util.*;

import com.rs2.Connection;
import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.game.bots.BotHandler;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.*;
import com.rs2.game.players.antimacro.AntiSpam;
import com.rs2.integrations.discord.JavaCord;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.util.Misc;
import com.rs2.world.clip.Region;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Commands implements PacketType {

    @Override
    public void processPacket(Player player, Packet packet) {
        String[] messageArr = packet.readString().split(" ");
        String playerCommand = messageArr[0];
        String[] commandArguments = Arrays.copyOfRange(messageArr, 1, messageArr.length);
        if ((playerCommand.startsWith("ban") || playerCommand.startsWith("ip") || playerCommand.startsWith("mute") || playerCommand.startsWith("un")) && player.playerRights > 0 && player.playerRights < 4) {
            writeLog(player.playerName, "commands", player.playerName + " used command: " + playerCommand);
        }
        if (player.playerRights >= 0) {
            playerCommands(player, playerCommand, commandArguments);
        }
        if (player.playerRights >= 1) {
            moderatorCommands(player, playerCommand, commandArguments);
        }
        if (player.playerRights >= 2 && player.playerRights < 4) {
            adminCommands(player, playerCommand, commandArguments);
        }
        if (player.playerRights == 3) {
            developerCommands(player, playerCommand, commandArguments);
        }
    }

    public static void playerCommands(Player player, String playerCommand, String[] arguments) {
        switch (playerCommand.toLowerCase()) {
            case "stuck":
                if(JavaCord.token != null) {
                    if (JavaCord.api != null && JavaCord.api.getTextChannelById(JavaCord.logChannelId).isPresent())
                        JavaCord.api.getTextChannelById(JavaCord.logChannelId).get().sendMessage(player.playerName + " used ::stuck at X/Y: " + player.absX + "/" + player.absY);
                }
                System.err.println("Player " + player.playerName + " used ::stuck at X/Y: " + player.absX + "/" + player.absY);
                player.getPlayerAssistant().spellTeleport(Constants.RESPAWN_X, Constants.RESPAWN_Y, 0);
                break;
            case "home":
                long currentTime = System.currentTimeMillis();
                long cooldown = 30 * 60 * 1000; // 30 minutes in milliseconds
                if (player.getLastHomeTeleport() == 0 || (currentTime - player.getLastHomeTeleport() >= cooldown)) {
                    player.getPlayerAssistant().spellTeleport(Constants.RESPAWN_X, Constants.RESPAWN_Y, 0);
                    player.setLastHomeTeleport(currentTime); // Update the last teleport time
                } else {
                    long remainingTime = cooldown - (currentTime - player.getLastHomeTeleport());
                    long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
                    long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(minutesLeft);
                    player.getPacketSender().sendMessage("You need to wait " + minutesLeft + " minutes and " + secondsLeft + " seconds to use home teleport again.");
                }
                break;
            case "link":
                player.setDiscordCode(arguments[0]);
                player.getPacketSender().sendMessage("Your Account has now been linked with Discord User ID:");
                player.getPacketSender().sendMessage(player.getDiscordCode());
                break;
            case "myxprate":
            case "checkxprate":
                if(Constants.VARIABLE_XP_RATE) {
                    player.getPacketSender().sendMessage("Your current XP rate is x" + player.getXPRate());
                    break;
                }
            case "xprate":
                if(Constants.VARIABLE_XP_RATE) {
                    if (arguments.length < 1 || !arguments[0].equals("confirm")) {
                        player.getPacketSender().sendMessage("You must type \"::xprate confirm\" to view the dialogue to change your XP rate.");
                        return;
                    }
                    if (player.getXPRate() == Constants.VARIABLE_XP_RATES[0]) {
                        player.getDialogueHandler().sendDialogues(10005, 2244);
                        return;
                    } else if (player.getXPRate() == Constants.VARIABLE_XP_RATES[1]) {
                        player.getDialogueHandler().sendDialogues(10006, 2244);
                        return;
                    } else if (player.getXPRate() == Constants.VARIABLE_XP_RATES[2]) {
                        player.getDialogueHandler().sendDialogues(10007, 2244);
                        return;
                    } else if (player.getXPRate() == Constants.VARIABLE_XP_RATES[3]) {
                        player.getPacketSender().sendMessage("You already have the highest XP rate.");
                        return;
                    } else {
                        player.getDialogueHandler().sendDialogues(10001, 2244);
                        return;
                    }
                } else {
                    player.getPacketSender().sendMessage("You can't use this command in this world.");
                }
                break;
            case "toggleyell":
            case "tglyell":
            case "hideyell":
                player.hideYell = !player.hideYell;
                player.getPacketSender().sendMessage("Your yell visibility preferences have been updated: " + (player.hideYell ? "hidden" : "visible"));
                break;
            case "yell":
                int delay = 0;
                if (player.playerRights <= 1) {
                    delay = 10000;
                }
                if (!AntiSpam.blockedWords(player, String.join(" ", arguments), true)) {
                    return;
                }
                if (Connection.isMuted(player)) {
                    player.getPacketSender().sendMessage("You are muted and can't speak.");
                    return;
                }
                if (System.currentTimeMillis() - player.lastYell < delay) {
                    player.getPacketSender().sendMessage("You must wait " + delay / 1000 + " seconds before yelling again.");
                    return;
                }
                for (int j = 0; j < PlayerHandler.players.length; j++) {
                    if (PlayerHandler.players[j] != null) {
                        Client c2 = (Client) PlayerHandler.players[j];
                        if (c2.hideYell) {
                            continue;
                        }
                        String msg = "";
                        if (player.playerRights == 0) {
                            msg = "@bla@[Player] ";
                        } else if (player.playerRights == 1) {
                            msg = "@blu@[Moderator] ";
                        } else if (player.playerRights == 2) {
                            msg = "@gre@[Administator] ";
                        } else if (player.playerRights == 3) {
                            msg = "@red@[Developer] ";
                        }
                        msg += "@bla@" + Misc.optimizeText(player.playerName) + ": @blu@" + Misc.optimizeText(String.join(" ", arguments));
                        c2.getPacketSender().sendMessage(msg);
                        player.lastYell = System.currentTimeMillis();
                    }
                }
                break;
            case "claimvote":
                if (!GameEngine.ersSecret.equals("")) {
                    final String playerName = player.playerName;

                    com.everythingrs.vote.Vote.service.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int currentPoints = player.votePoints;
                                com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward(GameEngine.ersSecret, playerName, "1", "all");
                                if (reward[0].message != null) {
                                    player.getPacketSender().sendMessage(reward[0].message);
                                    return;
                                }
                                player.votePoints = (currentPoints + reward[0].give_amount);
                                //player.getActionSender().sendMessage("Thank you for voting! You now have " + reward[0].vote_points + " vote points.");
                                player.getPacketSender().sendMessage(
                                        "Thank you for voting! You now have " + player.votePoints + " vote points.");
                            } catch (Exception e) {
                                player.getPacketSender().sendMessage("Api Services are currently offline. Please check back shortly");
                                e.printStackTrace();
                            }
                        }

                    });
                } else {
                    player.getPacketSender().sendMessage("Voting Is Not Enabled");
                }
                break;
            case "coords":
            case "coord":
            case "pos":
            case "loc":
                player.getPacketSender().sendMessage("Your coords are [" + player.absX + ", " + player.absY + ", " + player.heightLevel + "]");
                player.getPacketSender().sendMessage("local coord are [" + player.getLocalX() + ", " + player.getLocalY() + "]");
                break;
            case "energy":
                player.getPacketSender().sendMessage(String.format("Run energy: %d", (int) player.playerEnergy));
                break;
            case "password":
            case "changepassword":
            case "pwd":
                if (arguments.length < 2) {
                    player.getPacketSender().sendMessage("Must have 2 arguments: ::password oldpassword newpassword");
                    return;
                } else if (!PlayerSave.passwordHash(arguments[0]).equalsIgnoreCase(player.playerPass) || arguments[0].equalsIgnoreCase(player.playerPass)) {
                    player.getPacketSender().sendMessage("Your old password is incorrect");
                    return;
                } else {
                    player.getPacketSender().sendMessage("Password updated!");
                    player.playerPass = arguments[1];
                    player.disconnected = true;
                    player.logout(true);
                }
                break;
            case "players":
            case "playershops":
                int count = playerCommand.equalsIgnoreCase("players") ? PlayerHandler.getPlayerCount() : PlayerHandler.getPlayerShopCount();
                if (count != 1) {
                    player.getPacketSender().sendMessage("There are currently " + count + " " + (playerCommand.equalsIgnoreCase("players") ? "players" : "player shops") + " online (" + PlayerHandler.getNonPlayerCount() + " staff member online).");
                } else {
                    player.getPacketSender().sendMessage("There is currently " + count + " " + (playerCommand.equalsIgnoreCase("players") ? "player" : "player shop") + " online (" + PlayerHandler.getNonPlayerCount() + " staff member online).");
                }
                String[] players = new String[count];

                int playerIndex = 0;
                for (Player _player : PlayerHandler.players) {
                    if (_player != null) {
                        if (playerCommand.equalsIgnoreCase("players") != _player.isBot) {
                            players[playerIndex++] = _player.properName;
                        }
                    }
                }


                // Clear all lines
                for (int i = 8144; i < 8196; i++) player.getPacketSender().sendString("", i);
                for (int i = 12174; i < (12174 + 50); i++) {
                    player.getPacketSender().sendString( "", i);
                }
                for (int i = 14945; i < (14945 + 100); i++) {
                    player.getPacketSender().sendString("", i);
                }

                player.getPacketSender().sendString("@dre@" + (playerCommand.equalsIgnoreCase("players") ? "Players" : "Player Shops"), 8144);

                int playersLineNumber = 8147;
                for (String line : players) {
                    player.getPacketSender().sendString(line, playersLineNumber++);
                }
                player.getPacketSender().showInterface(8134);

                break;
            case "prayer":
                player.getPacketSender().sendMessage(String.format("Prayer points: %d", player.playerLevel[Constants.PRAYER]));
                break;
            case "togglenpckillmsgs":
            case "togglenpckillmsg":
            case "togglenpckcmsgs":
            case "togglenpckcmsg":
                player.displayRegularKcMessages = !player.displayRegularKcMessages;
                player.getPacketSender().sendMessage("You now have regular NPC kill count messages: " + (player.displayRegularKcMessages ? "enabled" : "disabled"));
                break;
            case "togglebosskillmsgs":
            case "togglebosskillmsg":
            case "togglebossksmsgs":
            case "togglebossksmsg":
                player.displayBossKcMessages = !player.displayBossKcMessages;
                player.getPacketSender().sendMessage("You now have boss NPC kill count messages: " + (player.displayBossKcMessages ? "enabled" : "disabled"));
                break;
            case "toggleslayerkillmsgs":
            case "toggleslayerkillmsg":
            case "toggleslayerkcmsgs":
            case "toggleslayerkcmsg":
                player.displaySlayerKcMessages = !player.displaySlayerKcMessages;
                player.getPacketSender().sendMessage("You now have slayer NPC kill count messages: " + (player.displaySlayerKcMessages ? "enabled" : "disabled"));
                break;
            case "kc":
            case "kills":
            case "checknpckill":
            case "checknpckills":
                if (arguments.length > 0) {
                    // Combine all arguments into a single string, assuming space-separated
                    String npcNameInput = String.join(" ", arguments).toLowerCase();
                    try {
                        // Try to parse as an ID
                        int npcId = Integer.parseInt(arguments[0]);
                        int killCount = player.getNpcKillCounts().getOrDefault(npcId, 0);
                        String npcName = NPCDefinition.forId(npcId).getName();
                        player.getPacketSender().sendMessage("Kill count for " + npcName + ": " + killCount);
                    } catch (NumberFormatException e) {
                        // If not an ID, treat as a name
                        List<NPCDefinition> matchingDefs = new ArrayList<>();
                        for (int id = 0; id <= 3789; id++) {
                            try {
                                NPCDefinition def = NPCDefinition.forId(id);
                                if (def.getName().toLowerCase().startsWith(npcNameInput)) {
                                    matchingDefs.add(def);
                                }
                            } catch (Exception exception) {
                                System.err.println("Exception during kc command: " + exception.getMessage());
                                break;
                            }
                        }
                        if (matchingDefs.isEmpty()) {
                            player.getPacketSender().sendMessage("No NPCs found with the name: " + npcNameInput);
                        } else {
                            boolean empty = true;
                            for (NPCDefinition def : matchingDefs) {
                                int killCount = player.getNpcKillCounts().getOrDefault(def.getId(), 0);
                                if (killCount > 0) {
                                    empty = false;
                                    player.getPacketSender().sendMessage("Kill count for " + def.getName() + " (ID: " + def.getId() + "): " + killCount);
                                }
                            }
                            if (empty) {
                                player.getPacketSender().sendMessage("Kill count for " + npcNameInput + ": 0");
                            }
                        }
                    }
                } else {
                    player.getPacketSender().sendMessage("Please provide an NPC ID or name.");
                }
                break;  
            case "bosskillcounts":
            case "bosskillcount":
            case "bosskc":
            case "kcboss":
                // Clear all lines
                for (int i = 8144; i < 8196; i++) {
                    player.getPacketSender().sendString("", i);
                }
                for (int i = 12174; i < (12174 + 50); i++) {
                    player.getPacketSender().sendString("", i);
                }
                for (int i = 14945; i < (14945 + 100); i++) {
                    player.getPacketSender().sendString("", i);
                }
                
                player.getPacketSender().sendString("@dre@Boss Kill Counts", 8144);
                int bossLineId = 8147; // Starting line for display
                player.getPacketSender().sendString("Barrows Chests: " + player.getNpcKillCounts().getOrDefault(100000, 0), bossLineId++);
                for (Integer bossId : Constants.BOSS_NPC_IDS) {
                    int killCount = player.getNpcKillCounts().getOrDefault(bossId, 0);
                    String npcName = NPCDefinition.forId(bossId).getName();
                    player.getPacketSender().sendString(npcName + ": " + killCount, bossLineId++);
                }
                
                player.getPacketSender().showInterface(8134);
                break;
            case "slayerkillcounts":
            case "slayerkillcount":
            case "slayerkc":
            case "kcslayer":
                // Clear all lines
                for (int i = 8144; i < 8196; i++) {
                    player.getPacketSender().sendString("", i);
                }
                for (int i = 12174; i < (12174 + 50); i++) {
                    player.getPacketSender().sendString("", i);
                }
                for (int i = 14945; i < (14945 + 100); i++) {
                    player.getPacketSender().sendString("", i);
                }
                
                player.getPacketSender().sendString("@dre@Slayer Kill Counts", 8144); 
                int slayerLineId = 8147; // Starting line for display
                
                // LinkedHashMap to store cumulative kills by NPC name
                LinkedHashMap<String, Integer> nameToKills = new LinkedHashMap<>();
                
                // Populate the HashMap
                for (Integer npcId : Constants.SLAYER_NPC_IDS) {
                    String npcName = NPCDefinition.forId(npcId).getName();
                    int killCount = player.getNpcKillCounts().getOrDefault(npcId, 0);
                    nameToKills.put(npcName, nameToKills.getOrDefault(npcName, 0) + killCount);
                }
                
                // Display the results
                for (Map.Entry<String, Integer> entry : nameToKills.entrySet()) {
                    player.getPacketSender().sendString(entry.getKey() + ": " + entry.getValue(), slayerLineId++);
                }
                
                player.getPacketSender().showInterface(8134);
                break;
            case "snow":
                Calendar date = new GregorianCalendar();
                if ((date.get(Calendar.MONTH) + 1) == 12 && !player.inWild()) {
                    if (player.isSnowy) {
                        player.isSnowy = false;
                        player.getPacketSender().walkableInterface(-1);
                    } else {
                        player.isSnowy = true;
                        player.getPacketSender().walkableInterface(11877);
                        player.getPacketSender().sendMessage("Happy Holidays! Type ::snow to disable/enable! (Auto-disabling in certain area)");
                    }
                }
                break;
            case "shop":
                player.getDialogueHandler().sendDialogues(10000, 0);
                break;
            case "withdrawshop":
                player.getPacketSender().sendMessage("Shorter version: ::wshop");
            case "wshop":
                BotHandler.takeCoins(player);
                break;
            case "closeshop":
                player.getPacketSender().sendMessage("Shorter version: ::cshop");
            case "cshop":
                BotHandler.closeShop(player);
                break;
            case "wealth":
                int totalWealth = player.getPlayerAssistant().totalGold();
                player.getPacketSender().sendMessage("You currently have " + totalWealth + "gp.");
                break;
            case "gfx100":
                if (arguments.length == 0)
                    player.getPacketSender().sendMessage("Must have 1 argument: ::gfx100 80");
                else
                    player.gfx100(Integer.parseInt(arguments[0]));
                break;
            case "gfx0":
                if (arguments.length == 0)
                    player.getPacketSender().sendMessage("Must have 1 argument: ::gfx0 80");
                else
                    player.gfx0(Integer.parseInt(arguments[0]));
                break;
            case "uptime":
                player.getPacketSender().sendMessage("The server has now been online for: " + Misc.getServerUptime(GameEngine.getServerStartTime()));
                break;
            case "tele":
                if (player.connectedFrom.equals("127.0.0.1")) {
                    try {
                        if (arguments.length < 2) {
                            player.getPacketSender().sendMessage("Must specify x, y and optionally z coordinates: ::tele 3222 3218 0");
                            return;
                        }
                        if (arguments.length == 3)
                            player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                        else
                            player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), player.heightLevel);
                    } catch (Exception e) {
                        player.getPacketSender().sendMessage("Invalid coordinates");
                    }
                } else {
                    player.getPacketSender().sendMessage("Can't tele with ip " + player.connectedFrom);
                }
                break;
            case "close_interface":
                player.getPacketSender().closeAllWindows();
                break;
            case "commands":
            case "cmd":
                String[] commands = new String[]{
                        "::players",
                        "Show a list of active players",
                        "",
                        "::changepassword",
                        "Change your password",
                        "",
                        "::highscores",
                        "Get a list of current highscores",
                        "",
                        "::loc, ::pos, ::coord",
                        "Get your current world position",
                        "",
                        "::randomtoggle",
                        "Enable/Disable random events",
                        "",
                        "::debug",
                        "Enable/Disable debug information",
                        "",
                        "::togglegfx",
                        "Enable/Disable graphics rendering",
                        "",
                        "::shop",
                        "Open/Move player owned shop to your location",
                        "",
                        "::closeshop(::cshop)",
                        "Close your player owned shop",
                        "",
                        "::withdrawshop(::wshop)",
                        "Withdraw profits from player owned shop",
                        "",
                        "::togglenpckillmsgs(::togglenpckcmsgs)",
                        "Toggle regular NPC kill count message display","",
                        "",
                        "::togglebosskillmsgs(::togglebosskcmsgs)",
                        "Toggle regular Boss kill count message display","",
                        "",
                        "::toggleslayerkillmsgs(::toggleslayerkcmsgs)",
                        "Toggle regular Slayer kill count message display",
                        "",
                        "::kc(::checknpckills)",
                        "Search for your NPC kills for an NPC name or ID",
                        "",
                        "::bosskc(::toggleslayerkcmsgs)",
                        "View your boss kills",
                        "",
                        "::slayerkc(::toggleslayerkcmsgs)",
                        "View your slayer kills",
                        "",
                        "::snow",
                        "Add some snow in your mainscreen(works only in december)",
                        (Constants.VARIABLE_XP_RATE ? "\\n" + "::xprate\\n" + "Opens dialogue for the player to set/increase their XP rate." : ""),
                        (Constants.VARIABLE_XP_RATE ? "\\n" + "::checkxprate(::myxprate)\\n" + "Displays the players currently set XP rate." : ""),
                };

                // Clear all lines
                for (int i = 8144; i < 8196; i++)
                    player.getPacketSender().sendString("", i);
                for (int i = 12174; i < (12174 + 50); i++) {
                    player.getPacketSender().sendString( "", i);
                }
                for (int i = 14945; i < (14945 + 100); i++) {
                    player.getPacketSender().sendString("", i);
                }
                player.getPacketSender().sendString("@dre@Commands", 8144);

                int commandsLineNumber = 8147;
                for (String line : commands) {
                    player.getPacketSender().sendString(line, commandsLineNumber++);
                }
                player.getPacketSender().showInterface(8134);
                break;
            case "randomtoggle":
            case "togglerandom":
            case "random":
                player.randomEventsEnabled = !player.randomEventsEnabled;
                player.getPacketSender().sendMessage("You will " + (player.randomEventsEnabled ? "now" : "no longer") + " receieve random events.");
                break;
            case "debug":
            case "debugmode":
                player.debugMode = !player.debugMode;
                player.getPacketSender().sendMessage("You will " + (player.debugMode ? "now" : "no longer") + " receieve additional debug information when doing things.");
                break;
            case "highscores":
            case "highscore":
            case "hiscores":
            case "hiscore":
                for (Player p : PlayerHandler.players) {
                    if (p == null) {
                        continue;
                    }
                    PlayerSave.saveGame(p);
                    System.out.println("Saved game for " + p.playerName + ".");
                    GameEngine.lastMassSave = System.currentTimeMillis();
                }
                HighscoresHandler hs = new HighscoresHandler();
                String[] highscores = new String[]{
                        "Top 10 Total Level:",
                        hs.getRank(player, 0, "level"),
                        hs.getRank(player, 1, "level"),
                        hs.getRank(player, 2, "level"),
                        hs.getRank(player, 3, "level"),
                        hs.getRank(player, 4, "level"),
                        hs.getRank(player, 5, "level"),
                        hs.getRank(player, 6, "level"),
                        hs.getRank(player, 7, "level"),
                        hs.getRank(player, 8, "level"),
                        hs.getRank(player, 9, "level"),
                        "",
                        "Top 10 Wealthiest Players:",
                        hs.getRank(player, 0, "gold"),
                        hs.getRank(player, 1, "gold"),
                        hs.getRank(player, 2, "gold"),
                        hs.getRank(player, 3, "gold"),
                        hs.getRank(player, 4, "gold"),
                        hs.getRank(player, 5, "gold"),
                        hs.getRank(player, 6, "gold"),
                        hs.getRank(player, 7, "gold"),
                        hs.getRank(player, 8, "gold"),
                        hs.getRank(player, 9, "gold"),
                        "",
                        "Top 10 Highest Total Damage:",
                        hs.getRank(player, 0, "damage"),
                        hs.getRank(player, 1, "damage"),
                        hs.getRank(player, 2, "damage"),
                        hs.getRank(player, 3, "damage"),
                        hs.getRank(player, 4, "damage"),
                        hs.getRank(player, 5, "damage"),
                        hs.getRank(player, 6, "damage"),
                        hs.getRank(player, 7, "damage"),
                        hs.getRank(player, 8, "damage"),
                        hs.getRank(player, 9, "damage"),
                };

                // Clear all lines
                for (int i = 8144; i < 8196; i++) player.getPacketSender().sendString("", i);
                for (int i = 12174; i < (12174 + 50); i++) {
                    player.getPacketSender().sendString( "", i);
                }
                for (int i = 14945; i < (14945 + 100); i++) {
                    player.getPacketSender().sendString("", i);
                }

                player.getPacketSender().sendString("@dre@Highscores", 8144);

                int highscoresLineNumber = 8147;
                for (String line : highscores) {
                    player.getPacketSender().sendString(line, highscoresLineNumber++);
                }
                player.getPacketSender().showInterface(8134);

                break;

        }

    }

    public static void moderatorCommands(Player player, String playerCommand, String[] arguments) {
        switch (playerCommand.toLowerCase()) {
            case "kick":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::kick playername");
                        return;
                    }
                    String playerToKick = String.join(" ", arguments);
                    for (Player player2 : PlayerHandler.players) {
                        if (player2 != null) {
                            if (player2.playerName.equalsIgnoreCase(playerToKick)) {
                                Client c2 = (Client) player2;
                                player.getPacketSender().sendMessage("You have kicked " + playerToKick + ".");
                                c2.disconnected = true;
                                c2.logout(true);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Online.");
                }
                break;

            case "mute":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::mute playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    Connection.addNameToMuteList(playerToBan);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                Client c2 = (Client) PlayerHandler.players[i];
                                c2.getPacketSender().sendMessage("You have been muted by: " + player.playerName);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;

            case "ipmute":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::ipmute playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
                                player.getPacketSender().sendMessage("You have IP Muted the user: " + PlayerHandler.players[i].playerName);
                                Client c2 = (Client) PlayerHandler.players[i];
                                c2.getPacketSender().sendMessage("You have been muted by: " + player.playerName);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;

            case "unipmute":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::unipmute playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
                                player.getPacketSender().sendMessage("You have Un Ip-Muted the user: " + PlayerHandler.players[i].playerName);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;

            case "unmute":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::unmute playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    Connection.unMuteUser(playerToBan);
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "mem":
                Runtime runtime = Runtime.getRuntime();
                int totalMemK = (int) (runtime.totalMemory() / 1024L);
                int freeMemK = (int) (runtime.freeMemory() / 1024L);
                int usedMemK = (int) totalMemK - freeMemK;
                player.getPacketSender().sendMessage("Total memory: " + (totalMemK / 1024) + "MB");
                player.getPacketSender().sendMessage("Used memory: " + (usedMemK / 1024) + "MB");
                player.getPacketSender().sendMessage("Free memory: " + (freeMemK / 1024) + "MB");
                break;
            case "update":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify the amount of time in seconds: ::update 300");
                        return;
                    }
                    PlayerHandler.updateSeconds = Integer.parseInt(arguments[0]);
                    PlayerHandler.updateAnnounced = false;
                    PlayerHandler.updateRunning = true;
                    PlayerHandler.updateStartTime = System.currentTimeMillis();
                } catch (Exception e) {
                    System.out.println("Update exception: " + e);
                }
                break;
        }
    }

    public static void adminCommands(Player player, String playerCommand, String[] arguments) {
        switch (playerCommand.toLowerCase()) {
            case "clearbank":
                player.getItemAssistant().clearBank();
                break;
            case "ipban":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::ipban playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom);
                                Connection.addIpToFile(PlayerHandler.players[i].connectedFrom);
                                player.getPacketSender().sendMessage("You have IP banned the user: " + PlayerHandler.players[i].playerName + " with the host: " + PlayerHandler.players[i].connectedFrom);
                                PlayerHandler.players[i].disconnected = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "ban":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::ban playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    Connection.addNameToBanList(playerToBan);
                    Connection.addNameToFile(playerToBan);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                PlayerHandler.players[i].disconnected = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "unban":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::unban playername");
                        return;
                    }
                    String playerToBan = String.join(" ", arguments);
                    Connection.removeNameFromBanList(playerToBan);
                    player.getPacketSender().sendMessage(playerToBan + " has been unbanned.");
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "empty":
                player.getPlayerAssistant().handleEmpty();
                break;
            case "dialogue":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an id: ::dialogue id");
                    return;
                }
                int npcType = 1552;
                int dialogueID = Integer.parseInt(arguments[0]);
                player.getDialogueHandler().sendDialogues(dialogueID, npcType);
                break;
            case "interface":
            case "int":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an id: ::interface id");
                    return;
                } else if (arguments.length == 1) {
                    int interface1 = Integer.parseInt(arguments[0]);
                    player.getPacketSender().showInterface(interface1);
                    return;
                } else if (arguments.length == 2) {
                    int interface1 = Integer.parseInt(arguments[0]);
                    int interface2 = Integer.parseInt(arguments[1]);
                    player.getPacketSender().sendFrame248(interface1, interface2);
                    return;
                } else if (arguments.length == 3) {
                    int interface1 = Integer.parseInt(arguments[0]);
                    int interface2 = Integer.parseInt(arguments[1]);
                    int interface3 = Integer.parseInt(arguments[2]);
                    player.getPacketSender().sendFrame246(interface1, interface2, interface3);
                    return;
                } else {
                    player.getPacketSender().sendMessage("Too many IDs specified, maximum of 3");
                }
                break;
            case "gfx":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an id: ::gfx id");
                    return;
                }
                int gfxID = Integer.parseInt(arguments[0]);
                player.gfx0(gfxID);
                break;
            case "anim":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an id: ::anim id");
                    return;
                }
                int animationID = Integer.parseInt(arguments[0]);
                player.startAnimation(animationID);
                player.getPlayerAssistant().requestUpdates();
                break;
            case "playnpc":    
            case "pnpc":
                int newNPC = Integer.parseInt(arguments[0]);
                if (newNPC <= 10000 && newNPC >= 0) {
                    player.npcId2 = newNPC;
                    player.getPacketSender().sendMessage("Playing NPC#" + player.npcId2);
                    player.isNpc = true;
                    player.updateRequired = true;
                    player.appearanceUpdateRequired = true;
                } else {
                    player.getPacketSender().sendMessage("You must specify an id: ::pnpc id");
                }
                break;    
            case "mypos":
                player.getPacketSender().sendMessage("X: " + player.absX);
                player.getPacketSender().sendMessage("Y: " + player.absY);
                player.getPacketSender().sendMessage("H: " + player.heightLevel);
                break;
            case "bank":
                player.getPacketSender().openUpBank();
                break;
            case "teletome":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::teletome playername");
                        return;
                    }
                    String teleToMe = String.join(" ", arguments);
                    for (int i = 0; i < PlayerHandler.players.length; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(teleToMe)) {
                                Client p = (Client) PlayerHandler.players[i];
                                player.getPacketSender().sendMessage(p.playerName + " has been teleported to you.");
                                p.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel);
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player is not online.");
                }
                break;
            case "teleto":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify a player name: ::teleto playername");
                    return;
                }
                String teleTo = String.join(" ", arguments);
                for (int i = 0; i < PlayerHandler.players.length; i++) {
                    if (PlayerHandler.players[i] != null) {
                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(teleTo)) {
                            player.getPlayerAssistant().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
                            return;
                        }
                    }
                }
                player.getPacketSender().sendMessage("Could not find " + teleTo + " they must be online!");
                break;
            case "tp":
            case "teleport":
            case "to":
                if (arguments.length <= 2)
                    player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), 0);
                else
                    player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                break;
            case "up":
                player.getPacketSender().sendMessage("You are now on height level " + (player.heightLevel + 1) + ".");
                player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel + 1);
                break;
            case "up2":
                player.getPlayerAssistant().movePlayer(player.absX, player.absY - 6400, player.heightLevel);
                player.getPacketSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                break;
            case "down":
                player.getPacketSender().sendMessage("You are now on height level " + (player.heightLevel - 1) + ".");
                player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel - 1);
                break;
            case "down2":
                player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6400, player.heightLevel);
                player.getPacketSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                break;
            case "spec":
                player.specAmount = 100.0;
                break;
            case "hp":
                player.getPacketSender().sendMessage("You attributed yourself 999,999 hitpoints.");
                player.playerLevel[Constants.HITPOINTS] = 999999;
                break;
            case "pray":
                player.getPacketSender().sendMessage("You attributed yourself 999,999 prayer points.");
                player.playerLevel[Constants.PRAYER] = 999999;
                break;
            case "setlevel":
            case "level":
            case "skill":
                try {
                    if (arguments.length < 2) {
                        player.getPacketSender().sendMessage("Must specify a skill and level - ::setlevel 1 99");
                        return;
                    }
                    int skill = Integer.parseInt(arguments[0]);
                    int level = Integer.parseInt(arguments[1]);
                    if (level > 99) {
                        level = 99;
                    } else if (level < 0) {
                        level = 1;
                    }
                    player.playerXP[skill] = player.getPlayerAssistant().getXPForLevel(level) + 5;
                    player.playerLevel[skill] = player.getPlayerAssistant().getLevelForXP(player.playerXP[skill]);
                    player.getPlayerAssistant().refreshSkill(skill);
                    player.getPlayerAssistant().levelUp(skill);
                } catch (Exception e) {
                }
                break;
            case "spellbook":
                if (player.inWild()) {
                    return;
                }
                if (player.playerMagicBook == 0) {
                    player.playerMagicBook = 1;
                    player.getPacketSender().setSidebarInterface(6, 12855);
                    player.getPacketSender().sendMessage("An ancient wisdomin fills your mind.");
                    player.getPlayerAssistant().resetAutocast();
                } else if (player.playerMagicBook == 1) {
                    player.getPacketSender().setSidebarInterface(6, 1151); // modern
                    player.playerMagicBook = 0;
                    player.getPacketSender().sendMessage("You feel a drain on your memory.");
                    player.autocastId = -1;
                    player.getPlayerAssistant().resetAutocast();
                }
                break;
            case "item":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("Must specify an item id: ::item 995 1000");
                        return;
                    }
                    int newItemID = Integer.parseInt(arguments[0]);
                    int newItemAmount = arguments.length >= 2 ? Integer.parseInt(arguments[1]) : 1;
                    if (newItemID <= 10000 && newItemID >= 0) {
                        player.getItemAssistant().addItem(newItemID, newItemAmount);
                        if (player.isBusy()) {
                            player.getPacketSender().closeAllWindows();
                        }
                        // player.getPacketSender().sendMessage("You spawn " + newItemAmount + " × "+ ItemAssistant.getItemName(newItemID) + ".");
                    } else {
                        player.getPacketSender().sendMessage("No such item.");
                    }
                } catch (Exception e) {
                }
                break;
            case "master":
                for (int i = 0; i < 25; i++) {
                    player.playerLevel[i] = 99;
                    player.playerXP[i] = player.getPlayerAssistant().getXPForLevel(100);
                    player.getPlayerAssistant().refreshSkill(i);
                }
                player.getPlayerAssistant().requestUpdates();
                break;
        }
    }

    public static void developerCommands(Player player, String playerCommand, String[] arguments) {
        switch (playerCommand.toLowerCase()) {
            case "quicksong":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a quick song id: ::quicksong id");
                        return;
                    }
                    int song = Integer.parseInt(arguments[0]);
                    player.getPacketSender().sendQuickSong(song, 2); //delay of 2 to repeat at least once before returning to regular music
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Sound could not be sent.");
                }
                break;
            case "ccs":
            case "cameracutscene":
                if (arguments.length < 5) {
                    return;
                }
                player.getPlayerAssistant().sendCameraCutscene(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4])); //Test numbers
                break;
            case "ccs2":
            case "cameracutscene2":
                if (arguments.length < 5) {
                    return;
                }
                player.getPlayerAssistant().sendCameraCutscene2(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4])); //Test numbers
                break;
            case "camerashake":
                player.getPlayerAssistant().sendCameraShake(1, 9, 1, 9); //these are just test numbers
                break;
            case "cr":
            case "camerareset":
                player.getPlayerAssistant().sendCameraReset(); //Resets the camera to the normal player view
                break;
            case "clicktotele":
            case "ctt": // alias
                player.clickToTele = !player.clickToTele;
                player.getPacketSender().sendMessage("Click to teleport: " + (player.clickToTele ? "Enabled" : "Disabled"));
                break;
            case "giveadmin":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::giveadmin playername");
                        return;
                    }
                    String playerToAdmin = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                Client c2 = (Client) PlayerHandler.players[i];
                                player.getPacketSender().sendMessage("You have given " + playerToAdmin + " admin.");
                                c2.playerRights = 2;
                                c2.logout(true);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "demote":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::demote playername");
                        return;
                    }
                    String playerToAdmin = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                Client c2 = (Client) PlayerHandler.players[i];
                                player.getPacketSender().sendMessage("You have demoted " + playerToAdmin + ".");
                                c2.playerRights = 0;
                                c2.logout(true);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "givemod":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify a player name: ::givemod playername");
                        return;
                    }
                    String playerToMod = String.join(" ", arguments);
                    for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                        if (PlayerHandler.players[i] != null) {
                            if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
                                Client c2 = (Client) PlayerHandler.players[i];
                                player.getPacketSender().sendMessage("You have given " + playerToMod + " mod.");
                                c2.playerRights = 1;
                                c2.logout(true);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Player Must Be Offline.");
                }
                break;
            case "object":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an ID: ::object 1000");
                    return;
                }
                player.getPacketSender().object(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 10);
                Region.addObject(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 10, 0, false);
                break;
            case "object2":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an ID: ::object2 1000");
                    return;
                }
                player.getPacketSender().object(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 0);
                Region.addObject(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 0, false);
                break;
            case "npc":
                try {
                    if (arguments.length == 0) {
                        player.getPacketSender().sendMessage("You must specify an ID: ::npc 1000");
                        return;
                    }
                    int newNPC = Integer.parseInt(arguments[0]),
                            maxHit = NpcHandler.getNpcListCombat(newNPC) / 10,
                            attack = NpcHandler.getNpcListCombat(newNPC),
                            defence = NpcHandler.getNpcListCombat(newNPC);
                    boolean attackPlayer = NpcHandler.getNpcListCombat(newNPC) > 0;
                    if (newNPC > 0) {
                        NpcHandler.spawnNpc(player, newNPC, player.absX, player.absY, player.heightLevel, 0, NpcHandler.getNpcListHP(newNPC), maxHit, attack, defence, attackPlayer, false);
                        player.getPacketSender().sendMessage("You spawn a " + NpcHandler.getNpcListName(newNPC) + ".");
                        //player.npcSpawned = newNPC;
                    } else {
                        player.getPacketSender().sendMessage("Npc " + newNPC + " does not exist.");
                    }
                } catch (Exception e) {
                }
                break;
            case "cantattack":
                player.npcCanAttack = !player.npcCanAttack;
                player.getPacketSender().sendMessage("Npcs " + (player.npcCanAttack ? "can" : "can no longer") + " attack you.");
                break;
            case "sound":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an ID: ::sound 10");
                    return;
                }
                player.getPacketSender().sendSound(Integer.parseInt(arguments[0]), 100, 0);
                break;
            case "tutprog":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an ID: ::tutprog 10");
                    return;
                }
                player.tutorialProgress = Integer.parseInt(arguments[0]);
                ;
                break;
            case "song":
                if (arguments.length == 0) {
                    player.getPacketSender().sendMessage("You must specify an ID: ::song 10");
                    return;
                }
                int songID = Integer.parseInt(arguments[0]);
                player.getPacketSender().sendSong(songID);
                break;
            case "run":
                player.getPacketSender().sendMessage("You have refilled your run-energy!");
                player.playerEnergy = 100;
                break;
            case "runes":
                final int amount = 10000;
                final int[][] RUNES = {{554, amount}, {555, amount},
                        {556, amount}, {557, amount}, {558, amount},
                        {559, amount}, {560, amount}, {561, amount},
                        {562, amount}, {563, amount}, {564, amount},
                        {565, amount}, {566, amount}, {1963, 1},};
                for (int[] element : RUNES) {
                    int item = element[0];
                    int amountToRecieve = element[1];
                    player.getItemAssistant().addItem(item, amountToRecieve);
                }
                break;
            case "sidebars":
                player.getPlayerAssistant().sendSidebars();
                break;
        }
    }
}
