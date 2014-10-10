package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class PlayModeView extends BorderPane {
	
	private final GameBoardView boardView;
	private final HumanActionPromptView actionPromptView;
	
	public PlayModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		boardView = new GameBoardView();
		setCenter(boardView);
		
		actionPromptView = new HumanActionPromptView();
		setRight(actionPromptView);
	}

	public GameBoardView getBoardView() {
		return boardView;
	}

	public HumanActionPromptView getActionPromptView() {
		return actionPromptView;
	}

}
