package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public abstract class PlayCardAction extends GameAction {

	private final Card card;

	public PlayCardAction(Card card) {
		this.card = card;
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().playCard(player, getCard());
		play(context, player);
	}
	
	protected abstract void play(GameContext context, Player player);

	public Card getCard() {
		return card;
	}

}
