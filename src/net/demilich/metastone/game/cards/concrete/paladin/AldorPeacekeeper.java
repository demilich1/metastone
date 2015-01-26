package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.HumilitySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AldorPeacekeeper extends MinionCard {

	public AldorPeacekeeper() {
		super("Aldor Peacekeeper", 3, 3, Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Battlecry: Change an enemy minion's Attack to 1.");
	}

	@Override
	public int getTypeId() {
		return 234;
	}

	@Override
	public Minion summon() {
		Minion aldorPeacekepper = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(HumilitySpell.create(), TargetSelection.MINIONS);
		aldorPeacekepper.setBattlecry(battlecry);
		return aldorPeacekepper;
	}
}
