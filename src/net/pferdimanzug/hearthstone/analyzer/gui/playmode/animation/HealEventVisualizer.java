package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameToken;

public class HealEventVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		HealEvent healEvent = (HealEvent) event;
		GameToken targetToken = boardView.getToken(healEvent.getEventTarget());
		if (targetToken == null) {
			return;
		}
		new HealingNumber("+"  + healEvent.getHealing(), targetToken);
	}

}
