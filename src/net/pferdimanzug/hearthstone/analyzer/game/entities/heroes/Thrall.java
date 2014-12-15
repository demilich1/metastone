package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.TotemicCall;

public class Thrall extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Thrall", HeroClass.SHAMAN);
	}

	public Thrall() {
		super("Thrall", HeroClass.SHAMAN, new TotemicCall());
	}
	
}
