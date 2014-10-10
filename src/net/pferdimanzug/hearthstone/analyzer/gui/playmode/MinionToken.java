package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MinionToken extends GameToken {
	@FXML
	private Label name;
	@FXML
	private Text attack;
	@FXML
	private Text hp;
	
	public MinionToken() {
		super("MinionToken.fxml");
	}

	public void setMinion(Minion minion) {
		name.setText(minion.getName());
		setScoreValue(attack, minion.getAttack(), minion.getTagValue(GameTag.BASE_ATTACK));
		setScoreValue(hp, minion.getHp(), minion.getMaxHp());
	}

}
