package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DiscardCardSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(DiscardCardSpell.class);
	
	private final int numberOfCards;

	public DiscardCardSpell() {
		this(1);
	}
	
	public DiscardCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		for (int i = 0; i < numberOfCards; i++) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			logger.debug("{} discards {}", player.getName(), randomHandCard);
			player.getHand().remove(randomHandCard);
		}
		
	}

}
