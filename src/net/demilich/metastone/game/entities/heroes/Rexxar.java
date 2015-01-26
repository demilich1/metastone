package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.SteadyShot;

public class Rexxar extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Rexxar", HeroClass.HUNTER);
	}

	public Rexxar() {
		super("Rexxar", HeroClass.HUNTER, new SteadyShot());
	}
	

}
