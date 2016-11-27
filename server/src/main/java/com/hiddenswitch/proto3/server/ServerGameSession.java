package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerGameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;

import java.util.List;

public class ServerGameSession extends GameSession implements ServerCommunicationSend, ServerListener {
	private String host;
	private int port;
	private SocketClientReceiver c1;
	private SocketClientReceiver c2;
	private PregamePlayerConfiguration pregamePlayerConfiguration1;
	private PregamePlayerConfiguration pregamePlayerConfiguration2;
	private ServerGameContext gameContext;
	private Player player1;
	private Player player2;

	private ClientConnectionConfiguration getConfigurationFor(PregamePlayerConfiguration player) {
		// TODO: It's obviously insecure to allow the client to specify things like their player object
		return new ClientConnectionConfiguration(getHost(), getPort(),
				new ClientToServerMessage(player.getPlayer(), getGameId()));
	}

	@Override
	public void onPlayerConnected(Player player) {
		if (getPlayer1() == null) {
			setPlayer1(player);
		} else if (getPlayer2() == null) {
			setPlayer2(player);
			DeckFormat simpleFormat = new DeckFormat();
			simpleFormat.addSet(CardSet.PROCEDURAL_PREVIEW);
			simpleFormat.addSet(CardSet.CLASSIC);
			simpleFormat.addSet(CardSet.BASIC);
			this.gameContext = new ServerGameContext(getPlayer1(), getPlayer2(), simpleFormat, getGameId());
			getGameContext().setUpdateListener(getPlayer1(), getPlayerListener(0));
			getGameContext().setUpdateListener(getPlayer2(), getPlayerListener(1));
			getGameContext().networkPlay();
		} else {
			throw new RuntimeException(String.format("Too many players connected!. gameId: %s", getGameId()));
		}
	}

	@Override
	public void onActionReceived(String id, Player callingPlayer, GameAction action) {
		checkContext();
		getGameContext().onActionReceived(id, callingPlayer, action);
	}

	@Override
	public void onMulliganReceived(String id, Player player, List<Card> ReceivedCards) {
		checkContext();
		getGameContext().onMulliganReceived(id, player, ReceivedCards);
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer1() {
		return getConfigurationFor(pregamePlayerConfiguration1);
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer2() {
		return getConfigurationFor(pregamePlayerConfiguration2);
	}

	public ServerGameSession(String host, int port, PregamePlayerConfiguration p1, PregamePlayerConfiguration p2) {
		super();
		setHost(host);
		setPort(port);
		// TODO: in PregamePlayerConfiguration should really contain a playerConfig object.
		this.pregamePlayerConfiguration1 = p1;
		this.pregamePlayerConfiguration2 = p2;
	}

	@Override
	public RemoteUpdateListener getPlayerListener(int player) {
		if (player == 0) {
			return getClient1();
		} else {
			return getClient2();
		}
	}

	public void kill() {

	}

	private void checkContext() {
		if (getGameContext() == null) {
			throw new NullPointerException(String.format("The game context for this game session is null. gameId: %s", getGameId()));
		}
	}

	public String getHost() {
		return host;
	}

	private void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		this.port = port;
	}

	public SocketClientReceiver getClient1() {
		return c1;
	}

	public void setClient1(SocketClientReceiver c1) {
		this.c1 = c1;
	}

	public SocketClientReceiver getClient2() {
		return c2;
	}

	public void setClient2(SocketClientReceiver c2) {
		this.c2 = c2;
	}

	public ServerGameContext getGameContext() {
		return gameContext;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public String getGameId() {
		return gameId;
	}
}
