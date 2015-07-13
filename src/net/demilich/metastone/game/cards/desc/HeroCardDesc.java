package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.entities.minions.Race;

public class HeroCardDesc extends CardDesc {
	
	public String heroPower;
	public Race race = Race.NONE;
	
	@Override
	public Card createInstance() {
		return new HeroCard(this);
	}

}
