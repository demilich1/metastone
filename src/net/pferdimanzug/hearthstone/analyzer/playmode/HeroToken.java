package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class HeroToken extends BorderPane {
	
	@FXML
	private Label attackLabel;
	@FXML
	private Label hpLabel;
	@FXML
	private Label manaLabel;
	
	@FXML
	private ImageView portrait;
	
	@FXML
	private ImageView heroPowerIcon;

	public HeroToken() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HeroToken.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void setHero(Player player) {
		Hero hero = player.getHero();
		attackLabel.setText(String.valueOf(hero.getAttack()));
		Image portraitIcon = new Image(PlayModeUiFactory.getIconUrl(hero));
		portrait.setImage(portraitIcon);
		hpLabel.setText(String.valueOf(hero.getHp()));
		manaLabel.setText("Mana: " + player.getMana() + "/" + player.getMaxMana());
	}

}