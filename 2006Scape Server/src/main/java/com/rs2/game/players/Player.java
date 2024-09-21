package com.rs2.game.players;

import com.everythingrs.hiscores.Hiscores;
import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.*;
import com.rs2.game.content.BankPin;
import com.rs2.game.content.EmoteHandler;
import com.rs2.game.content.combat.CombatAssistant;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.Specials;
import com.rs2.game.content.combat.magic.Enchanting;
import com.rs2.game.content.combat.prayer.PrayerData;
import com.rs2.game.content.combat.prayer.PrayerDrain;
import com.rs2.game.content.combat.range.DwarfCannon;
import com.rs2.game.content.combat.range.RangeData;
import com.rs2.game.content.consumables.Potions;
import com.rs2.game.content.guilds.impl.RangersGuild;
import com.rs2.game.content.minigames.Barrows;
import com.rs2.game.content.minigames.Dueling;
import com.rs2.game.content.minigames.FightPits;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.minigames.magetrainingarena.MageTrainingArena;
import com.rs2.game.content.music.PlayList;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.skills.SkillInterfaces;
import com.rs2.game.content.skills.agility.*;
import com.rs2.game.content.skills.cooking.Potatoes;
import com.rs2.game.content.skills.core.Mining;
import com.rs2.game.content.skills.crafting.GlassBlowing;
import com.rs2.game.content.skills.farming.*;
import com.rs2.game.content.skills.fletching.LogCuttingInterface;
import com.rs2.game.content.skills.runecrafting.Runecrafting;
import com.rs2.game.content.skills.slayer.Slayer;
import com.rs2.game.content.skills.smithing.Smithing;
import com.rs2.game.content.skills.smithing.SmithingInterface;
import com.rs2.game.content.traveling.DesertCactus;
import com.rs2.game.content.traveling.DesertHeat;
import com.rs2.game.dialogues.DialogueHandler;
import com.rs2.game.globalworldobjects.DoubleGates;
import com.rs2.game.globalworldobjects.GateHandler;
import com.rs2.game.globalworldobjects.SingleGates;
import com.rs2.game.items.*;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.impl.Greegree.MonkeyData;
import com.rs2.game.items.impl.PotionMixing;
import com.rs2.game.items.impl.Teles;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcActions;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.npcs.impl.Pets;
import com.rs2.game.objects.ObjectsActions;
import com.rs2.game.shops.ShopAssistant;
import com.rs2.gui.ControlPanel;
import com.rs2.net.Packet;
import com.rs2.net.PacketSender;
import com.rs2.net.packets.PacketHandler;
import com.rs2.net.packets.impl.ChallengePlayer;
import com.rs2.plugin.PluginService;
import com.rs2.util.Misc;
import com.rs2.util.Stream;
import com.rs2.world.Boundary;
import com.rs2.world.ObjectManager;

import io.netty.buffer.Unpooled;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apollo.game.session.GameSession;
import org.apollo.util.security.IsaacRandom;

public abstract class Player {

	public byte buffer[] = null;
	public String lastConnectedFrom;
	public int xpRate = 1;
	public String discordCode;
	private Compost compost = new Compost(this);
	private Allotments allotment = new Allotments(this);
	private Flowers flower = new Flowers(this);
	private Herbs herb = new Herbs(this);
	private Hops hops = new Hops(this);
	private Bushes bushes = new Bushes(this);
	private Seedling seedling = new Seedling(this);
	private WoodTrees trees = new WoodTrees(this);
	private FruitTree fruitTrees = new FruitTree(this);
	private SpecialPlantOne specialPlantOne = new SpecialPlantOne(this);
	private SpecialPlantTwo specialPlantTwo = new SpecialPlantTwo(this);
	private ToolLeprechaun toolLeprechaun = new ToolLeprechaun(this);
	public Stream outStream = null;
	public GameSession session;
	private final ItemAssistant itemAssistant = new ItemAssistant(this);
	private final ShopAssistant shopAssistant = new ShopAssistant(this);
	private final MageTrainingArena mageArena = new MageTrainingArena(this);
	private final Trading trading = new Trading(this);
	private final Dueling duel = new Dueling(this);
	private final PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private final CombatAssistant combatAssistant = new CombatAssistant(this);
	private final ObjectsActions actionHandler = new ObjectsActions(this);
	private final NpcActions npcs = new NpcActions(this);
	private final BlockingQueue<Packet> queuedPackets = new ArrayBlockingQueue<Packet>(25);
	private final Potions potions = new Potions(this);
	private final PotionMixing potionMixing = new PotionMixing(this);
	private final EmoteHandler emoteHandler = new EmoteHandler(this);
	private final SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private final Enchanting enchanting = new Enchanting(this);
	private final Potatoes potatoes = new Potatoes(this);
	private final PlayerAction playeraction = new PlayerAction(this);
	private final DesertCactus desert = new DesertCactus();
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
	private final LogCuttingInterface fletching = new LogCuttingInterface();
	private final ObjectManager objectManager = new ObjectManager();
	public ArrayList<GameItem> fishingTrawlerReward = new ArrayList<GameItem>();
	private final RangersGuild rangersGuild = new RangersGuild(this);
	private GlassBlowing glassBlowing = new GlassBlowing(this);
	private Barrows barrows = new Barrows(this);
	private Mining mining = new Mining();
	private ChallengePlayer challengePlayer = new ChallengePlayer();
	private DwarfCannon dwarfCannon = new DwarfCannon(this);
	private CycleEventContainer currentTask;
	private GateHandler gateHandler = new GateHandler();
	private SingleGates singleGates = new SingleGates();
	private DoubleGates doubleGates = new DoubleGates();
	
	private Map<Integer, Integer> npcKillCounts = new HashMap<>();
	public boolean displayBossKcMessages = false;
	public boolean displaySlayerKcMessages = false;
	public boolean displayRegularKcMessages = false;
	
	public int getNpcKillCount(int npcId) {
		return npcKillCounts.getOrDefault(npcId, 0);
	}
	
	public Map<Integer, Integer> getNpcKillCounts() {
		return npcKillCounts;
	}
	
	public void incrementNpcKillCount(int npcId, int count) {
		npcKillCounts.put(npcId, npcKillCounts.getOrDefault(npcId, 0) + count);
	}
	
	public int lastMainFrameInterface = -1; //Possibly used in future to prevent packet exploits

	public int getXPRate() { return xpRate; }

	public void setXPRate(int xpRate) { this.xpRate = xpRate; }

	public String getDiscordCode() { return discordCode; }

	public void setDiscordCode(String code) { this.discordCode = code; }

	public boolean isPreaching() {
		return preaching;
	}

	public void setPreaching(boolean preaching) {
		this.preaching = preaching;
	}

	public boolean preaching;

	public Compost getCompost() {
		return compost;
	}

	public Allotments getAllotment() {
		return allotment;
	}

	public Flowers getFlowers() {
		return flower;
	}

	public Herbs getHerbs() {
		return herb;
	}

	public Hops getHops() {
		return hops;
	}

	public Bushes getBushes() {
		return bushes;
	}

	public Seedling getSeedling() {
		return seedling;
	}

	public WoodTrees getTrees() {
		return trees;
	}

	public FruitTree getFruitTrees() {
		return fruitTrees;
	}

	public SpecialPlantOne getSpecialPlantOne() {
		return specialPlantOne;
	}

	public SpecialPlantTwo getSpecialPlantTwo() {
		return specialPlantTwo;
	}

	public ToolLeprechaun getFarmingTools() {
		return toolLeprechaun;
	}


	public LogCuttingInterface getFletching() {
		return fletching;
	}

	public SingleGates getSingleGates() {
		return singleGates;
	}

	public DoubleGates getDoubleGates() {
		return doubleGates;
	}

	public GateHandler getGateHandler() {
		return gateHandler;
	}

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

	public DesertCactus getDesert() {
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

	public MageTrainingArena getMageTrainingArena() {
		return mageArena;
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

	public GameSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Inventory getInventory() {
		return inventory;
	}

	private Inventory inventory = new Inventory(this);


	private int tempInteger;
	public boolean tempBoolean;

	public void setTempInteger(int tempInteger) {
		this.tempInteger = tempInteger;
	}

	public int getTempInteger() {
		return tempInteger;
	}

	public int totalShopItems;

	public boolean stopPlayer(boolean stop) {
		return (stop ? stopPlayerPacket == true : stopPlayerPacket == false);
	}

	public long objectDelay;

	public long getObjectDelay() {
		return (objectDelay);
	}

	public long setObjectDelay(long delay) {
		return (objectDelay = delay);
	}

	public boolean isSnowy;

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
		getPacketSender().sendString("1.", 4553);
		getPacketSender().sendFrame246(4546, 250, 6832);
		getPacketSender().sendString("2.", 4554);
		getPacketSender().sendFrame246(4547, 250, 6830);
		getPacketSender().sendString("3.", 4555);
		getPacketSender().sendFrame246(4548, 250, 6829);
		getPacketSender().sendString("4.", 4556);
		getPacketSender().sendFrame246(4550, 250, 3454);
		getPacketSender().sendFrame246(4551, 250, 8746);
		getPacketSender().sendFrame246(4552, 250, 6830);
		getPacketSender().showInterface(4543);
	}

	public void flushOutStream() {
		if (disconnected || outStream == null || outStream.currentOffset == 0 || (session != null && !session.isActive())) {
			return;
		}
		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);

		//	Packet packet = new Packet(-1, Type.FIXED, Unpooled.wrappedBuffer(temp));
		//	session.write(packet);
		session.write(Unpooled.buffer().writeBytes(temp));
		outStream.currentOffset = 0;


		//	ByteBuf buffer = Unpooled.buffer();
		//	buffer.writeBytes(temp);

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

	public void destruct() {
		if (session == null) {
			return;
		}
		if(Constants.GUI_ENABLED)
			ControlPanel.removeEntity(playerName);
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
		if (hasNpc) {
			getSummon().pickUpPet(this, summonId);
		}

		if(GameEngine.ersSecret  != null && !GameEngine.ersSecret.equals("") && this.playerRights < 2) {
			boolean debugMessage = false;
			System.out.println("Updating highscores for " + this.playerName + "!");
			Hiscores.update(GameEngine.ersSecret, "Normal Mode", this.playerName, this.playerRights, this.playerXP, debugMessage);
		} else {
			System.out.println("EverythingRS API Disabled, highscores not saved!");
		}

		System.out.println("[DEREGISTERED]: " + playerName + "");
		// HostList.getHostList().remove(session);
		CycleEventHandler.getSingleton().stopEvents(this);
		disconnected = true;
		session.close();
		session = null;
		outStream = null;
		isActive = false;
		buffer = null;
		playerListSize = 0;
		for (int i = 0; i < maxPlayerListSize; i++) {
			playerList[i] = null;
		}
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

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
			if(!forceLogout && (underAttackBy > 0 || underAttackBy2 > 0) || duelStatus == 5) {
				getPacketSender().sendMessage("You can't logout during combat!");
				return;
			}
			lastLoginDate = getLastLogin();
			lastX = absX;
			lastY = absY;
			lastH = heightLevel;
			CycleEventHandler.getSingleton().stopEvents(this);
			if (hasNpc) {
				getSummon().pickUpPet(this, summonId);
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

	/**
	 * This worlds event provider.
	 */
	private static final UniversalEventProvider eventProvider = new UniversalEventProvider();

	/**
	 * The service for plugins.
	 */
	private static final PluginService pluginService = new PluginService();

	/**
	 * Posts an event to this worlds event provider.
	 *
	 * @param player
	 *            The player to post the event for.
	 * @param event
	 *            The event to post.
	 */
	public <E extends Event> void post(Player player, E event) {
		eventProvider.post(player, event);
	}

	/**
	 * Posts an event to this world's event provider.
	 *
	 * @param event
	 *            The event to post.
	 */
	public <E extends Event> void post(E event) {
		post(this, event);
	}

	/**
	 * Provides an event subscriber to this worlds event provider.
	 *
	 * @param subscriber
	 *            The event subscriber.
	 */
	public static <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber) {
		eventProvider.provideSubscriber(subscriber);
	}

	/**
	 * Deprives an event subscriber to this worlds event provider.
	 *
	 * @param subscriber
	 *            The event subscriber.
	 */
	public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
		eventProvider.depriveSubscriber(subscriber);
	}

	/**
	 * Gets the service for plugins.
	 */
	public static PluginService getPluginService() {
		return pluginService;
	}

	public UniversalEventProvider getSubscribers() {
		return eventProvider;
	}

	public boolean wildernessWarning;
	public int axeAnimation = -1;

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

	public boolean isBusy;

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

	public void updateWalkEntities() {
		if (inWild() && !inCw()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (modY - 3520) / 8 + 1;
			getPacketSender().walkableInterface(197);
			isSnowy = false;
			if (CombatConstants.SINGLE_AND_MULTI_ZONES) {
				if (Boundary.isIn(this, Boundary.MULTI)) {
					getPacketSender().sendString("@yel@Level: " + wildLevel, 199);
				} else {
					getPacketSender().sendString("@yel@Level: " + wildLevel, 199);
				}
			} else {
				getPacketSender().multiWay(-1);
				getPacketSender().sendString("@yel@Level: " + wildLevel, 199);
			}
			getPacketSender().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPacketSender().walkableInterface(201);
			isSnowy = false;
			if (duelStatus == 5) {
				getPacketSender().showOption(3, 0, "Attack", 1);
			} else {
				getPacketSender().showOption(3, 0, "Challenge", 1);
			}
		} else if (getPlayerAssistant().inPitsWait()) {
			getPacketSender().showOption(3, 0, "Null", 1);
		} else if(GameEngine.trawler.players.contains(this)) {
			getPacketSender().walkableInterface(11908);
			isSnowy = false;
		} else if (Boundary.isIn(this, Boundary.BARROWS) || Boundary.isIn(this, Boundary.BARROWS_UNDERGROUND)) {
			getPacketSender().sendString("Kill Count: " + barrowsKillCount, 4536);
			getPacketSender().walkableInterface(4535);
			isSnowy = false;
		} else if (inCw() || inPits) {
			getPacketSender().showOption(3, 0, "Attack", 1);
		} else if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_ALCHEMY)) {
			getPacketSender().walkableInterface(15892);
		} else if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_ENCHANTING)) {
			getPacketSender().walkableInterface(15917);
		} else if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_TELEKINETIC)) {
			getPacketSender().walkableInterface(15962);
		} else if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
			getPacketSender().walkableInterface(15931);
		} else {
			getPacketSender().sendMapState(0);
			if (!isSnowy) {
				getPacketSender().walkableInterface(-1);
			}
			getPacketSender().showOption(3, 0, "Null", 1);
		}
	}

	public Client getClient(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
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
		if (id < 0 || id > Constants.MAX_PLAYERS) {
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

	public void process() {
		if (Boundary.isIn(this, Boundary.DESERT) && heightLevel == 0) {
			DesertHeat.callHeat(this);
		}
		if (playerEnergy < 100 && System.currentTimeMillis() - lastIncrease >= getPlayerAssistant().raiseTimer()) {
			playerEnergy += 1;
			lastIncrease = System.currentTimeMillis();
		}
		if (playerEnergy <= 0 && isRunning2) {
			isRunning = false;
			isRunning2 = false;
			getPacketSender().sendConfig(504, 0);
			getPacketSender().sendConfig(173, 0);
		}
		getPlayerAssistant().writeEnergy();
		if (System.currentTimeMillis() - specDelay > CombatConstants.INCREASE_SPECIAL_AMOUNT) {
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
				damageTaken = new int[Constants.MAX_PLAYERS];
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
			for (int skill = 0; skill < playerLevel.length; skill++) {
				if (playerLevel[skill] < playerAssistant.getLevelForXP(playerXP[skill])) {
					if (skill != 5) { // prayer doesn't restore
						playerLevel[skill] += 1;
						getPacketSender().setSkillLevel(skill, playerLevel[skill], playerXP[skill]);
						getPlayerAssistant().refreshSkill(skill);
					}
				} else if (playerLevel[skill] > playerAssistant.getLevelForXP(playerXP[skill])) {
					playerLevel[skill] -= 1;
					getPacketSender().setSkillLevel(skill, playerLevel[skill], playerXP[skill]);
					getPlayerAssistant().refreshSkill(skill);
				}
			}
		}

		if (!hasMultiSign && Boundary.isIn(this, Boundary.MULTI)) {
			hasMultiSign = true;
			getPacketSender().multiWay(1);
		}

		if (hasMultiSign && !Boundary.isIn(this, Boundary.MULTI)) {
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
				} else if (!goodDistance(absX, absY, PlayerHandler.players[frozenBy].absX, PlayerHandler.players[frozenBy].absY, 20)) {
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

		if (timeOutCounter > Constants.TIMEOUT) {
			if (!isBot)
				logout(true);
		}

		timeOutCounter++;
	}

	public void queueMessage(Packet arg1) {
		if (queuedPackets.size() < 25) {
			queuedPackets.add(arg1);
		}
	}

	public void processQueuedPackets() {
		while (!queuedPackets.isEmpty()) {
			Packet p = queuedPackets.poll();
			if (p.getOpcode() > 0) {
				PacketHandler.processPacket(this, p);
			}
			timeOutCounter = 0;
		}
	}

	public int soundVolume = 10;

	public boolean soundDone;

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
		if (Constants.SOUND) {
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
		if (Boundary.isIn(this, Boundary.PC_GAME)) {
			getPlayerAssistant().movePlayer(2657, 2639, 0);
		} else if (Boundary.isIn(this, Boundary.FIGHT_PITS)) {
			getPlayerAssistant().movePlayer(2399, 5178, 0);
		} else if (getX() == 0 && getY() == 0) {
			getPlayerAssistant().movePlayer(3222, 3218, 0);
		} else if (inFightCaves()) {
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

	public String slayerMaster;

	public boolean lostCannon, refresh, isBot;

	public int CannonSetupStage;

	public ArrayList<String> killedPlayers = new ArrayList<String>();
	public ArrayList<Integer> attackedPlayers = new ArrayList<Integer>();
	public ArrayList<String> lastKilledPlayers = new ArrayList<String>();

	public int[][] barrowCrypt = {
			{4921, 0},
			{2035, 0}
	};

	public long homeTele, lastDesert, lastButton, lastFire, lastLight, muteTime, waitTime, miscTimer,
			webSlashDelay, climbDelay, lastReport = 0,
			lastPlayerMove, lastPoison, lastPoisonSip, poisonImmune, lastSpear,
			lastProtItem, dfsDelay, lastYell, teleGrabDelay,
			protMageDelay, protMeleeDelay, protRangeDelay, lastAction,
			lastThieve, lastLockPick, alchDelay, specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay,
			godSpellDelay, singleCombatDelay, singleCombatDelay2, reduceStat,
			restoreStatsDelay, logoutDelay, buryDelay, foodDelay, potDelay,
			doorDelay, doubleDoorDelay, buySlayerTimer, lastIncrease,
			boneDelay, leverDelay = 0, searchObjectDelay = 0, clickDelay = 0;

	public boolean hideYell;


	private Npc specialTarget = null;

	public void setSpecialTarget(Npc target) {
		this.specialTarget = target;
	}

	public Npc getSpecialTarget() {
		return specialTarget;
	}

	public int miningAxe = -1, woodcuttingAxe = -1;

	public boolean initialized, musicOn = true, soundOn = true, luthas,
			playerIsCooking, disconnected, ruleAgreeButton,
			rebuildNPCList, isActive, isKicked,
			isSkulled, friendUpdate, newPlayer,
			hasMultiSign, saveCharacter, mouseButton,
			chatEffects = true, acceptAid, recievedMask,
			nextDialogue, autocasting, usedSpecial,
			mageFollow, craftingLeather, properLogout, isNpc,
			addStarter, accountFlagged, inPartyRoom, msbSpec,
			hasBankPin, enterdBankpin, firstPinEnter, requestPinDelete,
			secondPinEnter, thirdPinEnter, fourthPinEnter, hasBankpin,
			isBanking, isTeleporting, desertWarning,
			isPotionMaking, isGrinding, hasStarter, isSpinning,
			clickedSpinning, hasPaidBrim, playerStun, playerFletch, isWoodcutting, playerIsFiremaking,
			hasNpc, playerIsFishing, isOperate, below459 = true,
			splitChat, strongHold, village, needsNewTask,
			canSpeak = true, ignoreFrog, ratdied2,
			fishingWhirlPool, lostDuel, diedOnTut, storing, rope, rope2,
			canWalkTutorial, closeTutorialInterface, isCrafting, showedUnfire,
			showedFire, isPotCrafting, isFiremaking, playerIsFletching, milking,
			stopPlayerPacket, spiritTree, isSmelting,
			isSmithing, hasPaid, canTeleport, magicCharge,
			clickedVamp, allowFading, otherBank,
			recievedReward, poison, golemSpawned, zombieSpawned, shadeSpawned,
			treeSpiritSpawned,leprechaunSpawned, chickenSpawned, clickedTree, filter = true,
			stopPlayer, npcCanAttack = true, gliderOpen, hasSandwhichLady,
			isHarvesting, openDuel,  killedJad, canHealersRespawn = true, playerIsBusy, miningRock,
			randomEventsEnabled, debugMode, clickToTele = false;

	public int votePoints, saveDelay, playerKilled, gertCat, restGhost,
			romeojuliet, runeMist, lostCity, vampSlayer, cookAss, doricQuest, blackKnight, shieldArrav,
			sheepShear, impsC, randomActions,
			totalPlayerDamageDealt, killedBy, lastChatId = 1, privateChat,
			dialogueId, randomCoffin, newLocation, specEffect,
			specBarId, attackLevelReq, defenceLevelReq, strengthLevelReq,
			rangeLevelReq, magicLevelReq, slayerLevelReq, agilityLevelReq,
			followId, skullTimer, nextChat = 0, talkingNpc = -1,
			dialogueAction = 0, autocastId, followDistance, followId2,
			barrageCount = 0, delayedDamage = 0, delayedDamage2 = 0,
			pcPoints = 0, magePoints = 0, desertTreasure = 0, skillAmount,
			lastArrowUsed = -1, autoRet = 1, pcDamage = 0, xInterfaceId = 0,
			xRemoveId = 0, xRemoveSlot = 0, tzhaarToKill = 0, tzhaarKilled = 0,
			waveId, frozenBy = 0, poisonDamage = 0, teleAction = 0,
			bonusAttack = 0, lastNpcAttacked = 0, killCount = 0, witchspot,
			pirateTreasure, ptjob, cwKills, cwDeaths, cwGames, tzKekSpawn = 0,
			playerBankPin, recoveryDelay = 3, attemptsRemaining = 3,
			lastPinSettings = -1, setPinDate = -1, changePinDate = -1,
			deletePinDate = -1, firstPin, secondPin, thirdPin, fourthPin,
			bankPin1, bankPin2, bankPin3, bankPin4, pinDeleteDateRequested,
			rememberNpcIndex, ratsCaught, lastLoginDate, selectedSkill, newHerb,
			newItem, newXp, doingHerb, herbAmount, treeX, treeY, lastH, easterEvent, ectoTokens,
			cookingItem, smeltingItem, cookingObject, summonId, npcId2 = 0, leatherType = -1,
			weightCarried, teleotherType, rockX, rockY, itemUsing, tzKekTimer,
			bananas, flourAmount, grain, questPoints, questStages,
			teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy,
			underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0,
			teleBlockLength, poisonDelay, slayerPoints, blackMarks,
			SlayerMaster, teleOtherTimer = 0,
			teleOtherSlot = -1, tutorialProgress, cookStage1 = 1,
			knightS, brightness = 3, recoilHits, droppedItem = -1,
			spawnedHealers, cannonX = 0, cannonY = 0,
			playerShopId;

	public double playerEnergy = 100;

	public Pets getSummon() {
		return pets;
	}

	private final Pets pets = new Pets();

	public int removedTasks[] = { -1, -1, -1, -1 };

	public boolean isRunning() {
		return isNewWalkCmdIsRunning() || isRunning2 && isMoving;
	}

	public void faceNpc(int npc) {
		face = npc;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void faceNPC(int index) {
		faceNPC = index;
		faceNPCupdate = true;
		updateRequired = true;
	}

	protected boolean faceNPCupdate = false;
	public int faceNPC = -1;

	public void appendFaceNPCUpdate(Stream str) {
		str.writeWordBigEndian(faceNPC);
	}

	public int getLocalX() {
		return getX() - 8 * getMapRegionX();
	}

	public int getLocalY() {
		return getY() - 8 * getMapRegionY();
	}

	public int getKillCount() {
		return barrowsKillCount;
	}

	/**
	 * @param randomGrave
	 *            the randomGrave to set
	 */
	public void setRandomGrave(int randomGrave) {
		this.randomGrave = randomGrave;
	}

	/**
	 * @return the randomGrave
	 */
	public int getRandomGrave() {
		return randomGrave;
	}

	private int randomGrave;

	public void setBarrowsNpcDead(int index, boolean dead) {
		barrowsNpcDead[index] = dead;
	}

	/**
	 * @return the barrowsNpcDead
	 */
	public boolean[] getBarrowsNpcDead() {
		return barrowsNpcDead;
	}

	/**
	 * @return the barrowsNpcDead
	 */
	public boolean getBarrowsNpcDead(int id) {
		return barrowsNpcDead[id];
	}

	private final boolean barrowsNpcDead[] = new boolean[6];

	public int ectofuntusBoneUsed;
	public String ectofuntusBoneCrusherState = "Empty";
	public Client teleporter = null;
	public int[] party = new int[8];
	public int[] partyN = new int[8];
	public String lastReported = "";
	public String pinBank = "";
	public int attempts = 3;
	public boolean setPin = false;
	public int[][] playerSkillProp = new int[20][15];
	public boolean[] playerSkilling = new boolean[20];
	public boolean stopPlayerSkill;
	public int doAmount, addAmount;
	public int lastNpcClickIndex;
	public boolean[] killedPheasant = new boolean[5];
	public boolean playerHasRandomEvent;
	public boolean canLeaveArea;

	public int pieSelect = 0, getPheasent, kebabSelect = 0, breadID,
			chocSelect = 0, bagelSelect = 0, triangleSandwich = 0,
			squareSandwich = 0, breadSelect = 0;

	public String properName;
	public int lastX, lastY;
	public int[] voidStatus = new int[5];
	public int[] itemKeptId = new int[4];
	public int[] pouches = new int[4];
	public final int[] POUCH_SIZE = { 3, 6, 9, 12 };
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
	public long friends[] = new long[200], ignores[] = new long[200];
	public double specAmount = 0;
	public double specAccuracy = 1;
	public double specDamage = 1;
	public boolean isFletching;
	public double weight = 0.0;

	public boolean canChangeAppearance = false;
	public boolean mageAllowed;
	public int poisonMask;

	public Npc getCloseRandomNpc(int distance) {
		ArrayList<Npc> npcs = new ArrayList<Npc>();
		for (Npc npc : NpcHandler.npcs) {
			if (npc != null) {
				Npc n = npc;
				if (distanceToPoint(n.getX(), n.getY()) <= distance) {
					if (!n.underAttack) {
						if (n.heightLevel == heightLevel) {
							npcs.add(n);
						}
					}
				}
			}
		}
		if (npcs.size() > 0) {
			return npcs.get(Misc.random(npcs.size() - 1));
		} else {
			return null; // No near npcs :C
		}
	}

	public boolean isAutoButton(int button) {
		for (int j = 0; j < autocastIds.length; j += 2) {
			if (autocastIds[j] == button) {
				return true;
			}
		}
		return false;
	}

	public int[] autocastIds = { 51133, 32, 51185, 33, 51091, 34, 24018, 35,
			51159, 36, 51211, 37, 51111, 38, 51069, 39, 51146, 40, 51198, 41,
			51102, 42, 51058, 43, 51172, 44, 51224, 45, 51122, 46, 51080, 47,
			7038, 0, 7039, 1, 7040, 2, 7041, 3, 7042, 4, 7043, 5, 7044, 6,
			7045, 7, 7046, 8, 7047, 9, 7048, 10, 7049, 11, 7050, 12, 7051, 13,
			7052, 14, 7053, 15, 47019, 27, 47020, 25, 47021, 12, 47022, 13,
			47023, 14, 47024, 15 };

	// public String spellName = "Select Spell";
	public void assignAutocast(int button) {
		for (int j = 0; j < autocastIds.length; j++) {
			if (autocastIds[j] == button) {
				Client c = (Client) PlayerHandler.players[playerId];
				autocasting = true;
				autocastId = autocastIds[j + 1];
				c.getPacketSender().sendConfig(108, 1);
				c.getPacketSender().setSidebarInterface(0, 328);
				c = null;
				break;
			}
		}
	}

	public boolean inCwGame() {
		return isInAreaxxyy(2368, 2431, 9479, 9535) || isInAreaxxyy(2368, 2431, 3072, 3135) && !Boundary.isIn(this, Boundary.SARA_WAIT) && !Boundary.isIn(this, Boundary.ZAMMY_WAIT);
	}

	public void gameInterface(int id) {
		if (gameInterface != id) {
			gameInterface = id;
		}
	}

	public int gameInterface;
	public int lastGame;

	public int[][] barrowsNpcs = { { 2030, 0 }, // verac
			{ 2029, 0 }, // toarg
			{ 2028, 0 }, // karil
			{ 2027, 0 }, // guthan
			{ 2026, 0 }, // dharok
			{ 2025, 0 } // ahrim
	};

	public int barrowsKillCount;

	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = { 250000, 250000, 250000, 500000,
			500000, 500000 }; // how long does the other player stay immune to
	// the spell
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = { 1153, 1157, 1161, 1542, 1543, 1562 };
	public boolean[] canUseReducingSpell = { true, true, true, true, true, true };

	public int slayerTask, taskAmount;

	public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq,
			duelOption, duelingWith, duelStatus;
	public int headIconPk = -1, headIconHints;
	public boolean duelRequested;
	public boolean[] duelRule = new boolean[22];
	public final int[] DUEL_RULE_ID = { 1, 2, 16, 32, 64, 128, 256, 512, 1024,
			4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152,
			8388608, 16777216, 67108864, 134217728 };


	/**
	 * Combat variables
	 */
	public boolean doubleHit, usingSpecial, usingRangeWeapon,
			usingBow, usingMagic, castingMagic, unlockedBonesToPeaches;
	public int castingSpellId, oldSpellId,
			spellId, hitDelay;
	public int specMaxHitIncrease, freezeDelay, freezeTimer = -6, killerId,
			playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage,
			crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation,
			teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex,
			totalDamageDealt, globalDamageDealt, oldNpcIndex, fightMode, attackTimer,
			bowSpecShot, ectofuntusWorshipped, graveyardPoints, alchemyPoints, enchantmentPoints, telekineticPoints, telekineticMazesSolved;
	public boolean magicFailed, oldMagicFailed;
	/**
	 * End
	 */

	public int clickNpcType, clickObjectType, objectId, objectX,
			objectY, npcIndex, npcClickIndex, npcType;
	public int pItemX, pItemY, pItemId;
	public boolean isMoving, walkingToItem;
	public boolean isShopping, updateShop;
	public int shopId;
	public int tradeStatus, tradeWith;
	public boolean forcedChatUpdateRequired, inDuel, tradeAccepted, goodTrade,
			inTrade, tradeRequested, tradeResetNeeded, tradeConfirmed,
			tradeConfirmed2, acceptTrade, acceptedTrade;
	public int attackAnim, animationRequest = -1, animationWaitCycles;
	public int[] playerBonus = new int[12];
	public boolean isRunning2 = true;
	public boolean takeAsNote;
	public int combatLevel;
	public boolean saveFile;
	public int playerAppearance[] = new int[13];
	public int actionID;
	public int wearItemTimer, wearId, wearSlot, interfaceId;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	public boolean isMining;
	public boolean hasThievedStall;
	public boolean hasSearchedForTraps;
	public boolean stopFiremaking, pickedUpFiremakingLog, logLit;

	public boolean hasThievedStall() {
		return hasThievedStall;
	}

	public void setHasThievedStall(boolean hasThievedStall) {
		this.hasThievedStall = hasThievedStall;
	}

	public boolean hasSearchedForTraps() {
		return hasSearchedForTraps;
	}

	public void setHasSearchedForTraps(boolean hasSearchedForTraps) {
		this.hasSearchedForTraps = hasSearchedForTraps;
	}

	public boolean antiFirePot;

	public boolean underWater;
	public boolean prevRunning2;
	public int prevPrevPlayerRunIndex;
	public int prevPlayerStandIndex;
	public int prevplayerWalkIndex;
	public int prevPlayerTurnIndex;
	public int prevPlayerTurn90CWIndex;
	public int prevPlayerTurn90CCWIndex;
	public int prevPlayerTurn180Index;

	public Client asClient() {
		return (Client) this;
	}

	private Player player;
	public Player asPlayer() {
		return (Player) player;
	}

	public boolean inTrawlerBoat() {
		if(inArea(2808, 2811,3415,3425)) {
			return true;
		}
		return false;
	}

	public boolean inTrawlerGame() {
		if(inArea(2808, 2811,3415,3425)) {
			return true;
		}
		return false;
	}

	public long lastFishingTrawlerInteraction;
	public boolean inFishingTrawlerRewardsInterface;

	/**
	 * Castle Wars
	 */
	public int castleWarsTeam;
	public boolean inCwGame;
	public boolean inCwWait;

	/**
	 * Fight Pits
	 */
	public boolean inPits;
	public int pitsStatus = 0;

	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */

	public boolean inCw() {
		Client c = (Client) this;
		if (CastleWars.isInCwWait(c) || CastleWars.isInCw(c)) {
			return true;
		}
		return false;
	}

	public boolean inBarrows() {
		if (absX > 3520 && absX < 3598 && absY > 9653 && absY < 9750) {
			return true;
		}
		return false;
	}

	public boolean inArea(int x, int y, int x1, int y1) {
		if (absX > x && absX < x1 && absY < y && absY > y1) {
			return true;
		}
		return false;
	}

	public boolean inKqArea() {
		if (absX >= 3467  && absX <= 3506 && absY >= 9477 && absY <= 9513) {
			return true;
		}
		return false;
	}

	public boolean inWild() {
		if (inCw()) {
			return true;
		}
		if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966 || absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
			if (!wildernessWarning) {
				resetWalkingQueue();
				wildernessWarning = true;
				getPacketSender().sendString("WARNING!", 6940);
				getPacketSender().showInterface(1908);
			}
			return true;
		}
		return false;
	}

	public boolean inPlayerShopArea() {
		return isInArea(2938, 3389, 3059, 3329) || // Falador
				isInArea(3172, 3449, 3270, 3384) || // Varrock
				isInArea(3200, 3256, 3237, 3201) || // Lumbridge
				isInArea(2716, 3498, 2735, 3480) ||
				isInArea(3075, 3513, 3106, 3466) ||
				isInArea(3074, 3262, 3102, 3239) ||
				isInArea(2435, 3101, 2459, 3080) ||
				isInArea(2618, 3075, 2598, 3108) ||
				isInArea(2678, 3267, 2601, 3341) ||
				isInArea(3265, 3157, 3324, 3215) ||
				isInArea(3386, 3264, 3348, 3286) ||
				isInArea(2797, 3454, 2838, 3430) ||
				isInArea(2546, 3157, 2512, 3176) ||
				isInArea(2451, 3408, 2425, 3437) ||
				false;
	}

	public boolean duelingArena() {
		if (absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260) {
			return true;
		}
		return false;
	}


	public boolean playerIsBusy() {
		if (isShopping || inTrade || openDuel || isBanking || duelStatus == 1) {
			return true;
		}
		return false;
	}

	public boolean inDuelArena() {
		if (absX > 3322 && absX < 3394 && absY > 3195 && absY < 3291 || absX > 3311 && absX < 3323 && absY > 3223 && absY < 3248) {
			return true;
		}
		return false;
	}

	public boolean isInArea(final int x1, final int y1, final int x2, final int y2) {
		return (absX >= x1 && absX <= x2 || absX <= x1 && absX >= x2) && (absY >= y1 && absY <= y2 || absY <= y1 && absY >= y2);
	}

	public boolean isInAreaxxyy(final int x1, final int x2, final int y1, final int y2) {
		return absX >= x1 && absX <= x2 && absY >= y1 && absY <= y2;
	}

	public boolean altars() {
		return safeAreas(3090, 3506, 3097, 3506);
	}

	public boolean safeAreas(int x, int y, int x1, int y1) {
		return absX >= x && absX <= x1 && absY >= y && absY <= y1;
	}

	public boolean inFightCaves() {
		return absX >= 2360 && absX <= 2445 && absY >= 5045 && absY <= 5125;
	}

	public boolean inPirateHouse() {
		return absX >= 3038 && absX <= 3044 && absY >= 3949 && absY <= 3959;
	}

	public String connectedFrom = "";
	public String globalMessage = "";

	public int playerId = -1;
	public String playerName = null;
	public String playerName2 = null;
	public String playerPass = null;
	public int playerRights;
	public PlayerHandler handler = null;
	public int playerItems[] = new int[28];
	public int playerItemsN[] = new int[28];
	public int bankItems[] = new int[ItemConstants.BANK_SIZE];
	public int bankItemsN[] = new int[ItemConstants.BANK_SIZE];
	// used for player owned shops
	public int bankItemsV[] = new int[ItemConstants.BANK_SIZE];
	public boolean bankNotes = false;
	public boolean shouldSave = false;

	public int playerStandIndex = 0x328;
	public int playerTurnIndex = 0x337;
	public int playerWalkIndex = 0x333;
	public int playerTurn180Index = 0x334;
	public int playerTurn90CWIndex = 0x335;
	public int playerTurn90CCWIndex = 0x336;
	public int playerRunIndex = 0x338;

	public int playerHat = 0;
	public int playerCape = 1;
	public int playerAmulet = 2;
	public int playerWeapon = 3;
	public int playerChest = 4;
	public int playerShield = 5;
	public int playerLegs = 7;
	public int playerHands = 9;
	public int playerFeet = 10;
	public int playerRing = 12;
	public int playerArrows = 13;

	public int[] playerEquipment = new int[14];
	public int[] playerEquipmentN = new int[14];
	public int[] playerLevel = new int[25];
	public int[] playerXP = new int[25];

	public void updateShop(int i) {
		Client p = (Client) PlayerHandler.players[playerId];
		p.getShopAssistant().resetShop(i);
	}

	public void println_debug(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public void println(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public Player(int _playerId) {
		playerId = _playerId;
		playerRights = 0;

		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i = 0; i < playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i = 0; i < playerLevel.length; i++) {
			if (i == 3) {
				playerLevel[i] = 10;
			} else {
				playerLevel[i] = 1;
			}
		}

		for (int i = 0; i < playerXP.length; i++) {
			if (i == 3) {
				playerXP[i] = 1300;
			} else {
				playerXP[i] = 0;
			}
		}
		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i = 0; i < ItemConstants.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}

		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 7; // head
		playerAppearance[2] = 25;// Torso
		playerAppearance[3] = 29; // arms
		playerAppearance[4] = 35; // hands
		playerAppearance[5] = 39; // legs
		playerAppearance[6] = 44; // feet
		playerAppearance[7] = 14; // beard
		playerAppearance[8] = 7; // hair colour
		playerAppearance[9] = 8; // torso colour
		playerAppearance[10] = 9; // legs colour
		playerAppearance[11] = 5; // feet colour
		playerAppearance[12] = 0; // skin colour

		actionID = 0;

		playerEquipment[playerHat] = -1;
		playerEquipment[playerCape] = -1;
		playerEquipment[playerAmulet] = -1;
		playerEquipment[playerChest] = -1;
		playerEquipment[playerShield] = -1;
		playerEquipment[playerLegs] = -1;
		playerEquipment[playerHands] = -1;
		playerEquipment[playerFeet] = -1;
		playerEquipment[playerRing] = -1;
		playerEquipment[playerArrows] = -1;
		playerEquipment[playerWeapon] = -1;

		heightLevel = 0;

		if (Constants.TUTORIAL_ISLAND) {
			teleportToX = 3094;
			teleportToY = 3107;
		} else {
			teleportToX = 3233;
			teleportToY = 3229;
		}

		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public static final int maxPlayerListSize = Constants.MAX_PLAYERS;
	public Player playerList[] = new Player[maxPlayerListSize];
	public int playerListSize = 0;

	public byte playerInListBitmap[] = new byte[Constants.MAX_PLAYERS + 7 >> 3];

	public static final int maxNPCListSize = NpcHandler.MAX_NPCS;
	public Npc npcList[] = new Npc[maxNPCListSize];
	public int npcListSize = 0;

	public byte npcInListBitmap[] = new byte[NpcHandler.MAX_NPCS + 7 >> 3];

	public boolean withinDistance(Player otherPlr) {
		if (heightLevel != otherPlr.heightLevel) {
			return false;
		}
		int deltaX = otherPlr.absX - absX, deltaY = otherPlr.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(Npc npc) {
		if (heightLevel != npc.heightLevel) {
			return false;
		}
		if (npc.needRespawn) {
			return false;
		}
		int deltaX = npc.absX - absX, deltaY = npc.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(int absX, int getY, int getHeightLevel) {
		if (heightLevel != getHeightLevel) {
			return false;
		}
		if (objectId == 2242) {
			System.out.println("not within distance");
			return false;
		}
		int deltaX = getX() - absX, deltaY = getY() - getY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public int distanceToPoint(int pointX, int pointY) {
		return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
	}

	public int mapRegionX, mapRegionY;
	public int absX;

	public int absY;
	public int currentX, currentY;

	public int heightLevel;
	public int playerSE = 0x328;
	public int playerSEW = 0x333;
	public int playerSER = 0x334;

	public boolean updateRequired = true;

	public final int walkingQueueSize = 50;
	public int walkingQueueX[] = new int[walkingQueueSize],
			walkingQueueY[] = new int[walkingQueueSize];
	public int wQueueReadPtr = 0;
	public int wQueueWritePtr = 0;
	public boolean isRunning = true;
	public int teleportToX = -1, teleportToY = -1;

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;
		for (int i = 0; i < walkingQueueSize; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}

	public void addToWalkingQueue(int x, int y) {
		int next = (wQueueWritePtr + 1) % walkingQueueSize;
		if (next == wQueueWritePtr) {
			return;
		}
		walkingQueueX[wQueueWritePtr] = x;
		walkingQueueY[wQueueWritePtr] = y;
		wQueueWritePtr = next;
	}

	public boolean checkRangeDistance() {
		return (usingRangeWeapon || usingBow);
	}

	public int gatherRangeDistance(int distance) {
		//dart (non long range)
		if (usingRangeWeapon && RangeData.usingDart(this) && fightMode != 3) {
			distance = 3;
			//longbow (long range)
		} else if (usingBow && fightMode == 3 && RangeData.usingLongbow(this)) {
			distance = 10;
			//longbow (non long range)
		} else if (usingBow && fightMode != 3 && RangeData.usingLongbow(this)) {
			distance = RangeData.usingCrystalBow(this) ? 10 : 8;
			//dart, knife, throwing axe (long range)
		} else if (usingRangeWeapon && fightMode == 3) {
			distance = RangeData.usingDart(this) ? 5 : 6;
			//short bow
		} else if (usingBow && !RangeData.usingLongbow(this)) {
			distance = fightMode == 3 ? 7 : 9;
		}
		return distance;
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		if (checkRangeDistance()) {
			distance = gatherRangeDistance(distance);
		}

		return ((objectX-playerX <= distance && objectX-playerX >= -distance) && (objectY-playerY <= distance && objectY-playerY >= -distance));
	}

	public boolean goodObjectDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX-playerX <= distance && objectX-playerX >= -distance) && (objectY-playerY <= distance && objectY-playerY >= -distance));
	}

	public int getNextWalkingDirection() {
		if (wQueueReadPtr == wQueueWritePtr) {
			return -1;
		}
		int dir;
		do {
			dir = Misc.direction(currentX, currentY, walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if (dir == -1) {
				wQueueReadPtr = (wQueueReadPtr + 1) % walkingQueueSize;
			} else if ((dir & 1) != 0) {
				println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while (dir == -1 && wQueueReadPtr != wQueueWritePtr);
		if (dir == -1) {
			return -1;
		}
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		/*if (!Region.canMove(absX, absY, (absX + Misc.directionDeltaX[dir]), (absY + Misc.directionDeltaY[dir]), heightLevel, 1, 1))
			return -1;*/
		absX += Misc.directionDeltaX[dir];
		absY += Misc.directionDeltaY[dir];
		updateWalkEntities();
		return dir;
	}

	public boolean didTeleport = false;
	public boolean mapRegionDidChange = false;
	public int dir1 = -1, dir2 = -1;
	public boolean createItems = false;
	public int poimiX = 0, poimiY = 0;

	public synchronized void getNextPlayerMovement() {
		mapRegionDidChange = false;
		didTeleport = false;
		dir1 = dir2 = -1;

		if (teleportToX != -1 && teleportToY != -1) {
			mapRegionDidChange = true;
			if (mapRegionX != -1 && mapRegionY != -1) {
				int relX = teleportToX - mapRegionX * 8, relY = teleportToY
						- mapRegionY * 8;
				if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8
						&& relY < 11 * 8) {
					mapRegionDidChange = false;
				}
			}
			if (mapRegionDidChange) {
				mapRegionX = (teleportToX >> 3) - 6;
				mapRegionY = (teleportToY >> 3) - 6;
			}
			if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_ALCHEMY) && !Boundary.isIn(teleportToX, teleportToY, teleHeight, Boundary.MAGE_TRAINING_ARENA_ALCHEMY)) {
				// remove any alchemy training items
				getMageTrainingArena().alchemy.clearItems();
			}
			if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_ENCHANTING) && !Boundary.isIn(teleportToX, teleportToY, teleHeight, Boundary.MAGE_TRAINING_ARENA_ENCHANTING)) {
				// remove any enchanting training items
				getMageTrainingArena().enchanting.clearItems();
			}
			if (Boundary.isIn(this, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD) && !Boundary.isIn(teleportToX, teleportToY, teleHeight, Boundary.MAGE_TRAINING_ARENA_GRAVEYARD)) {
				// remove any enchanting training items
				getMageTrainingArena().graveyard.clearItems();
			}
			currentX = teleportToX - 8 * mapRegionX;
			currentY = teleportToY - 8 * mapRegionY;
			absX = teleportToX;
			absY = teleportToY;
			int newHeight = teleHeight >= 0 ? teleHeight : heightLevel >= 0 ? heightLevel : 0;
			if (heightLevel != newHeight)
				GameEngine.itemHandler.reloadItems(this);
			heightLevel = newHeight;
			resetWalkingQueue();

			teleportToX = teleportToY = teleHeight = -1;
			didTeleport = true;
			updateWalkEntities();
		} else {
			dir1 = getNextWalkingDirection();
			if (dir1 == -1) {
				return;
			}
			if (isRunning) {
				dir2 = getNextWalkingDirection();
			}
			int deltaX = 0, deltaY = 0;
			if (currentX < 2 * 8) {
				deltaX = 4 * 8;
				mapRegionX -= 4;
				mapRegionDidChange = true;
			} else if (currentX >= 11 * 8) {
				deltaX = -4 * 8;
				mapRegionX += 4;
				mapRegionDidChange = true;
			}
			if (currentY < 2 * 8) {
				deltaY = 4 * 8;
				mapRegionY -= 4;
				mapRegionDidChange = true;
			} else if (currentY >= 11 * 8) {
				deltaY = -4 * 8;
				mapRegionY += 4;
				mapRegionDidChange = true;
			}

			if (mapRegionDidChange) {
				currentX += deltaX;
				currentY += deltaY;
				for (int i = 0; i < walkingQueueSize; i++) {
					walkingQueueX[i] += deltaX;
					walkingQueueY[i] += deltaY;
				}
			}
		}
	}

	public void updateThisPlayerMovement(Stream str) {

		if (str != null) {
			if (mapRegionDidChange) {
				str.createFrame(73);
				str.writeWordA(mapRegionX + 6);
				str.writeWord(mapRegionY + 6);
			}

			if (didTeleport) {
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);
				str.writeBits(2, 3);
				str.writeBits(2, heightLevel);
				str.writeBits(1, 1);
				str.writeBits(1, updateRequired ? 1 : 0);
				str.writeBits(7, currentY);
				str.writeBits(7, currentX);
				return;
			}
		}
		if (dir1 == -1) {
			// don't have to update the character position, because we're just standing
			if (str != null){
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				isMoving = false;
				if (updateRequired) {
					// tell client there's an update block appended at the end
					str.writeBits(1, 1);
					str.writeBits(2, 0);
				} else {
					str.writeBits(1, 0);
				}
			}
			if (DirectionCount < 50) {
				DirectionCount++;
			}
		} else {
			DirectionCount = 0;
			if (str != null) {
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);
			}

			if (dir2 == -1) {
				isMoving = true;
				if (str != null) {
					str.writeBits(2, 1);
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					if (updateRequired) {
						str.writeBits(1, 1);
					} else {
						str.writeBits(1, 0);
					}
				}
			} else {
				isMoving = true;
				if (str != null) {
					str.writeBits(2, 2);
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
					if (updateRequired) {
						str.writeBits(1, 1);
					} else {
						str.writeBits(1, 0);
					}
				}
				if (playerEnergy > 0 && playerRights < 2) {
					// calculations from https://oldschool.runescape.wiki/w/Energy
					playerEnergy -= 0.64;
					if (weight > 0.0)
						playerEnergy -= Math.min(weight, 64) / 100;
				} else if (playerRights >= 2) {
					playerEnergy = 100;
					isRunning2 = true;
				} else if (playerEnergy <= 0) {
					playerEnergy = 0;
					isRunning2 = false;
				}
			}
		}
	}

	public void updatePlayerMovement(Stream str) {
		if (str == null)
			return;

		if (dir1 == -1) {
			if (updateRequired || isChatTextUpdateRequired()) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else if (dir2 == -1) {

			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(1, updateRequired || isChatTextUpdateRequired() ? 1 : 0);
		} else {

			str.writeBits(1, 1);
			str.writeBits(2, 2);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
			str.writeBits(1, updateRequired || isChatTextUpdateRequired() ? 1 : 0);
		}
	}

	public byte cachedPropertiesBitmap[] = new byte[Constants.MAX_PLAYERS + 7 >> 3];

	public void addNewNPC(Npc npc, Stream str, Stream updateBlock) {
		int id = npc.npcId;
		npcInListBitmap[id >> 3] |= 1 << (id & 7);
		npcList[npcListSize++] = npc;

		if (str != null) {
			str.writeBits(14, id);
		}

		int z = npc.absY - absY;
		if (z < 0) {
			z += 32;
		}

		if (str != null) {
			str.writeBits(5, z);
		}

		z = npc.absX - absX;
		if (z < 0) {
			z += 32;
		}

		if (str != null) {
			str.writeBits(5, z);

			str.writeBits(1, 0);
			str.writeBits(12, npc.npcType);
		}

		boolean savedUpdateRequired = npc.updateRequired;
		npc.updateRequired = true;
		npc.appendNPCUpdateBlock(updateBlock);
		npc.updateRequired = savedUpdateRequired;

		if (str != null) {
			str.writeBits(1, 1);
		}
	}

	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		int id = plr.playerId;
		playerInListBitmap[id >> 3] |= 1 << (id & 7);
		playerList[playerListSize++] = plr;

		if (str != null) {
			str.writeBits(11, id);
			str.writeBits(1, 1);
		}
		boolean savedFlag = plr.isAppearanceUpdateRequired();
		boolean savedUpdateRequired = plr.updateRequired;
		plr.setAppearanceUpdateRequired(true);
		plr.updateRequired = true;
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setAppearanceUpdateRequired(savedFlag);
		plr.updateRequired = savedUpdateRequired;
		if (str != null) {
			str.writeBits(1, 1);
		}
		int z = plr.absY - absY;
		if (z < 0) {
			z += 32;
		}

		if (str != null) {
			str.writeBits(5, z);
		}

		z = plr.absX - absX;
		if (z < 0) {
			z += 32;
		}

		if (str != null) {
			str.writeBits(5, z);
		}
	}

	public int headIcon = -1, bountyIcon = 0;

	public int DirectionCount = 0;
	public boolean appearanceUpdateRequired = true;
	public int hitDiff2;
	public int hitDiff = 0;
	public boolean hitUpdateRequired2;
	public boolean hitUpdateRequired = false;
	public boolean isDead = false;

	protected static Stream playerProps;
	static {
		playerProps = new Stream(new byte[100]);
	}

	protected void appendPlayerAppearance(Stream str) {
		playerProps.currentOffset = 0;

		playerProps.writeByte(playerAppearance[0]);

		playerProps.writeByte(headIcon);
		playerProps.writeByte(headIconPk);

		if(!isNpc) {
			if (playerEquipment[ItemConstants.HAT] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.HAT]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.CAPE] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.CAPE]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.AMULET] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.AMULET]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.WEAPON] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.WEAPON]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.CHEST] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.CHEST]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[2]);
			}

			if (playerEquipment[ItemConstants.SHIELD] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.SHIELD]);
			} else {
				playerProps.writeByte(0);
			}

			if (!ItemData.isFullBody(playerEquipment[ItemConstants.CHEST])) {
				playerProps.writeWord(0x100 + playerAppearance[3]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.LEGS] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.LEGS]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[5]);
			}

			if (!ItemData.isFullHelm(playerEquipment[ItemConstants.HAT])
					&& !ItemData.isFullMask(playerEquipment[ItemConstants.HAT])) {
				playerProps.writeWord(0x100 + playerAppearance[1]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[ItemConstants.HANDS] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.HANDS]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[4]);
			}

			if (playerEquipment[ItemConstants.FEET] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[ItemConstants.FEET]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[6]);
			}

			if (playerAppearance[0] != 1 && !ItemData.isFullMask(playerEquipment[ItemConstants.HAT])) {
				playerProps.writeWord(0x100 + playerAppearance[7]);
			} else {
				playerProps.writeByte(0);
			}

		} else {//send npc data
			playerProps.writeWord(-1);//Tells client that were being a npc
			playerProps.writeWord(npcId2);//send NpcID
		}

		playerProps.writeByte(playerAppearance[8]);
		playerProps.writeByte(playerAppearance[9]);
		playerProps.writeByte(playerAppearance[10]);
		playerProps.writeByte(playerAppearance[11]);
		playerProps.writeByte(playerAppearance[12]);
		playerProps.writeWord(playerStandIndex); // standAnimIndex
		playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
		playerProps.writeWord(playerWalkIndex); // walkAnimIndex
		playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
		playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
		playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
		playerProps.writeWord(playerRunIndex); // runAnimIndex
		playerProps.writeQWord(Misc.playerNameToInt64(playerName));
		combatLevel = calculateCombatLevel();
		playerProps.writeByte(combatLevel); // combat level
		playerProps.writeWord(0);
		str.writeByteC(playerProps.currentOffset);
		str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
	}

	public int calculateCombatLevel() {
		// Show the bots as level 0 combat
		if (isBot) {
			return 0;
		}
		int j = playerAssistant.getLevelForXP(playerXP[Constants.ATTACK]);
		int k = playerAssistant.getLevelForXP(playerXP[Constants.DEFENCE]);
		int l = playerAssistant.getLevelForXP(playerXP[Constants.STRENGTH]);
		int i1 = playerAssistant.getLevelForXP(playerXP[Constants.HITPOINTS]);
		int j1 = playerAssistant.getLevelForXP(playerXP[Constants.PRAYER]);
		int k1 = playerAssistant.getLevelForXP(playerXP[Constants.RANGED]);
		int l1 = playerAssistant.getLevelForXP(playerXP[Constants.MAGIC]);
		int combatLevel = (int) ((k + i1 + Math.floor(j1 / 2)) * 0.25D) + 1;
		double d = (j + l) * 0.32500000000000001D;
		double d1 = Math.floor(k1 * 1.5D) * 0.32500000000000001D;
		double d2 = Math.floor(l1 * 1.5D) * 0.32500000000000001D;
		if (d >= d1 && d >= d2) {
			combatLevel += d;
		} else if (d1 >= d && d1 >= d2) {
			combatLevel += d1;
		} else if (d2 >= d && d2 >= d1) {
			combatLevel += d2;
		}
		return combatLevel;
	}

	private boolean chatTextUpdateRequired = false;
	private byte chatText[] = new byte[4096];
	private byte chatTextSize = 0;
	private int chatTextColor = 0;
	private int chatTextEffects = 0;

	protected void appendPlayerChatText(Stream str) {
		if (str == null) return;
		str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8) + (getChatTextEffects() & 0xFF));
		str.writeByte(playerRights);
		str.writeByteC(getChatTextSize());
		str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
	}

	public void forcedChat(String text) {
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}

	public String forcedText = "null";

	public void appendForcedChat(Stream str) {
		if (str != null)
			str.writeString(forcedText);
	}

	/**
	 * Graphics
	 **/

	public int mask100var1 = 0;
	public int mask100var2 = 0;
	protected boolean mask100update = false;

	public void appendMask100Update(Stream str) {
		str.writeWordBigEndian(mask100var1);
		str.writeDWord(mask100var2);
	}

	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		mask100update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 65536;
		mask100update = true;
		updateRequired = true;
	}

	public boolean wearing2h() {
		Client c = (Client) this;
		String s = DeprecatedItems.getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h")) {
			return true;
		}
		return false;
	}

	/**
	 * Animations
	 **/
	public void startAnimation(int animId) {
		if (MonkeyData.isWearingGreegree(this) && !MonkeyData.isAnim(animId) || (wearing2h() && animId == 829)) {
			return;
		}
		if (animId == -1) {
			animId = 65535;
		}
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}

	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void appendAnimationRequest(Stream str) {
		str.writeWordBigEndian(animationRequest == -1 ? 65535
				: animationRequest);
		str.writeByteC(animationWaitCycles);
	}

	/**
	 * Face Update
	 **/

	protected boolean faceUpdateRequired = false;
	public int face = -1;
	public int FocusPointX = -1, FocusPointY = -1;

	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceUpdate(Stream str) {
		str.writeWordBigEndian(face);
	}

	public void turnPlayerTo(int pointX, int pointY) {
		FocusPointX = 2 * pointX + 1;
		FocusPointY = 2 * pointY + 1;
		updateRequired = true;
	}

	private void appendSetFocusDestination(Stream str) {
		str.writeWordBigEndianA(FocusPointX);
		str.writeWordBigEndian(FocusPointY);
	}

	/**
	 * Hit Update
	 **/

	protected void appendHitUpdate(Stream str) {
		str.writeByte(getHitDiff()); // What the person got 'hit' for
		if (poisonMask == 1) {
			str.writeByteA(2);
		} else if (getHitDiff() > 0) {
			str.writeByteA(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteA(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[Constants.HITPOINTS] <= 0) {
			playerLevel[Constants.HITPOINTS] = 0;
			isDead = true;
		}
		str.writeByteC(playerLevel[Constants.HITPOINTS]); // Their current hp, for HP bar
		str.writeByte(playerAssistant.getLevelForXP(playerXP[Constants.HITPOINTS]));
	}

	protected void appendHitUpdate2(Stream str) {
		str.writeByte(hitDiff2); // What the perseon got 'hit' for
		if (poisonMask == 2) {
			str.writeByteS(2);
			poisonMask = -1;
		} else if (hitDiff2 > 0) {
			str.writeByteS(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteS(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[Constants.HITPOINTS] <= 0) {
			playerLevel[Constants.HITPOINTS] = 0;
			isDead = true;
		}
		str.writeByte(playerLevel[Constants.HITPOINTS]); // Their current hp, for HP bar
		str.writeByteC(playerAssistant.getLevelForXP(playerXP[Constants.HITPOINTS])); // Their max hp, for HP
	}

	public void appendPlayerUpdateBlock(Stream str) {
		if (!updateRequired && !isChatTextUpdateRequired()) {
			return; // nothing required
		}
		int updateMask = 0;
		if (mask100update) {
			updateMask |= 0x100;
		}
		if (animationRequest != -1) {
			updateMask |= 8;
		}
		if (forcedChatUpdateRequired) {
			updateMask |= 4;
		}
		if (isChatTextUpdateRequired()) {
			updateMask |= 0x80;
		}
		if (isAppearanceUpdateRequired()) {
			updateMask |= 0x10;
		}
		if (faceUpdateRequired) {
			updateMask |= 1;
		}
		if (FocusPointX != -1) {
			updateMask |= 2;
		}
		if (isHitUpdateRequired()) {
			updateMask |= 0x20;
		}

		if (hitUpdateRequired2) {
			updateMask |= 0x200;
		}

		if (updateMask >= 0x100) {
			updateMask |= 0x40;
			str.writeByte(updateMask & 0xFF);
			str.writeByte(updateMask >> 8);
		} else {
			str.writeByte(updateMask);
		}

		// now writing the various update blocks itself - note that their
		// order crucial
		if (mask100update) {
			appendMask100Update(str);
		}
		if (animationRequest != -1) {
			appendAnimationRequest(str);
		}
		if (forcedChatUpdateRequired) {
			appendForcedChat(str);
		}
		if (isChatTextUpdateRequired()) {
			appendPlayerChatText(str);
		}
		if (faceUpdateRequired) {
			appendFaceUpdate(str);
		}
		if (isAppearanceUpdateRequired()) {
			appendPlayerAppearance(str);
		}
		if (FocusPointX != -1) {
			appendSetFocusDestination(str);
		}
		if (isHitUpdateRequired()) {
			appendHitUpdate(str);
		}
		if (hitUpdateRequired2) {
			appendHitUpdate2(str);
		}
	}

	public void clearUpdateFlags() {
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setHitUpdateRequired(false);
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		mask100update = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		poisonMask = -1;
		faceUpdateRequired = false;
		face = 65535;
	}

	public void stopMovement() {
		if (teleportToX <= 0 && teleportToY <= 0) {
			teleportToX = absX;
			teleportToY = absY;
		}
		newWalkCmdSteps = 0;
		getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
		getNextPlayerMovement();
	}

	private int newWalkCmdX[] = new int[walkingQueueSize];
	private int newWalkCmdY[] = new int[walkingQueueSize];
	public int newWalkCmdSteps = 0;
	private boolean newWalkCmdIsRunning = false;
	protected int travelBackX[] = new int[walkingQueueSize];
	protected int travelBackY[] = new int[walkingQueueSize];
	protected int numTravelBackSteps = 0;

	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public void postProcessing() {
		if (newWalkCmdSteps > 0) {
			int firstX = getNewWalkCmdX()[0];
			int firstY = getNewWalkCmdY()[0];
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			if (dir != -1 && (dir & 1) != 0) {
				do {
					int var13 = dir;
					--ptr;
					if (ptr < 0) {
						ptr = 49;
					}

					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					dir = Misc.direction(walkingQueueX[ptr],
							walkingQueueY[ptr], firstX, firstY);
					if (var13 != dir) {
						found = true;
						break;
					}
				} while (ptr != wQueueWritePtr);
			} else {
				found = true;
			}

			if (found) {
				wQueueWritePtr = wQueueReadPtr;
				addToWalkingQueue(currentX, currentY);
				int i;
				if (dir != -1 && (dir & 1) != 0) {
					for (i = 0; i < numTravelBackSteps - 1; ++i) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}

					i = travelBackX[numTravelBackSteps - 1];
					int wayPointY2 = travelBackY[numTravelBackSteps - 1];
					int wayPointX1;
					int wayPointY1;
					if (numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					} else {
						wayPointX1 = travelBackX[numTravelBackSteps - 2];
						wayPointY1 = travelBackY[numTravelBackSteps - 2];
					}

					dir = Misc.direction(wayPointX1, wayPointY1, i, wayPointY2);
					if (dir != -1 && (dir & 1) == 0) {
						dir >>= 1;
						found = false;
						int x = wayPointX1;
						int y = wayPointY1;

						while (x != i || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
								found = true;
								break;
							}
						}

						if (!found) {
							println_debug("Fatal: Internal error: unable to determine connection vertex!  wp1=("
									+ wayPointX1
									+ ", "
									+ wayPointY1
									+ "), wp2=("
									+ i
									+ ", "
									+ wayPointY2
									+ "), "
									+ "first=("
									+ firstX
									+ ", "
									+ firstY + ")");
						} else {
							addToWalkingQueue(wayPointX1, wayPointY1);
						}
					} else {
						println_debug("Fatal: The walking queue is corrupt! wp1=("
								+ wayPointX1
								+ ", "
								+ wayPointY1
								+ "), "
								+ "wp2=(" + i + ", " + wayPointY2 + ")");
					}
				} else {
					for (i = 0; i < numTravelBackSteps; ++i) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				for (i = 0; i < newWalkCmdSteps; ++i) {
					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
				}
			}

			isRunning = isNewWalkCmdIsRunning() || isRunning2;
		}
	}

	public int getMapRegionX() {
		return mapRegionX;
	}

	public int getMapRegionY() {
		return mapRegionY;
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}

	public int getH() {
		return heightLevel;
	}

	public int getId() {
		return playerId;
	}

	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}

	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}

	public int getHitDiff() {
		return hitDiff;
	}

	public void setHitUpdateRequired(boolean hitUpdateRequired) {
		this.hitUpdateRequired = hitUpdateRequired;
	}

	public void setHitUpdateRequired2(boolean hitUpdateRequired2) {
		this.hitUpdateRequired2 = hitUpdateRequired2;
	}

	public boolean isHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired2() {
		return hitUpdateRequired2;
	}

	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}

	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}

	public int getChatTextEffects() {
		return chatTextEffects;
	}

	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}

	public byte getChatTextSize() {
		return chatTextSize;
	}

	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}

	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}

	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}

	public byte[] getChatText() {
		return chatText;
	}

	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}

	public int getChatTextColor() {
		return chatTextColor;
	}

	public void setNewWalkCmdX(int newWalkCmdX[]) {
		this.newWalkCmdX = newWalkCmdX;
	}

	public int[] getNewWalkCmdX() {
		return newWalkCmdX;
	}

	public void setNewWalkCmdY(int newWalkCmdY[]) {
		this.newWalkCmdY = newWalkCmdY;
	}

	public int[] getNewWalkCmdY() {
		return newWalkCmdY;
	}

	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
	}

	public boolean isNewWalkCmdIsRunning() {
		return newWalkCmdIsRunning;
	}

	public void setInStreamDecryption(IsaacRandom inStreamDecryption) {

	}

	public void setOutStreamDecryption(IsaacRandom outStreamDecryption) {

	}

	public boolean samePlayer() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId) {
				continue;
			}
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}
			}
		}
		return false;
	}

	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();
	}


	public void dealDamage(int damage) {
		if (teleTimer <= 0) {
			playerLevel[Constants.HITPOINTS] -= damage;
			int difference = playerLevel[Constants.HITPOINTS] - damage;
			if (difference <= playerAssistant.getLevelForXP(playerXP[Constants.HITPOINTS]) / 10 && difference > 0)
				appendRedemption();
			getPlayerAssistant().handleROL();
		} else {
			if (hitUpdateRequired) {
				hitUpdateRequired = false;
			}
			if (hitUpdateRequired2) {
				hitUpdateRequired2 = false;
			}
		}
		getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
	}

	public void appendRedemption() {
		Client c = (Client) PlayerHandler.players[playerId];
		if (c.getPrayer().prayerActive[22]) {
			int added = c.playerLevel[Constants.HITPOINTS] += (int)(c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER]) * .25);
			if (added > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
				c.playerLevel[Constants.HITPOINTS] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]);
			} else {
				c.playerLevel[Constants.HITPOINTS] += (int)(playerAssistant.getLevelForXP(c.playerXP[Constants.PRAYER]) * .25);
			}
			c.playerLevel[Constants.PRAYER] = 0;
			c.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			c.getPlayerAssistant().refreshSkill(Constants.PRAYER);
			c.gfx0(436);
			PrayerDrain.resetPrayers(c);
		}
	}

	public int[] damageTaken = new int[PlayerHandler.players.length];

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

}