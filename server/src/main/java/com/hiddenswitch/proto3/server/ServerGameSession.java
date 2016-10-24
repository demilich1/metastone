package com.hiddenswitch.proto3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hiddenswitch.proto3.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.common.ClientToServerMessage;
import com.hiddenswitch.proto3.common.RemoteUpdateListener;

import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class ServerGameSession extends GameSession implements ServerCommunicationSend{
	
	private Lock gameSessionLock;
	SocketClientSession c1;
	SocketClientSession c2;
	ServerListener listener;
	boolean shouldRun = true;

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer1() {
		// TODO 
		throw new RuntimeException("to be implemented");
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer2() {
		// TODO 
		throw new RuntimeException("to be implemented");
	}
	
	public ServerGameSession(PlayerConfig player1, PlayerConfig player2){
		super(new PregamePlayerConfiguration(player1.getDeckForPlay(), player1.getName()), 
				new PregamePlayerConfiguration(player2.getDeckForPlay(), player2.getName()));
		this.gameSessionLock = new ReentrantLock();
		this.listener = new SocketServerListener(this, gameSessionLock);
		
	}
	
	//Temporary constructor since we don't have a matchmaker yet. 
	public ServerGameSession(){
		super(null, null);
		this.gameSessionLock = new ReentrantLock();
		this.listener = new SocketServerListener(this, gameSessionLock);
	}
	
	
	public Lock getLock() {
		return gameSessionLock;
	}

	@Override
	public RemoteUpdateListener getPlayerListener(int player) {
		if (player == 0) {
			return c1;
		} else {
			return c2;
		}
	}
	
	public void registerClient(SocketClientSession client, ClientToServerMessage firstMessage){
		if (c1 == null){
			c1 = client;
			c1.setServerGameSession(this);
			listener.onPlayerConnected(firstMessage.getPlayer1());
		} else {
			c2 = client;
			c2.setServerGameSession(this);
			listener.onPlayerConnected(firstMessage.getPlayer1());
		}
	}

	public void kill() {
		shouldRun = false;
	}	

}
