package com.hiddenswitch.proto3.net.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.visuals.GameContextVisuals;

public class RemoteGameContext extends GameContext implements GameContextVisuals, RemoteUpdateListener {
	private final List<GameEvent> gameEvents = new ArrayList<>();
	private boolean blockedByAnimation;
	private Player localPlayer;
	private final AtomicInteger activePlayer;
	private final AtomicBoolean actionRequested;
	private volatile boolean gameDecided = false;
	private int remoteTurn;
	private TurnState remoteTurnState;
	private volatile List<GameAction> remoteValidActions;
	private String host;
	private int port;
	private boolean mulligan;
	private String lastRequestId;
	private ClientConnectionConfiguration connectionConfiguration;
	private ClientCommunicationSend ccs;
	private ClientCommunicationReceive ccr;
	public boolean ignoreEventOverride = false;
	private final SocketClientConnection socketClientConnection;

	public RemoteGameContext(ClientConnectionConfiguration connectionConfiguration) {
		super(connectionConfiguration.getFirstMessage().getPlayer1(), null, new GameLogic(), new DeckFormat().withCardSets(CardSet.BASIC, CardSet.CLASSIC, CardSet.PROCEDURAL_PREVIEW));
		this.connectionConfiguration = connectionConfiguration;
		this.host = connectionConfiguration.getHost();
		this.port = connectionConfiguration.getPort();
		this.socketClientConnection = new SocketClientConnection(host, port);
		this.ccs = socketClientConnection;
		this.ccr = socketClientConnection;
		this.activePlayer = new AtomicInteger();
		this.getActivePlayerAtomic().set(-1);
		this.actionRequested = new AtomicBoolean();
		this.getActionRequested().set(false);
		ccr.RegisterListener(this);
		Thread networkingThread = new Thread(socketClientConnection);
		networkingThread.start();
	}


	protected void setActivePlayer(int id) {
		this.getActivePlayerAtomic().set(id);
	}


	@Override
	protected boolean acceptAction(GameAction nextAction) {
		throw new RuntimeException("should not be called");
	}

	@Override
	public void addTrigger(IGameEventListener trigger) {
		throw new RuntimeException("should not be called");
	}


	@Override
	public GameContext clone() {
		GameContext clone = super.clone();
		clone.setPlayers(new Player[]{getPlayer1().clone(), getPlayer2().clone()});
		return clone;
	}

	@Override
	public void dispose() {
		super.dispose();
		socketClientConnection.kill();
	}

	@Override
	protected void endGame() {
		for (Player player : getPlayers()) {
			player.getBehaviour().onGameOver(this, player.getId(), getWinner() != null ? getWinner().getId() : -1);
		}

		calculateStatistics();
	}

	@Override
	public void endTurn() {
		//do nothing;
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		throw new RuntimeException("should not be called");
	}

	private void addGameEvent(GameEvent gameEvent) {
		getGameEvents().add(gameEvent);
	}

	public synchronized List<GameEvent> getGameEvents() {
		return gameEvents;
	}

	@Override
	public Stack<EntityReference> getSummonReferenceStack() {
		throw new RuntimeException("should not be called");
	}

	@Override
	public int getTotalMinionCount() {
		throw new RuntimeException("should not be called");
	}

	@Override
	public List<IGameEventListener> getTriggersAssociatedWith(EntityReference entityReference) {
		throw new RuntimeException("should not be called");
	}

	public int getTurn() {
		return getRemoteTurn();
	}

	public TurnState getTurnState() {
		return getRemoteTurnState();
	}

	public List<GameAction> getValidActions() {
		return getRemoteValidActions();
	}


	@Override
	public boolean gameDecided() {
		//TODO: return localGameDecided;
		return gameDecided;
	}


	@Override
	public int getActivePlayerId() {
		//TODO: update this with remote;
		return getActivePlayerAtomic().get();
	}

	@Override
	public GameAction getAutoHeroPowerAction() {
		throw new RuntimeException("should not be called");
	}

	@Override
	public boolean ignoreEvents() {
		//Patch around lack of good notification when testing
		//TODO: refactor this patch
		if (ignoreEventOverride) {
			return true;
		}
		return (!mulligan && getActivePlayerId() == -1);
	}

	@Override
	public void init() {
		ccs.getSendToServer().sendGenericMessage(connectionConfiguration.getFirstMessage());
	}

	@Override
	public GameLogic getLogic() {
		//TODO: FIX
		return super.getLogic();
	}

	@Override
	public void play() {
		logger.debug("Game starts.");
		init();
		logger.debug("Waiting for players to connect...");
		while (getActivePlayerAtomic().get() == -1) {
			try {
				Thread.sleep(BuildConfig.DEFAULT_SLEEP_DELAY);
			} catch (InterruptedException ignored) {
			}
		}
		logger.debug("Players connected, waiting for action.");
		while (!gameDecided()) {
			synchronized (this) {
				if (getActionRequested().get()) {
					logger.debug("Action was requested from player.");
					GameAction action = getLocalPlayer().getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
					ccs.getSendToServer().sendAction(this.lastRequestId, getLocalPlayer(), action);
					getActionRequested().set(false);
				}
			}

		}
		endGame();
	}

	@Override
	public Player getActivePlayer() {
		return super.getPlayer(getActivePlayerAtomic().get());
	}

	public boolean isBlockedByAnimation() {
		return blockedByAnimation;
	}

	public void setBlockedByAnimation(boolean blockedByAnimation) {
		this.blockedByAnimation = blockedByAnimation;
	}

	@Override
	protected void onGameStateChanged() {
		if (ignoreEvents()) {
			return;
		}

		setBlockedByAnimation(true);
		NotificationProxy.sendNotification(GameNotification.GAME_STATE_UPDATE, this);

		while (blockedByAnimation) {
			try {
				Thread.sleep(BuildConfig.DEFAULT_SLEEP_DELAY);
			} catch (InterruptedException ignored) {
			}
		}
		NotificationProxy.sendNotification(GameNotification.GAME_STATE_LATE_UPDATE, this);
	}

	@Override
	public boolean playTurn() {
		throw new RuntimeException("should not be called");
	}

	public AtomicBoolean getActionRequested() {
		return actionRequested;
	}

	public AtomicInteger getActivePlayerAtomic() {
		return activePlayer;
	}

	public TurnState getRemoteTurnState() {
		return remoteTurnState;
	}

	public void setRemoteTurnState(TurnState remoteTurnState) {
		this.remoteTurnState = remoteTurnState;
	}

	public int getRemoteTurn() {
		return remoteTurn;
	}

	public void setRemoteTurn(int remoteTurn) {
		this.remoteTurn = remoteTurn;
	}

	public Player getLocalPlayer() {
		if (localPlayer == null) {
			return getPlayer1();
		} else {
			return localPlayer;
		}
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public List<GameAction> getRemoteValidActions() {
		return remoteValidActions;
	}

	public void setRemoteValidActions(List<GameAction> remoteValidActions) {
		this.remoteValidActions = remoteValidActions;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		this.addGameEvent(event);
		this.onGameStateChanged();
	}

	@Override
	public void onGameEnd(Player w) {
		this.setWinner(w);
		this.gameDecided = true;
		this.onGameStateChanged();
		this.endGame();
	}

	@Override
	public void onActivePlayer(Player ap) {
		logger.debug("ON_ACTIVE_PLAYER " + ap.getId());
		this.setActivePlayer(ap.getId());
		this.onGameStateChanged();
		logger.debug("End active player.");
	}

	@Override
	public void setPlayers(Player lp, Player rp) {
		this.setLocalPlayer(lp);
		this.setPlayer(lp.getId(), lp);
		this.setPlayer(rp.getId(), rp);
	}

	@Override
	public void onUpdate(Player player1, Player player2, TurnState newState) {
		this.setPlayer(GameContext.PLAYER_1, player1);
		this.setPlayer(GameContext.PLAYER_2, player2);
		this.setRemoteTurnState(newState);
		this.onGameStateChanged();

	}

	@Override
	public void onRequestAction(String id, List<GameAction> availableActions) {
		synchronized (this) {
			this.lastRequestId = id;
			this.getActionRequested().set(true);
			this.setRemoteValidActions(availableActions);
			this.onGameStateChanged();
		}
	}

	@Override
	public void onTurnEnd(Player ap, int turnNumber, TurnState turnState) {
		logger.debug("new active player: " + ap.getId());
		this.setActivePlayer(ap.getId());
		this.setRemoteTurn(turnNumber);
		this.setRemoteTurnState(turnState);
		this.onGameStateChanged();
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void onMulligan(String id, Player player, List<Card> cards) {
		mulligan = false;
		List<Card> discardedCards = player.getBehaviour().mulligan(this, player, cards);
		mulligan = true;
		ccs.getSendToServer().sendMulligan(id, player, discardedCards);
	}
}
