package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class DrawCardUntilConditionSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int cardCount = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		for (int i = 0; i < cardCount; i++) {
			Card card = context.getLogic().drawCard(player.getId(), source);
			if (card == null || (condition != null && condition.isFulfilled(context, player, source, card))) {
				return;
			}
		}
	}
}
