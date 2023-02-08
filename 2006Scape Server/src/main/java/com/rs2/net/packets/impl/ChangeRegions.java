package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.game.content.music.Music;
import com.rs2.game.globalworldobjects.Doors;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.GlobalDropsHandler;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		if (Constants.SOUND && player.musicOn) {
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
