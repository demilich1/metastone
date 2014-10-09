package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Bananas extends SpellCard {

	public Bananas() {
		super("Bananas", Rarity.FREE, HeroClass.ANY, 1);

		setSpell(BuffSpell.create(1, 1));
		setTargetRequirement(TargetSelection.MINIONS);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 462;
	}
}
