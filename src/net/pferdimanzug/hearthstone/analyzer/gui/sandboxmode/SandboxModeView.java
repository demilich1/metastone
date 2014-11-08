package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.HumanActionPromptView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.LoadingBoardView;

public class SandboxModeView extends BorderPane {
	
	@FXML
	private Button backButton;
	@FXML
	private Button playButton;
	@FXML
	private VBox sidebar;
	
	@FXML
	private Pane navigationPane;

	private final GameBoardView boardView;
	private final ToolboxView toolboxView;
	private final HumanActionPromptView actionPromptView;
	private final LoadingBoardView loadingBoardView;
	private boolean firstUpdate = true;

	public SandboxModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SandboxModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		boardView = new GameBoardView();
		
		loadingBoardView = new LoadingBoardView();
		loadingBoardView.setScaleX(0.9);
		loadingBoardView.setScaleY(0.9);
		loadingBoardView.setScaleZ(0.9);
		setCenter(loadingBoardView);

		toolboxView = new ToolboxView();
		actionPromptView = new HumanActionPromptView();
		
		backButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));
		playButton.setOnAction(this::startPlayMode);
		
		sidebar.getChildren().setAll(toolboxView, navigationPane);
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
	
	public GameBoardView getBoardView() {
		return boardView;
	}

	public void onPlayerSelectionChanged(Player selectedPlayer) {
		toolboxView.onPlayerSelectionChanged(selectedPlayer);
	}

	private void startPlayMode(ActionEvent actionEvent) {
		sidebar.getChildren().setAll(getActionPromptView(), navigationPane);
		backButton.setVisible(false);
		playButton.setText("Stop");
		ImageView buttonGraphic = (ImageView) playButton.getGraphic();
		buttonGraphic.setImage(new Image(IconFactory.getImageUrl("ui/pause_icon.png")));
		playButton.setOnAction(this::stopPlayMode);
		ApplicationFacade.getInstance().sendNotification(GameNotification.START_PLAY_SANDBOX);
	}

	private void stopPlayMode(ActionEvent actionEvent) {
		sidebar.getChildren().setAll(toolboxView, navigationPane);
		backButton.setVisible(true);
		playButton.setText("Play");
		ImageView buttonGraphic = (ImageView) playButton.getGraphic();
		buttonGraphic.setImage(new Image(IconFactory.getImageUrl("ui/play_icon.png")));
		playButton.setOnAction(this::startPlayMode);
		ApplicationFacade.getInstance().sendNotification(GameNotification.STOP_PLAY_SANDBOX);
	}
	
	public void showAnimations(GameContext context) {
		getBoardView().showAnimations(context);
	}
	
	public void updateSandbox(GameContext context) {
		if (firstUpdate) {
			setCenter(getBoardView());
			firstUpdate = false;
		}
		
		getBoardView().updateGameState(context);
		
		if (toolboxView.getParent() != null) {
			toolboxView.setContext(context);
		}
	}

}
