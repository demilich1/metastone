package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;

public class GameToken extends BorderPane {

	private StackPane target;
	private Button targetButton;

	public GameToken(String fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
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

        Blend red = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        targetIcon.getImage().getWidth(),
                        targetIcon.getImage().getHeight(),
                        Color.RED
                )
        );
        
        Blend green = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        targetIcon.getImage().getWidth(),
                        targetIcon.getImage().getHeight(),
                        new Color(0, 1, 0, 0.5)
                )
        );

        

        targetIcon.setCache(true);
        targetIcon.setCacheHint(CacheHint.SPEED);
		
		
		targetButton = new Button("", targetIcon);
		
		targetIcon.effectProperty().bind(
                Bindings
                    .when(targetButton.hoverProperty())
                        .then((Effect) green)
                        .otherwise((Effect) red)
        );
		targetButton.setId("target_button");
		hideTargetMarker();
		target.getChildren().add(targetButton);
	}
	
	public void hideTargetMarker() {
		targetButton.setVisible(false);
	}

	protected void setScoreValue(Text label, int value) {
		setScoreValue(label, value, value);
	}
	
	protected void setScoreValue(Text label, int value, int baseValue) {
		label.setText(String.valueOf(value));
		if (value > baseValue) {
			label.setFill(Color.GREEN);
		}
		else if (value < baseValue) {
			label.setFill(Color.RED);
		}
		else {
			label.setFill(Color.WHITE);
		}
	}
	
	public void showTargetMarker(EventHandler<ActionEvent> clickedHander) {
		targetButton.setOnAction(clickedHander);
		targetButton.setVisible(true);
	}

}
