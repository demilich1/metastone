package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class MinionOnBoardCondition extends Condition {

	public MinionOnBoardCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(ConditionArg.CARD_FILTER);
		int value = desc.contains(ConditionArg.VALUE) ? desc.getInt(ConditionArg.VALUE) : 1;

		int count = 0;
		for (Summon summon : player.getSummons()) {
			if ((cardFilter == null || cardFilter.matches(context, player, summon)) && !context.getSummonReferenceStack().contains(summon.getReference())) {
				count++;
			}
		}

		return count >= value;
	}

}
