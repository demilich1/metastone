package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PreDamageEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class PreDamageTrigger extends GameEventTrigger {

	public PreDamageTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		EntityType sourceEntityType = (EntityType) desc.get(EventTriggerArg.SOURCE_ENTITY_TYPE);
		EntityType targetEntityType = (EntityType) desc.get(EventTriggerArg.TARGET_ENTITY_TYPE);
		PreDamageEvent preDamageEvent = (PreDamageEvent) event;
		if (sourceEntityType != null && preDamageEvent.getSource().getEntityType() != sourceEntityType) {
			return false;
		}
		if (targetEntityType != null && preDamageEvent.getVictim().getEntityType() != targetEntityType) {
			return false;
		}
		
		if (desc.contains(EventTriggerArg.TARGET_PLAYER)) {
			TargetPlayer targetPlayer = (TargetPlayer) desc.get(EventTriggerArg.TARGET_PLAYER);
			switch (targetPlayer) {
			case OWNER:
			case SELF:
				if (getOwner() != preDamageEvent.getVictim().getOwner()) {
					return false;
				}
				break;
			case OPPONENT:
				if (getOwner() == preDamageEvent.getVictim().getOwner()) {
					return false;
				}
				break;
			default:
				break;
			}
		}

		int owner = preDamageEvent.getSource().getOwner();
		TargetPlayer sourcePlayer = desc.getSourcePlayer();
		switch (sourcePlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return owner != getOwner();
		case SELF:
		case OWNER:
			return owner == getOwner();
		case ACTIVE:
			return owner == event.getGameContext().getActivePlayerId();
		case INACTIVE:
			return owner != event.getGameContext().getActivePlayerId();
		default:
			break;
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PRE_DAMAGE;
	}

}
