package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DiscardCardSpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(DiscardCardSpell.class);
	
	private final int numberOfCards;

	public DiscardCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	
	public DiscardCardSpell() {
		this(1);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		logger.debug("{} discards {} cards", player.getName(), numberOfCards);
		for (int i = 0; i < numberOfCards; i++) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			player.getHand().remove(randomHandCard);
		}
		
	}

}
