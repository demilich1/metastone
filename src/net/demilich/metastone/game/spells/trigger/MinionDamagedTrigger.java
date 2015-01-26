package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;

public class MinionDamagedTrigger extends GameEventTrigger {
	
	private TargetPlayer targetPlayer;

	public MinionDamagedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		if (damageEvent.getVictim().getEntityType() != EntityType.MINION) {
			return false;
		}
		
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return damageEvent.getVictim().getOwner() != getOwner();
		case SELF:
			return damageEvent.getVictim().getOwner() == getOwner();
		}
		
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
