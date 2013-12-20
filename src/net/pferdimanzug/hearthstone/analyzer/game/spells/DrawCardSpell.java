package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DrawCardSpell implements ISpell {
	
	private int numberOfCards;

	public DrawCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		for (int i = 0; i < numberOfCards; i++) {
			context.getLogic().drawCard(player);
		}
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	protected void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

}
