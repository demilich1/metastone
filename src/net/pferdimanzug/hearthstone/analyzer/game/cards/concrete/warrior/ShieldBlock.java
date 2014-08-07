package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ShieldBlock extends SpellCard {

	public ShieldBlock() {
		super("Shield Block", Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Gain 5 Armor. Draw a card.");
		setSpell(MetaSpell.create(BuffHeroSpell.create(0, 5), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.NONE);
	}


	@Override
	public int getTypeId() {
		return 378;
	}
}
