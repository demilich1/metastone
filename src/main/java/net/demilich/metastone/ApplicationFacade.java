package net.demilich.metastone;

import net.demilich.nittygrittymvc.Facade;
import net.demilich.nittygrittymvc.interfaces.IFacade;
import net.demilich.metastone.gui.autoupdate.CheckForUpdateCommand;
import net.demilich.metastone.gui.battleofdecks.StartBattleOfDecksCommand;
import net.demilich.metastone.gui.deckbuilder.AddCardToDeckCommand;
import net.demilich.metastone.gui.deckbuilder.ChangeDeckNameCommand;
import net.demilich.metastone.gui.deckbuilder.DeleteDeckCommand;
import net.demilich.metastone.gui.deckbuilder.FillDeckWithRandomCardsCommand;
import net.demilich.metastone.gui.deckbuilder.FilterCardsCommand;
import net.demilich.metastone.gui.deckbuilder.ImportDeckCommand;
import net.demilich.metastone.gui.deckbuilder.LoadDeckFormatsCommand;
import net.demilich.metastone.gui.deckbuilder.LoadDecksCommand;
import net.demilich.metastone.gui.deckbuilder.RemoveCardFromDeckCommand;
import net.demilich.metastone.gui.deckbuilder.SaveDeckCommand;
import net.demilich.metastone.gui.deckbuilder.SetActiveDeckCommand;
import net.demilich.metastone.gui.deckbuilder.metadeck.AddDeckToMetaDeckCommand;
import net.demilich.metastone.gui.deckbuilder.metadeck.RemoveDeckFromMetaDeckCommand;
import net.demilich.metastone.gui.playmode.StartGameCommand;
import net.demilich.metastone.gui.playmode.animation.AnimationCompletedCommand;
import net.demilich.metastone.gui.playmode.animation.AnimationLockCommand;
import net.demilich.metastone.gui.playmode.animation.AnimationStartedCommand;
import net.demilich.metastone.gui.playmode.config.RequestDeckFormatsCommand;
import net.demilich.metastone.gui.playmode.config.RequestDecksCommand;
import net.demilich.metastone.gui.sandboxmode.commands.CreateNewSandboxCommand;
import net.demilich.metastone.gui.sandboxmode.commands.ModifyPlayerDeckCommand;
import net.demilich.metastone.gui.sandboxmode.commands.ModifyPlayerHandCommand;
import net.demilich.metastone.gui.sandboxmode.commands.PerformActionCommand;
import net.demilich.metastone.gui.sandboxmode.commands.SelectPlayerCommand;
import net.demilich.metastone.gui.sandboxmode.commands.SpawnMinionCommand;
import net.demilich.metastone.gui.sandboxmode.commands.StartPlaySandboxCommand;
import net.demilich.metastone.gui.sandboxmode.commands.StopPlaySandboxCommand;
import net.demilich.metastone.gui.simulationmode.SimulateGamesCommand;
import net.demilich.metastone.gui.trainingmode.PerformTrainingCommand;
import net.demilich.metastone.gui.trainingmode.RequestTrainingDataCommand;
import net.demilich.metastone.gui.trainingmode.SaveTrainingDataCommand;

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
		
		registerCommand(GameNotification.CHECK_FOR_UPDATE, new CheckForUpdateCommand());

		registerCommand(GameNotification.SET_ACTIVE_DECK, new SetActiveDeckCommand());
		registerCommand(GameNotification.ADD_CARD_TO_DECK, new AddCardToDeckCommand());
		registerCommand(GameNotification.REMOVE_CARD_FROM_DECK, new RemoveCardFromDeckCommand());
		registerCommand(GameNotification.SAVE_ACTIVE_DECK, new SaveDeckCommand());
		registerCommand(GameNotification.LOAD_DECKS, new LoadDecksCommand());
		registerCommand(GameNotification.LOAD_DECK_FORMATS, new LoadDeckFormatsCommand());
		registerCommand(GameNotification.FILTER_CARDS, new FilterCardsCommand());
		registerCommand(GameNotification.FILL_DECK_WITH_RANDOM_CARDS, new FillDeckWithRandomCardsCommand());
		registerCommand(GameNotification.IMPORT_DECK_FROM_URL, new ImportDeckCommand());
		registerCommand(GameNotification.CHANGE_DECK_NAME, new ChangeDeckNameCommand());
		registerCommand(GameNotification.ADD_DECK_TO_META_DECK, new AddDeckToMetaDeckCommand());
		registerCommand(GameNotification.REMOVE_DECK_FROM_META_DECK, new RemoveDeckFromMetaDeckCommand());
		registerCommand(GameNotification.DELETE_DECK, new DeleteDeckCommand());

		registerCommand(GameNotification.REQUEST_DECKS, new RequestDecksCommand());
		registerCommand(GameNotification.REQUEST_DECK_FORMATS, new RequestDeckFormatsCommand());

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

		registerCommand(GameNotification.SAVE_TRAINING_DATA, new SaveTrainingDataCommand());
		registerCommand(GameNotification.REQUEST_TRAINING_DATA, new RequestTrainingDataCommand());
	}

	public void startUp(MetaStone main) {
		sendNotification(GameNotification.APPLICATION_STARTUP);
	}

}
