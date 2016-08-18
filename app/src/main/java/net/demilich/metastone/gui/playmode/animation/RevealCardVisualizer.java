package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.CardRevealedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;

public class RevealCardVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		CardRevealedEvent cardRevealedEvent = (CardRevealedEvent) event;

		if (cardRevealedEvent.getCard().hasAttribute(Attribute.SECRET)) {
			return;
		}

		new CardRevealedToken(boardView, cardRevealedEvent.getCard(), cardRevealedEvent.getDelay());
	}

}
