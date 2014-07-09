package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardCardSpell extends Spell {
	
	public static final int ALL_CARDS = -1;
	
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
		int cardCount = numberOfCards == ALL_CARDS ? player.getHand().getCount() : numberOfCards;
		
		for (int i = 0; i < cardCount; i++) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			logger.debug("{} discards {}", player.getName(), randomHandCard);
			player.getHand().remove(randomHandCard);
		}
		
	}

}
