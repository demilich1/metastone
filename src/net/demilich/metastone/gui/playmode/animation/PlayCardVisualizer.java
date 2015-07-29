package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.CardPlayedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;

public class PlayCardVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		CardPlayedEvent cardPlayedEvent = (CardPlayedEvent) event;

		if (cardPlayedEvent.getCard().hasAttribute(Attribute.SECRET)) {
			return;
		}

		new CardPlayedToken(boardView, cardPlayedEvent.getCard());
	}

}
