package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.AddCardToDeckCommand;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.CreateNewDeckCommand;
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
		registerCommand(GameNotification.CLASS_CHOSEN, new CreateNewDeckCommand());
		registerCommand(GameNotification.ADD_CARD_TO_DECK, new AddCardToDeckCommand());
		registerCommand(GameNotification.REMOVE_CARD_FROM_DECK, new RemoveCardFromDeckCommand());

	}

	public void startUp(HearthstoneAnalyzer main) {
		sendNotification(GameNotification.APPLICATION_STARTUP);
	}

}
