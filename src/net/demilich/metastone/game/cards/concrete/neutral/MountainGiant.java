package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MountainGiant extends MinionCard {

	public MountainGiant() {
		super("Mountain Giant", 8, 8, Rarity.EPIC, HeroClass.ANY, 12);
		setDescription("Costs (1) less for each other card in your hand.");
	}

	@Override
	public int getManaCost(GameContext context, Player player) {
		int cardCount = player.getHand().getCount() - 1; 
		return Math.min(super.getManaCost(context, player) - cardCount, getBaseManaCost());
	}
	
	@Override
	public int getTypeId() {
		return 169;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
