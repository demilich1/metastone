package net.demilich.metastone.gui.cards;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.gui.DigitFactory;
import net.demilich.metastone.gui.IconFactory;

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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + fxml));
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

		boolean isMinionOrWeaponCard = card.getCardType().isCardType(CardType.MINION) || card.getCardType().isCardType(CardType.WEAPON);
		attackAnchor.setVisible(isMinionOrWeaponCard);
		hpAnchor.setVisible(isMinionOrWeaponCard);
		attackIcon.setVisible(isMinionOrWeaponCard);
		hpIcon.setVisible(isMinionOrWeaponCard);
		if (card.getCardType().isCardType(CardType.MINION)) {
			MinionCard minionCard = (MinionCard) card;
			setScoreValue(attackAnchor, minionCard.getAttack() + minionCard.getBonusAttack(), minionCard.getBaseAttack());
			setScoreValue(hpAnchor, minionCard.getHp() + minionCard.getBonusHp(), minionCard.getBaseHp());
		} else if (card.getCardType().isCardType(CardType.WEAPON)) {
			WeaponCard weaponCard = (WeaponCard) card;
			setScoreValue(attackAnchor, weaponCard.getDamage() + weaponCard.getBonusDamage(), weaponCard.getBaseDamage());
			setScoreValue(hpAnchor, weaponCard.getDurability() + weaponCard.getBonusDurability(), weaponCard.getBaseDurability());
		}
	}

	public void setNonCard(String name, String description) {
		nameLabel.setText(name);
		descriptionLabel.setText(description);
		setRarity(Rarity.FREE);
		manaCostAnchor.setVisible(false);
		attackAnchor.setVisible(false);
		hpAnchor.setVisible(false);
		attackIcon.setVisible(false);
		hpIcon.setVisible(false);
	}

	private void setRarity(Rarity rarity) {
		rarityGem.setFill(IconFactory.getRarityColor(rarity));
		rarityGem.setVisible(rarity != Rarity.FREE);
		rarityGem.setRadius(rarity == Rarity.LEGENDARY ? baseRarityGemSize * 1.5 : baseRarityGemSize);
	}

	protected void setScoreValue(Group group, int value) {
		setScoreValue(group, value, value);
	}

	protected void setScoreValue(Group group, int value, int baseValue) {
		Color color = Color.WHITE;
		if (value > baseValue) {
			color = Color.GREEN;
		}
		DigitFactory.showPreRenderedDigits(group, value, color);
	}
	
	protected void setScoreValue(Group group, int value, int baseValue, int maxValue) {
		Color color = Color.WHITE;
		if (value < maxValue) {
			color = Color.RED;
		} else if (value > baseValue) {
			color = Color.GREEN;
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
