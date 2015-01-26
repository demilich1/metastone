package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.CopyCardSpell;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Thoughtsteal extends SpellCard {

	public Thoughtsteal() {
		super("Thoughtsteal", Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Copy 2 cards from your opponent's deck and put them into your hand.");
		setSpell(CopyCardSpell.create(CardLocation.DECK, 2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 283;
	}
}
