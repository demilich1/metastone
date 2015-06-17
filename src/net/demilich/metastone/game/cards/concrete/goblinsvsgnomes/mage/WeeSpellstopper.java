package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class WeeSpellstopper extends MinionCard {

	public WeeSpellstopper() {
		super("Wee Spellstopper", 2, 5, Rarity.EPIC, HeroClass.MAGE, 4);
		setDescription("Adjacent minions can't be targeted by spells or Hero Powers.");
	}

	@Override
	public int getTypeId() {
		return 499;
	}



	@Override
	public Minion summon() {
		Minion weeSpellstopper = createMinion();
//		Aura direWolfAlphaAura = new AuraApplyTag(GameTag.UNTARGETABLE_BY_SPELLS, EntityReference.ADJACENT_MINIONS);
//		weeSpellstopper.setSpellTrigger(direWolfAlphaAura);
		return weeSpellstopper;
	}
}
