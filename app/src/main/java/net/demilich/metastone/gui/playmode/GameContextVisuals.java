package net.demilich.metastone.gui.playmode;

import java.util.List;

import net.demilich.metastone.game.events.GameEvent;

public interface GameContextVisuals {
	public List<GameEvent> getGameEvents();
	public boolean isBlockedByAnimation();
	public void setBlockedByAnimation(boolean blockedByAnimation);
	
}
