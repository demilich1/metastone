package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.kms.model.UnsupportedOperationException;
import com.hiddenswitch.proto3.net.amazon.GameContextConverter;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;

import java.util.HashMap;

@DynamoDBDocument
public class Game {
	private GameType type = GameType.CONVENTIONAL;
	private GamePlayer gamePlayer1 = null;
	private GamePlayer gamePlayer2 = null;

	private transient HashMap<String, GamePlayer> idToPlayer = new HashMap<>(2);

	@DynamoDBIgnore
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

	@DynamoDBIgnore
	public GamePlayer getPlayerForId(String id) {
		return idToPlayer.getOrDefault(id, null);
	}

	@DynamoDBTypeConvertedEnum
	public GameType getType() {
		return type;
	}

	private void idToPlayerPut(GamePlayer gamePlayer) {
		if (gamePlayer == null) {
			return;
		}

		idToPlayer.put(gamePlayer.getUserId(), gamePlayer);
	}

	public void setGamePlayer1(GamePlayer gamePlayer1) {
		this.gamePlayer1 = gamePlayer1;
		idToPlayerPut(gamePlayer1);
	}

	public void setGamePlayer2(GamePlayer gamePlayer2) {
		this.gamePlayer2 = gamePlayer2;
		idToPlayerPut(gamePlayer2);
	}

	@DynamoDBAttribute
	public GamePlayer getGamePlayer1() {
		return gamePlayer1;
	}

	@DynamoDBAttribute
	public GamePlayer getGamePlayer2() {
		return gamePlayer2;
	}

	/**
	 * Sets the next null gamePlayer to the provided gamePlayer. If the game is ready after
	 * setting the gamePlayer, the initial game context is set.
	 *
	 * @param gamePlayer
	 * @throws UnsupportedOperationException
	 */
	public void setNullPlayer(GamePlayer gamePlayer) throws UnsupportedOperationException {
		if (this.gamePlayer1 == null) {
			setGamePlayer1(gamePlayer);
		} else if (this.gamePlayer2 == null) {
			setGamePlayer2(gamePlayer);
		} else {
			throw new UnsupportedOperationException("Cannot set null player!");
		}
	}

	@DynamoDBIgnore
	public boolean isReadyToPlay() {
		return getPlayerCount() == 2;
	}

	public void setType(GameType type) {
		this.type = type;
	}
}

