import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Main {

	/*

	DEAR DEVELOPER!

	If you want to run the client locally, the easiest way to do that is run the class "Client.java" instead!

	If you REALLY want to use this class, add program arguments "-s localhost".
	But seriously, Client.java is just a copy-paste of this class and does it locally. Use that instead!

	 */

	public static void main(String[] args) {
		try {
			// Process server/ip address to connect to
			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith("-") && (i + 1) < args.length  && !args[i + 1].startsWith("-")) {
					switch(args[i]) {
						case "-s":
						case "-server":
						case "-ip":
							ClientSettings.SERVER_IP = args[++i];
							break;
					}
				}
			}

			Game game = new Game();

			// Process other arguments
			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith("-") && (i + 1) < args.length  && !args[i + 1].startsWith("-")) {
					switch(args[i]) {
						case "-u":
						case "-user":
						case "-username":
							game.myUsername = args[++i];
							break;
						case "-p":
						case "-pass":
						case "-password":
							game.myPassword = args[++i];
							break;
					}
				}
			}

			Game.nodeID = 10;
			Game.portOff = 0;
			Game.setHighMem();
			Game.isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());
			game.createClientFrame(503, 765);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
