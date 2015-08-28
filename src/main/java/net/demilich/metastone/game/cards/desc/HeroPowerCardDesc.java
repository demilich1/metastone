package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class HeroPowerCardDesc extends SpellCardDesc {

	@Override
	public Card createInstance() {
		return new HeroPower(this);
	}

}
