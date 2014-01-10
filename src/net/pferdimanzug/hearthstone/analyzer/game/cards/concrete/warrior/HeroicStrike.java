package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;

public class HeroicStrike extends SpellCard {

	public HeroicStrike() {
		super("Heroic Strike", Rarity.FREE, HeroClass.WARRIOR, 2);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(4, 0));
		
	}
	
}
