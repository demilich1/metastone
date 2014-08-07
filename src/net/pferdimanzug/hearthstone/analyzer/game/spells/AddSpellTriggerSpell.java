package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

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
