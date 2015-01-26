package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

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
