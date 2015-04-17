package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SpellUtils;

public class RaceOnBoardCondition extends Condition {

	public RaceOnBoardCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc) {
		Race race = (Race) desc.get(ConditionArg.RACE);
		return SpellUtils.hasMinionOfRace(player, race);
	}

}
