package com.hiddenswitch.proto3.server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class ServerGameSession extends GameSession implements ServerCommunicationSend {

	private Lock gameSessionLock;
	SocketClientSession c1;
	SocketClientSession c2;
	ServerListener listener;
	boolean shouldRun = true;
	private Player player1;
	private Player player2;

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer1() {
		return new ClientConnectionConfiguration(SocketServerSession.HOST, SocketServerSession.PORT, 
				new ClientToServerMessage(player1, getId()));
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer2() {
		return new ClientConnectionConfiguration(SocketServerSession.HOST, SocketServerSession.PORT, 
				new ClientToServerMessage(player2, getId()));
	}
	
	public ServerGameSession(PregamePlayerConfiguration p1, PregamePlayerConfiguration p2){
		super(p1, p2);
		//TODO: in PregamePlayerConfiguration should really contain a playerConfig object.
		this.player1 = new Player(new PlayerConfig(p1.getDeck(), new HumanBehaviour()));
		this.player2 = new Player(new PlayerConfig(p2.getDeck(), new HumanBehaviour()));
	}

	//Temporary constructor since we don't have a matchmaker yet. 
	public ServerGameSession() {
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

	public void registerClient(SocketClientSession client, ClientToServerMessage firstMessage) {
		if (c1 == null) {
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
