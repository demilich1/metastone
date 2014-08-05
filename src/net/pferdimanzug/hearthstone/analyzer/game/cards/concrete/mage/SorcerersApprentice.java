package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.SpellCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
		CardCostModifier costModifier = new SpellCostModifier(-1, false);
		sorcerersApprentice.setCardCostModifier(costModifier);
		return sorcerersApprentice;
	}
}
