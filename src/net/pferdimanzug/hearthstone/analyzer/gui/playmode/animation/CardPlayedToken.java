package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.gui.cards.CardTooltip;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;

public class CardPlayedToken {

	private final Popup popup;
	private final CardTooltip cardToken;

	public CardPlayedToken(GameBoardView boardView, Card card) {
		Window parent = boardView.getScene().getWindow();
		this.cardToken = new CardTooltip();
		
		popup = new Popup();
		popup.getContent().setAll(cardToken);
		popup.setX(parent.getX() + 40);
		popup.show(parent);
		popup.setY(parent.getY() + parent.getHeight() * 0.5 - cardToken.getHeight() * 0.5);

		cardToken.setCard(card);

		ApplicationFacade.getInstance().sendNotification(GameNotification.ANIMATION_STARTED);
		FadeTransition animation = new FadeTransition(Duration.seconds(1), cardToken);
		animation.setDelay(Duration.seconds(1));
		animation.setOnFinished(this::onComplete);
		animation.setFromValue(1);
		animation.setToValue(0);
		animation.play();
	}

	private void onComplete(ActionEvent event) {
		popup.hide();
		ApplicationFacade.getInstance().sendNotification(GameNotification.ANIMATION_COMPLETED);
	}
}
