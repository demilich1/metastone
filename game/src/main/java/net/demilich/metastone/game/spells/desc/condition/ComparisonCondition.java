package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.filter.Operation;

public class ComparisonCondition extends Condition {

	public ComparisonCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		int value1 = desc.getValue(ConditionArg.VALUE1, context, player, target, null, 0);
		int value2 = desc.getValue(ConditionArg.VALUE2, context, player, target, null, 0);
		Operation operation = (Operation) desc.get(ConditionArg.OPERATION);
		return SpellUtils.evaluateOperation(operation, value1, value2);
	}

}
