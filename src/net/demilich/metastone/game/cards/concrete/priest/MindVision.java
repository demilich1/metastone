package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.CopyCardSpell;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MindVision extends SpellCard {
	
	public MindVision() {
		super("Mind Vision", Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Put a copy of a random card in your opponent's hand into your hand.");
		setSpell(CopyCardSpell.create(CardLocation.HAND, 1));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 273;
	}
}
