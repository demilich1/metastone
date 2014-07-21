package net.pferdimanzug.hearthstone.analyzer.gui.playmode.config;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class RequestDecksCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		
		getFacade().sendNotification(GameNotification.LOAD_DECKS);
		
		List<Deck> decks = deckProxy.getDecks();
		getFacade().sendNotification(GameNotification.ANSWER_DECKS, decks);
	}

	

}
