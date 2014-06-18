package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class AddSpellTriggerSpell extends Spell {
	
	private SpellTrigger spellTrigger;

	public AddSpellTriggerSpell(SpellTrigger spellTrigger) {
		this.spellTrigger = spellTrigger;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().addSpellTrigger(player, spellTrigger, target);
	}

}
