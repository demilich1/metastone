package net.demilich.metastone.game.entities;

import java.util.function.Predicate;

import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class EntityRaceFilter implements Predicate<Entity> {

	private final Race race;

	public EntityRaceFilter(Race race) {
		this.race = race;
	}

	@Override
	public boolean test(Entity entity) {
		if (entity.getEntityType() != EntityType.MINION) {
			return false;
		}
		Minion minion = (Minion) entity;
		return minion.getRace() == race;
	}

}
