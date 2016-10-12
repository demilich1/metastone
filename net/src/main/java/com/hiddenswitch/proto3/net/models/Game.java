package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

import java.util.HashMap;

public class Game {
	public String id;
	public GameType type = GameType.PROCEDURAL;
	private GameContext context = null;
	private Player player1 = null;
	private Player player2 = null;

	private transient HashMap<String, Player> idToPlayer = new HashMap<>(2);

	public int getPlayerCount() {
		int count = 0;
		if (player1 != null) {
			count++;
		}
		if (player2 != null) {
			count++;
		}
		return count;
	}

	public Player getPlayerForId(String id) {
		return idToPlayer.getOrDefault(id, null);
	}

	private void idToPlayerPut(Player player) {
		if (player == null) {
			return;
		}

		idToPlayer.put(player.userId, player);
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
		idToPlayerPut(player1);
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
		idToPlayerPut(player2);
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	/**
	 * Sets the next null player to the provided player. If the game is ready after
	 * setting the player, the initial game context is set.
	 * @param player
	 * @throws UnsupportedOperationException
	 */
	public void setNullPlayer(Player player) throws UnsupportedOperationException {
		if (this.player1 == null) {
			setPlayer1(player);
		} else if (this.player2 == null) {
			setPlayer2(player);
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

		PlayerConfig playerConfig1 = getPlayer1().getPlayerConfig();
		PlayerConfig playerConfig2 = getPlayer2().getPlayerConfig();

		net.demilich.metastone.game.Player player1 = null;
		net.demilich.metastone.game.Player player2 = null;
		GameLogic logic = null;
		DeckFormat deckFormat = new DeckFormat();
		switch (type) {
			case CONVENTIONAL:
				player1 = new net.demilich.metastone.game.Player(playerConfig1);
				player2 = new net.demilich.metastone.game.Player(playerConfig2);
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

