package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.scene.layout.BorderPane;

public class PlayModePane extends BorderPane {
	
	private final GameBoardView boardView;

	public PlayModePane() {
		boardView = new GameBoardView();
		setCenter(boardView);
	}
	
	public void updateGameState(GameContextVisualizable context) {
		boardView.updateGameState(context);
	}
}
