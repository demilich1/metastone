package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

import java.util.HashMap;

@DynamoDBDocument
public class Game {
	public GameType type = GameType.PROCEDURAL;
	private GameContext context = null;
	private GamePlayer gamePlayer1 = null;
	private GamePlayer gamePlayer2 = null;

	private transient HashMap<String, GamePlayer> idToPlayer = new HashMap<>(2);

	public int getPlayerCount() {
		int count = 0;
		if (gamePlayer1 != null) {
			count++;
		}
		if (gamePlayer2 != null) {
			count++;
		}
		return count;
	}

	public GamePlayer getPlayerForId(String id) {
		return idToPlayer.getOrDefault(id, null);
	}

	private void idToPlayerPut(GamePlayer gamePlayer) {
		if (gamePlayer == null) {
			return;
		}

		idToPlayer.put(gamePlayer.userId, gamePlayer);
	}

	public void setGamePlayer1(GamePlayer gamePlayer1) {
		this.gamePlayer1 = gamePlayer1;
		idToPlayerPut(gamePlayer1);
	}

	public void setGamePlayer2(GamePlayer gamePlayer2) {
		this.gamePlayer2 = gamePlayer2;
		idToPlayerPut(gamePlayer2);
	}

	public GamePlayer getGamePlayer1() {
		return gamePlayer1;
	}

	public GamePlayer getGamePlayer2() {
		return gamePlayer2;
	}

	/**
	 * Sets the next null gamePlayer to the provided gamePlayer. If the game is ready after
	 * setting the gamePlayer, the initial game context is set.
	 * @param gamePlayer
	 * @throws UnsupportedOperationException
	 */
	public void setNullPlayer(GamePlayer gamePlayer) throws UnsupportedOperationException {
		if (this.gamePlayer1 == null) {
			setGamePlayer1(gamePlayer);
		} else if (this.gamePlayer2 == null) {
			setGamePlayer2(gamePlayer);
			if (getContext() == null) {
				setInitialGameContext();
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public void setInitialGameContext() {
		if (!isReadyToPlay()) {
			throw new UnsupportedOperationException();
		}

		PlayerConfig playerConfig1 = getGamePlayer1().getPlayerConfig();
		PlayerConfig playerConfig2 = getGamePlayer2().getPlayerConfig();

		Player player1 = null;
		Player player2 = null;
		GameLogic logic = null;
		DeckFormat deckFormat = new DeckFormat();
		switch (type) {
			case CONVENTIONAL:
				player1 = new Player(playerConfig1);
				player2 = new Player(playerConfig2);
				logic = new GameLogic();
				// TODO: Add all of the conventional cards to this format.
				deckFormat.addSet(CardSet.BASIC);
				deckFormat.addSet(CardSet.CLASSIC);
				break;
			case PROCEDURAL:
				player1 = new ProceduralPlayer(playerConfig1);
				player2 = new ProceduralPlayer(playerConfig2);
				logic = new ProceduralGameLogic();
				deckFormat.addSet(CardSet.BASIC);
				deckFormat.addSet(CardSet.CLASSIC);
				deckFormat.addSet(CardSet.PROCEDURAL_PREVIEW);
				break;
		}

		GameContext context = new GameContext(player1, player2, logic, deckFormat);

		setContext(context);
	}

	public boolean isReadyToPlay() {
		return getPlayerCount() == 2;
	}

	public GameContext getContext() {
		return context;
	}

	public void setContext(GameContext context) {
		this.context = context;
	}
}

