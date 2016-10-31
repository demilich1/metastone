package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.events.CardPlayedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.game.visuals.GameContextVisuals;

public class PlayCardVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContextVisuals gameContext, GameEvent event, GameBoardView boardView) {
		CardPlayedEvent cardPlayedEvent = (CardPlayedEvent) event;

		if (cardPlayedEvent.getCard().hasAttribute(Attribute.SECRET)) {
			return;
		}

		new CardPlayedToken(boardView, cardPlayedEvent.getCard());
	}

}
