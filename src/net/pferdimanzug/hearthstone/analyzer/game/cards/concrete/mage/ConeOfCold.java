package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ConeOfColdSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
