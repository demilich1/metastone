package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class Thrall extends Hero {
	
	public static HeroTemplate getTemplate() {
		return new HeroTemplate("Thrall", HeroClass.SHAMAN);
	}

	public Thrall() {
		super("Thrall", HeroClass.SHAMAN, (HeroPower) CardCatalogue.getCardById("hero_power_totemic_call"));
	}
	
}
