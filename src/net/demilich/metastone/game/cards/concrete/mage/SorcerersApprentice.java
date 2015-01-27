package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.cards.costmodifier.SpellCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SorcerersApprentice extends MinionCard {

	public SorcerersApprentice() {
		super("Sorcerer's Apprentice", 3, 2, Rarity.COMMON, HeroClass.MAGE, 2);
		setDescription("Your spells cost (1) less.");
	}

	@Override
	public int getTypeId() {
		return 72;
	}

	@Override
	public Minion summon() {
		Minion sorcerersApprentice = createMinion();
		CardCostModifier costModifier = new SpellCostModifier(-1);
		sorcerersApprentice.setCardCostModifier(costModifier);
		return sorcerersApprentice;
	}
}
