package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class ManaCostCondition extends Condition {

	public ManaCostCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		if (!(target instanceof Card)) {
			return false;
		}

		Card card = (Card) target;
		int value = desc.getInt(ConditionArg.VALUE);
		return context.getLogic().getModifiedManaCost(player, card) == value;
	}

}
