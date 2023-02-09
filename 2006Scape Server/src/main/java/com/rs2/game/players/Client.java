package com.rs2.game.players;

import org.apollo.game.session.GameSession;

import com.rs2.Constants;
import com.rs2.util.Stream;

public class Client extends Player {
	
	public Client(GameSession s, int _playerId) {
		super(_playerId);
		session = s;
		outStream = new Stream(new byte[Constants.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		buffer = new byte[Constants.BUFFER_SIZE];
	}
	
	//bots
	public Client(GameSession s) {
		super(-1);
		isBot = true;
		session = null;
		buffer = new byte[Constants.BUFFER_SIZE];
	}

	public void setSession(GameSession session) {
		this.session = session;
	}

}
