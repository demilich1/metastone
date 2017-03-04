package net.demilich.metastone.game.visuals;

import java.util.List;

import net.demilich.metastone.game.events.GameEvent;

public interface GameContextVisuals {
	public List<GameEvent> getGameEvents();

	public int getLocalPlayerId();

	public boolean isBlockedByAnimation();

	public void setBlockedByAnimation(boolean blockedByAnimation);

}
