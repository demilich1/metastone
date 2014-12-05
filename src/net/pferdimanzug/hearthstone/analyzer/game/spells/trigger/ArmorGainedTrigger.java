package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.ArmorGainedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class ArmorGainedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		ArmorGainedEvent armorGainedEvent = (ArmorGainedEvent) event;
		return armorGainedEvent.getPlayerId() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.ARMOR_GAINED;
	}

}
