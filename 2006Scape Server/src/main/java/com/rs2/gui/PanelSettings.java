package com.rs2.gui;

import com.rs2.Connection;
import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;

import javax.swing.*;
import java.util.ArrayList;

public class PanelSettings {

    public static ArrayList<String> npcList = new ArrayList<String>();
    public String[][] MESSAGE_COLORS = {
            {"Red", "@red@"},
            {"Blue", "@blu@"},
            {"Green", "@gre@"},
            {"Yellow", "@yel@"},
            {"Magenta", "@mag@"},
            {"Orange", "@or1@"},
            {"Dark Red", "@dre@"},
            {"Dark Blue", "@dbl@"}};
    private ControlPanel p;

    public PanelSettings(ControlPanel p) {
        this.p = p;
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer strBuff = new StringBuffer();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    public String getSelectedPlayer() {
        return p.ENTITY_LIST.getSelectedValue().toString();
    }

    public boolean inList(String id) {
        try {
            for (int i = 0; i < npcList.size(); i++) {
                if (npcList.get(i).equals(id))
                    return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public String getColor(String color) {
        for (int i = 0; i < MESSAGE_COLORS.length; i++) {
            if (color.equalsIgnoreCase(MESSAGE_COLORS[i][0])) {
                return MESSAGE_COLORS[i][1];
            }
        }
        return "@bla@";
    }

    public Client getClient(String name) {
        name = name.toLowerCase();
        for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
            if (validClient(i)) {
                Client c = getClient(i);
                if (c.playerName.toLowerCase().equalsIgnoreCase(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    public Client getClient(int id) {
        return (Client) GameEngine.playerHandler.players[id];
    }

    public boolean validClient(int id) {
        if (id < 0 || id > Constants.MAX_PLAYERS)
            return false;
        return validClient(getClient(id));
    }

    public boolean validClient(String name) {
        return validClient(getClient(name));
    }

    public boolean validClient(Client c) {
        return (c != null && !c.disconnected);
    }

    public boolean validNpc(int index) {
        if (index < 0 || index > NpcHandler.MAX_NPCS) {
            return false;
        }
        Npc n = getNpc(index);
        if (n != null) {
            return true;
        }
        return false;
    }

    public int getEntity(String name) {
        Client c = getClient(name);
        if (c != null)
            return c.playerId;
        return -1;
    }

    public Npc getNpc(int index) {
        return (Npc) GameEngine.npcHandler.npcs[index];
    }

    public String getInput(String title, String msg) {
        return JOptionPane.showInputDialog(p, msg, title, 3);
    }

    public int getInt(String title, String msg) {
        int i = -1;
        try {
            i = Integer.parseInt(getInput(title, msg));
        } catch (Exception e) {
            i = -1;
            p.displayMessage("There was an error parsing the Integer.", "Error", 0);
        }
        return i;
    }

    public void executeCommand(String cmd) {
        if (cmd.equalsIgnoreCase("Message")) {

            // Getting the Message name
            String SelectedName = p.MESSAGE_ALL_BOX.getSelectedItem() + ": ";
            if (SelectedName.equalsIgnoreCase("[None]: ")) {
                SelectedName = "";
            }

            // Getting the color
            String color = p.MESSAGE_ALL_COLOR_BOX.getSelectedItem().toString();
            SelectedName = SelectedName + getColor(color);

            // Sending the message
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    c.getPacketSender().sendMessage(SelectedName + p.MESSAGE_ALL_TEXT.getText());
                }
            }
            p.MESSAGE_ALL_TEXT.setText("");
            return;
        }
        if (cmd.equalsIgnoreCase("Players to Npcs")) {
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    try {
                        int newNPC = getInt(cmd, "Enter an Npc ID:");
                        if (newNPC <= 3871 && newNPC >= 0) {
                            c.npcId2 = newNPC;
                            c.isNpc = true;
                            c.updateRequired = true;
                            c.appearanceUpdateRequired = true;
                            p.displayMessage("You turn all players into a npc!", cmd, 1);
                        } else {
                            p.displayMessage(newNPC + " is not a valid Npc type!", "Error", 0);
                        }
                    } catch (Exception e) {
                        p.displayMessage("There was an error parsing the ID.", "Error", 0);
                    }
                }
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Update Players")) {
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    c.isNpc = false;
                    c.updateRequired = true;
                    c.appearanceUpdateRequired = true;
                }
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Teleport All")) {
            String area = (String) JOptionPane.showInputDialog(null, "Select a location:", cmd, -1, null, p.PANEL_TELEPORTS, p.PANEL_TELEPORTS);
            int x, y, z;
            if (area.equals("Cutsom Location")) {
                x = getInt(cmd, "Enter the X Coord:");
                y = getInt(cmd, "Enter the Y Coord:");
                z = getInt(cmd, "Enter the Z Coord:");
            } else {
                Location tele = new Location(0, 0, 0);
                tele = tele.getLocationByName(area);
                if (tele == null) {
                    p.displayMessage("This location has yet to be set.", cmd, 3);
                    return;
                }
                x = tele.getX();
                y = tele.getY();
                z = tele.getZ();
            }
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    c.getPlayerAssistant().spellTeleport(x, y, z);
                }
            }
            p.displayMessage("You successfully teleport all players to " + (area.equals("Cutsom Location") ? "x" + x + " y" + y + " z" + z : area) + "!", cmd, 1);
            return;
        }
        if (cmd.equals("Force Chat")) {
            String msg = getInput(cmd, "Enter a message for the players to say:");
            if (msg == null) {
                p.displayMessage("You must enter a message!", cmd, 3);
                return;
            }
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    c.forcedChat(msg);
                }
            }
        }
        if (cmd.equalsIgnoreCase("Disconnect All")) {
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    Client c = getClient(i);
                    c.disconnected = true;
                }
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Send")) {
            if (p.NPC_CHECKBOX.isSelected()) {
                for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
                    if (validNpc(i)) {
                        Npc n = getNpc(i);
                        if (inList("" + n.npcType))
                            n.forceChat(p.FORCE_NPCS_CHAT_TEXT.getText());
                    }
                }
            } else {
                for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
                    if (validNpc(i)) {
                        getNpc(i).forceChat(p.FORCE_NPCS_CHAT_TEXT.getText());
                    }
                }
            }
            p.FORCE_NPCS_CHAT_TEXT.setText("");
            return;
        }
        if (cmd.equalsIgnoreCase("Animate")) {
            if (p.NPC_CHECKBOX.isSelected()) {
                for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
                    if (validNpc(i)) {
                        Npc n = getNpc(i);
                        if (inList("" + n.npcType)) {
                            n.animNumber = Integer.parseInt(p.NPC_ANIMATION_TEXT.getText());
                            n.updateRequired = true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
                    if (validNpc(i)) {
                        Npc n = getNpc(i);
                        n.animNumber = Integer.parseInt(p.NPC_ANIMATION_TEXT.getText());
                        n.updateRequired = true;
                    }
                }
            }
            p.NPC_ANIMATION_TEXT.setText("");
            return;
        }
        if (cmd.equalsIgnoreCase("Add Npc")) {
            String id = getInput(cmd, "Enter an Npc ID");
            if (!inList(id)) {
                npcList.add(trim(id));
                p.LIST_NPCS.addElement(trim(id));
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Remove Npc")) {
            if (p.NPC_OPTION_LIST.getSelectedValue() != null) {
                p.LIST_NPCS.removeElement(p.NPC_OPTION_LIST.getSelectedValue().toString());
                npcList.remove(p.NPC_OPTION_LIST.getSelectedValue().toString());
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Update")) {
            GameEngine.playerHandler.updateSeconds = (Integer.parseInt(p.UPDATE_SERVER_TEXT.getText()));
            GameEngine.playerHandler.updateAnnounced = false;
            GameEngine.playerHandler.updateRunning = true;
            GameEngine.playerHandler.updateStartTime = System.currentTimeMillis();
            return;
        }
        if (cmd.equalsIgnoreCase("Reset")) {
            int[] SKIP_NPCS = {2627, 2630, 2743, 2745, 2746, 2738, 3500, 3491, 3493, 3494, 3495, 3496};
            for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
                boolean skip = false;
                Npc n = GameEngine.npcHandler.npcs[i];
                if (n != null) {
                    for (int k : SKIP_NPCS) {
                        if (n.npcType == k) {
                            skip = true;
                        }
                    }
                    if (!skip) {
                        n.isDead = true;
                    }
                }
            }            // Getting the Message name
            String SelectedName = p.MESSAGE_ALL_BOX.getSelectedItem() + ": ";
            if (SelectedName.equalsIgnoreCase("[None]: "))
                SelectedName = "";
            String color = p.MESSAGE_ALL_COLOR_BOX.getSelectedItem().toString();
            SelectedName = SelectedName + getColor(color);
            for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
                if (validClient(i)) {
                    getClient(i).getPacketSender().sendMessage(SelectedName + "Npcs have been reset.");
                }
            }
            return;
        }
        if (cmd.equalsIgnoreCase("Update Settings")) {
            Constants.SERVER_NAME = p.SERVER_NAME_TEXT.getText();
            Constants.ADMIN_CAN_TRADE = p.ADMINS_CAN_TRADE.isSelected();
            Constants.ADMIN_DROP_ITEMS = p.ADMINS_CAN_DROP.isSelected();
            Constants.ADMIN_CAN_SELL_ITEMS = p.ADMINS_CAN_SELL_ITEMS.isSelected();
            p.setTitle(Constants.SERVER_NAME + " ControlPanel");
            return;
        }

        /*
         * All commands based on the selected player!
         * If the player is not selected it will not work.
         */
        if (p.ENTITY_LIST.getSelectedValue() != null) {
            int entity = getEntity(getSelectedPlayer());
            if (!validClient(entity)) {
                p.displayMessage("This player is not valid..", "Error", 0);
                return;
            }
            Client c = getClient(entity);

            if (cmd.equalsIgnoreCase("Ban Player")) {
                Connection.addNameToBanList(c.playerName);
                Connection.addNameToFile(c.playerName);
                c.disconnected = true;
                return;
            }
            if (cmd.equalsIgnoreCase("IP-Ban")) {
                Connection.addIpToBanList(c.connectedFrom);
                Connection.addIpToFile(c.connectedFrom);
                c.disconnected = true;
                return;
            }
            if (cmd.equalsIgnoreCase("Mute Player")) {
                c.getPacketSender().sendMessage("You have been muted.");
                Connection.addNameToMuteList(c.playerName);
                return;
            }
            if (cmd.equalsIgnoreCase("IP-Mute")) {
                Connection.addIpToMuteList(c.connectedFrom);
                c.getPacketSender().sendMessage("You have been muted.");
                return;
            }
            if (cmd.equalsIgnoreCase("Disconnect")) {
                c.disconnected = true;
                return;
            }
            if (cmd.equalsIgnoreCase("Make Npc")) {
                try {
                    int newNPC = getInt(cmd, "Enter an Npc ID:");
                    if (newNPC <= 3871 && newNPC >= 0) {
                        c.npcId2 = newNPC;
                        c.isNpc = true;
                        c.updateRequired = true;
                        c.appearanceUpdateRequired = true;
                        p.displayMessage("You turn " + c.playerName + " into a Npc!", cmd, 1);
                    } else {
                        p.displayMessage(newNPC + " is not a valid Npc type!", "Error", 0);
                    }
                } catch (Exception e) {
                    p.displayMessage("There was an error parsing the ID.", "Error", 0);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Send Message")) {
                String message = getInput(cmd, "Enter a message you wish to send.");
                c.getPacketSender().sendMessage(message);
                return;
            }
            if (cmd.equalsIgnoreCase("Add Item")) {
                int item = getInt(cmd, "Enter an item ID");
                int amount = getInt(cmd, "Enter the amount");
                if (item <= 160000 && item > 0) {
                    c.getItemAssistant().addItem(item, amount);
                    p.displayMessage("You give " + c.playerName + " " + amount + " " + DeprecatedItems.getItemName(item) + "s!", cmd, 1);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Remove Item")) {
                int item = getInt(cmd, "Enter an item ID");
                if (item <= 160000 && item > 0) {
                    if (c.getItemAssistant().playerHasItem(item)) {
                        c.getItemAssistant().deleteItem(item, c.getItemAssistant().getItemSlot(item), c.getItemAssistant().itemAmount(item + 1));
                        p.displayMessage("The selecetd item has been deleted!", cmd, 1);
                    } else {
                        String invItems = "";
                        for (int i = 0; i < 28; i++) {
                            if (c.playerItems[i] != 0) {
                                invItems = (invItems + i + ". " + c.playerItems[i] + "\n");
                            }
                        }
                        p.displayMessage("This player does not have that item..\n Inventory:\n" + invItems, "Error", 0);
                    }
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Empty Inv.")) {
                int confirm = JOptionPane.showConfirmDialog(p, "Do you really want to empty this players inventory?", cmd, 2);
                if (confirm == 0) {
                    c.getItemAssistant().removeAllItems();
                    c.getPacketSender().sendMessage("Your inventory has been cleared.");
                    p.displayMessage(c.playerName + "'s inventory has been cleared!", cmd, 1);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Empty Bank")) {
                int confirm = JOptionPane.showConfirmDialog(p, "Do you really want to empty this players bank?", cmd, 2);
                if (confirm == 0) {
                    for (int i = 0; i < c.bankItems.length; i++) { // Setting bank items
                        c.bankItems[i] = 0;
                        c.bankItemsN[i] = 0;
                    }
                    c.getItemAssistant().resetBank();
                    c.getItemAssistant().resetItems(5064);
                    c.getPacketSender().sendMessage("Your bank has been cleared.");
                    p.displayMessage(c.playerName + "'s bank has been cleared!", cmd, 1);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Drop Item")) {
                int item = getInt(cmd, "Enter an item ID");
                if (item <= 160000 && item > 0) {
                    if (c.getItemAssistant().playerHasItem(item)) {
                        int slot = c.getItemAssistant().getItemSlot(item);
                        GameEngine.itemHandler.createGroundItem(c, item, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
                        c.getItemAssistant().deleteItem(item, slot, c.playerItemsN[slot]);
                        p.displayMessage("The selecetd item has been dropped!", cmd, 1);
                    } else {
                        String invItems = "";
                        for (int i = 0; i < 28; i++) {
                            if (c.playerItems[i] != 0) {
                                invItems = (invItems + i + ". " + c.playerItems[i] + "\n");
                            }
                        }
                        p.displayMessage("This player does not have that item..\n\n" + c.playerName + "'s Inventory:\n" + invItems, "Error", 0);
                    }
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Unequip Item")) {
                int slot = -1;
                String[] equipment = {"Hat", "Cape", "Amulet", "Weapon", "Chest", "Shield", "Legs", "Hands", "Feet", "Ring", "Arrows"};
                String id = (String) JOptionPane.showInputDialog(null, "Chose the Equipment you wish to remove:\n", "Magic Bomber", -1, null, equipment, equipment);
                for (int j = 0; j < equipment.length; j++) {
                    if (id.equals(equipment[j]))
                        slot = j;
                }
                if (slot < 14 && slot >= 0) {
                    c.getItemAssistant().removeItem(c.playerEquipment[slot], slot);
                } else {
                    p.displayMessage("You must enter an ID number of  0 - 13", "Error", 0);
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Remove All Obtained Items")) {
                int con = JOptionPane.showConfirmDialog(p, "Do you really want to delete EVERY item?!", cmd, 2);
                if (con == 0) {
                    int firm = JOptionPane.showConfirmDialog(p, "REALLY!! Are you sure you want to do that?!", cmd, 2);
                    if (firm == 0) {
                        c.getItemAssistant().removeAllItems();
                        for (int i = 0; i < 13; i++) {
                            c.getItemAssistant().removeItem(c.playerEquipment[i], i);
                        }
                        c.getItemAssistant().removeAllItems();
                        for (int i = 0; i < c.bankItems.length; i++) { // Setting bank items
                            c.bankItems[i] = 0;
                            c.bankItemsN[i] = 0;
                        }
                        c.getItemAssistant().resetBank();
                        c.getItemAssistant().resetItems(5064);
                        c.getPacketSender().sendMessage("I'm sorry to say, but every item you have has been deleted..");
                        p.displayMessage("The cruel dark deed you requested has been fulfilled.. Jerk..", cmd, 1);
                    }
                }
                return;
            }
            if (cmd.equalsIgnoreCase("Initiate CMD")) {
                playerCommand(p.FORCE_COMMANDS.getSelectedValue().toString(), c);
                return;
            }
            if (cmd.equalsIgnoreCase("Teleport Player")) {
                if (p.TELEPORT_LIST.getSelectedValue() == null) {
                    p.displayMessage("No location selected!", "Error", 0);
                    return;
                }
                String area = p.TELEPORT_LIST.getSelectedValue().toString();
                if (area.equals("Cutsom Location")) {
                    int x = getInt(cmd, "Enter the X Coord:");
                    int y = getInt(cmd, "Enter the Y Coord:");
                    int z = getInt(cmd, "Enter the Z Coord:");
                    c.getPlayerAssistant().spellTeleport(x, y, z);
                    p.displayMessage("You successfully teleport " + c.playerName + "!", cmd, 1);
                } else {
                    Location tele = Location.getLocationByName(area);
                    if (tele != null) {
                        c.getPlayerAssistant().spellTeleport(tele.getX(), tele.getY(), tele.getZ());
                        p.displayMessage("You successfully teleport " + c.playerName + " to " + area + "!", cmd, 1);
                        return;
                    }
                }
            }
        } else {
            p.displayMessage("No player selected!", "Error", 0);
            return;
        }
        p.displayMessage("This command has not yet been added.", "Error", 0);
        System.out.println("[Console]: No such command: " + cmd);
    }

    public void playerCommand(String cmd, Client c) {
        String SKILL_NAME[] = {
                "Attack", "Defence", "Strength", "Constitution",
                "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching",
                "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore",
                "Agility", "Thieving", "Slayer", "Farming", "Runecrafting"
        };

        if (cmd.equals("Force Animation")) {
            int id = getInt(cmd, "Enter the animation id:");
            if (id != -1)
                c.startAnimation(id);
        }
        if (cmd.equals("Display GFX")) {
            int id = getInt(cmd, "Enter the GFX id:");
            if (id != -1)
                c.gfx0(id);
        }
        if (cmd.equals("Lock EXP")) {
            p.displayMessage("Command not added! Add it yourself :D", cmd, 1);
        }
        if (cmd.equals("Force Bank")) {
            c.getPacketSender().openUpBank();
        }
        if (cmd.equals("Force Shop")) {
            int shop = -1;
            String name = (String) JOptionPane.showInputDialog(null, "Chose the Equipment you wish to remove:\n", "Magic Bomber", -1, null, GameEngine.shopHandler.shopName, GameEngine.shopHandler.shopName);
            for (int i = 0; i < GameEngine.shopHandler.shopName.length; i++) {
                if (name.equals(GameEngine.shopHandler.shopName[i]))
                    shop = i;
            }
            if (shop != -1)
                c.getShopAssistant().openShop(shop);
            else
                p.displayMessage("Could not find shop.", cmd, 1);
        }
        if (cmd.equals("Force Death")) {
            c.getPlayerAssistant().applyDead();
        }
        if (cmd.equals("Force Command")) {
            p.displayMessage("This command is not yet added! ADD IT YOURSELF :D", cmd, 1);
        }
        if (cmd.equals("Force Chat")) {
            c.forcedChat(getInput(cmd, "Enter a message for the player to say."));
        }
        if (cmd.equals("Give Master")) {
            for (int i = 0; i < 21; i++)
                c.getPlayerAssistant().addSkillXP((15000000), i);
            c.playerXP[3] = c.getPlayerAssistant().getXPForLevel(99) + 5;
            c.playerLevel[3] = c.getPlayerAssistant().getLevelForXP(c.playerXP[3]);
        }
        if (cmd.equals("Add SkillXP")) {
            int id = -1;
            String name = (String) JOptionPane.showInputDialog(null, "Chose a Skill:", cmd, -1, null, SKILL_NAME, SKILL_NAME);
            int amount = getInt(cmd, "Enter te amount of Exp you want to add:");
            for (int i = 0; i < SKILL_NAME.length; i++) {
                if (name.equals(SKILL_NAME[i]))
                    id = i;
            }
            if (id != -1) {
                if (amount < 0)
                    amount = 0;
                c.getPlayerAssistant().addSkillXP(amount, id);
                p.displayMessage("You have added " + amount + " experience to " + c.playerName + "s " + SKILL_NAME[id] + "!", cmd, 1);
            } else
                p.displayMessage("Error finding Input.", cmd, 1);
        }
        if (cmd.equals("Remove SkillXP")) {
            int id = -1;
            String name = (String) JOptionPane.showInputDialog(null, "Chose a Skill:", cmd, -1, null, SKILL_NAME, SKILL_NAME);
            int amount = getInt(cmd, "Enter te amount of Exp you want to take away:");
            for (int i = 0; i < SKILL_NAME.length; i++) {
                if (name.equals(SKILL_NAME[i]))
                    id = i;
            }
            if (id != -1) {
                if (c.playerXP[id] < amount)
                    amount = c.playerXP[id];
                c.playerXP[id] -= amount + 1;
                p.displayMessage("You have removed " + amount + " experience from " + c.playerName + "s " + SKILL_NAME[id] + "!", cmd, 1);
            } else
                p.displayMessage("Error finding Input.", cmd, 1);
        }
        if (cmd.equals("Reset Skill")) {
            p.displayMessage("This command is not yet added!", cmd, 1);
        }
        if (cmd.equals("Reset All Skills")) {
            p.displayMessage("This command is not yet added!", cmd, 1);
        }
    }

    public static class Location {

        public int x, y, z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static Location getLocationByName(String name) {
            if (name.equals("Edgevill"))
                return new Location(3087, 3492, 0);
            if (name.equals("Lumbridge"))
                return new Location(3222, 3219, 0);
            if (name.equals("Al-kharid"))
                return new Location(3293, 3188, 0);
            if (name.equals("Varrock"))
                return new Location(3211, 3423, 0);
            if (name.equals("Falador"))
                return new Location(2965, 3379, 0);
            if (name.equals("Camelot"))
                return new Location(2757, 3478, 0);
            if (name.equals("Ardounge"))
                return new Location(2661, 3305, 0);
            if (name.equals("Watchtower"))
                return new Location(2569, 3098, 0);
            if (name.equals("Trollheim"))
                return new Location(2867, 3593, 0);
            if (name.equals("Ape Atoll"))
                return new Location(2764, 2775, 0);
            if (name.equals("Canifas"))
                return new Location(3052, 3497, 0);
            if (name.equals("Port Sarim"))
                return new Location(3025, 3217, 0);
            if (name.equals("Rimmington"))
                return new Location(2957, 3214, 0);
            if (name.equals("Draynor"))
                return new Location(3093, 3244, 0);
            if (name.equals("IceQueen Lair"))
                return new Location(2866, 9953, 0);
            if (name.equals("Brimhaven Dungeon"))
                return new Location(2713, 9453, 0);
            if (name.equals("Gnome Agility"))
                return new Location(2477, 3438, 0);
            if (name.equals("Wilderness Agility"))
                return new Location(2998, 3932, 0);
            if (name.equals("Distant kingdom"))
                return new Location(2767, 4723, 0);
            if (name.equals("Maze Event"))
                return new Location(2911, 4551, 0);
            if (name.equals("Drill Instructor"))
                return new Location(3163, 4828, 0);
            if (name.equals("Grave Digger"))
                return new Location(1928, 5002, 0);
            if (name.equals("Karamja Lessers"))
                return new Location(2835, 9563, 0);
            if (name.equals("Evil Bob's Island"))
                return new Location(2525, 4776, 0);
            if (name.equals("Secret Island"))
                return new Location(2152, 5095, 0);
            if (name.equals("Ibans Trap"))
                return new Location(2319, 9804, 0);
            if (name.equals("Fishing Docks"))
                return new Location(2767, 3277, 0);
            if (name.equals("Mage Trainging"))
                return new Location(3365, 9640, 0);
            if (name.equals("Quest Place"))
                return new Location(2907, 9712, 0);
            if (name.equals("Duel Arena"))
                return new Location(3367, 3267, 0);
            if (name.equals("Bandit Camp"))
                return new Location(3171, 3028, 0);
            if (name.equals("Uzer"))
                return new Location(3484, 3092, 0);

            return null;
        }

        public void update(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
