package com.rebotted.game.players;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.apache.mina.common.IoSession;
import com.rebotted.Connection;
import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.BankPin;
import com.rebotted.game.content.EmoteHandler;
import com.rebotted.game.content.combat.CombatAssistant;
import com.rebotted.game.content.combat.Specials;
import com.rebotted.game.content.combat.magic.Enchanting;
import com.rebotted.game.content.combat.magic.MagicTeleports;
import com.rebotted.game.content.combat.prayer.PrayerData;
import com.rebotted.game.content.combat.prayer.PrayerDrain;
import com.rebotted.game.content.combat.range.DwarfCannon;
import com.rebotted.game.content.consumables.Food;
import com.rebotted.game.content.consumables.Potions;
import com.rebotted.game.content.guilds.impl.RangersGuild;
import com.rebotted.game.content.minigames.Barrows;
import com.rebotted.game.content.minigames.Dueling;
import com.rebotted.game.content.minigames.FightPits;
import com.rebotted.game.content.minigames.PestControl;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.music.PlayList;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.content.quests.QuestAssistant;
import com.rebotted.game.content.quests.impl.*;
import com.rebotted.game.content.skills.SkillInterfaces;
import com.rebotted.game.content.skills.agility.Agility;
import com.rebotted.game.content.skills.agility.ApeAtollAgility;
import com.rebotted.game.content.skills.agility.BarbarianAgility;
import com.rebotted.game.content.skills.agility.GnomeAgility;
import com.rebotted.game.content.skills.agility.PyramidAgility;
import com.rebotted.game.content.skills.agility.WerewolfAgility;
import com.rebotted.game.content.skills.agility.WildernessAgility;
import com.rebotted.game.content.skills.cooking.Potatoes;
import com.rebotted.game.content.skills.core.Mining;
import com.rebotted.game.content.skills.crafting.GlassBlowing;
import com.rebotted.game.content.skills.runecrafting.Runecrafting;
import com.rebotted.game.content.skills.slayer.Slayer;
import com.rebotted.game.content.skills.smithing.Smithing;
import com.rebotted.game.content.skills.smithing.SmithingInterface;
import com.rebotted.game.content.traveling.Desert;
import com.rebotted.game.dialogues.DialogueHandler;
import com.rebotted.game.items.GameItem;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.items.Weight;
import com.rebotted.game.items.impl.LightSources;
import com.rebotted.game.items.impl.PotionMixing;
import com.rebotted.game.items.impl.Teles;
import com.rebotted.game.npcs.NpcActions;
import com.rebotted.game.objects.ObjectsActions;
import com.rebotted.game.shops.ShopAssistant;
import com.rebotted.net.PacketSender;
import com.rebotted.net.HostList;
import com.rebotted.net.Packet;
import com.rebotted.net.StaticPacketBuilder;
import com.rebotted.net.packets.PacketHandler;
import com.rebotted.net.packets.impl.ChallengePlayer;
import com.rebotted.util.Misc;
import com.rebotted.util.Stream;
import com.rebotted.world.ObjectManager;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
    private IoSession session;
	private final ItemAssistant itemAssistant = new ItemAssistant(this);
	private final ShopAssistant shopAssistant = new ShopAssistant(this);
	private final Trading trading = new Trading(this);
	private final Dueling duel = new Dueling(this);
	private final PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private final CombatAssistant combatAssistant = new CombatAssistant(this);
	private final ObjectsActions actionHandler = new ObjectsActions(this);
	private final NpcActions npcs = new NpcActions(this);
	private final Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private final Potions potions = new Potions(this);
	private final PotionMixing potionMixing = new PotionMixing(this);
	private final Food food = new Food(this);
	private final EmoteHandler emoteHandler = new EmoteHandler(this);
	private final SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private final Enchanting enchanting = new Enchanting(this);
	private final Potatoes potatoes = new Potatoes(this);
	private final PlayerAction playeraction = new PlayerAction(this);
	private final Desert desert = new Desert();
	private final Specials specials = new Specials(this);
	private final SoundList sound = new SoundList(this);
	public String creationAddress = "";
	private final HashMap<String, Object> temporary = new HashMap<String, Object>();
	private final PlayList playList = new PlayList(this);
	private final Agility agility = new Agility(this);
	private final Runecrafting runecrafting = new Runecrafting(this);
	private final Teles teles = new Teles();
	private final BankPin bankPin = new BankPin(this);
	private final Slayer slayer = new Slayer(this);
	private final ImpCatcher impCatcher = new ImpCatcher(this);
	private final BlackKnightsFortress blackKnightF = new BlackKnightsFortress(this);
	private final CooksAssistant cooksAssistant = new CooksAssistant(this);
	private final RomeoJuliet romeoJuliet = new RomeoJuliet(this);
	private final DoricsQuest doricsQuest = new DoricsQuest(this);
	private final VampyreSlayer vampyreSlayer = new VampyreSlayer(this);
	private final RestlessGhost restlessGhost = new RestlessGhost(this);
	private final GertrudesCat gertrudesCat = new GertrudesCat(this);
	private final SheepShearer sheepShearer = new SheepShearer(this);
	private final RuneMysteries runeMysteries = new RuneMysteries(this);
	private final WitchsPotion witchsPotion = new WitchsPotion(this);
	private final PiratesTreasure piratesTreasure = new PiratesTreasure(this);
	private final PacketSender packetSender = new PacketSender(this);
	private final DialogueHandler dialogues = new DialogueHandler(this);
	private final GnomeAgility gnomeStrongHold = new GnomeAgility(this);
	private final WildernessAgility wildernessAgility = new WildernessAgility(this);
	private final BarbarianAgility barbarianAgility = new BarbarianAgility(this);
	private final PyramidAgility pyramidAgility = new PyramidAgility(this);
	private final WerewolfAgility werewolfAgility = new WerewolfAgility(this);
	private final ApeAtollAgility apeAtollAgility = new ApeAtollAgility(this);
	private final Smithing smithing = new Smithing();
	private final SmithingInterface smithingInterface = new SmithingInterface(this);
	private final PrayerData prayer = new PrayerData();
	private final ObjectManager objectManager = new ObjectManager();
	public ArrayList<GameItem> fishingTrawlerReward = new ArrayList<GameItem>();
	private final RangersGuild rangersGuild = new RangersGuild(this);
	private GlassBlowing glassBlowing = new GlassBlowing(this);
	private Barrows barrows = new Barrows(this);
	private Mining mining = new Mining();
	private ChallengePlayer challengePlayer = new ChallengePlayer();
	private DwarfCannon dwarfCannon = new DwarfCannon(this);
	private CycleEventContainer currentTask;

	public DwarfCannon getCannon() {
		return dwarfCannon;
	}

	public ChallengePlayer getChallengePlayer() {
		return challengePlayer;
	}

	public Mining getMining() {
		return mining;
	}
	
	public Barrows getBarrows() {
		return barrows;
	}
	
    public GlassBlowing getGlassBlowing() {
        return glassBlowing;
    }
	
	public RangersGuild getRangersGuild() {
		return rangersGuild;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public SmithingInterface getSmithingInt() {
		return smithingInterface;
	}

	public Smithing getSmithing() {
		return smithing;
	}

	public ApeAtollAgility getApeAtollAgility() {
		return apeAtollAgility;
	}

	public WerewolfAgility getWerewolfAgility() {
		return werewolfAgility;
	}

	public PyramidAgility getPyramidAgility() {
		return pyramidAgility;
	}

	public BarbarianAgility getBarbarianAgility() {
		return barbarianAgility;
	}

	public WildernessAgility getWildernessAgility() {
		return wildernessAgility;
	}

	public GnomeAgility getGnomeStrongHold() {
		return gnomeStrongHold;
	}

	public DialogueHandler getDialogueHandler() {
		return dialogues;
	}

	public PacketSender getPacketSender() {
		return packetSender;
	}

	public SoundList getSound() {
		return sound;
	}

	public Object getTemporary(String name) {
		return temporary.get(name);
	}

	public void addTemporary(String name, Object value) {
		temporary.put(name, value);
	}

	public PlayList getPlayList() {
		return playList;
	}

	public Specials getSpecials() {
		return specials;
	}

	public Potatoes getPTS() {
		return potatoes;
	}

	public EmoteHandler getEmoteHandler() {
		return emoteHandler;
	}

	public SkillInterfaces getSkillInterfaces() {
		return skillInterfaces;
	}

	public Enchanting getEnchanting() {
		return enchanting;
	}

	public PlayerAction getPlayerAction() {
		return playeraction;
	}
	
	public Desert getDesert() {
		return desert;
	}

	public Agility getAgility() {
		return agility;
	}

	public Runecrafting getRC() {
		return runecrafting;
	}

	public Slayer getSlayer() {
		return slayer;
	}

	public Teles getTeles() {
		return teles;
	}

	public BankPin getBankPin() {
		return bankPin;
	}

	public ImpCatcher getImpCatcher() {
		return impCatcher;
	}

	public BlackKnightsFortress getBlackKnightsFortress() {
		return 	blackKnightF;
	}

	public PiratesTreasure getPiratesTreasure() {
		return piratesTreasure;
	}

	public CooksAssistant getCooksAssistant() {
		return cooksAssistant;
	}

	public RomeoJuliet getRomeoJuliet() {
		return romeoJuliet;
	}

	public DoricsQuest getDoricsQuest() {
		return doricsQuest;
	}

	public VampyreSlayer getVampyreSlayer() {
		return vampyreSlayer;
	}

	public RestlessGhost getRestlessGhost() {
		return restlessGhost;
	}

	public GertrudesCat getGertrudesCat() {
		return gertrudesCat;
	}

	public SheepShearer getSheepShearer() {
		return sheepShearer;
	}

	public RuneMysteries getRuneMysteries() {
		return runeMysteries;
	}

	public WitchsPotion getWitchesPotion() {
		return witchsPotion;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItemAssistant() {
		return itemAssistant;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public ShopAssistant getShopAssistant() {
		return shopAssistant;
	}

	public Trading getTrading() {
		return trading;
	}

	public Dueling getDueling() {
		return duel;
	}

	public CombatAssistant getCombatAssistant() {
		return combatAssistant;
	}

	public PrayerData getPrayer() {
		return prayer;
	}

	public ObjectsActions getObjects() {
		return actionHandler;
	}

	public NpcActions getNpcs() {
		return npcs;
	}

	public IoSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public int TotalShopItems;

	public void startCurrentTask(int ticksBetweenExecution, CycleEvent event) {
		endCurrentTask();
		currentTask = CycleEventHandler.getSingleton().addEvent(this, event, ticksBetweenExecution);
	}

	public CycleEventContainer getCurrentTask() {
		return currentTask;
	}

	public void endCurrentTask() {
		if (currentTask != null && currentTask.isRunning()) {
			currentTask.stop();
			currentTask = null;
		}
	}

	private Map<Integer, TinterfaceText> interfaceText = new HashMap<Integer, TinterfaceText>();
	
	public class TinterfaceText {
		public int id;
		public String currentState;
		
		public TinterfaceText(String s, int id) {
			this.currentState = s;
			this.id = id;
		}
		
	}

	public boolean checkPacket126Update(String text, int id) {
		if(!interfaceText.containsKey(id)) {
			interfaceText.put(id, new TinterfaceText(text, id));
		} else {
			TinterfaceText t = interfaceText.get(id);
			if(text.equals(t.currentState)) {
				return false;
			}
			t.currentState = text;
		}
		return true;
	}

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;

	// used for bots
	public Client(IoSession s) {
		super(-1);
		isBot = true;
		session = null;
		inStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		session = s;
		synchronized (this) {
			outStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}

	/**
	 * Resets the shaking of the player's screen.
	 */
	public void resetShaking() {
		getPacketSender().shakeScreen(1, 0, 0, 0);
	}

	public final String disabled() {
		return "Skill is disabled for testing period.";
	}

	public void puzzleBarrow() {
		getPacketSender().sendFrame246(4545, 250, 6833);
		getPacketSender().sendFrame126("1.", 4553);
		getPacketSender().sendFrame246(4546, 250, 6832);
		getPacketSender().sendFrame126("2.", 4554);
		getPacketSender().sendFrame246(4547, 250, 6830);
		getPacketSender().sendFrame126("3.", 4555);
		getPacketSender().sendFrame246(4548, 250, 6829);
		getPacketSender().sendFrame126("4.", 4556);
		getPacketSender().sendFrame246(4550, 250, 3454);
		getPacketSender().sendFrame246(4551, 250, 8746);
		getPacketSender().sendFrame246(4552, 250, 6830);
		getPacketSender().showInterface(4543);
	}

	public void flushOutStream() {
		if (disconnected || outStream == null || outStream.currentOffset == 0) {
			return;
		}
		synchronized (this) {
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset];
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
	}

	public void sendClan(String name, String message, String clan, int rights) {
		if (outStream != null) {
			outStream.createFrameVarSizeWord(217);
			outStream.writeString(name);
			outStream.writeString(message);
			outStream.writeString(clan);
			outStream.writeWord(rights);
			outStream.endFrameVarSize();
		}
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	@Override
	public void destruct() {
		if (session == null) {
			return;
		}
		if (getCannon().hasCannon()) {
			getCannon().removeObject(cannonX, cannonY);
			for(int i = 0; i < GameEngine.cannonsX.length; i++) {
				if (GameEngine.cannonsX[i] == cannonX && GameEngine.cannonsY[i] == cannonY) {
					GameEngine.cannonsX[i] = 0;
					GameEngine.cannonsY[i] = 0;
					GameEngine.cannonsO[i] = null;
				}
				lostCannon = true;
				cannonX = -1;
				cannonY = -1;
			}
		}
		if(GameEngine.trawler.players.contains(this)) {
			GameEngine.trawler.players.remove(this);
	    }
		if (CastleWars.isInCwWait(this)) {
			CastleWars.leaveWaitingRoom(this);
		}
		if (CastleWars.isInCw(this)) {
			CastleWars.removePlayerFromCw(this);
		}
		if (FightPits.getState(this) != null) {
			FightPits.removePlayer(this, true);
		}
		if (PestControl.isInGame(this)) {
			PestControl.removePlayerGame(this);
			getPlayerAssistant().movePlayer(2657, 2639, 0);
		}
		if (PestControl.isInPcBoat(this)) {
			PestControl.leaveWaitingBoat(this);
			getPlayerAssistant().movePlayer(2657, 2639, 0);
		}
		if (hasNpc == true) {
			getSummon().pickUpClean(this, summonId);
		}

		if(GameEngine.ersSecret  != null && !GameEngine.ersSecret.equals("") && this.playerRights < 2) {
			boolean debugMessage = false;
			System.out.println("Updating highscores for " + this.playerName + "!");
			com.everythingrs.hiscores.Hiscores.update(GameEngine.ersSecret, "Normal Mode", this.playerName, this.playerRights, this.playerXP, debugMessage);
		} else {
			System.out.println("EverythingRS API Disabled, highscores not saved!");
		}

		Misc.println("[DEREGISTERED]: " + playerName + "");
		HostList.getHostList().remove(session);
		CycleEventHandler.getSingleton().stopEvents(this);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
		// PlayerSave.saveGame(this);
	}

	@Override
	public void initialize() {
		getPlayerAssistant().loginScreen();
		if (Connection.isNamedBanned(playerName)) {
			logout();
			return;
		}
		synchronized (this) {
			if (getOutStream() != null) {
				outStream.createFrame(249);
				outStream.writeByteA(membership ? 1 : 0);
				outStream.writeWordBigEndianA(playerId);
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (j == playerId) {
						continue;
					}
					if (PlayerHandler.players[j] != null) {
						if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
							disconnected = true;
						}
					}
				}
			}
			lastLoginDate = getLastLogin();
			QuestAssistant.sendStages(this);
			if (hasNpc == true) {
				if (summonId > 0) {
					GameEngine.npcHandler.spawnNpc3(this, summonId, absX, absY - 1,
							heightLevel, 0, 120, 25, 200, 200, true, false,
							true);
				}
			}
			if (questPoints > QuestAssistant.MAXIMUM_QUESTPOINTS || playerRights > 2) {
				questPoints = QuestAssistant.MAXIMUM_QUESTPOINTS;// check for abusers
			}
			if (playerHitpoints < 0) {
				isDead = true;
			}
			if (playerLevel[playerHitpoints] > 99) {
				playerLevel[playerHitpoints] = 99;// check for abusers
				getPlayerAssistant().refreshSkill(3);
			}
			if (playerLevel[playerFarming] > 1 && playerRights < 3) {
				playerLevel[playerFarming] = 1;
				getPlayerAssistant().refreshSkill(playerFarming);
			}
			if (tutorialProgress > 0 && tutorialProgress < 36 && GameConstants.TUTORIAL_ISLAND) {
				getPacketSender().sendMessage("@blu@Continue the tutorial from the last step you were on.@bla@");
			}
			if (tutorialProgress > 35) {
				getPlayerAssistant().sendSidebars();
				Weight.updateWeight(this);
				getPacketSender().sendMessage("Welcome to @blu@" + GameConstants.SERVER_NAME + "@bla@ - we are currently in Server Stage v@blu@" + GameConstants.TEST_VERSION + "@bla@.");
				getPacketSender().sendMessage("@red@Did you know?@bla@ We're open source! Pull requests are welcome");
				getPacketSender().sendMessage("Source code at github.com/dginovker/2006rebotted");
				getPacketSender().sendMessage("Join our Discord: discord.gg/4zrA2Wy");
				/*if (!hasBankpin) { //Kind of annoying. Maybe add Random % 10 or something.
					getActionSender().sendMessage("You do not have a bank pin it is highly recommended you set one.");
				}*/
			}
			getPlayerAssistant().firstTimeTutorial();
			getItemAssistant().sendWeapon(playerEquipment[playerWeapon], ItemAssistant.getItemName(playerEquipment[playerWeapon]));
			for (int i = 0; i < 25; i++) {
				getPacketSender().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPlayerAssistant().refreshSkill(i);
			}
			for (int p = 0; p < getPrayer().PRAYER.length; p++) { // reset
																	// prayer
																	// glows
				getPrayer().prayerActive[p] = false;
				getPacketSender().sendConfig(getPrayer().PRAYER_GLOW[p], 0);
			}
			lastX = absX;
			lastY = absY;
			lastH = heightLevel;
			if (inWild()) {
				WildernessWarning = true;
			}
			if (splitChat == true) {
				getPacketSender().sendConfig(502, 1);
				getPacketSender().sendConfig(287, 1);
			} else {
				getPacketSender().sendConfig(502, 0);
				getPacketSender().sendConfig(287, 0);
			}
			if (isRunning2) {
				getPacketSender().sendConfig(504, 1);
				getPacketSender().sendConfig(173, 1);
			} else {
				getPacketSender().sendConfig(504, 0);
				getPacketSender().sendConfig(173, 0);
			}

			getPlayList().fixAllColors();
			getPlayerAction().setAction(false);
			getPlayerAction().canWalk(true);
			getPlayerAssistant().handleWeaponStyle();
			MagicTeleports.handleLoginText(this);
			accountFlagged = getPlayerAssistant().checkForFlags();
			getPacketSender().sendConfig(108, 0);// resets autocast button
			getPacketSender().sendFrame107(); // reset screen
			getPacketSender().setChatOptions(0, 0, 0); // reset private
															// messaging options
			correctCoordinates();
			getPacketSender().showOption(4, 0, "Trade With", 3);
			getPacketSender().showOption(5, 0, "Follow", 4);
			getItemAssistant().resetItems(3214);
			getItemAssistant().resetBonus();
			getItemAssistant().getBonus();
			getItemAssistant().writeBonus();
			getItemAssistant().setEquipment(playerEquipment[playerHat], 1,
					playerHat);
			getItemAssistant().setEquipment(playerEquipment[playerCape], 1,
					playerCape);
			getItemAssistant().setEquipment(playerEquipment[playerAmulet], 1,
					playerAmulet);
			getItemAssistant().setEquipment(playerEquipment[playerArrows],
					playerEquipmentN[playerArrows], playerArrows);
			getItemAssistant().setEquipment(playerEquipment[playerChest], 1,
					playerChest);
			getItemAssistant().setEquipment(playerEquipment[playerShield], 1,
					playerShield);
			getItemAssistant().setEquipment(playerEquipment[playerLegs], 1,
					playerLegs);
			getItemAssistant().setEquipment(playerEquipment[playerHands], 1,
					playerHands);
			getItemAssistant().setEquipment(playerEquipment[playerFeet], 1,
					playerFeet);
			getItemAssistant().setEquipment(playerEquipment[playerRing], 1,
					playerRing);
			getItemAssistant().setEquipment(playerEquipment[playerWeapon],
					playerEquipmentN[playerWeapon], playerWeapon);
			getCombatAssistant().getPlayerAnimIndex();
			getPlayerAssistant().logIntoPM();
			getItemAssistant().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = GameConstants.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: " + playerName + "");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPlayerAssistant().resetFollow();
			LightSources.saveBrightness(this);
			getPlayerAssistant().sendAutoRetalitate();
			getCannon().loginCheck();
		}
	}

	@Override
	public void update() {
		synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		}
	}

	public void logout() {
		logout(false);
	}

	public void logout(boolean forceLogout) {
		synchronized (this) {
			if(GameEngine.trawler.players.contains(this)) {
				GameEngine.trawler.players.remove(this);
	        }
			if (getCannon().hasCannon()) {
				getCannon().removeObject(cannonX, cannonY);
				for(int i = 0; i < GameEngine.cannonsX.length; i++) {
					if (GameEngine.cannonsX[i] == cannonX && GameEngine.cannonsY[i] == cannonY) {
						GameEngine.cannonsX[i] = 0;
						GameEngine.cannonsY[i] = 0;
						GameEngine.cannonsO[i] = null;
					}
					lostCannon = true;
					cannonX = -1;
					cannonY = -1;
				}
			}
			if (CastleWars.isInCw(this)) {
				CastleWars.removePlayerFromCw(this);
			}
			if (CastleWars.isInCwWait(this)) {
				CastleWars.leaveWaitingRoom(this);
			}
			if (FightPits.getState(this) != null) {
				FightPits.removePlayer(this, true);
			}
			if (PestControl.isInGame(this)) {
				PestControl.removePlayerGame(this);
				getPlayerAssistant().movePlayer(2657, 2639, 0);
			}
			if (PestControl.isInPcBoat(this)) {
				PestControl.leaveWaitingBoat(this);
				getPlayerAssistant().movePlayer(2657, 2639, 0);
			}
			if(!forceLogout && (underAttackBy > 0 || underAttackBy2 > 0)) {
				getPacketSender().sendMessage("You can't logout during combat!");
				return;
			}
		    lastLoginDate = getLastLogin();
			lastX = absX;
			lastY = absY;
			lastH = heightLevel;
			CycleEventHandler.getSingleton().stopEvents(this);
			if (hasNpc == true) {
				getSummon().pickUpClean(this, summonId);
			}
			if (forceLogout || System.currentTimeMillis() - logoutDelay > 2500) {
				if (!isBot)
					outStream.createFrame(109);
				properLogout = true;
			} else {
				getPacketSender().sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		}
	}


	public int packetSize = 0, packetType = -1;
	public boolean WildernessWarning = false;

	public void antiFirePotion() {
		   CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				antiFirePot = false;
				getPacketSender().sendMessage("Your resistance to dragon fire has worn off.");
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 200);
	}

	public boolean isBusy = false;

	public boolean checkBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}
	
	 public int getLastLogin() {
	        Calendar cal = new GregorianCalendar();
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        int month = cal.get(Calendar.MONTH);
	        int year = cal.get(Calendar.YEAR);
	        return (year * 10000) + (month * 100) + day;
	    }

	@Override
	public void updateWalkEntities() {
		if (inWild() && !inCw()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (modY - 3520) / 8 + 1;
			getPacketSender().walkableInterface(197);
			if (GameConstants.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPacketSender().sendFrame126("@yel@Level: " + wildLevel,
							199);
				} else {
					getPacketSender().sendFrame126("@yel@Level: " + wildLevel,
							199);
				}
			} else {
				getPacketSender().multiWay(-1);
				getPacketSender().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPacketSender().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPacketSender().walkableInterface(201);
			if (duelStatus == 5) {
				getPacketSender().showOption(3, 0, "Attack", 1);
			} else {
				getPacketSender().showOption(3, 0, "Challenge", 1);
			}
		} else if (getPlayerAssistant().inPitsWait()) {
			getPacketSender().showOption(3, 0, "Null", 1);
        } else if(GameEngine.trawler.players.contains(this)) {
        	getPacketSender().walkableInterface(11908);
		} else if (isInBarrows() || isInBarrows2()) {
			getPacketSender().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPacketSender().walkableInterface(4535);
		} else if (inCw() || inPits) {
			getPacketSender().showOption(3, 0, "Attack", 1);
		} else {
			getPacketSender().sendMapState(0);
			getPacketSender().walkableInterface(-1);
			getPacketSender().showOption(3, 0, "Null", 1);
		}
	}

	public Client getClient(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < GameConstants.MAX_PLAYERS; i++) {
			if (validClient(i)) {
				Client client = getClient(i);
				if (client.playerName.toLowerCase().equalsIgnoreCase(name)) {
					return client;
				}
			}
		}
		return null;
	}

	public Client getClient(int id) {
		return (Client) PlayerHandler.players[id];
	}

	public boolean validClient(int id) {
		if (id < 0 || id > GameConstants.MAX_PLAYERS) {
			return false;
		}
		return validClient(getClient(id));
	}

	public boolean validClient(String name) {
		return validClient(getClient(name));
	}

	public boolean validClient(Client client) {
		return client != null && !client.disconnected;
	}

	@Override
	public void process() {

		if (playerEnergy < 100 && System.currentTimeMillis() - lastIncrease >= getPlayerAssistant().raiseTimer()) {
			playerEnergy += 1;
			lastIncrease = System.currentTimeMillis();
		}
		if (playerEnergy <= 0 && isRunning2) {
			isRunning2 = false;
			getPacketSender().sendConfig(504, 0);
			getPacketSender().sendConfig(173, 0);
		}
		getPlayerAssistant().writeEnergy();

		if (System.currentTimeMillis() - specDelay > GameConstants.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10) {
					specAmount = 10;
				}
				getItemAssistant().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (followId > 0) {
			getPlayerAssistant().followPlayer();
		} else if (followId2 > 0) {
			getPlayerAssistant().followNpc();
		}

		if (System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + --duelCount);
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[GameConstants.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}

		PrayerDrain.handlePrayerDrain(this);

		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPacketSender().setSkillLevel(level,
								playerLevel[level], playerXP[level]);
						getPlayerAssistant().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPacketSender().setSkillLevel(level,
							playerLevel[level], playerXP[level]);
					getPlayerAssistant().refreshSkill(level);
				}
			}
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPacketSender().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPacketSender().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPlayerAssistant().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPlayerAssistant().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPlayerAssistant().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}

		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPlayerAssistant().changeLocation();
				}
				if (teleTimer == 4) {
					teleTimer--;
					getPlayerAssistant().processTeleport();
				}
				if (teleTimer == 7 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombatAssistant().delayedHit(oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombatAssistant().playerDelayedHit(oldPlayerIndex);
			}
		}

		combatAssistant.attackingNpcTick();
		combatAssistant.attackingPlayerTick();

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombatAssistant().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombatAssistant().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombatAssistant().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombatAssistant().attackPlayer(playerIndex);
			}
		}

		if (timeOutCounter > GameConstants.TIMEOUT) {
			if (!isBot)
				logout(true);
		}

		timeOutCounter++;
	}

	public void queueMessage(Packet arg1) {
		// synchronized(queuedPackets) {
		// if (arg1.getId() != 41)
		queuedPackets.add(arg1);
		// else
		// processPacket(arg1);
		// }
	}

	@Override
	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized (queuedPackets) {
			p = queuedPackets.poll();
		}
		if (p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if (packetType > 0) {
			// getPacketDispatcher().sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}

	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if (p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if (packetType > 0) {
				// getPacketDispatcher().sendMessage("PacketType: " +
				// packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}

	public int soundVolume = 10;

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 * @param volume
	 *            The volume amount of the sound (1-100)
	 * @param delay
	 *            The delay (0 = immediately 30 = 1/2cycle 60=full cycle) before
	 *            the sound plays.
	 */
	public void sendSound(int id, int volume, int delay) {
		try {
			outStream.createFrameVarSize(174);
			outStream.writeWord(id);
			outStream.writeByte(volume);
			outStream.writeWord(delay);
			updateRequired = true;
			appearanceUpdateRequired = true;
			outStream.endFrameVarSize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 * @param volume
	 *            The volume amount of the sound (1-100)
	 */
	public void sendSound(int id, int volume) {
		sendSound(id, volume, 0);
	}

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 */
	public void sendSound(int id) {
		sendSound(id, 100);// pretty sure it's 100 just double check
	}

	/**
	 * Play sounds
	 * 
	 * @param SOUNDID
	 *            : ID
	 * @param delay
	 *            : SOUND DELAY
	 */
	public void playSound(Client c, int SOUNDID, int delay) {
		if (GameConstants.SOUND) {
			if (soundVolume <= -1) {
				return;
			}
			/**
			 * Deal with regions We dont need to play this again because you are
			 * in the current region
			 */
			if (c != null) {
				if (c.soundVolume >= 0) {
					if (c.goodDistance(c.absX, c.absY, absX, absY, 2)) {
						System.out.println("Playing sound " + c.playerName
								+ ", Id: " + SOUNDID + ", Vol: "
								+ c.soundVolume);
						if (c.getOutStream() != null) {
							c.getOutStream().createFrame(174);
							c.getOutStream().writeWord(SOUNDID);
							c.getOutStream().writeByte(c.soundVolume);
							c.getOutStream().writeWord( /* delay */0);
						}
					}
				}
			}

		}
	}

	public void correctCoordinates() {
		if (inPcGame()) {
			getPlayerAssistant().movePlayer(2657, 2639, 0);
			if (FightPitsArea()) {
				getPlayerAssistant().movePlayer(2399, 5178, 0);
				if (inFightCaves()) {
					getDialogueHandler().sendDialogues(101, 2617);
					getPlayerAssistant().movePlayer(absX, absY, playerId * 4);
					getPacketSender().sendMessage("Your wave will start in 10 seconds.");
					 CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							GameEngine.fightCaves.spawnNextWave((Client) PlayerHandler.players[playerId]);
							container.stop();
						}
						@Override
							public void stop() {
								
							}
					}, 16);
				}
			}
		}
	}
	
	 public void trawlerFade(final int x, final int y, final int height) {
	        if (System.currentTimeMillis() - lastAction > 5000) {
	            lastAction = System.currentTimeMillis();
	            resetWalkingQueue();
	            CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
	                int tStage = 5;
	                public void execute(CycleEventContainer container) {
	                    if (tStage == 5) {
	                    	getPacketSender().showInterface(18460);
	                        }
	                        if (tStage == 4) {
	                          getPlayerAssistant().movePlayer(x, y, height);
	                          getPlayerAssistant().resetAnimationsToPrevious();
	                          appearanceUpdateRequired = true;
	                        }
	                        if (tStage == 3) {
	                          getPacketSender().showInterface(18452);
	                        }
	                        if (tStage == 1) {
	                            container.stop();
	                            return;
	                        }
	                        if (tStage > 0) {
	                            tStage--;
	                          }
	                }
	                public void stop() {
	                    getPacketSender().closeAllWindows();
	                    tStage = 0;
	                }
	            }, 1);
	        }
	    }
	
	public void fade(final int x, final int y, final int height) {
        if (System.currentTimeMillis() - lastAction > 5000) {
            lastAction = System.currentTimeMillis();
            resetWalkingQueue();
            CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
                int tStage = 6;
                public void execute(CycleEventContainer container) {
                    if (tStage == 6) {
                    	getPacketSender().showInterface(18460);
                        }
                        if (tStage == 5) {
                          getPlayerAssistant().movePlayer(x, y, height);
                          updateRequired = true;
                          appearanceUpdateRequired = true;
                        }
                        if (tStage == 4) {
                        	getPacketSender().showInterface(18452);
                        }
                        if (tStage == 1) {
                            container.stop();
                            return;
                        }
                        if (tStage > 0) {
                            tStage--;
                          }
                }
                public void stop() {
                	getPacketSender().closeAllWindows();
                    tStage = 0;
                }
            }, 1);
        }
    }

	/**
	 * The option the player clicked
	 */

	private int optionClicked = -1;

	/**
	 * @return the option clicked
	 */

	public int getOptionClicked() {
		return optionClicked;
	}

	/**
	 * Sets the option clicked
	 * 
	 * @param i
	 *            the option clicked
	 */

	public void setOptionClicked(int i) {
		optionClicked = i;
	}
	
	public String statedInterface = "";
	
	public String getStatedInterface() {
		return statedInterface;
	}
	
	public void setStatedInterface(String statedInterface) {
		this.statedInterface = statedInterface;
	}

}
