package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MountainGiant extends MinionCard {

	public MountainGiant() {
		super("Mountain Giant", 8, 8, Rarity.EPIC, HeroClass.ANY, 12);
		setDescription("Costs (1) less for each other card in your hand.");
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		int cardCount = player.getHand().getCount() - 1; 
		return Math.min(super.getManaCost(context, player) - cardCount, getBaseManaCost());
	}

}
