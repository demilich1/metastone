package net.demilich.metastone.gui.simulationmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerInfoView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setInfo(PlayerConfig playerConfig) {
		HeroCard heroCard = playerConfig.getHeroCard();
		classIcon.setImage(IconFactory.getClassIcon(heroCard.getHeroClass()));
		heroLabel.setText(playerConfig.getName());
		deckLabel.setText(playerConfig.getDeck().getName());
		behaviourLabel.setText(playerConfig.getBehaviour().getName());
	}

}
