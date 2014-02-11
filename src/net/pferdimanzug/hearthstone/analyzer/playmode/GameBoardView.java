package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
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
	
	@FXML
	private HBox centerMessageArea;

	private HeroToken p1Hero;
	private HeroToken p2Hero;
	private HandCard[] p1Cards = new HandCard[GameLogic.MAX_HAND_CARDS];
	private HandCard[] p2Cards = new HandCard[GameLogic.MAX_HAND_CARDS];
	private MinionToken[] p1Minions = new MinionToken[GameLogic.MAX_MINIONS];
	private MinionToken[] p2Minions = new MinionToken[GameLogic.MAX_MINIONS];
	
	private final HashMap<GameToken, Button> summonHelperMap = new HashMap<GameToken, Button>();
	private final HashMap<Actor, GameToken> entityTokenMap = new HashMap<Actor, GameToken>();
	
	@FXML
	private Label centerMessageLabel;

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
		
		// initialize minion tokens elements
		for (int i = 0; i < p1Minions.length; i++) {
			Button summonHelper = createSummonHelper();
			p1MinionPane.getChildren().add(summonHelper);
			p1Minions[i] = new MinionToken();
			p1MinionPane.getChildren().add(p1Minions[i]);
			summonHelperMap.put(p1Minions[i], summonHelper);
			
			p2Minions[i] = new MinionToken();
		}
		// create one additional summon helper
		Button summonHelper = createSummonHelper();
		p1MinionPane.getChildren().add(summonHelper);
		summonHelperMap.put(null, summonHelper);
		
		p2MinionPane.getChildren().addAll(p2Minions);
		
		p1Hero = new HeroToken();
		p2Hero = new HeroToken();
		
		p1HeroAnchor.getChildren().add(p1Hero);
		p2HeroAnchor.getChildren().add(p2Hero);
		
	}
	
	private Button createSummonHelper() {
		ImageView icon = new ImageView(IconFactory.getSummonHelper());
		icon.setFitWidth(32);
		icon.setFitHeight(32);
		Button helper = new Button("", icon);
		helper.setVisible(false);
		helper.setManaged(false);
		return helper;
	}
	
	public void enableTargetSelection(final HumanTargetOptions targetOptions) {
		GameAction action = targetOptions.getAction();
		if (action.getActionType() == ActionType.SUMMON) {
			enableSummonTargets(targetOptions);
		} else {
			enableSpellTargets(targetOptions);
		}
		setCenterMessage("Select target for " + action.getActionType());
	}
	
	private void enableSpellTargets(final HumanTargetOptions targetOptions) {
		GameAction action = targetOptions.getAction();
		for (final Actor target : action.getValidTargets()) {
			GameToken token = entityTokenMap.get(target);
			
			EventHandler<ActionEvent> clickedHander = new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					disableTargetSelection();
					targetOptions.getBehaviour().setSelectedTarget(target);
				}
			};
			token.showTargetMarker(clickedHander);
		}
	}
	
	private void enableSummonTargets(final HumanTargetOptions targetOptions) {
		GameAction action = targetOptions.getAction();
		for (final Actor target : action.getValidTargets()) {
			GameToken token = entityTokenMap.get(target);
			Button summonHelper = summonHelperMap.get(token);
			summonHelper.setVisible(true);
			summonHelper.setManaged(true);
			EventHandler<ActionEvent> clickedHander = new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					disableTargetSelection();
					targetOptions.getBehaviour().setSelectedTarget(target);
				}
			};
			summonHelper.setOnAction(clickedHander);
		}
	}
	
	private void disableTargetSelection() {
		for (GameToken token : entityTokenMap.values()) {
			token.hideTargetMarker();
		}
		for (Button summonHelper : summonHelperMap.values()) {
			summonHelper.setVisible(false);
			summonHelper.setManaged(false);
		}
		hideCenterMessage();
	}
	
	private void setCenterMessage(String message) {
		centerMessageLabel.setText(message);
		centerMessageLabel.setVisible(true);
	}
	
	private void hideCenterMessage() {
		centerMessageLabel.setVisible(false);
	}

	public void updateGameState(GameContextVisualizable context) {
		entityTokenMap.clear();
		p1Hero.setHero(context.getPlayer1());
		entityTokenMap.put(context.getPlayer1().getHero(), p1Hero);
		p2Hero.setHero(context.getPlayer2());
		entityTokenMap.put(context.getPlayer2().getHero(), p2Hero);
		
		updateHandCards(context.getPlayer1(), p1Cards);
		updateHandCards(context.getPlayer2(), p2Cards);
		
		updateMinionTokens(context.getPlayer1(), p1Minions);
		updateMinionTokens(context.getPlayer2(), p2Minions);
		
		checkForWinner(context);
	}
	
	private void checkForWinner(GameContext context) {
		if (context.gameDecided()) {
			if (context.getWinner() == context.getPlayer1()) {
				centerMessageLabel.setStyle("-fx-font-size: 72; -fx-text-fill: green;");
				setCenterMessage("You won!!!");
			} else {
				centerMessageLabel.setStyle("-fx-font-size: 72; -fx-text-fill: red;");
				setCenterMessage("You lost :(");
			}
		}
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
				Minion minion = minions.get(i);
				minionTokens[i].setMinion(minion);
				minionTokens[i].setManaged(true);
				minionTokens[i].setVisible(true);
				entityTokenMap.put(minion, minionTokens[i]);
			} else {
				minionTokens[i].setManaged(false);
				minionTokens[i].setVisible(false);
			}
		}
	}

}

