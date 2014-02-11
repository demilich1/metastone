package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class DrawCardSpell extends Spell {
	
	private int numberOfCards;
	private boolean opponent;

	public DrawCardSpell() {
		this(1);
	}
	
	public DrawCardSpell(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	
	public DrawCardSpell(int numberOfCards, boolean opponent) {
		this.numberOfCards = numberOfCards;
		this.opponent = opponent;
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Player drawingPlayer = opponent ? context.getOpponent(player) : player;
		for (int i = 0; i < numberOfCards; i++) {
			context.getLogic().drawCard(drawingPlayer.getId());
		}
	}

	protected void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

}
