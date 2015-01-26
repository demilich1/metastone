package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.Shapeshift;

public class Malfurion extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Malfurion Stormrage", HeroClass.DRUID);
	}

	public Malfurion() {
		super("Malfurion Stormrage", HeroClass.DRUID, new Shapeshift());
	}

}
