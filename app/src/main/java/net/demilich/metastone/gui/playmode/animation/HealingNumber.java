package net.demilich.metastone.gui.playmode.animation;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.CacheHint;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.gui.playmode.GameToken;

public class HealingNumber extends Text {

	private final GameToken parent;

	public HealingNumber(String text, GameToken parent) {
		this.parent = parent;

		setText(text);
		setFill(Color.GREEN);
		setStyle("-fx-font-size: 28pt; -fx-font-family: \"System\";-fx-font-weight: bolder;-fx-stroke: black;-fx-stroke-width: 2;");

		setCache(true);
		setCacheHint(CacheHint.SPEED);

		parent.getAnchor().getChildren().add(this);

		NotificationProxy.sendNotification(GameNotification.ANIMATION_STARTED);
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), this);
		animation.setToY(-30);
		animation.setOnFinished(this::onComplete);
		animation.play();
	}

	private void onComplete(ActionEvent event) {
		parent.getAnchor().getChildren().remove(this);
		NotificationProxy.sendNotification(GameNotification.ANIMATION_COMPLETED);
	}
}
