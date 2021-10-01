import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Main {

	/*

	DEAR DEVELOPER!

	If you want to run the client locally, the easiest way to do that is run the class "Client.java" instead!

	If you REALLY want to use this class, add two random program arguments.
	But seriously, Client.java is just a copy-paste of this class and does it locally. Use that instead!


	 */

	public static void main(String[] args) {
		ClientSettings.SERVER_IP = args.length > 0 ? args[0] : ClientSettings.SERVER_IP;
		try {
			Game game = new Game();
			Game.nodeID = 10;
			Game.portOff = 0;
			Game.setHighMem();
			Game.isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());
			game.myUsername = args.length > 1 ? args[1] : "";
			game.myPassword = args.length > 2 ? args[2] : "";
			game.createClientFrame(503, 765);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
