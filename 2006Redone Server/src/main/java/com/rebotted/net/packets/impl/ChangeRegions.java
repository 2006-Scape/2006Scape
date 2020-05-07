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
	public void processPacket(Player player, int packetType, int packetSize) {
		if (GameConstants.SOUND && player.musicOn) {
			Music.playMusic(player);
		}
		GameEngine.objectHandler.updateObjects(player);//testing
		GameEngine.itemHandler.reloadItems(player);
		GameEngine.objectManager.loadObjects(player);
		Doors.getSingleton().load();
		GlobalDropsHandler.reset(player);
		player.getPlayerAssistant().removeObjects();// testing
		player.saveFile = true;
		if (player.skullTimer > 0) {
			player.isSkulled = true;
			player.headIconPk = 0;
			player.getPlayerAssistant().requestUpdates();
		}
	}
}
