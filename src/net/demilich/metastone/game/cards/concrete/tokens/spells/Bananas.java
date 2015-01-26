package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
