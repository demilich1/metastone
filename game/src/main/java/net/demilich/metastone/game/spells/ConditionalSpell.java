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
		// case 1 - only one condition
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		if (condition != null) {
			SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL);
			if (condition.isFulfilled(context, player, source, target)) {
				SpellUtils.castChildSpell(context, player, spell, source, target);
			}
			return;
		}
		
		// case 2 - multiple conditions and multiple spells
		Condition[] conditions = (Condition[]) desc.get(SpellArg.CONDITIONS);
		SpellDesc[] spells = (SpellDesc[]) desc.get(SpellArg.SPELLS);
		for (int i = 0; i < conditions.length; i++) {
			if (conditions[i].isFulfilled(context, player, source, target)) {
				SpellUtils.castChildSpell(context, player, spells[i], source, target);	
			}
		}
	}

}
