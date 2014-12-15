package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Shapeshift;

public class Malfurion extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Malfurion Stormrage", HeroClass.DRUID);
	}

	public Malfurion() {
		super("Malfurion Stormrage", HeroClass.DRUID, new Shapeshift());
	}

}
