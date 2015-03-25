package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.ChooseOneCard;

public class ChooseOneCardDesc extends SpellCardDesc {
	
	public String option1;
	public String option2;
	
	@Override
	public Card createInstance() {
		return new ChooseOneCard(this);
	}

}
