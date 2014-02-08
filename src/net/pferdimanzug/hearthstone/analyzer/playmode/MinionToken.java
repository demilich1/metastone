package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MinionToken extends GameToken {
	@FXML
	private Label name;
	@FXML
	private Label attack;
	@FXML
	private Label hp;
	
	@FXML
	private Shape selector;

	public MinionToken() {
		super("MinionToken.fxml");
	}

	public void setMinion(Minion minion) {
		name.setText(minion.getName());
		attack.setText(String.valueOf(minion.getAttack()));
		hp.setText(String.valueOf(minion.getHp()));
	}


	

}
