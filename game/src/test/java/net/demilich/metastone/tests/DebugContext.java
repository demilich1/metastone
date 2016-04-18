package net.demilich.metastone.tests;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;

public class DebugContext extends GameContext {

	public DebugContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		super(player1, player2, logic, deckFormat);
	}

	@Override
	public void init() {
		activePlayer = getPlayer(PLAYER_1).getId();
		getLogic().init(activePlayer, true);
		getLogic().init(getOpponent(getActivePlayer()).getId(), false);
	}

	public void setActivePlayer(int playerId) {
		this.activePlayer = playerId;
	}

}
