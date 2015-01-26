package net.demilich.metastone.game.entities.heroes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeroFactory {
	public static Hero createHero(HeroClass heroClass) {
		switch (heroClass) {
		case DRUID:
			return new Malfurion();
		case HUNTER:
			return new Rexxar();
		case MAGE:
			return new Jaina();
		case PALADIN:
			return new Uther();
		case PRIEST:
			return new Anduin();
		case ROGUE:
			return new Valeera();
		case SHAMAN:
			return new Thrall();
		case WARLOCK:
			return new Guldan();
		case WARRIOR:
			return new Garrosh();
		default:
			logger.error("Cannot instantiate heroClass {}", heroClass);
			throw new RuntimeException("Cannot instantiate heroClass " + heroClass);
		}
	}

	private final static Logger logger = LoggerFactory.getLogger(HeroFactory.class);

}
