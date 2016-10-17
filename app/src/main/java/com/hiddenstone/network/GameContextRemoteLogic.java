package com.hiddenstone.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.gui.playmode.GameContextVisuals;

public class GameContextRemoteLogic extends GameContext implements GameContextVisuals {
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
		SocketClientCommunication scc = new SocketClientCommunication();
		this.ccs = scc;
		this.ccr = scc;
		new Thread(scc).start();
		
		this.activePlayer = new AtomicInteger();
		this.activePlayer.set(-1);
		this.actionRequested = new AtomicBoolean();
		this.actionRequested.set(false);
		
		ccr.RegisterListener(new RemoteUpdateListener(){

			@Override
			public void onGameEvent(GameEvent event) {
				addGameEvent(event);
				onGameStateChanged();
			}

			@Override
			public void onGameEnd(Player w) {
				winner = w;
				onGameStateChanged();
				endGame();
			}

			@Override
			public void onActivePlayer(Player ap) {
				System.out.println("onActivePlayer" + ap.getId());
				setActivePlayer(ap.getId());
				onGameStateChanged();
			}
			
			@Override
			public void setPlayers(Player lp, Player rp){
				localPlayer = lp;
				players[lp.getId()] = lp;
				players[rp.getId()] = rp;
			}

			@Override
			public void onUpdate(Player player1, Player player2, TurnState newState) {			
				players[PLAYER_1] = player1;
				players[PLAYER_2] = player2;
				remoteTurnState = newState;
				onGameStateChanged();

			}

			@Override
			public void onRequestAction(List<GameAction> availableActions) {
				actionRequested.set(true);
				remoteValidActions = availableActions;
				onGameStateChanged();
			}

			@Override
			public void onTurnEnd(Player ap, int turnNumber, TurnState turnState) {
				System.out.println("new active player: " + ap.getId());
				setActivePlayer(ap.getId());
				remoteTurn = turnNumber;
				remoteTurnState = turnState;
				onGameStateChanged();
				
			}
		});
	}
	
	
	protected void setActivePlayer(int id) {
		this.activePlayer.set(id);
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
			player.getBehaviour().onGameOver(this, player.getId(), winner != null ? winner.getId() : -1);
		}

		if (winner != null) {
			logger.debug("Game finished after " + getTurn() + " turns, the winner is: " + winner.getName());
			winner.getStatistics().gameWon();
			Player looser = getOpponent(winner);
			looser.getStatistics().gameLost();
		} else {
			logger.debug("Game finished after " + getTurn()  + " turns, DRAW");
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
	
	public void addGameEvent(GameEvent gameEvent){
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
		return remoteTurn;
	}

	public TurnState getTurnState() {
		return remoteTurnState;
	}

	public List<GameAction> getValidActions() {
		return remoteValidActions;
	}

	
	@Override
	public boolean gameDecided() {
		//TODO: return localGameDecided;
		return gameDecided;
	}
	

	@Override
	public int getActivePlayerId() {
		//TODO: update this with remote;
		return activePlayer.get();
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
		while(activePlayer.get() == -1){
			//Busy wait
		}
		System.out.println("inGame");
		while (!gameDecided()) {
			if (actionRequested.get()){
				System.out.println("actionRequested");
				GameAction action = localPlayer.getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
				ccs.getSendToServer().registerAction(localPlayer, action);
				actionRequested.set(false);
			}
		}
		endGame();
	}
	
	@Override
	public Player getActivePlayer() {
		return super.getPlayer(activePlayer.get());
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

}
