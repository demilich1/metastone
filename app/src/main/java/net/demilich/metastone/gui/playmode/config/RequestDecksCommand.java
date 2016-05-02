package net.demilich.metastone.gui.playmode.config;

import java.util.List;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.deckbuilder.DeckProxy;

public class RequestDecksCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);

		getFacade().sendNotification(GameNotification.LOAD_DECKS);

		List<Deck> decks = deckProxy.getDecks();
		getFacade().sendNotification(GameNotification.REPLY_DECKS, decks);
	}

}
