package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.MinionCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SummoningPortal extends MinionCard {

	public SummoningPortal() {
		super("Summoning Portal", 0, 4, Rarity.COMMON, HeroClass.WARLOCK, 4);
		setDescription("Your minions cost (2) less, but not less than (1).");
	}

	@Override
	public int getTypeId() {
		return 355;
	}

	@Override
	public Minion summon() {
		Minion summoningPortal = createMinion();
		MinionCostModifier costModifier = new MinionCostModifier(-2);
		costModifier.setMinValue(1);
		summoningPortal.setCardCostModifier(costModifier);
		return summoningPortal;
	}
}
