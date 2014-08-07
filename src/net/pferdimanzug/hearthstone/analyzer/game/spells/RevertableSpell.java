package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class RevertableSpell extends Spell {

	protected abstract SpellDesc getReverseSpell(SpellDesc desc);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		GameEventTrigger revertTrigger = (GameEventTrigger) desc.get(SpellArg.REVERT_TRIGGER);
		GameEventTrigger secondRevertTrigger = (GameEventTrigger) desc.get(SpellArg.SECOND_REVERT_TRIGGER);
		if (revertTrigger != null) {
			SpellDesc revert = getReverseSpell(desc);
			revert.setTarget(target.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(revertTrigger, secondRevertTrigger, revert, true);
			context.getLogic().addGameEventListener(player, removeTrigger, target);
		}
	}

}
