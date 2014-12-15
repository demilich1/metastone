package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.DaggerMastery;

public class Valeera extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Valeera Sanguinar", HeroClass.ROGUE);
	}

	public Valeera() {
		super("Valeera Sanguinar", HeroClass.ROGUE, new DaggerMastery());
	}

}
