package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Inferno extends HeroPower {

	public Inferno() {
		super("INFERNO!", HeroClass.WARLOCK);
		setSpell(SummonSpell.create((MinionCard)CardCatalogue.getCardById("token_infernal")));
		setTargetRequirement(TargetSelection.NONE);
		
		setCollectible(false);
	}

}