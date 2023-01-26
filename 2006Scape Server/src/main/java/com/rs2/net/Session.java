package com.rs2.net;

import org.jboss.netty.channel.Channel;

import com.rs2.game.players.Client;

public class Session {
	
	private final Channel channel;
	private Client client;
	private boolean inList = false;
	
	public Session(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setInList(boolean bool) {
		this.inList = bool;
	}
	
	public boolean isInList() {
		return inList;
	}

}
