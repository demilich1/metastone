package net.demilich.metastone.game.behaviour;

import java.util.List;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public interface IBehaviour extends Cloneable {

	IBehaviour clone();

	String getName();

	List<Card> mulligan(GameContext context, Player player, List<Card> cards);

	void onGameOver(GameContext context, int playerId, int winningPlayerId);

	@Suspendable
	GameAction requestAction(GameContext context, Player player, List<GameAction> validActions);
}
