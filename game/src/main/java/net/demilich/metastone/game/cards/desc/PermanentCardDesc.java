package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.PermanentCard;

public class PermanentCardDesc extends SummonCardDesc {

	@Override
	public Card createInstance() {
		return new PermanentCard(this);
	}

}
