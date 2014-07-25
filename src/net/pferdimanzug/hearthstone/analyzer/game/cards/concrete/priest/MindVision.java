package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.CopyCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindVision extends SpellCard {
	
	public MindVision() {
		super("Mind Vision", Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Put a copy of a random card in your opponent's hand into your hand.");
		setSpell(new CopyCardSpell(CardLocation.HAND, 1));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 273;
	}
}
