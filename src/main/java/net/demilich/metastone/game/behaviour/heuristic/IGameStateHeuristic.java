package net.demilich.metastone.game.behaviour.heuristic;

import net.demilich.metastone.game.GameContext;

public interface IGameStateHeuristic {

	double getScore(GameContext context, int playerId);

	void onActionSelected(GameContext context, int playerId);
}
