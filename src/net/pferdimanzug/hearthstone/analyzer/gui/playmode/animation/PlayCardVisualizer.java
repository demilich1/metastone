package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.events.CardPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;

public class PlayCardVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		CardPlayedEvent cardPlayedEvent = (CardPlayedEvent) event;
		
		if (cardPlayedEvent.getCard().hasTag(GameTag.SECRET)) {
			return;
		}

		new CardPlayedToken(boardView, cardPlayedEvent.getCard());
	}

}
