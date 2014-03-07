package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AttackTrigger extends SpellTrigger {

	public AttackTrigger(Spell spell) {
		super(new PhysicalAttackTrigger(), spell);
	}

	@Override
	protected EntityReference getTargetForSpell(IGameEvent event) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getDefender().getReference();
	}
}
