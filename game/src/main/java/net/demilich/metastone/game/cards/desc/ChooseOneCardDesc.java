package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.ChooseOneCard;

public class ChooseOneCardDesc extends CardDesc {

	public String[] options;
	public String bothOptions;

	@Override
	public Card createInstance() {
		return new ChooseOneCard(this);
	}

}
