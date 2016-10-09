package net.demilich.metastone.gui.playmode;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import net.demilich.metastone.gui.DigitFactory;
import net.demilich.metastone.gui.IconFactory;

public class GameToken extends BorderPane {

	protected StackPane target;
	private ImageView targetButton;
	private EventHandler<MouseEvent> existingEventHandler;

	public GameToken(String fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + fxml));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		createTargetButton();
	}

	private void createTargetButton() {
		target = (StackPane) lookup("#targetAnchor");
		Image image = IconFactory.getTargetIcon();
		ImageView targetIcon = new ImageView(image);
		targetIcon.setClip(new ImageView(image));
		ColorAdjust monochrome = new ColorAdjust();
		monochrome.setSaturation(-1.0);

		Blend red = new Blend(BlendMode.MULTIPLY, monochrome,
				new ColorInput(0, 0, targetIcon.getImage().getWidth(), targetIcon.getImage().getHeight(), Color.RED));

		Blend green = new Blend(BlendMode.MULTIPLY, monochrome,
				new ColorInput(0, 0, targetIcon.getImage().getWidth(), targetIcon.getImage().getHeight(), new Color(0, 1, 0, 0.5)));

		targetButton = targetIcon;

		targetIcon.effectProperty().bind(Bindings.when(targetButton.hoverProperty()).then((Effect) green).otherwise((Effect) red));
		targetButton.setId("target_button");
		hideTargetMarker();
		target.getChildren().add(targetButton);
	}

	public StackPane getAnchor() {
		return target;
	}

	public void hideTargetMarker() {
		targetButton.setVisible(false);
	}

	protected void setScoreValue(Group group, int value) {
		setScoreValue(group, value, value);
	}

	protected void setScoreValue(Group group, int value, int baseValue) {
		Color color = Color.WHITE;
		if (value > baseValue) {
			color = Color.GREEN;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}
	
	protected void setScoreValue(Group group, int value, int baseValue, int maxValue) {
		Color color = Color.WHITE;
		if (value < maxValue) {
			color = Color.RED;
		} else if (value > baseValue) {
			color = Color.GREEN;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}

	protected void setScoreValueLowerIsBetter(Group group, int value, int baseValue) {
		Color color = Color.WHITE;
		if (value < baseValue) {
			color = Color.GREEN;
		} else if (value > baseValue) {
			color = Color.RED;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}

	public void showTargetMarker(EventHandler<MouseEvent> clickedHander) {
		if (existingEventHandler != null) {
			targetButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, existingEventHandler);
		}
		targetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, clickedHander);
		targetButton.setVisible(true);
		existingEventHandler = clickedHander;
	}

}
