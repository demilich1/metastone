package net.demilich.metastone.game.spells.filter;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;

public class RaceFilter implements Predicate<Entity> {

	private final Race race;

	public RaceFilter(Race race) {
		this.race = race;
	}

	@Override
	public boolean test(Entity entity) {
		return entity.getTag(GameTag.RACE) == race;
	}

}
