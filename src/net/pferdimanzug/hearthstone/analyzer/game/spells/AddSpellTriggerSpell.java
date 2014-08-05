package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class AddSpellTriggerSpell extends Spell {
	
	public AddSpellTriggerSpell(SpellTrigger spellTrigger) {
		setCloneableData(spellTrigger);
	}

	public SpellTrigger getSpellTrigger() {
		return (SpellTrigger) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().addGameEventListener(player, getSpellTrigger(), target);
	}

}
