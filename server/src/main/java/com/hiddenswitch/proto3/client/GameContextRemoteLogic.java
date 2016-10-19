package com.hiddenswitch.proto3.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.hiddenswitch.proto3.common.*;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.visuals.GameContextVisuals;

public class GameContextRemoteLogic extends GameContext implements GameContextVisuals, RemoteUpdateListener {
	private final List<GameEvent> gameEvents = new ArrayList<>();
	private boolean blockedByAnimation;
	private Player localPlayer;
	private final AtomicInteger activePlayer;
	private final AtomicBoolean actionRequested;
	private boolean gameDecided = false;
	private int remoteTurn;
	private TurnState remoteTurnState;
	private volatile List<GameAction> remoteValidActions;
	ClientCommunicationSend ccs;
	ClientCommunicationReceive ccr;

	public GameContextRemoteLogic(Player player1, Player player2, DeckFormat df) {
		super(player1, player2, new ProceduralGameLogic(), df);
		SocketClientConnection scc = new SocketClientConnection();
		this.ccs = scc;
		this.ccr = scc;
		new Thread(scc).start();

		this.activePlayer = new AtomicInteger();
		this.getActivePlayerAtomic().set(-1);
		this.actionRequested = new AtomicBoolean();
		this.getActionRequested().set(false);

		ccr.RegisterListener(this);
	}


	protected void setActivePlayer(int id) {
		this.getActivePlayerAtomic().set(id);
	}


	@Override
	protected boolean acceptAction(GameAction nextAction) {
		throw new RuntimeException("should not be called");
	}

	@Override
	public void addCardCostModifier(CardCostModifier cardCostModifier) {
		throw new RuntimeException("should not be called");
	}

	@Override
	public void addTrigger(IGameEventListener trigger) {
		throw new RuntimeException("should not be called");
	}


	@Override
	public GameContext clone() {
		Player player1Clone = getPlayer1().clone();
		// player1Clone.getDeck().shuffle();
		Player player2Clone = getPlayer2().clone();
		// player2Clone.getDeck().shuffle();
		GameContext clone = new GameContextRemoteLogic(player1Clone, player2Clone, getDeckFormat());
		return clone;
	}

	@Override
	public void dispose() {
		throw new RuntimeException("should not be called");
	}

	@Override
	protected void endGame() {
		for (Player player : getPlayers()) {
			player.getBehaviour().onGameOver(this, player.getId(), getWinner() != null ? getWinner().getId() : -1);
		}

		if (getWinner() != null) {
			logger.debug("Game finished after " + getTurn() + " turns, the winner is: " + getWinner().getName());
			getWinner().getStatistics().gameWon();
			Player looser = getOpponent(getWinner());
			looser.getStatistics().gameLost();
		} else {
			logger.debug("Game finished after " + getTurn() + " turns, DRAW");
			getPlayer1().getStatistics().gameLost();
			getPlayer2().getStatistics().gameLost();
		}
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
		return (getActivePlayerId() == -1);
	}

	@Override
	public void init() {

		ccs.getSendToServer().registerPlayer(getPlayer1(), getPlayer2());
	}

	@Override
	public GameLogic getLogic() {
		//TODO: FIX
		return super.getLogic();
		//throw new RuntimeException("should not be called");
	}

	@Override
	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		init();
		System.out.println("waiting");
		while (getActivePlayerAtomic().get() == -1) {
			//Busy wait
		}
		System.out.println("inGame");
		while (!gameDecided()) {
			if (getActionRequested().get()) {
				System.out.println("actionRequested");
				GameAction action = getLocalPlayer().getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
				ccs.getSendToServer().registerAction(getLocalPlayer(), action);
				getActionRequested().set(false);
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
			} catch (InterruptedException e) {
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
		return localPlayer;
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
		this.onGameStateChanged();
		this.endGame();
	}

	@Override
	public void onActivePlayer(Player ap) {
		System.out.println("ON_ACTIVE_PLAYER" + ap.getId());
		this.setActivePlayer(ap.getId());
		this.onGameStateChanged();
	}

	@Override
	public void setPlayers(Player lp, Player rp) {
		this.setLocalPlayer(lp);
		this.getPlayers()[lp.getId()] = lp;
		this.getPlayers()[rp.getId()] = rp;
	}

	@Override
	public void onUpdate(Player player1, Player player2, TurnState newState) {
		this.getPlayers()[GameContext.PLAYER_1] = player1;
		this.getPlayers()[GameContext.PLAYER_2] = player2;
		this.setRemoteTurnState(newState);
		this.onGameStateChanged();

	}

	@Override
	public void onRequestAction(List<GameAction> availableActions) {
		this.getActionRequested().set(true);
		this.setRemoteValidActions(availableActions);
		this.onGameStateChanged();
	}

	@Override
	public void onTurnEnd(Player ap, int turnNumber, TurnState turnState) {
		System.out.println("new active player: " + ap.getId());
		this.setActivePlayer(ap.getId());
		this.setRemoteTurn(turnNumber);
		this.setRemoteTurnState(turnState);
		this.onGameStateChanged();
	}
}
