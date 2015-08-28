package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public interface ISpellConditionChecker {

	boolean isFulfilled(GameContext context, Player player, Entity target);

}
