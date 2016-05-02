package net.demilich.metastone.gui.playmode.animation;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.gui.cards.CardTooltip;
import net.demilich.metastone.gui.playmode.GameBoardView;

public class JoustToken {
	
	private final Popup popup;
	private final CardTooltip cardToken;

	public JoustToken(GameBoardView boardView, Card card, boolean up, boolean won) {
		Window parent = boardView.getScene().getWindow();
		this.cardToken = new CardTooltip();

		popup = new Popup();
		popup.getContent().setAll(cardToken);
		popup.setX(parent.getX() + 600);
		popup.show(parent);
		int offsetY = up ? -200 : 100;
		popup.setY(parent.getY() + parent.getHeight() * 0.5 - cardToken.getHeight() * 0.5 + offsetY);

		cardToken.setCard(card);

		NotificationProxy.sendNotification(GameNotification.ANIMATION_STARTED);
		FadeTransition animation = new FadeTransition(Duration.seconds(1.0), cardToken);
		animation.setDelay(Duration.seconds(1f));
		animation.setOnFinished(this::onComplete);
		animation.setFromValue(1);
		animation.setToValue(0);
		animation.play();
		
		if (won) {
			ScaleTransition scaleAnimation = new ScaleTransition(Duration.seconds(0.5f), cardToken);
			scaleAnimation.setByX(0.1);
			scaleAnimation.setByY(0.1);
			scaleAnimation.setCycleCount(2);
			scaleAnimation.setAutoReverse(true);
			scaleAnimation.play();	
		}
	}

	private void onComplete(ActionEvent event) {
		popup.hide();
		NotificationProxy.sendNotification(GameNotification.ANIMATION_COMPLETED);
	}

}
