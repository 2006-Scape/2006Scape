import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Client {

	public static void main(String[] args) {
		ClientSettings.SERVER_IP = "2006rebotted.hopto.org";
		try {
			Game game = new Game();
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