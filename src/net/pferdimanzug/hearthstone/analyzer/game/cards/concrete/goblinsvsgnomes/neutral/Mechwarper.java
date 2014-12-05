package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.MinionCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
		MinionCostModifier minionCostModifier = new MinionCostModifier(-1);
		minionCostModifier.setRequiredRace(Race.MECH);
		minionCostModifier.setTargetPlayer(TargetPlayer.BOTH);
		mechwarper.setCardCostModifier(minionCostModifier);
		return mechwarper;
	}
}
