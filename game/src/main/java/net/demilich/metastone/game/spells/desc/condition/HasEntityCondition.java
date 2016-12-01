package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

public class HasEntityCondition extends Condition {

	public HasEntityCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		EntityReference targetReference = (EntityReference) desc.get(ConditionArg.TARGET);
		EntityFilter filter = (EntityFilter) desc.get(ConditionArg.FILTER);
		for (Entity entity : context.resolveTarget(player, source, targetReference)) {
			if (filter.matches(context, player, entity)) {
				return true;
			}
		}
		return false;
	}

}
