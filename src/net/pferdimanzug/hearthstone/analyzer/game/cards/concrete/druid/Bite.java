package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Bite extends SpellCard {

	public Bite() {
		super("Bite", Rarity.RARE, HeroClass.DRUID, 4);
		setDescription("Give your hero +4 Attack this turn and 4 Armor.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(BuffHeroSpell.create(4, 4));
	}

	@Override
	public int getTypeId() {
		return 3;
	}
}
