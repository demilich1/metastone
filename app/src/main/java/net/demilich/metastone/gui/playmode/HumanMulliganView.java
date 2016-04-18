package net.demilich.metastone.gui.playmode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.behaviour.human.HumanMulliganOptions;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.gui.cards.CardTooltip;

public class HumanMulliganView extends BorderPane implements EventHandler<MouseEvent> {

	private class MulliganEntry {

		public boolean mulligan;

		public ImageView discardIcon;

		public MulliganEntry(ImageView icon) {
			this.discardIcon = icon;
		}
	}

	@FXML
	private HBox contentArea;

	@FXML
	private Button doneButton;

	private final HashMap<Card, MulliganEntry> mulliganState = new HashMap<Card, MulliganEntry>();

	public HumanMulliganView(HumanMulliganOptions options) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HumanMulliganView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		displayCards(options);

		NotificationProxy.sendNotification(GameNotification.SHOW_MODAL_DIALOG, this);
	}

	private void displayCards(final HumanMulliganOptions options) {
		contentArea.getChildren().clear();
		for (Card card : options.getOfferedCards()) {
			StackPane stackPane = new StackPane();

			CardTooltip cardWidget = new CardTooltip();
			cardWidget.setCard(card);
			cardWidget.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
			stackPane.getChildren().add(cardWidget);

			ImageView mulliganIcon = new ImageView(IconFactory.getImageUrl("common/mulligan.png"));
			mulliganIcon.setMouseTransparent(true);
			mulliganIcon.setVisible(false);
			stackPane.getChildren().add(mulliganIcon);

			contentArea.getChildren().add(stackPane);

			mulliganState.put(card, new MulliganEntry(mulliganIcon));
		}

		doneButton.setOnAction(event -> {
			List<Card> discardedCards = new ArrayList<>();
			for (Card card : mulliganState.keySet()) {
				MulliganEntry entry = mulliganState.get(card);
				if (entry.mulligan) {
					discardedCards.add(card);
				}
			}
			options.getBehaviour().setMulliganCards(discardedCards);
			this.getScene().getWindow().hide();
		});
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		CardTooltip cardWidget = (CardTooltip) mouseEvent.getSource();
		Card card = cardWidget.getCard();
		MulliganEntry entry = mulliganState.get(card);
		entry.mulligan = !entry.mulligan;
		entry.discardIcon.setVisible(entry.mulligan);
	}
}
