package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.HeroCard;

public class HeroCardDesc extends CardDesc {
	
	public String heroPower;
	
	@Override
	public Card createInstance() {
		return new HeroCard(this);
	}

}
