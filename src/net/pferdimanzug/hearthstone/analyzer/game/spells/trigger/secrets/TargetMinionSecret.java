package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TargetMinionSecret extends Secret {

	public TargetMinionSecret(SecretTrigger trigger, Spell spell, Card source) {
		super(trigger, spell, source);
	}
	
	@Override
	protected EntityReference getTargetForSpell(GameEvent event) {
		SummonEvent summonEvent = (SummonEvent) event;
		return summonEvent.getMinion().getReference();
	}

}
