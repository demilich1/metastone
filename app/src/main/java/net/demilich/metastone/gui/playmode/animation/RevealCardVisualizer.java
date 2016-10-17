package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.CardRevealedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameContextVisuals;

public class RevealCardVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContextVisuals gameContext, GameEvent event, GameBoardView boardView) {
		CardRevealedEvent cardRevealedEvent = (CardRevealedEvent) event;

		if (cardRevealedEvent.getCard().hasAttribute(Attribute.SECRET)) {
			return;
		}

		new CardRevealedToken(boardView, cardRevealedEvent.getCard(), cardRevealedEvent.getDelay());
	}

}
