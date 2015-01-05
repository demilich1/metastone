package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

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
