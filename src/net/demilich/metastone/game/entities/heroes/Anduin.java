package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class Anduin extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Anduin Wrynn", HeroClass.PRIEST);
	}

	public Anduin() {
		super("Anduin Wrynn", HeroClass.PRIEST, (HeroPower) CardCatalogue.getCardById("hero_power_lesser_heal"));
	}

	

}
