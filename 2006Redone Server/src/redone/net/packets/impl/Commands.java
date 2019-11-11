package redone.net.packets.impl;

import redone.Connection;
import redone.Constants;
import redone.Server;
import redone.game.items.ItemAssistant;
import redone.game.npcs.NpcHandler;
import redone.game.players.*;
import redone.net.packets.PacketType;
import redone.util.GameLogger;
import redone.util.Misc;
import redone.world.clip.Region;

import java.util.Arrays;

import static redone.game.content.combat.magic.MagicTeleports.LUMBRIDGE_X;
import static redone.game.content.combat.magic.MagicTeleports.LUMBRIDGE_Y;

public class Commands implements PacketType {

        @Override
        public void processPacket(Client player, int packetType, int packetSize) {
                String[] messageArr = player.getInStream().readString().split(" ");
                String playerCommand = messageArr[0];
                String[] commandArguments = Arrays.copyOfRange(messageArr, 1, messageArr.length);
                if ((playerCommand.startsWith("ban") || playerCommand.startsWith("ip") || playerCommand.startsWith("mute") || playerCommand.startsWith("un")) && player.playerRights > 0 && player.playerRights < 4) {
                        GameLogger.writeLog(player.playerName, "commands", player.playerName + " used command: " + playerCommand);
                }
                if (player.playerRights >= 0) {
                        playerCommands(player, playerCommand, commandArguments);
                }
                if (player.membership || player.playerRights > 1) {
                        donatorCommands(player, playerCommand, commandArguments);
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

        public static void playerCommands(Client player, String playerCommand, String[] arguments) {
                switch (playerCommand.toLowerCase())
                {
                        case "bank":
                                player.getPlayerAssistant().openUpBank();
                                break;
                        case "claimvote":
                                if(!Server.ersSecret.equals("")) {
                                        final String playerName = player.playerName;

                                        com.everythingrs.vote.Vote.service.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                        try {
                                                                int currentPoints = player.votePoints;
                                                                com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward(Server.ersSecret, playerName, "1", "all");
                                                                if (reward[0].message != null) {
                                                                        player.getActionSender().sendMessage(reward[0].message);
                                                                        return;
                                                                }
                                                                player.votePoints = (currentPoints + reward[0].give_amount);
                                                                //player.getActionSender().sendMessage("Thank you for voting! You now have " + reward[0].vote_points + " vote points.");
                                                                player.getActionSender().sendMessage(
                                                                        "Thank you for voting! You now have " + player.votePoints + " vote points.");
                                                        } catch (Exception e) {
                                                                player.getActionSender().sendMessage("Api Services are currently offline. Please check back shortly");
                                                                e.printStackTrace();
                                                        }
                                                }

                                        });
                                } else {
                                        player.getActionSender().sendMessage("Voting Is Not Enabled");
                                }
                                break;
                        case "coords":
                        case "coord":
                        case "pos":
                                player.getActionSender().sendMessage("Your coords are [" + player.absX + "," + player.absY + "]");
                                break;
                        case "null":
                                break;
                        case "players":
                                if (PlayerHandler.getPlayerCount() != 1) {
                                        player.getActionSender().sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online.");
                                } else {
                                        player.getActionSender().sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online.");
                                }
                                break;
                        case "wealth":
                                int totalWealth = player.getPlayerAssistant().totalGold();
                                player.getActionSender().sendMessage("You currently have " + totalWealth + "gp.");
                                break;
                        case "gfx100":
                                if (arguments.length == 0)
                                        player.getActionSender().sendMessage("Must have 1 argument: ::gfx100 80");
                                else
                                        player.gfx100(Integer.parseInt(arguments[0]));
                                break;
                        case "gfx0":
                                if (arguments.length == 0)
                                        player.getActionSender().sendMessage("Must have 1 argument: ::gfx0 80");
                                else
                                        player.gfx0(Integer.parseInt(arguments[0]));
                                break;
                        case "tele":
                                if (player.connectedFrom.equals("127.0.0.1")) {
                                        try {
                                                if (arguments.length < 2) {
                                                        player.getActionSender().sendMessage("Must specify x, y and optionally z coordinates: ::tele 3222 3218 0");
                                                        return;
                                                }
                                                if (arguments.length == 3)
                                                        player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                                                else
                                                        player.getPlayerAssistant().movePlayer(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), player.heightLevel);
                                        } catch (Exception e) {
                                                player.getActionSender().sendMessage("Invalid coordinates");
                                        }
                                } else {
                                        player.getActionSender().sendMessage("Can't tele with ip " + player.connectedFrom);
                                }
                                break;
                        case "close_interface":
                                player.getPlayerAssistant().closeAllWindows();
                                break;
                        case "commands":
                                player.getActionSender().sendMessage("::players, ::highscores, ::loc, ::stuck, ::randomtoggle, ::debug");
                                break;
                        case "loc":
                                player.getActionSender().sendMessage(player.absX + "," + player.absY);
                                break;
                        case "stuck":
                                player.getPlayerAssistant().startTeleport(LUMBRIDGE_X, LUMBRIDGE_Y, 0, "modern");
                                player.getActionSender().sendMessage("How did you manage that one..");
                                player.gfx100(80);
                                player.startAnimation(404);
                                break;
                        case "randomtoggle":
                        case "togglerandom":
                                player.randomEventsEnabled = !player.randomEventsEnabled;
                                player.getActionSender().sendMessage("You will " + (player.debugMode ? "now" : "no longer") + " receieve random events.");
                                break;
                        case "debug":
                        case "debugmode":
                                player.debugMode = !player.debugMode;
                                player.getActionSender().sendMessage("You will " + (player.debugMode ? "now" : "no longer") + " receieve additional debug information when doing things.");
                                break;
                        case "highscores":
                                for (Player p : PlayerHandler.players) {
                                        if (p == null) {
                                                continue;
                                        }
                                        PlayerSave.saveGame((Client) p);
                                        System.out.println("Saved game for " + p.playerName
                                                + ".");
                                        Server.lastMassSave = System.currentTimeMillis();
                                }
                                HighscoresHandler hs = new HighscoresHandler();
                                String[] highscores = new String[]{
                                        "@dre@Highscores",
                                        "",
                                        "Top 5 Total Level:",
                                        hs.getRank(player, 0, "level"), hs.getRank(player,1, "level"), hs.getRank(player,2, "level"), hs.getRank(player,3, "level"), hs.getRank(player,4, "level"),
                                        "",
                                        "Top 5 Wealthiest Players:",
                                        hs.getRank(player,0, "gold"), hs.getRank(player,1, "gold"), hs.getRank(player,2, "gold"), hs.getRank(player,3, "gold"), hs.getRank(player, 4, "gold"),
                                        "",
                                        "Top 5 Highest Total Damage:",
                                        hs.getRank(player,0, "damage"), hs.getRank(player,1, "damage"), hs.getRank(player,2, "damage"), hs.getRank(player, 3, "damage"), hs.getRank(player, 4, "damage"),
                                };

                                for (int i = 8144; i < 8245; i++) {
                                        player.getPlayerAssistant().sendFrame126("", i);
                                }

                                for (int i = 8144; i < 8144 + highscores.length; i++) {
                                        player.getPlayerAssistant().sendFrame126(highscores[i - 8144], i+3);
                                }
                                player.getPlayerAssistant().showInterface(8134);

                                break;

                }

        }

        public static void donatorCommands(Client player, String playerCommand, String[] arguments) {

        }

        public static void moderatorCommands(Client player, String playerCommand, String[] arguments) {
                switch (playerCommand.toLowerCase()) {
                        case "kick":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::kick playername");
                                                return;
                                        }
                                        String playerToKick = String.join(" ", arguments);
                                        for(Player player2 : PlayerHandler.players) {
                                                if(player2 != null) {
                                                        if(player2.playerName.equalsIgnoreCase(playerToKick)) {
                                                                Client c2 = (Client)player2;
                                                                player.getActionSender().sendMessage("You have kicked " + playerToKick + ".");
                                                                c2.disconnected = true;
                                                                c2.logout(true);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Online.");
                                }
                                break;
                        case "yell":
                                for (int j = 0; j < PlayerHandler.players.length; j++) {
                                        if (PlayerHandler.players[j] != null) {
                                                Client c2 = (Client) PlayerHandler.players[j];
                                                if (player.playerRights == 1) {
                                                        c2.getActionSender().sendMessage("@blu@[Moderator] @bla@" + Misc.optimizeText(player.playerName) + ": " + Misc.optimizeText(String.join(" ", arguments)) + "");
                                                } else if (player.playerRights == 2) {
                                                        c2.getActionSender().sendMessage("@gre@[Administator] @bla@" + Misc.optimizeText(player.playerName) + ": " + Misc.optimizeText(String.join(" ", arguments)) + "");
                                                } else if (player.playerRights == 3) {
                                                        c2.getActionSender().sendMessage("@red@[Developer] @bla@" + Misc.optimizeText(player.playerName) + ": " + Misc.optimizeText(String.join(" ", arguments)) + "");
                                                }
                                        }
                                }
                                break;

                        case "mute":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::mute playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        Connection.addNameToMuteList(playerToBan);
                                        for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if (PlayerHandler.players[i] != null) {
                                                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                Client c2 = (Client) PlayerHandler.players[i];
                                                                c2.getActionSender().sendMessage("You have been muted by: " + player.playerName);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch (Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;

                        case "ipmute":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::ipmute playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if (PlayerHandler.players[i] != null) {
                                                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
                                                                player.getActionSender().sendMessage("You have IP Muted the user: " + PlayerHandler.players[i].playerName);
                                                                Client c2 = (Client) PlayerHandler.players[i];
                                                                c2.getActionSender().sendMessage("You have been muted by: " + player.playerName);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch (Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;

                        case "unipmute":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::unipmute playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if (PlayerHandler.players[i] != null) {
                                                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
                                                                player.getActionSender().sendMessage("You have Un Ip-Muted the user: " + PlayerHandler.players[i].playerName);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch (Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;

                        case "unmute":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::unmute playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        Connection.unMuteUser(playerToBan);
                                } catch (Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                }
        }

        public static void adminCommands(Client player, String playerCommand, String[] arguments) {
                switch (playerCommand.toLowerCase()) {
                        case "clearbank":
                                player.getItemAssistant().clearBank();
                                break;
                        case "ipban":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::ipban playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if(PlayerHandler.players[i] != null) {
                                                        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom);
                                                                Connection.addIpToFile(PlayerHandler.players[i].connectedFrom);
                                                                player.getActionSender().sendMessage("You have IP banned the user: "+PlayerHandler.players[i].playerName+" with the host: "+PlayerHandler.players[i].connectedFrom);
                                                                PlayerHandler.players[i].disconnected = true;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "ban":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::ban playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        Connection.addNameToBanList(playerToBan);
                                        Connection.addNameToFile(playerToBan);
                                        for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if(PlayerHandler.players[i] != null) {
                                                        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                PlayerHandler.players[i].disconnected = true;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "unban":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::unban playername");
                                                return;
                                        }
                                        String playerToBan = String.join(" ", arguments);
                                        Connection.removeNameFromBanList(playerToBan);
                                        player.getActionSender().sendMessage(playerToBan + " has been unbanned.");
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "empty":
                                player.getPlayerAssistant().handleEmpty();
                                break;
                        case "dialogue":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an id: ::dialogue id");
                                        return;
                                }
                                int npcType = 1552;
                                int dialogueID = Integer.parseInt(arguments[0]);
                                player.getDialogueHandler().sendDialogues(dialogueID, npcType);
                                break;
                        case "interface":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an id: ::interface id");
                                        return;
                                }
                                int interfaceID = Integer.parseInt(arguments[0]);
                                player.getPlayerAssistant().showInterface(interfaceID);
                                break;
                        case "gfx":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an id: ::gfx id");
                                        return;
                                }
                                int gfxID = Integer.parseInt(arguments[0]);
                                player.gfx0(gfxID);
                                break;
                        case "anim":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an id: ::anim id");
                                        return;
                                }
                                int animationID = Integer.parseInt(arguments[0]);
                                player.startAnimation(animationID);
                                player.getPlayerAssistant().requestUpdates();
                                break;
                        case "mypos":
                                player.getActionSender().sendMessage("X: " + player.absX);
                                player.getActionSender().sendMessage("Y: " + player.absY);
                                player.getActionSender().sendMessage("H: " + player.heightLevel);
                                break;
                        case "bank":
                                player.getPlayerAssistant().openUpBank();
                                break;
                        case "xteletome":
                        case "teletome":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::teletome playername");
                                                return;
                                        }
                                        String teleToMe = String.join(" ", arguments);
                                        for (int i = 0; i < PlayerHandler.players.length; i++) {
                                                if (PlayerHandler.players[i] != null) {
                                                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(teleToMe)) {
                                                                Client p = (Client) PlayerHandler.players[i];
                                                                player.getActionSender().sendMessage(p.playerName + " has been teleported to you.");
                                                                p.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel);
                                                        }
                                                }
                                        }
                                } catch (Exception e) {
                                        player.getActionSender().sendMessage("Player is not online.");
                                }
                                break;
                        case "xteleto":
                        case "teleto":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify a player name: ::teleto playername");
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
                                player.getActionSender().sendMessage("Could not find " + teleTo + " they must be online!");
                                break;
                        case "up":
                                player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel + 1);
                                player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                                break;
                        case "up2":
                                player.getPlayerAssistant().movePlayer(player.absX, player.absY - 6400, player.heightLevel);
                                player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                                break;
                        case "down":
                                player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel - 1);
                                player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                                break;
                        case "down2":
                                player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6400, player.heightLevel);
                                player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                                break;
                        case "spec":
                                player.specAmount = 100.0;
                                break;
                        case "setlevel":
                                try {
                                        if (arguments.length < 2) {
                                                player.getActionSender().sendMessage("Must specify a skill and level: ::setlevel 1 99");
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
                                } catch (Exception e) {}
                                break;
                        case "spellbook":
                                if (player.inWild()) {
                                        return;
                                }
                                if (player.playerMagicBook == 0) {
                                        player.playerMagicBook = 1;
                                        player.getActionSender().setSidebarInterface(6, 12855);
                                        player.getActionSender().sendMessage("An ancient wisdomin fills your mind.");
                                        player.getPlayerAssistant().resetAutocast();
                                } else if (player.playerMagicBook == 1) {
                                        player.getActionSender().setSidebarInterface(6, 1151); // modern
                                        player.playerMagicBook = 0;
                                        player.getActionSender().sendMessage("You feel a drain on your memory.");
                                        player.autocastId = -1;
                                        player.getPlayerAssistant().resetAutocast();
                                }
                                break;
                        case "item":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("Must specify an item id: ::item 995 1000");
                                                return;
                                        }
                                        int newItemID = Integer.parseInt(arguments[0]);
                                        int newItemAmount = arguments[1] != null ? Integer.parseInt(arguments[1]) : 1;
                                        if (newItemID <= 10000 && newItemID >= 0) {
                                                player.getItemAssistant().addItem(newItemID, newItemAmount);
                                                if (player.isBusy()) {
                                                        player.getPlayerAssistant().closeAllWindows();
                                                }
                                                player.getActionSender().sendMessage("You spawn " + newItemAmount + " × "+ ItemAssistant.getItemName(newItemID) + ".");
                                        } else {
                                                player.getActionSender().sendMessage("No such item.");
                                        }
                                } catch (Exception e) {}
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

        public static void developerCommands(Client player, String playerCommand, String[] arguments) {
                switch (playerCommand.toLowerCase()) {
                        case "clicktotele":
                        case "ctt": // alias
                                player.clickToTele = !player.clickToTele;
                                player.getActionSender().sendMessage("Click to teleport: " + (player.clickToTele ? "Enabled" : "Disabled"));
                                break;
                        case "giveadmin":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::giveadmin playername");
                                                return;
                                        }
                                        String playerToAdmin = String.join(" ", arguments);
                                        for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if(PlayerHandler.players[i] != null) {
                                                        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                                                Client c2 = (Client)PlayerHandler.players[i];
                                                                player.getActionSender().sendMessage("You have given " + playerToAdmin + " admin.");
                                                                c2.playerRights = 2;
                                                                c2.logout(true);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "demote":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::demote playername");
                                                return;
                                        }
                                        String playerToAdmin = String.join(" ", arguments);
                                        for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if(PlayerHandler.players[i] != null) {
                                                        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                                                Client c2 = (Client)PlayerHandler.players[i];
                                                                player.getActionSender().sendMessage("You have demoted " + playerToAdmin + ".");
                                                                c2.playerRights = 0;
                                                                c2.logout(true);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "givemod":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify a player name: ::givemod playername");
                                                return;
                                        }
                                        String playerToMod = String.join(" ", arguments);
                                        for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                                if(PlayerHandler.players[i] != null) {
                                                        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
                                                                Client c2 = (Client)PlayerHandler.players[i];
                                                                player.getActionSender().sendMessage("You have given " + playerToMod + " mod.");
                                                                c2.playerRights = 1;
                                                                c2.logout(true);
                                                                break;
                                                        }
                                                }
                                        }
                                } catch(Exception e) {
                                        player.getActionSender().sendMessage("Player Must Be Offline.");
                                }
                                break;
                        case "object":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an ID: ::object 1000");
                                        return;
                                }
                                player.getActionSender().object(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 10);
                                Region.addObject(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 10, 0, false);
                                break;
                        case "object2":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an ID: ::object2 1000");
                                        return;
                                }
                                player.getActionSender().object(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 0);
                                Region.addObject(Integer.parseInt(arguments[0]), player.absX, player.absY, player.heightLevel, 0, 0, false);
                                break;
                        case "npc":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify an ID: ::npc 1000");
                                                return;
                                        }
                                        int newNPC = Integer.parseInt(arguments[0]),
                                                maxHit = NpcHandler.getNpcListCombat(newNPC) / 10,
                                                attack = NpcHandler.getNpcListCombat(newNPC),
                                                defence = NpcHandler.getNpcListCombat(newNPC);
                                        boolean attackPlayer = NpcHandler.getNpcListCombat(newNPC) > 0;
                                        if (newNPC > 0) {
                                                NpcHandler.spawnNpc(player, newNPC, player.absX, player.absY, player.heightLevel, 0, NpcHandler.getNpcListHP(newNPC), maxHit, attack, defence, attackPlayer, false);
                                                player.getActionSender().sendMessage("You spawn a " + NpcHandler.getNpcListName(newNPC).toLowerCase() + ".");
                                                //player.npcSpawned = newNPC;
                                        } else {
                                                player.getActionSender().sendMessage("Npc " + newNPC + " does not exist.");
                                        }
                                } catch (Exception e) {}
                                break;
                        case "cantattack":
                                player.npcCanAttack = !player.npcCanAttack;
                                player.getActionSender().sendMessage("Npcs " + (player.npcCanAttack ? "can" : "can no longer") + " attack you.");
                                break;
                        case "sound":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an ID: ::sound 10");
                                        return;
                                }
                                player.getActionSender().sendSound(Integer.parseInt(arguments[0]), 100, 0);
                                break;
                        case "tutprog":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an ID: ::tutprog 10");
                                        return;
                                }
                                player.tutorialProgress = Integer.parseInt(arguments[0]);;
                                break;
                        case "song":
                                if (arguments.length == 0) {
                                        player.getActionSender().sendMessage("You must specify an ID: ::song 10");
                                        return;
                                }
                                int songID = Integer.parseInt(arguments[0]);
                                player.getActionSender().sendSong(songID);
                                break;
                        case "run":
                                player.getActionSender().sendMessage("You have refilled your run-energy!");
                                player.playerEnergy = 100;
                                break;
                        case "runes":
                                final int amount = 10000;
                                final int[][] RUNES = { { 554, amount }, { 555, amount },
                                        { 556, amount }, { 557, amount }, { 558, amount },
                                        { 559, amount }, { 560, amount }, { 561, amount },
                                        { 562, amount }, { 563, amount }, { 564, amount },
                                        { 565, amount }, { 566, amount }, { 1963, 1 }, };
                                for (int[] element : RUNES) {
                                        int item = element[0];
                                        int amountToRecieve = element[1];
                                        player.getItemAssistant().addItem(item, amountToRecieve);
                                }
                                break;
                        case "sidebars":
                                player.getPlayerAssistant().sendSidebars();
                                break;
                        case "update":
                                try {
                                        if (arguments.length == 0) {
                                                player.getActionSender().sendMessage("You must specify the amount of time in seconds: ::update 300");
                                                return;
                                        }
                                        int seconds = Integer.parseInt(arguments[0]);
                                        PlayerHandler.updateSeconds = seconds;
                                        PlayerHandler.updateAnnounced = false;
                                        PlayerHandler.updateRunning = true;
                                        PlayerHandler.updateStartTime = System.currentTimeMillis();
                                } catch (Exception e) {}
                                break;
                }
        }


}