package com.rs2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Stopwatch;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcList;
import com.rs2.gui.ControlPanel;

import com.rs2.util.CustomPrintStream;
import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.cache.decoder.ObjectDefinitionDecoder;
import org.apollo.jagcached.FileServer;

import com.rs2.game.bots.BotHandler;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.content.minigames.FightPits;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.minigames.magetrainingarena.MageTrainingArena;
import com.rs2.game.content.minigames.trawler.Trawler;
import com.rs2.game.globalworldobjects.Doors;
import com.rs2.game.globalworldobjects.DoubleDoors;
import com.rs2.game.items.ItemDefinitions;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.players.PlayerSave;
import com.rs2.game.shops.ShopHandler;
import com.rs2.integrations.PlayersOnlineWebsite;
import com.rs2.integrations.RegisteredAccsWebsite;
import com.rs2.integrations.discord.DiscordActivity;
import com.rs2.integrations.discord.JavaCord;
import com.rs2.util.HostBlacklist;
import com.rs2.world.GlobalDropsHandler;
import com.rs2.world.ItemHandler;
import com.rs2.world.ObjectHandler;
import com.rs2.world.ObjectManager;
import com.rs2.world.clip.RegionFactory;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import org.apollo.net.NetworkConstants;

/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 * @author Integration Julian.
 */
public class GameEngine {


	private static long minutesCounter;

	private static void startMinutesCounter() {
		try {
			minuteFile = new BufferedReader(new FileReader(
					Constants.SERVER_LOG_DIR + "minutes.log"));
			minutesCounter = Long.parseLong(minuteFile.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setMinutesCounter(long minutesCounter) {
		try {
			BufferedWriter minuteCounter = new BufferedWriter(new FileWriter(
					Constants.SERVER_LOG_DIR + "minutes.log"));
			minuteCounter.write(Long.toString(minutesCounter));
			minuteCounter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static long getMinutesCounter() {
		long d = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()
				- minutesCounter);
		return d;
	}

	public static String ersSecret;
	public static int[] cannonsX = new int[50];
	public static int[] cannonsY = new int[50];
	public static String[] cannonsO = new String[50];
	public static boolean sleeping;
	public static boolean updateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	public static boolean shutdownServer = false;
	public static int garbageCollectDelay = 40;
	public static boolean shutdownClientHandler;
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NpcHandler npcHandler = new NpcHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static FightCaves fightCaves = new FightCaves();
	private static PestControl pestControl = new PestControl();
	public static Trawler trawler = new Trawler();
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final static Lock lock = new ReentrantLock();
	public static ControlPanel panel;
	private static long serverStartTime;

	static {
		shutdownServer = false;
	}

	public static void main(java.lang.String[] args)
			throws NullPointerException, IOException {
		CustomPrintStream errorStream = new CustomPrintStream(System.err, "ERROR", true);
		System.setErr(errorStream);
		CustomPrintStream infoStream = new CustomPrintStream(System.out, "INFO", true);
		System.setOut(infoStream);
		serverStartTime = System.currentTimeMillis();
		if (NetworkConstants.RSA_EXPONENT != Constants.RSA_EXPONENT) {
			NetworkConstants.RSA_EXPONENT = Constants.RSA_EXPONENT;
			NetworkConstants.RSA_MODULUS = Constants.RSA_MODULUS;

		}
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-gui"))
				Constants.GUI_ENABLED = true;
			if (args[i].startsWith("-") && (i + 1) < args.length && !args[i + 1].startsWith("-")) {
				switch (args[i]) {
					case "-c":
					case "-config":
						try {
							System.out.println("Loading External Config..");
							ConfigLoader.loadSettings(args[++i]);
							System.out.println("Loaded Config File " + args[i]);
						} catch (IOException e) {
							System.out.println("Config File Not Found");
						}
						break;
				}
			}
		}

		System.out.println("Starting game engine..");
		if (Constants.SERVER_DEBUG) {
			System.out.println("@@@@ DEBUG MODE IS ENABLED @@@@");
		}

		if (!new File("data").exists()) {
			System.err.println("************************************");
			System.err.println("************************************");
			System.err.println("************************************");
			System.err.println("WARNING: I could not find the /data folder. You are LIKELY running this in the wrong directory!");
			System.err.println("In IntelliJ, fix it by clicking \"Server\" > Edit Configurations at the top of your screen");
			System.err.println("Then changing the \"Working Directory\" to be in \"2006Scape/2006Scape Server\", instead of just \"2006Scape\"");
			System.err.println("************************************");
			System.err.println("************************************");
			System.err.println("************************************");
			System.exit(1);
		}

		/**
		 * Starting Up Server
		 */
		System.out.println("Launching " + Constants.SERVER_NAME + " World: " + Constants.WORLD + "...");

		/**
		 * Start Integration Services
		 **/
		ConfigLoader.loadSecrets();
		JavaCord.init();

		/**
		 * Accepting Connections
		 */
		//TODO debug ResourceLeakDetector.setLevel(Level.PARANOID);
		ResourceLeakDetector.setLevel(Level.DISABLED);
		FileServer fs = new FileServer();
		try {
			fs.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		IndexedFileSystem cache = new IndexedFileSystem(Paths.get(Constants.FILE_SYSTEM_DIR), true);
		new ObjectDefinitionDecoder(cache).run();
		new ItemDefinitionDecoder(cache).run();
		
		/**
		 * Initialise Handlers
		 */
		RegionFactory.load(cache);
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		ItemDefinitions.load();
		GlobalDropsHandler.initialize();
		Connection.initialize();
		HostBlacklist.loadBlacklist();
		BotHandler.loadPlayerShops();
		startMinutesCounter();
		setMinutesCounter(minutesCounter);

		/**
		 * Load Plugins
		 */
		Player.getPluginService().load();

		/**
		 * Server Successfully Loaded
		 */
		System.out.println("World Server listening on " + fs.service.toString());

		/**
		 * Makes Visible Control Panel If Enabled
		 */
		if(Constants.GUI_ENABLED) {
			ControlPanel panel = new ControlPanel();
			panel.initComponents();
			panel.setVisible(true);
			System.out.println("Control Panel Enabled.");
		}

		/**
		 * Main Server Tick
		 *
		 * This scheduler will tick once every 600ms. If the previous tick takes
		 * 300ms to execute, this scheduler will wait 300ms only before the next
		 * tick.
		 *
		 * scheduleAtFixedRate() does not invoke concurrent Runnables.
		 */
		scheduler.scheduleAtFixedRate(new Runnable() {
			int gameTicksIncrementor;
			final int printInfoTick = Constants.CYCLE_LOGGING_TICK;
			public void run() {
				Stopwatch stopwatch = Stopwatch.createStarted();
				/**
				 * Main Server Tick
				 */
				try {
					if (GameEngine.shutdownServer) {
						scheduler.shutdown();
					}
					long startItemHandler = System.currentTimeMillis();
					itemHandler.process();
					long durationItemHandler = System.currentTimeMillis() - startItemHandler;
					checkAndLogDuration("ItemHandler", durationItemHandler);
					long startPlayerHandler = System.currentTimeMillis();
					playerHandler.process();
					long durationPlayerHandler = System.currentTimeMillis() - startPlayerHandler;
					checkAndLogDuration("PlayerHandler", durationPlayerHandler);
					long startNpcHandler = System.currentTimeMillis();
					npcHandler.process();
					long durationNpcHandler = System.currentTimeMillis() - startNpcHandler;
					checkAndLogDuration("NpcHandler", durationNpcHandler);
					long startShopHandler = System.currentTimeMillis();
					shopHandler.process();
					long durationShopHandler = System.currentTimeMillis() - startShopHandler;
					checkAndLogDuration("ShopHandler", durationShopHandler);
					long startObjectManager = System.currentTimeMillis();
					objectManager.process();
					long durationObjectManager = System.currentTimeMillis() - startObjectManager;
					checkAndLogDuration("ObjectManager", durationObjectManager);
					long startCastleWars = System.currentTimeMillis();
					CastleWars.process();
					long durationCastleWars = System.currentTimeMillis() - startCastleWars;
					checkAndLogDuration("CastleWars", durationCastleWars);
					long startFightPits = System.currentTimeMillis();
					FightPits.process();
					long durationFightPits = System.currentTimeMillis() - startFightPits;
					checkAndLogDuration("FightPits", durationFightPits);
					long startPestControl = System.currentTimeMillis();
					pestControl.process();
					long durationPestControl = System.currentTimeMillis() - startPestControl;
					checkAndLogDuration("PestControl", durationPestControl);
					long startObjectHandler = System.currentTimeMillis();
					objectHandler.process();
					long durationObjectHandler = System.currentTimeMillis() - startObjectHandler;
					checkAndLogDuration("CastleWars", durationObjectHandler);
					long startMageTrainingArena = System.currentTimeMillis();
					MageTrainingArena.process();
					long durationMageTrainingArena = System.currentTimeMillis() - startMageTrainingArena;
					checkAndLogDuration("MageTrainingArena", durationMageTrainingArena);
					long startCycleEventHandler = System.currentTimeMillis();
					CycleEventHandler.getSingleton().process();
					long durationCycleEventHandler = System.currentTimeMillis() - startCycleEventHandler;
					checkAndLogDuration("CycleEventHandler", durationCycleEventHandler);
					long startIntegrationEvents = System.currentTimeMillis();
					if (Constants.WEBSITE_INTEGRATION) {
						PlayersOnlineWebsite.addUpdatePlayersOnlineTask();
						RegisteredAccsWebsite.addUpdateRegisteredUsersTask();
					}
					if (DiscordActivity.playerCount) {
						DiscordActivity.updateActivity();
					}
					long durationIntegrationEvents = System.currentTimeMillis() - startIntegrationEvents;
					checkAndLogDuration("IntegrationEvents", durationIntegrationEvents);
					long startSaveEvents = System.currentTimeMillis();
					if (System.currentTimeMillis() - lastMassSave > 300000) {
						for (Player p : PlayerHandler.players) {
							if (p == null) {
								continue;
							}
							PlayerSave.saveGame((Client) p);
							System.out.println("Saved game for " + p.playerName + ".");
							lastMassSave = System.currentTimeMillis();
						}
					}
					long durationSaveEvents = System.currentTimeMillis() - startSaveEvents;
					checkAndLogDuration("SaveEvents", durationSaveEvents);
					long totalCycleDuration = stopwatch.elapsed(TimeUnit.MILLISECONDS);
					//Technically, we could add commands to test both client lag (creating many tile objects) and server lag (creating a BCrypt hash on game thread)
					if (totalCycleDuration > 500) {
						System.err.println("ERROR: Cycle duration exceeded 500 ms!");
					} else if (totalCycleDuration > 250) {
						System.err.println("WARNING: Cycle duration exceeded 250 ms!");
					} else if (totalCycleDuration > 100) {
						System.out.println("NOTICE: Cycle duration exceeded 100 ms.");
					}
					gameTicksIncrementor++;
					if (Constants.CYCLE_LOGGING && gameTicksIncrementor > 1 && gameTicksIncrementor % printInfoTick == 0) {
						long totalMem = Runtime.getRuntime().totalMemory();
						long freeMem = Runtime.getRuntime().freeMemory();
						long maxMem = Runtime.getRuntime().maxMemory();
						int playerCount = 0;
						for (Player p : PlayerHandler.players) {
							if (p != null) {
								playerCount++;
							}
						}
						int npcCount = 0;
						for (Npc npc : NpcHandler.npcs) {
							if (npc != null) {
								npcCount++;
							}
						}
						System.out.println("Cycle #" + gameTicksIncrementor + " took " + totalCycleDuration + " ms. Players: " + playerCount + ", NPCs: " + npcCount +
								", [Durations: i: " + durationItemHandler + " ms, p: " + durationPlayerHandler + " ms, n: " + durationNpcHandler + " ms, s: " + durationShopHandler +
								" ms, oh: " + durationObjectHandler + " ms, om: " + durationObjectManager + " ms], Memory: " + (totalMem - freeMem) / 1024 / 1024 + "MB/" +
								totalMem / 1024 / 1024 + "MB. Max: " + maxMem / 1024 / 1024 + "MB, Threads: " + Thread.activeCount() + ".");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.err.println("A fatal exception has been thrown in the GameEngine cycle! Saving all players.");
					for (Player p : PlayerHandler.players) {
						if (p == null) {
							continue;
						}
						if (p.inTrade) {
							((Client) p).getTrading().declineTrade();
						}
						if (p.duelStatus == 6) {
							((Client) p).getDueling().claimStakedItems();
						}
						PlayerSave.saveGame((Client) p);
						System.out.println("Saved game for " + p.playerName + ".");
					}
					scheduler.shutdown(); // Kills the tickloop thread if Exception is thrown.
				}
			}
		}, 0, Constants.CYCLE_TIME, TimeUnit.MILLISECONDS);

		/*
		 * I'd recommend disabling this until I can be bothered to implement it
		 * properly.
		 */
		// CommandConsole.getInstance();

		try {
			while (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
				// TODO
				// Cleanup?
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
	
	private static void checkAndLogDuration(String processName, long duration) {
		if (duration > 500) {
			System.err.println("ERROR: " + processName + " duration exceeded 500 ms! Duration: " + duration + " ms.");
		} else if (duration > 250) {
			System.err.println("WARNING: " + processName + " duration exceeded 250 ms! Duration: " + duration + " ms.");
		} else if (duration > 100) {
			System.out.println("NOTICE: " + processName + " duration exceeded 100 ms. Duration: " + duration + " ms.");
		}
	}

	public static boolean playerExecuted = false;
	private static BufferedReader minuteFile;

	public static long getServerStartTime() {
		return serverStartTime;
	}
}