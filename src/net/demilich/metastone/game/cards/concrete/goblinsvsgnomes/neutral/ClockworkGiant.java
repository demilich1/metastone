package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

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
