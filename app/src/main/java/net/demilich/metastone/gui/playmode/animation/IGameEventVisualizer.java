package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameContextVisuals;

public interface IGameEventVisualizer {

	void visualizeEvent(GameContextVisuals gameContext, GameEvent event, GameBoardView boardView);
}
