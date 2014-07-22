package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.FacelessSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
		Battlecry battlecry = Battlecry.createBattlecry(new FacelessSpell(), TargetSelection.MINIONS);
		facelessManipulator.setBattlecry(battlecry);
		return facelessManipulator;
	}
}
