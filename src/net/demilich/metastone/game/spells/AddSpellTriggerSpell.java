package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class AddSpellTriggerSpell extends Spell {
	
	public static SpellDesc create(SpellTrigger trigger) {
		SpellDesc desc = new SpellDesc(AddSpellTriggerSpell.class);
		desc.set(SpellArg.SPELL_TRIGGER, trigger);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		SpellTrigger spellTrigger = (SpellTrigger) desc.get(SpellArg.SPELL_TRIGGER);
		context.getLogic().addGameEventListener(player, spellTrigger, target);
	}

}
