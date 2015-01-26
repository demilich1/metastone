package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.TotemicCall;

public class Thrall extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Thrall", HeroClass.SHAMAN);
	}

	public Thrall() {
		super("Thrall", HeroClass.SHAMAN, new TotemicCall());
	}
	
}
