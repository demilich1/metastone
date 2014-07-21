package net.pferdimanzug.hearthstone.analyzer.gui.playmode.config;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public class PlayModeSetupView extends BorderPane {
	
	@FXML
	private HBox playerArea;
	
	private PlayerConfigView playerConfigHuman;
	private PlayerConfigView playerConfigCpu;
	
	public PlayModeSetupView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayModeSetupView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		playerConfigHuman = new PlayerConfigView();
		playerConfigCpu = new PlayerConfigView();
		
		playerArea.getChildren().add(playerConfigHuman);
		playerArea.getChildren().add(playerConfigCpu);
	}
	
	public void injectDecks(List<Deck> decks) {
		playerConfigHuman.injectDecks(decks);
		playerConfigCpu.injectDecks(decks);
	}

}
