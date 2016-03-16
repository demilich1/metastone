package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class AndCondition extends Condition {

	public AndCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		Condition[] conditions = (Condition[]) desc.get(ConditionArg.CONDITIONS);
		for (Condition condition : conditions) {
			if (!condition.isFulfilled(context, player, target)) {
				return false;
			}
		}
		return true;
	}

}
