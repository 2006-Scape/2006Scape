package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.game.content.music.Music;
import com.rebotted.game.globalworldobjects.Doors;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;
import com.rebotted.world.GlobalDropsHandler;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
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
