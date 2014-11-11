package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
	
	public BattleBatchResultToken() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleBatchResultToken.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void displayBatchResult(BattleBatchResult result) {
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
