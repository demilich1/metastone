package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.AddCardToDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.SetActiveDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.FillDeckWithRandomCardsCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.SaveDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.FilterCardsByTextCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.LoadDecksCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.RemoveCardFromDeckCommand;
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
		
		registerCommand(GameNotification.SET_ACTIVE_DECK, new SetActiveDeckCommand());
		registerCommand(GameNotification.ADD_CARD_TO_DECK, new AddCardToDeckCommand());
		registerCommand(GameNotification.REMOVE_CARD_FROM_DECK, new RemoveCardFromDeckCommand());
		registerCommand(GameNotification.SAVE_ACTIVE_DECK, new SaveDeckCommand());
		registerCommand(GameNotification.LOAD_DECKS, new LoadDecksCommand());
		registerCommand(GameNotification.FILTER_CARDS_BY_TEXT, new FilterCardsByTextCommand());
		registerCommand(GameNotification.FILL_DECK_WITH_RANDOM_CARDS, new FillDeckWithRandomCardsCommand());

	}

	public void startUp(HearthstoneAnalyzer main) {
		sendNotification(GameNotification.APPLICATION_STARTUP);
	}

}
