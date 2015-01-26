package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class BattlecryValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		Battlecry battlecry = (Battlecry) action;
		SpellDesc spellDesc = battlecry.getSpell();
		Entity target = null;
		if (spellDesc.getSpellClass().isAssignableFrom(SilenceSpell.class)) {
			target = context.resolveSingleTarget(action.getTargetKey());
			return Values.signHarmful(target, playerId) * Values.getSilenceScore(context, (Minion) target);
		}
		return 0;
	}

}
