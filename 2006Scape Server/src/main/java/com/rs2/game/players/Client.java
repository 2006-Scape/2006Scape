package com.rs2.game.players;

import com.rs2.GameConstants;
import com.rs2.util.Stream;

import io.netty.channel.Channel;

public class Client extends Player {
	
	public Client(Channel s, int _playerId) {
		super(_playerId);
		session = s;
		outStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}
	
	//bots
	public Client(Channel s) {
		super(-1);
		isBot = true;
		session = null;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}

}
