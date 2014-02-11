package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HeroicStrike extends SpellCard {

	public HeroicStrike() {
		super("Heroic Strike", Rarity.FREE, HeroClass.WARRIOR, 2);
		setDescription("Give your hero +4 Attack this turn.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(4, 0));
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
	}
	
}
