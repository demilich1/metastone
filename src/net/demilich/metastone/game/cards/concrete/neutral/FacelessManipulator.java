package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.FacelessSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FacelessManipulator extends MinionCard {

	public FacelessManipulator() {
		super("Faceless Manipulator", 3, 3, Rarity.EPIC, HeroClass.ANY, 5);
		setDescription("Battlecry: Choose a minion and become a copy of it.");
	}

	@Override
	public int getTypeId() {
		return 125;
	}

	@Override
	public Minion summon() {
		Minion facelessManipulator = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(FacelessSpell.create(), TargetSelection.MINIONS);
		facelessManipulator.setBattlecry(battlecry);
		return facelessManipulator;
	}

	
}
