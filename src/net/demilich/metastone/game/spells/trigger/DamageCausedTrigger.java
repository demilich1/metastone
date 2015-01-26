package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;

public class DamageCausedTrigger extends GameEventTrigger {

	private final boolean worksOnHeroes;

	public DamageCausedTrigger(boolean worksOnHeroes) {
		this.worksOnHeroes = worksOnHeroes;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		if (damageEvent.getSource() == null) {
			return false;
		}
		if (!worksOnHeroes && damageEvent.getVictim().getEntityType() == EntityType.HERO) {
			return false;
		}
		return damageEvent.getSource() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
