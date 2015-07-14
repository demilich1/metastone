package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public abstract class Condition {
	
	private final ConditionDesc desc;

	public Condition(ConditionDesc desc) {
		this.desc = desc;
	}
	
	protected abstract boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target);
	
	public boolean isFulfilled(GameContext context, Player player, Entity target) {
		boolean invert = desc.getBool(ConditionArg.INVERT);
		return isFulfilled(context, player, desc, target) != invert;
	}

}
