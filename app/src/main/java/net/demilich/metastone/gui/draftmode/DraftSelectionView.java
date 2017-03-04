package net.demilich.metastone.gui.draftmode;

import com.hiddenswitch.proto3.draft.HumanDraftBehaviour;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.behaviour.human.DraftSelectionOptions;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.gui.cards.CardTooltip;

import java.io.IOException;

public class DraftSelectionView extends BorderPane implements EventHandler<MouseEvent> {
	@FXML
	private HBox contentArea;

	private final HumanDraftBehaviour behaviour;

	public DraftSelectionView(DraftSelectionOptions options) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DraftSelectionView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		displayCards(options);

		behaviour = options.getBehaviour();

		NotificationProxy.sendNotification(GameNotification.SHOW_MODAL_DIALOG, this);
	}

	private void displayCards(final DraftSelectionOptions options) {
		contentArea.getChildren().clear();
		for (Card card : options.getOfferedCards()) {
			StackPane stackPane = new StackPane();

			CardTooltip cardWidget = new CardTooltip();
			cardWidget.setCard(card);
			cardWidget.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
			stackPane.getChildren().add(cardWidget);

			contentArea.getChildren().add(stackPane);
		}
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		CardTooltip cardWidget = (CardTooltip) mouseEvent.getSource();
		Card card = cardWidget.getCard();

		Platform.runLater(() -> {
			behaviour.putChosenCard(card);
		});

		// TODO: Notify this will be closed!
		this.getScene().getWindow().hide();
	}
}
