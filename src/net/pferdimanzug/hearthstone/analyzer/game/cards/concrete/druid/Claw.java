package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Claw extends SpellCard {

	public Claw() {
		super("Claw", Rarity.FREE, HeroClass.DRUID, 1);
		setDescription("Give your hero +2 Attack this turn and 2 Armor.");
		setSpell(new BuffHeroSpell(2, 2));
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 5;
	}
}
