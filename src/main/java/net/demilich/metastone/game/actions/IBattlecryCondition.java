package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;

public interface IBattlecryCondition {

	boolean isFulfilled(GameContext context, Player player);

}
