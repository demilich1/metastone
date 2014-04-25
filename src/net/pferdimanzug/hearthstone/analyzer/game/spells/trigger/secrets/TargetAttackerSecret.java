package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TargetAttackerSecret extends Secret {

	public TargetAttackerSecret(SecretTrigger trigger, Spell spell, Card source) {
		super(trigger, spell, source);
	}

	@Override
	protected EntityReference getTargetForSpell(GameEvent event) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getAttacker().getReference();
	}
	
	

}
