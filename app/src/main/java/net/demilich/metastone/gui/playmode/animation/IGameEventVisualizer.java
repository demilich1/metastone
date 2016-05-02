package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;

public interface IGameEventVisualizer {

	void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView);
}
