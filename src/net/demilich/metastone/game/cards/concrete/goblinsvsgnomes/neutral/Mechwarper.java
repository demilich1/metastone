package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TargetPlayer;

public class Mechwarper extends MinionCard {

	public Mechwarper() {
		super("Mechwarper", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Your Mechs cost (1) less.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 531;
	}

	@Override
	public Minion summon() {
		Minion mechwarper = createMinion();
		//MinionCostModifier minionCostModifier = new MinionCostModifier(-1);
		//minionCostModifier.setRequiredRace(Race.MECH);
		//minionCostModifier.setTargetPlayer(TargetPlayer.SELF);
		//mechwarper.setCardCostModifier(minionCostModifier);
		return mechwarper;
	}
}
