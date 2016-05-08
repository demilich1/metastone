package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DrawCardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int cardCount = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		for (int i = 0; i < cardCount; i++) {
			context.getLogic().drawCard(player.getId(), source);
		}
	}
}
