package net.demilich.metastone.gui.playmode.animation;

import java.util.HashMap;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameToken;

public class DamageEventVisualizer implements IGameEventVisualizer {

	private HashMap<Integer, HitInfo> recentHits = new HashMap<>();

	@Override
	public void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView) {
		DamageEvent damageEvent = (DamageEvent) event;
		GameToken targetToken = boardView.getToken(damageEvent.getVictim());
		if (targetToken == null) {
			return;
		}

		Integer victimId = damageEvent.getVictim().getId();
		if (!recentHits.containsKey(victimId)) {
			recentHits.put(victimId, new HitInfo());
		}
		// when the last displayed hit was on the same target and only a small
		// amount of time passed, offset
		// the damage numbers so that all are actually visible
		HitInfo hitInfo = recentHits.get(victimId);
		if (System.currentTimeMillis() - hitInfo.lastHitTime < 1000) {
			hitInfo.successiveHits++;
		} else {
			hitInfo.successiveHits = 0;
		}

		new DamageNumber("-" + damageEvent.getDamage(), targetToken, hitInfo.successiveHits);
		hitInfo.lastHitTime = System.currentTimeMillis();
	}

	private class HitInfo {
		public long lastHitTime;
		public int successiveHits;
	}

}
