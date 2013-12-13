package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DiscardCardSpell implements ISpell {
	
	private final int numberOfCards;

	public DiscardCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		for (int i = 0; i < numberOfCards; i++) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			player.getHand().remove(randomHandCard);
		}
		
	}

}
