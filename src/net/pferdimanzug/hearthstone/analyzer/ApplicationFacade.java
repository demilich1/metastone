package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks.StartBattleOfDecksCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.AddCardToDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.ChangeDeckNameCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.FillDeckWithRandomCardsCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.FilterCardsByTextCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.ImportDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.LoadDecksCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.RemoveCardFromDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.SaveDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.SetActiveDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.StartGameCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation.AnimationCompletedCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation.AnimationLockCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation.AnimationStartedCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.RequestDecksCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.CreateNewSandboxCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.ModifyPlayerDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.ModifyPlayerHandCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.PerformActionCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.SelectPlayerCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.SpawnMinionCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.StartPlaySandboxCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands.StopPlaySandboxCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.simulationmode.SimulateGamesCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.trainingmode.PerformTrainingCommand;
import de.pferdimanzug.nittygrittymvc.Facade;
import de.pferdimanzug.nittygrittymvc.interfaces.IFacade;

public class ApplicationFacade extends Facade<GameNotification> {

	@SuppressWarnings("unchecked")
	public static IFacade<GameNotification> getInstance() {
		if (instance == null) {
			instance = new ApplicationFacade();
		}

		return instance;
	}

	public ApplicationFacade() {
		registerCommand(GameNotification.APPLICATION_STARTUP, new ApplicationStartupCommand());
		registerCommand(GameNotification.START_GAME, new StartGameCommand());
		registerCommand(GameNotification.PLAY_GAME, new PlayGameCommand());
		registerCommand(GameNotification.SIMULATE_GAMES, new SimulateGamesCommand());
		registerCommand(GameNotification.START_TRAINING, new PerformTrainingCommand());
		registerCommand(GameNotification.COMMIT_BATTLE_OF_DECKS_CONFIG, new StartBattleOfDecksCommand());

		registerCommand(GameNotification.SET_ACTIVE_DECK, new SetActiveDeckCommand());
		registerCommand(GameNotification.ADD_CARD_TO_DECK, new AddCardToDeckCommand());
		registerCommand(GameNotification.REMOVE_CARD_FROM_DECK, new RemoveCardFromDeckCommand());
		registerCommand(GameNotification.SAVE_ACTIVE_DECK, new SaveDeckCommand());
		registerCommand(GameNotification.LOAD_DECKS, new LoadDecksCommand());
		registerCommand(GameNotification.FILTER_CARDS_BY_TEXT, new FilterCardsByTextCommand());
		registerCommand(GameNotification.FILL_DECK_WITH_RANDOM_CARDS, new FillDeckWithRandomCardsCommand());
		registerCommand(GameNotification.IMPORT_DECK_FROM_URL, new ImportDeckCommand());
		registerCommand(GameNotification.CHANGE_DECK_NAME, new ChangeDeckNameCommand());

		registerCommand(GameNotification.REQUEST_DECKS, new RequestDecksCommand());

		registerCommand(GameNotification.CREATE_NEW_SANDBOX, new CreateNewSandboxCommand());
		registerCommand(GameNotification.MODIFY_PLAYER_DECK, new ModifyPlayerDeckCommand());
		registerCommand(GameNotification.MODIFY_PLAYER_HAND, new ModifyPlayerHandCommand());
		registerCommand(GameNotification.SELECT_PLAYER, new SelectPlayerCommand());
		registerCommand(GameNotification.SPAWN_MINION, new SpawnMinionCommand());
		registerCommand(GameNotification.PERFORM_ACTION, new PerformActionCommand());
		registerCommand(GameNotification.START_PLAY_SANDBOX, new StartPlaySandboxCommand());
		registerCommand(GameNotification.STOP_PLAY_SANDBOX, new StopPlaySandboxCommand());

		registerCommand(GameNotification.GAME_STATE_UPDATE, new AnimationLockCommand());
		registerCommand(GameNotification.ANIMATION_STARTED, new AnimationStartedCommand());
		registerCommand(GameNotification.ANIMATION_COMPLETED, new AnimationCompletedCommand());
	}

	public void startUp(HearthstoneAnalyzer main) {
		sendNotification(GameNotification.APPLICATION_STARTUP);
	}

}
