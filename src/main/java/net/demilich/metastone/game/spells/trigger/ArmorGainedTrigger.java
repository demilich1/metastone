package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.ArmorGainedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class ArmorGainedTrigger extends GameEventTrigger {

	public ArmorGainedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		ArmorGainedEvent armorGainedEvent = (ArmorGainedEvent) event;
		return armorGainedEvent.getPlayerId() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.ARMOR_GAINED;
	}

}
