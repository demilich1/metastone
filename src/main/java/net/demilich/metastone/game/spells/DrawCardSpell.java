package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;

public class DrawCardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int numberOfCards = desc.getInt(SpellArg.VALUE, 1);
		ValueProvider drawModifier = desc.getValueProvider();
		int cardCount = drawModifier != null ? drawModifier.getValue(context, player, null, source) : numberOfCards;
		for (int i = 0; i < cardCount; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}
}
