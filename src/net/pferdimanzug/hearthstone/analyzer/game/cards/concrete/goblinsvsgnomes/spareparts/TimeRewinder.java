package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TimeRewinder extends SpellCard {

	public TimeRewinder() {
		super("Time Rewinder", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Return a friendly minion to your hand.");
		
		setSpell(ReturnMinionToHandSpell.create());
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		
		setCollectible(false);
	}

}
