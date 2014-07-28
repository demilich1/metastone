package net.pferdimanzug.hearthstone.analyzer.gui.cards;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;

public class CardToken extends BorderPane {

	@FXML
	protected Text manaCostLabel;
	@FXML
	protected Label nameLabel;
	@FXML
	protected Label descriptionLabel;

	@FXML
	protected Text attackLabel;
	@FXML
	protected Text hpLabel;

	@FXML
	protected ImageView attackIcon;
	@FXML
	protected ImageView hpIcon;
	
	@FXML
	protected Circle rarityGem;
	
	private double baseRarityGemSize;
	
	private Card card;

	protected CardToken(String fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		setCache(true);
		setCacheHint(CacheHint.DEFAULT);
		
		baseRarityGemSize = rarityGem.getRadius();
		manaCostLabel.setCache(true);
		attackLabel.setCache(true);
		hpLabel.setCache(true);
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
			setScoreValueLowerIsBetter(manaCostLabel, modifiedManaCost, card.getBaseManaCost());
		} else {
			manaCostLabel.setText(String.valueOf(card.getBaseManaCost()));
		}
		
		boolean isMinionCard = card.getCardType() == CardType.MINION;
		attackLabel.setVisible(isMinionCard);
		hpLabel.setVisible(isMinionCard);
		attackIcon.setVisible(isMinionCard);
		hpIcon.setVisible(isMinionCard);
		if (isMinionCard) {
			setScoreValue(attackLabel, card.getTagValue(GameTag.BASE_ATTACK));
			setScoreValue(hpLabel, card.getTagValue(GameTag.BASE_HP));
		}
	}
	
	private void setRarity(Rarity rarity) {
		rarityGem.setFill(IconFactory.getRarityColor(rarity));
		rarityGem.setVisible(rarity != Rarity.FREE);
		rarityGem.setRadius(rarity == Rarity.LEGENDARY ? baseRarityGemSize * 1.5 : baseRarityGemSize);
	}

	private void setScoreValue(Text label, int value) {
		setScoreValue(label, value, value);
	}

	private void setScoreValue(Text label, int value, int baseValue) {
		label.setText(String.valueOf(value));
		if (value > baseValue) {
			label.setFill(Color.GREEN);
		} else if (value < baseValue) {
			label.setFill(Color.RED);
		} else {
			label.setFill(Color.WHITE);
		}
	}

	private void setScoreValueLowerIsBetter(Text label, int value, int baseValue) {
		label.setText(String.valueOf(value));
		if (value < baseValue) {
			label.setFill(Color.GREEN);
		} else if (value > baseValue) {
			label.setFill(Color.RED);
		} else {
			label.setFill(Color.WHITE);
		}
	}

}
