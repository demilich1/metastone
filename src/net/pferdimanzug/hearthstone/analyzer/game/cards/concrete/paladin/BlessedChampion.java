package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.DoubleAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BlessedChampion extends SpellCard {

	public BlessedChampion() {
		super("Blessed Champion", Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Double a minion's Attack.");
		
		setSpell(DoubleAttackSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 237;
	}
	
}
