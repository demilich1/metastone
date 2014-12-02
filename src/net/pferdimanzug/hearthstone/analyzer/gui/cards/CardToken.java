package net.pferdimanzug.hearthstone.analyzer.gui.cards;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.gui.DigitFactory;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;

public class CardToken extends BorderPane {

	@FXML
	protected Group manaCostAnchor;
	@FXML
	protected Label nameLabel;
	@FXML
	protected Label descriptionLabel;

	@FXML
	protected Group attackAnchor;
	@FXML
	protected Group hpAnchor;

	@FXML
	protected ImageView attackIcon;
	@FXML
	protected ImageView hpIcon;

	@FXML
	protected Circle rarityGem;

	private double baseRarityGemSize;

	protected Card card;

	protected CardToken(String fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		baseRarityGemSize = rarityGem.getRadius();
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		setCard(null, card, null);
	}

	public void setCard(GameContext context, Card card, Player player) {
		this.card = card;
		nameLabel.setText(card.getName());
		setRarity(card.getRarity());
		if (context != null || player != null) {
			int modifiedManaCost = context.getLogic().getModifiedManaCost(player, card);
			setScoreValueLowerIsBetter(manaCostAnchor, modifiedManaCost, card.getBaseManaCost());
		} else {
			setScoreValue(manaCostAnchor, card.getBaseManaCost());
		}

		boolean isMinionCard = card.getCardType() == CardType.MINION;
		attackAnchor.setVisible(isMinionCard);
		hpAnchor.setVisible(isMinionCard);
		attackIcon.setVisible(isMinionCard);
		hpIcon.setVisible(isMinionCard);
		if (isMinionCard) {
			MinionCard minionCard = (MinionCard) card;
			setScoreValue(attackAnchor, minionCard.getAttack(), minionCard.getBaseAttack());
			setScoreValue(hpAnchor, card.getTagValue(GameTag.BASE_HP));
		}
	}

	private void setRarity(Rarity rarity) {
		rarityGem.setFill(IconFactory.getRarityColor(rarity));
		rarityGem.setVisible(rarity != Rarity.FREE);
		rarityGem.setRadius(rarity == Rarity.LEGENDARY ? baseRarityGemSize * 1.5 : baseRarityGemSize);
	}

	private void setScoreValue(Group group, int value) {
		setScoreValue(group, value, value);
	}

	private void setScoreValue(Group group, int value, int baseValue) {
		Color color = Color.WHITE;
		if (value > baseValue) {
			color = Color.GREEN;
		} else if (value < baseValue) {
			color = Color.RED;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}

	private void setScoreValueLowerIsBetter(Group group, int value, int baseValue) {
		Color color = Color.WHITE;
		if (value < baseValue) {
			color = Color.GREEN;
		} else if (value > baseValue) {
			color = Color.RED;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}

}
