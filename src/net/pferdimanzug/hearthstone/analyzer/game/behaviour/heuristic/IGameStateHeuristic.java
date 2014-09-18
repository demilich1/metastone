package net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public interface IGameStateHeuristic {

	double getScore(GameContext context, int playerId);
	
	void onActionSelected(GameContext context, int playerId);
}
