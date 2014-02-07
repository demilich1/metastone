package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class GameBoardView extends BorderPane {

	@FXML
	private HBox p1CardPane;
	@FXML
	private HBox p2CardPane;
	
	@FXML
	private HBox p1MinionPane;
	@FXML
	private HBox p2MinionPane;
	
	@FXML
	private VBox p1HeroAnchor;
	@FXML
	private VBox p2HeroAnchor;

	private HeroToken p1Hero;
	private HeroToken p2Hero;
	private HandCard[] p1Cards = new HandCard[GameLogic.MAX_HAND_CARDS];
	private HandCard[] p2Cards = new HandCard[GameLogic.MAX_HAND_CARDS];
	private MinionToken[] p1Minions = new MinionToken[GameLogic.MAX_MINIONS];
	private MinionToken[] p2Minions = new MinionToken[GameLogic.MAX_MINIONS];

	public GameBoardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameBoardView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		// initialize card ui elements
		for (int i = 0; i < p1Cards.length; i++) {
			p1Cards[i] = new HandCard();
			p2Cards[i] = new HandCard();
		}
		p1CardPane.getChildren().addAll(p1Cards);
		p2CardPane.getChildren().addAll(p2Cards);
		
		// initialize minion ui elements
		for (int i = 0; i < p1Minions.length; i++) {
			p1Minions[i] = new MinionToken();
			p2Minions[i] = new MinionToken();
		}
		p1MinionPane.getChildren().addAll(p1Minions);
		p2MinionPane.getChildren().addAll(p2Minions);
		
		p1Hero = new HeroToken();
		p2Hero = new HeroToken();
		
		p1HeroAnchor.getChildren().add(p1Hero);
		p2HeroAnchor.getChildren().add(p2Hero);
	}

	public void updateGameState(GameContextVisualizable context) {
		p1Hero.setHero(context.getPlayer1());
		p2Hero.setHero(context.getPlayer2());
		
		updateHandCards(context.getPlayer1(), p1Cards);
		updateHandCards(context.getPlayer2(), p2Cards);
		
		updateMinionTokens(context.getPlayer1(), p1Minions);
		updateMinionTokens(context.getPlayer2(), p2Minions);
	}
	
	private void updateHandCards(Player player, HandCard[] handCards) {
		CardCollection hand = player.getHand();
		for (int i = 0; i < handCards.length; i++) {
			if (i < hand.getCount()) {
				handCards[i].setCard(hand.get(i), player);
				handCards[i].setManaged(true);
				handCards[i].setVisible(true);
			} else {
				handCards[i].setManaged(false);
				handCards[i].setVisible(false);
			}
		}
	}
	
	private void updateMinionTokens(Player player, MinionToken[] minionTokens) {
		List<Minion> minions = player.getMinions();
		for (int i = 0; i < minionTokens.length; i++) {
			if (i < minions.size()) {
				minionTokens[i].setMinion(minions.get(i));
				minionTokens[i].setManaged(true);
				minionTokens[i].setVisible(true);
			} else {
				minionTokens[i].setManaged(false);
				minionTokens[i].setVisible(false);
			}
		}
	}

}

