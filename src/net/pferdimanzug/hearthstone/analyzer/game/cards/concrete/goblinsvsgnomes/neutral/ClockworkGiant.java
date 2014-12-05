package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class ClockworkGiant extends MinionCard {

	public ClockworkGiant() {
		super("Clockwork Giant", 8, 8, Rarity.EPIC, HeroClass.ANY, 12);
		setDescription("Costs (1) less Mana for each card in your opponent's hand.");
		setRace(Race.MECH);
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		Player opponent = context.getOpponent(player);
		return super.getManaCost(context, player) - opponent.getHand().getCount();
	}

	@Override
	public int getTypeId() {
		return 506;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
