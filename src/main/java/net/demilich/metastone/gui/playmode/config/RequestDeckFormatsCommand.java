package net.demilich.metastone.gui.playmode.config;

import java.util.List;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.gui.deckbuilder.DeckFormatProxy;

public class RequestDeckFormatsCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckFormatProxy deckFormatProxy = (DeckFormatProxy) getFacade().retrieveProxy(DeckFormatProxy.NAME);

		getFacade().sendNotification(GameNotification.LOAD_DECK_FORMATS);
		
		List<DeckFormat> deckFormats = deckFormatProxy.getDeckFormats();
		getFacade().sendNotification(GameNotification.REPLY_DECK_FORMATS, deckFormats);
	}

}
