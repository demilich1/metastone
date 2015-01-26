package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.DoubleAttackSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
