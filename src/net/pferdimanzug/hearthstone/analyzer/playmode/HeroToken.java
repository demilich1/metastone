package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class HeroToken extends GameToken {

	@FXML
	private Label attackLabel;
	@FXML
	private Label hpLabel;
	@FXML
	private Label manaLabel;
	@FXML
	private Label armorLabel;

	@FXML
	private ImageView armorIcon;

	@FXML
	private ImageView portrait;

	@FXML
	private ImageView heroPowerIcon;

	public HeroToken() {
		super("HeroToken.fxml");
	}

	public void setHero(Player player) {
		Hero hero = player.getHero();
		attackLabel.setText(String.valueOf(hero.getAttack()));
		Image portraitImage = new Image(IconFactory.getHeroIconUrl(hero));
		portrait.setImage(portraitImage);
		Image heroPowerImage = new Image(IconFactory.getHeroPowerIconUrl(hero.getHeroPower()));
		heroPowerIcon.setImage(heroPowerImage);
		hpLabel.setText(String.valueOf(hero.getHp()));
		manaLabel.setText("Mana: " + player.getMana() + "/" + player.getMaxMana());
		updateArmor(hero.getArmor());
	}
	
	private void updateArmor(int armor) {
		armorLabel.setText(String.valueOf(armor));
		boolean visible = armor > 0;
		armorIcon.setVisible(visible);
		armorLabel.setVisible(visible);
	}

}