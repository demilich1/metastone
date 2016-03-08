package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.JoustEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;

public class JoustVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		JoustEvent joustEvent = (JoustEvent) event;

		Card downCard = null;
		Card upCard = null;
		boolean upWon = false;
		if (joustEvent.getTargetPlayerId() == GameContext.PLAYER_1) {
			downCard = joustEvent.getOwnCard();
			upCard = joustEvent.getOpponentCard();
			upWon = !joustEvent.isWon();
		} else {
			downCard = joustEvent.getOpponentCard();
			upCard = joustEvent.getOwnCard();
			upWon = joustEvent.isWon();
		}

		if (upCard != null) {
			new JoustToken(boardView, upCard, true, upWon);
		}

		if (downCard != null) {
			new JoustToken(boardView, downCard, false, !upWon);
		}

	}

}
