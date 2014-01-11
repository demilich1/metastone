package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class AddSpellTriggerSpell implements ISpell {
	
	private SpellTrigger spellTrigger;

	public AddSpellTriggerSpell(SpellTrigger spellTrigger) {
		this.spellTrigger = spellTrigger;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		spellTrigger.setOwner(player);
		target.addSpellTrigger(spellTrigger);
		context.getEventManager().registerGameEventListener(spellTrigger);
	}

}
