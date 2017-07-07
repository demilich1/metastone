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
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(EventTriggerArg.TARGET_PLAYER);


		final int owner = host.getOwner();

		boolean targetPlayerSatisfied = targetPlayer == null
				|| (targetPlayer == TargetPlayer.SELF && owner == event.getTargetPlayerId())
				|| (targetPlayer == TargetPlayer.OWNER && owner == event.getTargetPlayerId());

		boolean targetSatisfied = target == null;

		if (target != null) {
			List<Entity> resolvedTargets = event.getGameContext().resolveTarget(event.getGameContext().getPlayer(owner), host, target);
			targetSatisfied = resolvedTargets != null && resolvedTargets.stream().anyMatch(e -> e.getId() == discardEvent.getCard().getId());
		}

		return targetPlayerSatisfied && targetSatisfied;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DISCARD;
	}

}
