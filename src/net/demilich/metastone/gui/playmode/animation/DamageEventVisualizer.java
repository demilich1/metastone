package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameToken;

public class DamageEventVisualizer implements IGameEventVisualizer {

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		DamageEvent damageEvent = (DamageEvent) event;
		GameToken targetToken = boardView.getToken(damageEvent.getVictim());
		if (targetToken == null) {
			return;
		}
		new DamageNumber("-"  + damageEvent.getDamage(), targetToken);
	}

}
