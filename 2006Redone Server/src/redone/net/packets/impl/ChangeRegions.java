package redone.net.packets.impl;

import redone.Constants;
import redone.Server;
import redone.game.content.music.Music;
import redone.game.globalworldobjects.Doors;
import redone.game.players.Client;
import redone.net.packets.PacketType;
import redone.world.GlobalDropsHandler;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (Constants.SOUND && c.musicOn) {
			Music.playMusic(c);
		}
		Server.objectHandler.updateObjects(c);//testing
		Doors.getSingleton().load();
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		GlobalDropsHandler.load(c);
		c.getPlayerAssistant().removeObjects();// testing
		c.saveFile = true;
		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPlayerAssistant().requestUpdates();
		}
	}
}
