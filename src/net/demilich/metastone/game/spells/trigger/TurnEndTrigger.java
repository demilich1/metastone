package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TurnEndEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TurnEndTrigger extends GameEventTrigger {
	
	private static Logger logger = LoggerFactory.getLogger(TurnEndTrigger.class);

	private final TargetPlayer targetPlayer;

	public TurnEndTrigger() {
		this(TargetPlayer.SELF);
	}

	public TurnEndTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		TurnEndEvent turnEndEvent = (TurnEndEvent) event;
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return turnEndEvent.getPlayer() != getOwner();
		case SELF:
			return turnEndEvent.getPlayer() == getOwner();
		}
		
		logger.warn("Invalid TargetPlayer type: " + targetPlayer);
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_END;
	}

}
