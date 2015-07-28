package net.demilich.metastone.gui.playmode;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.gui.DigitFactory;
import net.demilich.metastone.gui.cards.CardTooltip;

public class MinionToken extends GameToken {
	@FXML
	private Label name;
	@FXML
	private Group attackAnchor;
	@FXML
	private Group hpAnchor;
	
	@FXML
	private Node defaultToken;
	@FXML
	private Node divineShield;
	@FXML
	private Node taunt;
	@FXML
	private Node windfury;
	@FXML
	private Node deathrattle;
	
	@FXML
	private Shape frozen;
	
	private CardTooltip cardTooltip;
	
	public MinionToken() {
		super("MinionToken.fxml");
		Tooltip tooltip = new Tooltip();
		cardTooltip = new CardTooltip();
		tooltip.setGraphic(cardTooltip);
		Tooltip.install(this, tooltip);
		frozen.getStrokeDashArray().add(16.0);
	}

	public void setMinion(Minion minion) {
		name.setText(minion.getName());
		setScoreValue(attackAnchor, minion.getAttack(), minion.getAttributeValue(Attribute.BASE_ATTACK));
		Color color = Color.WHITE;
		if (minion.getHp() < minion.getMaxHp()) {
			color = Color.RED;
		} else if (minion.getHp() > minion.getAttributeValue(Attribute.BASE_HP)) {
			color = Color.GREEN;
		}
		DigitFactory.showPreRenderedDigits(hpAnchor, minion.getHp(), color);
		visualizeStatus(minion);
		cardTooltip.setCard(minion.getSourceCard());
	}
	
	private void visualizeStatus(Minion minion) {
		taunt.setVisible(minion.hasAttribute(Attribute.TAUNT));
		defaultToken.setVisible(!minion.hasAttribute(Attribute.TAUNT));
		divineShield.setVisible(minion.hasAttribute(Attribute.DIVINE_SHIELD));
		windfury.setVisible(minion.hasAttribute(Attribute.WINDFURY));
		deathrattle.setVisible(minion.hasAttribute(Attribute.DEATHRATTLES));
		frozen.setVisible(minion.hasAttribute(Attribute.FROZEN));
		visualizeStealth(minion);
	}
	
	private void visualizeStealth(Minion minion) {
		Node token = minion.hasAttribute(Attribute.TAUNT) ? taunt : defaultToken;
		token.setOpacity(minion.hasAttribute(Attribute.STEALTH) ? 0.5 : 1);
	}

}


