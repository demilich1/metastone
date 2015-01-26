package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.LifeTap;

public class Guldan extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Gul'dan", HeroClass.WARLOCK);
	}

	public Guldan() {
		super("Gul'dan", HeroClass.WARLOCK, new LifeTap());
	}

}
