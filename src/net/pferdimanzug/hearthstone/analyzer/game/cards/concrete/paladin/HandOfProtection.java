package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HandOfProtection extends SpellCard {

	public HandOfProtection() {
		super("Hand of Protection", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Give a minion Divine Shield.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(ApplyTagSpell.create(GameTag.DIVINE_SHIELD));
	}



	@Override
	public int getTypeId() {
		return 247;
	}
}
