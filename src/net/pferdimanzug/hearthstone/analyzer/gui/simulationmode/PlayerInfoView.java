package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroTemplate;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;

public class PlayerInfoView extends Pane {
	
	@FXML
	private ImageView classIcon;
	
	@FXML
	private Label heroLabel;
	
	@FXML
	private Label deckLabel;
	
	@FXML
	private Label behaviourLabel;
	
	public PlayerInfoView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerInfoView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void setInfo(PlayerConfig playerConfig) {
		HeroTemplate heroTemplate = playerConfig.getHeroTemplate();
		classIcon.setImage(IconFactory.getClassIcon(heroTemplate.getHeroClass()));
		heroLabel.setText(playerConfig.getName());
		deckLabel.setText(playerConfig.getDeck().getName());
		behaviourLabel.setText(playerConfig.getBehaviour().getName());
	}

}
