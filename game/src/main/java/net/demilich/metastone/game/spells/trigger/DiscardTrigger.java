package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DiscardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscardTrigger extends GameEventTrigger {

	public DiscardTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DiscardEvent discardEvent = (DiscardEvent) event;
		EntityReference target = (EntityReference) desc.get(EventTriggerArg.TARGET);

		final int owner = host.getOwner();
		if (owner == -1) {
			return false;
		}

		List<Entity> resolvedTargets = event.getGameContext().resolveTarget(event.getGameContext().getPlayer(owner), host, target);

		if (resolvedTargets == null) {
			return false;
		}

		return resolvedTargets.stream().anyMatch(e -> e.getId() == discardEvent.getCard().getId());
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DISCARD;
	}

}
