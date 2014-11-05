package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;

public class PlayModeView extends BorderPane {
	
	@FXML
	private Button backButton;
	
	@FXML
	private VBox sidePane;
	
	private final GameBoardView boardView;
	private final HumanActionPromptView actionPromptView;
	
	private final LoadingBoardView loadingView;
	
	private boolean firstUpdate = true;
	
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
		//setCenter(boardView);
		loadingView = new LoadingBoardView();
		setCenter(loadingView);
		
		actionPromptView = new HumanActionPromptView();
		sidePane.getChildren().remove(backButton);
		sidePane.getChildren().add(actionPromptView);
		
		backButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));
	}
	
	public void disableTargetSelection() {
		boardView.disableTargetSelection();
		actionPromptView.setVisible(true);
	}
	
	public void enableTargetSelection(HumanTargetOptions targetOptions) {
		boardView.enableTargetSelection(targetOptions);
	}
	
	public HumanActionPromptView getActionPromptView() {
		return actionPromptView;
	}

	public void updateGameState(GameContext context) {
		if (firstUpdate) {
			setCenter(boardView);
			firstUpdate = false;
		}
		boardView.updateGameState(context);
		if (context.gameDecided()) {
			sidePane.getChildren().clear();
			sidePane.getChildren().add(backButton);
		}
	}

}
