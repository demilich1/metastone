package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TurnStartEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class TurnStartTrigger extends GameEventTrigger {

	public TurnStartTrigger() {
		this(EventTriggerDesc.createEmpty(TurnStartTrigger.class));
	}

	public TurnStartTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		TurnStartEvent turnStartEvent = (TurnStartEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return turnStartEvent.getPlayer() != getOwner();
		case SELF:
		case OWNER:
			return turnStartEvent.getPlayer() == getOwner();
		default:
			break;
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_START;
	}

}
