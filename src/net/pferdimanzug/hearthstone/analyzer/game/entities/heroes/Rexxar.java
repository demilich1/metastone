package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.SteadyShot;

public class Rexxar extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Rexxar", HeroClass.HUNTER);
	}

	public Rexxar() {
		super("Rexxar", HeroClass.HUNTER, new SteadyShot());
	}
	

}
