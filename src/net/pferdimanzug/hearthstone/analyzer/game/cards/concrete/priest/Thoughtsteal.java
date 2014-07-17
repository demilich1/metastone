package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.CopyCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Thoughtsteal extends SpellCard {

	public Thoughtsteal() {
		super("Thoughtsteal", Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Copy 2 cards from your opponent's deck and put them into your hand.");
		setSpell(new CopyCardSpell(CardLocation.DECK, 2));
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 283;
	}
}
