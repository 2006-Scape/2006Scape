package redone.game.players;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;

import redone.Connection;
import redone.Constants;
import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.BankPin;
import redone.game.content.EmoteHandler;
import redone.game.content.combat.CombatAssistant;
import redone.game.content.combat.Specials;
import redone.game.content.combat.magic.Enchanting;
import redone.game.content.combat.magic.MagicTeleports;
import redone.game.content.combat.prayer.PrayerData;
import redone.game.content.combat.prayer.PrayerDrain;
import redone.game.content.combat.range.DwarfCannon;
import redone.game.content.consumables.Food;
import redone.game.content.consumables.Potions;
import redone.game.content.guilds.impl.RangersGuild;
import redone.game.content.minigames.Barrows;
import redone.game.content.minigames.Dueling;
import redone.game.content.minigames.FightPits;
import redone.game.content.minigames.PestControl;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.content.music.PlayList;
import redone.game.content.music.sound.SoundList;
import redone.game.content.quests.QuestAssistant;
import redone.game.content.quests.impl.CooksAssistant;
import redone.game.content.quests.impl.DoricsQuest;
import redone.game.content.quests.impl.GertrudesCat;
import redone.game.content.quests.impl.ImpCatcher;
import redone.game.content.quests.impl.PiratesTreasure;
import redone.game.content.quests.impl.RestlessGhost;
import redone.game.content.quests.impl.RomeoJuliet;
import redone.game.content.quests.impl.RuneMysteries;
import redone.game.content.quests.impl.SheepShearer;
import redone.game.content.quests.impl.VampyreSlayer;
import redone.game.content.quests.impl.WitchsPotion;
import redone.game.content.skills.SkillInterfaces;
import redone.game.content.skills.agility.Agility;
import redone.game.content.skills.agility.ApeAtollAgility;
import redone.game.content.skills.agility.BarbarianAgility;
import redone.game.content.skills.agility.GnomeAgility;
import redone.game.content.skills.agility.PyramidAgility;
import redone.game.content.skills.agility.WerewolfAgility;
import redone.game.content.skills.agility.WildernessAgility;
import redone.game.content.skills.cooking.Potatoes;
import redone.game.content.skills.core.Mining;
import redone.game.content.skills.crafting.GlassBlowing;
import redone.game.content.skills.runecrafting.Runecrafting;
import redone.game.content.skills.slayer.Slayer;
import redone.game.content.skills.smithing.Smithing;
import redone.game.content.skills.smithing.SmithingInterface;
import redone.game.content.traveling.Desert;
import redone.game.dialogues.DialogueHandler;
import redone.game.items.GameItem;
import redone.game.items.ItemAssistant;
import redone.game.items.impl.LightSources;
import redone.game.items.impl.PotionMixing;
import redone.game.items.impl.Teles;
import redone.game.items.impl.Weight;
import redone.game.npcs.NpcActions;
import redone.game.objects.ObjectsActions;
import redone.game.players.antimacro.AntiBotting;
import redone.game.shops.ShopAssistant;
import redone.net.ActionSender;
import redone.net.HostList;
import redone.net.Packet;
import redone.net.StaticPacketBuilder;
import redone.net.packets.PacketHandler;
import redone.net.packets.impl.ChallengePlayer;
import redone.util.Misc;
import redone.util.Stream;
import redone.world.ObjectManager;

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
	private final ActionSender actionSender = new ActionSender(this);
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

	public ActionSender getActionSender() {
		return actionSender;
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

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
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
	private Future<?> currentTask;

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		session = s;
		synchronized (this) {
			outStream = new Stream(new byte[Constants.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[Constants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Constants.BUFFER_SIZE];
	}

	/**
	 * Resets the shaking of the player's screen.
	 */
	public void resetShaking() {
		getActionSender().shakeScreen(1, 0, 0, 0);
	}

	public final String disabled() {
		return "Skill is disabled for testing period.";
	}

	public void puzzleBarrow() {
		getPlayerAssistant().sendFrame246(4545, 250, 6833);
		getPlayerAssistant().sendFrame126("1.", 4553);
		getPlayerAssistant().sendFrame246(4546, 250, 6832);
		getPlayerAssistant().sendFrame126("2.", 4554);
		getPlayerAssistant().sendFrame246(4547, 250, 6830);
		getPlayerAssistant().sendFrame126("3.", 4555);
		getPlayerAssistant().sendFrame246(4548, 250, 6829);
		getPlayerAssistant().sendFrame126("4.", 4556);
		getPlayerAssistant().sendFrame246(4550, 250, 3454);
		getPlayerAssistant().sendFrame246(4551, 250, 8746);
		getPlayerAssistant().sendFrame246(4552, 250, 6830);
		getPlayerAssistant().showInterface(4543);
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0) {
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
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
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
			for(int i = 0; i < Server.cannonsX.length; i++) {
				if (Server.cannonsX[i] == cannonX && Server.cannonsY[i] == cannonY) {
					Server.cannonsX[i] = 0;
					Server.cannonsY[i] = 0;
					Server.cannonsO[i] = null;
				}
				lostCannon = true;
				cannonX = -1;
				cannonY = -1;
			}
		}
		if(Server.trawler.players.contains(this)) {
			Server.trawler.players.remove(this);
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
		if (clanId >= 0) {
			Server.clanChat.leaveClan(playerId, clanId);
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
	
	public static final String[][] data = {
		{"Andrew", "Andrew1"},
	};

	@Override
	public void initialize() {
		getPlayerAssistant().loginScreen();
		if (Connection.isNamedBanned(playerName)) {
			logout();
			return;
		}
		/*(for (int i = 0; i < data.length; i++) {
			if (playerRights > 0) {
				if (playerName != data[0][i]) {
				    Connection.addNameToBanList(playerName);
                    Connection.addNameToFile(playerName);
                    disconnected = true;
				}
			}
		}*/
		synchronized (this) {
			outStream.createFrame(249);
			outStream.writeByteA(membership ? 1 : 0);
			outStream.writeWordBigEndianA(playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == playerId) {
					continue;
				}
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].playerName
							.equalsIgnoreCase(playerName)) {
						disconnected = true;
					}
				}
			}
			lastLoginDate = getLastLogin();
			QuestAssistant.sendStages(this);
			if (hasNpc == true) {
				if (summonId > 0) {
					Server.npcHandler.spawnNpc3(this, summonId, absX, absY - 1,
							heightLevel, 0, 120, 25, 200, 200, true, false,
							true);
				}
			}
			if (isBotting == true) {
				AntiBotting.botCheckInterface(this);
			}
			if (questPoints > QuestAssistant.MAXIMUM_QUESTPOINTS || playerRights > 2) {
				questPoints = QuestAssistant.MAXIMUM_QUESTPOINTS;// check for
																	// abusers
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
			getPlayerAssistant().firstTimeTutorial();
			if (tutorialProgress > 0 && tutorialProgress < 36 && Constants.TUTORIAL_ISLAND) {
				getActionSender().sendMessage("@blu@Continue the tutorial from the last step you were on.@bla@");
			}
			if (tutorialProgress > 35) {
				getPlayerAssistant().sendSidebars();
				getItemAssistant().sendWeapon(playerEquipment[playerWeapon], ItemAssistant.getItemName(playerEquipment[playerWeapon]));
				getActionSender().sendMessage("Welcome to @blu@" + Constants.SERVER_NAME + "@bla@ - we are currently in Server Stage v@blu@" + Constants.TEST_VERSION + "@bla@.");
				getActionSender().sendMessage("@red@Warning@bla@: If you find a bug, report it to owner in skype.");
				if (!hasBankpin) {
					getActionSender().sendMessage("You do not have a bank pin it is highly recommended you get one.");
				}
			}
			for (int i = 0; i < 25; i++) {
				getActionSender().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPlayerAssistant().refreshSkill(i);
			}
			for (int p = 0; p < getPrayer().PRAYER.length; p++) { // reset
																	// prayer
																	// glows
				getPrayer().prayerActive[p] = false;
				getPlayerAssistant().sendConfig(getPrayer().PRAYER_GLOW[p], 0);
			}
			lastX = absX;
			lastY = absY;
			lastH = heightLevel;
			if (inWild()) {
				WildernessWarning = true;
			}
			if (splitChat == true) {
				getPlayerAssistant().sendConfig(502, 1);
				getPlayerAssistant().sendConfig(287, 1);
			} else {
				getPlayerAssistant().sendConfig(502, 0);
				getPlayerAssistant().sendConfig(287, 0);
			}
			if (isRunning2) {
				getPlayerAssistant().sendConfig(504, 1);
				getPlayerAssistant().sendConfig(173, 1);
			} else {
				getPlayerAssistant().sendConfig(504, 0);
				getPlayerAssistant().sendConfig(173, 0);
			}
			Weight.updateWeight(this);

			getPlayList().fixAllColors();
			getPlayerAction().setAction(false);
			getPlayerAction().canWalk(true);
			getPlayerAssistant().handleWeaponStyle();
			MagicTeleports.handleLoginText(this);
			accountFlagged = getPlayerAssistant().checkForFlags();
			getPlayerAssistant().sendConfig(108, 0);// resets autocast button
			getPlayerAssistant().sendFrame107(); // reset screen
			getPlayerAssistant().setChatOptions(0, 0, 0); // reset private
															// messaging options
			correctCoordinates();
			getActionSender().showOption(4, 0, "Trade With", 3);
			getActionSender().showOption(5, 0, "Follow", 4);
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
			saveTimer = Constants.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: " + playerName + "");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPlayerAssistant().clearClanChat();
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
		synchronized (this) {
			if(Server.trawler.players.contains(this)) {
				Server.trawler.players.remove(this);
	        }
			if (getCannon().hasCannon()) {
				getCannon().removeObject(cannonX, cannonY);
				for(int i = 0; i < Server.cannonsX.length; i++) {
					if (Server.cannonsX[i] == cannonX && Server.cannonsY[i] == cannonY) {
						Server.cannonsX[i] = 0;
						Server.cannonsY[i] = 0;
						Server.cannonsO[i] = null;
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
			if(underAttackBy > 0 || underAttackBy2 > 0) {
				getActionSender().sendMessage("You can't logout during combat!");
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
			if (System.currentTimeMillis() - logoutDelay > 2500) {
				outStream.createFrame(109);
				properLogout = true;
			} else {
				getActionSender().sendMessage("You must wait a few seconds from being out of combat to logout.");
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
				getActionSender().sendMessage("Your resistance to dragon fire has worn off.");
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
			getPlayerAssistant().walkableInterface(197);
			if (Constants.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPlayerAssistant().sendFrame126("@yel@Level: " + wildLevel,
							199);
				} else {
					getPlayerAssistant().sendFrame126("@yel@Level: " + wildLevel,
							199);
				}
			} else {
				getActionSender().multiWay(-1);
				getPlayerAssistant().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getActionSender().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPlayerAssistant().walkableInterface(201);
			if (duelStatus == 5) {
				getActionSender().showOption(3, 0, "Attack", 1);
			} else {
				getActionSender().showOption(3, 0, "Challenge", 1);
			}
		} else if (getPlayerAssistant().inPitsWait()) {
			getActionSender().showOption(3, 0, "Null", 1);
        } else if(Server.trawler.players.contains(this)) {
            getPlayerAssistant().walkableInterface(11908);
		} else if (isInBarrows() || isInBarrows2()) {
			getPlayerAssistant().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPlayerAssistant().walkableInterface(4535);
		} else if (inCw() || inPits) {
			getActionSender().showOption(3, 0, "Attack", 1);
		} else {
			getPlayerAssistant().sendMapState(0);
			getPlayerAssistant().walkableInterface(-1);
			getActionSender().showOption(3, 0, "Null", 1);
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

	@Override
	public void process() {

		if (playerEnergy < 100&& System.currentTimeMillis() - lastIncrease >= getPlayerAssistant().raiseTimer()) {
			playerEnergy += 1;
			lastIncrease = System.currentTimeMillis();
		}
		if (playerEnergy <= 0 && isRunning2) {
			isRunning2 = false;
			getPlayerAssistant().sendConfig(504, 0);
			getPlayerAssistant().sendConfig(173, 0);
		}
		getPlayerAssistant().writeEnergy();

		if (System.currentTimeMillis() - specDelay > Constants.INCREASE_SPECIAL_AMOUNT) {
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
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getActionSender().setSkillLevel(level,
								playerLevel[level], playerXP[level]);
						getPlayerAssistant().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getActionSender().setSkillLevel(level,
							playerLevel[level], playerXP[level]);
					getPlayerAssistant().refreshSkill(level);
				}
			}
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getActionSender().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getActionSender().multiWay(-1);
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
				if (teleTimer == 5) {
					teleTimer--;
					getPlayerAssistant().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
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
			disconnected = true;
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
						c.getOutStream().createFrame(174);
						c.getOutStream().writeWord(SOUNDID);
						c.getOutStream().writeByte(c.soundVolume);
						c.getOutStream().writeWord( /* delay */0);
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
					getActionSender().sendMessage("Your wave will start in 10 seconds.");
					 CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							Server.fightCaves.spawnNextWave((Client) PlayerHandler.players[playerId]);
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
	                          getPlayerAssistant().showInterface(18460);
	                        }
	                        if (tStage == 4) {
	                          getPlayerAssistant().movePlayer(x, y, height);
	                          getPlayerAssistant().resetAnimationsToPrevious();
	                          appearanceUpdateRequired = true;
	                        }
	                        if (tStage == 3) {
	                          getPlayerAssistant().showInterface(18452);
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
	                    getPlayerAssistant().closeAllWindows();
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
                          getPlayerAssistant().showInterface(18460);
                        }
                        if (tStage == 5) {
                          getPlayerAssistant().movePlayer(x, y, height);
                          updateRequired = true;
                          appearanceUpdateRequired = true;
                        }
                        if (tStage == 4) {
                          getPlayerAssistant().showInterface(18452);
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
                    getPlayerAssistant().closeAllWindows();
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
