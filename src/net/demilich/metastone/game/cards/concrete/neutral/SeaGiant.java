package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SeaGiant extends MinionCard {

	public SeaGiant() {
		super("Sea Giant", 8, 8, Rarity.EPIC, HeroClass.ANY, 10);
		setDescription("Costs (1) less for each other minion on the battlefield.");
	}

	@Override
	public int getManaCost(GameContext context, Player player) {
		int minionsOnBattlefield = context.getMinionCount(player);
		Player opponent = context.getOpponent(player);
		minionsOnBattlefield += context.getMinionCount(opponent);
		return super.getManaCost(context, player) - minionsOnBattlefield;
	}

	@Override
	public int getTypeId() {
		return 193;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
