package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class SummonCopySpell extends Spell {
	
	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion template = (Minion) target;
		Minion clone = template.clone();
		clone.setSpellTrigger(null);
		
		context.getLogic().summon(player.getId(), clone, null, null, false);
		for (SpellTrigger trigger : context.getTriggersAssociatedWith(template.getReference())) {
			SpellTrigger triggerClone = trigger.clone();
			context.getLogic().addSpellTrigger(player, triggerClone, clone);
		}
	}

}
