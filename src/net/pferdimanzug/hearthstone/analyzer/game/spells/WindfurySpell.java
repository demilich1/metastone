package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.RemoveWindfurySpell;

public class WindfurySpell extends ApplyTagSpell {
	
	public WindfurySpell() {
		this(null);
	}

	public WindfurySpell(GameEventTrigger removeTrigger) {
		super(GameTag.WINDFURY, removeTrigger);
	}

	@Override
	protected Spell getReverseSpell() {
		return new RemoveWindfurySpell();
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (target.hasTag(GameTag.WINDFURY)) {
			return;
		}
		target.modifyTag(GameTag.NUMBER_OF_ATTACKS, +1);
		super.onCast(context, player, target);
	}
	
	

}
