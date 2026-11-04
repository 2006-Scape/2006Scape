import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Main {

	/*

	DEAR DEVELOPER!

	If you want to run the client locally, the easiest way to do that is run the class "Client.java" instead!
	If you REALLY want to use this class, add program arguments "-s localhost".
	 */

	public static void main(String[] args) {
		try {
			Game game = new Game();
			ClientArgs.apply(args, game);

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
