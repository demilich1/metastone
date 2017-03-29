package net.demilich.metastone.game.entities.minions;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;

public abstract class Summon extends Actor {

	public Summon(Card sourceCard) {
		super(sourceCard);
	}

	@Override
	public Summon clone() {
		Summon clone = (Summon) super.clone();
		return clone;
	}
	
}
