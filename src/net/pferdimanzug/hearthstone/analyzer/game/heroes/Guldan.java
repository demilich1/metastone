package net.pferdimanzug.hearthstone.analyzer.game.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.LifeTap;

public class Guldan extends Hero {

	public Guldan() {
		super("Gul'dan", HeroClass.WARLOCK, new LifeTap());
	}

}
