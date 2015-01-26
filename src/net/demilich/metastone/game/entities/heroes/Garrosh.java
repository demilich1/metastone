package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.ArmorUp;

public class Garrosh extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Garrosh Hellscream", HeroClass.WARRIOR);
	}

	public Garrosh() {
		super("Garrosh Hellscream", HeroClass.WARRIOR, new ArmorUp());
	}

	

}
