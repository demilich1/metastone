package net.demilich.metastone.gui.draftmode;

import com.hiddenswitch.proto3.draft.DraftContext;
import com.hiddenswitch.proto3.draft.PublicDraftState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.gui.deckbuilder.CardListView;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.HumanActionPromptView;
import net.demilich.metastone.gui.playmode.LoadingBoardView;

import java.io.IOException;

public class DraftModeView extends BorderPane {
	@FXML
	private Button backButton;

	@FXML
	private VBox sidePane;

	@FXML
	private Pane navigationPane;

	private final DraftCardListView cardListView;

	private boolean firstUpdate = true;

	public DraftModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		backButton.setOnAction(actionEvent -> NotificationProxy.sendNotification(GameNotification.MAIN_MENU));

		cardListView = new DraftCardListView();
		sidePane.getChildren().setAll(cardListView, navigationPane);
	}

	public void updateDraftState(PublicDraftState state) {
		cardListView.displayDeck(state.createDeck());
	}
}
