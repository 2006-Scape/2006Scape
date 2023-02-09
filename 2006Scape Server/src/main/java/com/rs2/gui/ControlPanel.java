package com.rs2.gui;

import com.rs2.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    public final String[] MESSAGE_COLOR = {"Red", "Blue", "Green", "Yellow", "Magenta", "Orange", "Dark Red", "Dark Blue", "None"};
    public final String[] PANEL_TELEPORTS = {"Cutsom Location", "Edgevill", "Lumbridge", "Al-kharid", "Varrock", "Falador", "Camelot", "Ardounge", "Watchtower", "Trollheim", "Ape Atoll", "Canifas", "Port Sarim", "Rimmington", "Draynor", "IceQueen Lair", "Brimhaven Dungeon", "Gnome Agility", "Wilderness Agility", "Distant kingdom", "Maze Event", "Drill Instructor", "Grave Digger", "Karamja Lessers", "Evil Bob's Island", "Secret Island", "Ibans Trap", "Fishing Docks", "Mage Trainging", "Quest Place", "Duel Arena", "Bandit Camp", "Uzer"};
    // Variables declaration - do not modify
    public JButton ADD_ITEM;
    public JButton ADD_NPC_BUTTON;
    public JCheckBox ADMINS_CAN_DROP;
    public JCheckBox ADMINS_CAN_TRADE;
    public JButton BAN_PLAYER;
    public JButton CLEAR_CONSOLE_BUTTON;
    public JLabel CLEAR_CONSOLE_LABEL;
    public JTabbedPane CONTROL_TABS;
    public JLabel DANGEROUS_COMMAND;
    public JCheckBoxMenuItem DEBUG_MODE;
    public JButton DROP_ITEM;
    public JButton EMPTY_BANK;
    public JButton EMPTY_INVENTORY;
    public JList ENTITY_LIST;
    public static DefaultListModel LIST_MODEL;
    public DefaultListModel LIST_NPCS;
    public JMenuItem EXIT_ITEM;
    public JMenu FILE_MENU;
    public JButton FORCE_CHAT;
    public JList FORCE_COMMANDS;
    public JTextField FORCE_NPCS_CHAT_TEXT;
    public JButton FORCE_NPC_CHAT_CMD;
    public JLabel FORCE_NPC_CHAT_LABEL;
    public JButton INIT_COMMAND;
    public JButton IPBAN_PLAYER;
    public JButton IPMUTE_PLAYER;
    public JButton KICK_ALL;
    public JButton KICK_PLAYER;
    public JLabel LABELBUTTON;
    public JButton MAKE_NPC;
    public JMenuBar MENU_BAR;
    public JLabel MESSAGE_ALL;
    public JButton MESSAGE_ALL_BUTTON;
    public JTextField MESSAGE_ALL_TEXT;
    public JComboBox MESSAGE_ALL_BOX;
    public JComboBox MESSAGE_ALL_COLOR_BOX;
    public JLabel MESSAGE_ALL_NAME_LABEL = null;
    public JLabel MESSAGE_ALL_COLOR_LABEL = null;
    public JButton MUTE_PLAYER;
    public JPanel NPCS_PANEL;
    public JButton NPC_ANIMATION_BUTTON;
    public JTextField NPC_ANIMATION_TEXT;
    public JCheckBox NPC_CHECKBOX;
    public JList NPC_OPTION_LIST;
    public JPanel PLAYER_CONTROLS;
    public JTabbedPane PLAYER_CONTROLS_TAB;
    public JPanel PLAYER_EQUIPMENT;
    public JCheckBox ADMINS_CAN_SELL_ITEMS;
    public JPanel PLAYER_LOCATION;
    public JPanel PLAYER_MISC;
    public JPanel PLAYER_PANEL;
    public JButton REMOVE_ITEM;
    public JButton REMOVE_NPC_BUTTON1;
    public JButton REMOVE_OBTAINED_ITEMS;
    public JButton RESET_NPCS_BUTTON;
    public JLabel RESET_NPCS_LABEL;
    public JScrollPane SCROLL_PANE;
    public JButton SEND_MESSAGE;
    public JPopupMenu.Separator SEPARATOR_ITEM;
    public JPanel SERVER_CONTROLS;
    public JLabel SERVER_NAME_LABEL;
    public JTextField SERVER_NAME_TEXT;
    public JPanel SERVER_PANEL;
    public JTabbedPane SERVER_SETTINGS;
    public JPanel SETTINGS_PANEL;
    public JButton TELEPORT_BUTTON;
    public JList TELEPORT_LIST;
    public JButton TELE_ALL;
    public JButton FORCE_NPC;
    public JButton UNEQUIP_ITEM;
    public JButton UPDATE_PLAYERS;
    public JButton UPDATE_SERVER_CMD;
    public JLabel UPDATE_SERVER_LABEL;
    public JTextField UPDATE_SERVER_TEXT;
    public JButton UPDATE_SETTINGS;
    public JLabel UPDATE_TIME_LABEL;
    public ButtonGroup buttonGroup1;
    public JScrollPane jScrollPane1;
    public JScrollPane jScrollPane2;
    public JScrollPane jScrollPane3;
    public JScrollPane jScrollPane4;
    private PanelSettings settings = null;
    public ControlPanel() {
    }

    public static void addEntity(String name) {
        LIST_MODEL.addElement(name);
    }

    public static void removeEntity(String name) {
        LIST_MODEL.removeElement(name);
    }

    public PanelSettings settings() {
        return settings;
    }

    @SuppressWarnings("serial")
    public void initComponents() {
        settings = new PanelSettings(this);
        LIST_MODEL = new DefaultListModel();
        LIST_NPCS = new DefaultListModel();
        buttonGroup1 = new ButtonGroup();
        CONTROL_TABS = new JTabbedPane();
        PLAYER_PANEL = new JPanel();
        PLAYER_CONTROLS_TAB = new JTabbedPane();
        PLAYER_CONTROLS = new JPanel();
        MUTE_PLAYER = new JButton();
        BAN_PLAYER = new JButton();
        KICK_PLAYER = new JButton();
        SEND_MESSAGE = new JButton();
        IPBAN_PLAYER = new JButton();
        IPMUTE_PLAYER = new JButton();
        FORCE_NPC = new JButton();
        PLAYER_EQUIPMENT = new JPanel();
        ADD_ITEM = new JButton();
        REMOVE_ITEM = new JButton();
        EMPTY_INVENTORY = new JButton();
        EMPTY_BANK = new JButton();
        DROP_ITEM = new JButton();
        UNEQUIP_ITEM = new JButton();
        REMOVE_OBTAINED_ITEMS = new JButton();
        DANGEROUS_COMMAND = new JLabel();
        PLAYER_MISC = new JPanel();
        jScrollPane1 = new JScrollPane();
        FORCE_COMMANDS = new JList();
        INIT_COMMAND = new JButton();
        PLAYER_LOCATION = new JPanel();
        jScrollPane2 = new JScrollPane();
        TELEPORT_LIST = new JList();
        TELEPORT_BUTTON = new JButton();
        MAKE_NPC = new JButton();
        UPDATE_PLAYERS = new JButton();
        TELE_ALL = new JButton();
        KICK_ALL = new JButton();
        FORCE_CHAT = new JButton();
        NPCS_PANEL = new JPanel();
        FORCE_NPCS_CHAT_TEXT = new JTextField();
        FORCE_NPC_CHAT_LABEL = new JLabel();
        FORCE_NPC_CHAT_CMD = new JButton();
        LABELBUTTON = new JLabel();
        NPC_ANIMATION_TEXT = new JTextField();
        NPC_ANIMATION_BUTTON = new JButton();
        NPC_CHECKBOX = new JCheckBox();
        jScrollPane3 = new JScrollPane();
        NPC_OPTION_LIST = new JList(LIST_NPCS);
        ADD_NPC_BUTTON = new JButton();
        REMOVE_NPC_BUTTON1 = new JButton();
        SERVER_PANEL = new JPanel();
        SERVER_SETTINGS = new JTabbedPane();
        SETTINGS_PANEL = new JPanel();
        SERVER_NAME_LABEL = new JLabel();
        SERVER_NAME_TEXT = new JTextField();
        ADMINS_CAN_TRADE = new JCheckBox();
        ADMINS_CAN_DROP = new JCheckBox();
        ADMINS_CAN_SELL_ITEMS = new JCheckBox();
        UPDATE_SETTINGS = new JButton();
        SERVER_CONTROLS = new JPanel();
        MESSAGE_ALL = new JLabel();
        MESSAGE_ALL_TEXT = new JTextField();
        MESSAGE_ALL_BUTTON = new JButton();
        MESSAGE_ALL_BOX = new JComboBox();
        MESSAGE_ALL_BOX.setEditable(true);
        MESSAGE_ALL_BOX.setModel(new DefaultComboBoxModel(new String[]{"[Alert]", "[Update]", "[Server]", "[Control Panel]", "[Panel]", "[Console]", "[Prompt]", "[None]"}));
        MESSAGE_ALL_BOX.setToolTipText("Choose a name that will show next to the message.");
        MESSAGE_ALL_COLOR_BOX = new JComboBox();
        MESSAGE_ALL_COLOR_BOX.setModel(new DefaultComboBoxModel(MESSAGE_COLOR));
        MESSAGE_ALL_NAME_LABEL = new JLabel();
        MESSAGE_ALL_NAME_LABEL.setText("Name:");
        MESSAGE_ALL_COLOR_LABEL = new JLabel();
        MESSAGE_ALL_COLOR_LABEL.setText("Color:");

        UPDATE_SERVER_LABEL = new JLabel();
        UPDATE_SERVER_TEXT = new JTextField();
        UPDATE_TIME_LABEL = new JLabel();
        UPDATE_SERVER_CMD = new JButton();

        RESET_NPCS_LABEL = new JLabel();
        RESET_NPCS_BUTTON = new JButton();

        CLEAR_CONSOLE_LABEL = new JLabel();
        CLEAR_CONSOLE_BUTTON = new JButton();

        jScrollPane4 = new JScrollPane();
        SCROLL_PANE = new JScrollPane();
        ENTITY_LIST = new JList(LIST_MODEL);
        MENU_BAR = new JMenuBar();
        FILE_MENU = new JMenu();
        DEBUG_MODE = new JCheckBoxMenuItem();
        SEPARATOR_ITEM = new JPopupMenu.Separator();
        EXIT_ITEM = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(Constants.SERVER_NAME + " ControlPanel");
        setBackground(new Color(245, 244, 244));
        setResizable(false);

        PLAYER_CONTROLS_TAB.setToolTipText("Player Options");

        MUTE_PLAYER.setText("Mute Player");
        MUTE_PLAYER.setToolTipText("Mute a player so that they cannot speak.");
        MUTE_PLAYER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        BAN_PLAYER.setText("Ban Player");
        BAN_PLAYER.setToolTipText("Ban a player.");
        BAN_PLAYER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        KICK_PLAYER.setText("Disconnect");
        KICK_PLAYER.setToolTipText("Disconnect a player from the server.");
        KICK_PLAYER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        SEND_MESSAGE.setText("Send Message");
        SEND_MESSAGE.setToolTipText("Send the lpayer a lovely message.");
        SEND_MESSAGE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        IPBAN_PLAYER.setText("IP-Ban");
        IPBAN_PLAYER.setToolTipText("IP-Ban a player from ever connecting.");
        IPBAN_PLAYER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        IPMUTE_PLAYER.setText("IP-Mute");
        IPMUTE_PLAYER.setToolTipText("IP-Mute a player so that any same connection cannot speak.");
        IPMUTE_PLAYER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        FORCE_NPC.setText("Make Npc");
        FORCE_NPC.setToolTipText("Turn the player into a npc.");
        FORCE_NPC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout PLAYER_CONTROLSLayout = new GroupLayout(PLAYER_CONTROLS);
        PLAYER_CONTROLS.setLayout(PLAYER_CONTROLSLayout);
        PLAYER_CONTROLSLayout.setHorizontalGroup(
                PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_CONTROLSLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(SEND_MESSAGE, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.LEADING, PLAYER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(BAN_PLAYER, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(IPBAN_PLAYER, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.LEADING, PLAYER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(MUTE_PLAYER, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(IPMUTE_PLAYER, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.LEADING, PLAYER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(KICK_PLAYER, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(FORCE_NPC, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(11, Short.MAX_VALUE))
        );
        PLAYER_CONTROLSLayout.setVerticalGroup(
                PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_CONTROLSLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(BAN_PLAYER, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(IPBAN_PLAYER, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(MUTE_PLAYER, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(IPMUTE_PLAYER, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PLAYER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(KICK_PLAYER, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(FORCE_NPC, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(SEND_MESSAGE, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        PLAYER_CONTROLS_TAB.addTab("Main", PLAYER_CONTROLS);

        ADD_ITEM.setText("Add Item");
        ADD_ITEM.setToolTipText("Add an item to the players Inventory. (etc: 995, 1046, 1050, 4151)");
        ADD_ITEM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        REMOVE_ITEM.setText("Remove Item");
        REMOVE_ITEM.setToolTipText("Remove an item from the players inventory.");
        REMOVE_ITEM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        EMPTY_INVENTORY.setText("Empty Inv.");
        EMPTY_INVENTORY.setToolTipText("Delete ALL items in the players Inventory.");
        EMPTY_INVENTORY.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        EMPTY_BANK.setText("Empty Bank");
        EMPTY_BANK.setToolTipText("Delete ALL items from the players bank.");
        EMPTY_BANK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        DROP_ITEM.setText("Drop Item");
        DROP_ITEM.setToolTipText("Force the player to drop an item.");
        DROP_ITEM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        UNEQUIP_ITEM.setText("Unequip Item");
        UNEQUIP_ITEM.setToolTipText("Force the player to remove an item from their equipment slot.");
        UNEQUIP_ITEM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        REMOVE_OBTAINED_ITEMS.setText("Remove All Obtained Items");
        REMOVE_OBTAINED_ITEMS.setToolTipText("Warning! This will delete ALL of the players items!!!!");
        REMOVE_OBTAINED_ITEMS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        DANGEROUS_COMMAND.setText("Warning! This will delete EVERYTHING!");

        GroupLayout PLAYER_EQUIPMENTLayout = new GroupLayout(PLAYER_EQUIPMENT);
        PLAYER_EQUIPMENT.setLayout(PLAYER_EQUIPMENTLayout);
        PLAYER_EQUIPMENTLayout.setHorizontalGroup(
                PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_EQUIPMENTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(REMOVE_OBTAINED_ITEMS, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                        .addGroup(PLAYER_EQUIPMENTLayout.createSequentialGroup()
                                                .addComponent(ADD_ITEM, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(REMOVE_ITEM, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PLAYER_EQUIPMENTLayout.createSequentialGroup()
                                                .addComponent(EMPTY_INVENTORY, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(EMPTY_BANK, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PLAYER_EQUIPMENTLayout.createSequentialGroup()
                                                .addComponent(DROP_ITEM, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(UNEQUIP_ITEM, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(DANGEROUS_COMMAND))
                                .addContainerGap())
        );
        PLAYER_EQUIPMENTLayout.setVerticalGroup(
                PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_EQUIPMENTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(ADD_ITEM, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(REMOVE_ITEM, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(EMPTY_INVENTORY, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(EMPTY_BANK, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PLAYER_EQUIPMENTLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(DROP_ITEM, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(UNEQUIP_ITEM, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DANGEROUS_COMMAND)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(REMOVE_OBTAINED_ITEMS, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        PLAYER_CONTROLS_TAB.addTab("Equipment", PLAYER_EQUIPMENT);

        FORCE_COMMANDS.setBackground(new Color(254, 254, 254));
        FORCE_COMMANDS.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        FORCE_COMMANDS.setFont(new Font("Tahoma", 0, 12));
        FORCE_COMMANDS.setForeground(new Color(153, 0, 51));
        FORCE_COMMANDS.setModel(new AbstractListModel() {
            String[] strings = {"Force Animation", "Display GFX", "Lock EXP", "Force Bank", "Force Shop", "Force Death", "Force Command", "Force Chat", "Give Master", "Add SkillXP", "Remove SkillXP", "Reset Skill", "Reset All Skills"};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        FORCE_COMMANDS.setToolTipText("Another list of commands you can force the player to perfoem.");
        jScrollPane1.setViewportView(FORCE_COMMANDS);

        INIT_COMMAND.setText("Initiate CMD");
        INIT_COMMAND.setToolTipText("Initiates the selected comand.");
        INIT_COMMAND.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout PLAYER_MISCLayout = new GroupLayout(PLAYER_MISC);
        PLAYER_MISC.setLayout(PLAYER_MISCLayout);
        PLAYER_MISCLayout.setHorizontalGroup(
                PLAYER_MISCLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, PLAYER_MISCLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_MISCLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(INIT_COMMAND, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                                .addContainerGap())
        );
        PLAYER_MISCLayout.setVerticalGroup(
                PLAYER_MISCLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_MISCLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(INIT_COMMAND, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addContainerGap())
        );

        PLAYER_CONTROLS_TAB.addTab("Misc", PLAYER_MISC);

        TELEPORT_LIST.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        TELEPORT_LIST.setForeground(new Color(0, 51, 102));
        TELEPORT_LIST.setModel(new AbstractListModel() {
            public int getSize() {
                return PANEL_TELEPORTS.length;
            }

            public Object getElementAt(int i) {
                return PANEL_TELEPORTS[i];
            }
        });
        jScrollPane2.setViewportView(TELEPORT_LIST);

        TELEPORT_BUTTON.setText("Teleport Player");
        TELEPORT_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout PLAYER_LOCATIONLayout = new GroupLayout(PLAYER_LOCATION);
        PLAYER_LOCATION.setLayout(PLAYER_LOCATIONLayout);
        PLAYER_LOCATIONLayout.setHorizontalGroup(
                PLAYER_LOCATIONLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_LOCATIONLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PLAYER_LOCATIONLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(TELEPORT_BUTTON, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                                .addContainerGap())
        );
        PLAYER_LOCATIONLayout.setVerticalGroup(
                PLAYER_LOCATIONLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_LOCATIONLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TELEPORT_BUTTON, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                                .addContainerGap())
        );

        PLAYER_CONTROLS_TAB.addTab("Location", PLAYER_LOCATION);

        MAKE_NPC.setText("Players to Npcs");
        MAKE_NPC.setToolTipText("Turn all of the players into Npcs.");
        MAKE_NPC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        UPDATE_PLAYERS.setText("Update Players");
        UPDATE_PLAYERS.setToolTipText("Update the players to their origional look.");
        UPDATE_PLAYERS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        TELE_ALL.setText("Teleport All");
        TELE_ALL.setToolTipText("Teleport all players to a set location.");
        TELE_ALL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        KICK_ALL.setText("Disconnect All");
        KICK_ALL.setToolTipText("Disconnect all players from the server.");
        KICK_ALL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        FORCE_CHAT.setText("Force Chat");
        FORCE_CHAT.setToolTipText("Force all players to say something.");
        FORCE_CHAT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout PLAYER_PANELLayout = new GroupLayout(PLAYER_PANEL);
        PLAYER_PANEL.setLayout(PLAYER_PANELLayout);
        PLAYER_PANELLayout.setHorizontalGroup(
                PLAYER_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(PLAYER_CONTROLS_TAB, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(PLAYER_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(FORCE_CHAT, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addComponent(KICK_ALL, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addComponent(TELE_ALL, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addComponent(UPDATE_PLAYERS, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addComponent(MAKE_NPC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(24, 24, 24))
        );
        PLAYER_PANELLayout.setVerticalGroup(
                PLAYER_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PLAYER_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(MAKE_NPC, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(UPDATE_PLAYERS, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TELE_ALL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(KICK_ALL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FORCE_CHAT, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47))
                        .addGroup(PLAYER_PANELLayout.createSequentialGroup()
                                .addComponent(PLAYER_CONTROLS_TAB)
                                .addGap(22, 22, 22))
        );

        CONTROL_TABS.addTab("Player", PLAYER_PANEL);

        FORCE_NPCS_CHAT_TEXT.setText("I love you!");
        FORCE_NPCS_CHAT_TEXT.setToolTipText("Enter a line of text you wish to have all npcs in game to say.");

        FORCE_NPC_CHAT_LABEL.setText("Force Chat:");
        FORCE_NPC_CHAT_LABEL.setToolTipText("Its just a label...");

        FORCE_NPC_CHAT_CMD.setText("Send");
        FORCE_NPC_CHAT_CMD.setToolTipText("Send the message.");
        FORCE_NPC_CHAT_CMD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });
        LABELBUTTON.setText("Force Animation:");

        NPC_ANIMATION_TEXT.setText("811");
        NPC_ANIMATION_TEXT.setToolTipText("Enter the npc ID");

        NPC_ANIMATION_BUTTON.setText("Animate");
        NPC_ANIMATION_BUTTON.setToolTipText("Animate npc's");
        NPC_ANIMATION_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        NPC_CHECKBOX.setText("Use Npc's from list");

        NPC_OPTION_LIST.setBorder(BorderFactory.createLineBorder(new Color(102, 0, 102)));
        NPC_OPTION_LIST.setForeground(new Color(153, 0, 153));
        NPC_OPTION_LIST.setToolTipText("A list of Npc ID's to use for the following settings.");
        jScrollPane3.setViewportView(NPC_OPTION_LIST);

        ADD_NPC_BUTTON.setText("Add Npc");
        ADD_NPC_BUTTON.setToolTipText("Add an Npc ID to the list.");
        ADD_NPC_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        REMOVE_NPC_BUTTON1.setText("Remove Npc");
        REMOVE_NPC_BUTTON1.setToolTipText("Remove an Npc ID from the list.");
        REMOVE_NPC_BUTTON1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout NPCS_PANELLayout = new GroupLayout(NPCS_PANEL);
        NPCS_PANEL.setLayout(NPCS_PANELLayout);
        NPCS_PANELLayout.setHorizontalGroup(
                NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, NPCS_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(NPC_CHECKBOX)
                                        .addComponent(LABELBUTTON)
                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(FORCE_NPCS_CHAT_TEXT, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(FORCE_NPC_CHAT_LABEL))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(FORCE_NPC_CHAT_CMD))
                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(NPC_ANIMATION_TEXT, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(NPC_ANIMATION_BUTTON))
                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                .addComponent(ADD_NPC_BUTTON, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(REMOVE_NPC_BUTTON1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        NPCS_PANELLayout.setVerticalGroup(
                NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(NPCS_PANELLayout.createSequentialGroup()
                                                .addComponent(NPC_CHECKBOX)
                                                .addGap(11, 11, 11)
                                                .addComponent(FORCE_NPC_CHAT_LABEL)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(FORCE_NPCS_CHAT_TEXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(FORCE_NPC_CHAT_CMD))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(LABELBUTTON)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(NPC_ANIMATION_TEXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(NPC_ANIMATION_BUTTON))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                                .addGroup(NPCS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(ADD_NPC_BUTTON, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(REMOVE_NPC_BUTTON1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                                .addGap(22, 22, 22))))
        );

        CONTROL_TABS.addTab("Npcs", NPCS_PANEL);

        SERVER_NAME_LABEL.setText("Server Name:");

        SERVER_NAME_TEXT.setText(Constants.SERVER_NAME);
        SERVER_NAME_TEXT.setToolTipText("Set the name of the server.");

        ADMINS_CAN_TRADE.setSelected(Constants.ADMIN_CAN_TRADE);
        ADMINS_CAN_TRADE.setText("Admins can Trade");
        ADMINS_CAN_TRADE.setToolTipText("Determins wether or not an admin can trade other players.");

        ADMINS_CAN_DROP.setText("Admins can Drop");
        ADMINS_CAN_DROP.setSelected(Constants.ADMIN_DROP_ITEMS);
        ADMINS_CAN_DROP.setToolTipText("Determins if admins can drop an item.");


        ADMINS_CAN_SELL_ITEMS.setText("Admins can sell Items");
        ADMINS_CAN_SELL_ITEMS.setSelected(Constants.ADMIN_CAN_SELL_ITEMS);
        ADMINS_CAN_SELL_ITEMS.setToolTipText("Determins if admins can sell items or not.");

        UPDATE_SETTINGS.setText("Update Settings");
        UPDATE_SETTINGS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        GroupLayout SETTINGS_PANELLayout = new GroupLayout(SETTINGS_PANEL);
        SETTINGS_PANEL.setLayout(SETTINGS_PANELLayout);
        SETTINGS_PANELLayout.setHorizontalGroup(
                SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(SETTINGS_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(ADMINS_CAN_SELL_ITEMS)
                                        .addGroup(SETTINGS_PANELLayout.createSequentialGroup()
                                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(SERVER_NAME_LABEL)
                                                        .addGroup(SETTINGS_PANELLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(SERVER_NAME_TEXT, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(ADMINS_CAN_TRADE)
                                                        .addComponent(ADMINS_CAN_DROP))
                                                .addGap(69, 69, 69)
                                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(SETTINGS_PANELLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(UPDATE_SETTINGS, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap())
        );
        SETTINGS_PANELLayout.setVerticalGroup(
                SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(SETTINGS_PANELLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(SERVER_NAME_LABEL))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(SERVER_NAME_TEXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(ADMINS_CAN_TRADE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(ADMINS_CAN_DROP))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ADMINS_CAN_SELL_ITEMS)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SETTINGS_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(UPDATE_SETTINGS, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        SERVER_SETTINGS.addTab("Settings", SETTINGS_PANEL);

        MESSAGE_ALL.setText("Message All Players:");

        MESSAGE_ALL_TEXT.setToolTipText("Set and send a message to everyone.");

        UPDATE_SERVER_LABEL.setText("Update Server:");

        UPDATE_SERVER_TEXT.setText("60");
        UPDATE_SERVER_TEXT.setToolTipText("Set the update timer in seconds.");

        UPDATE_TIME_LABEL.setText("Seconds:");

        UPDATE_SERVER_CMD.setText("Update");
        UPDATE_SERVER_CMD.setToolTipText("Initiate the update sequence.");
        UPDATE_SERVER_CMD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        RESET_NPCS_LABEL.setText("Reset Npcs:");

        RESET_NPCS_BUTTON.setText("Reset");
        RESET_NPCS_BUTTON.setToolTipText("Reset all npcs by death.");
        RESET_NPCS_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        CLEAR_CONSOLE_LABEL.setText("Clear Console:");

        CLEAR_CONSOLE_BUTTON.setText("Clear Text");
        CLEAR_CONSOLE_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });
        MESSAGE_ALL_BUTTON.setText("Message");
        MESSAGE_ALL_BUTTON.setToolTipText("Send a message to everyone on the server");
        MESSAGE_ALL_BUTTON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        MESSAGE_ALL_BOX.setEditable(true);
        MESSAGE_ALL_BOX.setModel(new DefaultComboBoxModel(new String[]{"[Alert]", "[Update]", "[Server]", "[Control Panel]", "[Panel]", "[Console]", "[Prompt]", "[None]"}));
        MESSAGE_ALL_BOX.setToolTipText("Choose a name that will show next to the message.");

        GroupLayout SERVER_CONTROLSLayout = new GroupLayout(SERVER_CONTROLS);
        SERVER_CONTROLS.setLayout(SERVER_CONTROLSLayout);
        SERVER_CONTROLSLayout.setHorizontalGroup(
                SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(MESSAGE_ALL)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(UPDATE_SERVER_LABEL)
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(UPDATE_SERVER_CMD))
                                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                                .addComponent(UPDATE_TIME_LABEL, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(UPDATE_SERVER_TEXT, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))))
                                .addGap(41, 41, 41))
                        .addGroup(GroupLayout.Alignment.TRAILING, SERVER_CONTROLSLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(MESSAGE_ALL_TEXT, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MESSAGE_ALL_BUTTON)
                                .addGap(21, 21, 21))
                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(MESSAGE_ALL_COLOR_LABEL)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MESSAGE_ALL_COLOR_BOX, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(275, Short.MAX_VALUE))
                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(MESSAGE_ALL_NAME_LABEL)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MESSAGE_ALL_BOX, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(226, Short.MAX_VALUE))
                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(CLEAR_CONSOLE_BUTTON, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                                        .addComponent(CLEAR_CONSOLE_LABEL))
                                .addGap(18, 18, 18)
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(RESET_NPCS_LABEL)
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(RESET_NPCS_BUTTON)))
                                .addGap(203, 203, 203))
        );
        SERVER_CONTROLSLayout.setVerticalGroup(
                SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(MESSAGE_ALL)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(MESSAGE_ALL_BOX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MESSAGE_ALL_NAME_LABEL))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(MESSAGE_ALL_COLOR_BOX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MESSAGE_ALL_COLOR_LABEL)))
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(UPDATE_SERVER_LABEL)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(UPDATE_TIME_LABEL)
                                                        .addComponent(UPDATE_SERVER_TEXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(UPDATE_SERVER_CMD)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(MESSAGE_ALL_BUTTON)
                                        .addComponent(MESSAGE_ALL_TEXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SERVER_CONTROLSLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(CLEAR_CONSOLE_LABEL)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CLEAR_CONSOLE_BUTTON))
                                        .addGroup(SERVER_CONTROLSLayout.createSequentialGroup()
                                                .addComponent(RESET_NPCS_LABEL)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(RESET_NPCS_BUTTON)))
                                .addGap(60, 60, 60))
        );

        SERVER_SETTINGS.addTab("Administration", SERVER_CONTROLS);

        GroupLayout SERVER_PANELLayout = new GroupLayout(SERVER_PANEL);
        SERVER_PANEL.setLayout(SERVER_PANELLayout);
        SERVER_PANELLayout.setHorizontalGroup(
                SERVER_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(SERVER_PANELLayout.createSequentialGroup()
                                .addComponent(SERVER_SETTINGS, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SERVER_PANELLayout.setVerticalGroup(
                SERVER_PANELLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(SERVER_SETTINGS, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        CONTROL_TABS.addTab("Server", SERVER_PANEL);

        jScrollPane4.setForeground(new Color(0, 153, 0));


        ENTITY_LIST.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        ENTITY_LIST.setForeground(new Color(51, 102, 0));
        ENTITY_LIST.setToolTipText("A list of Online players.");
        SCROLL_PANE.setViewportView(ENTITY_LIST);

        MENU_BAR.setToolTipText("Tooltips :D");

        FILE_MENU.setText("File");

        DEBUG_MODE.setText("Debug Mode");
        DEBUG_MODE.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FILE_MENU.add(DEBUG_MODE);
        FILE_MENU.add(SEPARATOR_ITEM);

        EXIT_ITEM.setText("Exit");
        FILE_MENU.add(EXIT_ITEM);

        MENU_BAR.add(FILE_MENU);

        setJMenuBar(MENU_BAR);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(SCROLL_PANE, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CONTROL_TABS, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(CONTROL_TABS, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(SCROLL_PANE, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pack();
        setVisible(Constants.GUI_ENABLED);
    }

    // Holds the button commands
    public void ActionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (cmd == null) {
            System.out.println("[Console]: Null Command.");
            return;
        }
        settings().executeCommand(cmd);
    }

    public void displayMessage(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }
    // End of variables declaration

}