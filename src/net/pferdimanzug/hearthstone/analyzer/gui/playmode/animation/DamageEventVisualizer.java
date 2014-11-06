package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import javafx.scene.paint.Color;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameToken;

public class DamageEventVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		DamageEvent damageEvent = (DamageEvent) event;
		GameToken targetToken = boardView.getToken(damageEvent.getEventTarget());
		if (targetToken == null) {
			return;
		}
		DamageNumber damageNumber = new DamageNumber(damageEvent.getDamage() + "", Color.RED, targetToken);
	}

}
