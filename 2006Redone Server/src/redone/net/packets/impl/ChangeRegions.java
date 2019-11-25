package redone.net.packets.impl;

import redone.GameConstants;
import redone.GameEngine;
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
		if (GameConstants.SOUND && c.musicOn) {
			Music.playMusic(c);
		}
		GameEngine.objectHandler.updateObjects(c);//testing
		Doors.getSingleton().load();
		GameEngine.itemHandler.reloadItems(c);
		GameEngine.objectManager.loadObjects(c);
		GlobalDropsHandler.reset(c);
		c.getPlayerAssistant().removeObjects();// testing
		c.saveFile = true;
		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPlayerAssistant().requestUpdates();
		}
	}
}
