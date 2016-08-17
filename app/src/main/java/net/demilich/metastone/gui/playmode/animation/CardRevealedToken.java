package net.demilich.metastone.gui.playmode.animation;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.gui.cards.CardTooltip;
import net.demilich.metastone.gui.playmode.GameBoardView;

public class CardRevealedToken {

	private final Popup popup;
	private final CardTooltip cardToken;

	public CardRevealedToken(GameBoardView boardView, Card card, double delay) {
		Window parent = boardView.getScene().getWindow();
		this.cardToken = new CardTooltip();

		popup = new Popup();
		popup.getContent().setAll(cardToken);
		popup.setX(parent.getX() + 40);
		popup.show(parent);
		popup.setY(parent.getY() + parent.getHeight() * 0.5 - cardToken.getHeight() * 0.5);

		cardToken.setCard(card);
		NotificationProxy.sendNotification(GameNotification.ANIMATION_STARTED);
		FadeTransition animation = new FadeTransition(Duration.seconds(delay), cardToken);
		animation.setOnFinished(this::secondTransition);
		animation.setFromValue(0);
		animation.setToValue(0);
		animation.play();
	}

	private void secondTransition(ActionEvent event) {
		FadeTransition animation = new FadeTransition(Duration.seconds(.6), cardToken);
		animation.setOnFinished(this::nextTransition);
		animation.setFromValue(0);
		animation.setToValue(1);
		animation.play();
	}

	private void nextTransition(ActionEvent event) {
		FadeTransition animation = new FadeTransition(Duration.seconds(.6), cardToken);
		animation.setOnFinished(this::onComplete);
		animation.setFromValue(1);
		animation.setToValue(0);
		animation.play();
	}

	private void onComplete(ActionEvent event) {
		popup.hide();
		NotificationProxy.sendNotification(GameNotification.ANIMATION_COMPLETED);
	}
}
