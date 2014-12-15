package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.LesserHeal;

public class Anduin extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Anduin Wrynn", HeroClass.PRIEST);
	}

	public Anduin() {
		super("Anduin Wrynn", HeroClass.PRIEST, new LesserHeal());
	}

	

}
