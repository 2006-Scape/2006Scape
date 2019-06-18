package redone.game.content;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

/**
 * Membership Handler
 * @author Andrew's
 */

public class MemberShipHandler {

	public static final boolean MEMBERS_AREAS = true;

	public static boolean isMembers(Client player) {
		if (!player.membership && player.playerRights < 2) {
			player.getActionSender()
					.sendMessage(
							"You can't use this feature because this is a members only feature.");
			player.getActionSender().sendClearScreen();
			return false;
		}
		return true;
	}

	public static boolean membersAreas(Client player) {
		player.getActionSender().sendMessage(
				"This member area is currently disabled until finished sorry.");
		player.getActionSender().sendClearScreen();
		return false;
	}

	private static void updateBank(Client player) {
		if (player.membership && Constants.MEMBERSHIP) {
			Constants.BANK_SIZE = 352;
		} else {
			player.getActionSender().sendMessage(
					"Error in the updating bank process.");
		}
	}

	public void teleHome(Client player) {
		if (player.membership == true) {
			String type = player.playerMagicBook == 0 ? "modern" : "ancient";
			player.getPlayerAssistant().startTeleport(Constants.RESPAWN_X,
					Constants.RESPAWN_Y, 0, type);
			player.getActionSender().sendMessage(
					"You teleport to Lumbridge.");
		} else {
			player.getActionSender().sendMessage(
					"Teleporting is disabled for non members.");
		}
	}

	public static void giveMembership(final Client other, final Client player) {
		   CycleEventHandler.getSingleton().addEvent(other, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (Constants.MEMBERSHIP) {
					other.membership = true;
					updateBank(other);
					other.getActionSender().sendMessage(
							"You have been awarded membership!");
					other.getActionSender().sendMessage(
							"You now have " + Constants.BANK_SIZE
									+ " bank slots!");// to test
					other.getActionSender().sendMessage(
							"You can now do ::membercommands for commands!");
					other.getActionSender().sendMessage(
							"You may now use the ancient magicks book!");
					other.getActionSender()
							.sendMessage(
									"Your membership lasts forever enjoy! You will be logged so the changes can take place.");
					other.logout();
					stop();
				} else {
					player.getActionSender().sendMessage("Awarding membership has been disabled.");
					container.stop();
				}
			}
			@Override
				public void stop() {
					
				}
		}, 3);
	}
}
