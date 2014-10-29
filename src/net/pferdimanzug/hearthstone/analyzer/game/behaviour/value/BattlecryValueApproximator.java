package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
