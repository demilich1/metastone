package net.pferdimanzug.hearthstone.analyzer.game.spells.filter;

import java.util.function.Predicate;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

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
