package net.demilich.metastone.gui.playmode;

import java.io.IOException;
import java.util.*;

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
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.visuals.GameContextVisuals;
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
	private MinionToken[] p1Minions = new MinionToken[GameLogic.MAX_MINIONS];
	private MinionToken[] p2Minions = new MinionToken[GameLogic.MAX_MINIONS];

	private final HashMap<GameToken, Button> summonHelperMap1 = new HashMap<>();
	private final HashMap<GameToken, Button> summonHelperMap2 = new HashMap<>();
	private final Map<Entity, GameToken> entityTokenMap = new EntityGameTokenMap();

	private final Button p1RightmostSummon;
	private final Button p2RightmostSummon;

	private final EventVisualizerDispatcher gameEventVisualizer = new EventVisualizerDispatcher();

	private int localPlayerId;

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
			p1Minions[i] = new MinionToken();
			p1MinionPane.getChildren().add(p1Minions[i]);
			summonHelperMap1.put(p1Minions[i], summonHelper);

			summonHelper = createSummonHelper();
			p2MinionPane.getChildren().add(summonHelper);
			p2Minions[i] = new MinionToken();
			p2MinionPane.getChildren().add(p2Minions[i]);
			summonHelperMap2.put(p2Minions[i], summonHelper);
		}
		// create one additional summon helper (for each player)
		p1RightmostSummon = createSummonHelper();
		p1MinionPane.getChildren().add(p1RightmostSummon);
		summonHelperMap1.put(null, p1RightmostSummon);

		p2RightmostSummon = createSummonHelper();
		p2MinionPane.getChildren().add(p2RightmostSummon);
		summonHelperMap2.put(null, p2RightmostSummon);

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

			EventHandler<MouseEvent> clickedHander = event -> {
				disableTargetSelection();
				targetOptions.getActionSelectionListener().onActionSelected(action);
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
			Button summonHelper = playerId == localPlayerId ? summonHelperMap1.get(token) : summonHelperMap2.get(token);
			if (summonHelper == null) {
				summonHelper = getRightmostSummon(playerId == localPlayerId ? 0 : 1);
			}
			summonHelper.setVisible(true);
			summonHelper.setManaged(true);
			EventHandler<ActionEvent> clickedHander = event -> {
				disableTargetSelection();
				targetOptions.getActionSelectionListener().onActionSelected(action);
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
		gameEventVisualizer.visualize((GameContextVisuals) context, this);
	}

	public void updateGameState(GameContext context) {
		entityTokenMap.clear();
		PlayerVisuals playerVisuals = new PlayerVisuals(context).invoke();
		Player localPlayer = playerVisuals.getLocalPlayer();
		Player opponentPlayer = playerVisuals.getOpponentPlayer();

		localPlayerId = localPlayer.getId();
		p1Hero.setHero(localPlayer);
		p1Hero.updateHeroPowerCost(context, localPlayer);
		p1Hero.highlight(context.getActivePlayerId() == localPlayer.getId());
		entityTokenMap.put(localPlayer, p1Hero);
		entityTokenMap.put(localPlayer.getHero(), p1Hero);
		p2Hero.setHero(opponentPlayer);
		p2Hero.updateHeroPowerCost(context, opponentPlayer);
		p2Hero.highlight(context.getActivePlayerId() == opponentPlayer.getId());
		entityTokenMap.put(opponentPlayer, p2Hero);
		entityTokenMap.put(opponentPlayer.getHero(), p2Hero);

		updateHandCards(context, localPlayer, p1Cards);
		updateHandCards(context, opponentPlayer, p2Cards);

		updateMinionTokens(localPlayer, p1Minions);
		updateMinionTokens(opponentPlayer, p2Minions);

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

	private Button getRightmostSummon(int playerId) {
		if (playerId == 0) {
			return p1RightmostSummon;
		} else {
			return p2RightmostSummon;
		}
	}

	private class PlayerVisuals {
		private GameContext context;
		private Player localPlayer;
		private Player opponentPlayer;

		public PlayerVisuals(GameContext context) {
			this.context = context;
		}

		public Player getLocalPlayer() {
			return localPlayer;
		}

		public Player getOpponentPlayer() {
			return opponentPlayer;
		}

		public PlayerVisuals invoke() {
			localPlayer = context.getPlayer1();
			opponentPlayer = context.getPlayer2();

			// Try to configure the players based on what data is available.
			if (context instanceof GameContextVisuals) {
				int localPlayerId = ((GameContextVisuals) context).getLocalPlayerId();

				if (context.hasPlayer(localPlayerId)) {
					localPlayer = context.getPlayer(localPlayerId);
				} else {
					localPlayer = Player.Empty();
					localPlayer.setId(localPlayerId);
				}

				final int opponentId = localPlayerId == GameContext.PLAYER_1 ? GameContext.PLAYER_2 : GameContext.PLAYER_1;

				if (context.hasPlayer(opponentId)) {
					opponentPlayer = context.getOpponent(localPlayer);
				} else {
					opponentPlayer = Player.Empty();
					opponentPlayer.setId(opponentId);
				}
			}
			return this;
		}
	}
}
