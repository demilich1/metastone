package net.demilich.metastone.gui.playmode;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.gui.cards.HandCard;
import net.demilich.metastone.gui.playmode.animation.EventVisualizerDispatcher;

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
	private SummonToken[] p1Minions = new SummonToken[GameLogic.MAX_MINIONS];
	private SummonToken[] p2Minions = new SummonToken[GameLogic.MAX_MINIONS];

	private final HashMap<GameToken, Button> summonHelperMap1 = new HashMap<GameToken, Button>();
	private final HashMap<GameToken, Button> summonHelperMap2 = new HashMap<GameToken, Button>();
	private final HashMap<Actor, GameToken> entityTokenMap = new HashMap<Actor, GameToken>();

	private final EventVisualizerDispatcher gameEventVisualizer = new EventVisualizerDispatcher();

	@FXML
	private Label centerMessageLabel;

	public GameBoardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameBoardView.fxml"));
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
			p1Cards[i].setVisible(false);
			p2Cards[i] = new HandCard();
			p2Cards[i].setVisible(false);
		}
		p1CardPane.getChildren().addAll(p1Cards);
		p2CardPane.getChildren().addAll(p2Cards);

		// initialize minion tokens elements
		for (int i = 0; i < p1Minions.length; i++) {
			Button summonHelper = createSummonHelper();
			p1MinionPane.getChildren().add(summonHelper);
			p1Minions[i] = new SummonToken();
			p1MinionPane.getChildren().add(p1Minions[i]);
			summonHelperMap1.put(p1Minions[i], summonHelper);

			summonHelper = createSummonHelper();
			p2MinionPane.getChildren().add(summonHelper);
			p2Minions[i] = new SummonToken();
			p2MinionPane.getChildren().add(p2Minions[i]);
			summonHelperMap2.put(p2Minions[i], summonHelper);
		}
		// create one additional summon helper (for each player)
		Button summonHelper = createSummonHelper();
		p1MinionPane.getChildren().add(summonHelper);
		summonHelperMap1.put(null, summonHelper);

		summonHelper = createSummonHelper();
		p2MinionPane.getChildren().add(summonHelper);
		summonHelperMap2.put(null, summonHelper);

		p1Hero = new HeroToken();
		p2Hero = new HeroToken();

		p1HeroAnchor.getChildren().add(p1Hero);
		p2HeroAnchor.getChildren().add(p2Hero);
	}

	private void checkForWinner(GameContext context) {
		if (context.gameDecided()) {
			if (context.getWinningPlayerId() == -1) {
				centerMessageLabel.setStyle("-fx-text-fill: red;");
				setCenterMessage("Game has ended in a draw.");
			} else {
				centerMessageLabel.setStyle("-fx-text-fill: green;");
				Player winner = context.getPlayer(context.getWinningPlayerId());
				setCenterMessage("Player " + winner.getName() + " has won the game.");
			}
		}

	}

	private Button createSummonHelper() {
		ImageView icon = new ImageView(IconFactory.getSummonHelper());
		icon.setFitWidth(32);
		icon.setFitHeight(32);
		Button helper = new Button("", icon);
		helper.setStyle("-fx-padding: 2 2 2 2;");
		helper.setVisible(false);
		helper.setManaged(false);
		return helper;
	}

	public void disableTargetSelection() {
		for (GameToken token : entityTokenMap.values()) {
			token.hideTargetMarker();
		}
		for (Button summonHelper : summonHelperMap1.values()) {
			summonHelper.setVisible(false);
			summonHelper.setManaged(false);
		}
		for (Button summonHelper : summonHelperMap2.values()) {
			summonHelper.setVisible(false);
			summonHelper.setManaged(false);
		}
		hideCenterMessage();
	}

	private void enableSpellTargets(final HumanTargetOptions targetOptions) {
		GameContext context = targetOptions.getContext();

		for (final GameAction action : targetOptions.getActionGroup().getActionsInGroup()) {
			Entity target = context.resolveSingleTarget(action.getTargetKey());
			GameToken token = getToken(target);

			EventHandler<MouseEvent> clickedHander = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					disableTargetSelection();
					targetOptions.getActionSelectionListener().onActionSelected(action);
				}
			};

			token.showTargetMarker(clickedHander);
		}
	}

	private void enableSummonTargets(final HumanTargetOptions targetOptions) {
		int playerId = targetOptions.getPlayerId();
		GameContext context = targetOptions.getContext();
		for (final GameAction action : targetOptions.getActionGroup().getActionsInGroup()) {
			Entity target = context.resolveSingleTarget(action.getTargetKey());
			GameToken token = getToken(target);
			Button summonHelper = playerId == 0 ? summonHelperMap1.get(token) : summonHelperMap2.get(token);
			summonHelper.setVisible(true);
			summonHelper.setManaged(true);
			EventHandler<ActionEvent> clickedHander = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					disableTargetSelection();
					targetOptions.getActionSelectionListener().onActionSelected(action);
				}
			};
			summonHelper.setOnAction(clickedHander);
		}
	}

	public void enableTargetSelection(final HumanTargetOptions targetOptions) {
		GameAction action = targetOptions.getActionGroup().getPrototype();
		if (action.getActionType() == ActionType.SUMMON) {
			enableSummonTargets(targetOptions);
		} else {
			enableSpellTargets(targetOptions);
		}
		setCenterMessage("Select target for " + action.getPromptText() + " - ESC to cancel");
	}

	public GameToken getToken(Entity entity) {
		return entityTokenMap.get(entity);
	}

	private void hideCenterMessage() {
		centerMessageLabel.setVisible(false);
	}

	private void setCenterMessage(String message) {
		centerMessageLabel.setText(message);
		centerMessageLabel.setVisible(true);
	}

	public void showAnimations(GameContext context) {
		gameEventVisualizer.visualize((GameContextVisualizable) context, this);
	}

	public void updateGameState(GameContext context) {
		entityTokenMap.clear();
		p1Hero.setHero(context.getPlayer1());
		p1Hero.updateHeroPowerCost(context, context.getPlayer1());
		p1Hero.highlight(context.getActivePlayer() == context.getPlayer1());
		entityTokenMap.put(context.getPlayer1().getHero(), p1Hero);
		p2Hero.setHero(context.getPlayer2());
		p2Hero.updateHeroPowerCost(context, context.getPlayer2());
		p2Hero.highlight(context.getActivePlayer() == context.getPlayer2());
		entityTokenMap.put(context.getPlayer2().getHero(), p2Hero);

		updateHandCards(context, context.getPlayer1(), p1Cards);
		updateHandCards(context, context.getPlayer2(), p2Cards);

		updateSummonTokens(context.getPlayer1(), p1Minions);
		updateSummonTokens(context.getPlayer2(), p2Minions);

		checkForWinner(context);
	}

	private void updateHandCards(GameContext context, Player player, HandCard[] handCards) {
		CardCollection hand = player.getHand();
		for (int i = 0; i < handCards.length; i++) {
			if (i < hand.getCount()) {
				handCards[i].setManaged(true);
				handCards[i].setVisible(true);
				handCards[i].setCard(context, hand.get(i), player);

			} else {
				handCards[i].setManaged(false);
				handCards[i].setVisible(false);
			}
		}
	}

	private void updateSummonTokens(Player player, SummonToken[] summonTokens) {
		List<Summon> summons = player.getSummons();
		for (int i = 0; i < summonTokens.length; i++) {
			if (i < summons.size()) {
				Summon summon = summons.get(i);
				summonTokens[i].setSummon(summon);
				summonTokens[i].setManaged(true);
				summonTokens[i].setVisible(true);
				entityTokenMap.put(summon, summonTokens[i]);
			} else {
				summonTokens[i].setManaged(false);
				summonTokens[i].setVisible(false);
			}
		}
	}
}
