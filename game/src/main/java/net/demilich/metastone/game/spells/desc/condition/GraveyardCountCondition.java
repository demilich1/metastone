package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.filter.Operation;

public class GraveyardCountCondition extends Condition {

	public GraveyardCountCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		int count = 0;
		for (Entity deadEntity : player.getGraveyard()) {
			if (deadEntity instanceof Minion) {
				count++;
			} else if (deadEntity instanceof Card) {
				continue;
			}
		}
		int targetValue = desc.getInt(ConditionArg.VALUE);
		Operation operation = (Operation) desc.get(ConditionArg.OPERATION);
		return SpellUtils.evaluateOperation(operation, count, targetValue);
	}

}
