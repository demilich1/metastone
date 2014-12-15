package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.LifeTap;

public class Guldan extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Gul'dan", HeroClass.WARLOCK);
	}

	public Guldan() {
		super("Gul'dan", HeroClass.WARLOCK, new LifeTap());
	}

}
