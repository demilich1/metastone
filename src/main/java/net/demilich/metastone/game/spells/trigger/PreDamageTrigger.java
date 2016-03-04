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
		PreDamageEvent preDamageEvent = (PreDamageEvent) event;
		
		EntityType sourceEntityType = (EntityType) desc.get(EventTriggerArg.SOURCE_ENTITY_TYPE);
		if (sourceEntityType != null && preDamageEvent.getSource().getEntityType() != sourceEntityType) {
			return false;
		}
		
		EntityType targetEntityType = (EntityType) desc.get(EventTriggerArg.TARGET_ENTITY_TYPE);
		if (targetEntityType != null && preDamageEvent.getVictim().getEntityType() != targetEntityType) {
			return false;
		}
		
		TargetPlayer sourcePlayer = desc.getSourcePlayer();
		//int sourcePlayerId = preDamageEvent.getSource().getOwner();
		if (sourcePlayer != null) {
			return determineTargetPlayer(preDamageEvent, sourcePlayer, host, getOwner());
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PRE_DAMAGE;
	}

}
