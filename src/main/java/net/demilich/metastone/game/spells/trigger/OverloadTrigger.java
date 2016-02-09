package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.OverloadEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class OverloadTrigger extends GameEventTrigger {

	public OverloadTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		OverloadEvent overloadEvent = (OverloadEvent) event;
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		int targetPlayerId = overloadEvent.getPlayerId();
		if (targetPlayer != null) {
			return determineTargetPlayer(overloadEvent, targetPlayer, host, targetPlayerId);
		}

		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.OVERLOAD;
	}

}