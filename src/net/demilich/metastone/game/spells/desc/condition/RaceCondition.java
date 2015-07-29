package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;

/**
 * 
 * This not a programmers nightmare poured into a class; rather a condition if
 * the specified target is of a certain race
 *
 */
public class RaceCondition extends Condition {

	public RaceCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		Race race = (Race) desc.get(ConditionArg.RACE);
		return target.getAttribute(Attribute.RACE) == race;
	}

}
