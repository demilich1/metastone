package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DrawCardSpell extends Spell {
	
	private int numberOfCards;

	public DrawCardSpell() {
		this(1);
	}
	
	public DrawCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		for (int i = 0; i < numberOfCards; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}

	protected void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

}
