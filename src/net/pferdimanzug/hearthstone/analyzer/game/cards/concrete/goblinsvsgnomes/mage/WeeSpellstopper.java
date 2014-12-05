package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.AuraApplyTag;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		Aura direWolfAlphaAura = new AuraApplyTag(GameTag.UNTARGETABLE_BY_SPELLS, EntityReference.ADJACENT_MINIONS);
		weeSpellstopper.setSpellTrigger(direWolfAlphaAura);
		return weeSpellstopper;
	}
}
