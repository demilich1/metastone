package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MinionToken extends BorderPane {
	@FXML
	private Label name;
	@FXML
	private Label attack;
	@FXML
	private Label hp;

	public MinionToken() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MinionToken.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void setMinion(Minion minion) {
		name.setText(minion.getName());
		attack.setText(String.valueOf(minion.getAttack()));
		hp.setText(String.valueOf(minion.getHp()));
	}

}
