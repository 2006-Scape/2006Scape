package com.rebotted.game.players;

import org.apache.mina.common.IoSession;

import com.rebotted.GameConstants;
import com.rebotted.util.Stream;

public class Client extends Player {
	
	public Client(IoSession s, int _playerId) {
		super(_playerId);
		session = s;
		outStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}
	
	//bots
	public Client(IoSession s) {
		super(-1);
		isBot = true;
		session = null;
		inStream = new Stream(new byte[GameConstants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[GameConstants.BUFFER_SIZE];
	}

}
