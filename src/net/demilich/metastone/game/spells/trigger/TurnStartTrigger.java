package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TurnStartEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

public class TurnStartTrigger extends GameEventTrigger {
	
	private final TargetPlayer targetPlayer;
	
	public TurnStartTrigger() {
		this(TargetPlayer.SELF);
	}
	
	public TurnStartTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}
	
	@Override
	public boolean fire(GameEvent event, Entity host) {
		TurnStartEvent turnStartEvent = (TurnStartEvent) event;
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return turnStartEvent.getPlayer() != getOwner();
		case SELF:
			return turnStartEvent.getPlayer() == getOwner();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_START;
	}

}
