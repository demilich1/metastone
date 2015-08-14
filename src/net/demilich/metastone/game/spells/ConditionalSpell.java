package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class ConditionalSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		if (condition.isFulfilled(context, player, target)) {
			SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
			SpellUtils.castChildSpell(context, player, spell, source, target);
		}
	}

}
