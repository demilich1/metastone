package net.pferdimanzug.hearthstone.analyzer.game.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Reinforce;

public class Uther extends Hero {

	public Uther() {
		super("Uther the Lightbringer", HeroClass.PALADIN, new Reinforce());
	}

}
