package net.demilich.metastone.gui.battleofdecks;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.gui.IconFactory;

public class BattleBatchResultToken extends BorderPane {

	@FXML
	private Label deck1Label;
	@FXML
	private ImageView deck1Icon;
	@FXML
	private Label deck2Label;
	@FXML
	private ImageView deck2Icon;

	@FXML
	private ProgressBar winrate1Bar;
	@FXML
	private Label winrate1Label;
	@FXML
	private ProgressBar winrate2Bar;
	@FXML
	private Label winrate2Label;
	@FXML
	private Node contentPane;
	@FXML
	private ProgressIndicator progressIndicator;

	public BattleBatchResultToken() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BattleBatchResultToken.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		contentPane.setOpacity(0.25);
		winrate1Bar.setVisible(false);
		winrate1Label.setVisible(false);
		winrate2Bar.setVisible(false);
		winrate2Label.setVisible(false);
	}

	public void displayBatchResult(BattleBatchResult result) {
		if (!result.isCompleted()) {
			progressIndicator.setProgress(result.getProgress());
			Tooltip.install(this, new Tooltip("In progress\n\n" + result.getDeck1().getName() + "\nVS.\n" + result.getDeck2().getName()));
		} else if (contentPane.getOpacity() < 1) {
			contentPane.setOpacity(1);
			progressIndicator.setVisible(false);
			winrate1Bar.setVisible(true);
			winrate1Label.setVisible(true);
			winrate2Bar.setVisible(true);
			winrate2Label.setVisible(true);
			Tooltip.install(this, null);
		}
		deck1Label.setText(result.getDeck1().getName());
		deck1Icon.setImage(IconFactory.getClassIcon(result.getDeck1().getHeroClass()));
		deck2Label.setText(result.getDeck2().getName());
		deck2Icon.setImage(IconFactory.getClassIcon(result.getDeck2().getHeroClass()));

		winrate1Bar.setProgress(result.getDeck1Winrate());
		winrate1Label.setText(String.format("%.2f", result.getDeck1Winrate() * 100) + "%");
		winrate2Bar.setProgress(result.getDeck2Winrate());
		winrate2Label.setText(String.format("%.2f", result.getDeck2Winrate() * 100) + "%");

	}

}
