package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.HeroPowerUsedEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HeroPowerUsedTrigger extends GameEventTrigger {

	public HeroPowerUsedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		HeroPowerUsedEvent heroPowerEvent = (HeroPowerUsedEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
		case OWNER:
			return heroPowerEvent.getPlayerId() == getOwner();
		case OPPONENT:
			return heroPowerEvent.getPlayerId() != getOwner();
		case ACTIVE:
			return heroPowerEvent.getPlayerId() == event.getGameContext().getActivePlayerId();
		case INACTIVE:
			return heroPowerEvent.getPlayerId() != event.getGameContext().getActivePlayerId();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.HERO_POWER_USED;
	}

}
