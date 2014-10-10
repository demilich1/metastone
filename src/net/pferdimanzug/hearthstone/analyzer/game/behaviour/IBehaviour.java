package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public interface IBehaviour extends Cloneable {

	IBehaviour clone();

	String getName();

	List<Card> mulligan(GameContext context, Player player, List<Card> cards);
	
	void onGameOver(GameContext context, int playerId, int winningPlayerId);
	
	GameAction requestAction(GameContext context, Player player, List<GameAction> validActions);
}
