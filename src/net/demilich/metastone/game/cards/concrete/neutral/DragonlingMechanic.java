package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.MechanicalDragonling;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DragonlingMechanic extends MinionCard {

	public DragonlingMechanic() {
		super("Dragonling Mechanic", 2, 4, Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Battlecry: Summon a 2/1 Mechanical Dragonling. ");
	}

	@Override
	public int getTypeId() {
		return 119;
	}
	
	@Override
	public Minion summon() {
		Minion dragonlingMechanic = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new MechanicalDragonling()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		dragonlingMechanic.setBattlecry(battlecry);
		return dragonlingMechanic;
	}
}
