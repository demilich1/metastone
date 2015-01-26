package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TimeRewinder extends SpellCard {

	public TimeRewinder() {
		super("Time Rewinder", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Return a friendly minion to your hand.");
		
		setSpell(ReturnMinionToHandSpell.create());
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 588;
	}
}
