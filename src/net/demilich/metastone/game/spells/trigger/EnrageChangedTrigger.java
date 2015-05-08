package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class EnrageChangedTrigger extends GameEventTrigger {

	public EnrageChangedTrigger(EventTriggerDesc desc) {
		super(desc);
	}
	
	public EnrageChangedTrigger() {
		this(EventTriggerDesc.createEmpty(EnrageChangedTrigger.class));
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		return event.getEventTarget() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.ENRAGE_CHANGED;
	}

}
