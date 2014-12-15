package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Fireblast;

public class Jaina extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Jaina Proudmoore", HeroClass.MAGE);
	}

	public Jaina() {
		super("Jaina Proudmoore", HeroClass.MAGE, new Fireblast());
	}

	

}
