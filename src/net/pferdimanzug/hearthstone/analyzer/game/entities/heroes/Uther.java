package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Reinforce;

public class Uther extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Uther, the Lightbringer", HeroClass.PALADIN);
	}

	public Uther() {
		super("Uther, the Lightbringer", HeroClass.PALADIN, new Reinforce());
	}

}
