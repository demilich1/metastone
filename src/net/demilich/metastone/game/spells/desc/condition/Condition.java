package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;

public abstract class Condition {
	
	private final ConditionDesc desc;

	public Condition(ConditionDesc desc) {
		this.desc = desc;
	}
	
	public boolean isFulfilled(GameContext context, Player player) {
		return isFulfilled(context, player, desc);
	}
	
	protected abstract boolean isFulfilled(GameContext context, Player player, ConditionDesc desc);

}
