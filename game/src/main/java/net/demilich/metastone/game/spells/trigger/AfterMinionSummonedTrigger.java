package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.AfterSummonEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class AfterMinionSummonedTrigger extends GameEventTrigger {

	public AfterMinionSummonedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		AfterSummonEvent summonEvent = (AfterSummonEvent) event;
		Race race = (Race) desc.get(EventTriggerArg.RACE);
		if (race != null && summonEvent.getMinion().getRace() != race) {
			return false;
		}

		Attribute requiredAttribute = (Attribute) desc.get(EventTriggerArg.REQUIRED_ATTRIBUTE);
		if (requiredAttribute != null && !summonEvent.getMinion().hasAttribute(requiredAttribute)) {
			return false;
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.AFTER_SUMMON;
	}

}
