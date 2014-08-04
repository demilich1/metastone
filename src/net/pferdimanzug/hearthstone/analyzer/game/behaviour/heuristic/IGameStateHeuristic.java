package net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public interface IGameStateHeuristic {

	int getScore(GameContext context, int playerId);
}
