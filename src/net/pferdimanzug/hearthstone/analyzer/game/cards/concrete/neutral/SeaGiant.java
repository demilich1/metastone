package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
	public Minion summon() {
		return createMinion();
	}

}
