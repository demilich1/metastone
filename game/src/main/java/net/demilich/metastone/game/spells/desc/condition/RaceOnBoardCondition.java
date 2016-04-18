package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class RaceOnBoardCondition extends Condition {

	public RaceOnBoardCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		Race race = (Race) desc.get(ConditionArg.RACE);
		int value = desc.contains(ConditionArg.VALUE) ? desc.getInt(ConditionArg.VALUE) : 1;

		int count = 0;
		for (Minion minion : player.getMinions()) {
			if (minion.getRace() == race && !context.getSummonReferenceStack().contains(minion.getReference())) {
				count++;
			}
		}

		return count >= value;
	}

}
