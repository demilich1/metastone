package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MinionToken extends GameToken {
	@FXML
	private Label name;
	@FXML
	private Text attack;
	@FXML
	private Text hp;
	
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
