package redone.net.packets.impl;
 
import redone.Connection;
import redone.Constants;
import redone.game.items.ItemAssistant;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;
import redone.util.GameLogger;
import redone.util.Misc;
import redone.world.clip.Region;
 
public class Commands implements PacketType {
 
        @Override
        public void processPacket(Client player, int packetType, int packetSize) {
                String playerCommand = player.getInStream().readString();
                if ((playerCommand.startsWith("ban") || playerCommand.startsWith("ip") || playerCommand.startsWith("mute") || playerCommand.startsWith("un")) && player.playerRights > 0 && player.playerRights < 4) {
        			GameLogger.writeLog(player.playerName, "commands", player.playerName + " used command: " + playerCommand + "");
        		}
                if (player.playerRights >= 0) {
                        playerCommands(player, playerCommand);
                }
                if (player.membership || player.playerRights > 1) {
                        donatorCommands(player, playerCommand);
                }
                if (player.playerRights >= 1) {
                        moderatorCommands(player, playerCommand);
                }
                if (player.playerRights >= 2 && player.playerRights < 4) {
                        adminCommands(player, playerCommand);
                }
                if (player.playerRights == 3) {
                        developerCommands(player, playerCommand);
                }
        }
       
        public static void playerCommands(Client player, String playerCommand) {
                if (playerCommand.equalsIgnoreCase("players")) {
                        if (PlayerHandler.getPlayerCount() > 1) {
                                player.getActionSender().sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online.");
                        } else {
                                player.getActionSender().sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online.");
                        }
                }
               
                if (playerCommand.contains("clip") && player.playerRights < 2) {
                        return;
                }
 
        }
       
        public static void donatorCommands(Client player, String playerCommand) {
               
        }
       
        public static void moderatorCommands(Client player, String playerCommand) {
        	if (playerCommand.startsWith("yell")) {
        		for (int j = 0; j < PlayerHandler.players.length; j++) {
        			if (PlayerHandler.players[j] != null) {
        				Client c2 = (Client)PlayerHandler.players[j];
						if (player.playerRights == 1) {
							c2.getActionSender().sendMessage("@blu@[Moderator]@bla@"+ Misc.optimizeText(player.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
						} else if (player.playerRights == 2) {
							c2.getActionSender().sendMessage("@gre@[Administator]@bla@"+ Misc.optimizeText(player.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
						} else if (player.playerRights == 3) {
							c2.getActionSender().sendMessage("@red@[Owner]@bla@"+ Misc.optimizeText(player.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
						}
					}
				}
			}
              
                if (playerCommand.startsWith("mute")) {
                        try {  
                                String playerToBan = playerCommand.substring(5);
                                Connection.addNameToMuteList(playerToBan);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                        Client c2 = (Client)PlayerHandler.players[i];
                                                        c2.getActionSender().sendMessage("You have been muted by: " + player.playerName);
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
 
                if (playerCommand.startsWith("ipmute")) {
                        try {  
                                String playerToBan = playerCommand.substring(7);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                        Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
                                                        player.getActionSender().sendMessage("You have IP Muted the user: "+PlayerHandler.players[i].playerName);
                                                        Client c2 = (Client)PlayerHandler.players[i];
                                                        c2.getActionSender().sendMessage("You have been muted by: " + player.playerName);
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
 
                if (playerCommand.startsWith("unipmute")) {
                        try {  
                                String playerToBan = playerCommand.substring(9);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                        Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
                                                        player.getActionSender().sendMessage("You have Un Ip-Muted the user: "+PlayerHandler.players[i].playerName);
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
 
                if (playerCommand.startsWith("unmute")) {
                        try {  
                                String playerToBan = playerCommand.substring(7);
                                Connection.unMuteUser(playerToBan);
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
        }
       
        public static void adminCommands(Client player, String playerCommand) {
        	
        		if (playerCommand.equalsIgnoreCase("clearbank")) {
        			player.getItemAssistant().clearBank();
        		}
               
                if (playerCommand.startsWith("ipban")) { // use as ::ipban name
                        try {
                                String playerToBan = playerCommand.substring(6);
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
                }
 
                if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') { // use as ::ban name
                        try {  
                                String playerToBan = playerCommand.substring(4);
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
                }
 
                if (playerCommand.startsWith("unban")) {
                        try {  
                                String playerToBan = playerCommand.substring(6);
                                Connection.removeNameFromBanList(playerToBan);
                                player.getActionSender().sendMessage(playerToBan + " has been unbanned.");
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }
                }
               
                if (playerCommand.equalsIgnoreCase("empty")) {
                        player.getPlayerAssistant().handleEmpty();
                }
               
                if (playerCommand.startsWith("dialogue")) {
                        int npcType = 1552;
                        int id = Integer.parseInt(playerCommand.split(" ")[1]);
                        player.getDialogueHandler().sendDialogues(id, npcType);
                }
 
                if (playerCommand.startsWith("interface")) {
                        String[] args = playerCommand.split(" ");
                        player.getPlayerAssistant().showInterface(Integer.parseInt(args[1]));
                }
 
                if (playerCommand.startsWith("gfx")) {
                	   String[] args = playerCommand.split(" ");
                	   player.gfx0(Integer.parseInt(args[1]));
                	  }
 
                if (playerCommand.startsWith("anim")) {
                        String[] args = playerCommand.split(" ");
                        player.startAnimation(Integer.parseInt(args[1]));
                        player.getPlayerAssistant().requestUpdates();
                }
               
                if (playerCommand.equalsIgnoreCase("mypos")) {
                        player.getActionSender().sendMessage("X: " + player.absX);
                        player.getActionSender().sendMessage("Y: " + player.absY);
                        player.getActionSender().sendMessage("H: " + player.heightLevel);
                }
 
                if (playerCommand.startsWith("bank")) {
                        player.getPlayerAssistant().openUpBank();
                }
 

                if (playerCommand.startsWith("xteletome")) {
                    try {
                        String teleTo = playerCommand.substring(10);
                        for (int i = 0; i < PlayerHandler.players.length; i++) {
                            if (PlayerHandler.players[i] != null) {
                                if (PlayerHandler.players[i].playerName.equalsIgnoreCase(teleTo)) {
                                    Client p = (Client) PlayerHandler.players[i];
                                        player.getActionSender().sendMessage(p.playerName + " has been teleported to you.");
                                        p.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel);
                                	}
                                }
                            }
                    } catch (Exception e) {
                        player.getActionSender().sendMessage("Player is not online.");
                    }
                }
        
            if (playerCommand.startsWith("xteleto")) {
                String name = playerCommand.substring(8);
                for (int i = 0; i < PlayerHandler.players.length; i++) {
                    if (PlayerHandler.players[i] != null) {
                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
                            player.getPlayerAssistant().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
                        }
                    }
                }
            }
                if (playerCommand.startsWith("tele")) {
                        String[] arg = playerCommand.split(" ");
                        if (arg.length > 3) {
                                player.getPlayerAssistant().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
                        } else if (arg.length == 3) {
                                player.getPlayerAssistant().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), player.heightLevel);
                        }
                }
               
                if (playerCommand.equalsIgnoreCase("up")) {
                        player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel + 1);
                        player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                }
 
                if (playerCommand.equalsIgnoreCase("down2")) {
                        player.getPlayerAssistant().movePlayer(player.absX, player.absY + 6400, player.heightLevel);
                }
 
                if (playerCommand.equalsIgnoreCase("down")) {
                        player.getPlayerAssistant().movePlayer(player.absX, player.absY, player.heightLevel - 1);
                        player.getActionSender().sendMessage("You are now on height level " + player.heightLevel + ".");
                }
 
                if (playerCommand.equalsIgnoreCase("up2")) {
                        player.getPlayerAssistant().movePlayer(player.absX, player.absY - 6400, player.heightLevel);
                }
               
                if (playerCommand.equals("spec")) {
                        player.specAmount = 100.0;
                }
               
                if (playerCommand.startsWith("setlevel")) {
                        try {
                                String[] args = playerCommand.split(" ");
                                int skill = Integer.parseInt(args[1]);
                                int level = Integer.parseInt(args[2]);
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
                }
               
                if (playerCommand.equalsIgnoreCase("spellbook")) {
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
                                player.getActionSender().sendMessage(   "You feel a drain on your memory.");
                                player.autocastId = -1;
                                player.getPlayerAssistant().resetAutocast();
                        }
                }
 
                if (playerCommand.startsWith("item")) {
                        try {
                                String[] args = playerCommand.split(" ");
                                if (args.length == 3) {
                                        int newItemID = Integer.parseInt(args[1]);
                                        int newItemAmount = Integer.parseInt(args[2]);
                                        if (newItemID <= 10000 && newItemID >= 0) {
                                                player.getItemAssistant().addItem(newItemID, newItemAmount);
                                                if (player.isBusy()) {
                        							player.getPlayerAssistant().closeAllWindows();
                        						}
                                                player.getActionSender().sendMessage("You spawn (" + newItemAmount + ") "+ ItemAssistant.getItemName(newItemID) + ".");
                                        } else {
                                                player.getActionSender().sendMessage("No such item.");
                                        }
                                } else {
                                        player.getActionSender().sendMessage("Use as ::item 995 200");
                                }
                        } catch (Exception e) {
                        }
                }
 
                if (playerCommand.equalsIgnoreCase("master")) {
                        for (int i = 0; i < 25; i++) {
                                player.playerLevel[i] = 99;
                                player.playerXP[i] = player.getPlayerAssistant().getXPForLevel(100);
                                player.getPlayerAssistant().refreshSkill(i);
                        }
                        player.getPlayerAssistant().requestUpdates();
                }
 
               
        }
 
        public static void developerCommands(Client player, String playerCommand) {
               
                if (playerCommand.startsWith("giveadmin")) {
                        try {  
                                String playerToAdmin = playerCommand.substring(10);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                                        Client c2 = (Client)PlayerHandler.players[i];
                                                        player.getActionSender().sendMessage("You have given " + playerToAdmin + " admin.");
                                                        c2.playerRights = 2;
                                                        c2.logout();
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
               
                if (playerCommand.startsWith("demote")) {
                        try {  
                                String playerToAdmin = playerCommand.substring(7);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
                                                        Client c2 = (Client)PlayerHandler.players[i];
                                                        player.getActionSender().sendMessage("You have demoted " + playerToAdmin + ".");
                                                        c2.playerRights = 0;
                                                        c2.logout();
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
               
                if (playerCommand.startsWith("givemod")) {
                        try {  
                                String playerToMod = playerCommand.substring(8);
                                for(int i = 0; i < Constants.MAX_PLAYERS; i++) {
                                        if(PlayerHandler.players[i] != null) {
                                                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
                                                        Client c2 = (Client)PlayerHandler.players[i];
                                                        player.getActionSender().sendMessage("You have given " + playerToMod + " mod.");
                                                        c2.playerRights = 1;
                                                        c2.logout();
                                                        break;
                                                }
                                        }
                                }
                        } catch(Exception e) {
                                player.getActionSender().sendMessage("Player Must Be Offline.");
                        }                      
                }
               
                if (playerCommand.startsWith("object")) {
                        String[] args = playerCommand.split(" ");
                        player.getActionSender().object(Integer.parseInt(args[1]), player.absX, player.absY, player.heightLevel, 0, 10);
                        Region.addObject(Integer.parseInt(args[1]), player.absX, player.absY, player.heightLevel, 10, 0, false);
                }
                
                if (playerCommand.startsWith("object2")) {
                    String[] args = playerCommand.split(" ");
                    player.getActionSender().object(Integer.parseInt(args[1]), player.absX, player.absY, player.heightLevel, 0, 0);
                    Region.addObject(Integer.parseInt(args[1]), player.absX, player.absY, player.heightLevel, 0, 0, false);
            }
 
                if (playerCommand.startsWith("npc")) {
                        try {
                                int newNPC = Integer.parseInt(playerCommand.substring(4)), maxHit = NpcHandler.getNpcListCombat(newNPC) / 10,
                                attack = NpcHandler.getNpcListCombat(newNPC), defence = NpcHandler.getNpcListCombat(newNPC);
                                boolean attackPlayer = NpcHandler.getNpcListCombat(newNPC) > 0;
                                if (newNPC > 0) {
                                        NpcHandler.spawnNpc(player, newNPC, player.absX, player.absY, player.heightLevel, 0, NpcHandler.getNpcListHP(newNPC), maxHit, attack, defence, attackPlayer, false);
                                        player.getActionSender().sendMessage("You spawn a " + NpcHandler.getNpcListName(newNPC).toLowerCase() + ".");
                                        //player.npcSpawned = newNPC;
                                } else {
                                        player.getActionSender().sendMessage("Npc " + newNPC + " does not exist.");
                                }
                        } catch (Exception e) {
 
                        }
                }
               
                if (playerCommand.equalsIgnoreCase("cantAttack")) {
                        if (player.npcCanAttack == true) {
                                player.getActionSender().sendMessage("Npcs can no longer attack you.");
                                player.npcCanAttack = false;
                        } else if (player.npcCanAttack == false) {
                                player.getActionSender().sendMessage("Npcs can attack you again.");
                                player.npcCanAttack = true;
                        }
                }
                
                if (playerCommand.startsWith("sound")) {
                	 String[] args = playerCommand.split(" ");
                	 player.getActionSender().sendSound(Integer.parseInt(args[1]), 100, 0);
                }
                
            	if (playerCommand.startsWith("tutprog")) {
        			String[] args = playerCommand.split(" ");
        			int id = Integer.parseInt(args[1]);
        			player.tutorialProgress = id;
        		}
            	
            	if (playerCommand.startsWith("song")) {
        			String[] args = playerCommand.split(" ");
        			int id = Integer.parseInt(args[1]);
        			player.getActionSender().sendSong(id);
        		}
            	
        		if (playerCommand.equalsIgnoreCase("run")) {
        			player.getActionSender().sendMessage("You have refilled your run-energy!");
        			player.playerEnergy = 100;
        		}

        		if (playerCommand.equalsIgnoreCase("runes")) {
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
        		}

        		if (playerCommand.startsWith("sidebars")) {
        			player.getPlayerAssistant().sendSidebars();
        		}
 
                if (playerCommand.startsWith("update")) {
                        try {
                                String[] args = playerCommand.split(" ");
                                if (args.length == 2) {
                                        int seconds = Integer.parseInt(args[1]);
                                        PlayerHandler.updateSeconds = seconds;
                                        PlayerHandler.updateAnnounced = false;
                                        PlayerHandler.updateRunning = true;
                                        PlayerHandler.updateStartTime = System.currentTimeMillis();
                                } else {
                                        player.getActionSender().sendMessage("Use as ::update (seconds)");
                                }
                        } catch (Exception e) {
                        }
                }
               
        }
 
               
}