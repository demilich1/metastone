package net.demilich.metastone.gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DigitFactory {

	private final static HashMap<Character, Image> digits = new HashMap<>();

	static {
		digits.put('-', new Image(IconFactory.RESOURCE_PATH + "/img/common/digits/-.png"));
		for (int i = 0; i < 10; i++) {
			char digitToChar = Character.forDigit(i, 10);
			digits.put(digitToChar, new Image(IconFactory.RESOURCE_PATH + "/img/common/digits/" + digitToChar + ".png"));
		}
	}

	private static void applyFontColor(ImageView image, Color color) {
		ColorAdjust monochrome = new ColorAdjust();
		monochrome.setSaturation(-1.0);
		Effect colorInput = new ColorInput(0, 0, image.getImage().getWidth(), image.getImage().getHeight(), color);
		Blend blend = new Blend(BlendMode.MULTIPLY, new ImageInput(image.getImage()), colorInput);
		image.setClip(new ImageView(image.getImage()));
		image.setEffect(blend);
		image.setCache(true);
	}

	private static Node getCachedDigitImage(int number, Color color) {
		String numberString = String.valueOf(number);
		if (numberString.length() == 1) {
			char digitToChar = Character.forDigit(number, 10);
			ImageView image = new ImageView(digits.get(digitToChar));
			applyFontColor(image, color);
			return image;
		}

		HBox layoutPane = new HBox(-4);
		for (int i = 0; i < numberString.length(); i++) {
			char digitToChar = numberString.charAt(i);
			ImageView image = new ImageView(digits.get(digitToChar));
			applyFontColor(image, color);
			layoutPane.getChildren().add(image);
		}
		return layoutPane;
	}

	public static void saveAllDigits() {
		Stage stage = new Stage(StageStyle.TRANSPARENT);
		DigitTemplate root = new DigitTemplate();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		SnapshotParameters snapshotParams = new SnapshotParameters();
		snapshotParams.setFill(Color.TRANSPARENT);
		root.digit.setText("-");
		for (int i = 0; i <= 10; i++) {
			WritableImage image = root.digit.snapshot(snapshotParams, null);

			File file = new File("src/" + IconFactory.RESOURCE_PATH + "/img/common/digits/" + root.digit.getText() + ".png");

			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			root.digit.setText("" + i);
		}

		stage.close();
	}

	public static void showPreRenderedDigits(Group group, int number) {
		showPreRenderedDigits(group, number, Color.WHITE);
	}

	public static void showPreRenderedDigits(Group group, int number, Color color) {
		group.getChildren().clear();
		group.getChildren().add(DigitFactory.getCachedDigitImage(number, color));
	}
}
