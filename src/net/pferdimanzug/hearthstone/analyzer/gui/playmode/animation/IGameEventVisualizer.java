package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;

public interface IGameEventVisualizer {

	void visualizeEvent(GameContext gameContext, GameEvent event, GameBoardView boardView);
}
