package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public interface IBattlecryCondition {
	
	boolean isFulfilled(GameContext context, Player player);

}
