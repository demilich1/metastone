package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.ChooseOneCard;

public class ChooseOneCardDesc extends SpellCardDesc {

	public String[] options;
	public String bothOptions;

	@Override
	public Card createInstance() {
		return new ChooseOneCard(this);
	}

}
