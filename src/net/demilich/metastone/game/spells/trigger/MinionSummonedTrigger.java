package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class MinionSummonedTrigger extends GameEventTrigger {

	public MinionSummonedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		SummonEvent summonEvent = (SummonEvent) event;
		Race race = (Race) desc.get(EventTriggerArg.RACE);
		if (race != null && summonEvent.getMinion().getRace() != race) {
			return false;
		}
		
		GameTag requiredAttribute = (GameTag) desc.get(EventTriggerArg.REQUIRED_ATTRIBUTE);
		if (requiredAttribute != null && !summonEvent.getMinion().hasTag(requiredAttribute)) {
			return false;
		}
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return summonEvent.getMinion().getOwner() != getOwner();
		case SELF:
			return summonEvent.getMinion().getOwner() == getOwner();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SUMMON;
	}

}
