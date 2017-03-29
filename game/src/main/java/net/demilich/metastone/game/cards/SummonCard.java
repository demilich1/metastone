package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.SummonCardDesc;
import net.demilich.metastone.game.entities.minions.Summon;

public abstract class SummonCard extends Card {

	public SummonCard(SummonCardDesc desc) {
		super(desc);
	}

	@Override
	public abstract PlayCardAction play();

	
	public abstract Summon summon();

}
