package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.cards.concrete.tokens.warlock.Infernal;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Inferno extends HeroPower {

	public Inferno() {
		super("INFERNO!", HeroClass.WARLOCK);
		setSpell(SummonSpell.create(new Infernal()));
		setTargetRequirement(TargetSelection.NONE);
		
		setCollectible(false);
	}

}