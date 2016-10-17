package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.HealEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameContextVisuals;
import net.demilich.metastone.gui.playmode.GameToken;

public class HealEventVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContextVisuals gameContext, GameEvent event, GameBoardView boardView) {
		HealEvent healEvent = (HealEvent) event;
		GameToken targetToken = boardView.getToken(healEvent.getEventTarget());
		if (targetToken == null) {
			return;
		}
		new HealingNumber("+" + healEvent.getHealing(), targetToken);
	}

}
