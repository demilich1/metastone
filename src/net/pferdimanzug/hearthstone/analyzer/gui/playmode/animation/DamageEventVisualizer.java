package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameToken;

public class DamageEventVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		DamageEvent damageEvent = (DamageEvent) event;
		GameToken targetToken = boardView.getToken(damageEvent.getVictim());
		if (targetToken == null) {
			System.out.println("Token is NULL for " + damageEvent.getVictim());
			return;
		}
		System.out.println("Creating damage numbner for: " + damageEvent.getVictim());
		new DamageNumber("-"  + damageEvent.getDamage(), targetToken);
	}

}
