package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.Fireblast;

public class Jaina extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Jaina Proudmoore", HeroClass.MAGE);
	}

	public Jaina() {
		super("Jaina Proudmoore", HeroClass.MAGE, new Fireblast());
	}

	

}
