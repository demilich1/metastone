package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class WhirlingBlades extends SpellCard {

	public WhirlingBlades() {
		super("Whirling Blades", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a minion +1 Attack.");
		
		setSpell(BuffSpell.create(1));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}

}
