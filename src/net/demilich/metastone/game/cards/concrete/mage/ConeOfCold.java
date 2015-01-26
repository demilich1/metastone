package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ConeOfColdSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ConeOfCold extends SpellCard {

	public ConeOfCold() {
		super("Cone of Cold", Rarity.COMMON, HeroClass.MAGE, 4);
		setDescription("Freeze a minion and the minions next to it, and deal $1 damage to them.");
		setSpell(ConeOfColdSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 56;
	}

	
}
