package com.rebotted;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.minigames.FightCaves;
import com.rebotted.game.content.minigames.FightPits;
import com.rebotted.game.content.minigames.PestControl;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.minigames.trawler.Trawler;
import com.rebotted.game.globalworldobjects.Doors;
import com.rebotted.game.globalworldobjects.DoubleDoors;
import com.rebotted.game.items.ItemDefinitions;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.game.players.PlayerSave;
import com.rebotted.game.shops.ShopHandler;
import com.rebotted.integrations.PlayersOnlineWebsite;
import com.rebotted.integrations.RegisteredAccsWebsite;
import com.rebotted.integrations.SettingsLoader;
import com.rebotted.integrations.discord.DiscordActivity;
import com.rebotted.integrations.discord.JavaCord;
import com.rebotted.net.ConnectionHandler;
import com.rebotted.net.ConnectionThrottleFilter;
import com.rebotted.util.HostBlacklist;
import com.rebotted.world.GlobalDropsHandler;
import com.rebotted.world.ItemHandler;
import com.rebotted.world.ObjectHandler;
import com.rebotted.world.ObjectManager;
import com.rebotted.world.clip.ObjectDef;
import com.rebotted.world.clip.Region;

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


	public static String ersSecret;
	public static int[] cannonsX = new int [50];
	public static int[] cannonsY = new int [50];
	public static String[] cannonsO = new String [50];
	public static boolean sleeping;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static IoAcceptor acceptor;
	private static ConnectionHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	private static boolean shutdownServer = false;
	public static int garbageCollectDelay = 40;
	public static boolean shutdownClientHandler;
	private static int serverlistenerPort;
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NpcHandler npcHandler = new NpcHandler();
	private static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static FightCaves fightCaves = new FightCaves();
	private static PestControl pestControl = new PestControl();
	public static Trawler trawler = new Trawler();	

	/**
	 * Port and Cycle rate.
	 */
	static {
		serverlistenerPort = 43594;
		shutdownServer = false;
	}

	public static void main(java.lang.String args[])
			throws NullPointerException, IOException {
		if (GameConstants.SERVER_DEBUG) {
			System.out.println("@@@@ DEBUG MODE IS ENABLED @@@@");
		}

		if (!new File("data").exists())
		{
		    System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("WARNING: I could not find the /data folder. You are LIKELY running this in the wrong directory!");
			System.out.println("In IntelliJ, fix it by clicking \"Server\" > Edit Configurations at the top of your screen");
			System.out.println("Then changing the \"Working Directory\" to be in \"2006rebotted/2006Redone Server\", instead of just \"2006rebotted\"");
			System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("************************************");
			System.exit(1);
		}

		/**
		 * Starting Up Server
		 */
		System.out.println("Launching " + GameConstants.SERVER_NAME + "...");

		/**
		 * Start Integration Services
         **/
        SettingsLoader.loadSettings();
		JavaCord.init();

		/**
		 * Accepting Connections
		 */
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler();

		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);

		throttleFilter = new ConnectionThrottleFilter(GameConstants.CONNECTION_DELAY);
		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		acceptor.bind(new InetSocketAddress(serverlistenerPort), connectionHandler, sac);

		/**
		 * Initialise Handlers
		 */
		ObjectDef.loadConfig();
		Region.load();
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		ItemDefinitions.read();
		GlobalDropsHandler.initialize();
		Connection.initialize();
		HostBlacklist.loadBlacklist();

		/**
		 * Server Successfully Loaded
		 */
		System.out.println("Server listening on port " + serverlistenerPort);

		/**
		 * Main Server Tick
		 */
		try {
			while (!GameEngine.shutdownServer) {
					Thread.sleep(600);
				itemHandler.process();
				playerHandler.process();
				npcHandler.process();
				shopHandler.process();
				objectManager.process();
				CastleWars.process();
				FightPits.process();
				pestControl.process();
				CycleEventHandler.getSingleton().process();
				PlayersOnlineWebsite.addUpdatePlayersOnlineTask();
				RegisteredAccsWebsite.addUpdateRegisteredUsersTask();
				DiscordActivity.updateActivity();
				if (System.currentTimeMillis() - lastMassSave > 300000) {
					for (Player p : PlayerHandler.players) {
						if (p == null) {
							continue;
						}
						PlayerSave.saveGame((Client) p);
						System.out.println("Saved game for " + p.playerName
								+ ".");
						lastMassSave = System.currentTimeMillis();
					   }
                }
            }
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A fatal exception has been thrown!");
			for (Player p : PlayerHandler.players) {
				if (p == null) {
					continue;
				}
				if (p.inTrade) {
					((Client)p).getTrading().declineTrade();
	            }
				if(p.duelStatus == 6) {
					((Client)p).getDueling().claimStakedItems();
				}
				PlayerSave.saveGame((Client) p);
				System.out.println("Saved game for " + p.playerName + ".");
			}
		}
		acceptor = null;
		connectionHandler = null;
		sac = null;
		System.exit(0);
	}

	public static boolean playerExecuted = false;

}
